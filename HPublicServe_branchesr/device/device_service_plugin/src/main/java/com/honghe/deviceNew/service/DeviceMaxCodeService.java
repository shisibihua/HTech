package com.honghe.deviceNew.service;

import com.honghe.deviceNew.entity.DeviceMaxCode;

/**
 * Created by jjdqh on 2017/9/5.
 */
public interface DeviceMaxCodeService {
    DeviceMaxCode getMaxDeviceCode(String deviceType) throws Exception;

    int updateMaxCode(DeviceMaxCode deviceMaxCode)throws Exception;

    long insertMaxCode(DeviceMaxCode deviceMaxCode)throws Exception;
}
