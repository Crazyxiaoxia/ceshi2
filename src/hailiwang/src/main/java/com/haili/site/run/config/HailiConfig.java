package com.haili.site.run.config;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.beetl.ext.jfinal.BeetlRenderFactory;

import com.haili.site.contants.ConstantCache;
import com.haili.site.contants.ConstantInit;
import com.haili.site.run.handle.GlobalHandler;
import com.haili.site.run.interceptor.AuthInterceptor;
import com.haili.site.run.interceptor.ParamPkgInterceptor;
import com.haili.site.run.plugin.DbMappingPlugin;
import com.haili.site.run.plugin.FileRenamePlugin;
import com.haili.site.run.plugin.I18NPlugin;
import com.haili.site.run.plugin.SqlXmlPlugin;
import com.haili.site.run.route.HailiRoutes;
import com.haili.site.tools.ToolBeetl;
import com.haili.site.tools.ToolCache;
import com.haili.site.tools.ToolString;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.i18n.I18nInterceptor;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.tx.TxByMethodRegex;
import com.jfinal.plugin.activerecord.tx.TxByMethods;
import com.jfinal.plugin.ehcache.EhCachePlugin;

/**
 * Jfinal API 引导式配置，系统的核心配置，负责生产、加载所有核心组件
 */
public class HailiConfig extends JFinalConfig {

    private static Logger log = Logger.getLogger(HailiConfig.class);

    /**
     * 配置常量
     */
    @Override
    public void configConstant(Constants constants) {
        log.info("configConstant 缓存 properties");
        PropKit.use("init.properties");

        log.info("configConstant 设置字符集");
        constants.setEncoding(ToolString.encoding);

        log.info("configConstant 设置是否开发模式");
        constants.setDevMode(PropKit.getBoolean(ConstantInit.config_devMode, false));
        log.info("configConstant 设置path相关");

        Properties p = null;

        //        String fileupload = (String) p.get("FILE_UPLOAD");
        //        if (fileupload == null) {
        //            fileupload = "files";
        //        }
        //        File folder = new File(fileupload);
        //        if (!folder.exists()) {
        //            folder.mkdir();
        //        }
        //        constants.setBaseUploadPath(fileupload); // 上传公共路径

        constants.setBaseDownloadPath(PathKit.getWebRootPath() + "/files"); // 下载公共路径
        log.info("configConstant 视图Beetl设置");
        constants.setMainRenderFactory(new BeetlRenderFactory());
        ToolBeetl.regiseter();
        log.info("configConstant 视图error page设置");
        constants.setError404View("/common/404.html");
        constants.setError500View("/common/500.html");

        log.info("configConstant i18n文件前缀设置设置");
        constants.setI18nDefaultBaseName(PropKit.get(ConstantInit.config_i18n_filePrefix));
        constants.setI18nDefaultLocale("zh_CN");
    }

    /**
     * 配置路由
     */
    @Override
    public void configRoute(Routes routes) {
        log.info("configRoute 手动注册路由");
        routes.add(new HailiRoutes());

    }

    /**
     * 配置插件
     */
    @Override
    public void configPlugin(Plugins plugins) {
        log.info("注册paltform ActiveRecordPlugin");
        new DbMappingPlugin(plugins);

        log.info("I18NPlugin 国际化键值对加载");
        plugins.add(new I18NPlugin());

        if (ToolCache.getCacheType().equals(ConstantCache.cache_type_ehcache)) {
            log.info("EhCachePlugin EhCache缓存");
            plugins.add(new EhCachePlugin());

        }

        log.info("SqlXmlPlugin 解析并缓存 xml sql");
        plugins.add(new SqlXmlPlugin());

        log.info("afterJFinalStart 配置文件上传命名策略插件");
        plugins.add(new FileRenamePlugin());
    }

    /**
     * 配置全局拦截器
     */
    @Override
    public void configInterceptor(Interceptors interceptors) {
        log.info("configInterceptor 权限认证拦截器");
        interceptors.add(new AuthInterceptor());

        log.info("configInterceptor 参数封装拦截器");
        interceptors.add(new ParamPkgInterceptor());

        log.info("configInterceptor 配置开启事物规则");
        interceptors.add(new TxByMethods("save", "update", "delete"));
        interceptors.add(new TxByMethodRegex("(.*save.*|.*update.*|.*delete.*)")); // 2.1只支持单实例添加，多方法名匹配使用一个正则匹配

        log.info("configInterceptor i18n拦截器");
        interceptors.add(new I18nInterceptor());
    }

    /**
     * 配置处理器
     */
    @Override
    public void configHandler(Handlers handlers) {
        log.info("configHandler 全局配置处理器，主要是记录日志和request域值处理");
        handlers.add(new GlobalHandler());
    }

    /**
     * 系统启动完成后执行
     */
    @Override
    public void afterJFinalStart() {
        //        log.info("afterJFinalStart 启动操作日志入库线程");
        //        ThreadSysLog.startSaveDBThread();
    }

    /**
     * 系统关闭前调用
     */
    @Override
    public void beforeJFinalStop() {
        //        log.info("beforeJFinalStop 释放日志入库线程");
        //        ThreadSysLog.setThreadRun(false);
    }

    /**
     * 运行此 main 方法可以启动项目
     * 说明：
     * 1. linux 下非root账户运行端口要>1024
     * 2. idea 中运行webAppDir路径可能需要适当调整，可以设置为WebContent的绝对路径
     */
    public static void main(String[] args) {
        JFinal.start("src/main/webapp", 99, "/", 5);

    }
}
