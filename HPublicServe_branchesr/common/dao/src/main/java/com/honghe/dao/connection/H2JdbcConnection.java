package com.honghe.dao.connection;

import org.h2.tools.Server;

import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: 北京鸿合盛视数字媒体技术有限公司</p>
 *
 * @author zhanghongbin
 * @date 2016/1/19
 */
class H2JdbcConnection implements DBConnection {

    private static Server server = null;

    static {

        try {
            Class.forName("org.h2.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                if (server != null)
                    server.stop();
            }
        }));
    }


    @Override
    public Connection getConnection(String connectInfo) {
        synchronized (H2JdbcConnection.class) {
            if (server == null) {
                try {
                    server = Server.createTcpServer(new String[0]).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        try{
            String path = URLDecoder.decode(H2JdbcConnection.class.getResource("/").getPath(), "UTF-8") + "h2";
            System.out.println(path + " h2 file path");
            return DriverManager.getConnection("jdbc:h2:/" + path, "sa", "");
        }catch (Exception e){
            return null;
        }
    }

}
