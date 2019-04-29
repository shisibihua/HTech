package com.honghe.permissions.strategy;

import com.honghe.user.dao.UserDao;

import java.util.Map;

/**
 * Created by zhanghongbin on 2016/10/11.
 */
public final class PermissionsStrategyFactory {

    private PermissionsStrategyFactory() {

    }

    public static final AbstractPermissionsStrategy newInstance(String userId) {
        Map<String, Map<String, String>> user2roleMap = UserDao.INSTANCE.getUserRoleRelationDao().find();
        if (user2roleMap.containsKey(userId)) { //存在角色
            return new RoleStrategyAbstract(userId, user2roleMap);
        } else {
            //不存在角色，通过身份获取权限
            return new TypeStrategyAbstract(userId);
        }
    }

}
