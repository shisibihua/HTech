package com.honghe.user.dao.entity;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 家长类
 *
 * @author zhaowj
 * @create 2017-01-09 08:51
 **/
public class Parent implements Serializable {

    private long id;
    private String parent_code; //系统内部编号
    private String parent_name;//家长姓名
    private String namepy;//家长拼音
    private String parent_path;//家长头像
    private String membership; //成员关系（1父亲，2母亲，3其他)
    private String parent_mobile;//家长手机号码
    private String email;//家长邮箱
    private String is_guardian; //是否是监护人（0否；1是）
    private String is_together; //是否生活在一起（0否；1是）

    private String studentNames;

    private User user;

    private Set<Student> studentSet = new LinkedHashSet<>();

    public Set<Student> getStudentSet() {
        return studentSet;
    }

    public void setStudentSet(Set<Student> studentSet) {
        this.studentSet = studentSet;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getParent_code() {
        return parent_code;
    }

    public void setParent_code(String parent_code) {
        this.parent_code = parent_code;
    }

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    public String getParent_path() {
        return parent_path;
    }

    public void setParent_path(String parent_path) {
        this.parent_path = parent_path;
    }

    public String getParent_mobile() {
        return parent_mobile;
    }

    public void setParent_mobile(String parent_mobile) {
        this.parent_mobile = parent_mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMembership() {
        return membership;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

    public String getIs_guardian() {
        return is_guardian;
    }
    public void setIs_guardian(String is_guardian) {
        this.is_guardian = is_guardian;
    }

    public String getIs_together() {
        return is_together;
    }

    public void setIs_together(String is_together) {
        this.is_together = is_together;
    }

    public String getNamepy() {
        return namepy;
    }

    public void setNamepy(String namepy) {
        this.namepy = namepy;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStudentNames() {
        Iterator<Student> it = studentSet.iterator();
        String names = "";
        while (it.hasNext()) {
            Student stu = it.next();
            names += stu.getRealName() + "，";
        }
        if(names.lastIndexOf("，") > 0){
            names = names.substring(0, names.lastIndexOf("，"));
        }
        return names;
    }

    public void setStudentNames(String studentNames) {
        this.studentNames = studentNames;
    }
}
