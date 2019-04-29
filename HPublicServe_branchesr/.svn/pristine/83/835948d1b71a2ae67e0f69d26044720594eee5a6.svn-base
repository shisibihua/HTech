package com.honghe.dao.connection;

import java.sql.Connection;

/**
 * Created by zhanghongbin on 2015/5/13.
 */
public final class ConnectionUtil {

    private ConnectionUtil() {

    }

    /**
     * @return
     */
    public final static Connection getConnection(String url) {
        if (url.indexOf("mysql") != -1) {
            return DBConnectionFactory.newInstance(DBConnectionFactory.Type.MYSQL_JDBC).getConnection(url);
        } else if (url.indexOf("sybase") != -1) {
            return DBConnectionFactory.newInstance(DBConnectionFactory.Type.SYBASE).getConnection(url);
        } else if (url.equals("")) {
            return DBConnectionFactory.newInstance(DBConnectionFactory.Type.H2_JDBC).getConnection(url);
        }
        return DBConnectionFactory.newInstance(DBConnectionFactory.Type.JNDIPOOL).getConnection(url);
    }

    public final static void closeConnection(Connection connecton) {
        if (connecton != null) {
            try {
                if (!connecton.isClosed()) {
                    connecton.close();
                }
            } catch (Exception e) {
            }
        }
    }
}
