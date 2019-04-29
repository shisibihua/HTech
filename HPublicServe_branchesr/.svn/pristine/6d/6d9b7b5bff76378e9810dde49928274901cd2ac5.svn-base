package com.honghe.device.command.impl;

import com.hht.DeviceManager.DeviceManager;
import com.hht.DeviceManager.operationRequest.hhtdsb.*;
import com.hht.DeviceManager.operationRequest.hhtwb.WhiteBoardSetSysTime;
import com.hht.global.GlobalDefinitions;
import com.honghe.device.Host;
import com.honghe.device.command.VirtualDeviceCommand;
import com.honghe.device.dao.CmdCodeUpdateDao;
import com.honghe.device.handler.DefaultResponseHandler;
import com.honghe.device.util.DateUtil;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lyx on 2016-07-07.
 * 数字班牌执行的操作命令
 */
public class DigitalClassCommand implements VirtualDeviceCommand {

    private final static Logger logger = Logger.getLogger(DigitalClassCommand.class);

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
                DeviceManager.WakeUpDevice(ip, hostMac);
                new Thread().currentThread().sleep(500);
            }
            re_value = true;
            logger.debug("开机成功,ip为" + ip);
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
             DeviceManager.invoke(ip, new DigSignBoardSetShutDown());
            re_value = true;
            logger.debug("关机命令ip为：" + ip + ",cmdCode为：" + cmdCode + ",re_value为:" + re_value);
        } catch (Exception e) {
            logger.error("关机异常ip为" + ip + ",cmdCode为：" + cmdCode + ",re_value为:" + re_value, e);
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
            Map<String, String> res = (Map<String, String>) DeviceManager.invoke(ip, new DigSignBoardGetAllState());
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
     * 获取截图
     *
     * @param ip
     * @return
     */
    @Override
    public Object getPictrue(String ip) {
        Object re_value;
        try {
            logger.debug("调取获取桌面截图接口");
            re_value = DeviceManager.invoke(ip, new DigSignBoardScreenShot());
            logger.debug("获取桌面截图结果："+re_value.toString());
        } catch (Exception e) {
            logger.error("获取桌面截图异常ip为" + ip, e);
            re_value = "";
        }
        return re_value;
    }


    /**
     * 获取设备所有的命令
     *     * @param type
     * @return
     */
    @Override
    public Map getCmd(String type) {
        Map<String, List<Map<String, String>>> re_value = new HashMap<String, List<Map<String, String>>>();
//        List<Map> dspecIds = SpecDao.INSTANCE.getDspecIds(deviceType);
//        if (dspecIds != null && dspecIds.size() > 0) {
//            for (int i = 0; i < dspecIds.size(); i++) {
//                List<Map<String, String>> cmdList = SpecDao.INSTANCE.getSpecCmd(dspecIds.get(i).get("dspec_id").toString());
//                re_value.put(dspecIds.get(i).get("dspec_id").toString(), cmdList);
//            }
//        }
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
            DeviceManager.invoke(ip, new DigSignBoardRing());
            logger.debug("设备打铃,ip为:" + ip);
            re_value = true;
        } catch (Exception e) {
            logger.error("打铃异常,ip为:" + ip, e);
        }
        return re_value;
    }

    /**
     * 切换信号源
     *
     * @param ip
     * @param signal
     * @return
     */
    @Override
    public boolean changeSignal(String ip, String signal) {
        return false;
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
     * 更新设备的命令表的地址
     *
     * @param ip
     * @param sqlStr
     */
    @Override
    public Boolean updateCmdCodeSql(String ip, String sqlStr) {
        boolean re_value = false;
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
        return false;
    }

    /**
     * 同步时间
     *
     * @param ip
     * @param deviceType 设备类型
     * @return
     */
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
     * 推送地址
     *
     * @param ip
     * @param addrStr
     * @return
     */
    @Override
    public Boolean sendAddress(String ip, String addrStr) {
        return null;
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
     * 设备重启
     *
     * @param ip
     * @return
     */
    public Map<String, String> setDeviceRestart(String ip) {
        Map<String, String> re_value = Collections.emptyMap();
        try {
            re_value = (Map<String, String>) DeviceManager.invoke(ip, new DigSignBoardSetRestart());
            logger.debug("重启命令ip为：" + ip + ",re_value为:" + re_value);
        } catch (Exception e) {
            logger.error("重启异常ip为" + ip + ",re_value为:" + re_value, e);
        }
        return re_value;
    }

    /**
     * 下发开机计划
     *
     * @param ip
     * @param values 开机命令信息
     * @return
     */
    public Map<String, String> DigSignBoardSwitchPlan(String ip, String values) {
        Map<String, String> re_value = Collections.emptyMap();

        try {
            re_value = (Map<String, String>)DeviceManager.invoke(ip, new DigSignBoardSwitchPlan(values));
            logger.debug("下发开机计划ip为：" + ip + ",re_value为:" + re_value);
        } catch (Exception e) {
            logger.error("下发开机计划异常ip为" + ip + ",re_value为:" + re_value, e);
        }

        return re_value;
    }

    /**
     * 通知终端下载信息
     * @param ip 设备ip
     * @param functionName 方法（由平台传给终端的方法名）
     * @param values 平台传给终端的信息
     * @return boolean
     */
    public boolean DigSignBoardCommonCommand(String ip,String functionName,String values){
        SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss:mmm");
        long startTime = System.currentTimeMillis();
        logger.debug("节目下发或删除--------------设备服务接口调用时间"+time.format(startTime));
        boolean flag = false;
        Hashtable hashtb = new Hashtable();
        try {
            hashtb = (Hashtable) DeviceManager.invoke(ip,new DigSignBoardCommonCommand(functionName,values));
            if (hashtb!=null&&hashtb.size()>0) {
                flag = true;
                logger.debug(functionName + ",通知ip为：" + ip+"functionName为："+functionName + ",flag为：" + flag);
            }
            long endTime = System.currentTimeMillis();
            String different = time.format(endTime-startTime);
            logger.debug("节目下发或删除--------------返回时间："+time.format(System.currentTimeMillis())+"，时间差为："+different);
        } catch (Exception e) {
            logger.error(functionName+"异常,通知ip为：" + ip +"functionName为："+functionName+ ",flag为：" + flag , e);
        }
        return flag;
    }

    /**
     * 班牌开屏
     * @param ip 设备ip
     * @return
     */
    public Object startDigSignScreen(String ip){
        Object re_value = new Object();
        try {
            re_value = DeviceManager.invoke(ip,new DigSignBoardScreenOn());
            logger.debug("班牌："+ip+"开屏信息为：："+re_value.toString());
        } catch (Exception e) {
            logger.error("班牌："+ip+"开屏异常！",e);
        }
        return re_value;
    }

    /**
     * 班牌关屏
     * @param ip 设备ip
     * @return
     */
    public Object shutDigSignScreen(String ip){
        Object re_value = new Object();
        try {
            re_value = DeviceManager.invoke(ip,new DigSignBoardScreenOff());
            logger.debug("班牌："+ip+"关屏信息为：："+re_value.toString());
        } catch (Exception e) {
            logger.error("班牌："+ip+"关屏异常！",e);
        }
        return re_value;
    }

}
