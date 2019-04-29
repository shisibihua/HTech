package com.honghe.service.client.http;

import org.jsoup.Connection;

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

    public static byte[] getFileByte(String url) {
        Connection connection = org.jsoup.Jsoup.connect(url).method(Connection.Method.GET).timeout(3000 * 3000).maxBodySize(10000*10000);
        try {
            Connection.Response response = connection.ignoreContentType(true).ignoreHttpErrors(true).execute();
            return response.bodyAsBytes();
        } catch (Exception e) {
            return null;
        }

    }

    public static String getFileContent(String url){
        Connection connection = org.jsoup.Jsoup.connect(url).method(Connection.Method.GET).timeout(3000 * 3000).followRedirects(true);
        try {
            Connection.Response response = connection.ignoreContentType(true).execute();
            return response.body().trim();
        } catch (Exception e) {
            return "";
        }
    }
}
