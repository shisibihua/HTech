package com.honghe.dao.connection;

import java.sql.Connection;

/**
 *
 * Created by zhanghongbin on 2015/5/13.
 */
public interface DBConnection {

    /**
     *
     * @param connectInfo
     * @return
     */
    public Connection getConnection(String connectInfo);
}
