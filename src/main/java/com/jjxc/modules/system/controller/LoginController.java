package com.jjxc.modules.system.controller;
  
import com.jjxc.utils.DecriptUtil;
import com.jjxc.utils.JsonUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

  
@Controller  
@RequestMapping("/login")  
public class LoginController { 

    /** 
     * 验证用户名和密码 
     * @return
     */  
    @RequestMapping(value="/checkLogin.json",method=RequestMethod.POST)  
    @ResponseBody  
    public String checkLogin(String username,String password) {  
        Map<String, Object> result = new HashMap<String, Object>();
        try{
            UsernamePasswordToken token = new UsernamePasswordToken(username, DecriptUtil.MD5(password));  
            Subject currentUser = SecurityUtils.getSubject();  
            if (!currentUser.isAuthenticated()){
                //使用shiro来验证  
//                token.setRememberMe(true);  
                currentUser.login(token);
                result.put("success", true);
            } 
        }catch(AuthenticationException e){
           result.put("success", false);
        }
        return JsonUtils.obj2String(result);  
    }  

    /** 
     * 退出登录
     */  
    @RequestMapping(value="/logout.json",method=RequestMethod.POST)    
    @ResponseBody    
    public String logout() {   
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        Subject currentUser = SecurityUtils.getSubject();       
        currentUser.logout();    
        return JsonUtils.obj2String(result);  
    } 
    
}