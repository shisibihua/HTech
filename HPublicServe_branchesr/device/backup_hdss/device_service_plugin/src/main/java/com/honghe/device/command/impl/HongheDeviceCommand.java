package com.honghe.device.command.impl;

import com.hht.DeviceManager.DeviceManager;
import com.hht.DeviceManager.operationRequest.hhtc.HHTCOperationRequestSetHostName;
import com.hht.DeviceManager.operationRequest.record.OperationRequestGetAllMediaToken;
import com.hht.DeviceManager.operationRequest.record.OperationRequestGetStreamingURL;
import com.hht.device.Device;
import com.hht.device.HHTCDevice;
import com.hht.global.GlobalConfig;
import com.hht.global.GlobalDefinitions;
import com.hht.protocols.common.deviceGrid.AddDeviceRequest;
import com.hht.protocols.common.deviceGrid.AddVirtualDeviceRequest;
import com.hht.protocols.common.deviceGrid.BaseChannelItem;
import com.honghe.authority.AuthorityCheck;
import com.honghe.common.springframe.global.Version;
import com.honghe.dao.PageData;
import com.honghe.device.Host;
import com.honghe.device.SubScribePool;
import com.honghe.device.dao.DeviceConfigListDao;
import com.honghe.device.dao.DeviceDao;
import com.honghe.device.dao.HostDeviceDao;
import com.honghe.device.dao.SpecDao;
import com.honghe.device.handler.DeviceEventHandlerImpl;
import com.honghe.device.util.*;
import org.apache.log4j.Logger;


import java.io.IOException;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
* Created by zhaolijiao on 2017/5/10.
*/
public class HongheDeviceCommand {
    private final static Logger logger = Logger.getLogger(HongheDeviceCommand.class);

    private boolean isDevicediscover = false;
    private static String HOSTINFO = "hostInfo";
    private static String ERROR_HOSTLIST = "获取设备列表并分页异常!";

    private static final ReentrantLock reentrantLock = new ReentrantLock();


    public HongheDeviceCommand() {
        NetWorkFileTimerScannerUtil.start(); //---------待修改
        GlobalConfig.setRequestTimeOutMllisecond(30000); //设置请求的超时时间
//            GlobalConfig.setNetWorkInterface("192.168.20.21");
//            logger.error("DeviceGrid IP:" + "192.168.17.20");
//            ControlDeviceNumberHelper.setDefalut_Device_Number(5);
        DeviceManager.SetEventHandler(new DeviceEventHandlerImpl());//设置事件回调
        //初始化注册已有设备
        List<Map<String, String>> hostList = new ArrayList();
        try {
            hostList = DeviceDao.INSTANCE.getHostList(); //数据库获取设备列表
        } catch (Exception e) {
            logger.error("获取主机列表异常", e);
            return;
        }
        for (Map<String, String> host : hostList) {
            Hashtable<String, Object> params = new Hashtable<>();
            params.put("host_name", host.get("host_name"));
            params.put("host_desc", host.get("host_desc"));
            params.put("host_id", host.get("host_id"));
            String className = host.get("host_serialno");

            //通过反射的类名来截取设备类型
//                int str_start = className.lastIndexOf(".");
//                int str_end = className.indexOf("Device");
            String host_type = host.get("dtype_name").toLowerCase();

            //判断此设备是否需要进行注册，若类型不属于平台定义类型，则跳出
            if (TypeTransformationUtil.unRegister(host_type)) {
                continue;
            }

            try {
                Class _class = Class.forName(className);
                Object obj = _class.getConstructor(String.class, String.class, String.class, String.class).
                        newInstance(host.get("host_ipaddr"), host.get("host_username"), host.get("host_password"), TypeTransformationUtil.TypeToDeviceType(host_type, className));
                _class.getMethod("SetDeviceExtensionInfo", Hashtable.class).invoke(obj, params);
//                    DeviceManager.registeDevice((Device) obj); //注册
            } catch (Exception e) {
                logger.error("注册设备异常", e);
                continue;
            }
        }
        DeviceManager.discover();//设备发现
        SubScribePool.getProperties();//设备服务重启时，加载配置文件中平台地址
    }


    /**
     * 心跳接口
     */
    public Map<String, String> keep_alive() {
        Map<String, String> re_value = new HashMap<>();
        re_value.put("token", "DEVICE");
        logger.debug("连接到心跳");
        return re_value;
    }

    public Map<String, String> getSubcribePoolUrl(){
        Map<String, String> urlPool = SubScribePool.subScribePool;
        return urlPool;
    }

    // 设备发现
    public  Map<String, String> discover(String deviceType){
        Map<String, String> re_value = new HashMap<>();
        Map<String, Device> deviceMap = discovered(deviceType);
        re_value.put("discoverCount", deviceMap.size() + "");
        logger.debug("设备发现数量：" + deviceMap.size());
        return re_value;
    }

    /**
     *添加设备入库
     * @param deviceType
     * @param userName
     * @param password
     * @return
     */
    public  Map<String, Object> addhost(String deviceType,String userName, String password)
    {
        Map<String, Object> resmap =new HashMap<>();
        resmap = loadNewHost(deviceType.toString(), userName, password);
        if (!resmap.get("hostId").toString().equals("0")) {
            if (CommonNameUtil.HHREC.toString().equals(deviceType.toString().toLowerCase())) {
                try {
                    addDeviceToHost((String) resmap.get("hostId"), resmap.get("hostIp").toString());//录播添加镜头
                } catch (Exception e) {
                    logger.error("添加镜头失败，ip为:" + resmap.get("hostIp").toString(), e);
                }
            }
        }
        return resmap;
    }

    /**
     * 手动发现：网络 已废弃
     * @param ip
     * @param deviceType 设备类型
     * @param typeInt 设备编号
     * @param className 设备名称
     * @param factory 设备出厂名称
     * @param metString
     * @return
     */
    @Deprecated
    public Map<String, Object> manualNetDiscovered(String ip,String deviceType, String typeInt, String className, String factory, String metString)
    {
        Map<String, Object> re_value = new HashMap<String, Object>();
        String model = CommonNameUtil.getModel(deviceType);
        String userName = "";
        String password = "";
        String[] netStringValue = metString.split(",");
        Boolean res = manualNetDiscovered(ip, typeInt, factory, className, model, userName, password, netStringValue);
        if (res) {
            if (Host.INSTANCE.isOnline(ip)) {
                re_value = loadNewHost(ip, deviceType, userName, password, "");
                if (!re_value.get("hostId").toString().equals("0")) {
                    updateHostName(ip, className);
                }
            }
        }
        return re_value;
    }

    /**
     * 手动发现
     * @param ip
     * @param deviceType
     * @param typeInt
     * @param className
     * @param factory
     * @param userName
     * @param password
     * @param hostPort
     * @param hostChannel
     * @param isTour
     * @param metString
     * @param cameraString
     * @return
     */
    public Map<String, Object> manualDiscover(String ip,String deviceType, String typeInt, String className, String factory, String userName,String password,String hostPort,String hostChannel,String isTour,String metString,String cameraString, String mcuClientCode)
    {
        String model = CommonNameUtil.getModel(deviceType);
        if(userName==null){userName = "";}
        if(password==null){password = "";}
        if(hostPort==null){hostPort = "";}
        if(hostChannel==null){hostChannel = "";}
        if(metString==null){metString = "";}
        if(cameraString==null){cameraString = "";}
        if(isTour==null){isTour = "0";}
        if(mcuClientCode==null){mcuClientCode = "";}
        Map host=DeviceDao.INSTANCE.getHostInfoByCode(mcuClientCode);

        if (!TypeTransformationUtil.isNeedDeviceGrid(deviceType.toUpperCase())) {//是否需要通过DeviceGrid服务添加
            Long result = Long.parseLong("0");
            Map hostMap = getHostInfoByIp(ip);
            if (hostMap==null || hostMap.size()==0) {
                String hostSeriano = "";
                int dspecId = 0;
                String hostDesc = "0";
                String hostMac = "";
                String hhtcMac = "";
                if (CommonNameUtil.HHTSE.toString().equals(deviceType.toLowerCase())) {//安防设备
                    hostSeriano = "com.hht.device.HHTSEDevice";
                    dspecId = 21;
                } else if (CommonNameUtil.HHTAU.toString().equals(deviceType.toLowerCase())) {//音频设备
                    hostSeriano = "com.hht.device.HHTSEDevice";
                    dspecId = 22;
                    factory = "HiteTech-HHTSE";//因代理jar包中音频未做处理，故暂时按安防的做处理
                } else if (CommonNameUtil.HHTCTE.toString().equals(deviceType.toLowerCase())) {//会议终端设备
                    hostSeriano = "com.hht.device.HHTCTEDevice";
                    dspecId = 34;
                }
                try {
                    if (CommonNameUtil.HHTSE.toString().equals(deviceType.toLowerCase())){
                        result = DeviceDao.INSTANCE.addHost(className, ip, hostSeriano, dspecId, hostDesc, userName, password, hostMac, hhtcMac, factory, hostPort, hostChannel,isTour);
                    } else if (CommonNameUtil.HHTMCU.toString().equals(deviceType.toLowerCase()) || CommonNameUtil.HHTCTE.toString().equals(deviceType.toLowerCase())) {
                        if(host!=null&&host.size()>0&&!"".equals(mcuClientCode) && host.get("mcu_code")!=null && host.get("mcu_code").equals(mcuClientCode)){
                            result=Long.parseLong("-1");
                        }else {
                            result = DeviceDao.INSTANCE.addMcuHost(className, ip, hostSeriano, dspecId, hostDesc, userName, password, hostMac, hhtcMac, factory, hostPort, hostChannel, mcuClientCode);
                        }
                    } else {
                        result = DeviceDao.INSTANCE.addHost(className, ip, hostSeriano, dspecId, hostDesc, userName, password, hostMac, hhtcMac, factory, hostPort, hostChannel);
                    }
                } catch (Exception e) {
                    logger.error("设备插入数据异常，异常ip为：" + ip);
                }
            }
            Map<String, Object> returnMap = new HashMap<>();
            returnMap.put("isDevicediscover", isDevicediscover);
            returnMap.put("isMuti", "0");
            returnMap.put("hostId", result);
            returnMap.put("hostIp", ip);
            returnMap.put("mac", "");
            return returnMap;
        }
        Boolean res = false;
        logger.debug("手动添加设备ip=" + ip + "--------------deviceType=" + deviceType + "---------------typeInt=" + typeInt);
        if (DeviceNameUtil.DEVICE_CLASSROOMMONITOR.equals(typeInt)) {//虚拟设备
            model = "NetworkVideoTransmitter";
            String[] netStringValue = metString.split(",");// 码流地址
            String[] cameraStringValue = cameraString.split(",");//镜头名称
            res = manualVirtualDiscovered(ip, typeInt, factory, className, model, userName, password, cameraStringValue, netStringValue);
        } else {
//            res = manualDiscovered(ip, typeInt, factory, className, model, userName, password);
            if(CommonNameUtil.HHTMCU.toString().equals(deviceType)) {
                if(!mcuClientCode.equals(host.get("mcu_code"))) {
                    res = manualDiscovered(ip, typeInt, factory, className, model, userName, password);
                }else{  //相同设备编码不能继续注册
                    Map<String, Object> error_map=new HashMap<>();
                    error_map.put("hostId",Long.parseLong("-1"));
                    return error_map;
                }
            }else{
                res = manualDiscovered(ip, typeInt, factory, className, model, userName, password);
            }
        }
        logger.debug("------------------devicegrid返回给设备服务：" + res);
        if (res) {
                logger.debug("------------------device服务添加成功ip=" + ip);
                Map<String, Object> resmap = loadNewHost(ip, deviceType, userName, password, mcuClientCode);
                if (!resmap.get("hostId").toString().equals("0") && !"".equals(className)) {

                    //mcu设备和不需要通知设备修改名称的直接在数据库里修改名称
                    if (CommonNameUtil.HHTMCU.toString().equals(deviceType)||!TypeTransformationUtil.updateNameNeedDeviceGrid(deviceType)){
                        try {
                            DeviceDao.INSTANCE.updateHostName(ip,className);
                            logger.debug("修改"+deviceType+"类型设备名称为："+className);
                        } catch (Exception e) {
                            logger.error("修改"+deviceType+"类型设备名称异常",e);
                        }
                    }else {
                        updateHostName(ip, className);
                    }
                    if (CommonNameUtil.HHREC.toString().equals(deviceType.toLowerCase())) {
                        try {
                            if (DeviceNameUtil.DEVICE_CLASSROOMMONITOR.equals(typeInt)) {
                                addVirtualDeviceToHost((String) resmap.get("hostId"), ip);//虚拟设备添加镜头
                            } else {
                                addDeviceToHost((String) resmap.get("hostId"), ip);//录播添加镜头
                            }
                        } catch (Exception e) {
                            logger.error("添加镜头失败，ip为:" + ip, e);
                        }
                    }
                }
                return resmap;
        } else {
            return new HashMap<String, Object>();
        }
    }

    /**
     * 手动发现虚拟教室，已合并到manualDiscover接口中
     * @param ip
     * @param deviceType
     * @param typeInt
     * @param className
     * @param factory
     * @param metString
     * @param cameraString
     * @return
     */
    @Deprecated
    public Map<String, Object> manualVirtualDiscovered(String ip,String deviceType, String typeInt, String className, String factory, String metString,String cameraString)
    {
        String model = "NetworkVideoTransmitter";
        String userName = "";
        String password = "";
        String[] netStringValue = metString.split(",");// 码流地址
        String[] cameraStringValue = cameraString.split(",");//镜头名称
        Boolean res = manualVirtualDiscovered(ip, typeInt, factory, className, model, userName, password, cameraStringValue, netStringValue);
        if (res) {
            if (Host.INSTANCE.isOnline(ip)) {
                Map<String, Object> resmap = loadNewHost(ip, deviceType, userName, password,"");
                if (!resmap.get("hostId").toString().equals("0")) {
                    updateHostName(ip, className);
                }
                return resmap;
            } else {
                return  new HashMap<String, Object>();
            }
        } else {
            return new HashMap<String, Object>();
        }
    }

    //设备是否在线
    public Boolean isOnline(String ip)
    {
        Boolean res = Host.INSTANCE.isOnline(ip);
        return res;
    }

    //获取设备在线状态
    public String getStatus(String ip)
    {
        String res = Host.INSTANCE.getStatus(ip);
        return res;
    }

    //批量获取设备在线状态，多个ip用逗号隔开
    public List<Map<String, String>> getDeviceStatus(String ip)
    {
        List<Map<String, String>> res = DeviceDao.INSTANCE.getDevicesDetail(ip);
        return res;
    }
    /**
     * 修改设备,不建议使用
     * @param ip
     * @param userName
     * @param password
     * @return
     */
    @Deprecated
    public Boolean update(String ip, String userName, String password)
    {
        String usernamestr = "";
        String passwordstr = "";
        if (userName == null || userName.equals("")) {
            usernamestr = "";
        } else {
            usernamestr = userName;
        }
        if (password == null || password.equals("")) {
            passwordstr = "";
        } else {
            passwordstr = password;
        }
        Boolean res = Host.INSTANCE.update(ip, usernamestr, passwordstr);
        return res;
    }

    //通过ip修改设备名称
    public Boolean updateHostName(String ip,String hostName)
    {
        boolean re_value = false;
        try {
            DeviceManager.invoke(ip, new HHTCOperationRequestSetHostName(hostName));//通知设备修改名称
            re_value = DeviceDao.INSTANCE.updateHostName(ip, hostName);
            logger.debug("修改设备" + ip + "的名称为" + hostName + ":" + re_value);
        } catch (Exception e) {
            logger.error("修改设备名称异常", e);
        }
        return re_value;
    }

    //通过id修改设备名称
    public Boolean updateHostNameById(String hostId,String hostName)
    {
        boolean re_value = false;
        int id = Integer.parseInt(hostId);
        if (!"".equals(hostName)) {
            try {
                Map<String, String> host = DeviceDao.INSTANCE.getHostBySpec(id);
                if (host.size() > 0) {
                    String Name = host.get("host_name");
                    if (!hostName.equals(Name)) {
                        String deviceType = host.get("dtype_name");
                        String hostIp = host.get("host_ipaddr");
                        if (TypeTransformationUtil.isNeedDeviceGrid(deviceType)) {//是否需要设备发现服务
                            if (TypeTransformationUtil.updateNameNeedDeviceGrid(deviceType)) {//是否需要通知设备
                                boolean isOnline = Host.INSTANCE.isOnline(hostIp);
                                logger.debug("设备" + hostIp + "在线状态为：：" + isOnline);
                                if (isOnline) {
                                   DeviceManager.invoke(hostIp, new HHTCOperationRequestSetHostName(hostName));//通知设备修改名称
                                   re_value = DeviceDao.INSTANCE.updateHostNameById(id, hostName);
                                   logger.debug("修改设备" + id + "的名称为" + hostName + ":" + re_value);
//
                                } else {
                                    logger.debug("修改设备名称失败");
                                    re_value = false;
                                }
                            } else {
                                re_value = DeviceDao.INSTANCE.updateHostNameById(id, hostName);
                                if (re_value) {
                                    logger.debug("修改设备" + id + "的名称为" + hostName + ":" + re_value);
                                } else {
                                    logger.debug("修改设备名称失败");
                                    re_value = false;
                                }
                            }
                        } else {
                            re_value = DeviceDao.INSTANCE.updateHostNameById(id, hostName);
                        }
                    }else {
                        logger.debug("名称相同，修改名称失败");
                        re_value = false;
                    }
                }
            } catch (Exception e) {
                logger.error("修改设备名称异常", e);
            }
        }
        return re_value;
    }

    /**
     * 更新设备信息（mcu用，可修改设备名称和ip）
     * 此方法不建议使用，建议应用进行处理设备的ip的改变，进行添加，删除
     * @param hostId 设备id
     * @param hostIp 设备ip
     * @param hostName 设备名称
     * @return
     */
    public Boolean updateHostInfo(String hostId,String hostIp, String hostName){
        Boolean res = false;
        try {
            Map host = DeviceDao.INSTANCE.getHostInfoById(hostId);
            if(host.size()>0){
                res = DeviceDao.INSTANCE.updateHostInfo(hostId,hostIp,hostName);
                logger.debug("修改设备" + hostId + "的信息成功");
            }
        } catch (Exception e) {
            logger.error("修改设备信息异常", e);
        }
        return res;

    }

    //设备名是否存在（集控平台）
    public Boolean isHostNameExist(String hostName)
    {
        Boolean res = DeviceDao.INSTANCE.isHostnameExist(hostName);
        return res;
    }

    //获取设备信息
    public List<Map<String, Object>> hostInfo(String hostIdStr,String deviceType)
    {
        List<Map<String, Object>> res = new ArrayList<>();
        try {
            res = DeviceDao.INSTANCE.getHostList(hostIdStr, deviceType);
        } catch (Exception e) {
            logger.error("根据型号获取主机异常", e);
        }
        return res;
    }

    //获取设备信息并分页
    public Map<String, Object> hostInfoByPage(String hostIdStr,String deviceType,String currentPage,String pageSize)
    {
        int currentPages = Integer.parseInt(currentPage);
        int pageSizes = Integer.parseInt(pageSize);
        Map<String, Object> res = new HashMap<>();
        try {
            PageData pageData = DeviceDao.INSTANCE.getHostListByPage(hostIdStr, deviceType, currentPages, pageSizes);
            res.put(HOSTINFO, pageData.getItems());
            res.put("pageCount", pageData.getTotalPages());
            res.put("itemsCount", pageData.getTotalItems());
            return res;
        } catch (Exception e) {
            logger.error(ERROR_HOSTLIST, e);
        }
        return res;
    }

    //获取设备及地点信息并分页
    public Map<String, Object> getHostListWithAreaByPage(String hostIdStr,String deviceType,String currentPage,String pageSize)
    {
        int currentPages = Integer.parseInt(currentPage);
        int pageSizes = Integer.parseInt(pageSize);
        Map<String, Object> res = new HashMap<>();
        try {
            PageData pageData = DeviceDao.INSTANCE.getHostListWithArecByPage(hostIdStr, deviceType, currentPages, pageSizes);
            res.put(HOSTINFO, pageData.getItems());
            res.put("pageCount", pageData.getTotalPages());
            res.put("itemsCount", pageData.getTotalItems());
        } catch (Exception e) {
            logger.error(ERROR_HOSTLIST, e);
        }
        return res;
    }

    //根据名称或者ip查询设备信息并分页
        public Map<String, Object> getConditionsHostListByPage(String areaId,String conditions,String deviceType,String currentPage,String pageSize)
    {
        int currentPages = Integer.parseInt(currentPage);
        int pageSizes = Integer.parseInt(pageSize);
        Map<String, Object> res = new HashMap<>();
        try {
            PageData pageData = DeviceDao.INSTANCE.getConditionsHostListByPage(areaId, conditions, deviceType, currentPages, pageSizes);
            res.put(HOSTINFO, pageData.getItems());
            res.put("pageCount", pageData.getTotalPages());
            res.put("itemsCount", pageData.getTotalItems());
        } catch (Exception e) {
            logger.error(ERROR_HOSTLIST, e);
        }
        return res;
    }

    /**
     * 未分组设备信息 （新版集控平台对设备的管理由设备管理控制）
     * @param hostIdStr
     * @param deviceType
     * @return
     */
    @Deprecated
    public  List<Map<String, Object>> notHostInfo(String hostIdStr,String deviceType)
    {
            String hostIdStrs = hostIdStr==null?"":hostIdStr;
            String type = deviceType==null?"":deviceType;
        List<Map<String, Object>> res = new ArrayList<>();
        try {
            res = DeviceDao.INSTANCE.getNotHostList(hostIdStrs, type);
        } catch (Exception e) {
            logger.error("获取未分组设备异常！", e);
        }
        return res;
    }

    //获取某一类型所有设备列表
    public Map<String, Object> getAllHostList(String deviceType)
    {
        String type = deviceType==null?"":deviceType;
        Map<String, Object> res = new HashMap<>();
        res.put(HOSTINFO, DeviceDao.INSTANCE.getAllHostList(type));
        return res;
    }

    public Map<String,Object> getRegisteredHost(){
        Map<String, Object> res = new HashMap<>();
        List<Map<String, Object>>  hostList = DeviceDao.INSTANCE.getRegisteredHost();
        logger.debug("获取已添加设备列表" +hostList.toString());
        res.put(HOSTINFO, hostList);
        return res;
    }
    /**
     * 未分组设备信息并分页
     * @param hostIdStr
     * @param deviceType
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Deprecated
    public Map<String, Object> notHostInfoByPage(String hostIdStr,String deviceType,String currentPage,String pageSize)
    {
        int currentPages = Integer.parseInt(currentPage);
        int pageSizes = Integer.parseInt(pageSize);
        Map<String, Object> res = new HashMap<>();
        try {
            PageData pageData = DeviceDao.INSTANCE.getNotHostListByPage(hostIdStr, deviceType, currentPages, pageSizes);
            res.put(HOSTINFO, pageData.getItems());
            res.put("pageCount", pageData.getTotalPages());
            res.put("itemsCount", pageData.getTotalItems());
            return res;
        } catch (Exception e) {
            logger.error("获取未分组设备信息并分页异常", e);
        }
        return res;
    }

    /**
     * 删除设备
     * @param hostId
     * @param ip
     * @return
     */
    public Boolean delHost(String hostId, String ip)
    {
        Integer id = Integer.parseInt(hostId);
        Boolean res = delHost(ip, id);
        logger.debug("删除设备"+hostId+"信息："+res);
        //删除录播设备的同时删除相关的镜头信息
        if (res == true && DeviceDao.INSTANCE.isHostIdExist(id)){
            try {
                res = DeviceDao.INSTANCE.delDeviceToHost(id);
                logger.debug("删除设备"+hostId+"镜头信息："+res);
            } catch (Exception e) {
                logger.error("删除镜头信息失败",e);
            }
        }
        return res;
    }

    /**
     * 根据类型删除设备
     * @param hostId
     * @param ip
     * @param deviceType
     * @return
     */
    public Boolean delHostByType(String hostId,String ip,String deviceType)
    {
        Integer id = Integer.parseInt(hostId);
        Boolean res = false;
        if (TypeTransformationUtil.isNeedDeviceGrid(deviceType)) {
            res = delHost(ip, id);
            if (res&&CommonNameUtil.HHREC.toString().equals(deviceType.toLowerCase())) {//录播设备删除镜头
                try {
                     HostDeviceDao.INSTANCE.delHost(id.toString());
                } catch (Exception e) {
                    logger.error("删除镜头失败，HostId为:" + id, e);
                }
            }
        } else {
            try {
                res = DeviceDao.INSTANCE.delHost(id);
            } catch (Exception e) {
                logger.error("删除数据失败，id为:" + id, e);
            }
        }
        return res;
    }

    public Map<String,Object> delHostListByType(String hostIdStr,String hostIpStr,String hostTypeStr){
        boolean res = false;
        int count = 0;
        Map<String,Object> map = new HashMap<String,Object>();
        String[] hostIds = hostIdStr.split(",");
        String[] hostTypes = hostTypeStr.split(",");
        for (int i=0;i<hostIds.length;i++){
            if (TypeTransformationUtil.isNeedDeviceGrid(hostTypes[i])) {
                try {
                    res =DeviceDao.INSTANCE.delHost(Integer.valueOf(hostIds[i]));
                } catch (Exception e) {
                    logger.error("删除设备失败，HostId为："+hostIds[i],e);
                }
                //录播设备删除镜头
                if (res&&CommonNameUtil.HHREC.toString().equals(hostTypes[i].toLowerCase())) {
                    try {
                        HostDeviceDao.INSTANCE.delHost(hostIds[i].toString());
                    } catch (Exception e) {
                        logger.error("删除镜头失败，HostId为:" + hostIds[i], e);
                    }
                }
            } else {
                try {
                    res = DeviceDao.INSTANCE.delHost( Integer.valueOf(hostIds[i]));
                } catch (Exception e) {
                    logger.error("删除数据失败，id为:" + hostIds[i], e);
                }
            }
            if (res){
                count++;
            }
        }
        DeviceManager.removeGroupDevice(hostIpStr);
        map.put("isSuccess",res);
        map.put("count",count);
        return map;
    }

    /**
     * 获取某一类型所有设备列表
     * @param type
     * @return
     */
    public List<Map<String, Object>> hostInfoBytype(String type)
    {
        String deviceType = type==null?"":type;
        List<Map<String, Object>> res = DeviceDao.INSTANCE.getHostBytype(deviceType);
        return res;
    }

    //获取设备数量
    public int hostCount(String deviceType)
    {
        int hostCount = DeviceDao.INSTANCE.getHostCount(deviceType);
        return hostCount;
    }

    //通过id获取设备信息（设备管理）
    public Map getHostInfoById(String hostId)
    {
        Map res = DeviceDao.INSTANCE.getHostInfoById(hostId);
        logger.debug("获取的设备信息为："+res.toString());
        return res;
    }

    //通过ip获取设备信息
    public Map getHostInfoByIp(String hostIp)
    {
        Map res = DeviceDao.INSTANCE.getHostInfoByIp(hostIp);
        return res;
    }

    //根据设备类型获取在线设备（录播设备）
    public List<Object[]> getOnlineDevice(String deviceType,String userName,String password)
    {
        String userNames = userName== null?"":userName;
        String passWord = password==null?"":password;
        // todo 修改获取在线设备接口
        List<Device> olineDevice = null;
        try {
            olineDevice = DeviceManager.getOnlineDevice();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Object[]> ipList = new ArrayList<Object[]>();
        if (olineDevice!=null){
            for (int i = 0; i < olineDevice.size(); i++) {
                Device device = olineDevice.get(i);
                if (device != null) {
                    if (device.getDeviceStatus().equals(GlobalDefinitions.OnlineStatus_Online)) {
                        String type = device.getDeviceType();
                        if (TypeTransformationUtil.isRecord(deviceType,type)) {
                            ipList.add(new Object[]{device.getIP(), userNames, passWord});
                        }
                    }
                }
            }
        }

        logger.debug("在线设备ip为："+ipList.toString());
        return ipList;
    }

    //获取镜头url
    public String getCameraUrl(String ip, String cameraToken)
    {
        String cameralUrl = "";
        OperationRequestGetStreamingURL operationRequestGetStreamingURL = new OperationRequestGetStreamingURL(cameraToken);
        try {
            Object result = DeviceManager.invoke(ip, operationRequestGetStreamingURL);
            logger.debug("getCameraUrl为" + result == null?"空" : result.toString());
            if (result != null) cameralUrl = result.toString();
        } catch (Exception e) {
            logger.error("获取getCameraUrl异常", e);
        }
        return cameralUrl;
    }

    //获取设备型号（录播添加镜头时用到）
    public String getLocalModel(String ip)
    {
        Device device = Host.INSTANCE.get(ip);
        String res = Host.INSTANCE.getLocalModel(device);
        return res;
    }

    //注册事件通知接收的url
    public Boolean registEventAdrr(String url,String port,String ip,String sys)
    {
        String portNum = port==null?"":port;
        String hostIp = ip==null?"":ip;
        String sysFlag = sys==null?"":sys;
        Boolean res = Host.INSTANCE.registEventAddr(hostIp, url, portNum, sysFlag);
        //平台订阅设备服务后，设备服务向代理jar包请求设备的在线和离线状态，by zlj
        String deviceType = DeviceSysToTypeUtil.getDeviceType(sys);
        logger.debug("平台请求设备在线状态的设备类型为："+deviceType);
        String networkInterface = "";
        networkInterface = System.getProperty("ip");
        String serviceUrl = "";
        if (!networkInterface.equals("")) {
            String servicePort = String.valueOf(TomcatPortUtil.INSTANCE.getPort());
            serviceUrl = "http://" + networkInterface + ":" + servicePort + "/service/eventOnOffLineService";
        }
        DeviceManager.notifyOnline(serviceUrl,deviceType);
        return res;
    }


    //获取主机ip地址
    public String getIp()
    {
        String ip = System.getProperty("ip");
        return ip;
    }

    //根据mac地址获取设备名
    public Map getNameByMac(String mac)
    {
        Map res = DeviceDao.INSTANCE.getNameByMac(mac);
        return res;
    }


    /**
     * 根据设备名获取主机信息
     * @param names 设备名称，多个可用逗号分割
     * @param deviceType 设备类型
     * @return
     */
    public List<Map<String, Object>> getHostListByNames(String names,String deviceType)
    {
        List<Map<String, Object>> res = new ArrayList<>();
        try {
            res = DeviceDao.INSTANCE.getHostListByNames(names, deviceType);
        } catch (Exception e) {
            logger.error("根据设备名获取主机信息异常", e);
        }
        return res;
    }

    //按名称获取机构下的设备
    public List<Map<String, Object>> getAreaHostListByNames(String areaId,String name,String deviceId,String deviceType)
    {
        List<Map<String, Object>> res = new ArrayList<>();
        try {
            res = DeviceDao.INSTANCE.getAreaHostListByNames(areaId, name, deviceType, deviceId);
        } catch (Exception e) {
            logger.error("根据设备名获取主机信息异常", e);
        }
        return res;
    }

    //获取所有设备类型
    public List<Map<String, Object>> getAllDeviceType()
    {
        List<Map<String, Object>> res = new ArrayList<>();
        try {
            res = DeviceDao.INSTANCE.getAllDeviceType();
        } catch (Exception e) {
            logger.error("获取设备类型信息异常", e);
        }
        return res;
    }

    //获取现有设备类型
    public List<Map<String, Object>> getExistingTypeList()
    {
        List<Map<String, Object>> res = new ArrayList<>();
        try {
            res = DeviceDao.INSTANCE.getExistingTypeList();
        } catch (Exception e) {
            logger.error("根据设备类型信息异常", e);
        }
        return res;
    }

    //获取录播子类型
    public List<Map<String, Object>> getHrecType()
    {
        List<Map<String, Object>> res = new ArrayList<>();
        try {
            res = DeviceDao.INSTANCE.getHrecType();
        } catch (Exception e) {
            logger.error("根据设备类型信息异常", e);
        }
        return res;
    }

    /**
     *获取设备的版本信息
     * @param ip 设备ip
     * @return
     */
    public String getDeviceVersion(String ip){
        Device device = DeviceManager.getDevice(ip);
        String deviceVer = "";
        if (device!=null){
            deviceVer = (String) device.GetDeviceExtensionInfo().get("vs");
            logger.debug("获取的设备版本为："+deviceVer);
        }else{
            logger.error("获取device为null");
        }
        return deviceVer;
    }

        /**
         * 删除设备
         *
         * @param ip
         * @param id
         * @return
         */
        private Boolean delHost(String ip, int id) {
            Boolean re_value = false;
            try {
                boolean flag =  Host.INSTANCE.delete(ip);
                //boolean flag = true;
                if (flag == true) {
                    re_value = DeviceDao.INSTANCE.delHost(id);
                }
            } catch (Exception e) {
                logger.error("删除设备异常", e);
            }
            return re_value;
        }

        /**
         * 发现设备
         * @param deviceType
         * @return
         */
        private Map<String, Device> discovered(String deviceType) {
            Map<String, Device> deviceMap = new LinkedHashMap<String, Device>();
            //查询允许数量的设备 todo 进行修改，直接调devicegrid接口
            Set<Device> deviceSet = DeviceManager.getLicenseDevices(CommonNameUtil.ALL.toString()); //按照大屏与录播整合后的查询所有设备
            if (deviceType.equals(CommonNameUtil.ALL.toString())) {
                for (Device device : deviceSet) {
                    try{
                        if (device!=null && TypeTransformationUtil.isAll(deviceType, device.getDeviceType())){
                            deviceMap.put(device.getIP(), device);
                        }
                    }catch (Exception e){
                        logger.error("设备发现异常", e);
                    }
                }
            } else {
                String[] types = deviceType.split(",");
                for (String type : types) {
                    for (Device device : deviceSet) {
                        try{
                            if (device!=null&&TypeTransformationUtil.isHas(type, device.getDeviceType())){
                                deviceMap.put(device.getIP(), device);
                            }
                        }catch (Exception e){
                            logger.error("设备发现异常", e);
                        }
                    }
                }
            }
            return deviceMap;

        }

        /**
         * 手动发现设备
         *
         * @param ip
         * @param userName
         * @param password
         * @return
         */
        private boolean manualDiscovered(String ip, String typeInt, String factory, String className, String model, String userName, String password) {

            String deviceUrl = DeviceNameUtil.getDeviceUrl(typeInt, ip);
            try {
                AddDeviceRequest addDeviceRequest = new AddDeviceRequest();
                addDeviceRequest.setType(model);
                addDeviceRequest.setxAddrs(deviceUrl);
                addDeviceRequest.setManufacturer(factory);
                addDeviceRequest.setModel("");
                addDeviceRequest.setVersion("");
                addDeviceRequest.setClassName(className);
                logger.debug("手动发现地址为：" + deviceUrl);
                return DeviceManager.createOnvifDevice(addDeviceRequest);
            } catch (Exception e) {
                logger.error("手动发现" + ip + "异常,发现地址为：" + deviceUrl, e);
//            logger.error(e.getMessage());
                return false;
            }

        }

        /**
         * 手动发现设备
         *
         * @param ip
         * @param userName
         * @param password
         * @return
         */
        private boolean manualNetDiscovered(String ip, String typeInt, String factory, String className, String model, String userName, String password, String[] netString) {
            String deviceUrl = DeviceNameUtil.getDeviceUrl(typeInt, ip);

            try {
                AddDeviceRequest addDeviceRequest = new AddDeviceRequest();
                addDeviceRequest.setType(model);
                addDeviceRequest.setxAddrs(deviceUrl);
                addDeviceRequest.setManufacturer(factory);
                addDeviceRequest.setModel("");
                addDeviceRequest.setVersion("");
                addDeviceRequest.setClassName(className);
                logger.debug("手动添加跨网段设备");
                addDeviceRequest.setServices(netString);
                logger.debug("手动发现ip为" + ip + "的网络设备，发现地址为" + deviceUrl);
                return DeviceManager.createOnvifDevice(addDeviceRequest);
            } catch (Exception e) {
                logger.error("手动发现网络设备失败,发现地址为" + deviceUrl, e);
//            logger.error(e.getMessage());
                return false;
            }

        }

        /**
         * 手动发现虚拟设备
         *
         * @param ip
         * @param userName
         * @param password
         * @return
         */
        private boolean manualVirtualDiscovered(String ip, String typeInt, String factory, String className, String model, String userName, String password, String[] cameraName, String[] netString) {
            String deviceUrl = "http://" + ip + ":8080/";
            try {
                AddVirtualDeviceRequest addVirtualDeviceRequest = new AddVirtualDeviceRequest();
                addVirtualDeviceRequest.setType(model);
                addVirtualDeviceRequest.setxAddrs(deviceUrl);
                addVirtualDeviceRequest.setManufacturer(factory);
                addVirtualDeviceRequest.setModel("HHTVD-IPCAM");
                addVirtualDeviceRequest.setVersion("");
                addVirtualDeviceRequest.setClassName(className);
                addVirtualDeviceRequest.setServices(netString);
                List<BaseChannelItem> baseChannelItems = new ArrayList();

                for (int i = 0; i < netString.length; i++) {
                    BaseChannelItem baseChannelItem = new BaseChannelItem();
                    baseChannelItem.setChannelname(cameraName[i]);
                    baseChannelItem.setUri(netString[i]);
                    baseChannelItems.add(baseChannelItem);
                }

                addVirtualDeviceRequest.setChannelItems(baseChannelItems);

                logger.debug("手动发现ip为" + ip + "的虚拟教室设备，发现地址为" + deviceUrl + " 教室名：" + className);
                return DeviceManager.createVirtualDevice(addVirtualDeviceRequest);
            } catch (Exception e) {
                logger.error("手动发现网络设备失败,发现地址为" + deviceUrl, e);
//            logger.error(e.getMessage());
                return false;
            }

        }

        /**
         * 添加设备入库
         *
         * @param _type    type 为空 所有设备，=hhrec 录播设备 =hhtc大屏设备
         * @param userName
         * @param password
         * @return
         */
        private Map<String, Object> loadNewHost(String _type, String userName, String password) {
            boolean isMuti = false;
            isDevicediscover = false;
            String hostId = "0";
            String hostIp = "";
            int discoverdSize = 0;

            if (reentrantLock.tryLock()) {
                if (_type.equals(CommonNameUtil.ALL.toString())) {
                    Collection<Device> deviceCollection = discovered(_type).values();//获取主机列表

                    discoverdSize = deviceCollection.size();//获取列表大小
                    logger.debug("发现设备数量为" + discoverdSize);
                    try {
                        for (Device device : deviceCollection) {
                            try {
                                hostIp = device.getIP();
                                hostId = addHost(device, _type, userName, password, "");
                                if (GlobalDefinitions.DeviceType_ONVIF.equals(device.getDeviceType()) || GlobalDefinitions.DeviceType_IPC.equals(device.getDeviceType())) {
                                    if ("Virtual".equals(device.getManufacturer())) {
                                        addVirtualDeviceToHost(hostId, hostIp);//虚拟设备添加镜头
                                    } else {
                                        addDeviceToHost(hostId, hostIp);//录播添加镜头
                                    }
                                }
                                logger.debug("设备入库hostid为：" + hostId + ",hostIp为：" + hostIp + "设备类型:" + device.getDeviceType());
                            } catch (Exception e) {
                                logger.error("添加设备异常，设备ip为" + device.getIP());
                                hostId = "0";
                                hostIp = "";
                            }
                        }
                    } finally {
                        reentrantLock.unlock();
                        isDevicediscover = discoverdSize != 0;
                    }
                } else {
                    Collection<Device> deviceCollection = discovered(_type).values();//获取主机列表
                    discoverdSize = deviceCollection.size();//获取列表大小
                    logger.debug("发现设备数量为" + discoverdSize);

                    try {
                        for (Device device : deviceCollection) {
                            try {
                                hostIp = device.getIP();
                                hostId = addHost(device, "", userName, password, "");
                                if (GlobalDefinitions.DeviceType_ONVIF.equals(device.getDeviceType()) || GlobalDefinitions.DeviceType_IPC.equals(device.getDeviceType())) {
                                    if ("Virtual".equals(device.getManufacturer())) {
                                        addVirtualDeviceToHost(hostId, hostIp);//虚拟设备添加镜头
                                    } else {
                                        addDeviceToHost(hostId, hostIp);//录播添加镜头
                                    }
                                }
                                logger.debug("设备入库hostid为：" + hostId + ",hostIp为：" + hostIp + "设备类型:" + device.getDeviceType());
                            } catch (Exception e) {
                                logger.error("添加设备异常，设备ip为" + device.getIP());
                                hostId = "0";
                                hostIp = "";
                            }
                        }
                    } finally {
                        reentrantLock.unlock();
                        isDevicediscover = discoverdSize != 0;
                    }
                }
            } else {
                isMuti = true;
            }
            Map<String, Object> map = new HashMap<>();
            map.put("isDevicediscover", isDevicediscover);
            map.put("isMuti", isMuti);
            map.put("hostId", hostId);
            map.put("hostIp", hostIp);
            map.put("deviceCount",discoverdSize);
            return map;
        }

        /**
         * 添加单个设备入库
         *
         * @param _type    type 为空 所有设备，=hhrec 录播设备 =hhtc大屏设备
         * @param userName
         * @param password
         * @return
         */
        private Map<String, Object> loadNewHost(String ip, String _type, String userName, String password, String mcuClientCode) {
            boolean isMuti = false;
            isDevicediscover = false;
            String hostId = "0";
            String hostIp = "";
            String mac = "";

            if (reentrantLock.tryLock()) {
                if (_type.equals(CommonNameUtil.ALL.toString())) {
//                    Set<String> registeKeys = DeviceManager.getRegistedDevicesList();


                    Collection<Device> deviceCollection = discovered(_type).values();//获取主机列表

                    int size = deviceCollection.size();//获取列表大小
                    try {
                        for (Device device : deviceCollection) {
                            try {
                                hostIp = device.getIP().toString();
                                mac = device.getMac().toString();
                                if (hostIp.equals(ip)) {
                                    hostId = addHost(device, _type, userName, password, mcuClientCode);
                                    logger.debug("单个设备入库hostid为：" + hostId + ",hostIp为：" + hostIp + "设备类型:" + device.getDeviceType());
                                    break;
                                }
                            } catch (Exception e) {
                                logger.error("添加单个设备异常，设备ip为" + ip + "实际添加ip为" + device.getIP());
                                hostId = "0";
                                hostIp = ip;
                                mac = "";
                            }
                        }
                    } finally {
                        reentrantLock.unlock();
                        isDevicediscover = size != 0;
                    }
                } else {
//                    Set<String> registeKeys = DeviceManager.getRegistedDevicesList();
                    Collection<Device> deviceCollection = discovered(_type).values();//获取主机列表
                    int size = deviceCollection.size();//获取列表大小
                    logger.debug("发现设备数量为" + size+",发现的设备详情为："+deviceCollection.toString());
                    try {
                        for (Device device : deviceCollection) {
                            try {
                                hostIp = device.getIP().toString();
                                if (hostIp.equals(ip)) {
//                                    if ("hhtmcu".equals(_type)) {
//                                        hostId = addHost(device, "hhtmcu", userName, password, mcuClientCode);
//                                    } else {
//                                        hostId = addHost(device, "", userName, password, mcuClientCode);
//                                    }
                                    hostId = addHost(device, _type, userName, password, mcuClientCode);
                                    mac = device.getMac().toString();
                                    logger.debug("单个设备入库hostid为：" + hostId + ",hostIp为：" + hostIp + "设备类型:" + device.getDeviceType());
                                    break;
                                }
                            } catch (Exception e) {
                                logger.error("添加单个设备异常，设备ip为" + ip + "实际添加ip为" + device.getIP());
                                hostId = "0";
                                hostIp = ip;
                                mac = "";
                            }
                        }
                    } finally {
                        reentrantLock.unlock();
                        isDevicediscover = size != 0;
                    }
                }
            } else {
                isMuti = true;
            }
            Map<String, Object> map = new HashMap<>();
            map.put("isDevicediscover", isDevicediscover);
            map.put("isMuti", isMuti);
            map.put("hostId", hostId);
            map.put("hostIp", ip);
            map.put("mac", mac);
            return map;
        }

        /**
         * 添加host入库
         * @param device
         * @param type
         */
        private String addHost(Device device, String type, String userName, String password, String mcuClientCode) {
            String hhtcMac = ""; //大屏设备mucmac地址
            String factory = "";//工厂名
            String sysType = "";//系统类型，主要用于区分android版和x86版的班牌

            type = TypeTransformationUtil.getType(device.getDeviceType());

            //处理901.6不能获取ops物理地址，只能获取大屏的物理地址的问题
            if (type == CommonNameUtil.HHTC.toString()) {
                HHTCDevice hhtcDevice = (HHTCDevice) device;
                try {
                    hhtcMac = hhtcDevice.getMcuAddress();
                } catch (Exception e) {
                    logger.error("获取大屏的mcumac地址异常，ip为" + device.getIP(), e);
                }
            }

            if (device.getIP().trim().length() == 0) {
                logger.debug("设备ip为null");
                return "0";
            }

            try {
                if (DeviceDao.INSTANCE.hasHost(device.getIP().trim())) {
                    Host.INSTANCE.delete(device.getIP());
                    return "0";
                }
            } catch (Exception e) {
                logger.error("删除设备异常", e);
                return "0";
            }
            String localModle = ""; //设备型号
            String mac = "";   //设备的mac地址
            //获取设备型号
            localModle = Host.INSTANCE.getLocalModel(device);
            //获取MAC地址
            mac = Host.INSTANCE.getMac(device);
            if (mac == null || mac.equals("")) {
                //syslogService.addDeviceLog(device.getIP(),device.getDeviceType()+"没有mac地址", "SYSTEM");
            }
            Map<String, String> spec = new HashMap<>();
            if (localModle == null || localModle.equals("") || localModle.equals("error") || localModle.equals("portisnotopened")) {
                spec = getSpecId(CommonNameUtil.UNKNOWN.toString());
            } else {
                spec = getSpecId(localModle);
                if (spec == null || spec.size() == 0) {
                    spec = getSpecId(CommonNameUtil.UNKNOWN.toString());
                }
            }
            int dspecId = Integer.parseInt(spec.get("dspec_id"));

            String hostName = Host.INSTANCE.getHostName(device);
            if (hostName.equals("")) {
                hostName = device.getIP();
            }

            //设置属性
            String desc = spec.get("record_type");
            String serialno = device.getClass().getName();
            factory = device.getManufacturer();
            sysType = (String) device.GetDeviceExtensionInfo().get("os");
            logger.debug("获取设备系统类型为："+sysType);

            try {
                Long result = 0L;
                if (type.equals(CommonNameUtil.HHTMCU.toString())) {
                    result = DeviceDao.INSTANCE.addMcuHost(hostName, device.getIP(), serialno, dspecId, desc, userName, password, mac, hhtcMac, factory,"", "", mcuClientCode);
                } else {
                    result = DeviceDao.INSTANCE.addHost(hostName, device.getIP(), serialno, dspecId, desc, userName, password, mac, hhtcMac, factory,sysType);
                }

                logger.debug("添加设备入库,设备信息为" + hostName + "," + device.getIP() + "," + serialno + "," + dspecId + "," + desc + "," + userName + "," + password + "," + mac + "," + hhtcMac + "," + factory+ ","+sysType+","+result);
                if (result >= 1) {
                    // todo 删除
                    DeviceManager.registDevice(device.getIP());//注册设备
//                    logger.debug("向devicegrid注册设备："+device.getIP());
                    result = Host.INSTANCE.registerEventSubscribe(device.getIP(), type, result);
                }
                return result.toString();
            } catch (Exception e) {
                logger.error("添加设备入库异常,设备信息为" + hostName + "," + device.getIP() + "," + serialno + "," + dspecId + "," + desc + "," + userName + "," + password + "," + mac + "," + hhtcMac + "," + factory+ ","+sysType, e);
                return "0";
            }
        }

    // todo 删除

//        /**
//         * 注册设备
//         *
//         * @param hostId
//         * @param userName
//         * @param password
//         * @param hostName
//         * @param hostDesc
//         * @param device
//         * @return
//         */
//        private boolean registByHost(Long hostId, String userName, String password, String hostName, String hostDesc, Device device) {
//            if (userName.equals("")) {
//                userName = null;
//            }
//            if (password.equals("")) {
//                password = null;
//            }
//            Host.INSTANCE.regist(device, hostId.toString(), hostName, userName, password, hostDesc);
//            logger.debug("向jar包注册设备信息为，hostId:" + hostId + ",hostName:" + hostName + ",ip:" + device.getIP());
//            return true;
//        }

        /**
         * 获取型号ID
         *
         * @param name
         * @return
         */
        private Map<String, String> getSpecId(String name) {
            try {
                return SpecDao.INSTANCE.getSpecByName(name);
            } catch (Exception e) {
                logger.error("获取型号ID异常", e);
                return null;
            }
        }

        /**
         * 添加虚拟镜头
         *
         * @param hostId
         * @param hostIp
         * @return
         */
        private void addVirtualDeviceToHost(String hostId, String hostIp) {
            List<String> mediaToken = null;
            try {
                mediaToken = (List<String>) DeviceManager.invoke(hostIp, new OperationRequestGetAllMediaToken());
                logger.debug("获取多媒体服务器tokenip为：" + hostIp + ",结果为：" + mediaToken.toString());
            } catch (Exception e) {
                logger.error("获取多媒体服务器token异常ip为：" + hostIp, e);
            }
            if (mediaToken == null) mediaToken = Collections.EMPTY_LIST;
            for (String str : mediaToken) {
                String mainStream = getCameraUrl(hostIp, str);
                String subStream = mainStream;
                HostDeviceDao.INSTANCE.addDeviceToken(hostId, str, str, str, mainStream, subStream);
            }
        }

        /**
         * 录播添加镜头
         *
         * @param hostId
         * @param hostIp
         * @return
         */
        private void addDeviceToHost(String hostId, String hostIp) throws Exception {
            List<String> mediaToken = null;
            String localModle = "";
            try {
                Device device = Host.INSTANCE.get(hostIp);
                localModle = Host.INSTANCE.getLocalModel(device);//获取设备型号

                mediaToken = (List<String>) DeviceManager.invoke(hostIp, new OperationRequestGetAllMediaToken());
                logger.debug("获取多媒体服务器tokenip为：" + hostIp + ",结果为：" + mediaToken.toString());
            } catch (Exception e) {
                logger.error("获取多媒体服务器token异常ip为：" + hostIp, e);
            }
            List<Map<String,String>> deviceConfigList = (List<Map<String,String>>) DeviceConfigListDao.INSTANCE.getDeviceConfigList();

            if (mediaToken == null) mediaToken = Collections.EMPTY_LIST;

            for (Map<String,String> obj : deviceConfigList) {
                if (obj.get("connect_param").trim().equals(localModle)) {
                    if (!mediaToken.contains(obj.get("sub"))) continue;
                    String mainStream = getCameraUrl(hostIp, obj.get("main"));
                    String subStream = getCameraUrl(hostIp, obj.get("sub"));
                    if (subStream.equals("")) {
                        subStream = mainStream;
                    }
                    HostDeviceDao.INSTANCE.addDeviceToken(hostId, obj.get("name"), obj.get("main"), obj.get("sub"), mainStream, subStream);
                }
            }
        }
    /**
     * 根据factory获取mcu设备列表
     * @param factory   ‘HiteTech-IFreeComm’
     * @return
     */
    public List<Map<String,String>> getMcuHostList(String factory){
        return DeviceDao.INSTANCE.getMcuHostList(factory);
    }
    public static void main(String[] args) {
//        boolean b = AuthorityCheck.authorityCheck("23", "d:/", "2.0.1");
//        System.out.println(b);

    }
}
