package com.honghe.device.action.commandHandler;

import com.honghe.device.dao.DeviceDao;

import java.util.Map;

/**
 * 根据ip获取设备列表
 *
 * @author lyx
 * @create 2017-05-09 13:21
 **/
public class IpHandler extends AbstractCommndHandler {
    @Override
    public String getComamnd(Map<String, Object> map) {

        String command = null;
        if (map.containsKey("ip") || map.containsKey("IP")) {
            String ip = map.containsKey("ip") ? map.get("ip").toString() : map.get("IP").toString();
            Map hostMap = DeviceDao.INSTANCE.getHostInfoByIp(ip);
            String deviceType = hostMap.get("dtype_name")==null?"":hostMap.get("dtype_name").toString().toLowerCase();//此部分需要通过ip查询到对应的类型
            command = super.getCommandByDeviceType(deviceType);
        } else {
            if (super.getCommandHandler() != null) {
                command = super.getCommandHandler().getComamnd(map);
            }
        }
        return command;
    }
}
