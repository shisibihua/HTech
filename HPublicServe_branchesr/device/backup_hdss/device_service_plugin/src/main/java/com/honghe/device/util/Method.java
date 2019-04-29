package com.honghe.device.util;

/**
 * Created by lyx on 2015-12-10.
 * 方法名
 */
public enum Method {

    Device_Discover("discover"),
    Device_GetLocalModel("getLocalModel"),
    Device_Addhost("addhost"),
    Device_Manual_Discover("manualDiscover"),
    Device_Is_Online("isOnline"),

    Device_Get_Status("getStatus"),
    Device_Update("update"),
    Device_HostInfo("hostInfo"),
    Device_HostInfoByPage("hostInfoByPage"),
    Device_NotHostInfo("notHostInfo"),

    Device_DelHost("delHost"),
    Device_UpdateHostName("updateHostName"),
    Device_UpdateHostInfo("updateHostInfo"),
    Device_IsHostNameExist("isHostNameExist"),
    Device_HostCount("hostCount"),
    Device_GetHostInfoById("getHostInfoById"),

    Device_HostInfoBytype("hostInfoBytype"),
    Device_NotHostInfoByPage("notHostInfoByPage"),
    Device_GetHostInfoByIp("getHostInfoByIp"),
    Device_GetAllHostList("getAllHostList"),
    Device_Command_Boot("boot"),
    Device_getMcuHostList("getMcuHostList"),
    Device_Command_Shutdown("shutdown"),
    Device_Command_ChangeSignal("changeSignal"),
    Device_Command_ChangeAudioMode("changeAudioMode"),
    Device_Command_ChangeAudioControl("changeAudioControl"),
    Device_Command_GetInfo("getInfo"),

    Device_Command_SendMessage("sendMessage"),
    Device_Command_GetPictrue("getPictrue"),
    Device_Command_UpdateCmdCodeSql("updateCmdCodeSql"),
    Device_Command_IsDeviceCmdCodeTimeOut("isDeviceCmdCodeTimeOut"),
    Device_Command_SetSysTime("setSysTime"),

    Device_Command_GetCmd("getCmd"),
    Device_Command_ChangeRemoteEnergy("changeRemoteEnergy"),
    Device_Command_SendAddress("sendAddress"),
    Device_Command_RegistEventAdrr("registEventAdrr"),
    Device_Command_Ring("ring"),

    Device_Command_Backup("backup"),
    Device_Command_Recovery("recovery"),
    Device_Command_ClearRubish("clearRubish"),
    Device_Command_OneKeyInstall("oneKeyInstall"),  //一键安装
    Device_Command_OneKeyUninstall("oneKeyUninstall"),//一键卸载

    Device_Command_AtvOrDtv("atvOrDtv"),
    Device_Hhrec_GetOnlineDevice("getOnlineDevice"),
    Device_Hhrec_GetCameraUrl("getCameraUrl"),
    Device_HhrecCommand_SetLogo("setLogo"),
    Device_HhrecCommand_GetLogo("getLogo"),

    Device_HhrecCommand_RemoveLogo("removeLogo"),
    Device_HhrecCommand_SetResolution("setResolution"),
    Device_HhrecCommand_GetResolution("getResolution"),
    Device_HhrecCommand_SetBackgroundDirectMode("setBackgroundDirectMode"),
    Device_HhrecCommand_SetBitrate("setBitrate"),

    Device_HhrecCommand_GetBitrate("getBitrate"),
    Device_HhrecCommand_SetNas("setNas"),
    Device_HhrecCommand_ClearNas("clearNas"),
    Device_HhrecCommand_GetRecordingStatus("getRecordingStatus"),
    Device_HhrecCommand_GetRecording("getRecording"),

    Device_HhrecCommand_GetVolume("getVolume"),
    Device_HhrecCommand_GetCaption("getCaption"),
    Device_HhrecCommand_GetTitleVideo("getTitleVideo"),
    Device_HhrecCommand_GetEndingVideo("getEndingVideo"),
    Device_HhrecCommand_GetCourseInfo("getCourseInfo"),

    Device_HhrecCommand_GetMainTokenByHostid("getMainTokenByHostid"),
    Device_HhrecCommand_GetDirectMode("getDirectMode"),
    Device_HhrecCommand_StartRecord("startRecord"),
    Device_HhrecCommand_StopRecord("stopRecord"),
    Device_HhrecCommand_AddPlanCommand("addPlanCommand"),

    Device_HhrecCommand_GetAllPlan("getAllPlan"),
    Device_HhrecCommand_GetCurrentLayout("getCurrentLayout"),
    Device_HhrecCommand_GetLive("getLive"),
    Device_HhrecCommand_GetAllMediaToken("getAllMediaToken"),
    Device_HhrecCommand_GetCameraUrl("getCameraUrl"),
    Device_HhrecCommand_GetCameraInfoById("getCameraInfoById"),

    Device_HhrecCommand_GetLayout("getLayout"),
    Device_HhrecCommand_StartStreaming("startStreaming"),
    Device_HhrecCommand_RestartStreaming("restartStreaming"),
    Device_HhrecCommand_StopStreaming("stopStreaming"),
    Device_HhrecCommand_PauseStreaming("pauseStreaming"),

    Device_HhrecCommand_PauseRecording("pauseRecording"),
    Device_HhrecCommand_SetCourseInfo("setCourseInfo"),
    Device_HhrecCommand_SetEndingVideo("setEndingVideo"),
    Device_HhrecCommand_SetTitleVideo("setTitleVideo"),
    Device_HhrecCommand_SetLayout("setLayout"),

    Device_HhrecCommand_PTZStartMove("PTZStartMove"),
    Device_HhrecCommand_PTZStopMove("PTZStopMove"),
    Device_HhrecCommand_GetNasSettings("getNasSettings"),
    Device_HhrecCommand_SetNasSettings("setNasSettings"),
    Device_HhrecCommand_SetLayoutConfigurations("setLayoutConfigurations"),

    Device_HhrecCommand_GetPresets("getPresets"),
    Device_HhrecCommand_SetPresets("setPresets"),
    Device_HhrecCommand_InsertKeyNote("insertKeyNote"),
    Device_HhrecCommand_RestartRecording("restartRecording"),
    Device_HhrecCommand_SettingMutilScreen("settingMutilScreen"),

    Device_HhrecCommand_RemoveTitleVideo("removeTitleVideo"),
    Device_HhrecCommand_RemoveEndVideo("removeEndVideo"),
    Device_HhrecCommand_DelPlan("delPlan"),
    Device_HhrecCommand_DelAllPlan("delAllPlan"),
    Device_HhrecCommand_GetSupports("getSupports"),

    Device_HhrecCommand_SetVideoRecorderInfo("setVideoRecorderInfo"),
    Device_ManualNetDiscovered("manualNetDiscovered"),
    Device_GetIp("getIp"),
    Device_GetNameByMac("getNameByMac"),//根据mac地址获取设备名
    Device_Command_SetProjectorTurnState("setProjectorTurnState"),//投影机设备开关机

    Device_HhtcCommand_GetScreenLockMode("getScreenLockMode"),//获得大屏屏幕锁定状态
    Device_HhtcCommand_SetScreenLockMode("setScreenLockMode"),//设置大屏屏幕锁定状态
    Device_HhtcCommand_GetPanelKeyLockMode("getPanelKeyLockMode"),//获得大屏面板按键锁定状态
    Device_HhtcCommand_SetPanelKeyLockMode("setPanelKeyLockMode"),//设置大屏面板按键锁定状态
    Device_HtprCommand_GetProjectorAllState("getProjectorAllState"),//投影仪获得设备所有状态

    Device_HhtwbCommand_SetBoardOneKeyLock("setBoardOneKeyLock"),//一体机一键锁定、解除锁定
    Device_HhtwbCommand_GetBoardOneKeyLockState("getBoardOneKeyLockState"),//一体机获得一键锁定状态
    Device_HhtwbCommand_SetBoardMuteState("setBoardMuteState"),//一体机设置静音状态
    Device_HhtwbCommand_GetBoardMuteState("getBoardMuteState"),//一体机获得静音状态
    Device_HhtwbCommand_SetBoardIncreaseVolume("setBoardIncreaseVolume"),//一体机设置音量增加

    Device_HhtwbCommand_SetBoardDecreaseVolume("setBoardDecreaseVolume"),//一体机设置音量减少
    Device_HhtwbCommand_SetBoardVolume("setBoardVolume"),//一体机设置音量值
    Device_HhtwbCommand_GetBoardVolume("getBoardVolume"),//一体机获取音量值
    Device_HhtwbCommand_SetBoardOpsRestart("setBoardOpsRestart"),//一体机OPS重启
    Device_HhtwbCommand_SetBoardProjectorStandby("setBoardProjectorStandby"),//一体机OPS重启

    Device_HhtwbCommand_GetBoardProjectorStandbyState("getBoardProjectorStandbyState"),//一体机获得投影机待机状态
    Device_HhtwbCommand_SetBoardProjectorTurn("setBoardProjectorTurn"),//一体机投影机电源
    Device_HhtwbCommand_GetBoardProjectorTurnState("getBoardProjectorTurnState"),//一体机获得投影机电源状态
    Device_HhtwbCommand_SetBoardProjectorEnergy("setBoardProjectorEnergy"),//一体机投影机省电
    Device_HhtwbCommand_GetBoardProjectorEnergyState("getBoardProjectorEnergyState"),//一体机获得投影机省电状态

    Device_ManualVirtualDiscover("manualVirtualDiscovered"),//添加虚拟设备
    Device_GetHostListByNames("getHostListByNames"),//根据主机名获取设备列表
    Device_Command_UpdateHhtcHostName("updateHhtcHostName"),//大屏修改设备名称
    Device_Command_GetAllChannelIntersectionByType("getAllChannelIntersectionByType"),//根据类型获取所有已添加的设备通道交集
    Device_HhrecCommand_SetPlanCommand("setPlanCommand"),//设置所有录像计划，


    Device_Command_UpdateHhrecHostName("updateHhrecHostName"),//录播修改设备名称
    Device_Command_DelTempPlan("delTempPlan"),//清空临时录像计划
    Device_Command_SetLubocfg("setLubocfg"),//扩展字段，先用于
    Device_Command_SetBrightness("setBrightness"),//设置设备亮度
    Device_Command_GetBrightness("getBrightness"),//查询设备亮度

    Device_Command_SetPTZSpeed("setPTZSpeed"),//设置云台速度
    Device_Command_GetPTZSpeed("getPTZSpeed"),//查询云台速度
    Device_GetHostListWithAreaByPage("getHostListWithAreaByPage"),//获取设备及地点信息并分页
    Device_GetAllDeviceType("getAllDeviceType"),//获取所有设备类型
    Device_GetConditionsHostListByPage("getConditionsHostListByPage"),//根据名称或ip获取设备列表
    Device_GetHostInfoByCondition("getHostInfoByCondition"),//根据名称和ip获取设备列表

    Device_GetExistingTypeList("getExistingTypeList"),//获取所有已发现的设备类型
    Device_GetHrecType("getHrecType"),//获取录播子类型
    Device_GetAreaHostListByNames("getAreaHostListByNames"),
    Device_DelHostByType("delHostByType"),
    Device_UpdateHostNameById("updateHostNameById"),//通过id修改名称

    Device_HhrecCommand_GetHRECDeviceInfo("getHRECDeviceInfo"),//通过ip查询镜头信息
    Device_HhrecCommand_PtzStart("ptzStart"),//控制云台移动
    Device_HhrecCommand_PtzStop("ptzStop"),//云台转动停止
    Device_HhrecCommand_GetHRECDeviceOnlineInfo("getHRECDeviceOnlineInfo"),//通过设备ip返回设备是否在线状态
    Device_HhrecCommand_GetDeviceConfigList("getDeviceConfigList");//获取设备镜头详情信息

    private String name;

    Method(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }


    // 普通方法
    public static Method getName(String name) {
        for (Method c : Method.values()) {
            if (c.name.equals(name)) {
                return c;
            }
        }
        return null;
    }

}
