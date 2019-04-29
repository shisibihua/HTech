package com.honghe.deviceNew.entity;

import java.io.Serializable;
import java.util.Date;

public class DeviceDloadparmRs extends DeviceDloadparmRsKey implements Serializable {
    private Integer centerid;

    private Integer deviceid;

    private String devicemodel;

    private Date downloadtime;

    private Date occurtime;

    private String operator;

    private Integer status;

    private Integer type;

    private String uuid;

    private static final long serialVersionUID = 1L;

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

    public String getDevicemodel() {
        return devicemodel;
    }

    public void setDevicemodel(String devicemodel) {
        this.devicemodel = devicemodel == null ? null : devicemodel.trim();
    }

    public Date getDownloadtime() {
        return downloadtime;
    }

    public void setDownloadtime(Date downloadtime) {
        this.downloadtime = downloadtime;
    }

    public Date getOccurtime() {
        return occurtime;
    }

    public void setOccurtime(Date occurtime) {
        this.occurtime = occurtime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
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
        DeviceDloadparmRs other = (DeviceDloadparmRs) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getDeviceno() == null ? other.getDeviceno() == null : this.getDeviceno().equals(other.getDeviceno()))
            && (this.getCenterid() == null ? other.getCenterid() == null : this.getCenterid().equals(other.getCenterid()))
            && (this.getDeviceid() == null ? other.getDeviceid() == null : this.getDeviceid().equals(other.getDeviceid()))
            && (this.getDevicemodel() == null ? other.getDevicemodel() == null : this.getDevicemodel().equals(other.getDevicemodel()))
            && (this.getDownloadtime() == null ? other.getDownloadtime() == null : this.getDownloadtime().equals(other.getDownloadtime()))
            && (this.getOccurtime() == null ? other.getOccurtime() == null : this.getOccurtime().equals(other.getOccurtime()))
            && (this.getOperator() == null ? other.getOperator() == null : this.getOperator().equals(other.getOperator()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getUuid() == null ? other.getUuid() == null : this.getUuid().equals(other.getUuid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getDeviceno() == null) ? 0 : getDeviceno().hashCode());
        result = prime * result + ((getCenterid() == null) ? 0 : getCenterid().hashCode());
        result = prime * result + ((getDeviceid() == null) ? 0 : getDeviceid().hashCode());
        result = prime * result + ((getDevicemodel() == null) ? 0 : getDevicemodel().hashCode());
        result = prime * result + ((getDownloadtime() == null) ? 0 : getDownloadtime().hashCode());
        result = prime * result + ((getOccurtime() == null) ? 0 : getOccurtime().hashCode());
        result = prime * result + ((getOperator() == null) ? 0 : getOperator().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getUuid() == null) ? 0 : getUuid().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", centerid=").append(centerid);
        sb.append(", deviceid=").append(deviceid);
        sb.append(", devicemodel=").append(devicemodel);
        sb.append(", downloadtime=").append(downloadtime);
        sb.append(", occurtime=").append(occurtime);
        sb.append(", operator=").append(operator);
        sb.append(", status=").append(status);
        sb.append(", type=").append(type);
        sb.append(", uuid=").append(uuid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}