package com.honghe;

import org.apache.log4j.Logger;

public abstract class AbstractService implements ServiceInterface {
    public Logger logger = getUserLogger();

    @Override
    public Logger getUserLogger() {
        return Logger.getLogger("user");
    }
}
