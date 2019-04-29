package com.honghe.communication.ioc;

import com.honghe.communication.message.Message;
import com.honghe.communication.message.MessageFactory;

import java.util.Map;

/**
 * Created by zhanghongbin on 2015/4/1.
 */
public final class CommandIOC {


    private CommandIOC(){

    }

    public final static String execute(Context context, Object requestMessage) {
        String mt = "";
        if (requestMessage == null)
            return null;
        String message = requestMessage.toString().trim();
        if (message.startsWith("<?xml version")) {
            mt = MessageFactory.Type.XML.name();
        } else if ((message.startsWith("[") && message.endsWith("]")) || (message.startsWith("{") && message.endsWith("}"))) {
            mt = MessageFactory.Type.JSON.name();
        }
        if (mt.equals("")) return null;
        return execute(context, mt, requestMessage);
    }

    /**
     * @param mt             消息类型 Json ,map
     * @param requestMessage 请求消息内容
     * @return
     */
    public final static String execute(Context context, String mt, Object requestMessage) {
        final Message message = MessageFactory.getInstance(mt);
        if (message == null) return null;
        Map<String, Object> requestMessageMap = message.receiver(requestMessage);
        if (!requestMessageMap.containsKey("cmd")) return null;
        Command command = CommandFactory.getInstance(requestMessageMap.get("cmd").toString());
        if (command != null) {
            Response responseMessage;
            try {
                responseMessage = command.execute(context, requestMessageMap);
                responseMessage.setType(responseMessage.getType() + "_ACK");
            } catch (Exception e) {
                e.printStackTrace();
                responseMessage = new Response("", "");
                responseMessage.setCode(Response.INNER_ERROR);
            }
            return message.build(responseMessage);
        }
        return null;
    }
}
