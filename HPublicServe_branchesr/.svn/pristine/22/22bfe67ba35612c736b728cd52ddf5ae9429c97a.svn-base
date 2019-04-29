package com.honghe.deviceNew.util;

import com.honghe.dao.JdbcTemplate;
import com.honghe.dao.JdbcTemplateUtil;
import org.apache.log4j.Logger;

import java.util.Properties;


/**
 * @author HOUJT
 */
public class JDBCConnectionUtil {
    private static Logger logger = Logger.getLogger("device");
    private static JdbcTemplate jdbcTemplate = null;

    /**
     * 侯骏涛
     *
     * @return 公共服务2.0框架专用获取数据库连接
     */
    public static JdbcTemplate getJdbctemplate() {
        return jdbcTemplate;
    }

    private static Properties jdbcProperties;


    public static Properties getJdbcProperties(){
        return jdbcProperties;
    }

    static {
        try {
            jdbcProperties = JdbcTemplateUtil.getJdbcProperties("config/jdbc.properties");
            String jdbcUrl = jdbcProperties.get("jdbc.url").toString();
            String userName = jdbcProperties.getProperty("jdbc.username").toString();
            String pwd = jdbcProperties.get("jdbc.password").toString();
            jdbcTemplate = JdbcTemplateUtil.getJdbcTemplate(jdbcUrl, userName, pwd);
            logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>获取数据库连接正常！");
            logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>URL:" + jdbcUrl);
            logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>username:" + userName);
            logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>password:" + pwd);
        } catch (Exception e) {
            logger.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>获取数据库连接异常：", e);
            e.printStackTrace();
        }
    }
}
