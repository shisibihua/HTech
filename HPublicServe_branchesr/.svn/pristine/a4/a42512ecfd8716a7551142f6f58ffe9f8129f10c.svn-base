package com.honghe.deviceNew.entity;

import java.io.Serializable;

public class DeviceHostevent implements Serializable {
    private Integer eventId;

    private String eventName;

    private String eventContent;

    private String eventType;

    private String eventTopic;

    private String eventDesc;

    private String eventRemark;

    private static final long serialVersionUID = 1L;

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName == null ? null : eventName.trim();
    }

    public String getEventContent() {
        return eventContent;
    }

    public void setEventContent(String eventContent) {
        this.eventContent = eventContent == null ? null : eventContent.trim();
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType == null ? null : eventType.trim();
    }

    public String getEventTopic() {
        return eventTopic;
    }

    public void setEventTopic(String eventTopic) {
        this.eventTopic = eventTopic == null ? null : eventTopic.trim();
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc == null ? null : eventDesc.trim();
    }

    public String getEventRemark() {
        return eventRemark;
    }

    public void setEventRemark(String eventRemark) {
        this.eventRemark = eventRemark == null ? null : eventRemark.trim();
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
        DeviceHostevent other = (DeviceHostevent) that;
        return (this.getEventId() == null ? other.getEventId() == null : this.getEventId().equals(other.getEventId()))
            && (this.getEventName() == null ? other.getEventName() == null : this.getEventName().equals(other.getEventName()))
            && (this.getEventContent() == null ? other.getEventContent() == null : this.getEventContent().equals(other.getEventContent()))
            && (this.getEventType() == null ? other.getEventType() == null : this.getEventType().equals(other.getEventType()))
            && (this.getEventTopic() == null ? other.getEventTopic() == null : this.getEventTopic().equals(other.getEventTopic()))
            && (this.getEventDesc() == null ? other.getEventDesc() == null : this.getEventDesc().equals(other.getEventDesc()))
            && (this.getEventRemark() == null ? other.getEventRemark() == null : this.getEventRemark().equals(other.getEventRemark()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getEventId() == null) ? 0 : getEventId().hashCode());
        result = prime * result + ((getEventName() == null) ? 0 : getEventName().hashCode());
        result = prime * result + ((getEventContent() == null) ? 0 : getEventContent().hashCode());
        result = prime * result + ((getEventType() == null) ? 0 : getEventType().hashCode());
        result = prime * result + ((getEventTopic() == null) ? 0 : getEventTopic().hashCode());
        result = prime * result + ((getEventDesc() == null) ? 0 : getEventDesc().hashCode());
        result = prime * result + ((getEventRemark() == null) ? 0 : getEventRemark().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", eventId=").append(eventId);
        sb.append(", eventName=").append(eventName);
        sb.append(", eventContent=").append(eventContent);
        sb.append(", eventType=").append(eventType);
        sb.append(", eventTopic=").append(eventTopic);
        sb.append(", eventDesc=").append(eventDesc);
        sb.append(", eventRemark=").append(eventRemark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}