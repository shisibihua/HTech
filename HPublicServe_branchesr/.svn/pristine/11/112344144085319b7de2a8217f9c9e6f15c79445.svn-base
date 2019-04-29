package com.honghe.dao.connection;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 * Created by zhanghongbin on 2015/5/13.
 */
public class JNDIConectionPool implements DBConnection {

    private static Map<String, DataSource> dataSourceMap = new HashMap<>();

    JNDIConectionPool() {

    }

    public final static Map<String, String> getConnectionInfo(String connectInfo) {
        if (!dataSourceMap.containsKey(connectInfo)) {
            dataSourceMap.put(connectInfo, _getDataSource(connectInfo));
        }
        Map<String,String> conInfo = new HashMap<>();
        DataSource dataSource = dataSourceMap.get(connectInfo);
        if (dataSource != null) {
            if (dataSource instanceof DruidDataSource) {
                DruidDataSource druidDataSource = (DruidDataSource) dataSource;
                conInfo.put("userName",druidDataSource.getUsername());
                conInfo.put("password",druidDataSource.getPassword());
                conInfo.put("url",druidDataSource.getUrl());
            }
        }

        return conInfo;
    }


    @Override
    public Connection getConnection(String connectInfo) {
        synchronized (this) {
            if (!dataSourceMap.containsKey(connectInfo)) {
                dataSourceMap.put(connectInfo, _getDataSource(connectInfo));
            }
        }
        try {
            return dataSourceMap.get(connectInfo).getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static final DataSource _getDataSource(String connectInfo) {
        try {
            Context initCtx = new InitialContext();
            Context envContext = (Context) initCtx.lookup("java:/comp/env");
            return (DataSource) envContext.lookup(connectInfo);
        } catch (Exception e) {
            try{
                Properties properties = new Properties();
                InputStream inputStream = JNDIConectionPool.class.getResourceAsStream("/db.properties");
                if (inputStream == null) return null;
                properties.load(inputStream);
                return DruidDataSourceFactory.createDataSource(properties);
            }catch (Exception ee){
                ee.printStackTrace();
            }

            return null;
        }
    }



}
