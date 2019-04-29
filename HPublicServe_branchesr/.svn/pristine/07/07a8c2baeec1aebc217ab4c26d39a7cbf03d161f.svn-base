package com.honghe.campus.loadbalance;

import com.honghe.cache.Cache;
import com.honghe.cache.CacheFactory;

import java.util.List;

/**
 * Created by zhanghongbin on 2016/9/30.
 */
public final class SipServerLoadBalance {


    public final static void initSipServer() {

    }

    public final static String getSipServer() {
        Cache cache = CacheFactory.newIntance();
        Object obj = cache.get("config.sipserver");
        if (obj == null) return "";
        LoadBalance loadBalance = new RandomLoadBanlance((List) obj);
        return loadBalance.get();
    }

}
