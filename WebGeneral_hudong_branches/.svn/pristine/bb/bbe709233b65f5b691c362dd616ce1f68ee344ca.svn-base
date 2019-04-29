package com.honghe.tech.dao;

import com.honghe.spring.MybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author xinqinggang on 2018/3/1.
 */
@MybatisDao
public interface ClasstimeDao {

    /**
     * 批量保存作息策略时间表
     * @param list 作息策略集合
     */
     void saveClassTimes(List<Map<String,String>> list);

    /**
     * 根据策略id删除课节信息
     * @param scheduleId 策略id
     */
     void deleteClassTimeByScheduleId(int scheduleId);

    /**
     * 根据策略id查看课节信息
     * @param schduleId 策略id
     * @return 课节信息列表
     */
     List<Map<String,Object>> selectClassTimeByScheduleId(int schduleId);

    /**
     * 根据策略id查询指定时间内的作息
     * @param scheduleId 策略id
     * @param day1 开始第几天
     * @param day2 结束第几天
     * @return
     */
     List<Map<String,Object>> selectClassTimeDuringDayByScheduleId(@Param("scheduleId")int scheduleId,@Param("startWeekDay") int day1,@Param("endWeekDay")int day2);

    /**
     * 根据课节id获取课节时间
     * @param classtimeId
     * @return
     */
     Map<String,Object> selectClassTimeByClasstimeId(String classtimeId);

}
