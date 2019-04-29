package com.honghe.device.event.Impl;

import com.honghe.device.Host;
import com.honghe.device.command.VirtualDeviceCommand;
import com.honghe.device.dao.DeviceDao;
import com.honghe.device.event.Event;
import com.honghe.device.event.EventSendAbs;
import com.honghe.device.util.CommandUtil;
import com.honghe.device.util.CommonNameUtil;
import com.honghe.device.util.JsonUtil;
import com.honghe.device.util.TomcatPortUtil;
import jodd.util.StringUtil;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.log4j.Logger;
import java.util.*;

/**
 * Created by lyx on 2016-07-14.
 * 处理校宣系统的事件处理
 */
public class EvendSendDss extends EventSendAbs {
    final static Logger logger = Logger.getLogger(EvendSendCampusLocation.class);

    public static final String SYS_DSS = "dss";//校园定位
    private String re_sys = SYS_DSS;

    public EvendSendDss(Event event) {
        super(event);
    }

    /**
     * 获取日志打印对象
     *
     * @return
     */
    @Override
    protected Logger getLogger() {
        return logger;
    }

    /**
     * 处理设备上线事件
     *
     * @param event
     */
    @Override
    protected void eventHostOnline(Event event) {
        hostOnline(event.getStrDeviceToken(), event.getDeviceType());
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

    /**
     * 处理设备发送的事件
     *
     * @param event
     */
    @Override
    protected void eventMessage(Event event) {
        re_sys = SYS_DSS;
        String deviceStr = event.getStrDeviceToken();
        String[] contextArr = event.stringSeparate();

        //如果是考勤信息，则进行以下处理
        if (contextArr != null && contextArr[3].equals("userRecordInfoList")) {

            String data = contextArr[2];
            //对于数字班牌的事件就是学生刷卡信息的推送，数据是要发送到校园定位系统
            re_sys = EvendSendCampusLocation.SYS_CAMPUSLOCATION;
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
            }
        }
        else {
            if(contextArr != null && contextArr[3].equals("ClassName")){
                boolean flag = false;
                    try {
                       flag = DeviceDao.INSTANCE.updateHostName(deviceStr, contextArr[2]);
                    } catch (Exception e) {
                        logger.debug("修改设备名称失败",e);
                    }
                    logger.debug("修改设备" + deviceStr + "的名称为" + contextArr[2] + ":" + flag);
            }
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("event", event.getStrEventType());
            hashMap.put("deviceType",event.getDeviceType());
            hashMap.put("strDeviceToken",event.getStrDeviceToken());
            hashMap.put("StrEventContext()",event.getStrEventContext());
        }

    }

    /**
     * 获取接收事件的系统
     *
     * @return
     */
    @Override
    public String getSys() {
        return re_sys;
    }

    /**
     * 设备上线
     *
     * @param strDeviceToken ip
     */
    private void hostOnline(final String strDeviceToken, String type) {
        final String hostIp = strDeviceToken;
        if (hostIp != null) {
            Long hostid = Long.parseLong("0");
            Host.INSTANCE.registerEventSubscribe(hostIp, type, hostid);
            String networkInterface = System.getProperty("ip");
            if (CommonNameUtil.isUpdateCmdCodeSql(type)) {
                String sqlDownloadStr = "";
                if (!networkInterface.equals("")) {
                    String port = String.valueOf(TomcatPortUtil.INSTANCE.getPort());
                    sqlDownloadStr = "http://" + networkInterface + ":" + port + "/service/sqlTxtDownloadService";
                }

                if (StringUtil.isEmpty(sqlDownloadStr)) {
                    VirtualDeviceCommand command = (VirtualDeviceCommand) CommandUtil.getComamnd(type);
                    if (command != null && command.isDeviceCmdCodeTimeOut(hostIp)) {
                        sqlDownloadStr = sqlDownloadStr + "?cmdcode_update=" + command.getDeviceCmdCodeUpdate(hostIp);
                        logger.debug("下载数据库文件,ip=" + hostIp + "下载地址:" + sqlDownloadStr);
                        command.updateCmdCodeSql(hostIp, sqlDownloadStr);
                    }
                }
            }
        }
    }
}
