package com.honghe.tech.listener;

import com.honghe.tech.service.ActivityService;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author xinqinggang on 2018/3/13.
 * 定时发送通知
 */
@Component
@Service
public class StartupInitialize implements InitializingBean {

    protected Logger logger = Logger.getLogger(StartupInitialize.class);
    private static final Long INITIALDELAY=1000L;
    private static final Long PERIOD=30000L;
    @Autowired
    private ActivityService activityService;
    @Override
    public void afterPropertiesSet() throws Exception {
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                activityService.asyncNoticeTime();
            }
        },INITIALDELAY,PERIOD, TimeUnit.MILLISECONDS);
    }
}
