package com.honghe.permissions.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanghongbin on 2016/10/9.
 */
public final class Permissions {

    private String id = "";
    private String name = "";

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private String desc = "";

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private String path = "";
    private List<Permissions> permissions;

    public String getPid() {
        return pId;
    }

    public void setPid(String pId) {
        this.pId = pId;
    }

    private String pId = "";

    public Permissions() {
        this("", "", "", "","");
    }

    public Permissions(String id, String name, String path, String desc,String pId) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.desc = desc;
        this.pId = pId;
        permissions = new ArrayList<>();

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Permissions> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permissions> permissions) {
        this.permissions = permissions;
    }
}
