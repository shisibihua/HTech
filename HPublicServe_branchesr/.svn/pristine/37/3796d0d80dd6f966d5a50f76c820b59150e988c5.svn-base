package com.honghe.device.command.impl;

import com.hht.DeviceManager.DeviceManager;
import com.hht.DeviceManager.operationRequest.OperationRequest;
import com.hht.DeviceManager.operationRequest.hhtc.*;
import com.hht.DeviceManager.operationRequest.record.OperationRequestGetVolume;
import com.hht.global.GlobalDefinitions;
import com.honghe.device.Host;
import com.honghe.device.command.VirtualDeviceCommand;
import com.honghe.device.dao.CmdCodeUpdateDao;
import com.honghe.device.dao.DeviceDao;
import com.honghe.device.dao.SpecDao;
import com.honghe.device.handler.DefaultResponseHandler;
import com.honghe.device.util.DateUtil;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by lyx on 2016-01-12.
 * 大屏设备执行的操作命令
 */
public class ScreenCommand implements VirtualDeviceCommand {

    private final static Logger logger = Logger.getLogger(ScreenCommand.class);
//    public final static ScreenCommand INSTANCE = new ScreenCommand();


    /**
     * 开机
     *
     * @param ip
     * @return
     */
    @Override
    public Boolean boot(String ip) {
        try {
            Map<String, String> mac = Host.INSTANCE.getMac(ip);
            if (mac != null && mac.size() > 0) {
                String hhtcMac = mac.get("host_hhtcmac");
                String hostMac = mac.get("host_mac");
                if (hhtcMac != null && !hhtcMac.equals("") && !hhtcMac.equals("null")) {
                    for (int i = 0; i < 3; i++) {
                        DeviceManager.WakeUpDevice(ip, hhtcMac);
                    }
                }
                for (int i = 0; i < 3; i++) {
                    DeviceManager.WakeUpDevice(ip, hostMac);
                    new Thread().currentThread().sleep(500);
                }
            }
            return true;
        } catch (Exception e) {
            logger.error("开机异常ip为" + ip, e);
            return false;
        }
    }

    /**
     * 关机
     *
     * @param ip
     * @param cmdCode 关机命令标识
     * @return
     */
    @Override
    public boolean shutdown(String ip, String cmdCode) {
        boolean re_value = false;
        try {
            DeviceManager.invoke(ip, new HHTCOperationRequestSetPower(cmdCode));
            re_value = true;
            logger.debug("关机命令ip为：" + ip + ",cmdCode为：" + cmdCode + ",re_value为:" + re_value);
        } catch (Exception e) {
            logger.error("关机异常ip为" + ip + ",cmdCode为：" + cmdCode + ",re_value为:" + re_value, e);
        }
        return re_value;
    }

    /**
     * 查询音量
     *
     * @param ip 设备的ip地址
     * @param ext 扩展字段
     * @return
     */
    @Override
    public Integer getVolume(String ip, String ext) {
        Integer re_value = 0;
        try {
            re_value = (Integer) DeviceManager.invoke(ip, new OperationRequestGetVolume());
            logger.debug("查询音量为：" + re_value + ",ip为" + ip + ",ext:" + ext);

        } catch (Exception e) {
            logger.error("查询音量异常ip为：" + ip + ",ext：" + ext, e);
        }
        return re_value;
    }

    /**
     * 切换信号源
     *
     * @param ip
     * @param cmdCode 信号源
     * @return
     */
    @Override
    public boolean changeSignal(String ip, String cmdCode) {
        boolean re_value = false;
        try {
            DeviceManager.invoke(ip, new HHTCOperationRequestSetChannel(cmdCode));
            logger.debug("切换信号源ip为" + ip + ",signal为：" + cmdCode);
            re_value = true;
        } catch (Exception e) {
            logger.error("切换信号源异常ip为" + ip + ",signal为：" + cmdCode, e);
        }
        return re_value;
    }


    /**
     * 获取设备信息
     *
     * @param ip
     * @return
     */
    @Override
    public Map<String, String> getInfo(String ip) {
        Map re_value = Collections.EMPTY_MAP;
        try {
            Map<String, String> res = (Map<String, String>) DeviceManager.invoke(ip, new HHTCOperationRequestGetAllState());
            if (res != null) {
                re_value = res;
            }
            logger.debug("获取设备信息为" + res == null ? "空" : res.toString());
        } catch (Exception e) {
            logger.error("获取设备信息异常ip为" + ip, e);
        }
        return re_value;
    }

    /**
     * 发送消息
     *
     * @param
     * @return
     */
    @Override
    public boolean sendMessage(String ip,String Content,String NewsId,String UserName,String DisplayMode,String IsLoop,
                               String CycleMode,String ExecValue,String StartDate,String EndDate,String StartTime,String EndTime,
                               String FontSize,String FontColor,String Font,String Control,String nType) {
        boolean re_value = false;
        String hostIp = ip!=null?ip:"";
        Map<String, String> hostMap = new HashMap<>();
        hostMap.put("Content", Content!= null ? Content:"");//内容
        hostMap.put("NewsId", NewsId!= null ? NewsId: "");//消息id
        hostMap.put("UserName", UserName!= null ?UserName: "");//用户名
        hostMap.put("DisplayMode", DisplayMode!= null ? DisplayMode: "");//显示方式
        hostMap.put("IsLoop", IsLoop!= null ?IsLoop: "");  //      信息一直为循环显示
        hostMap.put("CycleMode", CycleMode!= null ? CycleMode: "");//循环方式
        hostMap.put("ExecValue", ExecValue!= null ? ExecValue : "");//循环值
        hostMap.put("StartDate", StartDate!= null ? StartDate: "");//开始时间
        hostMap.put("EndDate", EndDate!= null ? EndDate: "");
        hostMap.put("StartTime", StartTime!= null ? StartTime: "");
        hostMap.put("EndTime", EndTime != null ? EndTime: "");
        hostMap.put("FontSize", FontSize != null ? FontSize: "");
        hostMap.put("FontColor", FontColor != null ? FontColor: "");
        hostMap.put("Font", Font != null ? Font: "");
        hostMap.put("Control", Control != null ? Control: "");
        hostMap.put("Ntype", nType != null ? nType: "");
        try {
            HHTCOperationRequestNewsManager hhtcOperationRequestNewsManager = new HHTCOperationRequestNewsManager(hostMap);
            DeviceManager.startInvoke(hostIp, "", hhtcOperationRequestNewsManager, new DefaultResponseHandler());
            logger.debug("发送消息ip为" + hostIp + "内容为:" + hostMap.toString());
            re_value = true;
        } catch (Exception e) {
            logger.error("发送消息异常ip为" + hostIp, e);
        }
        return re_value;
    }

    /**
     * 获取截图
     *
     * @param ip
     * @return
     */
    @Override
    public Object getPictrue(String ip) {
        Object re_value = "";
        try {
            HHTCOperationRequestScreenShot hhtcOperationRequestScreenShot = new HHTCOperationRequestScreenShot();
            re_value = DeviceManager.invoke(ip, hhtcOperationRequestScreenShot);
        } catch (Exception e) {
            logger.error("获取桌面截图异常ip为" + ip, e);
        }
        return re_value;
    }

    /**
     * 更新设备的命令表的地址
     *
     * @param ip
     * @param sqlStr
     */
    @Override
    public Boolean updateCmdCodeSql(String ip, String sqlStr) {
        boolean re_value = false;
        try {
            HHTCOperationRequestUpdateDB hhtcOperationRequestUpdateDB = new HHTCOperationRequestUpdateDB(sqlStr);
            DeviceManager.invoke(ip, hhtcOperationRequestUpdateDB);
            re_value = true;
        } catch (Exception e) {
            logger.error("获取更新设备的命令表异常ip为" + ip, e);
        }
        return re_value;
    }

    /**
     * 获取设备的命令表是否过期标识符
     *
     * @param ip
     * @return
     */
    @Override
    public boolean isDeviceCmdCodeTimeOut(String ip) {
        boolean flag = true;
        try {
            HHTCOperationRequestUpdateDB hhtcOperationRequestUpdateDB = new HHTCOperationRequestUpdateDB("cmdcodeupdate");
            Map<String, String> hMap = (Map<String, String>) DeviceManager.invoke(ip, hhtcOperationRequestUpdateDB);
            if (hMap != null && hMap.containsKey("Type")) {
                String cmdCodeUpdate = hMap.get("Type").toString();
                flag = CmdCodeUpdateDao.INSTANCE.isDeviceCmdCodeTimeOut(ip, cmdCodeUpdate);
            }
            logger.debug("获取设备命令表标识符cmdcodeupdate并进行比较,ip为:" + ip + "结果为:" + hMap.toString());
        } catch (Exception e) {
            logger.error("获取设备命令表标识符cmdcodeupdate并进行比较异常,ip为:" + ip, e);
        }
        return flag;
    }


    /**
     * 获取设备所有的命令
     *
     * @param deviceType
     * @return
     */
    @Override
    public Map getCmd(String deviceType) {
        List<Map> dspecIds = SpecDao.INSTANCE.getDspecIds(deviceType);
        Map<String, List<Map<String, String>>> resmap = new HashMap<String, List<Map<String, String>>>();
        if (dspecIds != null && dspecIds.size() > 0) {
            for (int i = 0; i < dspecIds.size(); i++) {
                //System.out.println(dspecIds.get(i));
                List<Map<String, String>> cmdList = SpecDao.INSTANCE.getSpecCmd(dspecIds.get(i).get("dspec_id").toString());
                resmap.put(dspecIds.get(i).get("dspec_id").toString(), cmdList);
            }
        }
        return resmap;
    }

    /**
     * 推送地址
     *
     * @param ip
     * @param addrStr
     * @return
     */
    @Override
    public Boolean sendAddress(String ip, String addrStr) {
        boolean re_value = false;
        try {
            HHTCOperationRequestEventSubscription hhtcOperationRequestEventSubscription = new HHTCOperationRequestEventSubscription(addrStr);
            DeviceManager.invoke(ip, hhtcOperationRequestEventSubscription);
            logger.debug("推送地址ip为" + ip + "地址为:" + addrStr);
            re_value = true;
        } catch (Exception e) {
            logger.error("推送地址异常ip为" + ip, e);
        }
        return re_value;
    }

    /**
     * 获取某一设备的命令表更新的标识符
     *
     * @param ip
     * @return
     */
    @Override
    public String getDeviceCmdCodeUpdate(String ip) {
        String str = "";
        try {
            str = CmdCodeUpdateDao.INSTANCE.getDeviceCmdCodeUpdate(ip);
            logger.debug("获取设备命令表标识符cmdcodeupdate，ip为:" + ip + "结果为:" + str);
        } catch (Exception e) {

            logger.error("获取设备命令表标识符cmdcodeupdate异常,ip为:" + ip, e);
        }

        return str;
    }


    /**
     * 音响模式切换
     *
     * @param ip
     * @param cmdCode 音响模式标识
     * @return
     */
    public boolean changeAudioMode(String ip, String cmdCode) {
        try {
            Object res = DeviceManager.invoke(ip, new HHTCOperationRequestSetAudioMode(cmdCode));
            logger.debug("切换音响模式ip为" + ip + ",audio_mode为：" + cmdCode);
            return true;
        } catch (Exception e) {
            logger.error("切换音响模式ip为" + ip + ",audio_mode为：" + cmdCode, e);
            return false;
        }
    }


    /**
     * 音量调节
     *
     * @param ip
     * @param cmdCode
     * @return
     */
    public boolean changeAudioControl(String ip, String cmdCode) {
        try {
            Object res = DeviceManager.invoke(ip, new HHTCOperationRequestSetVolume(Integer.parseInt(cmdCode)));
            logger.debug("音量调节ip为" + ip + ",audio为：" + cmdCode);
            return true;
        } catch (Exception e) {
            logger.error("音量调节异常ip为" + ip + ",audio为：" + cmdCode, e);
            return false;
        }
    }


    /**
     * 切换远程节能
     * @param ip
     * @param cmdCode 节能模式标识
     * @return
     */
    public boolean changeRemoteEnergy(String ip, String cmdCode) {
        boolean re_value = false;
        try {
            Object res = DeviceManager.invoke(ip, new HHTCOperationRequestSetEnergyMode(cmdCode));
            logger.debug("切换远程节能ip为" + ip + ",energy_Mode：" + cmdCode);
            re_value = true;
        } catch (Exception e) {
            logger.error("切换远程节能异常ip为" + ip + ",energy_Mode：" + cmdCode, e);
        }
        return re_value;
    }


    /**
     * 设置系统时间
     *
     * @param ip
     * @param deviceType
     * @return
     */
    public Boolean setSysTime(String ip, String deviceType) {
        boolean re_value = false;
        try {
            Calendar calendar = DateUtil.nowUTC();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            int sencond = calendar.get(Calendar.SECOND);
            if (deviceType.equals(GlobalDefinitions.DeviceType_HHTC)) {
                DeviceManager.startInvoke(ip, "", new HHTCOperationRequestSetSysTime(year, month, day, hour, minute, sencond), new DefaultResponseHandler());
            }
            re_value = true;
        } catch (Exception e) {
            logger.error("设置系统时间异常", e);
        }
        return re_value;
    }

    /**
     * 备份
     *
     * @param ip 地址
     * @return
     */
    public Boolean backup(String ip) {
        boolean re_value = false;
        try {
            HHTCOperationRequestSystemBackup hhtcOperationRequestSystemBackup = new HHTCOperationRequestSystemBackup();
            DeviceManager.startInvoke(ip, "", hhtcOperationRequestSystemBackup, new DefaultResponseHandler());
            logger.debug("发送备份命令,ip为:" + ip);
            re_value = true;
        } catch (Exception e) {
            logger.error("发送备份命令异常,ip为:" + ip, e);
        }
        return re_value;
    }

    /**
     * 还原
     *
     * @param ip 地址
     * @return
     */
    public Boolean recovery(String ip) {
        boolean re_value = false;
        try {
            HHTCOperationRequestSystemRecovery hhtcOperationRequestSystemRecovery = new HHTCOperationRequestSystemRecovery();
            DeviceManager.startInvoke(ip, "", hhtcOperationRequestSystemRecovery, new DefaultResponseHandler());
            logger.debug("发送还原命令,ip为:" + ip);
            re_value = true;
        } catch (Exception e) {
            logger.error("发送还原命令异常,ip为:" + ip, e);
        }
        return re_value;
    }

    /**
     * 清理垃圾
     *
     * @param ip 地址
     * @return
     */
    public Boolean clearRubish(String ip) {
        boolean re_value = false;
        try {
            HHTCOperationRequestOneKeyClean hhtcOperationRequestOneKeyClean = new HHTCOperationRequestOneKeyClean();
            DeviceManager.startInvoke(ip, "", hhtcOperationRequestOneKeyClean, new DefaultResponseHandler());
            logger.debug("发送清理垃圾命令,ip为:" + ip);
            re_value = true;
        } catch (Exception e) {
            logger.error("发送清理垃圾命令异常,ip为:" + ip, e);
        }
        return re_value;
    }

    /**
     * 一键安装软件
     * @param ip   大屏ip地址
     * @param softName 安装软件名称
     * @param softPath 安装软件地址
     * @param ftpUsername
     * @param ftpPassword
     * @param serverIp
     * @param port
     * @return
     */

    public Boolean oneKeyInstall(String ip, String softName, String softPath, String ftpUsername, String ftpPassword, String serverIp, String port) {
        boolean re_value = false;
        String userName = ftpUsername==null?"":ftpUsername;
        String password = ftpPassword==null?"":ftpPassword;
        String serverip = serverIp==null?"":serverIp;
        String portNum = port==null?"":port;
        try {
            HHTCOperationRequestOneKeyInstall hhtcOperationRequestOneKeyInstall = new HHTCOperationRequestOneKeyInstall(softName, softPath, userName, password, serverip, portNum);
            DeviceManager.startInvoke(ip, "", hhtcOperationRequestOneKeyInstall, new DefaultResponseHandler());
            logger.debug("发送一键安装软件命令,ip为:" + ip + ",softName:" + softName + ",softPath:" + softPath + ",ftpUsername:" + userName + ",ftpPassword:" + password + ",serverip:" + serverip + ",port:" + portNum);
            re_value = true;
        } catch (Exception e) {
            logger.error("发送一键安装软件命令异常,ip为:" + ip + ",softName:" + softName + ",softPath:" + softPath + ",ftpUsername:" + userName + ",ftpPassword:" + password + ",serverip:" + serverip + ",port:" + portNum, e);
        }
        return re_value;
    }

    /**
     * 一键卸载软件
     *
     * @param ip 大屏ip
     * @param softName 软件名称
     * @return
     */
    public Boolean oneKeyUninstall(String ip, String softName) {
        boolean re_value = false;
        try {
            HHTCOperationRequestOneKeyUninstall hhtcOperationRequestOneKeyUnstall = new HHTCOperationRequestOneKeyUninstall(softName);
            DeviceManager.startInvoke(ip, "", hhtcOperationRequestOneKeyUnstall, new DefaultResponseHandler());
            logger.debug("发送一键卸载软件命令,ip为:" + ip + ",softName:" + softName);
            re_value = true;
        } catch (Exception e) {
            logger.error("发送一键卸载软件命令异常,ip为:" + ip + ",softName:" + softName, e);
        }
        return re_value;
    }


    /**
     * 切换频道
     *
     * @param ip          ip
     * @param cmdCode  频道号
     * @param channelName 信号源
     * @return
     */
    public Boolean atvOrDtv(String ip, String cmdCode, String channelName) {
        boolean re_value = false;
        try {
            HHTCOperationRequestSetTVChannel hhtcOperationRequestSetTVChannel = new HHTCOperationRequestSetTVChannel(cmdCode, channelName);
            DeviceManager.startInvoke(ip, "", hhtcOperationRequestSetTVChannel, new DefaultResponseHandler());
            logger.debug("切换频道,ip为:" + ip + ",channelNum:" + cmdCode + ",channelName:" + channelName);
            re_value = true;
        } catch (Exception e) {
            logger.error("切换频道异常,ip为:" + ip + ",channelNum:" + cmdCode + ",channelName:" + channelName, e);
        }
        return re_value;
    }

    /**
     * 打铃
     *
     * @param ip 地址
     * @return
     */
    public Boolean ring(String ip) {
        boolean re_value = false;
        try {
            HHTCOperationRequestRing hhtcOperationRequestRing = new HHTCOperationRequestRing();
            DeviceManager.invoke(ip, hhtcOperationRequestRing);
            logger.debug("设备打铃,ip为:" + ip);
            re_value = true;
        } catch (Exception e) {
            logger.error("打铃异常,ip为:" + ip, e);
        }
        return re_value;
    }

    /**
     * 获取当前屏幕触控和面板按键锁定的状态
     *
     * @param ip 主机地址
     * @return 返回结果  "ScreenLock "：屏幕锁定；”ScreenUnlock”：屏幕未锁定
     */
    public Map<String, String> getScreenLockMode(String ip) {

        Map<String, String> re_value = Collections.EMPTY_MAP;
        try {
            re_value = (Map<String, String>) DeviceManager.invoke(ip, new HHTCOperationRequestGetScreenLock());
            logger.debug("获取屏幕触控屏锁定状态主机ip：" + ip);
        } catch (Exception e) {
            logger.error("获取屏幕触控锁定状态异常，主机ip：" + ip,e);
        }
        return re_value;
    }

    /**
     * 设置当前屏幕触控和面板按键锁定状态
     *
     * @param ip      主机地址
     * @param cmdCode 操作命令  "ScreenLock"或者” ScreenUnlock”
     * @return 返回结果集 " ScreenLock "：屏幕锁定；”ScreenUnlock”：屏幕未锁定
     */
    public Map<String, String> setScreenLockMode(String ip, String cmdCode) {
        Map<String, String> re_value = Collections.EMPTY_MAP;
        try {
            re_value = (Map<String, String>) DeviceManager.invoke(ip, new HHTCOperationRequestSetScreenLock(cmdCode));
            logger.debug("设置屏幕触控锁定状态，主机ip：" + ip + ",_cmdCode:" + cmdCode);
        } catch (Exception e) {
            logger.error("设置屏幕触控锁定状态异常，主机ip：" + ip + ",_cmdCode:" + cmdCode,e);
        }
        return re_value;
    }

    /**
     * 获取已添加设备的通道交集
     * @param deviceType 设备类型
     * @return
     */
    public List<Map<String, Object>> getAllChannelIntersectionByType(String deviceType){
        List<Map<String, Object>> res = new ArrayList<>();
        try {
            res = DeviceDao.INSTANCE.getAllChannelIntersectionByType(deviceType);
        } catch (Exception e) {
            logger.error("获取已添加设备的通道交集", e);
        }
        return res;
    }
    /**
     * 修改大屏设备的名称
     * @param ip 设备ip
     * @param hostName 设备名称
     * @return
     */
    public Boolean updateHhtcHostName(String ip, String hostName) {
        boolean re_value = false;
        try {
            DeviceManager.invoke(ip, new HHTCOperationRequestSetHostName(hostName));
            re_value = DeviceDao.INSTANCE.updateHostName(ip, hostName);
            logger.debug("修改设备" + ip + "的名称为" + hostName + ":" + re_value);
        } catch (Exception e) {
            logger.error("修改设备名称异常", e);
        }
        return re_value;

    }

    /**
     * 一键启用/禁用WiFi
     *
     * @param ip      主机地址
     * @param cmdCode 操作命令  "OneKeyDisableWifi"
     * @return 返回结果集 " OneKeyDisableWifi "：禁用wiff；
     */
    public Map<String, String> WiffControl(String ip, String cmdCode) {
        Map<String, String> re_value = Collections.EMPTY_MAP;
        try {
            // TODO: 2017/8/9 安卓确认是否有启用wiff
//            re_value = (Map<String, String>) DeviceManager.invoke(ip, new HHTCOperationRequestOneKeyDisableWifi(cmdCode));
            logger.debug("设置一键禁用屏幕wiff，主机ip：" + ip + ",_cmdCode:" + cmdCode);
        } catch (Exception e) {
            logger.error("设置一键禁用屏幕wiff异常，主机ip：" + ip + ",_cmdCode:" + cmdCode);
        }
        return re_value;
    }

    /**
     * 设置当前屏幕启用/禁用usb
     *
     * @param ip      主机地址
     * @param cmdCode 操作命令  "OneKeyDisableUsb" 禁用USB存储 / "OneKeyEnableUsb" 启用USB存储
     * @return 返回结果集 "
     */
    public Map<String, String> HSBControl(String ip, String cmdCode) {
        Map<String, String> re_value = Collections.EMPTY_MAP;
        try {
            // TODO: 2017/8/9
//            re_value = (Map<String, String>) DeviceManager.invoke(ip, new HHTCOperationRequestOneKeyUsb(cmdCode));
            logger.debug("设置屏幕usb状态，主机ip：" + ip + ",_cmdCode:" + cmdCode);
        } catch (Exception e) {
            logger.error("设置屏幕usb状态异常，主机ip：" + ip + ",_cmdCode:" + cmdCode,e);
        }
        return re_value;
    }
}
