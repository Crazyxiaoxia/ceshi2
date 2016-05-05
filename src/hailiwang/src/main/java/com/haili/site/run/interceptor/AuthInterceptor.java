package com.haili.site.run.interceptor;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import com.haili.site.common.BaseController;
import com.haili.site.contants.ConstantAuth;
import com.haili.site.contants.ConstantInit;
import com.haili.site.contants.ConstantWebContext;
import com.haili.site.tools.ToolWeb;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.kit.PropKit;

/**
 * 权限认证拦截器
 * @author 王晓东
 * 描述：
 * 1.处理权限验证
 * 2.处理全局异常
 * 3.处理权限相关的工具类方法
 */
public class AuthInterceptor implements Interceptor {

    private static Logger log = Logger.getLogger(AuthInterceptor.class);

    /**
     * 实现权限认证接口
     */
    @Override
    public void intercept(Invocation invoc) {
        BaseController contro = (BaseController) invoc.getController();
        HttpServletRequest request = contro.getRequest();
        log.info("获取reqSysLog!");
        //        Syslog reqSysLog = contro.getAttr(ConstantWebContext.reqSysLogKey);
        //        contro.setReqSysLog(reqSysLog);

        String uri = invoc.getActionKey(); // 默认就是ActionKey
        if (invoc.getMethodName().equals(ConstantWebContext.request_toUrl)) {
            uri = ToolWeb.getParam(request, ConstantWebContext.request_toUrl); // 否则就是toUrl的值
        }

        //        User user = (User) contro.getSession().getAttribute("user");
        //        if (null != user) {
        //            reqSysLog.set(Syslog.column_userids, user.getInt("userId"));
        //            contro.setAttr(ConstantWebContext.request_cUser, user);
        //            contro.setAttr(ConstantWebContext.request_cUserIds, user.getInt("userId"));
        //
        //            MDC.put("userId", user.getInt("userId"));
        //            MDC.put("userName", user.getStr("userName"));
        //        } else {
        //            if (!(uri.endsWith("/login") || uri.endsWith("/login/vali"))) {
        //
        //                contro.redirect("/tkamcbbc/login");
        //                return;
        //            }
        //            MDC.put("userId", "*unknown userId*");
        //            MDC.put("userName", "*unknown userName*");
        //        }
        //
        //        contro.setAttr("flag", user);
        //        reqSysLog.set(Syslog.column_status, "1");// 成功
        //        Date actionStartDate = ToolDateTime.getDate();// action开始时间
        //        reqSysLog.set(Syslog.column_actionstartdate, ToolDateTime.getSqlTimestamp(actionStartDate));
        //        reqSysLog.set(Syslog.column_actionstarttime, actionStartDate.getTime());

        try {
            invoc.invoke();
        } catch (Exception e) {
            String expMessage = e.getMessage();
            // 开发模式下的异常信息
            if (Boolean.parseBoolean(PropKit.get(ConstantInit.config_devMode))) {
                ByteArrayOutputStream buf = new ByteArrayOutputStream();
                e.printStackTrace(new PrintWriter(buf, true));
                expMessage = buf.toString();
            }

            //            reqSysLog.set(Syslog.column_status, "0");// 失败
            //            reqSysLog.set(Syslog.column_description, expMessage);
            //            reqSysLog.set(Syslog.column_cause, "3");// 业务代码异常

            log.error("返回失败提示页面!Exception = " + e.getMessage());
            toView(contro, ConstantAuth.auth_exception, "业务逻辑代码遇到异常Exception = " + expMessage);
        } finally {
            MDC.remove("userId");
            MDC.remove("userName");
        }
    }

    /**
     * 提示信息展示页
     * @param contro
     * @param type
     * @param msg
     */
    private void toView(BaseController contro, String type, String msg) {
        if (type.equals(ConstantAuth.auth_no_login)) {// 未登录处理
            contro.redirect("/tkamcbbc/login");
            return;
        }

        contro.setAttr("msg", msg);

        String isAjax = contro.getRequest().getHeader("X-Requested-With");
        if (isAjax != null && isAjax.equalsIgnoreCase("XMLHttpRequest")) {
            contro.render("/common/msgAjax.html"); // Ajax页面
        } else {
            contro.render("/common/msg.html"); // 完整html页面
        }
    }

    /**
     * 获取当前登录用户
     * @param request
     * @param response
     * @param userAgentVali 是否验证 User-Agent
     * @return
     */
    //    public static User getCurrentUser(HttpServletRequest request, HttpServletResponse response, boolean userAgentVali) {
    //        User user = (User) request.getSession().getAttribute("user");
    //        return user;
    //    }

    /**
     * 设置当前登录用户到cookie
     * @param request
     * @param response
     * @param user
     * @param autoLogin
     */
    //    public static void setCurrentUser(HttpServletRequest request, HttpServletResponse response, User user) {
    //        // 1.设置cookie有效时间
    //        int maxAgeTemp = -1;
    //        // 2.设置用户名到cookie
    //        ToolWeb.addCookie(response, "", "/", true, "userName", user.getStr("username"), maxAgeTemp);
    //
    //        // 3.生成登陆认证cookie
    //        String userIds = String.valueOf(user.getInt("userId"));
    //        String ips = ToolWeb.getIpAddr(request);
    //        String userAgent = request.getHeader("User-Agent");
    //        long date = ToolDateTime.getDateByTime();
    //
    //        StringBuilder token = new StringBuilder();// 时间戳.#.USERID.#.USER_IP.#.USER_AGENT.#.autoLogin
    //        token.append(date).append(".#.").append(userIds).append(".#.").append(ips).append(".#.").append(userAgent).append(".#.");
    //        String authmark = token.toString();
    //
    //        // 4. 添加到Cookie
    //        ToolWeb.addCookie(response, "", "/", true, ConstantWebContext.cookie_authmark, authmark, maxAgeTemp);
    //    }

}
