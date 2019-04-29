package com.honghe.deviceNew.service;

import com.honghe.deviceNew.entity.Device;

import java.util.List;
import java.util.Map;

/**
 * Created by jjdqh on 2017/9/5.
 */
public interface DeviceService {

    // 添加设备镜头
    Long addDeviceToken(Device device);

    // 删除设备镜头
    Boolean delDeviceToken(Integer hostId) throws Exception;

    // 通过hostid查询设备的镜头信息
    Map getDeviceInfo(Integer hostId);

    boolean isExsitHost(Integer hostId);

    // 修改tbox通道名称
    void updateTboxDeviceName(String channelName, String token, int hostId);

    List getDeviceConfigList();

    List<Map<String, String>> getDeviceCmdCodeUpdate(String ip);

    List<Map<String, String>> getCount(String ip, String cmdCodeUpdate);

}
