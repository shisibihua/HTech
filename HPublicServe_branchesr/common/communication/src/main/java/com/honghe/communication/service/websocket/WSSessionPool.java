package com.honghe.communication.service.websocket;

import javax.websocket.Session;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 会话池 用来维护用户和连接
 * User: zhanghongbin
 * Date: 14-9-17
 * Time: 上午10:28
 */
final class WSSessionPool implements SessionPool<Session> {

    private WSSessionPool() {
        sessionPool = new ConcurrentHashMap();
    }

    public static SessionPool INSTANCE() {
        return wsSessionPool;
    }

    private Map<String, Session> sessionPool;
    private static WSSessionPool wsSessionPool;

    static {
        wsSessionPool = new WSSessionPool();

    }

    /**
     * 根据用户id 删除连接
     *
     * @param key
     */
    public final void remove(String key) {
        sessionPool.remove(key);
    }

    /**
     * 增加用户和连接
     *
     * @param key
     * @param session
     */
    public final void add(String key, Session session) {
        sessionPool.put(key, session);
    }

    /**
     * 是否存在此用户连接
     *
     * @param key
     * @return
     */
    public final boolean contains(String key) {
        return sessionPool.containsKey(key);
    }

    /**
     * 获取此用户连接
     *
     * @param key
     * @return
     */
    public final Session get(String key) {
        return sessionPool.get(key);
    }


    /**
     * 获取所有用户
     *
     * @return
     */
    public final Set<String> keys() {
        return sessionPool.keySet();
    }


}
