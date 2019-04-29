package com.honghe.user.dao;

import com.honghe.user.util.JdbcTemplateUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanghongbin on 2015/6/2.
 */
public class User2RoleDao {


    public Map<String, Map<String, String>> find() {
        return JdbcTemplateUtil.getJdbcTemplate().find(" select u2r.user_id as userId, group_concat(ur.role_id) " +
                "as roleId,group_concat(ur.role_name) as roleName from user_role as ur,user_user2role as u2r where " +
                "ur.role_id = u2r.role_id group by u2r.user_id", "userId");
    }

    public List<Long> add(String userId, String[] roleId) {
        List<Long> result = new ArrayList<>();
        for (String roleInfo : roleId) {
            if("".equals(roleInfo)){
                continue;
            }
            result.add(JdbcTemplateUtil.getJdbcTemplate().add("insert into user_user2role(user_id,role_id) " +
                    "values(" + userId + "," + roleInfo + ")", "id"));
        }
        return result;
    }


    /**
     * 删除角色用户关系信息
     *
     * @param roleId 角色ID
     * @return boolean
     */
    public boolean deleteByRoleId(String roleId) {
        return JdbcTemplateUtil.getJdbcTemplate().delete("delete from user_user2role where role_id=" + roleId);
    }


    public boolean deleteByUserId(String userId) {
        return JdbcTemplateUtil.getJdbcTemplate().delete("delete from user_user2role where user_id=" + userId);
    }

    public Map<String, String> getRoleIdByUserId(String userId) {
        List<Map<String, String>> list = JdbcTemplateUtil.getJdbcTemplate().findList("select r.role_id as roleId," +
                "r.role_name as roleName, r.role_init as roleInit from user_role r left join user_user2role u on " +
                "r.role_id=u.role_id where u.user_id=" + userId);
        Map<String, String> returnMap = new HashMap<>();
        for (Map<String, String> map : list) {
            returnMap.put(map.get("roleId"), map.get("roleName"));
        }
        return returnMap;
    }
}
