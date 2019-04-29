package com.honghe.tech;

import com.honghe.spring.Command;
import com.honghe.spring.sql.SQLImport;
import com.honghe.tech.dao.AreaDao;
import com.honghe.tech.dao.NoticeDao;
import com.honghe.tech.entity.Area;
import com.honghe.tech.dao.ActivityDao;
import com.honghe.tech.entity.Activity;
import com.honghe.tech.form.ActivityInfoForm;
import com.honghe.tech.form.UserForm;
import com.honghe.tech.httpservice.*;
import com.honghe.tech.service.*;
import com.honghe.tech.service.impl.ScheduleServiceImpl;
import com.honghe.tech.util.ConfigUtil;
import com.honghe.tech.util.ConstantWord;
import com.honghe.tech.util.HttpRequestUtil;
import com.honghe.tech.util.WeekDateUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;


/**
 * 互动教学命接口
 *
 * @author xinqinggang
 * @date 2018/1/23
 */
@SQLImport("tech.sql")
@Command("htech")
public class TechCommand {
	private static final String SEARCH_TERMIAL_STATUS = "searchTerminalStatus";
	private static final String SEARCH_QUERY_INTERACT = "searchQueryInteract";
	private static final String SEARCH_MUTECLIENT_STATUS = "searchMuteClientStatus";
	private Logger logger = Logger.getLogger(TechCommand.class);
	private static final String KEY_SUCCESS = "0";
	private static final int MAX_NUM = 3000;
	private static final int PAGE_NUM = 10;
	/**
	 * 捷视飞通终端通用参数
	 */
	//上级域id，如果是顶级域则值为1
	private static final String SUPERDMDID = "1";
	//自动能力集，1支持，0，不支持，填1
	private static final String AUTOCAP = "1";
	//audioAdaptor    音频能力集 填0
	private static final String AUDIOADAPTOR = "0";
	//是否允许删除,(默认可以删除)，填1
	private static final String DELETABLEFLAG = "1";


	/**
	 * 设置日期格式
	 */
	SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdfTimeOfMinute = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	private ActivityService activityService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private NoticeService noticeService;
	@Autowired
	SubjectService subjecService;
	@Autowired
	private StatisticsService statisticsService;
	@Autowired
	private TeachingSupervisionService teachingSupervisionService;
	@Autowired
	private OperLogService operLogService;
	@Autowired
	private ScheduleService scheduleService;
	@Autowired
	private AreaDao areaDao;
	@Autowired
	private DeviceHttpService deviceHttpService;
	@Autowired
	private TermService termService;
	@Autowired
	private ActivityDao activityDao;
	@Autowired
	private ClasstimeService classtimeService;
	@Autowired
	private AreaHttpService areaHttpService;
	@Autowired
	private UserHttpService userHttpService;
	@Autowired
	private UserCheckService userCheckService;
	@Autowired
	private AdHttpService adHttpService;
	@Autowired
	private DeviceToJsftHttpService deviceToJsftHttpService;

/**********************************************李卓远的呆萌分割线(`0`)↓↓↓↓****************************************************************************************************************************************************/
	/**
	 * 添加预约活动
	 *
	 * @return true/false
	 */
	public Object saveActivity(String activityJson) {
		if (activityJson == null || "".equals(activityJson)) {
			logger.debug("日志类型logType为空，参数异常。");
			throw new IllegalArgumentException();
		} else {
			return activityService.saveActivity(activityJson);
		}
	}

	/**
	 * 根据id修改活动信息
	 *
	 * @return true/false
	 */
	public Object updateActivityByParams(String activityInfoStr) {
		if (activityInfoStr == null || "".equals(activityInfoStr)) {
			logger.debug("日志类型logType为空，参数异常。");
			throw new IllegalArgumentException();
		} else {
			JSONObject activityInfo = JSONObject.fromObject(activityInfoStr);
			return activityService.updateActivityByParam(activityInfo);
		}
	}

	/**
	 * 根据活动id获取活动的详细信息（用于修改教学活动）
	 *
	 * @param id 教学活动id
	 * @return
	 */
	public JSONObject getActivityById(String id) {
		JSONObject json = activityService.getActivityById(Integer.valueOf(id));
		return json;
	}

	/**
	 * 得到当天（格式：2018-01-01）的所有教学活动
	 *
	 * @return
	 */
	public JSONObject getActivitiesByDate() {
		JSONObject json = new JSONObject();
		try {
			Date date = new Date();
			json = activityService.getActivitiesByDate(sdfTime.format(date));
		} catch (Exception e) {
			logger.error(this.getClass().getSimpleName() + "---得到当天教学活动异常！", e);
			return null;
		}
		return json;
	}

	/**
	 * 根据终端ip或id得到当天（格式：2018-01-01）的所有教学活动
	 *
	 * @param ip 终端ip或id（终端编码）
	 * @param id 终端id
	 * @return
	 * @author caoqian
	 */
	public JSONObject getActivitiesByDateByIp(String ip, String id) {
		boolean existed = (ip == null || "".equals(ip)) && (id == null || "".equals(id));
		if (existed) {
			logger.debug("终端ip=" + ip + ",终端id=" + id + ",参数异常，获取当天教学活动失败。");
			throw new IllegalArgumentException();
		}
		JSONObject json;
		try {
			Date date = new Date();
			json = activityService.getActivitiesByDateByIp(sdfTime.format(date), ip, id, true);
		} catch (Exception e) {
			logger.error(this.getClass().getSimpleName() + "---得到当天教学活动异常！", e);
			return null;
		}
		return json;
	}

	/**
	 * 得到某个时间点（格式：2018-01-01 ：08:00）后的教学活动信息
	 *
	 * @param time
	 * @return
	 */
	public JSONObject getActivitiesByTime(String time) {
		if (time == null || "".equals(time)) {
			logger.debug("日志类型logType为空，参数异常。");
			throw new IllegalArgumentException();
		} else {
			Date date = new Date();
			String nowDate = sdfTime.format(date);
			String startTime = nowDate + " " + time;
			JSONObject json = new JSONObject();
			try {
				json = activityService.getActivitiesByTime(startTime, sdfTime.format(date));
			} catch (Exception e) {
				logger.error(this.getClass().getSimpleName() + "---得到当天某整点之后教学活动异常！", e);
				return null;
			}
			return json;
		}
	}


	/**
	 * 逻辑删除教学活动
	 *
	 * @param activityId 教学活动id
	 * @return
	 */
	public Object deleteActivity(String activityId, String userId) {
		return activityService.deleteActivityById(Integer.valueOf(activityId), Integer.valueOf(userId));
	}

	/**
	 * 获得市/县区统计方法
	 *
	 * @param schoolyearId 学年id
	 * @param termId       学期id
	 * @param placeKey     区域标志
	 * @param provinceId   省份id
	 * @return
	 */
	public JSONArray getRegionlRanking(String schoolyearId, String termId, String placeKey, String provinceId, String type) {
		String city = "city";
		String county = "county";
		if (city.equals(placeKey)) {
			JSONArray json = getCityClassHour(schoolyearId, termId, provinceId, type);
			return json;
		} else if (county.equals(placeKey)) {
			JSONArray json = getCountyClassHour(schoolyearId, termId, provinceId, type);
			return json;
		} else {
			JSONArray json = getSchoolClassHour(schoolyearId, termId, provinceId, type);
			return json;
		}
	}

	/**
	 * 得到地点课时数排行前十名公用方法
	 *
	 * @param placeList 地点id数组
	 * @param placeKey  区域标识符
	 * @return
	 */
	public JSONArray getClassHour(String schoolyearId, String termId, List<Map<String, String>> placeList, String placeKey, String type) {
		Map<String, String> mapParameter = termService.getTimeRange(schoolyearId, termId, type);
		List<Map<String, String>> list = new ArrayList<>();
		long count = 0;
		mapParameter.put("cityOrCountyOrSchool", placeKey);
		// TODO: 2018/6/1 循环每个地点
		for (int i = 0; i < placeList.size(); i++) {
			Map<String, String> mapDate = new HashMap<>();
			String areaId = placeList.get(i).get("areaId");
			//这里应该是从循环遍历中得到每一个地点的id
			mapParameter.put("placeId", areaId);
			try {
				count = statisticsService.getClassHour(mapParameter);
			} catch (Exception e) {
				logger.error(this.getClass().getSimpleName() + "---得到城市、区县、学校课时数异常！", e);
			}
			mapDate.put("name", placeList.get(i).get("areaName"));
			mapDate.put("number", String.valueOf(count));
			Map<String, String> areaInfoMap = null;
			switch (placeKey) {
				case "county":
					areaInfoMap = statisticsService.getAreaInfoByAreaId("%-%-" + areaId + "-%");
					if (areaInfoMap != null && areaInfoMap.get("city") != null) {
						mapDate.put("cityName", areaInfoMap.get("city"));
					}
					break;
				case "school":
					areaInfoMap = statisticsService.getAreaInfoByAreaId("%-%-%" + areaId);
					if (areaInfoMap != null && areaInfoMap.get("city") != null && areaInfoMap.get("county") != null) {
						mapDate.put("cityName", areaInfoMap.get("city"));
						mapDate.put("countyName", areaInfoMap.get("county"));
					}
					break;
				default:
					break;
			}
			list.add(mapDate);
		}
		//TODO:}  循环结束
		//return JSONArray.fromObject(list);//TODO:公共服务可以使用后，这里返回调用“getClassHoursTop10”方法后的数据
		return getClassHoursTop10(list);
	}

	/**
	 * 得到地点课时数排行前十名公用方法
	 *
	 * @param placeList 地点id数组
	 * @param placeKey  区域标识符
	 * @return
	 */
	public JSONArray getClassHour2(String schoolyearId, String termId, List<Map<String, String>> placeList, String placeKey, String type) {
		Map<String, String> termMap = termService.getTimeRange(schoolyearId, termId, type);
		//获取活动表中所有不同该类型地点下的活动数量
		List<Map<String, Object>> areaAcList = statisticsService.getAcCountByAreaGroup(termMap.get("startTime"), termMap.get("endTime"), placeKey);

		return statisticsService.getAreaAcInfo(placeKey, areaAcList);
	}

	/**
	 * 得到城市课时排名
	 *
	 * @param schoolyearId 学年id
	 * @param termId       学期id
	 * @param provinceId   省份id
	 * @return
	 */
	public JSONArray getCityClassHour(String schoolyearId, String termId, String provinceId, String type) {
		List<Map<String, String>> list = new ArrayList<>();
		String placeKey = "city";
		JSONArray json;
		try {
			json = getClassHour2(schoolyearId, termId, list, placeKey, type);
		} catch (Exception e) {
			logger.error("得到城市课时排名异常", e);
			return null;
		}
		return json;
	}

	/**
	 * 得到区县课时排行统计数据
	 *
	 * @param termId     学期id
	 * @param provinceId 省份id
	 * @return
	 */
	public JSONArray getCountyClassHour(String schoolyearId, String termId, String provinceId, String type) {
		List<Map<String, String>> list = new ArrayList<>();
		String placeKey = "county";
		JSONArray json = new JSONArray();
		try {
			json = getClassHour2(schoolyearId, termId, list, placeKey, type);
		} catch (Exception e) {
			logger.error("得到区县课时排行统计数据异常", e);
			return null;
		}
		return json;
	}

	/**
	 * 得到学校课时排行统计数据
	 *
	 * @param termId       学期id
	 * @param schoolyearId 学年id
	 * @param provinceId   省份id
	 * @return
	 */
	public JSONArray getSchoolClassHour(String schoolyearId, String termId, String provinceId, String type) {
		List<Map<String, String>> list = new ArrayList<>();
		String placeKey = "school";
		JSONArray json = new JSONArray();
		try {
			json = getClassHour2(schoolyearId, termId, list, placeKey, type);
		} catch (Exception e) {
			logger.error("得到学校课时排行统计数据异常", e);
			return null;
		}
		return json;
	}

	/**
	 * 得到教师课时数统计前十名
	 *
	 * @param termId       学期id
	 * @param schoolyearId 学年id
	 * @return
	 */
	public JSONArray getTeacherClassHour(String schoolyearId, String termId, String type) {
		List<Map<String, String>> listInfo = new ArrayList<>();
		Map<String, String> mapParameter = termService.getTimeRange(schoolyearId, termId, type);
		List<Map<String, Object>> list = statisticsService.getTeacherOfStatistics(mapParameter);
		for (Map<String, Object> map : list) {
			Map<String, String> mapInfo = new HashMap<>();
			Area area = areaDao.getAreaMessage(map.get("areaId").toString());
			mapInfo.put("name", area.getCity().toString() + area.getCounty() + area.getSchool() + map.get("hostName").toString());
			mapInfo.put("number", map.get("num").toString());
			listInfo.add(mapInfo);
		}
		JSONArray json = getClassHoursTop10(listInfo);
		return json;
	}

	/**
	 * 得到课程课时数统计前十名
	 *
	 * @param termId       学期id
	 * @param schoolyearId 学年id
	 * @return
	 */
	public JSONArray getSubjectOfStatistics(String schoolyearId, String termId, String type) {
		List<Map<String, String>> listInfo = new ArrayList<>();
		Map<String, String> mapParameter = termService.getTimeRange(schoolyearId, termId, type);
		List<Map<String, Object>> list = statisticsService.getSubjectOfStatistics(mapParameter);
		for (Map<String, Object> map : list) {
			Map<String, String> mapInfo = new HashMap<>();
			mapInfo.put("name", map.get("subjectName").toString());
			mapInfo.put("number", map.get("num").toString());
			listInfo.add(mapInfo);
		}
		JSONArray json = getClassHoursTop10(listInfo);
		return json;
	}

	/**
	 * 统计冒泡排序返回前十名方法
	 *
	 * @param list
	 * @return
	 */
	public JSONArray getClassHoursTop10(List<Map<String, String>> list) {
		try {
			for (int i = 0; i < list.size() - 1; i++) {
				for (int j = 0; j < list.size() - i - 1; j++) {
					Map<String, String> mapOne = new HashMap<>();
					Map<String, String> mapTwo = new HashMap<>();
					int one = Integer.valueOf(list.get(j).get("number"));
					int two = Integer.valueOf(list.get(j + 1).get("number"));
					if (one < two) {
						int temp;
						temp = one;
						one = two;
						two = temp;

						mapOne.put("name", list.get(j + 1).get("name").toString());
						mapOne.put("number", String.valueOf(one));
						if (list.get(j + 1).get("cityName") != null && !"".equals(list.get(j + 1).get("cityName"))) {
							mapOne.put("cityName", list.get(j + 1).get("cityName"));
						}
						if (list.get(j + 1).get("countyName") != null && !"".equals(list.get(j + 1).get("countyName"))) {
							mapOne.put("countyName", list.get(j + 1).get("countyName"));
						}
						mapTwo.put("name", list.get(j).get("name").toString());
						if (list.get(j).get("cityName") != null && !"".equals(list.get(j).get("cityName"))) {
							mapTwo.put("cityName", list.get(j).get("cityName"));
						}
						if (list.get(j).get("countyName") != null && !"".equals(list.get(j).get("countyName"))) {
							mapOne.put("countyName", list.get(j).get("countyName"));
						}
						mapTwo.put("number", String.valueOf(two));
						list.set(j, mapOne);
						list.set(j + 1, mapTwo);
					}
				}
			}
			//条数大于10时候返回前十条
			if (list.size() > PAGE_NUM) {
				return JSONArray.fromObject(list.subList(0, 10));
			} else {
				//小于10则返回全部
				return JSONArray.fromObject(list);
			}
		} catch (NumberFormatException e) {
			logger.error("统计冒泡排序返回前十名方法異常", e);
			return null;
		}
	}

/*********************************************李卓远的呆萌分割线(`0`)↑↑↑↑*****************************************************************************************************************************************************/

	/**
	 * 保存评论及回复的评论信息
	 *
	 * @param userId     评论用户id
	 * @param replayId   评论id
	 * @param activityId 活动id
	 * @param visible    评论是否可见
	 * @param content    评论内容
	 * @author caoqian
	 */
	public boolean saveComment(String userId, String replayId, String activityId, String visible, String content) {
		return commentService.saveCommentTable(userId, replayId, activityId, visible, content);
	}

	/**
	 * 删除评论，删除主评论则把回复的评论也删除，否则只删除单条评论
	 *
	 * @param commentId 评论id
	 * @param replayId  发布的评论为0，回复的评论为回复评论的id
	 * @param userId    用户id
	 * @return boolean        true:删除成功；false:删除失败
	 * @author caoqian
	 */
	public boolean deleteComment(String commentId, String replayId, String userId) {
		return commentService.deleteCommentTable(commentId, replayId, userId);
	}

	/**
	 * 分页获取直播评论
	 *
	 * @param activityId  活动id
	 * @param currentPage 当前页
	 * @param pageSize    每页条数
	 * @return
	 * @author caoqian
	 */
	public Map<String, Object> getCommentListByPage(String activityId, String currentPage, String pageSize) {
		return commentService.getCommentsListByPage(activityId, currentPage, pageSize);
	}

	/**
	 * 修改通知状态为已读
	 *
	 * @param noticeIdsArr 通知id，可多个，用","分割
	 * @param userId       用户id
	 * @return
	 * @author caoqian
	 */
	public boolean updateNoticeStatus(String[] noticeIdsArr, String userId) {
		return noticeService.changNoticeStatusTable(noticeIdsArr, userId);
	}

	/**
	 * 分页获取通知
	 *
	 * @param userId      用户id
	 * @param currentPage 当前页
	 * @param pageSize    每页条数
	 * @return
	 * @author caoqian
	 */
	public Map<String, Object> getNoticeListByPage(String userId, String currentPage, String pageSize) {
		return noticeService.getNoticesListByPage(userId, currentPage, pageSize);
	}

	/**
	 * 分页获取教学监管直播数据
	 *
	 * @param proviceId   省id
	 * @param cityId      城市id
	 * @param contyId     区(县)id
	 * @param schoolId    学校id
	 * @param selectType  教室状态，''-全部教室，0-未上课，1-上课中
	 * @param currentPage 当前页
	 * @param pageSize    每页数据条数
	 * @return
	 * @author caoqian
	 */
	public Map<String, Object> getTechSuperviseListByPage(String proviceId, String cityId, String contyId, String schoolId,
														  String selectType, String currentPage, String pageSize) {
		return teachingSupervisionService.techSuperviseListByPage(proviceId, cityId, contyId, schoolId, selectType, currentPage, pageSize);
	}

	/**
	 * 1拖三教学监管，查看详情
	 *
	 * @param uuid        教学活动与接收表对应uuid
	 * @param currentPage 当前页
	 * @param pageSize    每页数据条数
	 * @return map         教学监管一拖三数据
	 * @author caoqian
	 */
	public Map<String, Object> getTechingList(String uuid, String currentPage, String pageSize) {
		return teachingSupervisionService.techSuperviseList(uuid, currentPage, pageSize);
	}

	//----------------------------------------------------------查看课程列表---------------------------------------

	/**
	 * 根据活动id查看活动详细信息
	 *
	 * @param activityId 教学活动的id
	 * @return 教学活动的信息
	 */
	public ActivityInfoForm getActivityInfoByActivityId(String activityId) {
		ActivityInfoForm activityInfoForm = activityService.getActivityInfoById(Integer.valueOf(activityId));
		return activityInfoForm;
	}

	/**
	 * 根据地点、时间分页获取活动信息
	 *
	 * @param startTime    开始时间
	 * @param endTime      结束时间
	 * @param provinceId   省id
	 * @param cityId       市id
	 * @param countyId     县/区id
	 * @param schoolId     学校id
	 * @param activityName 活动名称
	 * @param currentPage  当前页
	 * @param pageSize     每页条数
	 * @return
	 */
	public JSONObject getActivityInfoByPage(String startTime, String endTime, String provinceId, String cityId, String countyId, String schoolId, String activityName, String currentPage, String pageSize) {
		JSONObject jsonObject = activityService.getActivityInfoByPage(startTime, endTime, provinceId, cityId, countyId, schoolId, activityName, currentPage, pageSize);
		return jsonObject;
	}

	/**
	 * 根据教室id获取当天课程预告
	 *
	 * @param roomId
	 * @return 课程预告集合
	 */
	public JSONArray getPreActivityList(String roomId) {
		return activityService.getPreActivityList(Integer.valueOf(roomId));
	}

	/**
	 * 根据地点获取日期下当天所有课程的简略信息
	 *
	 * @param dateTime   日期，天
	 * @param provinceId 省id
	 * @param cityId     城市id
	 * @param countyId   区县id
	 * @param schoolId   学校id
	 * @return 课程简略信息（课程名称、课程开始时间、课程结束时间、主讲教师）
	 */
	public JSONObject getActivityBriefInfo(String dateTime, String provinceId, String cityId, String countyId, String schoolId) {
		JSONObject obj = activityService.getActivityBriefInfo(dateTime, provinceId, cityId, countyId, schoolId);
		return obj;
	}

	/**
	 * 根据地点获取指定月每天课程数量
	 *
	 * @param startDate  开始时间
	 * @param endDate    结束时间
	 * @param provinceId 省id
	 * @param cityId     城市id
	 * @param countyId   区/县id
	 * @param schoolId   学校id
	 * @return 每天课程数
	 */
	public JSONObject getActivityNumByAreaId(String startDate, String endDate, String provinceId, String cityId, String countyId, String schoolId) {
		return activityService.getMonthTimetable(startDate, endDate, provinceId, cityId, countyId, schoolId);
	}

	//----------------------------------------------------------end------------------------------------------------

	//-----------------------------------------------------------周课表-------------------------------------------------

	/**
	 * 获取周课表
	 *
	 * @return
	 */
	public JSONArray getTermWeek() {
		return activityService.getTermWeeks();
	}

	/**
	 * 根据地点参数及教室类型获取指定周内课程
	 *
	 * @param provinceId 省id(避免为空)
	 * @param cityId     市id(不可为空)
	 * @param countyId   区县id(不可为空)
	 * @param schoolId   学校id(不可为空)
	 * @param roomType   教室类型（hostRoom:主讲教室、acceptRoom：接收教室；allRoom：所有教室）
	 * @param roomId     教室id(当教师类型为主讲或接收教室时，可传，null时默认全部该类型的教室)
	 * @param startTime  周开始时间
	 * @param endTime    周结束时间
	 * @return
	 */
	public JSONArray getTimeTableByAreaId(String provinceId, String cityId, String countyId, String schoolId, String roomType, String roomId, String startTime, String endTime) {
		return activityService.getTimeTableByAreaId(provinceId, cityId, countyId, schoolId, roomType, roomId, startTime, endTime);
	}


	/**
	 * @param teacherId 教师id
	 * @param startTime 周开始时间
	 * @param endTime   周结束时间
	 * @return 课节信息
	 */
	public JSONArray getTimeTableByTeacherId(String teacherId, String startTime, String endTime) {
		return activityService.getTimeTableByTeacherId(teacherId, startTime, endTime);
	}

	/**
	 * 根据学校获取教室
	 *
	 * @param schoolId  学校id
	 * @param roomType  教室类型，hostRoom:主讲教室；acceptRoom:接收教室
	 * @param startTime 周开始时间
	 * @param endTime   周结束时间
	 * @return 教室集合
	 * 无用接口
	 */
	public List<Map<String, Object>> getRoomBySchool(String schoolId, String roomType, String startTime, String endTime) {
		return activityService.getRoomBySchool(schoolId, roomType, startTime, endTime);
	}

	/**
	 * 根据地点id和地点类型获取地点及子级地点课程情况
	 *
	 * @param areaId        地点ID
	 * @param areaType      地点类型
	 * @param hasTermCourse 课程校验
	 * @return
	 */
	public JSONObject getAreaCourseCase(String areaId, String areaType, boolean hasTermCourse) {
		return activityService.getAreaCourseCase(areaId, areaType, hasTermCourse);
	}

	/**
	 * 查询所有学年信息
	 *
	 * @return 学年集合
	 */
	public List<Map<String, Object>> getTermBySchoolyearId(String schoolyearId) {
		return termService.getTermBySchoolyearId(schoolyearId);
	}

	/**
	 * 查询所有学年信息
	 *
	 * @return 学年集合
	 */
	public List<Map<String, Object>> getAllSchoolyear() {
		return termService.getAllSchoolyear();
	}

	/**
	 * @param infoJson 学期或假期信息json字符串
	 * @param operType 操作类型 add:添加；update:修改
	 * @return 0:操作成功；-1:与其它学期信息冲突；-2:未知操作失败；-3:未知操作；-4:该学期名称已存在
	 */
	public Object addOrUpdateInfo(String infoJson, String operType, String userId) {
		return termService.addOrUpdateInfo(infoJson, operType, Integer.valueOf(userId));
	}

	/**
	 * 查询所有学期或假期 信息
	 *
	 * @param currentPage 当前页
	 * @param pageSize    每页条数
	 * @param type        1:学期信息；2：假期信息
	 * @return 学期或假期分页信息
	 */
	public JSONObject getTermInfo(String currentPage, String pageSize, String type) {
		return termService.findInfoByPage(currentPage, pageSize, Integer.valueOf(type));
	}

	/**
	 * 根据学期或假期信息id更改信息状态
	 *
	 * @param termId 学期或假期信息id
	 * @param status 状态(0:关闭；1:开启)
	 * @return 是否更改成功
	 */
	public Object updateTermStatus(String termId, String status, String userId) {
		return termService.updateInfoStatus(Integer.valueOf(termId), Integer.valueOf(status), Integer.valueOf(userId));
	}

	/**
	 * 根据信息状态查看学期或假期信息
	 *
	 * @param type   学期(1)或假期信息(2)
	 * @param status 状态(0/1)
	 * @return 学期或假期信息
	 */
	public List<Map<String, String>> selectInfoByStatus(String status, String type) {
		return termService.selectInfoByStatus(status, Integer.valueOf(type));
	}

	/**
	 * 根据学期或假期信息id删除信息
	 *
	 * @param termId 学期或假期信息id
	 * @return 是否删除成功
	 */
	public Object deleteTermInfo(String termId, String userId) {
		return termService.deleteInfo(Integer.valueOf(termId), Integer.valueOf(userId));
	}

	/**
	 * 根据信息id查看学期信息
	 *
	 * @param termId 信息id
	 * @return
	 */
	public Map<String, Object> selectInfoById(String termId) {
		return termService.selectInfoById(Integer.valueOf(termId));
	}

	/**
	 * 保存日志
	 *
	 * @param userId  用户id
	 * @param time    保存日志时间
	 * @param module  模块功能
	 * @param content 日志内容
	 * @param type    日志类型,0:操作日志，1:系统日志
	 * @return boolean     true:保存成功；fasle：保存失败
	 * @author caoqian
	 */
	public boolean saveLog(String userId, String time, String module, String content, String type) {
		Map<String, Object> params = new HashMap<>();
		if (userId == null || "".equals(userId) || type == null || "".equals(type)
				|| module == null || "".equals(module) || content == null || "".equals(content)) {
			logger.debug("用户id:" + userId + ",日志类型type:" + type + ",模块功能module:" + module + ",日志内容content:" + content + ",保存日志失败，参数异常。");
			throw new IllegalArgumentException();
		} else {
			params.put("userId", userId);
			Date now = new Date();
//            params.put("logTime", time == null ? sdfTimeOfMinute.format(now) : time); //避免传递的时间与服务器时间不一致，导致日志显示错乱
			params.put("logTime", sdfTimeOfMinute.format(now));
			params.put("moduleName", module == null ? "" : module);
			params.put("content", content == null ? "" : content);
			params.put("type", type);
			return operLogService.saveLogTable(params);
		}
	}

	/**
	 * 根据日志ids删除日志
	 *
	 * @param ids 日志id,多个","分割，如"1,2,3"
	 * @return boolean       true:删除成功;false:删除失败
	 * @author caoqian
	 */
	public boolean deleteLogByIds(String ids) {
		return operLogService.deleteLogTableByIds(ids);
	}

	/**
	 * 根据日期范围及日志类型分页查询
	 *
	 * @param logType     日志类型，""：全部日志类型；0：操作日志；1:系统日志
	 * @param startDate   开始日期,不是必须
	 * @param endDate     结束日期,不是必须
	 * @param currentPage 当前页
	 * @param pageSize    每页条数
	 * @return map            日志数据
	 * @author caoqian
	 */
	public Map<String, Object> getLogListByPage(String logType, String startDate, String endDate, String currentPage, String pageSize) {
		return operLogService.getLogsByDateByPage(logType, startDate, endDate, currentPage, pageSize);
	}

	/**
	 * 根据日期及日志类型导出日志
	 *
	 * @param startDate 开始日期，格式：2018-01-06,不是必须
	 * @param endDate   结束日期，格式：2018-01-07，不是必须
	 * @param logType   日志类型，""全部日志，"0"操作日志,"1"系统日志
	 * @return String      日志文件地址
	 * @author caoqian
	 */
	public String exportLog(String startDate, String endDate, String logType) {
		String filePath = "";
		try {
			filePath = operLogService.writeExcel(startDate, endDate, logType);
		} catch (IOException e) {
			logger.error("导出日志异常。", e);
			filePath = "";
		}
		return filePath;
	}

	/**
	 * 分页查询作息策略，可支持名称模糊查询
	 *
	 * @param name        作息策略名称，不是必须
	 * @param currentPage 当前页
	 * @param pageSize    每页条数
	 * @return map           作息策略数据
	 * @author caoqian
	 */
	public Map<String, Object> getPloyListByPage(String name, String currentPage, String pageSize) {
		return scheduleService.getPloyListByPage(name, currentPage, pageSize);
	}

	//-----------------------------------------------------作息策略-----------------------------------------------------

	/**
	 * 根据策略id删除策略
	 *
	 * @param userId     用户id
	 * @param scheduleId 策略id
	 * @return
	 */
	public Object deleteSchedule(String userId, String scheduleId) {
		int result = (int) scheduleService.deleteScheduTable(Integer.valueOf(userId), Integer.valueOf(scheduleId));
		Map<String, Object> logParams = new HashMap<>();
		logParams.put("userId", userId);
		Date now = new Date();
		logParams.put("logTime", sdfTimeOfMinute.format(now));
		logParams.put("moduleName", ScheduleServiceImpl.MODULE_NAME);
		logParams.put("type", ScheduleServiceImpl.LOG_TYPE);
		if (result == 0) {
			logParams.put("content", ScheduleServiceImpl.PLOY_DELETE_SUCCESS);
		} else {
			logParams.put("content", ScheduleServiceImpl.PLOY_DELETE_FAILED);
		}
		operLogService.saveLogTable(logParams);
		return result;
	}

	/**
	 * 根据作息策略修改策略启用状态
	 *
	 * @param id       策略id
	 * @param userId   用户id
	 * @param isEnable 启用状态，0：禁用；1：启动
	 * @return 0：修改成功；-1：不存在；-2：修改失败
	 */
	public Object updatePloyById(String id, String userId, String isEnable) {
		int result = (int) scheduleService.updateScheduleStatusById(Integer.valueOf(id), Integer.valueOf(userId), isEnable);
		updateScheduleLog(userId, isEnable, result);
		return result;
	}

	/**
	 * 修改策略启用状态，保存日志
	 *
	 * @param userId
	 * @param isEnable
	 * @param result
	 */
	public void updateScheduleLog(String userId, String isEnable, int result) {
		Map<String, Object> logParams = new HashMap<>();
		logParams.put("userId", userId);
		Date now = new Date();
		logParams.put("logTime", sdfTimeOfMinute.format(now));
		logParams.put("moduleName", ScheduleServiceImpl.MODULE_NAME);
		logParams.put("type", ScheduleServiceImpl.LOG_TYPE);
		if (result == 0) {
			switch (Integer.parseInt(isEnable)) {
				case 0:
					logParams.put("content", ScheduleServiceImpl.PLOY_UPDATE_NO_ISENABLE_SUCCESS);
					break;
				case 1:
					logParams.put("content", ScheduleServiceImpl.PLOY_UPDATE_ISENABLE_SUCCESS);
					break;
				default:
					break;
			}
		} else {
			switch (Integer.parseInt(isEnable)) {
				case 0:
					logParams.put("content", ScheduleServiceImpl.PLOY_UPDATE_NO_ISENABLE_FAILED);
					break;
				case 1:
					logParams.put("content", ScheduleServiceImpl.PLOY_UPDATE_ISENABLE_FAILED);
					break;
				default:
					break;
			}
		}
		operLogService.saveLogTable(logParams);
	}

	/**
	 * 根据作息策略id修改策略名称
	 *
	 * @param scheduleId   策略id
	 * @param scheduleName 策略名称
	 * @return 0：修改成功；-1：不存在；-2：修改失败
	 */
	public int updateScheduleNameById(int scheduleId, String scheduleName) {
		return scheduleService.updateScheduleNameById(scheduleId, scheduleName);
	}

	/**
	 * 查询所有作息策略
	 *
	 * @return 策略
	 */
	public List<Map<String, String>> getScheduleListAll() {
		return scheduleService.getScheduleListAll();
	}

	/**
	 * 保存作息策略
	 *
	 * @param userId   用户id
	 * @param schedule 策略数据
	 * @return boolean  1：保存成功；-1：已存在；-2：保存失败
	 */
	public Object saveScheduleTable(String userId, String schedule) {
		Object obj = null;
		if (userId == null || "".equals(userId) || schedule == null || "".equals(schedule)) {
			logger.debug("保存作息策略失败，userId=" + userId + ",策略信息schedule=" + schedule);
		} else {
			JSONObject scheduleObject = JSONObject.fromObject(schedule);
			obj = classtimeService.saveScheduleTable(Integer.valueOf(userId), scheduleObject);
		}
		return obj;
	}

	/**
	 * 根据策略id获取策略详细信息
	 *
	 * @param scheduleId
	 * @return
	 */
	public JSONObject getScheduleInfoByScheduleId(String scheduleId) {
		return classtimeService.getScheduleInfoByScheduleId(Integer.valueOf(scheduleId));
	}

	/**
	 * 修改作息策略
	 *
	 * @param userId   用户id
	 * @param schedule 策略数据
	 * @return
	 */
	public Object updateScheduleTable(String userId, String schedule) {
		Object obj = null;
		if (userId == null || "".equals(userId) || schedule == null || "".equals(schedule)) {
			logger.debug("保存作息策略失败，userId=" + userId + ",策略信息schedule=" + schedule);
		} else {
			JSONObject scheduleObject = JSONObject.fromObject(schedule);
			obj = classtimeService.updateScheduleTable(Integer.valueOf(userId), scheduleObject);
		}
		return obj;
	}

	/**
	 * 根据作息策略id查询节次上课时间
	 *
	 * @param ployId 作息策略id
	 * @return list     节次时间
	 * @author caoqian
	 */
	public List<Map<String, Object>> getPloyListById(String ployId) {
		return classtimeService.getScheduleListByPloyId(ployId);
	}
	//-----------------------------------------------------end----------------------------------------------------------
	//-----------------------------------------------------学科---------------------------------------------------------

	/**
	 * 查询所有学段
	 *
	 * @return 学段集合
	 */
	public List<Map<String, Object>> getAllPeriod() {
		return subjecService.getAllPeriod();
	}

	/**
	 * 根据学段查询所有学科目录
	 *
	 * @param periodId 学段id
	 * @return 学科集合
	 */
	public List selectAllSubByPeriodId(String periodId) {
		return subjecService.selectAllSubByPeriodId(Integer.valueOf(periodId));
	}

	/**
	 * 根据学段保存学科信息
	 *
	 * @param subName  学科名称
	 * @param periodId 学段id
	 * @param userId   用户id
	 * @return true/false
	 */
	public Object saveSubjectByPeriodId(String subName, String periodId, String userId) {
		return subjecService.saveSubjectByPeriodId(subName, Integer.valueOf(periodId), Integer.valueOf(userId));
	}

	/**
	 * 更改学科信息
	 *
	 * @param subName  学科名称
	 * @param periodId 学段id
	 * @param subId    学科id
	 * @param userId   用户id
	 * @return true/false
	 */
	public Object updateSubject(String subName, String periodId, String subId, String userId) {
		return subjecService.updateSubject(subName, Integer.valueOf(periodId), Integer.valueOf(subId), Integer.valueOf(userId));
	}

	/**
	 * 根据学科id删除课程
	 *
	 * @param subId
	 * @param userId
	 * @return
	 */
	public Object delSub(String subId, String userId) {
		return subjecService.delSub(Integer.valueOf(subId), Integer.valueOf(userId));
	}

	/**
	 * 根据学科id查询学科信息
	 *
	 * @param subId 学科id
	 * @return 学科信息
	 */
	public Map<String, Object> getSubById(String subId) {
		return subjecService.selectSubById(Integer.valueOf(subId));
	}
	//-----------------------------------------------------end----------------------------------------------------------

	/**
	 * 呼叫终端
	 *
	 * @param url      被叫url，以sip:或h323:开始
	 * @param name     终端名称
	 * @param rate     呼叫速率
	 * @param audio    可选: inactive: 不启用音频；sendonly:只发送不接收音频；recvonly:只接收不发送音频；sendrecv:收发音频。默认为sendrecv
	 * @param masterIp 主讲教室终端ip
	 * @return boolean   true:呼叫成功;false:呼叫失败
	 * @author caoqian
	 */
	public boolean callClient(String url, String name, String rate, String audio, String masterIp) {
		logger.debug("callClient:url=" + url + ",name=" + name + ",rate=" + rate + ",audio=" + audio + ",masterIp=" + masterIp);
		boolean flag = false;
		Object o = deviceHttpService.callClient(url, name, rate, audio, masterIp);
		return getCode(o, flag);
	}

	/**
	 * 挂断终端
	 *
	 * @param url      被叫url，以sip:或h323:开始
	 * @param name     终端名称
	 * @param masterIp 主讲教室终端ip
	 * @return boolean   true：挂断终端成功;false:挂断终端失败
	 * @author caoqian
	 */
	public boolean disconClient(String url, String name, String masterIp) {
		logger.debug("disconClient:url=" + url + ",name=" + name + ",masterIp=" + masterIp);
		boolean flag = false;
		Object o = deviceHttpService.disconClient(url, name, masterIp);
		return getCode(o, flag);
	}

	/**
	 * 挂断全部终端
	 *
	 * @param url      被叫url，以sip:或h323:开始
	 * @param masterIp 主讲教室终端ip
	 * @return boolean   true：挂断全部终端成功;false:挂断全部终端失败
	 * @author caoqian
	 */
	public boolean disconAllClient(String url, String masterIp) {
		logger.debug("disconAllClient:url=" + url + ",masterIp=" + masterIp);
		boolean flag = false;
		Object o = deviceHttpService.disconAllClient(url, masterIp);
		return getCode(o, flag);
	}

	/**
	 * 请求呼叫记录
	 *
	 * @param url      被叫url，以sip:或h323:开始
	 * @param masterIp 主讲教室终端ip
	 * @return boolean   true：请求呼叫记录成功;false:请求呼叫记录失败
	 * @author caoqian
	 */
	public boolean callRecord(String url, String masterIp) {
		logger.debug("callRecord:url=" + url + ",masterIp=" + masterIp);
		boolean flag = false;
		Object o = deviceHttpService.callRecord(url, masterIp);
		return getCode(o, flag);
	}

	/**
	 * 终端静音
	 *
	 * @param url       被叫url，以sip:或h323:开始
	 * @param muteAudio false：代表不静音，true：代表静音
	 * @param name      终端名称
	 * @param masterIp  主讲教室终端ip
	 * @param acceptIp  接收教室终端ip
	 * @return boolean    true：终端静音成功;false:终端静音失败
	 * @author caoqian
	 */
	public boolean muteClient(String url, String muteAudio, String name, String masterIp, String acceptIp) {
		logger.debug("muteClient:url=" + url + ",muteAudio=" + muteAudio + ",name=" + name + ",masterIp=" + masterIp + ",acceptIp=" + acceptIp);
		boolean flag = false;
		Object o = deviceHttpService.muteClient(url, muteAudio, name, masterIp, acceptIp);
		return getCode(o, flag);
	}

	/**
	 * 开启互动
	 *
	 * @param url      被叫url，以sip:或h323:开始
	 * @param name     终端名称
	 * @param interact false：代表关闭互动，true：代表开启互动
	 * @param masterIp 主讲教室终端ip
	 * @param acceptIp 接收教室终端ip
	 * @return boolean    true：开启互动成功;false:开启互动失败
	 * @author caoqian
	 */
	public boolean interactionConference(String url, String name, String interact, String masterIp, String acceptIp) {
		logger.debug("interactionConference:url=" + url + ",name=" + name + ",interact=" + interact + ",masterIp=" + masterIp + ",acceptIp=" + acceptIp);
		boolean flag = false;
		Object o = deviceHttpService.interactionConference(url, name, interact, masterIp, acceptIp);
		return getCode(o, flag);
	}

	/**
	 * 获取终端在会状态
	 *
	 * @param url      被叫url，以sip:或h323:开始
	 * @param name     接收终端名称
	 * @param masterIp 主讲终端ip
	 * @return object
	 * @author caoqian
	 */
	public Object searchTerminalStatus(String url, String name, String masterIp) {
		logger.debug("searchTerminalStatus:url=" + url + ",name=" + name + ",masterIp=" + masterIp);
		boolean status = false;
		if (url == null || "".equals(url) || name == null || "".equals(name) || masterIp == null || "".equals(masterIp)) {
			logger.debug("获取终端在会状态失败，url=" + url + ",接收终端名name=" + name + ",主讲终端ip=" + masterIp);
			throw new IllegalArgumentException();
		} else {
			Object o = deviceHttpService.searchTerminalStatus(url, name, masterIp);
			if (o != null && !"".equals(o)) {
				JSONObject result = JSONObject.fromObject(o);
				try {
					String code = result.get("code").toString();
					status = Boolean.valueOf(result.get("status") == null ? "false" : result.get("status").toString());
					//返回结果:成功
					if (KEY_SUCCESS.equals(code) && status) {
						return status;
					} else {
						return false;
					}
				} catch (Exception e) {
					status = false;
					logger.error("json解析异常,缺失code,json=" + result, e);
				}
			}
		}
		return status;
	}

	/**
	 * 查询终端互动状态
	 *
	 * @param masterIp 主讲终端ip
	 * @return object
	 * @author caoqian
	 */
	public Object searchQueryInteract(String masterIp) {
		logger.debug("searchQueryInteract:masterIp=" + masterIp);
		String name = "";
		if (masterIp == null || "".equals(masterIp)) {
			logger.debug("查询终端互动状态失败，主讲终端ip=" + masterIp);
			throw new IllegalArgumentException();
		} else {
			Object o = deviceHttpService.searchQueryInteract(masterIp);
			if (o != null && !"".equals(o)) {
				JSONObject result = JSONObject.fromObject(o);
				try {
					String code = result.get("code").toString();
					name = result.get("name").toString();
					//返回结果:成功
					String str = "null";
					if (KEY_SUCCESS.equals(code) && !str.equals(name)) {
						return name;
					} else {
						return "";
					}
				} catch (Exception e) {
					name = "";
					logger.error("json解析异常,缺失code,json=" + result, e);
				}
			}
		}
		return name;
	}

	/**
	 * 南京旭顶获取终端状态：在线、互动、静音
	 *
	 * @param partyID     终端id
	 * @param requestFlag 请求参数，区分请求状态
	 * @return
	 */
	public boolean getPartyStatus(String partyID, String requestFlag) {
		logger.debug(this.getClass().getSimpleName() + ",partyID=" + partyID + ",requestFlag=" + requestFlag);
		boolean resultFlag = false;
		if (partyID == null || "".equals(partyID) || requestFlag == null || "".equals(requestFlag)) {
			throw new IllegalArgumentException();
		} else {
			JSONObject resultMap = deviceHttpService.getTerminalStatusForNjxd(partyID);
			if (resultMap != null && !resultMap.isEmpty()) {
				//终端是否在线
				if (requestFlag.equals(SEARCH_TERMIAL_STATUS)) {
					String connState = String.valueOf(resultMap.get("connState"));
					if (connState != null && KEY_SUCCESS.equals(connState)) {
						resultFlag = true;
					} else {
						resultFlag = false;
					}
				} else if (requestFlag.equals(SEARCH_MUTECLIENT_STATUS)) {
					//终端静音
					String micMuteState = String.valueOf(resultMap.get("micMuteState"));
					if (micMuteState != null && KEY_SUCCESS.equals(micMuteState)) {
						resultFlag = true;
					} else {
						resultFlag = false;
					}
				} else if (requestFlag.equals(SEARCH_QUERY_INTERACT)) {
					//终端互动
					String screenLayoutState = String.valueOf(resultMap.get("screenLayoutState"));
					if (screenLayoutState != null && KEY_SUCCESS.equals(screenLayoutState)) {
						resultFlag = true;
					} else {
						resultFlag = false;
					}
				} else {
					return false;
				}
			} else {
				return resultFlag;
			}
		}
		return resultFlag;
	}

	/**
	 * 南京旭顶静音（外部调用）
	 *
	 * @param confID  会议id
	 * @param partyID 终端编号id
	 * @param isMute  是否静音，"0":静音；"1"：不静音
	 * @return
	 * @author caoqian
	 */
	public boolean isMute(String confID, String partyID, String isMute) {
		logger.debug("isMute:confID=" + confID + ",partyID=" + partyID + ",isMute=" + isMute);
		boolean resultFlag = false;
		if (confID == null || "".equals(confID) || partyID == null || "".equals(partyID) || isMute == null || "".equals(isMute)) {
			throw new IllegalArgumentException();
		} else {
			JSONObject resultMap = deviceHttpService.micMuteForNjxd(confID, partyID, isMute);
			if (resultMap != null && resultMap.size() > 0) {
				String yesMute = String.valueOf(resultMap.get("code"));
				if (yesMute != null && KEY_SUCCESS.equals(yesMute)) {
					resultFlag = true;
				} else {
					resultFlag = false;
				}
			}
		}
		return resultFlag;
	}

	/**
	 * 南京旭顶开启/关闭互动
	 *
	 * @param confID       会议id
	 * @param splitMode    1p是主讲模式，2p1是互动模式
	 * @param screenLayout
	 * @return
	 * @author caoqian
	 */
	public boolean screenlayout(String confID, String splitMode, String screenLayout) {
		logger.debug("screenlayout:confID=" + confID + ",splitMode=" + splitMode + ",screenLayout=" + screenLayout);
		boolean resultFlag = false;
		if (confID == null || "".equals(confID) || splitMode == null || "".equals(splitMode) || screenLayout == null || "".equals(screenLayout)) {
			throw new IllegalArgumentException();
		} else {
			JSONObject resultMap = deviceHttpService.layoutForNjxd(confID, splitMode, screenLayout);
			if (resultMap != null && resultMap.size() > 0) {
				String screenLayoutState = String.valueOf(resultMap.get("screenLayoutState"));
				if (screenLayoutState != null && KEY_SUCCESS.equals(screenLayoutState)) {
					resultFlag = true;
				} else {
					resultFlag = false;
				}
			}
		}
		return resultFlag;
	}

	/**
	 * 南京旭顶获取正在互动的终端
	 *
	 * @param confID 会议id
	 * @return 终端id编码
	 * @author caoqian
	 */
	public String getInteractParty(String confID) {
		logger.debug("getInteractParty:confID=" + confID);
		String id = "";
		if (confID == null || "".equals(confID)) {
			throw new IllegalArgumentException();
		} else {
			JSONObject resultMap = deviceHttpService.getInteractPartyForNjxd(confID);
			if (resultMap != null && resultMap.size() > 0) {
				String str = "null";
				if (!str.equals(resultMap.get("id"))) {
					id = String.valueOf(resultMap.get("id"));
				}
			}
		}
		return id;
	}

	/**
	 * 验证平台是否接通
	 *
	 * @return boolean
	 * @author caoqian
	 */
	public boolean isConnection() {
		return true;
	}

	/**
	 * 获取设备服务返回值
	 *
	 * @param obj
	 * @param flag
	 * @return
	 */
	private boolean getCode(Object obj, boolean flag) {
		if (obj != null && !"".equals(obj)) {
			JSONObject result = JSONObject.fromObject(obj);
			try {
				String code = result.get("code").toString();
				if (KEY_SUCCESS.equals(code)) {
					flag = true;
				} else {
					flag = false;
				}
			} catch (Exception e) {
				flag = false;
				logger.error("json解析异常,缺失code,json=" + result, e);
			}
		} else {
			flag = false;
		}
		return flag;
	}

	/**
	 * 根据时间获取当天所有课程列表信息
	 *
	 * @param date     当前日期，格式：2018-01-01
	 * @param masterIp 主讲教室终端ip
	 * @param id       终端编码，如'1234010'
	 * @return list
	 * @author caoqian
	 */
	public List<Map<String, String>> getCourseInfoToday(String date, String masterIp, String id) {
		List<Map<String, String>> courseList = new ArrayList<>();
		Pattern p = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))" +
				"[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|" +
				"(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))" +
				"[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))" +
				"[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))?$");
		if (date != null && !"".equals(date) && p.matcher(date).matches()) {
			//验证是否是日期格式
			String startTime = date + " 00:00:00";
			String endTime = date + " 23:59:59";
			courseList = activityService.getCourseTodayListByTimeAndMasterIp(startTime, endTime, masterIp, id);
		} else {
			logger.debug("当前日期date=" + date + ",主讲教室终端masterIp=" + masterIp + ",获取当天所有课程列表失败，参数错误，应为日期格式。");
			throw new IllegalArgumentException();
		}
		return courseList;
	}

	/**
	 * 获取当前时间主讲课程
	 *
	 * @param time     当前时间，格式:"2018-01-01 10:00:00"
	 * @param masterIp 主讲教室终端Ip
	 * @param id       终端编码，如'1234010'
	 * @return list
	 * @author caoqian
	 */
	public List<Map<String, String>> getLectCourse(String time, String masterIp, String id) {
		List<Map<String, String>> lectCourseList;
		if (time != null && !"".equals(time)) {
			lectCourseList = activityService.getLectCourseListByTimeAndMasterIp(time, masterIp, id);
		} else {
			logger.debug("当前时间time=" + time + "，主讲教室终端masterIp=" + masterIp + ",获取当前时间主讲课程失败，参数异常。");
			throw new IllegalArgumentException();
		}
		return lectCourseList;
	}

	/**
	 * 根据活动id获取所有教室信息，包括主讲教室、接收教室
	 *
	 * @param id 活动id
	 * @return list
	 * @author caoqian
	 */
	public List<Map<String, String>> getRoomListById(String id) {
		List<Map<String, String>> resultList = new ArrayList<>();
		if (id != null && !"".equals(id)) {
			try {
				Activity activity = activityDao.selectActivityById(Integer.parseInt(id));
				if (activity != null) {
					List<Map<String, String>> roomList = activityDao.getRoomListByUuid(activity.getUuid());
					if (roomList != null && !roomList.isEmpty()) {
						int i = 0;
						for (Map<String, String> room : roomList) {
							Map<String, String> resultMap = new HashMap<>();
							Map<String, String> host = areaHttpService.getHostByRoomId(String.valueOf(room.get("roomId")));
							if (host != null && !host.isEmpty()) {
								resultMap.putAll(room);
								resultMap.put("hostId", host.get("host_id"));
								resultMap.put("hostIp", host.get("host_ipaddr"));
								if (i == 0) {
									//主讲教室标记(master)
									resultMap.put("flag", "master");
									resultMap.put("acceptNum", String.valueOf(roomList.size() - 1));
								} else {
									//接收教室标记(accept)
									resultMap.put("flag", "accept");
								}
								resultMap.put("roomId", host.get("mcu_code"));
								resultList.add(resultMap);
							} else {
								continue;
							}
							i++;
						}
					}
				}
			} catch (Exception e) {
				logger.error("根据获取id获取所有教室信息异常，活动id=" + id, e);
			}
		} else {
			logger.debug("获取id为空，获取教室信息失败，参数异常。");
			throw new IllegalArgumentException();
		}
		return resultList;
	}

	/**
	 * 插入大批量活动数据
	 *
	 * @return
	 */
	public boolean insertAcTest() {
		for (int i = 0; i < MAX_NUM; i++) {

			i = activityService.insertTest(i);
		}
		return false;
	}

	/**
	 * 根据用户id获得用户角色信息
	 *
	 * @param userId 用户的id
	 * @return list 一个用户可有多个角色
	 */
	public List<Map<String, String>> getUserRole(String userId) {
		List<Map<String, String>> list = new ArrayList<>();
		if (userId != null && !"".equals(userId)) {
			try {
				list = userHttpService.getUserRole(userId);
				return list;
			} catch (Exception e) {
				logger.error("根据用户id获取角色信息异常，用户id=" + userId, e);
			}
		} else {
			logger.debug("获取id为空，用户角色信息失败，参数异常。");
			throw new IllegalArgumentException();
		}
		return list;
	}

	/**
	 * 根据用户id获取地点权限
	 *
	 * @param userId 用户id
	 * @return list          用户地点权限
	 */
	public Map<String, Object> getAreaByUserId(String userId) {
		Map<String, Object> map = new HashMap<>();
		if (userId != null && !"".equals(userId)) {
			try {
				map = userHttpService.getAreaByUserId(userId);
				return map;
			} catch (Exception e) {
				logger.error("根据用户id获取权限下地点异常，用户id=" + userId, e);
			}
		} else {
			logger.debug("获取id为空，根据用户id获取权限下地点失败，参数异常。");
			throw new IllegalArgumentException();
		}
		return map;
	}

	/**
	 * 根据学校id，分页获取教室列表（支持教室名称）
	 *
	 * @param schoolId    学校id
	 * @param name        教室名称 不是必须
	 * @param roomType    主讲教室传“1”，所有教室传“0”
	 * @param currentPage 当前页数
	 * @param pageSize    每页数量
	 * @return 教室列表
	 */
	public Map<String, Object> getRoomBySchoolId(String schoolId, String name, String roomType, int currentPage, int pageSize, String roomIds) {
		Map<String, Object> map = new HashMap<>();
		if (schoolId != null && !"".equals(schoolId) && currentPage > 0 && pageSize > 0) {
			try {
				roomIds = WeekDateUtil.checkSts(roomIds);
				map = userHttpService.getRoomBySchoolId(schoolId, name, roomType, currentPage, pageSize, roomIds);
				return map;
			} catch (Exception e) {
				logger.error("根据学校id，分页获取教室列表异常，schoolId=" + schoolId, e);
			}
		} else {
			logger.debug("根据学校id，分页获取教室列表失败，参数异常。");
			throw new IllegalArgumentException();
		}
		return map;
	}

	/**
	 * 根据学校id分页获取教师信息（支持教师名称）
	 *
	 * @param schoolId    学校id
	 * @param name        教师名称 不是必须
	 * @param currentPage 当前页数
	 * @param pageSize    每页大小
	 * @return 教师列表
	 */
	public Map<String, Object> getTeacherBySchoolId(String schoolId, String name, int currentPage, int pageSize, String teacherIds) {
		Map<String, Object> map = new HashMap<>();
		if (schoolId != null && !"".equals(schoolId) && currentPage > 0 && pageSize > 0) {
			try {
				teacherIds = WeekDateUtil.checkSts(teacherIds);
				map = userHttpService.getTeacherBySchoolId(schoolId, name, currentPage, pageSize, teacherIds);
				if (map != null && map.get("items") != null) {
					List<Map<String, Object>> items = JSONArray.fromObject(map.get("items"));
					activityService.checkSub(items);
					map.put("items", items);
				}
				return map;
			} catch (Exception e) {
				logger.error("根据学校id，分页获取教师列表异常，schoolId=" + schoolId, e);
			}
		} else {
			logger.debug("根据学校id，分页获取教师列表失败，参数异常。");
			throw new IllegalArgumentException();
		}
		return map;
	}

	/**
	 * 根据省id获取市列表
	 *
	 * @param provinceId 省id
	 * @return 市列表
	 */
	public List<Map<String, String>> getCityByProvince(int provinceId) {
		List<Map<String, String>> list = new ArrayList<>();
		if (provinceId > 0) {
			try {
				list = userHttpService.getCityByProvince(provinceId);
				return list;
			} catch (Exception e) {
				logger.error("根据省id获取市列表异常，provinceId=" + provinceId, e);
			}
		} else {
			logger.debug("根据省id获取市列表失败，参数异常。");
			throw new IllegalArgumentException();
		}
		return list;
	}

	/**
	 * 根据城市id获取区县列表
	 *
	 * @param cityId 城市id
	 * @return 区县列表
	 */
	public List<Map<String, String>> getCountyByCity(int cityId) {
		List<Map<String, String>> list = new ArrayList<>();
		if (cityId > 0) {
			try {
				list = userHttpService.getCountyByCity(cityId);
				return list;
			} catch (Exception e) {
				logger.error("根据城市id获取区县列表异常，cityId=" + cityId, e);
			}
		} else {
			logger.debug("根据城市id获取区县列表失败，参数异常。");
			throw new IllegalArgumentException();
		}
		return list;
	}

	/**
	 * 根据区县id获取学校列表
	 *
	 * @param countyId 区县id
	 * @return 学校列表
	 */
	public List<Map<String, String>> getSchoolByCounty(int countyId) {
		List<Map<String, String>> list = new ArrayList<>();
		if (countyId > 0) {
			try {
				list = userHttpService.getSchoolByCounty(countyId);
				return list;
			} catch (Exception e) {
				logger.error("根据区县id获取学校列表异常，county=" + countyId, e);
			}
		} else {
			logger.debug("根据区县id获取学校列表失败，参数异常。");
			throw new IllegalArgumentException();
		}
		return list;
	}

	/**
	 * 获取MCU列表
	 *
	 * @return
	 */
	public JSONArray getMCUList() {
		JSONArray resultArray = new JSONArray();
		JSONObject mcuJson = deviceHttpService.getMCUList();
		if (mcuJson != null && !mcuJson.isEmpty()) {
			JSONObject resultJson = new JSONObject();
			resultJson.put("mcuId", mcuJson.get("host_id") == null ? "" : mcuJson.get("host_id"));
			resultJson.put("mcuIp", mcuJson.get("host_ipaddr") == null ? "" : mcuJson.get("host_ipaddr"));
			resultJson.put("mcuName", mcuJson.get("host_name") == null ? "" : mcuJson.get("host_name"));
			resultJson.put("mcuType", ConstantWord.HHT_MCU.toUpperCase());
			resultArray.add(resultJson);
		}
		return resultArray;
	}

	/**
	 * 用户登录，返回用户角色及所在组织机构信息
	 *
	 * @param loginName 登录名
	 * @param userPwd   密码
	 * @return object     用户信息
	 * @author caoqian
	 */
	public Object login(String loginName, String userPwd) {
		return userCheckService.userInfo(loginName, userPwd);
	}

	/**
	 * 查询根目录组织机构及所属行政区域信息
	 *
	 * @author caoqian
	 */
	public Map<String, String> getCmapusRoot() throws Exception {
		return adHttpService.getCampusRoot();
	}


	//-------------------------------add for 1.0 南京旭顶-------------begin---------------

	/**
	 * 获取服务器时间
	 *
	 * @return String
	 * @author caoqian
	 * add for 1.0
	 */
	public String getSysTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HH:mm:ss");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("dateTime", sdf.format(new Date()));
		return getResultJson("getSysTime", jsonObject).toString();
	}

	/**
	 * 根据主讲终端获取课表
	 *
	 * @return add for 1.0
	 */
	public Object getConferenceList(String ip) {
		JSONObject jsonObject = new JSONObject();
		if (ip == null || "".equals(ip)) {
			throw new IllegalArgumentException();
		} else {
			Date date = new Date();
			JSONObject json = activityService.getActivitiesByDateByIp(sdfTime.format(date), ip, null, false);
			jsonObject = getResultJson("getConferenceList", json);
		}
		return jsonObject;
	}

	/**
	 * 南京旭顶静音，对接1.0
	 *
	 * @param confName
	 * @param isMute
	 * @return add for 1.0
	 */
	public Object micMute(String confName, String partyIP, String isMute, String masterIp) {
		JSONObject result = new JSONObject();
		if (confName == null || "".equals(confName) || isMute == null || "".equals(isMute) || masterIp == null || "".equals(masterIp)) {
			throw new IllegalArgumentException();
		} else {
			JSONObject resultMap = deviceHttpService.micMuteForNjxd(getConfID(confName, masterIp),
					deviceHttpService.getHostCodeByIp(partyIP), isMute);
			result = getResultJson("micMute", resultMap);
		}
		return result;
	}

	/**
	 * 呼叫/断开终端
	 *
	 * @param confName
	 * @param partyName
	 * @param partyIP
	 * @param conn      0:呼叫 1:挂断
	 * @return add for 1.0
	 */
	public Object connParty(String confName, String partyName, String partyIP, String conn, String masterIp) {
		JSONObject result = new JSONObject();
		if (confName == null || "".equals(confName) || partyIP == null || "".equals(partyIP) ||
				conn == null || "".equals(conn) || masterIp == null || "".equals(masterIp)) {
			throw new IllegalArgumentException();
		} else {
			String connFlag = "";
			if (KEY_SUCCESS.equals(conn)) {
				connFlag = "true";
			} else {
				connFlag = "false";
			}
			JSONObject resultMap = deviceHttpService.connTerminalForNjxd(getConfID(confName, masterIp), confName,
					deviceHttpService.getHostCodeByIp(partyIP), partyIP, partyName, connFlag);
			//南京旭顶设备挂断后会主动连接，devicegrid会返回code=-1，为对接1.0，数据手动修改
			result = getResultJson("connParty", resultMap);
		}
		return result;
	}

	/**
	 * 开始互动
	 *
	 * @param confName
	 * @param parties
	 * @return add for 1.0
	 */
	public Object startConversation(String confName, String parties, String masterIp) {
		return operationConversation(confName, parties, "startConversation", masterIp);
	}

	/**
	 * 结束互动
	 *
	 * @param confName
	 * @param parties
	 * @return add for 1.0
	 */
	public Object stopConversation(String confName, String parties, String masterIp) {
		return operationConversation(confName, parties, "stopConversation", masterIp);
	}

	/**
	 * 提取开启/关闭会议代码
	 *
	 * @param confName
	 * @param parties
	 * @param actionName
	 * @return
	 */
	private JSONObject operationConversation(String confName, String parties, String actionName, String masterIp) {
		JSONObject result = new JSONObject();
		if (confName == null || "".equals(confName) || parties == null || "".equals(parties) || masterIp == null || "".equals(masterIp)) {
			throw new IllegalArgumentException();
		} else {
			String type = "";
			switch (actionName) {
				case "startConversation":
					type = ConstantWord.SCREEN_LAYOUT_2P1;
					break;
				case "stopConversation":
					type = ConstantWord.SCREEN_LAYOUT_1P;
					break;
				default:
					type = "";
					break;
			}
			JSONObject resultMap = deviceHttpService.layoutForNjxd(getConfID(confName, masterIp), type,
					activityService.getPartyArray(parties).toString());
			result = getResultJson(actionName, resultMap);
		}
		return result;
	}

	/**
	 * 获取终端状态
	 *
	 * @param partyIP
	 * @return add for 1.0
	 */
	public Object getPartyStatusForHK2000(String partyIP) {
		JSONObject result = new JSONObject();
		if (partyIP == null || "".equals(partyIP)) {
			throw new IllegalArgumentException();
		} else {
			JSONObject resultMap = deviceHttpService.getTerminalStatusForNjxd(deviceHttpService.getHostCodeByIp(partyIP));
			if (resultMap != null && !resultMap.isEmpty()) {
				String str = "null";
				if (resultMap.get("partyIP") == null || "".equals(resultMap.get("partyIP"))) {
					resultMap.put("partyIP", partyIP);
				}
				if (resultMap.get("micMuteState") == null || str.equals(resultMap.get("micMuteState").toString())) {
					resultMap.put("micMuteState", "1");
				}
			}
			result = getResultJson("getPartyStatus", resultMap);
		}
		return result;
	}

	/**
	 * 获取终端截图
	 *
	 * @param partyIP 终端ip
	 * @return add for 1.0
	 */
	public Object getPartyPic(String partyIP) {
		JSONObject result = new JSONObject();
		if (partyIP == null || "".equals(partyIP)) {
			throw new IllegalArgumentException();
		} else {
			JSONObject picJson = new JSONObject();
			picJson.put("picMsg", teachingSupervisionService.getScreenPicForHk2000(partyIP));
			result = getResultJson("getPartyPic", picJson);
		}
		return result;
	}

	/**
	 * 封装结果 add for 1.0
	 *
	 * @param actionName
	 * @param json
	 * @return
	 */
	private JSONObject getResultJson(String actionName, JSONObject json) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("action", actionName);
		if (json != null && !json.isEmpty()) {
			jsonObject.put("data", JSONArray.fromObject(json));
		} else {
			jsonObject.put("data", "");
		}
		return jsonObject;
	}

	/**
	 * 获取会议信息，名称+时间,confName="化学10=00~10=45" add for 1.0
	 *
	 * @param confName
	 * @return
	 */
	private String getConfID(String confName, String masterIp) {
		String name = "", startTime = "", endTime = "";
		if (confName != null && !"".equals(confName)) {
			String[] timeArr;
			try {
				name = confName.substring(0, (confName.length() - 11));
				String timeStr = confName.substring(confName.length() - 11, confName.length());
				timeArr = timeStr.split("~");
				startTime = timeArr[0].replace("=", ":");
				endTime = timeArr[1].replace("=", ":");
			} catch (Exception e) {
				name = "";
				startTime = "";
				endTime = "";
			}
		}
		return activityService.getConfID(name, startTime, endTime, masterIp);
	}

	//-------------------------------add for 1.0 南京旭顶-------------end---------------

	/**
	 * 提供帮助文档下载路径
	 *
	 * @return filePath
	 * @author caoqian
	 */
	public String getHelpPdf() {
		String filePath = HttpRequestUtil.getServerUrlPath() + "config/help.pdf";
		return filePath;
	}

	/**
	 * 根据用户id查询学段信息
	 *
	 * @param userId 用户id
	 * @return
	 */
	public Map<String, String> getStagesByUserId(String userId) {
		return userHttpService.getStagesByUserId(userId);
	}

	/**
	 * 获取用户所在组织机构
	 *
	 * @param userId 用户id
	 * @return
	 */
	public Object getCampusInfoByUser(String userId) {
		return userCheckService.getCampusInfo(userId);
	}

	/**
	 * 添加终端FOR 捷视飞通
	 *
	 * @param url     muc地址
	 * @param devName 终端名称
	 * @param devType 终端类型 默认为0
	 * @param devIp   终端IP
	 * @param e164    终端id(300B终端注册号)
	 * @throws Exception
	 */
	public Object addTerminalByJsft(String url, String devName, String devType, String devIp, String e164) {
		String username = ConfigUtil.getConfig("jsft_username").toString();
		String password = ConfigUtil.getConfig("jsft_password").toString();
		url = deviceToJsftHttpService.checkHTTP(url);
		JSONObject jsonObject = deviceToJsftHttpService.checkMcuLogin(url, username, password);
		if ("0".equals(jsonObject.get("code"))) {
			//alias参数值等于e164的值
			Object object = deviceToJsftHttpService.addHostToMcu(url, devName, devIp, devType, e164,
					SUPERDMDID, e164, AUTOCAP, AUDIOADAPTOR, DELETABLEFLAG);
			System.out.println(object);
			return object;
		} else {
			return null;
		}
	}

	/**
	 * 删除终端FOR 捷视飞通
	 *
	 * @param url MUCIP地址
	 * @param id  终端ID(MCU设备返回)
	 * @throws Exception
	 */
	public Object delTerminalByJsft(String url, String id) {
		String username = ConfigUtil.getConfig("jsft_username").toString();
		String password = ConfigUtil.getConfig("jsft_password").toString();
		url = deviceToJsftHttpService.checkHTTP(url);
		JSONObject jsonObject = new JSONObject();
		jsonObject = deviceToJsftHttpService.checkMcuLogin(url, username, password);
		if ("0".equals(jsonObject.get("code"))) {
			return deviceToJsftHttpService.delHostToMcu(url, id);
		} else {
			return null;
		}
	}
	//---------------------------for 捷视飞通-----------------------------------

	/**
	 * 呼叫终端
	 *
	 * @param url    mcu地址
	 * @param confID 会议id
	 * @param host   终端ip或者E164号
	 * @param type   终端类型
	 * @return
	 * @author gujt
	 */
	public Object callHost(String url, String confID, String host, String type) {
		JSONObject result = new JSONObject();
		if (url == null || "".equals(url) || confID == null || "".equals(confID) ||
				host == null || "".equals(host) || type == null || "".equals(type)) {
			throw new IllegalArgumentException();
		} else {
			String username = ConfigUtil.getConfig("jsft_username").toString();
			String password = ConfigUtil.getConfig("jsft_password").toString();
			url = deviceToJsftHttpService.checkHTTP(url);
			JSONObject jsonObject = new JSONObject();
			jsonObject = deviceToJsftHttpService.checkMcuLogin(url, username, password);
			if ("0".equals(jsonObject.get("code"))) {
				JSONObject resultMap = deviceToJsftHttpService.callHost(url, confID, host, type);
				return getResultJson("callHost", resultMap);
			} else {
				return null;
			}
		}
	}

	/**
	 * 开启互动----对话点名
	 *
	 * @param mcuIP    mcu地址
	 * @param confName 会议id
	 * @param parties     终端信息的json数组
	 * @return
	 * @author gujt
	 */
	public JSONObject setDialog(String mcuIP, String confName, String parties) {
		JSONObject result = new JSONObject();
		if (mcuIP == null || "".equals(mcuIP) || confName == null || "".equals(confName) || parties == null || "".equals(parties)) {
			throw new IllegalArgumentException();
		} else {
			String username = ConfigUtil.getConfig("jsft_username").toString();
			String password = ConfigUtil.getConfig("jsft_password").toString();
			mcuIP = deviceToJsftHttpService.checkHTTP(mcuIP);
			JSONObject jsonObject = new JSONObject();
			jsonObject = deviceToJsftHttpService.checkMcuLogin(deviceToJsftHttpService.checkHTTP(mcuIP), username, password);
			if ("0".equals(jsonObject.get("code"))) {
				//处理终端信息的json数组
				String ip="";
				try {
					JSONArray termianlArr = JSONArray.fromObject(activityService.getPartyArray(parties).toString());
					if(termianlArr!=null){
						JSONObject terminal=JSONObject.fromObject(termianlArr.get(0));
						if(terminal!=null && terminal.containsKey("partyIP")){
							ip=terminal.get("partyIP").toString();
						}
					}
				}catch (JSONException e){
					ip="";
				}
				String confID = deviceToJsftHttpService.getConInfo(deviceToJsftHttpService.checkHTTP(mcuIP),confName);
				JSONObject resultMap = deviceToJsftHttpService.setDialog(deviceToJsftHttpService.checkHTTP(mcuIP), confID, ip);
				return getResultJson("setDialog", resultMap);
			} else {
				return null;
			}
		}
	}

	/**
	 * 取消互动-------取消对话点名
	 *
	 * @param mcuIP    mcu地址
	 * @param confName 会议id
	 * @param parties     终端信息的json数组
	 * @return
	 * @author gujt
	 */
	public JSONObject cancelDialog(String mcuIP, String confName, String parties) {
		JSONObject result = new JSONObject();
		if (mcuIP == null || "".equals(mcuIP) || confName == null || "".equals(confName) || parties == null || "".equals(parties)) {
			throw new IllegalArgumentException();
		} else {
			String username = ConfigUtil.getConfig("jsft_username").toString();
			String password = ConfigUtil.getConfig("jsft_password").toString();
			mcuIP = deviceToJsftHttpService.checkHTTP(mcuIP);
			JSONObject jsonObject = new JSONObject();
			jsonObject = deviceToJsftHttpService.checkMcuLogin(deviceToJsftHttpService.checkHTTP(mcuIP), username, password);
			if ("0".equals(jsonObject.get("code"))) {
				//处理终端信息的json数组
				String ip="";
				try {
					JSONArray termianlArr = JSONArray.fromObject(activityService.getPartyArray(parties).toString());
					if(termianlArr!=null){
						JSONObject terminal=JSONObject.fromObject(termianlArr.get(0));
						if(terminal!=null && terminal.containsKey("partyIP")){
							ip=terminal.get("partyIP").toString();
						}
					}
				}catch (JSONException e){
					ip="";
				}
				String confID = deviceToJsftHttpService.getConInfo(deviceToJsftHttpService.checkHTTP(mcuIP),confName);
				//JSONObject resultMap = deviceToJsftHttpService.cancelDialog(deviceToJsftHttpService.checkHTTP(mcuIP), confID, ip);
				//设置单画面  192.168.18.30/script/confctrl/setsplitescreen.esp?confID=391&spliteMode=0&screenLayout=0,0
				//deviceToJsftHttpService.getSplitMode(mcuIP, confID, "0", "0,0", null);//自动填充
				JSONObject resultMap = deviceToJsftHttpService.getSplitMode(mcuIP, confID, "0", "1,0", null);//主讲教室
				return getResultJson("cancelDialog", resultMap);
			} else {
				return null;
			}
		}
	}

	/**
	 * 邀请会场
	 *
	 * @param url    mcu地址
	 * @param confID 会议id
	 * @param ip     设备ip
	 * @return
	 * @author gujt
	 */
	public Object callConven(String url, String confID, String ip) {
		JSONObject result = new JSONObject();
		if (url == null || "".equals(url) || confID == null || "".equals(confID) ||
				ip == null || "".equals(ip)) {
			throw new IllegalArgumentException();
		} else {
			String username = ConfigUtil.getConfig("jsft_username").toString();
			String password = ConfigUtil.getConfig("jsft_password").toString();
			url = deviceToJsftHttpService.checkHTTP(url);
			JSONObject jsonObject = new JSONObject();
			jsonObject = deviceToJsftHttpService.checkMcuLogin(url, username, password);
			if ("0".equals(jsonObject.get("code"))) {
				JSONObject resultMap = deviceToJsftHttpService.callConven(url, confID, ip);
				return getResultJson("callConven", resultMap);
			} else {
				return null;
			}
		}
	}
	/**
	 * 挂断会场
	 *
	 * @param url    mcu地址
	 * @param confID 会议id
	 * @param ip     设备ip
	 * @return
	 * @author gujt
	 */
	public Object dropConven(String url, String confID, String ip) {
		JSONObject result = new JSONObject();
		if (url == null || "".equals(url) || confID == null || "".equals(confID) ||
				ip == null || "".equals(ip)) {
			throw new IllegalArgumentException();
		} else {
			String username = ConfigUtil.getConfig("jsft_username").toString();
			String password = ConfigUtil.getConfig("jsft_password").toString();
			url = deviceToJsftHttpService.checkHTTP(url);
			JSONObject jsonObject = new JSONObject();
			jsonObject = deviceToJsftHttpService.checkMcuLogin(url, username, password);
			if ("0".equals(jsonObject.get("code"))) {
				JSONObject resultMap = deviceToJsftHttpService.dropConven(url, confID, ip);
				return getResultJson("dropConven", resultMap);
			} else {
				return null;
			}
		}
	}

	/**
	 * 呼叫/挂断终端
	 * @param mcuIP mcu的ip
	 * @param confName 会议名称
	 * @param partyName 终端名称
	 * @param partyIP 终端ip
	 * @param conn  0：呼叫终端，1：挂断终端
	 * @return
	 */
	public JSONObject Conven(String mcuIP,String confName,String partyName,String partyIP,String conn){
		JSONObject result = new JSONObject();
		if (mcuIP == null || "".equals(mcuIP) || confName == null || "".equals(confName) ||
				partyIP == null || "".equals(partyIP) ||
				conn == null || "".equals(conn)) {
			throw new IllegalArgumentException();
		} else {
			mcuIP = deviceToJsftHttpService.checkHTTP(mcuIP);
			String username = ConfigUtil.getConfig("jsft_username").toString();
			String password = ConfigUtil.getConfig("jsft_password").toString();
			JSONObject jsonObject = new JSONObject();
			jsonObject = deviceToJsftHttpService.checkMcuLogin(deviceToJsftHttpService.checkHTTP(mcuIP), username, password);
			if ("0".equals(jsonObject.get("code"))) {
				String confID = deviceToJsftHttpService.getConInfo(deviceToJsftHttpService.checkHTTP(mcuIP),confName);
				if (conn.equals("0")) {
					JSONObject resultMap = deviceToJsftHttpService.callConven(mcuIP, confID, partyIP);
					deviceToJsftHttpService.modifyTemp(deviceToJsftHttpService.checkHTTP(mcuIP),partyIP,conn);
					return getResultJson("Conven", resultMap);
				}else {
					JSONObject resultMap = deviceToJsftHttpService.dropConven(deviceToJsftHttpService.checkHTTP(mcuIP), confID, partyIP);
					deviceToJsftHttpService.modifyTemp(deviceToJsftHttpService.checkHTTP(mcuIP),partyIP,conn);
					return getResultJson("Conven", resultMap);
				}
			} else {
				return null;
			}
		}
	}
	/**
	 * 全部静音
	 *
	 * @param url    mcu地址
	 * @param confID 会议id
	 * @return
	 * @author gujt
	 */
	public Object micMuteForMcu(String url, String confID) {
		JSONObject result = new JSONObject();
		if (url == null || "".equals(url) || confID == null || "".equals(confID)) {
			throw new IllegalArgumentException();
		} else {
			String username = ConfigUtil.getConfig("jsft_username").toString();
			String password = ConfigUtil.getConfig("jsft_password").toString();
			url = deviceToJsftHttpService.checkHTTP(url);
			JSONObject jsonObject = new JSONObject();
			jsonObject = deviceToJsftHttpService.checkMcuLogin(url, username, password);
			if ("0".equals(jsonObject.get("code"))) {
				JSONObject resultMap = deviceToJsftHttpService.micMuteForMcu(url, confID);
				return getResultJson("micMuteForMcu", resultMap);
			} else {
				return null;
			}
		}
	}

	/**
	 * 会场静音/取消静音
	 *
	 * @param masterIp    mcu地址
	 * @param confName 会议id
	 * @param partyIP     设备ip
	 * @param isMute     0:静音,1:会场静音
	 * @return
	 * @author gujt
	 */
	public Object micMuteForEP(String masterIp, String confName, String partyIP,String isMute) {
		JSONObject result = new JSONObject();
		if (masterIp == null || "".equals(masterIp) || confName == null || "".equals(confName)) {
			throw new IllegalArgumentException();
		} else {
			String username = ConfigUtil.getConfig("jsft_username").toString();
			String password = ConfigUtil.getConfig("jsft_password").toString();
			masterIp = deviceToJsftHttpService.checkHTTP(masterIp);
			JSONObject jsonObject = new JSONObject();
			jsonObject = deviceToJsftHttpService.checkMcuLogin(deviceToJsftHttpService.checkHTTP(masterIp), username, password);
			if ("0".equals(jsonObject.get("code"))) {
				String confID = deviceToJsftHttpService.getConInfo(deviceToJsftHttpService.checkHTTP(masterIp), confName);
				if (isMute.equals("0")) {
					JSONObject resultMap = deviceToJsftHttpService.micMuteForEP(deviceToJsftHttpService.checkHTTP(masterIp), confID, partyIP);
					return getResultJson("micMuteForEP", resultMap);
				}else {
					JSONObject resultMap = deviceToJsftHttpService.delmicMuteForEP(deviceToJsftHttpService.checkHTTP(masterIp), confID, partyIP);
					return getResultJson("micMuteForEP",resultMap);
				}
			} else {
				return null;
			}
		}
	}

	/**
	 * 取消全部静音
	 *
	 * @param url    mcu地址
	 * @param confID 会议id
	 * @return
	 * @author gujt
	 */
	public Object delmicMuteForMcu(String url, String confID) {
		JSONObject result = new JSONObject();
		if (url == null || "".equals(url) || confID == null || "".equals(confID)) {
			throw new IllegalArgumentException();
		} else {
			String username = ConfigUtil.getConfig("jsft_username").toString();
			String password = ConfigUtil.getConfig("jsft_password").toString();
			url = deviceToJsftHttpService.checkHTTP(url);
			JSONObject jsonObject = new JSONObject();
			jsonObject = deviceToJsftHttpService.checkMcuLogin(url, username, password);
			if ("0".equals(jsonObject.get("code"))) {
				JSONObject resultMap = deviceToJsftHttpService.delmicMuteForMcu(url, confID);
				return getResultJson("delmicMuteForMcu", resultMap);
			} else {
				return null;
			}
		}
	}

	/**
	 * 取消会场静音
	 *
	 * @param masterIp    mcu地址
	 * @param confName 会议id
	 * @param partyIP     设备ip
	 * @return
	 * @author gujt
	 */
	public Object delmicMuteForEP(String masterIp, String confName, String partyIP,String isMute) {
		JSONObject result = new JSONObject();
		if (masterIp == null || "".equals(masterIp) || confName == null || "".equals(confName)) {
			throw new IllegalArgumentException();
		} else {
			String username = ConfigUtil.getConfig("jsft_username").toString();
			String password = ConfigUtil.getConfig("jsft_password").toString();
			masterIp = deviceToJsftHttpService.checkHTTP(masterIp);
			JSONObject jsonObject = new JSONObject();
			jsonObject = deviceToJsftHttpService.checkMcuLogin(deviceToJsftHttpService.checkHTTP(masterIp), username, password);
			if ("0".equals(jsonObject.get("code"))) {
				String confID = deviceToJsftHttpService.getConInfo(deviceToJsftHttpService.checkHTTP(masterIp),confName);
				JSONObject resultMap = deviceToJsftHttpService.delmicMuteForEP(deviceToJsftHttpService.checkHTTP(masterIp), confID, partyIP);
				return getResultJson("delmicMuteForEP", resultMap);
			} else {
				return null;
			}
		}
	}

	/**
	 * 获取终端状态
	 *
	 * @param mcuIP mcu地址
	 * @param partyIP 终端ip
	 * @return
	 * @author gujt
	 */
	public JSONObject getHostStat(String mcuIP,String partyIP) {
		JSONObject result = new JSONObject();
		if (mcuIP == null || "".equals(mcuIP)) {
			throw new IllegalArgumentException();
		} else {
			String username = ConfigUtil.getConfig("jsft_username").toString();
			String password = ConfigUtil.getConfig("jsft_password").toString();
			mcuIP = deviceToJsftHttpService.checkHTTP(mcuIP);
			JSONObject jsonObject = new JSONObject();
			jsonObject = deviceToJsftHttpService.checkMcuLogin(deviceToJsftHttpService.checkHTTP(mcuIP), username, password);
			if ("0".equals(jsonObject.get("code"))) {
				JSONObject resultMap = deviceToJsftHttpService.getHostList(deviceToJsftHttpService.checkHTTP(mcuIP));
				Map<String, JSONObject> statMap = new HashMap<>();
				if (resultMap != null && !resultMap.isEmpty()) {
					for (int i = 1; i > 0; i++) {
						if (resultMap.containsKey("num" + i)) {
							JSONObject jsonObject3 = resultMap.getJSONObject("num" + i);
							JSONObject js = new JSONObject();
							js.put("id",(String) jsonObject3.get("id"));
							js.put("name",(String) jsonObject3.get("devname"));
							js.put("stat",(String) jsonObject3.get("stat"));
							js.put("phone",(String) jsonObject3.get("phone"));
							//statMap.put((String) jsonObject3.get("ip"), (String) jsonObject3.get("stat"));
							statMap.put((String) jsonObject3.get("ip"),js);
						} else {
							break;
						}
					}
					//终端是否在线
					String connState = (String) statMap.get(partyIP).get("phone");
					result.put("code",resultMap.get("code"));
					result.put("confID","");
					result.put("confName","");
					if (connState.equals("1")){
						result.put("connState","0");
					}else {
						result.put("connState","1");
					}
//					result.put("connState",connState);
					result.put("micMuteState","");
					result.put("partyID",(String) statMap.get(partyIP).get("id"));
					result.put("partyIP",partyIP);
					result.put("partyName",(String) statMap.get(partyIP).get("name"));
					result.put("partyType","0");
				}
				return getResultJson("getHostStat", result);
			} else {
				return null;
			}
		}
	}

	/**
	 * 删除会议
	 *
	 * @param url mcu地址
	 * @param id  会议id
	 * @return
	 * @author gujt
	 */
	public JSONObject delConf(String url, String id) {
		JSONObject result = new JSONObject();
		if (url == null || "".equals(url)) {
			throw new IllegalArgumentException();
		} else {
			String username = ConfigUtil.getConfig("jsft_username").toString();
			String password = ConfigUtil.getConfig("jsft_password").toString();
			url = deviceToJsftHttpService.checkHTTP(url);
			JSONObject jsonObject = new JSONObject();
			jsonObject = deviceToJsftHttpService.checkMcuLogin(url, username, password);
			if ("0".equals(jsonObject.get("code"))) {
				JSONObject resultMap = deviceToJsftHttpService.delConf(url, id);
				return getResultJson("delConf", resultMap);
			} else {
				return null;
			}
		}
	}

	/**
	 * 预约
	 *
	 * @param url           mcu地址
	 * @param tmpID         会议模板id
	 * @param name          会议名称
	 * @param number        会议E164号
	 * @param type          会议类型 0：立即会议，1：预约会议，2：永久会议
	 * @param begin         开始时间---2016-04-27T09:21:39+08:00 格式：”日期”+”T”+时间+”时区”
	 * @param end           结束时间---2016-04-27T09:21:39+08:00（=10分钟）
	 * @param chairman      终端id---设置为主会场
	 * @param conventioners 与会者列表
	 * @return
	 * @author gujt
	 */
	public JSONObject addConfen(String url, String tmpID, String name, String number, String type, String begin, String end, String chairman, String conventioners) {
		JSONObject result = new JSONObject();
		if (url == null || "".equals(url)) {
			throw new IllegalArgumentException();
		} else {
			String username = ConfigUtil.getConfig("jsft_username").toString();
			String password = ConfigUtil.getConfig("jsft_password").toString();
			url = deviceToJsftHttpService.checkHTTP(url);
			JSONObject jsonObject = new JSONObject();
			jsonObject = deviceToJsftHttpService.checkMcuLogin(url, username, password);
			if ("0".equals(jsonObject.get("code"))) {
				JSONObject resultMap = deviceToJsftHttpService.addConf(url, tmpID, name, number, type, begin, end, chairman, conventioners);
				return getResultJson("addConfen", resultMap);
			} else {
				return null;
			}
		}
	}
	/**===20-18=========================================================================
	 * ===10-16=========================================================================
	 */

	/**
	 * 捷视飞通获取终端状态：是否在线
	 *
	 * @param mcuIP     mcu地址
	 * @param partyID 终端id
	 * @return
	 * @author gujt
	 */
	public boolean getParStatus(String mcuIP, String partyID,String requestFlag) {
		logger.debug("mcuIP="+mcuIP + ",partyID=" + partyID);
		boolean resultFlag = false;
		if (partyID == null || "".equals(partyID) || mcuIP == null || "".equals(mcuIP)) {
			throw new IllegalArgumentException();
		} else {
			mcuIP = deviceToJsftHttpService.checkHTTP(mcuIP);
			JSONObject jsonObject = new JSONObject();
			jsonObject = checkMcuLogin(mcuIP);
			if ("0".equals(jsonObject.get("code"))) {
				JSONObject resultMap = deviceToJsftHttpService.getHostList(deviceToJsftHttpService.checkHTTP(mcuIP));
				Map<String, String> statMap = new HashMap<>();
				if (resultMap != null && !resultMap.isEmpty()) {
					for (int i = 1; i > 0; i++) {
						if (resultMap.containsKey("num" + i)) {
							JSONObject jsonObject3 = resultMap.getJSONObject("num" + i);
							//statMap.put((String) jsonObject3.get("id"), (String) jsonObject3.get("stat"));
							statMap.put((String) jsonObject3.get("id"), (String) jsonObject3.get("phone"));
						} else {
							break;
						}
					}
					//终端是否在线
					String connState = statMap.get(partyID);
					if (connState != null && !KEY_SUCCESS.equals(connState)) {
						resultFlag = true;
					} else {
						resultFlag = false;
					}
				} else {
					return resultFlag;
				}
			} else {
				return resultFlag;
			}
		}
		return resultFlag;
	}

	/**
	 * 捷视飞通终端静音
	 *
	 * @param mcuIP    mcu地址
	 * @param confID 会议id
	 * @param partyID     终端id
	 * @param isMute     0：静音，1：不静音
	 * @return
	 * @author gujt
	 */
	public boolean isMuteForJSFT(String mcuIP, String confID, String partyID, String isMute) {
		logger.debug("mcuIP=" + mcuIP + ",confID=" + confID + ",partyID=" + partyID + ",isMute=" + isMute);
		boolean resultFlag = false;
		if (mcuIP == null || "".equals(mcuIP) || confID == null || "".equals(confID) || partyID == null || "".equals(partyID) || isMute == null || "".equals(isMute)) {
			throw new IllegalArgumentException();
		} else {
			mcuIP = deviceToJsftHttpService.checkHTTP(mcuIP);
			JSONObject jsonObject = new JSONObject();
			jsonObject = checkMcuLogin(mcuIP);
			if ("0".equals(jsonObject.get("code"))) {
				String ip = getHostIP(mcuIP,partyID);
				if (isMute.equals("0")) {
					JSONObject resultMap = deviceToJsftHttpService.micMuteForEP(deviceToJsftHttpService.checkHTTP(mcuIP), confID, ip);
					resultFlag = returnBoolean(resultMap);
				}else {
					JSONObject resultMap = deviceToJsftHttpService.delmicMuteForEP(deviceToJsftHttpService.checkHTTP(mcuIP), confID, ip);
					resultFlag = returnBoolean(resultMap);
				}
			} else {
				return resultFlag;
			}
		}
		return resultFlag;
	}

	/**
	 * 捷视飞通取消终端静音
	 *
	 * @param url    mcu地址
	 * @param confID 会议id
	 * @param ip     终端ip
	 * @return
	 * @author gujt
	 */
	public boolean delmicMute(String url, String confID, String ip) {
		logger.debug("url=" + url + ",confID=" + confID + ",ip=" + ip);
		boolean resultFlag = false;
		if (url == null || "".equals(url) || confID == null || "".equals(confID) || ip == null || "".equals(ip)) {
			throw new IllegalArgumentException();
		} else {
			url = deviceToJsftHttpService.checkHTTP(url);
			JSONObject jsonObject = new JSONObject();
			jsonObject = checkMcuLogin(url);
			if ("0".equals(jsonObject.get("code"))) {
				JSONObject resultMap = deviceToJsftHttpService.delmicMuteForEP(deviceToJsftHttpService.checkHTTP(url), confID, ip);
				resultFlag = returnBoolean(resultMap);
			} else {
				return resultFlag;
			}
		}
		return resultFlag;
	}

	/**
	 * 捷视飞通开始/关闭互动
	 *
	 * @param mcuIP    mcu地址
	 * @param confID 会议id
	 * @param screenLayout     终端json数组格式信息
	 * @param splitMode  1p是主讲模式，2p1是互动模式
	 * @return
	 * @author gujt
	 */
	public boolean startInteract(String mcuIP, String confID, String screenLayout,String splitMode) {
		logger.debug("mcuIP=" + mcuIP + ",confID=" + confID + ",screenLayout=" + screenLayout + ",splitMode=" + splitMode);
		boolean resultFlag = false;
		if (mcuIP == null || "".equals(mcuIP) || confID == null || "".equals(confID) || screenLayout == null || "".equals(screenLayout) || splitMode == null || "".equals(splitMode)) {
			throw new IllegalArgumentException();
		} else {
			mcuIP = deviceToJsftHttpService.checkHTTP(mcuIP);
			JSONObject jsonObject = new JSONObject();
			jsonObject = checkMcuLogin(mcuIP);
			if ("0".equals(jsonObject.get("code"))) {
				//处理终端信息的json数组
				String ip="";
				try {
					JSONArray termianlArr = JSONArray.fromObject(activityService.getPartyArray(screenLayout).toString());
					if(termianlArr!=null){
						JSONObject terminal=JSONObject.fromObject(termianlArr.get(0));
						if(terminal!=null && terminal.containsKey("partyIP")){
							ip=terminal.get("partyIP").toString();
						}
					}
				}catch (JSONException e){
					ip="";
				}
				if (splitMode.equals("2p1")){
					JSONObject resultMap = deviceToJsftHttpService.setDialog(deviceToJsftHttpService.checkHTTP(mcuIP), confID, ip);
					resultFlag = returnBoolean(resultMap);
				}else {
					JSONObject resultMap = deviceToJsftHttpService.cancelDialog(deviceToJsftHttpService.checkHTTP(mcuIP), confID, ip);
					resultFlag = returnBoolean(resultMap);
				}
			} else {
				return resultFlag;
			}
		}
		return resultFlag;
	}

	/**
	 * 捷视飞通取消互动
	 *
	 * @param url    mcu地址
	 * @param confID 会议id
	 * @param ip     终端ip
	 * @return
	 * @author gujt
	 */
	public boolean endInteract(String url, String confID, String ip) {
		logger.debug("url=" + url + ",confID=" + confID + ",ip=" + ip);
		boolean resultFlag = false;
		if (url == null || "".equals(url) || confID == null || "".equals(confID) || ip == null || "".equals(ip)) {
			throw new IllegalArgumentException();
		} else {
			url = deviceToJsftHttpService.checkHTTP(url);
			JSONObject jsonObject = new JSONObject();
			jsonObject = checkMcuLogin(url);
			if ("0".equals(jsonObject.get("code"))) {
				JSONObject resultMap = deviceToJsftHttpService.cancelDialog(deviceToJsftHttpService.checkHTTP(url), confID, ip);
				resultFlag = returnBoolean(resultMap);
			} else {
				return resultFlag;
			}
		}
		return resultFlag;
	}

	/**
	 * 挂断会场
	 *
	 * @param url    mcu地址
	 * @param confID 会议id
	 * @param ip     设备ip
	 * @return
	 * @author gujt
	 */
	public boolean stopConven(String url,String confID,String ip){
		logger.debug("url=" + url + ",confID=" + confID + ",ip=" + ip);
		boolean resultFlag = false;
		if (url == null || "".equals(url) || confID == null || "".equals(confID) || ip == null || "".equals(ip)) {
			throw new IllegalArgumentException();
		} else {
			url = deviceToJsftHttpService.checkHTTP(url);
			JSONObject jsonObject = new JSONObject();
			jsonObject = checkMcuLogin(url);
			if ("0".equals(jsonObject.get("code"))) {
				JSONObject resultMap = deviceToJsftHttpService.dropConven(deviceToJsftHttpService.checkHTTP(url),confID,ip);
				resultFlag = returnBoolean(resultMap);
			} else {
				return resultFlag;
			}
		}
		return resultFlag;
	}
	/**
	 * 呼叫终端
	 *
	 * @param url    mcu地址
	 * @param confID 会议id
	 * @param ip     设备ip
	 * @return
	 * @author gujt
	 */
	public boolean startConven(String url,String confID,String ip){
		logger.debug("url=" + url + ",confID=" + confID + ",ip=" + ip);
		boolean resultFlag = false;
		if (url == null || "".equals(url) || confID == null || "".equals(confID) || ip == null || "".equals(ip)) {
			throw new IllegalArgumentException();
		} else {
			url = deviceToJsftHttpService.checkHTTP(url);
			JSONObject jsonObject = new JSONObject();
			jsonObject = checkMcuLogin(url);
			if ("0".equals(jsonObject.get("code"))) {
				JSONObject resultMap = deviceToJsftHttpService.callConven(deviceToJsftHttpService.checkHTTP(url),confID,ip);
				resultFlag = returnBoolean(resultMap);
			} else {
				return resultFlag;
			}
		}
		return resultFlag;
	}
	/**
	 * 方法封装：根据设备id获取ip--for JSFT
	 */
	public String getHostIP(String mcuIP,String partyID) {
		JSONObject resultMap = deviceToJsftHttpService.getHostList(deviceToJsftHttpService.checkHTTP(mcuIP));
		Map<String, String> hostMap = new HashMap<>();
		if (resultMap != null && !resultMap.isEmpty()) {
			for (int i = 1; i > 0; i++) {
				if (resultMap.containsKey("num" + i)) {
					JSONObject jsonObject3 = resultMap.getJSONObject("num" + i);
					hostMap.put((String) jsonObject3.get("id"), (String) jsonObject3.get("ip"));
				} else {
					break;
				}
			}
			String ip = hostMap.get(partyID);
			return ip;
		}
		return null;
	}

	/**
	 * 登陆验证方法封装--for JSFT
	 * @param url MCU地址
	 * @return
	 * @author gujt
	 */
	private JSONObject checkMcuLogin(String url){
		JSONObject reValue = new JSONObject();
		if (url != null && !"".equals(url)) {
			String username = ConfigUtil.getConfig("jsft_username").toString();
			String password = ConfigUtil.getConfig("jsft_password").toString();
			if (username != null && !"".equals(username) && password != null && !"".equals(password)){
				reValue = deviceToJsftHttpService.checkMcuLogin(deviceToJsftHttpService.checkHTTP(url), username, password);
			}else {
				reValue.put("code","-1");
			}
		}else {

			reValue.put("code","-1");
		}
		return  reValue;
	}
	/**
	 * 返回boolean值方法封装--for JSFT
	 * @param resultMap  请求捷视飞通MCU的返回结果
	 * @return
	 * @author gujt
	 */
	private boolean returnBoolean(JSONObject resultMap){
		boolean resultFlag = false;
		if (resultMap != null && resultMap.size() > 0 ) {
			if (resultMap.containsKey("code")) {
				String yesMute = String.valueOf(resultMap.get("code"));
				if (yesMute != null && KEY_SUCCESS.equals(yesMute)) {
					resultFlag = true;
				} else {
					resultFlag = false;
				}
			}
		} else {
			return resultFlag;
		}
		return  resultFlag;
	}

	public static void main(String[] args) throws Exception {
		try {
			com.honghe.communication.main.EmbedHttpServer.main(new String[]{"123"});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
