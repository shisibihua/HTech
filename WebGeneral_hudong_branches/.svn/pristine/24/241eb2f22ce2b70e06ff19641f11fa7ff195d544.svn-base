package com.honghe.tech.dao;

import com.honghe.spring.MybatisDao;
import com.honghe.tech.entity.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author caoqian  20180124.
 */
@MybatisDao
public interface CommentDao {
    /**
     * 保存评论
     * @param comment  评论实体
     * @return
     */
     boolean saveComment(Comment comment);

    /**
     * 根据评论id，回复主评论id，用户id删除评论
     * @param commentId   评论id
     * @param replayId    主评论id
     * @param userId      用户i
     * @param flag        是否删除评论，true删除，false不删除
     * @return
     */
     boolean deleteCommentByCondition(@Param("commentId") int commentId,@Param("replayId") int replayId,
                                            @Param("userId") int userId,@Param("flag") boolean flag);

    /**
     * 分页获取直播评论
     * @param liveStreamId     直播评论id
     * @param firstResult      从第几条开始
     * @param pageSize         每页条数
     * @param pageBool         是否分页,true分页，false不分页
     * @return
     */
     List<Map<String,Object>> commentListByPage(@Param("activityId")String liveStreamId,
                                                      @Param("firstResult") Integer firstResult,
                                                      @Param("pageSize") Integer pageSize,
                                                      @Param("pageBool") boolean pageBool);

    /**
     * 根据评论id查询评论
     * @param commentId 评论id
     * @return 评论信息
     */
     Comment selectCommentById(@Param("commentId") int commentId);

    /**
     * 根据评论id获取回复评论的列表
     * @param commentId 评论id
     * @return
     */
     List<Map<String,Object>> getReplayCommentListById(@Param("commentId") String commentId);

}
