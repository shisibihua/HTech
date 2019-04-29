package com.honghe.tech.dao;

import com.honghe.spring.MybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author caoqian  20180124.
 */
@MybatisDao
public interface NoticeDao {
    /**
     * 保存通知
     * @param list  通知列表
     * @return boolean
     */
     boolean saveNotice(@Param("list")List<Map<String,Object>> list);

    /**
     * 修改通知读取状态
     * @param noticeIdArr    通知ids数组
     * @param userId         用户id
     * @return boolean
     */
     boolean changeNoticeStatus(@Param("noticeIdArr")String[] noticeIdArr,@Param("userId")String userId);

    /**
     * 分页获取通知
     * @param userId          用户id
     * @param firstResult     从第几条开始读取数据
     * @param pageSize        每页条数
     * @param pageBool        是否分页，true分页查询,false不分页查询
     * @return 通知列表
     */
     List<Map<String,Object>> noticeListByPage(@Param("userId")String userId, @Param("firstResult") Integer firstResult,
                                                     @Param("pageSize") Integer pageSize, @Param("pageBool") boolean pageBool);

    /**
     * 获取未读通知的数量
     * @param userId  用户id
     * @return boolean
     */
     int unReadNoticeNum(@Param("userId") String userId);

    /**
     * 根据活动id删除通知（删除过期活动的通知）
     * @param activityIdsArr  过期活动ids的数组
     * @return boolean
     */
     boolean deleteOverdueNotices(@Param("activityIdsArr")String[] activityIdsArr);

    /**
     * 根据活动id删除通知
     * @param activityId 活动id
     * @return boolean
     */
     boolean deleteNoticeByActivityId(@Param("activityId")int activityId);
}
