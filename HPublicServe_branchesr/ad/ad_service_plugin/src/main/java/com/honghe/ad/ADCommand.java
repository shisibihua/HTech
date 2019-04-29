package com.honghe.ad;

import com.honghe.communication.ioc.Command;
import com.honghe.communication.ioc.Context;
import com.honghe.communication.ioc.Response;

import java.util.Map;

/**
 * Created by zhanghongbin on 2016/7/27.
 */
public final class ADCommand implements Command {
    public ADCommand() {
    }

    @Override
    public Response execute(Context context, Map<String, Object> map) {
        Response response = new Response();
        if (!map.containsKey("cmd_op")) {
            response.setCode(Response.PARAM_ERROR);
            return response;
        }
        String cmd_op = map.get("cmd_op").toString();
        //获取目录服务中对于地点，组织机构，事件的处理命令对象
        Command command = ADCmdFactory.getCommand(cmd_op);
        if (command != null) {
            response = command.execute(context, map);
        } else {
            response.setCode(Response.NO_METHOD);
        }

        return response;
    }

//    public static void main(String[] args) {
//        JdbcTemplateUtil.setConnectionInfo("jdbc:mysql://192.168.16.170:3306/service?user=root&password=admin&useUnicode=true&characterEncoding=utf8");
//
//        Map<String,Object> params = new HashMap<>();
//        ADCommand adCommand = new ADCommand();
//        params.put("loginName", "");
//        params.put("page", "1");
//        params.put("pageSize", "16");
//        params.put("cmd_op", "userNotInCampusSearch");
//        System.out.println( adCommand.execute(null,params).getResult());
//    }

}
