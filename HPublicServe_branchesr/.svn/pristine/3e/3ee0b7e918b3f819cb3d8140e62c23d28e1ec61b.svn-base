package com.honghe.cache;

import java.util.*;

/**
 * Created by zhanghongbin on 2015/6/16.
 */
public final class MapCache implements Cache {

    private Map<String, Object> _cache = new LinkedHashMap<>();
    public final static MapCache INSTANCE = new MapCache();

    private MapCache() {

    }

    @Override
    public Object get(String key) {
        return _cache.get(key);
    }

    @Override
    public void put(String key, Object value) {
        _cache.put(key, value);
    }

    @Override
    public void remove(String key) {
        _cache.remove(key);
    }

    @Override
    public List<String> keys() {
        List<String> keys = new ArrayList<>();
        keys.addAll(_cache.keySet());
        return keys;
    }
}
