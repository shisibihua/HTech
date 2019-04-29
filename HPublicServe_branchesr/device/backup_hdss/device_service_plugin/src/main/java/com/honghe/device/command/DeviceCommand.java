package com.honghe.device.command;

/**
 * Created by lyx on 2016-01-12.
 * 设备操作命令接口
 */
public interface DeviceCommand {
    /**
     * 开机
     *
     * @param ip
     * @return
     */
    public Boolean boot(String ip);

    /**
     * 关机
     *
     * @param ip
     * @param cmdCode
     * @return
     */
    public boolean shutdown(String ip, String cmdCode);

    /**
     * 查询音量
     *
     * @param ip
     * @return
     */
    public Integer getVolume(String ip, String ext);

}
