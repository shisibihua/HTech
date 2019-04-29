package com.honghe.tech.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xinqinggang on 2018/1/24.
 */
public class Activity implements Serializable {
    private int id;
    private String name;
    private int activityStyleId;//活动模式
    private Date updateTime;
    private int periodId;//学段id
    private String periodName;//学段名称
    private int subjectId;//学科id
    private String subjectName;//学科名称
    private int gradeId;  //年级id
    private String gradeName;  //年级名称
    private int hostId;//主讲人
    private String hostName;//主讲人名称
    private int roomId;//主讲会场id
    private String roomName;//主讲会场名称
    private int roomStudents;//教室容纳人数
    private int classId;//主讲会场听课班级
    private String className;//主讲会场听课班级名称
    private String uuid;//和辅助教师表对应
    private String areaId;//地点名称对应id
    private String intro;//活动简介
    private Date startTime; //活动开始时间
    private Date endTime;   //活动结束时间
    private int sendEmail;//是否发送邮件 0:不发送；1:发送；
    private int sendMessage;//是否发送短信 0:不发送；1:发送；
    private String mcuId;
    private String mcuIp;
    private String mcuType;//mcu类型
    private int cloudLive;//是否云直播 0:否；1:是；
    private int status;//课程状态 0:无效；1:有效；
    private String invalidRemark;//课程无效备注；
    private int userId;//活动创建用户
    private String noticeTime;//发布通知时间
    private String noticeType;//发布通知时间
    private int isDel;//是否删除；1：是；0：未删；
    private String roomAddr; //保存省、市、区、学校id，用“-”分割
    private String spareA;   //对应课节id
    private String spareB;   //扩展字段，会议id
    private String spareC;   //扩展字段

    private String provinceName;
    private String cityName;
    private String countyName;
    private String schoolName;


    public Activity() {
    }


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

    public int getActivityStyleId() {
        return activityStyleId;
    }

    public void setActivityStyleId(int activityStyleId) {
        this.activityStyleId = activityStyleId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

    public int getHostId() {
        return hostId;
    }

    public void setHostId(int hostId) {
        this.hostId = hostId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(int sendEmail) {
        this.sendEmail = sendEmail;
    }

    public int getSendMessage() {
        return sendMessage;
    }

    public void setSendMessage(int sendMessage) {
        this.sendMessage = sendMessage;
    }

    public String getMcuId() {
        return mcuId;
    }

    public void setMcuId(String mcuId) {
        this.mcuId = mcuId;
    }

    public int getCloudLive() {
        return cloudLive;
    }

    public void setCloudLive(int cloudLive) {
        this.cloudLive = cloudLive;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInvalidRemark() {
        return invalidRemark;
    }

    public void setInvalidRemark(String invalidRemark) {
        this.invalidRemark = invalidRemark;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMcuType() {
        return mcuType;
    }

    public void setMcuType(String mcuType) {
        this.mcuType = mcuType;
    }

    public String getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(String noticeTime) {
        this.noticeTime = noticeTime;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSpareA() {
        return spareA;
    }

    public void setSpareA(String spareA) {
        this.spareA = spareA;
    }

    public String getSpareB() {
        return spareB;
    }

    public void setSpareB(String spareB) {
        this.spareB = spareB;
    }

    public String getSpareC() {
        return spareC;
    }

    public void setSpareC(String spareC) {
        this.spareC = spareC;
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

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getRoomAddr() {
        return roomAddr;
    }

    public void setRoomAddr(String roomAddr) {
        this.roomAddr = roomAddr;
    }

    public int getRoomStudents() {
        return roomStudents;
    }

    public void setRoomStudents(int roomStudents) {
        this.roomStudents = roomStudents;
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

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public String getMcuIp() {
        return mcuIp;
    }

    public void setMcuIp(String mcuIp) {
        this.mcuIp = mcuIp;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
}
