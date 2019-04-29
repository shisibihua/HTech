package com.honghe.deviceNew.entity;

public class DeviceType {
    private Integer deviceTypeId;

    private String UpperCase;

    private String LowerCase;

    private String ChineseDesc;

    public Integer getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(Integer deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public String getUpperCase() {
        return UpperCase;
    }

    public void setUpperCase(String upperCase) {
        UpperCase = upperCase;
    }

    public String getLowerCase() {
        return LowerCase;
    }

    public void setLowerCase(String lowerCase) {
        LowerCase = lowerCase;
    }

    public String getChineseDesc() {
        return ChineseDesc;
    }

    public void setChineseDesc(String chineseDesc) {
        ChineseDesc = chineseDesc;
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
        DeviceType other = (DeviceType) that;
        return (this.getDeviceTypeId() == null ? other.getDeviceTypeId() == null : this.getDeviceTypeId().equals(other.getDeviceTypeId()))
                && (this.getUpperCase() == null ? other.getUpperCase() == null : this.getUpperCase().equals(other.getUpperCase()))
                && (this.getLowerCase() == null ? other.getLowerCase() == null : this.getLowerCase().equals(other.getLowerCase()))
                && (this.getChineseDesc() == null ? other.getChineseDesc() == null : this.getChineseDesc().equals(other.getChineseDesc()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDeviceTypeId() == null) ? 0 : getDeviceTypeId().hashCode());
        result = prime * result + ((getUpperCase() == null) ? 0 : getUpperCase().hashCode());
        result = prime * result + ((getLowerCase() == null) ? 0 : getLowerCase().hashCode());
        result = prime * result + ((getChineseDesc() == null) ? 0 : getChineseDesc().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", deviceTypeId=").append(deviceTypeId);
        sb.append(", UpperCase=").append(UpperCase);
        sb.append(", LowerCase=").append(LowerCase);
        sb.append(", ChineseDesc=").append(ChineseDesc);
        sb.append("]");
        return sb.toString();
    }
}
