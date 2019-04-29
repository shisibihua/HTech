package com.honghe.web.device.controller.group;

import com.honghe.service.client.Result;
import com.honghe.web.device.service.group.GroupService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by sunchao on 2016/10/8.
 */

@Controller
@RequestMapping("/Group")

public class GroupController {
    @Autowired
    GroupService groupService;
    JSONObject json = new JSONObject();

    /**
     * 初始化右侧设备
     *
     * @param pw
     */
    @RequestMapping("/initDevice")
    public void initDevice(String type, PrintWriter pw) {
        Result res = groupService.initDevice(type);//获取服务中返回的结果
        if (res.getValue() != null) {
            json.put("value", res.getValue());
        }
        json.put("code", res.getCode());
        pw.write(json.toString());//将value和code传给前台
    }


    /**
     * 获取地点左侧树
     *
     * @param pw
     */
    @RequestMapping("/searchCampus")
    public void searchCampus(String userId, PrintWriter pw) {
        String res = groupService.searchCampus(userId);
        if (res != null) {
            pw.write(res);//拼接的字符串
        }
    }

    /**
     * 添加地点
     *
     * @param pId  地点id
     * @param name 地点名称
     * @param pw
     */
    @RequestMapping("/addCampus")
    public void addCampus(String pId, String name, String areaType, String campus, String number, String isSelect, String remark, PrintWriter pw) {
        Result res = groupService.addCampus(pId, name, areaType, campus, number, isSelect, remark);//获取服务中返回的结果
        if (res.getValue() != null) {
            json.put("value", res.getValue());
        }
        json.put("code", res.getCode());
        pw.write(json.toString());//将value和code传给前台
    }

    /**
     * 修改地点
     *
     * @param id   地点id
     * @param name 地点名称
     * @param pw
     */
    @RequestMapping("/updateCampus")
    public void updateCampus(String id, String name, String pId, String areaType, String campus, String number, String isSelect, String remark, PrintWriter pw) {
        Result res = groupService.updateCampus(id, name, pId, areaType, campus, number, isSelect, remark);//获取服务中返回的结果
        if (res.getValue() != null) {
            json.put("value", res.getValue());
        }
        json.put("code", res.getCode());
        pw.write(json.toString());//将value和code传给前台
    }

    /**
     * 删除地点
     *
     * @param id 地点id
     * @return
     */
    @RequestMapping("/deleteCampus")
    public void deleteCampus(String id, PrintWriter pw) {
        Result res = groupService.deleteCampus(id);//获取服务中返回的结果
        if (res.getValue() != null) {
            json.put("value", res.getValue());
        }
        json.put("code", res.getCode());
        pw.write(json.toString());//将value和code传给前台
    }

    /**
     * 删除组织机构成员
     *
     * @param areaId   地点id
     * @param deviceId 设备id
     * @param pw
     */
    @RequestMapping("/deleteDevices")
    public void deleteUser(String areaId, String deviceId, PrintWriter pw) {
        Result res = groupService.deleteDevices(areaId, deviceId);//获取服务中返回的结果
        if (res.getValue() != null) {
            json.put("value", res.getValue());
        }
        json.put("code", res.getCode());
        pw.write(json.toString());//将value和code传给前台
    }

    /**
     * 获取设备信息
     *
     * @param hostId 设备id
     * @param pw
     */
    @RequestMapping("/loadDevices")
    public void loadUsers(String hostId, PrintWriter pw) {
        Map res = groupService.loadDevices(hostId);
        JSONObject json = new JSONObject();
        json.put("userinfo", res);
        if (res != null) {
            pw.write(json.toString()); //拼接的字符串
        }
    }

    /**
     * 获取设备信息(初始化或搜索)
     *
     * @param page       当前页
     * @param pageSize   总页数
     * @param areaId     地点id
     * @param deviceType 设备类型
     * @param conditions 搜索词
     * @param pw
     */
    @RequestMapping("/searchDevice")
    public void searchDevice(String page, String pageSize, String areaId, String deviceType, String conditions, PrintWriter pw) {
        Result res = groupService.searchDevice(page, pageSize, areaId, deviceType, conditions);//获取服务中返回的结果
        if (res.getValue() != null) {
            Map map = (Map) res.getValue();
            pw.write(map.toString());//将value和code传给前台
        }
    }

    /**
     * 分配人员到组织机构
     *
     * @param areaId   地点id
     * @param deviceId 设备id
     * @param pw
     */
    @RequestMapping("/allocateDevices")
    public void allocateDevices(String areaId, String deviceId, PrintWriter pw) {
        Result res = groupService.allocateDevices(areaId, deviceId);//获取服务中返回的结果
        if (res.getValue() != null) {
            json.put("value", res.getValue());
        }
        json.put("code", res.getCode());
        pw.write(json.toString());//将value和code传给前台
    }

    /**
     * 获取所有设备
     *
     * @param pw
     */
    @RequestMapping("/deviceType")
    public void deviceType(PrintWriter pw) {
        Result res = groupService.deviceType();//获取服务中返回的结果
        if (res.getValue() != null) {
            json.put("value", res.getValue());
        }
        json.put("code", res.getCode());
        pw.write(json.toString());//将value和code传给前台
    }

    /**
     * 获取所有地点
     *
     * @param pw
     */
    @RequestMapping("/areaType")
    public void areaType(PrintWriter pw) {
        Result res = groupService.areaType();//获取服务中返回的结果
        if (res.getValue() != null) {
            json.put("value", res.getValue());
        }
        json.put("code", res.getCode());
        pw.write(json.toString());//将value和code传给前台
    }

    /**
     * 获取所有校区
     *
     * @param pw
     */
    @RequestMapping("/getCampus")
    public void getCampus(PrintWriter pw) {
        Result res = groupService.getCampus();//获取服务中返回的结果
        if (res.getValue() != null) {
            json.put("value", res.getValue());
        }
        json.put("code", res.getCode());
        pw.write(json.toString());//将value和code传给前台
    }

    /**
     * 获取所有校区
     *
     * @param pw
     */
    @RequestMapping("/getRoom")
    public void getRoom(String p_id, PrintWriter pw) {
        Result res = groupService.getRoom(p_id);//获取服务中返回的结果
        if (res.getValue() != null) {
            json.put("value", res.getValue());
        }
        json.put("code", res.getCode());
        pw.write(json.toString());//将value和code传给前台
    }

    /**
     * 获取所有校区
     *
     * @param pw
     */
    @RequestMapping("/getAreaDetail")
    public void getAreaDetail(String areaId, PrintWriter pw) {
        Result res = groupService.getAreaDetail(areaId);//获取服务中返回的结果
        if (res.getValue() != null) {
            json.put("value", res.getValue());
        }
        json.put("code", res.getCode());
        pw.write(json.toString());//将value和code传给前台
    }
//    /**
//     * 导入-下载用户模板
//     * @params pw 写入数据
//     */
//    @RequestMapping("/groupDownloadMould")
//    public void userDownloadMould(HttpServletRequest req,HttpServletResponse resp) {
//        groupService.groupDownloadMould(req,resp);
//    }
}