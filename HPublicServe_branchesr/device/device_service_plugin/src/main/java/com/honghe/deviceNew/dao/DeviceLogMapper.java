package com.honghe.deviceNew.dao;

import com.honghe.deviceNew.entity.DeviceLog;
import com.honghe.spring.MybatisDao;

@MybatisDao
public interface DeviceLogMapper {
    int deleteByPrimaryKey(Integer logId);

    int insert(DeviceLog record);

    int insertSelective(DeviceLog record);

    DeviceLog selectByPrimaryKey(Integer logId);

    int updateByPrimaryKeySelective(DeviceLog record);

    int updateByPrimaryKey(DeviceLog record);
}