package com.honghe.communication.plugin;

import com.honghe.client.DeviceClient;
import com.honghe.communication.ioc.Command;
import com.honghe.communication.ioc.CommandFactory;
import com.honghe.communication.ioc.ReflectCommand;
import com.honghe.communication.main.CommunicationService;
import com.honghe.communication.main.listener.HttpServletLoadListener;
import com.honghe.dao.JdbcTemplateUtil;
import com.honghe.device.command.impl.*;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by zhanghongbin on 2015/5/7.
 */
public final class DeviceCommandPlugin implements CommandPlugin {
    private static final Logger logger = Logger.getLogger(DeviceCommandPlugin.class);
    private static Properties jdbcProperties;
    static {
        try {
            jdbcProperties = JdbcTemplateUtil.getJdbcProperties("config/jdbc.properties");
            String jdbcUrl = jdbcProperties.get("jdbc.url").toString();
            String userName = jdbcProperties.getProperty("jdbc.username").toString();
            String pwd = jdbcProperties.get("jdbc.password").toString();
            String content = IOUtils.toString(DeviceCommandPlugin.class.getResourceAsStream("/devicecloud.sql"), "UTF-8");
            JdbcTemplateUtil.exeucteOneUserSQLFile(jdbcUrl,userName,pwd,"service",  content, "##");
            logger.debug("创建设备服务相关表成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public Map<String, Command> regist() {
            //向communication包内注册事件请求路径
            HttpServletLoadListener.load("com.honghe.device.event.EventOnAndOffLine",  "/eventOnOffLineService");//上下线事件
            HttpServletLoadListener.load("com.honghe.device.event.EventConsumer",  "/eventConsumerService");//104事件
            DeviceClient.getInstance();
            Map<String, Command> commandMap = new HashMap<>();
//            commandMap.put("eventConsumer", new EventConsumer());
            commandMap.put("device", new ReflectCommand(new HongheDeviceCommand()));
            commandMap.put("screenCommand", new ReflectCommand(new ScreenCommand()));
            commandMap.put("recordCommand", new ReflectCommand(new RecordCommand()));
            commandMap.put("projectorCommand", new ReflectCommand(new ProjectorCommand()));
            commandMap.put("whiteboardCommand", new ReflectCommand(new WhiteboardCommand()));
            commandMap.put("digitalClassCommand", new ReflectCommand(new DigitalClassCommand()));
            commandMap.put("positionAntennaCommand", new ReflectCommand(new PositionAntennaCommand()));
            commandMap.put("centerControllerCommand", new ReflectCommand(new CenterControllerCommand()));
            commandMap.put("interactiveTeachingCommand",new ReflectCommand(new InteractiveTeachingCommand()));
            commandMap.put("deviceCommand",new DeviceTransmitCommand());
//        commandMap.put("deviceControllCommand", new ReflectCommand(new DeviceControllCommand()));

            return commandMap;
        }

    public static void main(String[] args) throws Exception {

        try {
            CommunicationService.main(new String[]{"true"});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
