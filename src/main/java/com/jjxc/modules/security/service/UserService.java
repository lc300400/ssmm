package com.jjxc.modules.security.service;
  
import com.jjxc.commons.Page;
import com.jjxc.commons.ServiceException;
import com.jjxc.modules.security.dao.ParameterDao;
import com.jjxc.modules.security.dao.UserDao;
import com.jjxc.modules.security.entity.Parameter;
import com.jjxc.modules.security.entity.User;
import com.jjxc.utils.DecriptUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
  
@Service
public class UserService{  
	
	private static Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
    private UserDao userDao;  
    @Resource  
    private ParameterDao parameterDao;  

    public User getUserById(Integer userId) {  
        return userDao.queryById(userId);  
    }  

	public User getUserByUsername(String username) {
		return userDao.queryByUsername(username);
	}

	/**
	 * 保存、修改用户信息
	 * @param user
	 * @param roleList
	 */
	public void saveUserInfo(User user,String roleList) {
		String currentUsername = (String) SecurityUtils.getSubject().getPrincipal();
		if(user.getUsername().equals("admin")){
			logger.warn("操作员{}尝试修改超级管理员用户", currentUsername);
			throw new ServiceException("不能修改超级管理员用户");
		}
		List<Integer> roleIntegerList = strToList(roleList);
		if(user.getId() == null ){
			Parameter par = parameterDao.queryByName(Parameter.KEY_DEFAULT_PASSWORD);
			user.setPassword(DecriptUtil.MD5(par.getValue()));//设置用户默认密码
			userDao.insertUser(user);
			if(roleIntegerList !=null && roleIntegerList.size()>0){
				userDao.saveUserRole(user.getId(), roleIntegerList);
			}
			logger.info("操作员{}新增用户信息 [ID={},用户账号={},用户姓名={}]", 
					new Object[]{currentUsername, user.getId(), user.getUsername(), user.getName()});
		}else {
			userDao.updateUser(user);
			userDao.deleteUserRoleByUserId(user.getId());
			if(roleIntegerList !=null && roleIntegerList.size()>0){
				userDao.saveUserRole(user.getId(), roleIntegerList);
			}
			logger.info("操作员{}修改用户信息 [ID={},用户账号={},用户姓名={}]", 
					new Object[]{currentUsername, user.getId(), user.getUsername(), user.getName()});
		}
	}
	

	private  List<Integer> strToList(String listStr) {
		if(listStr == null || listStr.equals("")){
			return null;
		}
		String[] str_arr = listStr.split(",");
		List<String> asList = Arrays.asList(str_arr);
		List<Integer> list = new ArrayList<>();
		for (String str : asList) {
			list.add(Integer.parseInt(str));
		}
		return list;
	}
	
	/**
	 * 修改用户密码,如果用户密码为空将抛出异常.
	 * @param oldPassword 旧密码
	 * @param newPassword 新密码
	 */
	public void updatePassword(String oldPassword, String newPassword) {
		String username = (String) SecurityUtils.getSubject().getPrincipal();

		if(!StringUtils.hasText(newPassword)) {
			logger.warn("用户{}尝试将新密码设置为空", username);
			throw new ServiceException("密码不能设置为空");
		}
		User user = getCurrentUser();
		if(user == null) {
			logger.warn("用户{}未查询到自己的用户信息", username);
			throw new ServiceException(String.format("用户修改密码失败, 未查询到当前用户%s的用户信息.", username));
		}
		String oldMd5Password = DecriptUtil.MD5(oldPassword);
		if(!user.getPassword().equals(oldMd5Password)) {
			logger.warn("用户{}修改密码失败,旧密码验证失败", username);
			throw new ServiceException("旧密码验证错误");
		}
		String newMd5Password = DecriptUtil.MD5(newPassword);
		user.setPassword(newMd5Password);
		userDao.updateUser(user);
		logger.info("用户{}修改自己的用户密码", new Object[]{username});
		//更新shiro中用户密码
		try {
			UsernamePasswordToken token = new UsernamePasswordToken();
			token.setPassword(newMd5Password.toCharArray());
			SecurityUtils.getSubject().isRunAs();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("更新shiro失败： {}", e.getMessage());
		}
	}
	
	/**
	 * 获取当前用户信息.
	 */
	public User getCurrentUser() {
		return userDao.queryByUsername(SecurityUtils.getSubject().getPrincipal()+"");
	}
	
	/**
	 * 分页查询用户信息
	 */
	public List<User> findPage(Page page){
		return userDao.findPage(page);
	}
	
	/**
	 * 检查用户名是否唯一.
	 * @return username在数据库中唯一或等于oldUsername时返回true.
	 */
	public boolean isUsernameUnique(String newUsername, String oldUsername) {
		if (newUsername != null && newUsername.equals(oldUsername)) {
			return true;
		}
		if(newUsername==null || newUsername.equals("")){
			return false;
		}
		User user = userDao.queryByUsername(newUsername);
		return (user==null);
	}
	
	/**
	 * 重置用户密码,如果用户不存在将抛出异常.
	 * @param id 用户ID
	 */
	public void resetPassword(Integer id) {
		String currentUsername = (String) SecurityUtils.getSubject().getPrincipal();
		User user =  userDao.queryById(id);
		if(user == null) {
			logger.warn("操作员{}尝试为不存在的用户{}重置密码", new Object[]{currentUsername, id});
			throw new ServiceException(String.format("重置用户密码失败, 用户ID为%s的用户不存在.", id));
		}
		Parameter par = parameterDao.queryByName(Parameter.KEY_DEFAULT_PASSWORD);
		user.setPassword(DecriptUtil.MD5(par.getValue()));//设置用户默认密码
		userDao.updateUser(user);
		logger.info("操作员{}重置用户{}的密码", new Object[]{currentUsername, user.getUsername()});
	}
	
	
	/**
	 * 启用用户,如果用户不存在将抛出异常.
	 * @param id 用户ID
	 */
	public void enableUser(Integer id) {
		String currentUsername = (String) SecurityUtils.getSubject().getPrincipal();
		User user =  userDao.queryById(id);
		if(user == null) {
			logger.warn("操作员{}尝试启用不存在的用户{}", new Object[]{currentUsername, id});
			throw new ServiceException(String.format("启用用户失败, 用户ID为%s的用户不存在.", id));
		}
		//启用
		user.setEnable("1");
		userDao.updateUser(user);
		logger.info("操作员{}启用用户{}", new Object[]{currentUsername, user.getUsername()});
	}
	
	
	/**
	 * 停用用户,如果用户不存在将抛出异常.
	 * @param id 用户ID
	 */
	public void disableUser(Integer id) {
		String currentUsername = (String) SecurityUtils.getSubject().getPrincipal();
		User user = userDao.queryById(id);
		if(user == null) {
			logger.warn("操作员{}尝试停用不存在的用户{}", new Object[]{currentUsername, id});
			throw new ServiceException(String.format("停用用户失败, 用户ID为%s的用户不存在.", id));
		}
		if(user.getUsername().equals("admin")) {
			logger.warn("操作员{}尝试停用超级管理员用户", currentUsername);
			throw new ServiceException("不能停用超级管理员用户");
		}
		//停用
		user.setEnable("0");
		userDao.updateUser(user);
		logger.info("操作员{}停用用户{}", new Object[]{currentUsername, user.getUsername()});
	}
	
  
} 