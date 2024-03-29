package com.honghe.web.user.service.role;

import net.sf.json.JSONObject;
import com.honghe.service.client.Result;
import com.honghe.web.user.util.HttpServiceUtil;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lyx on 2016-09-26.
 */
@Service
    public class RoleService {
    private Logger logger = Logger.getLogger(RoleService.class);

    /**
     * 获取角色列表
     *
     * @return
     */
    public List getRoles() {
        Map<String, String> params = new HashMap<>();
        Result result = null;
        try {
            result = HttpServiceUtil.userService("roleSearch",  params);
        } catch (Exception e) {
            logger.error("获取角色列表失败：", e);
        }
        return (List)result.getValue();
    }
    /**
     * 获取角色列表,并根据用户已分配的角色做标记
     *
     * @return
     */
    public List getUserRoles(String userId){
        Map<String, String> params = new HashMap<>();

        Result result = null;
        Result usedResult = null;
        try {
            result = HttpServiceUtil.userService("roleSearch", params);
            params.put("userId",userId);
            usedResult = HttpServiceUtil.userService("roleSearch",params);
        } catch (Exception e) {
            logger.error("获取角色列表失败：", e);
        }
        List<Map<String,String>> list = (List<Map<String, String>>) result.getValue();
        JSONObject used = (JSONObject)usedResult.getValue();
        for(int i = 0 ;i < list.size() ; i ++){
            Map<String, String> map = list.get(i);
            if(used.get(map.get("roleId"))!=null){
                map.put("isSelect","m-checked");
            }else{
                map.put("isSelect","");
            }
        }
        return list;
    }
    /**
     * 查询现有系统
     * @param
     * @return 系统列表
     */
    public List<Map<String, Object>> getAllSystems() {
        List<Map<String, Object>> re_value = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        try {
            Result result = HttpServiceUtil.userService("sysSearch", params);
            if (result.getValue() != null) {
                re_value = (List<Map<String, Object>>) result.getValue();
            }
        } catch (Exception e) {
            logger.error("查询现有系统异常：", e);
        }
        return re_value;
    }

    /**
     * 改变角色 获取对应角色的系统和权限
     * @param token 当前系统
     * @param role_id    选中的角色的ID
     * @return
     */
    public Map changeRole(String role_id,String token) {
        // 第一次登入页面，默认使用第一个系统添加数据
        if(token == null){
            token = String.valueOf(getAllSystems().get(0).get("name"));
        }
        List UsedPermission = rolePermissionSearch(token);
        JSONObject AllPermission = permissionsSearch(token);
        JSONArray perArray = (JSONArray) AllPermission.get("permissions");
        perArray = isPermissionUsed(perArray,UsedPermission,role_id);
        AllPermission.put("permissions",perArray);
        Map roleInfoMap = new HashMap();

        List<Map<String, Object>> list = getAllSystems();
        // 根据角色改变之前的系统，调整显示的系统名称顺序
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).get("name").equals(token)) {
                Map temp = new HashMap();
                temp = list.get(i);
                list.set(i, list.get(0));
                list.set(0, temp);
            }
        }
        roleInfoMap.put("AllPermission", AllPermission);
        roleInfoMap.put("resultSysList", list);
        return roleInfoMap;
    }

    /**
     * 切换当前系统
     * @param role_id 角色ID
     * @param token 选中的系统
     * @return List
     */
    public JSONObject changeToken(String role_id,  String token){
        List UsedPermission = rolePermissionSearch(token);
        JSONObject AllPermission = permissionsSearch(token);
        JSONArray perArray = (JSONArray) AllPermission.get("permissions");
        perArray = isPermissionUsed(perArray,UsedPermission,role_id);
        AllPermission.put("permissions", perArray);
        return AllPermission;
    }


    /**
     * 获取所有身份
     * @return List
     */
    public List getUserType(){
        Map<String, String> params = new HashMap<>();
        List userTypeList = new ArrayList();
        try {
            Result result = HttpServiceUtil.userService("userTypeSearch", params);
            if (result.getValue()!=null){
                userTypeList = (List) result.getValue();
            }
        } catch (Exception e) {
            logger.error("获取身份信息失败：",e);
        }
        return userTypeList;
    }


    /**
     * * 修改角色内容
     *   先删除角色及其相关内容，再添加角色
     * @param role_name 角色名称
     * @param typeIds  修改后的身份ID
     * @param oldRoleId  修改前的角色ID
     */
    public boolean editRole(String role_name,String typeIds,String oldRoleId){
        Map<String, String> params = new HashMap<>();
        params.put("roleName",role_name);
        params.put("roleId",oldRoleId);
        try {
            Result result = HttpServiceUtil.userService("roleIsExist", params);
            if((Boolean)result.getValue()){
                return false;
            }
        } catch (Exception e) {
            logger.error("获取身份信息失败：",e);
        }

        params = new HashMap();
        //删除角色表内容
        roleDelete(params,oldRoleId);
        //删除角色身份关系表内容
        role2userTypeDelete(params,oldRoleId);
        //删除角色用户关系表内容
        role2userDelete(params,oldRoleId);
        //删除角色权限关系表内容
        role2permissionDelete(params,oldRoleId,"");
        long roleId = roleAdd(role_name);
        if (typeIds!=null || !typeIds.equals("")){
            String [] typeId = typeIds.split(",");
            for (int j =0;j<typeId.length;j++){
                role2userTypeAdd(typeId[j], String.valueOf(roleId));
            }
        }
        return true;
    }

    /**
     *  删除角色
     * @param role_id 角色ID
     */

    public void deleteRole(String role_id){
        Map<String, String> params = new HashMap();
        //删除角色表内容
        roleDelete(params,role_id);
        //删除角色身份关系表内容
        role2userTypeDelete(params,role_id);
        //删除角色用户关系表内容
        role2userDelete(params,role_id);
        //删除角色权限关系表内容
        role2permissionDelete(params,role_id,"");
    }
    /**
     *  修改权限
     *  先删除角色对应权限，再添加本次分配的权限
     * @param role_id 角色ID
     * @param authorityIds 所选权限
     */
    public void updatePermisssion(String role_id,String authorityIds,String token){
        Map<String, String> params = new HashMap();
        // 删除之前权限
        role2permissionDelete(params,role_id,token);
        if("".equals(authorityIds)){
            return;
        }
        String [] authority = authorityIds.split(",");
        for (int i = 0;i<authority.length;i++){
            //添加权限信息
            try {
                params.put("roleId",role_id);
                params.put("permissionId",authority[i]);
                HttpServiceUtil.userService("role2permissionAdd",params);
            } catch (Exception e) {
                logger.error("获取权限失败",e);
            }
        }
    }

    /**
     * 添加角色
     * @param role_name 角色名称
     * @return long
     */
    public long roleAdd(String role_name){
        Map<String, String> params = new HashMap();
        params.put("roleName",role_name);
        long re_value = 0;
        try {
            // 判断角色名是否存在
            Result result = HttpServiceUtil.userService("roleIsExist",params);
            if((Boolean)result.getValue()){
                re_value = -1;
            }else{
                result = HttpServiceUtil.userService("roleAdd",params);
                if (result.getValue()!=null){
                    String   value =  result.getValue().toString();
                    re_value = Long.valueOf(value);
                }
            }

        } catch (Exception e) {
            logger.error("添加角色失败：",e);
        }
        return re_value;
    }

    /**
     * 获取已选身份
     * @return List
     */
    public List getusedUsertype(){
        Map<String, String> params = new HashMap<>();
        List usedUsertypelist = new ArrayList();
        try {
            Result result = HttpServiceUtil.userService("role2userTypeSearch",params);
            if (result.getValue()!=null){
                usedUsertypelist= (List) result.getValue();
            }
        } catch (Exception e) {
            logger.error("获取身份信息失败",e);
        }
        return usedUsertypelist;
    }
//-------------------------只在service里需要调用的方法---------------------------------------
    /**
     * //根据角色已分配权限，在全部权限中做标记
     * @param array 权限数组
     * @param usedPermission 已分配权限列表
     * @param role_id 角色ID
     * @return JSONArray 所有权限数组
     */
    public JSONArray isPermissionUsed(JSONArray array,List usedPermission,String role_id){
        for(int i = 0 ; i < array.size() ; i ++){
            JSONObject jsonObject = (JSONObject) array.get(i);
            for (int j = 0; j < usedPermission.size(); j++) {
                Map useMap = (Map) usedPermission.get(j);
                if (useMap.get("roleId").equals(role_id) && jsonObject.get("id").equals(useMap.get("permissionId"))) {
                    jsonObject.put("isSelect", "m-checked");
                    break;
                } else {
                    jsonObject.put("isSelect", "");
                }
            }
            if(jsonObject.get("permissions")!=null&&((JSONArray)jsonObject.get("permissions")).size()>0){
                isPermissionUsed((JSONArray)jsonObject.get("permissions"),usedPermission,role_id);
            }
        }
        return array;
    }
    /**
     * //获取当前角色所有已使用的权限
     * @param token 该角色对应的第一个系统名称(暂无系统对应表 所以将结构写死)
     * @return list
     */
    public List rolePermissionSearch(String token){
        List rel_list = new ArrayList();
        Map<String, String> roleParams = new HashMap<>();
        //获取该角色对应的第一个系统名称
        try {
            Result result = HttpServiceUtil.userService("role2PermissionSearch", roleParams);
            if (result.getValue()!=null){
                rel_list = (List) result.getValue();
            }
        } catch (Exception e) {
            logger.error("获取权限失败：",e);
        }
        return rel_list;
    }

    /**
     * 获取该系统下所有权限
     * @param token 该角色对应的第一个系统名称(暂无系统对应表 所以将结构写死)
     * @return list
     */
    public JSONObject permissionsSearch(String token){
        Map<String, String> PermissionParams = new HashMap<>();
        PermissionParams.put("token", token);
        JSONObject rel_list = new JSONObject();
        try {
            Result result = HttpServiceUtil.userService("sys2permissionsSearch", PermissionParams);
            if (result.getValue()!=null){
                rel_list = (JSONObject) result.getValue();
            }
        } catch (Exception e) {
            logger.error("获取选定系统下的权限失败：",e);
        }
        return rel_list;
    }


    /**
     * 添加身份和角色之间的对关系
     * @param typeId 身份id
     * @param roleId 角色id
     */
    //todo 注释不清晰
    public void role2userTypeAdd(String typeId,String roleId){
        Map<String, String> userTypeParams = new HashMap();
        userTypeParams.put("roleId",roleId);
        userTypeParams.put("typeId",typeId);
        try {
            HttpServiceUtil.userService("role2userTypeAdd", userTypeParams);
        } catch (Exception e) {
            logger.error("用户和角色的关系添加失败",e);
        }
    }

    /**
     *删除角色表内容
     * @param params 调用服务方法所需的参数
     * @param oldRoleId 修改前的角色ID
     */
    public void roleDelete(Map<String, String> params,String oldRoleId){
        params.put("roleId",oldRoleId);
        try {
            HttpServiceUtil.userService("roleDelete",params);
        } catch (Exception e) {
            logger.error("删除角色列表失败",e);
        }
    }

    /**
     * 删除角色身份关系表内容
     * @param params 调用服务方法所需的参数
     */
    public void role2userTypeDelete(Map<String, String> params,String role_id){
        try {
            params.put("roleId",role_id);
            HttpServiceUtil.userService("role2userTypeDelete",params);
        } catch (Exception e) {
            logger.error("删除角色身份关系内容失败",e);
        }
    }
    /** 删除角色用户关系表内容
     * @param params 调用服务方法所需的参数
     */
    public void role2userDelete(Map<String, String> params,String role_id){
        try {
            params.put("roleId",role_id);
            HttpServiceUtil.userService("user2roleDelete",params);
        } catch (Exception e) {
            logger.error("删除角色用户关系内容失败",e);
        }
    }
    /** 删除角色权限关系表内容
     * @param params 调用服务方法所需的参数
     */
    public void role2permissionDelete(Map<String, String> params,String role_id,String token){
        try {
            params.put("roleId",role_id);
            params.put("token",token);
            HttpServiceUtil.userService("role2permissionDelete",params);
        } catch (Exception e) {
            logger.error("删除角色权限关系表内容失败",e);
        }
    }

}
