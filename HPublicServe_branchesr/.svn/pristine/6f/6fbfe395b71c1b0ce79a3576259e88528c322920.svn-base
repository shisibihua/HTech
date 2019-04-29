package com.honghe.service.client.websocket;

import com.honghe.service.client.ServiceClientCallback;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;


/**
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: 北京鸿合盛视数字媒体技术有限公司</p>
 *
 * @author zhanghongbin
 * @date 2015/11/5
 */


public class WebSocketImpl extends WebSocketClient {


    private String data;
    private ServiceClientCallback serviceClientCallback;
    public WebSocketImpl(URI serverUri, String data, ServiceClientCallback serviceClientCallback) {
        super(serverUri, new Draft_17());
        this.data = data;
        this.serviceClientCallback = serviceClientCallback;
        try {
            this.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        this.send(data);
    }

    @Override
    public void onMessage(String s) {
        try {
            this.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        serviceClientCallback.execute(s);
    }


    @Override
    public void onClose(int i, String s, boolean b) {
    }

    @Override
    public void onError(Exception e) {
    }
}
