package com.honghe.deviceNew.dao;

import com.honghe.deviceNew.entity.DeviceCmdcodeUpdate;
import com.honghe.spring.MybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@MybatisDao
public interface DeviceCmdcodeUpdateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceCmdcodeUpdate record);

    int insertSelective(DeviceCmdcodeUpdate record);

    DeviceCmdcodeUpdate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeviceCmdcodeUpdate record);

    int updateByPrimaryKey(DeviceCmdcodeUpdate record);

    List<Map<String, String>> getDeviceCmdCodeUpdate(@Param(value = "ip") String ip);

    List<Map<String, String>> getCount(@Param(value = "ip") String ip, @Param(value = "cmdCodeUpdate") String cmdCodeUpdate);
}