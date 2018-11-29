package com.jjxc.modules.security.entity;

import java.io.Serializable;
import java.util.List;

public class Role implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String description;
	
	private List<Authority> authorityList;
	
	public Role() {
		super();
	}

	public Role(Integer id, String name, String description) {
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


	public List<Authority> getAuthorityList() {
		return authorityList;
	}

	public void setAuthorityList(List<Authority> authorityList) {
		this.authorityList = authorityList;
	}

	@Override
	public String toString() {
		return "Authority [id=" + id + ", name=" + name + ", description="
				+ description + ", authorityList=" + authorityList + "]";
	}
	
	
	
}
