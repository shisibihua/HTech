package com.honghe.user.cloud;


import com.honghe.user.dao.UserDao;

import java.util.*;

/**
 * 云用户导入器
 * Created by zhanghongbin on 2016/12/29.
 */
public final class CloudUserImporter {

    private static final String STUDENT = "18";
    private static final String PARENT = "19";
    private static final String TYPE_TEACHER = "1";
    private static final String TYPE_STUDENT = "2";
    private static final String TYPE_PARENT = "3";

    private static String URL = "http://www.hitecloud.net/api";

    private CloudUserImporter() {

    }


    public static void importUser() {
        List<Map<String, String>> userInfoList = UserDao.INSTANCE.find();
        System.out.println(userInfoList + "@@@@@@@@@@@@@@@@@@@@@@");
        importUserToCloud(userInfoList);
    }

    private static void importUserToCloud(List<Map<String, String>> userInfoList) {
        for (Map<String, String> userInfo : userInfoList) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {

            }
            boolean flag = importUser(userInfo);
            if (flag) {
                UserDao.INSTANCE.update(userInfo.get("userId"));
            }
        }
    }

    private static boolean importUser(Map<String, String> userInfo) {
        String url = URL+"/loginapp/reginner";
        Map<String, String> params = new HashMap<>();
        String userName = "";
        if (!"".equals(userInfo.get("userMobile"))) {
            userName = userInfo.get("userMobile");
        }

        if ("".equals(userName)) {
            if (!"".equals(userInfo.get("userEmail"))) {
                userName = userInfo.get("userEmail");
            }

        }
        if ("".equals(userName)) {
            return false;
        }
        params.put("username", userName);
        params.put("salt", userInfo.get("userSalt"));
        params.put("password", userInfo.get("userPwd"));
        params.put("usertype", "1");
        params.put("realname", userInfo.get("userRealName"));
        params.put("nickname", "");
        params.put("subject", "");
        params.put("grade", "");
        params.put("schoolname", "");
        return false;

    }
}
