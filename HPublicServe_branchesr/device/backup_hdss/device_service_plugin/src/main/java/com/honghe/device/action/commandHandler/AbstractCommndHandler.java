package com.honghe.device.action.commandHandler;

import com.hht.global.GlobalDefinitions;
import com.honghe.device.util.CommonNameUtil;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lyx
 * Date: 2017-05-09 0009
 * Time: 13:17
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractCommndHandler {

    /**
     * 持有后继的责任对象
     */
    protected AbstractCommndHandler commandHandler;

    /***
     * 获取具体的设备类型的方法
     * map 包含的某方法的参数请求，需要根据Ip，IP，hostId等获取设备的类型
     *
     * @return
     */
    public abstract String getComamnd(Map<String, Object> map);


    public String getCommandByDeviceType(String deviceType) {
        String command = null;
        CommonNameUtil deviceTypeEnum = CommonNameUtil.getName(deviceType);
        if(GlobalDefinitions.DeviceType_NVR.toLowerCase().equals(deviceType)|| GlobalDefinitions.DeviceType_IPC.toLowerCase().equals(deviceType)){
            deviceTypeEnum = CommonNameUtil.HHREC;
        }
        switch (deviceTypeEnum) {
            case HHREC: {
                command = "recordCommand";
                break;
            }
            case HHTC: {
                command = "screenCommand";
                break;
            }
            case HTPR: {
                command ="projectorCommand";
                break;
            }
            case HHTWB: {
                command = "whiteboardCommand";
                break;
            }
            case HHTDC: {
                command ="digitalClassCommand";
                break;
            }
            case HHTPA: {
                command ="positionAntennaCommand";
                break;
            }
            case HHTCC: {
                command = "centerControllerCommand";
                break;
            }
            case HHTMCU: {
                command = "interactiveTeachingCommand";
                break;
            }
            default: {
                break;
            }
        }

        return command;
    }

    /**
     * 获取责任对象
     *
     * @return
     */
    public AbstractCommndHandler getCommandHandler() {
        return commandHandler;
    }

    /**
     * 设置后续的责任对象
     *
     * @param commandHandler
     */
    public void setCommandHandler(AbstractCommndHandler commandHandler) {
        this.commandHandler = commandHandler;
    }
}

