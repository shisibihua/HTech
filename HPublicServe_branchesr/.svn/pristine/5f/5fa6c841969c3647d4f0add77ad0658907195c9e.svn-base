package com.honghe.web.user.controller.user;

import com.honghe.service.client.Result;
import com.honghe.web.user.service.user.DeviceService;
import com.honghe.web.user.service.user.UserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by sunsun on 2016/9/27.
 */
@Controller
@RequestMapping("/User")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    DeviceService deviceService;
    /**
     * 用户详情-用户信息
     * @params pw 写入数据
     */
    @RequestMapping("/userDetails")
    public void userDetails(PrintWriter pw,String userId) {
        JSONObject obj = userService.userDetails(userId);
        pw.write(obj.toString());
    }
    /**
     * 用户详情-组织机构
     * @params pw 写入数据
     */
    @RequestMapping("/campusSearch")
    public void campusSearch(PrintWriter pw,String userId) {
        JSONObject obj = userService.campusSearch(userId);
        pw.write(obj.toString());
    }

    /**
     * 编辑用户-查询回显
     * @param pw 写入数据
     */
    @RequestMapping("/userSearch")
    public void userSearch(PrintWriter pw, String userId) {
        JSONObject obj = userService.userSearch(userId);
        pw.write(obj.toString());
    }
    /**
     * 编辑用户-修改用户
     * @param pw 写入数据
     */
    @RequestMapping("/userUpdate")
    public void userUpdate(HttpServletRequest req,PrintWriter pw, String userId, String userRealName, String userGender, String userBirthday, String userType, String userNum, String userMobile, String userEmail, String userAddress,String userAvatar) {
        JSONObject obj = userService.userUpdate(req,userId, userRealName, userGender, userBirthday, userType, userNum, userMobile, userEmail, userAddress,userAvatar);
        pw.write(obj.toString());
    }

    /**
     * 修改用户密码
     * @params pw 写入数据
     */
    @RequestMapping("/pwdUpdate")
    public void pwdUpdate(PrintWriter pw, String userId,String userPwd) {
        JSONObject obj = userService.pwdUpdate(userId, userPwd);
        pw.write(obj.toString());
    }

    /**
     * 添加用户
     * @param pw 写入数据
     */
    @RequestMapping("/userAdd")
    public void userAdd(HttpServletRequest req, PrintWriter pw, String userPwd, String userRealName, String userGender, String userBirthday, String userMobile, String userEmail, String userAddress, String userNum, String userType, String userAvatar) {
        JSONObject obj = userService.userAdd(req,userPwd, userRealName, userGender, userBirthday, userMobile, userEmail, userAddress, userNum, userType,userAvatar);
        pw.write(obj.toString());
    }

    /**
     * 用户身份查询
     * @params pw 写入数据
     */
    @RequestMapping("/userTypeSearch")
    public void userTypeSearch(PrintWriter pw) {
        String result = userService.userTypeSearch();
        pw.print(result);
    }

    /**
     * 修改用户状态-启动/禁用
     * @params pw 写入数据
     */
    @RequestMapping("/userUpdateStatus")
    public void userUpdateStatus(PrintWriter pw, String userId, String userStatus) {
        JSONObject obj = userService.userUpdateStatus(userId, userStatus);
        pw.write(obj.toString());
    }

    /**
     * 获取用户列表（分页查询）
     * @params pw 写入数据
     */
    @RequestMapping("/userSearchByPage")
    public void userSearchByPage(PrintWriter pw,String page ,String pageSize, String searchWord, String userStatus,String userType,String campusId,String userId,HttpServletRequest req) {
        String obj = userService.userSearchByPage(page ,pageSize,searchWord,userStatus,userType,campusId,userId,req);
        pw.write(obj.toString());
    }
    public void findUserByPage(PrintWriter pw,String page,String pageSize,String searchWord,String userType,String campusId){
        String listStr = userService.findUserByPage(page,pageSize,searchWord,campusId,userType);
        pw.write(listStr);
    }
    /**
     * 用户分配角色-存入缓存
     * @params pw 写入数据
     */
    @RequestMapping("/roleAllot")
    public void roleAllot(PrintWriter pw,String user_ids,String roles_ids) {
        JSONObject obj = userService.roleAllot(user_ids,roles_ids);
        pw.write(obj.toString());
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
//
//    /**
//     * 刷新cache内容
//     * @params pw 写入数据
//     */
//    @RequestMapping("/role2permissionCacheReload")
//    public void role2permissionCacheReload(PrintWriter pw) {
//        JSONObject obj = userService.role2permissionCacheReload();
//        pw.write(obj.toString());
//    }

    /**
     * 导入-下载用户模板
     * @params pw 写入数据
     */
    @RequestMapping("/userDownloadMould")
    public void userDownloadMould(HttpServletRequest req,HttpServletResponse resp) {
        userService.userDownloadMould(req,resp);
    }

    /**
     * 导入-用户导入
     * @params pw 写入数据
     * 用户导入成功后走缓存
     */
    @RequestMapping("/userUpload")
    public void userUpload(PrintWriter pw,HttpServletRequest req,HttpServletResponse response) {
        JSONObject obj = userService.userUpload(req,response);
        pw.write(obj.toString());
    }


    /**
     * 设置用户头像
     * @param pw
     * @param req
     * @param x
     * @param y
     * @param w
     * @param h
     * @param width
     * @param height
     */
    @RequestMapping("/imageUpload")
    public void imageUpload(PrintWriter pw,HttpServletRequest req,String x,String y,String w,String h,String width,String height,String extension) {

        JSONObject obj = userService.imageUpload(req,Integer.parseInt(x),Integer.parseInt(y),Integer.parseInt(w),Integer.parseInt(h),extension);
        pw.write(obj.toString());
    }

    /**
     * 导出-用户数据导出
     * @params pw 写入数据
     */
    @RequestMapping("/userExportExcel")
    public void userExportExcel(HttpServletRequest req,HttpServletResponse resp){
        userService.userExportExcel(req, resp);
    }

    /**
     * 导出-用户数据导出失败
     * @params pw 写入数据
     */
    @RequestMapping("/userDownloadFailed")
    public void userDownloadFailed(HttpServletRequest req, HttpServletResponse resp) {
        userService.userDownloadFailed(req, resp);
    }

    /**
     * 给机构分配管理员
     * @param pw
     * @param userId
     * @param campusIds
     */
    @RequestMapping("/editUserAdmin")
    public void addUserAdmin(PrintWriter pw,String userId,String campusIds){
        JSONObject obj = userService.editUserAdmin(userId,campusIds);
        pw.write(obj.toString());
    }
    @RequestMapping("/getUserInfo")
    public void getUserInfo(PrintWriter pw,String userId){
        JSONObject obj = userService.getUserInfo(userId);
        pw.write(obj.toString());
    }
}