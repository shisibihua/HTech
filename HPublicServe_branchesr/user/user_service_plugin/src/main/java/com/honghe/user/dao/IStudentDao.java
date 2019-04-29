package com.honghe.user.dao;/**
 * Created by lyx on 2017-01-07 0007.
 */

import com.honghe.dao.PageData;
import com.honghe.user.dao.entity.PcRelation;
import com.honghe.user.dao.entity.Student;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 学生的操作类
 *
 * @author lyx
 * @create 2017-01-07 15:19
 **/
public interface IStudentDao {

    /**
     * 新增学生
     *
     * @param student
     * @return
     */
    public int add(Student student);

    /**
     * 删除学生
     *
     * @param studentCode
     * @return
     */
    public boolean delete(String studentCode);

//    /**
//     * 获取所有的学生对象
//     *
//     * @return
//     */
//    public List<Object[]> getStudents();

    /**
     * 批量删除学生
     *
     * @param ids
     * @return
     */
    public boolean deleteStudents(final String ids);
    /**
     * 分页获取学生信息
     * @param page 当前页
     * @param pageSize  每页的条目
     * @param studentName 关键词
     * @param studentCode 内部流水
     * @param campusId 所属机构id
     * @return
     */
    public PageData<Student> getStudentsByPage(int page, int pageSize, String studentName, String studentCode, String campusId);

    /**
     * 获取当前数据库系统编号的最大值
     *
     * @return
     */
    public String getMaxStudentCode();

    /**
     * 根据条件查询学生信息
     * @param studentKey 学生姓名或者学籍号
     * @return
     */
    public List<Student> studentSearchByCondition(String studentKey);

    /**
     *依据系统编号返回对象
     * @param code
     * @return
     */
    public Student getStudentByCode(final String code);

//    /**
//     * 修改学生
//     *
//     * @param student
//     * @return
//     */
//    public int update(Student student);

    /**
     * 根据一个或多个学生内部流水找到学生集合
     *
     * @param codes
     * @return
     */
    public Set<Student> getStudentsByIds(final String codes);

    /**
     * 通过学生学籍号得到流水
     * @param studentNums
     * @return
     */
    public String getCodesByNums(final String studentNums);

    /**
     * 根据学生姓名获取学生信息列表
     * @param name 学生姓名
     * @return
     */
    public List<Map<String, String>> getStudentsByName(String name);


//    /**
//     * 根据学生code获取家长与学生的关系记录
//     * @param studentCode 学生code
//     * @return
//     */
//    public List<PcRelation> getRelationByStu(String studentCode);

}
