package com.honghe.user.util;

import com.honghe.exception.ParamException;

/**
 * Created by mz on 2018/2/9.
 */
public class ParamUtil {

    private ParamUtil() {}

    public static void checkParam(String...params) throws ParamException {
        String method = Thread.currentThread().getStackTrace()[2].getMethodName();
        if (params == null || params.length == 0) {
            throw new ParamException("调用互动教学用户服务方法: " + method + ":输入参数异常");
        }

        for (String param : params) {
            if (param == null || "".equals(param)) {
                throw new ParamException("调用互动教学用户服务方法: " + method + ":输入参数异常");
            }
        }
    }

}
