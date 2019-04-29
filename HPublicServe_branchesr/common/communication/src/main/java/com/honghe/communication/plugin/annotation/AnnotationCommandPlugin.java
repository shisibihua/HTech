package com.honghe.communication.plugin.annotation;

import com.honghe.communication.ioc.annotation.AnnotationCommand;
import com.honghe.communication.ioc.Command;

import java.util.*;


/**
 * Created by zhanghongbin on 2017/3/9.
 */
public class AnnotationCommandPlugin implements com.honghe.communication.plugin.CommandPlugin {
    Class<?> _class;

    public AnnotationCommandPlugin(Class<?> _class) {
        this._class = _class;
    }

    @Override
    public Map<String, Command> regist() {
        Map<String, Command> commandMap = new HashMap<>();
        String name = _class.getAnnotation(com.honghe.communication.plugin.annotation.CommandPlugin.class).name();
        commandMap.put(name, new AnnotationCommand(_class));
        return commandMap;
    }


}
