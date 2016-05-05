package com.haili.site.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.haili.site.common.BaseService;
import com.haili.site.model.Menu;
import com.haili.site.model.Role;
import com.haili.site.model.User;
import com.haili.site.tools.ToolSql;
import com.haili.site.tools.ToolString;
import com.jfinal.aop.Enhancer;

public class IndexService extends BaseService {

    @SuppressWarnings("unused")
    private static Logger log = Logger.getLogger(IndexService.class);

    public static final IndexService service = Enhancer.enhance(IndexService.class);

    /**
     * 查询用户可操作的菜单
     * @param user
     * @return List<Menu>
     */
    public List<Menu> menu(User user) {
        List<Menu> oneList = new ArrayList<Menu>();
        if (!ToolString.isEmpty(user.getStr("isSystem"))) {
            oneList = Menu.dao.find("select *  from sys_menu where isParent=? order by levels", "true");
            for (Menu menu : oneList) {
                List<Menu> twoList = Menu.dao.find("select * from sys_menu where parentId=? order by orderids ", menu.getInt("menuId"));
                menu.put("subList", twoList);
            }
        } else {
            List<Role> roles = user.getRole();
            if (null != roles && roles.size() >= 0) {
                for (Role role : roles) {
                    List<Menu> menus = role.getMenu();
                    for (Menu menu : menus) {
                        if ("".equals(menu.getInt("parentId")) || null == menu.getInt("parentId")) {
                            oneList.add(menu);
                            String sql = ToolSql.sqlIn("sys_menu", "menuId", role.getStr("menuIds").split(","));
                            StringBuffer sBuffer = new StringBuffer();
                            sBuffer.append(sql);
                            sBuffer.append(" and parentId='" + menu.getInt("menuId") + "' order by orderids");
                            List<Menu> twoList = Menu.dao.find(sBuffer.toString());
                            menu.put("subList", twoList);
                        } else {

                        }
                    }
                }
            }
            for (int i = 0; i < oneList.size(); i++) {
                for (int j = oneList.size() - 1; j > i; j--) {
                    if (oneList.get(i).get("menuId").equals(oneList.get(j).get("menuId"))) {
                        oneList.remove(oneList.get(j));
                    }
                }
            }
        }

        return oneList;
    }
}
