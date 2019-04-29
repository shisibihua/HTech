package com.honghe.deviceNew.util;

import com.alibaba.fastjson.JSONException;
import com.honghe.service.proxy.Result;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 添加会议终端通知mcu接口，暂放该类下，之后会做处理
 */
public class McuAddUtil {
    Logger logger = Logger.getLogger("device");

    /**
     * 获取mcu
     */
    public JSONObject getMCUList() {
        JSONObject resultJson = new JSONObject();
        try {
            Map<String, String> requestMap = new HashMap<String, String>();
            requestMap.put("deviceType", "hhtmcu");
            //调用设备服务，获取mcu
            Result result = new Result("getAllHostList",0,"") ;//该句代码为伪代码，不能实现查询mcu功能
            if (result.getCode() == 0 && result.getValue() != null && !"".equals(result.getValue())) {
                try {
                    resultJson = JSONObject.fromObject(result.getValue());
                }catch (JSONException e){
                    logger.error("获取mcu列表转换成json异常。",e);
                    return null;
                }
            } else {
                return resultJson;
            }
        } catch (Exception e) {
            logger.error("获取MCU列表异常", e);
        }
        return resultJson;
    }
    /**
     * 获取南京旭顶接口地址
     * @return
     */
    private String getNjxdServerPath(){
        //获取mcu列表，只有一个mcu
        JSONObject mcuJson = getMCUList();
        String mcuIp="";
        if (mcuJson != null) {
            List<Map<String, Object>> mcuList = JSONArray.fromObject(mcuJson.get("hostInfo"));
            if (mcuList != null && !mcuList.isEmpty()) {
                mcuIp = mcuList.get(0).get("host_ip").toString();
            }
        }else{
            return "";
        }
        //端口为8088，可加到配置文件中
        String serverPort="8088";
//        if(ConfigUtil.getConfig("njxd_port")!=null) {
//            serverPort = ConfigUtil.getConfig("njxd_port").toString().trim();
//        }
//        String njxdUrl="http://" + mcuIp + ":" + serverPort + NJXD_URL;
        return "njxdUrl";
    }

    /**
     * 调用南京旭顶中间件接口，添加终端到mcu
     * @param partyName   终端名称
     * @param partyId     终端id，生成规则:12340+加随机数，如123401,1234001,123401等
     * @param partyIp     终端ip
     * @return
     */
    public JSONObject addHostToMcu(String partyName, String partyId, String partyIp) {
        JSONObject re_value=new JSONObject();
        if(partyName!=null && !"".equals(partyName) && partyId!=null && !"".equals(partyId) && partyIp!=null && !"".equals(partyIp)){
            synchronized (this){
                //固定值,可提取到常量
                String netType="1";
                //固定值,可提取到常量
                String bandWidth="2048000";
                //固定值,可提取到常量
                String type="NJXD";
                //默认可为空
                String areaId="";
                JSONObject paramsJson = new JSONObject();
                paramsJson.put("e164", partyId);
                paramsJson.put("name", partyName);
                paramsJson.put("bandwidth", bandWidth);
                paramsJson.put("net_type", netType);
                paramsJson.put("ip", partyIp);
                paramsJson.put("area_id", "");
                String key = getMD5(partyName + netType + areaId + type);
                paramsJson.put("key", key);
                String path=getNjxdServerPath();
                if(!"".equals(path)) {
                    String url = path + "?act=AddTerminal";
                    String result = null;
                    try {
                        result = McuHttpServiceUtil.load(url, paramsJson.toString());
                    } catch (IOException e) {
                        result = "";
                    }
                    if(result!=null && !"".equals(result)) {
                        try {
                            JSONObject backJson = JSONObject.fromObject(result);
                            if (backJson != null && backJson.containsKey("status") &&
                                    "0".equals(backJson.get("status").toString())) {
                                re_value.put("code", "0");
                            }else{
                                re_value.put("code", "-1");
                            }
                        }catch (JSONException e){
                            logger.error("json转换异常",e);
                            re_value.put("code", "-2");
                        }
                    }else{
                        re_value.put("code", "-1");
                    }
                }
            }
        }
        return re_value;
    }
/*---------------------------------------------------------------------md5-------------------------------------------------------------------------*/
    /**
     * byte[]数组---->32位MD5加密字符串
     * @param b byte[]数组
     * @return 32位MD5加密字符串
     */
    public final static String getMD5(byte[] b)
    {
        MessageDigest md5=null;
        try
        {
            md5=MessageDigest.getInstance("MD5");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        byte[] md5Result=md5.digest(b);
        String value="";
        for(int i=0;i<16;i++)
        {
            value+=byte2uchar(md5Result[i]);
            //value+=NumberUtil.byte2uchar(md5Result[i]);
        }
        return value;
    }

    private final static String byte2uchar(byte b)
    {
        char[] hex={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        //char high='0';
        //char low='0';
        int high=(b>>>4)&0x0f;
        char highChar=hex[high];
        int low=b&0x0f;
        char lowChar=hex[low];
        return ""+highChar+lowChar;
    }
    /**
     * String字符串---->32位MD5加密字符串
     * @param value 字符串
     * @return 32位MD5加密字符串
     */
    public final static String getMD5(String value)
    {
        return getMD5(value.getBytes());
    }
}
