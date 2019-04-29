package com.honghe.role.dao;

import com.honghe.user.util.JdbcTemplateUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by zhanghongbin on 2016/10/9.
 */
public class Role2PermissionDao {


    public static Role2PermissionDao INSTANCE = new Role2PermissionDao();

    public Map<String,Map<String,String>> find(){
        return JdbcTemplateUtil.getJdbcTemplate().find("select r2p.role_id as roleId, group_concat(up.permission_id) as permissionId,group_concat(up.permission_name) as permissionName ,group_concat(up.permission_path) as permissionPath ,group_concat(up.p_id) as pId,group_concat(up.permission_desc) as permissionDesc  from user_role2permission as r2p,user_permission as up where r2p.permission_id = up.permission_id group by role_id","roleId");
    }
    public List<Map<String,String>> findList(){
        return JdbcTemplateUtil.getJdbcTemplate().findList("select role_id as roleId, permission_id as permissionId from user_role2permission ");
    }

    /**
     * 添加角色权限关系信息
     *
     * @param data 所有数据
     * @return long
     */
    public long add(Map<String, Object> data) {
        String roleId = "";
        if (data.containsKey("roleId")) {
            roleId = data.get("roleId").toString();
        }
        String typeId = "";
        if (data.containsKey("permissionId")) {
            typeId = data.get("permissionId").toString();
        }
        String sql = "insert into user_role2permission (role_id,permission_id) values ('" + roleId + "','" + typeId + "')";
        return JdbcTemplateUtil.getJdbcTemplate().add(sql, "id");
    }

    /**
     * 删除角色权限关系信息
     *
     * @param roleId 角色ID
     * @return boolean
     */
    public boolean delete(String roleId,Object token) {
        StringBuffer sb = new StringBuffer("delete from user_role2permission where role_id =");
        sb.append(roleId);
        if(token != null && !"".equals(token)){
            sb.append(" and permission_id in (select permission_id from user_sys2permission where sys_name='");
            sb.append(token.toString());
            sb.append("')");
        }
		//不删除首页权限
        sb.append(" and permission_id!='500001'");
        return JdbcTemplateUtil.getJdbcTemplate().delete(sb.toString());
    }

    /**
     * 根据角色id获取角色权限列表
     *
     * @param userId 用户ID
     * @param sys    平台标识
     * * @return boolean
     * @author caoqian
     */
    public List<Map<String,String>> userPermissionByUserId(String userId,String sys) {
        String sql="SELECT DISTINCT per.permission_id as per_id,per.permission_desc AS per_desc,per.p_id as pid FROM user_permission per " +
                "LEFT JOIN user_sys2permission user2per ON per.permission_id = user2per.permission_id " +
                "LEFT JOIN user_role2permission role2per ON role2per.permission_id=per.permission_id " +
                "LEFT JOIN user_user2role user2role ON user2role.role_id=role2per.role_id WHERE 1=1 ";
        if(userId!=null && !"1".equals(userId)){
            sql+=" and user2role.user_id="+userId;
        }
        if(sys!=null && !"".equals(sys)){
            sql+=" and user2per.sys_name = '"+sys+"'";
        }
        List<Map<String,String>> resultList=JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        if(resultList!=null && !resultList.isEmpty()){
            return resultList;
        }else{
            return null;
        }
    }
}
