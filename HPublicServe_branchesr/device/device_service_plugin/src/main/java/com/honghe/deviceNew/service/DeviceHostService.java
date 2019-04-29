package com.honghe.deviceNew.service;

import com.honghe.deviceNew.entity.Device;
import com.honghe.deviceNew.entity.DeviceHost;

import java.util.List;
import java.util.Map;

/**
 * Created by sky on 2017/10/23
 */
public interface DeviceHostService {

    long insert(DeviceHost deviceHost);

    Map<String,Object> addDevice(String deviceType, String hostIp, String typeInt, String className, String factory, String userName, String password, String hostPort, String hostChannel, String isTour, String metString, String cameraString,String mcuCode);

    Map<String,Object> deleteDevices(String hostIdStr,String hostIpStr,String hostTypeStr);

    Boolean deleteHostById(String hostId);

    List<Map<String,Object>> getDeviceInfo(String deviceType) ;

    DeviceHost getDeviceByHostIp(String hostIp);

    boolean updateHostNameById(String hostId, String hostName);

    List<DeviceHost> getDeviceHostList();

    List<Map<String,Object>> getAllHostInfo();

    Map<String, Object> getHostInfoByPage(String hostIdStr, String deviceType, String currentPage, String pageSize);

    Map<String, Object> getDeviceInfoByIp(String hostIp) ;

    int getHostCount(String deviceType);

    List<Map<String, Object>> getExistingTypeList();

    Map<String, Object> getConditionsHostListByPage(String areaId,String conditions,String deviceType,String currentPage,String pageSize);

    Map<String, Object> getHostListWithAreaByPage(String hostIdStr,String deviceType,String currentPage,String pageSize);

    boolean updateBatchHostNames(String params) ;

    List<Map<String,Object>> getMcuInfo(String factory);

    Map<String,Object> getHostInfoById(String hostId);

    int updateDevice(DeviceHost deviceHost);

    String getHostIsTourByCode(String code);

    DeviceHost getDeviceHostByMcuCode(String code);

}
