package com.honghe.permissions.strategy;

import com.honghe.role.cache.RoleCacheDao;
import com.honghe.user.cache.UserCacheDao;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhanghongbin on 2016/10/11.
 */
public class TypeStrategyAbstract extends AbstractPermissionsStrategy {


    public TypeStrategyAbstract(String userId) {
        this.userId = userId;
    }

    @Override
    public Set<String> loadPermission() {
        Set<String> permission = new HashSet<>();
        //不存在角色的用户，通过身份获取权限信息
        Map<String, Map<String, String>> role2typeMap = RoleCacheDao.INSTANCE.getRoleUserTypeRelationDao().find();
        if (role2typeMap.isEmpty()) {
            return permission;
        }
        int uId;
        try {
            uId = Integer.parseInt(userId);
        } catch (Exception e) {
            return permission;
        }
        Map<String, String> userInfo = UserCacheDao.INSTANCE.find(uId);
        String userType = userInfo.get("userType");
        if(userInfo.size() == 0){
            return permission;
        }
        //存在身份用户
        if (userType.length() != 0 && role2typeMap.containsKey(userType)) {
            Map<String, String> roleMap = role2typeMap.get(userType);
            this.loadPermission(permission, roleMap.get("roleId").split(","));
        }
        return permission;
    }
}
