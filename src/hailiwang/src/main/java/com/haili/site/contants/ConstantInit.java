package com.haili.site.contants;

/**
 * init配置文件对应的key
 * @author 王晓东
 */
public interface ConstantInit {

    /**
     * 扫描的包
     */
    public static final String config_scan_package = "config.scan.package";

    /**
     * 扫描的jar
     */
    public static final String config_scan_jar = "config.scan.jar";

    /**
     * 开发模式
     */
    public static final String config_devMode = "config.devMode";

    /**
     * #文件上传大小限制 10 * 1024 * 1024 = 10M
     */
    public static final String config_maxPostSize_key = "config.maxPostSize";

    /**
     * # cookie 值的时间
     */
    public static final String config_maxAge_key = "config.maxAge";

    /**
     * # 不使用自动登陆时，最大超时时间，单位：分钟
     */
    public static final String config_session_key = "config.session";

    /**
     * # 域名或者服务器IP，多个逗号隔开，验证Referer时使用
     */
    public static final String config_domain_key = "config.domain";

    /**
     *  缓存类型配置
     */
    public static final String config_cache_type = "config.cache.type";

    /**
     *  国际化配置，资源文件前缀
     */
    public static final String config_i18n_filePrefix = "config.i18n.filePrefix";

    /**
     * 当前数据库类型：mysql
     */
    public static final String db_type_mysql = "mysql";

    /**
     * 数据库连接参数：驱动
     */
    public static final String db_connection_mysql_driverClass = "mysql.driverClass";

    /**
     * 数据库连接参数：连接URL
     */
    public static final String db_connection_mysql_jdbcUrl = "mysql.jdbcUrl";

    /**
     * 数据库连接参数：用户名
     */
    public static final String db_connection_mysql_userName = "mysql.userName";

    /**
     * 数据库连接参数：密码
     */
    public static final String db_connection_mysql_passWord = "mysql.passWord";

    /**
     * 数据库连接池参数：初始化连接大小
     */
    public static final String db_initialSize = "db.initialSize";

    /**
     * 数据库连接池参数：最少连接数
     */
    public static final String db_minIdle = "db.minIdle";

    /**
     * 数据库连接池参数：最多连接数
     */
    public static final String db_maxActive = "db.maxActive";

    /**
     *  主数据源名称：系统主数据源
     */
    public static final String db_dataSource_main = "db.dataSource.main";
    /**
     * 企业理财模式,预置+比例
     */
    public static final String preset_1 = "1";
    /**
     * 企业理财模式，预置+金额
     */
    public static final String preset_2 = "2";
    /**
     * 企业理财模式，非预置+比例
     */
    public static final String preset_3 = "3";
    /**
     * 企业理财模式，非预置+金额
     */
    public static final String preset_4 = "4";
    public static final String isSuper = "1";
    /**
     * 导入模板标题
     */
    public static final String[] staff = { "序号", "员工工号", "员工姓名", "身份证号", "工资卡银行", "工资卡号", "所在城市", "手机号", "所在分公司", "邮件", "性别", "学历", "地址", "邮政编码" };
    public static final String[] staff_more = { "序号", "员工工号", "员工姓名", "身份证号", "工资卡银行", "工资卡号", "所在城市", "手机号", "所在分公司", "邮件", "性别", "学历", "地址", "邮政编码", "是否在职" };
    public static final String[] swages = { "序号", "员工姓名", "身份证号", "实发工资", "工资卡号", "分公司代码" };
    public static final String[] threePart = { "序号", "员工姓名", "身份证号", "签署日期" };

}
