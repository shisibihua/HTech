package com.honghe.device.command.impl;

import com.hht.DeviceManager.DeviceManager;
import com.hht.DeviceManager.operationRequest.Imaging.HImagingGetBrightness;
import com.hht.DeviceManager.operationRequest.Imaging.HImagingGetPTZSpeed;
import com.hht.DeviceManager.operationRequest.Imaging.HImagingSetBrightness;
import com.hht.DeviceManager.operationRequest.Imaging.HImagingSetPTZSpeed;
import com.hht.DeviceManager.operationRequest.record.*;
import com.hht.callback.DeviceCallBackHandler;
import com.hht.global.GlobalDefinitions;
import com.hht.protocols.record.director.Layout;
import com.hht.protocols.record.director.LayoutWindow;
import com.hht.protocols.record.director.RecordTask;
import com.hht.protocols.record.yuntai.PTZPreset;
import com.honghe.device.Host;
import com.honghe.device.command.DeviceCommand;
import com.honghe.device.dao.DeviceDao;
import com.honghe.device.dao.HostDeviceDao;
import com.honghe.device.exception.ProgramException;
import com.honghe.device.handler.DefaultResponseHandler;
import com.honghe.device.util.JsonUtil;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by lyx on 2016-01-12.
 * 录播设备执行的操作命令
 */
public class RecordCommand implements DeviceCommand {

    private final static Logger logger = Logger.getLogger(RecordCommand.class);
    public final static RecordCommand INSTANCE = new RecordCommand();
    private final static String VIRTUAL_DEVICEID = "17";//虚拟录播设备id
    private final static String AREC_DEVICE = "Arec";//爱录客设备

    /**
     * 开机
     *
     * @param ip
     * @return
     */
    @Override
    public Boolean boot(String ip) {
        try {
            Map<String, String> mac = Host.INSTANCE.getMac(ip);
            if (mac != null && mac.size() > 0) {
                String hhtcMac = mac.get("host_hhtcmac");
                String HostMac = mac.get("host_mac");
                if (hhtcMac != null && !hhtcMac.equals("") && !hhtcMac.equals("null")) {
                    for (int i = 0; i < 3; i++) {
                        DeviceManager.WakeUpDevice(ip, hhtcMac);
                        new Thread().currentThread().sleep(200);
                    }
                }
                for (int i = 0; i < 3; i++) {
                    DeviceManager.WakeUpDevice(ip, HostMac);
                    new Thread().currentThread().sleep(200);
                }
            }
            return true;
        } catch (Exception e) {
            logger.error("开机异常ip为" + ip, e);
            return false;
        }
    }

    /**
     * 关机
     *
     * @param ip
     * @param cmdCode 关机命令
     * @return
     */
    @Override

    public boolean shutdown(String ip, String cmdCode) {
        boolean re_value = false;
        try {
            DeviceManager.invoke(ip, new OperationRequestShutDown());
            re_value = true;

            logger.debug("关机命令ip为：" + ip + ",cmdCode为：" + cmdCode + ",re_value为:" + re_value);

        } catch (Exception e) {

            logger.error("关机异常ip为" + ip + ",cmdCode为：" + cmdCode + ",re_value为:" + re_value, e);
        }
        return re_value;
    }

    /**
     * 查询音量 （未找到平台调用之处）
     *
     * @param ip  ip地址
     * @param ext 预留字段，目前未用到
     * @return Integer
     */
    @Override
    public Integer getVolume(String ip, String ext) {
        Integer re_value = 0;
        try {
            re_value = (Integer) DeviceManager.invoke(ip, new OperationRequestGetVolume());
            logger.debug("查询音量为：" + re_value + ",ip为" + ip + ",ext:" + ext);

        } catch (Exception e) {
            logger.error("查询音量异常ip为：" + ip + ",ext：" + ext, e);
        }
        return re_value;
    }

    /**
     * 获取设备功能列表
     *
     * @param ip 地址
     * @return
     */
    public List getSupports(String ip) {
        List list = new ArrayList();
        try {
            OperationRequestGetCapabilities operationRequestGetCapabilities = new OperationRequestGetCapabilities();
             list = (List) DeviceManager.invoke(ip, operationRequestGetCapabilities);
            logger.debug("获取设备功能列表正常,ip为:" + ip + ",结果为:" + list.toString());
        } catch (Exception e) {
            logger.error("获取设备功能列表异常,ip为:" + ip, e);
        }
        return list;
    }


    /**
     * 录播设备命令--设置logo
     *
     * @param ip              设备ip
     * @param path            需要设置的台标路径
     * @param position        台标插入的位置 左上：1，右上：2，左下：3，右下：4(也代替之前的guid)
     */
    public final Boolean setLogo(String ip, String path, String position) {
        DeviceCallBackHandler callBackHandler = new DefaultResponseHandler();
        try {
            OperationRequestSetLogoConfiguration operationRequestSetLogoConfiguration = new OperationRequestSetLogoConfiguration(path, position, position);
            String uuid = "";
            DeviceManager.startInvoke(ip, uuid, operationRequestSetLogoConfiguration, callBackHandler);
            logger.debug("设置logo, ip为：" + ip + ",path为：" + path + ",guid:" + position + ",position:" + position);
            return true;
        } catch (Exception e) {
            logger.error("设置logo异常 ip为：" + ip + ",path为：" + path + ",guid:" + position + ",position:" + position, e);
            return false;
        }
    }


    /**
     * 获取logo
     *
     * @param ip  ip地址
     * @param ext  预留字段，现未用到
     */
    public final Boolean getLogo(String ip, String ext) {
        DeviceCallBackHandler callBackHandler = new DefaultResponseHandler();
        try {
            OperationRequestGetLogoConfiguration operationRequestGetLogoConfiguration = new OperationRequestGetLogoConfiguration(ext);
            DeviceManager.startInvoke(ip, "getLogo", operationRequestGetLogoConfiguration, callBackHandler);
            logger.debug("获取logo ip为：" + ip + ",ext:" + ext);
            return true;
        } catch (Exception e) {
            logger.error("获取logo异常 ip为：" + ip + ",ext:" + ext, e);
            return false;
        }
    }


    /**
     * 移除logo
     *
     * @param ip              ip地址
     * @param ext             预留字段，现未用到
     */
    public final Boolean removeLogo(String ip, String ext) {
        DeviceCallBackHandler callBackHandler = new DefaultResponseHandler();
        try {
            OperationRequestRemoveLogoConfiguration operationRequestRemoveLogo = new OperationRequestRemoveLogoConfiguration(ext);
            DeviceManager.startInvoke(ip, "removeLogo", operationRequestRemoveLogo, callBackHandler);
            logger.debug("移除logo ip为：" + ip + ",ext:" + ext);
            return true;
        } catch (Exception e) {
            logger.error("移除logo异常 ip为：" + ip + ",ext:" + ext, e);
            return false;
        }
    }

    /**
     * 设置分辨率
     *录播平台已用setVideoRecorderInfo接口代替
     * @param ip             ip
     * @param referenceToken 镜头
     * @param x              分辨率中的宽
     * @param y              分辨率中的高
     */
    @Deprecated
    public final Boolean setResolution(String ip, String referenceToken, String x, String y) {
        DeviceCallBackHandler callBackHandler = new DefaultResponseHandler();
        try {
            //String referenceToken = getMainTokenByHostid(Integer.parseInt(hostId), "电影模式");
            OperationRequestSetResolution operationRequestSetResolution = new OperationRequestSetResolution(referenceToken, y, x);
            DeviceManager.startInvoke(ip, ip, operationRequestSetResolution, callBackHandler);
            logger.debug("设置分辨率 ip为：" + ip + ",referenceToken:" + referenceToken + ",x:" + x + ",y:" + y);
            return true;
        } catch (Exception e) {
            logger.error("设置分辨率异常 ip为：" + ip + ",referenceToken:" + referenceToken + ",x:" + x + ",y:" + y, e);
            return false;
        }
    }


    /**
     * 查询分辨率
     *
     * @param ip             ip
     * @param referenceToken 镜头
     */
    public final Boolean getResolution(String ip, String referenceToken ) {
        DeviceCallBackHandler callBackHandler = new DefaultResponseHandler();
        try {
            //String referenceToken = getMainTokenByHostid(Integer.parseInt(hostId), "电影模式");
            OperationRequestGetResolution operationRequestGetResolution = new OperationRequestGetResolution(referenceToken);
            DeviceManager.startInvoke(ip, ip, operationRequestGetResolution, callBackHandler);
            logger.debug("查询分辨率 ip为：" + ip + ",referenceToken:" + referenceToken);
            return true;
        } catch (Exception e) {
            logger.error("查询分辨率异常 ip为：" + ip + ",referenceToken:" + referenceToken, e);
            return false;
        }
    }

    /**
     * 设置导播模式
     *
     * @param ip      ip地址
     * @param isStart 导播模式 0：自动，1：手动，2：后台，3：停止
     * @return
     */
    public final Boolean setBackgroundDirectMode(String ip, String isStart, String ext) {
        if (ext==null){
            ext = "";
        }
        try {
            OperationRequestSetDirectMode operationRequestSetDirectMode;
            String type = "";
            if (isStart.equals("0")) {
                type = GlobalDefinitions.NVRDirectMode_Auto;
            } else if (isStart.equals("1")) {
                type = GlobalDefinitions.NVRDirectMode_Manual;
            } else if (isStart.equals("2")) {
                type = GlobalDefinitions.NVRDirectMode_Center;
            } else if (isStart.equals("3")) {
                type = GlobalDefinitions.NVRDirectMode_Stop;
            }
            operationRequestSetDirectMode = new OperationRequestSetDirectMode(type, ext);
            DeviceManager.startInvoke(ip, "", operationRequestSetDirectMode, new DefaultResponseHandler());
            logger.debug("设置导播模式 ip为：" + ip + ",isStart:" + isStart + ",ext:" + ext);
            return true;
        } catch (Exception e) {
            logger.error("设置导播模式异常 ip为：" + ip + ",isStart:" + isStart + ",ext:" + ext, e);
            return false;
        }
    }

    /**
     * 设置码率
     *录播平台已用setVideoRecorderInfo接口代替
     * @param ip             ip地址
     * @param bitrate        码率
     * @param referenceToken 镜头
     */
    @Deprecated
    public final Boolean setBitrate(String ip, String referenceToken, String bitrate) {
        DeviceCallBackHandler callBackHandler = new DefaultResponseHandler();
        int bitRate = Integer.parseInt(bitrate);
        try {
            //String referenceToken = getMainTokenByHostid(Integer.parseInt(hostId), "电影模式");
            OperationRequestSetBitrate operationRequestSetBitrate = new OperationRequestSetBitrate(referenceToken, bitRate);
            DeviceManager.startInvoke(ip, "", operationRequestSetBitrate, callBackHandler);
            logger.debug("设置码率 ip为：" + ip + ",referenceToken:" + referenceToken + ",bitrate:" + bitrate);
            return true;
        } catch (Exception e) {
            logger.error("设置码率异常 ip为：" + ip + ",referenceToken:" + referenceToken + ",bitrate:" + bitrate, e);
            return false;
        }
    }


    /**
     * 查询码率
     * @param referenceToken
     * @param ip
     */
    public final Boolean getBitrate(String ip, String referenceToken) {
        DeviceCallBackHandler callBackHandler = new DefaultResponseHandler();
        try {
            //String referenceToken = getMainTokenByHostid(Integer.parseInt(hostId), "电影模式");
            OperationRequestGetBitrate operationRequestGetBitrate = new OperationRequestGetBitrate(referenceToken);
            DeviceManager.startInvoke(ip, "", operationRequestGetBitrate, callBackHandler);
            logger.debug("查询码率 ip为：" + ip + ",referenceToken:" + referenceToken);
            return true;
        } catch (Exception e) {
            logger.error("查询码率异常 ip为：" + ip + ",referenceToken:" + referenceToken, e);
            return false;
        }
    }

    /**
     * 设置nas
     *
     * @param ip
     * @param rootPath        NAS根路径
     * @param hostName        设备名称
     * @param userName        NAS用户名
     * @param password        NAS密码
     */
    public final Boolean setNas(String ip, String rootPath, String hostName, String userName, String password) {
        DeviceCallBackHandler callBackHandler = new DefaultResponseHandler();
        try {
            OperationRequestSetNasSettings operationRequestSetNasSettings = new OperationRequestSetNasSettings(rootPath, hostName, userName, password);
            DeviceManager.startInvoke(ip, "", operationRequestSetNasSettings, callBackHandler);
            logger.debug("设置nas ip为：" + ip + ",rootPath:" + rootPath + ",hostName:" + hostName + ",password:" + password);
            return true;
        } catch (Exception e) {
            logger.error("设置nas异常 ip为：" + ip + ",rootPath:" + rootPath + ",hostName:" + hostName + ",password:" + password, e);
            return false;
        }
    }

    /**
     * 清除nas设置
     *
     * @param ip              ip
     * @param ext             预留字段，目前未用到
     */
    public final Boolean clearNas(String ip, String ext) {
        DeviceCallBackHandler callBackHandler = new DefaultResponseHandler();
        try {
            DeviceManager.startInvoke(ip, "", new OperationRequestRemoveNasSettings(ext), callBackHandler);
            logger.debug("清除nas ip为：" + ip + ",ext:" + ext);
            return true;
        } catch (Exception e) {
            logger.error("清除nas异常 ip为：" + ip + ",ext:" + ext, e);
            return false;
        }
    }

    /**
     * 查询录制状态
     * 进入和退出导播界面时会用
     * <p/>
     * String ,Object
     * Status STOP,REC,PAUSED
     * Duration
     *
     * @param ip
     * @param ext 预留字段，目前未用到
     * @return
     */
    public final Map<String, Object> getRecordingStatus(String ip, String ext) {
        Map<String, Object> record = this.getRecording(ip, ext);
        Map<String, Object> live = this.getLive(ip, ext);
        Map<String, Object> status = new HashMap<>();
        if (record == null || live == null) {
            logger.debug("查询录制状态 ip为：" + ip + ",ext:" + ext + ",status为null");

            return status;
        }
        // if(record.size()==0 || live.size()==0) return status;
//        if(record.size() == 1 && live.get("Status").toString().equals("STOP"))
//        {
//            status = record;
//            status.put("workingMode","workingModeRecording");
//            return status;
//        }
        if (record.get("Status") != null && !record.get("Status").toString().equals("STOP")) {
            status = record;
            status.put("workingMode", "workingModeRecording");
            if (!live.get("Status").toString().equals("STOP")) {
                status.put("workingMode", "workingModeRecordingAndLivecast");
            }
        } else {
            if (live != null && !live.isEmpty() && live.get("Status") != null && !live.get("Status").toString().equals("STOP")) {
                status = live;
                status.put("workingMode", "workingModeLiveCasting");
            } else {
                status = record;
                status.put("workingMode", "workingModeRecording");
            }
        }
        logger.debug("查询录制状态 ip为：" + ip + ",ext:" + ext + ",status:" + status);
        return status;
    }

    /**
     * 获取录制信息
     *
     * @param ip  ip地址
     * @param ext 预留字段，目前未用到
     * @return
     */

    public final Map<String, Object> getRecording(String ip, String ext) {
        try {
            Map<String, Object> res = (Map<String, Object>) DeviceManager.invoke(ip, new OperationRequestGetRecordingStatus(ext));
            logger.debug("获取录制信息ip为：" + ip + ",ext：" + ext + ",信息为：" + res.toString());
            return res;
        } catch (Exception e) {
            logger.error("获取录制信息异常ip为：" + ip + ",ext：" + ext, e);
//            logger.error(e.getMessage());
            Map<String, Object> result = new HashMap<>();
            result.put("Status", "STOP");
            result.put("Duration", "");
            result.put("LocalFTPPath", "");
            result.put("NASPath", "");
            return result;
        }
    }

    /**
     * 获取直播
     * Status
     * Duration
     * Adress
     * Protocol
     */
    public Map<String, Object> getLive(String ip, String ext) {
        try {
            Map<String, Object> res = (Map<String, Object>) DeviceManager.invoke(ip, new OperationRequestGetStreamingStatus(ext));
            logger.debug("getLive异常ip为：" + ip + ",ext:" + ext + ",getLive为:" + res.toString());
            return res;
        } catch (Exception e) {
            logger.error("getLive异常ip为：" + ip + ",ext:" + ext, e);
            return Collections.EMPTY_MAP;
        }

    }


    /**
     * 调用设备通信进行字幕查询
     * 调用请求：OperationRequestGetCaption
     * 返回参数为hashtable
     * Key：CaptionPath    Value：string，字幕存储的相对路径。(/NVRSettings/Captions/***.***)
     * Key：Location		Value：string，位置代码，该字符串在GlobalDefine可以调用。
     */
    public final Map<String, Object> getCaption(String ip) {
        Map<String, Object> re_value = new HashMap<>();
        try {
            re_value = (Map<String, Object>) DeviceManager.invoke(ip, new OperationRequestGetCaption());
            logger.debug("获取字幕ip为：" + ip + "字幕为：" + re_value.toString());
        } catch (Exception e) {
            logger.error("获取字幕异常ip为：" + ip, e);
        }
        return re_value;
    }

    /**
     * 片头查询
     * 返回的参数为
     * string，片头存储的相对路径。(/NVRSettings/ VideoMaterial /***.***)
     *
     * @param ip
     * @return
     */
    public final Map<String, Object> getTitleVideo(String ip, String ext) {
        Map<String, Object> re_value = new HashMap<>();
        try {
            re_value = (Map<String, Object>) DeviceManager.invoke(ip, new OperationRequestGetTitleVideo(ext));
            logger.debug("获取片头ip为：" + ip + ",ext:" + ext + "片头为：" + re_value.toString());
        } catch (Exception e) {
            logger.error("获取片头异常ip为：" + ip + ",ext:" + ext, e);
//            logger.error(e.getMessage(), e);
        }
        return  re_value;
    }

    /**
     * 片尾查询
     *
     * @param ip
     * @return
     */
    public final Map<String, Object> getEndingVideo(String ip, String ext) {
        Map<String, Object> re_value = new HashMap<>();
        try {
            re_value = (Map<String, Object>) DeviceManager.invoke(ip, new OperationRequestGetEndingVideo(ext));
            logger.debug("获取片尾ip为：" + ip + ",ext:" + ext + "片尾为：" + re_value.toString());
        } catch (Exception e) {
            logger.error("获取片尾异常ip为：" + ip + ",ext:" + ext, e);
//            logger.error(e.getMessage(), e);
        }
        return re_value;
    }

    /**
     * 查询课程信息，调用设备通信进行查询。
     * 调用请求：OperationRequestGetCourseInfo
     * 返回参数为hashtable
     * Key：CourseTitle    Value：string，标题
     * Key：MainTitle    Value：string，主讲标题
     * Key：ClassName    Value：tring，年级
     * Key：SubjectName    Value：string，科目
     * Key：Teacher    Value：string，主讲人
     * Key：School    Value：string，学校
     */
    public final Map<String, String> getCourseInfo(String ip, String ext) {
        Map<String, String> re_value = new HashMap<>();
        try {
            re_value = (Map<String, String>) DeviceManager.invoke(ip, new OperationRequestGetCourseInfo(ext));
            logger.debug("查询课程信息ip为：" + ip + ",ext:" + ext + ",课程信息为：" + re_value.toString());
        } catch (Exception e) {
            logger.error("查询课程信息异常ip为：" + ip + ",ext:" + ext, e);
        }
        return re_value;
    }

    /**
     * 获取导播策略
     * 导播界面开启时，调用设备通信进行导播策略的查询。当状态为自动控制模式时候，分屏操作和云台操作禁用。
     * string，导播模式，该字符串在GlobalDefine可以调用。
     * @param ip
     * @return
     */
    public final Map<String, String> getDirectMode(String ip, String ext) {
        Map<String, String> re_value = new HashMap<>();
        try {
            re_value = (Map<String, String>) DeviceManager.invoke(ip, new OperationRequestGetDirectMode(ext));
            logger.debug("查询导播策略ip为：" + ip + ",ext:" + ext + ",导播策略为：" + re_value);
        } catch (Exception e) {
            logger.error("查询导播策略异常ip为：" + ip + ",ext:" + ext, e);
        }
        return re_value;
    }

    /**
     * @param ip String NVR的ip
     * @desc 开始录制(同步)
     */
    public int startRecord(String ip, String referenceToken, String recordType, String ext) {
        Integer type = Integer.parseInt(recordType);
        try {
            logger.debug("开始录制ip为：" + ip + ",ext:" + ext + ",referenceToken:" + referenceToken + ",recordType:" + type);
            String token = "";
            if (referenceToken != null && !referenceToken.trim().equals("")) {
                //先查询镜头是否在录制
                Map<String, Object> status = (Map<String, Object>) this.getRecordingStatus(ip, ext);
                if (status == null || status.size() == 0) {
                    return 0;
                } else if (status.get("Status").equals("REC") || status.get("Status").equals("PAUSE")) {
                    return 1;//已经开始录制
                }
                String[] tokenArray = referenceToken.split(",");
                if (tokenArray.length > 0) {
                    List nasToken = new ArrayList();
                    List deviceToken = new ArrayList();
                    for (int i = 0; i < tokenArray.length; i++) {
                        if (tokenArray[i].trim().equals("")) continue;
                        token = tokenArray[i].trim();
                        if (type == 0) { //简易录播
                            nasToken.add(token);//获取nasToken
                        } else if (type == 1) {//精品录播在本地存储
                            deviceToken.add(token);
                        }
                    }
                    OperationRequestStartRecording startRecord = new OperationRequestStartRecording(deviceToken, nasToken, ext);
                    Map<String, Object> res = (Map<String, Object>) DeviceManager.invoke(ip, startRecord);
                    if (res.get("Code").toString().equals("0")) {
                        int re_value = 0;
                        if (res.get("Extension") != null && !res.get("Extension").equals("")) {
                            String extension = res.get("Extension").toString();
                            if (extension.contains("NAS server error")) {
                                re_value = 2;
                            } else if (extension.contains("NAS no space")) {
                                re_value = 3;
                            } else if (extension.contains("USB no space")) {
                                re_value = 4;
                            }
                            re_value = 1;
                        }
                        return re_value;
                    } else if (res.get("Code").toString().equals("1")) {
                        return 1;//失败
                    } else {
                        return -3;//内部异常
                    }
                }
                return -1;//token错误
            }
            return -1;//获取镜头token错误
        } catch (Exception e) {
            logger.error("开始录制异常ip为：" + ip + ",ext:" + ext, e);
//            logger.error(e.getMessage(), e);
            return -2;//设备服务异常
        }
    }

    /**
     * @param ip
     * @desc 停止录制(同步)
     */
    public int stopRecord(String ip) {
        try {
            OperationRequestStopRecording stopRecord = new OperationRequestStopRecording();
            //String[] token = this.getToken(ip);
            Map<String, Object> res = (Map<String, Object>) DeviceManager.invoke(ip, stopRecord);
//            if (res.get("Duration") != null && Integer.parseInt(res.get("Duration").toString()) != 0) {
//                return 1;
//            }
            logger.debug("停止录制ip为：" + ip);
            if (!res.get("Code").toString().equals("1")) {
                return 0;
            } else {
                return 1;
            }
        } catch (Exception e) {
            logger.error("停止录制异常ip为：" + ip, e);
            return -1;
        }
    }

//    /**
//     * 获取镜头主码流token
//     *
//     * @param hostid
//     * @param device_name 电影模式
//     * @return
//     */
//    public String getMainTokenByHostid(int hostid, String device_name) {
//        try {
//            String token = HostDao.INSTANCE.getMainTokenByHostid(hostid, device_name);
//            //String deviceToken[] = {token};
//            return token;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        }
//    }



    /**
     * 获取某个录播主机所有计划
     *
     * @param ip
     * @return
     */
    public List<Map<String, Object>> getAllPlan(String ip, String ext) {
        try {
            OperationRequestGetRecordTasks getRecordTasks = new OperationRequestGetRecordTasks(ext);
            RecordTask[] res = (RecordTask[]) DeviceManager.invoke(ip, getRecordTasks);
            List<Map<String, Object>> list = new ArrayList<>();
            for (int i = 0; i < res.length; i++) {
                Map map = new HashMap();
                map.put("className", res[i].getClassName());
                map.put("courseTitle", res[i].getCourseTitle());
                map.put("endTime", res[i].getEndTime());
                map.put("extension", res[i].getExtension());
                map.put("name", res[i].getName());
                map.put("localMedias", res[i].getLocalMedias());
                map.put("mainTitle", res[i].getMainTitle());
                map.put("NASMedias", res[i].getNASMedias());
                map.put("school", res[i].getSchool());
                map.put("startTime", res[i].getStartTime());
                map.put("streamingAddress", res[i].getStreamingAddress());
                map.put("streamingProtocol", res[i].getStreamingProtocol());
                map.put("subjectName", res[i].getSubjectName());
                map.put("teacher", res[i].getTeacher());
                map.put("weekDay", res[i].getWeekDay());
                list.add(map);
            }
            logger.debug("获取所有的录像计划ip为：" + ip + ",ext:" + ext + ",录像计划为：" + list.toString());
            return list;
        } catch (Exception e) {
            logger.error("获取所有的录像计划异常ip为：" + ip + ",ext:" + ext, e);
            return new ArrayList<>();
        }
    }

    /**
     * 添加录像计划
     * @param hostId 设备id
     * @param referenceToken  主码流的token
     * @param timeplan_id     数据库表的录像计划ID
     * @param ext             未用到
     * @param week_id         0表示临时录像计划，1表示星期一，.......，7表示星期日
     * @param startTime       临时录像计划传带日期的时间格式，录像计划只传时间，开始时间 “HH:MM”形式的二十四小时制时间格式
     * @param stopTime        临时录像计划传带日期的时间格式，录像计划只传时间，结束时间 “HH:MM”形式的二十四小时制时间格式
     * @param protocol        直播使用的协议类型
     * @param streamimgAddres 直播地址
     * @param courseTitle     标题
     * @param mainTitle       主讲标题
     * @param subjectName     科目
     * @param school          学校
     * @param teacher         主讲人
     * @param extension       extension是扩展字段，目前没有实际意义
     * @return
     */
    public int addPlanCommand(String hostId, String referenceToken, String timeplan_id, String ext, String week_id, String startTime, String stopTime, String protocol, String streamimgAddres, String courseTitle, String mainTitle, String subjectName,
                              String school, String teacher, String extension){
        int id = Integer.parseInt(hostId);
        Integer res = addPlan(id, referenceToken, timeplan_id, ext, week_id, startTime, stopTime, protocol, streamimgAddres, courseTitle, mainTitle, subjectName,
                school, teacher, extension);
        return res;
    }
    /**
     *设置所有录像计划
     * @param recordtasks 所有录像计划
     * @return
     */
    public int setPlanCommand(String recordtasks){
        JSONArray jsonArray = JSONArray.fromObject(recordtasks);
        List<Map<String, String>> plans = (List<Map<String, String>>) JsonUtil.jsonToList(jsonArray);
        Integer res = addPlan(plans);
        return res;
    }

    /**
     * 为录像任务设置参数值
     *
     * @param id              timeplan ID
     * @param week_id         周几
     * @param startTime       开始时间  xx:xx
     * @param stopTime        结束时间   xx:xx
     * @param NASMedias       需要在NAS上录制的MediaProfileToken数组  （镜头token ）
     * @param LocalMedias     需要在本地录制的MediaProfileToken数组  （镜头token ）
     * @param protocol        直播使用的协议类型
     * @param streamimgAddres 直播地址
     * @param courseTitle     标题
     * @param mainTitle       主讲标题
     * @param className       年级
     * @param subjectName     科目
     * @param school          学校
     * @param teacher         主讲人
     * @return
     */
    public RecordTask setRecordTaskParam(String id, String week_id, String startTime, String stopTime, String[] NASMedias, String[] LocalMedias, String protocol,
                                         String streamimgAddres, String courseTitle, String mainTitle, String className, String subjectName,
                                         String school, String teacher, String extension) {
        RecordTask recordTask = new RecordTask();

        recordTask.setName(id);
        recordTask.setWeekDay(week_id);
        recordTask.setStartTime(startTime);
        recordTask.setEndTime(stopTime);
        List arrlist = new ArrayList();
        for (int i = 0; i < NASMedias.length; i++) {
            arrlist.add(NASMedias[i]);
        }
        recordTask.setNASMedias(arrlist);//需要在NAS上录制的MediaProfileToken数组
        List localList = new ArrayList();
        for (int i = 0; i < LocalMedias.length; i++) {
            localList.add(LocalMedias[i]);
        }
        recordTask.setLocalMedias(localList);//需要在本地录制的MediaProfileToken数组
        recordTask.setStreamingProtocol(protocol);//直播使用的协议类型
        recordTask.setStreamingAddress(streamimgAddres);//直播地址
        recordTask.setCourseTitle(courseTitle);
        recordTask.setMainTitle(mainTitle);
        recordTask.setClassName(className);
        recordTask.setSubjectName(subjectName);
        recordTask.setSchool(school);
        recordTask.setTeacher(teacher);
        recordTask.setExtension(extension);
        return recordTask;
    }

    /**
     * 获取当前layout
     *
     * @param ip  ip地址
     * @param ext 没有实际意义
     * @return
     */
    public String getCurrentLayout(String ip, String ext) {
        try {
            Map<String, String> layout = (Map<String, String>) DeviceManager.invoke(ip, new OperationRequestGetLayout(ext));
            logger.debug("获取当前的layout异常ip为：" + ip + ",ext:" + ext + ",layout:" + layout.toString());
            if (layout != null && layout.size() > 0) {
                return layout.get("Name");
            } else {
                return "";
            }
        } catch (Exception e) {
            logger.error("获取当前的layout异常ip为：" + ip + ",ext:" + ext, e);
            return "";
        }
    }


    /**
     * 获取版式
     * @param ip
     * @param ext
     * @return
     */
    public List<Map> getLayout(String ip, String ext) {
        try {
            List list = new ArrayList();
            List<Layout> layouts = (List<Layout>) DeviceManager.invoke(ip, new OperationRequestGetLayoutConfigurations(ext));
            if (layouts.size() > 0) {
                for (int i = 0; i < layouts.size(); i++) {
                    Map<String, Object> map = new HashMap();
                    map.put("name", layouts.get(i).getName());
                    List<LayoutWindow> layoutWindow = (List<LayoutWindow>) layouts.get(i).getWindows();
                    List windowList = new ArrayList();
                    for (int j = 0; j < layoutWindow.size(); j++) {
                        Map<String, Object> windowMap = new HashMap();
                        windowMap.put("height", layoutWindow.get(j).getHeight());
                        windowMap.put("left", layoutWindow.get(j).getLeft());
                        windowMap.put("top", layoutWindow.get(j).getTop());
                        windowMap.put("videoSource", layoutWindow.get(j).getVideoSource());
                        windowMap.put("width", layoutWindow.get(j).getWidth());
                        windowList.add(windowMap);
                    }
                    map.put("windows", windowList);
                    list.add(map);
                }
            }
            logger.debug("获取layout异常ip为：" + ip + ",ext:" + ext + ",layouts为:" + list.toString());
            return list;
        } catch (Exception e) {
            logger.error("获取layout异常ip为：" + ip + ",ext:" + ext, e);
            return new ArrayList<>();
        }
    }


    /**
     * 设置课程信息
     *
     * @param ip          ip地址
     * @param courseTitle 标题
     * @param mainTitle   主讲标题
     * @param className   年级名称
     * @param subjectName 科目
     * @param teacher     主讲人
     * @param school      学校
     * @param ext         预留字段，目前未用到
     * @return
     */
    public Boolean setCourseInfo(String ip, String courseTitle, String mainTitle, String className, String subjectName, String teacher, String school, String ext) {
        try {
            OperationRequestSetCourseInfo operationRequestSetCourseInfo = new OperationRequestSetCourseInfo(courseTitle, mainTitle, className, subjectName, teacher, school, ext);
            DeviceManager.invoke(ip, operationRequestSetCourseInfo);
            logger.debug("设置课程信息ip为：" + ip + ",ext:" + ext + ",courseTitle:" + courseTitle + ",mainTitle:" + mainTitle + ",className:" + className + ",subjectName:" + subjectName + ",teacher:" + teacher + ",school:" + school);
            return true;
        } catch (Exception e) {
            logger.error("设置课程信息异常ip为：" + ip + ",ext:" + ext + ",courseTitle:" + courseTitle + ",mainTitle:" + mainTitle + ",className:" + className + ",subjectName:" + subjectName + ",teacher:" + teacher + ",school:" + school, e);
            return false;
        }
    }


    /**
     * 获取多媒体服务器token
     *
     * @param ip
     * @return
     */
    public List<String> getAllMediaToken(String ip) {
        try {
            List<String> res = (List<String>) DeviceManager.invoke(ip, new OperationRequestGetAllMediaToken());
            logger.debug("获取多媒体服务器tokenip为：" + ip + ",结果为：" + res.toString());
            return res;
        } catch (Exception e) {
            logger.error("获取多媒体服务器token异常ip为：" + ip, e);
            return Collections.EMPTY_LIST;
        }

    }

    /**
     * 获取url
     *
     * @param ip
     * @param cameraToken 镜头token
     * @return
     */
    public String getCameraUrl(String ip, String cameraToken) {
        OperationRequestGetStreamingURL operationRequestGetStreamingURL = new OperationRequestGetStreamingURL(cameraToken);
        try {
            Object result = DeviceManager.invoke(ip, operationRequestGetStreamingURL);
            logger.debug("获取cameraurl,ip为：" + ip + ",cameraToken:" + cameraToken + "结果为：" + result == null ? "空" : result.toString());
            if (result != null) return result.toString();
        } catch (Exception e) {
            logger.error("获取cameraurl异常ip为：" + ip + ",cameraToken:" + cameraToken, e);
        }
        return "";
    }

    /**
     * 开始直播
     *
     * @param ip
     * @param address     直播的网络地址
     * @param protocol 直播使用的协议类型
     * @return
     */
    public int startStreaming(String ip, String address, String protocol) {
        try {
            OperationRequestStartStreaming operationRequestStartStreaming = new OperationRequestStartStreaming(address, protocol);
            Map<String, Object> res = (Map<String, Object>) DeviceManager.invoke(ip, operationRequestStartStreaming);
            logger.debug("开始直播ip为：" + ip + ",protocol:" + protocol + ",addr:" + address + "结果为:" + res.toString());
            if (!res.get("Code").toString().equals("1")) {
                return 0;
            } else {
                return 1;
            }
        } catch (Exception e) {
            logger.error("开始直播异常ip为：" + ip + ",protocol:" + protocol + ",addr:" + address, e);
            return -1;
        }
    }

    /**
     *重新开始直播
     * @param ip
     * @param address     直播的网络地址
     * @param protocol 直播使用的协议类型
     * @return
     */
    public int restartStreaming(String ip, String address, String protocol){
        return startStreaming(ip, address, protocol);
    }
    /**
     * 停止直播
     *
     * @param ip
     * @return
     */
    public int stopStreaming(String ip) {
        try {
            OperationRequestStopStreaming operationRequestStopStreaming = new OperationRequestStopStreaming();
            Map<String, Object> duration = (Map<String, Object>) DeviceManager.invoke(ip, operationRequestStopStreaming);
            logger.debug("停止直播ip为：" + ip + ",结果为:" + duration.toString());
            if (!duration.get("Code").toString().equals("1")) {
                return 0;
            } else {
                return 1;
            }
        } catch (Exception e) {
            logger.error("停止直播异常ip为：" + ip, e);
            return -1;
        }
    }

    /**
     * 暂停直播
     *
     * @param ip
     * @param ext 没有实际意义
     * @return
     */
    public int pauseStreaming(String ip, String ext) {
        try {
            OperationRequestStopStreaming operationRequestPauseStreaming = new OperationRequestStopStreaming();
            Map<String, Object> duration = (Map<String, Object>) DeviceManager.invoke(ip, operationRequestPauseStreaming);
            logger.debug("暂停直播ip为：" + ip + ",结果为:" + duration);
            if (!duration.get("Code").toString().equals("1")) {
                return 0;
            } else {
                return 1;
            }
        } catch (Exception e) {
            logger.error("暂停直播异常ip为：" + ip, e);
            return 0;
        }
    }

    /**
     * 暂停录制
     *
     * @param ip
     * @param ext
     * @return
     */
    public int pauseRecording(String ip, String ext) {
        try {
            OperationRequestPauseRecording operationRequestPauseRecording = new OperationRequestPauseRecording(ext);
            Map<String, Object> duration = (Map<String, Object>) DeviceManager.invoke(ip, operationRequestPauseRecording);
            logger.debug("暂停录制ip为：" + ip + ",ext:" + ext + ",结果为" + duration.toString());
            if (!duration.get("Code").toString().equals("1")) {
                return 0;
            } else {
                return 1;
            }
        } catch (Exception e) {
            logger.error("暂停录制异常ip为：" + ip + ",ext:" + ext, e);
            return 0;
        }
    }


    /**
     * 设置片尾
     *
     * @param ip
     * @param musPath  音乐文件路径
     * @param picPath  图片文件路径
     * @param duration 时长
     * @param ext      扩展字段
     * @param fileName 文件名称
     * @return
     */
    public Boolean setEndingVideo(String ip, String musPath, String picPath, String duration, String ext, String fileName) {
        int durationTime = Integer.parseInt(duration);//时长
        try {
            OperationRequestSetEndingVideo operationRequestSetEndingVideo = new OperationRequestSetEndingVideo(picPath, musPath, ext, durationTime, fileName);
            DeviceManager.invoke(ip, operationRequestSetEndingVideo);
            logger.debug("设置片尾ip为：" + ip + ",ext:" + ext + ",musPath:" + musPath + ",picPath:" + picPath + ",duration:" + durationTime + ",fileName:" + fileName);
            return true;
        } catch (Exception e) {
            logger.error("设置片尾异常ip为：" + ip + ",ext:" + ext + ",musPath:" + musPath + ",picPath:" + picPath + ",duration:" + durationTime + ",fileName:" + fileName, e);
            return false;
        }
    }

    /**
     * 设置片头
     *
     * @param ip
     * @param musPath  音乐文件路径
     * @param picPath  图片文件路径
     * @param duration 时长
     * @param ext      扩展字段
     * @param fileName 文件名称
     * @return
     */
    public Boolean setTitleVideo(String ip, String musPath, String picPath, String duration, String ext, String fileName) {
        int durationTime = Integer.parseInt(duration);//时长
        try {
            OperationRequestSetTitleVideo operationRequestSetTitleVideo = new OperationRequestSetTitleVideo(picPath, musPath, durationTime, fileName, ext);
            DeviceManager.invoke(ip, operationRequestSetTitleVideo);
            logger.debug("设置片头ip为：" + ip + ",ext:" + ext + ",musPath:" + musPath + ",picPath:" + picPath + ",duration:" + durationTime + ",fileName:" + fileName);

            return true;
        } catch (Exception e) {
            logger.error("设置片头异常ip为：" + ip + ",ext:" + ext + ",musPath:" + musPath + ",picPath:" + picPath + ",duration:" + duration + ",fileName:" + fileName, e);
            return false;
        }
    }

    /**
     * 切换布局
     *
     * @param ip
     * @param layoutName 布局名称（分屏名称）
     * @return
     */
    public Boolean setLayout(String ip, String layoutName) {
        try {
            OperationRequestSetLayout operationRequestSetLayout = new OperationRequestSetLayout(layoutName);
            DeviceManager.invoke(ip, operationRequestSetLayout);
            logger.debug("切换布局ip为：" + ip + ",layoutName:" + layoutName);
            return true;
        } catch (Exception e) {
            logger.error("切换布局异常ip为：" + ip + ",layoutName:" + layoutName, e);
            return false;
        }
    }

    /**
     * PTZ云台移动
     *
     * @param ip           Ip地址
     * @param x            云台移动的水平位置
     * @param y            云台移动的垂直位置
     * @param profileToken 镜头token
     * @param z            云台移动的纵深位置
     * @param timeout      设置的超时时间
     * @return
     */
    public Boolean PTZStartMove(String ip, String x, String y, String profileToken, String z, String timeout) {
        boolean re_value = false;
        float movex = Float.parseFloat(x);
        float movey = Float.parseFloat(y);
        long timeOut = Long.parseLong(timeout);
        try {
            float z_new = Float.parseFloat(z);
            OperationRequestPTZStartMove operationRequestPTZStartMove = new OperationRequestPTZStartMove(movex, movey, profileToken, z_new, timeOut);
            DeviceManager.invoke(ip, operationRequestPTZStartMove);
            logger.debug("PTZ云台移动ip为：" + ip + ",x:" + movex + ",y:" + movey + ",profileToken:" + profileToken + ",z:" + z + ",timeout:" + timeOut);
            re_value = true;
        } catch (Exception e) {
            logger.error("PTZ云台移动异常ip为：" + ip + ",x:" + movex + ",y:" + movey + ",profileToken:" + profileToken + ",z:" + z + ",timeout:" + timeOut, e);
        }
        return re_value;
    }

    /**
     * 云台停止移动
     *
     * @param ip
     * @param mediaToken 镜头token
     * @return
     */
    public Boolean PTZStopMove(String ip, String mediaToken) {
        boolean re_value = false;
        try {
            OperationRequestPTZStopMove operationRequestPTZStopMove = new OperationRequestPTZStopMove(mediaToken);
            DeviceManager.invoke(ip, operationRequestPTZStopMove);
            re_value = true;
        } catch (Exception e) {
            logger.error("云台停止移动异常ip为：" + ip + ",mediaToken:" + mediaToken, e);
        }
        return re_value;
    }

    /**
     * 获取Nas设置信息
     *
     * @param ip
     * @param ext 扩展字段 未用到
     * @return
     */
    public Map<String, String> getNasSettings(String ip, String ext) {
        Map<String,String> res = new HashMap<>();
        try {
            OperationRequestGetNasSettings operationRequestGetNasSettings = new OperationRequestGetNasSettings(ext);
             res = (Map<String, String>) DeviceManager.invoke(ip, operationRequestGetNasSettings);
            logger.debug("获取Nas设置信息ip为：" + ip + ",ext:" + ext + ",结果为:" + res.toString());
        } catch (Exception e) {
            logger.error("获取Nas设置信息异常ip为：" + ip + ",ext:" + ext, e);
        }
        return res;
    }

    /**
     * 设置nas
     *
     * @param ip
     * @param passWordNAS 密码
     * @param rootPathNAS 根路径
     * @param userIDNAS   用户名
     * @param savePath    保存路径
     * @return
     */
    public Boolean setNasSettings(String ip, String passWordNAS, String rootPathNAS, String userIDNAS, String savePath) {
        try {
            OperationRequestSetNasSettings operationRequestSetNasSettings = new OperationRequestSetNasSettings(rootPathNAS, savePath, userIDNAS, passWordNAS);
            DeviceManager.invoke(ip, operationRequestSetNasSettings);
            logger.debug("设置nasip为：" + ip + ",passWordNAS:" + passWordNAS + ",rootPathNAS:" + rootPathNAS + ",userIDNAS:" + userIDNAS + ",savePath:" + savePath);
            return true;
        } catch (Exception e) {
            logger.error("设置nas异常ip为：" + ip + ",passWordNAS:" + passWordNAS + ",rootPathNAS:" + rootPathNAS + ",userIDNAS:" + userIDNAS + ",savePath:" + savePath, e);
            return false;
        }
    }

    /**
     * 配置布局信息
     *
     * @param ip
     * @param deviceToken 镜头token
     * @param ext         扩展字段
     * @return
     */
    public Boolean setLayoutConfigurations(String ip, String deviceToken, String ext) {
        try {
            List<Layout> layouts = getLayouts(ip, ext);
            if (layouts.size() == 0) {
                throw new ProgramException("OperationRequestGetLayoutConfigurations method is null");
            }
            String currentLayout = getCurrentLayout(ip, ext);
            List<Layout> layoutsSet = new ArrayList<>();

            for (int i = 0; i < layouts.size(); i++) {
                if (layouts.get(i).getName().equals(currentLayout)) {
                    Layout layoutSet = new Layout();
                    String nameSet = layouts.get(i).getName();
                    layoutSet.setName(nameSet);
                    List<LayoutWindow> windowsSet = new ArrayList<>();
                    for (int k = 0; k < layouts.get(i).getWindows().size(); k++) {
                        if (k == 0) {
                            LayoutWindow windowSet = new LayoutWindow();
                            windowSet.setVideoSource(deviceToken);
                            windowSet.setHeight(layouts.get(i).getWindows().get(k).getHeight());
                            windowSet.setLeft(layouts.get(i).getWindows().get(k).getLeft());
                            windowSet.setTop(layouts.get(i).getWindows().get(k).getTop());
                            windowSet.setWidth(layouts.get(i).getWindows().get(k).getWidth());
                            windowsSet.add(windowSet);
                        } else {
                            LayoutWindow windowSet = new LayoutWindow();
                            String token = layouts.get(i).getWindows().get(k - 1).getVideoSource();
                            windowSet.setVideoSource(token);
                            windowSet.setHeight(layouts.get(i).getWindows().get(k).getHeight());
                            windowSet.setLeft(layouts.get(i).getWindows().get(k).getLeft());
                            windowSet.setTop(layouts.get(i).getWindows().get(k).getTop());
                            windowSet.setWidth(layouts.get(i).getWindows().get(k).getWidth());
                            windowsSet.add(windowSet);
                        }
                    }
                    layoutSet.setWindows(windowsSet);
                    layoutsSet.add(layoutSet);
                } else {
                    Layout layoutSet = new Layout();
                    String nameSet = layouts.get(i).getName();
                    layoutSet.setName(nameSet);

                    List<LayoutWindow> windowsSet = new ArrayList<>();
                    for (int k = 0; k < layouts.get(i).getWindows().size(); k++) {
                        LayoutWindow windowSet = new LayoutWindow();
                        String token = layouts.get(i).getWindows().get(k).getVideoSource();
                        windowSet.setVideoSource(token);
                        windowSet.setHeight(layouts.get(i).getWindows().get(k).getHeight());
                        windowSet.setLeft(layouts.get(i).getWindows().get(k).getLeft());
                        windowSet.setTop(layouts.get(i).getWindows().get(k).getTop());
                        windowSet.setWidth(layouts.get(i).getWindows().get(k).getWidth());
                        windowsSet.add(windowSet);
                    }
                    layoutSet.setWindows(windowsSet);
                    layoutsSet.add(layoutSet);
                }
            }
            OperationRequestSetLayoutConfigurations operationRequestSetLayoutConfigurations = new OperationRequestSetLayoutConfigurations(layoutsSet);
            DeviceManager.invoke(ip, operationRequestSetLayoutConfigurations);
            logger.debug("配置布局信息ip为：" + ip + ",deviceToken:" + deviceToken + ",ext:" + ext);
            return true;
        } catch (Exception e) {
            logger.error("配置布局信息异常ip为：" + ip + ",deviceToken:" + deviceToken + ",ext:" + ext, e);
            return false;
        }
    }

    /**
     * 获取预置位
     *
     * @param ip
     * @param profileToken 镜头token
     * @return
     */
    public List<Map<String, String>> getPresets(String ip, String profileToken) {
        List<Map<String, String>> resList = new ArrayList<>();
        try {
            OperationRequestGetPresets operationRequestGetPresets = new OperationRequestGetPresets(profileToken);
            List<PTZPreset> list = (List<PTZPreset>) DeviceManager.invoke(ip, operationRequestGetPresets);
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    Map<String, String> map = new HashMap<>();
                    map.put("deviceToken", list.get(i).getDeviceToken());
                    map.put("name", list.get(i).getName());
                    map.put("token", list.get(i).getToken());
                    resList.add(map);
                }
            }
            logger.debug("获取预置位ip为：" + ip + ",profileToken:" + profileToken + ",结果为：" + resList.toString());
        } catch (Exception e) {
            logger.error("获取预置位异常ip为：" + ip + ",profileToken:" + profileToken, e);
        }
        return resList;
    }

    /**
     * 设置预置位
     *
     * @param ip
     * @param profileToken 镜头token
     * @param presetToken  预置位编号（如1，2，3，4，5等）
     * @return
     */
    public Boolean setPresets(String ip, String profileToken, String presetToken) {
        boolean re_value = false;
        try {
            OperationRequestGotoPreset operationRequestGotoPreset = new OperationRequestGotoPreset(profileToken, presetToken);
            DeviceManager.invoke(ip, operationRequestGotoPreset);
            logger.debug("设置预置位ip为：" + ip + ",profileToken:" + profileToken + ",presetToken:" + presetToken);
            re_value = true;
        } catch (Exception e) {
            logger.error("设置预置位异常ip为：" + ip + ",profileToken:" + profileToken + ",presetToken:" + presetToken, e);
        }
        return re_value;
    }

    /**
     * 视频打点
     *
     * @param ip
     * @param time    打点位置，数值为当前录制的秒数
     * @param keynote 打点内容
     * @param ext
     * @return
     */
    public String insertKeyNote(String ip, String time, String keynote, String ext) {
        String re_value = "";
        try {
            OperationRequestInsertKeyNote operationRequestInsertKeyNote = new OperationRequestInsertKeyNote(Integer.parseInt(time), ext, keynote);
            re_value = (String) DeviceManager.invoke(ip, operationRequestInsertKeyNote);
            logger.debug("视频打点异常ip为：" + ip + ",time:" + time + ",keynote:" + keynote + ",ext:" + ext + ",结果为:" + re_value);
        } catch (Exception e) {
            logger.error("视频打点异常ip为：" + ip + ",time:" + time + ",keynote:" + keynote + ",ext:" + ext, e);
        }
        return re_value;
    }

    /**
     * 继续开始录制
     *
     * @param ip  ip地址
     * @param ext 扩展字段
     * @return
     */
    public int restartRecording(String ip, String ext) {
        int flag = 1;
        try {
            OperationRequestPlayRecording operationRequestPlayRecording = new OperationRequestPlayRecording(ext);
            Map<String, Object> res = (Map<String, Object>) DeviceManager.invoke(ip, operationRequestPlayRecording);
            logger.debug("继续开始录制异常ip为：" + ip + ",ext:" + ext + ",结果为:" + res.toString());
            if (!res.get("Code").toString().equals("1")) {
                return 0;
            }
        } catch (Exception e) {
            logger.error("继续开始录制异常ip为：" + ip + ",ext:" + ext, e);
            flag = -1;
        }
        return flag;
    }

    /**
     * 设置多分屏
     *
     * @param ip
     * @param to       分屏中要替换的某个镜头（比如教师全景）
     * @param from     替换某个镜头的另一个镜头（比如学生全景）
     * @param layoutId 分屏数（比如要设置的是二分屏里的内容）
     * @param operate
     * @param ext
     * @return
     */
    public Boolean settingMutilScreen(String ip, String to, String from, String layoutId, String operate, String ext) {
        boolean re_value = false;
        try {
            List<Layout> layouts = getLayouts(ip, ext);
            for (Layout layout : layouts) {
                if (layout.getName().equals(layoutId)) {
                    List<LayoutWindow> layoutWindows = layout.getWindows();
                    if (operate.equals("select")) {
                        for (LayoutWindow layoutWindow : layoutWindows) {
                            if (layoutWindow.getVideoSource().equals(from)) {
                                layoutWindow.setVideoSource(to);
                                break;
                            }
                        }
                    } else {
                        for (LayoutWindow layoutWindow : layoutWindows) {
                            if (layoutWindow.getVideoSource().equals(to)) {
                                layoutWindow.setVideoSource(from);
                                continue;
                            } else if (layoutWindow.getVideoSource().equals(from)) {
                                layoutWindow.setVideoSource(to);
                                continue;
                            }
                        }

                    }
                }
            }
            OperationRequestSetLayoutConfigurations operationRequestSetLayoutConfigurations = new OperationRequestSetLayoutConfigurations(layouts);
            DeviceManager.invoke(ip, operationRequestSetLayoutConfigurations);
            logger.debug("设置多分屏ip为：" + ip + ",ext:" + ext + ",to:" + to + ",from:" + from + ",layoutId:" + layoutId + ",operate:" + operate);
            re_value = true;
        } catch (Exception e) {
            logger.error("设置多分屏异常ip为：" + ip + ",ext:" + ext + ",to:" + to + ",from:" + from + ",layoutId:" + layoutId + ",operate:" + operate, e);
        }
        return re_value;
    }

    /**
     * 移除片头
     *
     * @param ip  ip地址
     * @param ext 扩展字段
     * @return
     */
    public Boolean removeTitleVideo(String ip, String ext) {
        boolean re_value = false;
        try {
            OperationRequestRemoveTitleVideo operationRequestRemoveTitleVideo = new OperationRequestRemoveTitleVideo(ext);
            DeviceManager.invoke(ip, operationRequestRemoveTitleVideo);
            logger.debug("移除片头ip为：" + ip + ",ext:" + ext);
            re_value = true;
        } catch (Exception e) {
            logger.error("移除片头异常ip为：" + ip + ",ext:" + ext, e);
        }
        return re_value;
    }

    /**
     * 移除片尾
     *
     * @param ip  ip地址
     * @param ext 扩展字段
     * @return
     */
    public Boolean removeEndVideo(String ip, String ext) {
        boolean re_value = false;
        try {
            OperationRequestRemoveEndingVideo operationRequestRemoveEndingVideo = new OperationRequestRemoveEndingVideo(ext);
            DeviceManager.invoke(ip, operationRequestRemoveEndingVideo);
            logger.debug("移除片尾ip为：" + ip + ",ext:" + ext);
            re_value = true;
        } catch (Exception e) {
            logger.error("移除片尾异常ip为：" + ip + ",ext:" + ext, e);
        }
        return re_value;
    }

    /**
     * 删除计划
     *
     * @param hostId      设备id
     * @param timeplan_id 数据库表的录像计划ID
     * @param ext         扩展字段
     * @return
     */
    public int delPlan(String hostId, String timeplan_id, String ext) {
        int flag = 0;//设备离线
        List<RecordTask> list = new ArrayList<>();
        Map map = DeviceDao.INSTANCE.getHostInfoById(hostId);
        String ip = "";
        if (map != null && map.size() > 0) {
            ip = map.get("host_ipaddr").toString();
        } else {
            logger.debug("删除计划失败--无设备信息,ip为：" + ip + ",ext:" + ext + ",hostId:" + hostId + ",timeplan_id:" + timeplan_id + ",结果:-1");
            flag = -1;
        }
        boolean isOnline = Host.INSTANCE.isOnline(ip);
        if (!isOnline) {
            logger.debug("删除计划失败--设备离线,ip为：" + ip + ",ext:" + ext + ",hostId:" + hostId + ",timeplan_id:" + timeplan_id + ",结果:0");
        }
        list = getAllPlanes(ip, ext);//获取所有计划

        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getName().equals(timeplan_id)) {
                    list.remove(i);
                }
            }
        }
        flag = addVideoPlan(ip, list);//1为成功 -1为失败
        logger.debug("删除计划ip为：" + ip + ",ext:" + ext + ",hostId:" + hostId + ",timeplan_id:" + timeplan_id + ",结果:" + flag);
        return flag;
    }

    /**
     * 清空录像计划
     *
     * @param hostId 设备id
     * @return
     */
    public int delAllPlan(String hostId) {
        int flag = 0;//设备离线
        Map map = DeviceDao.INSTANCE.getHostInfoById(hostId);
        String ip = "";
        if (map != null && map.size() > 0) {
            ip = map.get("host_ipaddr").toString();
        } else {
            logger.debug("删除计划失败--无设备信息,ip为：" + ip + ",结果:-1");
            flag = -1;
        }
        boolean isOnline = Host.INSTANCE.isOnline(ip);
        if (!isOnline) {
            logger.debug("删除计划失败--设备离线,ip为：" + ip + ",结果:0");
        }
        List<RecordTask> list = new ArrayList<>();
        flag = addVideoPlan(ip, list);//1为成功 -1为失败
        logger.debug("删除计划失败--设备离线,ip为：" + ip + ",结果:" + flag);
        return flag;
    }

    /**
     * 清除临时计划
     *
     * @param hostId 设备id
     * @return
     */
    public int delTempPlan(String hostId) {
        Map map = DeviceDao.INSTANCE.getHostInfoById(hostId);
        String ip = "";
        if (map != null && map.size() > 0) {
            ip = map.get("host_ipaddr").toString();
        } else {
            logger.debug("删除临时计划失败--无设备信息,ip为：" + ip + ",结果:-1");
            return -1;
        }
        boolean isOnline = Host.INSTANCE.isOnline(ip);
        if (!isOnline) {
            logger.debug("删除临时计划失败--设备离线,ip为：" + ip + ",结果:0");
            return 0;//设备离线
        }

        RecordTask recordTask = new RecordTask();
        recordTask.setName("");
        recordTask.setWeekDay("0");
        recordTask.setStartTime("");
        recordTask.setEndTime("");

        List<RecordTask> list = new ArrayList<>();
        list.add(recordTask);
        int flag = addVideoPlan(ip, list);//1为成功 -1为失败
        logger.debug("删除临时计划计划失败--设备离线,ip为：" + ip + ",结果:" + flag);
        return flag;
    }


    /**
     * 设置分辨率，码率
     *
     * @param ip         ip
     * @param width      宽
     * @param height     高
     * @param bitrate    码率值
     * @param mediaToken 镜头token
     * @return
     */
    public Boolean setVideoRecorderInfo(String ip, String width, String height, String bitrate, String mediaToken) {
        boolean re_value = false;
        int bitRate = Integer.valueOf(bitrate);
        try {
            OperationRequestSetVideoEncoderConfig operationRequestSetVideoEncoderConfig = new OperationRequestSetVideoEncoderConfig(mediaToken, height, width, bitRate);
            DeviceManager.startInvoke(ip, "", operationRequestSetVideoEncoderConfig, new DefaultResponseHandler());
            logger.debug("设置分辨率，码率异常,ip为:" + ip + ",width:" + width + ",height:" + height + ",bitrate:" + bitRate + ",mediaToken:" + mediaToken + ",serverip:");
            re_value = true;
        } catch (Exception e) {
            logger.error("设置分辨率，码率异常,ip为:" + ip + ",width:" + width + ",height:" + height + ",bitrate:" + bitRate + ",mediaToken:" + mediaToken + ",serverip:", e);
        }
        return re_value;
    }

    /**
     * 设置扩展方法
     *
     * @param ip
     * @param ethName
     * @param value
     * @param ext
     * @return
     */
    public Boolean setLubocfg(String ip, String ethName, String value, String ext) {
//        Boolean re_value = false;
//        try {
//            DeviceManager.invoke(ip, new OperationRequestSetLuboCfg(ethName, value, ext));
//            logger.debug("设置扩展方法ip为：" + ip + ",ext:" + ext);
//            re_value = true;
//        } catch (Exception e) {
//            logger.error("设置扩展方法ip为：" + ip + ",ext:" + ext, e);
//        }
//
//        return re_value;
        Boolean re_value = false;
        try {
            if ("PowerOn".equals(ext)) {
                OperationRequestSetLuboCfg setLuboCfg = new OperationRequestSetLuboCfg(ethName, value, ext);
                setLuboCfg.setExt("PowerOn");
                DeviceManager.invoke(ip, setLuboCfg);
            } else {
                DeviceManager.invoke(ip, new OperationRequestSetLuboCfg(ethName, value, ext));
            }
//            DeviceManager.invoke(ip, new OperationRequestSetLuboCfg(ethName, value, ext));
            logger.debug("设置扩展方法ip为：" + ip + ",ext:" + ext);
            re_value = true;
        } catch (Exception e) {
            logger.error("设置扩展方法ip为：" + ip + ",ext:" + ext, e);
        }

        return re_value;
    }

    /**
     * 查询设备亮度
     *
     * @param ip          设备ip
     * @param videoSource token
     * @return
     */
    public Map<String, String> getBrightness(String ip, String videoSource) {
        Map re_value = Collections.EMPTY_MAP;
        try {
            Map<String, String> res = (Map<String, String>) DeviceManager.invoke(ip, new HImagingGetBrightness(videoSource));
            if (res != null) {
                re_value = res;
            }
            logger.debug("获取设备亮度为" + res == null ? "空" : res.toString());
        } catch (Exception e) {
            logger.error("获取设备亮度异常ip为" + ip, e);
        }
        return re_value;
    }

    /**
     * 设置设备亮度
     *
     * @param ip          设备ip
     * @param videoSource token标识
     * @param brightness  亮度值
     * @return
     */
    public Map<String, String> setBrightness(String ip, String videoSource, String brightness) {
        Map re_value = Collections.EMPTY_MAP;
        try {
            Map<String, String> res = (Map<String, String>) DeviceManager.invoke(ip, new HImagingSetBrightness(videoSource, brightness));
            if (res != null) {
                re_value = res;
            }
            logger.debug("设置设备亮度为" + res == null ? "空" : res.toString());
        } catch (Exception e) {
            logger.error("设置设备亮度异常ip为" + ip, e);
        }
        return re_value;
    }


    /**
     * 设置云台速度
     *
     * @param ip          设备ip
     * @param videoSource token标识
     * @param speed
     * @return
     */
    public Map<String, String> setPTZSpeed(String ip, String videoSource, String speed) {
        Map re_value = Collections.EMPTY_MAP;
        try {
            Map<String, String> res = (Map<String, String>) DeviceManager.invoke(ip, new HImagingSetPTZSpeed(videoSource, speed));
            if (res != null) {
                re_value = res;
            }
            logger.debug("设置云台速度为" + res == null ? "空" : res.toString());
        } catch (Exception e) {
            logger.error("设置云台速度异常ip为" + ip, e);
        }
        return re_value;
    }

    /**
     * 查询云台速度
     *
     * @param ip          设备ip
     * @param videoSource token标识
     * @return
     */
    public Map<String, String> getPTZSpeed(String ip, String videoSource) {
        Map re_value = Collections.EMPTY_MAP;
        try {
            Map<String, String> res = (Map<String, String>) DeviceManager.invoke(ip, new HImagingGetPTZSpeed(videoSource));
            if (res != null) {
                re_value = res;
            }
            logger.debug("查询云台速度为" + res == null ? "空" : res.toString());
        } catch (Exception e) {
            logger.error("查询云台速度异常ip为" + ip, e);
        }
        return re_value;
    }

    /**
     * 通过设备id查询镜头信息
     *
     * @param hostId 设备id
     * @return 返回该设备id的所有镜头信息列表
     */
    public List<Map<String, String>> getCameraInfoById(String hostId) {
        List<Map<String, String>> listInfo = new ArrayList<>();
        try {
            listInfo = HostDeviceDao.INSTANCE.getDeviceInfo(hostId);
            logger.debug("查询镜头信息为：" + listInfo);
        } catch (Exception e) {
            logger.error("查询镜头信息失败", e);
        }
        return listInfo;
    }

    /**
     * @param ip   设备ip
     * @param hostName 设备名称
     * @return
     */
    public Boolean updateHhrecHostName(String ip, String hostName) {
        boolean re_value = false;
        try {
                re_value = DeviceDao.INSTANCE.updateHostName(ip, hostName);
                logger.debug("修改设备" + ip + "的名称为" + hostName + ":" + re_value);
        } catch (Exception e) {
            logger.error("修改设备名称异常", e);
        }
        return re_value;

    }

    //*****************增加手机对于录播设备的操作（原来由sip通过集控平台获取）*************************************/

    /**
     * 获取镜头信息
     * @param IP  设备IP，多个用逗号分割
     * @return
     */
    public List<Map<String, Object>> getHRECDeviceInfo(String IP) {
        List<Map<String, Object>> cmdList = new ArrayList<>();
        String[] ips = IP.split(",");

        for (int i = 0; i < ips.length; i++) {
            try {
                String hostIp = ips[i];
                Map<String, Object> objectMap = new HashMap<>();
                Map hostInfo = DeviceDao.INSTANCE.getHostInfoByIp(hostIp);
                String hostFactory = hostInfo.get("host_factory").toString();
                String dspecid = hostInfo.get("dspec_id").toString();
                if (hostInfo.size() == 0) {
                    objectMap.put("devicename", "");
                    objectMap.put("devicetype", "");
                    objectMap.put("deviceip", ips[i]);
                    objectMap.put("tokens", new ArrayList<>());
                    cmdList.add(objectMap);
                    continue;
                }
                objectMap.put("devicename", hostInfo.get("host_name").toString());
                objectMap.put("devicetype", hostInfo.get("dtype_name").toString());
                objectMap.put("deviceip", hostInfo.get("host_ipaddr").toString());

                List<Map<String, String>> deviceInfo = HostDeviceDao.INSTANCE.getDeviceInfo(hostInfo.get("host_id").toString());
                List tokensList = new ArrayList<>();
                for (Map simpleMap : deviceInfo) {
                    String isPtz = simpleMap.get("isPtz").toString();
                    if (!hostFactory.equals(AREC_DEVICE)) {//Arec设备不支持云台
                        if (dspecid.equals(VIRTUAL_DEVICEID))//虚拟设备云台控制全开
                        {
                            isPtz = "1";
                        }
                    }
                    Map<String, String> mainToken = new HashMap<>();
                    Map<String, String> subToken = new HashMap<>();
                    mainToken.put("name", simpleMap.get("deviceName").toString());
                    mainToken.put("token", simpleMap.get("deviceMaintoken").toString());
                    mainToken.put("url", simpleMap.get("deviceMainstream").toString());
                    mainToken.put("isptz", isPtz);
                    mainToken.put("tokentype", "main");
                    subToken.put("name", simpleMap.get("deviceName").toString());
                    subToken.put("token", simpleMap.get("deviceSubtoken").toString());
                    subToken.put("url", simpleMap.get("deviceSubstream").toString());
                    subToken.put("isptz", isPtz);
                    subToken.put("tokentype", "sub");
                    tokensList.add(mainToken);
                    tokensList.add(subToken);
                }
                objectMap.put("tokens", tokensList);
                cmdList.add(objectMap);
            } catch (Exception e) {
                Map newMap = new HashMap();
                newMap.put("devicename", "");
                newMap.put("devicetype", "");
                newMap.put("deviceip", ips[i]);
                newMap.put("tokens", new ArrayList<>());
                cmdList.add(newMap);
                continue;
            }
        }
        return cmdList;
    }

    /**
     * 云台移动
     *
     * @param IP    设备Ip
     * @param token 镜头标识
     * @param xyz   移动的坐标参数，用：分割
     * @return
     */
    public boolean ptzStart(String IP, String token, String xyz) {
        String[] coordinate = xyz.split(":");
        return PTZStartMove(IP, coordinate[0], coordinate[1], token, coordinate[2], "1000");
    }

    /**
     * 云台转动停止
     *
     * @param IP    设备Ip
     * @param token 镜头标识
     * @return
     */
    public boolean ptzStop(String IP, String token) {
        return PTZStopMove(IP, token);
    }

    /**
     * 判断是否在线
     *
     * @param IP 设备IP，多个用逗号分割
     * @return
     */
    public Map<String, String> getHRECDeviceOnlineInfo(String IP) {
        String[] ips = IP.split(",");
        Map<String,String> resultMap = new HashMap<String,String>();
        for (int i = 0; i < ips.length; i++) {
            resultMap.put(ips[i], Host.INSTANCE.isOnline(ips[i]) ? "1" : "0");
        }
        return resultMap;
    }
    //***************************************************结束*****************************************************/

    /**
     * 获取所有设备的镜头详情
     * @return
     */
    public List<Map<String,String>> getDeviceConfigList(){
        List list = HostDeviceDao.INSTANCE.getDeviceConfig();
        return list;
    }


    /**
     * 添加录像计划
     *
     * @param ip
     * @param recordTasks
     * @return
     */
    private int addVideoPlan(String ip, List<RecordTask> recordTasks) {
        try {
            OperationRequestSetRecordTasks setRecordTasks = new OperationRequestSetRecordTasks(recordTasks);
            DeviceManager.invoke(ip, setRecordTasks);
            logger.debug("添加录像计划ip为：" + ip + ",recordTasks:" + recordTasks.toString());
            return 1;
        } catch (Exception e) {
            logger.error("添加录像计划异常ip为：" + ip + ",recordTasks:" + recordTasks.toString(), e);
            return -1;
        }
    }

    /**
     * 获取所有的录像计划
     * @param ip
     * @param ext
     * @return
     */
    private List<RecordTask> getAllPlanes(String ip, String ext) {
        try {
            OperationRequestGetRecordTasks getRecordTasks = new OperationRequestGetRecordTasks(ext);
            List<RecordTask> res = (List<RecordTask>) DeviceManager.invoke(ip, getRecordTasks);
            logger.debug("获取所有的录像计划ip为：" + ip + ",ext:" + ext);
            return res;
        } catch (Exception e) {
            logger.error("获取所有的录像计划异常ip为：" + ip + ",ext:" + ext, e);
            return new ArrayList<>();
        }
    }

    /**
     *
     * @param ip
     * @param ext
     * @return
     */
    private List<Layout> getLayouts(String ip, String ext) {
        try {
            List<Layout> layouts = (List<Layout>) DeviceManager.invoke(ip, new OperationRequestGetLayoutConfigurations(ext));
            return layouts;
        } catch (Exception e) {
            logger.error("获取layouts异常 ip为：" + ip + ",ext:" + ext, e);
            return new ArrayList<>();
        }
    }

    /**
     * 添加录像计划
     *
     * @param hostId          设备id
     * @param referenceToken  主码流的token
     * @param timeplan_id     数据库表的录像计划ID
     * @param ext             未用到
     * @param week_id         0表示临时录像计划，1表示星期一，.......，7表示星期日
     * @param startTime       临时录像计划传带日期的时间格式，录像计划只传时间，开始时间 “HH:MM”形式的二十四小时制时间格式
     * @param stopTime        临时录像计划传带日期的时间格式，录像计划只传时间，结束时间 “HH:MM”形式的二十四小时制时间格式
     * @param protocol        直播使用的协议类型
     * @param streamimgAddres 直播地址
     * @param courseTitle     标题
     * @param mainTitle       主讲标题
     * @param subjectName     科目
     * @param school          学校
     * @param teacher         主讲人
     * @param extension       extension是扩展字段，目前没有实际意义
     * @return
     */
    private int addPlan(int hostId, String referenceToken, String timeplan_id, String ext, String week_id, String startTime, String stopTime, String protocol, String streamimgAddres, String courseTitle, String mainTitle, String subjectName,
                        String school, String teacher, String extension) {
        Map host = DeviceDao.INSTANCE.getHostInfoById(hostId + "");
        String hostName = host.get("host_name").toString();
        String ip = host.get("host_ipaddr").toString();
        RecordTask recordTask = new RecordTask();
        String deviceToken = referenceToken;//镜头token
        boolean isOnline = Host.INSTANCE.isOnline(ip);
        if (!isOnline) {
            return 0;//设备离线
        }
        String[] deviceTokenArray = deviceToken.split(",");
        String[] localTokenArray = new String[0];
        recordTask = setRecordTaskParam(timeplan_id, week_id, startTime, stopTime, deviceTokenArray, localTokenArray, protocol, streamimgAddres, courseTitle, mainTitle, hostName, subjectName, school, teacher, extension);
        List<RecordTask> list = new ArrayList<>();
        list = getAllPlanes(ip, ext);//获取所有计划
        list.add(recordTask);
        int flag = addVideoPlan(ip, list);//1为成功 -1为失败
        logger.debug("添加录像计划，ip为" + ip + "id为" + hostId);
        return flag;
    }

    /**
     * 添加录像计划
     *
     * @param plans 所有录像计划
     * @return
     */
    private int addPlan(List<Map<String, String>> plans) {
        List<RecordTask> list = new ArrayList<>();
        int flag = -1;
        String ip = "";
        int hostId = 0;


        if (flag != 0) {
            for (Map map : plans) {
                hostId = Integer.parseInt(map.get("hostId").toString());
                String timeplan_id = map.get("timeplan_id").toString();
                String ext = map.get("ext").toString();
                String week_id = map.get("week_id").toString();
                String startTime = map.get("startTime").toString();
                String stopTime = map.get("stopTime").toString();
                String referenceToken = map.get("referenceToken").toString();
                String protocol = map.get("protocol").toString();
                String streamimgAddres = map.get("streamimgAddres").toString();
                String courseTitle = map.get("courseTitle").toString();
                String mainTitle = map.get("mainTitle").toString();
                String subjectName = map.get("subjectName").toString();
                String school = map.get("school").toString();
                String teacher = map.get("teacher").toString();
                String extension = map.get("extension").toString();


                Map host = DeviceDao.INSTANCE.getHostInfoById(hostId + "");
                String hostName = host.get("host_name").toString();
                ip = host.get("host_ipaddr").toString();
                RecordTask recordTask = new RecordTask();
                String deviceToken = referenceToken;//镜头token

                boolean isOnline = Host.INSTANCE.isOnline(ip);
                if (!isOnline) {
                    return 0;//设备离线
                }
                String[] deviceTokenArray = deviceToken.split(",");
                String[] localTokenArray = new String[0];
                recordTask = setRecordTaskParam(timeplan_id, week_id, startTime, stopTime, deviceTokenArray, localTokenArray, protocol, streamimgAddres, courseTitle, mainTitle, hostName, subjectName, school, teacher, extension);
                list.add(recordTask);
            }

            if (!list.isEmpty() && flag != 0) {
                flag = addVideoPlan(ip, list);//1为成功 -1为失败
                logger.debug("添加录像计划，ip为" + ip + "id为" + hostId);
            }
        }
        return flag;
    }
}
