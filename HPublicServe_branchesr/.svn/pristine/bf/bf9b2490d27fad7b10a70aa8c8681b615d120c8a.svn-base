package com.honghe.campus.config;

import com.honghe.communication.ioc.Command;
import com.honghe.communication.ioc.Context;
import com.honghe.communication.ioc.Response;
import com.honghe.campus.loadbalance.SipServerLoadBalance;

import java.util.Map;

/**
 * Created by zhanghongbin on 2016/9/28.
 */
public class ConfigCommand implements Command {


    public ConfigCommand() {
        //SipServerLoadBalance.initSipServer();
    }

    @Override
    public Response execute(Context context, Map<String, Object> map) {
        Response response = new Response();
        String cmd_op = map.get("cmd_op").toString();
        response.setType(cmd_op);
        if (cmd_op.equals("configSearch")) {

        }else {
            response.setCode(Response.NO_METHOD);
        }
        return response;
    }
}
