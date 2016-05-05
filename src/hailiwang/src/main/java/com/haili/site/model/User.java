package com.haili.site.model;

import java.util.List;

import org.apache.log4j.Logger;

import com.haili.site.common.BaseModel;
import com.haili.site.tools.ToolSql;

/**
 * 用户model
 * @author 王晓东
 */
@SuppressWarnings("unused")
public class User extends BaseModel<User> {

    private static final long serialVersionUID = 6761767368352810428L;
    private static final String salt = "01234a";

    private static Logger log = Logger.getLogger(User.class);

    public static final User dao = new User();

    /**
     * sqlId : platform.user.splitPageSelect
     * 描述：分页Select
     */
    public static final String sqlId_splitPageSelect = "platform.user.splitPageSelect";

    /**
     * sqlId : platform.user.splitPageFrom
     * 描述：分页from
     */
    public static final String sqlId_splitPageFrom = "platform.user.splitPageFrom";
    public static final String sqlId_splitPageFroms = "platform.user.splitPageFroms";

    /**
     * sqlId : platform.user.paging
     * 描述：查询所有用户
     */
    public static final String sqlId_paging = "platform.user.paging";

    /**
     * sqlId : platform.user.treeUserNode
     * 描述：根据部门Id查询，人员树节点数据 
     */
    public static final String sqlId_treeUserNode = "platform.user.treeUserNode";

    /**
     * sqlId : platform.user.column
     * 描述：查询用户，自定义字段和值
     */
    public static final String sqlId_column = "platform.user.column";

    /**
     * sqlId : platform.user.stop
     * 描述：停用账户
     */
    public static final String sqlId_stop = "platform.user.stop";

    /**
     * sqlId : platform.user.start
     * 描述：启用停用账户
     */
    public static final String sqlId_start = "platform.user.start";

    /**
     * 关联查询，获取角色
     * @return
     */
    public List<Role> getRole() {
        String id = get("roleids");
        if (null != id && !id.isEmpty()) {
            return Role.dao.find(ToolSql.sqlIn("sys_role", "roleId", id.split(",")));
        }
        return null;
    }

    public void getSql() {
        getSql(User.sqlId_column);
        getSqlByBeetl(User.sqlId_column, null);
        getSqlByBeetl(User.sqlId_column, null, null);
    }
}
