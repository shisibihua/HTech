package com.honghe.tech.util;

import net.sf.json.xml.XMLSerializer;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.XML;


/**
 * xml转换成json字符串
 * @author caoqian
 */
public class XmlToJsonUtil {
    private static final Logger logger=Logger.getLogger(XmlToJsonUtil.class);

    /**
     * 通过XMLSerializer进行转换
     * @param xmlStr
     * @return
     *
     * <result><ncode>0</ncode> <root><domain><id>1</id><name>local</name></domain></root></result>
     * 转后
     * {"ncode":"0","root":{"domain":{"id":"1","name":"local"}}}
     */
    public static String getJsonByXMLSerializer(String xmlStr){
        String result="";
        try{
            if(!StringUtils.isEmpty(xmlStr)){
                XMLSerializer xmlSerializer = new XMLSerializer();
                result= xmlSerializer.read(xmlStr).toString();
            }
        }catch (Exception e){
            logger.error("xml转换成json异常,xml="+xmlStr);
            result="";
        }
        return result;
    }

    /**
     * 通过XML直接进行转换
     * @param xmlStr
     * @return
     *
     * <result><ncode>0</ncode> <root><domain><id>1</id><name>local</name></domain></root></result>
     * 转后
     * {"result":{"ncode":0,"root":{"domain":{"name":"local","id":1}}}}
     */
    public static String getJsonByXML(String xmlStr){
        String result="";
        try{
            if(!StringUtils.isEmpty(xmlStr)){
                result= XML.toJSONObject(xmlStr).toString();
            }
        }catch (Exception e){
            logger.error("xml转换成json异常,xml="+xmlStr);
            result="";
        }
        return result;
    }

    public static void main(String[] args) {
        String xml="<result><ncode>0</ncode> <root>" +
                "<domain><id>1</id><name>local</name></domain>" +
                "</root></result>";
        System.out.println(xml);
        System.out.println("____________________________________");

        System.out.println(XmlToJsonUtil.getJsonByXML(xml));
        System.out.println("____________________________________");
        //System.out.println(XmlToJsonUtil.getJsonByXMLSerializer(xml));
    }
}
