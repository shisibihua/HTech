package com.honghe.deviceNew.entity;

import java.io.Serializable;

public class DeviceCommandCode implements Serializable {
    private Integer codeId;

    private String codeName;

    private String codeType;

    private String codeResult;

    private String codeRemark;

    private String codeInstruction;

    private Integer dspecId;

    private String codeCode;

    private String codeFlag;

    private String codeGroup;

    private static final long serialVersionUID = 1L;

    public Integer getCodeId() {
        return codeId;
    }

    public void setCodeId(Integer codeId) {
        this.codeId = codeId;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName == null ? null : codeName.trim();
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType == null ? null : codeType.trim();
    }

    public String getCodeResult() {
        return codeResult;
    }

    public void setCodeResult(String codeResult) {
        this.codeResult = codeResult == null ? null : codeResult.trim();
    }

    public String getCodeRemark() {
        return codeRemark;
    }

    public void setCodeRemark(String codeRemark) {
        this.codeRemark = codeRemark == null ? null : codeRemark.trim();
    }

    public String getCodeInstruction() {
        return codeInstruction;
    }

    public void setCodeInstruction(String codeInstruction) {
        this.codeInstruction = codeInstruction == null ? null : codeInstruction.trim();
    }

    public Integer getDspecId() {
        return dspecId;
    }

    public void setDspecId(Integer dspecId) {
        this.dspecId = dspecId;
    }

    public String getCodeCode() {
        return codeCode;
    }

    public void setCodeCode(String codeCode) {
        this.codeCode = codeCode == null ? null : codeCode.trim();
    }

    public String getCodeFlag() {
        return codeFlag;
    }

    public void setCodeFlag(String codeFlag) {
        this.codeFlag = codeFlag == null ? null : codeFlag.trim();
    }

    public String getCodeGroup() {
        return codeGroup;
    }

    public void setCodeGroup(String codeGroup) {
        this.codeGroup = codeGroup == null ? null : codeGroup.trim();
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
        DeviceCommandCode other = (DeviceCommandCode) that;
        return (this.getCodeId() == null ? other.getCodeId() == null : this.getCodeId().equals(other.getCodeId()))
            && (this.getCodeName() == null ? other.getCodeName() == null : this.getCodeName().equals(other.getCodeName()))
            && (this.getCodeType() == null ? other.getCodeType() == null : this.getCodeType().equals(other.getCodeType()))
            && (this.getCodeResult() == null ? other.getCodeResult() == null : this.getCodeResult().equals(other.getCodeResult()))
            && (this.getCodeRemark() == null ? other.getCodeRemark() == null : this.getCodeRemark().equals(other.getCodeRemark()))
            && (this.getCodeInstruction() == null ? other.getCodeInstruction() == null : this.getCodeInstruction().equals(other.getCodeInstruction()))
            && (this.getDspecId() == null ? other.getDspecId() == null : this.getDspecId().equals(other.getDspecId()))
            && (this.getCodeCode() == null ? other.getCodeCode() == null : this.getCodeCode().equals(other.getCodeCode()))
            && (this.getCodeFlag() == null ? other.getCodeFlag() == null : this.getCodeFlag().equals(other.getCodeFlag()))
            && (this.getCodeGroup() == null ? other.getCodeGroup() == null : this.getCodeGroup().equals(other.getCodeGroup()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCodeId() == null) ? 0 : getCodeId().hashCode());
        result = prime * result + ((getCodeName() == null) ? 0 : getCodeName().hashCode());
        result = prime * result + ((getCodeType() == null) ? 0 : getCodeType().hashCode());
        result = prime * result + ((getCodeResult() == null) ? 0 : getCodeResult().hashCode());
        result = prime * result + ((getCodeRemark() == null) ? 0 : getCodeRemark().hashCode());
        result = prime * result + ((getCodeInstruction() == null) ? 0 : getCodeInstruction().hashCode());
        result = prime * result + ((getDspecId() == null) ? 0 : getDspecId().hashCode());
        result = prime * result + ((getCodeCode() == null) ? 0 : getCodeCode().hashCode());
        result = prime * result + ((getCodeFlag() == null) ? 0 : getCodeFlag().hashCode());
        result = prime * result + ((getCodeGroup() == null) ? 0 : getCodeGroup().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", codeId=").append(codeId);
        sb.append(", codeName=").append(codeName);
        sb.append(", codeType=").append(codeType);
        sb.append(", codeResult=").append(codeResult);
        sb.append(", codeRemark=").append(codeRemark);
        sb.append(", codeInstruction=").append(codeInstruction);
        sb.append(", dspecId=").append(dspecId);
        sb.append(", codeCode=").append(codeCode);
        sb.append(", codeFlag=").append(codeFlag);
        sb.append(", codeGroup=").append(codeGroup);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}