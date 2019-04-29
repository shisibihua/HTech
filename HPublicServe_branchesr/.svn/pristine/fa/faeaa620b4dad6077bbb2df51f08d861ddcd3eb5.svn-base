package com.honghe.device.util;

import com.hht.global.GlobalDefinitions;

/**
 * Created by lyx on 2015-12-31.
 * 平台的设备类型与设备服务的设备类型转换类
 */
public final class TypeTransformationUtil {

    /**
     * 用于平台的设备类型与设备服务的设备类型进行转换
     *
     * @param type
     * @return
     */
    public static String TypeToDeviceType(String type) {
        String deviceType = GlobalDefinitions.DeviceType_HHTC;
        if (CommonNameUtil.HHREC.toString().equals(type) || GlobalDefinitions.DeviceType_IPC.equals(type.toUpperCase()) || GlobalDefinitions.DeviceType_NVR.equals(type.toUpperCase())) {
            deviceType = GlobalDefinitions.DeviceType_ONVIF;
        } else if (CommonNameUtil.HHTC.toString().equals(type)) {
            deviceType = GlobalDefinitions.DeviceType_HHTC;
        } else if (CommonNameUtil.HTPR.toString().equals(type)) {
            deviceType = GlobalDefinitions.DeviceType_HHTPR;
        } else if (CommonNameUtil.HHTWB.toString().equals(type)) {
            deviceType = GlobalDefinitions.DeviceType_HHTWB;
        } else if (CommonNameUtil.HHTDC.toString().equals(type)) {
            deviceType = GlobalDefinitions.DeviceType_DIGSIGNBOARD;
        } else if (CommonNameUtil.HHTPA.toString().equals(type)) {
            deviceType = GlobalDefinitions.DeviceType_ANTENNA;
        } else if (CommonNameUtil.HHTMCU.toString().equals(type)) {
            deviceType = GlobalDefinitions.DeviceType_MCU;
        } else if (CommonNameUtil.HHTCC.toString().equals(type)) {
            deviceType = GlobalDefinitions.DeviceType_CTRL;
        }

        return deviceType;
    }


    /**
     * 判断此设备是否需要进行注册
     *
     * @param type
     * @return
     */
    public static boolean unRegister(String type) {
        boolean re_value = false;
        if (CommonNameUtil.HHTSE.toString().equals(type) || CommonNameUtil.HHTAU.toString().equals(type) || CommonNameUtil.HHTCTE.toString().equals(type)) {
            re_value = true;
        }
        return re_value;
    }

    /**
     * 用于平台的设备类型与设备服务的设备类型进行转换
     *
     * @param type
     * @return
     */
    public static String TypeToDeviceType(String type, String className) {

        int str_start = className.lastIndexOf(".");
        int str_end = className.indexOf("Device");
        String host_type = className.substring(str_start + 1, str_end);
        String deviceType = host_type.toUpperCase();
        if (CommonNameUtil.HHREC.toString().equals(type) || GlobalDefinitions.DeviceType_IPC.equals(type.toUpperCase()) || GlobalDefinitions.DeviceType_NVR.equals(type.toUpperCase())) {
            deviceType = GlobalDefinitions.DeviceType_ONVIF;
        } else if (CommonNameUtil.HHTC.toString().equals(type)) {
            deviceType = GlobalDefinitions.DeviceType_HHTC;
        } else if (CommonNameUtil.HTPR.toString().equals(type)) {
            deviceType = GlobalDefinitions.DeviceType_HHTPR;
        } else if (CommonNameUtil.HHTWB.toString().equals(type)) {
            deviceType = GlobalDefinitions.DeviceType_HHTWB;
        } else if (CommonNameUtil.HHTDC.toString().equals(type)) {
            deviceType = GlobalDefinitions.DeviceType_DIGSIGNBOARD;
        } else if (CommonNameUtil.HHTPA.toString().equals(type)) {
            deviceType = GlobalDefinitions.DeviceType_ANTENNA;
        } else if (CommonNameUtil.HHTCC.toString().equals(type)) {
            deviceType = GlobalDefinitions.DeviceType_CTRL;
        }
        return deviceType;
    }


    /**
     * 是否是所有设备
     *
     * @param type       平台对应的设备类型
     * @param deviceType 设备服务对应的设备类型
     * @return
     */
    public static boolean isAll(String type, String deviceType) {
        boolean re_value = false;
        if (CommonNameUtil.ALL.toString().equals(type)) {
            if (GlobalDefinitions.DeviceType_HHTWB.equals(deviceType) //白板一体机
                    || GlobalDefinitions.DeviceType_HHTPR.equals(deviceType)//
                    || GlobalDefinitions.ProtocolType_HHTC.equals(deviceType)
                    || GlobalDefinitions.DeviceType_ONVIF.equals(deviceType)
                    || GlobalDefinitions.DeviceType_IPC.equals(deviceType)
                    || GlobalDefinitions.DeviceType_ANTENNA.equals(deviceType)
                    || GlobalDefinitions.DeviceType_DIGSIGNBOARD.equals(deviceType)
                    ) {
                re_value = true;
            }
        }
        return re_value;
    }

    /**
     * 是否是录播设备
     *
     * @param type       平台对应的设备类型
     * @param deviceType 设备服务对应的设备类型
     * @return
     */
    public static boolean isRecord(String type, String deviceType) {
        boolean re_value = false;
        if (CommonNameUtil.HHREC.toString().equals(type)) {
            if (GlobalDefinitions.DeviceType_ONVIF.equals(deviceType) || GlobalDefinitions.DeviceType_IPC.equals(deviceType)) {
                re_value = true;
            }
        }
        return re_value;
    }

    /**
     * 是否是录播设备
     *
     * @param deviceType 设备服务对应的设备类型
     * @return
     */
    public static boolean isRecord(String deviceType) {
        boolean re_value = false;
        if (GlobalDefinitions.DeviceType_ONVIF.equals(deviceType) || GlobalDefinitions.DeviceType_IPC.equals(deviceType)) {
            re_value = true;
        }
        return re_value;
    }


    /**
     * 是否是大屏设备
     *
     * @param type       平台对应的设备类型
     * @param deviceType 设备服务对应的设备类型
     * @return
     */
    public static boolean isScreen(String type, String deviceType) {
        boolean re_value = false;
        if (CommonNameUtil.HHTC.toString().equals(type)) {
            if (GlobalDefinitions.DeviceType_HHTC.equals(deviceType)) {
                re_value = true;
            }
        }
        return re_value;
    }

    /**
     * 是否是大屏设备
     *
     * @param deviceType 设备服务对应的设备类型
     * @return
     */
    public static boolean isScreen(String deviceType) {
        boolean re_value = false;

        if (GlobalDefinitions.DeviceType_HHTC.equals(deviceType)) {
            re_value = true;
        }

        return re_value;
    }


    /**
     * 是否是投影机设备
     *
     * @param type       平台对应的设备类型
     * @param deviceType 设备服务对应的设备类型
     * @return
     */
    public static boolean isProjector(String type, String deviceType) {
        boolean re_value = false;
        if (CommonNameUtil.HTPR.toString().equals(type)) {
            if (GlobalDefinitions.DeviceType_HHTPR.equals(deviceType)) {
                re_value = true;
            }
        }
        return re_value;
    }

    /**
     * 是否是投影机设备
     *
     * @param deviceType 设备服务对应的设备类型
     * @return
     */
    public static boolean isProjector(String deviceType) {
        boolean re_value = false;
        if (GlobalDefinitions.DeviceType_HHTPR.equals(deviceType)) {
            re_value = true;
        }
        return re_value;
    }

    /**
     * 是否是白板设备
     *
     * @param type       平台对应的设备类型
     * @param deviceType 设备服务对应的设备类型
     * @return
     */
    public static boolean isWhiteBoard(String type, String deviceType) {
        boolean re_value = false;
        if (CommonNameUtil.HHTWB.toString().equals(type)) {
            if (GlobalDefinitions.DeviceType_HHTWB.equals(deviceType)) {
                re_value = true;
            }
        }
        return re_value;
    }

    /**
     * 是否是白板设备
     *
     * @param deviceType 设备服务对应的设备类型
     * @return
     */
    public static boolean isWhiteBoard(String deviceType) {
        boolean re_value = false;
        if (GlobalDefinitions.DeviceType_HHTWB.equals(deviceType)) {
            re_value = true;
        }
        return re_value;
    }


    /**
     * 是否是数字班牌
     *
     * @param deviceType 设备服务对应的设备类型
     * @return
     */
    public static boolean isDigitalClass(String deviceType) {
        boolean re_value = false;
        if (GlobalDefinitions.DeviceType_DIGSIGNBOARD.equals(deviceType)) {
            re_value = true;
        }
        return re_value;
    }

    /**
     * 是否是数字班牌
     *
     * @param type       平台对应的设备类型
     * @param deviceType 设备服务对应的设备类型
     * @return
     */
    public static boolean isDigitalClass(String type, String deviceType) {
        boolean re_value = false;
        if (CommonNameUtil.HHTDC.toString().equals(type)) {
            if (GlobalDefinitions.DeviceType_DIGSIGNBOARD.equals(deviceType)) {
                re_value = true;
            }
        }
        return re_value;
    }

    /**
     * 是否是定位天线
     *
     * @param deviceType 设备服务对应的设备类型
     * @return
     */
    public static boolean isPositioningAntenna(String deviceType) {
        boolean re_value = false;
        if (GlobalDefinitions.DeviceType_ANTENNA.equals(deviceType)) {
            re_value = true;
        }
        return re_value;
    }

    /**
     * 是否是定位天线
     *
     * @param type       平台对应的设备类型
     * @param deviceType 设备服务对应的设备类型
     * @return
     */
    public static boolean isPositioningAntenna(String type, String deviceType) {
        boolean re_value = false;
        if (CommonNameUtil.HHTPA.toString().equals(type)) {
            if (GlobalDefinitions.DeviceType_ANTENNA.equals(deviceType)) {
                re_value = true;
            }
        }

        return re_value;
    }

    /**
     * 是否是中控设备
     *
     * @param deviceType 设备类型
     * @return
     */
    public static boolean isCenterControl(String deviceType) {
        boolean re_value = false;
        if (GlobalDefinitions.DeviceType_CTRL.equals(deviceType)) {
            re_value = true;
        }
        return re_value;
    }

    /**
     * @param type       平台对应的设备类型
     * @param deviceType 设备服务对应的设备类型
     * @return
     */
    public static boolean isCenterControl(String type, String deviceType) {
        boolean re_value = false;
        if (CommonNameUtil.HHTCC.toString().equals(type) && GlobalDefinitions.DeviceType_CTRL.equals(deviceType)) {
            re_value = true;
        }
        return re_value;
    }

    /**
     * 是否是互动教学
     *
     * @param type       平台对应的设备类型
     * @param deviceType 设备服务对应的设备类型
     * @return
     */
    public static boolean isInteractiveTeaching(String type, String deviceType) {

        boolean re_value = false;
        if (CommonNameUtil.HHTMCU.toString().equals(type) && GlobalDefinitions.DeviceType_MCU.equals(deviceType)) {
            re_value = true;
        }

        return re_value;
    }

    /**
     * 是否是互动教学
     *
     * @param type       平台对应的设备类型
     * @param deviceType 设备服务对应的设备类型
     * @return
     */
    public static boolean isInteractiveTeaching(String type) {

        boolean re_value = false;
        if (GlobalDefinitions.DeviceType_MCU.toString().equals(type)) {
            re_value = true;
        }

        return re_value;
    }

    /**
     * 获取平台对应的类型
     *
     * @param deviceType 设备服务对应的设备类型
     * @return
     */
    public static String getType(String deviceType) {
        String re_value = "";

        if (isRecord(deviceType)) {
            re_value = CommonNameUtil.HHREC.toString();
        } else if (isScreen(deviceType)) {
            re_value = CommonNameUtil.HHTC.toString();
        } else if (isProjector(deviceType)) {
            re_value = CommonNameUtil.HTPR.toString();
        } else if (isWhiteBoard(deviceType)) {
            re_value = CommonNameUtil.HHTWB.toString();
        } else if (isDigitalClass(deviceType)) {
            re_value = CommonNameUtil.HHTDC.toString();
        } else if (isPositioningAntenna(deviceType)) {
            re_value = CommonNameUtil.HHTPA.toString();
        } else if (isCenterControl(deviceType)) {
            re_value = CommonNameUtil.HHTCC.toString();
        } else if (isInteractiveTeaching(deviceType)) {
            re_value = CommonNameUtil.HHTMCU.toString();
        }
        return re_value;
    }


    /**
     * 获取平台对应的类型
     *
     * @param deviceType 设备服务对应的设备类型
     * @return
     */
    public static CommonNameUtil getEnumType(String deviceType) {
        CommonNameUtil re_value = null;

        if (isRecord(deviceType)) {
            re_value = CommonNameUtil.HHREC;
        } else if (isScreen(deviceType)) {
            re_value = CommonNameUtil.HHTC;
        } else if (isProjector(deviceType)) {
            re_value = CommonNameUtil.HTPR;
        } else if (isWhiteBoard(deviceType)) {
            re_value = CommonNameUtil.HHTWB;
        } else if (isDigitalClass(deviceType)) {
            re_value = CommonNameUtil.HHTDC;
        } else if (isPositioningAntenna(deviceType)) {
            re_value = CommonNameUtil.HHTPA;
        } else if (isCenterControl(deviceType)) {
            re_value = CommonNameUtil.HHTCC;
        }
        return re_value;
    }


    /**
     * 平台对应设备类型是否配设备服务对应的设备类型
     *
     * @param type       平台对应的设备类型
     * @param deviceType 设备服务对应的设备类型
     * @return
     */
    public static Boolean isHas(String type, String deviceType) {
        boolean re_value = false;

        if (isRecord(type, deviceType)) {
            re_value = true;
        } else if (isScreen(type, deviceType)) {
            re_value = true;
        } else if (isProjector(type, deviceType)) {
            re_value = true;
        } else if (isWhiteBoard(type, deviceType)) {
            re_value = true;
        } else if (isDigitalClass(type, deviceType)) {
            re_value = true;
        } else if (isPositioningAntenna(type, deviceType)) {
            re_value = true;
        } else if (isInteractiveTeaching(type, deviceType)) {
            re_value = true;
        } else if (isCenterControl(type, deviceType)) {
            re_value = true;
        }
        return re_value;
    }

    /**
     * 判断设备是否需要通过device服务添加
     *
     * @param deviceType 设备服务对应的设备类型
     * @return
     */
    public static Boolean isNeedDeviceGrid(String deviceType) {
        boolean re_value = true;
        if (CommonNameUtil.HHTSE.toString().equals(deviceType.toLowerCase())) {//安防设备需要用户名，密码，端口号，通道号，是否支持巡课
            re_value = false;
        } else if (CommonNameUtil.HHTAU.toString().equals(deviceType.toLowerCase())) {//音频设备需要用户名，密码，端口号，通道号
            re_value = false;
        } else if (CommonNameUtil.HHTCTE.toString().equals(deviceType.toLowerCase())) {//会议终端设备
            re_value = false;
        } else if (CommonNameUtil.RTSP.toString().equals(deviceType.toLowerCase())) {
            re_value = false;
        }
        return re_value;
    }

    /**
     * 设备修改名称是否需要通知设备
     *
     * @param deviceType 设备服务对应的设备类型
     * @return
     */
    public static Boolean updateNameNeedDeviceGrid(String deviceType) {
        boolean re_value = false;
        if (CommonNameUtil.HHTC.toString().equals(deviceType.toLowerCase())) {//大屏
            re_value = true;
        } else if (CommonNameUtil.HTPR.toString().equals(deviceType.toLowerCase())) {//投影仪
            re_value = true;
        } else if (CommonNameUtil.HHTWB.toString().equals(deviceType.toLowerCase())) {//白板
            re_value = true;
        } else if (CommonNameUtil.HHTDC.toString().equals(deviceType.toLowerCase())) {//数字班牌和展板
            re_value = true;
        } else if (CommonNameUtil.HHTCC.toString().equals(deviceType.toLowerCase())) {//中控设备
            re_value = true;
        }
        return re_value;
    }
}
