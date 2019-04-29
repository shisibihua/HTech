package com.honghe.deviceNew.service;

import com.honghe.deviceNew.entity.DeviceSpecification;

public interface DeviceSpecService {

    //通过设备型号获取specId和record_type
    DeviceSpecification getSpecByName(String name);

    DeviceSpecification getDespecInfoByTypeInt(String typeInt);
}
