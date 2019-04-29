package com.honghe.dao.connection;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by zhanghongbin on 2015/5/13.
 */
 public final class DBConnectionFactory {

    public enum Type {
        JNDIPOOL, MYSQL_JDBC,H2_JDBC,SYBASE
    }

    private static Map<Integer, DBConnection> dbConnectionMap = new HashMap<>();

    static {
        dbConnectionMap.put(0, new JNDIConectionPool());
        dbConnectionMap.put(1, new MySQLJdbcConnection());
        dbConnectionMap.put(2, new H2JdbcConnection());
        dbConnectionMap.put(3, new SybaseJdbcConnection());
    }

    private DBConnectionFactory() {

    }

    /**
     *
     * @param type
     * @return
     */
    public final static DBConnection newInstance(Type type) {
        return dbConnectionMap.get(type.ordinal());
    }
}
