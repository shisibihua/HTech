package com.honghe.communication.ioc;

/**
 * Created by zhanghongbin on 2015/4/10.
 */
public final class Context {

    public enum Type {
        HTTP,WEBSOCKET, TCP,UDP,SIP
    }

    private Type type;
    private Object contextObj;

    public Context(Type type, Object contextObj) {
        this.type = type;
        this.contextObj = contextObj;
    }

    public Type getType() {
        return type;
    }

    public Object getContextObj() {
        return contextObj;
    }
}
