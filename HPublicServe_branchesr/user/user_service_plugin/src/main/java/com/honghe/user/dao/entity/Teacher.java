package com.honghe.user.dao.entity;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by sky on 2017/1/9.
 */
public class Teacher implements Serializable {
    private long id;
    private String teacher_code;//系统内部流水号，服务接口传送的教师编号
    private String teacher_name;//教师姓名
    private String employeeno;//教职工工号
    private String namepy;//姓名拼音
    private String teacher_path;//头像存放路径
    private String gender;//性别 1-男 2-女 0-未知
    private String mobile;//手机号码
    private String email;//电子邮箱
    private String qq;//qq号码
    private String idcode;//身份证号码
    private String birthday;//出生年月(2010-08-09)
    private String short_num;//短号
    private String nation;//民族
    private String political;//政治面貌
    private String address;//通讯地址
    private String work_date;//参工时间
    private String teach_date;//从教时间
    private String is_job;//是否在职 （0否,1是）
    private String professional_title;//职称（职业资格 1：三级教师，2：二级教师、3：一级教师:4：高级教师，5：正高级教师）
    private String stage_id;//教师所授课学段

    private User user;

    private Set<TeacherDutyType> teacherDutyTypes = new LinkedHashSet<>();

    private Set<Subject> subject = new LinkedHashSet<>();

    private Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeacher_code() {
        return teacher_code;
    }

    public void setTeacher_code(String teacher_code) {
        this.teacher_code = teacher_code;
    }

    public String getNamepy() {
        return namepy;
    }

    public void setNamepy(String namepy) {
        this.namepy = namepy;
    }

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getPolitical() {
        return political;
    }

    public void setPolitical(String political) {
        this.political = political;
    }

    public String getTeacher_path() {
        return teacher_path;
    }

    public void setTeacher_path(String teacher_path) {
        this.teacher_path = teacher_path;
    }

    public String getEmployeeno() {
        return employeeno;
    }

    public void setEmployeeno(String employeeno) {
        this.employeeno = employeeno;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getShort_num() {
        return short_num;
    }

    public void setShort_num(String short_num) {
        this.short_num = short_num;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWork_date() {
        return work_date;
    }

    public void setWork_date(String work_date) {
        this.work_date = work_date;
    }

    public String getTeach_date() {
        return teach_date;
    }

    public void setTeach_date(String teach_date) {
        this.teach_date = teach_date;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<TeacherDutyType> getTeacherDutyTypes() {
        return teacherDutyTypes;
    }

    public void setTeacherDutyTypes(Set<TeacherDutyType> teacherDutyTypes) {
        this.teacherDutyTypes = teacherDutyTypes;
    }

    public Set<Subject> getSubject() {
        return subject;
    }

    public void setSubject(Set<Subject> subject) {
        this.subject = subject;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIs_job() {
        return is_job;
    }

    public void setIs_job(String is_job) {
        this.is_job = is_job;
    }

    public String getStage_id() {
        return stage_id;
    }

    public void setStage_id(String stage_id) {
        this.stage_id = stage_id;
    }

    public String getProfessional_title() {
        return professional_title;
    }

    public void setProfessional_title(String professional_title) {
        this.professional_title = professional_title;
    }

}
