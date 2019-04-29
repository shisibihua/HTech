package com.honghe.user.dao.entity;/**
 * Created by zhaowj on 2017-01-09
 */

import java.io.Serializable;

/**
 * 科目类
 *
 * @author zhaowj
 * @create 2017-01-09 08:51
 **/
public class Subject implements Serializable {
    private long id;
    private String name; //科目名称
    private String description;//科目描述
    private String order_num; //排序顺序
    private int is_show;//是否显示（0否；1是）
    private String season_id; //学段id


    public long getId() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrder_num() {
        return order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }

    public int getIs_show() {
        return is_show;
    }

    public void setIs_show(int is_show) {
        this.is_show = is_show;
    }

    public String getSeason_id() {
        return season_id;
    }

    public void setSeason_id(String season_id) {
        this.season_id = season_id;
    }

}