package com.honghe.web.user.util;

import com.honghe.service.client.Result;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class UserCommonUtil {
    static Logger logger = Logger.getLogger(UserCommonUtil.class);
    /**
     * 通过用户id获取用户信息
     * @param userId 用户id
     * @return
     */
     public static Map<String,String> getUserInfoById(String userId){
        Map<String,String> userInfo = new HashMap<>();
        Map<String,String> params = new HashMap<>();
        Result result = new Result("",0,"");
        params.put("userId",userId);
        try {
            result = HttpServiceUtil.userService("userGetIsAdmin",params);
            if (result.getValue()!=null){
                userInfo = (Map<String, String>) result.getValue();
            }
        } catch (Exception e) {
            logger.error("获取用户信息失败",e);
        }
        return userInfo;
    }
}
