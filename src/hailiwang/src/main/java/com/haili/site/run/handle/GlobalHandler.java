package com.haili.site.run.handle;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.haili.site.contants.ConstantInit;
import com.haili.site.contants.ConstantWebContext;
import com.haili.site.run.plugin.I18NPlugin;
import com.haili.site.tools.ToolRandoms;
import com.haili.site.tools.ToolWeb;
import com.jfinal.handler.Handler;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;

/**
 * 全局Handler，设置一些通用功能
 * @author 王晓东
 * 描述：主要是一些全局变量的设置，再就是日志记录开始和结束操作
 */
public class GlobalHandler extends Handler {

    private static Logger log = Logger.getLogger(GlobalHandler.class);

    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {

        log.info("设置 web 路径");
        String cxt = ToolWeb.getContextPath(request);
        request.setAttribute(ConstantWebContext.request_cxt, cxt);

        log.info("request 随机分配一个请求id");
        request.setAttribute(ConstantWebContext.request_id, ToolRandoms.getUuid(true));

        log.debug("request cookie 处理");
        Map<String, Cookie> cookieMap = ToolWeb.readCookieMap(request);
        request.setAttribute(ConstantWebContext.request_cookieMap, cookieMap);

        log.debug("request param 请求参数处理");
        request.setAttribute(ConstantWebContext.request_paramMap, ToolWeb.getParamMap(request));

        log.debug("request 国际化");
        String localePram = request.getParameter(ConstantWebContext.request_localePram);
        if (null != localePram && !localePram.isEmpty()) {
            int maxAge = PropKit.getInt(ConstantInit.config_maxAge_key);
            localePram = I18NPlugin.localParse(localePram);
            ToolWeb.addCookie(response, "", "/", true, ConstantWebContext.cookie_language, localePram, maxAge);
        } else {
            localePram = ToolWeb.getCookieValueByName(request, ConstantWebContext.cookie_language);
            if (null == localePram || localePram.isEmpty()) {
                Locale locale = request.getLocale();
                String language = locale.getLanguage();
                localePram = language;
                String country = locale.getCountry();
                if (null != country && !country.isEmpty()) {
                    localePram += "_" + country;
                }
            }
            localePram = I18NPlugin.localParse(localePram);
        }
        Map<String, String> i18nMap = I18NPlugin.get(localePram);
        request.setAttribute(ConstantWebContext.request_localePram, localePram);
        request.setAttribute(ConstantWebContext.request_i18nMap, i18nMap);
        Prop prop = PropKit.use("init.properties");
        Map<String, String> eninfo = new HashMap<String, String>();
        Properties properties = prop.getProperties();
        Enumeration<Object> keys = properties.keys();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            String value = properties.getProperty(key);
            eninfo.put(key, value);
        }
        request.setAttribute("eninfo", eninfo);
        log.info("设置Header");
        request.setAttribute("decorator", "none");
        response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
        response.setHeader("Pragma", "no-cache"); //HTTP 1.0
        response.setDateHeader("Expires", 0); //prevents caching at the proxy server

        next.handle(target, request, response, isHandled);

        log.info("请求处理完毕，计算耗时");

    }

}
