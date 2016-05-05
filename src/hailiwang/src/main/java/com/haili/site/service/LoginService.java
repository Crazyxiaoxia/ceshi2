package com.haili.site.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.haili.site.common.BaseService;
import com.haili.site.model.User;
import com.haili.site.tools.ToolPbkdf2;
import com.haili.site.tools.ToolString;
import com.jfinal.aop.Enhancer;

public class LoginService extends BaseService {

    @SuppressWarnings("unused")
    private static Logger log = Logger.getLogger(LoginService.class);

    public static final LoginService service = Enhancer.enhance(LoginService.class);

    /**
     * 验证账号是否存在
     * @param userIds
     * @param userName
     * @return
     * 描述：新增用户时userIds为空，修改用户时userIds传值
     */
    public boolean valiUserName(String userIds, String userName) {
        List<User> list = new ArrayList<User>();
        if (ToolString.isEmpty(userIds)) {
            list = User.dao.find("select * from sys_user where userName=?", userName);
        } else {
            list = User.dao.find("select *  from  sys_user where userId=?", userIds);
        }

        int size = list.size();
        if (size == 0) {
            return true;
        }
        if (size == 1) {
            User user = list.get(0);
            if (userIds != null && String.valueOf(user.getInt("userId")).equals(userIds)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 用户登录后台验证
     * @param request
     * @param response
     * @param userName	账号
     * @param passWord	密码
     * @param autoLogin	是否自动登录
     * @return
     */
    public int login(String userName, String passWord) {
        // 1.取用户
        User user = null;
        List<User> userList = User.dao.find("select *  from sys_user where userName=? ", userName);
        if (userList.size() != 1) {
            return 1;// 用户不存在
        }
        user = userList.get(0);

        // 4.验证密码
        byte[] salt = user.getBytes("salt");// 密码盐
        byte[] encryptedPassword = user.getBytes("password");
        boolean bool = false;
        bool = ToolPbkdf2.authenticate(passWord, encryptedPassword, salt);

        if (bool) {
            // 密码验证成功
            // AuthInterceptor.setCurrentUser(request, response, user);// 设置登录账户
            return 3;
        } else {
            return 4;
        }
    }

}
