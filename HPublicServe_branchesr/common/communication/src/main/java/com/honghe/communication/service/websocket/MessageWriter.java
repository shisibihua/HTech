package com.honghe.communication.service.websocket;


/**
 * Created by zhanghongbin on 2015/3/12.\
 * 消息发送器
 */
public interface MessageWriter {

    /**
     * 发送消息
     * @param id
     * @param responseMessage
     * @return
     */
    public boolean send(String id, String responseMessage);

    /**
     * 发送消息
     * @param responseMessage
     */
    public void send(String responseMessage);
}
