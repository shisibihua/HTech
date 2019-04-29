package com.honghe.communication.ioc;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhanghongbin on 2015/3/13.
 * 命令工厂
 */
public final class CommandFactory {

    private CommandFactory() {

    }

    private static Map<String, Command> deviceCommandMap = new HashMap();


    /**
     * 增加命令
     *
     * @param commandMap
     */
    public static void add(Map<String, Command> commandMap) {
        deviceCommandMap.putAll(commandMap);
    }

    public static Set<String> keys() {
        return deviceCommandMap.keySet();
    }

    public static void add(String key, Command command) {
        deviceCommandMap.put(key, command);
    }

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static final Command getInstance(String key) {
        if (deviceCommandMap.containsKey(key)) {
            return deviceCommandMap.get(key);
        }
        return null;
    }


}
