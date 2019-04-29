package com.honghe.deviceNew.dao;

import com.honghe.deviceNew.entity.DeviceDlParam;
import com.honghe.spring.MybatisDao;

@MybatisDao
public interface DeviceDlParamMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceDlParam record);

    int insertSelective(DeviceDlParam record);

    DeviceDlParam selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeviceDlParam record);

    int updateByPrimaryKeyWithBLOBs(DeviceDlParam record);

    int updateByPrimaryKey(DeviceDlParam record);
}