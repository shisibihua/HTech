package com.honghe.tech.service.impl;

import com.honghe.tech.dao.ActivityDao;
import com.honghe.tech.dao.AreaDao;
import com.honghe.tech.httpservice.UserHttpService;
import com.honghe.tech.service.StatisticsService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LiZhuoyuan on 2018/2/2.
 * @author lizhuoyuan
 * @ 方法停用
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {
    Logger logger= Logger.getLogger(ActivityServiceImpl.class);
    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private UserHttpService userHttpService;

    @Autowired
    private AreaDao areaDao;

    /**
     * 根据地点串查询地点信息
     * @param areaAddr
     * @return
     */
    @Override
    public Map<String,String> getAreaInfoByAreaId(String areaAddr)
    {
        return areaDao.getAreaInfoByAreaId(areaAddr);
    }

    @Override
    public long getClassHour(Map map) {
        long count = 0;
        try {
            count = activityDao.getStatisticalQuantityOfCity(map);
        } catch (Exception e) {
            logger.error(this.getClass().getSimpleName()+"---查询课时数异常！",e);
        }
        return count;
    }

    @Override
    public long getCountyClassHour(Map map) {//暂时不用
        long count = 0;
        try {
            count = activityDao.getStatisticalQuantityOfCity(map);
        } catch (Exception e) {
            logger.error(this.getClass().getSimpleName()+"---查询课时数异常！",e);
        }
        return count;
    }

    @Override
    public long getSchoolClassHour(Map map) {//暂时不用
        long count = 0;
        try {
            count = activityDao.getStatisticalQuantityOfCity(map);
        } catch (Exception e) {
            logger.error(this.getClass().getSimpleName()+"---查询课时数异常！",e);
        }
        return count;
    }

    @Override
    public List<Map<String, Object>> getTeacherOfStatistics(Map map) {
        List<Map<String, Object>> list =  null;
        try {
            list =activityDao.getTeacherOfStatistics(map);
        }catch (Exception e){
            logger.error(this.getClass().getSimpleName()+"---查询课时数异常！",e);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> getSubjectOfStatistics(Map map) {
        List<Map<String, Object>> list = null;
        try {
            list = activityDao.getSubjectOfStatistics(map);
        }catch (Exception e){
            logger.error(this.getClass().getSimpleName()+"---查询课时数异常！",e);
        }
        return list;
    }

    /**
     * 根据地点分组查询一段时间内的活动数量
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param areaType 地点类型
     * @return 地点对应课程数量
     */
    @Override
    public List<Map<String,Object>> getAcCountByAreaGroup(String startTime,String endTime,String areaType)
    {
        Map params=new HashMap();
        if(startTime!=null&&!"".equals(startTime))
        {
            params.put("startTime",startTime);

        } if(endTime!=null&&!"".equals(endTime))
        {
            params.put("endTime",endTime);
        }
        switch (areaType) {
            case "city":
                params.put("num", 2);
                break;
            case "county":
                params.put("num", 3);
                break;
            case "school":
                params.put("num", 4);
                break;
            default:
                break;
        }
        return activityDao.getAcCountByAreaGroup(params);
    }

    /**
     * 比对地点服务及本地活动地点数据
     * @param areaList 地点服务地点信息
     * @param areaAcList 活动地点信息
     * @return 整合后地点信息
     */
    @Override
    public List<Map<String,Object>> getAreaInfo(List<Map<String,String>> areaList,List<Map<String,Object>> areaAcList)
    {
        List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
        for (int i=0; i < areaList.size(); i++)
        {
            Map<String,Object> areaMap=new HashMap<>();
            String areaId= areaList.get(i).get("areaId");
            boolean hasAc=false;
            for (int j=0;j<areaAcList.size();j++)
            {
                String areaNode=areaAcList.get(j).get("areaNode").toString();
                if(areaId.equals(areaNode))
                {
                    areaMap.put("placeId",areaId);
                    areaMap.put("name",areaAcList.get(i).get("areaName"));
                    areaMap.put("number",areaAcList.get(j).get("number"));
                    areaMap.put("areaNode",areaAcList.get(j).get("areaNode"));
                    hasAc=true;
                }else {
                    continue;
                }
            }
            if(!hasAc)
            {
                areaMap.put("placeId",areaId);
                areaMap.put("name",areaAcList.get(i).get("areaName"));
                areaMap.put("number",0);
                areaMap.put("areaNode","");
            }
            resultList.add(areaMap);
        }
        return resultList;
    }

    /**
     * 获取拥有活动的地点详细信息
     * @param areaType 地点类型
     * @param areaAcList 活动地点列表
     * @return 活动地点信息详细列表
     */
    @Override
    public JSONArray getAreaAcInfo(String areaType,List<Map<String,Object>> areaAcList)
    {
        JSONArray resultArray=new JSONArray();
        for (Map<String,Object> areaMap:areaAcList)
        {
            JSONObject areaJson=new JSONObject();
            areaJson.put("number", areaMap.get("num"));
            switch (areaType) {
                case "city":
                    areaJson.put("name", areaMap.get("city"));
                    break;
                case "county":
                    areaJson.put("cityName", areaMap.get("city"));
                    areaJson.put("name", areaMap.get("county"));
                    break;
                case "school":
                    areaJson.put("cityName", areaMap.get("city"));
                    areaJson.put("countyName", areaMap.get("county"));
                    areaJson.put("name", areaMap.get("school"));
                    break;
                default:
                    break;
            }
            resultArray.add(areaJson);
        }
        return resultArray;
    }

    /**
     * 根据地点id及地点类型获取父级地点名称
     * @param areaType 地点类型
     * @param num 地点个数
     * @return 父级地点信息
     */
    @Override
    public List<Map<String,Object>> getTenArea(String areaType,int num,List<Map<String,String>> placeList,List<Map<String,Object>> areaAcList)
    {
        List<Map<String,Object>> areaList=new ArrayList<>();
        List<String> areaIdList=new ArrayList<>();
        for(Map<String,Object> areaAcMap:areaAcList)
        {
            areaIdList.add(String.valueOf(areaAcMap.get("areaId")));
        }
        for (int i=0;i<num;i++)
        {
            if(areaIdList.contains(placeList.get(i).get("areaId")))
            {
                continue;
            }else {
                Map<String,Object> parentAreaMap=userHttpService.getParentArea(areaType,placeList.get(i).get("areaId"));
                Map<String,Object> areaMap=new HashMap<>();
                areaMap.put("city", parentAreaMap.get("cityName"));
                switch (areaType) {
                    case "city":
                        areaMap.put("num", 0);
                        break;
                    case "county":
                        areaMap.put("county", parentAreaMap.get("countyName"));
                        areaMap.put("num", 0);
                        break;
                    case "school":
                        areaMap.put("county", parentAreaMap.get("countyName"));
                        areaMap.put("school", parentAreaMap.get("schoolName"));
                        areaMap.put("num", 0);
                        break;
                    default:
                        break;
                }
                areaList.add(areaMap);
            }
        }
        return areaList;
    }

}
