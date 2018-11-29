package com.jjxc.commons;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jjxc.utils.Struts2Utils;

/**
 * 组装分页模板
 * @author lc
 */
public abstract class PageFrom<T> {

	protected Logger logger = LoggerFactory.getLogger(PageFrom.class);

	/**
	 * DataTables分页查询参数封装成Page分页对象.
	 */
	protected Page<T> getPageFromDataTables(HttpServletRequest request) {
		
		String start = request.getParameter("start"); //第一条记录在结果集中的位置（序号从0开始）
		String length = request.getParameter("length"); //每页显示记录条数

		int first = 0; //默认第一页
		if (StringUtils.hasText(start)) {
			first = Integer.parseInt(start);
		}

		int pageSize = 10; //默认每页10条记录
		if (StringUtils.hasText(length)) {
			pageSize = Integer.parseInt(length);
		}

		Page<T> page = new Page<T>(first, pageSize);

		//设置默认排序方式,默认按ID降序排列
		page.setOrderBy("id");
		page.setOrder(Page.DESC);

		return page;
	}
	
	/**
	 * Page分页对象转化成DataTables响应数据对象.
	 * @param page 分页对象
	 */
	protected DataTablesResponse converDataTablesResponse(HttpServletRequest request,Page<T> page) {
		int draw = Integer.parseInt(request.getParameter("draw"));

		DataTablesResponse response = new DataTablesResponse();
		response.setDraw(draw);
		response.setRecordsTotal(page.getTotalCount());
		response.setRecordsFiltered(page.getTotalCount());
		response.setData(page.getResult());

		return response;
	}
	
	
	/**
	 * Page分页对象转化成 EasyUI Datagrid 响应数据对象.
	 * @param page 分页对象
	 */
	protected DataGrid converEasyUIResponse(Page<T> page) {
		DataGrid dataGrid = new DataGrid();
		dataGrid.setTotal(page.getTotalCount());
		dataGrid.setRows(page.getResult());
		return dataGrid;
	}

	/**
	 * EasyUI Datagrid 分页查询参数封装成Page分页对象.
	 */
	protected Page<T> getPageFromEasyUI(HttpServletRequest request) {

		String _page = request.getParameter("page"); //当前页码
		String rows = request.getParameter("rows"); //每页显示记录条数

		int pageNo = 1; //默认第一页
		if (StringUtils.hasText(_page)) {
			pageNo = Integer.parseInt(_page);
		}

		int pageSize = 10; //默认每页10条记录
		if (StringUtils.hasText(rows)) {
			pageSize = Integer.parseInt(rows);
		}

		Page<T> page = new Page<T>(pageSize);

		page.setPageNo(pageNo);

		//设置默认排序方式,默认按ID降序排列
		page.setOrderBy("id");
		page.setOrder(Page.DESC);

		return page;
	}
	
	/**
	 * 将对象转换成JSON对象,并响应回前台.
	 * @param object 需要转换的对象
	 */
	protected void writeJson(Object object) {
		writeJsonByFilter(object, null, null);
	}
	
	/**
	 * 将对象转换成JSON对象,并响应回前台.
	 * @param object 需要转换的对象
	 * @param excludesProperties 需要排除的属性
	 */
	protected void writeJsonByExcludesProperties(Object object, String...excludesProperties) {
		writeJsonByFilter(object, null, excludesProperties);
	}
	
	/**
	 * 将对象转换成JSON对象,并响应回前台.
	 * @param object 需要转换的对象
	 * @param includesProperties 需要包含的属性
	 * @param excludesProperties 需要排除的属性 
	 */
	protected void writeJsonByFilter(Object object, String[] includesProperties, String...excludesProperties) {
		FastjsonFilter filter = new FastjsonFilter();// excludes优先于includes
		if (excludesProperties != null && excludesProperties.length > 0) {
			filter.getExcludes().addAll(Arrays.<String> asList(excludesProperties));
			if (logger.isDebugEnabled()) {
				logger.debug("对象转JSON：需要排除的属性[{}].", StringUtils.arrayToCommaDelimitedString(excludesProperties));
			}
		}
		if (includesProperties != null && includesProperties.length > 0) {
			filter.getIncludes().addAll(Arrays.<String> asList(includesProperties));
			if (logger.isDebugEnabled()) {
				logger.debug("对象转JSON：需要包含的属性[{}].", StringUtils.arrayToCommaDelimitedString(includesProperties));
			}
		}

		String json;
		String User_Agent = Struts2Utils.getRequest().getHeader("User-Agent");
		if (org.apache.commons.lang3.StringUtils.indexOfIgnoreCase(User_Agent, "MSIE 6") > -1) {
			// 使用SerializerFeature.BrowserCompatible特性会把所有的中文都会序列化为\\uXXXX这种格式，字节数会多一些，但是能兼容IE6
			json = JSON.toJSONString(object, filter, SerializerFeature.WriteDateUseDateFormat, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.BrowserCompatible);
		} else {
			// 使用SerializerFeature.WriteDateUseDateFormat特性来序列化日期格式的类型为yyyy-MM-dd hh24:mi:ss
			// 使用SerializerFeature.DisableCircularReferenceDetect特性关闭引用检测和生成
			json = JSON.toJSONString(object, filter, SerializerFeature.WriteDateUseDateFormat, SerializerFeature.DisableCircularReferenceDetect);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("转换后的JSON字符串：{}", json);
		}
		Struts2Utils.renderJson(json);
	}

}
