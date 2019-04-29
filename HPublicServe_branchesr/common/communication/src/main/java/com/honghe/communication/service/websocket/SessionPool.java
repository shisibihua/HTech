package com.honghe.communication.service.websocket;

import java.util.Set;

/**
 * Created by zhanghongbin on 2015/3/12.
 */
public interface SessionPool<T> {

    /**
     * 根据用户id 删除连接
     *
     * @param key
     */
    public void remove(String key);

    /**
     * 增加用户和连接
     *
     * @param key
     * @param
     */
    public void add(String key, T t);

    /**
     * 是否存在此用户连接
     *
     * @param key
     * @return
     */
    public boolean contains(String key);

    /**
     * 获取此用户连接
     *
     * @param key
     * @return
     */
    public T get(String key);


    /**
     * 获取所有用户
     *
     * @return
     */
    public Set<String> keys();

}
