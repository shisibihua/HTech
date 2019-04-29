package com.honghe.user.cache;

import com.honghe.cache.Cache;
import com.honghe.cache.CacheFactory;
import com.honghe.user.util.JdbcTemplateUtil;
import com.honghe.user.dao.User2RoleDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanghongbin on 2016/10/10.
 */
public final class User2RoleCacheDao extends User2RoleDao {


    @Override
    public Map<String, Map<String, String>> find() {
        Cache cache = CacheFactory.newIntance();
        Object obj = cache.get("user.user2role");
        if (obj == null) {
            cache.put("user.user2role", super.find());
        }
        return (Map<String, Map<String, String>>) cache.get("user.user2role");
    }


    @Override
    public boolean deleteByUserId(String userId) {
        boolean flag = super.deleteByUserId(userId);
        if (flag) {
            CacheFactory.newIntance().remove("user.user2role");
        }
        return flag;
    }

    @Override
    public boolean deleteByRoleId(String roleId) {
        boolean flag = super.deleteByRoleId(roleId);
        if (flag) {
            CacheFactory.newIntance().remove("user.user2role");
        }
        return flag;
    }

    @Override
    public List<Long> add(String userId, String[] roleId) {
        List<Long> ids = super.add(userId, roleId);
        CacheFactory.newIntance().remove("user.user2role");
        return ids;
    }

    @Override
    public Map<String, String> getRoleIdByUserId(String userId) {
        Map<String, String> roleInfoMap = new HashMap<>();
        Map<String, Map<String, String>> result = this.find();
        if (result.isEmpty()) {
            return roleInfoMap;
        }
        if (result.containsKey(userId)) {
            Map<String, String> roleINfo = result.get(userId);
            String[] roleId = roleINfo.get("roleId").split(",");
            String[] roleName = roleINfo.get("roleName").split(",");
            for (int i = 0; i < roleId.length; i++) {
                roleInfoMap.put(roleId[i], roleName[i]);
            }
        }
        return roleInfoMap;
    }
}
