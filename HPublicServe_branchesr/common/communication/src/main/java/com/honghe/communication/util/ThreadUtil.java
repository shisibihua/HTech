package com.honghe.communication.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: 北京鸿合盛视数字媒体技术有限公司</p>
 *
 * @author zhanghongbin
 * @date 2015/12/18
 */
@Deprecated
public final class ThreadUtil {

    private static final ExecutorService executorService = java.util.concurrent.Executors.newCachedThreadPool();

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                executorService.shutdown();
            }
        }));
    }

    private ThreadUtil() {

    }

    public final static void execute(Runnable runnable) {
        executorService.execute(runnable);
    }


    public final static Map<String, Object> invokeAll(Collection<Callable<Map<String, Object>>> callables) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Future<Map<String, Object>>> futureList = executorService.invokeAll(callables);
            for (Future<Map<String, Object>> future : futureList) {
                result.putAll(future.get());
            }
        } catch (Exception e) {
        } finally {
            executorService.shutdown();
        }
        return result;

    }
}
