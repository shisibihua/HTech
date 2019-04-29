package com.honghe.dao.connection;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by zhanghongbin on 2016/8/27.
 */
public class SybaseJdbcConnection implements DBConnection {


    static {
        try {
            Class.forName("com.sybase.jdbc3.jdbc.SybDriver");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    public Connection getConnection(String connectInfo) {
        try {
            return DriverManager.getConnection(connectInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
