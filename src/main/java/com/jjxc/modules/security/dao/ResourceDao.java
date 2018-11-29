package com.jjxc.modules.security.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jjxc.modules.security.entity.Resource;

public interface ResourceDao {
	
	//根据ID查询资源信息
	Resource queryById(@Param("id") int id);
    
	//根据ID查询所有资源
    List<Resource> queryAllResources();
    
    //根据ID插入资源信息
    int insertResource(@Param("resource") Resource resource);
    
    //根据ID删除资源信息
    int deleteResource(@Param("id") Integer id);
    
    //根据ID更新资源信息
    int updateById(@Param("resource") Resource resource);

    /**
	 * 查询没有下级的一级资源--未资源的权限信息
	 */
	List<Resource> getFirstLevelMenu();

	/**
	 * 更新资源信息
	 */
	int updateResource(@Param("resource") Resource resource);

	/**
	 * 根据资源ID查询此资源下最大排序序号
	 */
	String getMaxSeq(@Param("id") Integer pid);

	/**
	 * 根据资源ID删除资源-权限信息
	 */
	void deleteRourceAuthorityByResourceId(@Param("id") int id);
	
	/**
	 * 根据资源ID查询资源--同时关联资源的权限信息
	 */
	Resource findResourceAuthorityListByResourceId(@Param("id") int id);
    
	/**
	 * 查询没有下级的一级资源--同时关联资源的权限信息
	 * @return
	 * @author lc
	 */
	List<Resource> getFirstLevelResource();
}
