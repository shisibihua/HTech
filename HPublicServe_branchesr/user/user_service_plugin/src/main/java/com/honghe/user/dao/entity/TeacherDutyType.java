package com.honghe.user.dao.entity;

/**
 * 职务表
 *
 * @author sky
 * @date 2017/1/9
 */
public class TeacherDutyType {
    private int id;
    /**
     * //职务类型
     */
    private String type_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

}

