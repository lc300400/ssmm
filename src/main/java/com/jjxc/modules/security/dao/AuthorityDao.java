package com.jjxc.modules.security.dao;

import com.jjxc.commons.Page;
import com.jjxc.modules.security.entity.Authority;
import org.apache.ibatis.annotations.Param;
import java.util.List;



public interface AuthorityDao {
	
	//根据id查询权限
	Authority queryById(Integer id);  
    
	//查询所有权限列表
    List<Authority> queryAll();
    
    //插入权限
    void insertAuthority(Authority authority);
    
    //修改权限
    void updateById(Authority authority);
	
    //分页查询权限列表
	List<Authority> findPage(Page<Authority> page);
	
	//根据名字查询权限
	List<Authority> queryByName(@Param("name") String name);
	
	//根据ID删除权限
	int deleteAuthorityById(int id);

	//根据权限ID插入资源-权限关系数据
	void saveAuthority_Resource(@Param("authority_id") Integer authority_id,
                                @Param("resourceList") List<Integer> resourceList);
	
	//根据权限ID删除权限-资源关系
	void deleteAuthorityResourceByAuthorityId(Integer authorityid);
	
	//根据权限ID查询权限-资源信息  resourceList
	Authority findAuthority_ResourceById(@Param("id") Integer id);
	
	//根据权限ID查询权限-角色信息  roleList
	Authority findAuthority_RoleById(@Param("id") Integer id);
}
