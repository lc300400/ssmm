package com.jjxc.commons;

/**
 * 
 * JSON模型
 * 用户后台向前台返回的JSON对象
 * 
 */
public class Json {
	
	/**layer弹出层的图标*/
	/**正确*/
	public static final Integer ICON_RIGHT = 1;
	/**错误*/
	public static final Integer ICON_WRONG = 2;
	/**黄色的问号*/
	public static final Integer ICON_QUESTION = 3;
	/**锁*/
	public static final Integer ICON_LOCK = 4;
	/**伤心*/
	public static final Integer ICON_SAD = 5;
	/**笑脸*/
	public static final Integer ICON_SMILE = 6;
	/**警告*/
	public static final Integer ICON_WARNING = 7;
	
	
	private boolean success = false;
	private String msg = "";
	private Object obj = null;

	public Json() {}

	public Json(boolean success, String msg, Object obj) {
		this.success = success;
		this.msg = msg;
		this.obj = obj;
	}

	public boolean isSuccess() {
		return success;
	}

	public Json setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public Json setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public Object getObj() {
		return obj;
	}

	public Json setObj(Object obj) {
		this.obj = obj;
		return this;
	}

}
