package com.honghe.sys.cache;

import com.honghe.cache.Cache;
import com.honghe.cache.CacheFactory;
import com.honghe.user.util.JdbcTemplateUtil;
import com.honghe.sys.dao.SysDao;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zhanghongbin
 * @date 2016/10/10
 */
public final class SysCacheDao extends SysDao {

    private SysCacheDao() {

    }

    public final static SysCacheDao INSTANCE = new SysCacheDao();

    private Sys2PermissionsCacheDao sys2PermissionsCacheDao = new Sys2PermissionsCacheDao();

    @Override
    public List<Map<String, String>> find() {
        Cache cache = CacheFactory.newIntance();
        Object obj = cache.get("user.sys");
        if (obj == null) {
            cache.put("user.sys", super.find());
        }
        return (List<Map<String, String>>) cache.get("user.sys");
    }


    public Sys2PermissionsCacheDao getSysPermissionsRelationCacheDao(){
        return this.sys2PermissionsCacheDao;
    }

}
