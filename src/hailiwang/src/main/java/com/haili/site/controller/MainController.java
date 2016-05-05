/*
 * FileName: MainController.java
 * 北京神州新桥科技有限公司
 * Copyright 2014-2015 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.haili.site.controller;

import com.haili.site.common.BaseController;

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
 * 2016年5月1日 上午9:22:19          tigq        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
public class MainController extends BaseController {

    /**
     * 首页
     */
    public void index() {
        render("/main/index.html");
    }

    /**
     * 渔船查询列表
     */
    public void shipList() {
        render("/main/shipList.html");
    }

    /**
     * 渔船详情
    */
    public void shipDetail() {
        render("/main/shipdetail.html");
    }

    /**
     *租船用户详情
    */
    public void userShare() {
        render("/main/index.html");
    }
}
