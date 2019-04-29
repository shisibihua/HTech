package com.honghe.user.dao.entity;/**
 * Created by lyx on 2017-01-07 0007.
 */

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 用户类
 *
 * @author lyx
 * @create 2017-01-07 14:39
 **/
public class User implements Serializable {
    /**
     * //用户id；
     */
    private int user_id;
    /**
     * //系统编码，与学生，教师，家长系统编码对应
     */
    private String user_code;
    /**
     * //用户名,用于登陆系统使用
     */
    private String user_name;
    /**
     * //用户密码
     */
    private String user_pwd = "111111";
    /**
     * //加密串
     */
    private String user_salt;
    /**
     * //用户类型： 0 超级管理员参考user_type 表
     */
    private String user_type;
    /**
     * // 真实姓名
     */
    private String user_realname;
    /**
     * //头像存放路径
     */
    private String user_path;
    /**
     * //头像图片名称
     */
    private String user_avatar;
    /**
     * /性别 1-男 2-女 0-未知
     */
    private String user_gender;
    /**
     * //出生日期
     */
    private String user_birthday;
    /**
     * //手机号码
     */
    private String user_mobile;
    /**
     * //邮箱地址
     */
    private String user_email;
    /**
     * //家庭住址
     */
    private String user_address;
    /**
     * //用户号码，如学籍号等,学生，家长，教师分离后，可能会作废
     */
    private String user_num;
    /**
     * //注册时间
     */
    private String user_regdate;
    /**
     * //用户状态 0-用户注册未激活 1-用户正常使用 2-用户被禁用 3-用户未激活被禁用
     */
    private String user_status;
    /**
     * //同步云平台状态 0 未导入，1导入
     */
    private String user_syn_cloud;
    /**
     * //判断用户是否是校级管理员
     */
    private String user_isAdmin;
    /**
     * //是否为资源平台注册用户
     */
    private String user_hres;

    private Set<Campus> campuses = new LinkedHashSet<>();

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public String getUser_salt() {
        return user_salt;
    }

    public void setUser_salt(String user_salt) {
        this.user_salt = user_salt;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }

    public String getUser_status() {
        return user_status;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }

    public String getUser_realname() {
        return user_realname;
    }

    public void setUser_realname(String user_realname) {
        this.user_realname = user_realname;
    }

    public String getUser_path() {
        return user_path;
    }

    public String getUser_avatar() {
        return user_avatar;
    }

    public void setUser_path(String user_path) {
        this.user_path = user_path;
    }

    public void setUser_avatar(String user_avatar) {
        this.user_avatar = user_avatar;
    }

    public String getUser_birthday() {
        return user_birthday;
    }

    public void setUser_birthday(String user_birthday) {
        this.user_birthday = user_birthday;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public String getUser_num() {
        return user_num;
    }

    public void setUser_num(String user_num) {
        this.user_num = user_num;
    }

    public String getUser_regdate() {
        return user_regdate;
    }

    public void setUser_regdate(String user_regdate) {
        this.user_regdate = user_regdate;
    }

    public Set<Campus> getCampuses() {
        return campuses;
    }

    public void setCampuses(Set<Campus> campuses) {
        this.campuses = campuses;
    }

    public String getUser_syn_cloud() {
        return user_syn_cloud;
    }
    public void setUser_syn_cloud(String user_syn_cloud) {
        this.user_syn_cloud = user_syn_cloud;
    }

    public String getUser_isAdmin() {
        return user_isAdmin;
    }

    public void setUser_isAdmin(String user_isAdmin) {
        this.user_isAdmin = user_isAdmin;
    }

    public String getUser_hres() {
        return user_hres;
    }

    public void setUser_hres(String user_hres) {
        this.user_hres = user_hres;
    }
}
