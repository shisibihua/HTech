package com.honghe.device.command.impl;
import com.hht.DeviceManager.DeviceManager;
import com.hht.DeviceManager.operationRequest.mcu.*;
import com.hht.model.Conference;
import com.hht.model.Party;
import com.honghe.device.command.DeviceCommand;
import com.honghe.device.util.JsonUtil;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import java.util.*;
import java.util.List;

/**
 * Created by lyx on 2016-05-20.
 */
public class InteractiveTeachingCommand implements DeviceCommand {
    private final static Logger logger = Logger.getLogger(InteractiveTeachingCommand.class);

    /**
     * 同步mcu设备时间
     * @param dateTime 设置的时间
     * @param mcuIp mcu设备的ip
     * @return
     */
    public Object setMcuSysTime(String dateTime,String mcuIp){
        Object re_value = "";
        try {
            re_value = DeviceManager.invoke(mcuIp,new OperationRequestSetSysTime(dateTime));
            logger.debug("mcu设备ip为："+mcuIp+",同步mcu设备时间为："+dateTime);
        } catch (Exception e) {
            logger.error("同步mcu设备时间失败",e);
        }
        return re_value;
    }
    /**
     * 添加终端
     * @param terminalIp  终端ip
     * @param terminalName 终端名称
     * @param terminalType 终端类型（目前只有一种类型：h323）
     * @param mcuIp  所属mcu的ip
     * @return
     */
    public Object addTerminal(String terminalIp, String terminalName, String terminalType, String mcuIp){
        Object re_value = null;
        Party party = new Party();
        party.setPartyIP(terminalIp);
        party.setPartyName(terminalName);
        party.setPartyType(terminalType);
        try {
            re_value = DeviceManager.invoke(mcuIp, new OperationRequestAddParty(party));
            logger.debug("添加终端信息所属的mcuIp为 "+mcuIp+"，re_value为"+re_value);
        } catch (Exception e) {
            logger.error("添加终端失败",e);
        }
        return re_value;
    }

    /**
     * 删除终端
     * @param terminalIp 终端ip
     * @param terminalName 终端名称
     * @param terminalType 终端类型（目前只有一种类型：h323）
     * @param terminalId 终端id
     * @param mcuIp 所属mcu的ip
     * @return
     */
    public Object delTerminal(String terminalIp, String terminalName, String terminalType, String terminalId, String mcuIp){
        Object re_value = "";
        Party party = new Party();
        party.setPartyType(terminalType);
        party.setPartyIP(terminalIp);
        party.setPartyName(terminalName);
        party.setPartyID(terminalId);
        try {
            re_value = DeviceManager.invoke(mcuIp,new OperationRequestDelParty(party));
            logger.debug("删除终端信息所属的mcuIp为 "+mcuIp+"，re_value为"+re_value);
        } catch (Exception e) {
            logger.error("删除终端失败", e);
        }
        return re_value;
    }

    /**
     * 创建会议
     * @param meetingId 会议id，这里固定传“0”
     * @param meetingRoom 主讲教室和听讲教室信息（party对象）
     * @param meetingName 会议名称
     * @param meetingNum 会议编号
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param mcuIp mcu的ip
     * @return
     */
    public Object createConference(String meetingId,String meetingRoom,String meetingName,String meetingNum,String startTime,String endTime,String mcuIp){
        Object re_value = "";
        Conference conference = new Conference();

        JSONArray json = JSONArray.fromObject(meetingRoom);
        List partyLists = (List) JsonUtil.jsonToList(json);
        List<Party> listParty = new ArrayList<>();
        for (Object list : partyLists) {
            Map map = (Map) list;
            Party party = new Party();
            party.setPartyID(String.valueOf(map.get("partyID")));
            party.setPartyName(String.valueOf(map.get("partyName")));
            party.setPartyType(String.valueOf(map.get("partyType")));
            party.setPartyIP(String.valueOf(map.get("partyIP")));
            listParty.add(party);
        }
        conference.setConfID(meetingId);
        conference.setParties(listParty);
        conference.setConfName(meetingName);
        conference.setConfNum(meetingNum);
        conference.setStartTime(startTime);
        conference.setEndTime(endTime);
        try {
            re_value = DeviceManager.invoke(mcuIp,new OperationRequestCreateConference(conference));
            logger.debug("创建会议所属的mcuIp为 "+mcuIp+"，re_value为"+re_value);
        } catch (Exception e) {
            logger.error("创建会议失败", e);
        }
        return re_value;
    }

    /**
     * 删除会议
     * @param conferenceId 会议Id
     * @param conferenceName 会议名称
     * @param mcuIp 会议所属mcu的ip
     * @return
     */
    public Object delConference(String conferenceId,String conferenceName,String mcuIp){
        Object re_value = "";
        try {
             re_value = DeviceManager.invoke(mcuIp,new OperationRequestDelConference(conferenceId,conferenceName));
            logger.debug("删除会议所属的mcuIp为 "+mcuIp+"，re_value为"+re_value);
        } catch (Exception e) {
            logger.error("删除会议失败", e);
        }
        return re_value;
    }

    /**
     * 结束会议
     * @param conferenceId 会议Id
     * @param conferenceName 会议名称
     * @param mcuIp 会议所属mcu的ip
     * @return
     */
    public Object finishConference(String conferenceId,String conferenceName,String mcuIp){
        Object re_value = "";
        try {
            re_value = DeviceManager.invoke(mcuIp,new OperationRequestEndConference(conferenceId,conferenceName));
            logger.debug("结束会议所属的mcuIp为 "+mcuIp+"，re_value为"+re_value);
        } catch (Exception e) {
            logger.error("结束会议失败", e);
        }
        return re_value;
    }

    /**
     * 获取终端缩略图
     * @param conferenceId 会议Id
     * @param conferenceName 会议名称
     * @param terminalId 终端id
     * @param terminalIp 终端ip
     * @param terminalName 终端名称
     * @param terminalType 终端类型（目前只有一种类型：h323）
     * @param mcuIp 所属mcu的ip
     * @return
     */
    public Object getTerminalPic(String conferenceId,String conferenceName,String terminalId,String terminalIp,String terminalName,String terminalType,String mcuIp){
        Object re_value = "";
        Conference conference = new Conference();
        Party party = new Party();
        conference.setConfID(conferenceId);
        conference.setConfName(conferenceName);
        party.setPartyID(terminalId);
        party.setPartyName(terminalName);
        party.setPartyIP(terminalIp);
        party.setPartyType(terminalType);
        try {
            re_value = DeviceManager.invoke(mcuIp,new OperationRequestGetPartyPic(conference,party));
            logger.debug("获取终端缩略图所属的mcuIp为 "+mcuIp+"，re_value为"+re_value);
        } catch (Exception e) {
            logger.error("获取终端缩略图失败", e);
        }
        return re_value;
    }

    /**
     * 加入会议
     * @param conferenceId 会议Id
     * @param conferenceName
     * @param terminalList 终端信息的json数组
     * @param mcuIp  所属mcu的ip
     * @return
     */
    public Object joinConference(String conferenceId, String conferenceName, String terminalList, String mcuIp){
        Object re_value = "";
        JSONArray json = JSONArray.fromObject(terminalList);
        List partyList = (List) JsonUtil.jsonToList(json);
        List<Party> partiesList = new ArrayList<>();
        for (Object list :partyList){
            Party party = new Party();
            Map map = (Map)list;
            party.setPartyID(String.valueOf(map.get("partyID")));
            party.setPartyName(String.valueOf(map.get("partyName")));
            party.setPartyIP(String.valueOf(map.get("partyIP")));
            party.setPartyType(String.valueOf(map.get("partyType")));
            partiesList.add(party);
        }
        try {
            re_value = DeviceManager.invoke(mcuIp,new OperationRequestJoinConference(conferenceId,conferenceName,partiesList));
            logger.debug("移除终端所属mcuIp为 "+mcuIp+"，re_value为"+re_value);
        } catch (Exception e) {
            logger.error("加入会议失败", e);
        }
        return re_value;

    }

    /**
     * 移除终端（从某个会议中踢出，不是删除）
     * @param conferenceId 会议Id
     * @param conferenceName 会议名称
     * @param terminalId  终端id
     * @param terminalIp 终端ip
     * @param terminalName 终端名称
     * @param terminalType 终端类型
     * @param mcuIp 所属mcu的ip
     * @return
     */
    public Object  removeTerminal(String conferenceId,String conferenceName,String terminalId,String terminalIp,String terminalName,String terminalType,String mcuIp){
        Object re_value = "";
        Party party = new Party();
        party.setPartyIP(terminalIp);
        party.setPartyType(terminalType);
        party.setPartyName(terminalName);
        party.setPartyID(terminalId);
        try {
            re_value = DeviceManager.invoke(mcuIp,new OperationRequestRemoveParty(conferenceId,conferenceName,party));
            logger.debug("mcuIp为 "+mcuIp+"，re_value为"+re_value);
        } catch (Exception e) {
            logger.error("移除终端失败", e);
        }
        return re_value;
    }

    /**
     *   终端静音
     * @param conferenceId 会议Id
     * @param conferenceName 会议名称
     * @param terminalId 终端id
     * @param terminalIp 终端ip
     * @param terminalName 终端名称
     * @param isMute true:静音,false:不静音
     * @param terminalType 终端类型
     * @param mcuIp mcu的ip
     * @return
     */
    public Object micMute(String conferenceId,String conferenceName,String terminalId,String terminalIp,String terminalName,String terminalType,String isMute,String mcuIp){
        Object re_value = "";
        Party party = new Party();
        party.setPartyID(terminalId);
        party.setPartyIP(terminalIp);
        party.setPartyName(terminalName);
        party.setPartyType(terminalType);
        try {
            re_value = DeviceManager.invoke(mcuIp,new OperationRequestMicMute(conferenceId,conferenceName,party, Boolean.parseBoolean(isMute)));
            logger.debug("终端静音所操作的mcuip为 "+mcuIp+"，re_value为"+re_value);
        } catch (Exception e) {
            logger.error("终端静音失败", e);
        }
        return re_value;
    }
    /**
     * 连接/断开终端
     * @param conferenceId 会议Id
     * @param conferenceName 会议名称
     * @param terminalId 终端id
     * @param terminalIp 终端ip
     * @param terminalName 终端名称
     * @param isConn true:连接,false:断开.
     * @param terminalType 终端类型
     * @param mcuIp mcu的ip
     * @return
     */
        public Object connTerminal (String conferenceId,String conferenceName,String terminalId,String terminalIp,String terminalName,String terminalType,String isConn,String mcuIp){
            Object re_value = "";
            Party party = new Party();
            party.setPartyID(terminalId);
            party.setPartyIP(terminalIp);
            party.setPartyName(terminalName);
            party.setPartyType(terminalType);
            try {
                re_value = DeviceManager.invoke(mcuIp,new OperationRequestConnParty(conferenceId,conferenceName,party,Boolean.parseBoolean(isConn)));
                logger.debug("连接断开终端所操作的mcuip为 "+mcuIp+"，re_value为"+re_value);
            } catch (Exception e) {
                logger.error("连接断开终端失败", e);
            }
            return re_value;
        }

    /**
     * 获取终端状态
     * @param conferenceId 会议Id
     * @param conferenceName 会议名称
     * @param terminalId 终端id
     * @param terminalIp 终端ip
     * @param terminalName 终端名称
     * @param terminalType 终端类型
     * @param mcuIp mcu的ip
     * @return
     */
    public Object getTerminalStatus(String conferenceId,String conferenceName,String terminalId,String terminalIp,String terminalName,String terminalType,String mcuIp){
        Object re_value = "";
        Party party = new Party();
        party.setPartyID(terminalId);
        party.setPartyIP(terminalIp);
        party.setPartyName(terminalName);
        party.setPartyType(terminalType);
        try {
            re_value = DeviceManager.invoke(mcuIp,new OperationRequestGetPartyStatus(conferenceId,conferenceName,party));
            logger.debug("获取终端的mcuip为 "+mcuIp+"，re_value为"+re_value);
        } catch (Exception e) {
            logger.error("获取终端失败", e);
        }
        return re_value;
    }

    /**
     * 分屏设置
     * @param conferenceId 会议Id
     * @param conferenceName 会议名称
     * @param splitMode 分屏方式，目前有：0p,1p,2p1,2p2,3p1,3p2,3p3,4p1,4p2,4p3,4p4
     * @param terminalList 终端信息的json数组
     * @param mcuIp mcu的ip
     * @return
     */
    public Object screenLayout(String conferenceId,String conferenceName,String splitMode,String terminalList,String mcuIp){
        Object re_value = "";
        JSONArray json = JSONArray.fromObject(terminalList);
        List partyList = (List) JsonUtil.jsonToList(json);
        List<Party> partiesList = new ArrayList<>();
        for (Object list :partyList){
            Party party = new Party();
            Map map = (Map)list;
            party.setPartyID(String.valueOf(map.get("partyID")));
            party.setPartyName(String.valueOf(map.get("partyName")));
            party.setPartyIP(String.valueOf(map.get("partyIP")));
            party.setPartyType(String.valueOf(map.get("partyType")));
            partiesList.add(party);
        }
        try {
            re_value = DeviceManager.invoke(mcuIp,new OperationRequestScreenLayout(conferenceId,conferenceName,splitMode,partiesList));
            logger.debug("分屏的mcuip为 "+mcuIp+"，re_value为"+re_value);
        } catch (Exception e) {
            logger.error("分屏设置失败", e);
        }
        return re_value;
    }

    /**
     *
     * 编辑会议时间
     * @param conferenceName 会议名称
     * @param conferenceId 会议Id
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param mcuIp mcu的ip
     * @return
     */
    public Object updateConferenceTime(String conferenceName,String conferenceId,String startTime,String endTime,String mcuIp){
        Object re_value = "";
        Conference conference = new Conference();
        conference.setConfName(conferenceName);
        conference.setConfID(conferenceId);
        conference.setStartTime(startTime);
        conference.setEndTime(endTime);
        try {
            re_value = DeviceManager.invoke(mcuIp,new OperationRequestUpdateConfTime(conference));
            logger.debug("编辑会议时间操作的mcuip为 "+mcuIp+"，re_value为"+re_value);
        } catch (Exception e) {
            logger.error("编辑会议时间失败", e);
        }
        return re_value;
    }
    public Object getConfList(String mcuIp){
        Object re_value = "";
        try {
            re_value = DeviceManager.invoke(mcuIp,new OperationRequestGetConfList());
            logger.debug("mcuIp为："+mcuIp+"获取会议列表信息为："+re_value.toString());
        } catch (Exception e) {
            logger.error("获取会议列表失败", e);
        }
        return re_value;
    }

    @Override
    public Boolean boot(String ip) {
        return null;
    }

    @Override
    public boolean shutdown(String ip, String cmdCode) {
        return false;
    }

    @Override
    public Integer getVolume(String ip, String ext) {
        return null;
    }

    // TODO: 2018/2/27 互动教学新增接口 mz
    // 呼叫终端
    public Object callClient(String url, String name, String rate, String audio, String masterIp){
        Object re_value = "";
        try {
            re_value = DeviceManager.invoke(masterIp , new OperationRequestCallClient(url, name, rate, audio, masterIp));
            logger.debug("呼叫终端操作的mcuip为 "+masterIp+"，re_value为"+re_value);
        } catch (Exception e) {
            logger.error("呼叫终端失败", e);
        }
        return re_value;
    }

    // 挂断终端
    public Object hungupClient(String url, String name, String masterIp){
        Object re_value = "";
        try {
            re_value = DeviceManager.invoke(masterIp, new OperationRequestHungupClient(url, name, masterIp));
            logger.debug("挂断终端操作的mcuip为 "+masterIp+"，re_value为"+re_value);
        } catch (Exception e) {
            logger.error("挂断终端失败", e);
        }
        return re_value;
    }

    // 挂断全部终端
    public Object hungupAllClient(String url, String masterIp){
        Object re_value = "";
        try {
            re_value = DeviceManager.invoke(masterIp, new OperationRequestHungupAllClient(masterIp));
            logger.debug("挂断终端操作的mcuip为 "+masterIp+"，re_value为"+re_value);
        } catch (Exception e) {
            logger.error("挂断终端失败", e);
        }
        return re_value;
    }

    // 请求呼叫记录
    public Object callRecord(String url, String masterIp){
        Object re_value = "";
        try {
            re_value = DeviceManager.invoke(masterIp,new OperationRequestCallRecord(masterIp));
            logger.debug("请求呼叫记录操作的mcuip为 "+masterIp+"，re_value为"+re_value);
        } catch (Exception e) {
            logger.error("请求呼叫记录失败", e);
        }
        return re_value;
    }

    // 终端静音
    public Object muteClient(String url, String mute_audio, String name, String auditorIp, String masterIp){
        Object re_value = "";
        try {
            re_value = DeviceManager.invoke(masterIp, new OperationRequestMuteClient(url, mute_audio, name, auditorIp,masterIp));
            logger.debug("终端静音操作的mcuip为 "+masterIp+"，re_value为"+re_value);
        } catch (Exception e) {
            logger.error("终端静音失败", e);
        }
        return re_value;
    }

    // 开启互动
    public Object interact(String url, String name, String interact, String auditorIp, String masterIp){
        Object re_value = "";
        try {
            re_value = DeviceManager.invoke(masterIp, new OperationRequestInteract(url, name, interact, auditorIp, masterIp));
            logger.debug("开启互动操作的mcuip为 "+masterIp+"，re_value为"+re_value);
        } catch (Exception e) {
            logger.error("开启互动失败", e);
        }
        return re_value;
    }

    // 查询终端互动状态
    public Object queryInteract(String masterIp){
        Object re_value = "";
        try {
            re_value = DeviceManager.invoke(masterIp, new OperationRequestQueryInteract(masterIp));
            logger.debug("终端的mcuip为 "+masterIp+"，re_value为"+re_value);
        } catch (Exception e) {
            logger.error("查询终端互动状态操作失败", e);
        }
        return re_value;
    }

    // 查询终端在会状态
    public Object searchTerminalStatus(String url,String name,String masterIp){
        Object re_value = "";
        try {
            re_value = DeviceManager.invoke(masterIp, new OperationRequestSearchTerminalStatus(url,name,masterIp));
            logger.debug("终端的mcuip为 "+masterIp+"，re_value为"+re_value);
        } catch (Exception e) {
            logger.error("查询终端在会状态操作失败", e);
        }
        return re_value;
    }
    // 查询南京旭顶互动终端操作
    public Object getInteractParty(String confID,String mcuIp){
        Object re_value = "";
        try {
            re_value = DeviceManager.invoke(mcuIp, new OperationRequestSearchInteractParty(confID));
            logger.debug("mcuIp为 "+mcuIp+"，re_value为"+re_value);
        } catch (Exception e) {
            logger.error("查询南京旭顶互动终端操作失败", e);
        }
        return re_value;
    }
}
