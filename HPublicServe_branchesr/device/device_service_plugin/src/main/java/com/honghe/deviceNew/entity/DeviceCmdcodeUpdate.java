package com.honghe.deviceNew.entity;

import java.io.Serializable;

public class DeviceCmdcodeUpdate implements Serializable {
    private Integer id;

    private Integer dspecId;

    private String cmdcodeUpdate;

    private DeviceHost deviceHost;

    public DeviceHost getDeviceHost() {
        return deviceHost;
    }

    public void setDeviceHost(DeviceHost deviceHost) {
        this.deviceHost = deviceHost;
    }

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDspecId() {
        return dspecId;
    }

    public void setDspecId(Integer dspecId) {
        this.dspecId = dspecId;
    }

    public String getCmdcodeUpdate() {
        return cmdcodeUpdate;
    }

    public void setCmdcodeUpdate(String cmdcodeUpdate) {
        this.cmdcodeUpdate = cmdcodeUpdate == null ? null : cmdcodeUpdate.trim();
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
        DeviceCmdcodeUpdate other = (DeviceCmdcodeUpdate) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getDspecId() == null ? other.getDspecId() == null : this.getDspecId().equals(other.getDspecId()))
            && (this.getCmdcodeUpdate() == null ? other.getCmdcodeUpdate() == null : this.getCmdcodeUpdate().equals(other.getCmdcodeUpdate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getDspecId() == null) ? 0 : getDspecId().hashCode());
        result = prime * result + ((getCmdcodeUpdate() == null) ? 0 : getCmdcodeUpdate().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", dspecId=").append(dspecId);
        sb.append(", cmdcodeUpdate=").append(cmdcodeUpdate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}