package com.honghe.tech.httpservice.impl;

import com.honghe.service.proxy.Result;
import com.honghe.tech.httpservice.UserHttpService;
import com.honghe.tech.util.HttpServerUtil;
import com.honghe.tech.util.exceptionutil.HttpServerException;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Created by xinqinggang on 2018/1/29.
 */
@Service
public class UserHttpServiceImpl implements UserHttpService {
    private Logger logger=Logger.getLogger(UserHttpServiceImpl.class);
    private static final String SYS_NAME="hinteractiveteaching";  //互动模块名称
    /**
     * 根据用户id获取用户信息
     * @param id    用户id
     * @return map  用户信息
     * @author caoqian
     */
    @Override
    public Map<String,String> getUserById(int id)
    {
        Map<String,String> userMap=new HashMap<>();
        try{
            Map<String,String> params=new HashMap<>();
            params.put("userId",String.valueOf(id));
            Result result=HttpServerUtil.userService("userSearch",params);
            if(result!=null && result.getCode()==0 && result.getValue()!=null){
                userMap=(Map<String, String>) result.getValue();
            }else{
                logger.debug("根据用户id查询用户信息为空，userId="+id);
                return userMap;
            }
        }catch(Exception e){
            logger.error("根据用户id查询用户信息异常，userId="+id,e);
        }
        return userMap;
    }

    /**
     * 用户登录验证
     * @param loginName    用户名
     * @param userPwd      用户密码
     * @return  map        用户信息
     * @author caoqian
     */
    @Override
    public Map<String, String> checkUserLogin(String loginName, String userPwd) {
        Map<String,String> user=new HashMap<>();
        try{
            Map<String,String> params=new HashMap<>();
            params.put("loginName",loginName);
            params.put("userName",loginName);
            params.put("userPwd",userPwd);
            Result result= HttpServerUtil.userService("userCheck",params);
            if(result.getCode()==0 && result.getValue()!=null){
                user=(Map)result.getValue();
            } else{
                user=new HashMap<>();
            }
        }catch (Exception e){
            logger.error("用户验证异常，loginName="+loginName+",userPwd="+userPwd,e);
        }
        return user;
    }

    /**
     * 根据用户id获取用户角色
     * @param userId        用户id
     * @return List<Map>    [{"roleName": "省管理员","roleId": "145","userId": "89"}]   角色信息
     * @author caoqian
     */
    @Override
    public List<Map<String, String>> getUserRole(String userId) {
        List<Map<String,String>> re_values=null;
        try{
            Map<String,String> params=new HashMap<>();
            params.put("userId",userId);
            Result result=HttpServerUtil.userService("htechUserGetRoleByUserId",params);
            if(result.getCode()==0 && result.getValue()!=null){
                re_values=(List<Map<String,String>>) result.getValue();
            }else{
                re_values=new ArrayList<>();
            }
        }catch (Exception e){
            logger.error("根据用户id查询用户身份异常，userId="+userId,e);
        }
        return re_values;
    }

    /**
     * 根据用户id获取权限下的地点
     * @param userId  用户id
     * @return map    用户地点权限
     */
    @Override
    public Map<String,Object> getAreaByUserId(String userId) {
        Map<String,Object> map = new HashMap<>();
        try{
            Map<String,String> params=new HashMap<>();
            params.put("userId",userId);
            Result result=HttpServerUtil.userService("htechUserGetAreaByUserId",params);
            if(result.getCode()==0 && result.getValue()!=null){
                map = (Map)result.getValue();
                return map;
            }else{
                logger.error("根据用户id查询权限下的地点code有误，code=" + result.getCode());
            }
        }catch (Exception e){
            logger.error("根据用户id查询用户地点权限异常，userId="+userId,e);
        }
        return map;
    }

    /**
     * 根据学校id分页获取教室列表（支持教室名称）
     * @param schoolId 学校id
     * @param roomName 教室名称
     * @param roomType 主讲教室传“1”，所有教室传“0”，抛除普通教室
     * @param currentPage 当前页数
     * @param pageSize 每页数量
     * @param excRoomIds 需要排除的教室id串
     * @return 教室列表
     */
    @Override
    public Map<String, Object> getRoomBySchoolId(String schoolId, String roomName, String roomType, int currentPage, int pageSize,String excRoomIds) {
        Map<String,Object> map = new HashMap<>();

        try {
            Map<String,String> params = new HashMap<>();
            params.put("schoolId",schoolId);
            params.put("name",roomName);
            params.put("roomType",roomType);
            params.put("currentPage",String.valueOf(currentPage));
            params.put("pageSize",String.valueOf(pageSize));
            if(excRoomIds!=null)
            {
                params.put("roomIds",excRoomIds);
            }else {
                params.put("roomIds","");
            }
            Result result = HttpServerUtil.userService("htechUserGetRoomBySchoolId", params);
            if (result.getCode() == 0){
                map = (Map<String, Object>) result.getValue();
                return map;
            }else {
                logger.error("根据学校id分页获取教室code有误，code=" + result.getCode());
            }
        }catch (Exception e){
            logger.error("根据学校id分页获取教室异常，schoolId=" + schoolId,e);
        }
        return map;
    }

    /**
     * 根据学校id，获取教师信息 (支持教师名称)
     * @param schoolId 学校id
     * @param teacherName 教师名称
     * @param currentPage 当前页数
     * @param pageSize 每页大小
     * @param excTeacherIds 需要排除的教师id串
     * @return 教师列表
     */
    @Override
    public Map<String, Object> getTeacherBySchoolId(String schoolId, String teacherName, int currentPage, int pageSize,String excTeacherIds) {
        Map<String,Object> map = new HashMap<>();
        try {
            Map<String,String> params = new HashMap<>();
            params.put("schoolId",schoolId);
            params.put("name",teacherName);
            params.put("currentPage",String.valueOf(currentPage));
            params.put("pageSize",String.valueOf(pageSize));
            if(excTeacherIds!=null)
            {
                params.put("teacherIds",excTeacherIds);
            }else {
                params.put("teacherIds","");
            }
            Result result = HttpServerUtil.userService("htechUserGetTeacherBySchoolId", params);
            if (result.getCode() == 0){
                map = (Map<String, Object>) result.getValue();
                return map;
            }else {
                logger.error("根据学校id分页获取教师code有误，code=" + result.getCode());
            }
        }catch (Exception e){
            logger.error("根据学校id分页获取教师异常，schoolId=" + schoolId,e);
        }
        return map;
    }

    /**
     * 根据城市、区县、学校id获取教室
     * @param provinceId 省id
     * @param cityId 城市id
     * @param countyId 县区id 不是必须
     * @param schoolId 学校id 不是必须
     * @param currentPage 当前页数
     * @param pageSize 每页数量
     * @return 教室信息
     */
    @Override
    public Map<String, Object> getRoomByPage(String provinceId,String cityId, String countyId, String schoolId, int currentPage, int pageSize) {
        return getRoomByPage(provinceId,cityId,countyId,schoolId,null,currentPage,pageSize);
    }

    /**
     * 根据城市、区县、学校id分页获取除ids之外的教室
     * @param provinceId 省id
     * @param cityId 城市id
     * @param countyId 县区id 不是必须
     * @param schoolId 学校id 不是必须
     * @param roomIds 需要排除的id串 逗号分隔
     * @param currentPage 当前页数
     * @param pageSize 每页数量
     * @return 教室信息
     */
    private Map<String,Object> getRoomByPage(String provinceId,String cityId, String countyId, String schoolId, String roomIds, int currentPage, int pageSize){
        Map<String,Object> map = new HashMap<>();
        String method;
        try {
            Map<String,String> params = new HashMap<>();
            params.put("provinceId", String.valueOf(provinceId));
            params.put("cityId", String.valueOf(cityId));
            params.put("countyId",String.valueOf(countyId));
            params.put("schoolId",schoolId);
            if (roomIds != null && !"".equals(roomIds)){
                //排除ids
                params.put("roomIds",roomIds);
                method = "htechUserGetRoomExceptIds";
            }else {
                method = "htechUserGetRoomByPage";
            }
            params.put("currentPage",String.valueOf(currentPage));
            params.put("pageSize",String.valueOf(pageSize));

            Result result = HttpServerUtil.userService(method, params);
            if (result.getCode() == 0){
                map = (Map<String, Object>) result.getValue();
                return map;
            }else {
                logger.error("根据城市、区县、学校id分页获取教室code有误，code=" + result.getCode());
            }
        }catch (Exception e){
            logger.error("根据城市、区县、学校id分页获取教室异常，cityId=" + cityId + ",countyId=" + countyId + ",schoolId=" + schoolId + ",exceptIds=" + roomIds, e);
        }
        return map;
    }

    /**
     * 根据城市、区县、学校id分页获取除ids之外的教室
     * @param provinceId 省id
     * @param cityId 城市id
     * @param countyId 县区id 不是必须
     * @param schoolId 学校id 不是必须
     * @param roomIds 需要排除的id串 逗号分隔
     * @param currentPage 当前页数
     * @param pageSize 每页数量
     * @return 教室信息
     */
    @Override
    public Map<String, Object> getRoomExceptIds(String provinceId,String cityId, String countyId, String schoolId, String roomIds, int currentPage, int pageSize) {
        return getRoomByPage(provinceId,cityId,countyId,schoolId,roomIds,currentPage,pageSize);
    }



    /**
     * 根据省id获取下级市
     * @param provinceId 省id
     * @return 市列表
     */
    @Override
    public List<Map<String, String>> getCityByProvince(int provinceId) {
        List<Map<String,String>> list = new ArrayList<>();
        try {
            Map<String,String> params = new HashMap<>();
            params.put("provinceId",String.valueOf(provinceId));

            Result result = HttpServerUtil.userService("htechUserGetCityByProvince", params);
            if (result.getCode() == 0){
                list = (List<Map<String, String>>) result.getValue();
                return list;
            }else {
                logger.error("根据省id获取市列表 code有误，code=" + result.getCode());
            }
        }catch (Exception e){
            logger.error("根据省id获取市列表异常，provinceId=" + provinceId,e);
        }
        return list;
    }

    /**
     * 根据城市id获取区县列表
     * @param cityId 城市id
     * @return 区县列表
     */
    @Override
    public List<Map<String, String>> getCountyByCity(int cityId) {
        List<Map<String,String>> list = new ArrayList<>();
        try {
            Map<String,String> params = new HashMap<>();
            params.put("cityId",String.valueOf(cityId));

            Result result = HttpServerUtil.userService("htechUserGetCountyByCity", params);
            if (result.getCode() == 0){
                list = (List<Map<String, String>>) result.getValue();
                return list;
            }else {
                logger.error("根据城市id获取区县code有误，code=" + result.getCode());
            }
        }catch (Exception e){
            logger.error("根据城市id获取区县异常，cityId=" + cityId,e);
        }
        return list;
    }

    /**
     * 根据区县获取学校列表
     * @param countyId 区县id
     * @return 学校列表
     */
    @Override
    public List<Map<String, String>> getSchoolByCounty(int countyId) {
        List<Map<String,String>> list = new ArrayList<>();
        try {
            Map<String,String> params = new HashMap<>();
            params.put("countyId",String.valueOf(countyId));

            Result result = HttpServerUtil.userService("htechUserGetCampusByCounty", params);
            if (result.getCode() == 0){
                list = (List<Map<String, String>>) result.getValue();
                return list;
            }else {
                logger.error("根据区县id获取学校code有误，code=" + result.getCode());
            }
        }catch (Exception e){
            logger.error("根据区县获取学校异常，countyId=" + countyId,e);
        }
        return list;
    }
    /**
     * 根据地点获取教室ids，多个','隔开
     * @param provinceId      省id
     * @param cityId         市id
     * @param countyId       区/县id
     * @param schoolId       学校id
     * @return
     */
    @Override
    public String getRoomIdsByAreaId(String provinceId, String cityId, String countyId, String schoolId) {
        String areaIds="";
        try {
            Map<String,String> params = new HashMap<>();
            params.put("provinceId",provinceId);
            params.put("cityId",cityId);
            params.put("countyId",countyId);
            params.put("schoolId",schoolId);
            Result result = HttpServerUtil.userService("htechUserGetRoomByArea", params);
            if (result.getCode() == 0&&result.getValue()!=null){
                areaIds =  String.valueOf(result.getValue());
            }
        }catch (Exception e){
            logger.error("根据地点获取教室ids异常，areaIds=" + areaIds,e);
        }
        return areaIds;
    }

    /**
     * 根据地点获取不同类型教室数量，多个','隔开
     * @param provinceId      省id
     * @param cityId         市id
     * @param countyId       区/县id
     * @param schoolId       学校id
     * @return
     */
    @Override
    public Map<String,Integer> getRoomsByAreaIdAndType(String provinceId, String cityId, String countyId, String schoolId) {
        Map<String,Integer> roomMap=null;
        try {
            Map<String,String> params = new HashMap<>();
            params.put("provinceId",provinceId);
            params.put("cityId",cityId);
            params.put("countyId",countyId);
            params.put("schoolId",schoolId);
            Result result = HttpServerUtil.userService("htechUserGetRoomCount", params);
            if (result.getCode() == 0&&result.getValue()!=null){
                roomMap = JSONObject.fromObject(result.getValue());
            }
        }catch (Exception e){
            logger.error("根据地点获取不同教室类型数量异常，roomMap=" + roomMap.toString(),e);
        }
        return roomMap;
    }

    @Override
    public List<Map<String, String>> getUserPermissionByUserId(String userId) {
        List<Map<String, String>> re_value=new ArrayList<>();
        try {
            if(userId==null || "".equals(userId)){
                logger.debug("用户id为空，获取用户权限失败。");
                throw new IllegalArgumentException();
            }else {
                Map<String, String> params = new HashMap<>();
                params.put("userId", userId);
                params.put("sys", SYS_NAME);
                Result result = HttpServerUtil.userService("rolePermissionByUserId", params);
                if (result.getCode() == 0 && result.getValue()!=null && !result.getValue().equals("null")) {
                    re_value = (List<Map<String, String>>) result.getValue();
                }
            }
        }catch (Exception e){
            logger.error("根据用户id获取用户权限列表异常,userId="+userId ,e);
        }
        return re_value;
    }

    /**
     * 根据地点id及地点类型获取父级地点名称
     * @param areaType 地点类型
     * @param areaId 地点id
     * @return 父级地点信息
     */
    @Override
    public Map<String,Object> getParentArea(String areaType, String areaId)
    {
        Map<String, Object> re_value=new HashMap<>();
        try {
            Map<String, String> params = new HashMap<>();
            params.put("type", areaType);
            params.put("areaId", areaId);
            Result result = HttpServerUtil.userService("htechUserGetAreaParentById", params);
            if (result.getCode() == 0&&result.getValue()!=null) {
                re_value = (Map<String, Object>) result.getValue();
            }
        }catch (Exception e){
            logger.error("根据用户id获取用户权限列表异常,areaType="+areaType+",areaId="+areaId ,e);
        }
        return re_value;
    }

    @Override
    public Map<String, Object> getAllUserInfo() {
        Map<String, Object> re_value=new HashMap<>();
        Result result = null;
        try {
            Map<String, String> params = new HashMap<>();
            result = HttpServerUtil.userService("userFindAllUserInfo", params);
        }catch (HttpServerException e){
            logger.error("调用用户服务查询所有用户信息异常",e);
        }
        if (result.getCode() == 0&&result.getValue()!=null) {
            List<Map<String,String>> userInfo= (List<Map<String, String>>) result.getValue();
            if(userInfo!=null && !userInfo.isEmpty()){
                for(Map<String,String> user:userInfo){
                    String userId=user.get("userId");
                    if(userId!=null && !"".equals(userId)) {
                        re_value.put(userId, user);
                    }
                }
            }
        }
        return re_value;
    }

    @Override
    public Map<String,String> getStagesByUserId(String userId)
    {
        Map<String, String> re_value=new HashMap<>();
        try {
            Map<String, String> params = new HashMap<>();
            params.put("userId", userId);
            Result result = HttpServerUtil.userService("userFindStagesByUserId", params);
            if (result.getCode() == 0&&result.getValue()!=null) {
                re_value = (Map<String, String>) result.getValue();
            }
        }catch (Exception e){
            logger.error("根据用户id获取用户学段信息异常,userId="+userId ,e);
        }
        return re_value;
    }
}
