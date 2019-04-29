package com.honghe.deviceNew.dao;

import com.honghe.deviceNew.entity.DeviceConfig;
import com.honghe.spring.MybatisDao;

import java.util.List;

@MybatisDao
public interface DeviceConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceConfig record);

    int insertSelective(DeviceConfig record);

    DeviceConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeviceConfig record);

    int updateByPrimaryKey(DeviceConfig record);

    List<DeviceConfig> getDeviceConfigList();
}