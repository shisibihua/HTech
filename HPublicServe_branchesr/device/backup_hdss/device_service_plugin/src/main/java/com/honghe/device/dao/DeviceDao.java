package com.honghe.device.dao;

import com.hht.global.GlobalDefinitions;
import com.honghe.device.Host;
import com.hht.device.Device;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lyx on 2016-10-10.
 */
public final class DeviceDao extends HostDao {
    public static DeviceDao INSTANCE = new DeviceDao();

    @Override
    public String getStatus(String ip) {
        return Host.INSTANCE.getStatus(ip);
    }

    /**
     * 获取选定设备信息
     * @param ipStr 设备ip，多个用逗号隔开
     * @return
     */
    @Override
    public List<Map<String, String>> getDevicesDetail(String ipStr) {
        List<Device> deviceList = Host.INSTANCE.getDevicesDetail(ipStr);
        List<Map<String,String>> mapList = new ArrayList<>();
        if (deviceList!=null&&deviceList.size()>0){
            for (Device device:deviceList){
                String ip = device.getIP();
                if (ip==null||ip.equals("")){
                    continue;
                }
                Map<String,String> statusMap = new HashMap<String,String>();
                statusMap.put("ip",ip);
                statusMap.put("deviceStatus",device.getDeviceStatus()==null? GlobalDefinitions.OnlineStatus_Offline:device.getDeviceStatus());
                mapList.add(statusMap);
            }
        }
        return mapList;
    }

}
