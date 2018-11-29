package com.jjxc.commons;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;

import com.jjxc.modules.security.entity.Resource;

/**
 * 获取系统session存储相关信息
 * @author lc
 *
 */
public class SessionUtil {

	//获取当前用户登陆账户
	public static String getUsername(){
		return (String) SecurityUtils.getSubject().getPrincipal();
	}
	
	//获取登陆用户已授权的资源信息
	@SuppressWarnings("unchecked")
	public static List<Resource> getUserMenu(){
		List<Resource> resL =  new ArrayList<Resource>();
		resL = (List<Resource>) SecurityUtils.getSubject().getSession().getAttribute("resourceList");
		return resL;
	}
	
}
