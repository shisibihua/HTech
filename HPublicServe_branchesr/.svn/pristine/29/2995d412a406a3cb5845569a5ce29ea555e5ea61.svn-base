package com.honghe.service.client.http;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.jsoup.Connection;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: 北京鸿合盛视数字媒体技术有限公司</p>
 *
 * @author zhanghongbin
 * @date 2016/4/22
 */
public final class HttpUtil {

    private HttpUtil() {

    }


    public static String post(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static byte[] getFileByte(String url) {
       return getFileByte(url,3000*3000);
    }

    public static byte[] getFileByte(String url,int timeout) {
        Connection connection = org.jsoup.Jsoup.connect(url).method(Connection.Method.GET).timeout(timeout).maxBodySize(100000 * 100000);
        try {
            Connection.Response response = connection.ignoreContentType(true).ignoreHttpErrors(true).execute();
            return response.bodyAsBytes();
        } catch (Exception e) {
            return null;
        }

    }

    public static String getFileContent(String url) {
        Connection connection = org.jsoup.Jsoup.connect(url).method(Connection.Method.GET).timeout(3000 * 3000).maxBodySize(10000 * 10000).followRedirects(true);
        try {
            Connection.Response response = connection.ignoreContentType(true).execute();
            return response.body().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public static String getFileContent(String url, String content) {
        try {
            URL _url = new URL(url);
            URLConnection con = _url.openConnection();
            con.setDoOutput(true);
            con.setRequestProperty("Pragma:", "no-cache");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "text/xml");
            OutputStreamWriter out = new OutputStreamWriter(con
                    .getOutputStream());
            out.write(content);
            out.flush();
            out.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(con
                    .getInputStream()));
            String line = "";
            StringBuilder sb = new StringBuilder();
            for (line = br.readLine(); line != null; line = br.readLine()) {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }


    public static final String upload(String uploadUrl, File file) {
        HttpRequest httpRequest = HttpRequest.post(uploadUrl)
                .form(
                        "file", file
                );
        try {
            httpRequest.charset("UTF-8");
            HttpResponse httpResponse = httpRequest.send();
            httpResponse.charset("UTF-8");
            String result = httpResponse.bodyText().trim();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }


    }

    public static void main(String[] args)throws Exception {
//        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
//                "<request>" +
//                "<cmd>event</cmd>" +
//                "<cmd_op>eventAdd</cmd_op>" +
//                "<code>10000005</code>" +
//                "<date>2015-12-01 21:3:21</date>" +
//                "<address>rtsp://192.168.16.333:8770/saab</address>" +
//                "<content><in>333</in><out>2</out></content>" +
//                "</request>";
//        System.out.println(upload("http://localhost:8770/service/cloud/httpUploadService",new File("C:\\Users\\zhanghongbin\\Desktop\\solrconfig.xml")));
      //  System.out.println(getFileContent("http://219.148.140.98:8770/service/cloud/httpCommandService?mt=xml", xml));
    }
}
