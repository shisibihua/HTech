package com.honghe.exception;

/**
 * Created by hh on 2016/12/1.
 */
public class ParamException extends Exception{
    public ParamException() {
        super("方法参数错误");
    }
    public ParamException(String str) {
        super("方法参数错误！"+ str);
    }
}
