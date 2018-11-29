package com.jjxc.modules.security.controller;
  
import com.jjxc.commons.Json;
import com.jjxc.commons.PageFrom;
import com.jjxc.commons.ServiceException;
import com.jjxc.modules.security.entity.Dept;
import com.jjxc.modules.security.service.DeptService;
import com.jjxc.utils.Struts2Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
  
@Controller  
@RequestMapping("/security/dept")  
public class DeptController extends PageFrom<Dept>{  
	
    @Autowired
    private DeptService deptService;  
      
    @RequestMapping("/getInfo")  
    public String getDept(HttpServletRequest request,Model model){  
        int parameterId = Integer.parseInt(request.getParameter("id"));  
        Dept dept = deptService.getById(parameterId);  
        model.addAttribute("dept", dept);
        return " ";  
    }  
    
    //跳转到新增、修改页面
    @RequestMapping("/input.json")  
    public String toInput(HttpServletRequest request,Model model){
    	String id = request.getParameter("id");
    	if(!"".equals(id) && id !=null){
    		Dept dept = deptService.getById(Integer.parseInt(id));  
    		model.addAttribute("entity", dept);
    	}
    	return "/security/dept-input";  
    }  
    
    @RequestMapping("/save.do")  
    @ResponseBody
    public void save(HttpServletRequest request,Dept dept){
    	Json json = new Json();
    	try {
    		String id = request.getParameter("id");
    		if("".equals(id) || id==null){
    			String ppid = request.getParameter("pid");
    			dept.setParent(new Dept(Integer.parseInt(ppid)));
    			deptService.save(dept);
    		}else{
    			deptService.save(dept);
    		}
    		json.setSuccess(true);
    		json.setMsg("部门信息保存成功");
		}catch (ServiceException e) {
			json.setSuccess(false);
			json.setMsg(e.getMessage());
			logger.error(e.getMessage());
		}catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("部门信息保存失败");
			logger.error(e.getMessage());
		}finally{
			writeJson(json);
		}
    }
    
    //删除部门信息
    @RequestMapping("/delete.json")  
    @ResponseBody
    public void delete(HttpServletRequest request,Model model){  
    	Json json = new Json();
    	try {
    		String id = request.getParameter("id");
    		deptService.delete(Integer.parseInt(id));
			json.setSuccess(true);
			json.setMsg("删除部门成功");
    	}catch (ServiceException e) {
			json.setSuccess(false);
			json.setMsg(e.getMessage());
			logger.error(e.getMessage());
		} catch (Exception e) {
    		json.setSuccess(false);
    		json.setMsg("删除部门失败");
    		logger.error(e.getMessage());
    	}finally{
    		writeJson(json);
    	}
    }
    
    @RequestMapping("/getTreeGridData.json")  
    @ResponseBody
    public void getTreeGridData(HttpServletRequest request,Model model){
    	List<Dept> resultList = deptService.findFirstLevel();
		writeJsonByExcludesProperties(resultList, "parent");
    }
    
    /**
	 * 获取JSON格式的EasyUI部门树.
	 */
    @RequestMapping("/getEasyUITree.json")  
    @ResponseBody
	public void getEasyUITree() {
		String tree = deptService.getEasyUITree();
		Struts2Utils.renderJson(tree);
		if(logger.isDebugEnabled()){
			logger.debug("返回的前端的部门树：{}", tree);
		}
	}

}