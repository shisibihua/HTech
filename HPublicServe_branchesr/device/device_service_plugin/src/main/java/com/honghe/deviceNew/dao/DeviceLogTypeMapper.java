package com.honghe.deviceNew.dao;

import com.honghe.deviceNew.entity.DeviceLogType;
import com.honghe.spring.MybatisDao;

@MybatisDao
public interface DeviceLogTypeMapper {
    int deleteByPrimaryKey(Integer logtypeId);

    int insert(DeviceLogType record);

    int insertSelective(DeviceLogType record);

    DeviceLogType selectByPrimaryKey(Integer logtypeId);

    int updateByPrimaryKeySelective(DeviceLogType record);

    int updateByPrimaryKey(DeviceLogType record);
}