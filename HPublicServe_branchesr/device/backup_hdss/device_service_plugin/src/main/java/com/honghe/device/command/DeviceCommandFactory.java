package com.honghe.device.command;

/**
 * Created by lyx on 2016-01-12.
 */
public abstract class DeviceCommandFactory {
    public abstract <T extends DeviceCommand> T createDeviceCommand(Class<T> c);
}
