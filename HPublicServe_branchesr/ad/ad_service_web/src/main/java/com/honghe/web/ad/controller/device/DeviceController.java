package com.honghe.web.ad.controller.device;

import com.honghe.web.ad.service.device.DeviceService;
import com.honghe.web.ad.service.user.UserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by qinzhihui on 2016/10/9 0009.
 */
@Controller
@RequestMapping("/Device")
public class DeviceController {
    @Autowired
    UserService userService;
    @Autowired
    DeviceService deviceService;

    /**
     * 获取左侧用户组织树
     * @param pw
     */
    @RequestMapping("/searchCumpus")
    public void searchCumpus(PrintWriter pw){
        JSONObject json = new JSONObject();
        String cumpus = userService.searchCumpus();//组织树结构
        List userTypeList = userService.searchUserType();//用户身份列表
        List sysList = deviceService.searchSys();//设备分类列表
        if (cumpus!= null) {
            if (!json.isEmpty()){
                json.clear();
            }
            json.put("cumpus",cumpus);
            json.put("userTypeList",userTypeList);
            json.put("sysList",sysList);
            pw.write(json.toString());
        }
    }
    /**
     * 获取用户列表
     * @param campusId 组织机构id
     * @param searchWord 搜索词
     * @param pw
     */
    @RequestMapping("/searchUsers")
    public void searchUsers(String campusId,String searchWord,String userType,PrintWriter pw) {
        JSONObject json = new JSONObject();
        List userList = userService.searchUsers(campusId,userType, searchWord);
        if (userList != null) {
            if (!json.isEmpty()){
                json.clear();
            }
            json.put("userList",userList);
            pw.write(json.toString());
        }
    }

    /**
     * 获取用户的设备详情列表
     * @param userId
     * @param pw
     */
    @RequestMapping("/searchDevice")
    public void searchDevice(String userId,String deviceType,String searchWord,PrintWriter pw){
        JSONObject json = new JSONObject();
        if (deviceType==null){
            deviceType="";
        }
        if (searchWord==null){
            searchWord="";
        }
        List deviceList = userService.searchDevice(userId,deviceType,searchWord);
        if (deviceList != null) {
            if (!json.isEmpty()){
                json.clear();
            }
            json.put("deviceList",deviceList);
            pw.write(json.toString());
        }
    }

    /**
     * 删除用户角色关系
     * @param id
     * @param pw
     */
    @RequestMapping("/deleteUser2Device")
    public void deleteUser2Device(String id,PrintWriter pw){
        JSONObject json = new JSONObject();
        String result = userService.deleteUser2Device(id);
        if (result!=null){
            if (!json.isEmpty()){
                json.clear();
            }
            json.put("msg",result);
            pw.write(json.toString());
        }
    }

    /**
     * 获取设备树结构
     * @param userId
     * return
     */
    @RequestMapping("/searchDeviceTree")
    public void searchDeviceTree(String userId,PrintWriter pw){
        JSONObject json = new JSONObject();
        String deviceTree =deviceService.searchDeviceTree();//获取设备树结构
        List sysList = deviceService.searchSys();//设备类型列表
        if (!json.isEmpty()){
            json.clear();
        }
        json.put("deviceTree", deviceTree);
        json.put("sysList", sysList);
        json.put("userId",userId);
        pw.write(json.toString());
    }

    /**
     * 搜索需要分配的设备
     * @param areaId
     * @param deviceType
     * @param searchWord
     * @param pw
     */
    @RequestMapping("/searchAreaDevice")
    public void searchAreaDevice(String areaId,String deviceType,String searchWord,String deviceId,PrintWriter pw){
        JSONObject json = new JSONObject();
        List allDeviceList = deviceService.searchAreaDevice(areaId,deviceType,searchWord,deviceId);
        if (allDeviceList!=null){
            if (!json.isEmpty()){
                json.clear();
            }
            json.put("allDeviceList",allDeviceList);
            pw.write(json.toString());
        }
    }

    /**
     * 分配设备
     * @param userId
     * @param deviceId
     * @param pw
     */
    @RequestMapping("/allotDevice")
    public void allotDevice(String userId,String deviceId,PrintWriter pw){
        JSONObject json = new JSONObject();
        String result = userService.allotUser2Device(userId,deviceId);
        if (result!=null){
            if (!json.isEmpty()){
                json.clear();
            }
            json.put("msg",result);
            pw.write(json.toString());
        }
    }
}
