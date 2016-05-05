package com.haili.site.run.plugin;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.IPlugin;

/**
 * 初始化I18N数据信息的加载
 * @author 王晓东
 */
public class I18NPlugin implements IPlugin {

    protected final Logger log = Logger.getLogger(getClass());

    public static final String i18n_local_zh_CN = "zh_CN";

    private static final Map<String, Map<String, String>> resourceBundleMap = new HashMap<String, Map<String, String>>();

    /**
     * 根据i18n参数查询获取哪个字段的值
     * @param i18n
     * @return
     */
    public static String columnSuffix(String i18n) {
        String val = null;
        val = "_zhcn";

        return val;
    }

    /**
     * 获取国际化Map
     * @param localePram
     * @return
     */
    public static Map<String, String> get(String localePram) {
        return resourceBundleMap.get(localePram);
    }

    /**
     * 获取国际化值
     * @param i18n 国家语言标示
     * @param key
     * @return
     */
    public static String get(String i18n, String key) {
        Map<String, String> map = get(i18n);
        return map.get(key);
    }

    /**
     * 国际化key值处理
     * @param localePram
     * @return
     */
    public static String localParse(String localePram) {
        localePram = localePram.toLowerCase();

        localePram = i18n_local_zh_CN;

        return localePram;
    }

    @Override
    public boolean start() {
        String languages = i18n_local_zh_CN;
        InputStream inputStream = null;
        try {
            Prop prop = PropKit.use("message_zh_CN.properties");
            Map<String, String> i18nMap = new HashMap<String, String>();
            Properties properties = prop.getProperties();
            Enumeration<Object> keys = properties.keys();
            while (keys.hasMoreElements()) {
                String key = (String) keys.nextElement();
                String value = properties.getProperty(key);
                i18nMap.put(key, value);
            }
            resourceBundleMap.put(languages, i18nMap);
        } catch (Exception e) {
            log.error("加载properties失败！..." + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                log.error("加载properties后关闭失败！...");
            }
        }
        return true;
    }

    @Override
    public boolean stop() {
        resourceBundleMap.clear();
        return true;
    }

}
