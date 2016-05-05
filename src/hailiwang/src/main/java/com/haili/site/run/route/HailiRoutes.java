/*
 * FileName: HailiRoutes.java
 * 北京神州新桥科技有限公司
 * Copyright 2014-2015 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.haili.site.run.route;

import com.haili.site.controller.MainController;
import com.jfinal.config.Routes;

/**
 * <p>
 * Description: 
 * </p>
 *
 * @author tigq
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2016年4月30日 上午8:41:02          tigq        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
public class HailiRoutes extends Routes {

    /* (non-Javadoc)
     * @see com.jfinal.config.Routes#config()
     */
    @Override
    public void config() {
        // TODO Auto-generated method stub
        add("/haili", MainController.class);
    }

}
