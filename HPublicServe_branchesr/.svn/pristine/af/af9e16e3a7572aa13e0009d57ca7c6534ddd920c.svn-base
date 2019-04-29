package com.honghe.device.util;

import com.hht.global.GlobalDefinitions;
/**
 * Created by lyx on 2016-06-30.
 */
public final class DeviceSysToTypeUtil {
    public final static String SYS_CAMPUSLOCATION = "campusLocation"; //校园定位系统
    public final static String SYS_DMANAGER = "dmanager"; //集控系统
    public static final String SYS_DSS = "dss";//校宣系统

    /**
     * 依据设备具体类型获取服务地址
     *
     * @param
     * @return
     */
    public static final String getDeviceType(String sys) {
        String re_value = "";
        if (SYS_CAMPUSLOCATION.equals(sys)){
            re_value = GlobalDefinitions.DeviceType_ANTENNA;
//        }
//        else if (SYS_DMANAGER.equals(sys)){
//            re_value = GlobalDefinitions.DeviceType_ONVIF+","+GlobalDefinitions.DeviceType_HHTC+","+GlobalDefinitions.DeviceType_HHTPR+","+GlobalDefinitions.DeviceType_HHTWB;
        } else if (SYS_DSS.equals(sys)){
            re_value = GlobalDefinitions.DeviceType_DIGSIGNBOARD;
        }
    return re_value;
    }
}
