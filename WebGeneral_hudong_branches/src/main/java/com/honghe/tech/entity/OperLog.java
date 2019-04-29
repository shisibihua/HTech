package com.honghe.tech.entity;

import java.util.Date;

/**
 * 日志表
 * @author caoqian  20180124
 */
public class OperLog {
    private Integer logId;       //key
    private Integer userId;       //用户id
    private String logType;      //日志类型,0:操作日志，1:系统日志
    private String logContent;   //日志内容
    private String moduleName;    //功能模块
    private String spareA;        //备注字段
    private String spareB;        //备注字段
    private Date logTime;        //时间

    public OperLog() {
    }


    public OperLog(Integer logId, Integer userId, String logType, String logContent, String moduleName, Date logTime) {
        this.logId = logId;
        this.userId = userId;
        this.logType = logType;
        this.logContent = logContent;
        this.moduleName = moduleName;
        this.logTime = logTime;
    }

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
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

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }
}
