package com.jjxc.modules.security.service;

import com.jjxc.commons.Page;
import com.jjxc.modules.security.dao.RoleDao;
import com.jjxc.modules.security.entity.Role;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RoleService {
	private static Logger logger = LoggerFactory.getLogger(RoleService.class);
	
	@Autowired RoleDao roleDao;
	
	public Role queryById(int id) {
		return roleDao.queryById(id);
	}
	public Role findRole_AuthorityById(int id) {
		return roleDao.findRole_AuthorityById(id);
	}

	public List<Role> queryAll() {
		return roleDao.queryAll();
	}

	public List<Role> findPage(Page<Role> page) {
		
		return roleDao.findPage(page);
	}
	
	/**
	 * 保存角色信息，同时保存权限-角色关联信息（先删再插）
	 * 若角色ID存在：更新
	 * 若角色ID不存在：插入
	 */
	public void saveRole(Role role,String rListStr) {
		List<Integer> authorityList = str2li(rListStr);
		if(role.getId() != null ){
			insertRole(role,authorityList);
			
		}else {
			editRole(role,authorityList);
		}
	}
	
	/**
	 * 插入角色信息，同时插入权限-角色关联信息
	 */
	//@Transactional(rollbackFor={Exception.class})
	public void insertRole(Role role,List<Integer> authorityList) {
		try{
			roleDao.updateById(role);
			roleDao.deleteRoleAuthorityByRoleId(role.getId());
			if(authorityList!=null && authorityList.size()>0){
				roleDao.saveRole_Authority(role.getId(),authorityList);
			}
			logger.info("操作员{}添加角色信息 [ID={},角色名称={}]", 
					SecurityUtils.getSubject().getPrincipal(),
					role.getId(), role.getName());
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("操作员{}添加角色信息[失败] [ID={},角色名称={},错误信息={}]", 
					SecurityUtils.getSubject().getPrincipal(),
					role.getId(), role.getName(),e.getMessage());
		}
	}
	
	/**
	 * 更新角色信息，同时更新权限-角色关联信息
	 */
	//@Transactional(rollbackFor={Exception.class})
	public void editRole(Role role,List<Integer> authorityList) {
		try{
			roleDao.insertRole(role);
			if(authorityList!=null && authorityList.size()>0){
				roleDao.saveRole_Authority(role.getId(),authorityList);
			}
			logger.info("操作员{}修改角色信息 [ID={},角色名称={}]", 
					SecurityUtils.getSubject().getPrincipal(),
					role.getId(), role.getName());
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("操作员{}修改角色信息[失败] [ID={},角色名称={},错误信息={}]", 
					SecurityUtils.getSubject().getPrincipal(),
					role.getId(), role.getName(),e.getMessage());
		}
	}
	
	/**
	 * 删除信息，同时删除角色-权限关联信息
	 */
	//@Transactional(rollbackFor={Exception.class})
	public void deleteRoleById(int id) {
		
		String currentUsername = (String) SecurityUtils.getSubject().getPrincipal();
		try {
			roleDao.deleteRoleById(id);
			roleDao.deleteRoleAuthorityByRoleId(id);
			logger.info("操作员{}删除角色 [ID={}]", new Object[]{currentUsername, id});
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("操作员{}删除角色 [失败] [ID={},错误信息={}]", 
					new Object[]{currentUsername, e.getMessage()});
		} 
	}
	
	/**
	 * 检查角色名是否可以插入
	 * @param newName
	 * @param oldName
	 * @return boolean
	 * */
	public boolean isNameUnique(String newName, String oldName) {
		//若两次值相同，不做判断
		if (newName != null && newName.equals(oldName)) {
			return true;
		}
		//若新值为空，直接判否
		if(newName==null || newName.equals("")){
			return false;
		}
		return isNameUnique(newName);
	}
	
	private boolean isNameUnique(String roleName) {
		List<Role> extRoleList = roleDao.queryByName(roleName);
		return  (extRoleList==null || (extRoleList.size() <= 0));
	}
	
	private  List<Integer> str2li(String rListStr) {
		if(rListStr==null || rListStr.equals("")){
			return null;
		}
		String[] rl_arr = rListStr.split(",");
		List<String> asList = Arrays.asList(rl_arr); 
		
		 List<Integer> list= new ArrayList<>();
		 for (String str : asList) {
			list.add(Integer.parseInt(str));
		}
		
		return list;
	}
	
}
