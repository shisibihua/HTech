package com.honghe.user.controller;

import com.honghe.exception.ParamException;
import com.honghe.user.dao.UserDao;
import com.honghe.user.dao.impl.UserV2UserDao;

import java.util.Map;

/**
 * Created by zhaowj on 2017/03/13.
 */
public class UserV2Controller {

    private UserV2UserDao userV2Dao = UserV2UserDao.INSTANCE;
    private UserDao userDao = UserDao.INSTANCE;

    /**
     * 根据机构id获取所属机构管理员信息
     * @param agencyId
     * @return
     * @throws Exception
     */
    public Map<String, String> userAdminSearchByAgency(String agencyId) throws Exception {
        if (agencyId == null || "".equals(agencyId)) {
            throw new ParamException();
        }
        return userV2Dao.userAdminSearchByAgency(agencyId);
    }

    /**
     * 用户登陆验证
     *
     * @param loginName 用户名称
     * @param userName  用户名称
     * @param userPwd   用户密码
     * @return
     * @throws Exception
     */
    public Map<String, String> userCheck(String loginName, String userName, String userPwd) throws Exception {
        String name = "";
        if (loginName != null) {
            name = loginName;
        }
        if ("".equals(name) && userName != null) {
            name = userName;
        }
        if ("".equals(name) || userPwd == null || "".equals(userPwd)) {
            throw new ParamException();
        }
        return userV2Dao.find(name, userPwd);
    }


    /**
     * 查询用户信息
     *
     * @param userId     用户id
     * @param userName   用户名称
     * @param loginName  用户名称
     * @param userMobile 用户手机
     * @param userEmail  用户电子邮箱
     * @param userNum    用户编号
     * @return
     * @throws Exception
     */
    public Object userSearch(String userId, String userName, String loginName, String userMobile,
                             String userEmail, String userNum, String realName) throws Exception {
        if (userId != null && !"".equals(userId)) {
            String[] userIdArray = userId.split(",");
            if (userIdArray.length == 1) {
                return userV2Dao.find(Integer.parseInt(userIdArray[0]));
            } else {
                return userDao.find(userIdArray);
            }
        } else if (userName != null && !"".equals(userName)) {
            return userDao.findByName(userName);
        } else if (loginName != null && !"".equals(loginName)) {
            return userDao.findByLoginName(loginName);
        } else if (userMobile != null && !"".equals(userMobile)) {
            return userDao.findByUserMobile(userMobile);
        } else if (userEmail != null && !"".equals(userEmail)) {
            return userDao.findByUserEmail(userEmail);
        } else if (userNum != null && !"".equals(userNum)) {
            return userDao.findByUserNum(userNum);
        } else if (realName != null && !"".equals(realName)) {
            return userDao.findByRealName(realName);
        } else {
            throw new ParamException();
        }

    }
}
