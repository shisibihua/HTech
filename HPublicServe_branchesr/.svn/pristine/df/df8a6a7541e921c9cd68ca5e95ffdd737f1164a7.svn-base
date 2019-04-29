package com.honghe.role.dao;

import com.honghe.user.util.JdbcTemplateUtil;
import com.honghe.role.cache.Role2PermissionCacheDao;
import com.honghe.role.cache.Role2UserTypeCacheDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qinzhihui on 2016/7/14.
 */
public class RoleDao {


    protected RoleDao() {

    }
    private static final String HOMEPAGEID="500001";

    public static RoleDao INSTANCE = new RoleDao();
    private Role2UserTypeCacheDao role2UserTypeDao = new Role2UserTypeCacheDao();

    public Role2UserTypeDao getRoleUserTypeRelationDao() {
        return this.role2UserTypeDao;
    }

    private Role2PermissionCacheDao role2PermissionDao = new Role2PermissionCacheDao();
    //private Role2PermissionDao role2PermissionDao = new Role2PermissionDao();

    public Role2PermissionDao getRolePermissionRelationDao() {
        return this.role2PermissionDao;
    }


    @Deprecated//(以后可能不会用)
    public Map<String, String> find(String token) {
        return new HashMap<>();
    }

    /**
     * 查询所有角色
     *
     * @return 结果集
     */
    public List<Map<String, String>> findAllRole() {
        List<Map<String, String>> list = JdbcTemplateUtil.getJdbcTemplate().findList("SELECT role_id as roleId, role_name as roleName, role_init as roleInit FROM user_role order by role_id");
        return list;
    }

    /**
     * 根据角色id查询角色信息
     *
     * @param roleId
     * @return
     */
    @Deprecated
    public Map<String, String> find(String... roleId) {
        StringBuilder sb = new StringBuilder();
        for (String id : roleId) {
            sb.append("'" + id + "',");
        }
        if (sb.length() == 0) {
            return new HashMap<>();
        }
        String where = sb.substring(0, sb.length() - 1);
        return JdbcTemplateUtil.getJdbcTemplate().find("select role_id as roleId, role_name as roleName from user_role where role_id in(" + where + ")","roleId","roleName");
    }


    /**
     * 添加新角色信息
     *
     * @param data 所有数据
     * @return long
     */
    public long addRole(Map<String, Object> data) {
        String roleName = "";
        if (data.containsKey("roleName")) {
            roleName = data.get("roleName").toString();
        }

        String sql = "insert into user_role (role_name) values ('" + roleName + "')";
        Long indexId=JdbcTemplateUtil.getJdbcTemplate().add(sql, "role_id");
        String add_role2perssion_sql = "insert into user_role2permission (role_id,permission_id) values ('" + indexId + "','"+HOMEPAGEID+"')";
        JdbcTemplateUtil.getJdbcTemplate().add(add_role2perssion_sql,"id");
        return indexId;
    }

    /**
     * 查询角色是否存在
     *
     * @param data 所有数据
     * @return boolean
     */
    public boolean isRoleExist(Map<String, Object> data) {
        String roleName = "";
        String roleId = "";
        if (data.containsKey("roleName")) {
            roleName = data.get("roleName").toString();
        }
        String sql = "select count(*) from user_role where role_name='" + roleName + "'";
        if (data.containsKey("roleId")) {
            roleId = data.get("roleId").toString();
            sql = sql + " and role_id<>" + roleId;
        }

        long count = JdbcTemplateUtil.getJdbcTemplate().count(sql);
        if (count > 0) {
            return true;
        }
        return false;
    }

    /**
     * 根据角色id，查询角色名
     *
     * @param role_id 角色ID
     * @return String
     */
    public String findRoleNameById(String role_id) {

        String sql = "select role_name from user_role where role_id=" + role_id;
        Map<String,String> map = JdbcTemplateUtil.getJdbcTemplate().find(sql);
        if(map.size() == 0 || !map.containsKey("role_name")){
            return "null";
        }

        return JdbcTemplateUtil.getJdbcTemplate().find(sql).get("role_name").toString();
    }
    //-------------------------------------------------删除模块------------------------------------------------------------------

    /**
     * 删除角色信息
     *
     * @param roleId 角色ID
     * @return boolean
     */
    public boolean delete(String roleId) {
        return JdbcTemplateUtil.getJdbcTemplate().delete("delete from user_role where role_id in (" + roleId + ")");
    }

    /**
     * 修改角色信息
     *
     * @param data
     * @return
     */
    public boolean updateRoleByRoleId(Map<String, Object> data) {
        int roleId = Integer.parseInt(data.get("roleId").toString());
        String updateSql = "";
        if (data.containsKey("roleName")) {
            String roleName = data.get("roleName").toString().trim();
            updateSql += "role_name= '" + roleName + "',";
        }
        if (updateSql.length() == 0) {
            return false;
        }
        updateSql = updateSql.substring(0, updateSql.length() - 1);
        return JdbcTemplateUtil.getJdbcTemplate().update("update user_role set " + updateSql + " where role_id=" + roleId);
    }

}
