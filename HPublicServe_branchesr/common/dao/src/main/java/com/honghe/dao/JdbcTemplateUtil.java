package com.honghe.dao;

import com.honghe.dao.connection.JNDIConectionPool;
import com.honghe.dao.importer.SQLFileImporter;
import com.honghe.dao.importer.SQLFileOnceImporter;

import java.util.Map;


/**
 * Created by zhanghongbin on 2015/6/30.
 */
public final class JdbcTemplateUtil {


    private static String CONNECTINFO = "jdbc/service";

    private JdbcTemplateUtil() {

    }

    private static JdbcTemplate jdbcTemplate = new JdbcTemplate(CONNECTINFO);

    @Deprecated
    public final static JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public final static JdbcTemplate getJdbcTemplate(String connectionInfo) {
        return new JdbcTemplate(connectionInfo);
    }

    @Deprecated
    public static final void setConnectionInfo(String connectionInfo) {
        CONNECTINFO = connectionInfo;
        jdbcTemplate = new JdbcTemplate(connectionInfo);
    }

    public final static void executeUserSQLFile(String sqlContent, String dbName, String sqlDelimiter) {
        new SQLFileImporter().importFile(CONNECTINFO, sqlContent, dbName, sqlDelimiter);
    }

    @Deprecated
    public final static void executeUserSQLFile(String sqlContent) {
        executeUserSQLFile(sqlContent, "service", ";");
    }

    @Deprecated
    public final static void executeUserTriggerFile(String triggerContent) throws Exception {
        if (CONNECTINFO.startsWith("jdbc:")) {
            jdbcTemplate.connnectionUrl = jdbcTemplate.connnectionUrl + "&allowMultiQueries=true";
            boolean flag = jdbcTemplate.execute(triggerContent);
            if (!flag) {
                throw new Exception("trigger import failure");
            }
        } else {
            Map<String, String> connectionInfo = JNDIConectionPool.getConnectionInfo(CONNECTINFO);
            String url = connectionInfo.get("url") + "&user=" + connectionInfo.get("userName") + "&password="
                    + connectionInfo.get("password") + "&allowMultiQueries=true";
            boolean flag = new JdbcTemplate(url).execute(triggerContent);
            if (!flag) {
                throw new Exception("trigger import failure");
            }
        }
    }

    public final synchronized static void exeucteOneUserSQLFile(Class _class, String sqlContent, String dbName) {
        exeucteOneUserSQLFile(_class, sqlContent, dbName, ";");
    }

    public final synchronized static void exeucteOneUserSQLFile(Class _class, String sqlContent, String dbName, String sqlDelimiter) {
        new SQLFileOnceImporter(_class).importFile(CONNECTINFO, sqlContent, dbName, sqlDelimiter);
    }

    @Deprecated
    public final synchronized static void exeucteOneUserSQLFile(Class _class, String sqlContent) {
        exeucteOneUserSQLFile(_class, sqlContent, "service");
    }


}
