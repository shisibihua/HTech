package com.honghe;

import org.apache.log4j.Logger;

/**
 * @author HOUJT
 */
public interface LoggerInterface {
    /**
     * 获取日志记录类
     * 为了能够实现各个服务的日志分开存放，日志记录类的获取必须采用getLogger(String name)方法。
     * "name"为本服务代号，例如本服务就是"user"
     *
     * @return
     */
    Logger getUserLogger();
}
