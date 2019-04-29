package com.honghe.device.util;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by lyx on 2015-12-21.
 */
public final class MapUtil {

    /**
     * Map遍历取值
     *
     * @param map
     */
    public static String getMapValue(Map map) {
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        StringBuffer stringBuffer = new StringBuffer();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            stringBuffer.append("key= " + entry.getKey() + " and value= " + entry.getValue().toString());
        }
        return stringBuffer.toString();
    }
}
