package com.honghe.user.dao.impl;

import com.honghe.dao.PageData;
import com.honghe.user.dao.BaseUserDao;
import com.honghe.user.dao.IStudentDao;
import com.honghe.user.dao.entity.*;
import com.honghe.user.util.JdbcTemplateUtil;
import com.honghe.user.util.StringUtil;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * 学生的操作类
 *
 * @author lyx
 * @create 2017-01-07 15:22
 **/
public class StudentUserDao extends BaseUserDao implements IStudentDao {
    Logger logger = Logger.getLogger(StudentUserDao.class);
    private final int ERROR_ADD_UPDATE = -1; //添加或者修改失败错误返回码
    private final int SUCCESS_UPDATE = 0; //修改成功

    /**
     * 新增学生
     *
     * @param student
     * @return
     */
    @Override
    public int add(Student student) {
        int reValue = ERROR_ADD_UPDATE;
        String saveSql = "INSERT INTO user_student ( " +
                "	student_code, " +
                "	student_num, " +
                "	namepy, " +
                "	realname, " +
                "	student_path, " +
                "	gender, " +
                "	readtype, " +
                "	nation, " +
                "	status, " +
                "	enter_year, " +
                "	mobile, " +
                "	email, " +
                "	enter_way, " +
                "	address, " +
                "	birthday " +
                ") " +
                "VALUES " +
                "	( " +
                "		'" + student.getStudent_code() + "', " +
                "		'" + student.getStudent_num() + "', " +
                "		'" + student.getNamepy() + "', " +
                "		'" + student.getRealName() + "', " +
                "		'" + student.getStudent_path() + "', " +
                "		'" + student.getGender() + "', " +
                "		'" + student.getReadtype() + "', " +
                "		'" + student.getNation() + "', " +
                "		'" + student.getStatus() + "', " +
                "		'" + student.getEnter_year() + "', " +
                "		'" + student.getMobile() + "', " +
                "		'" + student.getEmail() + "', " +
                "		'" + student.getEnter_way() + "', " +
                "		'" + student.getAddress() + "', " +
                "		'" + student.getBirthday() + "' " +
                "	)";

        boolean execute = JdbcTemplateUtil.getJdbcTemplate().execute(saveSql);
        if (execute) {
            reValue = SUCCESS_UPDATE;
        }
        return reValue;
    }

    /**
     * 删除学生
     *
     * @param studentCode
     * @return
     */
    @Override
    public boolean delete(String studentCode) {
        String deleteSql = "DELETE " +
                "FROM " +
                "	user_student " +
                "WHERE " +
                "	student_code = '" + studentCode + "'";
        boolean reValue = JdbcTemplateUtil.getJdbcTemplate().execute(deleteSql);
        if (!reValue) {
            logger.error("删除学生失败！");
        }
        return reValue;
//        try {
//            Student student = getStudentByCode(studentCode);
//            this.getHibernateTemplate().delete(student);
//            List<PcRelation> pcRelations = getRelationByStu(studentCode);
//            if(null != pcRelations){
//                for (PcRelation item : pcRelations) {
//                    this.getHibernateTemplate().delete(item);
//                }
//            }
//            reValue = true;
//        } catch (DataAccessException e) {
//            logger.error("删除学生对象失败");
//        }
    }

    /**
     * 批量删除学生
     *
     * @param ids 学生id，多个用逗号隔开
     * @return
     */
    @Override
    public boolean deleteStudents(final String ids) {
        boolean result = false;
        String countSql = "select DISTINCT u.user_id from user_student s left join user u on u.user_code = " +
                "s.student_code where s.id in(" + ids + ")";
        //获取查询列表userid
        List<String> userIdList = JdbcTemplateUtil.getJdbcTemplate().findValue(countSql);
        String deleteUserStudentSql = "";
        String deleteUserSql = null;
        int query = 0;
        String uIds = "";
        if (userIdList.size() > 0) {
            for (String uId : userIdList) {
                uIds += uId + ",";
            }
            if (!"".equals(uIds) && uIds.endsWith(",")) {
                uIds = uIds.substring(0, uIds.lastIndexOf(","));
            }
            deleteUserStudentSql = "delete from user_student where id in(" + ids + ")";
            deleteUserSql = "delete from user where user_id in(" + uIds + ")";
            result = JdbcTemplateUtil.getJdbcTemplate().execute(deleteUserStudentSql, deleteUserSql);
        }
        if (!result) {
            logger.error("批量删除学生异常！sql:" + deleteUserStudentSql + ";" + deleteUserSql);
        }
        return result;
        //<editor-fold desc="oldcode">
        //        final Integer value = getHibernateTemplate().execute(new HibernateCallback<Integer>() {
//            @Override
//            public Integer doInHibernate(Session session) throws HibernateException {
//                String count_sql = "select DISTINCT u.user_id from user_student s left join user u on u.user_code = s.student_code where s.id in("+ids+")" ;
//                List<Integer> userIdList = session.createSQLQuery(count_sql).list();//获取查询列表userid
//                String sql = "";
//                int query = 0;
//                String uIds = "";
//                if (userIdList.size()>0){
//                    for (int uId:userIdList){
//                        uIds += uId+",";
//                    }
//                    if(!"".equals(uIds)&&uIds.endsWith(",")){
//                        uIds = uIds.substring(0,uIds.lastIndexOf(","));
//                    }
//                    sql = "delete from user_student where id in("+ids+")";
//                    query = session.createSQLQuery(sql).executeUpdate();
//                }
//                if (query>0){
//                    String userSql = "delete from user where user_id in("+uIds+")";
//                    session.createSQLQuery(userSql).executeUpdate();
//                }
//                return query;
//            }
//        });
//        if (value>0) {
//            re_value = true;
//        }
//        return re_value;
        //</editor-fold>
    }
//    /**
//     * 获取所有的学生对象
//     *
//     * @return
//     */
//    @Override
//    public List<Object[]> getStudents() {
//        List<Object[]> re_value = (List<Object[]>) this.getHibernateTemplate().find(" from Student");
//        return re_value;
//
//    }

    /**
     * 分页获取学生信息
     *
     * @param page        当前页
     * @param pageSize    每页的条目
     * @param studentName 关键词
     * @param studentCode 内部流水
     * @return
     */
    @Override
    public PageData<Student> getStudentsByPage(final int page, final int pageSize, final String studentName, final String studentCode, final String campusId) {
        String countSql = "select  count(DISTINCT user.user_id) from user_student left join user on user.user_code = " +
                "user_student.student_code left join ad_campus2user on " +
                "ad_campus2user.user_id = user.user_id";
        String conjuntion = " where ";
        if (!StringUtil.isEmpty(studentName)) {
            countSql += " where realname like " + "'%" + studentName + "%'";
            conjuntion = " and ";
        } else if (!StringUtil.isEmpty(studentCode)) {
            countSql += " where student_code = " + "'" + studentCode + "'";
            conjuntion = " and ";
        }
        if (!StringUtil.isEmpty(campusId)) {
            countSql += conjuntion + "ad_campus2user.campus_id = '" + campusId + "'";
        }
        //获取查询列表的总数量
        List<String> value = JdbcTemplateUtil.getJdbcTemplate().findValue(countSql);
        long count = 0;
        if (!value.isEmpty()) {
            count = Long.parseLong(value.get(0));
        }
        //查询学生表
        String sql = countSql.replace("count(DISTINCT user.user_id)", "distinct user_student.*");
        sql += " order by user_student.id desc";
        PageData pageData = JdbcTemplateUtil.getJdbcTemplate().findByPage(page, pageSize, countSql, sql);
        return pageData;

        //<editor-fold desc="oldcode">
        //        PageData pageData = getHibernateTemplate().execute(new HibernateCallback<PageData>() {
//            @Override
//            public PageData doInHibernate(Session session) throws HibernateException {
//                String count_sql = "select  count(DISTINCT user.user_id) from user_student left join user on user.user_code = user_student.student_code left join ad_campus2user on " +
//                        "ad_campus2user.user_id = user.user_id";
//                String conjuntion = " where ";
//                if (!StringUtil.isEmpty(studentName)) {
//                    count_sql += " where realname like " + "'%" + studentName + "%'";
//                    conjuntion = " and ";
//                } else if (!StringUtil.isEmpty(studentCode)) {
//                    count_sql += " where student_code = " + "'" + studentCode + "'";
//                    conjuntion = " and ";
//                }
//                if(!StringUtil.isEmpty(campusId)){
//                    count_sql +=conjuntion+"ad_campus2user.campus_id = '"+campusId+"'";
//                }
//                long count = Long.parseLong(session.createSQLQuery(count_sql).list().get(0).toString());//获取查询列表的总数量
//                String sql = count_sql.replace("count(DISTINCT user.user_id)", "distinct user_student.*");//查询学生表
//                sql += " order by user_student.id desc";
//                Query query = session.createSQLQuery(sql).addEntity(Student.class);
//                int start = PageData.calcFirstItemIndexOfPage(page, pageSize, (int) count);
//                query.setFirstResult(start);
//                query.setMaxResults(pageSize);
//                List<Student> list = query.list();
//                PageData pageData = new PageData(page, (int) count, pageSize, list);
//                return pageData;
//            }
//        });
//        return pageData;
        //</editor-fold>
    }

    /**
     * 根据条件查询学生信息
     *
     * @param studentKey 学生姓名或者学籍号
     * @return
     * @throws Exception
     */
    @Override
    public List<Student> studentSearchByCondition(String studentKey) {
        List<Student> list = new ArrayList<>();
        try {
            String sql = "";
            if (!"".equals(studentKey)) {
                sql = "select * from user_student where realName " +
                        "like " + "'%" + studentKey + "%'" + " or student_num like " + "'%" + studentKey + "%'";
            } else {
                sql = "select * from user_student";
            }
            List<Map<String, String>> stuList = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
            if (!stuList.isEmpty()) {
                for (Map<String, String> map : stuList) {
                    String studentCode = map.get("student_code") == null ? "" : map.get("student_code").toString().trim();
                    List<Map<String, String>> userList = getUserListByUserCode(studentCode);
                    Student student = createStudent(map);
                    if (!userList.isEmpty()) {
                        assembleStudentWithUser(studentCode, student);
                    }
                    list.add(student);

                }
            }
        } catch (Exception e) {
            logger.error("查询学生列表失败");
        }
        return list;
    }

    /**
     * 对parent进行组装操作，将user放置进去
     *
     * @param code
     * @param student
     */
    private void assembleStudentWithUser(String code, Student student) {
        List<Map<String, String>> userList = getUserListByUserCode(code);
        if (!userList.isEmpty()) {
            String userId = userList.get(0).get("user_id");
            User user = createUser(userList.get(0));

            String campusSql = "SELECT " +
                    "	a.* " +
                    "FROM " +
                    "	ad_campus a " +
                    "JOIN ad_campus2user b ON a.id = b.campus_id " +
                    "WHERE " +
                    "	b.campus_id = '" + userId + "'";
            List<Map<String, String>> campustList = JdbcTemplateUtil.getJdbcTemplate().findList(campusSql);
            if (!campustList.isEmpty()) {

                Set<Campus> campusSet = new HashSet<>();
                for (Map<String, String> map : campustList) {
                    Campus campus = createCampus(map);
                    campusSet.add(campus);
                }
                user.setCampuses(campusSet);

            }
            student.setUser(user);
        }
    }

    private Student createStudent(Map<String, String> map) {
        Student student = new Student();
        student.setAddress(map.get("address") == null ? "" : map.get("address").trim());
        student.setBirthday(map.get("birthday") == null ? "" : map.get("birthday").trim());
        student.setEmail(map.get("email") == null ? "" : map.get("email").trim());
        student.setEnter_way(map.get("enter_way") == null ? "" : map.get("enter_way").trim());
        student.setEnter_year(map.get("enter_year") == null ? "" : map.get("enter_year").trim());
        student.setGender(map.get("gender") == null ? "" : map.get("gender").trim());
        student.setId(Integer.parseInt(map.get("id") == null ? "" : map.get("id").trim()));
        student.setMobile(map.get("mobile") == null ? "" : map.get("mobile").trim());
        student.setNation(map.get("nation") == null ? "" : map.get("nation").trim());
        student.setReadtype(map.get("readtype") == null ? "" : map.get("readtype").trim());
        student.setRealName(map.get("realName") == null ? "" : map.get("realName").trim());
        student.setStatus(map.get("status") == null ? "" : map.get("status").trim());
        student.setStudent_code(map.get("student_code") == null ? "" : map.get("student_code").trim());
        student.setStudent_num(map.get("student_num") == null ? "" : map.get("student_num").trim());
        student.setStudent_path(map.get("student_path") == null ? "" : map.get("student_path").trim());
        student.setNamepy(map.get("namepy") == null ? "" : map.get("namepy").trim());
        return student;
    }

    /**
     * 获取当前数据库系统编号的最大值
     *
     * @return
     */
    @Override
    public String getMaxStudentCode() {
        String sql = "SELECT  max(s.student_code) FROM user_student";
        List<String> value = JdbcTemplateUtil.getJdbcTemplate().findValue(sql);
        if (!value.isEmpty()) {
            return value.get(0).toString();
        } else {
            return "";
        }

        //<editor-fold desc="oldcode">
        //        String reValue = null;
//
//        reValue = getHibernateTemplate().execute(new HibernateCallback<String>() {
//            @Override
//            public String doInHibernate(Session session) throws HibernateException {
//                String querySql = " SELECT  max (s.student_code) FROM Student as s";
//                Query query = session.createQuery(querySql);
//                String str = (String) query.uniqueResult();
//                return str;
//            }
//        });
        //</editor-fold>

//        return reValue;
    }

    /**
     * 依据系统编号返回对象
     *
     * @param code 系统编号
     * @return
     */
    @Override
    public Student getStudentByCode(final String code) {
        String sql = "SELECT * FROM user_student WHERE student_code= '" + code + "'";
        List<Map<String, String>> list = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        Student student = null;
        if (!list.isEmpty()) {
            student = createStudent(list.get(0));
        }
        return student;

        //<editor-fold desc="oldcode">
        //        Student re_value = new Student();
//        re_value = this.getHibernateTemplate().execute(new HibernateCallback<Student>() {
//            @Override
//            public Student doInHibernate(Session session) throws HibernateException {
//                String sql = "FROM Student WHERE student_code= '" + code + "'";
//                Query query = session.createQuery(sql);
//                List<Student> list = query.list();
//                return list == null ? null : list.get(0);
//            }
//        });
//        return re_value;
        //</editor-fold>
    }

    /**
     * 根据一个或多个学生内部流水找到学生集合
     *
     * @param codes
     * @return
     */
    @Override
    public Set<Student> getStudentsByIds(final String codes) {
        Set<Student> studentSet = new LinkedHashSet<>();
        String sqlCodes = StringUtil.getSqlString(codes);
        String sql = "FROM  user_student AS T WHERE T.student_code in(" + sqlCodes + ")";
        List<Map<String, String>> studentList = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        if (!studentList.isEmpty()) {
            for (Map<String, String> map : studentList) {
                Student student = createStudent(map);
                studentSet.add(student);
            }
        }
        return studentSet;

        //<editor-fold desc="oldcode">
        //        Set<Student> typeSet = new LinkedHashSet<>();
//        final String sql_codes = StringUtil.getSqlString(codes);
//        List<Student> list = this.getHibernateTemplate().execute(new HibernateCallback<List<Student>>() {
//            @Override
//            public List<Student> doInHibernate(Session session) throws HibernateException {
//                String sql = "FROM  Student AS T WHERE T.student_code in(" + sql_codes + ")";
//                Query query = session.createQuery(sql);
//                List<Student> list = (List<Student>) query.list();
//                return list;
//            }
//        });
//        typeSet.addAll(list);
//        return typeSet;
        //</editor-fold>
    }

    /**
     * 通过学生学籍号得到流水
     *
     * @param studentNums
     * @return
     */
    @Override
    public String getCodesByNums(final String studentNums) {
        String sql = "SELECT student_code FROM  user_student AS T WHERE T.student_num in(' + sql_name + ')";
        List<String> value = JdbcTemplateUtil.getJdbcTemplate().findValue(sql);
        String s = StringUtil.stringListToString(value, ",");
        return s;
//        String reValue = "";
//        final String sql_name = StringUtil.getSqlString(studentNums);
//        reValue = this.getHibernateTemplate().execute(new HibernateCallback<String>() {
//            @Override
//            public String doInHibernate(Session session) throws HibernateException {
//                String sql = " SELECT student_code FROM  Student AS T WHERE T.student_num in(" + sql_name + ")";
//                Query query = session.createQuery(sql);
//                List<Object> list = (List<Object>) query.list();
//                return StringUtil.listToString(list, ",");
//            }
//        });
//        return reValue;
    }

//    /**
//     * 修改学生
//     *
//     * @param student
//     * @return
//     */
//    @Override
//    public int update(Student student) {
//        try {
//            this.getHibernateTemplate().merge(student);
//        } catch (DataAccessException e) {
//            logger.error("修改学生对象失败");
//            return ERROR_ADD_UPDATE;
//        }
//        return SUCCESS_UPDATE;
//    }

    /**
     * 根据学生姓名获取学生信息列表
     *
     * @param name 学生姓名
     * @return
     */
    @Override
    public List<Map<String, String>> getStudentsByName(String name) {
        String sql = "select * from user_student s";
        if (!"".equals(name) && name != null) {
            sql += " where s.realName like '%" + name + "%'";
        }
        List<Map<String, String>> list = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        return list;
        //<editor-fold desc="oldcode">
        //        List<Map<String, String>> list = new ArrayList();
//        String hql = "from Student s";
//        if (!"".equals(name) && name != null) {
//            hql += " where s.realName like '%" + name + "%'";
//        }
//        list = (List<Map<String, String>>) this.getHibernateTemplate().find(hql);
//        return list;
        //</editor-fold>
    }


//    /**
//     * 根据学生code获取家长与学生的关系记录
//     *
//     * @param studentCode 学生code
//     * @return
//     */
//    @Override
//    public List<PcRelation> getRelationByStu(String studentCode) {
//        List<PcRelation> list = null;
//        try {
//            String hql = " from PcRelation where student_code = '" + studentCode + "'";
//            list = (List<PcRelation>) this.getHibernateTemplate().find(hql);
//        } catch (DataAccessException e) {
//            logger.error("查询学生列表失败");
//        }
//        return list;
//    }
}
