package com.honghe.web.device.util;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by yanxue on 2015-07-23.
 * 设备类型标识类
 */
public class DeviceTypeNameUtil {
    private final static Logger logger = Logger.getLogger(DeviceTypeNameUtil.class);

    public final static String DEVICE_TYPE_SCREEN = "hhtc";  //大屏设备
    public final static String DEVICE_TYPE_RECOURD = "hhrec"; //录播设备
    public final static String DEVICE_TYPE_PROJECTOR = "htpr"; //投影仪设备
    public static final String DEVICE_TYPE_WHITEBOARD = "hhtwb";//电子白板
    public static final String DEVICE_TYPE_POSITIONINGANTENNA = "hhtpa";//定位天线
    public static final String DEVICE_TYPE_DigitalClass = "hhtdc";//数字班牌(校宣)
    public static final String DEVICE_TYPE_SECURITYEQUIPMENT = "hhtse";//安防设备(视频)
    public static final String DEVICE_TYPE_AudioEquipment = "hhtau";//音频设备
    public static final String DEVICE_TYPE_VIDEOCONFERENCING = "hhtmcu"; //视频会议设备
    public static final String DEVICE_TYPE_CENTERCONTROL = "hhtcc";//中控设备
    public static final String DEVICE_TYPE_CONFERENCETERMINAL = "hhtcte";//会议终端设备
    public final static String DEVICE_TYPE_UNKNOWN = "unknown"; //未知设备
    public final static String DEVICE_TYPE_ALL = ""; //所有设备


    /**
     * 测试log4j输出
     *
     * @param arg
     */
    public static void main(String[] arg) {
        File file = new File("d:\\adfasf.txt");
        try {
            InputStream input = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            logger.info(" e.printStackTrace(),输出：");
            e.printStackTrace();
            logger.info("logger.error(e.toString())，输出：");
            logger.error(e.toString());
            logger.info("logger.error(e)，输出：");
            logger.error("", e);
            logger.info("logger.error(\"\",e)，输出：");
            logger.error("错误", e);

        }
    }
}