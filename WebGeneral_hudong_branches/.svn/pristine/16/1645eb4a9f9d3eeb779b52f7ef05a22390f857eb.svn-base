package com.honghe.tech.service.impl;

import com.honghe.tech.dao.ActivityDao;
import com.honghe.tech.dao.NoticeDao;
import com.honghe.tech.httpservice.UserHttpService;
import com.honghe.tech.service.ActivityService;
import com.honghe.tech.service.NoticeService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by caoqian on 2018/1/23.
 * @author caoqian
 */
@Service
public class NoticeServiceImpl implements NoticeService {
    private Logger logger=Logger.getLogger(NoticeServiceImpl.class);
    /**
     * 设置日期格式
     */
    private static final SimpleDateFormat DATE_FORMAT=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat YEAR_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private NoticeDao noticeDao;
    @Autowired
    private ActivityDao activityDao;

    /**
     * 保存通知数据
     * @param noticeJson {"userData":[{"userId":"1","userRole":"0"},{"userId":"2","userRole":"1"}],
     *                   "pubUserId":"2","time":"2018-1-25 17:00:~ 17:50",
     *                   "pubTime":"2018-1-25 18:30","name":"互动教学",
     *                   "type":"0","activityId":"1"}   通知数据
     *           userRole=0 预约人，userRole=1 主讲教师，userRole=2 辅助教师,userRole=3 管理员或学校领导
     *           type为通知类型，1期不用
     * @return  boolean  true:保存通知成功;false:保存通知失败
     * @author caoqian
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean saveNoticeTable(JSONObject noticeJson){
        boolean returnResult=false;
        if(noticeJson==null || noticeJson.size()==0){
            logger.debug("保存通知失败，通知数据为空，参数异常。");
            throw new IllegalArgumentException();
        }else {
            Map<String, Object> noticeMap = new HashMap<>();
            try {
                //通知的用户id，多个“,”分割
                String userStr = noticeJson.get("userData") == null ? "" : String.valueOf(noticeJson.get("userData"));
                if ("".equals(userStr)) {
                    logger.debug("用户数据为空，通知数据不保存，直接返回false。");
                    return false;
                } else {
                    String activityId = String.valueOf(noticeJson.get("activityId"));
                    //主讲教师信息
                    List<Map<String, String>> lectTeacher = activityDao.getLectTeacherList(activityId);
                    String teacherMessage = "";
                    if (lectTeacher != null && !lectTeacher.isEmpty()) {
                        teacherMessage = lectTeacher.get(0).get("city") + lectTeacher.get(0).get("county") +
                                lectTeacher.get(0).get("school") + lectTeacher.get(0).get("hostName");
                        logger.debug("主讲教师信息teacherMessage=" + teacherMessage);
                        noticeMap.put("teacherMessage", teacherMessage);
                        noticeMap.put("pubUserId", String.valueOf(noticeJson.get("pubUserId")));
                        noticeMap.put("time", String.valueOf(noticeJson.get("time")));
                        noticeMap.put("name", String.valueOf(noticeJson.get("name")));
                        noticeMap.put("activityId", activityId);
                        //活动类型，默认为0，预留字段
                        noticeMap.put("type", String.valueOf(noticeJson.get("type") == null ? "0" : noticeJson.get("type")));
                        noticeMap.put("status", 0);
                        Date now = new Date();
                        //通知时间
                        noticeMap.put("pubTime", DATE_FORMAT.format(now));
                        List<Map<String, Object>> noticeList = new ArrayList<>();
                        JSONArray userJsonArray = JSONArray.fromObject(userStr);
                        for (Object o : userJsonArray) {
                            Map<String, Object> map = new HashMap<>();
                            map.putAll(noticeMap);
                            String userId = String.valueOf(JSONObject.fromObject(o).get("userId"));
                            map.put("userId", userId);
                            String userRole = String.valueOf(JSONObject.fromObject(o).get("userRole"));
                            map.put("userRole", userRole);
                            saveNoticeByUser(map);
                            noticeList.add(map);
                        }
                        returnResult = noticeDao.saveNotice(noticeList);
                    } else {
                        logger.debug("主讲教师信息查不到，不保存该条通知。");
                        return false;
                    }
                }
            } catch (Exception e) {
                logger.error("保存通知数据异常,noticeMap=" + noticeMap.toString(), e);
                returnResult = false;
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();   //事物回滚
            }
            return returnResult;
        }
    }

    /**
     * 根据不同用户权限保存通知信息
     * @param noticeMap
     * @return
     */
    private Map<String,Object> saveNoticeByUser(Map<String,Object> noticeMap){
        if(noticeMap!=null && noticeMap.containsKey("userRole")) {
            int userRole = Integer.parseInt(String.valueOf(noticeMap.get("userRole")));
            String content = "";
            switch (userRole) {
                case 0:
                    //预约人
                    content = "您好,您已成功预约一节课程名称为" + noticeMap.get("name").toString() + "的互动教学课程" +
                            ",时间:" + noticeMap.get("time").toString() +
                            ",主讲教师:" + noticeMap.get("teacherMessage").toString();
                    noticeMap.put("content", content);
                    break;
                case 1:
                    //主讲教师
                    content = "您有一节主讲互动课程,课程名称:" + noticeMap.get("name").toString() +
                            ",时间:" + noticeMap.get("time").toString() +
                            ",主讲教师:" + noticeMap.get("teacherMessage").toString();
                    noticeMap.put("content", content);
                    break;
                case 2:
                    //辅助教师
                    content = "您有一节辅助互动课程,课程名称:" + noticeMap.get("name").toString() +
                            ",时间:" + noticeMap.get("time").toString() +
                            ",主讲教师:" + noticeMap.get("teacherMessage").toString();
                    noticeMap.put("content", content);
                    break;
                case 3:
                    //管理员或学校领导
                    content = "您好,一节课程名称为" + noticeMap.get("name").toString() + "的互动教学课程即将开始" +
                            ",时间:" + noticeMap.get("time").toString() +
                            ",主讲教师:" + noticeMap.get("teacherMessage").toString() + ",欢迎观看";
                    noticeMap.put("content", content);
                    break;
                default:
                    break;
            }
        }
        return noticeMap;
    }
    /**
     * 修改通知状态（通知置为已读）
     * @param noticeIdsArr       通知id，可多个，多个用","分割，如“1，2，3”
     * @param userId             用户Id
     * @return  boolean          true:修改成功；false:修改失败
     * @author caoqian
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean changNoticeStatusTable(String[] noticeIdsArr,String userId){
        boolean returnResult=false;
        boolean existed = (userId==null || "".equals(userId)) || (noticeIdsArr==null || noticeIdsArr.length==0);
        if(existed){
            logger.debug("用户id:"+userId+",通知ids:"+noticeIdsArr+",不能修改通知状态为已读,参数异常。");
            throw new IllegalArgumentException();
        }else{
            try {
                returnResult = noticeDao.changeNoticeStatus(noticeIdsArr, userId);
            }catch (Exception e){
                logger.error("修改通知已读状态异常,noticeIdsArr="+noticeIdsArr.toString()+",userId="+userId,e);
                returnResult=false;
//                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();   //事物回滚
            }
            return returnResult;
        }
    }

     /**
     * 分页获取通知
     * @param userId               用户id
     * @param currentPage          当前页
     * @param pageSize             每页条数
     * @return  map                通知数据
     * @author caoqian
     */
    @Override
    public Map<String, Object> getNoticesListByPage(String userId, String currentPage, String pageSize){
        Map<String,Object> resultMap=new HashMap<>();
        if(userId==null || "".equals(userId) || currentPage==null || "".equals(currentPage) || pageSize==null || "".equals(pageSize)){
            logger.debug("用户id:"+userId+",当前页currentPage:"+currentPage+",每页条数pageSize:"+pageSize+",分页获取通知失败，参数异常。");
            throw new IllegalArgumentException();
        }else {
            resultMap.put("currentPage", currentPage);
            resultMap.put("pageSize", pageSize);
            try {
                //获取第一条开始的位置
                int firstResult = (Integer.parseInt(currentPage) - 1) * Integer.parseInt(pageSize) < 0 ? 0 : (Integer.parseInt(currentPage) - 1) * Integer.parseInt(pageSize);
                List<Map<String, Object>> noticeDataList = noticeDao.noticeListByPage(userId, firstResult, Integer.parseInt(pageSize), true);
                int totalCount = noticeDao.noticeListByPage(userId, firstResult, Integer.parseInt(pageSize), false).size();
                //未读通知条数
                int unreadNoticeNum = noticeDao.unReadNoticeNum(userId);
                resultMap.put("total", totalCount);
                resultMap.put("dataList", noticeDataList);
                resultMap.put("unreadNoticeNum", unreadNoticeNum);
            } catch (Exception e) {
                resultMap.put("total", "");
                resultMap.put("dataList", "");
                resultMap.put("unreadNoticeNum", "");
                logger.error("分页获取通知异常,result=" + resultMap.toString(), e);
            }
            return resultMap;
        }
    }

    /**
     * 删除过期通知
     * @return true/false
     */
    @Override
    public boolean deleteOverdueNotices() {
        boolean result = false;
        String activityId="";
        try{
            Date now = new Date();
            String date = YEAR_FORMAT.format(now);
            activityId=activityDao.getOverdueActivityId(date+" 00:00:00");
            if(activityId!=null && !"".equals(activityId)){
                String[] idsArr=activityId.split(",");
                result=noticeDao.deleteOverdueNotices(idsArr);
            }else{
                result=false;
            }
        }catch (Exception e){
            result=false;
            logger.error("删除过期通知信息异常，活动ids="+activityId,e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();   //事物回滚
        }
        return result;
    }

}
