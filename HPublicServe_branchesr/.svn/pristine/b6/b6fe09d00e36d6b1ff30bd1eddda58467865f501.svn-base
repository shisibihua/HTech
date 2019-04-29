package com.honghe.device.message;

import com.hht.DeviceManager.operationRequest.OperationRequest;
import com.hht.DeviceManager.operationRequest.record.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 此部分为使用
 */
public class OperatorMessage {
    //private static DeviceService deviceService = (DeviceService) ContextLoaderListener.getCurrentWebApplicationContext().getBean(DeviceService.class);
    //private static Ftp4jService ftp4jService = (Ftp4jService) ContextLoaderListener.getCurrentWebApplicationContext().getBean(Ftp4jService.class);
    // private static HostgroupService hostgroupService = (HostgroupService) ContextLoaderListener.getCurrentWebApplicationContext().getBean(HostgroupService.class);
    // private static SettingsService settingsService = (SettingsService) ContextLoaderListener.getCurrentWebApplicationContext().getBean(SettingsService.class);
    //private static String uploadPath = OperatorMessage.class.getResource("/").getPath().replaceAll("WEB-INF/classes/", "") + "upload/";
    // private ComputerCommand computerCommand;
    static {
        //File file = new File(uploadPath);
        //if(!file.exists()){
        //   file.mkdir();
        //}
    }

    public OperatorMessage() {
        //this.computerCommand = computerCommand;
    }

    /*public OperationRequest[] cloudControl(String hostIp, String hostId, String cmdId, ReferenceToken referenceToken, JSONArray params) throws Exception{
    }*/


    private OperationRequest getRecordOperationRequest(String hostId, String recordingMode, String ext) throws Exception {
//        List<Object[]> deviceList = deviceService.getDeviceListService(Integer.parseInt(hostId));
//        List<String> token = new ArrayList();
//        if (recordingMode.equals("recordingModeMovie")) {//电影模式
//            for (Object[] objArray : deviceList) {
//                if (objArray[1].equals("电影模式")) {
//                    token.add(objArray[3].toString());
//                }
//            }
//        } else {
//            for (Object[] objArray : deviceList) {
//                token.add(objArray[3].toString());
//            }
//        }
//        if (token.isEmpty()) {
//            throw  new ProgramException("getRecordOperationRequest method token is empty");
//        }
//        return new OperationRequestStartRecording((String[]) token.toArray(new String[token.size()]), false, true, ext);
        return null;
    }

    public OperationRequest[] workingMode(String hostIp, String hostId, String cmdId, JSONArray params) throws Exception {
        /*String workingMode = params.getJSONObject(0).getString("workingMode");
        String recordingMode = params.getJSONObject(0).getString("recordingMode");
        String recordType = params.getJSONObject(0).getString("recordType");
        if (cmdId.equals("0x005004")) {
            OperationRequest courseOperationRequest = null;
            Map<String, JSONObject> courseInfoCache = settingsService.getCourseInfoCache();
            if (courseInfoCache != null && courseInfoCache.containsKey(hostIp)) {
                courseOperationRequest = this.getSetCourseInfo(courseInfoCache.get(hostIp));
                courseInfoCache.remove(hostIp);
            }
            if (workingMode.equals("workingModeRecording")) {

//                if (!settingsService.hasSettingNas(Integer.parseInt(hostId))) {
//                    throw new FrontException("请设置Nas服务器");
//                }
                String ext = "";
                if (recordType.equals("1")) {
                    ext = "mode_record";
                    //精品录播
                }
                if (courseOperationRequest != null) {
                    return new OperationRequest[]{this.getRecordOperationRequest(hostId, recordingMode, ext), courseOperationRequest};
                } else {
                    return new OperationRequest[]{this.getRecordOperationRequest(hostId, recordingMode, ext)};
                }

            } else if (workingMode.equals("workingModeLiveCasting")) {
                String liveAddr = settingsService.getSetting().getLiveAddr();
//                if (liveAddr == null || liveAddr.equals("")) {
//                    throw new FrontException("请设置直播服务器地址");
//                }
                Map<String, Object> info = computerCommand.getComputer().getExtensionInfo(hostIp);
                if (info.isEmpty()) throw new FrontException("班级已经下线");
                String hostName = info.get("host_name").toString();

               // return new OperationRequest[]{new OperationRequestStartStreaming("RTMP", liveAddr)};
                 return new OperationRequest[]{new OperationRequestStartStreaming("RTMP",liveAddr + "/" + DigestUtils.md5Hex(hostName))};
            } else {
//                if (!settingsService.hasSettingNas(Integer.parseInt(hostId))) {
//                    throw new FrontException("请设置Nas服务器");
//                }
                String liveAddr = settingsService.getSetting().getLiveAddr();
//                if (liveAddr == null || liveAddr.equals("")) {
//                    throw new FrontException("请设置直播服务器地址");
//                }
                Map<String, Object> info = computerCommand.getComputer().getExtensionInfo(hostIp);
                if (info.isEmpty()) throw new FrontException("班级已经下线");
                String hostName = info.get("host_name").toString();
                String ext = "";
                if (recordType.equals("1")) {
                    ext = "mode_record_live";
                }
                if (courseOperationRequest != null) {
                    return new OperationRequest[]{this.getRecordOperationRequest(hostId, recordingMode, ext), new OperationRequestStartStreaming("RTMP", liveAddr + "/" + DigestUtils.md5Hex(hostName)), courseOperationRequest};
                } else {
                    return new OperationRequest[]{this.getRecordOperationRequest(hostId, recordingMode, ext), new OperationRequestStartStreaming("RTMP", liveAddr + "/" + DigestUtils.md5Hex(hostName))};
                }

            }
        } else if (cmdId.equals("0x005006")) {
            if (workingMode.equals("workingModeRecording")) {
                return new OperationRequest[]{new OperationRequestPauseRecording()};
            } else if (workingMode.equals("workingModeLiveCasting")) {//直播
                return new OperationRequest[]{new OperationRequestPauseStreaming()};
            } else {//录制+直播
                return new OperationRequest[]{new OperationRequestPauseRecording(), new OperationRequestPauseStreaming()};
            }

        } else if (cmdId.equals("0x005007")) {
            if (workingMode.equals("workingModeRecording")) {
                return new OperationRequest[]{new OperationRequestStopRecording()};
            } else if (workingMode.equals("workingModeLiveCasting")) {//直播
                return new OperationRequest[]{new OperationRequestStopStreaming()};
            } else {//录制+直播
                return new OperationRequest[]{new OperationRequestStopRecording(), new OperationRequestStopStreaming()};
            }
        } else if (cmdId.equals("0x005008")) {
            String value = params.getJSONObject(0).getString("value");
            return new OperationRequest[]{new OperationRequestSetVolume(Integer.parseInt(value))};
        }
        //暂停后重新开始录制或直播
        else if (cmdId.equals("0x005013")) {
            if (workingMode.equals("workingModeRecording")) {
                return new OperationRequest[]{new OperationRequestPlayRecording()};
            } else if (workingMode.equals("workingModeLiveCasting")) {//直播
                return new OperationRequest[]{new OperationRequestPlayStreaming()};
            } else {//录制+直播
                return new OperationRequest[]{new OperationRequestPlayRecording(), new OperationRequestPlayStreaming()};
            }

        }*/
        return null;
    }

    /**
     * 导播策略
     *
     * @param hostIp
     * @param hostId
     * @param cmdId
     * @param params
     * @return
     */
    public OperationRequest[] directMode(String hostIp, String hostId, String cmdId, JSONArray params) throws Exception {
        OperationRequest operationRequest = null;
        if (cmdId.equals("0x006002")) {
            String value = params.getJSONObject(0).getString("value");
            if (value.equals("directModeAuto")) {
                operationRequest = new OperationRequestSetDirectMode("0", "");
            } else {
                operationRequest = new OperationRequestSetDirectMode("1", "");
            }
        }
        return new OperationRequest[]{operationRequest};
    }

    /**
     * 字幕
     *
     * @param cmdId
     * @param params
     * @return
     */
    public OperationRequest[] subTile(String hostIp, String hostId, String cmdId, JSONArray params) {
//        try {
//            if (cmdId.equals("0x005014")) {
//                if (hostId == null) {
//                    return null;
//                }
//                String ftp = hostIp;
//                String ftpaddr = "/NVRSettings/Captions/";
//
//                String imagesaddr = "http://" + InetAddress.getLocalHost().getHostAddress() + ":8080/image/frontend/n_logo.png";
//                Host host = hostgroupService.HostInfoService(hostId);
//                String name = host.getHostUserName();
//                String pass = host.getHostPassWord();
//                FTPClient ftpClient = ftp4jService.run(ftp, 21, name, pass);
//                if (ftpClient != null) {
//                    ftp4jService.uploadFile(ftpClient, imagesaddr, ftpaddr);
//                    String CaptionPath = "/NVRSettings/Captions/n_logo.png";//字幕存储的相对路径。
//                    String Location = params.getJSONObject(0).getString("duration");//位置代码，该字符串在GlobalDefine可以调用
//                    String subTitleName = params.getJSONObject(0).getString("subTitleName");
//                    String backColor = params.getJSONObject(0).getString("backColor");
//                    String transparency = params.getJSONObject(0).getString("transparency");
//                    String fontSize = params.getJSONObject(0).getString("fontSize");
//                    String startTime = params.getJSONObject(0).getString("startTime");
//                    String fontColor = params.getJSONObject(0).getString("fontColor");
//                    return new OperationRequest[]{new OperationRequestSetCaption(CaptionPath, Location)};
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
        return null;
    }

    /**
     * 视频打点准备
     *
     * @param cmdId
     * @param params
     * @return
     */
    public OperationRequest[] videoDotPrepare(String hostIp, String hostId, String cmdId, JSONArray params, String ext) throws Exception {
        return new OperationRequest[]{new OperationRequestGetRecordingStatus(ext)};
    }

    /**
     * 视频打点
     *
     * @param cmdId
     * @param params
     * @return
     */
    public OperationRequest[] videoDot(String hostIp, String hostId, String cmdId, JSONArray params, String ext) {
        if (cmdId.equals("0x005008")) {
            String timer = params.getJSONObject(0).getString("time");
            String KeyNote = params.getJSONObject(0).getString("keyNote");
            OperationRequest operationRequest = new OperationRequestInsertKeyNote(Integer.parseInt(timer), ext, KeyNote);
            return new OperationRequest[]{operationRequest};
        }
        return null;
    }

    /**
     * 片头
     *
     * @param cmdId
     * @param params
     * @return
     */
    public OperationRequest[] prelude(String hostIp, String hostId, String cmdId, JSONArray params) throws Exception {
        /*String avaliable = params.getJSONObject(0).getString("avaliable");
        if (avaliable.equals("1")) {
            return new OperationRequest[]{new OperationRequestRemoveTitleVideo()};
        }
        String picture = params.getJSONObject(0).getString("picture");
        String backMusic = params.getJSONObject(0).getString("backMusic");
        String recordType = params.getJSONObject(0).getString("recordType");

        Host host = hostgroupService.HostInfoService(hostId);
        String name = host.getHostUserName();
        String pass = host.getHostPassWord();
        FTPClient ftpClient = ftp4jService.run(hostIp, 21, "anonymous", "");
        if (ftpClient != null) {
            boolean flag = ftp4jService.uploadFile(ftpClient, uploadPath + picture, Constant.MATERIALPATH);
            if (!flag) throw new FrontException("片头图片上传失败");
            try {
                if (!ftpClient.currentDirectory().equals("/")) {
                    ftpClient.changeDirectory("/");
                }
            } catch (Exception e) {
                throw new FrontException("片头上传失败" + e.getMessage());
            }
            flag = ftp4jService.uploadFile(ftpClient, uploadPath + backMusic, Constant.MATERIALPATH);
            if (!flag) throw new FrontException("片头音乐上传失败");
            IOUtil.delete(uploadPath + backMusic);
            IOUtil.delete(uploadPath + picture);
            if (cmdId.equals("0x005009")) {
                String duration = params.getJSONObject(0).getString("duration");
                OperationRequest operationRequest;
                if (recordType.equals("0")) {//简易录播
                    operationRequest = new OperationRequestSetTitleVideo(Constant.MATERIALPATH + "/" + picture, Constant.MATERIALPATH + "/" + backMusic, "", Integer.parseInt(duration));
                } else {
                    Map<String, String> extParams = new HashMap<>();
                    extParams.put("title", params.getJSONObject(0).getString("title"));
                    extParams.put("course", params.getJSONObject(0).getString("course"));
                    extParams.put("grade", params.getJSONObject(0).getString("grade"));
                    extParams.put("subject", params.getJSONObject(0).getString("subject"));
                    extParams.put("school", params.getJSONObject(0).getString("school"));
                    extParams.put("teacher", params.getJSONObject(0).getString("teacher"));
                    extParams.put("preludeName", params.getJSONObject(0).getString("preludeName"));
                    extParams.put("fontSize", params.getJSONObject(0).getString("fontSize"));
                    extParams.put("fontColor", params.getJSONObject(0).getString("fontColor"));
                    String extString = JSONObject.fromObject(extParams).toString();
                    operationRequest = new OperationRequestSetTitleVideo(Constant.MATERIALPATH + "/" + picture, Constant.MATERIALPATH + "/" + backMusic, extString, Integer.parseInt(duration));
                }

                return new OperationRequest[]{operationRequest};
            }
        }*/
        //throw new FrontException("Ftp连接失败");
        return null;
    }

    /**
     * 片尾
     *
     * @param cmdId
     * @param params
     * @return
     */
    public OperationRequest[] curtain(String hostIp, String hostId, String cmdId, JSONArray params) throws Exception {
        /*String avaliable = params.getJSONObject(0).getString("avaliable");
        if (avaliable.equals("1")) {
            return new OperationRequest[]{new OperationRequestRemoveEndingVideo()};
        }
        String picture = params.getJSONObject(0).getString("picture");
        String backMusic = params.getJSONObject(0).getString("backMusic");
        String recordType = params.getJSONObject(0).getString("recordType");
        Host host = hostgroupService.HostInfoService(hostId);
        String name = host.getHostUserName();
        String pass = host.getHostPassWord();
        FTPClient ftpClient = ftp4jService.run(hostIp, 21, "anonymous", "");
        if (ftpClient != null) {
            boolean flag = ftp4jService.uploadFile(ftpClient, uploadPath + picture, Constant.MATERIALPATH);
            if (!flag) throw new FrontException("片尾图片上传失败");
            try {
                if (!ftpClient.currentDirectory().equals("/")) {
                    ftpClient.changeDirectory("/");
                }
            } catch (Exception e) {
                throw new FrontException("片尾上传失败" + e.getMessage());
            }

            flag = ftp4jService.uploadFile(ftpClient, uploadPath + backMusic, Constant.MATERIALPATH);
            if (!flag) throw new FrontException("片尾音乐上传失败");
            IOUtil.delete(uploadPath + backMusic);
            IOUtil.delete(uploadPath + picture);
            if (cmdId.equals("0x005010")) {
                String duration = params.getJSONObject(0).getString("duration");
                OperationRequest operationRequest;
                if (recordType.equals("0")) {//简易录播
                    operationRequest = new OperationRequestSetEndingVideo(Constant.MATERIALPATH + "/" + picture, Constant.MATERIALPATH + "/" + backMusic, "", Integer.parseInt(duration));
                } else {
                    Map<String, String> extParams = new HashMap<>();
                    extParams.put("date", params.getJSONObject(0).getString("date"));
                    extParams.put("copyright", params.getJSONObject(0).getString("copyright"));
                    extParams.put("curtainName", params.getJSONObject(0).getString("curtainName"));
                    extParams.put("production", params.getJSONObject(0).getString("production"));
                    extParams.put("fontSize", params.getJSONObject(0).getString("fontSize"));
                    extParams.put("fontColor", params.getJSONObject(0).getString("fontColor"));
                    String extString = JSONObject.fromObject(extParams).toString();
                    operationRequest = new OperationRequestSetEndingVideo(Constant.MATERIALPATH + "/" + picture, Constant.MATERIALPATH + "/" + backMusic, extString, Integer.parseInt(duration));
                }
                return new OperationRequest[]{operationRequest};
            }
        }
        throw new FrontException("Ftp连接失败");*/
        return null;
    }


    private OperationRequest getSetCourseInfo(JSONObject params) {
        String courseTitle = params.getString("course");
        String MainTititle = params.getString("title");
        String ClassName = params.getString("grade");
        String SubjectName = params.getString("subject");
        String Teacher = params.getString("teacher");
        String School = params.getString("school");
        String ext = params.getString("ext");
        OperationRequest operationRequest = new OperationRequestSetCourseInfo(courseTitle, MainTititle, ClassName, SubjectName, Teacher, School, ext);
        return operationRequest;
    }

    /**
     * 课程信息
     *
     * @param cmdId
     * @param params
     * @return
     */
    public OperationRequest[] courseInfo(String hostIp, String hostId, String cmdId, JSONArray params) {
        if (cmdId.equals("0x005011")) {
            String recordStatus = params.getJSONObject(0).getString("recordStatus");
            if (recordStatus.equals("stop")) {
                //Map<String, JSONObject> courseInfoCache = settingsService.getCourseInfoCache();
                // courseInfoCache.put(hostIp, params.getJSONObject(0));
                return null;
            }
            return new OperationRequest[]{this.getSetCourseInfo(params.getJSONObject(0))};
        }
        return null;
    }

    public OperationRequest[] mutilScreen(String hostIp, String hostId, String cmdId, JSONArray params) {
        if (cmdId.equals("0x006001")) {
            Map<String, String> layoutwindowMap = new ConcurrentHashMap();
            //layoutwindowMap = settingsService.getLayoutWindow();
            String ID = params.getJSONObject(0).getString("value");
            OperationRequest operationRequest = new OperationRequestSetLayout((String) layoutwindowMap.get(ID));
            return new OperationRequest[]{operationRequest};
        }
        return null;
    }


}
