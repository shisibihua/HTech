package com.honghe.dao.connection;

import jodd.db.connection.ConnectionProvider;

import java.sql.Connection;

/**
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: 北京鸿合盛视数字媒体技术有限公司</p>
 *
 * @author zhanghongbin
 * @date 2015/12/16
 */
public class JdbcConnectionProvider implements ConnectionProvider {

    private Connection connection;

    public JdbcConnectionProvider(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void init() {

    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void closeConnection(Connection connection) {
        ConnectionUtil.closeConnection(connection);

    }

    @Override
    public void close() {

    }
}
