package com.honghe.tech.util;

import com.honghe.service.proxy.Result;
import com.honghe.service.proxy.ServiceProxy;
import com.honghe.service.proxy.ServiceProxyFactory;
import com.honghe.tech.util.exceptionutil.HttpServerException;


import java.util.Map;


/**
 * Created by lyx on 2016-09-26.
 * <p>
 * 请求服务类的工厂，用于创建对应的服务的连接
 */
public class HttpServerUtil {

    private static HttpServerUtil httpServerUtil;

    private static String SERVER_AD = "ad";

    private static String SERVER_USER = "user";

    private static String SERVER_RESOURCE = "resource";

    private static String SERVER_CURRICULUM = "curriculum";

    private static String SERVER_DEVCONNTENT = "devconnect";

    private static String SERVER_DEVICE = "deviceConnectionCommand";

    private static String INTERACTIVE_TEACHING = "interactiveTeachingCommand";

    private static String SERVER_AREA = "area";

    private static String SERVER_NOTICE = "notify";

    private HttpServerUtil() {
    }

    /**
     * 获取初始化的httpServerUtil对象
     *
     * @return
     */
    public static HttpServerUtil getInstance() {
        if (httpServerUtil == null) {
            httpServerUtil = new HttpServerUtil();
        }

        return httpServerUtil;
    }

    /**
     * 用户服务连接调用
     *
     * @param method 方法名
     * @param params 参数
     * @return
     */
    public static Result userService(String method, Map<String, String> params) throws HttpServerException {
        ServiceProxy serviceProxy = ServiceProxyFactory.newInstance(ServiceProxyFactory.Type.HTTP);
        params.put("cmd",SERVER_USER);
        params.put("cmd_op",method);
        Result re_value = serviceProxy.send(params);
        throwExceptionMessage(SERVER_USER,method,params.toString(),re_value.getCode());
        return re_value;
    }

    /**
     * 目录服务连接调用
     *
     * @param method 方法名
     * @param params 参数
     * @return
     */
    public static Result adService(String method, Map<String, String> params) throws HttpServerException {
        ServiceProxy serviceProxy = ServiceProxyFactory.newInstance(ServiceProxyFactory.Type.HTTP);
        params.put("cmd",SERVER_AD);
        params.put("cmd_op",method);
        Result re_value = serviceProxy.send(params);
        throwExceptionMessage(SERVER_AD,method,params.toString(),re_value.getCode());
        return re_value;
    }

    /**
     * 资源服务连接调用
     *
     * @param method 方法名
     * @param params 参数
     * @return
     */
    public static Result resourceService(String method, Map<String, String> params) throws HttpServerException {
        ServiceProxy serviceProxy = ServiceProxyFactory.newInstance(ServiceProxyFactory.Type.HTTP);
        params.put("cmd",SERVER_RESOURCE);
        params.put("cmd_op",method);
        Result re_value = serviceProxy.send(params);
        throwExceptionMessage(SERVER_RESOURCE,method,params.toString(),re_value.getCode());
        return re_value;
    }

    /**
     * 课表服务连接调用
     *
     * @param method 方法名
     * @param params 参数
     * @return
     */
    public static Result curriculumService(String method, Map<String, String> params) throws HttpServerException {
        ServiceProxy serviceProxy = ServiceProxyFactory.newInstance(ServiceProxyFactory.Type.HTTP);
        params.put("cmd",SERVER_CURRICULUM);
        params.put("cmd_op",method);
        Result re_value = serviceProxy.send(params);
        throwExceptionMessage(SERVER_CURRICULUM,method,params.toString(),re_value.getCode());
        return re_value;
    }

    /**
     * 设备连接服务调用
     * @param params
     * @return
     */
    public static Result deviceConnectionService(String method, Map params) throws HttpServerException  {
        ServiceProxy serviceProxy = ServiceProxyFactory.newInstance(ServiceProxyFactory.Type.HTTP);
        params.put("cmd",SERVER_DEVCONNTENT);
        params.put("cmd_op",method);
        Result re_value = serviceProxy.send(params);
        throwExceptionMessage(SERVER_DEVCONNTENT,method,params.toString(),re_value.getCode());
        return re_value;
    }

    /**
     * 设备服务调用
     * @param params
     * @return
     */
    public static Result deviceService(String method, Map params) throws HttpServerException  {
        ServiceProxy serviceProxy = ServiceProxyFactory.newInstance(ServiceProxyFactory.Type.HTTP);
        params.put("cmd",SERVER_DEVICE);
        params.put("cmd_op",method);
        Result re_value = serviceProxy.send(params);
        throwExceptionMessage(SERVER_DEVICE,method,params.toString(),re_value.getCode());
        return re_value;
    }

    /**
     * 设备服务调用(互动教学)
     * @param params
     * @return
     */
    public static Result teachingService(String method, Map params) throws HttpServerException  {
        ServiceProxy serviceProxy = ServiceProxyFactory.newInstance(ServiceProxyFactory.Type.HTTP);
        params.put("cmd",INTERACTIVE_TEACHING);
        params.put("cmd_op",method);
        Result re_value = serviceProxy.send(params);
        throwExceptionMessage(INTERACTIVE_TEACHING,method,params.toString(),re_value.getCode());
        return re_value;
    }
    /**
     * 地点服务调用
     * @param params
     * @return
     */
    public static Result areaService(String method, Map params) throws HttpServerException  {
        ServiceProxy serviceProxy = ServiceProxyFactory.newInstance(ServiceProxyFactory.Type.HTTP);
        params.put("cmd",SERVER_AREA);
        params.put("cmd_op","area_"+method);
        Result re_value = serviceProxy.send(params);
        throwExceptionMessage(SERVER_AREA,method,params.toString(),re_value.getCode());
        return re_value;
    }
    /**
     * 抛出连接远程调用异常
     *
     * @param cmd    连接的cmd
     * @param cmd_op 连接的cmd_op
     * @param params 连接的参数
     * @param code   连接调用状态返回值
     * @throws Exception
     */
    private static void throwExceptionMessage(String cmd,String cmd_op,String params,int code){
        if (code != 0) {
            StringBuffer sb = new StringBuffer("");
            sb.append("\n"+"params:"+params+";");
            sb.append("\n"+"response fault:"+parseErrorCode(code)+";");
            throw new HttpServerException(cmd+"服务异常："+String.valueOf(sb));
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
        } else if (code == 404) {
            msg = "404";
        } else {
            msg = "未知错误";
        }
        return msg;
    }

    /**
     * 通知服务调用
     * @param params
     * @return
     */
    public static Result noticeService(String method, Map params) throws HttpServerException  {
        ServiceProxy serviceProxy = ServiceProxyFactory.newInstance(ServiceProxyFactory.Type.HTTP);
        params.put("cmd",SERVER_NOTICE);
        params.put("cmd_op",method);
        Result re_value = serviceProxy.send(params);
        throwExceptionMessage(SERVER_NOTICE,method,params.toString(),re_value.getCode());
        return re_value;
    }
}
