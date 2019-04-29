package com.honghe.role;

import com.cpjit.swagger4j.annotation.API;
import com.cpjit.swagger4j.annotation.APIs;
import com.cpjit.swagger4j.annotation.DataType;
import com.cpjit.swagger4j.annotation.Param;
import com.honghe.BaseReflectCommand;
import com.honghe.exception.ParamException;
import com.honghe.role.cache.RoleCacheDao;
import com.honghe.role.dao.Role2PermissionDao;
import com.honghe.user.dao.UserDao;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sunchao
 * @date 2016/12/1
 */
@APIs(value = "user")
public class RoleCommand extends BaseReflectCommand {

    private Logger logger = Logger.getLogger("user");

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public Class getCommandClass() {
        return this.getClass();
//        return RoleController.class;
    }

    /**
     * 用户角色查询
     * @param userId 用户id
     * @param token
     * @return
     */
    @API(value = "roleSearch",
            method = "get",
            summary = "用户角色查询",
            description = "用户角色查询",
            parameters = {
                    @Param(in = "query", name = "userId", description = "用户id", dataType = DataType.STRING),
                    @Param(in = "query", name = "token", description = "用户id", dataType = DataType.STRING)
            })
    public Object roleSearch(String userId,String token) {
        Object reValue;
        if (userId != null) {
            reValue = UserDao.INSTANCE.getUserRoleRelationDao().getRoleIdByUserId(userId);
        }else if(token != null){
            reValue = RoleCacheDao.INSTANCE.find(token);
        }
        else {
            reValue = RoleCacheDao.INSTANCE.findAllRole();
        }
        return reValue;
    }

    /**
     * 根据角色名称查找角色是否存在
     * @param roleName
     * @param roleId
     * @return
     * @throws ParamException
     */
    @API(value = "roleIsExist",
            method = "根据角色名称查找角色是否存在",
            summary = "根据角色名称查找角色是否存在",
            description = "",
            parameters = {
                    @Param(in = "query", name = "roleName", description = "角色名称", dataType = DataType.STRING),
                    @Param(in = "query", name = "roleId", description = "角色id", dataType = DataType.STRING)
            })
    public boolean roleIsExist(String roleName,String roleId) throws ParamException {
        boolean reValue;
        if (roleName == null ) {
            throw new ParamException();
        }
        Map<String, Object> pram = new HashMap<>();

        pram.put("roleName", roleName);
        if(roleId!=null){
            pram.put("roleId", roleId);
        }
        reValue = RoleCacheDao.INSTANCE.isRoleExist(pram);
        return reValue;
    }

    /**
     * 根据角色id查找角色名称
     * @param roleId 角色id
     * @return
     * @throws ParamException
     */
    @API(value = "roleNameSearch",
            method = "get",
            summary = "根据角色id查找角色名称",
            description = "根据角色id查找角色名称",
            parameters = {
                    @Param(in = "query", name = "roleId", description = "角色id", dataType = DataType.STRING)
            })
    public String roleNameSearch(String roleId) throws ParamException {
        String reValue;
        if (roleId == null ) {
            throw new ParamException();
        }

        reValue = RoleCacheDao.INSTANCE.findRoleNameById(roleId);
        return reValue;
    }

    /**
     * 给用户分配角色
     * @param userId 用户id
     * @param roleId 角色id
     * @return
     * @throws ParamException
     */
    @API(value = "roleAllot",
            method = "get",
            summary = "给用户分配角色",
            description = "给用户分配角色",
            parameters = {
                    @Param(in = "query", name = "userId", description = "用户id", dataType = DataType.STRING),
                    @Param(in = "query", name = "roleId", description = "角色id", dataType = DataType.STRING)
            })
    public boolean roleAllot(String userId, String roleId) throws  ParamException{
        boolean reValue;
        if (userId == null|| roleId == null) {
            throw new ParamException();
        }
        UserDao.INSTANCE.getUserRoleRelationDao().deleteByUserId(userId);
        List<Long> id = UserDao.INSTANCE.getUserRoleRelationDao().add(userId, roleId.split(","));
        reValue = true;
        return reValue;
    }

    /**
     * 角色权限查询
     * @return
     */
    @API(value = "role2PermissionSearch",
            method = "get",
            summary = "角色权限查询",
            description = "角色权限查询"
            )
    public List<Map<String,String>> role2PermissionSearch(){
        return RoleCacheDao.INSTANCE.getRolePermissionRelationDao().findList();
    }

    /**
     * 查询所有角色身份关系
     * @return
     */
    @API(value = "role2userTypeSearch",
            method = "get",
            summary = "查询所有角色身份关系",
            description = "查询所有角色身份关系"
    )
    public List<Map<String,String>> role2userTypeSearch(){
        return RoleCacheDao.INSTANCE.getRoleUserTypeRelationDao().findList();
    }

    /**
     * 添加用户角色
     * @param roleName
     * @return
     * @throws ParamException
     */
    @API(value = "roleAdd",
            method = "get",
            summary = "添加用户角色",
            description = "添加用户角色",
            parameters = {
                    @Param(in = "query", name = "roleName", description = "角色名称", dataType = DataType.STRING)
            })
    public Object roleAdd(String roleName) throws ParamException{

        Object reValue;
        Map<String, Object> pram = new HashMap<>();
        pram.put("roleName", roleName);
        //判断信息是否完整
        if (roleName == null) {
            throw new ParamException();
        }
        if(RoleCacheDao.INSTANCE.isRoleExist(pram)){
            //角色已存在
            reValue ="-1";
        }else{
            //返回角色id
            reValue = RoleCacheDao.INSTANCE.addRole(pram);
        }
        return reValue;
    }

    /**
     * 添加角色身份对应关系
     * @param roleId
     * @param typeId
     * @return
     * @throws ParamException
     */
    @API(value = "role2userTypeAdd",
            method = "get",
            summary = "添加角色身份对应关系",
            description = "添加角色身份对应关系",
            parameters = {
                    @Param(in = "query", name = "roleId", description = "角色id", dataType = DataType.STRING),
                    @Param(in = "query", name = "typeId", description = "类型id", dataType = DataType.STRING)
            })
    public boolean role2userTypeAdd(String roleId, String typeId)throws ParamException{
        boolean reValue;
        Map<String, Object> pram = new HashMap<>();
        pram.put("roleId", roleId);
        pram.put("typeId", typeId);
        if (roleId == null||typeId==null) {
            throw new ParamException();
        }
        reValue = RoleCacheDao.INSTANCE.getRoleUserTypeRelationDao().add(pram);
        return reValue;

    }

    /**
     * 添加角色权限对应关系
     * @param roleId
     * @param permissionId
     * @return
     * @throws ParamException
     */
    @API(value = "role2permissionAdd",
            method = "get",
            summary = "添加角色权限对应关系",
            description = "添加角色权限对应关系",
            parameters = {
                    @Param(in = "query", name = "roleId", description = "角色id", dataType = DataType.STRING),
                    @Param(in = "query", name = "permissionId", description = "权限id", dataType = DataType.STRING)
            })
    public long role2permissionAdd(String roleId, String permissionId) throws ParamException {
        long reValue;
        Map<String, Object> pram = new HashMap<>();
        pram.put("roleId", roleId);
        pram.put("permissionId", permissionId);
        if (roleId == null||permissionId==null) {
            throw new ParamException();
        }
        reValue = RoleCacheDao.INSTANCE.getRolePermissionRelationDao().add(pram);
        return reValue;
    }

    /**
     * 删除角色
     * @param roleId
     * @return
     * @throws ParamException
     */
    @API(value = "roleDelete",
            method = "get",
            summary = "删除角色",
            description = "删除角色",
            parameters = {
                    @Param(in = "query", name = "roleId", description = "角色id", dataType = DataType.STRING)
            })
    public boolean roleDelete(String roleId)  throws ParamException{
        boolean reValue;
        if (roleId == null) {
            throw new ParamException();
        }
        reValue = RoleCacheDao.INSTANCE.delete(roleId);
        return reValue;
    }

    /**
     * 删除角色身份对应关系
     * @param roleId
     * @return
     * @throws ParamException
     */
    @API(value = "role2userTypeDelete",
            method = "get",
            summary = "删除角色身份对应关系",
            description = "删除角色身份对应关系",
            parameters = {
                    @Param(in = "query", name = "roleId", description = "角色id", dataType = DataType.STRING)
            })
    public boolean role2userTypeDelete(String roleId)  throws ParamException{
        boolean reValue;
        if (roleId == null) {
            throw new ParamException();
        }
        reValue = RoleCacheDao.INSTANCE.getRoleUserTypeRelationDao().delete(roleId);
        return reValue;
    }

    /**
     * 删除角色权限对应关系
     * @param roleId 角色id
     * @return
     * @throws ParamException
     */
    @API(value = "role2permissionDelete",
            method = "get",
            summary = "删除角色权限对应关系",
            description = "删除角色权限对应关系",
            parameters = {
                    @Param(in = "query", name = "roleId", description = "角色id", dataType = DataType.STRING),
                    @Param(in = "query", name = "token", description = "权限模块标识（名称）", dataType = DataType.STRING)
            })
    public boolean role2permissionDelete(String roleId,String token)  throws ParamException{
        boolean reValue;
        if (roleId == null) {
            throw new ParamException();
        }

        reValue = RoleCacheDao.INSTANCE.getRolePermissionRelationDao().delete(roleId,token);
        return reValue;
    }

    /**
     * 更新角色信息
     * @param roleId 角色id
     * @param roleName 角色名称
     * @return
     * @throws ParamException
     */
    @API(value = "roleUpdate",
            method = "get",
            summary = "更新角色信息",
            description = "更新角色信息",
            parameters = {
                    @Param(in = "query", name = "roleId", description = "角色id", dataType = DataType.STRING)
            })
    public boolean roleUpdate(String roleId,String roleName) throws ParamException{
        boolean reValue;
        Map<String, Object> pram = new HashMap<>();
        pram.put("roleId", roleId);
        pram.put("roleName", roleName);
        if (roleId == null|| roleName ==null) {
            throw new ParamException();
        }
        if(RoleCacheDao.INSTANCE.isRoleExist(pram)){
            reValue = false;
        }else{
            reValue = RoleCacheDao.INSTANCE.updateRoleByRoleId(pram);
        }
        return reValue;


    }

    /**
     * 根据角色id获取角色权限列表
     *
     * @param userId 用户ID
     * @param sys    平台标识
     * * @return boolean
     * @author caoqian
     */
    @API(value = "rolePermissionByUserId",
            method = "get",
            summary = "根据角色id获取角色权限列表",
            description = "根据角色id获取角色权限列表",
            parameters = {
                    @Param(in = "query", name = "userId", description = "", dataType = DataType.STRING),
                    @Param(in = "query", name = "sys", description = "", dataType = DataType.STRING)
            })
    public List<Map<String,String>> rolePermissionByUserId(String userId,String sys) throws ParamException{
        List<Map<String,String>> permissionList;
        if(userId==null || "".equals(userId)){
            throw new ParamException();
        }else{
            permissionList= Role2PermissionDao.INSTANCE.userPermissionByUserId(userId, sys);
        }
        return permissionList;
    }

}
