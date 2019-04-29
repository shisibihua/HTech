package com.honghe.deviceNew.entity;

import java.io.Serializable;
import java.util.Date;

public class DeviceDevstate implements Serializable {
    private String id;

    private Integer centerid;

    private Integer deviceid;

    private Integer status;

    private Date statustime;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getCenterid() {
        return centerid;
    }

    public void setCenterid(Integer centerid) {
        this.centerid = centerid;
    }

    public Integer getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(Integer deviceid) {
        this.deviceid = deviceid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStatustime() {
        return statustime;
    }

    public void setStatustime(Date statustime) {
        this.statustime = statustime;
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
        DeviceDevstate other = (DeviceDevstate) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCenterid() == null ? other.getCenterid() == null : this.getCenterid().equals(other.getCenterid()))
            && (this.getDeviceid() == null ? other.getDeviceid() == null : this.getDeviceid().equals(other.getDeviceid()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getStatustime() == null ? other.getStatustime() == null : this.getStatustime().equals(other.getStatustime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCenterid() == null) ? 0 : getCenterid().hashCode());
        result = prime * result + ((getDeviceid() == null) ? 0 : getDeviceid().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getStatustime() == null) ? 0 : getStatustime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", centerid=").append(centerid);
        sb.append(", deviceid=").append(deviceid);
        sb.append(", status=").append(status);
        sb.append(", statustime=").append(statustime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}