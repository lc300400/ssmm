package com.jjxc.modules.security.dao;

import com.jjxc.commons.Page;
import com.jjxc.modules.security.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
      
	//根据ID查询用户信息
    User queryById(Integer id);
    
    //根据登陆账户查询用户信息
    User queryByUsername(String username);
    
    //根据部门编号查询用户信息
    List<User> queryByDeptId(Integer deptId);
      
    //保存用户信息
    void insertUser(User user);
      
    //更新用户信息
    void updateUser(User user);
    
    //分页查询用户信息
    List<User> findPage(Page page);
	
	//保存用户角色表信息
    void saveUserRole(@Param("userId") Integer userId,
                             @Param("roleList") List<Integer> roleList);
	
	//删除用户角色表信息
    void deleteUserRoleByUserId(Integer userId);
  
      
} 