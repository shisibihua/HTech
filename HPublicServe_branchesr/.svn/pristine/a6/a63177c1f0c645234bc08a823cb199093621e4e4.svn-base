package com.honghe.deviceNew.entity;

import java.io.Serializable;

public class DeviceMaxCode implements Serializable {
    private Integer codeId;//已添加设备的最大编码id
    private String maxCode;//已添加设备的最大编码
    private String deviceType;//设备类型

    public Integer getCodeId() {
        return codeId;
    }

    public void setCodeId(Integer codeId) {
        this.codeId = codeId;
    }

    public String getMaxCode() {
        return maxCode;
    }

    public void setMaxCode(String maxCode) {
        this.maxCode = maxCode;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
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
        DeviceMaxCode other = (DeviceMaxCode) that;
        return (this.getCodeId() == null ? other.getCodeId() == null : this.getCodeId().equals(other.getCodeId()))
                && (this.getMaxCode() == null ? other.getMaxCode() == null : this.getMaxCode().equals(other.getMaxCode()))
                && (this.getDeviceType() == null ? other.getDeviceType() == null : this.getDeviceType().equals(other.getDeviceType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCodeId() == null) ? 0 : getCodeId().hashCode());
        result = prime * result + ((getMaxCode() == null) ? 0 : getMaxCode().hashCode());
        result = prime * result + ((getDeviceType() == null) ? 0 : getDeviceType().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", codeId=").append(codeId);
        sb.append(", maxCode=").append(maxCode);
        sb.append(", deviceType=").append(deviceType);
        sb.append("]");
        return sb.toString();
    }
}
