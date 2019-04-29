package com.honghe.deviceNew.entity;

import java.io.Serializable;

public class DeviceConfig implements Serializable {
    private Integer id;

    private String connect_param;

    private String name;

    private String main;

    private String sub;

    private Integer is_ptz;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConnect_param() {
        return connect_param;
    }

    public void setConnect_param(String connect_param) {
        this.connect_param = connect_param;
    }

    public Integer getIs_ptz() {
        return is_ptz;
    }

    public void setIs_ptz(Integer is_ptz) {
        this.is_ptz = is_ptz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main == null ? null : main.trim();
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub == null ? null : sub.trim();
    }



    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        DeviceConfig other = (DeviceConfig) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getConnect_param() == null ? other.getConnect_param() == null : this.getConnect_param().equals(other.getConnect_param()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getMain() == null ? other.getMain() == null : this.getMain().equals(other.getMain()))
            && (this.getSub() == null ? other.getSub() == null : this.getSub().equals(other.getSub()))
            && (this.getIs_ptz() == null ? other.getIs_ptz() == null : this.getIs_ptz().equals(other.getIs_ptz()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getConnect_param() == null) ? 0 : getConnect_param().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getMain() == null) ? 0 : getMain().hashCode());
        result = prime * result + ((getSub() == null) ? 0 : getSub().hashCode());
        result = prime * result + ((getIs_ptz() == null) ? 0 : getIs_ptz().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", connectParam=").append(connect_param);
        sb.append(", name=").append(name);
        sb.append(", main=").append(main);
        sb.append(", sub=").append(sub);
        sb.append(", isPtz=").append(is_ptz);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}