package com.honghe.tech.service.impl;

import com.honghe.tech.dao.CommentDao;
import com.honghe.tech.entity.Comment;
import com.honghe.tech.httpservice.UserHttpService;
import com.honghe.tech.service.CommentService;
import com.honghe.tech.service.OperLogService;
import com.honghe.tech.util.SensitivewordFilter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author caoqian
 * @date 2018/1/23
 */
@Service
public class CommentServiceImpl implements CommentService {
    private Logger logger=Logger.getLogger(CommentServiceImpl.class);
    private static final SimpleDateFormat DATE_FORMAT=new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final SimpleDateFormat TIME_FORMAT=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String MODULE_NAME="直播";
    private static final String MODULE_CONTENT_ADD_SUCCESS="用户成功发布一条评论";
    private static final String MODULE_CONTENT_ADD_FAILED="用户发布一条评论失败";
    private static final String MODULE_CONTENT_DELETE_SUCCESS="用户成功删除一条评论";
    private static final String MODULE_CONTENT_DELETE_FAILED="用户删除一条评论失败";
    private static final String ZERO = "0";
    private static final String ONE = "1";
    /**
     * 操作日志
     */
    private static final String LOG_TYPE="0";
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private UserHttpService userHttpService;
    @Autowired
    private OperLogService operLogService;

    /**
     * 保存评论
     * @param userId          评论用户id
     * @param replayId        评论id，发布的评论默认为0
     * @param activityId      活动id
     * @param visible         评论是否可见，0不可见，1可见
     * @param content         评论内容
     * @return boolean        true:保存评论成功；false:保存评论失败
     * @author caoqian      20180125
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveCommentTable(String userId,String replayId,String activityId,String visible,String content){
        boolean returnResult=false;
        if(userId==null || "".equals(userId) || activityId==null || "".equals(activityId)){
            logger.debug("用户id:"+userId+",活动id:"+activityId+",保存评论失败，参数异常。");
            throw new IllegalArgumentException();
        }else {
            try {
                Comment comment = new Comment();
                comment.setActivityId(Integer.parseInt(activityId));
                //保存的评论
                if (replayId == null || "".equals(replayId)) {
                    comment.setCommentUserId(Integer.parseInt(userId));
                    //保存的回复评论
                    comment.setReplayId(0);
                } else if (replayId != null && !"".equals(replayId)) {
                    comment.setCommentUserId(Integer.parseInt(userId));
                    comment.setReplayId(Integer.parseInt(replayId));
                } else {
                    logger.error("保存评论失败,userId=" + userId + ",replayId=" + replayId);
                    return false;
                }
                //屏蔽关键字操作
                comment.setContent(shieldKeyWordsStr(content));
                // TODO: 2018/5/31 判断条件赋值给了exited 
                boolean exited = (visible == null || "".equals(visible)) || (!ZERO.equals(visible) && !ONE.equals(visible));
                if (exited) {
                    //默认可见
                    comment.setVisible(1);
                } else {
                    comment.setVisible(Integer.parseInt(visible));
                }
                comment.setUpdateTime(TIME_FORMAT.parse(TIME_FORMAT.format(new Date())));
                returnResult = commentDao.saveComment(comment);
            } catch (Exception e) {
                logger.error("保存评论异常，userId=" + userId + ",content=" + content, e);
                returnResult = false;
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();   //事物回滚
            }
            Map<String,Object> logParams=new HashMap<>();
            logParams.put("userId", userId);
            Date now=new Date();
            logParams.put("logTime", TIME_FORMAT.format(now));
            logParams.put("moduleName",MODULE_NAME);
            logParams.put("type", LOG_TYPE);
            if(returnResult){
                logParams.put("content",MODULE_CONTENT_ADD_SUCCESS);
            }else{
                logParams.put("content",MODULE_CONTENT_ADD_FAILED);
            }
            operLogService.saveLogTable(logParams);
            return returnResult;
        }
    }

    /**
     * 屏蔽评论中的关键字
     * @param content 内容
     * @return 屏蔽之后的内容
     */
    private String shieldKeyWordsStr(String content){
        SensitivewordFilter filter = new SensitivewordFilter();
        content=filter.replaceSensitiveWord(content,1,"*");
        return content;
    }
    /**
     * 根据评论id删除评论
     * @param commentId  评论id
     * @param replayId   回复评论id，如果是主评论，replayId=0
     * @param userId     用户id
     * @return boolean  true:删除成功;false：删除失败
     * @author  caoqian
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteCommentTable(String commentId,String replayId,String userId){
        boolean returnResult=false;
        if(commentId!=null && !"".equals(commentId) && replayId!=null && !"".equals(replayId) && userId!=null&&!"".equals(userId))
        {
            boolean flag = false;
            try {
                //根据评论id获取评论
                Comment comment = commentDao.selectCommentById(Integer.valueOf(commentId));
                if (comment != null) {
                    //用户自身发布的评论，可以删除，否则没有权限删除
                    if (comment.getCommentUserId().equals(Integer.valueOf(userId))) {
                        flag = true;
                    } else {
                        flag = false;
                    }
                }
                returnResult = commentDao.deleteCommentByCondition(Integer.parseInt(commentId), Integer.parseInt(replayId), Integer.parseInt(userId), flag);
            }catch (Exception e){
                logger.error("根据评论id删除评论异常,id="+commentId,e);
                returnResult=false;
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();   //事物回滚
            }
            Map<String,Object> logParams=new HashMap<>();
            logParams.put("userId", userId);
            Date now=new Date();
            logParams.put("logTime", TIME_FORMAT.format(now));
            logParams.put("moduleName",MODULE_NAME);
            logParams.put("type", LOG_TYPE);
            if(returnResult){
                logParams.put("content",MODULE_CONTENT_DELETE_SUCCESS);
            }else{
                logParams.put("content",MODULE_CONTENT_DELETE_FAILED);
            }
            operLogService.saveLogTable(logParams);
            return returnResult;
        }else{
            logger.debug("评论id="+commentId+",replayId="+replayId+",userId="+userId+"，无法删除评论，参数异常。");
            throw new IllegalArgumentException();
        }
    }

    /**
     * 分页获取直播评论
     * @param activityId         活动id
     * @param currentPage          当前页
     * @param pageSize             每页条数
     * @return  map                评论数据
     * @author caoqian
     */
    @Override
    public Map<String, Object> getCommentsListByPage(String activityId, String currentPage, String pageSize){
        Map<String,Object> resultMap=new HashMap<>();
        if(activityId==null || "".equals(activityId) || currentPage==null || "".equals(currentPage) || pageSize==null || "".equals(pageSize)){
            logger.debug("活动id:"+activityId+",当前页currentPage:"+currentPage+",每页条数pageSize:"+pageSize+",分页获取直播评论失败，参数异常。");
            throw new IllegalArgumentException();
        }else {
            resultMap.put("currentPage", currentPage);
            resultMap.put("pageSize", pageSize);
            List<Map<String, Object>> resultList = new ArrayList<>();
            try {
                //获取第一条开始的位置
                int firstResult = (Integer.parseInt(currentPage) - 1) * Integer.parseInt(pageSize) < 0 ? 0 : (Integer.parseInt(currentPage) - 1) * Integer.parseInt(pageSize);
                //主评论数据
                List<Map<String, Object>> commentDataList = commentDao.commentListByPage(activityId, firstResult, Integer.parseInt(pageSize), true);
                //主评论总条数
                int totalCount = commentDao.commentListByPage(activityId, firstResult, Integer.parseInt(pageSize), false).size();
                if (commentDataList != null && !commentDataList.isEmpty()) {
                    Map<String,Object> userInfo=userHttpService.getAllUserInfo();
                    for (Map<String, Object> map : commentDataList) {
                        Map<String, Object> commentMap = new HashMap<>();
                        String commentId = "";
                        if (map.get("comment_id") != null) {
                            commentId = String.valueOf(map.get("comment_id"));
                        }
                        commentMap.put("id", commentId);
                        List<Map<String, Object>> replayList = getReplayCommentList(commentId);
                        commentMap.put("replayNum", String.valueOf(replayList.size()));
                        commentMap.put("replayDataList", replayList);
                        String userId = map.get("user_id") == null ? "0" : String.valueOf(map.get("user_id"));
                        commentMap.put("userId", userId);
                        if (userInfo != null && !userInfo.isEmpty() && userInfo.get(userId)!=null) {
                            Map<String,String> user=(Map<String,String>)userInfo.get(userId);
                            commentMap.put("userName", user.get("userRealName")==null?"":user.get("userRealName"));
                        } else {
                            commentMap.put("userName", "");
                        }
                        Map<String,Object> userPower=userHttpService.getAreaByUserId(userId);
                        if(userPower!=null)
                        {
                            commentMap.put("provinceId",userPower.get("provinceId")==null?"":userPower.get("provinceId").toString());
                            commentMap.put("cityId",userPower.get("cityId")==null?"":userPower.get("cityId").toString());
                            commentMap.put("countyId",userPower.get("countyId")==null?"":userPower.get("countyId").toString());
                            commentMap.put("schoolId",userPower.get("schoolId")==null?"":userPower.get("schoolId").toString());
                        }
                        commentMap.put("content", map.get("content") == null ? "" : map.get("content").toString());
                        commentMap.put("updateTime", map.get("update_time") == null ? "" : DATE_FORMAT.format(DATE_FORMAT.parse(map.get("update_time").toString())));
                        //0:不可见；1：可见；默认为1
                        commentMap.put("visible", map.get("visible") == null ? "" : String.valueOf(map.get("visible")));
                        commentMap.put("replayId", map.get("replay_id") == null ? "" : String.valueOf(map.get("replay_id")));
                        resultList.add(commentMap);
                    }
                }
                resultMap.put("total", totalCount);
                resultMap.put("dataList", resultList);
            } catch (Exception e) {
                resultMap.put("total", "");
                resultMap.put("dataList", "");
                logger.error("分页获取直播评论异常，result=" + resultMap.toString(), e);
            }
        }
        return resultMap;
    }

    /**
     * 根据评论id获取回复评论的列表
     * @param commentId 评论id
     * @return
     */
    private List<Map<String,Object>> getReplayCommentList(String commentId){
        List<Map<String,Object>> comment=null;
        if(commentId!=null && !"".equals(commentId)){
            comment=commentDao.getReplayCommentListById(commentId);
        }else{
            comment=new ArrayList<>();
        }
        if(comment!=null && !comment.isEmpty()){
            Map<String,Object> userInfo=userHttpService.getAllUserInfo();
            for(Map<String,Object> map:comment){
                String userId=String.valueOf(map.get("userId"));
                map.put("userId",userId);
                if(userInfo!=null && !userInfo.isEmpty()&&userInfo.get(userId)!=null){
                    Map<String,String> user=(Map<String,String>)userInfo.get(userId);
                    map.put("userName",user.get("userRealName")==null?"":user.get("userRealName"));
                }else{
                    map.put("userName","");
                }
                Map<String,Object> userPower=userHttpService.getAreaByUserId(userId);
                if(userPower!=null)
                {
                    map.put("provinceId",userPower.get("provinceId")==null?"":userPower.get("provinceId").toString());
                    map.put("cityId",userPower.get("cityId")==null?"":userPower.get("cityId").toString());
                    map.put("countyId",userPower.get("countyId")==null?"":userPower.get("countyId").toString());
                    map.put("schoolId",userPower.get("schoolId")==null?"":userPower.get("schoolId").toString());
                }
                map.put("updateTime",DATE_FORMAT.format(map.get("updateTime")));
            }
        }
        return comment;
    }

}
