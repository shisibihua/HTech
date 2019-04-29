package com.honghe.ad.excetion;

/**
        * Created by lyx on 2016-10-14.
        */
public class ParamException extends Exception {
    public ParamException() {
        super("方法参数错误");
    }
    public ParamException(String str) {
        super("方法参数错误！"+ str);
    }
}
