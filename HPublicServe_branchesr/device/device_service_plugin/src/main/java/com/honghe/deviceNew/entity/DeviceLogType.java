package com.honghe.deviceNew.entity;

import java.io.Serializable;

public class DeviceLogType implements Serializable {
    private Integer logtypeId;

    private String logtypeKey;

    private String logtypeName;

    private static final long serialVersionUID = 1L;

    public Integer getLogtypeId() {
        return logtypeId;
    }

    public void setLogtypeId(Integer logtypeId) {
        this.logtypeId = logtypeId;
    }

    public String getLogtypeKey() {
        return logtypeKey;
    }

    public void setLogtypeKey(String logtypeKey) {
        this.logtypeKey = logtypeKey == null ? null : logtypeKey.trim();
    }

    public String getLogtypeName() {
        return logtypeName;
    }

    public void setLogtypeName(String logtypeName) {
        this.logtypeName = logtypeName == null ? null : logtypeName.trim();
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
        DeviceLogType other = (DeviceLogType) that;
        return (this.getLogtypeId() == null ? other.getLogtypeId() == null : this.getLogtypeId().equals(other.getLogtypeId()))
            && (this.getLogtypeKey() == null ? other.getLogtypeKey() == null : this.getLogtypeKey().equals(other.getLogtypeKey()))
            && (this.getLogtypeName() == null ? other.getLogtypeName() == null : this.getLogtypeName().equals(other.getLogtypeName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getLogtypeId() == null) ? 0 : getLogtypeId().hashCode());
        result = prime * result + ((getLogtypeKey() == null) ? 0 : getLogtypeKey().hashCode());
        result = prime * result + ((getLogtypeName() == null) ? 0 : getLogtypeName().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", logtypeId=").append(logtypeId);
        sb.append(", logtypeKey=").append(logtypeKey);
        sb.append(", logtypeName=").append(logtypeName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}