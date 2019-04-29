package com.honghe.device.command.impl;

import com.honghe.communication.ioc.Command;
import com.honghe.communication.ioc.CommandFactory;
import com.honghe.communication.ioc.Context;
import com.honghe.communication.ioc.Response;
import com.honghe.device.action.commandHandler.AbstractCommndHandler;
import com.honghe.device.action.commandHandler.CommonInterfaceHandler;
import com.honghe.device.action.commandHandler.HostIdHandler;
import com.honghe.device.action.commandHandler.IpHandler;
import org.apache.log4j.Logger;
import java.util.Map;

/**
 * 设备操作类，用户处理重复的类别
 *
 * @author lyx
 * @create 2017-05-09 10:46
 **/
public class DeviceTransmitCommand implements Command{
    private final static Logger logger = Logger.getLogger(DeviceTransmitCommand.class);

    @Override
    public Response execute(Context var1, Map<String, Object> map)  {
        Response response = new Response();
        String command = getCommandByDeviceType(map);
        if (command==null||"".equals(command)){
            response.setCode(Response.NO_METHOD);
            response.setOther("msg","没有对应的command中有该方法");
            response.setResult(new Object());
        }else {
            response = CommandFactory.getInstance(command).execute(var1,map);
        }
        return response;

    }

    private String getCommandByDeviceType(Map<String, Object> map) {
        AbstractCommndHandler handler = new IpHandler();
        AbstractCommndHandler handler1 = new HostIdHandler();
        AbstractCommndHandler handler2 = new CommonInterfaceHandler();
        handler.setCommandHandler(handler1);
        handler1.setCommandHandler(handler2);
        return handler.getComamnd(map);
    }
}
