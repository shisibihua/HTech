package com.honghe.area.dao;

import com.honghe.area.dao.areaDao.AreaDao;
import com.honghe.area.dao.areaDeviceDao.AreaDeviceDao;
import com.honghe.area.dao.areaTypeDao.AreaTypeDao;
import com.honghe.area.dao.schoolDao.SchoolDao;
import com.honghe.area.dao.schoolZoneDao.SchoolZoneDao;
import com.honghe.area.dao.techColumnDao.TechColumnDao;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author LC
 * @date 2017/3/8
 */
public class DaoFactory {
    private static Map<String,BasicDao> container = new HashMap<>();

    /**
     * 构造函数
     */
    public DaoFactory(){

    }

    static {
        container.put("area",new AreaDao());
        container.put("atype",new AreaTypeDao());
        container.put("adevice",new AreaDeviceDao());
        container.put("school",new SchoolDao());
        container.put("zone",new SchoolZoneDao());
        container.put("column",new TechColumnDao());
    }

    public static Boolean containsDao(String key){
        return container.containsKey(key);
    }
    public static BasicDao getBasicDaoInstance(String key){
        return container.get(key);
    }
}
