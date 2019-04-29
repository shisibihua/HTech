package com.honghe.tech.service;

import java.util.Map;

/**
 *
 * @author caoqian
 * @date 2018/1/23
 */
public interface CommentService {
    /**
     * 保存评论
     * @param userId          评论用户id
     * @param replayId        评论id，发布的评论默认为0
     * @param activityId    活动id
     * @param visible         评论是否可见，0不可见，1可见
     * @param content         评论内容
     * @return boolean        true:保存评论成功；false:保存评论失败
     * @author caoqian      20180125
     */
    public boolean saveCommentTable(String userId,String replayId,String activityId,String visible,String content);

    /**
     * 删除评论
     * @param commentId
     * @param replayId
     * @param userId
     * @return
     */
    public boolean deleteCommentTable(String commentId,String replayId,String userId);

    /**
     * 获取评论列表
     * @param activityId
     * @param currentPage
     * @param pageSize
     * @return
     */
    public Map<String,Object> getCommentsListByPage(String activityId, String currentPage, String pageSize);
}
