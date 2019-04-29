package com.honghe.device.util;

import com.honghe.device.command.CommandFactroy;
import com.honghe.device.command.DeviceCommand;
import com.honghe.device.command.impl.ProjectorCommand;
import com.honghe.device.command.impl.RecordCommand;
import com.honghe.device.command.impl.ScreenCommand;
import com.honghe.device.command.impl.WhiteboardCommand;

/**
 * Created by lyx on 2016-01-12.
 * 获取设备操作命令实体对象工具类
 */
public final class CommandUtil {
    static ScreenCommand screenCommand = new CommandFactroy().createDeviceCommand(ScreenCommand.class);
    static RecordCommand recordCommand = new CommandFactroy().createDeviceCommand(RecordCommand.class);
    static ProjectorCommand projectorCommand = new CommandFactroy().createDeviceCommand(ProjectorCommand.class);
    static WhiteboardCommand whiteboardCommand = new CommandFactroy().createDeviceCommand(WhiteboardCommand.class);

    public static DeviceCommand getComamnd(String _deviceType) {
        DeviceCommand deviceCommand = null;


        CommonNameUtil commonNameUtil = CommonNameUtil.getName(_deviceType);

        switch (commonNameUtil) {
            case HHTC:
                deviceCommand = screenCommand;
                break;
            case HHREC:
                deviceCommand = recordCommand;
                break;
            case HTPR:
                deviceCommand = projectorCommand;
                break;
            case HHTWB:
                deviceCommand = whiteboardCommand;
            default:
                break;
        }
        return deviceCommand;
    }
}
