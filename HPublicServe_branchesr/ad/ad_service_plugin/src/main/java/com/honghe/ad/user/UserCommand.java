package com.honghe.ad.user;/**
 * Created by lyx on 2016-11-30.
 */

import com.honghe.ad.BaseReflectCommand;
import com.honghe.ad.user.controller.UserController;
import com.honghe.communication.ioc.Context;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户逻辑操作的入口类
 *
 * @author lyx
 * @create 2016-11-30 9:40
 **/
public class UserCommand extends BaseReflectCommand {
    private Logger logger = Logger.getLogger(UserCommand.class);

    /**
     * 获取日志输出对象
     *
     * @return
     */
    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public Class getCommandClass() {
        return UserController.class;
    }

    public static void main(String[] args) {
        UserCommand userCommand = new UserCommand();
        Map<String, Object> parm = new HashMap<>();
        parm.put("cmd", "ad");
        parm.put("cmd_op", "area2DeviceDelete");

        userCommand.execute(new Context(Context.Type.HTTP, ""), parm);

    }
}
