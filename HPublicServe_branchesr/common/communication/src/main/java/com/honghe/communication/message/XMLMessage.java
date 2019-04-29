package com.honghe.communication.message;

import com.honghe.communication.ioc.Response;
import org.pinae.nala.xb.Xml;
import java.util.Collections;
import java.util.Map;


/**
 * Created by zhanghongbin on 2016/8/4.
 */
 final class XMLMessage implements Message {


    @Override
    public Map<String, Object> receiver(Object message) {
        try {
            return (Map<String, Object>) Xml.toMap(message.toString(), "UTF-8");
        } catch (Exception e) {
            return Collections.emptyMap();
        }
    }


    @Override
    public String build(Response message) {
        try {
            return Xml.toXML(message, "UTF-8", true);
        } catch (Exception e) {
            return null;
        }
    }
}
