package com.honghe.device.command.impl;

import com.hht.DeviceManager.DeviceManager;
import com.hht.DeviceManager.operationRequest.hhtwb.*;
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
 * Created by lyx on 2016-05-20.
 */
public class WhiteboardCommand implements VirtualDeviceCommand {
    private final static Logger logger = Logger.getLogger(WhiteboardCommand.class);

    /**
     * 开机
     *
     * @param ip
     * @return
     */
    @Override
    public Boolean boot(String ip) {
        Boolean re_value = false;
        try {
            Map<String, String> mac = Host.INSTANCE.getMac(ip);
            if (mac != null && mac.size() > 0) {
                String deviceMac = mac.get("host_hhtcmac");
                String hostMac = mac.get("host_mac");
                if (deviceMac != null && !deviceMac.equals("") && !deviceMac.equals("null")) {
                    for (int i = 0; i < 3; i++) {
                        DeviceManager.WakeUpDevice(ip, deviceMac);
                        new Thread().currentThread().sleep(500);
                    }
                }
                for (int i = 0; i < 3; i++) {
                    DeviceManager.WakeUpDevice(ip, hostMac);
                    new Thread().currentThread().sleep(500);
                }
            }
            re_value = true;
        } catch (Exception e) {
            logger.error("开机异常ip为" + ip, e);
        }
        return re_value;
    }

    /**
     * 关机
     *
     * @param ip
     * @param cmdCode
     * @return
     */
    @Override
    public boolean shutdown(String ip, String cmdCode) {
        Boolean re_value = false;
        try {
            DeviceManager.invoke(ip, new WhiteBoardSetPower(cmdCode));
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
     * @param ip
     * @param ext
     * @return
     */
    @Override
    public Integer getVolume(String ip, String ext) {
        Integer re_value = 0;
        try {
            re_value = (Integer) DeviceManager.invoke(ip, new WhiteBoardGetBoardVolume());
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
        Boolean re_value = false;
        try {
            re_value = (Boolean) DeviceManager.invoke(ip, new WhiteBoardSetBoardProjectorChannel(cmdCode));
            logger.debug("切换信号源ip为" + ip + ",signal为：" + cmdCode);
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
        Map<String, String> re_value = Collections.EMPTY_MAP;
        try {
            Map<String, String> res = (Map<String, String>) DeviceManager.invoke(ip, new WhiteBoardGetAllState());
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
     * @param map
     * @return
     */
    @Override
    public boolean sendMessage(String ip,String Content,String NewsId,String UserName,String DisplayMode,String IsLoop,
                               String CycleMode,String ExecValue,String StartDate,String EndDate,String StartTime,String EndTime,
                               String FontSize,String FontColor,String Font,String Control,String nType) {
        Boolean re_value = false;
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
            DeviceManager.startInvoke(hostIp, "", new WhiteBoardNewsManager(hostMap), new DefaultResponseHandler());
            logger.debug("发送消息ip为" + hostIp + "内容为:" + hostMap.toString());
            re_value = true;
        } catch (Exception e) {
            logger.error("发送消息异常ip为" + hostIp, e);
        }
        return re_value;
    }

    /**
     * 获取设备截图
     *
     * @param ip
     * @return
     */
    @Override
    public Object getPictrue(String ip) {
        Object re_value = null;
        try {
            re_value = DeviceManager.invoke(ip, new WhiteBoardScreenShot());
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
        Boolean re_value = false;
        try {
            DeviceManager.invoke(ip, new WhiteBoardUpdateDB(sqlStr));
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
        Boolean re_value = false;
        try {
            Map<String, String> hMap = (Map<String, String>) DeviceManager.invoke(ip, new WhiteBoardUpdateDB("cmdcodeupdate"));
            if (hMap != null && hMap.containsKey("Type")) {
                String cmdCodeUpdate = hMap.get("Type").toString();
                re_value = CmdCodeUpdateDao.INSTANCE.isDeviceCmdCodeTimeOut(ip, cmdCodeUpdate);
            }
            logger.debug("获取设备命令表标识符cmdcodeupdate并进行比较,ip为:" + ip + "结果为:" + hMap.toString());
        } catch (Exception e) {
            logger.error("获取设备命令表标识符cmdcodeupdate并进行比较异常,ip为:" + ip, e);
        }
        return re_value;
    }

    @Override
    public Boolean setSysTime(String ip, String deviceType) {
        Boolean re_value = false;
        try {
            Calendar calendar = DateUtil.nowUTC();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            int sencond = calendar.get(Calendar.SECOND);

            if (deviceType.equals(GlobalDefinitions.DeviceType_HHTWB)) {
                DeviceManager.startInvoke(ip, "", new WhiteBoardSetSysTime(year, month, day, hour, minute, sencond), new DefaultResponseHandler());
            }
            re_value = true;
        } catch (Exception e) {
            logger.error("设置系统时间异常", e);
        }

        return re_value;
    }

    /**
     * 获取设备所有的命令
     *
     * @param deviceType
     * @return
     */
    @Override
    public Map getCmd(String deviceType) {
        Map<String, List<Map<String, String>>> re_value = new HashMap<String, List<Map<String, String>>>();
        List<Map> dspecIds = SpecDao.INSTANCE.getDspecIds(deviceType);
        if (dspecIds != null && dspecIds.size() > 0) {
            for (int i = 0; i < dspecIds.size(); i++) {
                List<Map<String, String>> cmdList = SpecDao.INSTANCE.getSpecCmd(dspecIds.get(i).get("dspec_id").toString());
                re_value.put(dspecIds.get(i).get("dspec_id").toString(), cmdList);
            }
        }
        return re_value;
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
        Boolean re_value = false;
        try {
            DeviceManager.invoke(ip, new WhiteBoardEventSubscription(addrStr));
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

        String re_value = "";
        try {
            re_value = CmdCodeUpdateDao.INSTANCE.getDeviceCmdCodeUpdate(ip);
            logger.debug("获取设备命令表标识符cmdcodeupdate，ip为:" + ip + "结果为:" + re_value);
        } catch (Exception e) {

            logger.error("获取设备命令表标识符cmdcodeupdate异常,ip为:" + ip, e);
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
        Boolean re_value = false;
        try {
            DeviceManager.invoke(ip, new WhiteBoardRing());
            logger.debug("设备打铃,ip为:" + ip);
            re_value = true;
        } catch (Exception e) {
            logger.error("打铃异常,ip为:" + ip, e);
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
        Boolean re_value = false;
        try {
            DeviceManager.startInvoke(ip, "", new WhiteBoardSystemBackup(), new DefaultResponseHandler());
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
        Boolean re_value = false;
        try {
            DeviceManager.startInvoke(ip, "", new WhiteBoardSystemRecovery(), new DefaultResponseHandler());
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
        Boolean re_value = false;
        try {
            DeviceManager.startInvoke(ip, "", new WhiteBoardOneKeyClean(), new DefaultResponseHandler());
            logger.debug("发送清理垃圾命令,ip为:" + ip);
            re_value = true;
        } catch (Exception e) {
            logger.error("发送清理垃圾命令异常,ip为:" + ip, e);
        }
        return re_value;
    }

    /**
     * * 一键安装软件
     * @param ip
     * @param softName 软件名称
     * @param softPath 软件安装路径
     * @param ftpUsername ftp用户名称
     * @param ftpPassword ftp账号密码
     * @param serverIp ftp服务ip
     * @param port ftp服务端口号
     * @return
     */

    public Boolean oneKeyInstall(String ip, String softName, String softPath, String ftpUsername, String ftpPassword, String serverIp, String port) {
        Boolean re_value = false;
        try {
            DeviceManager.startInvoke(ip, "", new WhiteBoardOneKeyInstall(softName, softPath, ftpUsername, ftpPassword, serverIp, port), new DefaultResponseHandler());
            logger.debug("发送一键安装软件命令,ip为:" + ip + ",softName:" + softName + ",softPath:" + softPath + ",ftpUsername:" + ftpUsername + ",ftpPassword:" + ftpPassword + ",serverip:" + serverIp + ",port:" + port);
            re_value = true;
        } catch (Exception e) {
            logger.error("发送一键安装软件命令异常,ip为:" + ip + ",softName:" + softName + ",softPath:" + softPath + ",ftpUsername:" + ftpUsername + ",ftpPassword:" + ftpPassword + ",serverip:" + serverIp + ",port:" + port, e);
        }
        return re_value;
    }

    /**
     * 一键卸载软件
     *
     * @param ip
     * @param softName
     * @return
     */
    public Boolean oneKeyUninstall(String ip, String softName) {

        Boolean re_value = false;
        try {
            DeviceManager.startInvoke(ip, "", new WhiteBoardOneKeyUninstall(softName), new DefaultResponseHandler());
            logger.debug("发送一键卸载软件命令,ip为:" + ip + ",softName:" + softName);
            re_value = true;
        } catch (Exception e) {
            logger.error("发送一键卸载软件命令异常,ip为:" + ip + ",softName:" + softName, e);
        }
        return re_value;
    }


    /**
     * 一体机一键锁定、解除锁定
     *
     * @param ip
     * @param cmdCode 一键锁定：”Lock”，一键解除锁定：”Unlock”
     * @return
     */
    public Boolean setBoardOneKeyLock(String ip, String cmdCode) {
        Boolean re_value = false;
        try {
            DeviceManager.startInvoke(ip, "", new WhiteBoardSetBoardOneKeyLock(cmdCode), new DefaultResponseHandler());
            logger.debug("一体机一键锁定、解除锁定,ip为:" + ip + ",cmdCode:" + cmdCode);
            re_value = true;
        } catch (Exception e) {
            logger.error("一体机一键锁定、解除锁定异常,ip为:" + ip + ",cmdCode:" + cmdCode, e);
        }
        return re_value;
    }

    /**
     * 一体机获得一键锁定状态
     *
     * @param ip
     * @return 返回结果：一键锁定：”Lock”，一键解除锁定：”Unlock”
     */
    public Map<String, String> getBoardOneKeyLockState(String ip) {
        Map<String, String> re_value = Collections.EMPTY_MAP;
        try {
            re_value = (Map<String, String>) DeviceManager.invoke(ip, new WhiteBoardGetBoardOneKeyLockState());
            logger.debug("一体机获得一键锁定状态,ip为:" + ip);
        } catch (Exception e) {
            logger.error("一体机获得一键锁定状态异常,ip为:" + ip, e);
        }
        return re_value;
    }

    /**
     * 一体机设置静音状态
     *
     * @param ip
     * @param cmdCode Value：静音：”Mute”, 解除静音：“UnMute
     * @return
     */
    public Boolean setBoardMuteState(String ip, String cmdCode) {
        Boolean re_value = false;
        try {
            DeviceManager.startInvoke(ip, "", new WhiteBoardSetBoardMute(cmdCode), new DefaultResponseHandler());
            logger.debug("一体机设置静音状态,ip为:" + ip + ",cmdCode:" + cmdCode);
            re_value = true;
        } catch (Exception e) {
            logger.error("一体机设置静音状态异常,ip为:" + ip + ",cmdCode:" + cmdCode, e);
        }

        return re_value;
    }

    /**
     * 一体机获得静音状态
     *
     * @param ip
     * @return Value：静音：”Mute”, 解除静音：“UnMute
     */
    public Map<String, String> getBoardMuteState(String ip) {
        Map<String, String> re_value = Collections.EMPTY_MAP;
        try {
            re_value = (Map<String, String>) DeviceManager.invoke(ip, new WhiteBoardGetBoardMuteState());
            logger.debug("一体机获得静音状态,ip为:" + ip);
        } catch (Exception e) {
            logger.error("一体机获得静音状态异常,ip为:" + ip, e);
        }
        return re_value;
    }

    /**
     * 一体机设置音量增加
     *
     * @param ip
     * @return
     */
    public Boolean setBoardIncreaseVolume(String ip) {
        Boolean re_value = false;
        try {
            DeviceManager.startInvoke(ip, "", new WhiteBoardSetBoardIncreaseVolume(), new DefaultResponseHandler());
            logger.debug("一体机设置音量增加命令,ip为:" + ip);
            re_value = true;
        } catch (Exception e) {
            logger.error("一体机设置音量增加命令异常,ip为:" + ip, e);
        }
        return re_value;
    }

    /**
     * 一体机设置音量减少
     *
     * @param ip
     * @return
     */
    public Boolean setBoardDecreaseVolume(String ip) {
        Boolean re_value = false;
        try {
            WhiteBoardSetBoardDecreaseVolume obj = new WhiteBoardSetBoardDecreaseVolume();
            DeviceManager.startInvoke(ip, "", obj, new DefaultResponseHandler());
            logger.debug("一体机设置音量减少命令,ip为:" + ip);
            re_value = true;
        } catch (Exception e) {
            logger.error("一体机设置音量减少命令异常,ip为:" + ip, e);
        }
        return re_value;
    }

    /**
     * 一体机设置音量值
     *
     * @param ip
     * @param val 音量值：0-100
     * @return
     */
    public Boolean setBoardVolume(String ip, String val) {
        Boolean re_value = false;
        try {
            WhiteBoardSetBoardVolume obj = new WhiteBoardSetBoardVolume(val);
            DeviceManager.startInvoke(ip, "", obj, new DefaultResponseHandler());
            logger.debug("一体机设置音量,ip为:" + ip + ",val:" + val);
            re_value = true;
        } catch (Exception e) {
            logger.error("一体机设置音量异常,ip为:" + ip + ",val:" + val, e);
        }
        return re_value;
    }

    /**
     * 一体机获取音量值
     *
     * @param ip
     * @return Value：音量值：0-100
     */
    public Map<String, String> getBoardVolume(String ip) {
        Map<String, String> re_value = Collections.EMPTY_MAP;
        try {
            WhiteBoardGetBoardVolume obj = new WhiteBoardGetBoardVolume();
            re_value = (Map<String, String>) DeviceManager.invoke(ip, obj);
            logger.debug("一体机获取音量值,ip为:" + ip);
        } catch (Exception e) {
            logger.error("一体机获取音量值异常,ip为:" + ip, e);
        }
        return re_value;
    }

    /**
     * 一体机OPS重启
     *
     * @param ip ip地址
     * @return
     */
    public Boolean setBoardOpsRestart(String ip) {
        Boolean re_value = false;
        try {
            WhiteBoardSetBoardOpsRestart obj = new WhiteBoardSetBoardOpsRestart();
            DeviceManager.startInvoke(ip, "", obj, new DefaultResponseHandler());
            logger.debug("一体机OPS重启,ip为:" + ip);
            re_value = true;
        } catch (Exception e) {
            logger.error("一体机OPS重启异常,ip为:" + ip, e);
        }
        return re_value;
    }

    /**
     * 一体机设置投影机待机
     *
     * @param ip
     * @param cmdCode Value：待机：”Standby” ,正常：”Normal”
     * @return
     */
    public Boolean setBoardProjectorStandby(String ip, String cmdCode) {
        Boolean re_value = false;
        try {
            WhiteBoardSetBoardProjectorStandby obj = new WhiteBoardSetBoardProjectorStandby(cmdCode);
            DeviceManager.startInvoke(ip, "", obj, new DefaultResponseHandler());
            logger.debug("一体机设置投影机待机,ip为:" + ip);
            re_value = true;
        } catch (Exception e) {
            logger.error("一体机设置投影机待机异常,ip为:" + ip, e);
        }
        return re_value;
    }

    /**
     * 一体机获得投影机待机状态
     *
     * @param ip
     * @return Value：待机：”Standby” ,正常：”Normal”
     */
    public Map<String, String> getBoardProjectorStandbyState(String ip) {
        Map<String, String> re_value = Collections.EMPTY_MAP;
        try {
            WhiteBoardGetBoardProjectorStandbyState obj = new WhiteBoardGetBoardProjectorStandbyState();
            DeviceManager.invoke(ip, obj);
            logger.debug("一体机获得投影机待机状态,ip为:" + ip);
        } catch (Exception e) {
            logger.error("一体机获得投影机待机状态异常,ip为:" + ip, e);
        }
        return re_value;
    }

    /**
     * 一体机投影机电源
     *
     * @param ip
     * @param cmdCode Value：开机：”TurnOn”, 关机：“TurnOff”
     * @return
     */
    public Boolean setBoardProjectorTurn(String ip, String cmdCode) {
        Boolean re_value = false;
        try {
            WhiteBoardSetBoardProjectorTurn obj = new WhiteBoardSetBoardProjectorTurn(cmdCode);
            DeviceManager.startInvoke(ip, "", obj, new DefaultResponseHandler());
            logger.debug("一体机投影机电源,ip为:" + ip + ",cmdCode:" + cmdCode);
            re_value = true;
        } catch (Exception e) {
            logger.error("一体机投影机电源异常,ip为:" + ip + ",cmdCode:" + cmdCode, e);
        }
        return re_value;
    }

    /**
     * 一体机获得投影机电源状态
     *
     * @param ip
     * @return Value：开机：”TurnOn”, 关机：“TurnOff”
     */
    public Map<String, String> getBoardProjectorTurnState(String ip) {
        Map<String, String> re_value = Collections.EMPTY_MAP;
        try {
            WhiteBoardGetBoardProjectorTurnState obj = new WhiteBoardGetBoardProjectorTurnState();
            DeviceManager.invoke(ip, obj);
            logger.debug("一体机获得投影机电源状态,ip为:" + ip);
        } catch (Exception e) {
            logger.error("一体机获得投影机电源状态异常,ip为:" + ip, e);
        }
        return re_value;
    }

    /**
     * 一体机投影机省电
     *
     * @param ip
     * @param cmdCode Value：省电：”Energy” ,标准：”Standard”
     * @return
     */
    public Boolean setBoardProjectorEnergy(String ip, String cmdCode) {
        Boolean re_value = false;
        try {
            WhiteBoardSetBoardProjectorEnergy obj = new WhiteBoardSetBoardProjectorEnergy(cmdCode);
            DeviceManager.startInvoke(ip, "", obj, new DefaultResponseHandler());
            logger.debug("一体机投影机电源,ip为:" + ip + ",cmdCode:" + cmdCode);
            re_value = true;
        } catch (Exception e) {
            logger.error("一体机投影机电源异常,ip为:" + ip + ",cmdCode:" + cmdCode, e);
        }
        return re_value;
    }

    /**
     * 一体机获得投影机省电状态
     *
     * @param ip
     * @return
     */
    public Map<String, String> getBoardProjectorEnergyState(String ip) {
        Map<String, String> re_value = Collections.EMPTY_MAP;
        try {
            WhiteBoardGetBoardProjectorEnergyState obj = new WhiteBoardGetBoardProjectorEnergyState();
            DeviceManager.invoke(ip, obj);
            logger.debug("一体机获得投影机省电状态,ip为:" + ip);
        } catch (Exception e) {
            logger.error("一体机获得投影机省电状态异常,ip为:" + ip, e);
        }
        return re_value;
    }

    /**
     * 获取默认通道和童锁信息
     * @param deviceType 设备类型
     * @return
     */
    public List getAllChannelIntersectionByType(String deviceType)
    {
        List<Map<String, Object>> res = new ArrayList<>();
        try {
            res = DeviceDao.INSTANCE.getAllChannelIntersectionByType(deviceType);
        } catch (Exception e) {
            logger.error("获取已添加设备的通道交集", e);
        }
        return res;
    }
}
