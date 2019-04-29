package com.honghe.device.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpConnectUtil {
    private static Logger logger = Logger.getLogger(HttpConnectUtil.class);

    public static JSONObject httpPost(String url, JSONObject jsonParam) {
        //post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject jsonResult = null;
        HttpPost method = new HttpPost(url);
        try {
            List<NameValuePair> paramList = new ArrayList<>();
            for (Object entrys : jsonParam.entrySet()) {
                JSONObject.Entry<String, Object> entry = (JSONObject.Entry<String, Object>) entrys;
                paramList.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
            }
            // 模拟表单
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "UTF-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/x-www-form-urlencoded");
            method.setEntity(entity);
            method.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(5000).setConnectionRequestTimeout(1000)
                    .setSocketTimeout(5000).build();
            method.setConfig(requestConfig);
            HttpResponse result = httpClient.execute(method);
            url = URLDecoder.decode(url, "UTF-8");
            /**请求发送成功，并得到响应**/
            if (result.getStatusLine().getStatusCode() == 200) {
                String str = "";
                try {
                    /**读取服务器返回过来的json字符串数据**/
                    str = EntityUtils.toString(result.getEntity());
                    /**把json字符串转换成json对象**/

                    jsonResult = JSONObject.fromObject(str);
                    if (jsonResult == null) {
                        jsonResult = new JSONObject();
                        jsonResult.put("result", str);
                    }
                } catch (JSONException e){
                    jsonResult = new JSONObject();
                    jsonResult.put("result", str);
                }catch (Exception e) {
                    logger.error("post请求提交失败:" + url, e);
                }
            }
        } catch (IOException e) {
            logger.error("post请求提交失败:" + url, e);
        }
        return jsonResult;
    }

    public static JSONObject httpPostJson(String url, JSONObject jsonParam) {
        //post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject jsonResult = null;
        HttpPost method = new HttpPost(url);
        try {
            StringEntity entity = new StringEntity(jsonParam.toString(),"UTF-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            method.setEntity(entity);
            HttpResponse result = httpClient.execute(method);
            url = URLDecoder.decode(url, "UTF-8");
            /**请求发送成功，并得到响应**/
            if (result.getStatusLine().getStatusCode() == 200) {
                String str = "";
                try {
                    /**读取服务器返回过来的json字符串数据**/
                    str = EntityUtils.toString(result.getEntity());
                    /**把json字符串转换成json对象**/
                    jsonResult = JSONObject.fromObject(str);
                    if (jsonResult == null) {
                        jsonResult = new JSONObject();
                        jsonResult.put("result", str);
                    }
                } catch (Exception e) {
                    logger.error("post请求提交失败:" + url, e);
                }
            }
        } catch (IOException e) {
            logger.error("post请求提交失败:" + url, e);
        }
        return jsonResult;
    }

    /**
     * 发送get请求
     *
     * @param url 路径
     * @return
     */
    public static JSONObject httpGet(String url) {
        //get请求返回结果
        JSONObject jsonResult = null;
        try {
            DefaultHttpClient client = new DefaultHttpClient();
            //发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                String strResult = EntityUtils.toString(response.getEntity());
                /**把json字符串转换成json对象**/
                jsonResult = JSONObject.fromObject(strResult);
                url = URLDecoder.decode(url, "UTF-8");
            } else {
                logger.error("get请求提交失败:" + url);
            }
        } catch (IOException e) {
            logger.error("get请求提交失败:" + url, e);
        }
        return jsonResult;
    }

    public static void main(String[] args) {
        List list = new ArrayList();
        Map map = new HashMap();
        map.put("deviceIp","192.168.19.225");
        map.put("deviceType","NVR");
        map.put("eventContext","");
        map.put("eventType","102");
        list.add(map);
        JSONArray jsonArray = JSONArray.fromObject(list);
        System.out.println(jsonArray);
    }
}
