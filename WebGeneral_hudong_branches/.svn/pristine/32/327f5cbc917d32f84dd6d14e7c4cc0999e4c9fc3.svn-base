package com.honghe.tech.entity;

import java.util.Date;

/**
 * 评论审核表
 * @author caoqian   20180124
 */
public class CommentCheck {
    private Integer checkId;         //key
    private Integer checkUserId;     //审核人id
    private Integer commentId;       //评论id
    private Integer status;          //评论审核状态，0：未审核，1：已审核
    private String spare;            //备注字段
    private Date checkTime;          //审核时间

    public CommentCheck() {
    }

    public CommentCheck(Integer checkId, Integer checkUserId, Integer commentId, Integer status, String spare, Date checkTime) {
        this.checkId = checkId;
        this.checkUserId = checkUserId;
        this.commentId = commentId;
        this.status = status;
        this.spare = spare;
        this.checkTime = checkTime;
    }

    public Integer getCheckId() {
        return checkId;
    }

    public void setCheckId(Integer checkId) {
        this.checkId = checkId;
    }

    public Integer getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(Integer checkUserId) {
        this.checkUserId = checkUserId;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSpare() {
        return spare;
    }

    public void setSpare(String spare) {
        this.spare = spare;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }
}
