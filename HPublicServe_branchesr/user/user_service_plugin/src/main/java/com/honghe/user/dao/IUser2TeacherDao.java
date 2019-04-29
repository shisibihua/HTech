package com.honghe.user.dao;

import com.honghe.dao.PageData;

import java.util.List;
import java.util.Map;

/**
 * 专家教师的操作类
 *
 * @author zhaowj
 * @create 2017-01-09 09:19
 **/
public interface IUser2TeacherDao {

    /**
     * 设置教师是否是名师 是否是专家
     * (资源平台使用)
     *
     * @param teacherId   教师id
     * @param isFamous    是否是名师（0否, 1是）
     * @param isProfessor 是否是专家（0否, 1是）
     * @return
     */
    boolean setFamousTeacher(String teacherId, String isFamous, String isProfessor);


    /**
     * 查询教师是否是名师 是否是专家
     * (资源平台使用)
     *
     * @param teacherId 教师id
     * @return
     */
    Map<String, String> getIsFamous(String teacherId);

    /**
     * 用户关注其他用户功能
     * (资源平台使用)
     *
     * @param userId      必填，用户id
     * @param focusUserId 必填，要关注的用户id
     * @param focusFlag   必填，0取消关注  1关注
     * @return
     */
    boolean userFocusOtherUser(String userId, String focusUserId, String focusFlag);

    /**
     * 查询某个用户的粉丝或关注
     * (资源平台使用)
     *
     * @param userId    必填，用户id
     * @param queryType 必填，0粉丝,1关注
     * @return
     * @throws Exception
     */
    PageData findFansByUserId(Integer page, Integer pageSize, String userId, String queryType);

    /**
     * 查询某个用户的粉丝或关注数量
     *
     * @param userId 用户id
     * @return
     * @throws Exception
     */
    Map<String, String> countFansByUserId(String userId);

    /**
     * 分页查找名师列表 专家列表
     *
     * @param page
     * @param pageSize
     * @param queryType
     * @param searchWord
     * @param subjectId
     */
    PageData finFamousProfessorList(int page, int pageSize, String queryType, String searchWord, String subjectId, String userIds,String campusId);

    /**
     * 获取粉丝最多的前几名老师 （明星老师）
     * (资源平台)
     *
     * @param topNum
     * @return
     * @throws Exception
     */
    List findStarTeacher(int topNum);

//    /**
//     * 根据教师编号查询 教师信息
//     *
//     * @param teacherNum
//     * @return
//     */
//    Map<String, String> getTeacherInfoByNum(String teacherNum);


//    /**
//     * 根据学生编号查询 学生信息
//     *
//     * @param stuNum
//     * @return
//     */
//    Map<String, String> getStuInfoByNum(String stuNum);
}

