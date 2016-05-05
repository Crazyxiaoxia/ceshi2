package com.haili.site.controller.sys;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.apache.log4j.Logger;

import com.haili.site.common.BaseController;
import com.haili.site.contants.ConstantInit;
import com.haili.site.model.User;
import com.haili.site.service.UserService;
import com.haili.site.tools.ToolString;

/**
 * 用户管理
 */
public class UserController extends BaseController {

    @SuppressWarnings("unused")
    private static Logger log = Logger.getLogger(UserController.class);

    /**
     * 默认列表
     */
    public void index() {
        User user = getCUser();
        if (!ToolString.isEmpty(user.getStr("isSystem"))) {
            paging(ConstantInit.db_dataSource_main, splitPage, User.sqlId_splitPageSelect, User.sqlId_splitPageFrom);
        } else {
            paging(ConstantInit.db_dataSource_main, splitPage, User.sqlId_splitPageSelect, User.sqlId_splitPageFroms);
        }
        setAttr("userMessage", user);
        render("/tkamcbbc/user/list.html");
    }

    /**
     * 保存新增用户
     */
    public void save() {
        String password = getPara("password");
        User user = getModel(User.class);

        UserService.service.save(user, password);
        redirect("/tkamcbbc/user");
    }

    /**
     * 准备更新
     */
    public void edit() {
        User user = User.dao.findById(getPara("ids"));
        this.getRequest().setAttribute("user", user);
        render("/tkamcbbc/user/update.html");
    }

    /**
     * 更新
     */
    public void update() {
        String password = getPara("password");
        User user = getModel(User.class);

        UserService.service.update(user, password);
        redirect("/tkamcbbc/user");
    }

    //为用户设置角色
    public void setRole() {
        UserService.service.setRole(getPara("ids"), getPara("roleIds"));
        renderText(getPara("ids"));
    }

    /**
     * 删除
     */
    public void delete() {
        UserService.service.delete(getPara() == null ? ids : getPara());
        redirect("/tkamcbbc/user");
    }

    /**
     * 验证旧密码是否正确
     */
    public void valiPassWord() {

        String passWord = getPara("password");
        boolean bool = false;
        try {
            bool = UserService.service.valiPassWord(getCUser().getStr("userName"), passWord);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        renderText(String.valueOf(bool));
    }

    /**
     * 密码变更
     */
    public void passChange() {
        String userName = getCUser().getStr("userName");
        String passOld = getPara("passOld");
        String passNew = getPara("passNew");
        boolean flag = UserService.service.passChange(userName, passOld, passNew);
        renderText(String.valueOf(flag));
    }

    /**
     * 验证超级hr
     */
    public void valiSuper() {
        String userIds = getPara("userIds");
        String superHr = getPara("superHr");
        boolean flag = true;
        if (!superHr.equals("0")) {
            flag = UserService.service.valiSuper(userIds, superHr);
        }

        renderText(String.valueOf(flag));
    }

}
