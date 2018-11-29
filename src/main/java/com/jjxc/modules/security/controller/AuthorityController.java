package com.jjxc.modules.security.controller;

import com.jjxc.commons.*;
import com.jjxc.modules.security.entity.Authority;
import com.jjxc.modules.security.entity.Resource;
import com.jjxc.modules.security.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/security/authority")
public class AuthorityController extends PageFrom<Authority> {
	
	@Autowired private AuthorityService authorityService;
	
	//获取权限列表
	@RequestMapping("/list") @ResponseBody
	public void getAuthorityList(HttpServletRequest request){
		String name = request.getParameter("name");
    	Page<Authority> page = getPageFromDataTables(request);
    	//设置排序
		page.setOrderBy("id");
		//设置条件过滤
		Map<String ,Object> map = new HashMap<>();
		if(!"".equals(name) && name != null){
			map.put("name", name);
			page.setParams(map);
		}
	    List<Authority> ausList = authorityService.findPage(page);
	    page.setResult(ausList);
	    DataTablesResponse response = converDataTablesResponse(request,page);
		writeJson(response);
	}
	
	//根据ID获取权限数据，获取权限保存页面初始数据，跳转页面
	@RequestMapping("/savePage")  
    public String toInput(HttpServletRequest request,Map<String, Object> model){  
        String authorityId = request.getParameter("id"); 
        Authority authority = null;
        List<Integer> checkedResourceIds = new ArrayList<>();
		if(authorityId != null && !authorityId.equals("")){
			authority = authorityService.findAuthority_ResourceById(Integer.parseInt(authorityId));
			List<Resource> resourceList = authority.getResourceList();
			for (Resource resource : resourceList) {
				checkedResourceIds.add(resource.getId());
			}
		}
		model.put("checkedResourceIds", checkedResourceIds);
        model.put("authority", authority);
        return "/security/authority-input";  
    } 
	
	//保存权限相关信息
	@RequestMapping("/save") @ResponseBody
    public void save(HttpServletRequest request,Authority authority){  
		Json json = new Json();
    	try {
    		String rListStr = request.getParameter("resourceList_input");
    		authorityService.saveAuthority(authority,rListStr);
    		json.setSuccess(true);
    		json.setMsg("权限信息保存成功");
		}catch (ServiceException e) {
			json.setSuccess(false);
			json.setMsg(e.getMessage());
			logger.error(e.getMessage());
		}catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("权限信息保存失败");
			logger.error(e.getMessage());
		}finally{
			writeJson(json);
		}
    }  

	//删除权限相关信息
	@RequestMapping("/delete") @ResponseBody
	public void deleteResource(HttpServletRequest request){
		
		Json json = new Json();
		try {
    		String id = request.getParameter("id");
    		authorityService.deleteAuthorityById(Integer.parseInt(id));
			json.setSuccess(true);
			json.setMsg("删除权限信息成功");
    	} catch (Exception e) {
    		e.printStackTrace();
    		json.setSuccess(false);
    		json.setMsg("删除权限信息失败");
    		logger.error(e.getMessage());
    	}finally{
    		writeJson(json);
    	}
		
	}
	
	
	/**
	 * AJAX异步检查权限名称是否唯一.
	 */
	@RequestMapping("/checkNameUnique.action") @ResponseBody
	public Boolean checkNameUnique(HttpServletRequest request) {
		String newName = request.getParameter("name");
		String oldName = request.getParameter("oldName");
		return authorityService.isNameUnique(newName, oldName);
	}
	
	
}
