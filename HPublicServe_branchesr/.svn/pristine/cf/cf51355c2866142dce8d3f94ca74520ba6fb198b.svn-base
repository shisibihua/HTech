package com.honghe.device.util;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.util.Timer;
import java.util.TimerTask;

/**
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: 北京鸿合盛视数字媒体技术有限公司</p>
 *
 * @author Tpaduser
 * @date 2015/11/3
 */
public final class NetWorkFileTimerScannerUtil {
    final static Logger logger = Logger.getLogger(NetWorkFileTimerScannerUtil.class);
    private static Timer timer = new Timer();
    private static String path = "";

    static {
        try {
            setIp(java.net.InetAddress.getLocalHost().getHostAddress()); //获取ip
        } catch (Exception e) {
        }
        path = getNetWorkPath();
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                try {
                    NetWorkFileTimerScannerUtil.timer.cancel();
                } catch (Exception e) {
                }
            }
        }));
    }

    private static String getNetWorkPath() {
        String _path = new File("").getAbsolutePath();
        int startIndex = _path.toLowerCase().lastIndexOf("tomcat");
        if (startIndex != -1) {
            startIndex = _path.lastIndexOf("\\", startIndex);
            _path = _path.substring(0, startIndex) + File.separator + "DeviceGrid" + File.separator + "Caching" + File.separator + "Network.json";
        }
        return _path;
    }

    public static void start() {
        timer.schedule(new TimerTask() {
            public void run() {
                check();
            }
        }, 0, 60000L);
    }

    public static void setIp(String ip) {
        System.setProperty("ip", ip);
    }

    private static void check() {
        logger.debug("获取设备接入服务ip的文件path为:" + path);
        if (path.equals("")) {
            return;
        }
        File file = new File(path);
        String content = "";
        if (file.exists()) {
            try {
                content = IOUtils.toString(new FileInputStream(file), "UTF-8");
            } catch (Exception e) {
            }
            int startIndex = content.indexOf(":\"");
            int endIndex = content.indexOf("\"", startIndex + 2);
            if ((startIndex != -1) && (endIndex != -1)) {
                String ip = content.substring(startIndex + 2, endIndex).trim();
                logger.debug("获取设备接入服务ip为:" + ip);
                setIp(ip);
            }
        }
    }

}
