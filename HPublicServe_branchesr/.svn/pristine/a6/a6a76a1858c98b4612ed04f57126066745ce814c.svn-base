package com.honghe.device.command.impl;

import com.hht.DeviceManager.DeviceManager;
import com.hht.DeviceManager.operationRequest.hhtc.HHTCOperationRequestSetSysTime;
import com.hht.DeviceManager.operationRequest.hhtpr.*;
import com.hht.global.GlobalDefinitions;
import com.honghe.device.Host;
import com.honghe.device.command.VirtualDeviceCommand;
import com.honghe.device.dao.CmdCodeUpdateDao;
import com.honghe.device.dao.SpecDao;
import com.honghe.device.handler.DefaultResponseHandler;
import com.honghe.device.util.DateUtil;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by lyx on 2016-01-12.
 * 投影机设备操作命令
 */
public class ProjectorCommand implements VirtualDeviceCommand {

    private final static Logger logger = Logger.getLogger(ProjectorCommand.class);
    public final static ProjectorCommand INSTANCE = new ProjectorCommand();


    /**
     * 主机设备开机
     *
     * @param ip
     * @return
     */
    @Override
    public Boolean boot(String ip) {
        boolean re_value = false;
        try {
            Map<String, String> mac = Host.INSTANCE.getMac(ip);
            if (mac != null && mac.size() > 0) {
                String hhtcMac = mac.get("host_hhtcmac");
                String hostMac = mac.get("host_mac");
                if (hhtcMac != null && !hhtcMac.equals("") && !hhtcMac.equals("null")) {
                    for (int i = 0; i < 3; i++) {
                        DeviceManager.WakeUpDevice(ip, hhtcMac);
                        new Thread().currentThread().sleep(200);
                    }
                }
                for (int i = 0; i < 3; i++) {
                    DeviceManager.WakeUpDevice(ip, hostMac);
                    new Thread().currentThread().sleep(200);
                }
            }
            re_value = true;
        } catch (Exception e) {
            logger.error("开机异常ip为" + ip, e);
        }
        return re_value;
    }

    /**
     * 主机设备关机
     *
     * @param ip
     * @param cmdCode
     * @return
     */
    @Override
    public boolean shutdown(String ip, String cmdCode) {
        boolean re_value = false;
        try {
            DeviceManager.invoke(ip, new HHTPROperationRequestSetTerminalPower(cmdCode));
            re_value = true;
            logger.debug("关机命令ip为：" + ip + ",cmdCode为：" + cmdCode + ",re_value为:" + re_value);
        } catch (Exception e) {
            logger.error("关机异常ip为" + ip + ",cmdCode为：" + cmdCode + ",re_value为:" + re_value, e);
        }
        return re_value;
    }

    /**
     * 切换信号源
     *
     * @param ip 设备ip
     * @param cmdCode 信号源
     * @return
     */
    @Override
    public boolean changeSignal(String ip, String cmdCode) {
        boolean re_value = false;
        try {
            DeviceManager.invoke(ip, new HHTPROperationRequestSetChannel(cmdCode));
            logger.debug("切换信号源ip为" + ip + ",signal为：" + cmdCode);
            re_value = true;
        } catch (Exception e) {
            logger.error("切换信号源异常ip为" + ip + ",signal为：" + cmdCode, e);
        }
        return re_value;
    }


    /**
     * 查询音量
     *
     * @param ip
     * @param ext
     * @return
     */
    @Override
    public Integer getVolume(String ip, String ext) {
        return null;
    }

    /**
     * 获取设备信息
     *
     * @param ip
     * @return　返回投影仪当前状态："TurnState "：开关机状态；” ChannelState”： 信号状态；”CpuUsage”:Cpu使用率，”Memmary”：内存使用率；”Disk_C”:系统盘使用率；”TopSoftWare”：当前置顶软件
     */
    @Override
    public Map<String, String> getInfo(String ip) {
        Map re_value = Collections.EMPTY_MAP;
        try {
            Map<String, String> res = (Map<String, String>) DeviceManager.invoke(ip, new HHTPROperationRequestGetAllState());
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
        return false;
    }

    /**
     * 获取截图
     *
     * @param ip
     * @return
     */
    @Override
    public Object getPictrue(String ip) {
        try {
            HHTPROperationRequestScreenShot hhtcOperationRequestScreenShot = new HHTPROperationRequestScreenShot();
            return DeviceManager.invoke(ip, hhtcOperationRequestScreenShot);
        } catch (Exception e) {
            logger.error("获取桌面截图异常ip为" + ip, e);
            return "";
        }
    }

    /**
     * 更新设备的命令表的地址
     *
     * @param ip
     * @param sqlStr
     */
    @Override
    public Boolean updateCmdCodeSql(String ip, String sqlStr) {
        try {
            HHTPROperationRequestUpdateDB hhtcOperationRequestUpdateDB = new HHTPROperationRequestUpdateDB(sqlStr);
            DeviceManager.invoke(ip, hhtcOperationRequestUpdateDB);
            return true;
        } catch (Exception e) {
            logger.error("获取更新设备的命令表异常ip为" + ip, e);
            return false;
        }
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
            HHTPROperationRequestUpdateDB hhtcOperationRequestUpdateDB = new HHTPROperationRequestUpdateDB("cmdcodeupdate");
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
     * 时间同步
     *
     * @param ip
     * @param deviceType
     * @return
     */
    @Override
    public Boolean setSysTime(String ip, String deviceType) {
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
            return true;
        } catch (Exception e) {
            logger.error("设置系统时间异常", e);
            return false;
        }
    }

    /**
     * 获取设备所有的命令
     *
     * @param deviceType 设备类型
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
     * @param ip ip
     * @param addrStr 推送的地址
     * @return
     */
    @Override
    public Boolean sendAddress(String ip, String addrStr) {
        try {
            HHTPROperationRequestEventSubscription hhtcOperationRequestEventSubscription = new HHTPROperationRequestEventSubscription(addrStr);
            DeviceManager.invoke(ip, hhtcOperationRequestEventSubscription);
            logger.debug("推送地址ip为" + ip + "地址为:" + addrStr);
            return true;
        } catch (Exception e) {
            logger.error("推送地址异常ip为" + ip, e);
            return false;
        }
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
     * 投影机终端开关机命令
     *
     * @param ip      主机设备的ip地址
     * @param cmdCode ”TurnOff”:关机；”TurnOn”:开机；
     * @return
     */
    public boolean setProjectorTurnState(String ip, String cmdCode) {
        boolean re_value = false;
        try {
            DeviceManager.invoke(ip, new HHTPROperationRequestSetPower(cmdCode));
            re_value = true;
            logger.debug("投影机开关机命令 ip为：" + ip + ",cmdCode为：" + cmdCode + ",re_value为:" + re_value);
        } catch (Exception e) {
            logger.error("投影机开关机命令异常 ip为" + ip + ",cmdCode为：" + cmdCode + ",re_value为:" + re_value, e);
        }
        return re_value;
    }
}
