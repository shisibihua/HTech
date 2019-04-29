package com.honghe.user.dao.impl;

import com.honghe.user.dao.BaseUserDao;
import com.honghe.user.dao.ISubjectDap;
import com.honghe.user.dao.entity.Subject;
import com.honghe.user.util.JdbcTemplateUtil;
import com.honghe.user.util.StringUtil;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 科目
 *
 * @author lyx
 * @create 2017-01-11 14:08
 **/
public class SubjectUserDao extends BaseUserDao implements ISubjectDap {

    /**
     * 依据科目名字获取科目id
     *
     * @param names   科目名，多个用逗号分割
     * @param stageId 学段Id
     * @return 返回多个时，用逗号分割
     */
    @Override
    public String getIdsByName(String names, final Integer stageId) {
        final String sqlName = StringUtil.getSqlString(names);
        String subjectSql = "  SELECT id FROM classification_subject AS S WHERE S.name in (" + sqlName + ") AND S.season_id =" + stageId + "";
        List<String> value = JdbcTemplateUtil.getJdbcTemplate().findValue(subjectSql);
        if (!value.isEmpty()) {
            return value.get(0);
        } else {
            return "";
        }
        //<editor-fold desc="oldcode">
        //        String re_value = "";
//        final String sql_name = StringUtil.getSqlString(names);
//        re_value = this.getHibernateTemplate().execute(new HibernateCallback<String>() {
//            @Override
//            public String doInHibernate(Session session) throws HibernateException {
//                String sql = "  SELECT id FROM Subject AS S WHERE S.name in (" + sql_name + ") AND S.season_id ="+stageId+"" ;
//                Query query = session.createQuery(sql);
//                List<Object> list = (List<Object>) query.list();
//                return StringUtil.listToString(list, ",");
//            }
//        });
//        return re_value;
        //</editor-fold>
    }

    /**
     * 根据id获取科目对象
     *
     * @param ids 科目id，多个用逗号分割
     * @return
     */
    @Override
    public Set<Subject> getSubjectById(String ids) {
        Set<Subject> subjectSet = new LinkedHashSet<>();
        String sqlName = StringUtil.getSqlString(ids);
        String sql = "select * from  classification_subject AS s WHERE s.id in(" + sqlName + ")";
        List<Map<String, String>> list = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        if (!list.isEmpty()){
            for (Map<String, String> map : list) {
                Subject subject = createSubject(map);
                subjectSet.add(subject);
            }
        }
        return subjectSet;
        //<editor-fold desc="oldcode">
        //        Set<Subject> subjectSet = new LinkedHashSet<>();
//        final String sql_name = StringUtil.getSqlString(ids);
//        List<Subject> list = this.getHibernateTemplate().execute(new HibernateCallback< List<Subject>>() {
//            @Override
//            public  List<Subject> doInHibernate(Session session) throws HibernateException {
//                String sql = "FROM  Subject AS s WHERE s.id in(" + sql_name + ")";
//                Query query = session.createQuery(sql);
//                List<Subject> list = (List<Subject>) query.list();
//                return list;
//            }
//        });
//        subjectSet.addAll(list);
//        return subjectSet;
        //</editor-fold>
    }

    public static void main(String[] args) {
        SubjectUserDao subjectUserDao=new SubjectUserDao();
        subjectUserDao.getSubjectById("1");
    }
}
