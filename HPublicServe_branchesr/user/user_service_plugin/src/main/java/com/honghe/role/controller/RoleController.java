package com.honghe.role.controller;

import com.honghe.exception.ParamException;
import com.honghe.role.cache.RoleCacheDao;
import com.honghe.role.dao.Role2PermissionDao;
import com.honghe.user.dao.UserDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sunchao on 2016/12/1.
 */
public class RoleController {

    /**
     * 用户角色查询
     * @param userId 用户id
     * @param token
     * @return
     */
    public Object roleSearch(String userId,String token) {
        Object re_value;
        if (userId != null) {
            re_value = UserDao.INSTANCE.getUserRoleRelationDao().getRoleIdByUserId(userId);
        }else if(token != null){
            re_value = RoleCacheDao.INSTANCE.find(token);
        }
        else {
            re_value = RoleCacheDao.INSTANCE.findAllRole();
        }
        return re_value;
    }

    /**
     * 根据角色名称查找角色是否存在
     * @param roleName
     * @param roleId
     * @return
     * @throws ParamException
     */
    public boolean roleIsExist(String roleName,String roleId) throws ParamException {
        boolean re_value;
        if (roleName == null ) {
            throw new ParamException();
        }
        Map<String, Object> pram = new HashMap<>();

        pram.put("roleName", roleName);
        if(roleId!=null){
            pram.put("roleId", roleId);
        }
        re_value = RoleCacheDao.INSTANCE.isRoleExist(pram);
        return re_value;
    }

    /**
     * 根据角色id查找角色名称
     * @param roleId 角色id
     * @return
     * @throws ParamException
     */
    public String roleNameSearch(String roleId) throws ParamException {
        String re_value;
        if (roleId == null ) {
            throw new ParamException();
        }

        re_value = RoleCacheDao.INSTANCE.findRoleNameById(roleId);
        return re_value;
    }

    /**
     * 给用户分配角色
     * @param userId 用户id
     * @param roleId 角色id
     * @return
     * @throws ParamException
     */
    public boolean roleAllot(String userId, String roleId) throws  ParamException{
        boolean re_value;
        if (userId == null|| roleId == null) {
            throw new ParamException();
        }
        UserDao.INSTANCE.getUserRoleRelationDao().deleteByUserId(userId);
        List<Long> id = UserDao.INSTANCE.getUserRoleRelationDao().add(userId, roleId.split(","));
        re_value = true;
        return re_value;
    }

    /**
     * 角色权限查询
     * @return
     */
    public List<Map<String,String>> role2PermissionSearch(){
        List<Map<String,String>> re_value = RoleCacheDao.INSTANCE.getRolePermissionRelationDao().findList();
        return re_value;
    }

    /**
     * 查询所有角色身份关系
     * @return
     */
    public List<Map<String,String>> role2userTypeSearch(){
        List<Map<String,String>> re_value = RoleCacheDao.INSTANCE.getRoleUserTypeRelationDao().findList();
        return re_value;
    }

    /**
     * 添加用户角色
     * @param roleName
     * @return
     * @throws ParamException
     */
    public Object roleAdd(String roleName) throws ParamException{

        Object re_value;
        Map<String, Object> pram = new HashMap<>();
        pram.put("roleName", roleName);
        //判断信息是否完整
        if (roleName == null) {
            throw new ParamException();
        }
        if(RoleCacheDao.INSTANCE.isRoleExist(pram)){
            re_value ="-1";//角色已存在
        }else{
            re_value = RoleCacheDao.INSTANCE.addRole(pram);//返回角色id
        }
        return re_value;
    }

    /**
     * 添加角色身份对应关系
     * @param roleId
     * @param typeId
     * @return
     * @throws ParamException
     */
    public boolean role2userTypeAdd(String roleId, String typeId)throws ParamException{
        boolean re_value;
        Map<String, Object> pram = new HashMap<>();
        pram.put("roleId", roleId);
        pram.put("typeId", typeId);
        if (roleId == null||typeId==null) {
            throw new ParamException();
        }
        re_value = RoleCacheDao.INSTANCE.getRoleUserTypeRelationDao().add(pram);
        return re_value;

    }

    /**
     * 添加角色权限对应关系
     * @param roleId
     * @param permissionId
     * @return
     * @throws ParamException
     */
    public long role2permissionAdd(String roleId, String permissionId) throws ParamException {
        long re_value;
        Map<String, Object> pram = new HashMap<>();
        pram.put("roleId", roleId);
        pram.put("permissionId", permissionId);
        if (roleId == null||permissionId==null) {
            throw new ParamException();
        }
        re_value = RoleCacheDao.INSTANCE.getRolePermissionRelationDao().add(pram);
        return re_value;
    }

    /**
     * 删除角色
     * @param roleId
     * @return
     * @throws ParamException
     */
    public boolean roleDelete(String roleId)  throws ParamException{
        boolean re_value;
        if (roleId == null) {
            throw new ParamException();
        }
        re_value = RoleCacheDao.INSTANCE.delete(roleId);
        return re_value;
    }

    /**
     * 删除角色身份对应关系
     * @param roleId
     * @return
     * @throws ParamException
     */
    public boolean role2userTypeDelete(String roleId)  throws ParamException{
        boolean re_value;
        if (roleId == null) {
            throw new ParamException();
        }
        re_value = RoleCacheDao.INSTANCE.getRoleUserTypeRelationDao().delete(roleId);
        return re_value;
    }

    /**
     * 删除角色权限对应关系
     * @param roleId 角色id
     * @return
     * @throws ParamException
     */
    public boolean role2permissionDelete(String roleId,String token)  throws ParamException{
        boolean re_value;
        if (roleId == null) {
            throw new ParamException();
        }

        re_value = RoleCacheDao.INSTANCE.getRolePermissionRelationDao().delete(roleId,token);
        return re_value;
    }

    /**
     * 更新角色信息
     * @param roleId 角色id
     * @param roleName 角色名称
     * @return
     * @throws ParamException
     */
    public boolean roleUpdate(String roleId,String roleName) throws ParamException{
        boolean re_value;
        Map<String, Object> pram = new HashMap<>();
        pram.put("roleId", roleId);
        pram.put("roleName", roleName);
        if (roleId == null|| roleName ==null) {
            throw new ParamException();
        }
        if(RoleCacheDao.INSTANCE.isRoleExist(pram)){
            re_value = false;
        }else{
            re_value = RoleCacheDao.INSTANCE.updateRoleByRoleId(pram);
        }
        return re_value;


    }

    /**
     * 根据角色id获取角色权限列表
     *
     * @param userId 用户ID
     * @param sys    平台标识
     * * @return boolean
     * @author caoqian
     */
    public List<Map<String,String>> rolePermissionByUserId(String userId,String sys) throws ParamException{
        List<Map<String,String>> permissionList=new ArrayList<>();
        if(userId==null || "".equals(userId)){
            throw new ParamException();
        }else{
            permissionList=Role2PermissionDao.INSTANCE.userPermissionByUserId(userId, sys);
        }
        return permissionList;
    }

}
