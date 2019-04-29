package com.honghe.web.device.util;

import com.honghe.service.client.Result;
import com.honghe.service.client.http.HttpServiceClient;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Created by lyx on 2016-09-26.
 * <p>
 * 请求服务类的工厂，用于创建对应的服务的连接
 */
public class HttpServiceUtil {

    static Logger logger = Logger.getLogger(HttpServiceUtil.class);
    //用户服务标识
    public final static String SERVICE_USER = "user";
    //目录服务标识
    public final static String SERVICE_AD = "ad";
    //设备服务标识
    public final static String SERVICE_DEVICE = "device";
    //地点服务标识
    public final static String SERVICE_AREA = "area";

    private static String IP = "localhost";
    private static int PORT = 8770;

    static ResourceBundle resourceBundle = null;
    private static String classpath = "";
    static {
        try {
            //读取设备对应远程连接服务配置文件
            classpath = HttpServiceUtil.class.getResource("/").getPath();
            resourceBundle = ResourceBundle.getBundle("serviceConfig");
            if (resourceBundle.containsKey("ip") && resourceBundle.containsKey("port")) {
                IP = resourceBundle.getString("ip");
                PORT = Integer.parseInt(resourceBundle.getString("port"));
            }
        } catch (Exception e) {
            logger.debug("读取远程连接文件错误", e);
        }
    }

    private HttpServiceUtil() {
    }


    /**
     * 获取连接
     *
     * @return
     */
    private static HttpServiceClient getServiceClient() {
        HttpServiceClient httpServiceClient = new HttpServiceClient(IP, PORT);
        return httpServiceClient;
    }

    /**
     * 用户服务连接调用
     *
     * @param method 方法名
     * @param params 参数
     * @return
     */
    public static Result userService(String method, Map<String, String> params) throws Exception {
        Result re_value = getServiceClient().execute(SERVICE_USER, method, params);
        throwExceptionMessage(re_value.getCode());
        return re_value;
    }

    /**
     * 目录服务连接调用
     *
     * @param method 方法名
     * @param params 参数
     * @return
     */
    public static Result adService(String method, Map<String, String> params) throws Exception {
        Result re_value = getServiceClient().execute(SERVICE_AD, method, params);
        throwExceptionMessage(re_value.getCode());
        return re_value;
    }

    /**
     * 设备服务连接调用
     *
     * @param method 方法名
     * @param params 参数
     * @return
     */
    public static Result deviceService(String method, Map<String, String> params) throws Exception {
        String serviceFlag = getServiceConnect();
        Result re_value = getServiceClient().execute(serviceFlag, method, params);
        throwExceptionMessage(re_value.getCode());
        return re_value;
    }

    /**
     * 地点服务连接调用
     *
     * @param method 方法名
     * @param params 参数
     * @return
     */
    public static Result areaService(String method, Map<String, String> params) throws Exception {
        Result re_value = getServiceClient().execute(SERVICE_AREA, method, params);
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

    /**
     * 获取设备连接服务的url
     * @return
     */
    private static String getServiceConnect(){
        String serviceFlag = "device";
        Properties p = new Properties();
        try {
            FileReader fr = new FileReader(classpath+"deviceServiceRequest.properties");
            if (fr!=null){
                p.load(fr);
                fr.close();
                serviceFlag = p.getProperty("request_name");
            }
        } catch (FileNotFoundException e) {
            logger.error("配置文件未找到",e);
        } catch (IOException e) {
            logger.error("流读取异常",e);
        }
        return serviceFlag;
    }
}
