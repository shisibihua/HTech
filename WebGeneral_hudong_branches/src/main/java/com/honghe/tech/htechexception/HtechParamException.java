package com.honghe.tech.htechexception;

/**
 * 当传参不合法、数据有缺失等情况下使用此异常
 *
 * @author HOUJT
 */
public class HtechParamException extends RuntimeException {
    public HtechParamException() {
        super("参数异常！");
    }

    public HtechParamException(String message) {
        super("参数异常" + message);
    }
}
