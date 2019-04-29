package com.honghe.tech.dao;

import com.honghe.spring.MybatisDao;
import com.honghe.tech.entity.Activity;
import com.honghe.tech.form.ActivityInfoForm;
import org.apache.ibatis.annotations.Param;

import javax.annotation.security.PermitAll;
import javax.jws.Oneway;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author xinqinggang
 * @date 2018/1/23
 */
@MybatisDao
public interface ActivityDao {
    /**
     * 保存教学活动信息
     * @param activity 教学活动
     * @return boolean
     */
     boolean saveActivity(Activity activity);

    /**
     * 根据时间及课节查询课程
     * @param params
     * @return
     */
     List<Map<String,Object>> selectAcByClassTime(Map params);

    /**
     * 根据活动id查询教学活动信息
     * @param id 活动id
     * @return 教学活动
     */
     Activity selectActivityById(int id);

    /**
     * 根据活动id查询教学活动信息
     * @param id 活动id
     * @return 教学活动
     */
     Activity selectActivityById2(int id);

    /**
     * 根据教学活动id修改教学活动信息
     * @param params 参数（须包含活动id）
     * @return boolean
     */
     boolean updateActivityByParam(Map params);

    /**
     * 按条件查询活动信息
     * @param params
     * @return boolean
     */
     List<ActivityInfoForm> selectActivityInfoByParam(Map params);

    /**
     * 通过当天日期（格式：2018-01-01）得到教学活动集合
     * @param nowDate 当前日期
     * @return 预约互动集合
     */
     List getActivitiesByDate(@Param("nowDate") String nowDate);

    /**
     * 通过当天日期及教室id（格式：2018-01-01）得到教学活动集合
     * @param nowDate   当前日期
     * @param roomId    教室id
     * @return 教学活动集合
     * @author caoqian
     */
     List getActivitiesByDateAndRoomId(@Param("nowDate")String nowDate,@Param("roomId")String roomId);

    /**
     * 得到整点区间的教学活动数据
     * @param map 开始时间 格式：2018-01-01 08:00  和 日期
     * @return 教学活动数据
     */
     List getActivitiesByTime(Map map);

    /**
     * 通过id删除教学活动（逻辑删除）
     * @param id 教学活动id
     * @return boolean
     */
     boolean deleteActivity(int id);

    /**
     * 根据教室id获取月中某时间段内每天的活动总数
     * @param params 开始、结束时间，地点
     * @return 每天的课程数量
     * @author xinqinggang
     */
     List<Map<String,Object>> selectDayActivityNumByTime(Map params);

    /**
     * 获取时间段内拥有活动的主讲教室总数
     * @param params  （startTIme,endTime,roomAddr,）
     * @return 总数
     * @author xinqinggang
     */
     int selectRoomTotalNum(Map params);

    /**
     * 根据教室id获取查询某天的所有课程信息
     * @param params 包含dateTime.roomIds
     * @return  课程信息
     * @author xinqinggang
     */
     List<ActivityInfoForm> selectActivityByRoomIdAndTime(Map params);

    /**
     * 根据教室id获取今天预告课程信息
     * @param roomId 教室id
     * @return 课程预告列表
     * @author xinqinggang
     */
     List<ActivityInfoForm> selectPreActivityByRoomId(int roomId);

    /**
     * 统计市/区/学校课时数
     * @param map
     * @return
     */
     long getStatisticalQuantityOfCity(Map map);

    /**
     * 得到教师课时统计
     * @param map
     * @return
     */
     List<Map<String, Object>> getTeacherOfStatistics(Map map);

    /**
     * 获得学科课时统计
     * @param map
     * @return
     */
     List<Map<String, Object>> getSubjectOfStatistics(Map map);



    /**
     * 根据主讲教室id查询互动教学
     * @param classRoomIdArr    主讲教室Id，多个用","分割
     * @param firstResult       从第几条开始
     * @param pageSize          每页条数
     * @param pageBool          是否分页，true：分页；false:不分页
     * @return list             互动教学列表
     * @author   caoqian
     */
     List<Map<String,Object>> teachingActivityListByPage(@Param("classRoomIdArr")String[] classRoomIdArr,
                                                               @Param("firstResult") Integer firstResult, @Param("pageSize") Integer pageSize,
                                                               @Param("pageBool") boolean pageBool); //分页获取互动教学列表

    /**
     * 根据uuid查询一拖多教学监管
     * @param uuid              互动教学与接收表uuid
     * @param firstResult       从第几条开始
     * @param pageSize          每页条数
     * @param pageBool          是否分页，true：分页；false:不分页
     * @return    list          一拖多教学监管数据
     * @author caoqian
     */
     List<Map<String,Object>> techingListByUuidByPage(@Param("uuid")String uuid,@Param("firstResult") Integer firstResult,
                                                            @Param("pageSize") Integer pageSize,@Param("pageBool") boolean pageBool);

    /**
     * 根据地址id串查询时间内教学活动信息（支持活动名称查询）
     * @param params (roomAddr(省id-市id-区、县id-学校id))，startTime,endTime,(activityName)
     * @return 活动信息
     */
     List<ActivityInfoForm> selectAcInfosByRoomAddr(Map params);

    /**
     * 根据教室id查询正在上课的教室
     * @param classRoomIdArr  教室id数组
     * @return   list         正上课的互动教学信息
     * @author caoqian
     */
     List<Map<String,Object>> getTeachingActivityListByRoomIdArr(@Param("classRoomIdArr")String[] classRoomIdArr);

    /**
     * 查询正上课的教室ids，多个","分割
     * @return String  教室ids
     * @author caoqian
     */
     String getTeachingActivityRoomIds();

    //-------------------------------------------------周课表查询-------------------------------------------------------
    /**
     * 根据主讲教室地址获取时间内课程名称
     * @param params（roomAddr,周的startTime、endTime,roomId(可为空)）
     * @return 活动详细信息
     */
     List<ActivityInfoForm> getActivityNameByRoomAddr(Map params);

    /**
     * 根据主讲教师id获取时间内课程名称
     * @param params（周的startTime、endTime,teacherId）
     * @return 活动详细信息
     */
     List<ActivityInfoForm> getActivityNameByTeacherId(Map params);

    //--------------------------------------------------end-------------------------------------------------------------

    //--------------------------------------------------首页------------------------------------------------------------

    /**
     * 根据参数查询课程数量
     * @param params(roomAddr:教室所在学校id串(必填),startTermTime,weekTotalCourse,weekHasDoneCourse,weekDoneCourse)
     * @return 数量
     */
     int getTotalHostCourseCase(Map params);

    /**
     * 根据参数查询收益学生数量
     * @param params
     * @return 学生数量
     */
     int selectAllStudentsNum(Map params);

    /**
     * 根据主讲教师数量
     * @param params
     * @return 主讲教师数量
     */
     int selectHostTeacherNum(Map params);
    //------------------------------------------------------------------------------------------------------------------

    /**
     * 更改活动状态
     * @param params
     * @return 修改条数
     */
     int updateAcStatus(Map params);

    /**
     * 根据活动id查询主讲教师信息
     * @param activityId   活动id
     * @return  map        市、区、学校、老师名，如‘保定市莲池区保定一中张梅’
     * @author caoqian
     */
     List<Map<String,String>> getLectTeacherList(String activityId);
    /**
     * 根据活动id查询本周内的活动信息
     * @param id 活动id
     * @return  活动信息
     * @author xinqinggang
     */
     ActivityInfoForm  selectAcInfoDuringWeekByAcId(int id);

    /**
     * 根据时间获取当天课程名称
     * @param startTime   开始时间，如‘2018-01-01 00:00:00’
     * @param endTime     结束时间，如‘2018-01-01 23:59:59’
     * @param roomId     主讲教室id
     * @return list
     * @author caoqian
     */
     List<Map<String,String>> getCourseTodayListByTime(@Param("startTime") String startTime,@Param("endTime")String endTime,
                                                             @Param("roomId") String roomId);
    /**
     * 获取当前时间主讲课程
     * @param time       当前时间，格式:"2018-01-01 10:00:00"
     * @param roomId     主讲教室id
     * @return list
     * @author caoqian
     */
     List<Map<String,String>> getLectCourseListByTime(@Param("time") String time,@Param("roomId") String roomId);

    /**
     * 根据获取id获取所有教室信息，包括主讲教室、接收教室
     * @param uuid     活动uuid
     * @return list
     * @author caoqian
     */
     List<Map<String,String>> getRoomListByUuid(@Param("uuid") String uuid);

    /**
     * 查询一个小时之后的所有活动
     * @return 活动集合
     */
     List<Map<String,Object>> selectAcAfterHour();

    /**
     * 同步作息时间
     * @param acMap 活动信息
     * @return 活动集合
     */
     void sysnAcClasstime(Map<String,Object> acMap);


    /**
     * 查询时间内所有教学活动
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 教学活动信息
     */
     List<Map<String,Object>> selectAcDuringTime(@Param("startTime") String startTime,@Param("endTime") String endTime);

    /**
     * 根據通知時間查詢活動
     * @return 活動集合
     */
     List<Map<String,Object>> selectAcByNoticeTime();

    /**
     * 根据活动id更改通知时间
     * @param params
     * @return
     */
     boolean updateNoticeTimeByAcId(Map params);

    /**
     * 根据地点进行分组获取一段时间内的课程数量
     * @param params 参数
     * @return 地点及对应活动数量
     */
     List<Map<String,Object>> getAcCountByAreaGroup(Map params);

    /**
     * 根据当前时间获取过期的活动
     * @param now   当前时间，‘2018-04-03 00:00:00’
     * @return  id串，多个','分割
     * @author caoqian
     */
     String getOverdueActivityId(@Param("date")String now);

    /**
     * 根据主讲教室id查询主讲的互动教学：首页直播
     * @param classRoomIdArr    主讲教室Id，多个用","分割
     * @param firstResult       从第几条开始
     * @param pageSize          每页条数
     * @param pageBool          是否分页，true：分页；false:不分页
     * @return list             互动教学列表
     * @author   caoqian
     */
     List<Map<String,Object>> teachingActivityMasterListByPage(@Param("classRoomIdArr")String[] classRoomIdArr,@Param("firstResult") Integer firstResult,
                                                                     @Param("pageSize") Integer pageSize,@Param("pageBool") boolean pageBool); //分页获取主讲互动教学列表

    /**
     * 根据活动名称及开始、结束时间获取会议号
     * @param name         会议名称
     * @param startTime    开始时间
     * @param endTime      结束时间
     * @return  String 会议id
     * @author   caoqian
     */
     String getConfID(@Param("name")String name,@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("roomId")String roomId);

    /**
     * 获取主讲教室预约互动
     * @param nowDate     日期
     * @param roomId   主讲教室id
     * @return
     */
    List<Map<String,Object>> getMasterActivities(@Param("nowDate") String nowDate, @Param("roomId") String roomId);

    /**
     * 获取一周的周课时数量
     * @param activityInfo         主讲信息
     * @param assActivityInfoArr   接收信息
     * @param startTime            周开始时间
     * @param endTime              周结束时间
     * @return
     */
    String getWeekTotalHour(@Param("activityInfo") String activityInfo, @Param("assActivityInfoArr") String[] assActivityInfoArr,
                             @Param("startTime") String startTime, @Param("endTime") String endTime);
}
