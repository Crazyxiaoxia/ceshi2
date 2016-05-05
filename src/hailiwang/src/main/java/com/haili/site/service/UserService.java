package com.haili.site.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.haili.site.common.BaseService;
import com.haili.site.model.User;
import com.haili.site.tools.ToolPbkdf2;
import com.haili.site.tools.ToolString;
import com.jfinal.aop.Enhancer;

public class UserService extends BaseService {

    public static final UserService service = Enhancer.enhance(UserService.class);

    /**
     * 新增用户信息保存
     * @param user
     * @param passWord
     * 
     */
    public boolean save(User user, String password) {
        boolean flag = false;
        // 密码加密
        byte[] salt = ToolPbkdf2.generateSalt();// 密码盐
        byte[] encryptedPassword = ToolPbkdf2.getEncryptedPassword(password, salt);
        int pk = 1;
        User users = User.dao.findFirst("select max(userId) userId from sys_user ");
        if (users != null && null != users.getInt("userId")) {
            pk = users.getInt("userId") + 1;
        }
        user.set("userId", pk);
        user.set("salt", salt);
        user.set("password", encryptedPassword);
        if (user.getStr("superHr").equals("1")) {
            user.set("roleIds", "1,");
        } else {
            user.set("roleIds", "2,");
        }
        user.set("createDate", new Date());
        flag = user.save();

        return flag;
    }

    /**
     * 更新用户信息
     * @param user
     * @param passWord
     * @param userInfo
     */
    public boolean update(User user, String password) {
        boolean flag = false;
        // 密码加密
        if (null != password && !password.trim().equals("")) {
            byte[] salt = ToolPbkdf2.generateSalt();// 密码盐
            byte[] encryptedPassword = ToolPbkdf2.getEncryptedPassword(password, salt);
            user.set("salt", salt);
            user.set("password", encryptedPassword);
            // 更新用户
            flag = user.update();
        }

        return flag;
    }

    /**
     * 删除用户信息
     * @param ids
     */
    public boolean delete(String ids) {
        boolean flag = false;
        String[] idsArr = splitByComma(ids);
        for (String userIds : idsArr) {
            // 删除
            flag = User.dao.deleteById(userIds);

        }
        return flag;

    }

    /**
     * 设置角色
     * 
     * @param userIds
     * @param groupIds
     */
    public boolean setRole(String userIds, String roleIds) {
        boolean flag = false;
        if (ToolString.isEmpty(userIds)) {
            flag = false;
            return flag;
        }
        User user = User.dao.findById(userIds);
        user.set("roleIds", roleIds);
        flag = user.update();
        return flag;
    }

    /**
     * 验证密码是否正确
     * @param userName
     * @param passWord
     * @return
     * @throws InvalidKeySpecException 
     * @throws NoSuchAlgorithmException 
     */
    public boolean valiPassWord(String userName, String passWord) throws NoSuchAlgorithmException, InvalidKeySpecException {

        User user = User.dao.findFirst("select *  from  sys_user where userName=?", userName);
        if (user != null) {
            byte[] salt = user.getBytes("salt");// 密码盐
            byte[] encryptedPassword = user.getBytes("password");
            boolean bool;
            bool = ToolPbkdf2.authenticate(passWord, encryptedPassword, salt);
            return bool;

        } else {
            return false;
        }

    }

    /**
     * 密码变更
     * @param userName
     * @param passOld
     * @param passNew
     */
    public boolean passChange(String userName, String passOld, String passNew) {
        boolean bool = false;
        User user = User.dao.findFirst("select *  from sys_user where userName=?", userName);

        // 验证密码
        byte[] salt = user.getBytes("salt");// 密码盐
        byte[] encryptedPassword = user.getBytes("password");

        bool = ToolPbkdf2.authenticate(passOld, encryptedPassword, salt);
        if (bool) {
            byte[] saltNew = ToolPbkdf2.generateSalt();// 密码盐
            byte[] encryptedPasswordNew = ToolPbkdf2.getEncryptedPassword(passNew, saltNew);
            user.set("salt", saltNew);
            user.set("password", encryptedPasswordNew);
            // 更新用户
            user.update();

        }

        return bool;
    }

    /**
     * 验证是否是超级hr
     * @param userIds
     * @param superHr
     * 
     */
    public boolean valiSuper(String userIds, String superHr) {
        List<User> list = new ArrayList<User>();
        list = User.dao.find("select *  from sys_user where  superHr=?", "1");
        int size = list.size();
        if (size == 0) {
            return true;
        }
        if (size == 1) {
            User user = list.get(0);
            if (!ToolString.isEmpty(userIds)) {
                if (userIds != null && String.valueOf(user.getInt("userId")).equals(userIds)) {
                    return true;
                }
            }

        }
        if (size > 1) {
            return false;
        }
        return false;
    }
}
