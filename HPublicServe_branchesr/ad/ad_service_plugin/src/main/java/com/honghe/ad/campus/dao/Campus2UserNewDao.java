package com.honghe.ad.campus.dao;

import com.honghe.ad.Directory;
import com.honghe.ad.util.JdbcTemplateUtil;
import com.honghe.dao.PageData;

import java.util.*;


/**
 * Created by sunchao on 2017/2/28.
 */
public class Campus2UserNewDao {
    private Campus2UserNewDao() {

    }
    public final static Campus2UserNewDao INSTANCE = new Campus2UserNewDao();
    public final static CampusNewDao campusNewDao = new CampusNewDao();
    private final String USERTYPE_TEACHER = "17"; //教师身份
    private final String USERTYPE_STUDENT = "18"; //学生身份



    /**
     *通过userid查询这个用户所在机构以及下级的机构
     * @param userId 用户id
     * @param userType 用户职务类型
     * @return
     */
    public final Directory find(String userId, String userType) {
        Directory directory = new Directory();
            //通过id确定查询教师或是学生的组织机构
            String tableName = CampusNewDao.INSTANCE.getUserTableNameById(userId);
            String sql = "";
            List<Map<String, String>> result = new ArrayList<>();
//            String depids = "";
            sql = "select departid from "+tableName+" bs where bs.EMPLOYEENO = (select user_code from user where user_id = "+userId+" )";
            result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
            if (!"".equals(tableName)){
//                if ("base_teacher".equals(tableName)){
//                    String sqlteacher = "select departid from base_teacher bt left join base_r_stu_dept_tea br on bt.id=br.teacherid where bt.EMPLOYEENO = (select user_code from user where user_id = "+userId+" )";
//                    List<Map<String, String>> list = JdbcTemplateUtil.getJdbcTemplate().findList(sqlteacher);
//                }

                //查询该用户所在机构及以下的机构
                if(result.size()!=0) {
                    directory = CampusNewDao.INSTANCE.campusChildSearch(result.get(0).get("departid"));
                }
            }  else {
                return null;
            }
        return directory;
        }


    /**
     * 根据用户类型查询用户，带用户机构名称
     *
     * @param userType
     * @return
     */
    public final List<Map<String, String>> findByUserType(String userType) {
        String sql = "SELECT a.user_id AS userId,a.user_name AS userName,a.user_type AS userType, " +
                "a.user_realname AS userRealName, a.user_path AS userPath, a.user_avatar AS userAvatar," +
                "a.user_gender AS userGender, a.user_birthday AS userBirthday, a.user_mobile AS userMobile, " +
                "a.user_email AS userEmail, a.user_address AS userAddress, a.user_num AS userNum, a.user_regdate AS userRegdate," +
                "a.user_status AS userStatus, bd. DEPARTMENTNAME AS campusName from user AS a ";
        if ("教师".equals(userType)||"老师".equals(userType)){
            sql += "LEFT JOIN base_teacher tn ON a.user_code = tn.EMPLOYEENO "+
                    "LEFT JOIN base_inc_tea_dept bitd ON bitd.teacherid = tn.id "+
                    "LEFT JOIN base_dept_teacher bdt ON bdt.ID = bitd.DEPTID ";
        }else if ("学生".equals(userType)){
            sql += "LEFT JOIN base_student tn ON a.user_code = tn.EMPLOYEENO "+
                    "LEFT JOIN base_dept_student bd ON bd.ID = tn.DEPARTID ";
        }
         sql += "LEFT JOIN user_type ut ON a.user_type = ut.id "+
                "WHERE ut.type_name = '"+userType+"'";
        return JdbcTemplateUtil.getJdbcTemplate().findList(sql);
    }

    /**
     * 如果一个人别分配到多个机构会存在问题
     *
     * @param userNum
     * @return
     */
    public final List<Map<String, String>> findByUserNum(String[] userNum) {

        StringBuilder sb = new StringBuilder();
        for (String _userNum : userNum) {
            sb.append("'" + _userNum + "',");
        }
        String sql = "SELECT DISTINCT u.user_name AS userName, u.user_type AS userType, u.user_realname AS userRealName, u.user_path AS userPath, " +
                "u.user_avatar AS userAvatar, u.user_gender AS userGender, u.user_birthday AS userBirthday, u.user_mobile AS userMobile, " +
                "u.user_email AS userEmail, u.user_address AS userAddress, u.user_num AS userNum, u.user_regdate AS userRegdate, " +
                "u.user_status AS userStatus, bds.ID AS campusId, bds.departmentName AS campusName FROM user u,base_dept_student bds," +
                "base_dept_teacher bdt,base_student bs,base_teacher bt,base_inc_tea_dept bitd " +
                "WHERE ((u.user_code = bt.EMPLOYEENO AND bitd.teacherid = bt.id AND bdt.ID = bitd.DEPTID)" +
                "OR (u.user_code = bs.EMPLOYEENO AND bds.id = bs.DEPARTID)) AND u.user_num IN (" + sb.toString().substring(0, sb.length() - 1) + ")";
        return JdbcTemplateUtil.getJdbcTemplate().findList(sql);
    }

    /**
     * 分页获取没有和校园组织机构关联的用户
     *
     * @param page 当前页
     * @param pageSize 每页条目
     * @param loginName
     * @return
     */
    public PageData findUser(int page, int pageSize, String loginName) {
        String condition = "";
        if (!loginName.equals("")) {
            condition = "and (user_name like '%" + loginName + "%' or user_realname like '%" + loginName + "%')";
        }
        String countSql = "select count(*) from user where user_status=1 and user_id <>1 and user_id not in(SELECT DISTINCT user_id FROM USER u, base_student bs, base_dept_student bds, base_teacher bt, base_dept_teacher bdt, base_inc_tea_dept bitd" +
        "WHERE ( u.user_code = bs.employeeno AND bs.departid = bds.id) OR ( u.user_code = bt.employeeno AND bitd.teacherid = bt.id AND bdt.ID = bitd.DEPTID)) " + condition;
        String resultSql = "select  u.user_id as userId, u.user_name as userName," +
                " u.user_type as userType,u.user_realname as userRealName,u.user_path as userPath,u.user_avatar as userAvatar," +
                " u.user_gender as userGender,u.user_birthday as userBirthday,u.user_mobile as userMobile,u.user_email as userEmail," +
                " u.user_address as userAddress,u.user_regdate as userRegdate,u.user_status as userStatus,t.type_name as typeName from user u " +
                "left join user_type as t on t.id=u.user_type where u.user_status=1 and  u.user_id <>1 and user_id not in(SELECT DISTINCT user_id FROM USER u, base_student bs, base_dept_student bds, base_teacher bt, base_dept_teacher bdt " +
        "WHERE ( u.user_code = bs.employeeno AND bs.departid = bds.id) OR ( u.user_code = bt.employeeno AND bitd.teacherid = bt.id AND bdt.ID = bitd.DEPTID)) "+
                condition + " order by u.user_regdate desc limit ?,?";
        return JdbcTemplateUtil.getJdbcTemplate().findByPage(page, pageSize, countSql, resultSql);
    }

    /**通过用户id批量获取组织机构信息
     * @param userId
     * @return
     */
    public final List<Map<String, String>> findByUserId(String userId) {

        String sql = "";
        List<Map<String, String>> result = new ArrayList<>();
        if (userId.contains(",")) {
            String[] temp = userId.split(",");
            for (String s : temp) {
                String tableName = CampusNewDao.INSTANCE.getUserTableNameById(s);
                if(tableName.equals("base_student")){
                    sql =" SELECT a.user_id AS userId, a.user_name AS userName, a.user_type AS userType, a.user_realname AS userRealName, a.user_path AS userPath, a.user_avatar AS userAvatar, a.user_gender AS userGender, a.user_birthday AS userBirthday, a.user_mobile AS userMobile, a.user_email AS userEmail, a.user_address AS userAddress, a.user_num AS userNum, a.user_regdate AS userRegdate, a.user_status AS userStatus,       bs.DEPARTID AS campusId,       bd.DEPARTMENTNAME AS campusName FROM base_student bs     LEFT JOIN user a ON a.user_code = bs.EMPLOYEENO      LEFT JOIN base_dept_student bd ON bd.ID = bs.DEPARTID WHERE bs.EMPLOYEENO in ( SELECT user_code  FROM USER  WHERE user_id  ";

                }
                else if(tableName.equals("base_teacher")){
                    sql ="SELECT a.user_id AS userId, a.user_name AS userName, a.user_type AS userType, a.user_realname AS userRealName, a.user_path AS userPath, a.user_avatar AS userAvatar, a.user_gender AS userGender, a.user_birthday AS userBirthday, a.user_mobile AS userMobile, a.user_email AS userEmail, a.user_address AS userAddress, a.user_num AS userNum, a.user_regdate AS userRegdate, a.user_status AS userStatus, bi.DEPTID AS campusId FROM base_teacher bt LEFT JOIN USER a ON a.user_code = bt.EMPLOYEENO LEFT JOIN base_inc_tea_dept bi ON bi.TEACHERID = bt.ID WHERE bt.EMPLOYEENO IN ( SELECT user_code FROM USER WHERE user_id   ";
                }
                sql = sql + "='" + s + "')";
                result.addAll(JdbcTemplateUtil.getJdbcTemplate().findList(sql)) ;
            }
        } else {
            String tableName = CampusNewDao.INSTANCE.getUserTableNameById(userId);
            if(tableName.equals("base_student")){
                sql =" SELECT a.user_id AS userId, a.user_name AS userName, a.user_type AS userType, a.user_realname AS userRealName, a.user_path AS userPath, a.user_avatar AS userAvatar, a.user_gender AS userGender, a.user_birthday AS userBirthday, a.user_mobile AS userMobile, a.user_email AS userEmail, a.user_address AS userAddress, a.user_num AS userNum, a.user_regdate AS userRegdate, a.user_status AS userStatus,       bs.DEPARTID AS campusId,       bd.DEPARTMENTNAME AS campusName FROM base_student bs     LEFT JOIN user a ON a.user_code = bs.EMPLOYEENO      LEFT JOIN base_dept_student bd ON bd.ID = bs.DEPARTID WHERE bs.EMPLOYEENO in ( SELECT user_code  FROM USER  WHERE user_id  ";
            }
            else if(tableName.equals("base_teacher")){
                sql ="SELECT a.user_id AS userId, a.user_name AS userName, a.user_type AS userType, a.user_realname AS userRealName," +
                        " a.user_path AS userPath, a.user_avatar AS userAvatar, a.user_gender AS userGender, a.user_birthday AS userBirthday, a.user_mobile AS userMobile, a.user_email AS userEmail, a.user_address AS userAddress, a.user_num AS userNum, a.user_regdate AS userRegdate, a.user_status AS userStatus, bi.DEPTID AS campusId FROM base_teacher bt LEFT JOIN USER a ON a.user_code = bt.EMPLOYEENO LEFT JOIN base_inc_tea_dept bi ON bi.TEACHERID = bt.ID WHERE bt.EMPLOYEENO IN ( SELECT user_code FROM USER WHERE user_id   ";
            }
            sql = sql + "='" + userId + "')";
            result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        }
        return result;
    }

    /**
     * 通过人员id查询用户信息
     * @param ids 考勤表中的人员id，多个用逗号隔开
     * @param flag 人员标识 17：教师，18：学生
     * @return
     */
    public List<Map<String,String>> findInfoById(String ids,String flag){
        String sql = "SELECT u.user_id AS userId, u.user_realname AS userRealName, u.user_gender AS userGender," +
                " u.user_code AS userCode, bd.departmentNo, bd.departmentName,aa.employeeId FROM USER u,";
        if (ids!=null&&!"".equals(ids)) {
                if(USERTYPE_STUDENT.equals(flag)){
                    sql += "base_dept_student bd,base_student bs, student_att_attenddata aa " +
                            "WHERE u.user_code = bs.employeeno AND aa.employeeId = bs.id AND " + "bs.departid = bd.ID " ;
                } else if(USERTYPE_TEACHER.equals(flag)){
                    sql += "base_dept_teacher bd,base_teacher bt, teacher_att_attenddata aa ,base_inc_tea_dept bi " +
                            "WHERE u.user_code = bt.employeeno AND aa.employeeId = bt.id AND bi.TEACHERID = bt.ID and bd.ID = bi.DEPTID " ;
                }

        }
        sql+="AND aa.employeeId IN ("+ids+")";

        List<Map<String,String>> listInfo = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        return listInfo;
    }

    /**
     * 查找机构下用户数量
     * @param campusId 机构id
     * @param userType 用户类型
     * @return
     */
    public long findUserCount(String campusId, Object userType) {
        String tableName = campusNewDao.getCampusTableName(campusId);
        if(null==userType){
            userType="";
        }
        if(tableName.equals("base_dept_teacher"))
        {
            String sql = "select count(*) from BASE_INC_TEA_DEPT where DEPTID in ('"+campusId+"')" ;
            return JdbcTemplateUtil.getJdbcTemplate().count(sql);
        }

        if(tableName.equals("base_dept_student"))
        {

            if(userType.equals(USERTYPE_STUDENT)) {
                String sql = "select count(*) from base_student where DEPARTID in ('"+campusId+"')" ;
                return JdbcTemplateUtil.getJdbcTemplate().count(sql);
            }
            if(userType.equals(USERTYPE_TEACHER)) {
                String sql = "select count(*) from base_r_stu_dept_tea where DEPTID in ('"+campusId+"')" ;
                return JdbcTemplateUtil.getJdbcTemplate().count(sql);
            }
            else{
                String sql = "select count(*) from base_student where DEPARTID in ('"+campusId+"')" ;
                String sql2 = "select count(*) from base_r_stu_dept_tea where DEPTID in ('"+campusId+"')" ;
                long count =JdbcTemplateUtil.getJdbcTemplate().count(sql);
                long count2 =JdbcTemplateUtil.getJdbcTemplate().count(sql2);
                return count+count2;
            }
        }

        if(tableName.equals("base_group_teacher"))
        {
            String sql = "select count(*) from base_r_group_teacher where GROUPID in ('"+campusId+"')" ;
            return JdbcTemplateUtil.getJdbcTemplate().count(sql);
        }

        return 0;
    }


    /**
     * 通过组织机构id获取用户信息
     * @param campusId
     * @return
     */
    public List findUserByCampusId(String campusId,String userType,String userName)
    {

        String strTea = "";
        String strStu = "";
        List result = new ArrayList();
        if(campusId.contains(",")) {
            StringBuffer strStudent = new StringBuffer();
            StringBuffer strTeacher = new StringBuffer();;
            String[] temp = campusId.split(",");
            for (String s : temp) {
                String tableName = campusNewDao.getCampusTableName(s);
                if(tableName.equals("base_dept_teacher")||tableName.equals("base_group_teacher")||tableName.equals("base_dept_student")){
                    strTeacher.append("'").append(s).append("'").append(",");
                }
                if(tableName.equals("base_dept_student")){
                    strStudent.append("'").append(s).append("'").append(",");
                }
            }
          strTea =strTeacher.toString();
          strStu =strStudent.toString();
          strStu = strStu.substring(0,strStu.length()-1);
          strTea = strTea.substring(0,strTea.length()-1);
        }

        else{
            String tableName = campusNewDao.getCampusTableName(campusId);
            if(tableName.equals("base_dept_teacher")||tableName.equals("base_group_teacher")||tableName.equals("base_dept_student")){
                    strTea = campusId;
            }
            if(tableName.equals("base_dept_student")){
                    strStu = campusId;
            }
        }
        String loginNameWhere = "";
        String userTypeWhere = "";
        if (userName != null && !userName.toString().equals("")) {
            loginNameWhere = " and u.user_realname like '%" + userName + "%'";
        }
        if (userType != null && !userType.toString().equals("")) {
            userTypeWhere = " and u.user_type = " + userType + "";
        }
        String resultSql = "SELECT u.user_id AS userId, u.user_name AS userName, u.user_type AS userType, u.user_realname AS userRealName, u.user_path AS userPath, u.user_avatar AS userAvatar, u.user_gender AS userGender, u.user_birthday AS userBirthday," +
                " u.user_mobile AS userMobile, u.user_email AS userEmail, u.user_num AS userNum, " +
                "u.user_address AS userAddress, u.user_regdate AS userRegdate, u.user_status AS userStatus FROM USER AS u," +
                " ( SELECT user_id FROM USER u WHERE u.user_code IN ( SELECT EMPLOYEENO FROM base_teacher t WHERE t.ID IN ( SELECT d.TEACHERID FROM base_inc_tea_dept d WHERE d.DEPTID IN ('"+strTea+"') UNION SELECT br.TEACHERID FROM base_r_stu_dept_tea br WHERE br.DEPTID IN ('"+strTea+"') UNION SELECT bg.TEACHERID FROM base_r_group_teacher bg WHERE bg.GROUPID IN ('"+strTea+"') ) ) UNION ( SELECT user_id FROM USER u WHERE u.user_code IN ( SELECT EMPLOYEENO FROM base_student b WHERE b.DEPARTID IN ('"+strStu+"') ) ) ) AS b WHERE u.user_id = b.user_id "+loginNameWhere+" "+userTypeWhere+" ";
        result = JdbcTemplateUtil.getJdbcTemplate().findList(resultSql);
        return result;
    }


    /**通过用户id批量获取组织机构信息
     * @param userId
     * @return
     */
    public final List<Map<String, String>> findCampusByUserId(String userId) {
        String strTea = "''";
        String strStu = "''";
        List<Map<String, String>> result = new ArrayList();
        if(userId.contains(",")) {
            StringBuffer strStudent = new StringBuffer();
            StringBuffer strTeacher = new StringBuffer();;
            String[] temp = userId.split(",");
            for (String s : temp) {
                String tableName = campusNewDao.getUserTableNameById(s);
                if(tableName.equals("base_teacher")){
                    strTeacher.append("'").append(s).append("'").append(",");
                }
                if(tableName.equals("base_student")){
                    strStudent.append("'").append(s).append("'").append(",");
                }
            }
            strTea =strTeacher.toString();
            strStu =strStudent.toString();
            strStu = strStu.substring(0,strStu.length()-1);
            strTea = strTea.substring(0,strTea.length()-1);
        }
        else{
            String tableName = campusNewDao.getUserTableNameById(userId);
            if(tableName.equals("base_teacher")){
                strTea = userId;
            }
            if(tableName.equals("base_student")){
                strStu = userId;
            }
        }
        String resultSql = "SELECT a.user_id AS userId, a.user_name AS userName, a.user_type AS userType, a.user_realname AS userRealName, a.user_path AS userPath, a.user_avatar AS userAvatar, a.user_gender AS userGender, a.user_birthday AS userBirthday, a.user_mobile AS userMobile, a.user_email AS userEmail, a.user_address AS userAddress, a.user_num AS userNum, a.user_regdate AS userRegdate, a.user_status AS userStatus, bt.ID AS campusId,   bt.DEPARTMENTNAME AS campusName FROM base_teacher btt LEFT JOIN USER a ON a.user_code = btt.EMPLOYEENO LEFT JOIN base_inc_tea_dept bi ON bi.TEACHERID = btt.ID LEFT JOIN base_dept_teacher bt ON bi.DEPTID = bt.ID WHERE btt.EMPLOYEENO IN ( SELECT user_code FROM USER WHERE user_id IN ('"+strTea+"') ) UNION SELECT a.user_id AS userId, a.user_name AS userName, a.user_type AS userType, a.user_realname AS userRealName, a.user_path AS userPath, a.user_avatar AS userAvatar, a.user_gender AS userGender, a.user_birthday AS userBirthday, a.user_mobile AS userMobile, a.user_email AS userEmail, a.user_address AS userAddress, a.user_num AS userNum, a.user_regdate AS userRegdate, a.user_status AS userStatus, r.DEPTID AS campusId,     bs.DEPARTMENTNAME AS campusName FROM base_teacher bt LEFT JOIN USER a ON a.user_code = bt.EMPLOYEENO LEFT JOIN base_r_stu_dept_tea r ON r.TEACHERID = bt.ID LEFT JOIN base_dept_student bs ON r.DEPTID = bs.ID WHERE bt.EMPLOYEENO IN ( SELECT user_code FROM USER WHERE user_id IN ('"+strTea+"') ) UNION SELECT a.user_id AS userId, a.user_name AS userName, a.user_type AS userType, a.user_realname AS userRealName, a.user_path AS userPath, a.user_avatar AS userAvatar, a.user_gender AS userGender, a.user_birthday AS userBirthday, a.user_mobile AS userMobile, a.user_email AS userEmail, a.user_address AS userAddress, a.user_num AS userNum, a.user_regdate AS userRegdate, a.user_status AS userStatus, bg.ID AS campusId,       bg.GROUPNAME AS campusName FROM base_teacher bt LEFT JOIN USER a ON a.user_code = bt.EMPLOYEENO LEFT JOIN base_r_group_teacher brg ON brg.TEACHERID = bt.ID     LEFT JOIN base_group_teacher bg ON bg.ID = brg.GROUPID WHERE bt.EMPLOYEENO IN ( SELECT user_code FROM USER WHERE user_id IN ('"+strTea+"') ) UNION SELECT a.user_id AS userId, a.user_name AS userName, a.user_type AS userType, a.user_realname AS userRealName, a.user_path AS userPath, a.user_avatar AS userAvatar, a.user_gender AS userGender, a.user_birthday AS userBirthday, a.user_mobile AS userMobile, a.user_email AS userEmail, a.user_address AS userAddress, a.user_num AS userNum, a.user_regdate AS userRegdate, a.user_status AS userStatus, bs.DEPARTID AS campusId,       bd.DEPARTMENTNAME AS campusName FROM base_student bs LEFT JOIN USER a ON a.user_code = bs.EMPLOYEENO LEFT JOIN base_dept_student bd ON bd.ID = bs.DEPARTID WHERE bs.EMPLOYEENO IN ( SELECT user_code FROM USER WHERE user_id IN ('"+strStu+"') ) ";
        result = JdbcTemplateUtil.getJdbcTemplate().findList(resultSql);
        return result;
    }


    /**
     * 获取校园组织机构下除传入的用户编号之外的用户
     *
     * @param campusId 机构id
     * @param userNums 用户编号，多个用逗号分割
     * @return
     */
    public List findOtherUserByCampus(String campusId, String userNums){

        String sql = "SELECT DISTINCT u.user_id AS userId, u.user_name AS userName, u.user_type AS userType, u.user_realname AS userRealName," +
                " u.user_path AS userPath, u.user_avatar AS userAvatar, u.user_gender AS userGender, u.user_birthday AS userBirthday," +
                " u.user_mobile AS userMobile, u.user_email AS userEmail, u.user_address AS userAddress, u.user_num AS userNum," +
                " u.user_regdate AS userRegdate, u.user_status AS userStatus, u.user_card AS userCard, u.user_course AS userCourse, " +
                "u.user_info AS userInfo FROM USER u, base_student bs, base_dept_student bds, base_teacher bt, base_dept_teacher bdt, " +
                "base_inc_tea_dept bi WHERE (( u.user_code = bs.EMPLOYEENO AND bs.DEPARTID = bds.ID AND bds.ID = '"+campusId+"') " +
                "OR ( u.user_code = bt.EMPLOYEENO AND bt.ID = bi.TEACHERID AND bi.DEPTID = bdt.ID AND bdt.ID = '"+campusId+"'))";
        if (userNums != null && !"".equals(userNums)) {
            String[] temp = userNums.split(",");
            String userNum = "";
            for (String temp_ : temp) {
                userNum = userNum + "'" + temp_ + "',";
            }
            userNum = userNum.substring(0, userNum.length() - 1);
            sql = sql + " and u.user_num not in(" + userNum + ")";
        }

        return JdbcTemplateUtil.getJdbcTemplate().findList(sql);
    }

    /**
     * 查询机构下用户信息
     *
     * @param page
     * @param pageSize
     * @param campusId
     * @param userType  userType -1 查询所有人，否则查询指定身份人员
     * @param loginName
     * @return
     */
    public PageData findUser(int page, int pageSize, String campusId, int userType, Object loginName) {
        String strTea = "";
        String strStu = "";
        String tableName = campusNewDao.getCampusTableName(campusId);
        if(tableName.equals("base_dept_teacher")||tableName.equals("base_group_teacher")||tableName.equals("base_dept_student")){
            strTea = campusId;
        }
        if(tableName.equals("base_dept_student")){
            strStu = campusId;
        }
        String loginNameWhere = "";
        String userTypeWhere = "";
        if (loginName != null && !loginName.toString().equals("")) {
            loginNameWhere = " and u.user_realname like '%" + loginName + "%'";
        }
        if (userType != -1) {
            userTypeWhere = " and u.user_type=" + userType;
        }
        String countSql = "SELECT u.user_id AS userId, u.user_name AS userName, u.user_type AS userType, u.user_realname AS userRealName, u.user_path AS userPath, u.user_avatar AS userAvatar, u.user_gender AS userGender, u.user_birthday AS userBirthday," +
                " u.user_mobile AS userMobile, u.user_email AS userEmail, u.user_num AS userNum, " +
                "u.user_address AS userAddress, u.user_regdate AS userRegdate, u.user_status AS userStatus FROM USER AS u," +
                " ( SELECT user_id FROM USER u WHERE u.user_code IN ( SELECT EMPLOYEENO FROM base_teacher t WHERE t.ID IN ( SELECT d.TEACHERID FROM base_inc_tea_dept d WHERE d.DEPTID IN ('"+strTea+"') UNION SELECT br.TEACHERID FROM base_r_stu_dept_tea br WHERE br.DEPTID IN ('"+strTea+"') UNION SELECT bg.TEACHERID FROM base_r_group_teacher bg WHERE bg.GROUPID IN ('"+strTea+"') ) ) UNION ( SELECT user_id FROM USER u WHERE u.user_code IN ( SELECT EMPLOYEENO FROM base_student b WHERE b.DEPARTID IN ('"+strStu+"') ) ) ) AS b WHERE u.user_id = b.user_id "+loginNameWhere+" "+userTypeWhere+" ";
        String resultSql = countSql + " limit ?,?";
        return JdbcTemplateUtil.getJdbcTemplate().findByPage(page, pageSize, countSql, resultSql);
    }



    /**
     * 通过用户id获取所在机构信息
     * @param userId
     * @param campusType
     * @return
     */
    public Map<String, String> finds(String userId, String campusType) {

        String tableName = campusNewDao.getUserTableNameById(userId);
        Map<String, String> result = new HashMap<>();
        String where = "";
        if (campusType != "") {
            where = "and t.id=" + campusType + "";
        }
        if(tableName.equals("base_student")){
            String sql ="SELECT a.ID AS campusId,a.DEPARTMENTNAME AS campusName,a.PARENTID AS pId,a.DEPTTYPE AS typeId,a.CENTERID AS centerId,a.STATUS AS status,a.DEPARTMENTNO AS departmentno FROM base_dept_student a WHERE a.ID IN (SELECT DEPARTID FROM base_student bs WHERE bs.EMPLOYEENO = (SELECT user_code FROM USER WHERE user_id = "+userId+"))";
            result = JdbcTemplateUtil.getJdbcTemplate().find(sql);
        }
        else if(tableName.equals("base_teacher")){
            String sql ="SELECT a.ID AS campusId, a.DEPARTMENTNAME AS campusName, a.PARENTID AS pId FROM base_dept_teacher a WHERE a.ID IN ( SELECT b.DEPTID FROM base_inc_tea_dept b WHERE b.TEACHERID IN ( SELECT bt.ID FROM base_teacher bt WHERE bt.EMPLOYEENO = ( SELECT user_code FROM USER WHERE user_id = "+userId+" ) ) )   UNION   SELECT a.ID AS campusId, a.DEPARTMENTNAME AS campusName, a.PARENTID AS pId FROM base_dept_student a WHERE a.ID IN ( SELECT br.DEPTID FROM base_r_stu_dept_tea br WHERE br.TEACHERID IN ( SELECT bt.ID FROM base_teacher bt WHERE bt.EMPLOYEENO = ( SELECT user_code FROM USER WHERE user_id = "+userId+" ) ) )";
            result = JdbcTemplateUtil.getJdbcTemplate().find(sql);
        }
        return result;
    }
}
