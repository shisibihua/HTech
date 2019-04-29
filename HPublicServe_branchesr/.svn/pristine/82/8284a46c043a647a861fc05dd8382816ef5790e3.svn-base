package com.honghe.web.ad.service.user;


import com.honghe.service.client.Result;
import com.honghe.web.ad.util.Directory;
import com.honghe.web.ad.util.HttpServiceUtil;
import com.honghe.web.ad.util.JsonUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qinzhihui on 2016/10/9 0009.
 */
@Service
public class UserService {
    private Logger logger = Logger.getLogger(UserService.class);


    /**
     * 获取左侧树根节点
     * @return
     */
    public String searchCumpus(){
        String res="";
        Map<String, String> params = new HashMap<>();//用于存储参数
        Object obj = new Object();//用于类型转化
        try {
            Result result = HttpServiceUtil.adService("campusSearch", params); //请求服务并将结果返回
            JSONObject resultValue = (JSONObject) result.getValue();//将value转化成JSON
            Directory campusTree;
            if (result.getValue() != null) {
                obj = JsonUtil.toJavaBean(new Directory(), resultValue);//将Json转化成JavaBean 因为value与directory类型不符
            }
            if (obj instanceof Directory) {
                campusTree = (Directory) obj;//生成组织机构树
            } else {
                campusTree = new Directory("-1", "");//创建组织机构树根节点
            }

            StringBuilder sb = new StringBuilder(); //声明字符串变量对象
            //拼接组织机构树添加名称和按钮
            sb.append("<ul id='left_nav_ul'>" +
                    "<li>");
            sb.append("<a href='javascript:void(0)' id='" + campusTree.getId() + "'><i></i><h6>" + campusTree.getName() +
                    "</h6>" +
                    "</a>");
            this.recursive(campusTree.getDirectories(), sb);
            sb.append("</li>" +
                    "</ul>");
            res = sb.toString();//接受拼接的字符串并传递给controler
        }
        catch (Exception e) {
            logger.error("获取组织机构根节点失败：" + e);
        }
        return res;
    }
    /**
     * 获取左侧树
     * @param directoryList 用户列表
     * @param sb 拼接字符串
     */
    private void recursive(List directoryList, StringBuilder sb) {
        if (!directoryList.isEmpty()) {
            sb.append("<ul>");
        }
        for (Object directory  : directoryList) {
            Directory obj = (Directory) JsonUtil.toJavaBean(new Directory(),(JSONObject)directory);//Json转化成Javabean
            String name = obj.getName();//获取名称
            String id = obj.getId();//获取id
            //拼接左侧机构树
            sb.append("<li>");
            sb.append("<a href='javascript:void(0)' id='" + id + "'><i></i><h6>" + name + "</h6>\n" +
                    "</a>");
            if (obj.getDirectories().isEmpty()) {
                sb.append("</li>");
            }
            recursive(obj.getDirectories(), sb);//递归调用
        }
        if (!directoryList.isEmpty()) {
            sb.append("</ul>");
        }
    }
    /**
     * 点击机构获取用户表格
     * @param campusId 组织机构id
     * @param searchWord 搜索词
     * @return
     */
    public List searchUsers(String campusId,String userType,String searchWord)
    {
        Map<String, String> params = new HashMap<>();//用于存储参数
        params.put("campusId", campusId);
        params.put("userType",userType);
        params.put("searchWord", searchWord);
        Result result = new Result("",0,"");//初始化result
        try {
            result = HttpServiceUtil.adService("campus2UserSearch", params);//请求服务并将结果返回
        }
        catch (Exception e) {
            logger.error("获取组织机构下用户列表失败：" + e);
        }
        List list =new ArrayList();
        if (result.getCode() == 0 && result.getValue()!=null){
            list=(List) result.getValue();//获取服务返回的value
        }
        return list;
    }

    /**
     * 查询所有身份
     * @return
     */
    public List searchUserType(){
        List list =new ArrayList();
        Map<String, String> params = new HashMap<>();
        Result result = new Result("",0,"");//初始化result
        try {
            result = HttpServiceUtil.userService("userTypeSearch", params);
        }
        catch (Exception e) {
            logger.error("查询所有用户身份失败：" + e);
        }
        if (result.getCode() == 0 && result.getValue()!=null){
            list=(List) result.getValue();
        }
        return list;
    }

    /**
     * 查询用户所有设备
     * @param userId
     * @return
     */
    public List searchDevice(String userId,String deviceType,String searchWord){
        List list =new ArrayList();
        Map<String, String> params = new HashMap<>();
        params.put("userId",userId);
        params.put("dtypeName",deviceType);
        params.put("searchWord",searchWord);
        Result result = new Result("",0,"");//初始化result
        try {
            result = HttpServiceUtil.adService("user2DeviceSearch", params);
        }
        catch (Exception e) {
            logger.error("查找用户所有设备失败：" + e);
        }
        if (result.getCode() == 0 && result.getValue()!=null){
            list = (List)result.getValue();
        }
        return list;
    }

    /**
     * 删除用户设备关系
     * @param id
     * @return
     */
    public String deleteUser2Device(String id){
        String res="";
        Map<String, String> params = new HashMap<>();
        params.put("id",id);
        Result result = new Result("",0,"");//初始化result
        try {
            result = HttpServiceUtil.adService("user2DeviceDelete", params);
        }
        catch (Exception e) {
            logger.error("删除用户设备关系失败：" + e);
        }
        if (result.getCode() == 0 && result.getValue()!=null){
            res=result.getValue().toString();
        }
        return res;
    }

    /**
     * 给选择的人员分配设备
     * @param userId
     * @param deviceId
     * @return
     */
    public String allotUser2Device(String userId,String deviceId){
        String res="false";
        Map<String, String> params = new HashMap<>();
        params.put("userId",userId);
        Result result = new Result("",0,"");//初始化result
        try {
            result = HttpServiceUtil.adService("user2DeviceDelete", params);
            if (result!=null){
                res=result.getValue().toString()==""?"false":result.getValue().toString();
            }
        }
        catch (Exception e) {
            logger.error("角色用户关系删除失败：" + e);
            return res;
        }
        if (deviceId==null||"".equals(deviceId)){
            return res;
        }
        if (result.getCode() == 0 && Boolean.parseBoolean(result.getValue().toString())==true){
            params.put("deviceId",deviceId);
            try {
                result = HttpServiceUtil.adService("user2DeviceAdd", params);
            }
            catch (Exception e) {
                logger.error("角色用户关系添加失败：" + e);
            }
            if (result.getCode() == 0 && Boolean.parseBoolean(result.getValue().toString())==true){
                res="true";
            }
        }
        return res;
    }
}
