package com.jjxc.modules.security.dao;
  
import com.jjxc.commons.Page;
import com.jjxc.modules.security.entity.Parameter;

import java.util.List;
  
  
public interface ParameterDao {  
      
	//根据ID查询参数信息
    Parameter queryById(Integer id);
    
    //根据name查询参数信息
    Parameter queryByName(String name);
    
    //保存参数信息
    void insertParameter(Parameter parameter);
    
    //修改参数信息
    void updateById(Parameter parameter);
    
    //分页查询
    List<Parameter> findPage(Page page);
      
} 