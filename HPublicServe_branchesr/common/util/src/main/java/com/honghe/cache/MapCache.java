package com.honghe.cache;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanghongbin on 2016/10/19.
 */
@Deprecated
public class MapCache implements Cache  {

    @Override
    public List<String> keys(String pattern) {
        return null;
    }

    private Map<String, Object> _cache = new LinkedHashMap();
    public static final MapCache INSTANCE = new MapCache();

    public Object get(String key)
    {
        return this._cache.get(key);
    }

    public void put(String key, Object value)
    {
        this._cache.put(key, value);
    }

    public void remove(String key)
    {
        this._cache.remove(key);
    }

    public List<String> keys()
    {
        List<String> keys = new ArrayList();
        keys.addAll(this._cache.keySet());
        return keys;
    }

    @Override
    public void clear(String prefixKey) {

    }
}
