package com.honghe.web.device.util;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * Created by sunchao on 2016/10/13.
 */
public class TypeTransform {

    public static final Logger logger = Logger.getLogger(TypeTransform.class);

    /**
     * 将设备类型英文名转换成中文名
     * @param type 英文类型
     * @return
     */
    public static String transform(String type) {
        try
        {
            if (type.toUpperCase().equals("HHTC")) {
                type = "大屏设备";
            } else if (type.toUpperCase().equals("NVR")) {
                type = "录播设备";
            } else if (type.toUpperCase().equals("HTPR")) {
                type = "投影仪设备";
            } else if (type.toUpperCase().equals("HHTWB")) {
                type = "电子白板";
            } else if (type.toUpperCase().equals("HHTPA")) {
                type = "定位天线";
            } else if (type.toUpperCase().equals("HHTDC")) {
                type = "数字班牌";
            } else if (type.toUpperCase().equals("HHTSE")) {
                type = "安防设备";
            } else if (type.toUpperCase().equals("HHTAU")) {
                type = "音频设备";
            } else if (type.toUpperCase().equals("IPC")) {
                type = "镜头";
            }else if (type.toUpperCase().equals("HHTCTE")) {
                type = "会议终端设备";
            }else if (type.toUpperCase().equals("HHTMCU")) {
                type = "视频会议设备";
            }else {
                type = "未知设备";
            }
        }
        catch (Exception e) {
            logger.error("转换名称错误", e);
        }
    return type;

    }
}
