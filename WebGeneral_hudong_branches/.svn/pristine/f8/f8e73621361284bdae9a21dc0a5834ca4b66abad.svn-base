package com.honghe.tech.listener;

import com.honghe.tech.httpservice.UserHttpService;
import com.honghe.tech.service.NoticeService;
import com.honghe.tech.service.OperLogService;
import com.honghe.tech.util.LogUtil;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @author caoqian on 20180402.
 * 备份日志数据、删除过期通知
 */
@Component
@Service
public class BackUpLogListener implements InitializingBean {
    private final Logger logger = Logger.getLogger(BackUpLogListener.class);
    //时
    private static final int TIME_DELETE_HOUR=17;
    //分
    private static final int TIME_DELETE_MINUTES=29;
    //秒
    private static final int TIME_DELETE_SECOND=00;
    //日志类型,0:操作日志，1:系统日志
    private static final String OPER_LOG="操作日志";
    //日志类型,0:操作日志，1:系统日志
    private static final String SYSTEM_LOG="系统日志";
    @Autowired
    private OperLogService operLogService;
    @Autowired
    private UserHttpService userHttpService;
    @Autowired
    private NoticeService noticeService;
    @Override
    public void afterPropertiesSet() throws Exception {
        /*Calendar calendar = Calendar.getInstance();
        // 控制时
        calendar.set(Calendar.HOUR_OF_DAY, TIME_DELETE_HOUR);
        // 控制分
        calendar.set(Calendar.MINUTE, TIME_DELETE_MINUTES);
        // 控制秒
        calendar.set(Calendar.SECOND, TIME_DELETE_SECOND);
        // 得出执行任务的时间,此处为今天的23：00：00
        Date time = calendar.getTime();
        Long period=Long.parseLong("2160000000");
        //每25天执行一次。第一次在指定time时间点执行任务，之后每隔25天时间调用任务一次。
        Timer timer=new Timer("backupLog");
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                backUpData();
            }
        }, time, period);*/
    }

    /**
     * 备份日志文件并删除过期的通知数据
     */
    private void backUpData(){
        logger.debug("---【监听器监听到开始备份日志文件并删除过期的通知数据】---");
        try {
            List<Map<String,Object>> logMessage=returnOperLog();
            if(logMessage!=null) {
                boolean result = LogUtil.INSTANCE.backUpLogInfo(JSONArray.fromObject(logMessage).toString());
                if (result) {
                    operLogService.deleteLogTable();
                } else {
                    logger.debug("备份数据库失败。");
                }
            }
            boolean deleteNotice=noticeService.deleteOverdueNotices();
            if(deleteNotice){
                logger.debug("删除过期通知数据成功!");
            }else{
                logger.debug("删除过期通知数据失败!");
            }
        } catch (IOException e) {
            logger.error("备份日志文件异常。",e);
        }
        logger.debug("---【监听器监听到备份日志文件并删除过期的通知数据结束】---");
    }

    /**
     * 获取所有日志信息
     * @return
     */
    private List<Map<String,Object>> returnOperLog(){
        List<Map<String,Object>> logMessage=operLogService.getAllLogs();
        if(logMessage!=null && !logMessage.isEmpty()) {
            Map<String,Object> userInfo=userHttpService.getAllUserInfo();
            for (Map<String, Object> log : logMessage) {
                String userId = String.valueOf(log.get("userId"));
                if (userInfo!=null && !userInfo.isEmpty() && userInfo.get(userId)!=null) {
                    Map<String,String> user=(Map<String,String>)userInfo.get(userId);
                    log.put("userName", user.get("userRealName")==null?"":user.get("userRealName"));
                } else {
                    log.put("userName", "");
                }
                String type = String.valueOf(log.get("type"));
                if (type != null) {
                    if ("0".equals(type)) {
                        log.put("type", OPER_LOG);
                    } else {
                        log.put("type", SYSTEM_LOG);
                    }
                } else {
                    log.put("type", "");
                }
            }
        }
        return logMessage;
    }
}
