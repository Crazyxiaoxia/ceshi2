package com.haili.site.model;

import java.util.List;

import org.apache.log4j.Logger;

import com.haili.site.common.BaseModel;
import com.haili.site.tools.ToolSql;

/**
 * 角色model
 * @author 王晓东
 */
@SuppressWarnings("unused")
public class Role extends BaseModel<Role> {

    private static final long serialVersionUID = 6761767368352810428L;

    private static Logger log = Logger.getLogger(Role.class);

    public static final Role dao = new Role();

    /**
     * sqlId : platform.role.paging
     * 描述：查询所有角色
     */
    public static final String sqlId_paging = "platform.role.paging";

    /**
     * sqlId : platform.role.noCheckedFilter
     * 描述：
     */
    public static final String sqlId_noCheckedFilter = "platform.role.noCheckedFilter";

    /**
     * sqlId : platform.role.checkedFilter
     * 描述：
     */
    public static final String sqlId_checkedFilter = "platform.role.checkedFilter";

    /**
     * sqlId : platform.role.noChecked
     * 描述：
     */
    public static final String sqlId_noChecked = "platform.role.noChecked";

    /**
     * sqlId : platform.role.splitPageSelect
     * 描述：分页Select
     */
    public static final String sqlId_splitPage_selects = "platform.role.splitPageSelect";

    /**
     * sqlId : platform.role.splitPageFrom
     * 描述：分页from
     */
    public static final String sqlId_splitPageFrom = "platform.role.splitPageFrom";

    //菜单
    public List<Menu> getMenu() {

        return Menu.dao.find(ToolSql.sqlIn("sys_menu", "menuId", get("menuIds").toString(), ",") + " order by levels");
    }

}
