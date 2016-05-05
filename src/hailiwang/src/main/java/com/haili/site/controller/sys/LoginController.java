package com.haili.site.controller.sys;

import org.apache.log4j.Logger;

import com.haili.site.common.BaseController;
import com.haili.site.contants.ConstantWebContext;
import com.haili.site.model.User;
import com.haili.site.service.LoginService;
import com.haili.site.tools.ToolWeb;

/**
 * 登陆处理
 */
public class LoginController extends BaseController {

    @SuppressWarnings("unused")
    private static Logger log = Logger.getLogger(LoginController.class);

    /**
     * 准备登陆
     */
    public void index() {
        User user = getCUser(); // cookie认证自动登陆处理
        if (null != user) {//后台
            redirect("/tkamcbbc/");
        } else {
            render("/tkamcbbc/login/login.html");
        }
    }

    /**
     * 验证账号是否可用
     */
    public void valiUserName() {
        String userIds = getRequest().getParameter("userIds");
        userIds = getPara("userIds");
        String userName = getRequest().getParameter("userName");
        boolean bool = LoginService.service.valiUserName(userIds, userName);
        renderText(String.valueOf(bool));
    }

    /**
     * 登陆验证
     */
    public void vali() {
        String username = getPara("username");
        String password = getPara("password");
        int result = LoginService.service.login(username, password);
        if (result == 3) {
            User user = User.dao.findFirst("select *  from  sys_user where userName=?", username);
            this.getRequest().getSession().setAttribute("user", user);
            redirect("/tkamcbbc/index");
            return;
        }
        if (password.length() > 5 && username.length() > 1) {
            setAttr("error", "1");
        }
        render("/tkamcbbc/login/login.html");
    }

    /**
     * 注销
     */
    public void logout() {
        ToolWeb.addCookie(getResponse(), "", "/", true, ConstantWebContext.cookie_authmark, null, 0);
        this.getRequest().getSession().removeAttribute("user");
        redirect(this.getCxt() + "/tkamcbbc/login");
    }

    /**
     * 测试抽象公共方法是否有异常
     */
    public void test() {
        this.addToParamMap("a", "1");
        this.getParamMap();
        this.toUrl();
        this.getRequestContent();
        renderText("success");
    }

}
