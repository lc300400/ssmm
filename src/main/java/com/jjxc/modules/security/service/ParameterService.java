package com.jjxc.modules.security.service;
  
import com.jjxc.commons.Page;
import com.jjxc.modules.security.dao.ParameterDao;
import com.jjxc.modules.security.entity.Parameter;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class ParameterService{  
    
	private static Logger logger = LoggerFactory.getLogger(DeptService.class);
	
	@Resource  
    private ParameterDao parameterDao;

	/**
	 * 根据Id查询参数信息
	 * @param id 主键ID
	 * @return Parameter
	 */
	public Parameter queryById(int id) {
		return parameterDao.queryById(id);
	}
	
	/**
	 * 根据参数名称查询参数信息
	 * @param name 参数名称
	 * @return Parameter
	 */
	public Parameter queryByName(String name) {
		return parameterDao.queryByName(name);
	}

	/**
	 * 新增参数信息
	 */
	public void insertParameter(Parameter parameter) {
		String currentUsername = (String) SecurityUtils.getSubject().getPrincipal();
		parameterDao.insertParameter(parameter);
		logger.info("操作员{}保存参数信息 [ID={},参数名称={},参数值={}]",
				currentUsername, parameter.getId(), parameter.getName(), parameter.getValue());
	}

	/**
	 * 修改参数信息
	 */
	public void updateById(Parameter parameter) {
		String currentUsername = (String) SecurityUtils.getSubject().getPrincipal();
		parameterDao.updateById(parameter);
		logger.info("操作员{}修改参数信息 [ID={},参数名称={},参数值={}]",
				currentUsername, parameter.getId(), parameter.getName(), parameter.getValue());
	}

	/**
	 * 分页查询参数信息
	 */
	public List<Parameter> findPage(Page page) {
		return parameterDao.findPage(page);
	}
} 