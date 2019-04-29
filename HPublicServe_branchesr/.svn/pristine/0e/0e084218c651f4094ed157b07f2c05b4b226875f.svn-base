package com.honghe.permissions.strategy;

import com.honghe.role.dao.RoleDao;

import java.util.*;

/**
 * Created by zhanghongbin on 2016/10/11.
 */
public abstract class AbstractPermissionsStrategy {


    protected String userId;
    /**
     * 管理员特殊处理
     *
     * @param resArray
     * @return
     */
    public final Object isAdmin(String[] resArray) {
        if (resArray == null) {
            if ("1".equals(userId)) {
                return this.loadAllPermission();
            }
        } else {
            if ("1".equals(userId)) {
                if (resArray.length == 1) {
                    //单个权限验证
                    return true;
                } else {
                    //多个权限验证
                    Map<String, Boolean> result = new HashMap<>();
                    for (String resItem : resArray) {
                        result.put(resItem, true);
                    }
                    return result;
                }
            }
        }
        return null;
    }

    public abstract Set<String> loadPermission();

    protected void loadPermission(Set<String> permission, String[] roleIds) {
        Map<String, Map<String, String>> role2permission = RoleDao.INSTANCE.getRolePermissionRelationDao().find();
        for (String roleId : roleIds) {
            Object obj = role2permission.get(roleId);
            if (obj != null) {
                Map<String, String> role2permissionMap = (Map<String, String>) obj;
                String[] nameArray = role2permissionMap.get("permissionName").split(",");
                String[] pathArray = role2permissionMap.get("permissionPath").split(",");
                permission.addAll(Arrays.asList(nameArray));
                permission.addAll(Arrays.asList(pathArray));
            }
        }
    }

    private final Set<String> loadAllPermission() {
        Set<String> permission = new HashSet<>();
        Map<String, Map<String, String>> role2permission = RoleDao.INSTANCE.getRolePermissionRelationDao().find();
        for (Map.Entry<String, Map<String, String>> entry : role2permission.entrySet()) {
            Map<String, String> role2permissionMap = entry.getValue();
            String[] nameArray = role2permissionMap.get("permissionName").split(",");
            String[] pathArray = role2permissionMap.get("permissionPath").split(",");
            permission.addAll(Arrays.asList(nameArray));
            permission.addAll(Arrays.asList(pathArray));
        }
        return permission;
    }
}
