package com.honghe.deviceNew.dao;

import com.honghe.deviceNew.entity.DeviceDloadparmRs;
import com.honghe.deviceNew.entity.DeviceDloadparmRsKey;
import com.honghe.spring.MybatisDao;

@MybatisDao
public interface DeviceDloadparmRsMapper {
    int deleteByPrimaryKey(DeviceDloadparmRsKey key);

    int insert(DeviceDloadparmRs record);

    int insertSelective(DeviceDloadparmRs record);

    DeviceDloadparmRs selectByPrimaryKey(DeviceDloadparmRsKey key);

    int updateByPrimaryKeySelective(DeviceDloadparmRs record);

    int updateByPrimaryKey(DeviceDloadparmRs record);
}