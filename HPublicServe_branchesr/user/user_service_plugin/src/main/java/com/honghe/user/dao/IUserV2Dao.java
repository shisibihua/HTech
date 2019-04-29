package com.honghe.user.dao;/**
 * Created by lyx on 2017-01-07 0007.
 */

import java.util.Map;

/**
 * 用户的操作类
 *
 * @author zhaowj
 * @create 2017-03-13 09:15
 **/
public interface IUserV2Dao {

    /**
     * 通过机构id查找用户信息（资源平台目前使用campus表）
     * @param campusId
     * @return
     */
    public Map<String, String> userAdminSearchByAgency(String campusId);

    public  Map<String, String> find(String name, String password);

    public  Map<String, String> find(int userId);
}
