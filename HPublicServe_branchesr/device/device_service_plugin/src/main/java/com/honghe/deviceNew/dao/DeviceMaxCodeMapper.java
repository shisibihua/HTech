package com.honghe.deviceNew.dao;

import com.honghe.deviceNew.entity.DeviceMaxCode;
import com.honghe.spring.MybatisDao;

@MybatisDao
public interface DeviceMaxCodeMapper {

    long insert(DeviceMaxCode record);

    int updateByPrimaryKey(DeviceMaxCode record);

    DeviceMaxCode selectMaxCode(String deviceType) throws Exception;

}