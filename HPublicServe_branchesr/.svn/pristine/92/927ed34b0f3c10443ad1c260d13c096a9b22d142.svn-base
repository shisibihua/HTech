package com.honghe.user.dao;/**
 * Created by lyx on 2017-01-07 0007.
 */

import com.honghe.dao.PageData;
import com.honghe.user.dao.entity.Teacher;

import java.util.List;
import java.util.Map;

/**
 * 学生的操作类
 *
 * @author lyx
 * @create 2017-01-07 15:19
 **/
public interface ITeacherDao {
    /**
     * 新增教师
     * @param teacher
     * @return int
     */
    public int add(Teacher teacher);

    /**
     * 修改教师信息
     * @param teacher 教师信息
     * @return boolean
     */
    public boolean updateTeacher(Teacher teacher);
    /**
     * 通过id查询教师信息
     * @param teacherCode 教师内部编码
     * @return
     */
    public Teacher findTeacherByCode(String teacherCode);

    /**
     * 修改教师信息
     * @param sql
     * @return
     */
    public boolean updateTeacherByUserInfo(String sql);

    /**
     * 获取教师对象(支持导出)
     * @return
     */
    public List<Map<String, String>> findTeachers(String teacherName);

    /**
     * 分页获取教师对象
     * @param pageSize 每页的条目
     * @param page 当前页
     * @return
     */
    public PageData<Teacher> getTeacherByPage(int page, int pageSize, final String teacherName, String teacherCode, String campusId, String flag);
    /**
     * 获取当前数据库系统编号的最大值
     *
     * @return
     */
    public String getMaxTeacherCode();

    /**
     *获取职务类型列表
     * @return UserType
     */
    public List getTteacherDuty();

    /**
     *获取学段列表
     * @return UserType
     */
    public List  getStages();

    /**
     * 获取教师科目列表
     * @return Subject
     */
    public List getSubjectInfo(String seasonId);

    /**
     * 删除教师
     *
     * @param teacherCode
     * @return
     */
    public boolean delete(String teacherCode);

    /**
     * 批量删除教师
     * @param userIds 用户id，多个用逗号隔开
     * @return
     * @throws Exception
     */
    public boolean deleteTeachers(final String userIds);

//    /**
//     *依据系统编号返回对象
//     * @param code
//     * @return
//     */
//    public Teacher getTeacherByCode(final String code);

    /**
     * 保存教师与学科关系
     * @param teacher      教师
     * @return
     */
    public boolean saveTeacher2Subject(Teacher teacher);

    /**
     * 判断教师与学科关系是否存在
     * @param teacherCode  教师code
     * @return
     */
    public long exitTeacher2Subject(String teacherCode);

    /**
     * 根据教师code删除教师与学科关系
     * @param teacherCode
     * @return
     */
    public boolean deleteTeacher2Subject(String teacherCode);

}
