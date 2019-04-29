package com.honghe.tech.service.impl;

import com.honghe.tech.dao.ActivityStyleDao;
import com.honghe.tech.service.ActivityService;
import com.honghe.tech.service.ActivityStyleService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xinqinggang on 2018/1/25.
 * @author xinqinggang
 */
@Service
public class ActivityStyleServiceImpl implements ActivityStyleService {
    Logger logger=Logger.getLogger(ActivityStyleServiceImpl.class);
    @Autowired
    private ActivityStyleDao activityStyleDao;
    @Override
    public String findStyleById(int id) {
        String styleName="";
        try{
            styleName=activityStyleDao.findStyleById(id).getName();
        }catch (Exception e)
        {
            logger.error("根据活动模式id获取模式名称异常，活动模式id="+id,e);
        }
        return styleName;
    }
}
