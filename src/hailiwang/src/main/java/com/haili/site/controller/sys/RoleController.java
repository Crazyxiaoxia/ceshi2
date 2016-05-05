package com.haili.site.controller.sys;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.haili.site.common.BaseController;
import com.haili.site.contants.ConstantInit;
import com.haili.site.model.Role;
import com.haili.site.service.RoleService;

/**
 * 角色管理
 * @author 王晓东
 */
@SuppressWarnings("unused")
public class RoleController extends BaseController {

    private static Logger log = Logger.getLogger(RoleController.class);

    private List<Role> noCheckedList; // 未选角色
    private List<Role> checkedList; // 已选角色

    /**
     * 角色列表
     */
    public void index() {
        paging(ConstantInit.db_dataSource_main, splitPage, Role.sqlId_splitPage_selects, Role.sqlId_splitPageFrom);
        render("/tkamcbbc/role/list.html");
    }

    /**
     * 保存角色
     */
    public void save() {
        boolean flag = RoleService.service.save(getModel(Role.class));
        redirect("/tkamcbbc/role");
    }

    /**
     * 准备更新角色
     */
    public void edit() {
        this.getRequest().setAttribute("role", Role.dao.findById(getPara()));
        render("/tkamcbbc/role/update.html");
    }

    /**
     * 更新角色
     */
    public void update() {
        RoleService.service.update(getModel(Role.class));
        redirect("/tkamcbbc/role");
    }

    /**
     * 删除角色
     */
    public void delete() {
        RoleService.service.delete(getPara() == null ? ids : getPara());
        redirect("/tkamcbbc/role");
    }

    /**
     * 设置角色拥有的菜单
     */
    public void setMenu() {
        RoleService.service.setMenu(ids, getPara("menuIds"));
        renderJson(ids);
    }

    /**
     * 分组对应的角色选择
     */
    @SuppressWarnings("unchecked")
    public void select() {
        Map<String, Object> map = RoleService.service.select(ids);
        noCheckedList = (List<Role>) map.get("noCheckedList");
        checkedList = (List<Role>) map.get("checkedList");
        render("/tkamcbbc/role/select.html");
    }

}
