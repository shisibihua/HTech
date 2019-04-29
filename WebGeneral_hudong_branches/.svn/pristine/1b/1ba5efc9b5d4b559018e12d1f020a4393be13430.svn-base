package com.honghe.tech.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * Created by caoqian on 2018/5/23.
 */
public class HttpRequestUtil {
    private static Logger logger=Logger.getLogger(HttpRequestUtil.class);
    //服务端口号
    private static final String PORT= "8756";
    /**
     * post请求
     * @param url        url地址
     * @param query      参数
     * @return
     * @throws Exception
     */
    public static String load(String url,String query) throws IOException {
        String resultStr = "";
        PrintStream ps=null;
        BufferedReader bReader =null;
        try {
            URL restURL = new URL(url);
            /*
             * 此处的urlConnection对象实际上是根据URL的请求协议(此处是http)生成的URLConnection类 的子类HttpURLConnection
            */
            HttpURLConnection conn = (HttpURLConnection) restURL.openConnection();
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            //设置超时时间
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            //请求方式
            conn.setRequestMethod("POST");
            //设置是否从httpUrlConnection读入，默认情况下是true;
            conn.setDoOutput(true);
            //allowUserInteraction 如果为 true，则在允许用户交互（例如弹出一个验证对话框）的上下文中对此 URL 进行检查。
            conn.setAllowUserInteraction(false);

            ps = new PrintStream(conn.getOutputStream());
            ps.write(query.getBytes());

            bReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line="";

            while (null != (line = bReader.readLine())) {
                resultStr += line;
            }
        }catch (Exception e){
            resultStr="";
            logger.error("调用接口异常，url="+url+",params="+query);
        }finally {
            ps.close();
            bReader.close();
        }
        return resultStr;
    }

    /**
     * 获取互动服务远程地址
     * @return
     */
    public static String getServerUrlPath(){
        //服务ip
        String ip=ConfigUtil.getConfig("htech_ip").toString().trim();
        //服务端口号
        String port=ConfigUtil.getConfig("htech_port").toString().trim();
        if(StringUtils.isEmpty(ip)){
            try {
                ip= InetAddress.getLocalHost().getHostAddress().toString();
            } catch (UnknownHostException e) {
                logger.error("getServerUrlPath------获取服务器ip异常。",e);
            }
        }
        if(StringUtils.isEmpty(port)){
            port=PORT;
        }
        return "http://"+ip+":"+port+"/service/";
    }
    public static void main(String []args) {try {
                String url="http://218.249.67.190:8088/Api/v2/IndexAction.php?act=AddMeeting";
                String params="{\"model\":\"MODEL_A\",\"starttime\":1527926400,\"endtime\":1527929100,\"master\":\"1234021\",\"terminals\":[1234021,1234022],\"key\":\"3950214E74D44586357E99CC8F8C6D81\",\"name\":\"语文 1527919345612\"}";
                String resultString = load(url,params);
                System.out.println(resultString);
         } catch (Exception e) {

                System.out.print(e.getMessage());

        }

    }
}


