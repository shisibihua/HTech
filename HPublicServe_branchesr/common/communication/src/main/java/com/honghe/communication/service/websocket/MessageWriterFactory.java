package com.honghe.communication.service.websocket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanghongbin on 2015/3/14.
 */
public final class MessageWriterFactory {

    public enum Type {
        WS("ws"), SOCKET("socket");
        private String type;

        Type(String type) {
            this.type = type;
        }
    }

    private MessageWriterFactory() {

    }

    private static Map<String, MessageWriter> deviceMessageWriterMap = new HashMap<String, MessageWriter>();

    static {
        deviceMessageWriterMap.put(Type.WS.name(), new WSMessageWriter());

    }


    public static final MessageWriter getInstance(Type type) {
        if (deviceMessageWriterMap.containsKey(type.name())) {
            return deviceMessageWriterMap.get(type.name());
        }
        return null;
    }

    public static final List<MessageWriter> getInstance(String[] type) {
        List<MessageWriter> messageWriterList = new ArrayList<MessageWriter>();
        for (String _type : type) {
            if (deviceMessageWriterMap.containsKey(_type)) {
                messageWriterList.add(deviceMessageWriterMap.get(_type));
            }
        }
        return messageWriterList;
    }


}
