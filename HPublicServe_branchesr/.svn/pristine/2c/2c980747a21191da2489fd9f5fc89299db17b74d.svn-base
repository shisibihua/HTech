package com.honghe.device.command;

import java.util.Map;

/**
 * Created by lyx on 2016-01-13.
 * 虚拟设备命令，因为这些设备都是由受控端连接来控制设备 例如大屏设备，投影机
 */
public interface VirtualDeviceCommand extends DeviceCommand {
    /**
     * 切换信号源
     *
     * @param ip
     * @param signal
     * @return
     */
    public boolean changeSignal(String ip, String signal);

    /**
     * 获取设备信息
     *
     * @param ip
     * @return
     */
    public Map<String, String> getInfo(String ip);

    /**
     * 发送消息
     * @param
     * @return
     */
    public boolean sendMessage(String ip,String Content,String NewsId,String UserName,String DisplayMode,String IsLoop,
                               String CycleMode,String ExecValue,String StartDate,String EndDate,String StartTime,String EndTime,
                               String FontSize,String FontColor,String Font,String Control,String nType);

    /**
     * 获取大屏截图
     *
     * @param ip
     * @return
     */
    public Object getPictrue(String ip);

    /**
     * 更新设备的命令表的地址
     *
     * @param ip
     * @param sqlStr
     */
    public Boolean updateCmdCodeSql(String ip, String sqlStr);

    /**
     * 获取设备的命令表是否过期标识符
     *
     * @param ip
     * @return
     */
    public boolean isDeviceCmdCodeTimeOut(String ip);


    //设置系统时间
    public Boolean setSysTime(String ip, String deviceType);


    /**
     * 获取设备所有的命令
     *
     * @param type
     * @return
     */
    public Map getCmd(String type);


    /**
     * 推送地址
     *
     * @param ip
     * @param addrStr
     * @return
     */
    public Boolean sendAddress(String ip, String addrStr);


    /**
     * 获取某一设备的命令表更新的标识符
     *
     * @param ip
     * @return
     */
    public String getDeviceCmdCodeUpdate(String ip);

}
