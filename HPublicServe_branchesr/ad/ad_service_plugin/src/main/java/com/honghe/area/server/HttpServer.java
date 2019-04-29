package com.honghe.area.server;

import com.honghe.service.proxy.Result;
import com.honghe.service.proxy.ServiceProxyFactory;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * 各个服务调用
 * @author xinye
 * @date 2017/03/09
 */
public class HttpServer {

    private static Logger logger = Logger.getLogger(HttpServer.class);

    /**
     * 用户服务标识
     */
    public final static String SERVICE_USER = "user";
    /**
     * 目录服务标识
     */
    public final static String SERVICE_AD = "ad";
    /**
     * 设备服务标识
     */
    public final static String SERVICE_DEVICE = "device";

    private static String IP = "localhost";
    private static int PORT = 7070;
    private static final int FAILED = -2;

    private HttpServer() {
    }
    /**
     * 用户服务连接调用
     *
     * @param method 方法名
     * @param params 参数
     * @return
     */
    public static Result userService(String method, Map<String, String> params) throws Exception {
        params.put("cmd",SERVICE_USER);
        params.put("cmd_op",method);
        Result reValue = ServiceProxyFactory.newInstance(ServiceProxyFactory.Type.HTTP).send(params);
        throwExceptionMessage(reValue.getCode());
        return reValue;
    }

    /**
     * 目录服务连接调用
     *
     * @param method 方法名
     * @param params 参数
     * @return
     */
    public static Result adService(String method, Map<String, String> params) throws Exception {
        params.put("cmd",SERVICE_AD);
        params.put("cmd_op",method);
        Result reValue = ServiceProxyFactory.newInstance(ServiceProxyFactory.Type.HTTP).send(params);
        throwExceptionMessage(reValue.getCode());
        return reValue;
    }

    /**
     * 设备服务连接调用
     *
     * @param method 方法名
     * @param params 参数
     * @return
     */
    public static Result deviceService(String method, Map<String, String> params) throws Exception {
        params.put("cmd",SERVICE_USER);
        params.put("cmd_op",SERVICE_DEVICE);
        Result reValue = ServiceProxyFactory.newInstance(ServiceProxyFactory.Type.HTTP).send(params);
        throwExceptionMessage(reValue.getCode());
        return reValue;
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
        String msg;
        if (code == -1) {
            msg = "参数错误";
        } else if (code == FAILED) {
            msg = "没有此方法";
        } else {
            msg = "未知错误";
        }
        return msg;
    }
}
