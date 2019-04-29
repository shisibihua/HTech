package com.honghe.device.license;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by lyx on 2016-10-11.
 * 存储设备对应的注册数量
 */
public class DeviceLicense {
    final static Logger logger = Logger.getLogger(DeviceLicense.class);

    public static Hashtable<String, Integer> deviceLicenseNum = new Hashtable<>();

    //未注册时，默认设备数量
    public static final int DEVICE_NUM_DEFAULT = 5;


    /**
     * 添加注册的设备数量
     *
     * @param deviceType 设备类型
     * @param num        数量
     */
    public static void add(String deviceType, String num) {
        if (deviceLicenseNum.containsKey(deviceType)) {
            int old_value = deviceLicenseNum.get(deviceType);
            if (old_value < Integer.parseInt(num)) {
                deviceLicenseNum.put(deviceType, Integer.valueOf(num));
                logger.debug("设备类型为:" + deviceType + "允许注册为:" + num);
            }
        }
    }

    /**
     * 获取某个设备的注册数量
     *
     * @param deviceType
     * @return
     */
    public static int getNum(String deviceType) {
        int re_value = DEVICE_NUM_DEFAULT;
        if (deviceLicenseNum.containsKey(deviceType)) {
            re_value = deviceLicenseNum.get(deviceType);
        }
        return re_value;
    }

    /**
     * 获取某个设备的注册数量
     *
     * @param deviceType 设备类型
     * @return
     */
    public static Map<String, Integer> getNums(String deviceType) {
        Map re_value = new HashMap();
        String[] deviceTypes = deviceType.split(",");
        for (String obj : deviceTypes) {
            if (deviceLicenseNum.containsKey(obj)) {
                int value = deviceLicenseNum.get(obj);
                re_value.put(obj, value);
            } else {
                re_value.put(obj, DEVICE_NUM_DEFAULT);
            }
        }
        return re_value;
    }

}
