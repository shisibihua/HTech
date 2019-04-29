package com.honghe.deviceNew.util;

import com.honghe.deviceNew.dao.DeviceHostMapper;
import com.honghe.deviceNew.service.DeviceSpecService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

@Controller
public class CommandNewUtil {
    public final static CommandNewUtil INSTANCE = new CommandNewUtil();

    @Autowired
    DeviceHostMapper deviceHostMapper;

    @Autowired
    DeviceSpecService deviceSpecService ;


    private static Logger logger = Logger.getLogger("device");

    /**
     * 获取不同设备类型对应的授权编号
     * @param deviceType 设备类型
     * @return
     * @throws Exception
     */
    public static String getSysNum(String deviceType) {
        String sysNum = "";
        if (deviceType==null||"".equals(deviceType)) return sysNum;
        Properties properties = new Properties();
        FileReader fr = null;
        String fileName = CommandNewUtil.class.getResource("/").getPath()+"config/deviceSysNum.properties";
        try {
            fr = new FileReader(fileName);
            if (fr!=null){
                properties.load(fr);
                fr.close();
                sysNum = properties.getProperty(deviceType.toLowerCase());
            }
        } catch (FileNotFoundException e) {
            logger.error("配置文件未找到",e);
            return sysNum;
        } catch (IOException e) {
            logger.error("流读取异常",e);
        }
        return sysNum;
    }

}
