package com.honghe.device;

import com.hht.DeviceManager.DeviceManager;
import com.hht.DeviceManager.operationRequest.antenna.AntennaEventSubscribe;
import com.hht.DeviceManager.operationRequest.hhtc.HHTCOperationRequestEventSubscription;
import com.hht.DeviceManager.operationRequest.hhtc.HHTCOperationRequestUpdateDB;
import com.hht.DeviceManager.operationRequest.hhtdsb.DigSignBoardEventSubscribe;
import com.hht.DeviceManager.operationRequest.hhtpr.HHTPROperationRequestEventSubscription;
import com.hht.DeviceManager.operationRequest.hhtwb.WhiteBoardEventSubscription;
import com.hht.DeviceManager.operationRequest.record.OperationRequestEventSubscribe;
import com.hht.device.Device;
import com.hht.device.HHTCDevice;
import com.hht.device.OnvifDevice;
import com.hht.global.GlobalDefinitions;
import com.honghe.device.dao.DeviceDao;
import com.honghe.device.util.CommonNameUtil;
import com.honghe.device.util.TomcatPortUtil;
import org.apache.log4j.Logger;

import java.util.*;


/**
 * Created by zhanghongbin on 2014/11/17.
 */
public class Host {
    public final static Host INSTANCE = new Host();
    final static Logger logger = Logger.getLogger(Host.class);

    // todo 删除
//    注册
//    public boolean regist(Device device, String hostId, String hostName, String userName, String password, String desc) {
//        boolean re_value = false;
//        try {
//            Hashtable<String, Object> params = new Hashtable();
//            params.put("host_name", hostName);
//            params.put("host_id", hostId);
//            params.put("host_desc", "");
//            device.SetDeviceExtensionInfo(params);
//            if (device instanceof HHTCDevice) {
//
//            } else if (device instanceof OnvifDevice) {
//                OnvifDevice onvifDevice = (OnvifDevice) device;
//                try {
//                    onvifDevice.setUserIDAndPW(userName, password);
//                } catch (Exception e) {
//                    logger.error("", e);
//                }
//            }
//            DeviceManager.registeDevice(device);
//            re_value = true;
//        } catch (Exception e) {
//            logger.error("", e);
//            re_value = false;
//        }
//        return re_value;
//    }

    public final boolean update(String ip, String userName, String password) {
        boolean flag = true;
        Device device = DeviceManager.getDevice(ip);
        if (device == null) return false;
        try {
            device.getClass().getMethod("setUserID", String.class).invoke(device, userName);
            device.getClass().getMethod("setPassword", String.class).invoke(device, password);
            logger.debug("修改用户名和密码为：" + userName + "," + password);
        } catch (Exception e) {
            logger.error("修改用户名密码异常", e);
            flag = false;
        }
        return flag;
    }

    // todo 删除
//    public final Boolean updateHostName(String ip, String hostName) {
//        try {
//            get(ip).GetDeviceExtensionInfo().put("host_name", hostName);
//            logger.debug("修改设备名称为：" + hostName);
//            return true;
//        } catch (Exception e) {
//            logger.error("修改设备名称异常", e);
//            return false;
//        }
//    }

    /**
     * 获取选定设备信息
     * @param hostIpStr 设备ip，多个用逗号隔开
     * @return
     */
    public  List<Device> getDevicesDetail(String hostIpStr){
        List<Device> deviceList;
        try {
            deviceList = DeviceManager.getDevicesInfo(hostIpStr);
        } catch (Exception e) {
            logger.error("通过ip串获取设备信息异常"+e);
            deviceList = Collections.EMPTY_LIST;
        }
        return deviceList;
    }
    public final String getStatus(String ip) {
        String deviceStatus = GlobalDefinitions.OnlineStatus_Offline;
        try {
            Device device = DeviceManager.getDevice(ip);
            if(device!=null){
                if (device.getDeviceStatus().equals(GlobalDefinitions.OnlineStatus_Online)) {
                    deviceStatus = GlobalDefinitions.OnlineStatus_Online;
                }
            }
            logger.debug("当前设备状态为：在线");
        } catch (Exception e) {
            logger.error("获取设备状态异常", e);
        }
        return deviceStatus;
    }

    public final String getHostName(Device device) {
        Object obj = this.getValue(device, "getHostName");
        if (obj != null) {
            return obj.toString();
        }
        return "";
    }


    private final Object getValue(Device device, String methodName) {
        try {
            return device.getClass().getMethod(methodName).invoke(device);
        } catch (Exception e) {
            logger.error("获取设备名称失败", e);
            return null;
        }
    }

    public final boolean isOnline(final String ip) {
        String status = this.getStatus(ip);
        return status.equals(GlobalDefinitions.OnlineStatus_Online) ? true : false;
    }

    public final Device get(String ip) {
        return DeviceManager.getDevice(ip);
    }

    public final String getIp(String token) {
        Device device = DeviceManager.getDevice(token);
        if (device == null) return null;
        return device.getIP();
    }

    public final Map<String, Object> getExtensionInfo(String ip) {
        Device device = get(ip);
        if (device != null) {
            return device.GetDeviceExtensionInfo();
        }
        return Collections.EMPTY_MAP;
    }

    /**
     * 删除设备
     *
     * @param ip
     */
    public final boolean delete(String ip) {
        boolean re_value = false;
        try {
            re_value = DeviceManager.removeDevice(ip);
        } catch (Exception e) {
            logger.error("删除设备异常：re_value为："+re_value, e);
        }
        return re_value;
    }

    /**
     * 根据ip获取token
     *
     * @param ip
     * @return
     */
    public final String getToken(String ip) {
        return ip;
    }

    /*public final boolean check(String tokenOrIp, String userName, String password) {
        Device device = null;
        //从发现池里找
        if (tokenOrIp.indexOf("-") != -1) {
            Map<String, Device> discovered = this.discovered();
            if (discovered.containsKey(tokenOrIp)) {
                device = discovered.get(tokenOrIp);
            }
        } else {
            device = this.get(tokenOrIp);
        }
        if (device != null) {
            try {
                device.getClass().getMethod("setUserID", String.class).invoke(device, userName);
                device.getClass().getMethod("setPassword", String.class).invoke(device, password);
            } catch (Exception e) {
                return true;
            }
            return device.AuthTest();
        }
        return false;
    }*/

    /**
     * 获取设备
     *
     * @param device
     * @return
     */
    public String getLocalModel(Device device) {
        try {
            String localModel = device.getLocalModel();
            logger.debug("获取设备localModel为:" + localModel);
            return localModel;
        } catch (Exception e) {
            logger.error("获取设备localModel异常", e);
            return "";
        }
    }

    /**
     * 获取mac地址
     *
     * @param ip String ip
     * @return
     */
    public Map getMac(String ip) {
        try {
            return DeviceDao.INSTANCE.getHostMac(ip);
        } catch (Exception e) {
            logger.error("获取mac地址异常", e);
            return new HashMap();
        }
    }

    /**
     * 获取IP地址
     *
     * @param device
     * @return
     */
    public String getMac(Device device) {
        try {
            return device.getMac();
        } catch (Exception e) {
            logger.error("获取mac地址异常", e);
            return "";
        }
    }

    //更新设备的命令表的地址
    public void updateCmdCodeSql(String ip, String sqlStr) {
        try {
            HHTCOperationRequestUpdateDB hhtcOperationRequestUpdateDB = new HHTCOperationRequestUpdateDB(sqlStr);
            DeviceManager.invoke(ip, hhtcOperationRequestUpdateDB);
        } catch (Exception e) {
            logger.error("更新设备的命令表的地址异常", e);
        }
    }

    //获取设备的命令表是否过期标识符
    public boolean isDeviceCmdCodeTimeOut(String ip) {
        boolean flag = true;
        try {
            HHTCOperationRequestUpdateDB hhtcOperationRequestUpdateDB = new HHTCOperationRequestUpdateDB("cmdcodeupdate");
            Map<String, String> hMap = (Map<String, String>) DeviceManager.invoke(ip, hhtcOperationRequestUpdateDB);
            if (hMap != null && hMap.containsKey("Type")) {
                String cmdCodeUpdate = hMap.get("Type").toString();
                //flag = newsService.isDeviceCmdCodeTimeOut(ip,cmdCodeUpdate);
            }
        } catch (Exception e) {
            logger.error("获取设备命令表标识符cmdcodeupdate异常", e);
        }
        return flag;
    }

    //推送订阅地址
    public void sendAddress(String ip, String addrStr) {
        try {
            HHTCOperationRequestEventSubscription hhtcOperationRequestEventSubscription = new HHTCOperationRequestEventSubscription(addrStr);
            DeviceManager.invoke(ip, hhtcOperationRequestEventSubscription);
        } catch (Exception e) {
            logger.error("推送订阅地址异常", e);
        }
    }

    //注册事件订阅url
    public Long registerEventSubscribe(String ip, String deviceType, Long hostId) {
        Long re_value=0L;
        String networkInterface = "";
        networkInterface = System.getProperty("ip");
        String url = "";
        if (!networkInterface.equals("")) {
            String port = String.valueOf(TomcatPortUtil.INSTANCE.getPort());
            url = "http://" + networkInterface + ":" + port + "/service/eventConsumerService";
        }
        boolean flag = registerEventSubscribe(ip, url, deviceType, logger);
        re_value = Long.valueOf(flag ? hostId : 0);
        return re_value;
    }


    /**
     * 根据类型注册事件通知
     *
     * @param ip
     * @param url
     * @param deviceType
     * @param logger
     * @return
     */
    private final boolean registerEventSubscribe(String ip, String url, String deviceType, Logger logger) {
        boolean re_value = false;
        try {
            if (CommonNameUtil.HHREC.toString().equals(deviceType)) {
                logger.debug("录播事件定阅url:" + url + "录播主机ip为：" + ip);
                OperationRequestEventSubscribe operationRequestEventSubscribe = new OperationRequestEventSubscribe(url, 0, new ArrayList<String>(), "");
                DeviceManager.invoke(ip, operationRequestEventSubscribe);
            } else if (CommonNameUtil.HHTC.toString().equals(deviceType)) {
                HHTCOperationRequestEventSubscription hhtcOperationRequestEventSubscription = new HHTCOperationRequestEventSubscription(url);
                DeviceManager.invoke(ip, hhtcOperationRequestEventSubscription);
            } else if (CommonNameUtil.HTPR.toString().equals(deviceType)) {
                HHTPROperationRequestEventSubscription hhtprOperationRequestEventSubscription = new HHTPROperationRequestEventSubscription(url);
                DeviceManager.invoke(ip, hhtprOperationRequestEventSubscription);
            } else if (CommonNameUtil.HHTWB.toString().equals(deviceType)) {
                WhiteBoardEventSubscription obj = new WhiteBoardEventSubscription(url);
                DeviceManager.invoke(ip, obj);
            } else if (CommonNameUtil.HHTDC.toString().equals(deviceType)) {
                DigSignBoardEventSubscribe obj = new DigSignBoardEventSubscribe(url);
                DeviceManager.invoke(ip, obj);
            } else if (CommonNameUtil.HHTPA.toString().equals(deviceType)) {
                AntennaEventSubscribe obj = new AntennaEventSubscribe(url, "");
                DeviceManager.invoke(ip, obj);
            }
            logger.debug("事件定阅url:" + url + ",设备ip为:" + ip + ",设备类型为:" + deviceType);
            re_value = true;
        } catch (Exception e) {
            logger.error("事件定阅异常url:" + url + ",设备ip为:" + ip + ",设备类型为:" + deviceType, e);
        }
        return re_value;
    }
    //同步当前有效信息
//    public void sendNewsInitial(String ip)
//    {
//        Map<String,String> hostInfo= HostDao.INSTANCE.getHostInfoByIp(ip);
//        String hostId = hostInfo.get("host_id").toString();
//        String networkInterface = System.getProperty("NetworkInterface", "");
//        String port="8080";
//        String url = "http://" + networkInterface + ":" + port + "/hhtc/getNews?hostId="+hostId;
//    }

    /**
     * 注册事件通知地址
     * @param ip   ip地址
     * @param url  为http://ip:port/xxxx
     * @param port 端口号
     * @param sys
     * @return
     */
    public Boolean registEventAddr(String ip, String url, String port, String sys) {
        boolean re_value = false;
        try {
            if (ip.equals("")) {
                ip = System.getProperty("ip");
            }
            if (port.equals("")) {
                port = "8080";
            }
            url = "http://" + ip + ":" + port + "/" + url;
            String key = sys + ":" + ip;
            SubScribePool.addUrl(key, url);
            logger.debug("平台定阅的地址为：" + url);

            SubScribePool.writeProperties(key,url);
            re_value = true;
        } catch (Exception e) {
            logger.error("平台定阅异常", e);
        }
        return re_value;
    }
}
