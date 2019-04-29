package com.honghe.user.dao.impl;

import com.honghe.dao.PageData;
import com.honghe.exception.DaoException;
import com.honghe.user.dao.BaseUserDao;
import com.honghe.user.dao.IParentDao;
import com.honghe.user.dao.entity.*;
import com.honghe.user.util.JdbcTemplateUtil;
import com.honghe.user.util.StringUtil;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * 家长的操作类
 *
 * @author zhaowj
 * @create 2017-01-09 09:22
 **/
public class ParentUserDao extends BaseUserDao implements IParentDao {

    Logger logger = Logger.getLogger(ParentUserDao.class);

    /**
     * 新增家长
     *
     * @param parent
     * @return
     */
    @Override
    public int parentAdd(Parent parent) throws DaoException {
        String saveParentSql = createSaveParentSql(parent);
        boolean execute = JdbcTemplateUtil.getJdbcTemplate().execute(saveParentSql);

        User user = parent.getUser();
        String saveUserSql = getSaveUserSql(user);
        boolean execute1 = JdbcTemplateUtil.getJdbcTemplate().execute(saveUserSql);

        String[] saveUserPcRelationsSqls = getSaveUserPcRelationsSqls(parent);
        boolean execute2 = JdbcTemplateUtil.getJdbcTemplate().execute(saveUserPcRelationsSqls);

        if (execute && execute1 && execute2) {
            return 1;
        } else {
            logger.error("新增家长失败！sql:" + saveParentSql + ";" + saveUserSql + ";" + saveUserPcRelationsSqls.toString());
            throw new DaoException("新增家长失败！");
        }

        //<editor-fold desc="oldcode">
        //        int re_value = ERROR_ADD_UPDATE;
//        try {
//            Serializable pKey = this.getHibernateTemplate().save(parent);
//            re_value = Integer.parseInt(pKey.toString());
//            parent.setId(re_value);
//        } catch (DataAccessException e) {
//            logger.error("插入家长对象失败");
//        }
//        return re_value;
        //</editor-fold>
    }

    private String[] getSaveUserPcRelationsSqls(Parent parent) {
        Set<Student> studentSet = parent.getStudentSet();
        String parentCode = String.valueOf(parent.getId());
        Iterator<Student> iterator = studentSet.iterator();
        String[] saveUserPcRelationsSqls = new String[studentSet.size()];
        int count = 0;
        while (iterator.hasNext()) {
            Student next = iterator.next();
            String studentCode = String.valueOf(next.getId());
            String saveUserPcRelationsSql = createSaveUserPcRelationsSql(parentCode, studentCode);
            saveUserPcRelationsSqls[count] = saveUserPcRelationsSql;
            count++;
        }
        return saveUserPcRelationsSqls;
    }

    private String createSaveUserPcRelationsSql(String parentCode, String studentCode) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO user_pc_relations ( " +
                " parent_code, " +
                " student_code " +
                ") " +
                "VALUES (")
                .append("'").append(parentCode).append("',")
                .append("'").append(studentCode).append("');");
        return sb.toString();
    }

    private String createSaveParentSql(Parent parent) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO user_parent ( " +
                " parent_code, " +
                " namepy, " +
                " parent_name, " +
                " parent_path, " +
                " membership, " +
                " parent_mobile, " +
                " email, " +
                " is_guardian, " +
                " is_together " +
                ") VALUES (")
                .append("'").append(parent.getParent_code()).append("'")
                .append("','").append(parent.getNamepy()).append("'")
                .append("','").append(parent.getParent_name()).append("'")
                .append("','").append(parent.getParent_path()).append("'")
                .append("','").append(parent.getMembership()).append("'")
                .append("','").append(parent.getParent_mobile()).append("'")
                .append("','").append(parent.getEmail()).append("'")
                .append("','").append(parent.getIs_guardian()).append("'")
                .append("','").append(parent.getIs_together()).append("'").append(");");
        return sb.toString();
    }

//    /**
//     * 修改家长
//     *
//     * @param parent
//     * @return
//     */
//    @Override
//    public int update(Parent parent) {
//        try {
//            this.getHibernateTemplate().merge(parent);
//        } catch (DataAccessException e) {
//            logger.error("修改家长对象失败");
//            return ERROR_ADD_UPDATE;
//        }
//        return SUCCESS_UPDATE;
//    }

    /**
     * 依据系统编号返回对象
     *
     * @param code 系统编号
     * @rn
     */
    @Override
    public Parent getParentByCode(final String code) {
        Parent re_value = new Parent();
        String parentSql = "FROM user_parent WHERE parent_code= '" + code + "'";
        List<Map<String, String>> parentList = JdbcTemplateUtil.getJdbcTemplate().findList(parentSql);
        if (!parentList.isEmpty()) {
            String parentCode = parentList.get(0).get("parent_code");
            Parent parent = createParent(parentList.get(0));
            assembleParentWithUser(parentCode, parent);
        }

        return re_value;
    }

    /**
     * 对parent进行组装操作，将user放置进去
     *
     * @param parentCode
     * @param parent
     */
    private void assembleParentWithUser(String parentCode, Parent parent) {
        List<Map<String, String>> userList = getUserListByUserCode(parentCode);
        if (!userList.isEmpty()) {
            User user = getUserById(userList);
            parent.setUser(user);
        }
    }

    private Parent createParent(Map<String, String> map) {
        Parent parent = new Parent();
        parent.setEmail(map.get("email") == null ? "" : map.get("email").trim());
        parent.setId(Long.parseLong(map.get("id") == null ? "" : map.get("id").trim()));
        parent.setIs_guardian(map.get("is_guardian") == null ? "" : map.get("is_guardian").trim());
        parent.setIs_together(map.get("is_together") == null ? "" : map.get("is_together").trim());
        parent.setMembership(map.get("membership") == null ? "" : map.get("membership").trim());
        parent.setNamepy(map.get("namepy") == null ? "" : map.get("namepy").trim());
        parent.setParent_code(map.get("parent_code") == null ? "" : map.get("parent_code").trim());
        parent.setParent_mobile(map.get("parent_mobile") == null ? "" : map.get("parent_mobile").trim());
        parent.setParent_name(map.get("parent_name") == null ? "" : map.get("parent_name").trim());
        parent.setParent_path(map.get("parent_path") == null ? "" : map.get("parent_path").trim());
        return parent;
    }

    /**
     * 依据家长名字返回对象list
     *
     * @param parentName 家长姓名可为空
     * @return
     */
    @Override
    public List<Parent> getParentByName(final String parentName) {
        List<Parent> list = new ArrayList<>();
        String parentSql = "SELECT " +
                "	* " +
                "FROM " +
                "	user_parent " +
                "WHERE " +
                "	parent_name LIKE '%" + parentName == null ? "" : parentName.trim() + "%'";
        List<Map<String, String>> parentList = JdbcTemplateUtil.getJdbcTemplate().findList(parentSql);
        if (!parentList.isEmpty()) {
            for (int i = 0; i < parentList.size(); i++) {
                Parent parent = createParent(parentList.get(i));
                String parentCode = parentList.get(i).get("parent_code");
                assembleParentWithUser(parentCode, parent);
                list.add(parent);

            }
        }
//        List<Parent> list = this.getHibernateTemplate().execute(new HibernateCallback<List<Parent>>() {
//            @Override
//            public List<Parent> doInHibernate(Session session) throws HibernateException {
//                String sql = "FROM Parent ";
//                if (!StringUtil.isEmpty(parentName)) {
//                    sql += "WHERE parent_name like " + "'%" + parentName + "%'";
//                }
//                Query query = session.createQuery(sql);
//                List<Parent> list = (List<Parent>) query.list();
//                return list;
//            }
//        });
        return list;
    }

    @Override
    public List<Parent> getStudentByParent(final String userId) {
        List<Parent> list = new ArrayList<>();
        String parentSql = "from user_parent p where p.parent_code in (select u.user_code FROM user u where u.user_id in (" + userId + " ))";
        List<Map<String, String>> parentList = JdbcTemplateUtil.getJdbcTemplate().findList(parentSql);
        if (!parentList.isEmpty()) {
            for (Map<String, String> map : parentList) {
                Parent parent = createParent(map);
                list.add(parent);
            }
        }
//        List<Parent> list = this.getHibernateTemplate().execute(new HibernateCallback<List<Parent>>() {
//            @Override
//            public List<Parent> doInHibernate(Session session) throws HibernateException {
//                String sql = "from Parent p where p.parent_code in (select u.user_code FROM User u where u.user_id in (" + userId + " ))";
//
//                Query query = session.createQuery(sql);
//                List<Parent> list = (List<Parent>) query.list();
//                return list;
//            }
//        });
        return list;
    }

//    /**
//     * 删除家长
//     *
//     * @param parentCode
//     * @return
//     */
//    @Override
//    public boolean delete(String parentCode) {
//        boolean re_value = false;
//        try {
//            Parent parent = getParentByCode(parentCode);
//            this.getHibernateTemplate().delete(parent);
//            re_value = true;
//        } catch (DataAccessException e) {
//            logger.error("删除家长对象失败");
//        }
//        return re_value;
//    }

    /**
     * 批量删除家长
     *
     * @param ids 家长id，多个用逗号隔开
     * @return
     */
    @Override
    public boolean deleteParents(final String ids) {
        String countSql = "select DISTINCT u.user_id from user_parent p left join user u on u.user_code = " +
                "p.parent_code where p.id in(" + ids + ")";
        List<String> userIdList = JdbcTemplateUtil.getJdbcTemplate().findValue(countSql);
        StringBuilder userIds = new StringBuilder();
        String sql = "";
        boolean execute = false;
        try {
            if (userIdList.size() > 0) {
                for (String uId : userIdList) {
                    userIds.append(uId)
                            .append(",");
                }
                String subUserIds = "";
                if (userIds.length() > 0 && ids.endsWith(",")) {
                    subUserIds = userIds.toString().substring(0, ids.lastIndexOf(","));
                }
                sql = "delete from user_parent where id in(" + subUserIds + ")";
                String userSql = "delete from user where user_id in(" + userIds + ")";
                execute = JdbcTemplateUtil.getJdbcTemplate().execute(sql, userSql);
            }
        } catch (Exception e) {
            logger.error("删除家长时出现异常", e);
            execute = false;
        }
        return execute;
//        final Integer value = getHibernateTemplate().execute(new HibernateCallback<Integer>() {
//            @Override
//            public Integer doInHibernate(Session session) throws HibernateException {
//                String count_sql = "select DISTINCT u.user_id from user_parent p left join user u on u.user_code = " +
//                        "p.parent_code where p.id in(" + ids + ")";
//                List<Integer> userIdList = session.createSQLQuery(count_sql).list();
//                String uIds = "";
//                int query = 0;
//                String sql = "";
//                if (userIdList.size() > 0) {
//                    for (int uId : userIdList) {
//                        uIds += uId + ",";
//                    }
//                    if (!"".equals(uIds) && uIds.endsWith(",")) {
//                        uIds = uIds.substring(0, uIds.lastIndexOf(","));
//                    }
//                    sql = "delete from user_parent where id in(" + ids + ")";
//                    query = session.createSQLQuery(sql).executeUpdate();
//                }
//                if (query > 0) {
//                    String userSql = "delete from user where user_id in(" + uIds + ")";
//                    session.createSQLQuery(userSql).executeUpdate();
//                }
//                return query;
//            }
//        });
    }

    /**
     * 获取所有的家长对象
     *
     * @return
     */
    @Override
    public List<Parent> getParents() {
        String parentSql = "select * from user_parent";
        List<Parent> resultList = new ArrayList<>();
        List<Map<String, String>> parentList = JdbcTemplateUtil.getJdbcTemplate().findList(parentSql);
        if (!parentList.isEmpty()) {
            for (Map<String, String> map : parentList) {
                Parent parent = createParent(map);
                resultList.add(parent);
            }
        }
//        List<Parent> re_value = new ArrayList<>();
//        re_value = (List<Parent>) this.getHibernateTemplate().find("from Parent");
//        return re_value;
        return resultList;
    }

    /**
     * 分页获取家长信息
     *
     * @param page       当前页
     * @param pageSize   每页的条目
     * @param parentName 关键词
     * @param parentCode 内部流水
     * @return
     */
    @Override
    public PageData<Parent> getParentsByPage(final int page, final int pageSize, final String parentName, final String parentCode, final String campusId) {
        String countSql = "select  count(*) from user_parent p";
        String conjuntion = " where ";
        if (!StringUtil.isEmpty(parentName)) {
            countSql += " where p.parent_name like " + "'%" + parentName + "%'";
            conjuntion = " and ";
        } else if (!StringUtil.isEmpty(parentCode)) {
            countSql += " where p.parent_code = " + "'" + parentCode + "'";
            conjuntion = " and ";
        }
        if (!StringUtil.isEmpty(campusId)) {
            countSql += conjuntion + " p.parent_code in (select pc.parent_code from user_pc_relations pc " +
                    "left join user_student s on s.student_code = pc.student_code " +
                    "left join user u on s.student_code = u.user_code " +
                    "left join ad_campus2user ad on u.user_id = ad.user_id where ad.campus_id ='" + campusId + "')";
        }
        List<String> value = JdbcTemplateUtil.getJdbcTemplate().findValue(countSql);
        //获取查询列表的总数量
        long count = 0;
        if (!value.isEmpty()) {
            count = Long.parseLong(value.get(0));
        }
//        session.createSQLQuery(countSql).list().get(0).toString()
//                long count = (Long) session.createSQLQuery(count_sql).uniqueResult();//获取查询列表的总数量
        String sql = countSql.replace("count(*)", "p.*");
        sql += " order by p.id desc";
        PageData pageData = JdbcTemplateUtil.getJdbcTemplate().findByPage(page, pageSize, countSql, sql);
        return pageData;
//        PageData pageData = getHibernateTemplate().execute(new HibernateCallback<PageData>() {
//            @Override
//            public PageData doInHibernate(Session session) throws HibernateException {
//                String count_sql = "select  count(*) from user_parent p";
//                String conjuntion = " where ";
//                if (!StringUtil.isEmpty(parentName)) {
//                    count_sql += " where p.parent_name like " + "'%" + parentName + "%'";
//                    conjuntion = " and ";
//                } else if (!StringUtil.isEmpty(parentCode)) {
//                    count_sql += " where p.parent_code = " + "'" + parentCode + "'";
//                    conjuntion = " and ";
//                }
//                if (!StringUtil.isEmpty(campusId)) {
//                    count_sql += conjuntion + " p.parent_code in (select pc.parent_code from user_pc_relations pc " +
//                            "left join user_student s on s.student_code = pc.student_code " +
//                            "left join user u on s.student_code = u.user_code " +
//                            "left join ad_campus2user ad on u.user_id = ad.user_id where ad.campus_id ='" + campusId + "')";
//                }
//                long count = Long.parseLong(session.createSQLQuery(count_sql).list().get(0).toString());//获取查询列表的总数量
////                long count = (Long) session.createSQLQuery(count_sql).uniqueResult();//获取查询列表的总数量
//                String sql = count_sql.replace("count(*)", "p.*");
//                sql += " order by p.id desc";
//                Query query = session.createSQLQuery(sql).addEntity(Parent.class);
//                int start = PageData.calcFirstItemIndexOfPage(page, pageSize, (int) count);
//                query.setFirstResult(start);
//                query.setMaxResults(pageSize);
//                List<Parent> list = query.list();
//                PageData pageData = new PageData(page, (int) count, pageSize, list);
//                return pageData;
//            }
//        });
    }

    /**
     * 获取当前数据库系统编号的最大值
     *
     * @return
     */
    @Override
    public String getMaxParentCode() {
        String querySql = " SELECT  max (s.parent_code) FROM user_parent as s";
        List<String> strings = JdbcTemplateUtil.getJdbcTemplate().findValue(querySql);
        return strings.get(0);
//        String reValue = null;
//        reValue = getHibernateTemplate().execute(new HibernateCallback<String>() {
//            @Override
//            public String doInHibernate(Session session) throws HibernateException {
//                String querySql = " SELECT  max (s.parent_code) FROM Parent as s";
//                Query query = session.createQuery(querySql);
//                String str = (String) query.uniqueResult();
//                return str;
//            }
//        });
//        return reValue;
    }
}
