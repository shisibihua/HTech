package com.honghe.tech.service;

import com.honghe.tech.form.ActivityInfoForm;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 *
 * @author xinqinggang
 * @date 2018/1/25
 */
public interface ActivityService {
    /**
     * 添加教学活动信息
     * @param jsonInfo
     * @return
     */
    Object saveActivity(String jsonInfo);

    /**
     * 根据教学活动id修改教学活动信息
     * @param activityInfo 教学活动修改信息
     * @return
     */
     Object updateActivityByParam(JSONObject activityInfo);

    /**
     * 通过当天日期（格式：2018-01-01）得到教学活动集合
     * @param date 当前时间
     * @return
     * @Author LiZhuoyuan 2018-01-26
     */
     JSONObject getActivitiesByDate(String date);

    /**
     * 通过当天日期（格式：2018-01-01）得到教学活动集合
     * @param date 当前时间
     * @param ip 终端ip
     * @param id 终端id
     * @param flag true:互动2.0；false:互动1.0
     * @return
     * @author caoqian
     */
     JSONObject getActivitiesByDateByIp(String date,String ip,String id,boolean flag);

    /**
     * 得到整点区间的教学活动数据
     * @param startTime 开始时间 格式：2018-01-01 08:00
     * @param date      开始日期 格式：2018-01-01
     * @return
     */
     JSONObject getActivitiesByTime(String startTime, String date);

    /**
     * 通过活动id删除教学活动（逻辑删除）
     * @param activityId 教学活动id
     * @param userId
     * @return
     */
     Object deleteActivityById(int activityId,int userId);

    /**
     * 根据活动id获取活动的详细信息
     * @param id 活动id
     * @return 活动详细信息
     */
     ActivityInfoForm getActivityInfoById(int id);

    /**
     * 根据活动id获取活动的详细信息(用于教学活动修改)
     * @param id 教学活动id
     * @return
     */
     JSONObject getActivityById(int id);

    /**
     * 根据地点id查询省、市、区、学校名称
     * @param areaId    地点id
     * @return  map     地点名称
     * @author caoqian
     */
     Map<String,String> getAreaNameMap(String areaId);

    /**
     * 根据教室id获取当天课程预告
     * @param roomId
     * @return 课程预告集合
     */
     JSONArray getPreActivityList(int  roomId);

    /**
     * //根据地点获取时间内每天的课程数
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param provinceId 省id
     * @param cityId 城市id
     * @param countyId 区/县id
     * @param schoolId 学校id
     * @return 每天课程数
     */
     JSONObject getMonthTimetable(String startTime,String endTime,String provinceId,String cityId,String countyId,String schoolId);

    /**
     * 根据地点获取日期下当天所有课程的简略信息
     * @param dateTime 日期，天
     * @param provinceId 省id
     * @param cityId 城市id
     * @param countyId 区县id
     * @param schoolId 学校id
     * @return 课程简略信息（课程名称、课程开始时间、课程结束时间、主讲教师）
     */
     JSONObject getActivityBriefInfo(String dateTime,String provinceId,String cityId,String countyId,String schoolId);

    /**
     * 根据主讲教室分页获取活动列表
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param provinceId 省id
     * @param cityId 市id
     * @param countyId 县/区id
     * @param schoolId 学校id
     * @param activityName 活动名称
     * @param currentPage 当前页
     * @param pageSize 每页条数
     * @return
     */
     JSONObject getActivityInfoByPage(String startTime,String endTime,String provinceId,String cityId,String countyId,String schoolId,String activityName,String currentPage,String pageSize);

    /**
     * 获取当前学期所有周及相应时间
     * @return
     */
     JSONArray getTermWeeks();

    /**
     * 获取教师的周课表
     * @param teacherId   教师id
     * @param startTime 周开始时间
     * @param endTime 周结束时间
     * @return 课节信息
     */
     JSONArray getTimeTableByTeacherId(String teacherId,String startTime,String endTime);

    /**
     * 根据地点参数及教室类型获取指定周内课程
     * @param provinceId 省id(避免为空)
     * @param cityId 市id(不可为空)
     * @param countyId 区县id(不可为空)
     * @param schoolId 学校id(不可为空)
     * @param roomType  教室类型（hostRoom:主讲教室、acceptRoom：接收教室；allType：所有教室）
     * @param roomId   教室id(当教师类型为主讲或接收教室时，可传，null时默认全部该类型的教室)
     * @param startTime 周开始时间
     * @param endTime 周结束时间
     * @return
     */
      JSONArray getTimeTableByAreaId(String provinceId,String cityId,String countyId,String schoolId,String roomType,String roomId,String startTime,String endTime);

    /**
     * 根据学校获取教室
     * @param schoolId 学校id
     * @param roomType 教室类型，hostRoom:主讲教室；acceptRoom:接收教室
     * @param startTime 周开始时间
     * @param endTime 周结束时间
     * @return 教室集合
     */
     List<Map<String,Object>> getRoomBySchool(String schoolId,String roomType,String startTime,String endTime);

    /**
     * 根据地点id和地点类型获取地点及子级地点课程情况
     * @param areaId
     * @param areaType
     * @param hasChild 是否查询子级
     * @return
     */
     JSONObject getAreaCourseCase(String areaId,String areaType,boolean hasChild);

    /**
     * 根据时间及终端ip获取当天课程名称
     * @param startTime   开始时间，如‘2018-01-01 00:00:00’
     * @param endTime    结束时间，如‘2018-01-01 23:59:59’
     * @param masterIp   主讲教室终端ip
     * @param id         终端编码
     * @return list
     * @author caoqian
     */
     List<Map<String,String>> getCourseTodayListByTimeAndMasterIp(String startTime,String endTime,String masterIp,String id);


    /**
     * 获取当前时间主讲课程
     * @param time        当前时间，格式:"2018-01-01 10:00:00"
     * @param masterIp    主讲教室终端ip
     * @param id          终端编码
     * @return list
     * @author caoqian
     */
     List<Map<String,String>> getLectCourseListByTimeAndMasterIp(String time,String masterIp,String id);

    /**
     * 发送通知，同步通知时间
     */
     void asyncNoticeTime();

    /**
     * 预约教室去重
     * @param roomList
     * @param roomIds
     */
     void uniqRoom(List<Map<String,Object>> roomList,String roomIds);

    /**
     * 预约教师去重
     * @param teacherList
     */
     void checkSub(List<Map<String,Object>> teacherList);

    /**
     * 插入数据测试
     * @param num
     * @return
     */
     int insertTest(int num);

    /**
     * 获取party的list集合
     * @param parties 如：[{"partyID":"0","partyIP":"192.168.19.153","partyName":"0","partyProtocol":"h323","partyType":"by_address"}]
     * @return
     * @author caoqian
     */
     JSONArray getPartyArray(String parties);


    /**
     * 根据活动名称及开始、结束时间获取会议号
     * @param name
     * @param startTime
     * @param endTime
     * @param masterIp
     * @return 会议号
     */
     String getConfID(String name,String startTime,String endTime,String masterIp);
}
