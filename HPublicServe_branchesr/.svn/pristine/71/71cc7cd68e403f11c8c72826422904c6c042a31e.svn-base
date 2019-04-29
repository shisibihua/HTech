package com.honghe.deviceNew.entity;

import java.io.Serializable;

public class Device implements Serializable {
    private Integer deviceId;

    private String deviceName;

    private String deviceMaintoken;

    private String deviceSubtoken;

    private Integer hostId;

    private String deviceMainstream;

    private String deviceSubstream;

    private static final long serialVersionUID = 1L;

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
    }

    public String getDeviceMaintoken() {
        return deviceMaintoken;
    }

    public void setDeviceMaintoken(String deviceMaintoken) {
        this.deviceMaintoken = deviceMaintoken == null ? null : deviceMaintoken.trim();
    }

    public String getDeviceSubtoken() {
        return deviceSubtoken;
    }

    public void setDeviceSubtoken(String deviceSubtoken) {
        this.deviceSubtoken = deviceSubtoken == null ? null : deviceSubtoken.trim();
    }

    public Integer getHostId() {
        return hostId;
    }

    public void setHostId(Integer hostId) {
        this.hostId = hostId;
    }

    public String getDeviceMainstream() {
        return deviceMainstream;
    }

    public void setDeviceMainstream(String deviceMainstream) {
        this.deviceMainstream = deviceMainstream == null ? null : deviceMainstream.trim();
    }

    public String getDeviceSubstream() {
        return deviceSubstream;
    }

    public void setDeviceSubstream(String deviceSubstream) {
        this.deviceSubstream = deviceSubstream == null ? null : deviceSubstream.trim();
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
        Device other = (Device) that;
        return (this.getDeviceId() == null ? other.getDeviceId() == null : this.getDeviceId().equals(other.getDeviceId()))
            && (this.getDeviceName() == null ? other.getDeviceName() == null : this.getDeviceName().equals(other.getDeviceName()))
            && (this.getDeviceMaintoken() == null ? other.getDeviceMaintoken() == null : this.getDeviceMaintoken().equals(other.getDeviceMaintoken()))
            && (this.getDeviceSubtoken() == null ? other.getDeviceSubtoken() == null : this.getDeviceSubtoken().equals(other.getDeviceSubtoken()))
            && (this.getHostId() == null ? other.getHostId() == null : this.getHostId().equals(other.getHostId()))
            && (this.getDeviceMainstream() == null ? other.getDeviceMainstream() == null : this.getDeviceMainstream().equals(other.getDeviceMainstream()))
            && (this.getDeviceSubstream() == null ? other.getDeviceSubstream() == null : this.getDeviceSubstream().equals(other.getDeviceSubstream()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDeviceId() == null) ? 0 : getDeviceId().hashCode());
        result = prime * result + ((getDeviceName() == null) ? 0 : getDeviceName().hashCode());
        result = prime * result + ((getDeviceMaintoken() == null) ? 0 : getDeviceMaintoken().hashCode());
        result = prime * result + ((getDeviceSubtoken() == null) ? 0 : getDeviceSubtoken().hashCode());
        result = prime * result + ((getHostId() == null) ? 0 : getHostId().hashCode());
        result = prime * result + ((getDeviceMainstream() == null) ? 0 : getDeviceMainstream().hashCode());
        result = prime * result + ((getDeviceSubstream() == null) ? 0 : getDeviceSubstream().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", deviceId=").append(deviceId);
        sb.append(", deviceName=").append(deviceName);
        sb.append(", deviceMaintoken=").append(deviceMaintoken);
        sb.append(", deviceSubtoken=").append(deviceSubtoken);
        sb.append(", hostId=").append(hostId);
        sb.append(", deviceMainstream=").append(deviceMainstream);
        sb.append(", deviceSubstream=").append(deviceSubstream);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}