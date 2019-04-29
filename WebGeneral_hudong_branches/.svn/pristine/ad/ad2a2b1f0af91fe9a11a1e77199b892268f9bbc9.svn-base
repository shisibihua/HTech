package com.honghe.tech.dao;

import com.honghe.spring.MybatisDao;
import com.honghe.tech.entity.Schedule;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *
 * @author xinqinggang
 * @date 2018/02/02
 * 作息策略
 */
@MybatisDao
public interface ScheduleDao {

    /**
     * 查询正在启用的作息策略(已弃用，该接口禁用)
     * @return 策略id
     */
     int getScheduleId();

    /**
     * 根据状态查询作息策略
     * @param status  状态 0、关闭；1启动
     * @return 作息策略
     */
     List<Map<String,Object>> getScheduleByStatus(int status);

    /**
     * 根据作息策略id查询作息策略
     * @param scheduleId   作息策略id
     * @return  策略
     * @author caoqian
     */
     Map<String,Object> getScheduleByScheduleId(int scheduleId);

    /**
     * 查询所有作息策略
     * @return  策略
     */
     List<Map<String,String>> getScheduleAll();

    /**
     * 保存作息策略
     * @param name   作息策略id
     * @return boolean     true:保存成功;false:保存失败
     * @author caoqian
     */
     boolean saveSchedule(String name);

    /**
     * 根据作息策略id修改策略是否启用
     * @param scheduleId    策略id
     * @param isEnable      0：禁用;1:启用
     * @return boolean      true:修改成功;false:修改失败
     * @author caoqian
     */
     boolean updateScheduleStatusByScheduleId(@Param("scheduleId") int scheduleId,@Param("isEnable") int isEnable);


    /**
     * 根据作息策略id修改策略名称
     * @param scheduleId    策略id
     * @param name     策略名称
     * @return boolean      true:修改成功;false:修改失败
     */
     boolean updateScheduleNameByScheduleId(@Param("scheduleId") int scheduleId,@Param("name") String name);

    /**
     * 根据策略id删除策略
     * @param scheduleId 策略id
     * @return  boolean   true:删除成功;false:删除失败
     * @author caoqian
     */
     boolean  deleteSchedule(int scheduleId);

    /**
     * 分页查询作息策略，只是按名称模糊查询
     * @param scheduleName        策略名
     * @param firstResult         第几条数据
     * @param pageSize            每页条数
     * @param pageBool            是否分页，true：分页；false：不分页
     * @return  list              策略数据
     * @author  caoqian
     */
     List<Map<String,Object>> scheduleListByPage(@Param("name") String scheduleName,
                                                       @Param("firstResult") Integer firstResult,
                                                       @Param("pageSize") Integer pageSize,
                                                       @Param("pageBool") boolean pageBool);

    /**
     * 根据策略名称查询策略信息
     * @param name 策略名称
     * @return 策略信息
     */
     Map selectScheduleByName(String name);

}
