package com.honghe.sys.dao;

import com.honghe.user.util.JdbcTemplateUtil;
import com.honghe.permissions.bean.Permissions;
import com.honghe.permissions.bean.SysPermissions;
import com.honghe.user.util.SysName;
import net.sf.json.JSONSerializer;

import java.util.List;
import java.util.Map;

/**
 *
 * @author zhanghongbin
 * @date 2016/10/9
 */
public class Sys2PermissionsDao
{
    public Permissions find(String sysName)
    {
        String newSysName = SysName.convertSysName(sysName);
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList("select p.* from user_permission as p, user_sys2permission as s where p.permission_id = s.permission_id and s.sys_name='" + newSysName.trim() + "'");
        Permissions rootPermissions = new Permissions();
        rootPermissions.setId("0");
        recursiveOrganization(rootPermissions, result, rootPermissions.getId());
        return rootPermissions;
    }

    public SysPermissions find(String userId, String oldSysName)
    {
        String sysName = SysName.convertSysName(oldSysName);
        String sql = "select DISTINCT p.* from user_permission p ";
        if (!"1".equals(userId)) {
            sql = sql + " RIGHT JOIN (select r.permission_id from user_role2permission r ,user_user2role ur where r.role_id = ur.role_id  and ur.user_id='" + userId + "') as ru ON p.permission_id = ru.permission_id ";
        }
        if (!"".equals(sysName)) {
            sql = sql + " RIGHT  JOIN (select s.permission_id from user_sys2permission s where s.sys_name='" + sysName + "') as u ON p.permission_id = u.permission_id";
        }
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        SysPermissions rootPermissions = new SysPermissions();
        rootPermissions.setId("0");
        rootPermissions.setSysName(sysName);
        recursiveOrganization(rootPermissions, result, rootPermissions.getId(), sysName);
        return rootPermissions;
    }

    public SysPermissions find(String userId, String sysName, int pId)
    {
        sysName = SysName.convertSysName(sysName);
        String sql = "select DISTINCT p.* from user_permission p ";
        if (!"".equals(sysName)) {
            sql = sql + " RIGHT JOIN user_sys2permission s ON p.permission_id = s.permission_id ";
        }
        if (!"1".equals(userId)) {
            sql = sql + " RIGHT JOIN (select r.permission_id from user_role2permission r ,user_user2role ur where r.role_id = ur.role_id  and ur.user_id='" + userId + "') as ru ON p.permission_id = ru.permission_id ";
        }
        sql = sql + " where p.p_id=" + pId;
        if (!"".equals(sysName)) {
            sql = sql + " and s.sys_name='" + sysName + "'";
        }
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        SysPermissions rootPermissions = new SysPermissions();
        rootPermissions.setId(String.valueOf(pId));
        rootPermissions.setSysName(sysName);
        recursiveOrganization(rootPermissions, result, rootPermissions.getId(), sysName);
        return rootPermissions;
    }

    public Map<String, Map<String, String>> findBySysName(String sysName)
    {
        sysName = SysName.convertSysName(sysName);
        return JdbcTemplateUtil.getJdbcTemplate().find("select permission_id as permissionId from user_sys2permission  where sys_name='" + sysName.trim() + "'", "permissionId");
    }

    private void recursiveOrganization(Permissions permissions, List<Map<String, String>> result, String id)
    {
        for (Map<String, String> mapResult : result)
        {
            String pId = mapResult.get("p_id");
            if (pId.equals(id))
            {
                String permission_id = mapResult.get("permission_id");

                Permissions dd = new Permissions(permission_id, mapResult.get("permission_name"), (String)mapResult.get("permission_path"), (String)mapResult.get("permission_desc"), (String)mapResult.get("p_id"));
                permissions.getPermissions().add(dd);
                recursiveOrganization(dd, result, permission_id);
            }
        }
    }

    private void recursiveOrganization(SysPermissions sysPermissions, List<Map<String, String>> result, String id, String sysName)
    {
        for (Map<String, String> mapResult : result)
        {
            String pId = mapResult.get("p_id");
            if (pId.equals(id))
            {
                String permission_id = mapResult.get("permission_id");

                SysPermissions dd = new SysPermissions(permission_id, mapResult.get("permission_name"),
                        mapResult.get("permission_path"), mapResult.get("permission_desc"), mapResult.get("p_id"), sysName);
                sysPermissions.getPermissions().add(dd);
                recursiveOrganization(dd, result, permission_id, sysName);
            }
        }
    }
}
