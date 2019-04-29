package com.honghe.cache;

import org.apache.mahout.math.map.OpenHashMap;

import java.util.*;

/**
 * Created by zhanghongbin on 2016/7/15.
 */
public final class EfficientCache implements Cache {

    private OpenHashMap openHashMap = new OpenHashMap();
    public final static EfficientCache INSTANCE = new EfficientCache();

    private EfficientCache() {

    }

    @Override
    public List<String> keys(String pattern) {
        return this.keys();
    }

    @Override
    public Object get(String key) {
        return openHashMap.get(key);
    }

    @Override
    public void put(String key, Object value) {
        openHashMap.put(key, value);
    }

    @Override
    public void remove(String key) {
        openHashMap.remove(key);
    }

    @Override
    public List<String> keys() {
        List<String> keys = new ArrayList<>();
        openHashMap.keys(keys);
        return keys;
    }

    @Override
    public void clear(String prefixKey) {
        Set<String> keySet = openHashMap.keySet();
        for (String key : keySet) {
            if (key.startsWith(prefixKey)) {
                this.remove(key);
            }
        }
    }
}
