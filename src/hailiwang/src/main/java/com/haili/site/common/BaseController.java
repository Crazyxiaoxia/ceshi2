package com.haili.site.common;

import java.util.List;
import java.util.Map;

import com.haili.site.contants.ConstantWebContext;
import com.haili.site.model.User;
import com.haili.site.tools.ToolWeb;
import com.jfinal.core.Controller;
import com.jfinal.render.JsonRender;

/**
 * 公共Controller
 * @author 王晓东
 */
public abstract class BaseController extends Controller {

    /**
     * 全局变量
     */
    protected String ids; // 主键
    protected SplitPage splitPage; // 分页封装
    protected List<?> list; // 公共list

    /**
     * 请求/WEB-INF/下的视图文件
     */
    public void toUrl() {
        String toUrl = getPara(ConstantWebContext.request_toUrl);
        render(toUrl);
    }

    /**
     * 获取项目请求根路径
     * @return
     */
    protected String getCxt() {
        return getAttr(ConstantWebContext.request_cxt);
    }

    /**
     * 获取当前用户
     * @return
     */
    protected User getCUser() {
        return getAttr(ConstantWebContext.request_cUser);
    }

    /**
     * 获取ParamMap
     * @return
     */
    protected Map<String, String> getParamMap() {
        return getAttr(ConstantWebContext.request_paramMap);
    }

    /**
     * 添加值到ParamMap
     * @return
     */
    protected void addToParamMap(String key, String value) {
        Map<String, String> map = getAttr(ConstantWebContext.request_paramMap);
        map.put(key, value);
    }

    /**
     * request流转字符串
     * @return
     */
    protected String getRequestContent() {
        return ToolWeb.requestStream(getRequest());
    }

    /**
     * 重写getPara，进行二次decode解码
     */
    @Override
    public String getPara(String name) {
        if ("GET".equalsIgnoreCase(getRequest().getMethod().toUpperCase())) {
            return ToolWeb.getParam(getRequest(), name);
        }
        return super.getPara(name);
    }

    /**
     * 重写renderJson，避免出现IE8下出现下载弹出框
     */
    @Override
    public void renderJson(Object object) {
        String userAgent = getRequest().getHeader("User-Agent");
        if (userAgent.toLowerCase().indexOf("msie") != -1) {
            render(new JsonRender(object).forIE());
        } else {
            super.renderJson(object);
        }
    }

    /**
     * 分页
     * @param dataSource 数据源
     * @param splitPage
     * @param sqlId
     */
    protected void paging(String dataSource, SplitPage splitPage, String selectSqlId, String fromSqlId) {
        BaseService.service.paging(dataSource, splitPage, selectSqlId, fromSqlId);
    }

    /************************************		get 	set 	方法		************************************************/

    public void setSplitPage(SplitPage splitPage) {
        this.splitPage = splitPage;
    }

    /**
     * 数据权限控制
     * 
     * @return
     */
    //    protected List<Enorgnization> defaultParam() {
    //        User user = getCUser();
    //        Map<String, Object> map = new HashMap<String, Object>();
    //        String userId = String.valueOf(user.getInt("userId"));
    //        List<Enorgnization> orgs = new ArrayList<Enorgnization>();
    //        Map<String, Object> params = splitPage.getQueryParam();
    //        if (!ToolString.isEmpty(user.getStr("isSystem")) || user.getStr("superHr").equals("1")) {
    //            orgs = Enorgnization.dao.find("select *  from bbc_enorgnization where parentId is not null");
    //            String orgId = EnorgnizationService.service.getAllOrgByUserId(null);
    //            map.put("fitler", orgId);
    //            if (params.isEmpty()) {
    //                splitPage.setQueryParam(map);
    //            } else {
    //                map.putAll(params);
    //                splitPage.setQueryParam(map);
    //            }
    //        } else {
    //            orgs = Enorgnization.dao.find(ToolSql.sqlIn("bbc_enorgnization", "orgId", user.getStr("orgIds").split(",")) + " and parentId is not null");
    //            String orgId = EnorgnizationService.service.getAllOrgByUserId(userId);
    //            map.put("fitler", orgId);
    //            if (params.isEmpty()) {
    //                splitPage.setQueryParam(map);
    //            } else {
    //                map.putAll(params);
    //                splitPage.setQueryParam(map);
    //            }
    //        }
    //
    //        return orgs;
    //    }

}
