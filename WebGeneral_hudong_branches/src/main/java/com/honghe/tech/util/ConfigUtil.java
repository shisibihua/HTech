package com.honghe.tech.util;

import com.honghe.communication.util.WebRootUtil;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtil {

    static Logger logger = Logger.getLogger(ConfigUtil.class);

    public static final ConfigUtil INSTANCE = new ConfigUtil();

    private ConfigUtil(){

    }
    /**
     * 读取配置文件
     * @param key 想要获取的值
     * @return
     */
    public static Object getConfig(String key){
        Object value = null;
        Properties properties = new Properties();
        String filePath=WebRootUtil.getWebRoot() + "config/tech-config.properties";
        try {
            properties.load(new FileInputStream(filePath));
            if(properties.containsKey(key) && !"".equals(properties.getProperty(key))){
                value =  properties.get(key);
            }
        }catch (IOException e) {
            logger.error("读取配置文件出现异常,key:"+key,e);
        }
        return value;
    }
}
