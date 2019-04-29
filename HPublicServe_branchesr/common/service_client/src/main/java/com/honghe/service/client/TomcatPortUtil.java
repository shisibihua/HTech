package com.honghe.service.client;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import java.util.Iterator;
import java.util.Set;

/**
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: 北京鸿合盛视数字媒体技术有限公司</p>
 *
 * @author zhanghongbin
 * @date 2015/12/22
 */
public final class TomcatPortUtil {

    private int port = 0;
    public final static TomcatPortUtil INSTANCE = new TomcatPortUtil();

    public int getPort() {
        if (this.port == 0) {
            this.port = 8080;
        }
        return this.port;
    }

    private TomcatPortUtil() {
        port = getTomcatHttpPort();
        System.out.println(port);
    }

    private Integer getTomcatHttpPort() {
        MBeanServer mBeanServer = null;
        if (MBeanServerFactory.findMBeanServer(null).size() > 0) {
            mBeanServer = (MBeanServer) MBeanServerFactory.findMBeanServer(null).get(0);
        }
        Set names = null;
        try {
            names = mBeanServer.queryNames(new ObjectName("Catalina:type=Connector,*"), null);
            Iterator it = names.iterator();
            ObjectName oname;
            while (it.hasNext()) {
                oname = (ObjectName) it.next();
                Object protocol = mBeanServer.getAttribute(oname, "protocol");
                if(protocol != null){
                    if(protocol.toString().startsWith("HTTP")){
                        return ((Integer) mBeanServer.getAttribute(oname, "port"));
                    }
                }

            }
        } catch (Exception e) {
            return 0;
        }
        return 0;
    }

}
