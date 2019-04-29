package com.honghe.sys.cache;

import com.honghe.cache.Cache;
import com.honghe.cache.CacheFactory;
import com.honghe.permissions.bean.Permissions;
import com.honghe.sys.dao.Sys2PermissionsDao;

/**
 * Created by zhanghongbin on 2016/10/10.
 */
public final class Sys2PermissionsCacheDao extends Sys2PermissionsDao {

    @Override
    public Permissions find(String sysName) {
        //分布式cache不能使用
        Cache cache = CacheFactory.newIntance();
        Object obj = cache.get("user.sys2permissions");
        if (obj == null) {
            cache.put("user.sys2permissions", super.find(sysName));
        }
        return (Permissions) cache.get("user.sys2permissions");
    }
}
