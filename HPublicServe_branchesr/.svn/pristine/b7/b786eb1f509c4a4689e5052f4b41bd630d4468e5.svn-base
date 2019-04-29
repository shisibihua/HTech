package com.honghe.deviceNew.entity;

import java.io.Serializable;

public class DeviceEventfield implements Serializable {
    private Integer fieldId;

    private Integer fieldBelong;

    private String fieldName;

    private String fieldContent;

    private String fieldRemark;

    private static final long serialVersionUID = 1L;

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    public Integer getFieldBelong() {
        return fieldBelong;
    }

    public void setFieldBelong(Integer fieldBelong) {
        this.fieldBelong = fieldBelong;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName == null ? null : fieldName.trim();
    }

    public String getFieldContent() {
        return fieldContent;
    }

    public void setFieldContent(String fieldContent) {
        this.fieldContent = fieldContent == null ? null : fieldContent.trim();
    }

    public String getFieldRemark() {
        return fieldRemark;
    }

    public void setFieldRemark(String fieldRemark) {
        this.fieldRemark = fieldRemark == null ? null : fieldRemark.trim();
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
        DeviceEventfield other = (DeviceEventfield) that;
        return (this.getFieldId() == null ? other.getFieldId() == null : this.getFieldId().equals(other.getFieldId()))
            && (this.getFieldBelong() == null ? other.getFieldBelong() == null : this.getFieldBelong().equals(other.getFieldBelong()))
            && (this.getFieldName() == null ? other.getFieldName() == null : this.getFieldName().equals(other.getFieldName()))
            && (this.getFieldContent() == null ? other.getFieldContent() == null : this.getFieldContent().equals(other.getFieldContent()))
            && (this.getFieldRemark() == null ? other.getFieldRemark() == null : this.getFieldRemark().equals(other.getFieldRemark()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getFieldId() == null) ? 0 : getFieldId().hashCode());
        result = prime * result + ((getFieldBelong() == null) ? 0 : getFieldBelong().hashCode());
        result = prime * result + ((getFieldName() == null) ? 0 : getFieldName().hashCode());
        result = prime * result + ((getFieldContent() == null) ? 0 : getFieldContent().hashCode());
        result = prime * result + ((getFieldRemark() == null) ? 0 : getFieldRemark().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", fieldId=").append(fieldId);
        sb.append(", fieldBelong=").append(fieldBelong);
        sb.append(", fieldName=").append(fieldName);
        sb.append(", fieldContent=").append(fieldContent);
        sb.append(", fieldRemark=").append(fieldRemark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}