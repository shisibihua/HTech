package com.honghe.tech.httpservice.impl;

import com.honghe.service.proxy.Result;
import com.honghe.tech.httpservice.AreaHttpService;
import com.honghe.tech.util.ConstantWord;
import com.honghe.tech.util.HttpServerUtil;
import org.springframework.stereotype.Service;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xinqinggang
 */
@Service
public class AreaHttpServiceImpl implements AreaHttpService {
    private Logger logger = Logger.getLogger(AreaHttpServiceImpl.class);

    /**
     * 根据地点id查询子级所有地点
     *
     * @param areaId   地点id
     * @param areaType 地点类型(省份：province;市级：city;县、区：county;校级：school;)
     * @return 子级地点Map集合(areaId : areaName)
     */
    @Override
    public Map<String, String> getChildAreaByAreaId(String areaId, String areaType) {
        Map<String, String> map = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        String method = null;
        switch (areaType) {
            case "province":
                //省
                params.put("provinceId", areaId);
                method = "htechUserGetCityByProvince";
                break;
            case "city":
                params.put("cityId", areaId);
                method = "htechUserGetCountyByCity";
                break;
            case "county":
                params.put("countyId", areaId);
                method = "htechUserGetCampusByCounty";
                break;
            default:
                break;
        }
        try {
            if (method != null && params.size() > 0) {
                Result result = HttpServerUtil.userService(method, params);
                if (result.getCode() == 0) {
                    List<Map<String, String>> list = (List<Map<String, String>>) result.getValue();
                    map = changToMap(list);
                } else {
                    logger.error("请求子地点的code有误，code=" + result.getCode());
                }
            } else {
                logger.error("方法名或参数有误 method=" + method);
            }
        } catch (Exception e) {
            logger.error("根据地点id获取子级地点异常，type=" + areaType + ",id=" + areaId, e);
        }
        return map;
    }

    /**
     * 将返回的地点数据转换为需要的格式
     *
     * @param list 地点集合
     * @return （地点id : 名称）
     */
    private Map<String, String> changToMap(List<Map<String, String>> list) {
        Map<String, String> map = new HashMap<>();
        if (list != null) {
            for (Map<String, String> area : list) {
                map.put(area.get("areaId"), area.get("areaName"));
            }
        }
        return map;
    }

    @Override
    public String getRoomIdByHostIp(String hostIp) {
        String roomId = "";
        try {
            Map<String, String> params = new HashMap<>();
            params.put("hostIp", hostIp);
            Result result = HttpServerUtil.areaService("getRoomByHostIp", params);
            roomId = getIdinResult(roomId, result);
        } catch (Exception e) {
            roomId = "";
            logger.error("根据终端ip查询所在教室id异常，hostIp=" + hostIp, e);
        }
        return roomId;
    }
    @SuppressWarnings("unchecked")
    private String getIdinResult(String roomId, Result result) {
        if (result.getCode() == 0 && result.getValue() != null && !"".equals(result.getValue())) {
            Map<String, String> map = (Map<String, String>) result.getValue();
            if (map != null && !map.isEmpty()) {
                roomId = map.get("id");
            } else {
                roomId = "";
            }
        }
        return roomId;
    }

    @Override
    public String getRoomIdByHostId(String hostId) {
        String roomId = "";
        try {
            Map<String, String> params = new HashMap<>();
            params.put("hostId", hostId);
            Result result = HttpServerUtil.areaService("getRoomByHostId", params);
            roomId = getIdinResult(roomId, result);
        } catch (Exception e) {
            roomId = "";
            logger.error("根据终端id查询所在教室id异常，hostId=" + hostId, e);
        }
        return roomId;
    }

    @Override
    public String getRoomIdByHostCode(String hostCode) {
        String roomId = "";
        try {
            Map<String, String> params = new HashMap<>();
            params.put("mcuCode", hostCode);
            Result result = HttpServerUtil.areaService("getRoomByCode", params);
            roomId = getIdinResult(roomId, result);
        } catch (Exception e) {
            roomId = "";
            logger.error("根据终端code查询所在教室id异常，hostCode=" + hostCode, e);
        }
        return roomId;
    }

    @Override
    public Map<String, String> getHostByRoomId(String roomId) {
        Map<String, String> host = new HashMap<>();
        try {
            Map<String, String> params = new HashMap<>();
            params.put("roomId", roomId);
            params.put("dtypeName", ConstantWord.HHT_MCU);
            Result result = HttpServerUtil.areaService("getHostByRoomId", params);
            if (result.getCode() == 0 && result.getValue() != null && !"".equals(result.getValue())) {
                host = (Map<String, String>) result.getValue();
            }
        } catch (Exception e) {
            logger.error("根据教室id查询设备信息异常，roomId=" + roomId, e);
        }
        return host;
    }

    @Override
    public String getAllAreaIds() {
        String areaIds = "";
        try {
            Map<String, String> params = new HashMap<>();
            Result result = HttpServerUtil.areaService("getAllAreaIds", params);
            if (result.getCode() == 0 && result.getValue() != null && !"".equals(result.getValue())) {
                areaIds = String.valueOf(result.getValue());
            }
        } catch (Exception e) {
            logger.error("获取所有教室ids异常，areaIds=" + areaIds, e);
        }
        return areaIds;
    }
}
