package com.jjxc.modules.security.controller;

import com.jjxc.commons.*;
import com.jjxc.modules.security.entity.Authority;
import com.jjxc.modules.security.entity.Role;
import com.jjxc.modules.security.service.AuthorityService;
import com.jjxc.modules.security.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/security/role")
public class RoleController extends PageFrom<Role> {

    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthorityService authorityService;


    //获取角色列表
    @RequestMapping("/list")
    @ResponseBody
    public void getRoleList(HttpServletRequest request) {
        String name = request.getParameter("name");
        Page<Role> page = getPageFromDataTables(request);
        //设置排序
        page.setOrderBy("id");
        //设置条件过滤
        Map<String, Object> map = new HashMap<>();
        if (!"".equals(name) && name != null) {
            map.put("name", name);
            page.setParams(map);
        }
        List<Role> ausList = roleService.findPage(page);
        page.setResult(ausList);
        DataTablesResponse response = converDataTablesResponse(request, page);
        writeJson(response);
    }

    //根据ID获取角色数据，获取角色保存页面初始数据，跳转页面
    @RequestMapping("/savePage")
    public String toInput(HttpServletRequest request, Map<String, Object> model) {
        String roleId = request.getParameter("id");
        Role role = null;
        List<Integer> checkedAuthorityIds = new ArrayList<>();
        if (roleId != null && !roleId.equals("")) {
            role = roleService.findRole_AuthorityById(Integer.parseInt(roleId));
            List<Authority> authorityList = role.getAuthorityList();
            for (Authority authority : authorityList) {
                checkedAuthorityIds.add(authority.getId());
            }
        }
        model.put("checkedAuthorityIds", checkedAuthorityIds);
        model.put("authorityList", authorityService.queryAll());
        model.put("role", role);
        return "/security/role-input";
    }

    //保存角色相关信息
    @RequestMapping("/save")
    @ResponseBody
    public void save(HttpServletRequest request, Role role) {
        Json json = new Json();
        try {
            String rListStr = request.getParameter("authChecked");
            roleService.saveRole(role, rListStr);
            json.setSuccess(true);
            json.setMsg("权限信息保存成功");
        } catch (ServiceException e) {
            json.setSuccess(false);
            json.setMsg(e.getMessage());
            logger.error(e.getMessage());
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg("权限信息保存失败");
            logger.error(e.getMessage());
        } finally {
            writeJson(json);
        }
    }

    //删除权限相关信息
    @RequestMapping("/delete")
    @ResponseBody
    public void deleteAuthority(HttpServletRequest request) {

        Json json = new Json();
        try {
            String id = request.getParameter("id");
            roleService.deleteRoleById(Integer.parseInt(id));
            json.setSuccess(true);
            json.setMsg("删除权限信息成功");
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            json.setMsg("删除权限信息失败");
            logger.error(e.getMessage());
        } finally {
            writeJson(json);
        }

    }


    /**
     * AJAX异步检查权限名称是否唯一.
     */
    @RequestMapping("/checkNameUnique.action")
    @ResponseBody
    public Boolean checkNameUnique(HttpServletRequest request) {
        String newName = request.getParameter("name");
        String oldName = request.getParameter("oldName");
        return roleService.isNameUnique(newName, oldName);
    }

}
