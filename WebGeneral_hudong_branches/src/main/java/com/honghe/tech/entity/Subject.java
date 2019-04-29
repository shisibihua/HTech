package com.honghe.tech.entity;

/**
 * Created by xinqinggang on 2018/1/24.
 */
public class Subject {
    private int id;
    private String name;
    private String gradeId;
    private String spare;

    public Subject() {
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


    public Subject(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
