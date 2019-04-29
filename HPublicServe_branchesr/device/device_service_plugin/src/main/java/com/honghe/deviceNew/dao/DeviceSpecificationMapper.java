package com.honghe.deviceNew.dao;

import com.honghe.deviceNew.entity.DeviceSpecification;
import com.honghe.spring.MybatisDao;

import java.util.List;
import java.util.Map;

@MybatisDao
public interface DeviceSpecificationMapper {

    int deleteByPrimaryKey(Integer dspecId);

    int insert(DeviceSpecification record);

    int insertSelective(DeviceSpecification record);

    DeviceSpecification selectByPrimaryKey(Integer dspecId);

    int updateByPrimaryKeySelective(DeviceSpecification record);

    int updateByPrimaryKey(DeviceSpecification record);

    DeviceSpecification selectByModel(String model);

    DeviceSpecification selectInfoByTypeInt(String typeInt);

    List<Map<String,Object>> getAllInfo();
}