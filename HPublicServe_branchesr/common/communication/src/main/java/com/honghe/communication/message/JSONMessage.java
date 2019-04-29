package com.honghe.communication.message;

import com.google.gson.Gson;
import com.honghe.communication.ioc.Response;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import java.util.Collections;
import java.util.Map;

/**
 * Created by zhanghongbin on 2015/3/28.
 * JSON 消息
 */
  class JSONMessage implements Message {


    @Override
    public Map<String, Object> receiver(Object message) {
        try {
            return JSONObject.fromObject(message);
        } catch (Exception e) {
            return Collections.emptyMap();
        }
    }

    @Override
    public final String build(Response message) {
        try {
//            String json = JSONSerializer.toJSON(message).toString();
//            String json = new Gson().toJson(message);
//            System.out.println(json);
            return new Gson().toJson(message);
        } catch (Exception e) {
            return null;
        }
    }
}
