package com.honghe.tech.dao;

import com.honghe.spring.MybatisDao;

import java.util.Map;

/**
 *
 * @author xinqinggang
 * @date 2018/3/1
 */
@MybatisDao
public interface CriteriaDao {

    /**
     * 根据策略id删除信息
     * @param scheduleId 策略id
     */
     void deleteCriteriaByScheduleId(int scheduleId);

    /**
     * 保存作息策略限制条件
     * @param criteria 策略限制条件
     * @return true/false
     */
     boolean saveCriteria(Map<String,Integer> criteria);

    /**
     * 修改作息策略限制条件
     * @param criteria 策略限制条件
     * @return true/false
     */
     boolean updateCriteria(Map<String,Integer> criteria);

}
