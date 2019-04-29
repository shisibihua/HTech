package com.honghe.device.event;

import com.hht.DeviceManager.DeviceManager;
import com.hht.device.Device;
import com.hht.global.GlobalDefinitions;
import com.honghe.device.SubScribePool;
import com.honghe.device.event.Impl.EvendSendDmanager;
import com.honghe.device.event.Impl.EvendSendDss;
import com.honghe.net.OKHttpUtil;
import net.sf.json.JSONObject;
import okhttp3.*;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by lyx on 2016-07-07.
 * 事件发送
 */
public abstract class EventSendAbs implements EventSend {

    private Event event;

    private Logger logger = Logger.getLogger("device");

    public EventSendAbs(Event event) {
        this.event = event;
    }


    /**
     * 给注册的平台发送消息
     *
     * @return
     */
    public Boolean noticeSubscribe() {
        boolean re_value = false;
        Map<String, String> urlPool = SubScribePool.subScribePool;
        eventProcessing();
        getLogger().debug("平台:" + urlPool.toString() + "平台数量：" + urlPool.size());
        if (urlPool != null && urlPool.size() > 0) {
            if (EvendSendDss.SYS_DSS.equals(getSys())) {
                sendInfoToPlat(urlPool, EvendSendDmanager.SYS_DMANAGER);
            }
            re_value = sendInfoToPlat(urlPool, getSys());
        }
        return re_value;
    }

    /**
     * 发送事件通知到平台
     *
     * @param urlPool 保存订阅平台的信息
     * @param sysName 要发送的平台系统名称
     * @return
     */
    private boolean sendInfoToPlat(Map<String, String> urlPool, String sysName) {
        boolean flag = false;
        Iterator it = urlPool.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            String url = (String) entry.getValue();
            if (key.contains(sysName)) {
                flag = sendMessage(url);
            }
        }
        return flag;
    }

    private RequestBody batchInsert() {
        Map<String, String> eventData = getEventData();
        FormBody.Builder builder = new FormBody.Builder();
        for (String s : eventData.keySet()) {
            builder.add(s, eventData.get(s));
        }
        return builder.build();
    }

    /**
     * 发送消息处理
     *
     * @param url
     * @return
     */
    protected Boolean sendMessage(String url) {
//        ServiceProxyFactory.newInstance(ServiceProxyFactory.Type.HTTP).send()
        boolean re_value = false;


        OkHttpClient okClient = OKHttpUtil.getOKHttpClient();
        RequestBody requestBody = batchInsert();
        Request build = new Request.Builder().url(url).post(requestBody).build();
        Call call = okClient.newCall(build);
        try {
            Response response = call.execute();
//            Connection.Response response = connection.execute();
            logger.debug("response:" + response.code() + "; " + response.message() + "; " + response.body() + "; ");

            if (response.isSuccessful()) {
                ResponseBody response1 = response.body();
                String json = response1.string();
//                String json = response.body();
                if (json != null && !json.isEmpty()) {
                    JSONObject jsonObject = JSONObject.fromObject(json);
                    String state = (String) jsonObject.get("state");
                    if (state.equals("success")) {
                        re_value = true;
                    }
                }
                if (re_value) {
                    getLogger().debug("发送消息到平台,url:" + url + ",内容:" + getEventData() + "信息：" + response.code() + ", " + response.body());
                } else {
                    getLogger().debug("发送消息到平台异常,url:" + url + ",内容:" + getEventData() + "信息：" + response.code() + ", " + response.body());
                }
                response1.close();
            } else {
                getLogger().error("发送消息到平台异常,url:" + url + ",内容:" + getEventData() + "信息：" + response.code() + ", " + response.body());
            }

        } catch (IOException e) {
            getLogger().error("发送消息到平台异常,url:" + url + ",内容:" + getEventData(), e);
        }

        return re_value;
    }

    /**
     * 封装发送的事件的数据
     *
     * @return
     */
    private Map<String, String> getEventData() {
        Map<String, String> re_value = new HashMap<>();
        re_value.put("strDeviceToke", event.getStrDeviceToken());
        re_value.put("strEventContext", event.getStrEventContext());
        re_value.put("deviceType", event.getDeviceType());

        Device device = DeviceManager.getDevice(event.getStrDeviceToken());
        String mac = device == null ? "" : device.getMac();
        //这部分针对901.6的大屏，不能够获取到ops的mac地址，只能获取大屏设备的物理地址进行处理。默认osp的mac地址与大屏物理地址一致（数据库也进行了相应的处理）
        if ("00:00:00:00:00:00".equals(mac)) {
            mac = device.getMcuAddress();
        }

        re_value.put("deviceMac", mac);
        re_value.put("event", event.getStrEventType());

        return re_value;
    }


    /**
     * 事件处理
     *
     * @return
     */
    private void eventProcessing() {
        if (GlobalDefinitions.Event_DeviceOnline.equals(event.getStrEventType())) {
            eventHostOnline(event);
        } else if (GlobalDefinitions.Event_DeviceOffline.equals(event.getStrEventType())) {
            eventHostOffline(event);
        } else if (GlobalDefinitions.Event_DeviceWrongAuth.equals(event.getStrEventType())) {
            eventWrongAuth(event);
        } else if (GlobalDefinitions.Event_DeviceMessage.equals(event.getStrEventType())) {
            eventMessage(event);
        }
    }

    /**
     * 获取日志打印对象
     *
     * @return
     */
    protected abstract Logger getLogger();

    /**
     * 处理设备上线事件
     *
     * @param event
     */
    protected abstract void eventHostOnline(Event event);

    /**
     * 处理设备下线事件
     *
     * @param event
     */
    protected abstract void eventHostOffline(Event event);

    /**
     * 处理用户密码错误的事件
     *
     * @param event
     */
    protected abstract void eventWrongAuth(Event event);

    /**
     * 处理设备发送的事件
     *
     * @param event
     */
    protected abstract void eventMessage(Event event);

}
