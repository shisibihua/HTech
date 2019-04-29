package com.honghe.web.user.util;

import com.honghe.service.client.Result;
import com.honghe.service.client.http.HttpServiceClient;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.apache.log4j.Logger;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
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
    // 地点服务标识
    public final static String SERVICE_AREA = "area";

    private static String IP = "localhost";
    private static int PORT = 8770;

    private static String STORAGE_IP = "localhost";

    private static int STORAGE_PORT = 8781;

    private static String UPLOAD_IP = "localhost";
    //    private static final String IP = "192.168.17.121";
    private static int UPLOAD_PORT = 8770;

    static ResourceBundle resourceBundle = null;

    static {
        try {
            InetAddress netAddress = getInetAddress();
            STORAGE_IP = getHostIp(netAddress);
            //读取设备对应远程连接服务配置文件
            resourceBundle = ResourceBundle.getBundle("serviceConfig");
            if (resourceBundle.containsKey("ip") && resourceBundle.containsKey("port")) {
                IP = resourceBundle.getString("ip");
                PORT = Integer.parseInt(resourceBundle.getString("port"));
            }
            if (resourceBundle.containsKey("upload_ip") && resourceBundle.containsKey("upload_port")) {
                UPLOAD_IP = resourceBundle.getString("upload_ip");
                UPLOAD_PORT = Integer.parseInt(resourceBundle.getString("upload_port"));
            }
            if (resourceBundle.containsKey("storage_ip") && resourceBundle.containsKey("storage_port")) {
                STORAGE_IP = resourceBundle.getString("storage_ip");
                STORAGE_PORT = Integer.parseInt(resourceBundle.getString("storage_port"));
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
     * 设备服务连接调用
     *
     * @param method 方法名
     * @param params 参数
     * @return
     */
    public static Result deviceService(String method, Map<String, String> params) throws Exception {
        Result re_value = getServiceClient().execute(SERVICE_DEVICE, method, params);
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

    public static final String upload(String uploadUrl, File file) {
        HttpRequest httpRequest = HttpRequest.post(uploadUrl).form("file", file);

        try {
            httpRequest.charset("UTF-8");
            HttpResponse e = httpRequest.send();
            e.charset("UTF-8");
            String result = e.bodyText().trim();
            return result;
        } catch (Exception var5) {
            var5.printStackTrace();
            return "";
        }
    }
    /**
     * 上传文件到服务器
     *
     * @param file
     * @return String 上传到服务器上的文件名
     */
    public static String uploadFile(File file){
        String ret = upload("http://" + UPLOAD_IP + ":" + UPLOAD_PORT + "/service/cloud/httpUploadService", file);
        return ret;
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

    public static String getStorageIp() {
        return STORAGE_IP;
    }

    public static int getStoragePort() {
        return STORAGE_PORT;
    }

    /**
     * 获取本机InetAddress
     * @return
     */
    public static InetAddress getInetAddress(){
        try{
            try {
                return InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }catch(Exception e){
            logger.debug("unknown host!");
        }
        return null;
    }

    /**
     * 获取本机ip
     * @return
     */
    public static String getHostIp(InetAddress netAddress){
        if(null == netAddress){
            return null;
        }
        String ip = netAddress.getHostAddress(); //get the ip address
        return ip;
    }

    public static void main(String[] args) {
        HttpServiceUtil httpServiceUtil=new HttpServiceUtil();
        ResourceBundle re = ResourceBundle.getBundle("serviceConfig");
        if (re.containsKey("ip") && re.containsKey("port")) {
            String ip = re.getString("ip");
            int port = Integer.parseInt(re.getString("port"));
            System.out.println(ip+">>>>>>"+port);
        }
    }
}
