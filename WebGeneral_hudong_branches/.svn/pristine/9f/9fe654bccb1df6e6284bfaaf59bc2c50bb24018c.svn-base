package com.honghe.tech.httpservice;

import java.util.Map;

/**
 * 地点服务所需接口
 * @author caoqian
 */
public interface AreaHttpService {
    /**
     * 根据地点id查询子级所有地点
     * @param areaId 地点id
     * @param areaType 地点类型（省份：province;市级：city;县、区：county;校级：school;）
     * @return 地点Map(areaId:areaName)
     */
    Map<String,String> getChildAreaByAreaId(String areaId,String areaType);

    /**
     * 根据终端ip获取终端所在教室id
     * @param hostIp  终端ip
     * @return String 教室id
     * @author caoqian
     */
    String getRoomIdByHostIp(String hostIp);

    /**
     * 根据终端id获取终端所在教室id
     * @param hostId  终端id
     * @return String 教室id
     * @author caoqian
     */
    String getRoomIdByHostId(String hostId);

    /**
     * 根据终端编码id获取终端所在教室id
     * @param hostCode  终端编码
     * @return String 教室id
     * @author caoqian
     */
    String getRoomIdByHostCode(String hostCode);

    /**
     * 根据教室id查询终端信息
     * @param roomId  教室id
     * @return map 终端信息
     * @author caoqian
     */
    Map<String,String> getHostByRoomId(String roomId);

    /**
     * 获取所有教室ids，多个“，”隔开
     * @return String
     * @author caoqian
     */
    String getAllAreaIds();

}
