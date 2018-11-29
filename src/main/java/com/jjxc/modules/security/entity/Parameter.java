package com.jjxc.modules.security.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * 系统参数信息.
 * 
 * @author lc
 */
public class Parameter{

	/** 参数类型_不可修改 */
	//public static final int TYPE_CANNOT = 0;
	/** 参数类型_允许修改 */
	//public static final int TYPE_ALLOW = 1;

	/** 用户默认密码的KEY值 */
	public static final String KEY_DEFAULT_PASSWORD = "default_password";

	private Integer id;    // 主键编号
	private Integer type;  // 参数类型(0--不可修改、1--允许修改)
	private String name;   // 参数名称
	private String value;  // 参数值
	private String remark; // 参数说明
	
	
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
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
