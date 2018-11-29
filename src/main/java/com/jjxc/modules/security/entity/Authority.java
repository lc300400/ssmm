package com.jjxc.modules.security.entity;

import java.io.Serializable;
import java.util.List;

public class Authority implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String description;
	
	private List<Resource> resourceList;
	
	private List<Role> roleList;
	
	public Authority() {
		super();
	}

	public Authority(Integer id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Resource> getResourceList() {
		return resourceList;
	}

	public void setResourceList(List<Resource> resourceList) {
		this.resourceList = resourceList;
	}

	
	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	@Override
	public String toString() {
		return "Authority [id=" + id + ", name=" + name + ", description="
				+ description + ", resourceList=" + resourceList + 
				", roleList=" + roleList + "]";
	}
	
	
	
}
