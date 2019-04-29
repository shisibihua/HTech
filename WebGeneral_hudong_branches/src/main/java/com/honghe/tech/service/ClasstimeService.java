package com.honghe.tech.service;

import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 *
 * @author xinqinggang
 * @date 2018/3/2
 */
public interface ClasstimeService {

    /**
     * 保存作息策略
     * @param userId  用户id
     * @param scheduleObject  策略数据
     * @return boolean  1：保存成功；-1：已存在；-2：保存失败
     */
    public Object saveScheduleTable(int userId,JSONObject scheduleObject);

    /**
     * 根据策略id获取策略详细信息
     * @param scheduleId
     * @return
     */
    public JSONObject getScheduleInfoByScheduleId(int scheduleId);

    /**
     * 修改作息策略
     * @param userId  用户id
     * @param scheduleObject  策略数据
     * @return
     */
    public Object updateScheduleTable(int userId,JSONObject scheduleObject);

    /**
     * 根据作息策略id查询节次上课时间
     * @param scheduleId     作息策略id
     * @return  list     节次时间
     */
    public List<Map<String,Object>> getScheduleListByPloyId(String scheduleId);
}
