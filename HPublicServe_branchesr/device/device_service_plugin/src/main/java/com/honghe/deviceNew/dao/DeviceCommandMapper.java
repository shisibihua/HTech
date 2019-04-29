package com.honghe.deviceNew.dao;

import com.honghe.deviceNew.entity.DeviceCommand;
import com.honghe.spring.MybatisDao;

@MybatisDao
public interface DeviceCommandMapper {
    int deleteByPrimaryKey(Integer cmdId);

    int insert(DeviceCommand record);

    int insertSelective(DeviceCommand record);

    DeviceCommand selectByPrimaryKey(Integer cmdId);

    int updateByPrimaryKeySelective(DeviceCommand record);

    int updateByPrimaryKey(DeviceCommand record);
}