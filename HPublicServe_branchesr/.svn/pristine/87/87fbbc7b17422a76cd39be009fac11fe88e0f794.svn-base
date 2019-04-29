package com.honghe.role.cache;

import com.honghe.cache.Cache;
import com.honghe.cache.CacheFactory;
import com.honghe.role.dao.Role2UserTypeDao;

import java.util.Map;

/**
 * Created by zhanghongbin on 2016/10/11.
 */
public final class Role2UserTypeCacheDao extends Role2UserTypeDao {


    @Override
    public final Map<String, Map<String, String>> find() {
        Cache cache = CacheFactory.newIntance();
        Object obj = cache.get("user.role2type");
        if (obj == null) {
            cache.put("user.role2type", super.find());
        }
        return (Map<String, Map<String, String>>) cache.get("user.role2type");
    }

    @Override
    public boolean add(Map<String, Object> data) {
        boolean flag = super.add(data);
        if(flag){
            CacheFactory.newIntance().remove("user.role2type");
        }
        return flag;
    }

    @Override
    public boolean delete(String roleId) {
        boolean flag = super.delete(roleId);
        if(flag){
            CacheFactory.newIntance().remove("user.role2type");
        }
        return flag;
    }




}
