package com.honghe.util;


import com.honghe.communication.config.ServiceConfigFileParser;
import com.honghe.net.OKHttpUtil;
import okhttp3.*;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OkHttpUtils {

    private static final Logger logger = Logger.getLogger(OkHttpUtils.class);
    private static final int CONNET_TIME = 6000;  //连接超时时间
    private static final int REQUEST_TIME = 6000; //读取超时时间
    private static String URLVAR;
    private static final String PATH = ""; // 项目名，可能会变动
    private static OkHttpUtils instance = null;

    private OkHttpUtils() {

    }

    static {
        String licenseAddress = ServiceConfigFileParser.getLicense();
        if (licenseAddress.equals("")) {
            URLVAR = "http://localhost:58890";
        } else {
            URLVAR = "http://" + licenseAddress + ":58890";
        }
    }

    public static OkHttpUtils getInstance() {
        if (instance == null) {
            instance = new OkHttpUtils();
        }
        return instance;
    }

    /**
     * 通过Post向服务器请求服务，并返回Json格式的字符串
     *
     * @param url
     * @param params
     * @return
     */
    public String getJsonByPost(String url, Map<String, String> params) throws IOException {
        String json = null;
        String URL = URLVAR + PATH + url;
        synchronized (instance) {
            OkHttpClient okHttpClient = OKHttpUtil.newOKHttpClient();
            okHttpClient.newBuilder().readTimeout(REQUEST_TIME, TimeUnit.SECONDS)//设置读取超时时间
                    .connectTimeout(CONNET_TIME, TimeUnit.SECONDS)//设置连接超时时间
                    .build();

            FormBody.Builder builder = new FormBody.Builder();
            Iterator keyIterator = params.keySet().iterator();

            while (keyIterator.hasNext()) {
                String key = (String) keyIterator.next();
                builder.add(key, params.get(key));
            }

            RequestBody requestBody = builder.build();

            Response response = okHttpClient.newCall((new Request.Builder()).url(URL).post(requestBody).build()).execute();
            json = responseResult(response);

        }

        return json;
    }


    /**
     * 通过get方式向服务器请求服务，并返回Json格式的字符串
     *
     * @param url
     * @param params
     * @return
     */
    public String getJsonByGet(String url, String params) throws IOException {
        String json = null;
        String URL = URLVAR + PATH + url;
        synchronized (instance) {
            OkHttpClient okHttpClient = OKHttpUtil.newOKHttpClient();
            okHttpClient.newBuilder().readTimeout(REQUEST_TIME, TimeUnit.SECONDS)//设置读取超时时间
                    .connectTimeout(CONNET_TIME, TimeUnit.SECONDS)//设置连接超时时间
                    .build();

            Response response = okHttpClient.newCall((new Request.Builder()).url(URL + "?" + params).build()).execute();
            json = responseResult(response);

        }

        return json;
    }

    /**
     * 解析请求结果
     *
     * @param response
     * @return
     * @throws IOException
     */
    private String responseResult(Response response) throws IOException {
        ResponseBody responseBody = null;
        String responseResult = null;

        if (response.isSuccessful()) {
            responseBody = response.body();
            responseResult = new String(responseBody.bytes(), "GBK");
            logger.info(" json:" + responseResult);
        } else {
            logger.error("connect failed");
        }

        if (responseBody != null) {
            responseBody.close();
        }
        return responseResult;
    }

}
