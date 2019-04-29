package com.honghe.device.command;

import org.apache.log4j.Logger;

/**
 * Created by lyx on 2016-01-13.
 * 创建命令工厂
 */
public class CommandFactroy extends DeviceCommandFactory {
    private final static Logger logger = Logger.getLogger(DeviceCommandFactory.class);
    DeviceCommand deviceCommand = null;

    @Override
    public <T extends DeviceCommand> T createDeviceCommand(Class<T> c) {

        try {
            deviceCommand = (DeviceCommand) Class.forName(c.getName()).newInstance();
        } catch (Exception e) {
            logger.error("生成命令对象错误:", e);
        }
        return (T) deviceCommand;
    }
}
