package com.honghe.role.cache;

import com.honghe.cache.Cache;
import com.honghe.cache.CacheFactory;
import com.honghe.user.util.JdbcTemplateUtil;
import com.honghe.role.dao.Role2PermissionDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanghongbin on 2016/10/10.
 */
public final class Role2PermissionCacheDao extends Role2PermissionDao {

    @Override
    public Map<String, Map<String, String>> find() {
        Cache cache = CacheFactory.newIntance();
        Object obj = cache.get("user.role2permission");
        if (obj == null) {
            cache.put("user.role2permission", super.find());
        }
        return (Map<String, Map<String, String>>) cache.get("user.role2permission");
    }

    @Override
    public boolean delete(String roleId, Object token) {
        boolean flag = super.delete(roleId, token);
        if (flag) {
            CacheFactory.newIntance().remove("user.role2permission");
        }
        return flag;
    }

    @Override
    public long add(Map<String, Object> data) {
        long n = super.add(data);
        if (n > 0) {
            CacheFactory.newIntance().remove("user.role2permission");
        }
        return n;
    }

    @Override
    public List<Map<String, String>> findList() {
        Map<String, Map<String, String>> result = find();
        List<Map<String, String>> role2permission = new ArrayList<>();
        for (Map.Entry<String, Map<String, String>> entry : result.entrySet()) {
            String roleId = entry.getKey();
            String[] values = entry.getValue().get("permissionId").split(",");
            Map<String, String> record;
            for (String value : values) {
                record = new HashMap<>();
                record.put("roleId", roleId);
                record.put("permissionId", value);
                role2permission.add(record);
            }
        }
        return role2permission;
    }
}
