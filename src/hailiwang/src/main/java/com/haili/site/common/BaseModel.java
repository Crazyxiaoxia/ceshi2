package com.haili.site.common;

import java.util.LinkedList;
import java.util.Map;

import com.haili.site.contants.ConstantRender;
import com.haili.site.tools.ToolSqlXml;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Table;
import com.jfinal.plugin.activerecord.TableMapping;

/**
 * Model基础类
 * 
 * @author 王晓东
 * @param <M>
 * 
 * 抽取公共方法，并重写save、update、getDate方法
 */
public abstract class BaseModel<M extends Model<M>> extends Model<M> {

    private static final long serialVersionUID = -900378319414539856L;

    /**
     * 字段描述：版本号 
     * 字段类型 ：bigint 
     */
    public static final String column_version = "version";

    /**
     * sqlId : platform.baseModel.select
     * 描述：通用查询
     */
    public static final String sqlId_select = "platform.baseModel.select";

    /**
     * sqlId : platform.baseModel.update
     * 描述：通用更新
     */
    public static final String sqlId_update = "platform.baseModel.update";

    /**
     * sqlId : platform.baseModel.delete
     * 描述：通用删除
     */
    public static final String sqlId_delete = "platform.baseModel.delete";

    /**
     * sqlId : platform.baseModel.deleteIn
     * 描述：通用删除
     */
    public static final String sqlId_deleteIn = "platform.baseModel.deleteIn";

    /**
     * sqlId : platform.baseModel.deleteOr
     * 描述：通用删除
     */
    public static final String sqlId_deleteOr = "platform.baseModel.deleteOr";

    /**
     * sqlId : platform.baseModel.splitPageSelect
     * 描述：通用select *
     */
    public static final String sqlId_splitPageSelect = "platform.baseModel.splitPageSelect";

    /**
     * 获取SQL，固定SQL
     * @param sqlId
     * @return
     */
    protected String getSql(String sqlId) {
        return ToolSqlXml.getSql(sqlId);
    }

    /**
     * 获取SQL，动态SQL，使用Beetl解析
     * @param sqlId
     * @param param
     * @return
     */
    protected String getSqlByBeetl(String sqlId, Map<String, Object> param) {
        return ToolSqlXml.getSql(sqlId, param, ConstantRender.sql_renderType_beetl);
    }

    /**
     * 获取SQL，动态SQL，使用Beetl解析
     * @param sqlId 
     * @param param 查询参数
     * @param list 用于接收预处理的值
     * @return
     */
    protected String getSqlByBeetl(String sqlId, Map<String, Object> param, LinkedList<Object> list) {
        return ToolSqlXml.getSql(sqlId, param, ConstantRender.sql_renderType_beetl, list);
    }

    /**
     * 获取表映射对象
     * 
     * @return
     */
    protected Table getTable() {
        return TableMapping.me().getTable(getClass());
    }

    /**
     * 重写save方法
     */
    @Override
    public boolean save() {

        return super.save();
    }

    /**
     * 重写update方法
     */
    @Override
    public boolean update() {
        return super.update();
    }

}
