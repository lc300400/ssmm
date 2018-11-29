package com.jjxc.modules.security.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 资源.
 * 
 * @author gwh
 */
public class Resource implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id;         // 资源ID
	private Integer type;       // 资源类型(1--菜单、2--功能)
	private String name;        // 资源名称
	private String url;         // 资源路径
	private String menuId;      // 资源标识
	private String icon;        // 资源图标
	private String description; // 资源描述
	private Integer seq;        // 显示顺序
	private Integer pid;    // 上级资源
	
	private List<Authority> authorityList;
	
	private List<Resource> children = new ArrayList<Resource>(); // 下级机构列表
	
	public Resource() {}

	public Resource(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public List<Resource> getChildren() {
		return children;
	}

	public void setChildren(List<Resource> children) {
		this.children = children;
	}

	public List<Authority> getAuthorityList() {
		return authorityList;
	}

	public void setAuthorityList(List<Authority> authorityList) {
		this.authorityList = authorityList;
	}

	@Override
	public String toString() {
		return "Resource [id=" + id + ", type=" + type + ", name=" + name
				+ ", url=" + url + ", menuId=" + menuId + ", icon=" + icon
				+ ", description=" + description + ", seq=" + seq + ", pid="
				+ pid + ", authorityList=" + authorityList + ", children="
				+ children + "]";
	}
	
}
