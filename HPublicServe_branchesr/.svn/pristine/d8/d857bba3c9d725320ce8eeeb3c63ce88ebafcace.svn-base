package com.honghe.communication.plugin.impl.notification;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import net.sf.json.JSONObject;

import javax.ws.rs.core.MediaType;
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
        Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter(
                "api", "d0cc3d6af5bf9c1cb07c1e488aff0db5"));
        WebResource webResource = client.resource(
                "http://sms-api.luosimao.com/v1/send.json");
        MultivaluedMapImpl formData = new MultivaluedMapImpl();
        formData.add("mobile", this.phoneNum);
        formData.add("message", this.content);
        try {
            ClientResponse response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED).
                    post(ClientResponse.class, formData);
            String textEntity = response.getEntity(String.class);
            Map<String, Object> result = JSONObject.fromObject(textEntity);
            System.out.println(result);
            if (result.containsKey("error")) {
                if (result.get("error").toString().equals("0")) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;

    }

    public static void main(String[] args) {
        String[] www = {"13582068616"};
        for (String w : www) {
            SMS sms = new SMS(w, "There will be a meeti【HHT Meetings Management Platform】");
            System.out.println(sms.send());
        }

    }
}
