package com.honghe.tech.dao;

import com.honghe.spring.MybatisDao;

import java.util.List;
import java.util.Map;

/**
 *
 * @author xinqinggang
 * @date 2018/3/1
 */
@MybatisDao
public interface SchoolyearDao {

    /**
     * 查询所有学年
     * @return 学期或假期集合
     */
     List<Map<String,Object>> findSchoolYearAll();

    /**
     * 保存学年
     * @param params 学期或假期信息
     */
     void saveSchoolYear(Map params);

    /**
     * 根据学年名称查询学年信息
     * @param name
     * @return
     */
     Map<String,Object> selectSchoolYearByName(String name);

}
