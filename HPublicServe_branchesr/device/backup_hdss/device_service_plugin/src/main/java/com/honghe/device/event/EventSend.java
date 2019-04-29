package com.honghe.device.event;

/**
 * Created by lyx on 2016-07-08.
 */
public interface EventSend {
    /**
     * 发送消息
     *
     * @return
     */
    public Boolean noticeSubscribe();

    /**
     * 获取接收事件的系统
     *
     * @return
     */
    public String getSys();

}
