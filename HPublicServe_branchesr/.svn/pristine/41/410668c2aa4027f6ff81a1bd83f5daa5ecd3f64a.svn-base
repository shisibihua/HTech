package com.honghe.deviceNew.service.impl;

import com.honghe.deviceNew.dao.DeviceSpecificationMapper;
import com.honghe.deviceNew.entity.DeviceSpecification;
import com.honghe.deviceNew.service.DeviceSpecService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DeviceSpecServiceImpl implements DeviceSpecService{
//    Logger logger = Logger.getLogger("device");
//    public static DeviceSpecServiceImpl INSTANCE = new DeviceSpecServiceImpl();

    @Resource
    DeviceSpecificationMapper deviceSpecificationMapper;

    @Override
    public DeviceSpecification getSpecByName(String name) {
        return deviceSpecificationMapper.selectByModel(name);
    }

    @Override
    public DeviceSpecification getDespecInfoByTypeInt(String typeInt) {
        return deviceSpecificationMapper.selectInfoByTypeInt(typeInt);
    }
}
