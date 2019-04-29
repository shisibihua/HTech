package com.honghe.communication.util;

import java.util.Timer;

/**
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: 北京鸿合盛视数字媒体技术有限公司</p>
 *
 * @author zhanghongbin
 * @date 2016/3/24
 */
@Deprecated
public final class TimerUtil {

    private static Timer timer = new Timer("TimerUtil", true);

    private TimerUtil() {

    }

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                timer.cancel();
            }
        }));
    }

    public final static Timer getTimer() {
        return timer;
    }
}
