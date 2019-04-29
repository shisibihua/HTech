package com.honghe.tech.service;

import java.util.Map;

/**
 * 教学监管方法
 * @author caoqian
 */
public interface TeachingSupervisionService {
    /**
     * 分页获取教学监管直播数据
     * @param provinceId              省id
     * @param cityId                 城市id
     * @param contyId                区(县)id
     * @param schoolId               学校id
     * @param selectType             教室状态，''-全部教室，0-未上课，1-上课中
     * @param currentPage            当前页
     * @param pageSize               每页数据条数
     * @return
     */
    Map<String,Object> techSuperviseListByPage(String provinceId,String cityId, String contyId, String schoolId,
                                                               String selectType, String currentPage, String pageSize);

    /**
     * 1拖三教学监管，查看详情
     * @param uuid          教学活动与接收表对应uuid
     * @param currentPage   当前页
     * @param pageSize      每页数据条数
     * @return  map         教学监管一拖三数据
     */
    Map<String,Object> techSuperviseList(String uuid, String currentPage, String pageSize);

    /**
     * 获取教室直播流地址
     * @param mcuClientCode   终端编码号,如‘123407’
     * @return  String       教室直播流地址,格式‘http://192.168.16.108/live/123407’
     */
    String getRtmpAddr(String mcuClientCode);

    /**
     * 获取终端截图,对接互动1.0主讲控制终端
     * @param ip  终端ip
     * @return
     */
    String getScreenPicForHk2000(String ip);
}
