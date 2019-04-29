package com.honghe.deviceNew.entity;

import java.io.Serializable;

public class DeviceCommand implements Serializable {
    private Integer cmdId;

    private String cmdName;

    private String cmdGroup;

    private String cmdImage;

    private Boolean cmdDefault;

    private String cmdFlag;

    private String cmdHex;

    private static final long serialVersionUID = 1L;

    public Integer getCmdId() {
        return cmdId;
    }

    public void setCmdId(Integer cmdId) {
        this.cmdId = cmdId;
    }

    public String getCmdName() {
        return cmdName;
    }

    public void setCmdName(String cmdName) {
        this.cmdName = cmdName == null ? null : cmdName.trim();
    }

    public String getCmdGroup() {
        return cmdGroup;
    }

    public void setCmdGroup(String cmdGroup) {
        this.cmdGroup = cmdGroup == null ? null : cmdGroup.trim();
    }

    public String getCmdImage() {
        return cmdImage;
    }

    public void setCmdImage(String cmdImage) {
        this.cmdImage = cmdImage == null ? null : cmdImage.trim();
    }

    public Boolean getCmdDefault() {
        return cmdDefault;
    }

    public void setCmdDefault(Boolean cmdDefault) {
        this.cmdDefault = cmdDefault;
    }

    public String getCmdFlag() {
        return cmdFlag;
    }

    public void setCmdFlag(String cmdFlag) {
        this.cmdFlag = cmdFlag == null ? null : cmdFlag.trim();
    }

    public String getCmdHex() {
        return cmdHex;
    }

    public void setCmdHex(String cmdHex) {
        this.cmdHex = cmdHex == null ? null : cmdHex.trim();
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
        DeviceCommand other = (DeviceCommand) that;
        return (this.getCmdId() == null ? other.getCmdId() == null : this.getCmdId().equals(other.getCmdId()))
            && (this.getCmdName() == null ? other.getCmdName() == null : this.getCmdName().equals(other.getCmdName()))
            && (this.getCmdGroup() == null ? other.getCmdGroup() == null : this.getCmdGroup().equals(other.getCmdGroup()))
            && (this.getCmdImage() == null ? other.getCmdImage() == null : this.getCmdImage().equals(other.getCmdImage()))
            && (this.getCmdDefault() == null ? other.getCmdDefault() == null : this.getCmdDefault().equals(other.getCmdDefault()))
            && (this.getCmdFlag() == null ? other.getCmdFlag() == null : this.getCmdFlag().equals(other.getCmdFlag()))
            && (this.getCmdHex() == null ? other.getCmdHex() == null : this.getCmdHex().equals(other.getCmdHex()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCmdId() == null) ? 0 : getCmdId().hashCode());
        result = prime * result + ((getCmdName() == null) ? 0 : getCmdName().hashCode());
        result = prime * result + ((getCmdGroup() == null) ? 0 : getCmdGroup().hashCode());
        result = prime * result + ((getCmdImage() == null) ? 0 : getCmdImage().hashCode());
        result = prime * result + ((getCmdDefault() == null) ? 0 : getCmdDefault().hashCode());
        result = prime * result + ((getCmdFlag() == null) ? 0 : getCmdFlag().hashCode());
        result = prime * result + ((getCmdHex() == null) ? 0 : getCmdHex().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", cmdId=").append(cmdId);
        sb.append(", cmdName=").append(cmdName);
        sb.append(", cmdGroup=").append(cmdGroup);
        sb.append(", cmdImage=").append(cmdImage);
        sb.append(", cmdDefault=").append(cmdDefault);
        sb.append(", cmdFlag=").append(cmdFlag);
        sb.append(", cmdHex=").append(cmdHex);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}