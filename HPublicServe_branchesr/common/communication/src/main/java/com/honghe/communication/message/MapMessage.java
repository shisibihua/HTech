package com.honghe.communication.message;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhanghongbin on 2015/3/28.
 */
final class MapMessage extends JSONMessage {

    @Override
    public Map<String, Object> receiver(Object message) {
        Map<String, Object> result = new HashMap<String, Object> ();
        Map<String, String[]> reqParam = (Map<String, String[]>) message;
        Set<Map.Entry<String, String[]>> entrySet = reqParam.entrySet();
        for (Map.Entry<String, String[]> entry : entrySet) {
            String[] values = entry.getValue();
            String value = "";
            if(values != null && values.length != 0){
                value = values[0];
            }
            result.put(entry.getKey(), value);
        }

        return result;
    }

}
