package com.honghe.device.event.DeviceStatusEvent;

import com.hht.global.GlobalDefinitions;
import com.honghe.device.SubScribePool;
import com.honghe.device.event.Event;
import com.honghe.device.event.EventOnAndOffLine;
import com.honghe.device.event.EventSend;
import com.honghe.device.event.factory.EventSendFactory;
import com.honghe.device.util.CommonNameUtil;
import com.honghe.device.util.PlatformToDevices;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ManyEventsProsses {
    static final  Logger logger = Logger.getLogger(EventOnAndOffLine.class);
/*---------------------------------------------------------多个设备上下线事件上报处理--------------------------------------------------------------------------*/

    //处理多个设备上下线事件
    public boolean manyEventsProsser(List eventsList){
        boolean re_value = false;

        //获取注册的平台，遍历平台并给各个平台发送关注的设备上下线事件信息
        Map<String, String> urlPool = SubScribePool.subScribePool;
        Iterator it = urlPool.entrySet().iterator();
        while (it.hasNext()) {
            List eventsData = new ArrayList();
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            String url = (String) entry.getValue();
            String[] keyLable = key.split(":");
            String sysLable = keyLable[0].toString();//获取各平台标识

            //通过平台获取平台所关注的设备类型
            String devices = PlatformToDevices.convertDevicePlatform(sysLable);

            //从上报过来的设备中挑选出该平台关注的设备，并放入list中返回
            eventsData = ManyEventsSend.getSendEventsData(devices,eventsList,eventsData);

            //若有该平台关注的设备，则给该平台发送平台关注的事件信息
            if (!eventsData.isEmpty()){
                JSONArray sendDatas = JSONArray.fromObject(eventsData);
                re_value = ManyEventsSend.sendEventsToPlatform(url, String.valueOf(sendDatas));
            }

        }
        return re_value;
    }

/*---------------------------------------------------------单条设备上下线事件上报处理--------------------------------------------------------------------------*/
    /**
     * 单个设备上下线事件上报
     * @param deviceInfo 上报设备内容
     * @return
     */
    public boolean oneEventProsser(Map deviceInfo){
        boolean re_value = false;
        String deviceType = "";
        String deviceIp = deviceInfo.get("deviceIp").toString();//设备ip
        String devicetype = deviceInfo.get("deviceType").toString();//设备类型
        logger.debug("接收到devicegrid发送的设备信息为：deviceType："+devicetype);
        if(GlobalDefinitions.DeviceType_IPC.equals(devicetype)|| GlobalDefinitions.DeviceType_NVR.equals(devicetype)){
            deviceType = CommonNameUtil.HHREC.toString();
        }else {
            deviceType = devicetype.toLowerCase();
        }
        logger.debug("处理后的设备类型为："+deviceType);
        String eventType = deviceInfo.get("eventType").toString();//在线状态标识，101：上线，102：离线，103：主机帐号或密码错误
        String eventContext = deviceInfo.get("eventContext").toString();//事件内容，上下线的事件内容为空，主机帐号或密码错误内容为"WrongAuth for ID"
        //创建事件
        re_value = sendEventNotice(deviceIp,deviceType,eventType,eventContext);
        if (!re_value) {
            re_value =sendEventNotice(deviceIp,deviceType,eventType,eventContext);
        }
        logger.debug("事件上下线通知：ip:" + deviceIp + ",事件类型：" +eventType +", 平台接收状态"+re_value);
        return re_value;
    }




    /**
     * 处理上下线事件通知并发送给平台
     * @param deviceIp 设备ip
     * @param type 平台设备设备类型标识
     * @param eventType 事件类型，101：上线，102：离线，103：主机帐号或密码错误
     * @param eventContext 事件内容
     * @return
     */
    private boolean sendEventNotice(String deviceIp,String type,String eventType,String eventContext){
        boolean re_value = false;

        Event event = new Event(deviceIp, type, eventType, eventContext);
        EventSend eventSend = EventSendFactory.CreateEventSend(event);
        if (eventSend != null) {
            //对事件进行处理并发送给平台
            re_value = eventSend.noticeSubscribe();
            logger.debug("事件通知---ip:" + deviceIp + "事件类型:" + eventType +",设备类型："+type+ ",事件内容:" + eventContext + "返回值：" + re_value);
        } else {
            logger.debug("事件通知异常---ip:" + deviceIp + "事件类型:" + eventType +",设备类型："+type+ ",事件内容:" + eventContext + "返回值：" + re_value);
        }
        return re_value;
    }

}
