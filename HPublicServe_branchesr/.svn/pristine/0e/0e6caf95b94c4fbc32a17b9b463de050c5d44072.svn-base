package com.honghe.ad.util;

import com.honghe.dao.JdbcTemplate;

import java.util.Properties;

/**
 * @author hansho
 * @create 2018-01-16 9:20
 */
public class JdbcTemplateUtil {
    private JdbcTemplateUtil(){

    }

    private static Properties properties;
    static{
        properties = com.honghe.dao.JdbcTemplateUtil.getJdbcProperties("config/jdbc.properties");
    }

    public static final Properties getProperties(){
        return properties;
    }
    public static final JdbcTemplate getJdbcTemplate(){
        return com.honghe.dao.JdbcTemplateUtil.getJdbcTemplate(
                properties.getProperty("jdbc.url",""),
                properties.getProperty("jdbc.username",""),
                properties.getProperty("jdbc.password",""));
    }
}
