package com.honghe.device.handler;

import com.hht.callback.DeviceEventHandler;
import com.honghe.device.event.Event;
import com.honghe.device.event.EventSend;
import com.honghe.device.event.factory.EventSendFactory;
import com.honghe.device.util.TypeTransformationUtil;
import org.apache.log4j.Logger;


/**
 * 设备通知回调类
 * User: zhanghongbin
 * Date: 14-10-9
 * Time: 下午12:44
 * To change this template use File | Settings | File Templates.
 */
public class DeviceEventHandlerImpl extends DeviceEventHandler {
    final static Logger logger = Logger.getLogger(DeviceEventHandlerImpl.class);

    /**
     * @param strDeviceToken
     * @param strEventType    101 主机上线
     *                        102 主机下线
     *                        103 主机帐号或密码错误
     *                        104 事件通知
     * @param strEventContext
     */
    @Override
    public boolean OnEvent(final String strDeviceToken, String deviceType, String strEventType, String strEventContext) {
        boolean re_value = false;

        String type = TypeTransformationUtil.getType(deviceType.trim());
        Event event = new Event(strDeviceToken, type, strEventType, strEventContext);
        EventSend eventSend = EventSendFactory.CreateEventSend(event);
        if (eventSend != null) {
            re_value = eventSend.noticeSubscribe();
            logger.debug("事件通知---ip:" + strDeviceToken + "事件类型:" + strEventType + ",事件内容:" + strEventContext + "返回值：" + re_value);
        } else {
            logger.debug("事件通知异常---ip:" + strDeviceToken + "事件类型:" + strEventType + ",事件内容:" + strEventContext + "返回值：" + re_value);
        }

        return re_value;
    }
}
