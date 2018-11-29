package com.jjxc.commons;

import java.util.Collections;
import java.util.List;

/**
 * DataTables响应数据对象.
 * @author lc
 */
public class DataTablesResponse {

	private int draw; //用来确保Ajax请求从服务器返回的是对应的,Datatables发送的draw是多少那么服务器就返回多少.
	private long recordsTotal = 0; //查询结果的记录总数
	private long recordsFiltered = 0; //过滤后的记录数
	private List data = Collections.emptyList();
	private String error; //错误提示信息

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public long getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public long getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(long recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
