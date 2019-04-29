package com.honghe.role.dao;

import com.honghe.user.util.JdbcTemplateUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by zhanghongbin on 2016/10/9.
 */
public class Role2UserTypeDao {


    public Map<String, Map<String, String>> find() {
        return JdbcTemplateUtil.getJdbcTemplate().find("SELECT type_id as typeId, group_concat(role_id) as roleId  FROM user_role2type group by type_id", "typeId");
    }

    /**
     * 添加角色身份关系信息
     *
     * @param data
     * @return
     */
    public boolean add(Map<String, Object> data) {
        String roleId = data.get("roleId").toString();
        String typeId = data.get("typeId").toString();
        String sql = "insert into user_role2type (role_id,type_id) values (?,?)";
        return JdbcTemplateUtil.getJdbcTemplate().add(sql, new Object[]{roleId, typeId});
    }

    /**
     * 查询所有角色身份关系
     *
     * @return
     */
    public  List<Map<String, String>> findList() {

        return JdbcTemplateUtil.getJdbcTemplate().findList("SELECT id as role2typeId, role_id as roleId, type_id as typeId FROM user_role2type");
    }

    public  List<Map<String, String>> findByUserType(String typeId) {
        return JdbcTemplateUtil.getJdbcTemplate().findList("select role_id as roleId FROM user_role2type where type_id = '" + typeId + "'");
    }


    /**
     * 删除角色身份关系信息
     *
     * @param roleId 角色ID
     * @return boolean
     */
    public boolean delete(String roleId) {
        return JdbcTemplateUtil.getJdbcTemplate().delete("delete from user_role2type where role_id in (" + roleId + ")");
    }

}
