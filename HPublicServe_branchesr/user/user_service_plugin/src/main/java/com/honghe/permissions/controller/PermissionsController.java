package com.honghe.permissions.controller;

import com.honghe.exception.ParamException;
import com.honghe.permissions.bean.Permissions;
import com.honghe.permissions.strategy.AbstractPermissionsStrategy;
import com.honghe.permissions.strategy.PermissionsStrategyFactory;
import com.honghe.sys.cache.Sys2PermissionsCacheDao;

import java.util.*;

/**
 * Created by sunchao on 2016/12/2.
 */
public class PermissionsController {

    /**
     * 用户权限验证接口
     *
     * 1.如果是管理员 ，直接返回true
     * 2.查询出用户所有权限，验证是否有传入的权限
     *
     * @param userId 用户ID
     * @param name 权限名称
     * @param path 权限path
     * @param res 权限
     * @return
     * @throws ParamException
     */
    public Object authCheck(String userId, String name, String path, String res) throws ParamException {

        Object re_value;
        if (res!=null) {
            String[] resArray = res.split(":");
            name = resArray[0];
        }
        if (name != null) {
            res = name.toString();
        }
        if (path != null) {
            res = path.toString();
        }
        if (userId == null || res.length() == 0) {
            throw new ParamException();
        }
        String[] resArray = res.split(",");
        AbstractPermissionsStrategy abstractPermissionsStrategy = PermissionsStrategyFactory.newInstance(userId.toString());
        Object obj = abstractPermissionsStrategy.isAdmin(resArray);
        if (obj != null) { //是管理员
            re_value = obj;
            return re_value;
        }
        Set<String> permission = abstractPermissionsStrategy.loadPermission();
        if (resArray.length == 1) { //单个权限验证
            re_value = permission.contains(res);
        } else {
            //多个权限验证
            Map<String, Boolean> result = new HashMap<>();
            for (String _res : resArray) {
                result.put(_res, permission.contains(_res));
            }
            re_value =  result;
        }

        return re_value;


    }

    /**
     * 通过用户id返回权限信息
     * @param userId
     * @return
     * @throws ParamException
     */
    public Object authPermissionsSearch(String userId) throws ParamException {
        Object re_value;
        if (userId == null) {
            throw new ParamException();
        }
        else{
        AbstractPermissionsStrategy abstractPermissionsStrategy = PermissionsStrategyFactory.newInstance(userId.toString());
        Object obj = abstractPermissionsStrategy.isAdmin(null);
        if (obj != null) {
            re_value = obj;
        } else {
            re_value = abstractPermissionsStrategy.loadPermission();
        }
        return re_value;
        }
    }

    /**
     * 查询用户在某系统下是否有权限（有任一权限返回true）
     *
     * @param userId 用户ID
     * @param token 系统名称
     * @return
     * @throws ParamException
     */
    public Object authUserSys(String userId, String token) throws ParamException{
        Object re_value = false;
        if (userId == null || token == null) {
            throw new ParamException();
        } else {

            AbstractPermissionsStrategy abstractPermissionsStrategy = PermissionsStrategyFactory.newInstance(userId);
            if ("1".equals(userId)) { //是管理员
                re_value = true;
                return re_value;
            }
            // 用户权限
            Set<String> permission = abstractPermissionsStrategy.loadPermission();
            // 系统权限
            Permissions p = new Sys2PermissionsCacheDao().find(token);
            List<String> list = new ArrayList<>();
            list = getPermissions(list,p);
            // 验证是否有同样的权限
            for(String s : list){
                if(!"".equals(s)&&permission.contains(s)){
                    re_value = true;
                    break;
                }
            }
            return re_value;
        }
    }
    // 从Permissions对象中递归得到所有权限名称
    // 放入list中
    private List<String> getPermissions(List<String> list,Permissions p){

        list.add(p.getName());
        List<Permissions> plist = p.getPermissions();
        if(plist != null && plist.size() > 0){
            for(Permissions ps : plist){
                getPermissions(list,ps);
            }
        }
        return list;
    }

}
