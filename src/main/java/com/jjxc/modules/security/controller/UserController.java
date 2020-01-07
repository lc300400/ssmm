package com.jjxc.modules.security.controller;
  
import com.jjxc.commons.*;
import com.jjxc.modules.security.entity.Dept;
import com.jjxc.modules.security.entity.Parameter;
import com.jjxc.modules.security.entity.User;
import com.jjxc.modules.security.service.ParameterService;
import com.jjxc.modules.security.service.RoleService;
import com.jjxc.modules.security.service.UserService;
import com.jjxc.utils.Struts2Utils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

  
@Controller  
@RequestMapping("/security/user")  
public class UserController extends PageFrom<User>{  
	
    @Resource  
    private UserService userService;  
    @Resource  
    private RoleService roleService;  
    @Resource  
    private ParameterService parameterService;  
    
    
    //跳转到新增、修改页面
	@SuppressWarnings("unchecked")
	@RequestMapping("/input")
    public String toInput(HttpServletRequest request,Model model){
    	User user = null;
    	List<Integer> checkedRoleIds = new ArrayList<>();//页面中钩选的角色id列表
    	String userId = request.getParameter("id");
    	if(!"".equals(userId) && userId !=null){
    		user = userService.getUserById(Integer.parseInt(userId));
    		checkedRoleIds = ReflectionUtils.convertElementPropertyToList(user.getRoleList(), "id");
    	}
    	//获取系统默认密码
    	Parameter par = parameterService.queryByName(Parameter.KEY_DEFAULT_PASSWORD);
    	String defaultPassword = par.getValue();
        model.addAttribute("user", user);  
        model.addAttribute("checkedRoleIds", checkedRoleIds);  
        model.addAttribute("roleList", roleService.queryAll());  
        model.addAttribute("defaultPassword", defaultPassword);  
        return "/security/user-input"; 
    }
	
	//新增、修改用户信息
	@RequestMapping("/save")
	@ResponseBody
    public void save(HttpServletRequest request,User user){  
		Json json = new Json();
    	try {
    		String roleStr = request.getParameter("roleChecked");
    		String deptId = request.getParameter("deptId");
    		user.setEnable("1");//默认用户状态“启用”
    		user.setDept(new Dept(Integer.parseInt(deptId)));
    		userService.saveUserInfo(user,roleStr);
    		json.setSuccess(true);
    		json.setMsg("用户信息保存成功");
		}catch (ServiceException e) {
			json.setSuccess(false);
			json.setMsg(e.getMessage());
			logger.error(e.getMessage());
		}catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("用户信息保存失败");
			logger.error(e.getMessage());
		}finally{
			writeJson(json);
		}
    }  
	
    
    //查询用户个人信息
    @RequestMapping("/getPersonalInfo")
    public String getPersonalInfo(Model model){
    	String currentUsername= (String) SecurityUtils.getSubject().getPrincipal();
    	User user = userService.getUserByUsername(currentUsername);
        model.addAttribute("user", user);  
        return "/personal/user"; 
    }
    
    //用户密码修改
    @RequestMapping("/updatePwd")
    @ResponseBody
    public void updatePwd(HttpServletRequest request){
		Json json = new Json();
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		try {
			userService.updatePassword(oldPassword, newPassword);
			json.setSuccess(true);
			json.setMsg("密码修改成功");
		} catch (ServiceException e) {
			json.setSuccess(false);
			json.setMsg(e.getMessage());
			logger.error(e.getMessage());
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("密码修改失败");
			logger.error(e.getMessage());
		} finally {
			writeJson(json);
		}
    }
    
    //根据部门查询用户信息
    @RequestMapping("/listByDept")
    @ResponseBody
    public void list(HttpServletRequest request) {
		// EasyUI dataGrid数据
		Page<User> page = getPageFromEasyUI(request);
		page.setOrderBy("id");
		page.setOrder(Page.DESC);
		String branchNO = request.getParameter("branchNO");
		String children = request.getParameter("children");
		String username = request.getParameter("username");
		String enable = request.getParameter("enable");
		//设置条件过滤
		Map<String ,Object> map = new HashMap<>();
		if(!"".equals(username) && username !=null){
			map.put("username", username);
		}
		if(!"".equals(enable) && enable !=null){
			map.put("enable", enable);
		}
		if (!"".equals(branchNO) && branchNO !=null) {
			if("all".equals(children)) {
				int length = branchNO.length();
				while (branchNO.endsWith("000")) {
					length -= 3;
					branchNO = branchNO.substring(0, length);
				}
				if (length > 0) {
					map.put("all", "yes");//是否查询下级-是
				}
			}else {
				map.put("all", "no");//是否查询下级-否
			}
			map.put("branchNO", branchNO);
		}
		page.setParams(map);
	    List<User> userL = userService.findPage(page);
	    page.setResult(userL);
		DataGrid dataGrid = converEasyUIResponse(page);
		String[] excludesProperties = { "password", "roleList", "parent","children" };
		writeJsonByExcludesProperties(dataGrid, excludesProperties);
	}
    
    
    /**
	 * 密码重置
	 */
    @RequestMapping("/resetPassword") 
	@ResponseBody
	public void resetPassword(HttpServletRequest request) {
		Json json = new Json();
		try {
			String id = request.getParameter("id");
			userService.resetPassword(Integer.parseInt(id));
			json.setSuccess(true);
			json.setMsg("密码重置成功");
		} catch (ServiceException e) {
			json.setSuccess(false);
			json.setMsg(e.getMessage());
			logger.error(e.getMessage());
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("密码重置失败");
			logger.error(e.getMessage());
		} finally {
			writeJson(json);
		}
	}
    
    /**
	 * 启用用户
	 */
    @RequestMapping("/enableUser") 
	@ResponseBody
	public void enableUser(HttpServletRequest request) {
		Json json = new Json();
		try {
			String id = request.getParameter("id");
			userService.enableUser(Integer.parseInt(id));
			json.setSuccess(true);
			json.setMsg("用户启用成功");
		} catch (ServiceException e) {
			json.setSuccess(false);
			json.setMsg(e.getMessage());
			logger.error(e.getMessage());
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("用户启用失败");
			logger.error(e.getMessage());
		} finally {
			writeJson(json);
		}
	}
    
    
    
    /**
     * 停用用户
     */
    @RequestMapping("/disable") 
    @ResponseBody
    public void disable(HttpServletRequest request) {
    	Json json = new Json();
    	try {
    		String id = request.getParameter("id");
    		userService.disableUser(Integer.parseInt(id));
    		json.setSuccess(true);
    		json.setMsg("用户停用成功");
    	} catch (ServiceException e) {
    		json.setSuccess(false);
    		json.setMsg(e.getMessage());
    		logger.error(e.getMessage());
    	} catch (Exception e) {
    		json.setSuccess(false);
    		json.setMsg("用户停用失败");
    		logger.error(e.getMessage());
    	} finally {
    		writeJson(json);
    	}
    }
    
    /**
	 * AJAX异步检查权限名称是否唯一.
	 */
	@RequestMapping("/checkUsernameUnique") 
	@ResponseBody
	public void checkNameUnique(HttpServletRequest request) {
		String newUsername = request.getParameter("username");
		String oldUsername = request.getParameter("oldUsername");
		if (userService.isUsernameUnique(newUsername, oldUsername)) {
			Struts2Utils.renderText("true");
		} else {
			Struts2Utils.renderText("false");
		}
	}
} 