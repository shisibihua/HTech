package com.honghe.tech.cache;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * 保存南京旭顶会议id到缓存
 * @author caoqian
 */
public class CacheMap {
    private final static Logger logger=Logger.getLogger(CacheMap.class);
    public static Map<String,String> cacheMap=new HashMap<>();

    public static void pushCacheMap(String key,String value) {
        logger.debug("活动id="+key+",会议id="+value+",放到缓存中。");
        cacheMap.put(key,value);
    }
    public static String getCacheMap(String key) {
        if(cacheMap.containsKey(key)) {
            logger.debug("活动id="+key+",获取缓存中会议id，confID="+cacheMap.get(key));
            return cacheMap.get(key);
        }
        return "";
    }
    public static void removeCacheMap(String key) {
        if(cacheMap.containsKey(key)) {
            logger.debug("活动id="+key+",清除缓存中会议id，confID="+cacheMap.get(key));
            cacheMap.remove(key);
        }
    }
}