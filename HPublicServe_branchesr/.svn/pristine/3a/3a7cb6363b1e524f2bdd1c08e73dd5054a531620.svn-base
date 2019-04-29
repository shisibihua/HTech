package com.honghe.deviceNew.command;

import com.honghe.deviceNew.entity.DeviceHost;
import com.honghe.deviceNew.service.*;
import com.honghe.spring.Command;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaolijiao on 2017/5/10.
 */
@Command("deviceConnectionCommand")
public class deviceConnectionCommand {
    private final static Logger logger = Logger.getLogger("device");

    @Autowired
    DeviceHostService deviceHostService;

    @Autowired
    DeviceSpecService deviceSpecService;

    @Autowired
    DeviceMaxCodeService deviceMaxCodeService;

    @Autowired
    DeviceService deviceService;


    /*------------------------------------------------对接设备连接服务接口-------------------------------------------*/
    /**
     * 心跳接口
     */
    public Map<String, String> keep_alive() {
        Map<String, String> re_value = new HashMap<>();
        re_value.put("token", "DEVICE_CONNECTION");
        logger.debug("连接到心跳");
        return re_value;
    }

    /**
     * 手动添加设备
     * @param deviceType 设备类型
     * @param ip 设备ip
     * @param typeInt 设备编号
     * @param className 设备名称
     * @param factory 厂家标识
     * @param mcuClientCode mcu码（mcu设备特有）
     * @param userName  账号名称（录播设备特有）
     * @param password 密码（录播设备特有）
     * @param hostPort 设备端口（安防设备特有）
     * @param hostChannel 设备通道号（安防设备特有）
     * @param isTour 是否支持巡课（安防设备添加字段）
     * @param metString 镜头名称（虚拟设备特有）
     * @param cameraString 镜头流地址（虚拟设备特有）
     * @return
     */
    public Map<String, Object> manualDiscover(String ip,String deviceType, String typeInt, String className, String factory, String userName,String password,String hostPort,String hostChannel,String isTour,String metString,String cameraString, String mcuClientCode) {
       return  deviceHostService.addDevice(deviceType,ip,typeInt,className,factory,userName,password,hostPort,hostChannel,isTour,metString,cameraString,mcuClientCode);
    }

    /**
     * 删除设备
     * @param hostIpStr 设备ip
     * @param hostTypeStr 设备类型
     * @param hostIdStr 设备ids，多个用逗号隔开
     * @return
     */
    public Map<String, Object> delHostListByType(String hostIdStr,String hostIpStr,String hostTypeStr) {
        return deviceHostService.deleteDevices(hostIdStr,hostIpStr,hostTypeStr);
    }

    /**
     * 根据设备编码获取设备信息
     * @param mcuCode
     * @return
     */
    public DeviceHost getDeviceHostByCode(String mcuCode) {
        return deviceHostService.getDeviceHostByMcuCode(mcuCode);
    }
//    /**
//     * 获取设备列表信息（设备列表导出数据）
//     *
//     * @param deviceType 设备类型，当设备类型为空时，查询所有的设备信息
//     * @return
//     */
//    public List<Map<String, Object>> getDeviceInfo(String deviceType) {
//        return deviceHostService.getDeviceInfo(deviceType);
//    }


//    /**
//     *获取所有设备信息
//     */
//    public List<DeviceHost> getDeviceHost() {
//        return deviceHostService.getDeviceHostList();
//    }

    /**
     * 通过设备id修改设备名称(平台修改名称)
     *
     * @param hostId 设备id
     * @param hostName 设备名称
     * @return
     */
    public boolean updateHostNameById(String hostId, String hostName) {
        return deviceHostService.updateHostNameById( hostId, hostName);
    }

    /**
     * 通过设备ip获取设备信息
     *
     * @param hostIp 设备ip
     * @return
     */
    public DeviceHost getDeviceByHostIp(String hostIp) {
        return deviceHostService.getDeviceByHostIp(hostIp);
    }

//    //获取设备信息并分页
//    public Map<String, Object> hostInfoByPage(String hostIdStr, String deviceType, String currentPage, String pageSize) {
//        return deviceHostService.getHostInfoByPage(hostIdStr, deviceType, currentPage, pageSize);
//
//    }
    /**
     * 获取所有设备信息（提供给连接服务）
     * @return
     */
    public List<Map<String,Object>> getAllHostInfo(){
        return deviceHostService.getAllHostInfo();
    }

    /**
     * 获取mcu设备信息
     * @return
     */
    public List<Map<String,Object>> getMcuInfo(String factory){
        return deviceHostService.getMcuInfo(factory);
    }

    /**
     * 获取设备信息（包括设备所属地点）
     * @param hostIdStr 设备id串，多个用逗号隔开
     * @param deviceType 设备类型
     * @param currentPage 当前页
     * @param pageSize 每页条数
     * @return
     */
    public Map<String, Object> getHostListWithAreaByPage(String hostIdStr,String deviceType,String currentPage,String pageSize){
        return deviceHostService.getHostListWithAreaByPage(hostIdStr,deviceType,currentPage,pageSize);
    }

    /**
     * 获取设备数量
     * @param deviceType 设备类型
     * @return
     */
    public int hostCount(String deviceType) {
        int hostCount = deviceHostService.getHostCount(deviceType);
        return hostCount;
    }

    /**
     * 根据名称或者ip查询设备信息并分页
     * @param areaId 地点id
     * @param conditions 搜索条件 ip或是设备名称
     * @param deviceType 设备类型
     * @param currentPage 当前页
     * @param pageSize 每页条数
     * @return
     */
    public Map<String, Object> getConditionsHostListByPage(String areaId,String conditions,String deviceType,String currentPage,String pageSize) {
        return deviceHostService.getConditionsHostListByPage(areaId,conditions,deviceType,currentPage,pageSize);
    }

    /**
     * 获取设备列表中的所有设备类型
     * @return
     */
    public List<Map<String, Object>> getExistingTypeList() {
        return deviceHostService.getExistingTypeList();
    }

    /**
     * 事件上报接口
     *
     * @param params
     * @return
     */
    public boolean setHostName(String params) {
        return deviceHostService.updateBatchHostNames(params);
    }

    /**
     * 通过id获取设备信息（设备管理）
     * @param hostId
     * @return
     */
    public Map getHostInfoById(String hostId)
    {
        Map res = deviceHostService.getHostInfoById(hostId);
        logger.debug("获取的设备信息为："+res.toString());
        return res;
    }

    /**
     * 通过终端编码获取捷视飞通设备ids，格式：1,2,3
     * @param code 终端编码
     * @return
     */
    public String getHostIsTourByCode(String code)
    {
        String res = deviceHostService.getHostIsTourByCode(code);
        logger.debug("获取的设备信息为："+res.toString());
        return res;
    }
}
