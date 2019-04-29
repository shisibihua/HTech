package com.honghe.campus.user2sip;

import com.honghe.cache.CacheFactory;
import com.honghe.campus.user2sip.cache.User2SipCacheDao;
import com.honghe.communication.ioc.Command;
import com.honghe.communication.ioc.Context;
import com.honghe.communication.ioc.Response;
import com.honghe.service.proxy.Result;
import com.honghe.service.proxy.ServiceProxyFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhanghongbin on 2016/9/21.
 */
public final class User2SipCommand implements Command {


    @Override
    public Response execute(Context context, Map<String, Object> map) {
        Response response = new Response();
        String cmd_op = map.get("cmd_op").toString();
        response.setType(cmd_op);
        if (cmd_op.equals("userLogin") && context.getType() == Context.Type.SIP) {
            this.userLogin(context, map, response);
        } else if (cmd_op.equals("sipSearch")) {
            if (map.containsKey("userId")) {
                response.setResult(User2SipCacheDao.INSTANCE.findByUserId(map.get("userId").toString().trim()));
            } else if (map.containsKey("sipId")) {
                response.setResult(User2SipCacheDao.INSTANCE.findBySipId(map.get("sipId").toString().trim()));
            } else if (map.containsKey("orgId")) {
                response.setResult(User2SipCacheDao.INSTANCE.findByOrgId(map.get("orgId").toString().trim()));
            } else {
                response.setResult(User2SipCacheDao.INSTANCE.find());
            }
        } else if (cmd_op.equals("userLogout")) {
            if (!map.containsKey("userId")) {
                response.setCode(Response.PARAM_ERROR);
                return response;
            }
            User2SipCacheDao.INSTANCE.delete(map.get("userId").toString());
            response.setResult(true);
        } else if (cmd_op.equals("sipLogin")) {

        } else if (cmd_op.equals("sipLogout")) {

        } else if (cmd_op.equals("onlineUserSearch")) {
            Object obj = CacheFactory.newIntance().get("config.user2sip");
            if (obj == null) {
                response.setResult(new HashMap<>());
            } else {
                response.setResult(obj);
            }
        } else {
            response.setCode(Response.NO_METHOD);
        }
        return response;
    }

    //modify by zjy
    private void userLogin(Context context, Map<String, Object> map, Response response) {
        Object[] params = (Object[]) context.getContextObj();
        final String remoteSipClientId = params[1].toString();
        if (remoteSipClientId.equals("")) {
            response.setCode(Response.PARAM_ERROR);
        } else {
//            HttpServiceClient httpServiceClient = new HttpServiceClient();
            final Map<String, String> data = new HashMap<>();

            for (Map.Entry<String, Object> entry : map.entrySet()) {
                data.put(entry.getKey(), entry.getValue().toString());
            }

            data.put("user","userCheck");
            Result result = ServiceProxyFactory.newInstance(ServiceProxyFactory.Type.HTTP).send(data);
//            final Result result = httpServiceClient.execute("user", "userCheck", data);

            response.setCode(result.getCode());
            response.setResult(result.getValue());
            if (result.getCode() == 0) {
                final Map<String, String> userInfo = (Map) result.getValue();
                if (!userInfo.isEmpty()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            User2SipCacheDao.INSTANCE.add(userInfo.get("userId"), remoteSipClientId);
                        }
                    }).start();
                }
            }
        }
    }

}
