package com.honghe.device.event.Impl;

import com.honghe.device.dao.DeviceDao;
import com.honghe.device.event.Event;
import com.honghe.device.event.EventSendAbs;
import com.honghe.device.util.CommonNameUtil;
import com.honghe.device.util.JsonUtil;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lyx on 2016-07-14.
 * 处理校园定位系统的事件发送
 */
public class EvendSendCampusLocation extends EventSendAbs {

    public static final String SYS_CAMPUSLOCATION = "campusLocation";//校园定位

    public EvendSendCampusLocation(Event event) {
        super(event);
    }


    @Override
    public String getSys() {
        return SYS_CAMPUSLOCATION;
    }


    @Override
    protected Logger getLogger() {
        return Logger.getLogger(EvendSendCampusLocation.class);
    }

    /**
     * 处理设备上线事件
     *
     * @param event
     */
    @Override
    protected void eventHostOnline(Event event) {

    }

    /**
     * 处理设备下线事件
     *
     * @param event
     */
    @Override
    protected void eventHostOffline(Event event) {

    }

    /**
     * 处理用户密码错误的事件
     *
     * @param event
     */
    @Override
    protected void eventWrongAuth(Event event) {

    }


    @Override
    protected void eventMessage(Event event) {
        String[] contextArr = event.stringSeparate();

        //如果是考勤信息，则进行以下处理
        if (contextArr != null && contextArr[3].equals("userRecordInfoList")) {

            String data = contextArr[2];
            JSONObject jsonObject = JSONObject.fromObject(data);
            Map map = JsonUtil.jsonToMap(jsonObject);
            if (null != data && !data.isEmpty()) {
                Map<String, String> deviceInfo = (Map<String, String>) map.get("deviceInfo");
                String deviceIp = deviceInfo.get("deviceIp");
                Map hostMap = DeviceDao.INSTANCE.getHostInfoByIp(deviceIp);
                String deviceId = hostMap.size() != 0 ? (String) hostMap.get("host_id") : "0";
                String deviceName = hostMap.size() != 0 ? (String) hostMap.get("host_name") : "";
                String deviceMac = hostMap.size() != 0 ? (String) hostMap.get("host_mac") : "";

                List<Map> list = (List<Map>) map.get("userRecordInfoList");
                List<Map> sendList = new ArrayList<>();
                for (Map obj : list) {
                    obj.put("deviceId", deviceId);
                    obj.put("deviceName", deviceName);
                    obj.put("deviceMac", deviceMac);
                    obj.put("desc", event.getDeviceType());
                    sendList.add(obj);
                }
                HashMap<String, Object> hashMap = new HashMap<>();

                hashMap.put("list", sendList);
                String str = JSONSerializer.toJSON(hashMap).toString();
                event.setStrEventContext(str);
//            System.out.println("map:" + map);
//            System.out.println("sendList:" + sendList);
//            System.out.println("str:" + str);
            }
        }
    }

    public static void main(String[] args) {
        String url = "http://192.168.17.11:8080/eventConsumerService";
        Map<String, String> params = new HashMap<>();
        params.put("event", "101");
//        sendMessage(url, params);

        JSONObject object = new JSONObject();
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("userNum", "0011");
        map.put("cardTime", "2016-07-06 13:00:00");
        map.put("classId", "1");
        map.put("className", "高中一班");
        map.put("course", "2016-07-06 13:00:00");
        map.put("endTime", "2016-07-06 13:50:00");
        map.put("realName", "张三");
        map.put("record_course", "");
        map.put("startTime", "2016-07-06 13:00:00");
        map.put("techer", "张老师");
        map.put("userNum", "1");
        list.add(map);

        map.put("cardTime", "2016-07-06 13:05:05");
        map.put("classId", "2");
        map.put("className", "高中二班");
        map.put("course", "2016-07-06 13:00:00");
        map.put("endTime", "2016-07-06 13:50:00");
        map.put("realName", "李四");
        map.put("record_course", "");
        map.put("startTime", "2016-07-06 13:00:00");
        map.put("techer", "王老师");
        map.put("userNum", "1");
        list.add(map);


        object.put("deviceIp", "192.168.0.100");
        object.put("stuSignInfoList", list);

        Event event = new Event("", CommonNameUtil.HHTDC.toString(), "", object.toString());
        EvendSendCampusLocation obj = new EvendSendCampusLocation(event);
        obj.eventMessage(event);
    }


}
