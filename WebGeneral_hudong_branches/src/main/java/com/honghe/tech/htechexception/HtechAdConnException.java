package com.honghe.tech.htechexception;

import com.honghe.tech.util.exceptionutil.HttpServerException;

/**
 * 连接ad服务的时候出现问题时候使用此异常
 * @author HOUJT
 */
public class HtechAdConnException extends HttpServerException {
    public HtechAdConnException() {
        super("请求ad服务发生异常！");
    }

    public HtechAdConnException(String message) {
        super("请求ad服务发生异常"+message);
    }
}
