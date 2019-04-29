package com.honghe.communication.service.websocket;

import javax.websocket.Session;
import java.util.Set;

/**
 * Created by zhanghongbin on 2015/3/12.
 */
public final class WSMessageWriter implements MessageWriter {


    /**
     * 发送消息到web js负责接收
     */
    public final boolean send(String id, String responseMessage) {
        if (responseMessage == null) return false;
        return _send(id, responseMessage);
    }

    private final boolean _send(String id, String message) {
        boolean flag = false;
        if (WSSessionPool.INSTANCE().contains(id)) {
            Session messageInbound = (Session) WSSessionPool.INSTANCE().get(id);
            try {
                if (messageInbound.isOpen()) {
                    messageInbound.getBasicRemote().sendText(message);
                    flag = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    @Override
    public void send(String responseMessage) {
        if (responseMessage == null) return;
        Set<String> idSet = WSSessionPool.INSTANCE().keys();
        for (String id : idSet) {
            send(id, responseMessage);
        }
    }
}
