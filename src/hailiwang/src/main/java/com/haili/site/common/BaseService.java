package com.haili.site.common;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.log4j.Logger;

import com.haili.site.contants.ConstantRender;
import com.haili.site.tools.ToolSqlXml;
import com.haili.site.tools.ToolString;
import com.jfinal.aop.Enhancer;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

/**
 * 公共方法
 * @author 王晓东
 */
public class BaseService {

    private static Logger log = Logger.getLogger(BaseService.class);

    public static final BaseService service = Enhancer.enhance(BaseService.class);

    /**
     * 分页
     * @param dataSource	数据源
     * @param splitPage		分页对象
     * @param selectSqlId	select之后，from之前
     * @param fromSqlId		from之后
     */
    @SuppressWarnings("unchecked")
    public void paging(String dataSource, SplitPage splitPage, String selectSqlId, String fromSqlId) {
        String selectSql = getSqlByBeetl(selectSqlId, splitPage.getQueryParam());

        Map<String, Object> map = getFromSql(splitPage, fromSqlId);
        String fromSql = (String) map.get("sql");
        LinkedList<Object> paramValue = (LinkedList<Object>) map.get("paramValue");

        // 分页封装
        Page<?> page = Db.use(dataSource).paginate(splitPage.getPageNumber(), splitPage.getPageSize(), selectSql, null, fromSql, paramValue.toArray());
        splitPage.setTotalPage(page.getTotalPage());
        splitPage.setTotalRow(page.getTotalRow());
        splitPage.setList(page.getList());
        splitPage.compute();
    }

    /**
     * 分页获取form sql和预处理参数
     * @param splitPage
     * @param sqlId
     * @return
     */
    private Map<String, Object> getFromSql(SplitPage splitPage, String sqlId) {
        // 接收返回值对象
        StringBuilder formSqlSb = new StringBuilder();
        LinkedList<Object> paramValue = new LinkedList<Object>();

        // 调用生成from sql，并构造paramValue
        String sql = getSqlByBeetl(sqlId, splitPage.getQueryParam(), paramValue);
        formSqlSb.append(sql);

        // 排序
        String orderColunm = splitPage.getOrderColunm();
        String orderMode = splitPage.getOrderMode();
        if (null != orderColunm && !orderColunm.isEmpty() && null != orderMode && !orderMode.isEmpty()) {
            formSqlSb.append(" order by ").append(orderColunm).append(" ").append(orderMode);
        }

        String formSql = formSqlSb.toString();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sql", formSql);
        map.put("paramValue", paramValue);

        return map;
    }

    /**
     * 获取SQL，动态SQL，使用Beetl解析
     * @param sqlId
     * @param param
     * @return
     */
    public String getSqlByBeetl(String sqlId, Map<String, Object> param) {
        return ToolSqlXml.getSql(sqlId, param, ConstantRender.sql_renderType_beetl);
    }

    /**
     * 获取SQL，动态SQL，使用Beetl解析
     * @param sqlId 
     * @param param 查询参数
     * @param list 用于接收预处理的值
     * @return
     */
    public String getSqlByBeetl(String sqlId, Map<String, Object> param, LinkedList<Object> list) {
        return ToolSqlXml.getSql(sqlId, param, ConstantRender.sql_renderType_beetl, list);
    }

    /**
     * 把11,22,33...转成'11','22','33'...
     * @param ids
     * @return
     */
    public String sqlIn(String ids) {
        if (null == ids || ids.trim().isEmpty()) {
            return null;
        }

        String[] idsArr = ids.split(",");

        return sqlIn(idsArr);
    }

    /**
     * 把数组转成'11','22','33'...
     * @param ids
     * @return
     */
    public String sqlIn(String[] idsArr) {
        if (idsArr == null || idsArr.length == 0) {
            return null;
        }

        int length = idsArr.length;

        StringBuilder sqlSb = new StringBuilder();

        for (int i = 0, size = length - 1; i < size; i++) {
            String id = idsArr[i];
            if (!ToolString.regExpVali(id, ToolString.regExp_letter_5)) { // 匹配字符串，由数字、大小写字母、下划线组成
                log.error("字符安全验证失败：" + id);
            }
            sqlSb.append(" '").append(id).append("', ");
        }

        if (length != 0) {
            String id = idsArr[length - 1];
            if (!ToolString.regExpVali(id, ToolString.regExp_letter_5)) { // 匹配字符串，由数字、大小写字母、下划线组成
                log.error("字符安全验证失败：" + id);

            }
            sqlSb.append(" '").append(id).append("' ");
        }

        return sqlSb.toString();
    }

    /**
     * 把11,22,33...转成数组['11','22','33'...]
     * @param ids
     * @return
     * 描述：把字符串分割成数组返回，并验证分割后的数据
     */
    public String[] splitByComma(String ids) {
        if (null == ids || ids.trim().isEmpty()) {
            return null;
        }

        String[] idsArr = ids.split(",");

        for (String id : idsArr) {
            if (!ToolString.regExpVali(id, ToolString.regExp_letter_5)) { // 匹配字符串，由数字、大小写字母、下划线组成
                log.error("字符安全验证失败：" + id);

            }
        }

        return idsArr;
    }

}
