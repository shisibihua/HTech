package com.honghe.user.dao.impl;/**
 * Created by lyx on 2017-01-11 0011.
 */

import com.honghe.user.dao.BaseUserDao;
import com.honghe.user.dao.ITeacherDutyType;
import com.honghe.user.dao.entity.TeacherDutyType;
import com.honghe.user.util.JdbcTemplateUtil;
import com.honghe.user.util.StringUtil;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 教师职务
 *
 * @author lyx
 * @create 2017-01-11 13:10
 **/
public class TeacherDutyTypeUserDao extends BaseUserDao implements ITeacherDutyType {


    @Override
    public String getIdsByName(final String names) {
        String sqlName = StringUtil.getSqlString(names);
        String sql = " SELECT id FROM  user_type AS T WHERE T.type_name in(" + sqlName + ")";
        List<String> value = JdbcTemplateUtil.getJdbcTemplate().findValue(sql);
        if(value!=null && !value.isEmpty()) {
            return StringUtil.listToString(value, ",");
        }else{
            return "";
        }
        //<editor-fold desc="oldcode">
        //        String re_value = "";
//        final String sql_name = StringUtil.getSqlString(names);
//        re_value = this.getHibernateTemplate().execute(new HibernateCallback<String>() {
//            @Override
//            public String doInHibernate(Session session) throws HibernateException {
//                String sql = " SELECT id FROM  TeacherDutyType AS T WHERE T.type_name in(" + sql_name + ")";
//                Query query = session.createQuery(sql);
//                List<Object> list = (List<Object>) query.list();
//                return StringUtil.listToString(list, ",");
//            }
//        });
//        return re_value;
        //</editor-fold>
    }

    @Override
    public Set<TeacherDutyType> getTypeByIds(String ids) {
        String sqlName = StringUtil.getSqlString(ids);
        String sql = "select * FROM  user_type AS T WHERE T.id in(" + sqlName + ")";
        List<Map<String, String>> list = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        Set<TeacherDutyType> typeSet = new LinkedHashSet<>();
        for (Map<String, String> map : list) {
            TeacherDutyType teacherDutyType = createTeacherDutyType(map);
            typeSet.add(teacherDutyType);
        }
        return typeSet;

        //<editor-fold desc="oldcode">
        //        Set<TeacherDutyType> typeSet = new LinkedHashSet<>();
//        final String sql_name = StringUtil.getSqlString(ids);
//        List<TeacherDutyType> list = this.getHibernateTemplate().execute(new HibernateCallback< List<TeacherDutyType>>() {
//            @Override
//            public  List<TeacherDutyType> doInHibernate(Session session) throws HibernateException {
//                String sql = "FROM  TeacherDutyType AS T WHERE T.id in(" + sql_name + ")";
//                Query query = session.createQuery(sql);
//                List<TeacherDutyType> list = (List<TeacherDutyType>) query.list();
//                return list;
//            }
//        });
//        typeSet.addAll(list);
//    return typeSet;
        //</editor-fold>
    }

    private TeacherDutyType createTeacherDutyType(Map<String, String> map) {
        TeacherDutyType teacherDutyType = new TeacherDutyType();
        teacherDutyType.setId(Integer.parseInt(map.get("id") == null ? "" : map.get("id").trim()));
        teacherDutyType.setType_name(map.get("type_name") == null ? "" : map.get("type_name").trim());
        return teacherDutyType;
    }
}
