package com.honghe.tech.entity;

/**
 * Created by xinqinggang on 2018/1/24.
 */
public class LiveStream {
    private int id;
    private String streamAddr;
    private int activityId;
    private String apareA;
    private String spareB;

    public LiveStream() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreamAddr() {
        return streamAddr;
    }

    public void setStreamAddr(String streamAddr) {
        this.streamAddr = streamAddr;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }
}
