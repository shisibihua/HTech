package com.honghe.ad.campus.dao;

import com.honghe.ad.Directory;
import com.honghe.ad.campus.bean.CampusType;
import com.honghe.ad.campus.bean.UserInfo;
import com.honghe.ad.excetion.ParamException;
import com.honghe.ad.util.JdbcTemplateUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sunchao on 2017/2/28.
 */
public class CampusNewDao {
    CampusNewDao() {

    }

    public final static CampusNewDao INSTANCE = new CampusNewDao();
    private final String USERTYPE_TEACHER = "17"; //老师身份
    private final String USERTYPE_STUDENT = "18"; //学生身份
    private final String USERTYPE_STR_PARENT = "PARENT"; //家长身份
    private final String USERTYPE_ADMIN = "0"; //管理员身份
    private final String AREA_CLASSROOM_PARENT = "6"; //地点类型 教室类型的根节点
    private final String ROOT_SCHOOLID = "1"; //学校根节点id
    private static final String areaType_buildingRoom = "1,4,6";

    /**
     * 根据campusId获取是查找展能中的哪个组织机构表
     * @param campusId 组织机构id
     * @return
     */
    public String getCampusTableName(String campusId) {
        String sqlTeacher = "select count(*) from base_dept_teacher where ID = '" + campusId + "' UNION SELECT count(*) FROM base_r_group_teacher WHERE GROUPID = '" + campusId + "'";
        String sqlStudent = "select count(*) from base_dept_student where ID = '" + campusId + "'";
        String sqlGroup = "SELECT count(*) FROM base_group_teacher WHERE ID = '" + campusId + "'";
        long resultTeacher = JdbcTemplateUtil.getJdbcTemplate().count(sqlTeacher);
        long resultStudent = JdbcTemplateUtil.getJdbcTemplate().count(sqlStudent);
        long resultGroup = JdbcTemplateUtil.getJdbcTemplate().count(sqlGroup);
        if (resultTeacher > 0 ) {
            return "base_dept_teacher";
        }  if (resultStudent > 0  ) {
            return "base_dept_student";
        }  if (resultGroup > 0  ) {
            return "base_group_teacher";
        } else {
            return "";
        }
    }

    /**
     * 根据userId获取是查找展能中的哪个用户表
     * @param userId 用户id
     * @return
     */
    public String getUserTableNameById(String userId) {
        String re_value = "";
        int id = Integer.parseInt(userId);
        String sqlStudent = "select count(*) from base_student bs where bs.EMPLOYEENO = (select user_code from user where user_id = " + id + ")";
        String sqlTeacher = "select count(*) from base_teacher bt where bt.EMPLOYEENO = (select user_code from user where user_id = " + id + ")";
        long studentCount = JdbcTemplateUtil.getJdbcTemplate().count(sqlStudent);
        long teacherCount = JdbcTemplateUtil.getJdbcTemplate().count(sqlTeacher);
        if (studentCount > 0) {
            re_value = "base_student";
        } else if (teacherCount > 0) {
            re_value = "base_teacher";
        }
        return re_value;
    }

    /**
     * 根据机构id查找机构下所有的子机构
     *
     * @param campusId
     * @return
     */
    public final Directory campusChildSearch(String campusId) {
        if(campusId.equals(ROOT_SCHOOLID)){
            return findClassCampus();
        }
        String sql = "select c.id as id,c.parentid as p_id,c.departmentname as name, c.parentid as pId,c.depttype as typeId from base_dept_student c ";
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        return findChildren(result, campusId);
    }

    /**
     * 查询某机构下所有子机构
     *
     * @param result
     * @param campusId
     * @return
     */
    private final Directory findChildren(List<Map<String, String>> result, String campusId) {
        Directory directory = null;
        Map<String, String> oneCampus = findOneCampus(campusId);
        String pId = oneCampus.get("p_id");
        for (Map<String, String> _result : result) {
            String p_id = _result.get("p_id");
            String id = _result.get("id");
            if (p_id.equals(pId) && id.equals(campusId)) {
                directory = new Directory(_result.get("id"), _result.get("name"),"", _result.get("typeId"),"","","","","",_result.get("pId"),"");
                break;
            }
        }
        if (directory == null) {
            return new Directory("0", "");
        }
        recursiveOrganization(directory.getDirectories(), result, directory.getId());
        return directory;
    }

    /**
     * 通过某一个机构id 机构表 查询机构信息
     *
     * @param campusId
     * @return
     */
    public final Map<String, String> findOneCampus(String campusId) {
        String sql = "select c.id as id,c.parentid as p_id from base_dept_student c where c.id='" + campusId + "'";
        Map<String, String> result = JdbcTemplateUtil.getJdbcTemplate().find(sql);
        return result;
    }

    private void recursiveOrganization(List<Directory> directories, List<Map<String, String>> result, String id) {
        for (Map<String, String> _result : result) {
            String p_id = _result.get("p_id");
            if (p_id.equals(id)) {
                String currentId = _result.get("id");
                Directory directory = new Directory(_result.get("id"), _result.get("name"),"", _result.get("typeId"),"","","","","",_result.get("pId"),"");
                directories.add(directory);
                recursiveOrganization(directory.getDirectories(), result, currentId);
            }
        }
    }

    /**
     * 教室树(学校->楼栋>教室)
     *
     * @return
     */
    public final Directory findRoomTree() {
        StringBuffer sb = new StringBuffer();
        sb.append("select c.id as id,c.`name`,c.p_id ,ct.name as typeName " +
                "from ad_area c LEFT JOIN ad_area_type ct on c.type_id=ct.id " +
                "where ct.id ");
        String ids = getIds(areaType_buildingRoom);
        sb.append(" in (" + ids + ")");
        sb.append(" or ct.p_id = '" + AREA_CLASSROOM_PARENT + "'");
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(sb.toString());
        return findOneType(result, false, "0");
    }

    private String getIds(String str) {
        StringBuffer sb = new StringBuffer();
        String[] temp = str.split(",");
        for (String s : temp) {
            sb.append("'" + s + "',");
        }
        String ids = sb.toString();
        ids = ids.substring(0, ids.length() - 1);
        return ids;
    }

    /**
     * 教师树  学校/教研组/教师
     *
     * @return
     */
    public final Directory findTeacherGroup() {
        String sql = "select c.id as id,c.groupname as name from base_group_teacher c ";
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        for (Map<String, String> map : result) {
            map.put("p_id", "schoolroot");
        }
        return findOneType(result, true, "");
    }

    /**
     * 班级树  学校/学段/年级/班级
     *
     * @return
     */
    public final Directory findClassCampus() {
        String sql = "select c.id as id,c.parentid as p_id,c.departmentname as name,c.depttype as typeId from base_dept_student c ";
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        return findStageGrade(result);
    }

    /**
     * 查询学段>年级>班级树 (展能的学段暂用ad_stages)
     *
     * @param result
     * @return
     */
    private final Directory findStageGrade(List<Map<String, String>> result) {
        Map<String, String> rootMap = findSchoolInfo();
        Directory directory = new Directory(rootMap.get("id"), rootMap.get("name"),"", CampusType.CAMPUS_TYPE_SCHOOL.getType(),"","","","","","","");

        String sql = "select distinct(c.parentid) as p_id, s.name as stageName from base_dept_student c,ad_stages s where c.parentid = s.id and c.depttype= " + CampusType.CAMPUS_TYPE_GRADE.getType();
        List<Map<String, String>> stageList = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        for (Map<String, String> item : stageList) {
            String stageId = item.get("p_id");
            Directory stageDirectory = new Directory(stageId, item.get("stageName"),"", CampusType.CAMPUS_TYPE_STAGE.getType(),"","","","","",ROOT_SCHOOLID,"");
            directory.getDirectories().add(stageDirectory);
            recursiveOrg(stageDirectory.getDirectories(), result, stageId);
        }
        return directory;
    }

    /**
     * 递归查询树
     *
     * @param directories
     * @param result
     * @param id
     */
    private void recursiveOrg(List<Directory> directories, List<Map<String, String>> result, String id) {
        for (Map<String, String> _result : result) {
            String p_id = _result.get("p_id");
            if (p_id.equals(id)) {
                String currentId = _result.get("id");
                Directory directory = new Directory(currentId, _result.get("name"),"", _result.get("typeId"),"","","","","",_result.get("p_id"),"");
                directories.add(directory);
                recursiveOrg(directory.getDirectories(), result, currentId);
            }
        }
    }

    /**
     * 获取教室树和教师分组树
     *
     * @param result
     * @param getUserFlag
     * @param rootPId
     * @return
     */
    private final Directory findOneType(List<Map<String, String>> result, boolean getUserFlag, String rootPId) {
        Directory directory = null;
        for (Map<String, String> _result : result) {
            String p_id = _result.get("p_id");
            if (p_id.equals(rootPId)) {
                directory = new Directory(_result.get("id"), _result.get("name"));
                break;
            } else if (p_id.equals("schoolroot")) {
                //学校>教研组>教师
                Map<String, String> rootMap = findSchoolInfo();
                directory = new Directory(rootMap.get("id"), rootMap.get("name"));
                break;
            }
        }
        if (directory == null) {
            return new Directory("0", "");
        }
        recursiveOrgTeachers(directory.getDirectories(), result, directory.getId(), getUserFlag);
        return directory;
    }


    /**
     * 递归查询树
     *
     * @param directories
     * @param result
     * @param id
     * @param getUserFlag
     */
    private void recursiveOrgTeachers(List<Directory> directories, List<Map<String, String>> result, String id, boolean getUserFlag) {
        for (Map<String, String> _result : result) {
            if (getUserFlag) {
                String currentId = _result.get("id");
                Directory directory = new Directory(currentId, _result.get("name"));
                //查找机构下的所有用户
                List<Map<String, String>> userList = CampusNewDao.INSTANCE.findTeachersByGroup(currentId);
                List<UserInfo> newList = new ArrayList<UserInfo>();
                for (int i = 0; i < userList.size(); i++) {
                    UserInfo userInfo = new UserInfo();
                    userInfo.setUserId(userList.get(i).get("userId"));
                    userInfo.setUserName(userList.get(i).get("userName"));
                    userInfo.setUserRealName(userList.get(i).get("userRealName"));
                    userInfo.setUserNum(userList.get(i).get("userNum"));
                    userInfo.setUserGender(userList.get(i).get("userGender"));
                    userInfo.setUserType(userList.get(i).get("userType"));
                    newList.add(userInfo);
                    directory.setUserList(newList);
                }
                directory.setUserCount(newList.size() + "");
                directories.add(directory);
            } else {
                String p_id = _result.get("p_id");
                if (p_id.equals(id)) {
                    String currentId = _result.get("id");
                    Directory directory = new Directory(currentId, _result.get("name"));
                    directories.add(directory);
                    recursiveOrgTeachers(directory.getDirectories(), result, currentId, getUserFlag);
                }
            }
        }
    }

    /**
     * 查询某个分组下的用户信息
     *
     * @param groupId
     * @return
     */
    public List<Map<String, String>> findTeachersByGroup(String groupId) {
        String sql = "select  u.user_id as userId, u.user_name as userName," +
                "u.user_type as userType,u.user_realname as userRealName,u.user_path as userPath,u.user_avatar as userAvatar," +
                "u.user_gender as userGender,u.user_birthday as userBirthday,u.user_mobile as userMobile,u.user_email as userEmail," +
                "u.user_address as userAddress,u.user_num as userNum,u.user_regdate as userRegdate,u.user_status as userStatus," +
                "u.user_card as userCard,u.user_course as userCourse,u.user_info as userInfo ";
        sql = sql + " from user u LEFT JOIN  base_teacher t on u.user_code = t.employeeno" +
                " LEFT JOIN base_r_group_teacher a ON t.id = a.teacherid" +
                " where a.groupid='" + groupId + "'";
        List<Map<String, String>> list = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        return list;
    }

    /**
     * 根据教室名称或编号获取教室信息
     *
     * @param roomId(两者可都传，也可单传)
     * @param roomName
     * @return
     * @throws ParamException
     */
    public final List<Map<String, String>> campus2roomSearch(String roomId, String roomName) {
        String sql = "select c.id ,c.`name`,c.p_id ,c.type_id,c.number,c.campus,c.isselect " +
                "from ad_area c where ";
        if (null != roomId && null != roomName) {
            sql += "c.id ='" + roomId + "' and c.name = '" + roomName + "'";
        } else if (null != roomId) {
            sql += "c.id ='" + roomId + "'";
        } else {
            sql += "c.name = '" + roomName + "'";
        }
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        return result;
    }

    /**
     * 根据班级名称获取班级信息
     *
     * @param classesName
     * @return
     * @throws ParamException
     */
    public final List<Map<String, String>> campus2classSearch(String classesName) {
        String sql = "select c.id ,c.`departmentname` as name,c.parentid as p_id " +
                "from base_dept_student c where c.departmentname = '" + classesName + "'";
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        return result;
    }


    /**
     * 查询班级下的所有学生信息
     *
     * @param campusId
     * @return
     */
    public List findStudentUser(String campusId) {
        if (campusId != null && !"".equals(campusId)) {
            String[] temp = campusId.split(",");
            String campusIds = "";
            for (String temp_ : temp) {
                campusIds = campusIds + "'" + temp_ + "',";
            }
            campusIds = campusIds.substring(0, campusIds.length() - 1);
            String resultSql = "select u.user_id as userId, u.user_name as userName,u.user_type as userType,u.user_realname as userRealName,u.user_path as userPath," +
                    " u.user_avatar as userAvatar,u.user_gender as userGender,u.user_birthday as userBirthday," +
                    " u.user_mobile as userMobile,u.user_email as userEmail,u.user_num as userNum,u.user_address as userAddress,u.user_regdate as userRegdate,t.type_name as typeName," +
                    " u.user_status as userStatus,bd.ID AS campusId,bd.DEPARTMENTNAME AS campusName from user u ,user_type t , base_student s left join base_dept_student bd on s.departid=bd.id  where u.user_code = s.employeeno and t.id = u.user_type and s.departid in (" + campusIds + ") ";
            return JdbcTemplateUtil.getJdbcTemplate().findList(resultSql);
        }
        else {
            return new ArrayList<>();
        }
    }

    /**
     * 查询某机构下的所有学生信息
     *
     * @param campusId
     * @return
     */
    public List<Map<String, String>> findTeacherUser(String campusId) {
        if (campusId != null && !"".equals(campusId)) {
            String[] temp = campusId.split(",");
            String campusIds = "";
            for (String temp_ : temp) {
                campusIds = campusIds + "'" + temp_ + "',";
            }
            campusIds = campusIds.substring(0, campusIds.length() - 1);
            String resultSql = "SELECT u.user_id AS userId, u.user_name AS userName, u.user_type AS userType, u.user_realname AS userRealName, u.user_path AS userPath, u.user_avatar AS userAvatar, u.user_gender AS userGender, u.user_birthday AS userBirthday, u.user_mobile AS userMobile, u.user_email AS userEmail, u.user_num AS userNum, u.user_address AS userAddress, u.user_regdate AS userRegdate, t.type_name AS typeName, u.user_status AS userStatus,   bd.ID AS campusId,   bd.DEPARTMENTNAME AS campusName  FROM USER u, user_type t,   base_teacher bt,   base_dept_student bd LEFT JOIN base_r_stu_dept_tea br on br.DEPTID=bd.ID WHERE u.user_code = bt.employeeno AND t.id = u.user_type AND bd.ID in ("+campusIds+") AND br.DEPTTYPE = "+"1"+" AND t.id = "+"17"+"";
            return JdbcTemplateUtil.getJdbcTemplate().findList(resultSql);
        } else {
            return new ArrayList<>();
        }
    }


    public Map<String, String> findUserType(String userId) {
        String sql = "select user_type from user where user_id = " + userId + "";
        return JdbcTemplateUtil.getJdbcTemplate().find(sql);
    }



    /**
     * 通过家长id获取学生ids
     *
     * @param parentId
     * @return
     */
    public Map<String, Object> findClassesByParentId(String parentId) {
        String sql = "SELECT ds.id AS classId,ds.departmentname as name FROM base_student s,base_dept_student ds" +
                " WHERE s.departid = ds.id and s.id in(" +
                " SELECT pc.stuid FROM user u,base_parent p,base_n_parent_stu pc" +
                " WHERE u.user_code = p.pno" +
                " AND p.id = pc.parentid" +
                " AND u.user_id = '" + parentId + "' )";
        List<Map<String, String>> classList = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        return processClassList(classList, USERTYPE_STR_PARENT);
    }

    /**
     * 组装班级圈列表接口返回值
     *
     * @param classList
     * @param userTypeStr
     * @return
     */
    private Map<String, Object> processClassList(List<Map<String, String>> classList, String userTypeStr) {
        for (Map<String, String> map : classList) {
            map.put("image", "");
        }
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("classlist", classList);
        retMap.put("usertype", userTypeStr);
        return retMap;
    }

    /**
     * 获取登录用户相关的班级圈列表
     *
     * @param userId   (教师或学生的用户id)
     * @param userType
     * @return
     */
    public final Map<String, Object> findClassesByStuId(String userId, String userType) {
        if ("".equals(userId) || "".equals(userType)) {
            return new HashMap<String, Object>();
        }
        String sql = "";
        String id = "";
        String usertypeStr = "";
        if (userType.equals(USERTYPE_STUDENT)) {
            id = findStuTecIdByUserId(userId, "base_student").get("id");
            sql = "SELECT ds.id AS classId,ds.departmentname as name FROM base_student s,base_dept_student ds " +
                    "where s.departid = ds.id and s.id in (" + id + ")";
            usertypeStr = "USERTYPE_STR_STUDENT";
        } else if (userType.equals(USERTYPE_TEACHER)) {
            id = findStuTecIdByUserId(userId, "base_teacher").get("id");
            sql = "SELECT ds.id AS classId,ds.departmentname as name FROM base_teacher s,base_r_stu_dept_tea dt,base_dept_student ds " +
                    "where dt.teacherid = s.id " +
                    "and dt.deptid = ds.id " +
                    "and s.id='" + id + "'";
            usertypeStr = "USERTYPE_STR_TEACHER";
        } else if (userType.equals(USERTYPE_ADMIN)) {
            usertypeStr = "USERTYPE_STR_ADMIN";
        }
        List<Map<String, String>> retList = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        return processClassList(retList, usertypeStr);
    }

    /**
     * 通过用户id获取相应的学生或教师id
     *
     * @param tableName
     * @return
     */
    public final Map<String, String> findStuTecIdByUserId(String userId, String tableName) {
        String sql = "SELECT s.id FROM " + tableName + " s,user u " +
                "where u.user_code = s.employeeno and u.user_id = " + userId + "";
        return JdbcTemplateUtil.getJdbcTemplate().find(sql);
    }

    /**
     * 获取学校信息 ad_area中的根节点
     *
     * @return
     */
    public final Map<String, String> findSchoolInfo() {
        String sql = "SELECT id,name from ad_area where p_id='0'";
        return JdbcTemplateUtil.getJdbcTemplate().find(sql);
    }


}
