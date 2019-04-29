package com.honghe.util;

/**
 * 请求Http的工具类
 *
 * @author yanxue
 */
public class HttpUtils {

    private static HttpUtils intance = null;
    static final int CONNET_TIME=6000;
    static final int REQUEST_TIME=6000;
    private HttpUtils() {

    }

    public static HttpUtils getInstance() {
        if (intance == null) {
            intance = new HttpUtils();
        }

        return intance;
    }




    /**
     * 通过Post向服务器请求服务，并返回Json格式的字符串
     *
     * @param URL
     * @param params
     * @return
     */
    /*public String getJsonByPost(String URL, List<NameValuePair> params) throws IOException {
        String json = null;
        synchronized (this) {
            HttpPost httpPost = ConnNet.getIntance().gethttpPost(URL);

            httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

            // 请求信息类型MIME每种响应类型的输出（普通文本、html 和 XML，json）。允许的响应类型应当匹配资源类中生成的
            // MIME
            // 类型
            // 资源类生成的 MIME 类型应当匹配一种可接受的 MIME 类型。如果生成的 MIME 类型和可接受的 MIME 类型不
            // 匹配，那么将
            // 生成
            // com.sun.jersey.api.client.UniformInterfaceException。例如，将可接受的
            // MIME 类型设置为 text/xml，而将
            // 生成的 MIME 类型设置为 application/xml。将生成 UniformInterfaceException。
            httpPost.addHeader("Accept", "text/json");
            httpPost.addHeader("charset", HTTP.UTF_8);

            HttpParams httpparams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpparams, CONNET_TIME); // 设置连接超时
            HttpConnectionParams.setSoTimeout(httpparams, REQUEST_TIME); // 设置请求超时
            httpPost.setParams(httpparams);

            // 获取响应的结果
            HttpResponse response = ConnNet.getIntance().getHttpClient().execute(httpPost);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                json = EntityUtils.toString(response.getEntity(), "GBK");
//                    System.out.println(" json:" + json);
            } else {
//                    System.out.println(" 连接不成功");
            }


        }
        return json;
    }*/

    /**
     * 通过get方式向服务器请求服务，并返回Json格式的字符串
     *
     * @param URL
     * @param params
     * @return
     */
    /*public String getJsonByGet(String URL, String params) throws IOException {
        String json = null;
        synchronized (this) {
            HttpGet httpGet = ConnNet.getIntance().gethttpGet(URL + "?" + URLEncoder.encode(params,HTTP.UTF_8));


//                httpGet.set.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

            // 请求信息类型MIME每种响应类型的输出（普通文本、html 和 XML，json）。允许的响应类型应当匹配资源类中生成的
            // MIME
            // 类型
            // 资源类生成的 MIME 类型应当匹配一种可接受的 MIME 类型。如果生成的 MIME 类型和可接受的 MIME 类型不
            // 匹配，那么将
            // 生成
            // com.sun.jersey.api.client.UniformInterfaceException。例如，将可接受的
            // MIME 类型设置为 text/xml，而将
            // 生成的 MIME 类型设置为 application/xml。将生成 UniformInterfaceException。
            httpGet.addHeader("Accept", "text/json");
            httpGet.addHeader("charset", HTTP.UTF_8);

            HttpParams httpparams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpparams, CONNET_TIME); // 设置连接超时
            HttpConnectionParams.setSoTimeout(httpparams, REQUEST_TIME); // 设置请求超时
            httpGet.setParams(httpparams);

            // 获取响应的结果
            HttpResponse response = ConnNet.getIntance().getHttpClient().execute(httpGet);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                json = EntityUtils.toString(response.getEntity(), "GBK");
//                    System.out.println(" json:" + json);
            } else {
//                    System.out.println(" 连接不成功");
            }

        }
        return json;
    }*/

    /**
     * 向服务器请求服务，并返回Json格式的字符串
     *
     * @param URL
     * @return
     */
    /*public String getJson(String URL) {
        String json = null;

        synchronized (this) {
            HttpPost httpPost = ConnNet.getIntance().gethttpPost(URL);
            try {

                // 请求信息类型MIME每种响应类型的输出（普通文本、html 和 XML，json）。允许的响应类型应当匹配资源类中生成的
                // MIME
                // 类型
                // 资源类生成的 MIME 类型应当匹配一种可接受的 MIME 类型。如果生成的 MIME 类型和可接受的 MIME 类型不
                // 匹配，那么将
                // 生成
                // com.sun.jersey.api.client.UniformInterfaceException。例如，将可接受的
                // MIME 类型设置为 text/xml，而将
                // 生成的 MIME 类型设置为 application/xml。将生成 UniformInterfaceException。
                httpPost.addHeader("Accept", "text/json");
//                System.out.println(" httPost:" + httpPost.getURI());
                // 获取响应的结果
                HttpResponse response = ConnNet.getIntance().getHttpClient().execute(httpPost);

                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    json = EntityUtils.toString(response.getEntity(), "utf-8");
//                    System.out.println( " json:" + json);
                }
            } catch (UnsupportedEncodingException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            } catch (IOException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
        }
        return json;
    }*/
}
