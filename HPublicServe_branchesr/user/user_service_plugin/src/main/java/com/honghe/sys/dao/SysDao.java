package com.honghe.sys.dao;

import com.honghe.user.util.JdbcTemplateUtil;
import com.honghe.user.util.StringUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author zhanghongbin
 * @date 2016/10/9
 */
public class SysDao {
    private static final Logger logger = Logger.getLogger(SysDao.class);

    public List<Map<String, String>> find()
    {
        return JdbcTemplateUtil.getJdbcTemplate().findList("select sys_id as id,sys_name as name,sys_desc as description from user_sys");
    }

    public void setModuleInfo(List<Map<String, String>> listInfo)
    {
        long count = JdbcTemplateUtil.getJdbcTemplate().count("select count(*) from user_sys_status");
        if (count > 0L)
        {
            JdbcTemplateUtil.getJdbcTemplate().delete("delete from user_sys_status");
            logger.debug("删除数据库已存在的模块状态信息");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
        String intime = sdf.format(new Date());
        for (Object module : listInfo)
        {
            Map moduleMap = (Map)module;
            String mdesc = moduleMap.get("mdesc").toString();
            String name = moduleMap.get("name").toString().toLowerCase();
            String status = moduleMap.get("status").toString();
            String sql = "insert into user_sys_status (m_desc,m_name,m_status,m_time) values('" + mdesc + "','" + name + "','" + status + "','" + intime + "')";
            JdbcTemplateUtil.getJdbcTemplate().add(sql, new String[] { "id" });
        }
    }

    public boolean isExistSetting(String id, String name)
    {
        boolean flag = false;
        String sql = "select count(*) from user_sys_position where user_id = '" + id + "' ";
        if (!StringUtil.isEmpty(name)) {
            sql = sql + "and pm_name='" + name + "'";
        }
        long count = JdbcTemplateUtil.getJdbcTemplate().count(sql);
        if (count > 0L) {
            flag = true;
        }
        return flag;
    }

    public List<Map<String, String>> getModuleInfo(String userId)
    {
        String sql = "select pm_name as name,pm_desc as mdesc,pm_x as rowIndex,pm_y as colIndex,pm_width as colSize,pm_length as rowSize from user_sys_position where user_id = '" + userId + "' order by pm_x,pm_y";

        return JdbcTemplateUtil.getJdbcTemplate().findList(sql);
    }

    public List getPermissionedModule(String hostId)
    {
        String sql = "SELECT DISTINCT usp.sys_name FROM user_sys2permission usp WHERE usp.permission_id in ( SELECT DISTINCT urp.permission_id FROM user_role2permission urp, user_user2role uur, USER u WHERE u.user_id = uur.user_id AND uur.role_id = urp.role_id AND u.user_id = '" + hostId + "')";



        return JdbcTemplateUtil.getJdbcTemplate().findList(sql);
    }

    public List getInstalledModule()
    {
        return JdbcTemplateUtil.getJdbcTemplate().findList("select m_name from user_sys_status where m_status='1'");
    }

    public void setModulePositionInfo(List<Map<String, String>> list, String userId)
    {
        for (Object module : list)
        {
            Map moduleMap = (Map)module;
            String name = moduleMap.get("name").toString();
            String desc = moduleMap.get("mdesc").toString();
            String pmX = moduleMap.get("rowIndex").toString();
            String pmY = moduleMap.get("colIndex").toString();
            String length = moduleMap.get("rowSize").toString();
            String width = moduleMap.get("colSize").toString();
            if (isExistSetting(userId, name))
            {
                String sql = "update user_sys_position set user_id='" + userId + "',pm_name='" + name + "',pm_desc='" + desc + "'" + ",pm_x='" + pmX + "',pm_y='" + pmY + "',pm_length='" + length + "',pm_width='" + width + "' where user_id=" + userId + " and pm_name='" + name + "'";

                JdbcTemplateUtil.getJdbcTemplate().update(sql);
            }
            else
            {
                String sql = "insert into user_sys_position (user_id,pm_name,pm_desc,pm_x,pm_y,pm_length,pm_width) values (" + userId + ",'" + name + "','" + desc + "'," + pmX + "," + pmY + "," + length + "," + width + ")";

                JdbcTemplateUtil.getJdbcTemplate().add(sql, new String[] { "id" });
            }
        }
    }

}
