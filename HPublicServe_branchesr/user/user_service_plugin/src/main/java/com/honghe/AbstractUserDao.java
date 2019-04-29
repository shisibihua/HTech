package com.honghe;

import org.apache.log4j.Logger;

/**
 * @author HOUJT
 * 抽象基础dao类，提供了一些常用工具，各个业务模块可在此基础上继承
 */
public abstract class AbstractUserDao implements LoggerInterface {
    public Logger logger = getUserLogger();

    @Override
    public Logger getUserLogger() {
        return Logger.getLogger("user");
    }

}
