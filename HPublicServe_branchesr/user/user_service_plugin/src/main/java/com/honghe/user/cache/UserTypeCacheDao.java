package com.honghe.user.cache;

import com.honghe.cache.Cache;
import com.honghe.cache.CacheFactory;
import com.honghe.user.dao.UserTypeDao;

import java.util.List;
import java.util.Map;

/**
 * Created by zhanghongbin on 2016/10/10.
 */
public final class UserTypeCacheDao extends UserTypeDao {

    @Override
    public List<Map<String, String>> find() {
        Cache cache = CacheFactory.newIntance();
        Object obj = cache.get("user.type");
        if (obj == null) {
            cache.put("user.type", super.find());
        }
        return (List<Map<String, String>>) cache.get("user.type");
    }

    @Override
    public Map<String, String> findByTypeName(String typeName) {
        return super.findByTypeName(typeName);
    }
}
