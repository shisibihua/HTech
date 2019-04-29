package com.honghe.tech.service;

import net.sf.json.JSON;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 *
 * @author xinqinggang
 * @date 2018/1/25
 */
public interface SubjectService {
    /**
     * 根据学段id查看所有学科信息
     * @param periodId 学段id
     * @return 学科集合
     */
    public List selectAllSubByPeriodId(int periodId);

    /**
     * 根据学科id查询学科名称
     * @param id 学科id
     * @return 学科名称
     */
    public String selectSubNameById(int id);

    /**
     * 查询所有学段
     * @return 学段集合
     */
    public List<Map<String,Object>> getAllPeriod();

    /**
     * 根据学段保存学科信息
     * @param subName 学科名称
     * @param periodId 学段id
     * @param userId 用户id
     * @return true/false
     */
    public Object saveSubjectByPeriodId(String subName,int periodId,int userId);

    /**
     * 更改学科信息
     * @param subName 学科名称
     * @param periodId 学段id
     * @param subId 学科id
     * @param userId 用户id
     * @return true/false
     */
    public Object updateSubject(String subName,int periodId,int subId,int userId);

    /**
     * 根据学科id删除课程
     * @param subId
     * @param userId
     * @return
     */
    public Object delSub(int subId,int userId);

    /**
     * 根据学科id查询学科信息
     * @param id 学科id
     * @return 学科信息
     */
    public Map<String,Object> selectSubById(int id);

    /**
     * 保存学科
     * @param subName 学科名称
     * @param periodId
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    Object saveSub(String subName, int periodId);

    /**
     * 修改学科
     * @param subName 学科名
     * @param periodId
     * @param subId
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    Object updateSub(String subName, int periodId, int subId);
}
