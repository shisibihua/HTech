package com.honghe.ad.data.controller;

import com.honghe.ad.data.dao.DataDao;
import com.honghe.ad.excetion.ParamException;

import java.util.List;
import java.util.Map;

/**
 * Created by yuk on 2016/11/30.
 *
 * 提供一些获取基础数据的接口
 */
public class DataController {

    private DataDao dataDao = DataDao.INSTANCE;
    /**
     * 获取所有地点类型列表
     *
     * @param p_id
     * @return list 地点类型列表
     */
    public List<Map<String,String>> dataGetAreaTypes(String p_id){
        return dataDao.getAreaTypes(p_id);
    }

    /**
     * 获取所有学段列表
     *
     * @return list 学段列表
     */
    public List<Map<String,String>> dataGetStages(){
        return dataDao.getStages();
    }

    /**
     * 获取比机构类型级别大的机构类型
     *
     * @param level 机构级别
     *
     * @return list 机构类型列表
     */
    public List<Map<String,String>> dataGetCampusTypes(String level){
        return dataDao.getCampusTypes(level);
    }

    /**
     * 获取所有校区信息
     *
     *
     * @return list 校区信息列表
     */
    public List<Map<String,String>> dataGetSchoolZone(){
        return dataDao.getSchoolZone();
    }
}
