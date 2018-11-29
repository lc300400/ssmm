package com.jjxc.modules.security.service;

import com.jjxc.commons.Page;
import com.jjxc.modules.security.dao.AuthorityDao;
import com.jjxc.modules.security.entity.Authority;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AuthorityService {
    private static Logger logger = LoggerFactory.getLogger(AuthorityService.class);

    @Autowired
    AuthorityDao authorityDao;

    /**
     * 根据ID查询权限信息，包含此权限下的所有资源
     */
    public Authority findAuthority_ResourceById(int id) {
        return authorityDao.findAuthority_ResourceById(id);
    }

    /**
     * 查询所有权限列表
     */
    public List<Authority> queryAll() {
        return authorityDao.queryAll();
    }

    /**
     * 分页查询权限列表
     */
    public List<Authority> findPage(Page<Authority> page) {

        return authorityDao.findPage(page);
    }

    /**
     * 保存权限信息，同时保存权限-角色关联信息（先删再插）
     * 若权限ID存在：更新
     * 若权限ID不存在：插入
     */
    public void saveAuthority(Authority authority, String rListStr) {

        List<Integer> resourceList = str2li(rListStr);
        if (authority.getId() != null) {
            insertAuthority(authority, resourceList);
        } else {
            editAuthority(authority, resourceList);
        }
    }

    /**
     * 插入权限信息，同时插入权限-角色关联信息
     */
    //@Transactional(rollbackFor={Exception.class})
    public void insertAuthority(Authority authority, List<Integer> resourceList) {
        try {
            authorityDao.updateById(authority);
            authorityDao.deleteAuthorityResourceByAuthorityId(authority.getId());
            if (resourceList != null && resourceList.size() > 0) {
                authorityDao.saveAuthority_Resource(authority.getId(), resourceList);
            }
            logger.info("操作员{}修改权限信息 [ID={},权限名称={}]",
                    new Object[]{(String) SecurityUtils.getSubject().getPrincipal(),
                            authority.getId(), authority.getName()});
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("操作员{}修改权限信息[失败] [ID={},权限名称={},错误信息={}]",
                    new Object[]{(String) SecurityUtils.getSubject().getPrincipal(),
                            authority.getId(), authority.getName(), e.getMessage()});
        }
    }

    /**
     * 更新权限信息，同时更新权限-角色关联信息
     */
    //@Transactional(rollbackFor={Exception.class})
    public void editAuthority(Authority authority, List<Integer> resourceList) {
        try {
            authorityDao.insertAuthority(authority);
            if (resourceList != null && resourceList.size() > 0) {
                authorityDao.saveAuthority_Resource(authority.getId(), resourceList);
            }
            logger.info("操作员{}添加权限信息 [ID={},权限名称={}]",
                    SecurityUtils.getSubject().getPrincipal(),
                            authority.getId(), authority.getName());
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("操作员{}添加权限信息 [失败] [权限名称={},错误信息={}]",
                     SecurityUtils.getSubject().getPrincipal(),
                            authority.getName(), e.getMessage());
        }
    }


    /**
     * 删除权限信息，同时删除权限-角色关联信息
     */
    //@Transactional(rollbackFor={Exception.class})
    public void deleteAuthorityById(int id) {
        String currentUsername = (String) SecurityUtils.getSubject().getPrincipal();
        try {
            authorityDao.deleteAuthorityById(id);
            authorityDao.deleteAuthorityResourceByAuthorityId(id);
            logger.info("操作员{}删除权限 [ID={}]", new Object[]{currentUsername, id});
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("操作员{}删除权限 [失败] [ID={},错误信息={}]",
                    new Object[]{currentUsername, e.getMessage()});
        }
    }

    /**
     * 检查权限名是否可以插入
     *
     * @param newName
     * @param oldName
     * @return boolean
     */
    public boolean isNameUnique(String newName, String oldName) {

        //若两次值相同，不做查询，直接返true
        if (newName != null && newName.equals(oldName)) {
            return true;
        }
        //若新值为空，直接判否
        if (newName == null || newName.equals("")) {
            return false;
        }

        return isNameUnique(newName);

    }

    /**
     * 检查权限名是否存在
     *
     * @param authorityName
     */
    private boolean isNameUnique(String authorityName) {
        List<Authority> extAuthorityList = authorityDao.queryByName(authorityName);
        return (extAuthorityList == null || (extAuthorityList.size() <= 0));
    }


    //资源id字符串转资源ID集合
    private List<Integer> str2li(String rListStr) {
        if (rListStr == null || rListStr.equals("")) {
            return null;
        }
        String[] rl_arr = rListStr.split(",");
        List<String> asList = Arrays.asList(rl_arr);

        List<Integer> list = new ArrayList<>();
        for (String str : asList) {
            list.add(Integer.parseInt(str));
        }

        return list;
    }

}
