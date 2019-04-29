package com.honghe.web.user.controller.role;

import com.honghe.service.client.Result;
import com.honghe.web.user.service.role.RoleService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lyx on 2016-09-26.
 */
@Controller
@RequestMapping("/Role")
public class RoleController {
    @Autowired
    RoleService roleService;

    /**
     * 获取所有角色列表
     *
     * @param pw 写入数据
     */
    @RequestMapping("/getRoles")
    public void getRoles(PrintWriter pw) {
        JSONObject json = new JSONObject();
        List roles = roleService.getRoles();
        json.put("roleList", roles);
        pw.write(json.toString());
    }

    /**
     * 获取所有角色列表，并根据用户已分配的角色做标记
     *
     * @param pw 写入数据
     */
    @RequestMapping("/getUserRoles")
    public void getUserRoles(String userId,PrintWriter pw) {
        JSONObject json = new JSONObject();
        List roles = roleService.getUserRoles(userId);
        json.put("roleList", roles);
        pw.write(json.toString());
    }
    /**
     * 改变角色 获取对应角色的系统和权限
     * @param role_id  选中的角色对应的所有ID
     * @param token  该角色对应的所有系统
     * @param pw
     */
    @RequestMapping("/changeRole")
    public void changeRole(String role_id,String token,PrintWriter pw){
        JSONObject json=new JSONObject();
        if (role_id == null) {
            json.put("msg", "error");
            pw.write(json.toString());
            return;
        }
        Map roleInfo=roleService.changeRole(role_id,token);
        json.put("sysList", roleInfo.get("resultSysList"));
        json.put("AllPermission", roleInfo.get("AllPermission"));

        pw.write(json.toString());

    }

    /**
     * 切换当前系统
     * @param role_id 该角色的所有ID
     * @param token 选中的系统
     * @param pw
     */
    @RequestMapping("/changeToken")
    public void changeToken(String role_id, String token,PrintWriter pw ){
        JSONObject json = new JSONObject();
        JSONObject AllPermission = roleService.changeToken(role_id,token);
        json.put("AllPermission", AllPermission);
        pw.write(json.toString());
    }

    /**
     * 获取所有系统 添加角色时使用
     * @param pw
     */
    @RequestMapping("/getToken")
    public void getToken(PrintWriter pw){
        //查询所有系统
        JSONObject json = new JSONObject();
        List<Map<String,Object>> sysList = roleService.getAllSystems();
        json.put("tokenList", sysList);
        pw.write(json.toString());
    }

    /**
     * 获取所有身份 添加角色时使用
     * @param pw
     */
    @RequestMapping("/getUserType")
    public void getUserType(PrintWriter pw){
        JSONObject json = new JSONObject();
        List userTypeList = roleService.getUserType();
        json.put("userTypeList",userTypeList);
        pw.write(json.toString());
    }


    /**
     * 获取所有用户身份  修改角色时使用
     * @param role_id 该角色的所有ID
     * @param pw
     */
    @RequestMapping("/getUserTypeUsed")
    public void getUserTypeUsed(String role_id,PrintWriter pw){
        JSONObject json = new JSONObject();
        List allUserTypeList = roleService.getUserType();//获取所有身份
        List usedUsertype = roleService.getusedUsertype();//获取已选身份
        if (usedUsertype==null){
            for (int i = 0; i < allUserTypeList.size(); i++) {
                Map allMap = (Map) allUserTypeList.get(i);
                allMap.put("isSelect", "");
            }
            json.put("usedTypeList", allUserTypeList);
            pw.write(json.toString());
            return;
        }
        //将被选中的项目加上选中提示
        String oldUsedType =addTip(role_id,allUserTypeList,usedUsertype);
        json.put("usedTypeList", allUserTypeList);
        if (!"".equals(oldUsedType)){
            json.put("oldUsedType",oldUsedType.substring(0,oldUsedType.length()-1));
        }else {
            json.put("oldUsedType","");
        }
        pw.write(json.toString());

    }

    /**
     * 确定添加角色
     * @param role_name 角色名称
     * @param typeIds 身份集合
     * @param pw
     */
    @RequestMapping("/addRole")
    public void addRole(String role_name,String typeIds,PrintWriter pw){
        JSONObject json = new JSONObject();
        String [] typeId = typeIds.split(",");

        long roleId = roleService.roleAdd(role_name);
        // 添加失败
        if(roleId == -1){
            json.put("msg",-1);
        }else{
            // 添加成功 ，添加用户与身份关系
            if (!typeIds.equals("")||typeIds==null){
                for (int j =0;j<typeId.length;j++){
                    roleService.role2userTypeAdd(typeId[j], String.valueOf(roleId));
                }
            }
            json.put("msg",1);
        }
        pw.write(json.toString());
    }

    /**
     * 修改角色内容
     * @param role_name 角色名称
     * @param typeIds  修改后的身份ID
     * @param oldRoleId  修改前的角色ID
     * @param pw
     */
    @RequestMapping("/editRole")
    public void editRole(String role_name,String typeIds,String oldRoleId,PrintWriter pw){
        JSONObject json = new JSONObject();
        boolean flag = roleService.editRole(role_name,typeIds,oldRoleId);
        json.put("msg",flag);
        pw.write(json.toString());
    }

    /**
     *  删除角色
     * @param role_id 角色ID
     * @param pw
     */
    @RequestMapping("/deleteRole")
    public void deleteRole(String role_id,PrintWriter pw){
        JSONObject json = new JSONObject();
        roleService.deleteRole(role_id);
        json.put("msg",true);
        pw.write(json.toString());
    }
    /**
     *  修改权限
     * @param role_id 角色ID
     * @param authorityIds 所选权限
     * @param pw
     */
    @RequestMapping("/updatePermisssion")
    protected void updatePermisssion(String role_id,String authorityIds,String token,PrintWriter pw){
        JSONObject json = new JSONObject();
        roleService.updatePermisssion(role_id,authorityIds,token);
        json.put("msg",true);
        pw.write(json.toString());
    }

    //---------------------------------controller里供调用的方法-----------------------------------

    /**
     * 将被选中的项目加上选中提示
     * @param role_id  该角色的所有ID
     * @param allUserTypeList 所有身份
     * @param usedUsertype 已选身份
     * @return String
     */
    protected String addTip(String role_id,List allUserTypeList,List usedUsertype){
        String usertype="";
        for (int i = 0; i < allUserTypeList.size(); i++) {
            Map allMap = (Map) allUserTypeList.get(i);
            String type_id = (String) allMap.get("typeId");
            for (int j = 0; j < usedUsertype.size(); j++) {
                Map usedMap = (Map) usedUsertype.get(j);
                String usedType = (String) usedMap.get("typeId");
                if (isInRoleId(String.valueOf(usedMap.get("roleId")),role_id) && type_id.equals(usedType)) {
                    allMap.put("isSelect", "m-checked");
                    usertype +=usedType+",";
                    break;
                } else {
                    allMap.put("isSelect", "");
                }
            }
        }
        return  usertype;
    }
    /**
     * 判断角色id中是否有被选中的id
     * @param roleId
     * @param roleIds
     * @return boolean
     */

    protected boolean isInRoleId(String roleId,String roleIds){
        String[] roleList = roleIds.split(",");
        for(int i = 0 ; i < roleList.length ; i ++){
            if(roleList[i].equals(roleId)){
                return true;
            }
        }
        return false;
    }


}
