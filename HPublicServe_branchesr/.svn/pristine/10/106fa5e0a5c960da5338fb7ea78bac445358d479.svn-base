package com.honghe.deviceNew.service.impl;

import com.honghe.deviceNew.dao.DeviceCommandCodeMapper;
import com.honghe.deviceNew.service.DeviceCommandCodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * 设备管理service实现层
 */
@Service
public class DeviceCommandCodeServiceImpl implements DeviceCommandCodeService {
@Resource
DeviceCommandCodeMapper deviceCommandCodeMapper;

    @Override
    public List<Map<String, String>> getSpecCmd(int dspecid) {
        return deviceCommandCodeMapper.getSpecCmd(dspecid);
    }
}
