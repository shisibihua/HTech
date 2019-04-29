package com.honghe.tech.service;


import com.honghe.tech.form.UserForm;

import java.util.Map;
/**
 * 用户验证接口类
 * @author xinqinggang
 * @date 2018/1/25
 */
public interface UserCheckService {
    /**
     * 用户登录验证，返回用户角色及所在组织机构信息
     * @param loginName   登录名
     * @param userPwd     密码
     * @return map  用户信息
     * @author caoqian
     */
    public UserForm userInfo(String loginName, String userPwd);

    /**
     * 获取用户组织机构信息
     * @param userId
     * @return
     */
    public UserForm getCampusInfo(String userId);
}
