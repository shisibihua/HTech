package com.honghe.device.event.Impl;

import com.honghe.device.Host;
import com.honghe.device.command.VirtualDeviceCommand;
import com.honghe.device.dao.DeviceDao;
import com.honghe.device.dao.HostDeviceDao;
import com.honghe.device.event.Event;
import com.honghe.device.event.EventSendAbs;
import com.honghe.device.util.CommandUtil;
import com.honghe.device.util.CommonNameUtil;
import com.honghe.device.util.TomcatPortUtil;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by lyx on 2016-07-14.
 * 处理集控平台的事件发送
 */
public class EvendSendDmanager extends EventSendAbs {
    final static Logger logger = Logger.getLogger(EvendSendDmanager.class);
    public static final String SELF_DEFINED1 = "10";
    public static final String SELF_DEFINED2 = "11";
    public static final String SYS_DMANAGER = "dmanager";//校园定位

    public EvendSendDmanager(Event event) {
        super(event);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

    /**
     * 获取接收事件的系统
     *
     * @return
     */
    @Override
    public String getSys() {
        return "dmanager";
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
        String deviceIp = event.getStrDeviceToken();//设备ip
        String[] contextArr = event.stringSeparate();
        if ("updateTboxChannel".equals(contextArr[3])){ //修改tbox通道名称
            String tboxInfo = contextArr[2];
            String[] tboxInfos = tboxInfo.split(" ");
            String token = tboxInfos[0];
            String deviceName = tboxInfos[1];
            try {
                String newDeviceName = new String(deviceName.split(":")[1].getBytes("ISO-8859-1"), "gb2312");
                String newToken = token.split(":")[1];
                // 只能修改自定义1(10) 和 自定义2(11)设备的通道名称
                if (SELF_DEFINED1.equals(newToken) || SELF_DEFINED2.equals(newToken)) {
                    Map deviceInfo = DeviceDao.INSTANCE.getHostInfoByIp(deviceIp);
                    int hostId = Integer.parseInt(deviceInfo.get("host_id").toString());
                    HostDeviceDao.INSTANCE.updateTboxDeviceName(newDeviceName, newToken, hostId);
                    logger.debug("修改tbox通道名称为："+newDeviceName);
                }
            } catch (UnsupportedEncodingException e) {
                logger.debug("修改tbox通道名称失败",e);
            }
        }
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

                if (sqlDownloadStr != null && !sqlDownloadStr.equals("")) {
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
