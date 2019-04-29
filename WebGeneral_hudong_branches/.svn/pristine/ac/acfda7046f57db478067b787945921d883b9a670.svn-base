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
public interface TermDao {

    /**
     * 根据参数查询学期信息
     * @param params 参数
     * @return 学期或假期集合
     */
     List<Map<String,String>> findInfoByParam(Map params);

    /**
     * 保存学期或假期信息
     * @param params 学期或假期信息
     */
     void saveInfo(Map params);

    /**
     * 修改活动状态
     * @param params 学期或假期信息id及状态
     * @return boolean
     */
     boolean updateInfoStatus(Map  params);

    /**
     * 修改学期或假期信息
     * @param params 学期或假期信息
     */
     void updateInfo(Map params);

    /**
     * 根据参数获取信息数量
     * @param params 参数
     * @return 数量
     */
     int findInfoNumByParam(Map params);

    /**
     * 根据id获取信息
     * @param id 学期或假期id
     * @return 学期或假期信息
     */
     Map<String,Object> findInfoById(int id);

    /**
     * 根据id删除信息
     * @param id 学期或假期id
     */
     void deleteInfoById(int id);

    /**
     * 根据学年id查询学期信息
     * @param schoolyearId 学年id
     * @return
     */
     List<Map<String,Object>> findInfoBySchoolyearId(String schoolyearId);

    /**
     * 查询正在使用的学期
     * @return
     */
     Map<String,Object> findTermUsing();

}
