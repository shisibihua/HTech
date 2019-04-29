package com.honghe.web.user.service.user;


import com.honghe.service.client.Result;
import com.honghe.web.user.service.campus.Directory;
import com.honghe.web.user.util.HttpServiceUtil;
import com.honghe.web.user.util.JsonUtil;
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
public class DeviceService {
    private Logger logger = Logger.getLogger(DeviceService.class);


    /**
     * 获取所有软件
     * @return
     */
    public List searchSys(){
        Map<String, String> params = new HashMap<>();
        Result result = new Result("",0,"");
        try {
            result = HttpServiceUtil.deviceService("getAllDeviceType", params);//请求服务并将结果返回
        }
        catch (Exception e) {
            logger.error("查找所有软件列表失败：" + e);
        }
        List list =new ArrayList();
        if (result.getCode() == 0 && result.getValue()!=null){
            list=(List) result.getValue();
        }
        return list;
    }

    /**
     * 获取设备树根节点
     * @return
     */
    public String searchDeviceTree(){
        String res="";
        Map<String, String> params = new HashMap<>();//用于存储参数
        Object obj = new Object();//用于类型转化
        try {
            params.put("type", "");//type为空查询所有设备树
            Result result = HttpServiceUtil.adService("areaSearch", params); //请求服务并将结果返回
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
            logger.error("设备树根节点获取失败：" + e);
        }
        return res;
    }
    /**
     * 获取设备树目录结构
     * @param directoryList 用户列表
     * @param sb 拼接字符串
     */
    private void recursive(List directoryList, StringBuilder sb) {
        if (!directoryList.isEmpty()) {
            sb.append("<ul>");
        }
        for (Object directory  : directoryList) {
            Directory obj = (Directory) JsonUtil.toJavaBean(new Directory(), (JSONObject) directory);//Json转化成Javabean
            String name = obj.getName();//获取名称
            String id = obj.getId();//获取id
            //拼接左侧机构树
            sb.append("<li>");
            sb.append("<a href='javascript:void(0)' id='" + id + "'><i></i><h6>" + name + "</h6><div class='menu-btns'></div></a>");
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
     * 搜索需要分配的设备
     * @param areaId
     * @param deviceType
     * @param searchWord
     * @return
     */
    public List searchAreaDevice(String areaId,String deviceType,String searchWord,String deviceId){
        Map<String, String> params = new HashMap<>();
        Result result = new Result("",0,"");
        params.put("areaId",areaId);//设备树ID
        params.put("deviceType",deviceType);//设备类型
        params.put("name",searchWord);//关键字
        if (deviceId!=null &&!"".equals(deviceId)){
            deviceId = deviceId.substring(0,deviceId.length()-1);
        }
        params.put("deviceId",deviceId);//已选设备ID
        try {
            result = HttpServiceUtil.deviceService("getAreaHostListByNames", params);//请求服务并将结果返回
        }
        catch (Exception e) {
            logger.error("查找该用户未分配的设备列表失败：" + e);
        }
        List list =new ArrayList();
        if (result.getCode() == 0 && result.getValue()!=null){
            list=(List) result.getValue();
        }
        return list;
    }
}
