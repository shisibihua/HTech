package com.honghe.tech.service;

import net.sf.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 作息策略
 * @author caoqian
 */
public interface ScheduleService {

    /**
     * 分页查询作息策略
     * @param name            策略名称
     * @param currentPage     当前页
     * @param pageSize        每页条数
     * @return
     */
    public Map<String,Object> getPloyListByPage(String name,String currentPage,String pageSize);


    /**
     * 根据策略id删除策略
     * @param scheduleId 策略id
     * @param userId 用户id
     * @return
     */
    public Object deleteScheduTable(int userId,int scheduleId);
    /**
     * 根据作息策略修改策略启用状态
     * @param scheduleId      策略id
     * @param userId      用户id
     * @param isEnable    启用状态，0：禁用；1：启动
     * @return 0：修改成功；-1：不存在；-2：修改失败
     */
    public Object updateScheduleStatusById(int scheduleId,int userId,String isEnable);

    /**
     * 根据作息策略id修改策略名称
     * @param scheduleId      策略id
     * @param scheduleName   策略名称
     * @return 0：修改成功；-1：不存在；-2：修改失败
     */
    public int updateScheduleNameById(int scheduleId,String scheduleName);

    /**
     * 查询所有作息策略
     * @return  策略
     */
    public List<Map<String,String>> getScheduleListAll();
}
