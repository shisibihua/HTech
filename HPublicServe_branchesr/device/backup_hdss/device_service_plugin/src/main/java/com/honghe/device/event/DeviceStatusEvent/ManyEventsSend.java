package com.honghe.device.event.DeviceStatusEvent;

import com.honghe.device.event.EventOnAndOffLine;
import com.honghe.device.util.HttpConnectUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

public class ManyEventsSend {

    static final Logger logger = Logger.getLogger(EventOnAndOffLine.class);

    public static List getSendEventsData(String devices,List eventsList,List eventsData){
        String[] deviceStr = devices.split(",");
        for (int i=0;i<deviceStr.length;i++){
            String deviceType = deviceStr[i];
            for (Object object:eventsList){
                Map<String,String> eventMap = (Map) object;
                if (eventMap!=null&&!eventMap.isEmpty()){
                    String type = eventMap.get("deviceType");
                    if (deviceType.equals(type)){
                        eventsData.add(eventMap);
                        break;
                    }
                }
            }
        }
        return eventsData;
    }
//    public static boolean sendEventsNotice(List eventsList){
//        boolean re_value = false;
//        List eventsData = new ArrayList();
//        Map<String, String> urlPool = SubScribePool.subScribePool;
//        Iterator it = urlPool.entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry entry = (Map.Entry) it.next();
//            String key = (String) entry.getKey();
//            String url = (String) entry.getValue();
//            String[] keyLable = key.split(":");
//            String sysLable = keyLable[0].toString();
//            String devices = PlatformToDevices.convertDevicePlatform(sysLable);
//            String[] deviceStr = devices.split(",");
//            for (int i=0;i<deviceStr.length;i++){
//                String deviceType = deviceStr[i];
//                for (Object object:eventsList){
//                    JSONObject eventObject = (JSONObject) object;
//                    if (eventObject!=null){
//                        String type = eventObject.get("deviceType").toString();
//                        if (deviceType.equals(type)){
//                            eventsData.add(eventObject);
//                            break;
//                        }
//                    }
//                }
//            }
//            JSONArray sendDatas = null;
//            if (!eventsData.isEmpty()){
//                sendDatas = JSONArray.fromObject(eventsData);
//            }
//           re_value = sendEventsToPlatform(url, String.valueOf(sendDatas));
//        }
//        return re_value;
//    }

    public static boolean sendEventsToPlatform(String url,String eventData){
        boolean re_value = false;
        JSONObject params = new JSONObject();
        params.put("eventsData",eventData);
        JSONObject returnJson = HttpConnectUtil.httpPost(url,params);
        if(returnJson == null){
            logger.error("发送消息到平台异常,url:" + url + ",内容:" + eventData);
        }else{
            String temp = returnJson.getString("state");
            if("success".equals(temp) ){
                logger.debug("发送消息到平台结束，返回结果："+temp);
                re_value = true;
            }
        }
        return re_value;
    }
}
