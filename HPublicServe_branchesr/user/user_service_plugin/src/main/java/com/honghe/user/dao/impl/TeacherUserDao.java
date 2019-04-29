package com.honghe.user.dao.impl;

import com.honghe.dao.JdbcTemplate;
import com.honghe.dao.PageData;
import com.honghe.user.dao.BaseUserDao;
import com.honghe.user.dao.ITeacherDao;
import com.honghe.user.dao.entity.*;
import com.honghe.user.util.JdbcTemplateUtil;
import com.honghe.user.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by sky on 2017/1/9.
 */
public class TeacherUserDao extends BaseUserDao implements ITeacherDao {

    Logger logger = Logger.getLogger(TeacherUserDao.class);
    public final static String ISADMIN_Y = "1";//表示该用户为管理员
    public final static String ISADMIN_N = "0";//表示该用户不是管理员
    private final static String SEASON_ID = "1";//默认学科season_id=1

    /**
     * 新增教师对象
     *
     * @param teacher
     * @return int
     */
    @Override
    public synchronized int add(Teacher teacher) {
        //保存teacher
        String saveTeacherSql = createSaveTeacherSql(teacher);
        String[] saveTeacherSqls = new String[1];
        saveTeacherSqls[0] = saveTeacherSql;
        //保存user
        User user = teacher.getUser();
        String saveUserSql = getSaveUserSql(user);
        String[] saveUserSqls = new String[1];
        saveUserSqls[0] = saveUserSql;
        //保存user和campus关系
        String[] saveCampus2UserSqls = getSaveCampus2UserSqls(user.getCampuses(), String.valueOf(user.getUser_id()));
        //保存teacher的负责subject
        Set<Subject> subject = teacher.getSubject();
        String[] saveSubjectSqls = getSaveSubjectSqls(subject, teacher.getTeacher_code());
        //保存teacher的职务
        Set<TeacherDutyType> teacherDutyTypes = teacher.getTeacherDutyTypes();
        String[] saveTeacherDutySqls = getSaveteacher2SubjectSqls(teacherDutyTypes, teacher.getTeacher_code());
        //最终的sql数组
        //为了能够完整的回滚【添加教师】这个动作，必须将所有sql集中起来一起执行
        String[] finalSqls = StringUtil.mergedStringArr(saveTeacherSqls, saveUserSqls, saveCampus2UserSqls, saveSubjectSqls, saveTeacherDutySqls);

        JdbcTemplate jdbcTemplate = JdbcTemplateUtil.getJdbcTemplate();
        String maxIdQuery = "select max(user_id) from user";
        boolean executeResults = jdbcTemplate.execute(finalSqls);
        List<String> value = jdbcTemplate.findValue(maxIdQuery);
        int maxId = value.get(0) == null ? 0 : Integer.parseInt(value.get(0));
        user.setUser_id(maxId);
        teacher.setUser(user);

        if (executeResults) {
            return 1;
        } else {
            return 0;
        }

        //<editor-fold desc="oldcode">
        //        int re_value = -1;
//        try {
//            Serializable pKey = this.getHibernateTemplate().save(teacher);
//            re_value = Integer.parseInt(pKey.toString());
//            teacher.setId(re_value);
//        } catch (DataAccessException e) {
//            logger.error("插入教师对象失败", e);
//        }
//        return re_value;
        //</editor-fold>
    }

    private String createSaveTeacherSql(Teacher teacher) {
        StringBuilder sql = new StringBuilder("INSERT INTO user_teacher ( " +
                " teacher_code, " +
                " teacher_name, " +
                " employeeno, " +
                " namepy, " +
                " teacher_path, " +
                " gender, " +
                " mobile, " +
                " email, " +
                " qq, " +
                " idcode, " +
                " birthday, " +
                " short_num, " +
                " nation, " +
                " political, " +
                " address, " +
                " work_date, " +
                " teach_date, " +
                " is_job, " +
                " professional_title, " +
                " stage_id " +
                ") " +
                "VALUES(")
                .append("'").append(teacher.getTeacher_code().trim()).append("',")
                .append(teacher.getTeacher_name() == null ? null + "," : "'" + teacher.getTeacher_name() + "',")
                .append(teacher.getEmployeeno() == null ? null + "," : "'" + teacher.getEmployeeno() + "',")
                .append(teacher.getNamepy() == null ? null + "," : "'" + teacher.getNamepy() + "',")
                .append(teacher.getTeacher_path() == null ? null + "," : "'" + teacher.getTeacher_path() + "',")
                .append(teacher.getGender() == null ? null + "," : "'" + teacher.getGender() + "',")
                .append(teacher.getMobile() == null ? null + "," : "'" + teacher.getMobile() + "',")
                .append(teacher.getEmail() == null ? null + "," : "'" + teacher.getEmail() + "',")
                .append(teacher.getQq() == null ? null + "," : "'" + teacher.getQq() + "',")
                .append(teacher.getIdcode() == null ? null + "," : "'" + teacher.getIdcode() + "',")
                .append(teacher.getBirthday() == null ? null + "," : "'" + teacher.getBirthday() + "',")
                .append(teacher.getShort_num() == null ? null + "," : "'" + teacher.getShort_num() + "',")
                .append(teacher.getNation() == null ? null + "," : "'" + teacher.getNation() + "',")
                .append(teacher.getPolitical() == null ? null + "," : "'" + teacher.getPolitical() + "',")
                .append(teacher.getAddress() == null ? null + "," : "'" + teacher.getAddress() + "',")
                .append(teacher.getWork_date() == null ? null + "," : "'" + teacher.getWork_date() + "',")
                .append(teacher.getTeach_date() == null ? null + "," : "'" + teacher.getTeach_date() + "',")
                .append(teacher.getIs_job() == null ? null + "," : "'" + teacher.getIs_job() + "',")
                .append(teacher.getProfessional_title() == null ? "1" + "," : "'" + teacher.getProfessional_title() + "',")
                .append("'").append(teacher.getStage_id().trim()).append("'").append(");");
        return sql.toString();
    }

    private String createSaveSubject2UserSql(String teacherCode, String typeId) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO `user_teacher2type` ( " +
                "	`teacher_code`, " +
                "	`type_id` " +
                ") " +
                "VALUES " +
                "	(")
                .append("'").append(teacherCode).append("',")
                .append("'").append(typeId).append("');");
        return sb.toString();
    }

    private String[] getSaveteacher2SubjectSqls(Set<TeacherDutyType> teacherDutyTypes, String teacherCode) {
        String[] sqls = new String[teacherDutyTypes.size()];
        Iterator<TeacherDutyType> iterator = teacherDutyTypes.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            TeacherDutyType next = iterator.next();
            sqls[count] = createSaveSubject2UserSql(teacherCode, String.valueOf(next.getId()));
            count++;
        }
        return sqls;
    }

    private String[] getSaveSubjectSqls(Set<Subject> subject, String teacherCode) {
        String[] sqls = new String[subject.size()];
        Iterator<Subject> iterator = subject.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            Subject next = iterator.next();
            sqls[count] = createSaveSubjectSql(teacherCode, String.valueOf(next.getId()));
            count++;
        }
        return sqls;
    }

    private String[] getSaveCampus2UserSqls(Set<Campus> campuses, String userId) {
        String[] sqls = new String[campuses.size()];
        Iterator<Campus> iterator = campuses.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            Campus next = iterator.next();
            sqls[count] = createSaveCampus2UserSql(next.getId(), userId);
            count++;
        }
        return sqls;
    }


    private String createSaveCampus2UserSql(String campusId, String userId) {
        StringBuilder sb = new StringBuilder();
        sb.append(" INSERT INTO `ad_campus2user` (" +
                "`campus_id`, " +
                "`user_id`) + " +
                " VALUES  + " +
                " (")
                .append("'").append(campusId)
                .append("','").append(userId).append("'").append(");");
        return sb.toString();

    }

    private String createSaveSubjectSql(String teacherCode, String subjectId) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO user_teacher2subject ( " +
                " teacher_code, " +
                " subject_id " +
                ") " +
                "VALUES(")
                .append("'").append(teacherCode).append("',")
                .append("'").append(subjectId).append("');");
        return sb.toString();

    }

    @Override
    public boolean updateTeacher(Teacher teacher) {
        String updateTeacherSql = createUpdateTeacherSql(teacher);
        String updateUserSql = createUpdateUserSql(teacher.getUser());
        boolean execute = true;
        try {
            JdbcTemplateUtil.getJdbcTemplate().execute(updateTeacherSql, updateUserSql);
            //不修改数据库内容更新，数据库修改行数返回0，导致页面一直报修改失败，因此直接返回true，异常时返回false
        } catch (Exception e) {
            logger.error("更新教师失败！！sql:" + updateTeacherSql + ";" + updateUserSql);
            execute = false;
        }
        if (!execute) {
            logger.debug("更新教师失败！！sql:" + updateTeacherSql + ";" + updateUserSql);
        }
        return execute;
    }

    private String createUpdateUserSql(User user) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE user SET ")
                .append(user.getUser_regdate() == null ? "" : "user_regdate = '" + user.getUser_regdate()+"',")
                .append(user.getUser_syn_cloud() == null ? "user_syn_cloud =0," : " user_syn_cloud = '" + user.getUser_syn_cloud()+"',")
                .append(user.getUser_status() == null ? "user_status = 0," : " user_status = '" + user.getUser_status()+"',")
                .append(user.getUser_salt() == null ? "" : " user_salt = '" + user.getUser_salt()+"',")
                .append(user.getUser_realname() == null ? "" : " user_realname = '" + user.getUser_realname()+"',")
                .append(user.getUser_pwd() == null ? "" : " user_pwd = '" + user.getUser_pwd()+"',")
                .append(user.getUser_path() == null ? "" : " user_path = '" + user.getUser_path()+"',")
                .append(user.getUser_isAdmin() == null ? null : " user_isAdmin = '" + user.getUser_isAdmin()+"',")
                .append(user.getUser_hres() == null ? "" : " user_hres = '" + user.getUser_hres()+"',")
                .append(user.getUser_gender() == null ? " user_gender = 1" : " user_gender = '" + user.getUser_gender()+"',")
                .append(user.getUser_code() == null ? "" : " user_code = '" + user.getUser_code()+"',")
                .append(user.getUser_birthday() == null ? "" : " user_birthday = '" + user.getUser_birthday()+"',")
                .append(user.getUser_avatar() == null ? "" : " user_avatar = '" + user.getUser_avatar()+"',")
                .append(user.getUser_address() == null ? "" : " user_address = '" + user.getUser_address()+"',")
                .append(user.getUser_type() == null ? " user_type = 17,"  : " user_type = '" + user.getUser_type()+"',")
                .append(user.getUser_num() == null ? "" : " user_num = '" + user.getUser_num()+"',")
                .append(user.getUser_name() == null ? "" : " user_name = '" + user.getUser_name()+"',")
                .append(user.getUser_mobile() == null ? "" : " user_mobile = '" + user.getUser_mobile()+"',")
                .append(user.getUser_email() == null ? "" : " user_email = '" + user.getUser_email()+"'");
        String sql=sb.toString();
        if(sql!=null && !"".equals(sql) && sql.endsWith(",")){
            sql=sql.substring(0,sql.lastIndexOf(","));
        }
        sql+=" WHERE user_id = '" + user.getUser_id() + "'";
        return sql;
    }

    private String createUpdateTeacherSql(Teacher teacher) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE user_teacher SET ")
                .append(teacher.getTeacher_code() == null ? "" : "teacher_code = '" + teacher.getTeacher_code()+"',")
                .append(teacher.getTeacher_name() == null ? "" : " teacher_name = '" + teacher.getTeacher_name()+"',")
                .append(teacher.getEmployeeno() == null ? "" : " employeeno = '" + teacher.getEmployeeno()+"',")
                .append(teacher.getNamepy() == null ? "" : " namepy = '" + teacher.getNamepy()+"',")
                .append(teacher.getTeacher_path() == null ? "" : " teacher_path = '" + teacher.getTeacher_path()+"',")
                .append(teacher.getGender() == null ? " gender = 0," : " gender = '" + teacher.getGender()+"',")
                .append(teacher.getMobile() == null ? "" : " mobile = '" + teacher.getMobile()+"',")
                .append(teacher.getEmail() == null ? "" : " email = '" + teacher.getEmail()+"',")
                .append(teacher.getQq() == null ? "" : " qq = '" + teacher.getQq()+"',")
                .append(teacher.getIdcode() == null ? "" : " idcode = '" + teacher.getIdcode()+"',")
                .append(teacher.getBirthday() == null ? "" : " birthday = '" + teacher.getBirthday()+"',")
                .append(teacher.getShort_num() == null ? "" : " short_num = '" + teacher.getShort_num()+"',")
                .append(teacher.getNation() == null ? "" : " nation = '" + teacher.getNation()+"',")
                .append(teacher.getPolitical() == null ? "" : " political = '" + teacher.getPolitical()+"',")
                .append(teacher.getAddress() == null ? "" : " address = '" + teacher.getAddress()+"',")
                .append(teacher.getWork_date() == null ? "" : " work_date = '" + teacher.getWork_date()+"',")
                .append(teacher.getTeach_date() == null ? "" : " teach_date = '" + teacher.getTeach_date()+"',")
                .append(teacher.getIs_job() == null ? "is_job = 1," : " is_job = '" + teacher.getIs_job()+"',")
                .append((teacher.getProfessional_title() == null || "".equals(teacher.getProfessional_title()))? " professional_title = 1," : " professional_title = '" + teacher.getProfessional_title()+"',")
                .append(teacher.getStage_id() == null ? "stage_id=5" : " stage_id = '" + teacher.getStage_id()+"'");
//                .append("WHERE (user_id = '" + teacher.getId() + "')");
            String sql=sb.toString();
            if(sql!=null && !"".equals(sql) && sql.endsWith(",")){
                sql=sql.substring(0,sql.lastIndexOf(","));
            }
            sql+=" WHERE id = '" + teacher.getId() + "'";
            return sql;
    }

    @Override
    public boolean updateTeacherByUserInfo(String sql) {
        if(sql!=null && !"".equals(sql)){
            return JdbcTemplateUtil.getJdbcTemplate().update(sql);
        }
        return false;
    }
    @Override
    public Teacher findTeacherByCode(final String teacherCode) {
        String sql = "SELECT * FROM user_teacher WHERE teacher_code = '" + teacherCode + "'";
        List<Map<String, String>> list1 = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        Teacher teacher = null;
        if (!list1.isEmpty()) {
            teacher = createTeacher(list1.get(0));
        }
        return teacher;
        //<editor-fold desc="oldcode">
        //        Teacher teacher = new Teacher();
//        teacher = this.getHibernateTemplate().execute(new HibernateCallback<Teacher>() {
//            @Override
//            public Teacher doInHibernate(Session session) throws HibernateException {
//                String sql = "FROM Teacher WHERE teacher_code = '" + teacherCode + "'";
//                Query query = session.createQuery(sql);
//                List<Teacher> list = query.list();
//                return list == null ? null : list.get(0);
//            }
//        });
//        return teacher;
        //</editor-fold>
    }

    /**
     * 获取教师对象(支持导出)
     *
     * @param teacherName 教师姓名
     * @return List
     */
    @Override
    public List<Map<String, String>> findTeachers(String teacherName) {
        String sql = "select * from user_teacher t ";
        if (!"".equals(teacherName) && teacherName != null) {
            sql += "where t.teacher_name like '%" + teacherName + "%'";
        }
        List<Map<String, String>> list = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
//        List<Map<String, String>> finalList = new ArrayList<>();
        if (!list.isEmpty()) {
            for (Map<String, String> map : list) {
                String userQuerySql = "select * from user where user_code = '" + map.get("teacher_code") + "'";
                List<Map<String, String>> userList = JdbcTemplateUtil.getJdbcTemplate().findList(userQuerySql);
                if (!userList.isEmpty()) {
                    Map<String, String> userMap = userList.get(0);
                    String userId = userMap.get("user_id");
                    String campusSql = "SELECT " +
                            " c.* " +
                            "FROM " +
                            " ad_campus2user a " +
                            "JOIN USER b ON a.user_id = b.user_id " +
                            "JOIN ad_campus c ON a.campus_id = c.id " +
                            "WHERE " +
                            " a.user_id = '" + userId + "'";
                    List<Map<String, String>> campusList = JdbcTemplateUtil.getJdbcTemplate().findList(campusSql);
//                    Set<String> campusSet = new HashSet(Arrays.asList(campusList.isEmpty() ? new ArrayList() : campusList));
                    userMap.put("campuses", JSONArray.fromObject(campusList).toString());
//                    userMap.put("teacherDutyTypes",JSONArray.fromObject(getDutySet(userMap.get("user_type"))).toString());
                    map.put("user", JSONObject.fromObject(userMap).toString());
                    map.put("teacherDutyTypes", JSONArray.fromObject(getDutySet(userMap.get("user_type"))).toString());
                    map.put("subject", JSONArray.fromObject(queryAndCreateSubject(userMap.get("user_code"))).toString());
//                    finalList.add(map);
                }
            }
        }
        return list;

        //<editor-fold desc="oldcode">
        //        List<Map<String, String>> list = new ArrayList();
//        String hql = "from Teacher t ";
//        if (!"".equals(teacherName) && teacherName != null) {
//            hql += "where t.teacher_name like '%" + teacherName + "%'";
//        }
//        list = (List<Map<String, String>>) this.getHibernateTemplate().find(hql);
//        return list;
        //</editor-fold>
    }

    /**
     * 分页获取教师对象
     *
     * @param pageSize 每页的条目
     * @param page     当前页
     * @return
     */
    @Override
    public PageData<Teacher> getTeacherByPage(final int page, final int pageSize, final String teacherName,
                                              final String teacherCode, final String campusId, final String flag) {
        String countSql = "select count(DISTINCT user.user_id) from user_teacher t left join user on user.user_code = t.teacher_code" +
                " left join ad_campus2user on User.user_id = ad_campus2user.user_id";
        String conditions = " where ";
        if (teacherName != null && !"".equals(teacherName)) {
            countSql += " where t.teacher_name like '%" + teacherName + "%'";
            conditions = " and ";
        } else if (teacherCode != null && !"".equals(teacherCode)) {
            countSql += " where t.teacher_code like '%" + teacherCode + "%'";
            conditions = " and ";
        }
        if ("ad".equals(flag)) {
            countSql += conditions + "ad_campus2user.campus_id='" + campusId + "'";
        }
        countSql += " order by t.id desc";
        //获取查询列表的总数量
        List<String> value = JdbcTemplateUtil.getJdbcTemplate().findValue(countSql);
        long count = 0;
        if (!value.isEmpty()) {
            count = Long.parseLong(value.get(0));
        }
        //查询教师表
        String sql = countSql.replace("count(DISTINCT user.user_id)", "DISTINCT t.*") + " limit ?,?";

        PageData pageData = JdbcTemplateUtil.getJdbcTemplate().findByPage(page, pageSize, countSql, sql);
        List<Map<String, String>> list = pageData.getItems();

//        if (!list.isEmpty()) {
//
//            //查询user表，将数据拼接到teacher类中,这是在改造hibernate前的类结构，teacher类包含user类，我也不知道为啥
//            String userQuerySql = "select * from user where user_code = '" + list.get(0).get("teacher_code") + "'";
//            List<Map<String, String>> users = JdbcTemplateUtil.getJdbcTemplate().findList(userQuerySql);
//            HashMap<String, String> userMap = new HashMap<>();
//            userMap.put("user", users.isEmpty() ? "" : users.get(0).toString());
//            list.add(userMap);
//        }

        if ("us".equals(flag)) {
            String[] campusIdStr = campusId.split(",");
            String str = "";
            for (int i = 0; i < campusIdStr.length; i++) {
                str += "'" + campusIdStr[i] + "',";
            }
            if (!"".equals(str) && str.endsWith(",")) {
                str = str.substring(0, str.lastIndexOf(","));
            }
            //查询选定机构下分配的管理员
            List<Map<String, String>> userList = JdbcTemplateUtil.getJdbcTemplate().findList(
                    "select distinct user_id from ad_campus2admin where campus_id in(" + str + ")");
            List userIdList = new ArrayList();
            if (userList != null && userList.size() > 0) {
                //将管理员的id放入list集合中
                for (Map userId : userList) {
                    int id = Integer.valueOf(userId.get("user_id").toString());
                    userIdList.add(id);
                }
            }
            if (!list.isEmpty()) {
                //判断用户如果不属于选定机构的管理员，则返回给界面值时取消该用户的管理员身份
                for (Map<String, String> map : list) {
                    Teacher teacher = createTeacher(map);
                    User user = teacher.getUser();
                    int userid = 0;
                    if (user != null) {
                        userid = user.getUser_id();
                        if (!userIdList.contains(userid)) {
                            user.setUser_isAdmin(ISADMIN_N);
                        }
                    }
                }
            }
        }
        List<Teacher> finalResult = new ArrayList<>();
        for (Map<String, String> map : list) {
            Teacher teacher = createTeacher(map);
            finalResult.add(teacher);
        }


        PageData result = new PageData(page, (int) count, pageSize, finalResult);
        return result;

        //<editor-fold desc="oldcode">
        //        final PageData pageData = getHibernateTemplate().execute(new HibernateCallback<PageData>() {
//            @Override
//            public PageData doInHibernate(Session session) throws HibernateException {
//                String count_sql = "select count(DISTINCT user.user_id) from user_teacher t left join user on user.user_code = t.teacher_code" +
//                        " left join ad_campus2user on User.user_id = ad_campus2user.user_id";
//                String conjuntion = " where ";
//                if (teacherName!=null&&!"".equals(teacherName)){
//                    count_sql += " where t.teacher_name like '%"+teacherName+"%'";
//                    conjuntion = " and ";
//                } else if (teacherCode!=null&&!"".equals(teacherCode)){
//                    count_sql += " where t.teacher_code like '%"+teacherCode+"%'";
//                    conjuntion = " and ";
//                }
//                if("ad".equals(flag)){
//                    count_sql+=conjuntion+"ad_campus2user.campus_id='"+campusId+"'";
//                }
//                count_sql+=" order by t.id desc";
//                long count = Long.parseLong(session.createSQLQuery(count_sql).list().get(0).toString());//获取查询列表的总数量
//                String sql = count_sql.replace("count(DISTINCT user.user_id)","DISTINCT t.*");//查询教师表
//                Query query = session.createSQLQuery(sql).addEntity(Teacher.class);
//                int start = PageData.calcFirstItemIndexOfPage(page,pageSize,(int) count);
//                query.setFirstResult(start);
//                query.setMaxResults(pageSize);
//                List<Teacher> list = query.list();
//                if ("us".equals(flag)){
//                    String[] campusIdStr = campusId.split(",");
//                    String str = "";
//                    for (int i=0;i<campusIdStr.length;i++){
//                      str += "'"+campusIdStr[i]+"',";
//                    }
//                    if(!"".equals(str)&&str.endsWith(",")){
//                        str = str.substring(0,str.lastIndexOf(","));
//                    }
//                    //查询选定机构下分配的管理员
//                    List<Map<String,String>> userList = JdbcTemplateUtil.getJdbcTemplate().findList("select distinct user_id from ad_campus2admin where campus_id in("+str+")");
//                    List userIdList = new ArrayList();
//                    if (userList!=null&&userList.size()>0){
//                        //将管理员的id放入list集合中
//                        for (Map userId:userList){
//                            int id = Integer.valueOf(userId.get("user_id").toString());
//                            userIdList.add(id);
//                        }
//                   }
//                    if (list!=null&&list.size()>0){
//                        //判断用户如果不属于选定机构的管理员，则返回给界面值时取消该用户的管理员身份
//                        for (Teacher teacher:list){
//                            User user = teacher.getUser();
//                            int userid = 0;
//                            if (user!=null){
//                                userid = user.getUser_id();
//                                if (!userIdList.contains(userid)){
//                                    user.setUser_isAdmin(ISADMIN_N);
//                                }
//                            }
//                        }
//                    }
//                }
//                PageData pageData = new PageData(page, (int) count, pageSize, list);
//                return pageData;
//            }
//        });
//        return pageData;
        //</editor-fold>
    }

    private Teacher createTeacher(Map<String, String> map) {
        Teacher teacher = new Teacher();
        teacher.setId(Integer.parseInt(map.get("id") == null ? "0" : map.get("id")));
        teacher.setAddress(map.get("address") == null ? "" : map.get("address").trim());
        teacher.setBirthday(map.get("birthday") == null ? "" : map.get("birthday").trim());
        teacher.setEmail(map.get("email") == null ? "" : map.get("email").trim());
        teacher.setEmployeeno(map.get("employeeno") == null ? "" : map.get("employeeno").trim());
        teacher.setGender(map.get("gender") == null ? "" : map.get("gender").trim());
//        teacher.setId(Integer.parseInt(map.get("id") == null ? "" : map.get("id").trim()));
        teacher.setIdcode(map.get("idcode") == null ? "" : map.get("idcode").trim());
        teacher.setIs_job(map.get("is_job") == null ? "" : map.get("is_job").trim());
        teacher.setMobile(map.get("mobile") == null ? "" : map.get("mobile").trim());
        teacher.setNamepy(map.get("namepy") == null ? "" : map.get("namepy").trim());
        teacher.setNation(map.get("nation") == null ? "" : map.get("nation").trim());
        teacher.setPolitical(map.get("political") == null ? "" : map.get("political").trim());
        teacher.setProfessional_title(map.get("professional_title") == null ? "1" : map.get("professional_title").trim());
        teacher.setQq(map.get("qq") == null ? "" : map.get("qq").trim());
        teacher.setShort_num(map.get("short_num") == null ? "" : map.get("short_num").trim());
        teacher.setStage_id(map.get("stage_id") == null ? "" : map.get("stage_id").trim());
        teacher.setTeach_date(map.get("teach_date") == null ? "" : map.get("teach_date").trim());
        teacher.setTeacher_code(map.get("teacher_code") == null ? "" : map.get("teacher_code").trim());
        teacher.setTeacher_name(map.get("teacher_name") == null ? "" : map.get("teacher_name").trim());
        teacher.setTeacher_path(map.get("teacher_path") == null ? "" : map.get("teacher_path").trim());
        teacher.setWork_date(map.get("work_date") == null ? "" : map.get("work_date").trim());

        //组装stage类到teacher
        String stageId = map.get("stage_id") == null ? "" : map.get("stage_id").trim();
        Stage stage = queryAndCreateStage(stageId);
        teacher.setStage(stage);

        //组装subject到teacher
        Set<Subject> subjects = queryAndCreateSubject(teacher.getTeacher_code());
        teacher.setSubject(subjects);

        //组装user到teacher
        String userCode = map.get("teacher_code") == null ? "" : map.get("teacher_code").trim();
        assembleTeacherWithUser(userCode, teacher);
        //组装duty到teacher
        Set<TeacherDutyType> dutySet = getDutySet(teacher.getUser().getUser_type());

        teacher.setTeacherDutyTypes(dutySet);
        return teacher;
    }

    private Set<TeacherDutyType> getDutySet(String userType) {
        String userTypeQuerySql = "SELECT " +
                " a.* " +
                "FROM " +
                " user_type a " +
                "JOIN USER b ON a.id = b.user_type " +
                "WHERE " +
                " b.user_type = '" + userType + "'";
        List<Map<String, String>> userTypeList = JdbcTemplateUtil.getJdbcTemplate().findList(userTypeQuerySql);
        Set<TeacherDutyType> dutyTypeSet = new HashSet<>();
        if (!userTypeList.isEmpty()) {
            Map<String, String> dutyMap = userTypeList.get(0);
            TeacherDutyType teacherDutyType = creteDuty(dutyMap);
            dutyTypeSet.add(teacherDutyType);
        }
        return dutyTypeSet;
    }

    private TeacherDutyType creteDuty(Map<String, String> map) {
        TeacherDutyType teacherDutyType = new TeacherDutyType();
        teacherDutyType.setType_name(map.get("type_name"));
        teacherDutyType.setId(Integer.parseInt(map.get("id")));
        return teacherDutyType;
    }

    private Set<Subject> queryAndCreateSubject(String teacherCode) {
        String subjectSql = "SELECT " +
                "	a.* " +
                "FROM " +
                "	classification_subject a " +
                "JOIN user_teacher2subject b ON b.subject_id = a.id " +
                "WHERE " +
                "	b.teacher_code = '" + teacherCode + "'";
        List<Map<String, String>> list = JdbcTemplateUtil.getJdbcTemplate().findList(subjectSql);
        Set<Subject> subjects = new HashSet<>();
        if (!list.isEmpty()) {
            for (Map<String, String> map : list) {
                Subject subject = createSubject(map);
                subjects.add(subject);
            }
        }
        return subjects;

    }

    /**
     * 对parent进行组装操作，将user放置进去
     *
     * @param code
     * @param teacher
     */
    private void assembleTeacherWithUser(String code, Teacher teacher) {
        List<Map<String, String>> userList = getUserListByUserCode(code);
        if (!userList.isEmpty()) {
            User user = getUserById(userList);
            teacher.setUser(user);
        }
    }

    private Stage queryAndCreateStage(String stageId) {
        String stageSql = "select * from ad_stages where id = '" + stageId + "'";
        List<Map<String, String>> list = JdbcTemplateUtil.getJdbcTemplate().findList(stageSql);
        Stage stage = new Stage();
        if (!list.isEmpty()) {
            stage = createStage(list.get(0));
        }
        return stage;

    }

    private Stage createStage(Map<String, String> map) {
        Stage stage = new Stage();
        stage.setId(Integer.parseInt(map.get("id") == null ? "" : map.get("id").trim()));
        stage.setName(map.get("name") == null ? "" : map.get("name").trim());
        stage.setNumber(Integer.parseInt(map.get("number") == null ? "" : map.get("number").trim()));
        return stage;
    }

    /**
     * 获取当前数据库系统编号的最大值
     *
     * @return
     */
    @Override
    public String getMaxTeacherCode() {
        String querySql = "select max(t.teacher_code) from user_teacher as t";
        List<String> value = JdbcTemplateUtil.getJdbcTemplate().findValue(querySql);
        if (value.isEmpty()) {
            return "0";
        }
        return value.get(0);
        //<editor-fold desc="oldcode">
        //        String re_value = null;
//        re_value = getHibernateTemplate().execute(new HibernateCallback<String>() {
//            @Override
//            public String doInHibernate(Session session) throws HibernateException {
//                String querySql = "select max(t.teacher_code) from Teacher as t";
//                Query query = session.createQuery(querySql);
//                String str = (String) query.uniqueResult();
//                return str;
//            }
//        });
//        return re_value;
        //</editor-fold>
    }

    /**
     * 获取职务类型列表,添加教师的时候用到
     *
     * @return UserType
     */
    @Override
    public List getTteacherDuty() {
        String sql = "select * from user_type";
        List<Map<String, String>> list = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        return list;
//        List userTypeList = new ArrayList();
//        userTypeList = (List) this.getHibernateTemplate().find("from TeacherDutyType");
//        return userTypeList;
    }

    /**
     * 获取学段列表,添加教师时的下拉菜单列表
     *
     * @return Stage
     */
    @Override
    public List getStages() {
        String sql = "select * from ad_stages";
        List<Map<String, String>> list = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        return list;
//        List stageList = new ArrayList();
//        stageList = (List) this.getHibernateTemplate().find("from Stage");
//        return stageList;
    }

    /**
     * 获取教师科目列表，添加教师时的下拉菜单列表
     *
     * @return Subject
     */
    @Override
    public List getSubjectInfo(String seasonId) {
        if(seasonId==null || "".equals(seasonId)){
            seasonId = SEASON_ID;
        }
        String sql ="select distinct * from classification_subject where season_id = '" + seasonId + "'";
        List<Map<String, String>> list = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        return list;
        //<editor-fold desc="oldcode">
        //        List subjectList = new ArrayList();
//        String sql = "from Subject";
//        if (seasonId != null) {
//            sql += " where season_id = " + seasonId;
//        }
//        subjectList = (List) this.getHibernateTemplate().find(sql);
//        return subjectList;
        //</editor-fold>
    }

    /**
     * 删除教师
     *
     * @param teacherCode
     * @return
     */
    @Override
    public boolean delete(String teacherCode) {
        //删除教师
        String deleteSql = "delete from user_teacher where teacher_code = '" + teacherCode + "'";
        boolean execute = JdbcTemplateUtil.getJdbcTemplate().execute(deleteSql);
        if (!execute) {
            logger.error("删除教师失败！sql：" + deleteSql);
        } else {
            //删除用户
            String deleteUserSql = "delete from user where user_code = '" + teacherCode + "'";
            //删除用户与学科关系
            String deleteTeacher2SubSql="delete from user_teacher2subject where teacher_code='"+teacherCode+"'";
            //删除用户与组织机构的关系
            String deleteCampus2User="delete from ad_campus2user where user_id=(select user_id from user where user_code='"+teacherCode+"')";
            JdbcTemplateUtil.getJdbcTemplate().execute(deleteTeacher2SubSql,deleteCampus2User,deleteUserSql);
        }
        return execute;
        //<editor-fold desc="oldcode">
        //        boolean reValue = false;
//        try {
//            Teacher teacher = getTeacherByCode(teacherCode);
//            this.getHibernateTemplate().delete(teacher);
//            reValue = true;
//        } catch (DataAccessException e) {
//            logger.error("删除教师对象失败");
//        }
//        return reValue;
        //</editor-fold>
    }
    /**
     * 批量删除教师
     *
     * @param userIds 用户id，多个用逗号隔开
     * @return
     * @throws Exception
     */
    @Override
    public boolean deleteTeachers(final String userIds) {
        String count_sql = "select DISTINCT u.user_id from user_teacher t left join user u on u.user_code = " +
                "t.teacher_code where t.id in(" + userIds + ")";
        //获取查询列表userid
        List<String> userIdList = JdbcTemplateUtil.getJdbcTemplate().findValue(count_sql);
        String sql = "";
        StringBuilder sb = new StringBuilder();
        int query = 0;
        boolean execute = false;
        if (userIdList.size() > 0) {
            sql = "delete from user_teacher where id in(" + userIds + ")";
            for (String uId : userIdList) {
                sb.append(uId + ",");
            }
            String uIds = sb.toString();
            if (!"".equals(uIds) && uIds.endsWith(",")) {
                uIds = uIds.substring(0, uIds.lastIndexOf(","));
            }
            //added by HOUJT 2018/6/19 15:54 同时删除user_teacher2subject表
            String deleteTeacher2SubjectSql = "DELETE " +
                    "FROM " +
                    "	user_teacher2subject " +
                    "WHERE " +
                    "	teacher_code IN ( " +
                    "		SELECT " +
                    "			user_code " +
                    "		FROM " +
                    "			user " +
                    "		WHERE " +
                    "			user_id IN ("+uIds+") " +
                    "	)";
            //如果删除了教师，则删除用户数据和教师对应的机构管理员身份
            String userSql = "delete from user where user_id in(" + uIds + ")";
            String relateSql = "delete from ad_campus2user where user_id in(" + uIds + ")";
//            execute = JdbcTemplateUtil.getJdbcTemplate().execute(sql, userSql, relateSql,deleteTeacher2SubjectSql);
            execute = JdbcTemplateUtil.getJdbcTemplate().execute(sql,deleteTeacher2SubjectSql,relateSql,userSql);
        }

        return execute;
        //<editor-fold desc="oldcode">
        //        boolean re_value = false;
//        final Integer value = getHibernateTemplate().execute(new HibernateCallback<Integer>() {
//            @Override
//            public Integer doInHibernate(Session session) throws HibernateException {
//                String count_sql = "select DISTINCT u.user_id from user_teacher t left join user u on u.user_code = t.teacher_code where t.id in(" + userIds + ")";
//                List<Integer> userIdList = session.createSQLQuery(count_sql).list();//获取查询列表userid
//                String sql = "";
//                String uIds = "";
//                int query = 0;
//                if (userIdList.size() > 0) {
//                    sql = "delete from user_teacher where id in(" + userIds + ")";
//                    for (int uId : userIdList) {
//                        uIds += uId + ",";
//                    }
//                    if (!"".equals(uIds) && uIds.endsWith(",")) {
//                        uIds = uIds.substring(0, uIds.lastIndexOf(","));
//                    }
//                    query = session.createSQLQuery(sql).executeUpdate();
//                    //如果删除了教师，则删除用户数据和教师对应的机构管理员身份
//                    if (query > 0) {
//                        String userSql = "delete from user where user_id in(" + uIds + ")";
//                        session.createSQLQuery(userSql).executeUpdate();
//                        String relateSql = "delete from ad_campus2admin where user_id in(" + uIds + ")";
//                        session.createSQLQuery(relateSql).executeUpdate();
//                    }
//                }
//
//                return query;
//            }
//        });
//        if (value > 0) {
//            re_value = true;
//        }
//        return re_value;
        //</editor-fold>
    }

//    /**
//     * 依据系统编号返回对象
//     *
//     * @param code 系统编号
//     * @rn
//     */
//    @Override
//    public Teacher getTeacherByCode(final String code) {
//        Teacher re_value = new Teacher();
//        re_value = this.getHibernateTemplate().execute(new HibernateCallback<Teacher>() {
//            @Override
//            public Teacher doInHibernate(Session session) throws HibernateException {
//                String sql = "FROM Teacher WHERE teacher_code= '" + code + "'";
//                Query query = session.createQuery(sql);
//                List<Teacher> list = query.list();
//                return list == null ? null : list.get(0);
//            }
//        });
//        return re_value;
//    }

    @Override
    public long exitTeacher2Subject(String teacherCode){
        String sql="select count(*) from user_teacher2subject where teacher_code='"+teacherCode+"'";
        return JdbcTemplateUtil.getJdbcTemplate().count(sql);
    }
    @Override
    public boolean saveTeacher2Subject(Teacher teacher){
        boolean result=false;
        if(teacher!=null){
            String teacherCode=teacher.getTeacher_code();
            Set<Subject> subject=teacher.getSubject();
            if(subject!=null && !subject.isEmpty()){
                Iterator iterator=subject.iterator();
                long subjectId=0L;
                while (iterator.hasNext()){
                    Subject sub=(Subject)iterator.next();
                    subjectId=sub.getId();
                }
                long count=exitTeacher2Subject(teacherCode);
                String sql="";
                if(count>0){
                    //修改
                    sql="update user_teacher2subject set subject_id="+subjectId+" where teacher_code='"+teacherCode+"'";
                    result=JdbcTemplateUtil.getJdbcTemplate().update(sql);
                }else{
                    //插入
                    sql="insert into user_teacher2subject(teacher_code,subject_id) values(?,?)";
                    result=JdbcTemplateUtil.getJdbcTemplate().add(sql,teacherCode,subjectId);
                }
            }else{
                result=deleteTeacher2Subject(teacherCode);
            }
        }
        return result;
    }
    @Override
    public boolean deleteTeacher2Subject(String teacherCode){
        String sql="delete from user_teacher2subject where teacher_code='"+teacherCode+"'";
        return JdbcTemplateUtil.getJdbcTemplate().execute(sql);
    }
}
