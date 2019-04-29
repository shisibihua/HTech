package com.honghe.ad.campus.dao;

import com.honghe.ad.Directory;
import com.honghe.ad.area.dao.AreaDao;
import com.honghe.ad.campus.bean.UserInfo;
import com.honghe.ad.excetion.ParamException;
import com.honghe.ad.util.JdbcTemplateUtil;
import com.honghe.dao.PageData;

import java.util.*;

/**
 * Created by zhanghongbin on 2016/7/14.
 */
public final class Campus2UserDao {

    private Campus2UserDao() {

    }

    public final static Campus2UserDao INSTANCE = new Campus2UserDao();
    public final static String CAMPUSTYPE_SCHOOL = "1"; //学校类型
    public final static String CAMPUSTYPE_GRADE = "3";  //年级类型
    public final static String CAMPUSTYPE_CLASSES = "4"; //班级类型
    public final static String CAMPUSTYPE_TEACHGROUP = "5"; //教研组类型
    private final int USERTYPE_STUDENT = 18; //学生身份
    private final int USERTYPE_TEACHER = 17; //老师身份
    private final int USERTYPE_ADMIN = 0; //管理员身份
    private final String AREA_CLASSROOM_PARENT = "6"; //地点类型 教室类型的根节点


    @Deprecated
    public long add(Map<String, Object> data) {
        String campusId = data.get("campusId").toString().trim();
        String userId = data.get("userId").toString().trim();
        String sql = "insert into ad_campus2user(campus_id,user_id) values('" + campusId + "'," + userId + ")";
        return JdbcTemplateUtil.getJdbcTemplate().add(sql, "id");
    }

    public boolean add(String campusId, String... userId) {
        List<String> sql = new ArrayList<>();
        for (String _id : userId) {
            sql.add("insert into ad_campus2user(campus_id,user_id) values('" + campusId + "'," + _id + ")");
        }
        return JdbcTemplateUtil.getJdbcTemplate().execute(sql.toArray(new String[sql.size()]));
    }

    public long findUserCount(String campusId, Object userType) {
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList("select * from ad_campus");
        Set<String> campusIdSet = new HashSet<>();
        campusIdSet.add(campusId);
        recursiveOrganization(campusIdSet, result, campusId);
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (String _campusId : campusIdSet) {
            sb.append("'" + _campusId + "',");
        }
        String where = sb.substring(0, sb.length() - 1) + ")";
        String sql;
        if (userType == null || "".equals(userType)) {
            sql = "select count(*) from ad_campus2user where campus_id in" + where;
        } else {
            sql = "select count(*) from ad_campus2user c2u,user u where campus_id in" + where + " and u.user_type=" + userType.toString() + " and u.user_id=c2u.user_id";
        }
        return JdbcTemplateUtil.getJdbcTemplate().count(sql);
    }

    public List<Map<String, String>> findUserCountByCampusId(String campusId) {
        String[] strList = campusId.split(",");
        String ids = "";
        for (String str : strList) {
            ids = ids + "'" + str + "',";
        }
        ids = ids.substring(0, ids.length() - 1);
        String sql = "select campus_id as campusId,count(campus_id) as count from ad_campus2user where campus_id in (" + ids + ") group by campus_id order by campus_id";
        List<Map<String, String>> list = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        // 统计总数
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            count = count + Integer.parseInt(list.get(i).get("count").toString());
        }
        Map<String, String> map = new HashMap<>();
        map.put("campusId", "total");
        map.put("count", String.valueOf(count));
        list.add(map);
        return list;
    }


    private final void recursiveOrganization(Set<String> campusIdSet, List<Map<String, String>> result, String campusId) {
        for (Map<String, String> _result : result) {
            String p_id = _result.get("p_id");
            if (p_id.equals(campusId)) {
                String id = _result.get("id");
                campusIdSet.add(id);
                recursiveOrganization(campusIdSet, result, id);
            }
        }
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
        String sql = "select u.*,b.name as campusName" +
                " from (" +
                "select a.user_id as userId, a.user_name as userName," +
                "a.user_type as userType,a.user_realname as userRealName,a.user_path as userPath,a.user_avatar as userAvatar," +
                "a.user_gender as userGender,a.user_birthday as userBirthday,a.user_mobile as userMobile,a.user_email as userEmail," +
                "a.user_address as userAddress,a.user_num as userNum,a.user_regdate as userRegdate,a.user_status as userStatus,c2d.campus_id as campusId  from user as a left join ad_campus2user as c2d on a.user_id=c2d.user_id where a.user_num in(" + sb.toString().substring(0, sb.length() - 1) + ")) as u " +
                "left join ad_campus as b on u.campusId=b.id";
        return JdbcTemplateUtil.getJdbcTemplate().findList(sql);
    }

    /**
     * @param userId
     * @return
     */
    public final List<Map<String, String>> findByUserId(String userId) {
        String sql = "select u.*,b.name as campusName" +
                " from (" +
                "select a.user_id as userId, a.user_name as userName," +
                "a.user_type as userType,a.user_realname as userRealName,a.user_path as userPath,a.user_avatar as userAvatar," +
                "a.user_gender as userGender,a.user_birthday as userBirthday,a.user_mobile as userMobile,a.user_email as userEmail," +
                "a.user_address as userAddress,a.user_num as userNum,a.user_regdate as userRegdate,a.user_status as userStatus,c2d.campus_id as campusId  from user as a left join ad_campus2user as c2d on a.user_id=c2d.user_id where a.user_id in(" + userId + ")) as u " +
                "left join ad_campus as b on u.campusId=b.id";
        return JdbcTemplateUtil.getJdbcTemplate().findList(sql);
    }

    /**
     * 获取登录用户相关的班级圈列表
     *
     * @param userId
     * @return
     */
    public final Object findCampusByUserId(String userId,String userType) {
        if ("".equals(userId) || "".equals(userType)) {
            return  new ArrayList<Map<String, String>>();
        }
        String campusType = "'" + CAMPUSTYPE_CLASSES + "'" + ",'" + CAMPUSTYPE_TEACHGROUP + "'"; //班级类型
        String sql = "SELECT b.id AS classId,b.name" +
                " FROM USER u,ad_campus2user cu,ad_campus b" +
                " WHERE u.user_id = cu.user_id" +
                " AND b.id = cu.campus_id" +
                " AND u.user_id IN (" + userId + ")" +
                " AND b.type_id IN (" + campusType + ")";
        List<Map<String, String>> retList= JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        for(Map<String, String> map: retList){
            map.put("image","");
        }
        String usertypeStr = "USERTYPE_STR_PARENT";
        if (Integer.parseInt(userType)== USERTYPE_STUDENT) {
            usertypeStr = "USERTYPE_STR_STUDENT";
        }else if(Integer.parseInt(userType)== USERTYPE_TEACHER){
            usertypeStr = "USERTYPE_STR_TEACHER";
        }else if(Integer.parseInt(userType)== USERTYPE_ADMIN){
            usertypeStr = "USERTYPE_STR_ADMIN";
        }
        return processClassList(retList,usertypeStr);
    }

    /**
     * 根据用户类型查询用户，带用户机构名称
     *
     * @param userType
     * @return
     */
    public final List<Map<String, String>> findByUserType(String userType) {
        String sql = "select u.*,b.name as campusName from (" +
                "select a.user_id as userId, a.user_name as userName,a.user_type as userType,a.user_realname as userRealName,a.user_path as userPath,a.user_avatar as userAvatar,a.user_gender as userGender,a.user_birthday as userBirthday,a.user_mobile as userMobile,a.user_email as userEmail,a.user_address as userAddress,a.user_num as userNum,a.user_regdate as userRegdate,a.user_status as userStatus,c2d.campus_id as campusId " +
                " from user as a left join ad_campus2user c2d on a.user_id=c2d.user_id LEFT JOIN user_type t on a.user_type=t.id where t.type_name='" + userType + "') as u left join ad_campus as b on u.campusId=b.id";
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
        String loginNameWhere = "";
        if (loginName != null && !loginName.toString().equals("")) {
            loginNameWhere = " and u.user_realname like '%" + loginName + "%'";
        }
        String userTypeWhere = "";
        if (userType != -1) {
            userTypeWhere = " and u.user_type=" + userType;
        }
        String countSql = "select count(*) from ad_campus2user as c2u,user as u where campus_id='" + campusId + "' and c2u.user_id=u.user_id " + userTypeWhere + loginNameWhere;
        String resultSql = "select a.*,b.type_name as typeName from " +
                "(select u.user_id as userId, u.user_name as userName,u.user_type as userType,u.user_realname as userRealName,u.user_path as userPath,u.user_avatar as userAvatar,u.user_gender as userGender,u.user_birthday as userBirthday," +
                "u.user_mobile as userMobile,u.user_email as userEmail,u.user_num as userNum,u.user_address as userAddress,u.user_regdate as userRegdate,u.user_status as userStatus from user as u," +
                "(SELECT user_id FROM ad_campus2user where campus_id='" + campusId + "') as b where u.user_id=b.user_id " + userTypeWhere + loginNameWhere + ") a " +
                "left join user_type b on b.id= a.userType limit ?,?";
        return JdbcTemplateUtil.getJdbcTemplate().findByPage(page, pageSize, countSql, resultSql);
    }

    public Map<String, String> find(String userId, String campusType) {
        String where = "";
        if (campusType != "") {
            where = "and t.id=" + campusType + "";
        }
        String sql = "select a.id as campusId,a.name as campusName from ad_campus a LEFT JOIN ad_campus_type t on a.type_id=t.id LEFT JOIN ad_campus2user u on a.id=u.campus_id where u.user_id = " + userId + " " + where + "";
        return JdbcTemplateUtil.getJdbcTemplate().find(sql);
    }

    public Map<String, String> findUserType(String userId) {
        String sql = "select user_type from user where user_id = " + userId + "";
        return JdbcTemplateUtil.getJdbcTemplate().find(sql);
    }

    public PageData findUser(int page, int pageSize, String loginName) {
        String condition = "";
        if (!loginName.equals("")) {
            condition = "and (user_name like '%" + loginName + "%' or user_realname like '%" + loginName + "%')";
        }
        String countSql = "select count(*) from user where user_status=1 and user_id <>1 and user_id not in(select user_id from ad_campus2user) " + condition;
        String resultSql = "select  u.user_id as userId, u.user_name as userName," +
                " u.user_type as userType,u.user_realname as userRealName,u.user_path as userPath,u.user_avatar as userAvatar," +
                " u.user_gender as userGender,u.user_birthday as userBirthday,u.user_mobile as userMobile,u.user_email as userEmail," +
                " u.user_address as userAddress,u.user_regdate as userRegdate,u.user_status as userStatus,t.type_name as typeName from user u left join user_type as t on t.id=u.user_type where u.user_status=1 and  u.user_id <>1 and user_id not in(select user_id from ad_campus2user) " + condition + " order by u.user_regdate desc limit ?,?";
        return JdbcTemplateUtil.getJdbcTemplate().findByPage(page, pageSize, countSql, resultSql);
    }

    public PageData findTS(int page, int pageSize, String searchName, String campusType, String campusId, String stageId, String tsFlag) {
        String condition = "";
        if (!searchName.equals("")) {
            condition = "and (user_name like '%" + searchName + "%' or user_realname like '%" + searchName + "%')";
        }
        String countSql = "";
        String resultSql = "";
        String queryColumn = " DISTINCT u.user_id as userId, u.user_name as userName," +
                " u.user_type as userType,u.user_realname as userRealName,u.user_path as userPath,u.user_avatar as userAvatar," +
                " u.user_gender as userGender,u.user_birthday as userBirthday,u.user_mobile as userMobile,u.user_email as userEmail," +
                " u.user_address as userAddress,u.user_regdate as userRegdate,u.user_status as userStatus,ty.type_name as typeName";
        if (tsFlag.equals("ST")) { //学生
            countSql = "select count(*) from user u,user_type ty, user_student s where u.user_code = s.student_code" +
                    " and u.user_type = ty.id" +
                    " and u.user_id <>1" +
                    " and u.user_id not in(select user_id from ad_campus2user) " + condition;
            resultSql = countSql.replace("count(*)", queryColumn);
        } else if (tsFlag.equals("TE")) { //教师
            countSql = "select count(DISTINCT u.user_id) from user u,user_type ty,user_teacher t,user_teacher2type ut where u.user_code = t.teacher_code" +
                    " and t.teacher_code = ut.teacher_code" +
                    " and u.user_type = ty.id" +
                    " and t.stage_id = " + stageId +
                    " and t.is_job = 1 " +
                    " and u.user_id <>1 " +
                    " and user_id not in(select user_id from ad_campus2user where campus_id  = '" + campusId + "')" + condition;
            if (campusType.equals(CAMPUSTYPE_GRADE)) { //年级类型的机构
                countSql += " and ut.type_id = '15'";
            } else if (campusType.equals(CAMPUSTYPE_SCHOOL)) { //学校类型的机构
                countSql += " and ut.type_id not in ('1','2','5','6','14','15','16','17','18','19','22','23') ";
            }
            resultSql = countSql.replace("count(DISTINCT u.user_id)", queryColumn);
        }
        resultSql += " order by u.user_regdate desc limit ?,?";
        return JdbcTemplateUtil.getJdbcTemplate().findByPage(page, pageSize, countSql, resultSql);
    }

    public List findUser(String campusId, int userType, String searchWord) {
        String loginNameWhere = "";
        if (searchWord != null && !searchWord.toString().equals("")) {
            loginNameWhere = " and u.user_realname like '%" + searchWord + "%'";
        }
        String userTypeWhere = "";
        if (userType != -1) {
            userTypeWhere = " and u.user_type=" + userType;
        }
        String resultSql = "select a.*,b.type_name as typeName from " +
                "(select u.user_id as userId, u.user_name as userName,u.user_type as userType,u.user_realname as userRealName,u.user_path as userPath,u.user_avatar as userAvatar,u.user_gender as userGender,u.user_birthday as userBirthday," +
                "u.user_mobile as userMobile,u.user_email as userEmail,u.user_num as userNum,u.user_address as userAddress,u.user_regdate as userRegdate,u.user_status as userStatus from user as u," +
                "(SELECT user_id FROM ad_campus2user where campus_id='" + campusId + "') as b where u.user_id=b.user_id " + userTypeWhere + loginNameWhere + ") a " +
                "left join user_type b on b.id= a.userType";
        return JdbcTemplateUtil.getJdbcTemplate().findList(resultSql);
    }

    public Map<String, List<Map<String, String>>> findStuByCampusIds(String campusIds, String searchWord) {
        Map<String, List<Map<String, String>>> retMap = new HashMap<String, List<Map<String, String>>>();
        String[] temp = campusIds.split(",");
        for (String campusId : temp) {
            List<Map<String, String>> stuList = findUser(campusId, USERTYPE_STUDENT, searchWord);
            retMap.put(campusId, stuList);
        }
        return retMap;
    }

    public boolean delete(String... id) {
        List<String> sql = new ArrayList<>();
        for (String _id : id) {
            sql.add("delete from ad_campus2user where id=" + _id.trim());
        }
        return JdbcTemplateUtil.getJdbcTemplate().execute(sql.toArray(new String[sql.size()]));
    }

    public boolean delete(String campusId, String... userId) {
        StringBuilder sb = new StringBuilder();
        for (String _userId : userId) {
            sb.append(_userId + ",");
        }
        String where = sb.toString().substring(0, sb.length() - 1);
        return JdbcTemplateUtil.getJdbcTemplate().delete("delete from ad_campus2user where campus_id='" + campusId + "' and user_id in(" + where + ")");
    }

    public List<Map<String, String>> findOtherUserByCampus(String campusId, String users) {
        String sql = "select  u.user_id as userId, u.user_name as userName," +
                "u.user_type as userType,u.user_realname as userRealName,u.user_path as userPath,u.user_avatar as userAvatar," +
                "u.user_gender as userGender,u.user_birthday as userBirthday,u.user_mobile as userMobile,u.user_email as userEmail," +
                "u.user_address as userAddress,u.user_num as userNum,u.user_regdate as userRegdate,u.user_status as userStatus," +
                "u.user_card as userCard,u.user_course as userCourse,u.user_info as userInfo ";
        sql = sql + " from user u LEFT JOIN ad_campus2user a on u.user_id = a.user_id " +
                "where a.campus_id='" + campusId + "'";
        if (users != null && !"".equals(users)) {
            String[] temp = users.split(",");
            String userNums = "";
            for (String temp_ : temp) {
                userNums = userNums + "'" + temp_ + "',";
            }
            userNums = userNums.substring(0, userNums.length() - 1);
            sql = sql + " and u.user_num not in(" + userNums + ")";
        }
        List<Map<String, String>> list = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        return list;
    }

    /**
     * 查询机构下的校区/教研组/年级/班级
     *
     * @param campusType
     * @param campusId
     * @return
     */
    public final Directory findCampusByType(String campusType, String campusId, boolean getUserFlag, boolean areaFlag) {
        String sql = "select c.id as id,c.`name`,c.p_id ,ct.name as typeName " +
                "from ad_campus c LEFT JOIN ad_campus_type ct on c.type_id=ct.id " +
                "where ct.id ";
        String ids = getIds(campusType);
        sql = sql + " in (" + ids + ")";
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        return findOneType(result, getUserFlag, campusId, areaFlag);
    }

    /**
     * 教室树(学校->楼栋>房间)
     *
     * @param campusType
     * @param campusId
     * @return
     */
    public final Directory findRoomTree(String campusType, String campusId, boolean getUserFlag) {
         StringBuffer sb = new StringBuffer();
        sb.append("select c.id as id,c.`name`,c.p_id ,ct.name as typeName " +
                "from ad_area c LEFT JOIN ad_area_type ct on c.type_id=ct.id " +
                "where ct.id ");
        String ids = getIds(campusType);
        sb.append(" in (" + ids + ")");
        sb.append(" or ct.p_id = '" + AREA_CLASSROOM_PARENT + "'");
        sb.append(" or c.id = '1'");
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(sb.toString());
        return findOneType(result, getUserFlag, campusId, true);
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
     * 根据班级名称或编号获取班级信息
     *
     * @param classesName
     * @return
     * @throws ParamException
     */
    public final List<Map<String, String>> campus2classSearch(String classesName) {
        String sql = "select c.id ,c.`name`,c.p_id ,c.type_id,c.number,c.stages_id,c.schoolyear,c.remark " +
                "from ad_campus c where c.name = '" + classesName + "'";
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        return result;
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

    public final Directory campusChildSearch(String campusId) {
        String sql = "select c.id as id,c.`name`,c.p_id ,ct.name as typeName,c.type_id,c.stages_id,c.schoolyear,c.number,c.remark " +
                "from ad_campus c LEFT JOIN ad_campus_type ct on c.type_id=ct.id ";
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        return findChildren(result, campusId);
    }

    public final String findOneCampus(String campusId) {
        String sql = "select c.id as id,c.p_id from ad_campus c where c.id='" + campusId + "'";
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        return result.get(0).get("p_id");
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
        String pId = findOneCampus(campusId);
        for (Map<String, String> _result : result) {
            String p_id = _result.get("p_id");
            String id = _result.get("id");
            if (p_id.equals(pId) && id.equals(campusId)) {
                directory = new Directory(_result.get("id"), _result.get("name"), _result.get("number"), _result.get("type_id"), _result.get("typeName"), _result.get("stagesId"), _result.get("schoolYear"), _result.get("remark"), _result.get("level"), p_id, "");
                break;
            }
        }
        if (directory == null) {
            return new Directory("0", "");
        }
        recursiveOrganization(directory.getDirectories(), result, directory.getId());
        return directory;
    }

    private final Directory findOneType(List<Map<String, String>> result, boolean getUserFlag, String campusId, boolean areaFlag) {
        Directory directory = null;
        for (Map<String, String> _result : result) {
            String p_id = _result.get("p_id");
            String id = _result.get("id");
            String pId = "";
            if (null != campusId && !"".equals(campusId)) {
                if (!areaFlag) {
                    pId = findOneCampus(campusId);
                } else {
                    List<Map<String, String>> areaList = AreaDao.INSTANCE.findByAreaId(campusId);
                    pId = areaList.get(0).get("p_id");
                }
                if (p_id.equals(pId) && campusId.equals(id)) {
                    directory = new Directory(_result.get("id"), _result.get("name"), _result.get("number"), _result.get("type_id"), _result.get("typeName"), _result.get("stagesId"), _result.get("schoolYear"), _result.get("remark"), _result.get("level"), p_id, "");
                    break;
                }
            } else {
                if (p_id.equals("0")) {
                    directory = new Directory(_result.get("id"), _result.get("name"), _result.get("number"), _result.get("type_id"), _result.get("typeName"), _result.get("stagesId"), _result.get("schoolYear"), _result.get("remark"), _result.get("level"), p_id, "");
                    break;
                }
            }
        }
        if (directory == null) {
            return new Directory("0", "");
        }
        recursiveOrgTeachers(directory.getDirectories(), result, directory.getId(), getUserFlag);
        return directory;
    }

    private void recursiveOrganization(List<Directory> directories, List<Map<String, String>> result, String id) {
        for (Map<String, String> _result : result) {
            String p_id = _result.get("p_id");
            if (p_id.equals(id)) {
                String currentId = _result.get("id");
                Directory directory = new Directory(currentId, _result.get("name"), _result.get("number"), _result.get("type_id"), _result.get("typeName"), _result.get("stagesId"), _result.get("schoolYear"), _result.get("remark"), _result.get("level"), p_id, "");
                directories.add(directory);
                recursiveOrganization(directory.getDirectories(), result, currentId);
            }
        }
    }

    private void recursiveOrgTeachers(List<Directory> directories, List<Map<String, String>> result, String id, boolean getUserFlag) {
        for (Map<String, String> _result : result) {
            String p_id = _result.get("p_id");
            if (p_id.equals(id)) {
                String currentId = _result.get("id");
                Directory directory = new Directory(currentId, _result.get("name"));
                //查找机构下的所有用户
                List<Map<String, String>> userList = Campus2UserDao.INSTANCE.findTeachersByCampus(currentId);
                if (getUserFlag) {
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
                }
                directories.add(directory);
                recursiveOrgTeachers(directory.getDirectories(), result, currentId, getUserFlag);
            }
        }
    }

    public List<Map<String, String>> findTeachersByCampus(String campusId) {
        String sql = "select  u.user_id as userId, u.user_name as userName," +
                "u.user_type as userType,u.user_realname as userRealName,u.user_path as userPath,u.user_avatar as userAvatar," +
                "u.user_gender as userGender,u.user_birthday as userBirthday,u.user_mobile as userMobile,u.user_email as userEmail," +
                "u.user_address as userAddress,u.user_num as userNum,u.user_regdate as userRegdate,u.user_status as userStatus," +
                "u.user_card as userCard,u.user_course as userCourse,u.user_info as userInfo ";
        sql = sql + " from user u LEFT JOIN ad_campus2user a on u.user_id = a.user_id " +
                "where a.campus_id='" + campusId + "'";
        List<Map<String, String>> list = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        return list;
    }

    /**
     * 通过机构找到对应的用户
     * @param campusId
     * @param campusId
     * @return
     */
    public final List<Map<String, String>> findTeachersByTeachGroup(String campusId) {
        String sql = "select cu.user_id,c.user_name,c.user_type " +
                "from ad_campus2user cu , user u  where cu.user_id = u.user_id " +
                "and cu.campus_id = " + campusId;
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        if (result.isEmpty()) {
            return null;
        }
        return result;
    }

    /**
     * 通过家长id获取学生ids
     *
     * @param parentId
     * @return
     */
    public String findStuIdByParentId(String parentId) {
        String sql = "SELECT user_id FROM user WHERE user_code IN (" +
                " SELECT pc.student_code FROM user u,user_parent p,user_pc_relations pc" +
                " WHERE u.user_code = p.parent_code" +
                " AND p.parent_code = pc.parent_code" +
                " AND u.user_id = '" + parentId + "' )";
        String retStr = "";
        List<Map<String, String>> stuList = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        StringBuffer sb = new StringBuffer();
        for (Map<String, String> map : stuList) {
            String userId = map.get("user_id");
            sb.append("'" + userId + "',");
        }
        if (sb.length() > 0) {
            retStr = sb.toString().substring(0, sb.toString().lastIndexOf(","));
        }
        return retStr;
    }

    /**
     * 组装班级圈列表接口返回值
     * @param classList
     * @param userTypeStr
     * @return
     */
    private Map<String, Object> processClassList(List<Map<String, String>> classList,String userTypeStr) {
        for (Map<String, String> map : classList) {
            map.put("image", "");
        }
        Map<String,Object> retMap = new HashMap<String,Object>();
        retMap.put("classlist",classList);
        retMap.put("usertype",userTypeStr);
        return retMap;
    }
}
