package com.honghe.communication.message;

import com.honghe.communication.ioc.Response;

import java.util.Map;

/**
 * Created by zhanghongbin on 2015/3/28.
 * 消息接口负责接受消息和构建消息
 */
public interface Message {

    /**
     * 接受消息转换成Map结构
     * @param message
     * @return
     */
    public Map<String, Object> receiver(Object message);

    /**
     * 构建一个制定格式的消息
     * @param message
     * @return
     */
    public String build(Response message);
}
