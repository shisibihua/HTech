package com.honghe.util;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 网络连接类
 *
 * @author yanxue
 */
public class ConnNet {
    public static String URLVAR = "http://localhost:58890";// IP地址，也可能会变动
//       public static String URLVAR = "http://192.168.16.113:58890";// IP地址，也可能会变动
//   public static String URLVAR = "http://192.168.21.5:58890";// IP地址，也可能会变动

    private static final String PATH = ""; // 项目名，可能会变动

    //private static HttpClient httpclient = null;
    private static ConnNet intance = null;

    private ConnNet() {

    }

    public static ConnNet getIntance() {

        if (intance == null) {
            intance = new ConnNet();
        }

        return intance;
    }

    // 将路径定义为一个常量，修改的时候也好更改
    // 通过url获取网络连接 connection
    public HttpURLConnection getConn(String urlpath) {
        String finalurl = URLVAR + urlpath;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(finalurl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true); // 允许输入流
            connection.setDoOutput(true); // 允许输出流
            connection.setUseCaches(false); // 不允许使用缓存
            connection.setRequestMethod("POST"); // 请求方式
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return connection;

    }

    /**
     * 获得HttpPost
     *
     * @param action
     * @return
     */
    /*public HttpPost gethttpPost(String action) {
        HttpPost httpPost = new HttpPost(URLVAR + PATH + action);
//		System.out.println("URLVAR+uripath:" + (URLVAR + PATH + action));

        return httpPost;
    }*/


    /**
     * 获得HttpGet
     *
     * @param action
     * @return
     */
    /*public HttpGet gethttpGet(String action) {
        HttpGet httpGet = new HttpGet();

        try {
            httpGet.setURI(new URI(URLVAR + PATH + action));
//            System.out.println("URLVAR+uripath:" + (new URI(URLVAR + PATH + action)).toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return httpGet;
    }*/


    /**
     * 获取 HttpClient
     *
     * @return
     */
    /*public HttpClient getHttpClient() {
        if (httpclient == null) {
            httpclient = new DefaultHttpClient();
        }
        return httpclient;
    }*/

    /**
     * 断开与服务器的连接
     */
    /*public void clearHttpClient() {
        if (httpclient != null) {
            httpclient = null;
        }
    }*/

}
