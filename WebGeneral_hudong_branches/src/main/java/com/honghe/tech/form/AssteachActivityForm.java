package com.honghe.tech.form;

/**
 * Created by Administrator on 2018/2/5/005.
 */
public class AssteachActivityForm {
    private int id;
    private int assistTeacherId;//辅助教师
    private String assistTeacherName;//辅助教师
    private int  acceptRoomId;//接收教室id
    private String acceptCityName;//接收教室所在市
    private String acceptCountyName;//接收教室所在区县
    private String acceptSchoolName;//接收教室所在学校
    private String acceptRoomName;//接收教室名称
    private int acceptClassId;//接收教室听课班级
    private String acceptClassName;//接收教室听课班级名称
    private String acceptRoomAddr;//接收教室地址id串 省id-市id-区县id-学校id
    private String acceptCityId;
    private String acceptCountyId;
    private String acceptSchoolId;
    private int roomStudents;//教室容纳人数
    private String uuid;//表明同一批数据

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAssistTeacherId() {
        return assistTeacherId;
    }

    public void setAssistTeacherId(int assistTeacherId) {
        this.assistTeacherId = assistTeacherId;
    }

    public String getAssistTeacherName() {
        return assistTeacherName;
    }

    public void setAssistTeacherName(String assistTeacherName) {
        this.assistTeacherName = assistTeacherName;
    }

    public int getAcceptRoomId() {
        return acceptRoomId;
    }

    public void setAcceptRoomId(int acceptRoomId) {
        this.acceptRoomId = acceptRoomId;
    }

    public String getAcceptCityName() {
        return acceptCityName;
    }

    public void setAcceptCityName(String acceptCityName) {
        this.acceptCityName = acceptCityName;
    }

    public String getAcceptCountyName() {
        return acceptCountyName;
    }

    public void setAcceptCountyName(String acceptCountyName) {
        this.acceptCountyName = acceptCountyName;
    }

    public String getAcceptSchoolName() {
        return acceptSchoolName;
    }

    public void setAcceptSchoolName(String acceptSchoolName) {
        this.acceptSchoolName = acceptSchoolName;
    }

    public String getAcceptRoomName() {
        return acceptRoomName;
    }

    public void setAcceptRoomName(String acceptRoomName) {
        this.acceptRoomName = acceptRoomName;
    }

    public int getAcceptClassId() {
        return acceptClassId;
    }

    public void setAcceptClassId(int acceptClassId) {
        this.acceptClassId = acceptClassId;
    }

    public String getAcceptClassName() {
        return acceptClassName;
    }

    public void setAcceptClassName(String acceptClassName) {
        this.acceptClassName = acceptClassName;
    }

    public String getAcceptRoomAddr() {
        return acceptRoomAddr;
    }

    public void setAcceptRoomAddr(String acceptRoomAddr) {
        this.acceptRoomAddr = acceptRoomAddr;
    }

    public int getRoomStudents() {
        return roomStudents;
    }

    public void setRoomStudents(int roomStudents) {
        this.roomStudents = roomStudents;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAcceptCityId() {
        return acceptCityId;
    }

    public void setAcceptCityId(String acceptCityId) {
        this.acceptCityId = acceptCityId;
    }

    public String getAcceptCountyId() {
        return acceptCountyId;
    }

    public void setAcceptCountyId(String acceptCountyId) {
        this.acceptCountyId = acceptCountyId;
    }

    public String getAcceptSchoolId() {
        return acceptSchoolId;
    }

    public void setAcceptSchoolId(String acceptSchoolId) {
        this.acceptSchoolId = acceptSchoolId;
    }
}
