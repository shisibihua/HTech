package com.honghe.user.cache;

import com.honghe.cache.CacheFactory;
import com.honghe.user.dao.UserDao;

/**
 * Created by zhanghongbin on 2016/10/11.
 */
public final class UserCacheDao extends UserDao {

    public final static UserCacheDao INSTANCE = new UserCacheDao();

    protected UserCacheDao() {
        super();
    }


    @Override
    public boolean updateUserStatus(String userId, String userStatus) {
        boolean flag = super.updateUserStatus(userId, userStatus);
        if (flag) {
              if("2".equals(userStatus)){
                  CacheFactory.newIntance().remove("user.user2role");
              }
        }
        return flag;
    }
}
