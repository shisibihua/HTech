package com.honghe.tech.httpservice;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * @author HOUJT
 * 注：主讲控制终端与设备通信不通过设备服务，采用直连方式，与设备直接通信
 */
public interface DeviceHttpService {


    /**
     * 呼叫终端
     * @param url        被叫url，以sip:或h323:开始
     * @param name       终端名称
     * @param rate       呼叫速率
     * @param audio      可选: inactive: 不启用音频；sendonly:只发送不接收音频；recvonly:只接收不发送音频；sendrecv:收发音频。默认为sendrecv
     * @param masterIp   主讲教室终端ip
     * @return object
     * @author  caoqian
     */
    Object callClient(String url,String name,String rate,String audio,String masterIp);

    /**
     * 挂断终端
     * @param url        被叫url，以sip:或h323:开始
     * @param name       终端名称
     * @param masterIp   主讲教室终端ip
     * @return object
     * @author caoqian
     */
    Object disconClient(String url,String name,String masterIp);

    /**
     * 挂断全部终端
     * @param url        被叫url，以sip:或h323:开始
     * @param masterIp   主讲教室终端ip
     * @return object
     * @author caoqian
     */
    Object disconAllClient(String url,String masterIp);

    /**
     * 请求呼叫记录
     * @param url        被叫url，以sip:或h323:开始
     * @param masterIp   主讲教室终端ip
     * @return object
     * @author caoqian
     */
    Object callRecord(String url,String masterIp);

    /**
     * 终端静音
     * @param url          被叫url，以sip:或h323:开始
     * @param muteAudio    false：代表不静音，true：代表静音
     * @param name         终端名称
     * @param masterIp     主讲教室终端ip
     * @param acceptIp     接收教室终端ip
     * @return object
     * @author caoqian
     */
    Object muteClient(String url,String muteAudio,String name,String masterIp,String acceptIp);

    /**
     * 开启互动
     * @param url          被叫url，以sip:或h323:开始
     * @param name         终端名称
     * @param interact     false：代表关闭互动，true：代表开启互动
     * @param masterIp    主讲教室终端ip
     * @param acceptIp    接收教室终端ip
     * @return object
     * @author caoqian
     */
    Object interactionConference(String url,String name,String interact,String masterIp,String acceptIp);

    /**
     * 获取终端在会状态
     * @param url         被叫url，以sip:或h323:开始
     * @param name        接收终端名称
     * @param masterIp    主讲终端ip
     * @return object
     * @author caoqian
     */
    Object searchTerminalStatus(String url,String name,String masterIp);

    /**
     * 查询终端互动状态
     * @param masterIp    主讲终端ip
     * @return object
     * @author caoqian
     */
    Object searchQueryInteract(String masterIp);

    /**
     * 创建MCU教学活动
     * @param meetingName 教学活动名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param roomJsonArray 终端信息（主讲教室在前，接收教室在后）
     * @param mcuIp mcu的ip
     * @return
     */
    JSONObject addActivity(String meetingName, String startTime, String endTime, JSONArray roomJsonArray, String mcuIp);

    /**
     * 获取MCU列表
     * @return
     */
    JSONObject getMCUList();

    /**
     * 南京旭顶获取终端状态
     * @param terminalId    终端id
     * @return
     */
    JSONObject getTerminalStatusForNjxd(String terminalId);

    /**
     * 南京旭顶终端静音
     * @param conferenceId   会议id
     * @param terminalId     终端id
     * @param isMute         是否静音，0：静音；1：不静音
     * @return
     */
    JSONObject micMuteForNjxd(String conferenceId, String terminalId,String isMute);

    /**
     * 南京旭顶开启/关闭互动
     * @param conferenceId     会议id
     * @param splitMode        1p是主讲模式，2p1是互动模式
     * @param terminalList     json数组，如 [{"partyID":"1234014","partyIP":"192.168.20.7","partyType":"h323"}]
     * @return
     */
    JSONObject layoutForNjxd(String conferenceId,String splitMode,String terminalList);

    /**
     * 南京旭顶获取正在互动的终端
     * @param confId     会议id
     * @return
     */
    JSONObject getInteractPartyForNjxd(String confId);

    /**
     * 删除mcu会议
     * @param confId  会议id
     * @param confName  会议名称
     * @author caoqian
     * @return
     */
    boolean delConference(String confId, String confName);

    /**
     * 连接/断开终端
     * @param conferenceId 会议Id
     * @param conferenceName 会议名称
     * @param terminalId 终端id
     * @param terminalIp 终端ip
     * @param terminalName 终端名称
     * @param isConn true:连接,false:断开.
     * @return
     */
     JSONObject connTerminalForNjxd(String conferenceId,String conferenceName,String terminalId,String terminalIp,String terminalName,String isConn);

    /**
     * 添加终端到mcu
     * @param partyName   终端名称
     * @param partyId     终端id
     * @param partyIp     终端ip
     * @return
     */
    JSONObject addHostToMcu(String partyName,String partyId,String partyIp);

    /**
     * 根据设备ip查询设备信息
     * @param ip
     * @return
     */
     Map getHostByIp(String ip);

    /**
     * 根据设备ip获取设备编码
     * @param ip
     * @return
     */
     String getHostCodeByIp(String ip);

    /**
     * 根据终端编码获取捷士飞通终端id
     * @param code   终端编码
     * @return
     */
    String getHostIsTourByCode(String code);
}