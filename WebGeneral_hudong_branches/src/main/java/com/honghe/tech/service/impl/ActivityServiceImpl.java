package com.honghe.tech.service.impl;

import com.honghe.tech.cache.CacheMap;
import com.honghe.tech.dao.*;
import com.honghe.tech.entity.*;
import com.honghe.tech.form.ActivityInfoForm;
import com.honghe.tech.form.AssteachActivityForm;
import com.honghe.tech.httpservice.*;
import com.honghe.tech.service.ActivityService;
import com.honghe.tech.service.NoticeService;
import com.honghe.tech.service.OperLogService;
import com.honghe.tech.service.TeachingSupervisionService;
import com.honghe.tech.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author xinqinggang
 * @date 2018/1/23
 * Author LiZhuoyuan 2018-01-26
 * update houjuntao
 */
@Service
public class ActivityServiceImpl implements ActivityService {
    Logger logger = Logger.getLogger(ActivityServiceImpl.class);
    private final static String MASTER="master";
    private final static String MODULE_NAME = "教学活动";
    private final static String MODULE_CONTENT_ADD_SUCCESS = "用户成功添加一堂互动教学课程";
    private final static String MODULE_CONTENT_ADD_FAILED = "用户添加一堂互动教学课程失败";
    private final static String MODULE_CONTENT_UPDATE_SUCCESS = "用户成功修改互动教学课程";
    private final static String MODULE_CONTENT_UPDATE_FAILED = "用户修改互动教学课程失败";
    /**
     * //操作日志
     */
    private final static String LOG_TYPE = "0";
    private static final int MAX_ACTIVITY_NUM_VHD = 3;
    @Autowired
    private ActivityDao activityDao;
    @Autowired
    private AssteachActivityDao assteachActivityDao;
    @Autowired
    private AreaHttpService areaHttpService;
    @Autowired
    private UserHttpService userHttpService;
    @Autowired
    private LiveStreamDao liveStreamDao;
    @Autowired
    private ScheduleDao scheduleDao;
    @Autowired
    private AreaDao areaDao;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private DeviceHttpService deviceHttpService;
    @Autowired
    private ClasstimeDao classtimeDao;
    @Autowired
    private TermDao termDao;
    @Autowired
    private TeachingSupervisionService teachingSupervisionService;
    @Autowired
    private NoticeHttpService noticeHttpService;
    @Autowired
    private OperLogService operLogService;
    @Autowired
    private SubjectDao subjectDao;
    @Autowired
    private PeriodDao periodDao;
    @Autowired
    private NoticeDao noticeDao;
    @Autowired
    private DeviceToJsftHttpService deviceToJsftHttpService;
    /**
     * //设置日期格式
     */
    SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    /**
     * //设置日期格式
     */
    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * //设置日期格式
     */
    SimpleDateFormat sdfTimeOfHour = new SimpleDateFormat("yyyy-MM-dd HH");
    /**
     * //设置日期格式
     */
    SimpleDateFormat sdfTimeOfMinute = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * //设置时间格式
     */
    SimpleDateFormat sdfHour = new SimpleDateFormat("HH:mm");
    /**
     * //设置时间格式
     */
    SimpleDateFormat sdfTimeMin = new SimpleDateFormat("HH:mm:ss");
    /**
     * //时间格式：yyyy-MM-dd 00:00:00
     */
    public static String startTerm;
    /**
     * 时间格式：yyyy-MM-dd 23:59:59
     */
    public static String endTerm;
    /**
     * 开始日期
     */
    public static Date startTermDate;
    /**
     * 教学周期结束时间
     */
    public static Date endTermDate;

    /**
     * 添加教学活动
     *
     * @param jsonInfo 教学活动信息
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Object saveActivity(String jsonInfo) {
        JSONObject activityJson=null;
        try {
            activityJson = JSONObject.fromObject(jsonInfo);
        }catch (JSONException e){
            logger.error("教学活动信息转成json异常，保存信息失败。",e);
            return "教学活动信息格式错误，不能保存。";
        }
        try {
            JSONArray acceptInfoArr = JSONArray.fromObject(activityJson.get("idsInfo"));
            if(acceptInfoArr==null || acceptInfoArr.size()==0){
                return "接收教室不能为空。";
            }
        }catch (JSONException e){
            return "接收教室数据格式错误，不能保存。";
        }
        //教学活动保存状态
        boolean isSave=false;
        try {
            boolean isCanPre = this.judgePreMay(activityJson.get("startTime").toString() + ":00");
            if (!isCanPre) {
                return "当前时间不可以进行预约";
            }
            isCanPre = this.judgePreInTerm(activityJson.get("startTime").toString() + ":00", activityJson.get("endTime").toString() + ":00");
            if (!isCanPre) {
                return "当前预约时间不在学期内，无法预约";
            }
            //从前端数据中封装主讲教师信息
            Map<String,Object> masterActivityInfo=converMasterAcToMap(activityJson);
            Activity activity=(Activity) masterActivityInfo.get("activity");
            String uuid=activity.getUuid();
            activityJson.put("uuid",uuid);
            //获取地点id
            String areaIdMd5 = MD5.getMD5(activityJson.get("roomAddr") == null ? "" : activityJson.get("roomAddr").toString());
            activity.setAreaId(areaIdMd5);
            //保存地点id到地点表
            boolean isSaveArea=saveArea(activityJson,areaIdMd5);
            if (!isSaveArea) {
                logger.error(this.getClass().getSimpleName() + "在预约活动处添加地点出现异常！！！");
                return "预约活动时，添加主讲教室地点出现异常";
            }
            JSONArray roomInfo = new JSONArray();
            //获取主讲教室信息
            getRoomInfo(String.valueOf(activityJson.get("hostRoomId")),roomInfo);
            JSONArray acceptAndTeacherIds = JSONArray.fromObject(activityJson.get("idsInfo"));
            if (acceptAndTeacherIds.size() <= 0) {
                return "必须选择一个接收教室";
            }
            List<String> roomIdList = (List<String>)masterActivityInfo.get("roomIdList");
            //保存接收预约活动
            Map<String,Object> assteachMap=new HashMap<>();
            assteachMap.put("acceptTeacherIds",acceptAndTeacherIds);
            assteachMap.put("uuid",uuid);
            assteachMap.put("roomIdList",roomIdList);
            List<String> userIdList = new ArrayList<>();
            userIdList.add(String.valueOf(activity.getHostId()));
            assteachMap.put("userIdList",userIdList);
            Map<String,Object> assteachActivityMap=saveAssteachActivity(roomInfo,assteachMap);
            if(assteachActivityMap.containsKey("error")){
                return assteachActivityMap.get("error").toString();
            }else {
                //辅助教师信息
                JSONArray assteachInfo = (JSONArray) assteachActivityMap.get("assteachInfo");
                //调用南京旭鼎接口 增加了existed参数
                boolean isVhd = ConfigUtil.getConfig("isVhd") == null ? false : Boolean.parseBoolean(ConfigUtil.getConfig("isVhd").toString().trim());
                boolean existed = (isVhd && acceptAndTeacherIds.size() > MAX_ACTIVITY_NUM_VHD) || !isVhd;
                //判断此地点该课节是否有课程
                Object stag = this.judgeHasPreAc("", (activityJson.get("startTime").toString() + ":00"), (activityJson.get("endTime").toString() + ":00"), activity.getSpareA(), activity.getUuid(), roomIdList, userIdList);
                if (stag instanceof String) {
                    return stag;
                } else {
                    long currentTimeStamp=System.currentTimeMillis();
                    activity.setSpareC(String.valueOf(currentTimeStamp));
                    activity = saveMcuConfence(activity, activityJson, assteachActivityMap, existed,currentTimeStamp);
                    //保存预约活动
                    isSave = activityDao.saveActivity(activity);
                    //集合主讲教师、辅助教师、预约人信息
                    JSONArray allUser = new JSONArray();
                    //主讲人
                    JSONArray userInfo = (JSONArray) masterActivityInfo.get("userInfo");
                    allUser.addAll(userInfo);
                    allUser.addAll(assteachInfo);
                    //保存日志及通知信息
                    saveLogAndNoticeInfo(activity, activityJson, allUser, isSave);
                }
            }
        } catch (Exception e) {
            logger.error(this.getClass().getSimpleName() + "---saveActivity" + "---添加教学活动异常！", e);
        }
        return isSave;
    }

    /**
     * 教学活动保存完后，如果走南京旭顶，则同步活动记录到mcu
     * @param activity
     * @param activityJson
     * @param assteachActivityMap
     * @param existed   true:保存活动到南京旭顶mcu:false：不保存
     * @return
     */
    private Activity saveMcuConfence(Activity activity,JSONObject activityJson,Map<String,Object> assteachActivityMap,
                                     boolean existed,long currentTimeStamp){
        //封装主动预约mcu教学活动的所需的数据
        Map<String, Object> map = new HashMap<>();
        map.put("activityName", activityJson.get("className") == null ? "" : activityJson.get("className").toString() + currentTimeStamp);
        String mcuIp=activity.getMcuIp();
        map.put("mcuIp", mcuIp);
        JSONObject result=new JSONObject();
        if (existed) {
            map.put("roomInfoList", assteachActivityMap.get("roomInfo")==null ? null:assteachActivityMap.get("roomInfo"));
            Map device=deviceHttpService.getHostByIp(mcuIp);
            if(device!=null && !device.isEmpty()){
                String mcuType=device.get("hostFactory").toString();
                if(ConstantWord.FACTORY_NJXD.equals(mcuType)){
                    map.put("startTime", activityJson.get("startTime") == null ? null : activityJson.get("startTime").toString() + ":00");
                    map.put("endTime", activityJson.get("endTime") == null ? null : activityJson.get("endTime").toString() + ":00");
                    //添加活动到mcu
                    result = addMcuActivity(map);
                }else if(ConstantWord.FACTORY_IFREECOMM.equals(mcuType)){
                    map.put("activityName", currentTimeStamp);
                    map.put("startTime",DateTimeUtil.transTimeToFormat(activityJson.get("startTime").toString() + ":00")+"%2B08:00");
                    map.put("endTime",DateTimeUtil.transTimeToFormat(activityJson.get("endTime").toString() + ":00")+"%2B08:00");
                    result=getJsftResult(mcuIp,map);
                }
            }
            if (result != null && result.get("confID") != null) {
                activity.setSpareB(String.valueOf(result.get("confID")));
            } else {
                activity.setSpareB("");
            }

            logger.debug("保存活动，会议confID=" + activity.getSpareB());
        } else {
            activity.setSpareB("");
        }
        return activity;
    }

    private JSONObject getJsftResult(String mcuIp,Map<String,Object> map) {
        Map<String,String> termianlMap=getTermianlMap(JSONArray.fromObject(map.get("roomInfoList")));
        String matserTerminalCode=termianlMap.get("masterId");
        String acceptTerminalsCode=termianlMap.get("terminals");
        //主会场id
        String masterConferenceId =deviceHttpService.getHostIsTourByCode(matserTerminalCode);
        //与会者id，‘；’隔开
        String convertionersId =deviceHttpService.getHostIsTourByCode(acceptTerminalsCode).replace(",",";");
        String conferenceTempId=ConfigUtil.getConfig("jsft_conference_tempId").toString();
        String number=String.valueOf(System.currentTimeMillis());
        String startTime= map.get("startTime").toString();
        String endTime=map.get("endTime").toString();
        return deviceToJsftHttpService.addConfen(mcuIp,conferenceTempId,map.get("activityName").toString(),
                number,"1",startTime,endTime,masterConferenceId,convertionersId);
    }

    /**
     * 预约活动保存完成后，保存日志信息及通知信息
     * @param activity
     * @param activityJson
     * @param userInfo
     * @param isSave
     */
    private void saveLogAndNoticeInfo(Activity activity,JSONObject activityJson,
                                      JSONArray userInfo,boolean isSave){
        Map<String, Object> logParams = new HashMap<>();
        logParams.put("userId", activity.getUserId());
        Date now =new Date();
        logParams.put("logTime", sdfTimeOfMinute.format(now));
        logParams.put("moduleName", MODULE_NAME);
        logParams.put("type", LOG_TYPE);
        String confId=activity.getSpareB();
        if (isSave) {
            if (!"".equals(confId)) {
                String activityID = String.valueOf(activity.getId());
                //活动id与对应的会议id保存到缓存
                CacheMap.pushCacheMap(activityID,confId);
                logger.debug("保存会议id到缓存，confID=" + confId + ",活动id=" + activityID);
            }
            //预约活动完成后，保存通知信息
            saveNoticeInfo(activity,activityJson,userInfo);
            logParams.put("content", MODULE_CONTENT_ADD_SUCCESS);
            // 发送通知（短信、邮件）
            String noticeTime="noticeTime";
            String sendMessage="1";
            if (activityJson.get(noticeTime) != null && activityJson.get(noticeTime).toString().contains(sendMessage)) {
                try {
                    this.sendMessage(activityJson);
                } catch (Exception e) {
                    logger.error("发送通知异常", e);
                }
            }
        } else {
            logParams.put("content", MODULE_CONTENT_ADD_FAILED);
        }
        operLogService.saveLogTable(logParams);
    }
    /**
     * 保存通知信息
     * @param activity                 活动实体
     * @param activityJson             活动信息
     * @param userInfo                 封装的用户信息
     */
    private void saveNoticeInfo(Activity activity,JSONObject activityJson,JSONArray userInfo){
        JSONObject noticeJson = new JSONObject();
        noticeJson.put("userData", userInfo);
        noticeJson.put("pubUserId", activityJson.get("userId").toString());
        noticeJson.put("time", activityJson.get("startTime").toString() + "~" + activityJson.get("endTime").toString().substring(11));
        noticeJson.put("type", "1");
        noticeJson.put("activityId", String.valueOf(activity.getId()));
        noticeJson.put("name", String.valueOf(activity.getName()));
        //保存消息通知内容
        boolean saveNoticeInfo = noticeService.saveNoticeTable(noticeJson);
        if (!saveNoticeInfo) {
            logger.error(this.getClass().getSimpleName() + "---saveActivity" + "---保存消息通知异常！");
        }
    }
    /**
     * 保存地点信息
     * @param activityJson     主讲课程信息
     * @param areaIdMd5        地点id
     * @return
     */
    private boolean saveArea(JSONObject activityJson,String areaIdMd5){
        String provinceName = (activityJson.get("provinceName") == null ? "" : activityJson.get("provinceName").toString());
        String cityName = (activityJson.get("cityName") == null ? "" : activityJson.get("cityName").toString());
        String countyName = (activityJson.get("countyName") == null ? "" : activityJson.get("countyName").toString());
        String schoolName = (activityJson.get("schoolName") == null ? "" : activityJson.get("schoolName").toString());
        return saveArea(provinceName, cityName, countyName, schoolName, areaIdMd5);
    }

    /**
     * 保存接收教室信息
     * @param roomInfo
     * @param assteachMap
     * @return
     */
    private Map<String,Object> saveAssteachActivity(JSONArray roomInfo,Map<String,Object> assteachMap) {
        Map<String,Object> assteachActivityMap=new HashMap<>();
        JSONArray assteachArr=new JSONArray();
        if(assteachMap!=null && !assteachMap.isEmpty()) {
            JSONArray acceptTeacherArr=(JSONArray)assteachMap.get("acceptTeacherIds");
            String uuid=String.valueOf(assteachMap.get("uuid"));
            List<String> roomIdList=(List<String>)assteachMap.get("roomIdList");
            List<String> userIdList=(List<String>)assteachMap.get("userIdList");
            for (int i = 0; i < acceptTeacherArr.size(); i++) {
//                JSONObject assteachUserInfo = new JSONObject();
                AssteachActivity assteachActivity = new AssteachActivity();
                assteachActivity.setUuid(uuid);
                JSONObject classOrTeacherInfo = JSONObject.fromObject(acceptTeacherArr.get(i));
                int acceptRoomId = classOrTeacherInfo.get("acceptRoomId") == null ? 0 : Integer.valueOf(String.valueOf(classOrTeacherInfo.get("acceptRoomId")));
                assteachActivity.setAcceptRoomId(acceptRoomId);
                if (roomIdList.contains(acceptRoomId + "")) {
                      assteachActivityMap.put("error","主讲教室与接收教室不能相同");
                      return assteachActivityMap;
                } else {
                    roomIdList.add(acceptRoomId + "");
                }
                assteachActivity.setAcceptRoomName(classOrTeacherInfo.get("acceptRoomName") == null ? "" : classOrTeacherInfo.get("acceptRoomName").toString());
                assteachActivity.setRoomStudents(classOrTeacherInfo.get("acceptRoomStudents") == null ? 0 : Integer.valueOf(classOrTeacherInfo.get("acceptRoomStudents").toString()));
                if (classOrTeacherInfo.containsKey("assistTeacherId") && !"".equals(classOrTeacherInfo.get("assistTeacherId").toString())
                        && !"null".equals(classOrTeacherInfo.get("assistTeacherId").toString())) {
                    assteachActivity.setAssistTeacherId((int) classOrTeacherInfo.get("assistTeacherId"));
//                    assteachUserInfo.put("userId", classOrTeacherInfo.get("assistTeacherId").toString());
                } else {
                    assteachActivity.setAssistTeacherId(0);
//                    assteachUserInfo.put("userId", 0);
                }
                assteachActivity.setAssistTeacherName(classOrTeacherInfo.get("assistTeacherName") == null ? "" : classOrTeacherInfo.get("assistTeacherName").toString());
                assteachActivity.setAcceptClassId(0);
                assteachActivity.setAcceptClassName(classOrTeacherInfo.get("acceptClassName") == null ? "" : classOrTeacherInfo.get("acceptClassName").toString());
                assteachActivity.setAcceptRoomAddr(classOrTeacherInfo.get("roomAddr") == null ? "" : classOrTeacherInfo.get("roomAddr").toString());
                String assteacherId=String.valueOf(assteachActivity.getAssistTeacherId());
                if (userIdList.contains(assteacherId)) {
                    assteachActivityMap.put("error","主讲教师与辅助教师不能相同");
                    return assteachActivityMap;
                } else {
                    JSONObject userJson=new JSONObject();
                    if(!"0".equals(assteacherId)) {
                        userJson.put("userId", assteacherId);
                        userJson.put("userRole", ConstantWord.USER_ASS_ROLE);
                        assteachArr.add(userJson);
                        userIdList.add(assteacherId);
                    }
                }
                String areaIdMd5 = MD5.getMD5(classOrTeacherInfo.get("roomAddr") == null ? "" : classOrTeacherInfo.get("roomAddr").toString());
                //获取接收教室信息
                getRoomInfo(String.valueOf(acceptRoomId),roomInfo);
                boolean isSaveArea=saveArea(classOrTeacherInfo,areaIdMd5);
                if (!isSaveArea) {
                    logger.error(this.getClass().getSimpleName() + "在预约活动处添加地点出现异常！！！");
                    assteachActivityMap.put("error","预约活动时，添加接收教室地点出现异常");
                    return assteachActivityMap;
                }
                assteachActivity.setAreaId(areaIdMd5);
//                assteachUserInfo.put("userRole", ConstantWord.USER_ASS_ROLE);
//                userInfo.add(assteachUserInfo);
                assteachActivityDao.saveAssteachActivity(assteachActivity);
            }
            assteachActivityMap.put("roomInfo",roomInfo);
            assteachActivityMap.put("assteachInfo",assteachArr);
        }
        return assteachActivityMap;
    }

    /**
     * 封装单个教室信息
     * @param roomId       教室id
     * @param roomInfo     保存教室的数组
     * @return
     */
    private JSONArray getRoomInfo(String roomId,JSONArray roomInfo){
        Map<String, String> host = areaHttpService.getHostByRoomId(String.valueOf(roomId));
        if (host != null && !host.isEmpty()) {
            JSONObject roomJson = new JSONObject();
            roomJson.put("partyID", host.get("mcu_code"));
            roomJson.put("partyIP", host.get("host_ipaddr"));
            roomJson.put("partyName", host.get("host_name"));
            roomJson.put("partyType", ConstantWord.HHT_DEVICE_TYPE);
            roomInfo.add(roomJson);
        }
        return roomInfo;
    }
    /**
     * 保存预约，封装主讲活动信息到map
     * @param activityJson
     * @return
     */
    private Map<String,Object> converMasterAcToMap(JSONObject activityJson){
        Activity activity = new Activity();
        activity.setName(activityJson.get("className") == null ? "" : activityJson.get("className").toString());
        activity.setPeriodId(activityJson.get("periodId") == null ? 0 : Integer.valueOf(activityJson.get("periodId").toString()));
        activity.setPeriodName(activityJson.getString("periodName") == null ? "" : activityJson.get("periodName").toString());
        activity.setGradeId(activityJson.get("gradeId") == null ? 0 : Integer.valueOf(activityJson.get("gradeId").toString()));
        activity.setGradeName(activityJson.getString("gradeName") == null ? "" : activityJson.get("gradeName").toString());
        activity.setSubjectId(activityJson.get("subjectId") == null ? 0 : Integer.valueOf(activityJson.get("subjectId").toString()));
        activity.setSubjectName(activityJson.get("subjectName") == null ? "" : activityJson.get("subjectName").toString());
        try {
            activity.setStartTime(activityJson.get("startTime") == null ? null : sdfTime.parse(activityJson.get("startTime").toString()));
            activity.setEndTime(activityJson.get("endTime") == null ? null : sdfTime.parse(activityJson.get("endTime").toString()));
        } catch (Exception e) {
            logger.error(this.getClass().getSimpleName() + "时间转换异常!", e);
        }
        //课程简介
        activity.setIntro(activityJson.get("briefIntroduction") == null ? "" : activityJson.get("briefIntroduction").toString());
        activity.setHostId(activityJson.get("hostTeacherId") == null ? 0 : Integer.valueOf(activityJson.get("hostTeacherId").toString()));
        activity.setActivityStyleId(1);
        //主讲教师信息
        JSONObject masterUserInfo = new JSONObject();
        masterUserInfo.put("userId", activityJson.get("hostTeacherId").toString());
        masterUserInfo.put("userRole", ConstantWord.USER_MASTER_ROLE);
        //预约人
        JSONObject applyUserInfo = new JSONObject();
        applyUserInfo.put("userId", activityJson.get("userId").toString());
        applyUserInfo.put("userRole", ConstantWord.USER_APPLY_ROLE);
        //封装消息通知用户部分的数据
        JSONArray masterUserArr = new JSONArray();
        masterUserArr.add(masterUserInfo);
        masterUserArr.add(applyUserInfo);
        activity.setHostName(activityJson.get("hostTeacherName") == null ? "" : activityJson.get("hostTeacherName").toString());
        activity.setRoomId(activityJson.get("hostRoomId") == null ? 0 : Integer.valueOf(activityJson.get("hostRoomId").toString()));
        List<String> roomIdList = new ArrayList<>();
        roomIdList.add(activityJson.get("hostRoomId").toString());
        activity.setRoomName(activityJson.get("hostRoomName") == null ? "" : activityJson.get("hostRoomName").toString());
        activity.setRoomStudents(activityJson.get("hostRoomStudents") == null ? 0 : Integer.valueOf(activityJson.get("hostRoomStudents").toString()));
        //主讲教室听课班级
        activity.setClassId(activityJson.get("hostListenClassId") == null ? 0 : Integer.valueOf(activityJson.get("hostListenClassId").toString()));
        activity.setClassName(activityJson.get("hostListenClassName") == null ? "" : activityJson.get("hostListenClassName").toString());
        activity.setNoticeType(activityJson.get("noticeTime") == null ? "" : activityJson.get("noticeTime").toString());
        //预留字段，默认为0
        activity.setStatus(0);
        activity.setInvalidRemark(activityJson.get("remark") == null ? "" : activityJson.get("remark").toString());
        activity.setUserId(activityJson.get("userId") == null ? 0 : Integer.valueOf(activityJson.get("userId").toString()));
        activity.setRoomAddr(activityJson.get("roomAddr") == null ? "" : activityJson.get("roomAddr").toString());
        //对应课节id
        activity.setSpareA(activityJson.get("lessonId") == null ? "" : activityJson.get("lessonId").toString());
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        activity.setUuid(uuid);
        activity.setIsDel(0);
        activity.setMcuId(activityJson.get("mcuIds") == null ? "" : activityJson.get("mcuIds").toString());
        activity.setMcuIp(activityJson.get("mcuIp") == null ? "" : activityJson.get("mcuIp").toString());
        activity.setMcuType(ConstantWord.HHT_MCU);
        //是否发送邮箱 0-不发送 1-发送
        activity.setSendEmail(activityJson.get("sendEmail") == null ? 0 : Integer.valueOf(activityJson.get("sendEmail").toString()));
        //是否发送短信 0-不发送 1-发送
        activity.setSendMessage(activityJson.get("sendMessage") == null ? 0 : Integer.valueOf(activityJson.get("sendMessage").toString()));
        //处理通知问题
        activity = this.dealNoticeTime(activity, activityJson);
        //是否云直播 0-否 1-是
        activity.setCloudLive(activityJson.get("cloudLive") == null ? 0 : Integer.valueOf(activityJson.get("cloudLive").toString()));
        Map<String,Object> activityInfo=new HashMap<>();
        activityInfo.put("activity",activity);
        activityInfo.put("userInfo",masterUserArr);
        activityInfo.put("roomIdList",roomIdList);
        return activityInfo;
    }
    /**
     * 处理通知时间
     *
     * @param ac
     * @param acJson
     * @return
     */
    private Activity dealNoticeTime(Activity ac, JSONObject acJson) {
        if (acJson.get("noticeTime") != null && !"".equals(acJson.get("noticeTime"))) {
            String noticeType = acJson.get("noticeTime").toString();
            Map<String, Object> acMap = JSONObject.fromObject(acJson);
            String date = this.getNoticeTime(acMap.get("startTime").toString() + ":00", noticeType);
            ac.setNoticeTime(date);
        } else {
            ac.setNoticeType("");
        }
        return ac;
    }

    /**
     * 修改活动信息用：获取通知类型及通知时间
     *
     * @param acJson
     * @return
     */
    private void dealNoticeTime(Map map, JSONObject acJson, Activity activity) {
        try {
            if (acJson.get("noticeTime") != null && !"".equals(acJson.get("noticeTime"))) {
                String noticeType = acJson.get("noticeTime").toString();
                String date = this.getNoticeTime(DateTimeUtil.formatDateTime(activity.getStartTime()), noticeType);
                map.put("noticeType", noticeType);
                map.put("noticeTime", date);
            } else {
                map.put("noticeType", "");
                map.put("noticeTime", "");
            }
        } catch (Exception e) {
            logger.error("修改活动信息用：获取通知类型及通知时间异常", e);
        }
    }

    /**
     * 添加mcu教学活动
     *
     * @param map 所需的教学活动信息
     * @return
     */
    public JSONObject addMcuActivity(Map map) {
        try {
            JSONObject json = deviceHttpService.addActivity(map.get("activityName").toString(),
                    map.get("startTime").toString(), map.get("endTime").toString(),
                    JSONArray.fromObject(map.get("roomInfoList")), map.get("mcuIp").toString());
            //TODO: 这里暂时返回true，应根据json中的code来判断是否预约成功，若成功则返回true，否则返回false
            return json;
        } catch (Exception e) {
            logger.error("添加mcu教学活动异常", e);
            return null;
        }
    }

    /**
     * 保存地点
     *
     * @param provinceName 省份名称
     * @param cityName     城市名称
     * @param countyName   区县名称
     * @param schoolName   学校名称
     * @param areaId       地点id
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean saveArea(String provinceName, String cityName, String countyName, String schoolName, String areaId) {
        try {
            Area oldArea = areaDao.getAreaMessage(areaId);
            if (oldArea != null) {
                Map map = new HashMap();
                map.put("province", provinceName);
                map.put("city", cityName);
                map.put("county", countyName);
                map.put("school", schoolName);
                map.put("areaId", areaId);
                areaDao.updateAreaById(map);
                return true;
            } else {
                Area area = new Area();
                area.setProvince(provinceName);
                area.setCity(cityName);
                area.setCounty(countyName);
                area.setSchool(schoolName);
                area.setAreaId(areaId);
                areaDao.saveArea(area);
                return true;
            }
        } catch (Exception e) {
            logger.error(this.getClass().getSimpleName() + "---saveArea" + "---保存或更新地点（本地area）异常！", e);
            return false;
        }
    }

    /**
     * 用于修改教学活动回显
     *
     * @param id 教学活动id
     * @return
     */
    @Override
    public JSONObject getActivityById(int id) {
        JSONObject activityInfo = new JSONObject();
        //所有接收教室和辅助教师信息
        JSONArray acceptInfo = new JSONArray();
        try {
            Activity activity = activityDao.selectActivityById2(id);
            if (activity == null) {
                return activityInfo;
            } else {
                activityInfo = JSONObject.fromObject(activity);
                List<AssteachActivity> assteachActivities = assteachActivityDao.selectAssteachActivityByUuid2(activity.getUuid());
                if (assteachActivities != null && assteachActivities.size() > 0) {
                    for (AssteachActivity as : assteachActivities) {
                        //一个接收教室和辅助教师信息
                        JSONObject oneAcceptInfo = new JSONObject();
                        oneAcceptInfo = JSONObject.fromObject(as);
                        acceptInfo.add(oneAcceptInfo);
                    }
                }
                activityInfo.put("acceptInfo", acceptInfo);
            }
        } catch (Exception e) {
            logger.error("用于修改教学活动回显异常", e);
        }
        return activityInfo;
    }

    /**
     * 根据学校获取教室
     *
     * @param schoolId  学校id
     * @param roomType  教室类型，hostRoom:主讲教室；acceptRoom:接收教室
     * @param startTime 周开始时间
     * @param endTime   周结束时间
     * @return 教室集合
     */
    @Override
    public List<Map<String, Object>> getRoomBySchool(String schoolId, String roomType, String startTime, String endTime) {
        if (schoolId == null || "".equals(schoolId)) {
            logger.debug("学校id:" + schoolId + "根据学校查询教室，参数异常。");
            throw new IllegalArgumentException();
        } else {
            List<Map<String, Object>> roomList = null;
            if (roomType == null || "".equals(roomType)) {
                return new ArrayList<Map<String, Object>>();
            }
            Map<String, Object> map = null;
            switch (roomType) {
                case "hostRoom":
                    map = userHttpService.getRoomBySchoolId(schoolId, "", "1", 1, 9999, "");
                    break;
                case "acceptRoom":
                    map = userHttpService.getRoomBySchoolId(schoolId, "", "0", 1, 9999, "");
                    break;
                default:
                    throw new IllegalArgumentException();
            }
            if (map.get("items") != null) {
                roomList = JSONArray.fromObject(map.get("items"));
            } else {
                return new ArrayList<Map<String, Object>>();
            }
            List<Map<String, Object>> resultList = new ArrayList<>();
            if (roomList != null && roomList.size() > 0) {
                for (Map<String, Object> roomInfo : roomList) {
                    Map<String, Object> roomMap = new HashedMap();
                    roomMap.put("roomId", roomInfo.get("roomId"));
                    roomMap.put("roomName", roomInfo.get("roomName"));
                    roomMap.put("roomStudents", roomInfo.get("number"));
                    resultList.add(roomMap);
                }
            }
            return resultList;
        }
    }


    /**
     * 修改教学活动
     *
     * @param activityInfo 教学活动修改信息
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Object updateActivityByParam(JSONObject activityInfo) {
        Activity activity =null;
        try{
            if(activityInfo.get("id")!=null && !"".equals(String.valueOf(activityInfo.get("id")))){
                int id = Integer.valueOf(String.valueOf(activityInfo.get("id")));
                activity = activityDao.selectActivityById(id);
            }
        }catch (ClassCastException e){
            logger.error("字符串转成整型异常",e);
            return "教学活动id错误！";
        }
        boolean isUpdate = false;
        if (activity == null) {
            return ConstantWord.UN_EXIST;
        } else {
            String uuid = activity.getUuid();
            assteachActivityDao.deleteAssteachActivityByUuid(uuid);
            //根据时间判断是否可以预约和修改
            boolean isCanPre = false;
            try {
                isCanPre = this.judgePreMay(DateTimeUtil.formatDateTime(activity.getStartTime()));
            } catch (ParseException e) {
                logger.error("时间转换异常",e);
            }
            if (!isCanPre) {
                return "当前时间不可以修改预约活动";
            }
            Map params=converAcJsonToMap(activityInfo);
            String areaIdMd5=params.get("areaId").toString();
            boolean isSaveArea = saveArea(activityInfo,areaIdMd5);
            if (!isSaveArea) {
                logger.error(this.getClass().getSimpleName() + "在修改教学活动处修改地点出现异常！！！");
                return "修改教学活动地点出现异常";
            }
            //保存单个教室信息
            String roomId=activityInfo.get("roomId").toString();
            JSONArray roomInfo = new JSONArray();
            getRoomInfo(roomId,roomInfo);
            JSONArray acceptAndTeacherIds = JSONArray.fromObject(activityInfo.get("idsInfo"));
            if (acceptAndTeacherIds.size() <= 0) {
                return "必须选择一个接收教室";
            }
            //保存接收预约活动
            Map<String,Object> assteachMap=new HashMap<>();
            assteachMap.put("acceptTeacherIds",acceptAndTeacherIds);
            assteachMap.put("uuid",uuid);
            List<String> roomIdList = new ArrayList<>();
            roomIdList.add(activityInfo.get("roomId").toString());
            assteachMap.put("roomIdList",roomIdList);
            List<String> userIdList = new ArrayList<>();
            userIdList.add(String.valueOf(activity.getHostId()));
            assteachMap.put("userIdList",userIdList);
            Map<String,Object> assteachActivityMap=saveAssteachActivity(roomInfo,assteachMap);
            if(assteachActivityMap.containsKey("error")){
                return assteachActivityMap.get("error").toString();
            }

            Object stag = null;
            try {
                stag = this.judgeHasPreAc(activityInfo.get("id").toString(), DateTimeUtil.formatDateTime(activity.getStartTime()),
                        DateTimeUtil.formatDateTime(activity.getEndTime()), activity.getSpareA(), uuid, roomIdList, userIdList);
            } catch (ParseException e) {
                logger.error("时间转换异常",e);
            }
            if (stag instanceof String) {
                    return stag;
            } else {
                //封装活动信息，更新mcu会议记录
                Map<String,Object> activityMap=new HashMap<>();
                activityMap.put("acceptTeacherArr",acceptAndTeacherIds);
                activityMap.put("activity",activity);
                activityMap.put("activityInfo",activityInfo);
                activityMap.put("roomInfo",roomInfo);
                activityMap.put("params",params);
                long currentTimeStamp=System.currentTimeMillis();
                activityMap.put("currentTimeStamp",String.valueOf(currentTimeStamp));
                params=updateMcuConfence(activityMap);
                //获取通知类型及通知时间
                this.dealNoticeTime(params, activityInfo, activity);
                isUpdate = activityDao.updateActivityByParam(params);
                //保存更新活动日志
                saveUpdateLog(activity,activityInfo,params,isUpdate);
            }
        }
        return isUpdate;
    }

    /**
     * 将互动信息转换成json
     * @param activityInfo
     * @return
     */
    private Map converAcJsonToMap(JSONObject activityInfo){
        Map params = new HashMap();
        params.put("id", activityInfo.get("id") == null ? null : activityInfo.get("id"));
        params.put("name", activityInfo.get("name") == null ? null : activityInfo.get("name"));
        params.put("gradeId", activityInfo.get("gradeId") == null ? null : activityInfo.get("gradeId"));
        params.put("gradeName", activityInfo.get("gradeName") == null ? null : activityInfo.get("gradeName"));
        params.put("periodId", activityInfo.get("periodId") == null ? null : activityInfo.get("periodId"));
        params.put("periodName", activityInfo.get("periodName") == null ? null : activityInfo.get("periodName"));
        params.put("subjectId", activityInfo.get("subjectId") == null ? null : activityInfo.get("subjectId"));
        params.put("subjectName", activityInfo.get("subjectName") == null ? null : activityInfo.get("subjectName"));
        params.put("intro", activityInfo.get("intro") == null ? null : activityInfo.get("intro"));
        params.put("hostId", activityInfo.get("hostId") == null ? null : activityInfo.get("hostId"));
        params.put("hostName", activityInfo.get("hostName") == null ? null : activityInfo.get("hostName"));
        params.put("roomId", activityInfo.get("roomId") == null ? null : activityInfo.get("roomId"));
        params.put("roomName", activityInfo.get("roomName") == null ? null : activityInfo.get("roomName"));
        params.put("roomStudents", activityInfo.get("roomStudents") == null ? null : activityInfo.get("roomStudents"));
        params.put("classId", activityInfo.get("classId") == null ? null : activityInfo.get("classId"));
        params.put("className", activityInfo.get("className") == null ? null : activityInfo.get("className"));
        params.put("cloudLive", activityInfo.get("cloudLive") == null ? null : activityInfo.get("cloudLive"));
        params.put("roomAddr", activityInfo.get("roomAddr") == null ? null : activityInfo.get("roomAddr"));
        params.put("mcuId", activityInfo.get("mcuId") == null ? null : activityInfo.get("mcuId"));
        params.put("mcuIp", activityInfo.get("mcuIp") == null ? null : activityInfo.get("mcuIp"));
        params.put("mcuType", activityInfo.get("mcuType") == null ? null : activityInfo.get("mcuType"));
        params.put("sendEmail", activityInfo.get("sendEmail") == null ? null : activityInfo.get("sendEmail"));
        params.put("sendMessage", activityInfo.get("sendMessage") == null ? null : activityInfo.get("sendMessage"));
        params.put("userId", activityInfo.get("userId") == null ? null : activityInfo.get("userId"));
        params.put("spareA", activityInfo.get("spareA") == null ? null : activityInfo.get("spareA"));
        params.put("status", activityInfo.get("status") == null ? null : activityInfo.get("status"));
        params.put("invalidRemark", activityInfo.get("invalidRemark") == null ? null : activityInfo.get("invalidRemark"));
        String areaIdMd5 = MD5.getMD5(activityInfo.get("roomAddr") == null ? "" : activityInfo.get("roomAddr").toString());
        params.put("areaId", areaIdMd5);
        return params;
    }
    /**
     * 保存更新活动日志
     * @param activity
     * @param activityInfo
     * @param params
     * @param isUpdate  true:预约活动更新成功;false:更新失败
     */
    private void saveUpdateLog(Activity activity,JSONObject activityInfo,Map params,boolean isUpdate){
        Map<String, Object> logParams = new HashMap<>();
        logParams.put("userId", params.get("userId"));
        Date now=new Date();
        logParams.put("logTime", sdfTimeOfMinute.format(now));
        logParams.put("moduleName", MODULE_NAME);
        logParams.put("type", LOG_TYPE);
        if(isUpdate){
            //发送通知
            if (activityInfo.get("noticeTime") != null && activityInfo.get("noticeTime").toString().contains("1")) {
                try {
                    activityInfo.put("startDate", DateTimeUtil.formatDateTime(activity.getStartTime()));
                    this.sendMessage(activityInfo);
                } catch (ParseException e) {
                    logger.error("发送通知异常", e);
                }
            }
            logParams.put("content", MODULE_CONTENT_UPDATE_SUCCESS);
        }else{
            logParams.put("content", MODULE_CONTENT_UPDATE_FAILED);
        }
        operLogService.saveLogTable(logParams);
    }
    /**
     * 更新mcu会议记录
     * @param activityMap  活动信息集合
     * @return
     */
   private Map updateMcuConfence(Map<String,Object> activityMap){
       boolean isVhd = ConfigUtil.getConfig("isVhd") == null ? false : Boolean.parseBoolean(ConfigUtil.getConfig("isVhd").toString().trim());
       Map params=(Map)activityMap.get("params");
       if(activityMap!=null && !activityMap.isEmpty()) {
           //走南京旭顶
           JSONArray acceptTeacherArr = (JSONArray) activityMap.get("acceptTeacherArr");
           if ((isVhd && acceptTeacherArr.size() > MAX_ACTIVITY_NUM_VHD) || !isVhd) {
               //先删除mcu原有会议，在添加mcu会议
               Activity activity=(Activity)activityMap.get("activity");
               JSONObject activityInfo=(JSONObject)activityMap.get("activityInfo");
               Map device=deviceHttpService.getHostByIp(activity.getMcuIp());
               JSONObject result=new JSONObject();
               if(device!=null && !device.isEmpty()){
                    String mcuType=device.get("hostFactory").toString();
                    Map<String, Object> map = new HashMap<>();
                    //封装主动预约mcu教学活动的所需的数据
                    map.put("activityName", activityInfo.get("name") == null ? "" : activityInfo.get("name").toString()
                            + activityMap.get("currentTimeStamp"));
                    map.put("mcuIp", activityInfo.get("mcuIp") == null ? "" : activityInfo.get("mcuIp"));
                    JSONArray roomInfo=(JSONArray)activityMap.get("roomInfo");
                    map.put("roomInfoList", roomInfo);
                    if(ConstantWord.FACTORY_NJXD.equals(mcuType)){
                       deviceHttpService.delConference(activity.getSpareB(), activity.getName());
                       try {
                           map.put("startTime", DateTimeUtil.formatDateTime(activity.getStartTime()));
                           map.put("endTime", DateTimeUtil.formatDateTime(activity.getEndTime()));
                       } catch (ParseException e) {
                           logger.error("时间转换异常",e);
                           map.put("startTime",null);
                           map.put("endTime",null);
                       }
                       result = addMcuActivity(map);
                   }else if(ConstantWord.FACTORY_IFREECOMM.equals(mcuType)){
                        String mcuIp=activityInfo.get("mcuIp") == null ? "" : activityInfo.get("mcuIp").toString();
                        if(mcuIp!=null && !"".equals(mcuIp)){
                            deviceToJsftHttpService.delConfForJSFT(mcuIp,activity.getSpareB());
                           try {
                               String startTime=DateTimeUtil.transTimeToFormat(DateTimeUtil.formatDateTime(activity.getStartTime()))+"%2B08:00";
                               String endTime = DateTimeUtil.transTimeToFormat(DateTimeUtil.formatDateTime(activity.getEndTime()))+"%2B08:00";
                               map.put("activityName",activityMap.get("currentTimeStamp"));
                               map.put("startTime",startTime);
                               map.put("endTime",endTime);
                           } catch (ParseException e) {
                               logger.error("时间转换异常",e);
                               map.put("activityName","");
                               map.put("startTime",null);
                               map.put("endTime",null);
                           }
                           result=getJsftResult(mcuIp,map);
                        }
                   }
               }
               if (result!= null && result.get("confID") != null) {
                   params.put("spareB", String.valueOf(result.get("confID")));
               } else {
                   params.put("spareB", "");
               }
               logger.debug("修改活动，会议confID=" + params.get("spareB"));
           } else {
               params.put("spareB", "");
           }
       }
       params.put("spareC",activityMap.get("currentTimeStamp"));
       return params;
   }
    /**
     * 通过当天日期（格式：2018-01-01）得到教学活动集合
     *
     * @param date 当前时间
     * @return
     */
    @Override
    public JSONObject getActivitiesByDate(String date) {
        JSONObject json = new JSONObject();
        try {
            List activities = activityDao.getActivitiesByDate(date);
            if (activities != null && !activities.isEmpty()) {
                json = courseResult(activities);
            }
        } catch (Exception e) {
            logger.error(this.getClass().getSimpleName() + "---查询当天教学活动异常！", e);
        }
        return json;
    }

    /**
     * 通过当天日期,終端ip、終端id（格式：2018-01-01）得到教学活动集合
     *
     * @param date 当前时间
     * @return
     */
    @Override
    public JSONObject getActivitiesByDateByIp(String date, String ip, String id, boolean flag) {
        JSONObject json = new JSONObject();
        List<Activity> activityList;
        if(flag) {
            activityList=getActivities(date,ip,id,null);
            if(activityList!=null && !activityList.isEmpty()) {
                json = courseResult(activityList);
            }
        }else{
            activityList=getActivities(date,ip,id,MASTER);
            if(activityList!=null && !activityList.isEmpty()) {
                json = courseResultForHk2000(activityList);
            }
        }
        return json;
    }

    /**
     * 获取预约活动列表
     * @param date     获取日期
     * @param ip       终端ip
     * @param id       终端编码id
     * @param flag     null:获取主讲及接收教室活动;master：只获取主讲教室活动
     * @return
     */
    private List<Activity> getActivities(String date,String ip,String id,String flag){
        List<Activity> activityList = new ArrayList<>();
        try {
            //主讲教室id
            String roomId="";
            if(id!=null && !"".equals(id)){
                roomId=areaHttpService.getRoomIdByHostCode(id);
            }else if(ip!=null && !"".equals(ip)){
                roomId=areaHttpService.getRoomIdByHostIp(ip);
            }
            if(!"".equals(roomId)) {
                List<Map<String,Object>> activities;
                if(flag!=null && !MASTER.equals(flag)){
                    activities=activityDao.getActivitiesByDateAndRoomId(date+"%", roomId);
                }else{
                    activities=activityDao.getMasterActivities(date+"%", roomId);
                }
                if(activities!=null && !activities.isEmpty()){
                    for(Map<String,Object> ac:activities) {
                        if (ac.get("activity_id") == null || ac.get("activity_style_id") == null || ac.get("name") == null || ac.get("start_time") == null
                                || ac.get("end_time") == null || ac.get("subject_id") == null || ac.get("grade_id") == null || ac.get("host_id") == null
                                || ac.get("room_id") == null) {
                            continue;
                        } else {
                            activityList.add(getActivityByMap(ac));
                        }
                    }
                }
            }

        } catch (Exception e) {
            logger.error(this.getClass().getSimpleName() + "---查询当天教学活动异常！", e);
        }
        return activityList;
    }

    /**
     * 将活动的map转换成Activity实体
     * @param ac
     * @return
     */
    private Activity getActivityByMap(Map<String,Object> ac){
        Activity activity=new Activity();
        activity.setId(Integer.parseInt(String.valueOf(ac.get("activity_id"))));
        activity.setActivityStyleId(Integer.parseInt(String.valueOf(ac.get("activity_style_id"))));
        activity.setName(ac.get("name").toString()+String.valueOf(ac.get("currentTimeStamp")));
        try {
            activity.setStartTime(sdfTimeOfMinute.parse(ac.get("start_time").toString()));
            activity.setEndTime(sdfTimeOfMinute.parse(ac.get("end_time").toString()));
            activity.setUpdateTime(ac.get("update_time") == null ? null : sdfTimeOfMinute.parse(ac.get("update_time").toString()));
        }catch (ParseException e){
            logger.error("时间转换异常。",e);
            activity.setStartTime(null);
            activity.setEndTime(null);
            activity.setUpdateTime(null);
        }
        activity.setSubjectId(Integer.parseInt(String.valueOf(ac.get("subject_id"))));
        activity.setGradeId(Integer.parseInt(String.valueOf(ac.get("grade_id"))));
        activity.setHostId(Integer.parseInt(String.valueOf(ac.get("host_id"))));
        activity.setRoomId(Integer.parseInt(String.valueOf(ac.get("room_id"))));
        activity.setRoomName(ac.get("room_name") == null ? "" : ac.get("room_name").toString());
        activity.setClassId(ac.get("class_id") == null ? 0 : Integer.parseInt(String.valueOf(ac.get("class_id"))));
        activity.setUuid(ac.get("uuid") == null ? "" : ac.get("uuid").toString());
        activity.setIntro(ac.get("intro") == null ? "" : ac.get("intro").toString());
        activity.setSendEmail(ac.get("send_email") == null ? 0 : Integer.parseInt(String.valueOf(ac.get("send_email"))));
        activity.setSendMessage(ac.get("send_message") == null ? 0 : Integer.parseInt(String.valueOf(ac.get("send_message"))));
        activity.setMcuId(ac.get("mcu_id") == null ? "" : ac.get("mcu_id").toString());
        activity.setCloudLive(ac.get("cloud_live") == null ? 0 : Integer.parseInt(String.valueOf(ac.get("cloud_live"))));
        activity.setStatus(ac.get("status") == null ? 0 : Integer.parseInt(String.valueOf(ac.get("status"))));
        activity.setInvalidRemark(ac.get("invalid_remark") == null ? "" : ac.get("invalid_remark").toString());
        activity.setUserId(ac.get("userId") == null ? 0 : Integer.parseInt(String.valueOf(ac.get("userId"))));
        activity.setNoticeTime(ac.get("notice_time") == null ? "" : ac.get("notice_time").toString());
        activity.setSpareB(ac.get("spare_b") == null ? "" : ac.get("spare_b").toString());
        return activity;
    }
    /**
     * 得到整点之后的教学活动数据
     *
     * @param startTime 开始时间 格式：2018-01-01 08:00
     * @return
     */
    @Override
    public JSONObject getActivitiesByTime(String startTime, String date) {
        Map map = new HashMap();
        List<Activity> activities = null;
        map.put("startTime", startTime);
        map.put("date", date);
        try {
            activities = activityDao.getActivitiesByTime(map);
        } catch (Exception e) {
            logger.error(this.getClass().getSimpleName() + "---查询当天整点之后的教学活动异常！", e);
        }
        JSONObject json = new JSONObject();
        json = courseResult(activities);
        return json;
    }


    /**
     * 教学活动格式化返回数据
     * @param activities 教学活动list
     * @return
     */
    private JSONObject courseResult(List<Activity> activities) {
        JSONObject jsonObject = new JSONObject();
        JSONArray data = new JSONArray();
        List<AssteachActivity> assteachAcitivities;
        try {
            jsonObject.put("success", true);
            int bookcount = 0;
//            boolean isVhd = ConfigUtil.getConfig("isVhd") == null ? false : Boolean.parseBoolean(ConfigUtil.getConfig("isVhd").toString().trim());
            for (Activity ac : activities) {
                assteachAcitivities = assteachActivityDao.selectAssteachActivityByUuid(ac.getUuid());
                if (assteachAcitivities != null && assteachAcitivities.size() <= MAX_ACTIVITY_NUM_VHD) {
                    bookcount++;
                    JSONArray content = new JSONArray();
                    JSONObject info = new JSONObject();
                    info.put("date", sdfTime.format(new Date()));
                    JSONObject contentInfo = new JSONObject();
                    contentInfo.put("status", String.valueOf(ac.getStatus()));
                    contentInfo.put("subject", ac.getSubjectName());
                    contentInfo.put("begintime", sdfTimeOfMinute.format(ac.getStartTime()));
                    contentInfo.put("endtime", sdfTimeOfMinute.format(ac.getEndTime()));
                    contentInfo.put("abstract", ac.getIntro());
                    String startHour = sdfHour.format(sdfTimeOfMinute.parse(contentInfo.get("begintime").toString()));
                    String endHour = sdfHour.format(sdfTimeOfMinute.parse(contentInfo.get("endtime").toString()));
                    contentInfo.put("theme", ac.getName() + " " + startHour + "~" + endHour);
                    //接收教室数量加上一个主讲教室
                    contentInfo.put("classcount", String.valueOf(assteachAcitivities.size() + 1));
                    JSONArray classInfo = classInfoResult(ac);
                    contentInfo.put("classinfo", classInfo);
                    contentInfo.put("meetingnum", ac.getSpareB() == null ? "" : ac.getSpareB());
                    content.add(contentInfo);
                    info.put("content", content);
                    data.add(info);
                }
            }
            jsonObject.put("data", data);
            //活动总数量
            jsonObject.put("bookcount", bookcount);
        } catch (Exception e) {
            jsonObject.put("success", false);
            jsonObject.put("data", "");
            logger.error(this.getClass().getSimpleName() + "---格式化返回数据失败！！！", e);
        }
        return jsonObject;
    }

    /**
     * 格式化返回数据
     *
     * @param activities 教学活动list,对接互动1.0
     * @return
     */
    private JSONObject courseResultForHk2000(List<Activity> activities) {
        JSONObject jsonObject = new JSONObject();
        List<AssteachActivity> assteachAcitivities = new ArrayList<>();
        try {
            jsonObject.put("success", true);
            JSONObject info = new JSONObject();
            info.put("date", sdfDate.format(new Date()));
            info.put("bookcount", activities.size());
            JSONArray content = new JSONArray();
            for (Activity ac : activities) {
                assteachAcitivities = assteachActivityDao.selectAssteachActivityByUuid(ac.getUuid());
                if (assteachAcitivities != null) {
                    JSONObject contentInfo = new JSONObject();
                    contentInfo.put("status", String.valueOf(ac.getStatus()));
                    contentInfo.put("subject", ac.getSubjectName());
                    contentInfo.put("begintime", sdfTimeMin.format(ac.getStartTime()));
                    contentInfo.put("endtime", sdfTimeMin.format(ac.getEndTime()));
                    contentInfo.put("abstract", ac.getIntro());
                    String startHour = sdfHour.format(sdfTimeMin.parse(contentInfo.get("begintime").toString()));
                    String endHour = sdfHour.format(sdfTimeMin.parse(contentInfo.get("endtime").toString()));
                    contentInfo.put("theme", ac.getName() + " " + startHour + "~" + endHour);
                    //接收教室数量加上一个主讲教室
                    contentInfo.put("classcount", String.valueOf(assteachAcitivities.size() + 1));
                    JSONArray classInfo = classInfoResult(ac);
                    contentInfo.put("classinfo", classInfo);
                    contentInfo.put("meetingnum", ac.getSpareB() == null ? "" : ac.getSpareB());
                    content.add(contentInfo);
                    info.put("content", content);
                }
            }
            jsonObject.put("data", JSONArray.fromObject(info));
        } catch (Exception e) {
            jsonObject.put("success", false);
            jsonObject.put("data", "");
            logger.error(this.getClass().getSimpleName() + "---格式化返回数据失败！！！", e);
        }
        return jsonObject;
    }

    /**
     * 格式化返回教室信息
     *
     * @param ac 教学活动实体
     * @return jsonarray
     * update by caoqian
     */
    public JSONArray classInfoResult(Activity ac) {
        //主讲教室终端信息
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> masterHost = areaHttpService.getHostByRoomId(String.valueOf(ac.getRoomId()));
        Map<String, String> hostMap = new HashMap<>();
        if (masterHost != null && !masterHost.isEmpty()) {
            //TODO: 通过主讲教室id得到对应终端编号，如‘123407’
            hostMap.put("id", String.valueOf(masterHost.get("mcu_code")));
            //TODO: 通过主讲教室id得到对应终端i
            hostMap.put("ip", masterHost.get("host_ipaddr") == null ? "" : masterHost.get("host_ipaddr"));
            hostMap.put("rtmp", teachingSupervisionService.getRtmpAddr(hostMap.get("id")));
        } else {
            hostMap.put("id", "");
            hostMap.put("ip", "");
            hostMap.put("rtmp", "");
        }
        //TODO: 通过主讲教室id得到名称
        hostMap.put("name", ac.getRoomName() == null ? "" : ac.getRoomName());
        hostMap.put("identity", "master");
        list.add(hostMap);
        List<AssteachActivity> assteachAcitivities = assteachActivityDao.selectAssteachActivityByUuid(ac.getUuid());
        if (assteachAcitivities != null && !assteachAcitivities.isEmpty()) {
            for (AssteachActivity at : assteachAcitivities) {
                Map<String, String> listenRoomMap = new HashMap<>();
                //接收教室终端
                Map<String, String> acceptHost = areaHttpService.getHostByRoomId(String.valueOf(at.getAcceptRoomId()));
                if (acceptHost != null && !acceptHost.isEmpty()) {
                    //TODO: 通过接收教室id得到对应终端编号，如‘123407’
                    listenRoomMap.put("id", String.valueOf(acceptHost.get("mcu_code")));
                    //TODO：通过接收教室id得到终端ip
                    listenRoomMap.put("ip", acceptHost.get("host_ipaddr") == null ? "" : acceptHost.get("host_ipaddr"));
                    listenRoomMap.put("rtmp", teachingSupervisionService.getRtmpAddr(listenRoomMap.get("id")));
                } else {
                    //TODO: 通过接收教室id得到对应终端编号，如‘123407’
                    listenRoomMap.put("id", "");
                    //TODO：通过接收教室id得到终端ip
                    listenRoomMap.put("ip", "");
                    listenRoomMap.put("rtmp", "");
                }
                //TODO: 通过接收教室id得到名称
                listenRoomMap.put("name", at.getAcceptRoomName() == null ? "" : at.getAcceptRoomName());
                listenRoomMap.put("identity", "auditor");
                list.add(listenRoomMap);
            }
        }
        return JSONArray.fromObject(list);
    }

    /**
     * 删除教学活动（逻辑删除）
     *
     * @param activityId 教学活动id
     * @param userId     用户id
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Object deleteActivityById(int activityId, int userId) {
        boolean result = false;
        try {
            Map params = new HashMap();
            params.put("id", activityId);
            //查询活动信息
            List<ActivityInfoForm> activityList = activityDao.selectActivityInfoByParam(params);
            if (activityList != null && !activityList.isEmpty()) {
                ActivityInfoForm activityInfo = activityList.get(0);
                boolean isCanPre = this.judgePreMay(activityInfo.getStartTime());
                if (!isCanPre) {
                    return "当前时间不可以删除预约活动";
                }
                long activityStartTime = DateTimeUtil.parseDateTime(activityInfo.getStartTime()).getTime();
                long nowTime = System.currentTimeMillis();
                long intervalTime = 3600 * 1000;
                if ((activityStartTime - nowTime) > intervalTime) {
                    //会议名称
                    String confName = activityInfo.getName();
                    String confID = CacheMap.getCacheMap(String.valueOf(activityId));
                    String mcuIp="";
                    String conferenceId="";
                    Activity ac = activityDao.selectActivityById(activityId);
                    if (ac != null) {
                        mcuIp = ac.getMcuIp();
                        conferenceId = ac.getSpareB();
                    }
                    if (confID == null || "".equals(confID)) {
                        if (ac != null) {
                            confID = ac.getSpareB();
                        }
                    }
                    //删除活动
                    result = activityDao.deleteActivity(activityId);
                    if (result) {
                        Map device=deviceHttpService.getHostByIp(mcuIp);
                        //删除mcu会议
                        if(device!=null && !device.isEmpty()){
                            String mcuType=device.get("hostFactory").toString();
                            if(ConstantWord.FACTORY_NJXD.equals(mcuType)){
                                deviceHttpService.delConference(confID, confName);
                            }else if(ConstantWord.FACTORY_IFREECOMM.equals(mcuType)){
                                if(!"".equals(mcuIp)) {
                                    deviceToJsftHttpService.delConfForJSFT(mcuIp, conferenceId);
                                }
                            }
                        }
                        //清空缓存中保存的会议id
                        CacheMap.removeCacheMap(String.valueOf(activityId));
                    }
                } else {
                    logger.debug("该时间内不允许删除节目");
                    return "该时间内不允许删除节目";
                }
                Map<String, Object> logParams = new HashMap<>();
                logParams.put("userId", userId);
                Date now = new Date();
                logParams.put("logTime", sdfTimeOfMinute.format(now));
                logParams.put("moduleName", MODULE_NAME);
                logParams.put("type", LOG_TYPE);
                String activityName = activityInfo.getName();
                if (result) {
                    logParams.put("content", "用户成功删除" + activityName);
                    noticeDao.deleteNoticeByActivityId(activityId);
                } else {
                    logParams.put("content", "用户删除" + activityName + "失败");
                }
                operLogService.saveLogTable(logParams);
            } else {
                logger.info("该活动不存在，活动id=" + activityId);
                return "该活动已经被删除";
            }
        } catch (Exception e) {
            result = false;
            logger.error("根据活动id删除活动异常，activityId=" + activityId + ";userId=" + userId, e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();   //事物回滚
        }
        return result;
    }

    /**
     * 根据教室id获取当天课程预告
     *
     * @param roomId 教室ID
     * @return 课程预告集合
     */
    @Override
    public JSONArray getPreActivityList(int roomId) {
        JSONArray acArray = new JSONArray();
        try {
            //根据教室id查询活动信息
            List<ActivityInfoForm> activityList = activityDao.selectPreActivityByRoomId(roomId);
            if (activityList != null && !activityList.isEmpty()) {
                JSONObject acInfoJson = new JSONObject();
                for (ActivityInfoForm acInfo : activityList) {
                    if (DateTimeUtil.parseDateTime(acInfo.getStartTime()).getTime() <= System.currentTimeMillis()) {
                        acInfo.setActivityStatus("正在直播");
                    } else {
                        acInfo.setActivityStatus("");
                    }
                    acInfoJson.put("startTime", DateTimeUtil.formatTime_NoSecond(DateTimeUtil.parseDateTime(acInfo.getStartTime())));
                    acInfoJson.put("endTime", DateTimeUtil.formatTime_NoSecond(DateTimeUtil.parseDateTime(acInfo.getEndTime())));
                    acInfoJson.put("name", acInfo.getName());
                    acInfoJson.put("hostCityName", acInfo.getHostCityName());
                    acInfoJson.put("hostCountyName", acInfo.getHostCountyName());
                    acInfoJson.put("hostSchoolName", acInfo.getHostSchoolName());
                    acInfoJson.put("hostName", acInfo.getHostName());
                    acInfoJson.put("activityStatus", acInfo.getActivityStatus());
                    acArray.add(acInfoJson);
                }
                return acArray;
            } else {
                return acArray;
            }

        } catch (Exception e) {
            logger.error("根据教室id获取当天课程预告异常，教室id=" + roomId, e);
        }
        return acArray;
    }

    /**
     * 获取当前学期所有周及相应时间
     * @return 周开始时间及结束时间集合
     */
    @Override
    public JSONArray getTermWeeks() {
        JSONArray resultArry = new JSONArray();
        try {
            //查询当前学期的开始及结束时间
            Map<String, Object> term = this.getUseingTermInfo();
            if (term != null && !term.isEmpty()) {
                resultArry = WeekDateUtil.getTermWeek(startTerm, endTerm);
            }
        } catch (Exception e) {
            logger.error("获取学期周异常", e);
        }
        return resultArry;
    }

    /**
     * 获取每周的开始时间和结束时间
     *
     * @param startWeek 开始周次
     * @param endWeek   结束周次
     * @return 对应周次的开始时间及结束时间集合
     */
    public JSONArray getPerWeekTime(int startWeek, int endWeek) {
        JSONArray weekArray = new JSONArray();
        try {
            int nowWeek = WeekDateUtil.getWeeks(new Date());
            Calendar a = Calendar.getInstance();
            int year = a.get(Calendar.YEAR);
            for (int i = startWeek; i <= endWeek; i++) {
                JSONObject weekJson = new JSONObject();
                String startTime = DateTimeUtil.formatDate(WeekDateUtil.getDayOfWeek(year, i, "begin"));
                String endTime = DateTimeUtil.formatDate(WeekDateUtil.getDayOfWeek(year, i, "end"));
                weekJson.put("weekStartTime", startTime);
                weekJson.put("weekEndTime", endTime);
                if (nowWeek == i) {
                    weekJson.put("isThisWeek", true);
                } else {
                    weekJson.put("isThisWeek", false);
                }
                weekArray.add(weekJson);
            }
        } catch (Exception e) {
            logger.error("获取每周的开始时间和结束时间异常", e);
        }
        return weekArray;
    }

    /**
     * 根据地点参数及教室类型获取指定周内课程
     *
     * @param provinceId 省id(避免为空)
     * @param cityId     市id(不可为空)
     * @param countyId   区县id(不可为空)
     * @param schoolId   学校id(不可为空)
     * @param roomType   教室类型（hostRoom:主讲教室、acceptRoom：接收教室；allType：所有教室）
     * @param roomId     教室id(当教师类型为主讲或接收教室时，可传，null时默认全部该类型的教室)
     * @param startTime  周开始时间 格式：yyyy-MM-dd
     * @param endTime    周结束时间 格式：yyyy-MM-dd
     * @return
     */
    @Override
    public JSONArray getTimeTableByAreaId(String provinceId, String cityId, String countyId, String schoolId, String roomType, String roomId, String startTime, String endTime) {
        //获取学校地址id参数，省id-市id-区、县id-学校id
        String roomAddr = this.getSchoolAddrIdByArea(provinceId, cityId, countyId, schoolId);
        if (roomAddr == null) {
            return new JSONArray();
        } else {
            List<Map<String, Object>> scheduleList = this.getCurrentScheduleOfTime(startTime, endTime);
            if (scheduleList != null && scheduleList.size() > 0) {
                List<Map<String, Object>> acInfoList = new ArrayList<>();
                List<Map<String, Object>> acInfoList1 = new ArrayList<>();
                List<Map<String, Object>> acInfoList2 = new ArrayList<>();
                switch (roomType) {
                    case "hostRoom":
                        //根据主讲教室地点获取时间内所有课程名称
                        acInfoList1 = this.getActivityInfoByHostRoomAddr(startTime, endTime, roomAddr, roomId);
                        if (acInfoList1 != null && acInfoList1.size() > 0) {
                            acInfoList.addAll(acInfoList1);
                        }
                        break;
                    case "acceptRoom":
                        //根据接收教室地点获取时间内所有课程名称
                        acInfoList2 = this.getActivityInfoByAcceptRoomAddr(startTime, endTime, roomAddr, roomId);
                        if (acInfoList2 != null && acInfoList2.size() > 0) {
                            acInfoList.addAll(acInfoList2);
                        }
                        break;
                    case "allRoom":
                        //根据主讲教室地点获取时间内所有课程名称
                        acInfoList1 = this.getActivityInfoByHostRoomAddr(startTime, endTime, roomAddr, roomId);
                        if (acInfoList1 != null && acInfoList1.size() > 0) {
                            acInfoList.addAll(acInfoList1);
                        }
                        //根据接收教室地点获取时间内所有课程名称
                        acInfoList2 = this.getActivityInfoByAcceptRoomAddr(startTime, endTime, roomAddr, roomId);
                        if (acInfoList2 != null && acInfoList2.size() > 0) {
                            acInfoList.addAll(acInfoList2);
                        }
                        break;
                    default:
                        return null;
                }
                List<Map<String, Object>> classtimeList = this.findClastimeByShedule(startTime, scheduleList);
                if (classtimeList != null && classtimeList.size() > 0) {
                    classtimeList = this.getClassTimeActivity(classtimeList, acInfoList);
                }
                Map<String, Integer> sectionMap = this.getWeekActivitiesBySchedule(scheduleList);
                JSONArray array = this.shareAcForClasstime(sectionMap, classtimeList);
                return array;
            } else {
                return new JSONArray();
            }
        }
    }

    /**
     * 查找给定时间内的作息时间
     *
     * @return 作息时间集合
     */
    private List<Map<String, Object>> findClastimeByShedule(String startTime, List<Map<String, Object>> scheduleList) {
        List<Map<String, Object>> classtimeList = new ArrayList<>();
        Map<String, Object> schedule1 = scheduleList.get(0);
        if (scheduleList.size() == 1) {
            classtimeList = classtimeDao.selectClassTimeDuringDayByScheduleId((int) schedule1.get("scheduleId"), 1, 8);
        } else {
            //获取第一个策略使用天数
            int days = this.getDaysOfFirstShcedule(startTime);
            Map<String, Object> schedule2 = scheduleList.get(1);
            //获取策略的stag,如果stag为true,则为一周开始的第一个策略，否则为第二个
            boolean stag = (boolean) schedule1.get("isFirstSchedule");
            if (stag) {
                //获取第一个策略使用时间内的作息时间
                classtimeList = classtimeDao.selectClassTimeDuringDayByScheduleId((int) schedule1.get("scheduleId"), 1, days);
                //获取第二个策略使用时间内的作息时间
                List<Map<String, Object>> classTimeList2 = classtimeDao.selectClassTimeDuringDayByScheduleId((int) schedule2.get("scheduleId"), (days + 1), 7);
                classtimeList.addAll(classTimeList2);
            } else {
                classtimeList = classtimeDao.selectClassTimeDuringDayByScheduleId((int) schedule2.get("scheduleId"), 1, days);
                List<Map<String, Object>> classTimeList2 = classtimeDao.selectClassTimeDuringDayByScheduleId((int) schedule1.get("scheduleId"), (days + 1), 7);
                classtimeList.addAll(classTimeList2);
            }
        }
        return classtimeList;
    }

    /**
     * 获取第一个策略的使用天数
     *
     * @param startTime 周开始时间  格式：yyyy-MM-dd
     * @return 天数
     */
    private int getDaysOfFirstShcedule(String startTime) {
        String[] st1 = startTime.split("-");
        int startYear = Integer.valueOf(st1[0]);
        int startMonth = Integer.valueOf(st1[1]);
        String endDayOfStartMonth = DateTimeUtil.getLastDayOfMonth(startYear, startMonth) + " 00:00:00";
        startTime += " 00:00:00";
        int days = (int) WeekDateUtil.daysBetween(startTime, endDayOfStartMonth) + 1;
        return days;
    }

    /**
     * 根据使用的策略获取一天上午最大课节数及总课节数
     * 1.获取日期使用策略及策略使用天数。
     * 2.获取一天的最大课节数
     *
     * @param scheduleList 周时间内启用的策略集合
     * @return
     */
    private Map<String, Integer> getWeekActivitiesBySchedule(List<Map<String, Object>> scheduleList) {
        Map<String, Integer> map = new HashedMap();
        //获取一天总课节数及上午最大课节数
        //第一个策略上午最大课节数
        int maxAmSections = (int) scheduleList.get(0).get("am");
        //第一个策略下午最大课节数
        int maxPmOfSchedule = (int) scheduleList.get(0).get("pm");
        int totalSecitons = maxAmSections + maxPmOfSchedule;
        int intNum =2;
        if (scheduleList.size() == intNum) {
            //第二个策略上午最大课节数
            //第二个策略下午最大课节数
            int maxAmOfSecondSchedule = (int) scheduleList.get(1).get("am");
            int maxPmOfSecondSchedule = (int) scheduleList.get(1).get("pm");
            if (maxAmSections < maxAmOfSecondSchedule) {
                maxAmSections = maxAmOfSecondSchedule;
            }
            if (totalSecitons < (maxAmOfSecondSchedule + maxPmOfSecondSchedule)) {
                totalSecitons = maxAmOfSecondSchedule + maxPmOfSecondSchedule;
            }
        }
        map.put("maxAmSections", maxAmSections);
        map.put("totalSections", totalSecitons);
        return map;
    }

    /**
     * 获取周时间内所使用的所有策略
     *
     * @param startTime 周开始日期 格式：yyyy-MM-dd
     * @param endTime   周结束日期  格式：yyyy-MM-dd
     * @return 作息时间
     */
    private List<Map<String, Object>> getCurrentScheduleOfTime(String startTime, String endTime) {
        //存放周时间内存在的启用策略
        List<Map<String, Object>> scheduleList2 = new ArrayList<>();
        //查询所有正在使用的策略
        List<Map<String, Object>> scheduleList = scheduleDao.getScheduleByStatus(1);
        if (scheduleList != null && scheduleList.size() > 0) {
            int startMonth = Integer.valueOf((startTime.split("-"))[1]);
            int endMonth = Integer.valueOf((endTime.split("-"))[1]);
            if (startMonth > endMonth) {
                endMonth += startMonth;
            }
            //判断时间内是否有启用策略，并获得策略id;(时间可能存在跨策略及跨年)
            for (Map<String, Object> scheduleMap : scheduleList) {
                int schStartMonth = Integer.valueOf(scheduleMap.get("startTime").toString());
                int schEndMonth = Integer.valueOf(scheduleMap.get("endTime").toString());
                if (schStartMonth > schEndMonth) {
                    schEndMonth += schStartMonth;
                }
                boolean existed=startMonth <= schEndMonth && endMonth >= schStartMonth || (startMonth + 12) <= schEndMonth && (endMonth + 12) >= schStartMonth;
                if (existed) {
                    if (startMonth == schEndMonth) {
                        scheduleMap.put("isFirstSchedule", true);
                    } else {
                        scheduleMap.put("isFirstSchedule", false);
                    }
                    scheduleList2.add(scheduleMap);
                } else {
                    continue;
                }
            }
            return scheduleList2;
        } else {
            return null;
        }
    }

    /**
     * @param teacherId 教师id
     * @param startTime 周开始时间
     * @param endTime   周结束时间
     * @return
     */
    @Override
    public JSONArray getTimeTableByTeacherId(String teacherId, String startTime, String endTime) {
        //如果无开启的作息策略，则直接返回空
        List<Map<String, Object>> scheduleList = this.getCurrentScheduleOfTime(startTime, endTime);
        if (scheduleList != null && scheduleList.size() > 0) {
            if (teacherId == null) {
                return new JSONArray();
            } else {
                List<Map<String, Object>> acInfoList = new ArrayList<>();
                //根据教师id获取所有主讲课程
                List<Map<String, Object>> acInfoList1 = this.getActivityInfoByHostTeacherId(startTime, endTime, teacherId);
                if (acInfoList1 != null && acInfoList1.size() > 0) {
                    acInfoList.addAll(acInfoList1);
                }
                //根据教师id获取所有辅助课程
                List<Map<String, Object>> acInfoList2 = this.getActivityInfoByAssistTeacherId(startTime, endTime, teacherId);
                if (acInfoList2 != null && acInfoList2.size() > 0) {
                    acInfoList.addAll(acInfoList2);
                }
                //查询周内作息时间
                List<Map<String, Object>> classtimeList = this.findClastimeByShedule(startTime, scheduleList);
                if (classtimeList != null && classtimeList.size() > 0) {
                    //查询课节内课程
                    classtimeList = this.getClassTimeActivity(classtimeList, acInfoList);
                    //查询策略内最大上午课节数及最大总课节数
                    Map<String, Integer> sectionMap = this.getWeekActivitiesBySchedule(scheduleList);
                    JSONArray array = this.shareAcForClasstime(sectionMap, classtimeList);
                    return array;
                } else {
                    return new JSONArray();
                }
            }
        } else {
            return new JSONArray();
        }
    }

    /**
     * 为课节分配课程
     *
     * @param classTimeList 课节
     * @param acList        活动集合
     * @return 课节课程
     */
    private List<Map<String, Object>> getClassTimeActivity(List<Map<String, Object>> classTimeList, List<Map<String, Object>> acList) {
        for (Map<String, Object> obj : classTimeList) {
            JSONArray acInfoArray = new JSONArray();
            int classtimeId = (int) obj.get("id");
            if (acList != null && !acList.isEmpty()) {
                for (Map<String, Object> acInfo : acList) {
                    if (acInfo.get("classtimeId") == null) {
                        continue;
                    } else {
                        int sectionId = Integer.valueOf(acInfo.get("classtimeId").toString());
                        if (sectionId == classtimeId) {
                            JSONObject acInfoJson = JSONObject.fromObject(acInfo);
                            acInfoArray.add(acInfoJson);
                        } else {
                            continue;
                        }
                    }
                }
            }
            obj.put("activityList", acInfoArray);
        }
        return classTimeList;
    }

    /**
     * 包装课节课程
     *
     * @param sectionMap    存放上午最大课节数及一天最大总课节数
     * @param classTimeList 课节课程集合
     * @return
     */
    public JSONArray shareAcForClasstime(Map<String, Integer> sectionMap, List<Map<String, Object>> classTimeList) {
        JSONArray resultArray = new JSONArray();
        for (int i = 1; i <= sectionMap.get("totalSections"); i++) {
            int type = 2;
            int section = i;
            int maxAm = sectionMap.get("maxAmSections");
            if (i > maxAm) {
                type++;
                section = i - maxAm;
            }
            JSONObject sectionJson = new JSONObject();
            String sectionName = "第" + this.toChinese(String.valueOf(i)) + "节";
            sectionJson.put("name", sectionName);
            JSONArray sectionArray = new JSONArray();
            int week = 7;
            for (int k = 1; k <= week; k++) {
                boolean hasSchedule = false;
                //分配课程
                for (int j = 0; j < classTimeList.size(); j++) {
                    Map<String, Object> map = classTimeList.get(j);
                    if (map != null && map.size() > 0) {
                        //确定课节
                        if ((int) map.get("type") == type && (int) map.get("section") == section && (int) map.get("week") == k) {
                            hasSchedule = true;
                            //添加课节信息
                            JSONObject dayJson = new JSONObject();
                            dayJson.put("id", map.get("id"));
                            dayJson.put("startTime", map.get("startTime"));
                            dayJson.put("endTime", map.get("endTime"));
                            List acList = (List) map.get("activityList");
                            if (acList != null && acList.size() > 0) {
                                dayJson.put("activityList", acList);
                            } else {
                                dayJson.put("activityList", new ArrayList<>());
                            }
                            sectionArray.add(dayJson);
                            classTimeList.remove(j);
                            j--;
                        } else {
                            continue;
                        }
                    } else {
                        JSONObject dayJson = new JSONObject();
                        dayJson.put("activityList", new ArrayList<>());
                        sectionArray.add(dayJson);
                    }
                }
                if (!hasSchedule) {
                    JSONObject dayJson = new JSONObject();
                    dayJson.put("activityList", new ArrayList<>());
                    sectionArray.add(dayJson);
                } else {
                    continue;
                }
            }
            sectionJson.put("section", sectionArray);
            resultArray.add(sectionJson);
        }
        return resultArray;
    }

    /**
     * 根据主讲教室地点获取时间内所有课程名称
     *
     * @param startTime 开始时间 时间格式：yyyy-MM-dd
     * @param endTime   结束时间 时间格式：yyyy-MM-dd
     * @param roomAddr  教室地址
     * @param roomId    教室id
     * @return 活动信息
     */
    private List<Map<String, Object>> getActivityInfoByHostRoomAddr(String startTime, String endTime, String roomAddr, String roomId) {
        List<Map<String, Object>> acMapList = new ArrayList<>();
        try {
            Map params = new HashMap();
            params.put("startTime", (startTime + " 00:00:00"));
            params.put("endTime", (endTime + " 23:59:59"));
            params.put("roomAddr", roomAddr);
            if (roomId != null && !"".equals(roomId)) {
                params.put("roomId", roomId);
            }
            long nowDateTime = System.currentTimeMillis();
            List<ActivityInfoForm> acInfoList = activityDao.getActivityNameByRoomAddr(params);
            if (acInfoList != null && acInfoList.size() > 0) {
                for (ActivityInfoForm acInfo : acInfoList) {
                    Map<String, Object> acMap = new HashMap<>();
                    acMap.put("activityId", acInfo.getId());
                    acMap.put("activityName", acInfo.getName());
                    acMap.put("classtimeId", acInfo.getSectionId());
                    long startDateTime = DateTimeUtil.parseDateTime(acInfo.getStartTime()).getTime();
                    long endDateTime = DateTimeUtil.parseDateTime(acInfo.getEndTime()).getTime();
                    if (endDateTime <= nowDateTime) {
                        acMap.put("status", ConstantWord.AC_END);
                    } else if (startDateTime > nowDateTime) {
                        acMap.put("status", ConstantWord.AC_WAIT);
                    } else {
                        acMap.put("status", ConstantWord.AC_SHOW);
                    }
                    //主讲课程标志
                    acMap.put("isHost", true);
                    acMapList.add(acMap);
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("根据主讲教室地点获取时间内所有课程名称", e);
        }
        return acMapList;
    }

    private String toChinese(String string) {
        String[] s1 = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String[] s2 = {"十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千"};
        String result = "";
        int n = string.length();
        for (int i = 0; i < n; i++) {

            int num = string.charAt(i) - '0';

            if (i != n - 1 && num != 0) {
                result += s1[num] + s2[n - 2 - i];
            } else {
                result += s1[num];
            }
        }
        String strZero ="零";
        String strEleven ="十一";
        if (result.endsWith(strZero)) {
            result = result.substring(0, result.length() - 1);
        }
        if (result.startsWith(strEleven)) {
            result = result.substring(1);
        }
        return result;

    }

    /**
     * 根据接收教室地点获取时间内所有课程名称
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param roomAddr  教室地址
     * @param roomId    教室id
     * @return 活动信息
     */
    private List<Map<String, Object>> getActivityInfoByAcceptRoomAddr(String startTime, String endTime, String roomAddr, String roomId) {
        List<Map<String, Object>> acMapList = new ArrayList<>();

        try {
            Map params = new HashMap();
            params.put("startTime", (startTime + " 00:00:00"));
            params.put("endTime", (endTime + " 23:59:59"));
            params.put("roomAddr", roomAddr);
            if (roomId != null && !"".equals(roomId)) {
                params.put("roomId", roomId);
            }
            List<ActivityInfoForm> acInfoList = assteachActivityDao.selectActivityByAssRoomAddr(params);
            if (acInfoList != null && acInfoList.size() > 0) {
                long nowDateTime = System.currentTimeMillis();
                for (ActivityInfoForm acInfo : acInfoList) {
                    Map<String, Object> acMap = new HashMap<>();
                    acMap.put("activityId", acInfo.getId());
                    acMap.put("activityName", acInfo.getName());
                    acMap.put("classtimeId", acInfo.getSectionId());
                    long startDateTime = DateTimeUtil.parseDateTime(acInfo.getStartTime()).getTime();
                    long endDateTime = DateTimeUtil.parseDateTime(acInfo.getEndTime()).getTime();

                    if (endDateTime <= nowDateTime) {
                        acMap.put("status", -1);
                    } else if (startDateTime > nowDateTime) {
                        acMap.put("status", 1);
                    } else {
                        acMap.put("status", 0);
                    }
                    acMap.put("isHost", false);
                    acMapList.add(acMap);
                }
            } else {
                return null;
            }

        } catch (Exception e) {
            logger.error("根据接收教室地点获取时间内所有课程名称异常", e);
        }
        return acMapList;
    }

    /**
     * 根据主讲教师id获取时间内所有课程名称
     *
     * @param startTime 开始时间 格式：yyyy-MM-dd
     * @param endTime   结束时间 格式：yyyy-MM-dd
     * @param teacherId 主讲教师id
     * @return 活动信息
     */
    private List<Map<String, Object>> getActivityInfoByHostTeacherId(String startTime, String endTime, String teacherId) {
        List<Map<String, Object>> acMapList = new ArrayList<>();
        try {
            Map params = new HashMap();
            params.put("startTime", (startTime + " 00:00:00"));
            params.put("endTime", (endTime + " 23:59:59"));
            params.put("teacherId", teacherId);
            List<ActivityInfoForm> acInfoList = activityDao.getActivityNameByTeacherId(params);
            if (acInfoList != null && acInfoList.size() > 0) {
                long nowDateTime = System.currentTimeMillis();
                for (ActivityInfoForm acInfo : acInfoList) {
                    Map<String, Object> acMap = new HashMap<>();
                    acMap.put("activityId", acInfo.getId());
                    acMap.put("activityName", acInfo.getName());
                    acMap.put("classtimeId", acInfo.getSectionId());
                    long startDateTime = DateTimeUtil.parseDateTime(acInfo.getStartTime()).getTime();
                    long endDateTime = DateTimeUtil.parseDateTime(acInfo.getEndTime()).getTime();
                    if (endDateTime <= nowDateTime) {
                        acMap.put("status", -1);
                    } else if (startDateTime > nowDateTime) {
                        acMap.put("status", 1);
                    } else {
                        acMap.put("status", 0);
                    }
                    acMap.put("isHost", true);
                    acMapList.add(acMap);
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("根据主讲教师id获取时间内所有课程名称异常", e);
        }
        return acMapList;
    }

    /**
     * 根据辅助教师id获取时间内所有课程名称
     *
     * @param startTime 开始时间 格式：yyyy-MM-dd
     * @param endTime   结束时间 格式：yyyy-MM-dd
     * @param teacherId 辅助教师id
     * @return 活动信息
     */
    private List<Map<String, Object>> getActivityInfoByAssistTeacherId(String startTime, String endTime, String teacherId) {
        List<Map<String, Object>> acMapList = new ArrayList<>();
        try {
            Map params = new HashMap();
            params.put("startTime", (startTime + " 00:00:00"));
            params.put("endTime", (endTime + " 23:59:59"));
            params.put("teacherId", teacherId);
            List<ActivityInfoForm> acInfoList = assteachActivityDao.selectActivityByAssTeacherId(params);
            if (acInfoList != null && acInfoList.size() > 0) {
                long nowDateTime = System.currentTimeMillis();
                for (ActivityInfoForm acInfo : acInfoList) {
                    Map<String, Object> acMap = new HashMap<>();
                    acMap.put("activityId", acInfo.getId());
                    acMap.put("activityName", acInfo.getName());
                    acMap.put("classtimeId", acInfo.getSectionId());
                    long startDateTime = DateTimeUtil.parseDateTime(acInfo.getStartTime()).getTime();
                    long endDateTime = DateTimeUtil.parseDateTime(acInfo.getEndTime()).getTime();
                    if (endDateTime <= nowDateTime) {
                        acMap.put("status", -1);
                    } else if (startDateTime > nowDateTime) {
                        acMap.put("status", 1);
                    } else {
                        acMap.put("status", 0);
                    }
                    acMap.put("isHost", false);
                    acMapList.add(acMap);
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("根据辅助教师id获取时间内所有课程名称异常", e);
        }
        return acMapList;
    }

    /**
     * 根据地点id查询省、市、区、学校名称
     *
     * @param areaId 地点id
     * @return map     地点名称
     * @author caoqian
     */
    @Override
    public Map<String, String> getAreaNameMap(String areaId) {
        Map<String, String> areaMap = new HashMap<>();
        try {
            if (areaId != null && !"".equals(areaId)) {
                Area area = areaDao.getAreaMessage(areaId);
                areaMap.put("provinceName", area.getProvince());
                areaMap.put("cityName", area.getCity());
                areaMap.put("countyName", area.getCounty());
                areaMap.put("schoolName", area.getSchool());
            } else {
                areaMap.put("provinceName", "");
                areaMap.put("cityName", "");
                areaMap.put("countyName", "");
                areaMap.put("schoolName", "");
            }
        } catch (Exception e) {
            logger.error("根据地点id查询省、市、区、学校名称异常,areaId" + areaId, e);
        }
        return areaMap;
    }

    /**
     * 检查地点信息，获取学校地址id串
     *
     * @param provinceId 省id
     * @param cityId     市id
     * @param countyId   区、县id
     * @param schoolId   学校id
     * @return 省id-市id-区、县id-学校id（该地点串用于数据库like查询）
     */
    private String getSchoolAddrIdByArea(String provinceId, String cityId, String countyId, String schoolId) {
        String areaAddrIds = "";
        if (provinceId != null && !"".equals(provinceId)) {
            areaAddrIds += provinceId;
        } else {
            areaAddrIds += "%";
        }
        if (cityId != null && !"".equals(cityId)) {
            areaAddrIds += "-" + cityId;
        } else {
            areaAddrIds += "-%";
        }
        if (countyId != null && !"".equals(countyId)) {
            areaAddrIds += "-" + countyId;
        } else {
            areaAddrIds += "-%";
        }
        if (schoolId != null && !"".equals(schoolId)) {
            areaAddrIds += "-" + schoolId;
        } else {
            areaAddrIds += "-%";
        }
        if ("".equals(areaAddrIds)) {
            return null;
        } else {
            logger.info("学校地址信息为：" + areaAddrIds);
        }
        return areaAddrIds;
    }

    /**
     * 检查参数
     *
     * @param method   方法名
     * @param starTime 时间参数-开始时间
     * @param endTime  时间参数-结束时间
     * @param cityId   城市id
     * @return 上述参数是否都不为空
     */
    private boolean checkParam(String method, String starTime, String endTime, String cityId) {
        if (starTime == null || "".equals(starTime)) {
            logger.error(method + ":获取开始时间异常");
            return false;
        }
        if (endTime == null || "".equals(endTime)) {
            logger.error(method + ":获取结束时间异常");
            return false;
        }
        if (cityId == null || "".equals(cityId)) {
            logger.error(method + ":获取市地点异常");
            return false;
        }
        return true;
    }

    /**
     * 根据条件查询时间内地点下有活动的主讲教室总数
     *
     * @param startTime    开始时间
     * @param endTime      结束时间
     * @param roomAddr     省id
     * @param activityName 活动名称
     * @return 教学活动详细信息集合
     */
    private int getHostRoomTotalNum(String startTime, String endTime, String roomAddr, String activityName) {
        int num = 0;
        Map params = new HashMap();
        try {
            params.put("startTime", startTime);
            params.put("endTime", endTime);
            params.put("roomAddr", roomAddr);
            if (activityName != null && !"".equals(activityName)) {
                params.put("activityName", ("%" + activityName + "%"));
            }
            num = activityDao.selectRoomTotalNum(params);
        } catch (Exception e) {
            logger.error("根据条件查询时间内地点下所有有活动的主讲教室总数异常", e);
        }
        return num;
    }


    /**
     * 检查地点类型
     *
     * @param provinceId 省份id
     * @param cityId     市id
     * @param countyId   县、区id
     * @param schoolId   学校id
     * @param roomId     教室id
     * @return map(areaId : 地点id ; areaType : 地点类型 ;)
     */
    private Map<String, String> checkAreaType(String provinceId, String cityId, String countyId, String schoolId, String roomId) {
        String areaType = "";
        String areaId = "";
        if (roomId != null && "".equals(roomId)) {
            areaType = "room";
            areaId = roomId;
        } else if (schoolId != null && "".equals(schoolId)) {
            areaType = "school";
            areaId = schoolId;
        } else if (countyId != null && "".equals(countyId)) {
            areaId = countyId;
            areaType = "county";
        } else if (cityId != null && "".equals(cityId)) {
            areaId = cityId;
            areaType = "city";
        } else if (provinceId != null && "".equals(provinceId)) {
            areaId = provinceId;
            areaType = "province";
        } else {
            return null;
        }
        Map map = new HashMap();
        map.put("areaId", areaId);
        map.put("areaType", areaType);
        return map;
    }

    /**
     * 获取课程的课时情况
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param acInfo    活动
     * @param acInfo    活动信息
     * @return
     */
    private ActivityInfoForm getActivityHourCase(String startTime, String endTime, ActivityInfoForm acInfo) {
        Map params = new HashMap();
        try {
            params.put("startTime", startTime);
            params.put("endTime", endTime);
            params.put("name", acInfo.getName());
            params.put("hostId", acInfo.getHostId());
            params.put("gradeId", acInfo.getGradeId());
            params.put("subjectId", acInfo.getSubjectId());
            params.put("roomId", acInfo.getHostRoomId());
            //根据参数查询相同课程数
            List<ActivityInfoForm> actInfoList = activityDao.selectActivityInfoByParam(params);
            Map assMap = new HashMap();
            //是否拥有接收教室
            boolean hasAcceptRoom = true;
            if (acInfo.getUuid() != null) {
                //根据uuid获取教室及对应教师的id串
                assMap = assteachActivityDao.selectAssRoomTeacherIdsByUuid(acInfo.getUuid());
                if (assMap == null || assMap.isEmpty()) {
                    hasAcceptRoom = false;
                } else {
                    hasAcceptRoom = true;
                }
            } else {
                hasAcceptRoom = false;
            }
            //相同课程数
            int sameActivityNum = 0;
            //本周内课程数
            int weekActivitysNum = 0;
            //已完成课程数
            int doneActivitysNum = 0;
            //正在直播课程
            List<ActivityInfoForm> doingActivityList = new ArrayList();
            //未完成直播课程
            List<ActivityInfoForm> undoneActivityList = new ArrayList();
            //比较其与活动的接收教室及辅助教师是否一致
            for (ActivityInfoForm ac : actInfoList) {
                if (ac.getUuid() != null) {
                    Map otherAssMap = assteachActivityDao.selectAssRoomTeacherIdsByUuid(ac.getUuid());
                    if (otherAssMap == null || otherAssMap.isEmpty()) {
                        //如果活动没有接收教室，则如果出现没有其它拥有接收教室的活动，则相同课程数+1
                        if (!hasAcceptRoom) {
                            sameActivityNum++;
                        } else {
                            continue;
                        }
                    } else {
                        if (assMap.get("roomIds").equals(otherAssMap.get("roomIds")) && assMap.get("teacherIds").equals(otherAssMap.get("teacherIds"))) {
                            sameActivityNum++;
                        } else {
                            continue;
                        }
                    }
                } else {
                    //如果活动没有接收教室，则如果出现没有其它拥有接收教室的活动，则相同课程数+1
                    if (!hasAcceptRoom) {
                        sameActivityNum++;
                    } else {
                        continue;
                    }
                }
                //检测本周内是否存在该活动信息
                ActivityInfoForm acInfoForm = activityDao.selectAcInfoDuringWeekByAcId(ac.getId());
                if (acInfoForm != null) {
                    weekActivitysNum++;
                }
                long nowDate = System.currentTimeMillis();
                Date date = DateTimeUtil.parseDateTime(ac.getEndTime());
                if (date.getTime() <= nowDate) {
                    doneActivitysNum++;
                } else {
                    Date startDate = DateTimeUtil.parseDateTime(ac.getStartTime());
                    if (startDate.getTime() < nowDate && date.getTime() > nowDate) {
                        doingActivityList.add(ac);
                        acInfo.setActivityStatus("正在直播");
                    } else {
                        undoneActivityList.add(ac);
                    }
                }
            }
            //放入时间内总课时数
            acInfo.setTotalPlanHour(sameActivityNum);
            //放入周内课程总数
            acInfo.setWeekTotalHour(weekActivitysNum);
            //放入时间内已完成课时数
            acInfo.setDoneTotalHour(doneActivitysNum);
            //如果不存在等待或正在直播的节目,那么直播状态返回结束，如果有直播的则返回正在直播，并将直播id及直播地址返回，否则返回等待直播
            if (undoneActivityList.isEmpty() && doingActivityList.isEmpty()) {
                acInfo.setActivityStatus("已结束");
            } else {
                String playStatus="正在直播";
                if (acInfo.getActivityStatus() != null && playStatus.equals(acInfo.getActivityStatus())) {
                    LiveStream liveStream = liveStreamDao.findLiveStreamByActivityId(doingActivityList.get(0).getId());
                    acInfo.setLiveStreamId(liveStream.getId());
                    acInfo.setLiveStreamAddr(liveStream.getStreamAddr());
                } else {
                    acInfo.setActivityStatus("等待直播");
                }
            }
        } catch (Exception e) {
            logger.error("根据课程获取时间内计划课程数、本周课程数和实际课时异常", e);
        }
        return acInfo;
    }

    /**
     * //根据地点获取时间内每天的课程数
     *
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @param provinceId 省id
     * @param cityId     城市id
     * @param countyId   区/县id
     * @param schoolId   学校id
     * @return 每天课程数
     */
    @Override
    public JSONObject getMonthTimetable(String startTime, String endTime, String provinceId, String cityId, String countyId, String schoolId) {
        //根据地点获取学校地址id串，省id-市id-区县id-学校id
        String roomAddr = getSchoolAddrIdByArea(provinceId, cityId, countyId, schoolId);
        if (roomAddr != null && !"".equals(roomAddr)) {
            return this.getDayCourseNum(roomAddr, startTime, endTime);
        } else {
            return null;
        }
    }

    /**
     * 根据地点查询某月内第N天的课程数
     *
     * @param roomAddr
     * @param startTime 格式 xxxx-MM-dd
     * @param endTime   格式 xxxx-MM-dd
     * @return
     */
    public JSONObject getDayCourseNum(String roomAddr, String startTime, String endTime) {
        JSONObject resultObjet = new JSONObject();
        try {
            Map params = new HashMap();
            params.put("startTime", (startTime + " 00:00:00"));
            params.put("endTime", (endTime + " 23:59:59"));
            params.put("roomAddr", roomAddr);
            List<Map<String, Object>> dayList = activityDao.selectDayActivityNumByTime(params);
            if (dayList != null && dayList.size() > 0) {
                for (Map<String, Object> map : dayList) {
                    String dayNum = map.get("dayNum").toString();
                    if (dayNum.length() < 2) {
                        dayNum = 0 + dayNum;
                    }
                    resultObjet.put(dayNum, map.get("acitivityNum"));
                }
                return resultObjet;
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("根据地点id获取月中某段时间内每天课程总数异常", e);
            return null;
        }
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
    @Override
    public JSONObject getActivityBriefInfo(String dateTime, String provinceId, String cityId, String countyId, String schoolId) {
        JSONObject activitysJson = new JSONObject();
        //根据地点获取学校地址id串，省id-市id-区县id-学校id
        String roomAddr = getSchoolAddrIdByArea(provinceId, cityId, countyId, schoolId);
        Map dayMap = new HashMap();
        if (roomAddr != null && !"".equals(roomAddr)) {
            //查询地点下某天的所有课程信息
            Map params = new HashMap();
            try {
                params.put("dateTime", dateTime);
                params.put("roomAddr", roomAddr);
                //根据教室id获取查询某天的所有课程信息
                List<ActivityInfoForm> acInfoList = activityDao.selectActivityByRoomIdAndTime(params);
                if (acInfoList == null || acInfoList.isEmpty()) {
                    return null;
                } else {
                    //存放活动信息
                    JSONArray acArray = new JSONArray();
                    for (ActivityInfoForm acInfo : acInfoList) {
                        JSONObject acInfoJson = new JSONObject();
                        acInfoJson.put("activityStartTime", DateTimeUtil.formatTime_NoSecond(DateTimeUtil.parseDateTime(acInfo.getStartTime())));
                        acInfoJson.put("activityEndTime", DateTimeUtil.formatTime_NoSecond(DateTimeUtil.parseDateTime(acInfo.getEndTime())));
                        acInfoJson.put("activityName", acInfo.getName());
                        acInfoJson.put("hostCityName", acInfo.getHostCityName());
                        acInfoJson.put("hostCountyName", acInfo.getHostCountyName());
                        acInfoJson.put("hostSchoolName", acInfo.getHostSchoolName());
                        acInfoJson.put("hostName", acInfo.getHostName());
                        acArray.add(acInfoJson);
                    }
                    activitysJson.put("activityInfo", acArray);
                    activitysJson.put("activityNum", acArray.size());
                }
                return activitysJson;
            } catch (Exception e) {
                logger.error("根据地点获取日期下当天所有课程的简略信息", e);
            }
            return null;
        } else {
            return null;
        }
    }

    /**
     * 根据时间和地点分页查询活动信息
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
     * @return added by xinqinggang 2018/01/31
     */
    @Override
    public JSONObject getActivityInfoByPage(String startTime, String endTime, String provinceId, String cityId, String countyId, String schoolId, String activityName, String currentPage, String pageSize) {
        //根据地点获取学校地址id串，省id-市id-区县id-学校id
        String roomAddr = this.getSchoolAddrIdByArea(provinceId, cityId, countyId, schoolId);
        this.updateAcStatus(startTime, endTime, roomAddr);
        //查询地点下的详细活动信息
        List<ActivityInfoForm> activityInfoList = this.getActivityInfoListByArea(startTime, endTime, roomAddr, activityName);
        if (activityInfoList == null || activityInfoList.isEmpty()) {
            return null;
        } else {
            JSONObject resultObject = new JSONObject();
            int hostRoomNum = this.getHostRoomTotalNum(startTime, endTime, roomAddr, activityName);
            //存放主讲教室总数
            resultObject.put("totalCount", hostRoomNum);
            //存放主讲教室总数
            resultObject.put("currentPage", currentPage);
            //存放地点数据
            List<Integer> roomIdList = new ArrayList();
            //存放分页筛选后的活动集合
            List<ActivityInfoForm> acInfoList = new ArrayList<>();
            List uuidList = new ArrayList();
            int beginNum = (Integer.valueOf(currentPage) - 1) * Integer.valueOf(pageSize);
            //根据页数和条数对当前活动信息按主讲教室地点进行分页
            this.findAcInfoByPage(activityInfoList, acInfoList, roomIdList, uuidList, beginNum, Integer.valueOf(pageSize));
            JSONArray acInfoArray = new JSONArray();
            //如果分页后活动信息集合不为空，那么查询接收教室信息
            if (!acInfoList.isEmpty() && !uuidList.isEmpty()) {
                //获取教学活动的详细信息
                acInfoArray = this.getAcInfoList(acInfoList, uuidList);
                acInfoArray = this.orderAcByStatus(acInfoArray);
            } else {
                return null;
            }
            //按格式包装到结果集
            JSONArray dataJsonArray = new JSONArray();
            for (int i = 0; i < roomIdList.size(); i++) {
                //存放地点信息
                JSONObject areaJson = new JSONObject();
                //存放活动信息
                JSONArray acJsonArray = new JSONArray();
                for (int j = 0; j < acInfoArray.size(); j++) {
                    JSONObject acInfoJson = JSONObject.fromObject(acInfoArray.get(j));
                    //如果地点id与活动中主讲教室地点id相同，则将活动信息置于该地点下
                    if (roomIdList.get(i).equals(acInfoJson.get("hostRoomId"))) {
                        //如果主讲教室地点信息不全，则用活动中主讲教室信息补全
                        if (!areaJson.containsKey("hostRoomId")) {
                            if (acInfoJson.containsKey("areaAddr") && !"".equals(acInfoJson.get("areaAddr"))) {
                                String[] areaArr = acInfoJson.get("areaAddr").toString().split("-");
                                areaJson.put("hostCityId", areaArr[1]);
                                areaJson.put("hostCountyId", areaArr[2]);
                                areaJson.put("hostSchoolId", areaArr[3]);
                            }
                            areaJson.put("hostCityName", acInfoJson.get("hostCityName"));
                            areaJson.put("hostCountyName", acInfoJson.get("hostCountyName"));
                            areaJson.put("hostSchoolName", acInfoJson.get("hostSchoolName"));
                            areaJson.put("hostRoomName", acInfoJson.get("hostRoomName"));
                            areaJson.put("hostRoomId", acInfoJson.get("hostRoomId"));
                            areaJson.put("roomStudents", acInfoJson.get("roomStudents"));
                        }
                        acJsonArray.add(acInfoJson);
                        acInfoArray.remove(acInfoArray.get(j));
                        j--;
                    }
                    continue;
                }
                if (acJsonArray != null && acJsonArray.size() > 0) {
                    areaJson.put("activityList", acJsonArray);
                    dataJsonArray.add(areaJson);
                } else {
                    continue;
                }
            }
            resultObject.put("dataList", dataJsonArray);
            return resultObject;
        }
    }

    /**
     * 根据活动状态进行排序
     */
    public JSONArray orderAcByStatus(JSONArray acInfoArray) {
        try {
            JSONArray resultArryay = new JSONArray();
            JSONArray waitAcArray = new JSONArray();
            JSONArray endAcArray = new JSONArray();
            for (int i = 0; i < acInfoArray.size(); i++) {
                JSONObject acJson = JSONObject.fromObject(acInfoArray.get(i));
                int statusStag = (int) acJson.get("orderStag");
                switch (statusStag) {
                    case 2:
                        resultArryay.add(acJson);
                        break;
                    case 1:
                        waitAcArray.add(acJson);
                        break;
                    default:
                        endAcArray.add(acJson);
                }
            }
            resultArryay.addAll(waitAcArray);
            resultArryay.addAll(endAcArray);
            return resultArryay;
        } catch (NumberFormatException e) {
            logger.error("对活动进行排序依次", e);
            return acInfoArray;
        }
    }

    /**
     * 将活动信息按主讲教室地点进行分页
     *
     * @param activityInfoList 所有活动信息
     * @param acInfoList       按教室分页后的所有活动信息
     * @param roomIdList       主讲教师集合
     * @param uuidList         uuid集合
     * @param beginNum         开始条数
     * @param pageSize         页面条数
     */
    public void findAcInfoByPage(List<ActivityInfoForm> activityInfoList, List<ActivityInfoForm> acInfoList, List<Integer> roomIdList, List<String> uuidList, int beginNum, int pageSize) {
        int flag = 0;
        List roomIdList2 = new ArrayList();
        for (int i = 0; i < activityInfoList.size(); i++) {
            ActivityInfoForm activityInfo = activityInfoList.get(i);
            if (!roomIdList2.contains(activityInfo.getHostRoomId())) {
                roomIdList2.add(activityInfo.getHostRoomId());
                flag++;
            }
            if (flag > beginNum) {
                acInfoList.add(activityInfo);
                uuidList.add(activityInfo.getUuid());
            }
            if (roomIdList.contains(activityInfo.getHostRoomId())) {
                continue;
            } else {
                if (flag > beginNum) {
                    roomIdList.add(activityInfo.getHostRoomId());
                }

            }
            if (roomIdList.size() <= pageSize) {
                continue;
            } else {
                roomIdList.remove(roomIdList.size() - 1);
                acInfoList.remove(acInfoList.size() - 1);
                uuidList.remove(uuidList.size() - 1);
                break;
            }
        }
    }

    /**
     * 根据地点类型获取地点地址id串
     *
     * @param areaId   地点id
     * @param areaType 地点类型
     * @return 子级地点地址id串集合
     * @childAreasMap 子级地点Map集合
     */
    private Map<String, String> getAreaAddrByType(String areaId, String areaType, Map<String, String> childAreasMap) {
        Map<String, String> childAreaAddrMap = new HashMap<>();
        switch (areaType) {
            case "province":
                for (String childAreaId : childAreasMap.keySet()) {
                    String areaAddr = this.getSchoolAddrIdByArea(areaId, childAreaId, "", "");
                    childAreaAddrMap.put(areaAddr, childAreaId);
                }
                break;
            case "city":
                for (String childAreaId : childAreasMap.keySet()) {
                    String areaAddr = this.getSchoolAddrIdByArea("", areaId, childAreaId, "");
                    childAreaAddrMap.put(areaAddr, childAreaId);
                }
                break;
            case "county":
                for (String childAreaId : childAreasMap.keySet()) {
                    String areaAddr = this.getSchoolAddrIdByArea("", "", areaId, childAreaId);
                    childAreaAddrMap.put(areaAddr, childAreaId);
                }
                break;
            default:
                return null;
        }
        return childAreaAddrMap;
    }

    /**
     * 获取子级地点节点类型
     *
     * @param areaType 地点类型
     * @return 节点类型
     */
    private String getChidAreaType(String areaType) {
        switch (areaType) {
            case "province":
                return "city";
            case "city":
                return "county";
            case "county":
                return "school";
            default:
                return null;
        }
    }

    /**
     * 获取目前系统时间所在的学期信息
     *
     * @return
     */
    public synchronized Map<String, Object> getUseingTermInfo() {
        try {
            Map<String, Object> term = termDao.findTermUsing();
            if (term != null && !term.isEmpty()) {
                startTerm = term.get("startDate").toString();
                endTerm = term.get("endDate").toString();
                startTermDate = DateTimeUtil.parseDateTime(startTerm);
                endTermDate = DateTimeUtil.parseDateTime(endTerm);
                return term;
            }
        } catch (Exception e) {
            logger.error("获取学期开始结束时间异常", e);
        }
        return null;
    }

    /**
     * 根据地点id和地点类型获取地点及子级地点课程情况
     *
     * @param areaId        地点id
     * @param areaType      地点类型
     * @param hasTermCourse 是否查询学期课程情况
     * @return 地点课程情况
     */
    @Override
    public JSONObject getAreaCourseCase(String areaId, String areaType, boolean hasTermCourse) {
        JSONObject areaObject = new JSONObject();
        //获取所有子级地点id及名称
        Map<String, String> childAreasMap = areaHttpService.getChildAreaByAreaId(areaId, areaType);
        if (childAreasMap == null || childAreasMap.isEmpty()) {
            return null;
        } else {
            if (hasTermCourse) {
                //初始化学期时间
                this.getUseingTermInfo();
            }
            areaObject.put("areaId", areaId);
            int hostCourseNum = 0, acceptCourseNum = 0, termPlanHour = 0, studentNum = 0, teacherNum = 0, weekDoneHour = 0, weekPlanHour = 0, weekTotalHour = 0;
            //根据地点及地点类型获取子级地点id串
            Map<String, String> childAreaAddrsMap = this.getAreaAddrByType(areaId, areaType, childAreasMap);
            JSONArray childAreaArray = new JSONArray();
            //获取子级地点类型
            String childAreaType = this.getChidAreaType(areaType);
            //遍历地点id串，查询每个地点课程情况
            for (String childAreaAddr : childAreaAddrsMap.keySet()) {
                JSONObject courseCaseJson = new JSONObject();
                String childAreaName = childAreasMap.get(childAreaAddrsMap.get(childAreaAddr)).replace(" ", "");
                //放入子级地点名称
                courseCaseJson.put("childAreaName", childAreaName);
                //放入子级地点id
                courseCaseJson.put("childAreaId", childAreaAddrsMap.get(childAreaAddr));
                //放入子级地点类型
                courseCaseJson.put("childAreaType", childAreaType);
                //获取地点的主讲课程数量
                Map<String, Integer> roomMap = this.getRoomTypeNum(childAreaAddrsMap.get(childAreaAddr), childAreaType);
                //获取主讲课程数
                courseCaseJson.put("hostCourseNum", roomMap.get("hostRoomCount"));
                int acceptNum = roomMap.get("roomCount") - roomMap.get("hostRoomCount");
                //获取接收课程数
                courseCaseJson.put("acceptCourseNum", acceptNum);
                hostCourseNum += roomMap.get("hostRoomCount");
                acceptCourseNum += acceptNum;
                //计算父级地点主讲课时数
                if (hasTermCourse) {
                    //如果hasTermCourse为ture.则查询学期及周课时情况
                    courseCaseJson = this.getRoomListCourseCase(courseCaseJson, childAreaAddr);
                    termPlanHour += (Integer) courseCaseJson.get("termPlanHour");
                    studentNum += (Integer) courseCaseJson.get("studentNum");
                    teacherNum += (Integer) courseCaseJson.get("teacherNum");
                    weekDoneHour += (Integer) courseCaseJson.get("weekDoneHour");
                    weekPlanHour += (Integer) courseCaseJson.get("weekPlanHour");
                    weekTotalHour += (Integer) courseCaseJson.get("weekTotalHour");
                }
                childAreaArray.add(courseCaseJson);
            }
            areaObject.put("hostCourseNum", hostCourseNum);
            areaObject.put("acceptCourseNum", acceptCourseNum);
            if (hasTermCourse) {
                areaObject.put("termPlanHour", termPlanHour);
                areaObject.put("studentNum", studentNum);
                areaObject.put("teacherNum", teacherNum);
                areaObject.put("weekDoneHour", weekDoneHour);
                areaObject.put("weekPlanHour", weekPlanHour);
                areaObject.put("weekTotalHour", weekTotalHour);
            }
            areaObject.put("childAreaList", childAreaArray);

            return areaObject;
        }
    }

    /**
     * 根据地点获取主讲教室及接收教室的数量
     *
     * @param areaId
     * @param areaType
     * @return
     */
    public Map<String, Integer> getRoomTypeNum(String areaId, String areaType) {
        Map<String, Integer> roomMap = null;
        switch (areaType) {
            case "city":
                roomMap = userHttpService.getRoomsByAreaIdAndType("", areaId, "", "");
                break;
            case "county":
                roomMap = userHttpService.getRoomsByAreaIdAndType("", "", areaId, "");
                break;
            case "school":
                roomMap = userHttpService.getRoomsByAreaIdAndType("", "", "", areaId);
                break;
            default:
                return null;
        }
        return roomMap;
    }

    /**
     * 根据地点查询课程情况
     *
     * @param roomJson
     * @param areaAddr 地点地址id串
     * @return
     */
    public JSONObject getRoomListCourseCase(JSONObject roomJson, String areaAddr) {
        //获取学期内所有计划课时(主讲课时)数
        int termTotalCourse = this.getHostCourseNum(areaAddr, startTerm, endTerm, "", "", "");
        //获取本周课时情况
        int weekTotalHour = this.getHostCourseNum(areaAddr, "", "", "weekTotalHour", "", "");
        //获取本周应授开始情况
        int weekDoneHour = this.getHostCourseNum(areaAddr, "", "", "weekTotalCourse", "", "weekDoneCourse");
        //获取本周已授课时
        int weekHasDoneHour = this.getHostCourseNum(areaAddr, "", "", "weekTotalCourse", "weekHasDoneCourse", "");
        //获取收益学生数
        int studentsNum = this.getStudentsNum(areaAddr, startTerm, endTerm);
        //获取主讲教师数
        int teachersNum = this.getTeachersNum(areaAddr, startTerm, endTerm);
        roomJson.put("termPlanHour", termTotalCourse);
        roomJson.put("teacherNum", teachersNum);
        roomJson.put("studentNum", studentsNum);
        roomJson.put("weekTotalHour", weekTotalHour);
        roomJson.put("weekDoneHour", weekHasDoneHour);
        roomJson.put("weekPlanHour", weekDoneHour);
        return roomJson;
    }

    /**
     * 获取课程数量
     *
     * @param areaAddr                                                              地点id串
     * @param startTermTime                                                         学期开始时间
     * @param endTermTime                                                           学期结束时间
     * @param ：weekTotalCourse:周总课时,weekHasDoneCourse:周已完成课时；weekDoneCourse：计划已完成课时
     * @return
     */
    private Integer getHostCourseNum(String areaAddr, String startTermTime, String endTermTime, String weekTotalCourse,
                                     String weekHasDoneCourse, String weekDoneCourse) {
        Map params = new HashMap();
        try {
            params.put("areaAddr", areaAddr);
            if (startTermTime != null && !"".equals(startTermTime)) {
                params.put("startTermTime", startTermTime);
                params.put("endTermTime", endTermTime);
            }
            if (weekTotalCourse != null && !"".equals(weekTotalCourse)) {
                params.put("weekTotalCourse", weekTotalCourse);
            }
            if (weekHasDoneCourse != null && !"".equals(weekHasDoneCourse)) {
                params.put("weekHasDoneCourse", weekHasDoneCourse);
            }
            if (weekDoneCourse != null && !"".equals(weekDoneCourse)) {
                params.put("weekDoneCourse", weekDoneCourse);
            }
            int courseNum = activityDao.getTotalHostCourseCase(params);
            return courseNum;
        } catch (Exception e) {
            logger.error("获取课程数量异常", e);
        }
        return null;
    }

    /**
     * 获取时间内收益学生数
     *
     * @param areaAddr  地点地址串
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    private Integer getStudentsNum(String areaAddr, String startTime, String endTime) {
        Map params = new HashMap();
        try {
            params.put("roomAddr", areaAddr);
            params.put("startTime", startTime);
            params.put("endTime", endTime);
            int studentsNum = activityDao.selectAllStudentsNum(params);
            return studentsNum;
        } catch (Exception e) {
            logger.error("获取收益学生数异常", e);
        }
        return null;
    }


    /**
     * 获取时间内主讲教师数量
     *
     * @param areaAddr 地点地址串
     * @return
     */
    private Integer getTeachersNum(String areaAddr, String startTime, String endTime) {
        Map params = new HashMap();
        try {
            params.put("roomAddr", areaAddr);
            params.put("startTime", startTime);
            params.put("endTime", endTime);
            int teachersNum = activityDao.selectHostTeacherNum(params);
            return teachersNum;
        } catch (Exception e) {
            logger.error("获取收益学生数异常", e);
        }
        return null;
    }

    /**
     * 根据活动id获取活动的详细信息
     *
     * @param id 活动id
     * @return 活动详细信息
     */
    @Override
    public ActivityInfoForm getActivityInfoById(int id) {
        List<ActivityInfoForm> acInfoList;
        Map params = new HashMap();
        try {
            params.put("id", id);
            acInfoList = activityDao.selectActivityInfoByParam(params);
            if (acInfoList != null && !acInfoList.isEmpty() && acInfoList.get(0).getUuid() != null) {
                List uuidList = new ArrayList();
                uuidList.add(acInfoList.get(0).getUuid());
                //主讲教室终端信息
                Map<String, String> masterHost = areaHttpService.getHostByRoomId(String.valueOf(acInfoList.get(0).getHostRoomId()));
                if (masterHost != null && !masterHost.isEmpty()) {
                    acInfoList.get(0).setLiveStreamAddr(teachingSupervisionService.getRtmpAddr(String.valueOf(masterHost.get("mcu_code"))));
                }
                //获取活动的详细信息
                return this.getActivityInfo(acInfoList.get(0), uuidList);
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("根据活动id查询活动详细信息异常", e);
        }
        return null;
    }

    /**
     * 获取教学活动接收教室信息并封装到活动中
     *
     * @param acInfo   教学活动
     * @param uuidList 活动与接收教室表唯一对应关系
     * @return 教学活动详情集合
     */
    private ActivityInfoForm getActivityInfo(ActivityInfoForm acInfo, List uuidList) {
        //根据教学活动的uuid获取其接收班级及对应的辅助教师信息
        List<AssteachActivityForm> assteachInfoList = assteachActivityDao.selectAssteachActivityByUuids(uuidList);
        if (assteachInfoList != null && assteachInfoList.size() > 0) {
            List<AssteachActivityForm> acceptList = new ArrayList<>();
            for (AssteachActivityForm assteachInfo : assteachInfoList) {
                acceptList.add(assteachInfo);
            }
            acInfo.setAssteachActivityList(acceptList);
        } else {
            acInfo.setAssteachActivityList(new ArrayList<AssteachActivityForm>());
        }
        return acInfo;
    }

    /**
     * 根据地址id串查询时间内教学活动信息
     *
     * @param startTime    开始时间
     * @param endTime      结束时间
     * @param areaIds      地点id串
     * @param activityName 活动名称
     * @return 教学活动详细信息集合
     */
    private List<ActivityInfoForm> getActivityInfoListByArea(String startTime, String endTime, String areaIds, String activityName) {
        List<ActivityInfoForm> acInfoList = new ArrayList<>();
        Map params = new HashMap();
        try {
            params.put("startTime", startTime);
            params.put("endTime", endTime);
            params.put("areaIds", areaIds);
            if (activityName != null && !"".equals(activityName)) {
                params.put("activityName", ("%" + activityName + "%"));
            }
            acInfoList = activityDao.selectAcInfosByRoomAddr(params);
        } catch (Exception e) {
            logger.error("根据地址id串查询时间内教学活动信息异常", e);
        }
        return acInfoList;
    }

    /**
     * 更改时间内活动状态
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param areaIds   地点id串
     */
    private int updateAcStatus(String startTime, String endTime, String areaIds) {
        List<ActivityInfoForm> acInfoList = new ArrayList<>();
        Map params = new HashMap();
        try {
            if (startTime != null && !"".equals(startTime)) {
                params.put("startTime", startTime);
            }
            if (endTime != null && !"".equals(endTime)) {
                params.put("endTime", endTime);
            }
            if (areaIds != null && !"".equals(areaIds)) {
                params.put("areaIds", areaIds);
            }
            int rowNum = activityDao.updateAcStatus(params);
            return rowNum;
        } catch (Exception e) {
            logger.error("更改时间内活动状态异常", e);
        }
        return 0;
    }

    private JSONArray getAcInfoList(List<ActivityInfoForm> acInfoList, List<String> uuidList) {
        JSONArray acInfoArray = new JSONArray();
        //根据教学活动的uuid获取其接收班级及对应的辅助教师信息
        List<AssteachActivityForm> assteachInfoList = assteachActivityDao.selectAssteachActivityByUuids(uuidList);
        Map<String, JSONObject> sameAcMap = new HashMap<>();
        Map<String,JSONObject> saveActivityCount=new HashMap<>();
        Calendar a = Calendar.getInstance();
        int year = a.get(Calendar.YEAR);
        int week = WeekDateUtil.getWeeks(new Date());
        String startTime ="";
        String endTime="";
        try {
            startTime = DateTimeUtil.formatDate(WeekDateUtil.getDayOfWeek(year, week, "begin"));
            endTime = DateTimeUtil.formatDate(WeekDateUtil.getDayOfWeek(year, week, "end"));
        }catch (Exception e){
            logger.error("时间转换异常。",e);
        }
        //遍历活动集合，获取活动详细信息并封装
        for (int i = 0; i < acInfoList.size(); i++) {
            ActivityInfoForm acInfo = acInfoList.get(i);
            String sameSt = acInfo.getName() + "-" + acInfo.getHostId() + "-" + acInfo.getHostRoomId() +
                    "-" + acInfo.getSubjectId() + "-" + acInfo.getGradeId();
            List acceptList = new ArrayList();
            for (int j = 0; j < assteachInfoList.size(); j++) {
                if (acInfo.getUuid().equals(assteachInfoList.get(j).getUuid())) {
                    AssteachActivityForm ass = assteachInfoList.get(j);
                    String roomAddr = ass.getAcceptRoomAddr();
                    String[] addr = roomAddr.split("-");
                    ass.setAcceptCityId(addr[1]);
                    ass.setAcceptCountyId(addr[2]);
                    ass.setAcceptSchoolId(addr[3]);
                    acceptList.add(assteachInfoList.get(j));
                    sameSt += ";" + assteachInfoList.get(j).getAcceptRoomId();
                    if (assteachInfoList.get(j).getAssistTeacherId() > 0) {
                        sameSt += "-" + assteachInfoList.get(j).getAssistTeacherId() + ";";
                    } else {
                        sameSt += ";";
                    }
                    assteachInfoList.remove(j);
                    j--;
                } else {
                    continue;
                }
            }

            acInfo.setAssteachActivityList(acceptList);
            if (!"".equals(acInfo.getAreaAddr())) {
                String[] areaArr = acInfo.getAreaAddr().toString().split("-");
                acInfo.setHostCityId(areaArr[1]);
                acInfo.setHostCountyId(areaArr[2]);
                acInfo.setHostSchoolId(areaArr[3]);
            }
            JSONObject acInfoJson = this.getAcInfoJson(acInfo);
            //相同课程数,本周内课程数,已完成课程数
            JSONObject countJson=new JSONObject();
            JSONObject getCountJson=saveActivityCount.get(sameSt);
            countJson.put("sameAcNum",getCountJson==null?"0":String.valueOf(getCountJson.get("sameAcNum")));
            countJson.put("doneAcNum",getCountJson==null?"0":String.valueOf(getCountJson.get("doneAcNum")));
            saveActivityCount.put(sameSt,countJson);
            this.getAcHours(acInfoJson,saveActivityCount, sameAcMap, sameSt);
        }

        getWeekTotalCount(sameAcMap,startTime,endTime);
        for (String st : sameAcMap.keySet()) {
            acInfoArray.add(sameAcMap.get(st));
        }
        return acInfoArray;
    }

    /**
     * 获取预约活动的周课时
     * @param sameAcMap
     */
    private Map<String, JSONObject> getWeekTotalCount(Map<String, JSONObject> sameAcMap,String startTime,String endTime) {
        if(sameAcMap!=null && !sameAcMap.isEmpty()){
            Iterator iterator=sameAcMap.keySet().iterator();
            while(iterator.hasNext()){
                String key=String.valueOf(iterator.next());
                if(!StringUtils.isEmpty(key)){
                    String[] keyArr=key.replace(";;",";").split(";");
                    String activityInfo=keyArr[0];
                    String assActivityInfo="";
                    for(int i=1;i<keyArr.length;i++){
                        assActivityInfo+=keyArr[i]+"@@@";
                    }
                    String[] assActivityInfoArr=assActivityInfo.split("@@@");
                    String weekTotalHour=activityDao.getWeekTotalHour(activityInfo,assActivityInfoArr,startTime+" 00:00:00",endTime+" 23:59:59");
                    sameAcMap.get(key).put("weekTotalHour",weekTotalHour);
                }
            }
        }
        return sameAcMap;
    }

    /**
     * 获取课程一段时间内的课时及完成情况和当前系统时间周内的课程情况（课时、周课时）
     *
     * @param acInfoJson 活动信息
     * @return
     */
    private JSONObject getAcHours(JSONObject acInfoJson, Map<String,JSONObject> countMap,Map sameAcMap, String sameSt) {
        int weekAcNum=0;
        int doneAcNum=0;
        //活动开始时间、结束时间
        long acStartTime = 0L, acEndTime = 0L;
        try {
            acStartTime = DateTimeUtil.parseDateTime(acInfoJson.get("startTime").toString()).getTime();
            acEndTime = DateTimeUtil.parseDateTime(acInfoJson.get("endTime").toString()).getTime();
        } catch (Exception e) {
            logger.error("获取课程课时情况时，转换时间异常", e);
        }
        if (sameAcMap.get(sameSt) != null && "2".equals(JSONObject.fromObject(sameAcMap.get(sameSt)).get("orderStag").toString())) {
            acInfoJson = JSONObject.fromObject(sameAcMap.get(sameSt));
        } else {
            if (acStartTime <= System.currentTimeMillis() && acEndTime > System.currentTimeMillis()) {
                acInfoJson.put("activityStatus", "直播中");
                acInfoJson.put("orderStag", 2);
            } else if (acStartTime > System.currentTimeMillis()) {
                acInfoJson.put("activityStatus", "等待直播");
                acInfoJson.put("orderStag", 1);
            } else {
                acInfoJson.put("activityStatus", "已结束");
                acInfoJson.put("orderStag", 0);
            }
        }
        JSONObject countJson=countMap.get(sameSt);
        int sameAcNum=Integer.parseInt(countJson.get("sameAcNum").toString()) + 1;
        countJson.put("sameAcNum",sameAcNum);
        acInfoJson.put("totalPlanHour", sameAcNum);
        if (acEndTime < System.currentTimeMillis()) {
            if (acInfoJson.get("doneTotalHour") != null) {
                doneAcNum =Integer.parseInt(countJson.get("doneAcNum").toString())+1;
            } else {
                doneAcNum++;
            }
            acInfoJson.put("doneTotalHour", doneAcNum);
            countJson.put("doneAcNum",doneAcNum);
        }else{
            acInfoJson.put("doneTotalHour", countJson.get("doneAcNum"));
        }
        sameAcMap.put(sameSt, acInfoJson);
        return acInfoJson;
    }

    private JSONObject getAcInfoJson(ActivityInfoForm acInfo) {
        JSONObject acInfoJson = JSONObject.fromObject(acInfo);
        return acInfoJson;
    }


    //-----------------------------------------------------------end----------------------------------------------------

    /**
     * 根据时间及主讲终端ip获取当天课程名称
     *
     * @param startTime 开始时间，如‘2018-01-01 00:00:00’
     * @param endTime   结束时间，如‘2018-01-01 23:59:59’
     * @param masterIp  主讲教室终端ip
     * @param id        终端编码
     * @return list
     * @author caoqian
     */
    @Override
    public List<Map<String, String>> getCourseTodayListByTimeAndMasterIp(String startTime, String endTime, String masterIp, String id) {
        List<Map<String, String>> courseList = new ArrayList<>();
        try {
            String roomId = "";
            if (id != null && !"".equals(id)) {
                roomId = areaHttpService.getRoomIdByHostCode(id);
            } else if (masterIp != null && !"".equals(masterIp)) {
                roomId = areaHttpService.getRoomIdByHostIp(masterIp);
            }

            courseList = activityDao.getCourseTodayListByTime(startTime, endTime, roomId);
            if (courseList != null && !courseList.isEmpty()) {
                for (Map<String, String> course : courseList) {
                    String name = course.get("name")+course.get("currentTimeStamp");
                    String newStartTime = course.get("start_time");
                    String newEndTime = course.get("end_time");
                    course.put("name", name + " " + newStartTime + "~" + newEndTime);
                }
            }
        } catch (Exception e) {
            logger.error("根据时间及主讲教室id获取当天所有课程列表信息异常," +
                    "startTime=" + startTime + ",endTime=" + endTime + ",masterIp=" + masterIp, e);
        }
        return courseList;
    }


    /**
     * 获取当前时间主讲课程
     *
     * @param time     当前时间，格式:"2018-01-01 10:00:00"
     * @param masterIp 主讲教室终端ip
     * @return list
     * @author caoqian
     */
    @Override
    public List<Map<String, String>> getLectCourseListByTimeAndMasterIp(String time, String masterIp, String id) {
        List<Map<String, String>> courseList = new ArrayList<>();
        try {
            String roomId = "";
            if (id != null && !"".equals(id)) {
                roomId = areaHttpService.getRoomIdByHostCode(id);
            } else if (masterIp != null && !"".equals(masterIp)) {
                roomId = areaHttpService.getRoomIdByHostIp(masterIp);
            }
            logger.debug(this.getClass().getSimpleName() + "教室roomId=" + roomId);
            if (!"".equals(roomId)) {
                courseList = activityDao.getLectCourseListByTime(time, roomId);
                if (courseList != null && !courseList.isEmpty()) {
                    for (Map<String, String> course : courseList) {
                        String activityId = String.valueOf(course.get("activityId"));
                        if (CacheMap.cacheMap != null && !CacheMap.cacheMap.isEmpty() && CacheMap.getCacheMap(activityId) != null && !"".equals(CacheMap.getCacheMap(activityId))) {
                            course.put("confID", CacheMap.getCacheMap(activityId));
                            logger.debug("查询当天课表，从缓存获取会议id>>>>>>>>>" + CacheMap.getCacheMap(activityId));
                        } else {
                            try {
                                Activity activity = activityDao.selectActivityById(Integer.parseInt(activityId));
                                //保存会议id
                                course.put("confID", activity.getSpareB());
                                logger.debug("查询当天课表，从数据库获取会议id>>>>>>>>>" + activity.getSpareB());
                            } catch (Exception e) {
                                course.put("confID", "");
                                logger.error("根据活动id查询预约活动异常。", e);
                            }
                        }

                        String name = course.get("activityName")+course.get("currentTimeStamp");
                        String startTime = course.get("start_time");
                        String endTime = course.get("end_time");
                        course.put("activityName", name + " " + startTime + "~" + endTime);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("获取当前时间主讲课程信息异常," + "time=" + time + ",masterIp=" + masterIp, e);
        }
        return courseList;
    }

    /**
     * 计算通知时间
     *
     * @param startTime       活动开始时间
     * @param noticeTimeStyle 通知时间选择
     */
    private String getNoticeTime(String startTime, String noticeTimeStyle) {
        String date = null;
        if (noticeTimeStyle != null && !"".equals(noticeTimeStyle)) {
            String[] notices = noticeTimeStyle.split(",");
            Arrays.sort(notices);
            for (String st : notices) {
                switch (Integer.valueOf(st)) {
                    case 3:
                        //活动前半小时通知
                        date = DateTimeUtil.getTimebeforMinutes(startTime, -30);
                        break;
                    default:
                        //活动前一下小时通知（case==2）
                        date = DateTimeUtil.getTimebeforMinutes(startTime, -60);
                        return date;
                }
            }
        }
        return date;
    }

    /**
     * 获取通知内容
     *
     * @param acMap 活动信息
     * @return
     */
    private String getNoticeContent(Map<String, Object> acMap) {
        String content = "";
        Object startDate = acMap.get("startDate");
        if (startDate != null && !"".equals(startDate)) {
            content = " 您于 " + acMap.get("startDate") + " 有一场名为“" + acMap.get("name") + "”的活动，请及时参加";
        } else {
            content = " 您于 " + acMap.get("startTime") + " 有一场名为“" + acMap.get("className") + "”的活动，请及时参加";
        }
        return content;
    }

    /**
     * 发送通知，同步通知时间
     */
    @Override
    public void asyncNoticeTime()
    {
        try{
            List<Map<String,Object>> acList=activityDao.selectAcByNoticeTime();
            if(acList!=null&&acList.size()>0)
            {
                for(Map<String,Object>ac:acList)
                {
                    this.sendMessage(ac);
                    if (ac.get("noticeType") != null && !"".equals(ac.get("noticeType")) && ac.get("noticeType").toString().contains("3")) {
                        String date = this.getNoticeTime(ac.get("startDate") + ":00".toString(), "3");
                        this.updateNoticeTime((int) ac.get("activityId"), date);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("监听器发送通知异常", e);
        }
    }

    /**
     * 发送通知
     *
     * @param ac
     */
    private void sendMessage(Map<String, Object> ac) {
        //获取用户联系人
        List<String> userIds = new ArrayList<>();
        Object hostID = ac.get("hostId");
        if (hostID != null && !"".equals(hostID.toString())) {
            userIds.add(ac.get("hostId").toString());
        } else {
            userIds.add(ac.get("hostTeacherId").toString());
        }
        List<AssteachActivity> assList = assteachActivityDao.selectAssteachActivityByUuid(ac.get("uuid").toString());
        if (assList != null && ac.size() > 0) {
            for (AssteachActivity ass : assList) {
                if (ass.getAssistTeacherId() != 0) {
                    userIds.add(String.valueOf(ass.getAssistTeacherId()));
                }
            }
        }
        String content = this.getNoticeContent(ac);
        Map phoneMap = this.getUserPhones(userIds);
        if (phoneMap != null && !phoneMap.isEmpty()) {
            Object sendMessage = ac.get("sendMessage");
            Object phone = phoneMap.get("phone");
            if (Integer.valueOf(sendMessage.toString()) == 1 && phone != null && !"".equals(phone.toString())) {
                try {
                    noticeHttpService.sendPhones(phone.toString(), content + "【鸿合科技】");
                } catch (Exception e) {
                    logger.error("发送短信異常", e);
                }
            }
            Object sendEmail = ac.get("sendEmail");
            Object email = phoneMap.get("email");
            if (Integer.valueOf(sendEmail.toString()) == 1 && email != null && !"".equals(email.toString())) {
                try {
                    noticeHttpService.sendEamils(email.toString(), "【鸿合科技】", content);
                } catch (Exception e) {
                    logger.error("发送邮件异常", e);
                }
            }
        }
    }

    /**
     * 根据活动id更改通知时间
     *
     * @param activityId
     * @param noticeTime
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean updateNoticeTime(int activityId, String noticeTime) {
        try {
            Map<String, Object> params = new HashedMap();
            params.put("activityId", activityId);
            params.put("noticeTime", noticeTime);
            activityDao.updateNoticeTimeByAcId(params);
            return true;
        } catch (Exception e) {
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("根据活动id修改通知时间异常", e);
            return false;
        }
    }

    /**
     *
     */
    public Map<String, String> getUserPhones(List<String> userIds) {
        String phones = "";
        String eMails = "";
        //获取活动相关人电话联系方式
        Map<String,Object> userInfo=userHttpService.getAllUserInfo();
        Map<String, String> resultMap = new HashedMap();
        if(userInfo!=null && !userInfo.isEmpty()){
            for (String userId : userIds) {
                if(userInfo.get(userId)!=null) {
                    Map<String, String> user =(Map<String,String>) userInfo.get(userId);
                    String phone = user.get("mobile")==null?"":user.get("mobile");
                    String eMail = user.get("email")==null?"":user.get("email");
                    if (!"".equals(phone)) {
                        phones += "," + phone;
                    }
                    if (!"".equals(eMail)) {
                        eMails += "," + eMail;
                    }
                }
            }
            String strsplit=",";
            if (phones.indexOf(strsplit) != -1) {
                phones = phones.substring(1);
            }
            if (eMails.indexOf(strsplit) != -1) {
                eMails = eMails.substring(1);
            }
            resultMap.put("phone", phones);
            resultMap.put("email", eMails);
        }
        return resultMap;
    }

    /**
     * 判断此地点该课节是否有课程
     *
     * @param startTime   课节开始时间 格式yyyy-MM-dd hh:mm:ss
     * @param endTime     课节结束时间  格式yyyy-MM-dd hh:mm:ss
     * @param classtimeId 课节id
     * @param roomIdList  教室id集合
     * @return 0:无课程
     */
    public Object judgeHasPreAc(String acId, String startTime, String endTime, String classtimeId, String uuid, List<String> roomIdList, List<String> teacherList) {
        try {
            List<Map<String, Object>> acList = this.selectAcByClasstime(acId, startTime, endTime, classtimeId);
            if (acList != null && acList.size() > 0) {
                for (Map<String, Object> ac : acList) {
                    if (!ac.get("uuid").toString().equals(uuid)) {
                        if (roomIdList.contains(ac.get("roomId").toString())) {
                            return "教室:" + ac.get("roomName") + " 本节课已有预约课程";
                        } else if (teacherList.contains(ac.get("teacherId").toString())) {
                            return "教师:" + ac.get("hostName") + "在本节课已有预约课程";
                        } else if (roomIdList.contains(ac.get("acceptRoomId").toString())) {
                            //此处检测接收教室及接收教师是否存在冲突
                            return "教室:" + ac.get("acceptRoomName") + " 本节课已有预约课程";
                        } else if (teacherList.contains(ac.get("assistTeacherId").toString())) {
                            return "教师:" + ac.get("assistTeacherName") + " 在本节课已有预约课程";
                        } else {
                            continue;
                        }
                    } else {
                        continue;
                    }
                }
                return 0;
            } else {
                return 0;
            }
        } catch (Exception e) {
            logger.error("预约检查课节是否已存在课程异常", e);
            return "不小心出错了，再来一遍吧";
        }

    }

    /**
     * 跟据课节查询活动
     *
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @param classtimeId 课节id
     * @return 活动信息
     * @throws Exception
     */
    private List<Map<String, Object>> selectAcByClasstime(String acId, String startTime, String endTime, String classtimeId) throws Exception {
        Map params = new HashMap();
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        params.put("classtimeId", classtimeId);
        if (acId != null && !"".equals(acId)) {
            params.put("activityId", acId);
        }
        return activityDao.selectAcByClassTime(params);
    }

    /**
     * 根据时间判断是否可以预约和修改
     *
     * @return true:可以预约；false:不可以预约
     * @Param startTime 开始时间 格式：yyyy-MM-dd hh:mm:ss
     */
    private boolean judgePreMay(String startTime)
    {
        try{
            String time=DateTimeUtil.getTimebeforMinutes(startTime,-60);
            long time2=DateTimeUtil.parseDateTime(time).getTime();
            if(System.currentTimeMillis()<time2)
            {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error("判断当前时间是否可以预约异常", e);
            return false;
        }
    }

    /**
     * 根据时间判断是预约否在当前学期时间内
     *
     * @return true:可以预约；false:不可以预约
     * @Param startTime 开始时间 格式：yyyy-MM-dd hh:mm:ss
     */
    private boolean judgePreInTerm(String startTime, String endTime) {
        try {
            this.getUseingTermInfo();
            long startTime1 = DateTimeUtil.parseDateTime(startTime).getTime();
            long endTime1 = DateTimeUtil.parseDateTime(endTime).getTime();
            if (startTime1 > startTermDate.getTime() && endTime1 < endTermDate.getTime()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error("判断当前时间是否可以预约异常", e);
            return false;
        }
    }

    /**
     * 预约教室去重
     */
    @Override
    public void uniqRoom(List<Map<String,Object>> roomList, String roomIds)
    {
        String[] roomIdArray = roomIds.split(",");
        for(int i=0;i<roomList.size();i++)
        {
            Map<String,Object> roomMap=roomList.get(i);
            for (String roomId:roomIdArray)
            {
                if(roomMap.get("roomId").equals(roomId))
                {
                    roomList.remove(i);
                    i--;
                    break;
                } else {
                    continue;
                }
            }
        }
    }

    /**
     * 预约教师学科处理
     */
    @Override
    public void checkSub(List<Map<String, Object>> teacherList) {
        for (int i = 0; i < teacherList.size(); i++) {
            Map<String, Object> teacherMap = teacherList.get(i);
            if (teacherMap.get("subject") == null || "".equals(teacherMap.get("subject"))) {
                teacherMap.put("subject", "全部学科");
            } else {
                continue;
            }
        }
    }

    /**
     * 插入数据测试
     *
     * @return
     */
    @Override
    public int insertTest(int num) {
        JSONObject acJson = new JSONObject();
        try {
            List<Map<String, Object>> periodList = periodDao.selectAllPeriod();
            Map<String, Object> period = (Map<String, Object>) DataTest.getOne(periodList);
            List subList = subjectDao.selectAllSubByPeriodId((int) period.get("periodId"));
            Map<String, Object> subMap = JSONObject.fromObject(DataTest.getOne(subList));
            acJson.put("periodId", period.get("periodId"));
            acJson.put("periodName", period.get("name"));
            acJson.put("subjectId", subMap.get("id"));
            acJson.put("subjectName", subMap.get("name"));
            acJson.put("name", subMap.get("name") + "基础");
            acJson.put("gradeId", 1);
            acJson.put("gradeName", "一年级");
            acJson.put("userId", 1);
            List<Map<String, String>> cityList = userHttpService.getCityByProvince(3);
            Map<String, String> city = (Map<String, String>) DataTest.getOne(cityList);
            List<Map<String, String>> countyList = userHttpService.getCountyByCity(Integer.valueOf(city.get("areaId")));
            Map<String, String> county = (Map<String, String>) DataTest.getOne(countyList);
            List<Map<String, String>> schoolList = userHttpService.getSchoolByCounty(Integer.valueOf(county.get("areaId")));
            Map<String, String> school = (Map<String, String>) DataTest.getOne(schoolList);
            Map<String, Object> roomMap = userHttpService.getRoomBySchoolId(school.get("areaId"), "", "1", 1, 9999, "");
            List<Map<String, Object>> roomList = JSONArray.fromObject(roomMap.get("items"));
            Map<String, Object> room = (Map<String, Object>) DataTest.getOne(roomList);
            Map<String, Object> teacherMap = userHttpService.getTeacherBySchoolId(school.get("areaId"), "", 1, 9999, "");
            List<Map<String, Object>> teacherList = JSONArray.fromObject(teacherMap.get("items"));
            Map<String, Object> teacher = (Map<String, Object>) DataTest.getOne(teacherList);
            acJson.put("provinceName", "河北省");
            acJson.put("cityName", city.get("areaName"));
            acJson.put("countyName", county.get("areaName"));
            acJson.put("schoolName", school.get("areaName"));
            acJson.put("hostRoomId", room.get("roomId"));
            acJson.put("hostRoomName", room.get("roomName"));
            acJson.put("hostRoomStudents", room.get("number"));
            String areaIds = "3-" + city.get("areaId") + "-" + county.get("areaId") + "-" + school.get("areaId");
            acJson.put("roomAddr", areaIds);
            acJson.put("hostTeacherId", teacher.get("id"));
            acJson.put("hostTeacherName", teacher.get("name"));
            String uuid = UUID.randomUUID().toString();
            acJson.put("uuid", uuid);
            int acceptNum = (int) (Math.random() * 3) + 1;
            List<Map<String, Object>> acceptList = new ArrayList<>();
            for (int j = 0; j < acceptNum; j++)
            {
                JSONObject acceptJson = new JSONObject();
                Map<String, String> acceptCity = (Map<String, String>) DataTest.getOne(cityList);
                Map<String, String> acceptCounty = (Map<String, String>) DataTest.getOne(cityList);
                Map<String, String> acceptSchool = (Map<String, String>) DataTest.getOne(schoolList);
                Map<String, Object> acceptRoomMap = userHttpService.getRoomBySchoolId(school.get("areaId"), "", "", 1, 9999, "");
                List<Map<String, Object>> acceptRoomList = JSONArray.fromObject(acceptRoomMap.get("items"));
                Map<String, Object> acceptRoom = (Map<String, Object>) DataTest.getOne(acceptRoomList);
                Map<String, Object> assistTeacherMap = userHttpService.getTeacherBySchoolId(school.get("areaId"), "", 1, 9999, "");
                List<Map<String, Object>> assistTeacherList = JSONArray.fromObject(assistTeacherMap.get("items"));
                Map<String, Object> assistTeacher = (Map<String, Object>) DataTest.getOne(assistTeacherList);
                acceptJson.put("provinceName", "河北省");
                acceptJson.put("cityName", acceptCity.get("areaName"));
                acceptJson.put("countyName", acceptCounty.get("areaName"));
                acceptJson.put("schoolName", acceptSchool.get("areaName"));
                String acceptAreaIds = "3-" + acceptCity.get("areaId") + "-" + acceptCounty.get("areaId") + "-" + acceptSchool.get("areaId");
                acceptJson.put("roomAddr", acceptAreaIds);
                acceptJson.put("assistTeacherId", Integer.valueOf(assistTeacher.get("id").toString()));
                acceptJson.put("assistTeacherName", assistTeacher.get("name"));
                acceptJson.put("acceptRoomId", acceptRoom.get("roomId"));
                acceptJson.put("acceptRoomName", acceptRoom.get("roomName"));
                acceptJson.put("acceptRoomStudents", acceptRoom.get("number"));
                acceptJson.put("uuid", uuid);
                acceptList.add(acceptJson);
            }
            acJson.put("idsInfo", acceptList);
            int year = (int) (Math.random() * 3) + 2017;
            int month = (int) (Math.random() * 12) + 1;
            int date = (int) (Math.random() * 30) + 1;
            int classtimeId = (int) (Math.random() * 97) + 99;
            Map<String, Object> classtimeMap = classtimeDao.selectClassTimeByClasstimeId(classtimeId + "");
            String stStartDate = year + "-" + month + "-" + date + "  " + classtimeMap.get("startTime");
            String stEndDate = year + "-" + month + "-" + date + "  " + classtimeMap.get("endTime");
            acJson.put("startTime", stStartDate);
            acJson.put("endTime", stEndDate);
            acJson.put("lessonId", classtimeId);
            String stag = this.saveActivity(acJson.toString()).toString();
            if (stag.equals(true)) {
            } else {
                num--;
            }
        } catch (Exception e) {
            num--;
        }
        return num;
    }

    /**
     * 获取终端列表
     * @param parties 如：[{"partyID":"0","partyIP":"192.168.19.153","partyName":"0","partyProtocol":"h323","partyType":"by_address"}]
     * @return
     */
    @Override
    public JSONArray getPartyArray(String parties) {
        JSONArray partyArray = new JSONArray();
        if (parties != null && !"".equals(parties)) {
            JSONArray jsonArray = JSONArray.fromObject(parties);
            for (Object json : jsonArray) {
                JSONObject party = new JSONObject();
                JSONObject data = JSONObject.fromObject(json);
                party.put("partyIP", data.get("partyIP") == null ? "" : data.get("partyIP").toString());
                party.put("partyID", deviceHttpService.getHostCodeByIp(party.get("partyIP").toString()));
                party.put("partyName", data.get("partyName") == null ? "" : data.get("partyName").toString());
                party.put("partyType", data.get("partyType") == null ? "" : data.get("partyType").toString());
                partyArray.add(party);
            }
        }
        return partyArray;
    }

    /**
     * 获取会议号
     * @param name
     * @param startTime
     * @param endTime
     * @param masterIp
     * @return
     */
    @Override
    public String getConfID(String name, String startTime, String endTime,String masterIp) {
        try {
            Date now = new Date();
            startTime = DateTimeUtil.formatDate(now) + " " + startTime + ":00";
            endTime = DateTimeUtil.formatDate(now) + " " + endTime + ":00";
        } catch (Exception e) {
            logger.error("时间转换异常。", e);
        }
        String roomId=areaHttpService.getRoomIdByHostIp(masterIp);
        return activityDao.getConfID(name, startTime, endTime,roomId);
    }

    private Map<String,String> getTermianlMap(JSONArray partyJsonArray){
        String terminals="";
        String masterId="";
        if(partyJsonArray!=null){
            JSONObject partyJson=JSONObject.fromObject(partyJsonArray.get(0));
            if(partyJson.containsKey("partyID")) {
                //获取主讲终端id(如：1234021)
                masterId =partyJson.get("partyID").toString();
                //获取教学活动终端id串
                for (Object party : partyJsonArray) {
                    JSONObject host = JSONObject.fromObject(party);
                    terminals += host.get("partyID") + ",";
                }
            }
        }
        if(terminals.endsWith(",")) {
            terminals = terminals.substring(0, terminals.lastIndexOf(","));
        }
        Map<String,String> map=new HashMap<>();
        map.put("terminals",terminals);
        map.put("masterId",masterId);
        return map;
    }
}
