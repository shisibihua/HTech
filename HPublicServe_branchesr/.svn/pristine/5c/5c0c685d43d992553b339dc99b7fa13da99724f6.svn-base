package com.honghe.permissions.strategy;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhanghongbin on 2016/10/11.
 */
public class RoleStrategyAbstract extends AbstractPermissionsStrategy {

    private Map<String, Map<String, String>> user2roleMap;

    public RoleStrategyAbstract(String userId, Map<String, Map<String, String>> user2roleMap) {
        this.user2roleMap = user2roleMap;
        this.userId = userId;
    }

    @Override
    public Set<String> loadPermission() {
        Set<String> permission = new HashSet<>();
        Map<String, String> roleMap = user2roleMap.get(userId);
        this.loadPermission(permission, roleMap.get("roleId").split(","));
        return permission;
    }
}
