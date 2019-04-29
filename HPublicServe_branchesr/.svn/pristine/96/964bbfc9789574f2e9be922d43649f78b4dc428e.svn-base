package com.honghe.deviceNew.dao;

import com.honghe.deviceNew.entity.Device;
import com.honghe.spring.MybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@MybatisDao
public interface DeviceMapper {
    int deleteByPrimaryKey(Integer deviceId);

    int insert(Device record);

    int insertDeviceBatch(List<Device> deviceList);

    int insertSelective(Device record);

    Device selectByPrimaryKey(Integer deviceId);

    int updateByPrimaryKeySelective(Device record);

    int updateByPrimaryKey(Device record);

    int insertDevices(List deviceTokenList);

    Long addDeviceToken(Device device);

    Boolean delDeviceToken(Integer hostId);

    Map getDeviceInfo(Integer hostId);

    void updateTboxDeviceName(@Param(value = "channelName") String channelName, @Param(value = "token") String token, @Param(value = "hostId") int hostId);

    List getDeviceConfigList();
}