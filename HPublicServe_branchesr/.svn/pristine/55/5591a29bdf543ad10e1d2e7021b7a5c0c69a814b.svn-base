package com.honghe.dao.connection;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by zhanghongbin on 2015/5/13.
 */
class MySQLJdbcConnection implements DBConnection {
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
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

    public static void main(String[] args) throws Exception {
        MySQLJdbcConnection jdbcConnection = new MySQLJdbcConnection();
        Connection connection = jdbcConnection.getConnection("jdbc:mysql://192.168.17.170:8788/nvrmanager?user=root&password=admin&useUnicode=true&characterEncoding=utf8");
        System.out.println(connection.isClosed());
    }
}
