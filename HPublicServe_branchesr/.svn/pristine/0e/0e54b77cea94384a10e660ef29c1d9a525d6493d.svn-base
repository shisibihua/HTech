package com.honghe.device.util;

/**
 * Created by lyx on 2016-06-30.
 */
public final class DeviceNameUtil {
    public final static String DEVICE_NORMALIZATION_RECOURD = "0"; //常态化录播主机
    public final static String DEVICE_FINE_RECOURD = "1"; //精品录播主机
    public static final String DEVICE_EMBEDDED_RECOURD = "2";//嵌入式精品录播主机(ZJ0500)
    public final static String DEVIC_SCREEN = "3";  //大屏设备
    public final static String DEVICE_TBOX_RECOURD = "4"; //TBOX
    public final static String DEVICE_PROJECTOR = "5"; //投影机
    public final static String DEVICE_CLASSROOMMONITOR = "6"; //教室监控
    public final static String DEVICE_WHITEBOARD = "7"; //白板一体机
    public final static String DEVICE_DIGSIGNBOARD = "8"; //数字班牌
    public final static String DEVICE_POSITIONANTENNA = "9"; //定位天线
    public final static String DEVICE_WBOX_RECOURD = "20"; //WBOX
    public final static String DEVICE_VIDEO_CONFERENCING = "21"; //视频会议设备
    public final static String DEVICE_VIDEO_VHD = "22"; //威海德mcu
    public final static String DEVICE_CENTERCONTROL = "14"; //中控设备
    /**
     * 依据设备具体类型获取服务地址
     *
     * @param type
     * @return
     */
    public static final String getDeviceUrl(String type, String ip) {
        String re_value = "";
        if (DEVICE_NORMALIZATION_RECOURD.equals(type)) {//简易
            re_value = "http://" + ip + ":8080/onvif/device_service";
        } else if (DEVIC_SCREEN.equals(type) || DEVICE_PROJECTOR.equals(type)||DEVICE_WHITEBOARD.equals(type) || DEVICE_DIGSIGNBOARD.equals(type)) {//大屏  投影机 白板一体机 数字班盘
            re_value = "http://" + ip + ":40001/HHTControl";
        } else if (DEVICE_FINE_RECOURD.equals(type) || DEVICE_EMBEDDED_RECOURD.equals(type)||DEVICE_TBOX_RECOURD.equals(type)) { //精品
            re_value = "http://" + ip + ":31111/DeviceBinding";
        } else if (DEVICE_POSITIONANTENNA.equals(type)) {
            re_value = "http://" + ip + ":5000/positionantenna";
        } else if (DEVICE_WBOX_RECOURD.equals(type)) {
            re_value = "http://" + ip +":5010/wbox";
        } else if (DEVICE_VIDEO_CONFERENCING.equals(type)||DEVICE_VIDEO_VHD.equals(type)){
            re_value = "http://" + ip +":80/mcuService";
        } else if (DEVICE_CENTERCONTROL.equals(type)){
            re_value = "http://" + ip +":41795/ctrlService";
        }
        return re_value;
    }
}
