package com.honghe.role.cache;

import com.honghe.cache.CacheFactory;
import com.honghe.user.util.JdbcTemplateUtil;
import com.honghe.role.dao.RoleDao;
import com.honghe.sys.cache.SysCacheDao;

import java.util.*;

/**
 * Created by zhanghongbin on 2016/10/11.
 */
public final class RoleCacheDao extends RoleDao {


    public final static RoleCacheDao INSTANCE = new RoleCacheDao();

    protected RoleCacheDao() {
        super();
    }

    @Override
    public boolean delete(String roleId) {
        boolean flag = super.delete(roleId);
        if (flag) {
            CacheFactory.newIntance().remove("user.role2type");
            CacheFactory.newIntance().remove("user.role2permission");
            CacheFactory.newIntance().remove("user.user2role");
        }
        return flag;
    }


    @Override
    @Deprecated
    public Map<String, String> find(String token) {

        List<String> roleIdList = new ArrayList<>();
        Map<String, Map<String, String>> sys2permission = SysCacheDao.INSTANCE.getSysPermissionsRelationCacheDao().findBySysName(token);
        sys2permission.putAll(SysCacheDao.INSTANCE.getSysPermissionsRelationCacheDao().findBySysName("communication"));
        if (sys2permission.isEmpty()) {
            return new HashMap<>();
        }
        Map<String, Map<String, String>> role2permission = RoleDao.INSTANCE.getRolePermissionRelationDao().find();
        if (role2permission.isEmpty()) {
            return new HashMap<>();
        }
        Set<String> permission = sys2permission.keySet();
        for (Map.Entry<String, Map<String, String>> entry : role2permission.entrySet()) {
            String roleId = entry.getKey();
            Map<String, String> value = entry.getValue();
            String[] permissionIdArray = value.get("permissionId").split(",");
            for (String permissionId : permissionIdArray) {
                if (permission.contains(permissionId)) {
                    roleIdList.add(roleId);
                    break;
                }
            }
        }
        if (roleIdList.isEmpty()) {
            return new HashMap<>();
        }
        return this.find(roleIdList.toArray(new String[roleIdList.size()]));
    }
}
