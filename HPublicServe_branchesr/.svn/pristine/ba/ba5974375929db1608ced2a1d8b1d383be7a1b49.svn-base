package com.honghe.user.dao.entity;/**
 * Created by lyx on 2017-01-07 0007.
 */


import java.io.Serializable;

/**
 * 学生类
 *
 * @author lyx
 * @create 2017-01-07 14:23
 **/
public class Student implements Serializable {

    private int id;
    private String student_code; //系统内部编号
    private String student_num;//学籍号
    private String namepy; //姓名拼音
    private String realName;//学生姓名
    private String student_path;//学生头像
    private String gender; //性别 1-男 2-女 0-未知
    private String readtype;//就读方式（1走读；2住宿；3借宿；4其他）
    private String nation;//民族
    private String status;//在校状态(1在校；2离校；3毕业)
    private String enter_year;//入学年度（2016）
    private String mobile;//手机号码
    private String email;//电子邮箱
    private String enter_way;//入学方式（1普通入学；2体育特招；3外校转入；4其他）
    private String address;//户口所在地
    private String birthday;// 生日

    private User user;

//    private Set<Parent>  parentSet = new HashSet<Parent>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudent_code() {
        return student_code;
    }

    public void setStudent_code(String student_code) {
        this.student_code = student_code;
    }

    public String getStudent_num() {
        return student_num;
    }

    public void setStudent_num(String student_num) {
        this.student_num = student_num;
    }

    public String getNamepy() {
        return namepy;
    }

    public void setNamepy(String namepy) {
        this.namepy = namepy;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getStudent_path() {
        return student_path;
    }

    public void setStudent_path(String student_path) {
        this.student_path = student_path;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getEnter_way() {
        return enter_way;
    }

    public void setEnter_way(String enter_way) {
        this.enter_way = enter_way;
    }
    public String getReadtype() {
        return readtype;
    }

    public void setReadtype(String readtype) {
        this.readtype = readtype;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEnter_year() {
        return enter_year;
    }

    public void setEnter_year(String enter_year) {
        this.enter_year = enter_year;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

//    @ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER,mappedBy="studentSet")
//    @JoinTable(name = "user_pc_relations",
//            joinColumns = {@JoinColumn(name = "parent_code", referencedColumnName = "parent_code")},
//            inverseJoinColumns = {@JoinColumn(name = "student_code", referencedColumnName = "student_code")})
//    public Set<Parent> getParentSet() {
//        return parentSet;
//    }

//    public void setParentSet(Set<Parent> parentSet) {
//        this.parentSet = parentSet;
//    }
}
