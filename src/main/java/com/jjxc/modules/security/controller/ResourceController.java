package com.jjxc.modules.security.controller;

import com.jjxc.commons.Json;
import com.jjxc.commons.PageFrom;
import com.jjxc.commons.ServiceException;
import com.jjxc.commons.SessionUtil;
import com.jjxc.modules.security.entity.Resource;
import com.jjxc.modules.security.service.ResourceService;
import com.jjxc.utils.Struts2Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/security/resource")
public class ResourceController extends PageFrom<Resource>{

	@Autowired private ResourceService resourceService;
	
	//根据ID获取起源数据，跳转页面
	@RequestMapping("/savePage")
	public String input(Map<String, Object> model,HttpServletRequest request){
		String id = request.getParameter("id");
		Resource resource = null;
		if(id != null && !id.equals("")){
			resource = resourceService.getResourceById(Integer.parseInt(id));
		}
		model.put("resource", resource);
		return "/security/resource-input";
	}
	
	//获取资源树数据
	@RequestMapping("/getTreeGridData") @ResponseBody
	public void getTreeGridData(){
		writeJsonByExcludesProperties(resourceService.getFirstLevelMenu(),
				new String[]{"authorityList"});
	}


	@RequestMapping("/getEasyUITree") @ResponseBody
	public void getEasyUITree(){
		String tree = resourceService.getEasyUITree();
		Struts2Utils.renderJson(tree);
		if(logger.isDebugEnabled()){
			logger.debug("返回的前端的资源树：{}", tree);
		}
	}
	
	//保存权限相关信息
	@RequestMapping("/save") @ResponseBody
	public void saveResource(HttpServletRequest request,HttpServletResponse response,
			Resource resource){
		
		Json json = new Json();
    	try {
    		resourceService.saveResource(resource);
    		json.setSuccess(true);
    		json.setMsg("资源信息保存成功");
		}catch (ServiceException e) {
			json.setSuccess(false);
			json.setMsg(e.getMessage());
			logger.error(e.getMessage());
		}catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("资源信息保存失败");
			logger.error(e.getMessage());
		}finally{
			writeJson(json);
		}
		
	}
	
	//删除资源相关信息
	@RequestMapping("/delete") @ResponseBody
	public void deleteResource(HttpServletRequest request,HttpServletResponse response){
		
		Json json = new Json();
		try {
    		String id = request.getParameter("id");
    		resourceService.deleteResource(Integer.parseInt(id));
			json.setSuccess(true);
			json.setMsg("删除资源信息成功");
    	} catch (Exception e) {
    		e.printStackTrace();
    		json.setSuccess(false);
    		json.setMsg("删除资源信息失败");
    		logger.error(e.getMessage());
    	}finally{
    		writeJson(json);
    	}
	}
	
	//根据资源ID获取资源信息（包括资源-权限信息）
	@RequestMapping("/getRAresult") @ResponseBody
	public Map<String, Object> getRAresult(HttpServletRequest request,HttpServletResponse response,
			Integer id){
		
		Map<String, Object> map = new HashMap<String, Object>();
		Resource rr = resourceService.findResourceAuthorityListByResourceId(id);
		map.put("resource",rr );
		map.put("AuthorityList", rr.getAuthorityList());
		
		return map;
	}
	
	
	/**
	 * 获取JSON格式的用户已授权菜单.
	 * @author lc
	 */
	@RequestMapping("/getUserMenu") 
	@ResponseBody
	public void getUserMenu(HttpServletRequest request) {
		List<Resource> meunList = SessionUtil.getUserMenu();
		writeJsonByExcludesProperties(meunList, new String[]{"parent","authorityList"});
	}



}