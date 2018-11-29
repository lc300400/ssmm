package com.jjxc.modules.security.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jjxc.commons.Page;
import com.jjxc.modules.security.entity.Role;

public interface RoleDao {
	
	//根据ID查询角色
	Role queryById(Integer id);  
	
    //查询所有角色列表
    List<Role> queryAll();
    
    //插入角色
    void insertRole(Role role);
    
    //更新角色
    void updateById(Role role);
	
    //分页查询角色
	List<Role> findPage(Page<Role> page);

	//跟姐名字查询角色
	List<Role> queryByName(@Param("name") String name);

	//根据ID删除角色
	int deleteRoleById(int id);

	//根据角色ID插入角色-权限关系信息
	void saveRole_Authority(@Param("role_id") Integer role_id,
                            @Param("authorityList") List<Integer> authorityList);
	
	//根据角色ID删除角色-权限信息
	void deleteRoleAuthorityByRoleId(Integer roleid);
	
	//根据角色ID查询角色信息（包括权限信息：authorityLis）
	Role findRole_AuthorityById(@Param("id") Integer id);
}
