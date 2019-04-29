package com.honghe.deviceNew.service.impl;

import com.honghe.deviceNew.dao.DeviceMaxCodeMapper;
import com.honghe.deviceNew.entity.DeviceMaxCode;
import com.honghe.deviceNew.service.DeviceMaxCodeService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 设备管理service实现层
 */
@Service
public class DeviceMaxCodeServiceImpl implements DeviceMaxCodeService {
    Logger logger = Logger.getLogger("device");
    @Resource
    DeviceMaxCodeMapper deviceMaxCodeMapper;

    @Override
    public DeviceMaxCode getMaxDeviceCode(String deviceType) throws Exception {
        return deviceMaxCodeMapper.selectMaxCode(deviceType);
    }

    @Override
    public int updateMaxCode(DeviceMaxCode deviceMaxCode) throws Exception {
        return deviceMaxCodeMapper.updateByPrimaryKey(deviceMaxCode);
    }

    @Override
    public long insertMaxCode(DeviceMaxCode deviceMaxCode) throws Exception {
        return deviceMaxCodeMapper.insert(deviceMaxCode);
    }

}
