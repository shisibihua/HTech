package com.honghe.tech.form;

import com.honghe.tech.entity.AssteachActivity;
import com.honghe.tech.util.DateTimeUtil;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xinqinggang on 2018/2/3.
 * 存放活动的详细信息
 */
public class ActivityInfoForm {
    private int id;
    private String name;
    private int subjectId;
    private String subjectName;//学科名称
    private int periodId;
    private String periodName;  //年级名称
    private int gradeId;
    private String gradeName;  //年级名称
    private int hostId;//主讲人
    private String hostName;//主讲人名称
    private int hostRoomId;//主讲会场id
    private String hostRoomName;//主讲会场名称
    private int roomStudents;//教室人数
    private String uuid;//和辅助教师表对应
    private String hostCityName;//城市名称
    private String hostCityId;//城市id
    private String hostCountyName;//区县名
    private String hostCountyId;//区县id
    private String hostSchoolName;//学校名
    private String hostSchoolId;//学校id
    private int totalPlanHour;//总课时数
    private int weekTotalHour;//周课时数
    private int doneTotalHour;//已完成课时数
    private String intro;//活动简介
    private String startTime;
    private String endTime;
    private String activityStatus;
    private int liveStreamId;
    private String liveStreamAddr;
    private List<AssteachActivityForm> assteachActivityList=new ArrayList<>();
    private String sectionId;
    private String areaAddr;
    private int userId;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public int getHostId() {
        return hostId;
    }

    public void setHostId(int hostId) {
        this.hostId = hostId;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        try {
            this.startTime = DateTimeUtil.formatDateTime(startTime);
        } catch (Exception e) {
            Logger.getLogger(ActivityInfoForm.class).error("setStartTime: 转换日期异常");
        }
    }

   public String getEndTime() {
        return endTime;
    }

     public void setEndTime(Date endTime) {
        try {
            this.endTime = DateTimeUtil.formatDateTime(endTime);
        } catch (Exception e) {
            Logger.getLogger(ActivityInfoForm.class).error("setEndTime: 转换日期异常");
        }
    }

    public int getHostRoomId() {
        return hostRoomId;
    }

    public void setHostRoomId(int hostRoomId) {
        this.hostRoomId = hostRoomId;
    }

    public String getHostRoomName() {
        return hostRoomName;
    }

    public void setHostRoomName(String hostRoomName) {
        this.hostRoomName = hostRoomName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getHostCityName() {
        return hostCityName;
    }

    public void setHostCityName(String hostCityName) {
        this.hostCityName = hostCityName;
    }

    public String getHostCountyName() {
        return hostCountyName;
    }

    public void setHostCountyName(String hostCountyName) {
        this.hostCountyName = hostCountyName;
    }

    public String getHostSchoolName() {
        return hostSchoolName;
    }

    public void setHostSchoolName(String hostSchoolName) {
        this.hostSchoolName = hostSchoolName;
    }

    public List<AssteachActivityForm> getAssteachActivityList() {
        return assteachActivityList;
    }

    public void setAssteachActivityList(List<AssteachActivityForm> assteachActivityList) {
        this.assteachActivityList = assteachActivityList;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public int getTotalPlanHour() {
        return totalPlanHour;
    }

    public void setTotalPlanHour(int totalPlanHour) {
        this.totalPlanHour = totalPlanHour;
    }

    public int getWeekTotalHour() {
        return weekTotalHour;
    }

    public void setWeekTotalHour(int weekTotalHour) {
        this.weekTotalHour = weekTotalHour;
    }

    public int getDoneTotalHour() {
        return doneTotalHour;
    }

    public void setDoneTotalHour(int doneTotalHour) {
        this.doneTotalHour = doneTotalHour;
    }

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
    }

    public int getLiveStreamId() {
        return liveStreamId;
    }

    public void setLiveStreamId(int liveStreamId) {
        this.liveStreamId = liveStreamId;
    }

    public String getLiveStreamAddr() {
        return liveStreamAddr;
    }

    public void setLiveStreamAddr(String liveStreamAddr) {
        this.liveStreamAddr = liveStreamAddr;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public int getPeriodId() {
        return periodId;
    }

    public void setPeriodId(int periodId) {
        this.periodId = periodId;
    }

    public String getPeriodName() {
        return periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    public String getHostCityId() {
        return hostCityId;
    }

    public void setHostCityId(String hostCityId) {
        this.hostCityId = hostCityId;
    }

    public String getHostCountyId() {
        return hostCountyId;
    }

    public void setHostCountyId(String hostCountyId) {
        this.hostCountyId = hostCountyId;
    }

    public String getHostSchoolId() {
        return hostSchoolId;
    }

    public void setHostSchoolId(String hostSchoolId) {
        this.hostSchoolId = hostSchoolId;
    }

    public String getAreaAddr() {
        return areaAddr;
    }

    public void setAreaAddr(String areaAddr) {
        this.areaAddr = areaAddr;
    }

    public int getRoomStudents() {
        return roomStudents;
    }

    public void setRoomStudents(int roomStudents) {
        this.roomStudents = roomStudents;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
