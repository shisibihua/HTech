package com.honghe.user.dao;


import com.honghe.communication.ioc.Command;
import com.honghe.communication.ioc.CommandFactory;
import com.honghe.communication.ioc.Response;
import com.honghe.dao.PageData;
import com.honghe.security.MD5;
import com.honghe.service.proxy.Result;
import com.honghe.service.proxy.ServiceProxyFactory;
import com.honghe.user.cache.User2RoleCacheDao;
import com.honghe.user.cache.UserTypeCacheDao;
import com.honghe.user.util.JdbcTemplateUtil;
import com.honghe.user.util.SaltRandom;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.honghe.user.dao.impl.TeacherUserDao.ISADMIN_N;

/**
 * Created by zhanghongbin on 2015/5/13.
 * �û�������
 */
public class UserDao {

    public final static UserDao INSTANCE = new UserDao();

    private User2RoleCacheDao userRoleRelationDao = new User2RoleCacheDao();

    private static final String DATETEMPLATE = "yyyy-MM-dd HH:mm:ss";

    private UserTypeCacheDao userTypeCacheDao = new UserTypeCacheDao();

    private CampusDao campusDao = new CampusDao();

    private Command storageCommand;
    private Logger logger= Logger.getLogger(UserDao.class);

    protected UserDao() {
        storageCommand = CommandFactory.getInstance("storage");
    }

    public final static String SQL = "select  u.user_id as userId, u.user_name as userName," +
            "u.user_type as userType,u.user_realname as userRealName,u.user_code as userCode,u.user_realname as name,u.user_path as userPath,u.user_avatar as userAvatar," +
            "u.user_gender as userGender,u.user_birthday as userBirthday,u.user_mobile as userMobile,u.user_email as userEmail," +
            "u.user_address as userAddress,u.user_num as userNum,u.user_regdate as userRegdate,u.user_status as userStatus," +
            "u.user_card as userCard,u.user_course as userCourse,u.user_info as userInfo,u.user_isadmin as userIsAdmin,u.user_itoken as userIToken," +
            "a.id as agencyId, a.name as agencyName,a.p_id as pId,a.type_id as agencyLevel, u.user_hres as userRegister " +
            "from user u left join ad_campus2user c2u on u.user_id=c2u.user_id left join ad_campus a on c2u.campus_id=a.id ";

    public final static String USER_TEACHER = "teacher";
    public final static String USER_STUDENT = "student";
    public final static String USER_PARENT = "parent";
    public final static String CAMPUSID = "1";//区级机构id

    /**
     * �����û�����ȡ�û���Ϣ
     *
     * @param name
     * @return
     */
    public Map<String, String> findByName(String name) {
        return JdbcTemplateUtil.getJdbcTemplate().find("select user_id as userId from user where user_name='" + name + "' and (user_status=1 or user_status=0)");
    }

    public Map<String, String> findByLoginName(String name) {
        return JdbcTemplateUtil.getJdbcTemplate().find(SQL + "where (user_name='" + name + "' or user_email='" + name + "' or user_mobile='" + name + "' or user_num='" + name + "') and (user_status=1 or user_status=0)");
    }


    public boolean find(Map<String, Object> userInfo) {
        StringBuilder sb = new StringBuilder();
        if (userInfo.containsKey("userName")) {
            String userName = userInfo.get("userName").toString();
            if (!"".equals(userName)) {
                sb.append("user_name='" + userName + "' or user_mobile='" + userName + "' or user_email='" + userName + "' or user_num='" + userName + "' or ");
            }
        }
        if (userInfo.containsKey("userMobile")) {
            String userMobile = userInfo.get("userMobile").toString();
            if (!"".equals(userMobile)) {
                sb.append("user_name='" + userMobile + "' or user_mobile='" + userMobile + "' or user_email='" + userMobile + "' or user_num='" + userMobile + "' or ");
            }
        }
        if (userInfo.containsKey("userEmail")) {
            String userEmail = userInfo.get("userEmail").toString();
            if (!"".equals(userEmail)) {
                sb.append("user_name='" + userEmail + "' or user_mobile='" + userEmail + "' or user_email='" + userEmail + "' or user_num='" + userEmail + "' or ");
            }
        }
        if (userInfo.containsKey("userNum")) {
            String userNum = userInfo.get("userNum").toString();
            if (!"".equals(userNum)) {
                sb.append("user_name='" + userNum + "' or user_mobile='" + userNum + "' or user_email='" + userNum + "' or user_num='" + userNum + "' or ");
            }
        }
        String where = sb.substring(0, sb.length() - 3);
        String sql = "select count(*) from user where (" + where + ")";
        long count = JdbcTemplateUtil.getJdbcTemplate().count(sql);
        if (count == 0) {
            return false;
        }
        return true;
    }

    public final boolean findExceptThis(Map<String, Object> userInfo) {
        StringBuilder sb = new StringBuilder();
        if (userInfo.containsKey("userName")) {
            String userName = userInfo.get("userName").toString();
            if (!"".equals(userName)) {
                sb.append("user_name='" + userName + "' or user_mobile='" + userName + "' or user_email='" + userName + "' or user_num='" + userName + "' or ");
            }
        }
        if (userInfo.containsKey("userMobile")) {
            String userMobile = userInfo.get("userMobile").toString();
            if (!"".equals(userMobile)) {
                sb.append("user_name='" + userMobile + "' or user_mobile='" + userMobile + "' or user_email='" + userMobile + "' or user_num='" + userMobile + "' or ");
            }
        }
        if (userInfo.containsKey("userEmail")) {
            String userEmail = userInfo.get("userEmail").toString();
            if (!"".equals(userEmail)) {
                sb.append("user_name='" + userEmail + "' or user_mobile='" + userEmail + "' or user_email='" + userEmail + "' or user_num='" + userEmail + "' or ");
            }
        }
        if (userInfo.containsKey("userNum")) {
            String userNum = userInfo.get("userNum").toString();
            if (!"".equals(userNum)) {
                sb.append("user_name='" + userNum + "' or user_mobile='" + userNum + "' or user_email='" + userNum + "' or user_num='" + userNum + "' or ");
            }
        }
        if (sb.toString().length() == 0) {
            return false;
        }
        String userId = userInfo.get("userId").toString();

        String where = sb.substring(0, sb.length() - 3);
        String sql = "select count(*) from user where (" + where + ") and (user_status=1 or user_status=0) and user_id <>" + userId;
        long count = JdbcTemplateUtil.getJdbcTemplate().count(sql);
        if (count == 0) {
            return false;
        }
        return true;
    }

    public String findByUserMobile(String mobile) {
        Map<String, String> result = JdbcTemplateUtil.getJdbcTemplate().find("select u.user_id as userId from user u where u.user_mobile='" + mobile + "' and (u.user_status=1 or u.user_status=0)");
        if (result.isEmpty()) {
            return "";
        }
        if (!result.containsKey("userId")) {
            return "";
        }
        return result.get("userId").toString();
    }

    /**
     * 通过用户手机号获取用户信息
     * @param mobile 手机号
     * @return
     */
    public Map<String,String> findUserByMobile(String mobile){
        Map<String,String> userInfo = JdbcTemplateUtil.getJdbcTemplate().find("select user_id as userId,user_type as userType,user_num as userNum from user where user_mobile='" + mobile + "'");
        return userInfo;
    }
    public String findByUserEmail(String email) {
        Map<String, String> result = JdbcTemplateUtil.getJdbcTemplate().find("select u.user_id as userId from user u where u.user_email='" + email + "' and (u.user_status=1 or u.user_status=0)");
        if (result.isEmpty()) {
            return "";
        }
        if (!result.containsKey("userId")) {
            return "";
        }
        return result.get("userId").toString();
    }

    public String findByUserNum(String userNum) {
        Map<String, String> result = JdbcTemplateUtil.getJdbcTemplate().find("select u.user_id as userId from user u where u.user_num='" + userNum + "' and (u.user_status=1 or u.user_status=0)");
        if (result.isEmpty()) {
            return "";
        }
        if (!result.containsKey("userId")) {
            return "";
        }
        return result.get("userId").toString();
    }

    public List<Map<String, String>> findByRealName(String realName) {
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList("select u.user_id as userId from user u where u.user_realname='" + realName + "' and (u.user_status=1 or u.user_status=0)");
        if (result.size() <= 0) {
            return new ArrayList<Map<String, String>>();
        }
        return result;
    }

    public final boolean hasUserMobile(String mobile) {
        long count = JdbcTemplateUtil.getJdbcTemplate().count("select count(*) from user where user_mobile='" + mobile + "'");
        if (count == 0) {
            return false;
        }
        return true;
    }

    public final boolean hasUserEmail(String email) {
        long count = JdbcTemplateUtil.getJdbcTemplate().count("select count(*) from user where user_email='" + email + "'");
        if (count == 0) {
            return false;
        }
        return true;
    }

    public List<Map<String,String>> getcampusById(String userId){
        String sql = "select distinct c.id, c.p_id, c.name from ad_campus c ,ad_campus2user cu ," +
                "ad_campus2admin ca where (c.id = cu.campus_id and cu.user_id = "+userId+") or " +
                "( c.id = ca.campus_id and ca.user_id = "+userId+")";
        return JdbcTemplateUtil.getJdbcTemplate().findList(sql);
    }

    public final Map<String,String> getUserIsAdmin(String userId){
        String sql = "select distinct u.user_id as userId, u.user_name as userName," +
                "u.user_type as userType,u.user_realname as userRealName,u.user_realname as name," +
                "u.user_path as userPath,u.user_avatar as userAvatar,u.user_gender as userGender," +
                "u.user_birthday as userBirthday,u.user_mobile as userMobile,u.user_email as userEmail," +
                "u.user_address as userAddress,u.user_num as userNum,u.user_regdate as userRegdate," +
                "u.user_status as userStatus,u.user_card as userCard,u.user_course as userCourse," +
                "u.user_info as userInfo,u.user_isadmin as userIsAdmin,cu.campus_id as campusId from user u " +
                "left join ad_campus2admin cu on u.user_id = cu.user_id where u.user_id="+userId;
        return JdbcTemplateUtil.getJdbcTemplate().find(sql);
    }

    public final List<Map<String,String>> getCampusUser(String userId){
        String sql = "select campus_id from ad_campus2admin where user_id ="+userId;
        List<Map<String,String>> campusUser = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        return campusUser;
    }

    /**
     * �����û����������ȡ�û���Ϣ
     *
     * @param name
     * @param password
     * @return
     */
    public final Map<String, Object> find(String name, String password) {
        String querySql = "select  user_id as userId, user_name as userName,user_pwd as userPwd,user_salt as userSalt," +
                "user_type as userType,user_realname as userRealName,user_path as userPath,user_avatar as userAvatar," +
                "user_gender as userGender,user_birthday as userBirthday,user_mobile as userMobile,user_email as userEmail," +
                "user_address as userAddress,user_num as userNum,user_regdate as userRegdate,user_status as userStatus, " +
                "user_card as userCard,user_course as userCourse,user_info as userInfo,user_isAdmin as userIsAdmin,user_itoken as userIToken,user_hres as userRegister from user ";
        Map<String, String> result = JdbcTemplateUtil.getJdbcTemplate().find(querySql + "where (user_name='" + name + "' or user_email='" + name + "' or user_mobile='" + name + "' or user_num='" + name + "') and (user_status=1 or user_status=0)");
        if (result.isEmpty()) {
            return Collections.emptyMap();
        }
        //如果获取的token为null，则返回空
        result.put("userIToken",result.get("userIToken")==null?"":result.get("userIToken").toString());
        String userId = result.get("userId").toString();
        String sql ="select a.campus_id ,c.type_id,c.name from ad_campus2user a ,ad_campus c where a.campus_id = c.id and user_id = "+userId+" ";
        List resultCampus = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        if(resultCampus.size()>0) {
            StringBuffer campusid = new StringBuffer();
            StringBuffer campustype = new StringBuffer();
            StringBuffer campusname = new StringBuffer();
            for (int i = 0; i < resultCampus.size(); i++) {
                Map map = (Map) resultCampus.get(i);
                if(i!=0){
                    campusid.append(",");
                    campustype.append(",");
                    campusname.append(",");
                }
                campusid.append(map.get("campus_id").toString());
                campustype.append(map.get("type_id").toString());
                campusname.append(map.get("name").toString());
            }
            String campusId = campusid.toString();
            String campusType = campustype.toString();
            String campusName = campusname.toString();
            result.put("agencyId", campusId);
            result.put("agencyType", campusType);
            result.put("agencyName", campusName);
        }
        else{
            result.put("agencyId", "");
            result.put("agencyType", "");
            result.put("agencyName", "");
        }
        Map<String,Object> lastResult = new HashMap<>();
        List<Map<String,String>> adminCampusList = Collections.EMPTY_LIST;
        //如果是管理员身份，则查找出管理的组织信息
        if("1".equals(result.get("userIsAdmin"))){
             adminCampusList = this.findAdminCampusList(result.get("userId"));
            List campusId = new ArrayList();
            for (Map campusMap : adminCampusList){
                String id = campusMap.get("agencyId").toString();
                campusId.add(id);
            }
            if ("1".equals(userId)){
                adminCampusList = CampusDao.INSTANCE.getAllCampusInfo();
            }
        }
        lastResult.put("userAdminList",adminCampusList);
        String userSalt = result.get("userSalt").toString();
        password = new MD5().encrypt(password + userSalt);
        if (result.get("userPwd").equalsIgnoreCase(password)) {
            result.remove("userPwd");
            result.remove("userSalt");
            Set<String> stringSet = result.keySet();
            for(String key:stringSet){
                lastResult.put(key,result.get(key));
            }
            return lastResult;
        }
        return Collections.emptyMap();
    }
    public final List<Map<String,String>> findAdminCampusList(String userId){
        String sql = "select a2a.campus_id AS agencyId,name AS agencyName from ad_campus2admin AS a2a left JOIN ad_campus AS aa on a2a.campus_id = aa.id " +
                "WHERE a2a.user_id="+userId;
        return JdbcTemplateUtil.getJdbcTemplate().findList(sql);
    }
    /**
     *给用户设置‘管理员’身份
     * @param teacherIds
     * @param campusIds
     * @return
     */
    public final boolean addUserAdmin(String teacherIds,String campusIds){

        //将机构id进行拼串，查询数据用
        String str = getString(campusIds);
        //查询ad_campus2admin中机构对应的用户
        String adminSql = "select distinct user_id from ad_campus2admin where campus_id in ("+str+")";
        List<Map<String,String>> adminUser = JdbcTemplateUtil.getJdbcTemplate().findList(adminSql);
        //将adminuser中的数据放入list中，主要用于将机构重新分配管理员之前的用户（且该管理员不是其他机构的管理员）置为非管理员身份
        List<String> adminList = new ArrayList();
        for (Map userMap:adminUser){
            adminList.add(userMap.get("user_id").toString());
        }

        String clearSql = "delete from ad_campus2admin where campus_id in("+str+")";


        boolean isDelete =JdbcTemplateUtil.getJdbcTemplate().execute(clearSql);
        //获取teacherids对应的userids
        String uIdStr = "select u.user_id from user u , user_teacher t where u.user_code = t.teacher_code and t.id in ("+teacherIds+")";
        List<Map<String,String>> uIdList = JdbcTemplateUtil.getJdbcTemplate().findList(uIdStr);
        if (isDelete){
            //遍历分配用户给相应的机构
            for (Map<String,String> uIdMap:uIdList){
                String uId = uIdMap.get("user_id").toString();
                String[] campusId = campusIds.split(",");
                //将user表中的是否是管理员字段置为1（1为管理员）
                String updateSql = "update user set user_isAdmin =1 where user_id="+uId;
                JdbcTemplateUtil.getJdbcTemplate().execute(updateSql);
                //判断之前机构的用户是否包含现在分配的用户，如果包含则去掉
                if (adminList.contains(uId)){
                    adminList.remove(uId);
                }
                //向ad_campus2admin中添加机构和用户的对应关系
                for (int i=0;i<campusId.length;i++){
                    JdbcTemplateUtil.getJdbcTemplate().execute("insert into ad_campus2admin(user_id,campus_id) values("+uId+",'"+campusId[i]+"')");
                }
            }
            //分配好用户和机构关系后查询表中的用户,并放入list集合中
            String userSql = "select distinct user_id from ad_campus2admin";
            List<Map<String,String>> userList = JdbcTemplateUtil.getJdbcTemplate().findList(userSql);
            List allUser = new ArrayList();
            for (Map user:userList){
                allUser.add(user.get("user_id").toString());
            }
            //判断之前的机构分配的用户是否是其他机构的管理员，如果不是则直接将user表中的用户是否是管理员置为0（0为非管理员）
            for (String userid:adminList){
                if (!allUser.contains(userid)){
                    JdbcTemplateUtil.getJdbcTemplate().execute("update user set user_isAdmin =0 where user_id ="+userid);
                }
            }

        }


        return true;
    }

    /**
     *取消指定用户的‘管理员’身份
     * @param campusIds
     * @return
     */
    public final boolean deleteUserAmin(String campusIds){
        boolean re_value = false;
        String str = getString(campusIds);
        //获取选定机构的用户管理员
        String adminSql = "select distinct user_id from ad_campus2admin where campus_id in ("+str+")";
        List<Map<String,String>> adminUser = JdbcTemplateUtil.getJdbcTemplate().findList(adminSql);
        //删除选定机构的所有和用户的对应关系
        boolean isDelete = JdbcTemplateUtil.getJdbcTemplate().execute("delete from ad_campus2admin where campus_id in("+str+")");
        if (isDelete){
            List<Map<String,String>> allAdmin = new ArrayList<>();
            List<String> userList = new ArrayList<>();
            //获取删除选定机构对应关系后的所有机构对应的用户id，并放入list集合中
            allAdmin = JdbcTemplateUtil.getJdbcTemplate().findList("select distinct user_id from ad_campus2admin");
            for (Map allUser:allAdmin){
                userList.add(allUser.get("user_id").toString());
            }
            //判断如果此时的用户id中没有选中机构对应的用户了，则将user表中的user_isadmin字段置为0（0为非管理员）
            for (Map userMap:adminUser){
                String userId = userMap.get("user_id").toString();
                if (!userList.contains(userId)){
                    JdbcTemplateUtil.getJdbcTemplate().execute("update user set user_isAdmin = 0 where user_id = "+userId);
                }
            }
            re_value = true;
        }
        return re_value;
    }


    private String getString(String campusIds){
        String str = "";
        if(campusIds!=null&&!"".equals(campusIds)){
            String[] campusArray = campusIds.split(",");
            for (int i=0;i<campusArray.length;i++){
                str+="'"+campusArray[i]+"',";
            }
            if (!"".equals(str)&&str.endsWith(",")){
                str = str.substring(0,str.lastIndexOf(","));
            }

        }
        return str;
    }
//    public final Map<String, String> find(String name, String password, String token) {
//        String _SQL = "select  u.user_id as userId, user_name as userName,user_pwd as userPwd,user_salt as userSalt," +
//                "user_type as userType,user_realname as userRealName,user_path as userPath,user_avatar as userAvatar," +
//                "user_gender as userGender,user_birthday as userBirthday,user_mobile as userMobile,user_email as userEmail," +
//                "user_address as userAddress,user_num as userNum,user_regdate as userRegdate,user_status as userStatus," +
//                "u.user_card as userCard,u.user_course as userCourse,u.user_info as userInfo from user as u,user_token as userToken ";
//        Map<String, String> result = JdbcTemplateUtil.getJdbcTemplate().find(_SQL + "where u.user_id=userToken.user_id and userToken.token='" + token + "' and (user_name='" + name + "' or user_email='" + name + "' or user_mobile='" + name + "' or user_num='" + name + "') and (user_status=1 or user_status=0)");
//        if (result.isEmpty()) {
//            return Collections.emptyMap();
//        }
//        String userSalt = result.get("userSalt").toString();
//        password = new MD5().encrypt(password + userSalt);
//        if (result.get("userPwd").equals(password)) {
//            result.remove("userPwd");
//            result.remove("userSalt");
//            return result;
//        }
//        return Collections.emptyMap();
//    }

    public final boolean find(String name) {
        //修改用户名查重判断为精确查询
        long n = JdbcTemplateUtil.getJdbcTemplate().count("select count(*) from user where user_name = '" + name + "' and (user_status=1 or user_status=0)");
        if (n == 0) {
            return false;
        }
        return true;
    }

    /**
     * 获取同步云平台用户数据
     * @return
     */
    public final List<Map<String, String>> find() {
        String resultSql = "select  u.user_id as userId,u.user_pwd as userPwd,u.user_salt as userSalt, u.user_name as userName," +
                " u.user_type as userType,u.user_realname as userRealName,u.user_path as userPath,u.user_avatar as userAvatar," +
                " u.user_gender as userGender,u.user_birthday as userBirthday,u.user_mobile as userMobile,u.user_email as userEmail," +
                " u.user_address as userAddress,u.user_regdate as userRegdate,u.user_num as userNum,u.user_status as userStatus," +
                " u.user_card as userCard,u.user_course as userCourse,u.user_info as userInfo from user u " +
                " where  u.user_id <>1 and u.user_type in(5,17) and u.user_syn_cloud=0 ";
        return JdbcTemplateUtil.getJdbcTemplate().findList(resultSql);
    }


    /**
     *更新用户云平台同步状态
     * @return
     */
    public boolean update(String userId){
        return JdbcTemplateUtil.getJdbcTemplate().update("update user set user_syn_cloud=1 where user_id=" + userId);
    }


    /**
     * ��ҳ�����û����ƻ�ȡ�����û���Ϣ
     *
     * @param page
     * @param pageSize
     * @param name
     * @param userType 用户类型
     * @param campusId 组织机构id
     * @return
     */
    public final PageData find(int page, int pageSize, String name, int userStatus, String userType, String campusId) {
        String condition = "";
        String connectSql = "from user u left join user_type as t on t.id=u.user_type left join ad_campus2user as c " +
        "on c.user_id = u.user_id left join ad_campus2admin as ac on ac.user_id = u.user_id where " ;
        String eqSql = "";
        if (!"".equals(name)) {
            condition = "and (user_name like '%" + name + "%' or user_realname like '%" + name + "%' or user_num like '%"+name+"%' or user_mobile like '%"+name+"%')";
        }
        String userStatusWhere;
        if (userStatus == -1) {
            userStatusWhere = "";
        } else {
            userStatusWhere = "user_status=" + userStatus + " and ";
        }
        if(!"".equals(userType)){
            switch (userType) {
                case USER_TEACHER:
                    condition += " and (t.id<>18 and t.id<>19)";
                    break;
                case USER_STUDENT:
                    condition += " and t.id = 18";
                    break;
                case USER_PARENT:
                    connectSql = " from user u left join user_type as t on t.id=u.user_type where ";
                    condition += " and t.id = 19";
                    eqSql = " and u.user_code in ( select pc.parent_code from user_pc_relations pc " +
                            "left join user_student s on s.student_code = pc.student_code " +
                            "left join user u on s.student_code = u.user_code " +
                            "left join ad_campus2user ad on u.user_id = ad.user_id where ad.campus_id ='" + campusId + "') ";
                    break;
                default:
                    break;
            }
        }
        if(!"".equals(campusId)&&!USER_PARENT.equals(userType)){
            condition +=" and (c.campus_id = '"+campusId+"' or ac.campus_id = '"+campusId+"')";
        }
        int count = (int) JdbcTemplateUtil.getJdbcTemplate().count("select count(distinct u.user_id) "+connectSql + userStatusWhere + "u.user_id <>1 "+ eqSql + condition);
        int start = PageData.calcFirstItemIndexOfPage(page, pageSize, count);
        String sql = "select distinct u.user_id as userId, u.user_name as userName," +
                " u.user_type as userType,u.user_realname as userRealName,u.user_path as userPath,u.user_avatar as userAvatar," +
                " u.user_gender as userGender,u.user_birthday as userBirthday,u.user_mobile as userMobile,u.user_email as userEmail," +
                " u.user_address as userAddress,u.user_regdate as userRegdate,u.user_num as userNum,u.user_status as userStatus," +
                " u.user_card as userCard,u.user_course as userCourse,u.user_info as userInfo,t.type_name as typeName,u.user_isAdmin as userIsAdmin ";
        String resultSql = sql +connectSql+ userStatusWhere + " u.user_id <>1 "+eqSql + condition + " order by u.user_isAdmin desc,u.user_regdate desc limit " + start + "," + pageSize;
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(resultSql);

        if (result!=null&&result.size()>0&&USER_TEACHER.equals(userType)){
            //查询选定机构下分配的管理员
            List<Map<String,String>> userList = JdbcTemplateUtil.getJdbcTemplate().findList("select distinct user_id from ad_campus2admin where campus_id = '"+campusId+"'");
            List<String> uIdlist = new ArrayList<>();
            if (userList!=null&&userList.size()>0){
                for (Map userMap:userList){
                    uIdlist.add(userMap.get("user_id").toString());
                }
            }

            //判断用户如果不属于选定机构的管理员，则返回给界面值时取消该用户的管理员身份
            for (Map userInfo:result){
                String userid = userInfo.get("userId").toString();
                if (!uIdlist.contains(userid)){
                    userInfo.put("userIsAdmin",ISADMIN_N);
                }
            }
            //如果该机构进行排序，管理员置顶
            result = sortListData(result);

        }

        PageData pageData = new PageData(page, count, pageSize, result);
        return pageData;
    }

    /**
     * 分页获取注册用户
     * @param page 当前页
     * @param pageSize 每页条数
     * @param keyword 关键字
     * @param campusId 机构ID
     * @param userType 用户类型
     * @param userId 登录者ID
     * @return
     */
    public final PageData userRegisterByPage(Integer page,Integer pageSize,String keyword,String campusId,String userType,String userId){
        String condition = "";
        String connectSql = "from user u left join user_type as t on t.id=u.user_type left join ad_campus2user as c " +
                "on c.user_id = u.user_id left join ad_campus as ac on c.campus_id = ac.id where user_status = 0 " ;
        if (!"".equals(keyword)) {
            condition = "and (user_name like '%" + keyword + "%' or user_realname like '%" + keyword + "%' )";
        }
        if(!"".equals(userType)){
            switch(userType){
                case "1":
                    condition += " and t.id = 17 ";
                    break;
                case "2":
                    condition += " and t.id = 18 ";
                    break;
                default:
                    condition += " and (t.id = 18 or t.id = 17) ";
                    break;
            }
        }
        //admin管理员可看到所有注册用户
        if (!"1".equals(userId)){
            condition +=" and c.campus_id = '"+campusId+"' ";
        }
        int count = (int) JdbcTemplateUtil.getJdbcTemplate().count("select count(distinct u.user_id) "+connectSql + " and u.user_status = 0 and u.user_id <>1 " + condition);
        int start = PageData.calcFirstItemIndexOfPage(page, pageSize, count);
        String sql = "select distinct u.user_id as userId, u.user_name as userName," +
                " u.user_type as userType,u.user_realname as userRealName,u.user_path as userPath,u.user_avatar as userAvatar," +
                " u.user_gender as userGender,u.user_mobile as userMobile,u.user_email as userEmail," +
                " u.user_status as userStatus," +
                " u.user_info as userInfo,t.type_name as typeName,ac.id as agencyId,ac.name as agencyName ";
        String resultSql = sql +connectSql+" and u.user_status = 0 and u.user_id <>1 "+ condition + " order by u.user_regdate desc limit " + start + "," + pageSize;
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(resultSql);
        PageData pageData = new PageData(page, count, pageSize, result);
        return pageData;
    }

    private List<Map<String,String>> sortListData(List<Map<String,String>> resourceList){
        if (null != resourceList&& resourceList.size()>0) {
            Collections.sort(resourceList,new Comparator<Map>() {
                @Override
                public int compare(Map o1, Map o2) {
                    int ret = 0;
                    //比较两个对象的顺序，如果前者小于、等于或者大于后者，则分别返回1/0/-1
                    ret = o2.get("userIsAdmin").toString().compareTo(o1.get("userIsAdmin").toString());//顺序的话就用o1.compareTo(o2)即可
                    return ret;
                }
            });
        }
        return resourceList;
    }
    /**
     * 查找指定用户类型指定组织机构的管理员列表
     * @param userType
     * @param campusId
     * @return
     */
    public final PageData findUserAdmin(String userType, String campusId){
        String count_sql = "select count(*) from user ";
        return null;
    }
    public final Map<String, String> find(int userId) {
        Map result = JdbcTemplateUtil.getJdbcTemplate().find(SQL + " where u.user_id=" + userId);
        List<Map<String,String>> resultCampus = new ArrayList<>();
        if(userId==1){
            Map map = new HashMap();
            map.put("campus_id","1");
            map.put("type_id","1");
            resultCampus.add(map);
        }else{
            String sql ="select a.campus_id ,c.type_id from ad_campus2user a ,ad_campus c where a.campus_id = c.id and user_id = "+userId+" ";
            resultCampus = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        }
        if(result.size()>0) {
            if (resultCampus.size() > 0) {
                StringBuffer campusname = new StringBuffer();
                StringBuffer campustype = new StringBuffer();
                for (int i = 0; i < resultCampus.size(); i++) {
                    Map map = (Map) resultCampus.get(i);
                    campusname.append(map.get("campus_id").toString()).append(",");
                    campustype.append(map.get("type_id").toString()).append(",");
                }
                String campusId = campusname.toString();
                String campusType = campustype.toString();
                if (null != campusId && !"".equals(campusId)) {
                    campusId = campusId.substring(0, campusId.length() - 1);
                }
                if (null != campusType && !"".equals(campusType)) {
                    campusType = campusType.substring(0, campusType.length() - 1);
                }
                result.put("campusId", campusId);
                result.put("campusType", campusType);
            }
            else{
                result.put("campusId", "");
                result.put("campusType", "");
            }
        }
        return result;

    }

    public final Map<String, String> findUserInfo(String userNum) {
        String sql = SQL + " where u.user_num='" + userNum +"'";
        Map result = JdbcTemplateUtil.getJdbcTemplate().find(sql);
        return result;

    }


    public final Map<String, String> findByUserId(String userId) {
        String sql = SQL + " where u.user_id='" + userId +"'";
        Map result = JdbcTemplateUtil.getJdbcTemplate().find(sql);
        return result;

    }


    public final List<Map<String, String>> find(String[] userId) {
        StringBuilder sb = new StringBuilder();
        for (String userIds : userId) {
            sb.append(userIds + ",");
        }
        String u = sb.toString().substring(0, sb.length() - 1);
        return JdbcTemplateUtil.getJdbcTemplate().findList(SQL + "where  u.user_id in(" + u + ")");
    }


    /**
     * @param page
     * @param pageSize
     * @param name
     * @param token
     * @return
     */
    public final PageData find(int page, int pageSize, String name, String token) {
        String condition = "";
        if (!"".equals(name)) {
            condition = "and (u.user_name like '%" + name + "%' or u.user_num like '%"+name+"%' or u.user_mobile like '%" + name + "%')";
        }
        String countSql = "select count(*) from user u ,user_user2role u2r,(select * from user_role a where " +
                "a.role_id in(select distinct(c.role_id) from user_role2permission c, " +
                "user_sys2permission d where d.sys_name='" + token + "' and c.permission_id = d.permission_id)) " +
                " r where u.user_id = u2r.user_id and r.role_id = u2r.role_id and u.user_status=1 and  u.user_id <>1 " + condition;
        int count = (int) JdbcTemplateUtil.getJdbcTemplate().count(countSql);
        int start = PageData.calcFirstItemIndexOfPage(page, pageSize, count);
        String resultSql = "select  u.user_id as userId, u.user_name as userName," +
                " u.user_type as userType,u.user_realname as userRealName,u.user_path as userPath,u.user_avatar as userAvatar," +
                " u.user_gender as userGender,u.user_birthday as userBirthday,u.user_mobile as userMobile,u.user_email as userEmail," +
                " u.user_address as userAddress,u.user_regdate as userRegdate,u.user_status as userStatus,user_num as userNum," +
                " u.user_card as userCard,u.user_course as userCourse,u.user_info as userInfo,r.role_id as roleId,r.role_name roleName from user u ,user_user2role u2r," +
                " (select * from user_role a where a.role_id in(select distinct(c.role_id) from user_role2permission c, user_sys2permission d where d.sys_name='" + token + "' and c.permission_id = d.permission_id))" +
                " r where u.user_id = u2r.user_id and r.role_id = u2r.role_id and u.user_status=1 and  u.user_id <>1 " + condition + " order by u.user_regdate desc limit " + start + "," + pageSize;
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(resultSql);
        PageData pageData = new PageData(page, count, pageSize, result);
        return pageData;
    }


    public final int update(String userId, String oldPassword, String password) {
        if (!"".equals(oldPassword)) {
            Map<String, String> userInfo = JdbcTemplateUtil.getJdbcTemplate().find(
                    "select user_pwd as userPwd,user_salt as userSalt  from user where user_id=" + userId);
            if (userInfo.isEmpty()) {
                // 用户不存在
                return 1;
            }
            String userPwd = userInfo.get("userPwd");
            String userSalt = userInfo.get("userSalt");
            if (!new MD5().encrypt(oldPassword + userSalt).equals(userPwd)) {
                return 2;// 旧密码错误
            }
        }
        String salt = SaltRandom.runVerifyCode(6);
        password = new MD5().encrypt(password + salt);
        if (JdbcTemplateUtil.getJdbcTemplate().update("update user set user_pwd='" + password + "',user_salt='" +
                salt + "' where user_id=" + userId)) {
            return 0;// 修改成功
        }
        ;
        return 3; // 未知错误
    }

    public final boolean update(String userId, String userName) {
        if (!"".equals(userName)) {
            return JdbcTemplateUtil.getJdbcTemplate().update("update user set user_name='" + userName + "' where user_id=" + userId);
        } else {
            return false;
        }
    }

    public boolean deleteUser(String userId){
        return JdbcTemplateUtil.getJdbcTemplate().update("delete from user where user_id in (" + userId.trim() + ")");
    }

    public boolean updateUserStatus(String userId, String userStatus) {
        return JdbcTemplateUtil.getJdbcTemplate().update(
                "update user set user_status=" + userStatus.trim() + " where user_id in(" + userId.trim() + ")");
    }

    public boolean updateUserIToken(String iToken,String mobile){
        return JdbcTemplateUtil.getJdbcTemplate().update("update user set user_itoken= '" + iToken +
                "' where user_mobile ='"+mobile+"'");
    }

    public boolean update(Map<String, Object> data) {
        String userId = data.get("userId").toString();
        String updateSql = "";
        if (data.containsKey("userType")) {
            String userType = data.get("userType").toString().trim();
            updateSql += "user_type=" + userType + ",";
        }
        if (data.containsKey("userRealName")) {
            String userRealName = data.get("userRealName").toString().trim();
            updateSql += "user_realname='" + userRealName + "',";
        }
        if (data.containsKey("userName")) {
            String userName = data.get("userName").toString().trim();
            updateSql += "user_name='" + userName + "',";
        }
        if (data.containsKey("userNum")) {
            String userNum = data.get("userNum").toString().trim();
            updateSql += "user_num='" + userNum + "',";
        }
        if (data.containsKey("userAvatar")) {
            String userAvatar = data.get("userAvatar").toString().trim();
            String uploadFilePath = getWebRoot() + "upload_dir" + File.separator + userAvatar;
            String path = "";
            if (storageCommand != null) {
                Map<String, Object> params = new HashMap<>();
                params.put("cmd_op", "save");
                params.put("path", uploadFilePath);
                Response storageResponse = storageCommand.execute(null, params);
                if (storageResponse.getCode() == 0) {
                    path = storageResponse.getResult().toString();
                    if (!"".equals(path)) {
                        updateSql += "user_path='" + path.replaceAll("\\\\", "/") + "',";
                    }
                }
                try {
                    FileUtils.forceDelete(new File(uploadFilePath));
                } catch (Exception e) {

                }
            } else {
                path = uploadFilePath;
                updateSql += "user_path='" + path.replaceAll("\\\\", "/") + "',";
            }

            updateSql += "user_avatar='" + userAvatar + "',";
        }
        if (data.containsKey("userGender")) {
            String userGender = data.get("userGender").toString().trim();
            updateSql += "user_gender=" + userGender + ",";
        }
        if (data.containsKey("userBirthday")) {
            String userBirthday = data.get("userBirthday").toString().trim();
            updateSql += "user_birthday='" + userBirthday + "',";
        }
        if (data.containsKey("userMobile")) {
            String userMobile = data.get("userMobile").toString().trim();
            updateSql += "user_mobile='" + userMobile + "',";
        }

        if (data.containsKey("userEmail")) {
            String userEmail = data.get("userEmail").toString().trim();
            updateSql += "user_email='" + userEmail + "',";
        }
        if (data.containsKey("userAddress")) {
            String userAddress = data.get("userAddress").toString().trim();
            updateSql += "user_address='" + userAddress + "',";
        }
        /*if (data.containsKey("userPwd")) {
            String password = data.get("userPwd").toString().trim();
            if (!"".equals(password)) {
                String salt = SaltRandom.runVerifyCode(6);
                password = new MD5().encrypt(password + salt);
                updateSql += "user_pwd='" + password + "',user_salt='" + salt + "',";
            }
        }*/
        if (data.containsKey("userStatus")) {
            String userStatus = data.get("userStatus").toString().trim();
            updateSql += "user_status=" + userStatus + ",";
        }
        if (data.containsKey("userCard")) {
            String userCard = data.get("userCard").toString().trim();
            updateSql += "user_card='" + userCard + "',";
        }
        if (data.containsKey("userCourse")) {
            String userCourse = data.get("userCourse").toString().trim();
            updateSql += "user_course='" + userCourse + "',";
        }
        if (data.containsKey("userInfo")) {
            String userInfo = data.get("userInfo").toString().trim();
            updateSql += "user_info='" + userInfo + "',";
        }
        if (updateSql.length() == 0) {
            return false;
        }
        updateSql = updateSql.substring(0, updateSql.length() - 1);
//        logger.debug(">>>>>>>>>>>updateSql=>>>>>>>>>>> update user set "+updateSql+" where user_id=" + userId);
        return JdbcTemplateUtil.getJdbcTemplate().update("update user set " + updateSql + " where user_id=" + userId);
    }

    public Map updateImage(String userId, String ret) {

        Map<String, String> params = new HashMap<>();
        params.put("fileName", ret);
        params.put("cmd", "storage");
        params.put("cmd_op", "upload");
        Result result = ServiceProxyFactory.newInstance(ServiceProxyFactory.Type.HTTP).send(params);
        String userPath = "";
        if (result.getCode() == 0) {
            String path = result.getValue().toString();
            userPath = path.replaceAll("\\\\", "/");
            JdbcTemplateUtil.getJdbcTemplate().update("update user set user_avatar='" + ret + "',user_path='" + userPath + "' where user_id=" + userId);
        }
        Map<String, String> map = new HashMap<>();
        map.put("path", userPath);
        return map;
    }

    private final String getWebRoot() {
        String path = this.getClass().getResource("/").getPath();
        int n = path.indexOf("WEB-INF/classes/");
        if (n == -1) {
            return "";
        } else {
            path = path.substring(0, n);
            path = path.replaceAll("%20", " ");
            return path;
        }
    }

    public Map<String, String> userAdminSearchByAgency(String agencyId) {
        String sql = "select u.user_id as userId,u.user_realname as userRealName,u2.agency_id as agencyId " +
                "from user as u,(select u2a.user_id,u2a.agency_id from user2agency as u2a where u2a.agency_id=" +
                "(select ag1.p_id from agency as ag1 where ag1.agency_id=" + agencyId + ")) as u2 " +
                "where u.user_id =u2.user_id and ( u.user_type = 1 or u.user_type=2)";
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        if (result.isEmpty()) {
            return new HashMap<>();
        }
        return result.get(0);
    }

    public final long add(Map<String, Object> data) {
        if (!data.containsKey("userPwd") || !data.containsKey("userType")) {
            return 0;
        }
        String userName = "";
        if (data.containsKey("userName")) {
            userName = data.get("userName").toString().trim();
        }

        String userPwd = data.get("userPwd").toString().trim();
        String userType = data.get("userType").toString().trim();
        if ("".equals(userType)) {
            userType = "null";
        }
        String salt = SaltRandom.runVerifyCode(6);
        userPwd = new MD5().encrypt(userPwd + salt);
        String userRealName = "";
        if (data.containsKey("userRealName")) {
            userRealName = data.get("userRealName").toString().trim();
        }
        String userPath = "";
        if (data.containsKey("userPath")) {
            userPath = data.get("userPath").toString().trim();
        }
        String userAvatar = "";
        if (data.containsKey("userAvatar")) {
            userAvatar = data.get("userAvatar").toString().trim();
            Map<String, String> params = new HashMap<>();
            params.put("fileName", userAvatar);
            params.put("cmd", "storage");
            params.put("cmd_op", "upload");
            Result result = ServiceProxyFactory.newInstance(ServiceProxyFactory.Type.HTTP).send(params);
            if (result.getCode() == 0) {
                String path = result.getValue().toString();
                userPath = path.replaceAll("\\\\", "/");
            }
        }
        String userGender = "0";
        if (data.containsKey("userGender")) {
            userGender = data.get("userGender").toString().trim();
            if ("".equals(userGender)) {
                userGender = "0";
            }
        }
        String userBirthday = "";
        if (data.containsKey("userBirthday")) {
            userBirthday = data.get("userBirthday").toString().trim();
        }
        String userMobile = "";
        if (data.containsKey("userMobile")) {
            userMobile = data.get("userMobile").toString().trim();
        }
        String userEmail = "";
        if (data.containsKey("userEmail")) {
            userEmail = data.get("userEmail").toString().trim();
        }
        String userAddress = "";
        if (data.containsKey("userAddress")) {
            userAddress = data.get("userAddress").toString().trim();
        }
        String userNum = "";
        if (data.containsKey("userNum")) {
            userNum = data.get("userNum").toString().trim();
        }
        if ("".equals(userName)) {
            if (!"".equals(userMobile)) {
                userName = userMobile;
            }
            if (!"".equals(userEmail)) {
                userName = userName + userEmail;
            }
            if (!"".equals(userNum)) {
                userName = userName + userNum;
            }
        }
        String userRegdate = new  SimpleDateFormat(DATETEMPLATE).format(new Date());
        String userStatus = "1";
        if (data.containsKey("userStatus")) {
            userStatus = data.get("userStatus").toString().trim();
        }
        String userCard = "";
        if (data.containsKey("userCard")) {
            userCard = data.get("userCard").toString().trim();
        }
        String userCourse = "";
        if (data.containsKey("userCourse")) {
            userCourse = data.get("userCourse").toString().trim();
        }
        String userInfo = "";
        if (data.containsKey("userInfo")) {
            userInfo = data.get("userInfo").toString().trim();
        }
        String sql = "insert into user (" +
                "user_name," +
                "user_pwd," +
                "user_salt," +
                "user_type," +
                "user_realname," +
                "user_path," +
                "user_avatar," +
                "user_gender," +
                "user_birthday," +
                "user_mobile," +
                "user_email," +
                "user_address," +
                "user_num," +
                "user_regdate," +
                "user_status," +
                "user_card," +
                "user_course," +
                "user_info)values('" + userName + "','" + userPwd + "','" + salt + "'," + userType + ",'" + userRealName + "'," +
                "'" + userPath + "','" + userAvatar + "'," + userGender + ",'" + userBirthday + "','" + userMobile + "','" + userEmail + "','" + userAddress + "','" + userNum + "','" + userRegdate + "'," + userStatus + ",'" + userCard + "','" + userCourse + "','" + userInfo + "')";
        return JdbcTemplateUtil.getJdbcTemplate().add(sql, "user_id");
    }

    public final List<Map<String, String>> find(String name, int userStatus) {
        String condition = "";
        if (!"".equals(name)) {
            condition = "and (user_name like '%" + name + "%' or user_realname like '%" + name + "%')";
        }
        String userStatusWhere;
        if (userStatus == -1) {
            userStatusWhere = "";
        } else {
            userStatusWhere = "user_status=" + userStatus + " and ";
        }
        String resultSql = "select  u.user_id as userId, u.user_name as userName," +
                " u.user_type as userType,u.user_realname as userRealName,u.user_path as userPath,u.user_avatar as userAvatar," +
                " u.user_gender as userGender,u.user_birthday as userBirthday,u.user_mobile as userMobile,u.user_email as userEmail," +
                " u.user_address as userAddress,u.user_regdate as userRegdate,u.user_num as userNum,u.user_status as userStatus," +
                " u.user_card as userCard,u.user_course as userCourse,u.user_info as userInfo,t.type_name as typeName from user u left join user_type as t on t.id=u.user_type where " + userStatusWhere + " u.user_id <>1 " + condition + " order by u.user_regdate desc";
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(resultSql);
        return result;
    }

    public User2RoleDao getUserRoleRelationDao() {
        return this.userRoleRelationDao;
    }

    public UserTypeDao getUserTypeDao() {
        return this.userTypeCacheDao;
    }

    public CampusDao getCampusDao() {
        return this.campusDao;
    }

    /**
     * 通过用户id获取用户身份
     * @param userId
     * @return
     */
    public String getType(String userId)
    {
        String sql = "select user_type from user where user_id = "+userId+"";
        List result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        if(result.size()>0) {
            return result.get(0).toString();
        }
        else {
            return null;
        }
    }

    /**
     *根据身份及机构查询用户列表
     * @param user_type 用户类型 除了学生和家长，其余都认为是老师
     * @param page
     * @param pageSize
     * @param campusId
     * @param searchWord
     * @return
     */
    public PageData findUserByPage(String user_type,Integer page,Integer pageSize,String campusId,String searchWord){
        String condition = "u.user_type = "+user_type;
        if ("0".equals(user_type)){//0表示教师
            condition = "u.user_type <>18 and u.user_type <>19";
        }
        String condition1 = "";
        if(searchWord!=null&&!"".equals(searchWord)){
            condition1 = " and (u.user_name like '%" + searchWord + "%' or u.user_realname like '%" + searchWord + "%' or u.user_num like '%"+searchWord+"%')";
        }
        String resultSql = "select u.user_id as userId, u.user_name as userName,u.user_type as userType,u.user_realname as userRealName" +
                ",u.user_realname as name,u.user_path as userPath,u.user_avatar as userAvatar,u.user_gender as userGender" +
                ",u.user_birthday as userBirthday,u.user_mobile as userMobile,u.user_email as userEmail,u.user_address as userAddress" +
                ",u.user_num as userNum,u.user_regdate as userRegdate,u.user_status as userStatus," +
                "u.user_card as userCard,u.user_course as userCourse,u.user_info as userInfo,user_role.role_name as roleName from user AS u LEFT JOIN " +
                "ad_campus2user ON u.user_id=ad_campus2user.user_id LEFT JOIN user_user2role ON u.user_id = user_user2role.user_id\n" +
                "LEFT JOIN user_role ON user_user2role.role_id = user_role.role_id where "+condition+" AND campus_id = '"+campusId+"' "+condition1+" limit ?,?";
        String countSql = "select count(*) from user AS u LEFT JOIN " +
                "ad_campus2user ON u.user_id=ad_campus2user.user_id where "+condition+" AND campus_id = '"+campusId+"'"+condition1;
        return JdbcTemplateUtil.getJdbcTemplate().findByPage(page,pageSize,countSql,resultSql);
    }

    /**
     * 查询所有的用户信息
     * @return
     */
    public List<Map<String,String>> getAllUserInfo() {
        Map<String,Object> user;
        String sql="select distinct user_id as userId,user_name as userName,user_realname as userRealName," +
                "user_type as userType,user_gender as gender,user_mobile as mobile,user_email as email," +
                "user_status as status from user where user_status=1";
        return JdbcTemplateUtil.getJdbcTemplate().findList(sql);
    }

    public Map<String,String> findStagesByUid(String userId) {
        Map<String,String> stages=new HashMap<>();
        String sql="select stage.id as stageId,stage.name as stageName,u.user_id as userId,u.user_name as userName,u.user_realname as userRealName " +
                "from ad_stages stage " +
                "left join user_teacher t on t.stage_id=stage.id " +
                "left join user u on u.user_code=t.teacher_code " +
                "where u.user_status=1 and u.user_id="+userId;
        if(sql!=null && !"".equals(sql)){
            stages=JdbcTemplateUtil.getJdbcTemplate().find(sql);
        }
        return stages;
    }

    public static void main(String[] args) {
        UserDao userDao=UserDao.INSTANCE;
        userDao.findStagesByUid("4");
    }
}
