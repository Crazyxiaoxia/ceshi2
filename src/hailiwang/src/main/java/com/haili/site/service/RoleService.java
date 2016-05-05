package com.haili.site.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.haili.site.common.BaseService;
import com.haili.site.model.Role;
import com.haili.site.model.User;
import com.haili.site.tools.ToolSql;
import com.haili.site.tools.ToolString;
import com.jfinal.aop.Enhancer;

public class RoleService extends BaseService {

    @SuppressWarnings("unused")
    private static Logger log = Logger.getLogger(RoleService.class);

    public static final RoleService service = Enhancer.enhance(RoleService.class);

    /**
     * 保存
     * @param role
     * @return
     */
    public boolean save(Role role) {
        boolean flag = false;
        // 保存
        int pk = 1;
        Role roles = Role.dao.findFirst("select max(roleId) roleId from sys_role ");
        if (null != roles) {
            if (roles.getInt("roleId") != null) {
                pk = roles.getInt("roleId") + 1;
            }

        }
        role.set("roleId", pk);
        flag = role.save();
        return flag;
    }

    /**
     * 更新
     * @param role
     */
    public boolean update(Role role) {
        boolean flag = false;
        // 更新
        flag = role.update();

        return flag;
    }

    /**
     * 删除
     * @param role
     */
    public boolean delete(String ids) {
        boolean flag = false;
        String[] idsArr = splitByComma(ids);
        for (String roleIds : idsArr) {

            // 删除
            flag = Role.dao.deleteById(roleIds);
        }
        return flag;
    }

    /**
     * 设置角色功能
     * @param roleIds
     * @param moduleIds
     * @param operatorIds
     */
    public boolean setMenu(String roleIds, String menuIds) {
        boolean flag = false;
        Role role = Role.dao.findById(roleIds);
        role.set("menuIds", menuIds);
        flag = role.update();
        Role.dao.findById(roleIds).get("menuIds");
        return flag;
    }

    /**
     * 角色选择
     * @param ids 用户ids
     */
    public Map<String, Object> select(String ids) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Role> noCheckedList = new ArrayList<Role>();
        List<Role> checkedList = new ArrayList<Role>();
        if (ToolString.isEmpty(ids)) {
            noCheckedList = Role.dao.find("select *  from sys_role");
        } else {
            String roleIds = User.dao.findById(ids).getStr("roleIds");
            if (null != roleIds && !roleIds.equals("")) {
                String sql = ToolSql.sqlIn("sys_role", "roleId", roleIds.split(","));
                String notselect = ToolSql.sqlNotIn("sys_role", "roleId", roleIds.split(","));
                noCheckedList = Role.dao.find(notselect);
                checkedList = Role.dao.find(sql);
            } else {
                noCheckedList = Role.dao.find("select *  from sys_role");
            }
        }

        map.put("noCheckedList", noCheckedList);
        map.put("checkedList", checkedList);
        return map;
    }
}
