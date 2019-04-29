package com.honghe.communication.plugin;

import com.honghe.communication.ioc.Command;

import java.util.Map;

/**
 * Created by zhanghongbin on 2015/4/13.
 * 命令插件
 */
public interface CommandPlugin {

    public Map<String,Command> regist();

}
