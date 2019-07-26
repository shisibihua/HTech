package com.honghe.notification;


import com.honghe.net.OKHttpUtil;
import net.sf.json.JSONObject;
import okhttp3.*;

import java.util.Map;

/**
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: 北京鸿合盛视数字媒体技术有限公司</p>
 *
 * @author zhanghongbin
 * @date 2015/12/28
 */
public final class SMS implements Notification {

    public SMS(String phoneNum, String content) {
        this.content = content;
        this.phoneNum = phoneNum;
    }

    private String content;
    private String phoneNum;

    @Override
    public boolean send() {
        Response response = null;
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("mobile", this.phoneNum);
        builder.add("message", this.content);
        RequestBody requestBody = builder.build();
        String credential = Credentials.basic("api", "d0cc3d6af5bf9c1cb07c1e488aff0db5");
        Request request = new Request.Builder().header("Authorization", credential)
                .url("http://sms-api.luosimao.com/v1/send.json").post(requestBody)
                .build();
        try {
            response = OKHttpUtil.newOKHttpClient().newCall(request).execute();
            if (response.isSuccessful()) {
                String content = response.body().string().trim();
                Map<String, Object> result = JSONObject.fromObject(content);
                if (result.containsKey("error")) {
                    if (result.get("error").toString().equals("0")) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return false;
    }
    public static void main(String[] args) {
        String[] www = {"15076268240"};
        for (String w : www) {
            SMS sms = new SMS(w, "你好刘备【HHT Meetings Management Platform】");
            System.out.println(sms.send());
        }

    }
}
