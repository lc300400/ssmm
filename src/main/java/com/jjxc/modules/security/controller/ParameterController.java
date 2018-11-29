package com.jjxc.modules.security.controller;
  
import com.jjxc.commons.DataTablesResponse;
import com.jjxc.commons.Json;
import com.jjxc.commons.Page;
import com.jjxc.commons.PageFrom;
import com.jjxc.modules.redis.service.RedisCache;
import com.jjxc.modules.security.entity.Parameter;
import com.jjxc.modules.security.service.ParameterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
  
@Controller  
@RequestMapping("/security/parameter")  
public class ParameterController extends PageFrom<Parameter>{  
	
    @Resource  
    private ParameterService paraService;
    @Resource
	private RedisCache redisCache;
      
    @RequestMapping("/getInfo")  
    public String getParameter(HttpServletRequest request,Model model){  
        int parameterId = Integer.parseInt(request.getParameter("id"));  
        Parameter parameter = paraService.queryById(parameterId);  
        model.addAttribute("parameter", parameter);
        return " ";  
    }  
    
    
    @RequestMapping("/list.json")  
    @ResponseBody
    public void list(HttpServletRequest request){

    	//reids 测试
//		redisCache.SET("lc","李成123");
//		String str = redisCache.GET("lc");
//		System.out.println(str+"~~~~~~~~~~");

		String name = request.getParameter("name");
    	Page<Parameter> page = getPageFromDataTables(request);
    	//设置排序
		page.setOrderBy("id");
		page.setOrder(Page.DESC);
		//设置条件过滤
		Map<String ,Object> map = new HashMap<>();
		if(!"".equals(name) && name !=null){
			map.put("name", name);
			page.setParams(map);
		}
	    List<Parameter> parameter = paraService.findPage(page);
	    page.setResult(parameter);
	    DataTablesResponse response = converDataTablesResponse(request,page);
//	    DataGrid dataGrid = converEasyUIResponse(page);
		writeJson(response);
    }  
    
    @RequestMapping("/input.json")  
    public String toInput(HttpServletRequest request,Model model){  
        int parameterId = Integer.parseInt(request.getParameter("id"));  
        Parameter parameter = paraService.queryById(parameterId);  
        model.addAttribute("parameter", parameter);
        return "/security/parameter-input";
    } 
    
    @RequestMapping("/save.form")  
    @ResponseBody
    public void save(HttpServletRequest request){
    	Json json = new Json();
    	try {
    		String id = request.getParameter("id");
    		String name = request.getParameter("name");
    		String value = request.getParameter("value");
    		String remark = request.getParameter("remark");
    		if("".equals(id) || id==null){
    			Parameter parameter = new Parameter();
    			parameter.setName(name);
    			parameter.setValue(value);
    			parameter.setRemark(remark);
    			paraService.insertParameter(parameter);
    		}else{
    			Parameter parameter = paraService.queryById(Integer.parseInt(id)); 
    			parameter.setValue(value);
    			parameter.setRemark(remark);
    			paraService.updateById(parameter);
    		}
    		json.setMsg("数据保存成功");
    		json.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			writeJson(json);
		}
    }  
} 