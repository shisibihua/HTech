package com.honghe.deviceNew.util;

import com.honghe.client.util.Util;
import com.honghe.net.OKHttpUtil;
import com.honghe.service.proxy.Result;
import com.honghe.service.proxy.ServiceProxy;
import com.honghe.service.proxy.ServiceProxyFactory;
import okhttp3.*;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;


public class DeviceConfigUtil {

    static Logger logger = Logger.getLogger("device");

    private static String SERVICE_AUTHORITY = "authority";

    private DeviceConfigUtil() {

    }

    public static String get(String name) {
        return Util.get(name);
    }


    /**
     * 获取连接
     *
     * @return
     */
    private static ServiceProxy getServiceClient() {
        return ServiceProxyFactory.newInstance(ServiceProxyFactory.Type.HTTP);
    }

    /**
     * 授权服务连接调用
     *
     * @param method 方法名
     * @param params 参数
     * @return
     */
    public static Result authorityService(String method, Map<String, String> params) throws Exception {
        params.put("cmd", SERVICE_AUTHORITY);
        params.put("cmd_op", method);
        Result re_value = getServiceClient().send(params);
        throwExceptionMessage(re_value.getCode());
        return re_value;
    }

    /**
     * 抛出连接远程调用异常
     *
     * @param code 连接调用状态返回值
     * @throws Exception
     */
    private static void throwExceptionMessage(int code) throws Exception {
        if (code != 0) {
            logger.debug(parseErrorCode(code));
            throw new Exception("response fault:" + parseErrorCode(code));
        }
    }

    /**
     * 返回值错误解析
     *
     * @param code
     * @return
     */
    private static String parseErrorCode(int code) {
        String msg = "";
        if (code == -1) {
            msg = "参数错误";
        } else if (code == -2) {
            msg = "没有此方法";
        } else {
            msg = "未知错误";
        }
        return msg;
    }


}
