package com.honghe.tech.dao;

import com.honghe.spring.MybatisDao;
import com.honghe.tech.entity.Subject;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author xinqinggang on 2018/1/25.
 */
@MybatisDao
public interface SubjectDao {
    /**
     * 根据学科id查询学科名称
     * @param id 学科id
     * @return 学科名称
     */
     String selectSubNameById(int id);

    /**
     * 根据学科id查询学科信息
     * @param id 学科id
     * @return 学科信息
     */
     Map<String,Object> selectSubById(int id);

    /**
     * 根据学段id查询所有学科
     * @param periodId 学段id
     * @return 学科集合
     */
     List<Subject> selectAllSubByPeriodId(int periodId);

    /**
     * 保存学科信息
     * @param params 学科信息
     * @return boolean
     */
     boolean saveSubject(Map<String,String> params);

    /**
     * 根据学科id修改学科信息
     * @param params 学科信息
     * @return boolean
     */
     boolean updateSubject(Map<String,String> params);

    /**
     * 根据学科id删除学科信息
     * @param subjectId 学科id
     * @return boolean
     */
     boolean deleteSubjectBySubjectId(int subjectId);

    /**
     * 根据学段及学科名称查询学科信息
     * @param periodId 学段id
     * @param name 学科名称
     * @result 学科信息
     * @return 返回学科实体
     */
     Subject selectSubByPeriodIdAndName(@Param("periodId") int periodId,@Param("name") String name);
}
