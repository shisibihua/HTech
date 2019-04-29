package com.honghe.device.command.impl;

import com.hht.DeviceManager.DeviceManager;
import com.hht.DeviceManager.operationRequest.antenna.*;
import com.honghe.device.command.DeviceCommand;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.Map;

/**
 * Created by lyx on 2016-07-15.
 */
public class PositionAntennaCommand implements DeviceCommand {

    private final static Logger logger = Logger.getLogger(PositionAntennaCommand.class);

    /**
     * 连接
     *
     * @param ip
     * @return
     */
    @Override
    public Boolean boot(String ip) {
        Boolean re_value = false;
        try {
            Map<String, String> temp = Collections.EMPTY_MAP;
            temp = (Map<String, String>) DeviceManager.invoke(ip, new AntennaConnection());
            String tem = temp.get("type");
            if (tem != null && tem.equals("Connection")) {
                re_value = true;
            }
            logger.debug("连接命令ip为：" + ip + ",re_value为:" + re_value);
        } catch (Exception e) {
            logger.error("连接异常ip为" + ip + ",re_value为:" + re_value, e);
        }
        return re_value;
    }

    /**
     * 断开连接
     *
     * @param ip
     * @param cmdCode
     * @return
     */
    @Override
    public boolean shutdown(String ip, String cmdCode) {
        Boolean re_value = false;
        try {
            Map<String, String> temp = Collections.EMPTY_MAP;
            temp = (Map<String, String>) DeviceManager.invoke(ip, new AntennaDisConnection());
            String tem = temp.get("type");
            if (tem != null && tem.equals("DisConnection")) {
                re_value = true;
            }
            logger.debug("断开命令ip为：" + ip + ",re_value为:" + re_value);
        } catch (Exception e) {
            logger.error("断开异常ip为" + ip + ",re_value为:" + re_value, e);
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
     * @return 返回心跳时间，超时时间，维持通道时间
     */
    public Map<String, String> getInfo(String ip) {
        Map<String, String> re_value = Collections.EMPTY_MAP;
        try {
            Map<String, String> res = (Map<String, String>) DeviceManager.invoke(ip, new AntennaGetConfigurationInfo());
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
     * 设置心跳时间
     *
     * @param ip
     * @param setHeartbeat 0-900s
     * @return
     */
    public Map<String, String> setHeartbeat(String ip, String setHeartbeat) {
        Map<String, String> re_value = Collections.EMPTY_MAP;
        try {
            Map<String, String> res = (Map<String, String>) DeviceManager.invoke(ip, new AntennaSetHeartbeat(Integer.valueOf(setHeartbeat)));
            if (res != null) {
                re_value = res;
            }
            logger.debug("设置心跳为ip为" + ip + res == null ? "空" : res.toString());
        } catch (Exception e) {
            logger.error("设置心跳异常ip为" + ip, e);
        }
        return re_value;
    }

    /**
     * 设置超时时间
     *
     * @param ip
     * @param setTimeoutTime 超时时间 1-300s
     * @return
     */
    public Map<String, String> setTimeoutTime(String ip, String setTimeoutTime) {
        Map<String, String> re_value = Collections.EMPTY_MAP;
        try {
            Map<String, String> res = (Map<String, String>) DeviceManager.invoke(ip, new AntennaSetTimeoutTime(Integer.valueOf(setTimeoutTime)));
            if (res != null) {
                re_value = res;
            }
            logger.debug("设置超时时间为 ip为" + ip + res == null ? "空" : res.toString());
        } catch (Exception e) {
            logger.error("设置设置超时时间异常ip为" + ip, e);
        }
        return re_value;
    }

    /**
     * 设置通道维持时间
     *
     * @param ip
     * @param setPersistTime 通道维持时间 0-3600 s
     * @return
     */
    public Map<String, String> setPersistTime(String ip, String setPersistTime) {
        Map<String, String> re_value = Collections.EMPTY_MAP;
        try {
            Map<String, String> res = (Map<String, String>) DeviceManager.invoke(ip, new AntennaSetPersistTime(Integer.valueOf(setPersistTime)));
            if (res != null) {
                re_value = res;
            }
            logger.debug("设置超时时间为 ip为" + ip + res == null ? "空" : res.toString());
        } catch (Exception e) {
            logger.error("设置设置超时时间异常ip为" + ip, e);
        }
        return re_value;
    }

}
