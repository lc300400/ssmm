package com.jjxc.modules.security.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门
 * @author lc
 */
public class Dept{


	private Integer id;
	private String name;        // 部门名称
	private String number;      // 部门编码
	private String description; // 部门描述
	private String linkman;     // 联系人
	private String tel;         // 联系电话
	private Integer type;       // 部门类别(1--公司、2--分公司、3--项目部、4--部门)
	private Integer seq;        // 显示顺序
	private Dept parent;        // 上级部门

	private List<Dept> children = new ArrayList<Dept>(); // 下级部门列表

	public Dept() {}

	public Dept(Integer id) {
		this.id = id;
	}
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Dept getParent() {
		return parent;
	}

	public void setParent(Dept parent) {
		this.parent = parent;
	}

	public List<Dept> getChildren() {
		return children;
	}

	public void setChildren(List<Dept> children) {
		this.children = children;
	}
}
