package com.honghe.web.device.service.device;

import com.honghe.service.client.Result;
import com.honghe.web.device.util.DeviceNameUtil;
import com.honghe.web.device.util.DeviceTypeNameUtil;
import com.honghe.web.device.util.HttpServiceUtil;
import com.honghe.web.device.util.UuidUtil;
import jodd.joy.page.PageData;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by sky on 2016/10/6.
 */
@Service
   public class DeviceService {
    private Logger logger = Logger.getLogger(DeviceService.class);
    private static final String MCU_TYPE="hhtmcu";
    private static final String DEVICE_IFREE_FACTORY="HiteTech-IFreeComm";
    private static final String DEVICE_NJXD_FACTORY="HiteTech-Njxd";
    /**
     * 获取各个设备类型的数量
     * @return Map
     */
    public Map getdCount(){
        Map params = new HashMap();
        Map map = new HashMap();
        int  httcCount=0;
        int  hrecCount = 0;
        int  securityCount = 0;
        int  positionCount = 0;
        int  digitalCount = 0;
        int  audioCount = 0;
        int  unknownCount = 0;
        int  htprCount = 0;
        int  hhtwbCount = 0;
        int  hhtccCount = 0;
        int  hhtmcuCount = 0;
        int  hhtcteCount = 0;
        try {
            //获取大屏设备数量
            params.put("deviceType", DeviceTypeNameUtil.DEVICE_TYPE_SCREEN);
            httcCount = (int) HttpServiceUtil.deviceService("hostCount",params).getValue();
            //获取录播数量
            params.put("deviceType", DeviceTypeNameUtil.DEVICE_TYPE_RECOURD);
            hrecCount = (int) HttpServiceUtil.deviceService("hostCount",params).getValue();
            //获取音频设备数量
            params.put("deviceType", DeviceTypeNameUtil.DEVICE_TYPE_AudioEquipment);
            audioCount = (int) HttpServiceUtil.deviceService("hostCount",params).getValue();
            //获取数字班牌数量
            params.put("deviceType", DeviceTypeNameUtil.DEVICE_TYPE_DigitalClass);
            digitalCount = (int) HttpServiceUtil.deviceService("hostCount",params).getValue();
            //获取定位天线设备
            params.put("deviceType", DeviceTypeNameUtil.DEVICE_TYPE_POSITIONINGANTENNA);
            positionCount = (int) HttpServiceUtil.deviceService("hostCount",params).getValue();
            //获取安防设备数量
            params.put("deviceType", DeviceTypeNameUtil.DEVICE_TYPE_SECURITYEQUIPMENT);
            securityCount = (int) HttpServiceUtil.deviceService("hostCount",params).getValue();
            //获取投影仪设备数量
            params.put("deviceType", DeviceTypeNameUtil.DEVICE_TYPE_PROJECTOR);
            htprCount = (int) HttpServiceUtil.deviceService("hostCount",params).getValue();
            //获取白板设备数量
            params.put("deviceType", DeviceTypeNameUtil.DEVICE_TYPE_WHITEBOARD);
            hhtwbCount = (int) HttpServiceUtil.deviceService("hostCount",params).getValue();
            //获取中控设备数量
            params.put("deviceType", DeviceTypeNameUtil.DEVICE_TYPE_CENTERCONTROL);
            hhtccCount = (int) HttpServiceUtil.deviceService("hostCount",params).getValue();
            //获取视频会议设备数量
            params.put("deviceType", DeviceTypeNameUtil.DEVICE_TYPE_VIDEOCONFERENCING);
            hhtmcuCount = (int) HttpServiceUtil.deviceService("hostCount",params).getValue();
            //获取会议终端设备数量
            params.put("deviceType", DeviceTypeNameUtil.DEVICE_TYPE_CONFERENCETERMINAL);
            hhtcteCount = (int) HttpServiceUtil.deviceService("hostCount",params).getValue();
            //获取未知设备数量
            params.put("deviceType", DeviceTypeNameUtil.DEVICE_TYPE_UNKNOWN);
            unknownCount = (int) HttpServiceUtil.deviceService("hostCount",params).getValue();
        } catch (Exception e) {
            logger.error("获取设备数量失败",e);
        }
        map.put("httcCount",httcCount);
        map.put("hrecCount",hrecCount);
        map.put("htprCount",htprCount);
        map.put("hhtwbCount",hhtwbCount);
        map.put("audioCount",audioCount);
        map.put("digitalCount",digitalCount);
        map.put("positionCount",positionCount);
        map.put("securityCount",securityCount);
        map.put("hhtccCount",hhtccCount);
        map.put("hhtmcuCount",hhtmcuCount);
        map.put("hhtcteCount",hhtcteCount);
        map.put("unknownCount",unknownCount);
        return map;
    }

    /**
     * 获取所有设备
     * @param page 当前查询页
     * @param pageSize 每页设备数
     * @return List
     */
    public PageData getDeviceList(String page, String pageSize){
        Map map = new HashMap();
        Map params = new HashMap();
        params.put("hostIdStr","");
        params.put("deviceType","");
        params.put("currentPage", page);
        params.put("pageSize", pageSize);
        try {
            Result result = HttpServiceUtil.deviceService("getHostListWithAreaByPage", params);
            if(result.getValue()!=null) {
                map = (Map) result.getValue();
            }

        } catch (Exception e) {
            logger.error("获取设备失败",e);
        }
        List hostlist = (List) map.get("hostInfo");//设备信息
        int totalCount = (int) map.get("itemsCount");//设备总数
        PageData pageData = new PageData( Integer.parseInt(page), totalCount,Integer.parseInt(pageSize), hostlist);
        return pageData;
    }
    /**
     * 自动发现设备
     * @param deviceType
     * @return
     */
    public Map discover(String deviceType){
        Map map = new HashMap();
        Map params = new HashMap();
        String discoverCount = "";
        params.put("deviceType",deviceType);
        try {
            //自动发现设备数量
            Result result = HttpServiceUtil.deviceService("discover",params);
            if (result.getCode() == 0 && result.getValue() != null) {
                Map values = new HashMap();
                values = result.getValue().equals("") ? new HashMap() : ((Map) result.getValue());
                discoverCount = values.isEmpty() ? "" : values.get("discoverCount").toString();
            }
        }
        catch (Exception e) {
            logger.error("设备发现失败", e);
        }
        map.put("discoverCount",discoverCount);
        return map;
    }

    /**
     * 添加设备入库
     * @param deviceType
     * @return
     */
    public Map addDevice(String deviceType){
        Map params = new HashMap();
        Map addMap = new HashMap();
        Map map = new HashMap();
        String userName = "";
        String password = "";
        params.put("deviceType",deviceType);
        params.put("userName", userName);
        params.put("password", password);
        //将发现的设备添加入库
        Result addresult= null;
        try {
            addresult = HttpServiceUtil.deviceService("addhost", params);
        } catch (Exception e) {
            logger.error("添加设备失败",e);
        }
        if (addresult.getValue()!=null){
            addMap= (Map) addresult.getValue();
            map.put("addResult",addMap.get("isDevicediscover"));
            map.put("isMuti", addMap.get("isMuti"));
        }else{
            map.put("addResult","");
            map.put("isMuti", "");
        }

        return map;
    }
    /**
     * 手动添加设备
     * @param typeInt 设备类型编号
     * @param className 设备名称
     * @param hostPort 端口号
     * @param hostChannel 通道号
     * @param userName 用户名
     * @param password 密码
     * @param ip ip地址
     * @param isTour 是否支持巡课
     * @param metString 通道地址
     * @param cameraString 通道名称
     * @return Map
     */
    public Map manualAdd(String typeInt,String className,String hostPort,String hostChannel,String userName,
                         String password,String ip,String isTour,String metString,String cameraString, String mcuClientCode){
        boolean flag = false;
        Map map = new HashMap();
        String deviceType = "";
        Map params = new HashMap();
        String systems="";
        String factory = DeviceNameUtil.getFactoryByTypeInt(typeInt);
        if (typeInt.contains(String.valueOf(DeviceNameUtil.DEVICE_VIDEO_CONFERENCING))){
            deviceType = DeviceTypeNameUtil.DEVICE_TYPE_VIDEOCONFERENCING;
            String [] type = typeInt.split(",");
            typeInt = type[1];
            factory = DeviceNameUtil.getFactoryByTypeInt(typeInt);
        } else if (typeInt.contains(String.valueOf(DeviceNameUtil.DEVICE_RECOURD))){//对录播主机子类型的处理
            deviceType = DeviceTypeNameUtil.DEVICE_TYPE_RECOURD;
            String [] type = typeInt.split(",");
            typeInt = type[1];
            factory = DeviceNameUtil.getFactoryByTypeInt(typeInt);

            //设备类型是教室监控的情况，ip会进行特殊处理
            if (typeInt.equals(String.valueOf(DeviceNameUtil.DEVICE_CLASSROOMMONITOR))){
                ip = UuidUtil.getUUID();
                systems = "record";
            }
        }else {
            deviceType = DeviceNameUtil.getDeviceType(typeInt);
        }
        if (typeInt.equals(String.valueOf(DeviceNameUtil.DEVICE_VIDEO_IFREECOMM))||
                typeInt.equals(String.valueOf(DeviceNameUtil.DEVICE_VIDEO_CISCO)) ||
                typeInt.equals(String.valueOf(DeviceNameUtil.DEVICE_VIDEO_NJXD)) ){
            typeInt = String.valueOf(DeviceNameUtil.DEVICE_VIDEO_CONFERENCING);
        }
        //logger.debug(this.getClass().getSimpleName()+">>>>>>>>>>>typeInt========"+typeInt);
        boolean haveMcu=false;
        if(DEVICE_IFREE_FACTORY.equals(factory) || DEVICE_NJXD_FACTORY.equals(factory)){  //mcu
            // logger.debug("添加的为mcu设备");
            try {
                Map map_mcu = new HashMap();
                map_mcu.put("factory",factory);
                Result result = HttpServiceUtil.deviceService("getMcuInfo",map_mcu);
                if(result.getCode()==0 && result.getValue()!=null){
                    List<Map<String,String>> mcuList=(List<Map<String,String>>)result.getValue();
                    if(mcuList!=null && !mcuList.isEmpty()){
                        haveMcu=true;
                        map.put("hostId","-2");
                        //logger.debug(">>>>>>>>>>>>>>>>>mcuList不为空，返回值resultMap="+map);
                        return map;
                    }
                }
            } catch (Exception e) {
                logger.error("根据factory查询mcu设备列表异常。",e);
            }
        }else{
            if(mcuClientCode!=null && !"".equals(mcuClientCode)){
                try {
                    Map map_mcu = new HashMap();
                    map_mcu.put("mcuCode",mcuClientCode);
                    Result result = HttpServiceUtil.deviceService("getDeviceHostByCode",map_mcu);
                    if(result.getCode()==0 && result.getValue()!=null && !"".equals(result.getValue())){
                        haveMcu=true;
                        map.put("hostId","-8");
                        return map;
                    }
                } catch (Exception e) {
                    logger.error("根据设备编码查询设备信息异常。",e);
                }
            }
        }
        if(!haveMcu) {
            params.put("typeInt", typeInt);
            params.put("deviceType", deviceType);
            params.put("factory", factory);
            params.put("system", systems);
            params.put("className", className);
            params.put("hostPort", hostPort);
            params.put("hostChannel", hostChannel);
            params.put("userName", userName);
            params.put("password", password);
            params.put("ip", ip);
            params.put("isTour", isTour);
            params.put("metString", metString);
            params.put("cameraString", cameraString);
            params.put("mcuClientCode", mcuClientCode);
            try {
                //logger.debug(this.getClass().getSimpleName()+">>>>>>>>>>>>haveMcu===="+haveMcu);
                Result result = HttpServiceUtil.deviceService("manualDiscover", params);
                map = (Map) result.getValue();
            } catch (Exception e) {
                logger.error("添加设备失败", e);
            }
        }
        return map;
    }

    /**
     * 删除设备
     * @param hostIdStr 选中设备的id
     * @param hostIpStr 选中设备的ip
     * @param hostTypeStr 选中的设备类型
     */
    public Map deldevice(String hostIdStr,String hostIpStr,String hostTypeStr){
        boolean flag = false;
        Map map = new HashMap();
        Map<String,Object> hashMap = new HashMap<String,Object>();
        try {
            //删除设备
            Map params = new HashMap();
            params.put("hostIdStr",hostIdStr);
            params.put("hostIpStr",hostIpStr);
            params.put("hostTypeStr",hostTypeStr);
            Result result =  HttpServiceUtil.deviceService("delHostListByType", params);
            if (result.getValue()!=null){
                hashMap = (Map) result.getValue();
                logger.debug("返回值为："+result.getValue());
            }
            //返回相应的结果
            map.put("success", hashMap.get("isSuccess"));
            map.put("msg", hashMap.get("count")+"个设备删除成功");

        } catch (Exception e) {
            logger.error("删除设备失败",e);
        }
        return map;
    }
    /**
     * 按条件搜索设备
     * @param conditions  查找设备所需的条件，ip或是设备名称
     * @param currentPage 当前页
     * @param pageSize 每页设备数
     * @param deviceType 设备类型
     * @return
     */
    public PageData getSearchHostList(String conditions,String currentPage,String pageSize,String deviceType){
        Map params = new HashMap();
        String areaId="";
        int itemsTotal = 0;
        List list = new ArrayList();
        params.put("conditions",conditions);
        params.put("currentPage",currentPage);
        params.put("pageSize",pageSize);
        params.put("deviceType",deviceType);
        params.put("areaId",areaId);
        try {
            Result result = HttpServiceUtil.deviceService("getConditionsHostListByPage",params);
            if(result.getValue()!=null){
                Map map = (Map) result.getValue();
                list = (List) map.get("hostInfo");
                itemsTotal = (int)map.get("itemsCount");
            }
        } catch (Exception e) {
            logger.error("设备查找失败",e);
        }
        PageData pageData = new PageData(Integer.parseInt(currentPage),itemsTotal,Integer.parseInt(pageSize),list);
        return pageData;
    }

    /**
     * 获取现有设备类型
     * @return list
     */
    public List getAllList(){
        Map params = new HashMap();
        List list = new ArrayList();
        try {
            Result result = HttpServiceUtil.deviceService("getExistingTypeList",params);
            if(result.getValue()!=null){
                list = (List) result.getValue();
            }
        } catch (Exception e) {
            logger.error("获取所有类型失败",e);
        }
        return list;
    }

    /**
     * 获取录播子类型
     * @return
     */
    public List getHrecType(){
        Map params = new HashMap();
        List list = new ArrayList();
        try {
            Result result = HttpServiceUtil.deviceService("getHrecType",params);
            if (result.getValue()!=null){
                list = (List) result.getValue();
            }
        } catch (Exception e) {
            logger.error("获取录播子类型失败",e);
        }
        return list;
    }

    /**
     * 根据id获取设备信息
     *
     * @param id
     * @return
     */
    public Map<String, String> getHostInfoById(String id) throws Exception {
        Map<String, String> re_value = new HashMap<>();
        Map params = new HashMap();
        params.put("hostId", id);
        Result result = HttpServiceUtil.deviceService("getHostInfoById", params);
        if (result.getValue() != null) {
            re_value = (Map<String, String>) result.getValue();
        }
        return re_value;
    }

    /**
     * 修改设备名称
     * @param id 设备id
     * @param hostName 设备名称
     * @return boolean
     * @throws Exception
     */
    public boolean updateHostName(String id, String hostName) throws Exception {
        boolean re_value = false;
        Map params = new HashMap();
        params.put("hostId", id);
        params.put("hostName", hostName);
        Result result = HttpServiceUtil.deviceService("updateHostNameById", params);
        if (result.getValue() != null) {
            re_value = (boolean) result.getValue();
        }
        return re_value;
    }
}
