package com.honghe.device.command.impl;

import com.hht.DeviceManager.DeviceManager;
import com.hht.DeviceManager.operationRequest.common.CommonOperationRequest;
import com.honghe.device.command.DeviceCommand;
import org.apache.log4j.Logger;


/**
 * Created by lyx on 2016-05-20.
 */
public class CenterControllerCommand implements DeviceCommand {
    private final static Logger logger = Logger.getLogger(CenterControllerCommand.class);

    /**
     * 场景信息处理
     * @param ip 设备ip
     * @param commandStr 场景信息命令及内容json串（命令包括获取场景列表、获取详细场景信息、修改场景信息和执行场景命令）
     * @return
     */
    public Object modelInfoCommand(String ip,String commandStr){
        Object re_value = null;
        try {
            re_value = DeviceManager.invoke(ip,new CommonOperationRequest(commandStr));
            logger.debug("场景信息接收成功："+re_value.toString());
        } catch (Exception e) {
            logger.error("场景信息接收异常",e);
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
}
