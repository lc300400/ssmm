package com.jjxc.modules.security.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSON;
import com.jjxc.commons.ServiceException;
import com.jjxc.commons.Tree;
import com.jjxc.modules.security.dao.ResourceDao;
import com.jjxc.modules.security.dao.RoleDao;
import com.jjxc.modules.security.entity.Authority;
import com.jjxc.modules.security.entity.Resource;
import com.jjxc.modules.security.entity.Role;
import com.jjxc.modules.security.entity.User;

@Service
public class ResourceService {

	private static Logger logger = LoggerFactory.getLogger(ResourceService.class);
	
	@Autowired ResourceDao rm;
	@Autowired 
	private RoleDao roleDao;

	/** 资源类型_菜单 */
	private static final int TYPE_MENU = 1;
	/** 资源类型_功能 */
	private static final int TYPE_FUNCTION = 2;
	
	public List<Resource> getFirstLevelMenu() {
		return rm.getFirstLevelMenu();
	}

	public Resource getResourceById(int id) {
		return rm.queryById(id);
	}

	public String getEasyUITree() {
		List<Resource> resourceList = rm.getFirstLevelMenu();
		List<Tree> treeList = new ArrayList<Tree>();

		for(Resource resource : resourceList) {
			Tree tree = new Tree();

			tree.setId(resource.getId().toString());
			tree.setText(resource.getName());
			tree.setChildren(getEasyUIChildrenTree(resource));
			tree.setPid(resource.getPid() == null ? "" : resource.getPid()+"");

			treeList.add(tree);
		}

		return JSON.toJSONString(treeList);
	}

	/**
	 * 封装指定菜单的下级EasyUI菜单树列表.
	 */
	private List<Tree> getEasyUIChildrenTree(Resource resource) {
		List<Resource> childrenResourceList = resource.getChildren();
		List<Tree> childrenTreeList = new ArrayList<Tree>();

		if(childrenResourceList != null && childrenResourceList.size() > 0) {
			for(Resource children : childrenResourceList) {
				if(children.getType() == TYPE_MENU) {
					Tree tree = new Tree();
					
					tree.setId(children.getId().toString());
					tree.setText(children.getName());
					tree.setChildren(getEasyUIChildrenTree(children));
					tree.setPid(resource.getId().toString());
					
					childrenTreeList.add(tree);
				}
			}
		}

		return childrenTreeList;
	}

	//删除资源信息
	//如果尝试删除存在下级资源的资源将抛出异常
	//@Transactional(rollbackFor={Exception.class})
	public Boolean deleteResource(int id) {
		
		String currentUsername = (String) SecurityUtils.getSubject().getPrincipal();
		Resource resource = rm.queryById(id);
		if(resource == null) {
			logger.warn("操作员{}尝试删除不存在的资源 ID={}", new Object[]{currentUsername, id});
			return false;
		}
		if(resource.getChildren() != null && resource.getChildren().size() > 0) {
			logger.warn("操作员{}尝试删除存在下级资源的资源 {}", new Object[]{currentUsername, resource.getName()});
			throw new ServiceException("不能删除存在下级资源的资源");
		}
		int deleteNum = rm.deleteResource(id);
		if(deleteNum>0){
			rm.deleteRourceAuthorityByResourceId(id);
		}
		logger.info("操作员{}删除资源 [ID={},资源名称={}]", new Object[]{currentUsername, id, resource.getName()});
		return rm.deleteResource(id)>0;
	}

	/**
	 * 保存资源信息，同时保存权限-角色关联信息（先删再插）
	 * 若资源ID存在：更新
	 * 若资源ID不存在：插入
	 */
	public Boolean saveResource(Resource resource) {
		
		if(!isNameUnique(resource)){
			throw new ServiceException(String.format("名称为%s的资源已经存在", resource.getName()));
		}
		
		// 系统为没有填写显示顺序的资源自动分配顺序号.
		if(resource.getSeq() == null) {
			Integer pid = null;
			if(resource.getPid() != null) {
				pid = resource.getPid();
			}
			String seq = rm.getMaxSeq(pid);
			if(seq == null){
				resource.setSeq(1);
			}else{
				resource.setSeq(Integer.parseInt(rm.getMaxSeq(pid)) + 1);
			}
		}
		
		int optNum = 0;
		if(resource.getId() !=null){
			optNum = rm.updateResource(resource);
			logger.info("操作员{}修改资源信息 [ID={},权限名称={}]", new Object[]{(String) SecurityUtils.getSubject().getPrincipal(), resource.getId(), resource.getName()});
		}else {
			optNum = rm.insertResource(resource);
			logger.info("操作员{}添加资源信息 [ID={},权限名称={}]", new Object[]{(String) SecurityUtils.getSubject().getPrincipal(), resource.getName()});
		}
		return optNum >0;
	}

	/**
	 * 检查权限名是否可以插入
	 * @param newName
	 * @param oldName
	 * @return boolean
	 * */
	private boolean isNameUnique(Resource resource) {
		List<Resource> rList = null;
		if(resource.getPid() == null) {
			rList = rm.getFirstLevelMenu();
		}else{
			Resource parent = rm.queryById(resource.getPid());
			rList = parent.getChildren();
		}
		if(rList == null || rList.size() == 0) {
			return true;
		}
		for(Resource tmp : rList) {
			if(tmp.getName().equals(resource.getName()) && !tmp.getId().equals(resource.getId())) {
				return false;
			}
		}
		return true;
	}
	//根据资源ID获取资源信息（包括资源-权限信息）
	public Resource findResourceAuthorityListByResourceId(Integer id) {
		// TODO Auto-generated method stub
		return rm.findResourceAuthorityListByResourceId(id);
	}

	
	/**
	 * 查询用户已授权的菜单,包含一级菜单及以下各级菜单.
	 * @param user
	 * @return
	 * @author lc
	 */
	public List<Resource> findUserMenu(final User user) {
		if(user == null) {
			return new ArrayList<Resource>();
		}
		List<Resource> firstLevelMenu = rm.getFirstLevelResource();
		filterNotMenu(firstLevelMenu);
		// 超级管理员允许访问全部菜单
		if(user.getUsername().equals("admin")) {
			return firstLevelMenu;
		}
		// 用户权限集合
		Map<Integer, Authority> userAuthMap = new HashMap<Integer, Authority>();
		for (Role role : user.getRoleList()) {
			Role roles = roleDao.findRole_AuthorityById(role.getId());
			if(roles.getAuthorityList().size()>0){
				for (Authority auth : roles.getAuthorityList()) {
					userAuthMap.put(auth.getId(), auth);
				}
			}
		}
		// 用户未授权访问任何资源.
		if(userAuthMap.isEmpty()) {
			firstLevelMenu.clear();
			return firstLevelMenu;
		}
		boolean allow = false;
		boolean supperUser = user.getUsername().equals("admin");
		ListIterator<Resource> lit = firstLevelMenu.listIterator();
		while (lit.hasNext()) {
			Resource resource = lit.next();
			allow = false;
			for (Authority auth : resource.getAuthorityList()) {
				if(userAuthMap.get(auth.getId()) != null) {
					allow = true;
					break;
				}
			}
			if (allow || supperUser) {
				filterChildrenMenu(resource.getChildren(), userAuthMap, supperUser);
			} else {
				lit.remove();
			}
		}
		return firstLevelMenu;
	}
	
	/**
	 * 过滤非菜单资源.
	 * @param resourceList 资源列表
	 * @author lc
	 */
	@SuppressWarnings("static-access")
	private void filterNotMenu(List<Resource> resourceList) {
		if(resourceList == null || resourceList.size() == 0) {
			return;
		}
		ListIterator<Resource> lit = resourceList.listIterator();
		while (lit.hasNext()) {
			Resource resource = lit.next();
			if(resource.getType() != this.TYPE_MENU) {
				lit.remove();
			}
		}
	}
	
	/**
	 * 过滤没有授权的下级菜单.
	 * @param childrenList 下级菜单列表
	 * @param userAuthMap 用户权限集合
	 * @param supperUser 是否超级管理员
	 * @author lc
	 */
	private void filterChildrenMenu(List<Resource> childrenList, final Map<Integer, Authority> userAuthMap, final boolean supperUser) {
		if(childrenList == null || childrenList.size() == 0) {
			return;
		}
		boolean allow = false;
		ListIterator<Resource> lit = childrenList.listIterator();
		while (lit.hasNext()) {
			Resource resource = lit.next();
			allow = false;
			Resource res = rm.findResourceAuthorityListByResourceId(resource.getId());
			if(res.getAuthorityList().size()>0){
				for (Authority auth : res.getAuthorityList()) {
					if(userAuthMap.get(auth.getId()) != null) {
						allow = true;
						break;
					}
				}
			}
			if (allow || supperUser) {
				filterChildrenMenu(resource.getChildren(), userAuthMap, supperUser);
			} else {
				lit.remove();
			}
		}
	}
	
}
