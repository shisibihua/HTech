package com.honghe.ad.area;

import com.honghe.ad.BaseReflectCommand;
import com.honghe.ad.area.controller.AreaController;
import com.honghe.communication.ioc.Context;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhanghongbin on 2016/8/5.
 * @author zhanghongbin
 */
public final class AreaCommand extends BaseReflectCommand {
    private Logger logger = Logger.getLogger(AreaCommand.class);

    public static void main(String[] args) {
        AreaCommand newAreaCommand = new AreaCommand();

        Map<String, Object> parm = new HashMap<>();
        parm.put("cmd", "ad");
        parm.put("cmd_op", "area2DeviceDelete");
        parm.put("deviceId", "900");
        newAreaCommand.execute(new Context(Context.Type.HTTP, ""), parm);
    }

    /**
     * 获取日志输出对象
     * @return
     */
    @Override
    public Logger getLogger() {
        return logger;
    }

    /**
     * 获取命令类
     * @return
     */
    @Override
    public Class getCommandClass() {
        return AreaController.class;
    }


}
