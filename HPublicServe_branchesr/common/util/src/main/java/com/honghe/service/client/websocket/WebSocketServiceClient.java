package com.honghe.service.client.websocket;

import com.honghe.service.client.Result;
import com.honghe.service.client.ServiceClientCallback;
import net.sf.json.JSONSerializer;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: 北京鸿合盛视数字媒体技术有限公司</p>
 *
 * @author zhanghongbin
 * @date 2015/11/9
 */
public class WebSocketServiceClient {

    private String ip;
    private int port;

    public WebSocketServiceClient() {
        this("localhost", 8770);
    }

    public WebSocketServiceClient(String ip){
        this(ip,8770);
    }

    public WebSocketServiceClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void execute(String cmd, String method, Map<String, String> params, ServiceClientCallback serviceClientCallback) {
        params.put("cmd", cmd);
        params.put("cmd_op", method);
        String jsonParams = JSONSerializer.toJSON(params).toString();
        String uri = "ws://" + ip + ":" + port + "/service/cloud/WSCommandService";
        new WebSocketImpl(URI.create(uri), jsonParams, serviceClientCallback);
    }

    public static void main(String[] args) {
        WebSocketServiceClient webSocketServiceClient = new WebSocketServiceClient("localhost",8770);
        HashMap hashMap = new HashMap<String, String>();

        hashMap.put("userName","admin");
        hashMap.put("userPwd","123456789");
        webSocketServiceClient.execute("user", "userCheck", hashMap, new ServiceClientCallback() {
            @Override
            protected void response(Result result) {
                System.out.println(result.getValue());
            }
        });

    }
}
