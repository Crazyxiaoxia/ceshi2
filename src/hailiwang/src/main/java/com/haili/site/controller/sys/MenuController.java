package com.haili.site.controller.sys;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.haili.site.common.BaseController;
import com.haili.site.model.Menu;
import com.haili.site.service.MenuService;

/**
 * 菜单管理
 * 
 */
public class MenuController extends BaseController {

    @SuppressWarnings("unused")
    private static Logger log = Logger.getLogger(MenuController.class);

    /**
     * 菜单管理首页
     */
    public void index() {
        render("/tkamcbbc/menu/list.html");
    }

    /**
     * ztree子节点数据
     */
    public void treeData() {
        List<Map<String, String>> nodeList = MenuService.service.childNodeData(ids);
        renderJson(nodeList);
    }

    /**
     * 获取菜单
     */
    public void getMenu() {
        //        List<ZtreeNode> nodeList = MenuService.service.getParentMenu();
        //        renderJson(nodeList);
    }

    /**
     * 保存菜单
     */
    public void save() {
        Menu menu = getModel(Menu.class);

        MenuService.service.save(menu);
        redirect("/tkamcbbc/menu");
    }

    /**
     * 更新菜单
     */
    public void update() {
        Menu menu = getModel(Menu.class);
        MenuService.service.update(menu);
        redirect("/tkamcbbc/menu");
    }

    /**
     * 准备更新菜单
     */
    public void toEdit() {
        Menu menu = Menu.dao.findById(getPara("id"));
        setAttr("menu", menu);
        render("/tkamcbbc/menu/update.html");
    }

    /**
     * 删除菜单
     */
    public void delete() {
        MenuService.service.delete(getPara("ids"));
        redirect("/tkamcbbc/menu");
    }

    //获取父菜单
    public void getAll() {

        List<Menu> menus = MenuService.service.findRoots();

        renderJson(menus);

    }

    //获取子菜单
    public void getNodes() {

        List<Menu> rs = MenuService.service.getChildren(getPara("parentId"));
        renderJson(rs);
    }

}
