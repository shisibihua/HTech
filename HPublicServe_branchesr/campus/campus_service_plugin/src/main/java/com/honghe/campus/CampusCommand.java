package com.honghe.campus;

import com.honghe.campus.config.ConfigCommand;
import com.honghe.campus.user2sip.User2SipCommand;
import com.honghe.communication.ioc.Command;
import com.honghe.communication.ioc.Context;
import com.honghe.communication.ioc.Response;

import java.util.Map;

/**
 * Created by zhanghongbin on 2016/10/8.
 */
public class CampusCommand implements Command {


    private static User2SipCommand user2SipCommand = new User2SipCommand();
    private static ConfigCommand configCommand = new ConfigCommand();

    @Override
    public Response execute(Context context, Map<String, Object> map) {
        Response response = new Response();
        if (!map.containsKey("cmd_op")) {
            response.setCode(Response.PARAM_ERROR);
            return response;
        }
        String cmd_op = map.get("cmd_op").toString();
        response.setType(cmd_op);
        if (cmd_op.startsWith("config")) {
            return configCommand.execute(context, map);
        }else {
            return user2SipCommand.execute(context, map);
        }
    }
}
