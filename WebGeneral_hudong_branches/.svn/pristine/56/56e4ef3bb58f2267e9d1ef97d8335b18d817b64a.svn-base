package com.honghe.tech.service;

import net.sf.json.JSONObject;

import java.util.Map;

/**
 * 消息通知
 * @author caoqian
 */
public interface NoticeService {
      /**
       * 保存通知
       * @param noticeJson
       * @return
       */
      public boolean saveNoticeTable(JSONObject noticeJson);

      /**
       * 修改通知
       * @param noticeIdsArr
       * @param userId
       * @return
       */
      public boolean changNoticeStatusTable(String[] noticeIdsArr,String userId);

      /**
       * 获取通知列表
       * @param userId
       * @param currentPage
       * @param pageSize
       * @return
       */
      public Map<String,Object> getNoticesListByPage(String userId, String currentPage, String pageSize);

      /**
       * 删除过期通知
       * @return
       */
      public boolean deleteOverdueNotices();
}
