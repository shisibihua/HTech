package com.honghe.communication.plugin;

import com.honghe.communication.ioc.Command;
import com.honghe.notification.NotificationCommand;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhanghongbin on 2017/8/28.
 */
public class NotificationCommandPlugin implements CommandPlugin {


    @Override
    public Map<String, Command> regist() {
        Map<String, Command> commandMap = new HashMap<>();
        commandMap.put("notify", new NotificationCommand());
        return commandMap;
    }
}
