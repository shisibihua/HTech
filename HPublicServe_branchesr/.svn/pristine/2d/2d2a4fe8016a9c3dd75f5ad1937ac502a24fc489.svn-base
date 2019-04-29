package com.honghe.communication.plugin;

import com.honghe.communication.ioc.CommandFactory;
import com.honghe.communication.plugin.impl.notification.NotificationCommand;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhanghongbin on 2015/4/13.
 */
public final class CommandPluginManager {

    private CommandPluginManager() {

    }

    public final static void load() {
        List<CommandPlugin> commandPlugins = new ArrayList<>();
        commandPlugins.addAll(new JarLoader().find(CommandPlugin.class));
        for (CommandPlugin commandPlugin : commandPlugins) {
            CommandFactory.add(commandPlugin.regist());
        }
        loadDefaultPlugin();
    }

    private final static void loadDefaultPlugin() {
        CommandFactory.add("notify", new NotificationCommand());
    }


}

