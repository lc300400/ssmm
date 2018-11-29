package com.jjxc.shiro;

import com.jjxc.modules.security.entity.Resource;
import com.jjxc.modules.security.entity.Role;
import com.jjxc.modules.security.entity.User;
import com.jjxc.modules.security.service.ResourceService;
import com.jjxc.modules.security.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyShiroRealm extends AuthorizingRealm{
	
    /**注解引入业务类**/
    @Autowired 
    private UserService userService;  
    @Autowired 
    private ResourceService resourceService;  

	/* 
     * 登录验证
     */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		
		String username = (String)token.getPrincipal();                //得到用户名 
        String password = new String((char[])token.getCredentials());  //得到密码
        User user = userService.getUserByUsername(username);
        /**检测是否有此用户 **/
        if(user == null){
            throw new UnknownAccountException();//没有找到账号异常
        }
        /**检验账号是否被锁定 **/
        if("0".equals(user.getEnable())){
            throw new LockedAccountException();//抛出账号锁定异常
        }
        /**AuthenticatingRealm使用CredentialsMatcher进行密码匹配**/
        if(null != username && null != password){
        	setSession("currUser", user);
        	List<Resource> reL = resourceService.findUserMenu(user);
        	setSession("resourceList", reL);
            return new SimpleAuthenticationInfo(username, user.getPassword(), getName());
        }else{
            return null;
        }
	}
	
	/* 
     * 授权
     */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//获取当前登录的用户名,等价于(String)principals.fromRealm(this.getName()).iterator().next()
        String currentUser = (String) super.getAvailablePrincipal(principals);
		Set<String> roleNames = new HashSet<>();
        User user = userService.getUserByUsername(currentUser);
        for(Role role:user.getRoleList()){
        	roleNames.add(role.getName());
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        info.addRoles(roleNames);
        return info; 
	}
	
	/**
     * 将一些数据放到ShiroSession中,以便于其它地方使用
     * 比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到
     */
	private void setSession(Object key, Object value){
        Subject currentUser = SecurityUtils.getSubject();
        if(null != currentUser){
            Session session = currentUser.getSession();
            if(null != session){
                session.setAttribute(key, value);
            }
        }
    }
}
