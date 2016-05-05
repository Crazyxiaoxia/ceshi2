package com.haili.site.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.haili.site.common.BaseService;
import com.haili.site.model.Menu;
import com.haili.site.model.Role;
import com.haili.site.tools.ToolString;
import com.jfinal.aop.Enhancer;

public class MenuService extends BaseService {

    @SuppressWarnings("unused")
    private static Logger log = Logger.getLogger(MenuService.class);

    public static final MenuService service = Enhancer.enhance(MenuService.class);

    /**
     * 获取子节点数据
     * 
     * @param roleId
     * 
     * 
     * @return
     */
    public List<Map<String, String>> childNodeData(String roleId) {
        Role role = Role.dao.findById(roleId);
        String[] str = null;
        if (role != null) {
            str = role.getStr("menuIds").split(",");
        }
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        List<Menu> listall = Menu.dao.find("select * from  sys_menu");
        for (Menu menu : listall) {
            Map<String, String> pg = new HashMap<String, String>();
            pg.put("menuId", String.valueOf(menu.getInt("menuId")));
            pg.put("menuName", menu.getStr("menuName"));
            pg.put("parentId", String.valueOf(menu.getInt("parentId")));
            if (null != role && null != role.getStr("menuIds") && Arrays.asList(str).contains(String.valueOf(menu.getInt("menuId")))) {
                pg.put("checked", "true");
            }
            pg.put("open", "true");
            list.add(pg);
        }
        return list;
    }

    /**
     * 保存
     * @param pIds
     * @param names
     * @param orderIds
     * @param i18n
     * @return
     */
    public Boolean save(Menu menu) {
        boolean flag = false;
        String images = "2.png";
        menu.set("images", images);
        int pk = 1;
        Menu menus = Menu.dao.findFirst("select max(menuId) menuId from sys_menu ");
        if (null != menus) {
            if (menus.getInt("menuId") != null) {
                pk = menus.getInt("menuId") + 1;
            }

        }
        menu.set("menuId", pk);
        flag = menu.save();
        return flag;
    }

    /**
     * 更新
     * @param ids
     * @param pIds
     * @param names
     * @param principalIds
     */
    public boolean update(Menu menu) {
        boolean flag = false;
        if (menu.getStr("isParent").equals("true")) {
            menu.set("menuUrl", null);
        }
        flag = menu.update();
        return flag;
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    public boolean delete(String ids) {
        boolean flag = Menu.dao.deleteById(ids);
        return flag;
    }

    /**
     * 获取所有菜单数据
     * @return
     */
    public List<Menu> findRoots() {
        List<Menu> menus = new ArrayList<Menu>();
        menus = Menu.dao.find("select *  from  sys_menu where parentId is null order by levels");
        for (Menu menu : menus) {
            menu.put("children", menu.getChildren());
        }
        return menus;
    }

    /**
     * 根据父节点获取子节点
     * @param parentId
     * @return
     */
    public List<Menu> getChildren(String parentId) {
        List<Menu> list = new ArrayList<Menu>();
        if (ToolString.isEmpty(parentId)) {
            return list;
        } else {
            list = Menu.dao.find("select *  from  sys_menu  where parentId=? order by orderids", parentId);
            for (Menu menu : list) {
                menu.put("children", menu.getChildren());
            }
        }

        return list;
    }
}
