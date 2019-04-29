package com.honghe.tech.entity;

import java.util.Date;

/**
 * 通知表
 * @author  caoqian 20180124
 */
public class Notice {
    private Integer noticeId;         //key
    private Integer userId;           //用户id
    private String content;           //通知内容
    private String name;              //通知名称
    private Integer status;           //通知状态，0：未读，1：已读
    private Integer pubUserId;        //发布人id
    private Integer activityId;       //活动id
    private Integer type;             //通知类型（预留，1期没有）
    private String spareA;            //备注字段
    private String spareB;            //备注字段
    private Date pubTime;             //发布时间

    public Notice() {
    }

    public Notice(Integer noticeId, Integer userId, String content, String name, Integer status, Integer pubUserId, String spareA, String spareB, Date pubTime, Integer type, Integer activityId) {
        this.noticeId = noticeId;
        this.userId = userId;
        this.content = content;
        this.name = name;
        this.status = status;
        this.pubUserId = pubUserId;
        this.spareA = spareA;
        this.spareB = spareB;
        this.pubTime = pubTime;
        this.type = type;
        this.activityId = activityId;
    }

    public Integer getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPubUserId() {
        return pubUserId;
    }

    public void setPubUserId(Integer pubUserId) {
        this.pubUserId = pubUserId;
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

    public Date getPubTime() {
        return pubTime;
    }

    public void setPubTime(Date pubTime) {
        this.pubTime = pubTime;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
