package com.honghe.deviceNew.entity;

import java.io.Serializable;

public class DeviceSpecification implements Serializable {
    private Integer dspecId;

    private String dspecName;

    private Integer dtypeId;

    private String connectParam;

    private Integer recordType;

    private Integer typeint;

    private static final long serialVersionUID = 1L;

    public Integer getDspecId() {
        return dspecId;
    }

    public void setDspecId(Integer dspecId) {
        this.dspecId = dspecId;
    }

    public String getDspecName() {
        return dspecName;
    }

    public void setDspecName(String dspecName) {
        this.dspecName = dspecName == null ? null : dspecName.trim();
    }

    public Integer getDtypeId() {
        return dtypeId;
    }

    public void setDtypeId(Integer dtypeId) {
        this.dtypeId = dtypeId;
    }

    public String getConnectParam() {
        return connectParam;
    }

    public void setConnectParam(String connectParam) {
        this.connectParam = connectParam == null ? null : connectParam.trim();
    }

    public Integer getRecordType() {
        return recordType;
    }

    public void setRecordType(Integer recordType) {
        this.recordType = recordType;
    }

    public Integer getTypeint() {
        return typeint;
    }

    public void setTypeint(Integer typeint) {
        this.typeint = typeint;
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
        DeviceSpecification other = (DeviceSpecification) that;
        return (this.getDspecId() == null ? other.getDspecId() == null : this.getDspecId().equals(other.getDspecId()))
            && (this.getDspecName() == null ? other.getDspecName() == null : this.getDspecName().equals(other.getDspecName()))
            && (this.getDtypeId() == null ? other.getDtypeId() == null : this.getDtypeId().equals(other.getDtypeId()))
            && (this.getConnectParam() == null ? other.getConnectParam() == null : this.getConnectParam().equals(other.getConnectParam()))
            && (this.getRecordType() == null ? other.getRecordType() == null : this.getRecordType().equals(other.getRecordType()))
            && (this.getTypeint() == null ? other.getTypeint() == null : this.getTypeint().equals(other.getTypeint()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDspecId() == null) ? 0 : getDspecId().hashCode());
        result = prime * result + ((getDspecName() == null) ? 0 : getDspecName().hashCode());
        result = prime * result + ((getDtypeId() == null) ? 0 : getDtypeId().hashCode());
        result = prime * result + ((getConnectParam() == null) ? 0 : getConnectParam().hashCode());
        result = prime * result + ((getRecordType() == null) ? 0 : getRecordType().hashCode());
        result = prime * result + ((getTypeint() == null) ? 0 : getTypeint().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", dspecId=").append(dspecId);
        sb.append(", dspecName=").append(dspecName);
        sb.append(", dtypeId=").append(dtypeId);
        sb.append(", connectParam=").append(connectParam);
        sb.append(", recordType=").append(recordType);
        sb.append(", typeint=").append(typeint);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}