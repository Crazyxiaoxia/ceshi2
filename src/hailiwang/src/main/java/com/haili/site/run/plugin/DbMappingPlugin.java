package com.haili.site.run.plugin;

import org.apache.log4j.Logger;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import com.haili.site.contants.ConstantInit;
import com.haili.site.model.Dict;
import com.haili.site.model.DictValue;
import com.haili.site.model.Menu;
import com.haili.site.model.Role;
import com.haili.site.model.User;
import com.jfinal.config.Plugins;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;

/**
 * 
 * <p>
 * Description: 配置数据库，表映射
 * </p>
 *
 * @author tigq
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2016年4月3日 上午10:42:54          tigq        1.0         To create
 * </p>
 *
 * @since 
 * @see
 */
public class DbMappingPlugin {

    private static Logger log = Logger.getLogger(DbMappingPlugin.class);

    /**
     * 配置数据库连接、表映射
     * @param plugins 数据库插件
     */
    public DbMappingPlugin(Plugins plugins) {
        log.info(" 配置Druid数据库连接池连接属性");

        String jdbcUrl = PropKit.get(ConstantInit.db_connection_mysql_jdbcUrl);
        String user = PropKit.get(ConstantInit.db_connection_mysql_userName);
        String password = PropKit.get(ConstantInit.db_connection_mysql_passWord);

        //映射关系
        DruidPlugin druidPlugin = new DruidPlugin(jdbcUrl, user, password);

        log.info("配置Druid数据库连接池大小");
        druidPlugin.set(PropKit.getInt(ConstantInit.db_initialSize), PropKit.getInt(ConstantInit.db_minIdle), PropKit.getInt(ConstantInit.db_maxActive));

        log.info("配置Druid数据库连接池过滤器配制");
        druidPlugin.addFilter(new StatFilter());
        WallFilter wall = new WallFilter();
        wall.setDbType(PropKit.get("mysql"));
        WallConfig config = new WallConfig();
        config.setFunctionCheck(false); // 支持数据库函数
        wall.setConfig(config);
        druidPlugin.addFilter(wall);

        log.info("配置ActiveRecordPlugin插件");
        String configName = ConstantInit.db_dataSource_main;
        ActiveRecordPlugin arp = new ActiveRecordPlugin(configName, druidPlugin);
        //arp.setTransactionLevel(4);//事务隔离级别
        boolean devMode = Boolean.parseBoolean(PropKit.get(ConstantInit.config_devMode));
        arp.setDevMode(devMode); // 设置开发模式
        arp.setShowSql(devMode); // 是否显示SQL
        arp.setContainerFactory(new CaseInsensitiveContainerFactory(true));// 大小写不敏感

        log.info("configPlugin 添加druidPlugin插件");
        plugins.add(druidPlugin); // 多数据源继续添加
        log.info("configPlugin 表手工注册");
        arp.setDialect(new MysqlDialect());
        arp.addMapping("sys_role", Role.class);
        arp.addMapping("sys_menu", Menu.class);
        arp.addMapping("sys_user", User.class);
        arp.addMapping("sys_dict", "dict_id", Dict.class);
        arp.addMapping("sys_dictdetail", "detail_id", DictValue.class);

        log.info(" 注册ActiveRecordPlugin插件");
        plugins.add(arp);
    }

}
