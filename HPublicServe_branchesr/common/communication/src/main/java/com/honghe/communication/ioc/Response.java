package com.honghe.communication.ioc;


/**
 * Created by zhanghongbin on 2015/5/5.
 */
public final class Response {

    public final static int PARAM_ERROR = -1;
    public final static int NO_METHOD = -2;

    public final static int INNER_ERROR = -3;

    public final static int UNAUTHORIZED_ACCESS = -4;

    public final static int RESTRICTING_ACCESS = -5;

    private String type;
    private int code = 0;
    private Object result = "";

    public Response() {

    }

    public Response(String type, Object result) {
        this.type = type;
        this.result = result;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }


}
