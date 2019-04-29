package com.honghe.deviceNew.enumentity;

/**
 * Created by lyx on 2015-12-09.
 * 设备类型命名类 
 */
public enum DeviceTypeNameEnum {

    ALL(""),//所有设备类型
    HHTC("hhtc"), //大屏设备
    HHREC("hhrec"),//录播设备
    UNKNOWN("unknown"),//未知设备
    OTHER("other"),//其他设备
    HTPR("htpr"), //投影机设备
    HHTWB("hhtwb"),//白板一体机
    HHTDC("hhtdc"),//数字班牌
    HHTPA("hhtpa"),//定位天线
    HHTSE("hhtse"),//安防设备
    HHTAU("hhtau"),//音频设备
    HHTCTE("hhtcte"),//会议终端设备
    HHTCC("hhtcc"),//中控设备
    HHTMCU("hhtmcu"),//视频会议设备
    RTSP("rtsp"),//安防rtsp设备
    //子类型
    DEVICE_NORMALIZATION_RECOURD ("0") , //常态化录播主机
    DEVICE_FINE_RECOURD ("1") ,//精品录播主机
    DEVICE_EMBEDDED_RECOURD ("2"),//嵌入式精品录播主机(ZJ0500)
    DEVIC_SCREEN ("3") ,  //大屏设备
    DEVICE_TBOX_RECOURD ("4"), //TBOX
    DEVICE_PROJECTOR ("5") , //投影机
    DEVICE_CLASSROOMMONITOR ("6") , //教室监控
    DEVICE_WHITEBOARD ("7"), //白板一体机
    DEVICE_DIGSIGNBOARD ("8") , //数字班牌
    DEVICE_POSITIONANTENNA ("9"), //定位天线
    DEVICE_CENTERCONTROL ("14"), //中控设备
    DEVICE_WBOX_RECOURD ("20"), //WBOX
     DEVICE_VIDEO_CONFERENCING ("21"); //视频会议设备
    private String name;//设备名

    DeviceTypeNameEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    // 普通方法
    public static DeviceTypeNameEnum getName(String name) {
        for (DeviceTypeNameEnum c : DeviceTypeNameEnum.values()) {
            if (c.name.equals(name)) {
                return c;
            }
        }
        return null;
    }

    /**
     * 赋值给设备默认的具体设备类型id
     * @param deviceType
     * @return
     */
    public static int getDespecIdByType(String deviceType){
        String type = deviceType==null?"":deviceType.toLowerCase();
        int specId = 0;
        if (HHTSE.toString().equals(type)){//安防设备
            specId = 21;
        }else if (HHTAU.toString().equals(type)){ //音频设备
            specId = 22;
        }else if (HHTCTE.toString().equals(type)) {//会议终端设备
            specId = 34;
        } else if (RTSP.toString().equals(type)) {
        } else if (HHTDC.toString().equals(type)){
            specId = 18;
        }else if (HHTC.toString().equals(type)){
            specId = 13;
        }else if (HHREC.toString().equals(type)){
            specId = 2;
        }else if (HHTMCU.toString().equals(type)){
            specId = 39;
        }
        return specId;
    }
}
