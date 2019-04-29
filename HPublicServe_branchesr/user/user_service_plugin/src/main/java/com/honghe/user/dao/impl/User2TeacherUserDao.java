package com.honghe.user.dao.impl;


import com.honghe.AbstractUserDao;
import com.honghe.dao.PageData;
import com.honghe.exception.ParamException;
import com.honghe.user.dao.IUser2TeacherDao;
import com.honghe.user.util.JdbcTemplateUtil;
import com.honghe.user.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhaowj
 * @date 2017/4/13
 */
public class User2TeacherUserDao extends AbstractUserDao implements IUser2TeacherDao {

    public static final User2TeacherUserDao INSTANCE = new User2TeacherUserDao();
    /**
     * //普通老师
     */
    private static final String QUERYTYPE_COMMON = "0";
    /**
     * //查询类型名师
     */
    private static final String QUERYTYPE_FAMOUS = "1";
    /**
     * //查询类型专家
     */
    private static final String QUERYTYPE_PROFESSOR = "2";
    /**
     * //查询类型 全部
     */
    private static final String QUERYTYPE_ALL = "-1";
    /**
     * //取消关注
     */
    private static final String FOCUS_FALSE = "0";
    /**
     * //关注
     */
    private static final String FOCUS_TRUE = "1";
    /**
     * user表的所有列，因为使用频繁故提出公用字段
     */
    private static final String QUERYCOLUMN = "u.user_id as userId, u.user_name as userName," +
            " u.user_realname as userRealName,u.user_path as userPath,u.user_avatar as userAvatar," +
            " u.user_gender as userGender,u.user_mobile as userMobile,u.user_email as userEmail," +
            " u.user_address as userAddress,u.user_regdate as userRegdate,u.user_status as userStatus," +
            " u.user_info as userInfo,concat(c.name,s.name) as subjectName,t2s.subject_id as subjectId,t.id as teacherId";

    /**
     * 设置教师是否是名师 是否是专家
     * (资源平台使用)
     *
     * @param teacherId   教师id
     * @param isFamous    是否是名师（0否, 1是）
     * @param isProfessor 是否是专家（0否, 1是）
     * @return
     */
    @Override
    public boolean setFamousTeacher(String teacherId, String isFamous, String isProfessor) {
        String sql = "select count(*) from user_famous where teacher_id = '" + teacherId + "'";
        long count = JdbcTemplateUtil.getJdbcTemplate().count(sql);
        StringBuilder sb = new StringBuilder();
        if (count > 0) {
            sb.append("update user_famous set");
            if (!StringUtil.isEmpty(isFamous) && !StringUtil.isEmpty(isProfessor)) {
                sb.append(" is_famous = " + isFamous);
                sb.append(",is_professor = " + isProfessor);
            } else if (!StringUtil.isEmpty(isProfessor)) {
                sb.append(" is_professor = " + isProfessor);
            } else if (!StringUtil.isEmpty(isFamous)) {
                sb.append(" is_famous = " + isFamous);
            }
            sb.append(" where teacher_id = '" + teacherId + "'");
        } else {
            isFamous = isFamous == null ? "0" : isFamous;
            isProfessor = isProfessor == null ? "0" : isProfessor;
            sb.append("insert into user_famous values('" + teacherId + "'," + isFamous + "," + isProfessor + " )");
        }
        return JdbcTemplateUtil.getJdbcTemplate().execute(sb.toString());
    }


    /**
     * 查询教师是否是名师 是否是专家
     * (资源平台使用)
     *
     * @param teacherId 教师id
     * @return
     */
    @Override
    public Map<String, String> getIsFamous(String teacherId) {
        String sql = "select is_famous, is_professor from user_famous where teacher_id = '" + teacherId + "'";
        return JdbcTemplateUtil.getJdbcTemplate().find(sql);
    }

    /**
     * 用户关注其他用户功能
     * (资源平台使用)
     *
     * @param userId      必填，用户id
     * @param focusUserId 必填，要关注的用户id
     * @param focusFlag   必填，0取消关注  1关注
     * @return
     */
    @Override
    public boolean userFocusOtherUser(String userId, String focusUserId, String focusFlag) {
        try {
            StringBuilder sb = new StringBuilder();
            if (focusFlag.equals(FOCUS_FALSE)) {
                sb.append("delete from user_focus_fans where user_id=" + userId + " and focus_userid=" + focusUserId);
            } else if (focusFlag.equals(FOCUS_TRUE)) {
                sb.append("insert into user_focus_fans (user_id, focus_userid) values(" + userId + "," + focusUserId + " )");
            }
            return JdbcTemplateUtil.getJdbcTemplate().execute(sb.toString());
        } catch (Exception e) {
            logger.error("用户设置关注失败", e);
            return false;
        }
    }

    /**
     * 查询某个用户的粉丝或关注
     * (资源平台使用)
     *
     * @param userId    必填，用户id
     * @param queryType 必填，0粉丝,1关注
     * @return
     * @throws Exception
     */
    @Override
    public PageData findFansByUserId(Integer page, Integer pageSize, String userId, String queryType) {
        PageData pageData = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select count(*) from user_focus_fans c,user u,ad_campus ac,ad_campus2user acu where u.user_id = acu.user_id and acu.campus_id = ac.id and ");
            if (queryType.equals(FOCUS_FALSE)) { //粉丝列表
                sb.append("u.user_id = c.user_id ");
                sb.append("and c.focus_userid = '" + userId + "' ");
            } else if (queryType.equals(FOCUS_TRUE)) { //关注列表
                sb.append("u.user_id = c.focus_userid ");
                sb.append("and c.user_id = '" + userId + "' ");
            }
            String countSql = sb.toString();
            String queryStr = " u.user_id as userId,u.user_realname as userRealName,u.user_path as userPath,ac.name as campusName ";
            String resultSql = countSql.replace("count(*)", queryStr) + " order by u.user_regdate desc limit ?,?";
            pageData = JdbcTemplateUtil.getJdbcTemplate().findByPage(page, pageSize, countSql, resultSql);
            List<Map<String, String>> result = pageData.getItems();
            Map<String, String> school = findSchoolInfo();
            for (Map<String, String> map : result) {
                Map<String, String> countMap = countFansByUserId(map.get("userId"));
                map.put("fansCount", countMap.get("fansCount"));
                map.put("focusCount", countMap.get("focusCount"));
            }
        } catch (Exception e) {
            logger.error("查询某个用户的粉丝或关注列表失败", e);
        }
        return pageData;
    }

    /**
     * 查询当前用户是否关注某个老师
     * (资源平台使用)
     *
     * @param userId    必填，用户id
     * @param teacherId 必填，教师id
     * @return
     * @throws ParamException
     */
    //todo duijie
    public boolean findUserFocusTeacher(String userId, String teacherId) {
        boolean ret = false;
        String sql = "select count(*) from user_focus_fans f where f.focus_userid =" +
                "(SELECT u.user_id FROM USER u,user_teacher t WHERE u.user_code = t.teacher_code" +
                " and t.id = '" + teacherId + "')" +
                " and f.user_id = " + userId;
        long count = JdbcTemplateUtil.getJdbcTemplate().count(sql);
        if (count > 0) {
            ret = true;
        }
        return ret;
    }

    /**
     * 查询某个用户的粉丝或关注数量
     *
     * @param userId 用户id
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, String> countFansByUserId(String userId) {
        StringBuilder sb = new StringBuilder();
        sb.append("select count(*) from user_focus_fans c where c.focus_userid = '" + userId + "' ");
        long fansCount = JdbcTemplateUtil.getJdbcTemplate().count(sb.toString());
        sb.setLength(0);
        sb.append("select count(*) from user_focus_fans c where c.user_id = '" + userId + "' ");
        long foucusCount = JdbcTemplateUtil.getJdbcTemplate().count(sb.toString());
        Map<String, String> retMap = new HashMap<>();
        retMap.put("fansCount", String.valueOf(fansCount));
        retMap.put("focusCount", String.valueOf(foucusCount));
        return retMap;
    }


    /**
     * 分页查找名师列表 专家列表
     *
     * @param page
     * @param pageSize
     * @param queryType
     * @param searchWord
     * @param subjectId
     * @param userIds
     */
    @Override
    public final PageData finFamousProfessorList(int page, int pageSize, String queryType, String searchWord, String subjectId, String userIds, String campusId) {
        String condition = "";
        String realNameWhere = "";
        String subjectWhere = "";
        String userIdsWhere = "";
        if (queryType.equals(QUERYTYPE_FAMOUS)) { //查询名师
            condition = " and f.is_famous = 1";
        } else if (queryType.equals(QUERYTYPE_PROFESSOR)) { //查询专家
            condition = " and f.is_professor = 1";
        }
        if (!StringUtil.isEmpty(searchWord)) {
            realNameWhere = " and u.user_realname like '%" + searchWord + "%'";
        }
        if (!StringUtil.isEmpty(subjectId)) {
            subjectWhere = " and t2s.teacher_code = t.teacher_code and t2s.subject_id = '" + subjectId + "'";
        }
        if (!StringUtil.isEmpty(userIds)) {
            userIdsWhere = " and u.user_id not in(" + userIds + ")";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("select count(*) from user u left join user_teacher t on t.teacher_code = u.user_code ");
        sb.append("left join user_teacher2subject t2s on t2s.teacher_code = t.teacher_code ");
        sb.append("left join classification_subject s on s.id = t2s.subject_id ");
        sb.append("left join ad_stages c on c.id = s.season_id ");
        if (queryType.equals(QUERYTYPE_FAMOUS) || queryType.equals(QUERYTYPE_PROFESSOR)) { //查询全部
            sb.append(" inner join user_famous f on f.teacher_id = t.id");
        }
        if (null != campusId && !"".equals(campusId)) {
            sb.append(" ,ad_campus2user acu ");
        }
        sb.append(" where 1=1 and u.user_status = 1 and u.user_id <> 1  " + condition + subjectWhere + realNameWhere + userIdsWhere);
        if (null != campusId && !"".equals(campusId)) {
            sb.append(" and u.user_id = acu.user_id and acu.campus_id = '" + campusId + "'");
        }
        String countSql = sb.toString();
        String resultSql = countSql.replace("count(*)", QUERYCOLUMN) + " order by u.user_regdate desc limit ?,?";
        PageData pageData = JdbcTemplateUtil.getJdbcTemplate().findByPage(page, pageSize, countSql, resultSql);
        List<Map<String, String>> result = pageData.getItems();
        for (int i = result.size() - 1; i >= 0; i--) {
            Map<String, String> item = result.get(i);
            String userId = item.get("userId");
            String sql = "select f.is_famous,f.is_professor from user u,user_teacher t, user_famous f where u.user_code = t.teacher_code " +
                    " and f.teacher_id = t.id and user_id = " + userId;
            Map<String, String> map = JdbcTemplateUtil.getJdbcTemplate().find(sql);
            String schoolSql = "select c.id,c.name from ad_campus c left join ad_campus2user cu on c.id=cu.campus_id where cu.user_id=" + userId;
            Map<String, String> user2campus = JdbcTemplateUtil.getJdbcTemplate().find(schoolSql);
            item.put("campusId", user2campus.get("id"));
            item.put("campusName", user2campus.get("name"));
            item.put("teacherType", QUERYTYPE_ALL);

            if (!map.isEmpty()) {
                if (("1").equals(map.get("is_professor"))) {
                    item.put("teacherType", QUERYTYPE_PROFESSOR);
                } else if (("1").equals(map.get("is_famous"))) {
                    item.put("teacherType", QUERYTYPE_FAMOUS);
                }
            }
        }
        return pageData;
    }

    /**
     * 分页查找名师列表 专家列表
     *
     * @param page
     * @param pageSize
     * @param searchWord
     * @param subjectId
     */
    //todo duijie
    public final PageData findCommonTeasList(int page, int pageSize, String searchWord, String subjectId, String campusId) {
        String realNameWhere = "";
        String subjectWhere = "";
        if (!StringUtil.isEmpty(searchWord)) {
            realNameWhere = "and u.user_realname like '%" + searchWord + "%' ";
        }
        if (!StringUtil.isEmpty(subjectId)) {
            subjectWhere = " and t2s.teacher_code = t.teacher_code and t2s.subject_id = '" + subjectId + "'";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("select count(*) from user u,user_teacher t " +
                "left join user_teacher2subject t2s on t2s.teacher_code = t.teacher_code " +
                "left join classification_subject s on s.id = t2s.subject_id " +
                "left join ad_stages c on c.id = s.season_id,ad_campus2user acu where u.user_status = 1 and t.teacher_code = u.user_code  ");
        sb.append(subjectWhere + realNameWhere);
        sb.append("and u.user_status = 1 and u.user_id <> 1 and t.id not in(select f.teacher_id from user_famous f where f.is_famous = 1 or f.is_professor = 1) ");
        if (null != campusId || !"".equals(campusId)) {
            sb.append("and u.user_id = acu.user_id and acu.campus_id = '" + campusId + "'");
        }
        String countSql = sb.toString();
        String resultSql = countSql.replace("count(*)", QUERYCOLUMN) + " order by u.user_regdate desc limit ?,?";
        PageData pageData = JdbcTemplateUtil.getJdbcTemplate().findByPage(page, pageSize, countSql, resultSql);
        List<Map<String, String>> result = pageData.getItems();
        for (int i = result.size() - 1; i >= 0; i--) {
            Map<String, String> item = result.get(i);
            String userId = item.get("userId");
            String sql = "select c.id,c.name from ad_campus c left join ad_campus2user cu on c.id=cu.campus_id where cu.user_id=" + userId;
            Map<String, String> user2campus = JdbcTemplateUtil.getJdbcTemplate().find(sql);
            item.put("campusId", user2campus.get("id"));
            item.put("campusName", user2campus.get("name"));
            item.put("teacherType", QUERYTYPE_COMMON);
        }
        return pageData;
    }

    /**
     * 获取学校信息 ad_area中的根节点
     *
     * @return
     */
    public final Map<String, String> findSchoolInfo() {
        String sql = "SELECT id,name from ad_area where p_id='1'";
        return JdbcTemplateUtil.getJdbcTemplate().find(sql);
    }

    /**
     * 获取粉丝最多的前几名老师 （明星老师）
     * (资源平台)
     *
     * @param topNum
     * @return
     * @throws Exception
     */
    @Override
    public final List findStarTeacher(int topNum) {
        String resultSql = "select u.user_id as userId, u.user_name as userName," +
                " u.user_realname as userRealName,u.user_path as userPath,u.user_avatar as userAvatar," +
                " u.user_status as userStatus,u.user_info as userInfo,c.id as campusId,c.name as campusName" +
                " from user u" +
                " left join ad_campus2user cu on cu.user_id = u.user_id" +
                " left join ad_campus c on c.id = cu.campus_id" +
                " inner join user_focus_fans f on f.focus_userid = u.user_id" +
                " group by focus_userid order by count(focus_userid) desc limit 0," + topNum;
        return JdbcTemplateUtil.getJdbcTemplate().findList(resultSql);
    }

//    /**
//     * 根据教师编号查询 教师信息
//     *
//     * @param teacherNum
//     * @return
//     */
//    @Override
//    //todo 根据教师编号查询教师
//    public Map<String, String> getTeacherInfoByNum(String teacherNum) {
//        String sql = "select id,name from base_teacher where employeeno='" + teacherNum + "'";
//        return JdbcTemplateUtil.getJdbcTemplate().find(sql);
//    }

//    /**
//     * 根据学生编号查询 学生信息
//     *
//     * @param stuNum
//     * @return
//     */
//    @Override
//    public Map<String, String> getStuInfoByNum(String stuNum) {
//        String sql = "select s.id,s.name,d.parentid as gradeId from base_student s,base_dept_student d where s.departid = d.id and employeeno='" + stuNum + "'";
//        return JdbcTemplateUtil.getJdbcTemplate().find(sql);
//    }

    /**
     * 获取科目信息
     * （资源平台）
     *
     * @return
     */
    public List<Map<String, String>> getSubjects() {
        String sql = "select s.id as subject_id,CONCAT(t.name,s.name) as subject_name,s.season_id as subject_parent_id " +
                "from classification_subject s,ad_stages t where s.season_id = t.id ";
        return JdbcTemplateUtil.getJdbcTemplate().findList(sql);
    }
}
