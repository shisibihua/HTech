package com.honghe.device.event;

/**
 * Created by lyx on 2016-07-07.
 * 事件
 */
public class Event {
    static final String Event_TEXT_SEPARATOR = ":::::::";//事件字符串间隔符
    //设备IP
    private String strDeviceToken;
    //设备类型
    private String deviceType;
    //事件类型
    private String strEventType;
    //事件内容
    private String strEventContext;

    public Event(String strDeviceToken, String deviceType, String strEventType, String strEventContext) {
        this.strDeviceToken = strDeviceToken;
        this.deviceType = deviceType;
        this.strEventType = strEventType;
        this.strEventContext = strEventContext;
    }

    public String getStrDeviceToken() {
        return strDeviceToken;
    }

    public void setStrDeviceToken(String strDeviceToken) {
        this.strDeviceToken = strDeviceToken;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getStrEventType() {
        return strEventType;
    }

    public void setStrEventType(String strEventType) {
        this.strEventType = strEventType;
    }

    public String getStrEventContext() {
        return strEventContext;
    }

    public void setStrEventContext(String strEventContext) {
        this.strEventContext = strEventContext;
    }


    //事件字符串整体拆分
    public String[] stringSeparate() {
        String[] re_value;
        if (this.strEventContext == null || this.strEventContext.trim().equals("")) {
            re_value = null;
        } else if (this.strEventContext.indexOf(this.strEventContext) > -1) {
            re_value = this.strEventContext.split(this.Event_TEXT_SEPARATOR);
        } else {
            String[] tmp = new String[1];
            tmp[0] = this.strEventContext;
            re_value = tmp;
        }
        return re_value;
    }
}
