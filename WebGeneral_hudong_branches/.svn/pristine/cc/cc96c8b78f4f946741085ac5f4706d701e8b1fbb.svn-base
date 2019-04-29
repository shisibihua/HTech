package com.honghe.tech.service.impl;

import com.honghe.tech.dao.ClasstimeDao;
import com.honghe.tech.dao.CriteriaDao;
import com.honghe.tech.dao.ScheduleDao;
import com.honghe.tech.service.ClasstimeService;
import com.honghe.tech.service.OperLogService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author xinqinggang
 * @date 2018/3/2
 */
@Service
public class ClasstimeServiceImpl implements ClasstimeService{
    Logger logger=Logger.getLogger(ClasstimeServiceImpl.class);
    /**
     * 设置日期格式
     */
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final int WEEK = 7;


    @Autowired
    private ScheduleDao scheduleDao;
    @Autowired
    private CriteriaDao criteriaDao;
    @Autowired
    private ClasstimeDao classtimeDao;
    @Autowired
    private OperLogService operLogService;

    /**
     * 添加作息策略
     * 1.只有最高管理员拥有添加权限，其它管理员拥有查看权限，其它用户不可见;
     * 2.作息策略时间格式为 MM-dd hh:mm:ss,保存只保存月份。
     * 3.作息策略名称不能相同;
     * 4.作息策略只区分上午及下午;
     * @param userId  用户id
     * @param scheduleObject  策略数据
     * @return  0:保存成功；-1:没有权限；-2:策略名称已存在;
     * @author caoqian
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Object saveScheduleTable(int userId,JSONObject scheduleObject)
    {
        int result=0;
        try{
            String scheduleName=scheduleObject.get("name").toString();
            //检测策略名称是否已存在
            Map<String,String> scheduleMap=scheduleDao.selectScheduleByName(scheduleName.trim());
            if(scheduleMap!=null)
            {
                logger.error("该策略已存在，策略名称为"+scheduleName.trim());
                result= -2;
            }else {
                //保存策略名称及初始化状态
                scheduleDao.saveSchedule(scheduleName);
                //获取新建策略id
                scheduleMap = scheduleDao.selectScheduleByName(scheduleName.trim());
                scheduleObject.put("scheduleId",scheduleMap.get("scheduleId"));
                //保存课程节次信息
                Map<String,Integer> criteria=this.getCriteriaFromSchedule(scheduleObject);
                criteria.put("userId",userId);
                criteriaDao.saveCriteria(criteria);
                //保存时间表信息
                List<Map<String,String>> classtimeList=this.getClasstimeList(scheduleObject);
                classtimeDao.saveClassTimes(classtimeList);
                result= 0;
            }
        }catch (Exception e)
        {
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();   //事物回滚
            logger.error("保存作息策略异常",e);
            result= -4;
        }
        if(result==0){
            saveScheduleLog(userId,ScheduleServiceImpl.PLOY_ADD_SUCCESS);
        }else{
            saveScheduleLog(userId,ScheduleServiceImpl.PLOY_ADD_FAILED);
        }
        return result;
    }

    /**
     * 保存作息策略操作日誌
     * @param userId    用户id
     * @param content   日志内容
     */
    private void saveScheduleLog(int userId,String content){
        Map<String,Object> logParams=new HashMap<>();
        logParams.put("userId", userId);
        Date now=new Date();
        logParams.put("logTime", simpleDateFormat.format(now));
        logParams.put("moduleName", ScheduleServiceImpl.MODULE_NAME);
        logParams.put("type",ScheduleServiceImpl.LOG_TYPE);
        logParams.put("content",content);
        operLogService.saveLogTable(logParams);
    }

    /**
     * 修改作息策略
     * 1.最高管理员拥有修改权限，其它管理员查看，其它用户不可见
     * 2.该策略须为禁闭状态
     * @param userId  用户id
     * @param scheduleObject  策略数据
     * @return 0:修改成功；-1:没有权限；-2:名称已存在；-3:策略正在使用，不能修改；-4:未知原因修改失败；-5：该策略不存在
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Object updateScheduleTable(int userId,JSONObject scheduleObject)
    {
        int result=0;
        try{
//            if(userId!=1)
//            {
//                result= -1;
//            }else{
                int scheduleId=(int) scheduleObject.get("scheduleId");
                //检测策略是否存在
                Map<String,Object> scheduleMap=scheduleDao.getScheduleByScheduleId(scheduleId);
                if(scheduleMap==null)
                {
                    result= -5;
                }else{
                    int status=(int)scheduleMap.get("isEnable");
                    if(status==1)
                    {
                        result= -3;
                    }else{
                        String name=scheduleObject.get("name").toString();
                        //检测名称是否已存在
                        scheduleMap=scheduleDao.selectScheduleByName(name);
                        if(scheduleMap!=null&&(int)scheduleMap.get("scheduleId")!=scheduleId)
                        {
                            result= -2;
                        }else{
                            //策略更改流程：新建策略-删除旧策略
                            //更改策略名称
                            scheduleDao.updateScheduleNameByScheduleId((int) scheduleObject.get("scheduleId"),name);
                            //修改课程节次信息
                            Map<String,Integer> criteria=this.getCriteriaFromSchedule(scheduleObject);
                            //更改策略限制条件
                            criteriaDao.updateCriteria(criteria);
                            //修改时间表信息
                            List<Map<String,String>> classtimeList=this.getClasstimeList(scheduleObject);
                            //删除旧的作息策略
                            classtimeDao.deleteClassTimeByScheduleId(scheduleId);
                            //保存新的作息策略
                            classtimeDao.saveClassTimes(classtimeList);
                            result= 0;
                        }
                    }
                }
//            }
        }catch (Exception e)
        {
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();   //事物回滚
            logger.error("修改作息策略异常",e);
            result= -4;
        }
        if(result==0){
            saveScheduleLog(userId,ScheduleServiceImpl.PLOY_UPDATE_SUCCESS);
        }else{
            saveScheduleLog(userId,ScheduleServiceImpl.PLOY_UPDATE_FAILED);
        }
        return result;
    }

    /**
     * 根据策略id获取策略详细信息
     * @param scheduleId
     * @return
     */
    @Override
    public JSONObject getScheduleInfoByScheduleId(int scheduleId)
    {
        JSONObject scheduleJson=new JSONObject();
        Map<String,Object> scheduleMap=scheduleDao.getScheduleByScheduleId(scheduleId);
        if(scheduleMap==null)
        {
            logger.error("该策略不存在，策略id=" + scheduleId);
            return null;
        }else {
            scheduleJson.put("scheduleId",scheduleMap.get("scheduleId"));
            scheduleJson.put("name",scheduleMap.get("name"));
            scheduleJson.put("isEnable",scheduleMap.get("isEnable"));
            scheduleJson.put("criteriaId",scheduleMap.get("criteriaId"));
            scheduleJson.put("am",scheduleMap.get("am"));
            scheduleJson.put("pm",scheduleMap.get("pm"));
            scheduleJson.put("startTime",scheduleMap.get("startTime"));
            scheduleJson.put("endTime",scheduleMap.get("endTime"));
            //查询策略详细时间表
            List<Map<String,Object>> classtimeList=classtimeDao.selectClassTimeByScheduleId(scheduleId);
            if(classtimeList!=null&&classtimeList.size()>0)
            {
                JSONObject classtimeJson=this.getScheduleObject(classtimeList);
                scheduleJson.put("classtime",classtimeJson);
            }
            return scheduleJson;
        }
    }

    /**
     * 根据作息策略id查询节次上课时间
     * @param scheduleId     作息策略id
     * @return  list     节次时间
     */
    @Override
    public JSONArray getScheduleListByPloyId(String scheduleId){
        JSONArray scheduleArray=new JSONArray();
        if(scheduleId==null || "".equals(scheduleId)){
            logger.debug("作息策略scheduleId为空,参数异常。");
            throw new IllegalArgumentException();
        }else{
            // TODO: 2018/5/31 去除代码
            scheduleArray=this.getScheduleTime(Integer.valueOf(scheduleId));
        }
        return scheduleArray;
    }

    /**
     * 根据策略id获取策略时间
     * @param scheduleId 策略id
     * @return 策略时间集合
     */
    private JSONArray getScheduleTime(int scheduleId)
    {
        JSONArray resultArray=new JSONArray();
        //获取作息策略上下午最大课节数
        Map<String,Object> scheduleMap=scheduleDao.getScheduleByScheduleId(scheduleId);
        //获取作息时间表
        List<Map<String,Object>> scheduleList=classtimeDao.selectClassTimeByScheduleId(scheduleId);
        //上午最大课节数
        int maxAm=(int)scheduleMap.get("am");
        //下午最大课节数
        int maxPm=(int)scheduleMap.get("pm");
        int total=maxAm+maxPm;
        for(int i=1;i<=total;i++)
        {
            int type = 2;
            int section = 1;
            if (i > maxAm) {
                type++;
                section = i - maxAm;
            }else{
                section=i;
            }
            JSONObject sectionJson=new JSONObject();
            sectionJson.put("sectionName","第"+i+"节");
            String sectionTime="";
            for(int j=1;j<=WEEK;j++)
            {
                boolean hasTime=false;
                for (Map<String,Object> schedule:scheduleList)
                {
                    if ((int)schedule.get("type")==type&&(int) schedule.get("section")==section&&(int)schedule.get("week")==j)
                    {
                        hasTime=true;
                        sectionTime+=","+schedule.get("startTime")+"-"+schedule.get("endTime");
                    }else{
                        continue;
                    }
                }
                if(!hasTime)
                {
                    sectionTime+=",-";
                }else {
                    continue;
                }
            }
            if(sectionTime.indexOf(",")!=-1)
            {
                sectionTime=sectionTime.substring(1);
            }
            sectionJson.put("sectionTime",sectionTime);
            resultArray.add(sectionJson);
        }
        return resultArray;
    }

    /**
     * 根据集合封装策略信息
     * @param scheduleList 策略集合
     * @return 一周策略信息
     */
    public JSONObject getScheduleObject(List<Map<String,Object>> scheduleList)
    {
        JSONObject scheduleJson=new JSONObject();
        Map<Integer,String> weekMap=this.initWeek2();
        Map<Integer,String> sectionMap=this.initSectionType2();

        for (int i=1;i<WEEK+1;i++)
        {
            int initNum= 2;
            int initLast = 4;
            JSONObject daySchedule=new JSONObject();
            for (int j=initNum;j<initLast;j++)
            {
                List<Map<String,Object>> typeScheduleList=new ArrayList<>();
//                for(Map<String,Object> scheduleMap:scheduleList)
                for(int k=0;k<scheduleList.size();k++)
                {
                    Map<String,Object> scheduleMap=scheduleList.get(k);
                    if((int)scheduleMap.get("week")==i&&(int)scheduleMap.get("type")==j)
                    {
                        scheduleMap.remove("week");
                        typeScheduleList.add(scheduleMap);
                        scheduleList.remove(k);
                        k--;
                    }else{
                        continue;
                    }
                }
                daySchedule.put(sectionMap.get(j),typeScheduleList);
            }
            scheduleJson.put(weekMap.get(i),daySchedule);
        }
        return scheduleJson;
    }


    /**
     * 获取最大课节数
     * 1.获取作息限制条件，包括策略id,策略开始及结束时间，及上、下午最大课节数
     * @param scheduleJson 策略信息
     * @return 早晨、上午、下午及晚自习对应的课节情况
     */
    private Map<String,Integer> getCriteriaFromSchedule(JSONObject scheduleJson)
    {
        // TODO: 2018/5/31 去除部分无用代码
        Map<String, Integer> criteria = new HashMap<>();
        criteria.put("scheduleId", (int) scheduleJson.get("scheduleId"));
        criteria.put("startTime", Integer.valueOf(scheduleJson.get("startTime").toString()));
        criteria.put("endTime", Integer.valueOf(scheduleJson.get("endTime").toString()));
        Map<String, Integer> weekMap = this.initWeek();
        Map<String, Integer> sectionTypeMap = this.initSectionType();
        try {
            int maxAm = 0, maxPm = 0;
            for (String week : weekMap.keySet()) {
                JSONObject dayScheduleJson = JSONObject.fromObject(scheduleJson.get(week));
                if (dayScheduleJson != null && dayScheduleJson.size() > 0) {
                    for (String type : sectionTypeMap.keySet()) {
                        if (dayScheduleJson.get(type) != null) {
                            JSONArray typeSchedule = JSONArray.fromObject(dayScheduleJson.get(type));
                            switch (type) {
                                case "am":
                                    if (typeSchedule.size() > maxAm) {
                                        maxAm = typeSchedule.size();
                                    }
                                    break;
                                case "pm":
                                    if (typeSchedule.size() > maxPm) {
                                        maxPm = typeSchedule.size();
                                    }
                                    break;
                                default:
                                    break;
                            }
                        } else {
                            continue;
                        }
                    }
                }

            }
            criteria.put("am", maxAm);
            criteria.put("pm", maxPm);
            return criteria;
        } catch (Exception e) {
            logger.debug("作息策略：" + scheduleJson.toString());
            logger.error("获取周一课节情况异常", e);
            return null;
        }
    }

    /**
     * 遍历周，获取每天的作息时间
     * @param scheduleJson
     * @return
     */
    private  List<Map<String,String>> getClasstimeList(JSONObject scheduleJson)
    {
        List weekClastimeList = new ArrayList();
        Map<String, Integer> weekMap = this.initWeek();
        Map<String, Integer> sectionTypeMap = this.initSectionType();
        String scheduleId = scheduleJson.get("scheduleId").toString();
        for (String week : weekMap.keySet()) {
            JSONObject daySchedule = JSONObject.fromObject(scheduleJson.get(week));
            if (daySchedule != null && daySchedule.size() > 0) {
                for (String type : sectionTypeMap.keySet()) {
                    JSONArray typeSchedule = JSONArray.fromObject(daySchedule.get(type));
                    if (typeSchedule != null && typeSchedule.size() > 0) {
                        for (int i = 0; i < typeSchedule.size(); i++) {
                            Map<String, String> classtime = new HashMap<>();
                            JSONObject typeScheduleJson = JSONObject.fromObject(typeSchedule.get(i));
                            if (typeScheduleJson != null && typeScheduleJson.size() > 0) {
                                classtime.put("scheduleId", scheduleId);
                                classtime.put("week", weekMap.get(week).toString());
                                classtime.put("section", String.valueOf(i + 1));
                                classtime.put("type", sectionTypeMap.get(type).toString());
                                classtime.put("startTime", typeScheduleJson.get("startTime").toString());
                                classtime.put("endTime", typeScheduleJson.get("endTime").toString());
                                if (typeScheduleJson.containsKey("classtimeId")) {
                                    classtime.put("classtimeId", typeScheduleJson.get("classtimeId").toString());
                                }
                                weekClastimeList.add(classtime);
                            } else {
                                continue;
                            }
                        }
                    } else {
                        continue;
                    }
                }
            } else {
                continue;
            }
        }
        return weekClastimeList;
    }

    /**
     * 初始化星期
     * @return
     */
    private Map<String,Integer> initWeek()
    {
        Map<String,Integer> weekMap=new HashMap<>();
        weekMap.put("monday",1);
        weekMap.put("tuesday",2);
        weekMap.put("wednesday",3);
        weekMap.put("thursday",4);
        weekMap.put("friday",5);
        weekMap.put("saturday",6);
        weekMap.put("sunday",7);
        return weekMap;
    }

    /**
     * 初始化星期
     * @return
     */
    private Map<Integer,String> initWeek2()
    {
        Map<Integer,String> weekMap=new HashMap<>();
        weekMap.put(1,"monday");
        weekMap.put(2,"tuesday");
        weekMap.put(3,"wednesday");
        weekMap.put(4,"thursday");
        weekMap.put(5,"friday");
        weekMap.put(6,"saturday");
        weekMap.put(7,"sunday");
        return weekMap;
    }

    /**
     * 初始化节次类型
     * @return
     */
    private Map<String,Integer> initSectionType()
    {
        Map<String,Integer> typeMap=new HashMap<>();
        typeMap.put("am",2);
        typeMap.put("pm",3);
        return typeMap;
    }

    /**
     * 初始化节次类型
     * @return
     */
    private Map<Integer,String> initSectionType2()
    {
        Map<Integer,String> typeMap=new HashMap<>();
        typeMap.put(2,"am");
        typeMap.put(3,"pm");
        return typeMap;
    }
}
