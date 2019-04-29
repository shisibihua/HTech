package com.honghe.user.dao.entity;


/**
 * 学段表
 * Created by sky on 2017/1/9.
 */
public class Stage {
    private long id;

    private String name;//学段名称

    private int number;//学段编号

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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}
