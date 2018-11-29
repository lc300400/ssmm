package com.jjxc.modules.security.dao;
  
import com.jjxc.modules.security.entity.Dept;
import org.apache.ibatis.annotations.Param;

import java.util.List;
  
  
public interface DeptDao {  
      
	//根据ID查询部门信息
    Dept queryById(Integer id);
    
    //保存部门信息
    void insertDept(Dept dept);
    
    //查询没有上级部门的一级部门
    List<Dept> findFirstLevel();
    
    //查询同级部门的最大部门编码
    String getMaxNumber(@Param("id") Integer id);
    
    //查询同级部门的最大显示顺序
    String getMaxSeq(@Param("id") Integer id);
    
    //修改部门新
    void updateDept(Dept dept);
    
    //删除部门信息
    void deleteDept(Integer id);
} 