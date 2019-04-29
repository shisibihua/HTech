package com.honghe.communication.message;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhanghongbin on 2015/3/28.
 */
public final class MessageFactory {

    public enum Type {
        MAP("map"), JSON("json"), XML("xml");
        private String type;

        private Type(String type) {
            this.type = type;
        }
    }

    private MessageFactory() {

    }

    private static Map<String, Message> messageMap = new HashMap();

    static {
        messageMap.put(Type.MAP.name(), new MapMessage());
        messageMap.put(Type.JSON.name(), new JSONMessage());
        messageMap.put(Type.XML.name(), new XMLMessage());
    }


    public static final Message getInstance(String mt) {
        if (messageMap.containsKey(mt)) {
            return messageMap.get(mt);
        }
        return null;
    }
}
