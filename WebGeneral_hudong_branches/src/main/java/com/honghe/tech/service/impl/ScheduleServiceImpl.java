package com.honghe.tech.service.impl;


import com.esotericsoftware.kryo.serializers.FieldSerializer;
import com.honghe.tech.dao.ActivityDao;
import com.honghe.tech.dao.ClasstimeDao;
import com.honghe.tech.dao.CriteriaDao;
import com.honghe.tech.dao.ScheduleDao;
import com.honghe.tech.service.OperLogService;
import com.honghe.tech.service.ScheduleService;
import com.honghe.tech.util.DateTimeUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author caoqian
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {
    private Logger logger=Logger.getLogger(ScheduleServiceImpl.class);
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 用户无此权限(删除策略)
     */
    private final int USER_ERROR=-1;
    /**
     * 有预约活动使用该策略，删除错误
     */
    private final int TECH_ERROR=-2;
    /**
     * 策略启动，删除错误
     */
    private final int ISENABLE_ERROR=-3;
    /**
     * 异常错误
     */
    private final int EXCEPT_ERROR=-4;
    /**
     * 查不到此策略
     */
    private final int DATA_ERROR=-5;
    /**
     * 修改成功
     */
    private final int SUCCESS = 0;

    public final static String MODULE_NAME="基础设置";
    public final static String PLOY_ADD_SUCCESS="用户成功添加作息策略";
    public final static String PLOY_ADD_FAILED="用户添加作息策略失败";
    public final static String PLOY_DELETE_SUCCESS="用户成功删除作息策略";
    public final static String PLOY_DELETE_FAILED="用户删除作息策略失败";
    public final static String PLOY_UPDATE_ISENABLE_SUCCESS="用户成功启用作息策略";
    public final static String PLOY_UPDATE_ISENABLE_FAILED="用户启用作息策略失败";
    public final static String PLOY_UPDATE_NO_ISENABLE_SUCCESS="用户成功禁用作息策略";
    public final static String PLOY_UPDATE_NO_ISENABLE_FAILED="用户禁用作息策略失败";
    public final static String PLOY_UPDATE_NAME_SUCCESS="用户成功修改作息策略名称";
    public final static String PLOY_UPDATE_NAME_FAILED="用户修改作息策略名称失败";
    public final static String PLOY_UPDATE_SUCCESS="用户成功修改作息策略信息";
    public final static String PLOY_UPDATE_FAILED="用户修改作息策略信息失败";
    public final static String LOG_TYPE="0";
    public final static Long CURRENTTIME = System.currentTimeMillis();

    @Autowired
    private ScheduleDao scheduleDao;
    @Autowired
    private ClasstimeDao classTimeDao;
    @Autowired
    private CriteriaDao criteriaDao;

    @Autowired
    private ActivityDao activityDao;
    @Autowired
    private OperLogService operLogService;

    /**
     * 查询所有作息策略
     * @return  策略
     */
    @Override
    public List<Map<String,String>>getScheduleListAll()
    {
        return  scheduleDao.getScheduleAll();
    }


    /**
     * 分页查询作息策略，可支持名称模糊查询
     * @param name            作息策略名称，不是必须
     * @param currentPage     当前页
     * @param pageSize        每页条数
     * @return  map          作息策略数据
     * @author  caoqian
     */
    @Override
    public Map<String, Object> getPloyListByPage(String name, String currentPage, String pageSize){
        Map<String, Object>  ployMap=new HashMap<>();
        if(currentPage==null || "".equals(currentPage) || pageSize==null || "".equals(pageSize)){
            logger.debug("当前页currentPage:"+currentPage+",每页条数pageSize:"+pageSize+",获取策略失败，参数异常。");
            throw new IllegalArgumentException();
        }else {
            ployMap.put("currentPage", currentPage);
            ployMap.put("pageSize", pageSize);
            try {
                //获取第一条开始的位置
                int firstResult = (Integer.parseInt(currentPage) - 1) * Integer.parseInt(pageSize) < 0 ? 0 : (Integer.parseInt(currentPage) - 1) * Integer.parseInt(pageSize);
                //数据总条数
                int total = scheduleDao.scheduleListByPage(name.trim(), firstResult, Integer.parseInt(pageSize), false).size();
                List<Map<String, Object>> polyList = scheduleDao.scheduleListByPage(name.trim(), firstResult, Integer.parseInt(pageSize), true);
                ployMap.put("total", total);
                ployMap.put("dataList", polyList);
            } catch (Exception e) {
                ployMap.put("total", "");
                ployMap.put("dataList", "");
                logger.error("分页查询作息策略异常，name=" + name + ",currentPage=" + currentPage +",pageSize=" + pageSize, e);
            }
        }
        return ployMap;
    }

    /**
     * 根据策略id删除策略
     * @param userId 用户id
     * @param scheduleId 策略id
     * @return 0:删除成功;-1:没有权限;-2:
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Object deleteScheduTable(int userId,int scheduleId)
    {
        try {
            Map<String, Object> scheduleMap = scheduleDao.getScheduleByScheduleId(scheduleId);
            if (scheduleMap == null) {
                return DATA_ERROR;
            } else {
                int status = Integer.parseInt(String.valueOf(scheduleMap.get("isEnable")));
                if (status == 1) {
                    return ISENABLE_ERROR;
                } else {
                    scheduleDao.deleteSchedule(scheduleId);
                    criteriaDao.deleteCriteriaByScheduleId(scheduleId);
                    classTimeDao.deleteClassTimeByScheduleId(scheduleId);
                    return 0;
                }
            }
        } catch (Exception e) {
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();   //事物回滚
            logger.error("删除作息策略异常,策略id=" + scheduleId, e);
            return EXCEPT_ERROR;
        }
    }

    /**
     * 根据作息策略修改策略启用状态
     * 1.允许多个策略同时启用；
     * 2.启用策略之间不能存在时间冲突。
     * 3.策略中存在未完成的预约时，不能禁闭。
     * 4.最高管理员可以修改，其它管理员可以查看，其它用户不可见。
     * @param scheduleId   策略id
     * @param userId      用户id
     * @param isEnable    启用状态，0：禁用；1：启动
     * @return 0：修改成功；-1:没有权限；-3:策略时间与其它启用策略冲突；-4:因未知原因修改失败；-5:策略不存在；-6:策略内存在未结束预约活动，无法关闭
     * @author  caoqian
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Object updateScheduleStatusById(int scheduleId,int userId,String isEnable){
        if(isEnable==null || "".equals(isEnable) || isEnable==null || "".equals(isEnable))
        {
            logger.debug("作息策略scheduleId:"+scheduleId+",启用状态isEnable:"+isEnable+",根据作息策略修改策略启用状态失败，参数异常。");
            throw new IllegalArgumentException();
        }else {
            try {
                //根据策略id检测该策略是否存在
                Map<String, Object> schedule = scheduleDao.getScheduleByScheduleId(scheduleId);
                if (schedule == null) {
                    logger.error("该策略不存在，策略id=" + scheduleId);
                    return -5;
                } else {
                    if (Integer.valueOf(isEnable) == 1) {
                        //查询正在启动的作息策略，如果存在启动策略，则检查时间是否存在冲突(须考虑跨年)
                        List<Map<String, Object>> scheduleList = scheduleDao.getScheduleByStatus(1);
                        if (scheduleList != null && scheduleList.size() > 0) {
                            for (Map<String, Object> sch : scheduleList) {
                                boolean stag = this.checkScheduleTime(schedule, sch);
                                if (stag) {
                                    return -3;
                                } else {
                                    continue;
                                }
                            }
                        }
                        // TODO: 2018/5/30 删除了多余代码
                        scheduleDao.updateScheduleStatusByScheduleId(scheduleId, 1);
                    } else {
                        //关闭作息策略，须确定该作息策略无未完成的预约活动。
                        Map<String, String> timeMap = this.getScheduleTime(schedule);
                        long startTime = DateTimeUtil.parseDateTime(timeMap.get("startTime")).getTime();
                        long endTime = DateTimeUtil.parseDateTime(timeMap.get("endTime")).getTime();
                        // TODO: 2018/5/31 时间更改为currenttime
                        long nowTime = CURRENTTIME;
                        List<Map<String, Object>> acList;
                        if (nowTime < startTime) {
                            acList = activityDao.selectAcDuringTime(timeMap.get("startTime"), timeMap.get("endTime"));
                        } else if (nowTime >= startTime && nowTime < endTime) {
                            acList = activityDao.selectAcDuringTime(DateTimeUtil.formatDateTime(new Date()), timeMap.get("endTime"));
                        } else {
                            acList = new ArrayList<>();
                        }
                        if (acList != null && acList.size() > 0) {
                            return -6;
                        } else {
                            scheduleDao.updateScheduleStatusByScheduleId(scheduleId, 0);
                        }
                    }
                    return 0;
                }
            } catch (Exception e) {
//                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();   //事物回滚
                logger.error("根据作息策略修改策略启用状态异常，策略id=" + scheduleId + ",启用状态isEnable=" + isEnable, e);
                return -4;
            }
        }
    }

    /**
     * 检查两个策略时间是否冲突
     * @param schedule1
     * @param schedule2
     * @return true：冲突；false:不冲突
     */
    private boolean checkScheduleTime(Map<String,Object> schedule1,Map<String,Object> schedule2)
    {
        int startMonth1 = Integer.valueOf(schedule1.get("startTime").toString());
        int endMonth1 = Integer.valueOf(schedule1.get("endTime").toString());
        int startMonth2 = Integer.valueOf(schedule2.get("startTime").toString());
        int endMonth2 = Integer.valueOf(schedule2.get("endTime").toString());
        if (endMonth1 < startMonth1) {
            endMonth1 = startMonth1 + endMonth1;
        }
        if (endMonth2 < startMonth2) {
            endMonth2 = startMonth2 + endMonth2;
        }
        if (startMonth1 <= endMonth2 && endMonth1 >= startMonth2) {
            logger.error("此策略" + schedule1.get("name") + "与”" + schedule2.get("name") + "“策略时间冲突");
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取策略开始及结束时间
     * @return 策略的开始时间及结束时间
     */
    private Map<String,String> getScheduleTime( Map<String,Object> schedule)
    {
        Map<String,String> sheduleTime=new HashedMap();
        Calendar cal = Calendar.getInstance();
        // TODO: 2018/5/31 直接访问通过类名访问
        int nowYear=cal.get(Calendar.YEAR);
        int nowMonth=cal.get(Calendar.MONTH)+1;
        String startTime="";
        String endTime="";
        int startMonth=Integer.valueOf(schedule.get("startTime").toString());
        int endMonth=Integer.valueOf(schedule.get("endTime").toString());
        //如果开始月份小于结束月份，则在一年内，否则跨年
        if(startMonth<=endMonth)
        {
            startTime=nowYear+"-"+startMonth+"-1 00:00:00";
            endTime= DateTimeUtil.getLastDayOfMonth(nowYear,endMonth)+" 23:59:59";
        }else{
            //1.如果现在月份大于等于开始月份，则结束月份在第二年内
            //2.如果现在月份小于等于结束月份，则开始月份在上-年内
            //3.上述都不满足，则采用情况1。
            if(nowMonth<=startMonth)
            {
                startTime=(nowYear-1)+"-"+startMonth+"-1 00:00:00";
                endTime=DateTimeUtil.getLastDayOfMonth((nowYear),endMonth)+" 23:59:59";
            }else{
                startTime=nowYear+"-"+startMonth+"-1 00:00:00";
                endTime=DateTimeUtil.getLastDayOfMonth((nowYear+1),endMonth)+" 23:59:59";
            }
        }
        sheduleTime.put("startTime",startTime);
        sheduleTime.put("endTime",endTime);
        return sheduleTime;
    }

    /**
     * 同步活动作息时间，作息策略开启时触发
     * @param classtimeMap
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void syncAcTime(Map<Integer,Map<String,String>> classtimeMap )
    {
        try{
            List<Map<String,Object>> acMapList=activityDao.selectAcAfterHour();
            if(acMapList!=null&&acMapList.size()>0)
            {
                for (Map<String,Object> acMap:acMapList)
                {
                    int classtimeId=Integer.valueOf(acMap.get("classtimeId").toString());
                    if(classtimeMap.get(classtimeId)!=null)
                    {
                        String startDate=acMap.get("startDate").toString();
                        String startTime=classtimeMap.get(classtimeId).get("startTime");
                        String nowStartTime=startDate+" "+startTime+":00";
                        String endDate=acMap.get("endDate").toString();
                        String endTime=classtimeMap.get(classtimeId).get("endTime");
                        String nowEndTime=endDate+" "+endTime+":00";
                        acMap.put("startDate",nowStartTime);
                        acMap.put("endDate",nowEndTime);
                        //同步数据库作息时间
                        activityDao.sysnAcClasstime(acMap);
                    }
                }
            }
        }catch (Exception e)
        {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();   //事物回滚
            logger.error("同步活动作息时间异常",e);
        }
    }

    /**
     * 根据集合封装策略信息
     * @param scheduleId 策略id
     * @return 一周作息时间映射
     */
    public Map<Integer,Map<String,String>> getClasstimeMap(int scheduleId)
    {
        //查询策略详细时间表
        List<Map<String,Object>> classtimeList=classTimeDao.selectClassTimeByScheduleId(scheduleId);
        if(classtimeList!=null&&classtimeList.size()>0)
        {
            Map classtimeMap=new HashMap();
            for (Map<String,Object> classtime:classtimeList)
            {
                Map timeMap=new HashMap();
                timeMap.put("startTime",classtime.get("startTime"));
                timeMap.put("endTime",classtime.get("endTime"));
                classtimeMap.put(classtime.get("id"),timeMap);
            }
            return classtimeMap;
        }else{
            return null;
        }
    }

    /**
     * 根据作息策略id修改策略名称
     * @param scheduleId      策略id
     * @param scheduleName   策略名称
     * @return 0：修改成功；-1：不存在；-2：修改失败
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public int updateScheduleNameById(int scheduleId,String scheduleName)
    {
        int result=0;
        if(scheduleName==null || "".equals(scheduleName)){
            logger.debug("作息策略scheduleId:"+scheduleId+",策略名称:"+scheduleName+",根据作息策略修改策略名称失败，参数异常。");
            throw new IllegalArgumentException();
        }else {
            try {
                //根据策略id检测该策略是否存在
                Map<String,Object> schedule=scheduleDao.getScheduleByScheduleId(scheduleId);
                if(schedule==null)
                {
                    logger.error("该策略不存在，策略id="+scheduleId);
                    result=-1;
                }else {
                    scheduleDao.updateScheduleNameByScheduleId(scheduleId, scheduleName);
                    result=0;
                }
                Map<String,Object> logParams=new HashMap<>();
                logParams.put("userId", String.valueOf(schedule.get("userId")));
                Date now=new Date();
                logParams.put("logTime", simpleDateFormat.format(now));
                logParams.put("moduleName", ScheduleServiceImpl.MODULE_NAME);
                logParams.put("type",ScheduleServiceImpl.LOG_TYPE);
                if(result==0){
                    logParams.put("type",PLOY_UPDATE_NAME_SUCCESS);
                }else{
                    logParams.put("type",PLOY_UPDATE_NAME_FAILED);
                }
                operLogService.saveLogTable(logParams);
            } catch (Exception e)
            {
//                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();   //事物回滚
                logger.error("根据作息策略修改策略启用状态异常，策略id=" + scheduleId + ",策略名称" + scheduleName, e);
                result=TECH_ERROR;
            }

        }
        return result;
    }

}
