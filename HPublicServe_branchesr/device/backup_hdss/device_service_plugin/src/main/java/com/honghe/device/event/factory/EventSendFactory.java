package com.honghe.device.event.factory;

import com.honghe.device.event.*;
import com.honghe.device.event.Impl.EvendSendCampusLocation;
import com.honghe.device.event.Impl.EvendSendDmanager;
import com.honghe.device.event.Impl.EvendSendDss;
import com.honghe.device.util.CommonNameUtil;

/**
 * Created by lyx on 2016-07-14.
 * 事件发送工厂
 */
public class EventSendFactory {

    public static EventSend CreateEventSend(Event event) {
        EventSend eventSend = null;
        String deviceType = event.getDeviceType();

        if (event != null) {
            if (CommonNameUtil.HHTC.toString().equals(deviceType)
                    || CommonNameUtil.HHREC.toString().equals(deviceType)
                    || CommonNameUtil.HHTWB.toString().equals(deviceType)
                    || CommonNameUtil.HTPR.toString().equals(deviceType)) {
                eventSend = new EvendSendDmanager(event);
            } else if (CommonNameUtil.HHTPA.toString().equals(deviceType)) {
                eventSend = new EvendSendCampusLocation(event);
            } else if (CommonNameUtil.HHTDC.toString().equals(deviceType)) {
                eventSend = new EvendSendDss(event);
            }
        }

        return eventSend;
    }

}
