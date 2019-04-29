package com.honghe.user.util;

import com.honghe.dao.JdbcTemplate;

import java.util.Properties;

/**
 * @Description
 * @Author sunchao
 * @Date: 2018-01-16 10:08
 * @Mender:
 */

public final class JdbcTemplateUtil {

    private static Properties properties;

    public static String url;

    public static String userName;

    public static String password;

    private JdbcTemplateUtil() {

    }

    static {
        properties = com.honghe.dao.JdbcTemplateUtil.getJdbcProperties("config/jdbc.properties");
        url = properties.getProperty("jdbc.url", "jdbc:mysql://localhost:8788/service?useUnicode=true&characterEncoding=utf8");
        url = url.replace("8066","8788");
        userName =  properties.getProperty("jdbc.username", "root");
        password = properties.getProperty("jdbc.password", "bhjRjxwC8EBqaJC7");

    }

    public static final Properties getProperties() {
        return properties;
    }

    public static final JdbcTemplate getJdbcTemplate() {
        return com.honghe.dao.JdbcTemplateUtil.getJdbcTemplate(url, userName, password);
    }

}
