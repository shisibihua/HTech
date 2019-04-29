package com.honghe.permissions;

import com.cpjit.swagger4j.annotation.API;
import com.cpjit.swagger4j.annotation.APIs;
import com.cpjit.swagger4j.annotation.DataType;
import com.cpjit.swagger4j.annotation.Param;
import com.honghe.BaseReflectCommand;
import com.honghe.exception.ParamException;
import com.honghe.permissions.bean.Permissions;
import com.honghe.permissions.strategy.AbstractPermissionsStrategy;
import com.honghe.permissions.strategy.PermissionsStrategyFactory;
import com.honghe.sys.cache.Sys2PermissionsCacheDao;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * @author sunchao
 * @date 2016/12/2
 */
@APIs(value = "user")
public class PermissionsCommand extends BaseReflectCommand {

    private Logger logger = Logger.getLogger("user");

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public Class getCommandClass() {
        return this.getClass();
    }

    /**
     * 用户权限验证接口
     * <p>
     * 1.如果是管理员 ，直接返回true
     * 2.查询出用户所有权限，验证是否有传入的权限
     *
     * @param userId 用户ID
     * @param name   权限名称
     * @param path   权限path
     * @param res    权限
     * @return
     * @throws ParamException
     */
    @API(value = "authCheck",
            method = "get",
            summary = "用户权限验证接口",
            description = "用户权限验证接口",
            parameters = {
                    @Param(in = "query", name = "userId", description = "用户ID", dataType = DataType.STRING),
                    @Param(in = "query", name = "name", description = "权限名称", dataType = DataType.STRING),
                    @Param(in = "query", name = "path", description = "权限path", dataType = DataType.STRING),
                    @Param(in = "query", name = "res", description = "权限", dataType = DataType.STRING)
            })
    public Object authCheck(String userId, String name, String path, String res) throws ParamException {

        Object re_value;
        if (res != null) {
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
        //是管理员
        if (obj != null) {
            re_value = obj;
            return re_value;
        }
        Set<String> permission = abstractPermissionsStrategy.loadPermission();
        //单个权限验证
        if (resArray.length == 1) {
            re_value = permission.contains(res);
        } else {
            //多个权限验证
            Map<String, Boolean> result = new HashMap<>();
            for (String item : resArray) {
                result.put(item, permission.contains(item));
            }
            re_value = result;
        }

        return re_value;


    }

    /**
     * 通过用户id返回权限信息
     *
     * @param userId
     * @return
     * @throws ParamException
     */
    @API(value = "authPermissionsSearch",
            method = "get",
            summary = "通过用户id返回权限信息",
            description = "通过用户id返回权限信息",
            parameters = {
                    @Param(in = "query", name = "userId", description = "用户ID", dataType = DataType.STRING)
            })
    public Object authPermissionsSearch(String userId) throws ParamException {
        Object re_value;
        if (userId == null) {
            throw new ParamException();
        } else {
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
     * @param token  系统名称
     * @return
     * @throws ParamException
     */
    @API(value = "authUserSys",
            method = "get",
            summary = "查询用户在某系统下是否有权限（有任一权限返回true）",
            description = "查询用户在某系统下是否有权限（有任一权限返回true）",
            parameters = {
                    @Param(in = "query", name = "userId", description = "", dataType = DataType.STRING),
                    @Param(in = "query", name = "token", description = "", dataType = DataType.STRING)
            })
    public Object authUserSys(String userId, String token) throws ParamException {
        Object re_value = false;
        if (userId == null || token == null) {
            throw new ParamException();
        } else {

            AbstractPermissionsStrategy abstractPermissionsStrategy = PermissionsStrategyFactory.newInstance(userId);
            //是管理员
            if ("1".equals(userId)) {
                re_value = true;
                return re_value;
            }
            // 用户权限
            Set<String> permission = abstractPermissionsStrategy.loadPermission();
            // 系统权限
            Permissions p = new Sys2PermissionsCacheDao().find(token);
            List<String> list = new ArrayList<>();
            list = getPermissions(list, p);
            // 验证是否有同样的权限
            for (String s : list) {
                if (!"".equals(s) && permission.contains(s)) {
                    re_value = true;
                    break;
                }
            }
            return re_value;
        }
    }

    /**
     * // 从Permissions对象中递归得到所有权限名称
     * // 放入list中
     *
     * @param list
     * @param p
     * @return
     */
    private List<String> getPermissions(List<String> list, Permissions p) {

        list.add(p.getName());
        List<Permissions> plist = p.getPermissions();
        if (plist != null && plist.size() > 0) {
            for (Permissions ps : plist) {
                getPermissions(list, ps);
            }
        }
        return list;
    }

}
