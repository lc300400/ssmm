package com.jjxc.modules.security.entity;

import com.jjxc.commons.ReflectionUtils;

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户
 * @author lc
 */
public class User  {

	private Integer id;       // 主键ID
	private String username;  // 用户账号
	private String password;  // 用户密码
	private String name;      // 用户姓名
	private String mobile;    // 手机号码
	private String email;     // 电子邮箱
	private String enable;    // 用户状态(0--禁用、1--启用)
	private Dept dept;        // 用户部门

	private List<Role> roleList = new ArrayList<Role>();

    public User() {}

	public User(Integer id) {
		this.id = id;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	/**
	 * 用户拥有的角色名称字符串, 多个角色名称用','分隔.
	 */
	// 非持久化属性.
	@Transient
	public String getRoleNames() {
		return ReflectionUtils.convertElementPropertyToString(roleList, "name", ", ");
	}

	@Transient
	public List<Integer> getRoleIds() {
		return ReflectionUtils.convertElementPropertyToList(roleList, "id");
	}


}
