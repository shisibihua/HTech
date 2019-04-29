package com.honghe.tech.service.impl;

import com.honghe.tech.dao.ActivityDao;
import com.honghe.tech.httpservice.AdHttpService;
import com.honghe.tech.httpservice.AreaHttpService;
import com.honghe.tech.httpservice.DeviceHttpService;
import com.honghe.tech.httpservice.UserHttpService;
import com.honghe.tech.service.ActivityService;
import com.honghe.tech.service.TeachingSupervisionService;
import com.honghe.tech.util.ConfigUtil;
import com.honghe.tech.util.HttpRequestUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author cooqian
 */
@Service
public class TeachingSupervisionServiceImpl implements TeachingSupervisionService {
    private Logger logger=Logger.getLogger(TeachingSupervisionServiceImpl.class);
    private static final String SCREENPORT="8059";
    private static final String RESOLUTION="280_160";
    private static final String NOCLASS = "0";
    private static final String CLASSING = "1";
    private static final String CLASSEND ="2";
    private static final int MAXPLAYCLASS = 4;
    @Autowired
    private ActivityDao activityDao;
    @Autowired
    private DeviceHttpService deviceHttpService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private UserHttpService userHttpService;
    @Autowired
    private AreaHttpService areaHttpService;
    /**
     * 分页获取教学监管数据
     * @param provinceId             省id
     * @param cityId                 城市id
     * @param contyId                区(县)id
     * @param schoolId               学校id
     * @param selectType             教室状态，''-全部教室，0-上课中，1-未上课，2-所有直播
     * @param currentPage            当前页
     * @param pageSize               每页数据条数
     * @return  map                  教学监管数据
     * @author caoqian
     */
    @Override
    public Map<String, Object> techSuperviseListByPage(String provinceId,String cityId, String contyId, String schoolId,
                                                       String selectType, String currentPage, String pageSize){
        Map<String,Object> resultMap=new HashMap<>();
        if(selectType==null || currentPage==null || "".equals(currentPage) || pageSize==null || "".equals(pageSize)){
            logger.debug("教室状态selectType:"+selectType+",当前页currentPage:"+currentPage+",每页条数pageSize:"+pageSize+",分页获取教学监管数据失败，参数异常。");
            throw new IllegalArgumentException();
        }else {
            try {
                // TODO: 2018/5/30 增加了括号 
                if(provinceId==null) {
                    provinceId="";
                }
                //查询地点下所有教室监管
                // TODO: 2018/5/30 修改为常量判断 selectType的对比
                if ("".equals(selectType)) {
                    //教室分页列表
                    Map<String, Object> classRoom = userHttpService.getRoomByPage(provinceId,cityId, contyId, schoolId, Integer.parseInt(currentPage), Integer.parseInt(pageSize));
                    //所有教室ids
                    String classRoomIds=userHttpService.getRoomIdsByAreaId(provinceId,cityId,contyId,schoolId);
                    selectAllRoomsTeach(classRoom,classRoomIds);
                    resultMap.putAll(classRoom);
                    //查询上课中教室监管
                } else if (CLASSING.equals(selectType)) {
                    //所有教室ids
                    String classRoomIds=userHttpService.getRoomIdsByAreaId(provinceId,cityId,contyId,schoolId);
                    resultMap.putAll(selectTeachingRoomsTeach(classRoomIds, currentPage, pageSize));
                    //查询未上课教室监管
                } else if (NOCLASS.equals(selectType)) {
                    resultMap.putAll(selectNoTeachingRoomsTeach(provinceId,cityId, contyId, schoolId, currentPage, pageSize));
                    //为首页提供直播数据
                } else if(CLASSEND.equals(selectType)){
                    String classRoomIds=areaHttpService.getAllAreaIds();
                    resultMap.putAll(selectMasterTeachingRoomsTeach(classRoomIds, currentPage, pageSize));
                }
            } catch (Exception e) {
                logger.error("分页获取教学监管列表异常，cityId=" + cityId + ",contyId=" + contyId +
                        ",schoolId=" + schoolId + ",selectType=" + selectType, e);
            }
        }
        return resultMap;
    }

    /**
     * 根据地点查询所有教室教学监管情况
     * @param classRoom 地点（教室名称）
     * @param classRoomIds 地点ID
     */
    private void selectAllRoomsTeach(Map<String, Object> classRoom, String classRoomIds) {
        List<Map<String, Object>> classRoomList = new ArrayList<>();
        if (classRoom != null && !classRoom.isEmpty()) {
            //教室数据，包括城市名、区/县名、学校名、教室名、教室id
            classRoomList = (List<Map<String, Object>>) classRoom.get("items");
        }
        //教室id
        String classRoomId = "";
        if (classRoomList != null && !classRoomList.isEmpty()) {
            for (int i = 0; i < classRoomList.size() - 1; i++) {
                classRoomId += String.valueOf(classRoomList.get(i).get("areaId")) + ",";
            }
            classRoomId += String.valueOf(classRoomList.get(classRoomList.size() - 1).get("areaId"));
            logger.info("根据地点查询的教室ids=" + classRoomId);
            String[] classRoomIdArr = classRoomId.split(",");
            //正在上课的教室
            List<Map<String, Object>> teachingActivityList = activityDao.getTeachingActivityListByRoomIdArr(classRoomIdArr);
            for (int j = 0; j < classRoomList.size(); j++) {
                Map<String, Object> classRoomMap = classRoomList.get(j);
                //教室id
                String areaId = String.valueOf(classRoomMap.get("areaId"));
                //根据教室id查询的设备信息
                Map<String, String> host = areaHttpService.getHostByRoomId(areaId);
                String mcuClientCode = "";
                if (host != null && !host.isEmpty()) {
                    mcuClientCode = host.get("mcu_code");
                }
                for (int k = 0; k < teachingActivityList.size(); k++) {
                    Map<String, Object> activityMap = teachingActivityList.get(k);
                    //教室id
                    String roomId = String.valueOf(activityMap.get("roomid"));
                    if (areaId.equals(roomId)) {
                        //活动id
                        classRoomMap.put("activityId", activityMap.get("activity_id"));
                        //活动名称
                        classRoomMap.put("activityName", activityMap.get("name"));
                        //年级名称
                        classRoomMap.put("gradeName", activityMap.get("grade_name"));
                        //主讲人
                        classRoomMap.put("teacherName", activityMap.get("host_name"));
                        //主讲教室id
                        String lectureRoomId = String.valueOf(activityMap.get("room_id"));
                        if (lectureRoomId.equals(roomId)) {
                            //教室id与主讲教室id一致，说明是主讲教室，否则是接收教室
                            classRoomMap.put("classRoomType", NOCLASS);
                        } else {
                            classRoomMap.put("classRoomType", CLASSING);
                        }
                        //活动开始时间
                        classRoomMap.put("startTime", activityMap.get("start_time").toString());
                        //活动结束时间
                        classRoomMap.put("endTime", activityMap.get("end_time").toString());
                        classRoomMap.put("uuid", activityMap.get("uuid").toString());
                        //上课中,供前端判断是否是上课中
                        classRoomMap.put("activityStatus", NOCLASS);
                        break;
                    } else {
                        //未上课教室
                        classRoomMap.put("activityStatus", CLASSING);
                        continue;
                    }
                }
                //提供推流地址
                classRoomMap.put("rtmp", getRtmpAddr(mcuClientCode));
            }
            sortList(classRoomList);

            //查询正在上课的数量
            if (classRoomIds != null && !"".equals(classRoomIds)) {
                String[] idsArr = classRoomIds.split(",");
                //正在上课的数量
                int techingNum = activityDao.teachingActivityListByPage(idsArr, 0, 0, false).size();
                classRoom.put("teachingNum", techingNum);
            } else {
                classRoom.put("teachingNum", 0);
            }
        }
    }

    /**
     * 对有直播的教室进行排序，优先显示
     * @param orderFromList 直播教室列表
     */
    private void sortList(List<Map<String,Object>> orderFromList){
            Collections.sort(orderFromList, new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    int map1value = 0;
                    int map2value=0;
                    try {
                        // TODO: 2018/5/30 删除代码
                        if(o1.containsKey("activityStatus")) {
                            map1value = Integer.parseInt(String.valueOf(o1.get("activityStatus")));
                        }
                        if(o2.containsKey("activityStatus")){
                            map2value = Integer.parseInt(String.valueOf(o2.get("activityStatus")));
                        }
                    } catch (Exception e) {
                        logger.error("教学监管按直播顺序查询所有教室数据异常!",e);
                    }
                    return map1value > map2value ? 1:-1;
                }
            });
    }

    /**
     * 所有正在上课
     * @param classRoomIds 教室ID
     * @param currentPage 当前页码
     * @param pageSize 总页码
     * @return 返回上学监督列表
     */
    private Map<String,Object> selectTeachingRoomsTeach(String classRoomIds,String currentPage,String pageSize){
        String[] classRoomIdsArr=null;
        if(classRoomIds!=null && !"".equals(classRoomIds)) {
            classRoomIdsArr=classRoomIds.split(",");
        }
        Map<String,Object> resultMap=new HashMap<>();
        if(classRoomIdsArr!=null && classRoomIdsArr.length>0){
            //获取第一条开始的位置
            int firstResult = (Integer.parseInt(currentPage)-1)*Integer.parseInt(pageSize)<0?0:(Integer.parseInt(currentPage)-1)*Integer.parseInt(pageSize);
            //总数量
            int total=activityDao.teachingActivityListByPage(classRoomIdsArr,firstResult,Integer.parseInt(pageSize),false).size();
            //教学活动数据
            List<Map<String,Object>> activityList=activityDao.teachingActivityListByPage(classRoomIdsArr,firstResult,Integer.parseInt(pageSize),true);
            resultMap=getResultMap(activityList);
            //上课数量
            resultMap.put("teachingNum",total);
            //数据总条数
            resultMap.put("totalItems",total);
            //当前页码
            resultMap.put("currentPage",currentPage);
            //总页码
            resultMap.put("pageSize",pageSize);
        }
        return resultMap;
    }

    /**
     * 直播数据(所有主讲) 和上一个方法合并 增加类型
     * @param classRoomIds
     * @param currentPage
     * @param pageSize
     * @return
     */
    private Map<String,Object> selectMasterTeachingRoomsTeach(String classRoomIds,String currentPage,String pageSize){
        String[] classRoomIdsArr=null;
        if(classRoomIds!=null && !"".equals(classRoomIds)) {
            classRoomIdsArr=classRoomIds.split(",");
        }
        Map<String,Object> resultMap=new HashMap<>();
        if(classRoomIdsArr!=null && classRoomIdsArr.length>0){
            //获取第一条开始的位置
            int firstResult = (Integer.parseInt(currentPage)-1)*Integer.parseInt(pageSize)<0?0:(Integer.parseInt(currentPage)-1)*Integer.parseInt(pageSize);
            //总数量
            int total=activityDao.teachingActivityMasterListByPage(classRoomIdsArr,firstResult,Integer.parseInt(pageSize),false).size();
            //教学活动数据
            List<Map<String,Object>> activityList=activityDao.teachingActivityMasterListByPage(classRoomIdsArr,firstResult,Integer.parseInt(pageSize),true);
            resultMap=getResultMap(activityList);
            //上课数量
            resultMap.put("teachingNum",total);
            //数据总条数
            resultMap.put("totalItems",total);
            resultMap.put("currentPage",currentPage);
            resultMap.put("pageSize",pageSize);
        }
        return resultMap;
    }

    /**
     * 获取教学监督数据（数据返回格式处理）
     * @param activityList 互动教学数据
     * @return
     */
    private Map<String,Object> getResultMap(List<Map<String,Object>> activityList){
        List<Map<String,Object>> addActivityList=new ArrayList<>();
        Map<String,Object> techSuperviseMap=new HashMap<>();
        if(activityList!=null && !activityList.isEmpty()){
            for(Map<String,Object> activityMap:activityList){
                Map<String,Object> map=new HashMap<>();
                //互动教学id
                map.put("activityId",activityMap.get("activity_id").toString());
                //活动名称
                map.put("activityName",activityMap.get("name").toString());
                //根据年级id查询，年级名称
                map.put("gradeName",activityMap.get("grade_name").toString());
                //教师名称
                map.put("teacherName",activityMap.get("teacherName").toString());
                //主讲教室id
                String roomId=String.valueOf(activityMap.get("room_id"));
                //教室id
                String nowRoomId=String.valueOf(activityMap.get("roomid"));
                //根据教室ID查询设备
                Map<String,String> host=areaHttpService.getHostByRoomId(nowRoomId);
                if(host!=null && !host.isEmpty()){
                    //直播流
                    map.put("rtmp",getRtmpAddr(host.get("mcu_code")));
                }else{
                    map.put("rtmp","");
                }
                //教室名称
                map.put("areaName",activityMap.get("roomName").toString());
                //主讲教室id
                map.put("areaId",nowRoomId);
                //教室id与主讲教室id一致，说明是主讲教室，否则是接收教室
                if(roomId.equals(nowRoomId)){
                    //主讲教室
                    map.put("classRoomType",NOCLASS);
                }else{
                    //接收教室
                    map.put("classRoomType",CLASSING);
                }
                //活动开始时间
                map.put("startTime",activityMap.get("start_time").toString());
                //活动结束时间
                map.put("endTime",activityMap.get("end_time").toString());
                //活动表与接收教室uuid
                map.put("uuid",activityMap.get("uuid").toString());
                String areaId=activityMap.get("areaId").toString();
                //根据地点ID获取地点数据
                Map<String,String> areaMap=activityService.getAreaNameMap(areaId);
                //省级名称
                map.put("provinceName",areaMap.get("provinceName"));
                map.put("cityName",areaMap.get("cityName"));
                map.put("countyName", areaMap.get("countyName"));
                map.put("schoolName", areaMap.get("schoolName"));
                map.put("activityStatus", "0");
                addActivityList.add(map);
            }
            //监管列表
            techSuperviseMap.put("items",addActivityList);
        }
        return techSuperviseMap;
    }

    /**
     * 获取未上课教学监管数据
     * @param proviceId 省级ID
     * @param cityId 市级ID
     * @param contyId 县区ID
     * @param schoolId 学校ID
     * @param currentPage 当前页码
     * @param pageSize 总页码
     * @return
     */
   private Map<String,Object> selectNoTeachingRoomsTeach(String proviceId,String cityId, String contyId, String schoolId,
                                                         String currentPage,String pageSize){
       //正上课的教室ids，多个“，”分割
        String teachingClassRoomIds=activityDao.getTeachingActivityRoomIds();
        Map<String,Object> resultMap=new HashMap<>();
        if(teachingClassRoomIds!=null && !"".equals(teachingClassRoomIds)){
            //查找未上课教室
            resultMap=userHttpService.getRoomExceptIds(proviceId,cityId,contyId,schoolId,teachingClassRoomIds,Integer.parseInt(currentPage),Integer.parseInt(pageSize));
        }else{
            //查找所有教室
            // TODO: 2018/5/30 方法调用错误，已经修改
            resultMap=userHttpService.getRoomByPage(proviceId,cityId, contyId, schoolId, Integer.parseInt(currentPage), Integer.parseInt(pageSize));
        }
        if(resultMap!=null && !resultMap.isEmpty()){
            List<Map<String,Object>> list=(List<Map<String,Object>>)resultMap.get("items");
            if(list!=null && !list.isEmpty()){
                for(Map<String,Object> map:list){
                    String areaId=String.valueOf(map.get("areaId"));
                    Map<String,String> host=areaHttpService.getHostByRoomId(areaId);
                    if(host!=null && !host.isEmpty()){
                        map.put("rtmp",getRtmpAddr(host.get("mcu_code")));
                    }
                }
            }
        }
        return resultMap;
   }

    /**
     * 获取一拖多教学监管数据
     * @param uuid          教学活动与接收表对应uuid
     * @param currentPage   当前页
     * @param pageSize      每页条数
     * @return  map         一拖多教学监管数据
     * @author caoqian
     */
    @Override
    public Map<String, Object> techSuperviseList(String uuid, String currentPage, String pageSize){
        Map<String,Object> techSupervise=new HashMap<>();
        if(uuid==null || "".equals(uuid) || currentPage==null || "".equals(currentPage) || pageSize==null || "".equals(pageSize)){
            logger.debug("uuid:"+uuid+",当前页currentPage:"+currentPage+",每页条数pageSize:"+pageSize+",获取一拖多教学监管数据失败，参数异常。");
            throw new IllegalArgumentException();
        }else {
            try {
                //获取第一条开始的位置
                int firstResult = (Integer.parseInt(currentPage) - 1) * Integer.parseInt(pageSize) < 0 ? 0 : (Integer.parseInt(currentPage) - 1) * Integer.parseInt(pageSize);
                List<Map<String, Object>> activityList = activityDao.techingListByUuidByPage(uuid, firstResult, Integer.parseInt(pageSize), false);
                int total = activityList.size();
                techSupervise.put("currentPage", currentPage);
                techSupervise.put("pageSize", pageSize);
                techSupervise.put("totalItems", total);
                List<Map<String, Object>> activityPageList = new ArrayList<>();
                //数据大于4条，监管数据进行分页
                if (total > MAXPLAYCLASS) {
                    activityPageList = activityDao.techingListByUuidByPage(uuid, firstResult, Integer.parseInt(pageSize), true);
                } else {
                    activityPageList = activityList;
                }
                formatActivityMessage(activityPageList);
                techSupervise.put("items", activityPageList);
            } catch (Exception e) {
                logger.error("根据教学活动uuid查询一拖多监管情况异常,uuid=" + uuid, e);
            }
        }
        return techSupervise;
    }

    /**
     * 格式化一拖多教学监管数据
     * @param activityPageList 教学监管数据
     * @return
     */
    private List<Map<String,Object>> formatActivityMessage(List<Map<String,Object>> activityPageList){
        if(activityPageList!=null && !activityPageList.isEmpty()){
            for(Map<String,Object> map:activityPageList){
                String areaId=map.get("areaId").toString();
                Map<String,String> areaMap=activityService.getAreaNameMap(areaId);
                map.put("cityName",areaMap.get("cityName"));
                map.put("countyName", areaMap.get("countyName"));
                map.put("schoolName", areaMap.get("schoolName"));
                //开始时间
                map.put("startTime",map.get("startTime").toString());
                //结束时间
                map.put("endTime",map.get("endTime").toString());
                //教师名称
                map.put("teacherName",map.get("teacherName").toString());
                //教室名称
                map.put("classRoomName",map.get("classRoomName").toString());
                //根据教室id查询终端信息
                Map<String,String> host=areaHttpService.getHostByRoomId(String.valueOf(map.get("classRoomId")));
                //直播流
                if(host!=null && !host.isEmpty()){
                    map.put("rtmp",getRtmpAddr(host.get("mcu_code")));
                }else {
                    map.put("rtmp", "");
                }
            }
        }
        return activityPageList;
    }

    /**
     * 获取教室直播流地址
     * @param mcuClientCode   终端编码号,如‘123407’
     * @return  String       教室直播流地址,格式‘http://192.168.16.108/live/123407’
     * @author caoqian
     */
    @Override
    public String getRtmpAddr(String mcuClientCode){
        //直播流ip
        String ip= ConfigUtil.getConfig("liveStreamIp")==null?"":ConfigUtil.getConfig("liveStreamIp").toString().trim();
        String rtmp="";
        if(mcuClientCode!=null && !StringUtils.isEmpty(mcuClientCode) && !StringUtils.isEmpty(ip)){
            rtmp="rtmp://"+ip+"/live/"+mcuClientCode;
            logger.debug("直播流地址rtmp================"+rtmp);
        }
        return rtmp;
    }

    /**
     * 获取终端截图,对接互动1.0主讲控制终端
     * @param ip  终端ip
     * @return
     * @author caoqian
     * add for 1.0
     */
    @Override
    public String getScreenPicForHk2000(String ip) {
        String serverIp;
        String screenPort=SCREENPORT;
        String resolution=RESOLUTION;
        if(ConfigUtil.getConfig("htech_ip")!=null) {
            serverIp = ConfigUtil.getConfig("htech_ip").toString().trim();
        }else{
            try {
                serverIp = InetAddress.getLocalHost().getHostAddress().toString();
            }catch (Exception e){
                serverIp="localhost";
            }
        }
        if(ConfigUtil.getConfig("screen_port")!=null) {
            screenPort=ConfigUtil.getConfig("screen_port").toString().trim();
        }
        if(ConfigUtil.getConfig("screen_resolution")!=null){
            resolution=ConfigUtil.getConfig("screen_resolution").toString().trim();
        }
        String screenUrl="http://"+serverIp+":"+screenPort+"/AddRtmpScreenShotTask";
        JSONObject paramsJson=new JSONObject();
        paramsJson.put("date",System.currentTimeMillis());
        paramsJson.put("cmd","RtmpScreenShot");
        paramsJson.put("cmd_op","AddRtmpScreenShotTask");
        paramsJson.put("srcURL",getRtmpAddr(deviceHttpService.getHostCodeByIp(ip)));
        paramsJson.put("Resolution",resolution);
        String picMsg= null;
        try {
            picMsg = HttpRequestUtil.load(screenUrl, paramsJson.toString());
        } catch (IOException e) {
            picMsg="";
        }
        if(picMsg!=null && !"".equals(picMsg)){
            JSONArray picArray= JSONArray.fromObject(picMsg);
            JSONObject picJson=JSONObject.fromObject(picArray.get(0));
            String success="0";
            if(picJson.get("code")!=null && success.equals(String.valueOf(picJson.get("code")))){
                picMsg=picJson.get("result").toString();
            }else{
                picMsg="";
            }
        }
        return picMsg;
    }

}
