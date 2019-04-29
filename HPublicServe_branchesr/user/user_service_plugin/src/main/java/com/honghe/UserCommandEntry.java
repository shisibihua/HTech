package com.honghe;

import com.honghe.communication.ioc.Command;
import com.honghe.communication.ioc.Context;
import com.honghe.communication.ioc.Response;

import java.util.Map;

/**
 * @author hh
 * @date 2016/12/1
 */
public final class UserCommandEntry implements Command {
    @Override
    public Response execute(Context context, Map<String, Object> map) {
        Response response = new Response();
        if (!map.containsKey("cmd_op")) {
            response.setCode(Response.PARAM_ERROR);
            return response;
        }
        String cmd_op = map.get("cmd_op").toString();
        //获取目录服务中对于地点，组织机构，事件的处理命令对象
        Command command = UserCmdFactory.getCommand(cmd_op);
        if (command != null) {
            response = command.execute(context, map);
        } else {
            response.setCode(Response.NO_METHOD);
        }

        return response;
    }
}
