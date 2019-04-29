package com.honghe.service.client;
import net.sf.json.JSONObject;

/**
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: 北京鸿合盛视数字媒体技术有限公司</p>
 *
 * @author zhanghongbin
 * @date 2015/11/6
 */
public abstract class ServiceClientCallback {


    public final void execute(String message) {
        try{
            JSONObject jsonObject = JSONObject.fromObject(message);
            response(new Result("", Integer.parseInt(jsonObject.getString("code")), jsonObject.get("result")));
        }catch (Exception e){

        }
    }

    protected abstract void response(Result result);
}
