package com.honghe.tech.service;

import net.sf.json.JSONArray;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Karl
 * @date 2018/2/2
 */
public interface StatisticsService {

    /**
     * 获得城市课时数
     * @param map
     * @return
     */
    public long getClassHour(Map map);

    /**
     * 获得县区课时数
     * @param map
     * @return
     */
    public long getCountyClassHour(Map map);

    /**
     * 获得学校课时数
     * @param map
     * @return
     */
    public long getSchoolClassHour(Map map);

    /**
     * 得到教师课时统计
     * @param map
     * @return
     */
    public List<Map<String, Object>> getTeacherOfStatistics(Map map);

    /**
     * 得到学科课时统计
     * @param map
     * @return
     */
    public List<Map<String, Object>> getSubjectOfStatistics(Map map);

    /**
     * 根据地点串查询地点信息
     * @param areaAddr
     * @return
     */
    public Map<String,String> getAreaInfoByAreaId(String areaAddr);

    /**
     * 根据地点分组查询一段时间内的活动数量
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param areaType 地点类型
     * @return 地点对应课程数量
     */
    public List<Map<String,Object>> getAcCountByAreaGroup(String startTime,String endTime,String areaType);

    /**
     * 比对地点服务及本地活动地点数据
     * @param areaList 地点服务地点信息
     * @param areaAcList 活动地点信息
     * @return 整合后地点信息
     */
    public List<Map<String,Object>> getAreaInfo(List<Map<String,String>> areaList,List<Map<String,Object>> areaAcList);

    /**
     * 获取拥有活动的地点详细信息
     * @param areaType 地点类型
     * @param areaAcList 活动地点列表
     * @return 活动地点信息详细列表
     */
    public JSONArray getAreaAcInfo(String areaType,List<Map<String,Object>> areaAcList);

    /**
     * 根据地点id及地点类型获取父级地点名称
     * @param areaType
     * @param num
     * @param placeList
     * @param areaAcList
     * @return
     */
    public List<Map<String,Object>> getTenArea(String areaType,int num,List<Map<String,String>> placeList,List<Map<String,Object>> areaAcList);

}
