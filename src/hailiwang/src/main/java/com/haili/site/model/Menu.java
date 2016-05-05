package com.haili.site.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.haili.site.common.BaseModel;

/**
 * 菜单model
 * @author 王晓东
 */
@SuppressWarnings("unused")
public class Menu extends BaseModel<Menu> {

    private static final long serialVersionUID = 6761767368352810428L;

    private static Logger log = Logger.getLogger(Menu.class);

    public static final Menu dao = new Menu();

    /**
     * sqlId : platform.menu.root
     * 描述：查询根菜单
     */
    public static final String sqlId_root = "platform.menu.root";

    /**
     * sqlId : platform.menu.child
     * 描述：查询子菜单
     */
    public static final String sqlId_child = "platform.menu.child";

    /**
     * sqlId : platform.menu.childCount
     * 描述：查询子菜单数量
     */
    public static final String sqlId_childCount = "platform.menu.childCount";

    /**
     * sqlId : platform.menu.rootId
     * 描述：查询根菜单id
     */
    public static final String sqlId_rootId = "platform.menu.rootId";

    /**
     * sqlId : platform.menu.operator
     * 描述：查询根菜单，包含对应功能
     */
    public static final String sqlId_operator = "platform.menu.operator";
    public static final String sqlId_splitPageFrom = "tkamcbbc.menu.splitPageFrom";
    public static final String sqlId_splitPageSelect = "tkamcbbc.menu.splitPageSelect";
    public List<Menu> children = new ArrayList<Menu>();

    /* (non-Javadoc)
    * @see com.jfinal.plugin.activerecord.Model#put(java.util.Map)
    */
    @Override
    public Menu put(String key, Object value) {
        // TODO Auto-generated method stub
        return super.put(key, value);
    }

    public List<Menu> getChildren() {
        children = this.dao.find("select *  from  sys_menu where parentId=?", get("menuId"));
        return children;
    }

}
