package com.honghe.deviceNew.dao;

import com.honghe.deviceNew.entity.DeviceEventfield;
import com.honghe.spring.MybatisDao;

@MybatisDao
public interface DeviceEventfieldMapper {
    int deleteByPrimaryKey(Integer fieldId);

    int insert(DeviceEventfield record);

    int insertSelective(DeviceEventfield record);

    DeviceEventfield selectByPrimaryKey(Integer fieldId);

    int updateByPrimaryKeySelective(DeviceEventfield record);

    int updateByPrimaryKey(DeviceEventfield record);
}