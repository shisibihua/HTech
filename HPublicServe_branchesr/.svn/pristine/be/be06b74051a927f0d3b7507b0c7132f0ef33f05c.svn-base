package com.honghe.deviceNew.service.impl;

import com.honghe.deviceNew.dao.DeviceCmdcodeUpdateMapper;
import com.honghe.deviceNew.dao.DeviceConfigMapper;
import com.honghe.deviceNew.dao.DeviceMapper;
import com.honghe.deviceNew.entity.Device;
import com.honghe.deviceNew.service.DeviceService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 设备管理service实现层
 */
@Service
public class DeviceServiceImpl implements DeviceService {
    Logger logger = Logger.getLogger("device");

    @Resource
    DeviceMapper deviceMapper;
    @Resource
    DeviceConfigMapper deviceConfigMapper;
    @Resource
    DeviceCmdcodeUpdateMapper deviceCmdcodeUpdateMapper;


    @Override
    public Long addDeviceToken(Device device) {
        return deviceMapper.addDeviceToken(device);
    }

    @Override
    public Boolean delDeviceToken(Integer hostId) {
        return deviceMapper.delDeviceToken(hostId);
    }

    @Override
    public Map getDeviceInfo(Integer hostId) {
        return deviceMapper.getDeviceInfo(hostId);
    }

    @Override
    public boolean isExsitHost(Integer hostId) {
        boolean re_value = false;
        Map hostMap = deviceMapper.getDeviceInfo(hostId);
        if (hostMap!=null&&hostMap.size()>0){
            re_value = true;
        }
        return re_value;
    }

    @Override
    public void updateTboxDeviceName(String channelName, String token, int hostId) {
        deviceMapper.updateTboxDeviceName(channelName, token, hostId);
    }

    @Override
    public List getDeviceConfigList() {
        return deviceConfigMapper.getDeviceConfigList();
    }

    @Override
    public List<Map<String, String>> getDeviceCmdCodeUpdate(String ip) {
        return deviceCmdcodeUpdateMapper.getDeviceCmdCodeUpdate(ip);
    }

    @Override
    public List<Map<String, String>> getCount(String ip, String cmdCodeUpdate) {
        return deviceCmdcodeUpdateMapper.getCount(ip, cmdCodeUpdate);
    }

}
