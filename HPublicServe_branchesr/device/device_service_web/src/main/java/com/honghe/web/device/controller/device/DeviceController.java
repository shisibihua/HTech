package com.honghe.web.device.controller.device;

import com.honghe.web.device.service.device.DeviceService;
import jodd.joy.page.PageData;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;

/**
 * Created by sky on 2016/10/8.
 */
@Controller
@RequestMapping("/device")
public class DeviceController {
    Logger logger = Logger.getLogger(DeviceController.class);
    @Autowired
    DeviceService deviceService;

    /**
     * 获取所有设备
     *
     * @param page     当前页
     * @param pageSize 每页设备数
     * @param pw
     */
    @RequestMapping("/getDeviceList")
    public void getDeviceList(String page, String pageSize, PrintWriter pw) {
        JSONObject json = new JSONObject();
        PageData device = deviceService.getDeviceList(page, pageSize);
        json.put("device", device);
        pw.write(json.toString());
    }

    /**
     * 获取现有设备数量
     *
     * @param pw
     */
    @RequestMapping("/getdCount")
    public void getdCount(PrintWriter pw) {
        JSONObject json = new JSONObject();
        Map map = deviceService.getdCount();
        json.put("count", map);
        pw.write(json.toString());
    }

    /**
     * 自动发现设备数量
     *
     * @param pw
     */
    @RequestMapping("/addDevice")
    public void addDevice(PrintWriter pw) {
        JSONObject json = new JSONObject();
        String deviceType = "";
        Map map = deviceService.discover(deviceType);
        if (Integer.parseInt(String.valueOf(map.get("discoverCount"))) > 0) {
            Map deviceMap = deviceService.addDevice(deviceType);
            map.put("addResult", deviceMap.get("addResult"));
            map.put("isMuti", deviceMap.get("isMuti"));
        }
        json.put("discover", map);
        pw.write(json.toString());
    }

    /**
     * 删除设备
     *
     * @param hostIdStr 要删除设备的id
     * @param hostIpStr 要删除设备的ip
     * @param pw
     */
    @RequestMapping("/deleteDevice")
    public void deleteDevice(String hostIdStr, String hostIpStr, String hostTypeStr, PrintWriter pw) {
        JSONObject json = new JSONObject();
        Map map = deviceService.deldevice(hostIdStr, hostIpStr, hostTypeStr);
        json.put("result", map);
        pw.write(json.toString());
    }

    /**
     * 按条件查询设备信息
     *
     * @param conditions  查询设备所需条件：ip、名称、类型
     * @param currentPage 当前页
     * @param pageSize    每页设备数
     * @param deviceType  设备类型
     * @param pw
     */
    @RequestMapping("/searchByNameOrIp")
    public void searchByNameOrIp(String conditions, String currentPage, String pageSize, String deviceType, PrintWriter pw) {
        PageData pageData = deviceService.getSearchHostList(conditions, currentPage, pageSize, deviceType);
        JSONObject json = new JSONObject();
        json.put("device", pageData);
        pw.write(json.toString());
    }

    /**
     * 获取现有设备类型
     *
     * @param
     */

    @RequestMapping("/getAllType")
    public void getAllType(PrintWriter pw) {
        JSONObject json = new JSONObject();
        List list = new ArrayList();
        list = deviceService.getAllList();
        json.put("typeList", list);
        pw.write(json.toString());
    }

    /**
     * 获取录播子类型
     *
     * @param model
     * @return
     */
    @RequestMapping("/getHrecType")
    public String getHrecType(ModelMap model) {
        List list = new ArrayList();
        list = deviceService.getHrecType();
        model.addAttribute("hrecType", list);
        return "device_add";
    }

    /**
     * 添加设备
     *
     * @param deviceType   设备类型
     * @param className    设备名称
     * @param hostPort     端口号
     * @param hostChannel  通道号
     * @param userName     用户名
     * @param password     密码
     * @param ip           设备ip
     * @param isTour       是否支持巡课
     * @param metString    通道地址
     * @param cameraString 通道名称
     * @param pw
     */
//    @RequestMapping("/mannelAdd")
//    public void mannelAdd(String deviceType, String className, String hostPort, String hostChannel, String userName, String password, String ip, String isTour, String metString, String cameraString, PrintWriter pw) {
//        JSONObject json = new JSONObject();
//        Map map = deviceService.manualAdd(deviceType, className, hostPort, hostChannel, userName, password, ip, isTour, metString, cameraString);
//        if (map.size() == 0) {
//            json.put("result", 0);
//        } else {
//            json.put("result", map);
//        }
//        pw.write(json.toString());
//    }

    @RequestMapping("/mannelAdd")
    public void mannelAdd(String deviceType, String className, String hostPort, String hostChannel, String userName, String password, String ip, String isTour, String metString, String cameraString, String mcuClientCode,PrintWriter pw) {
        JSONObject json = new JSONObject();
        Map map = deviceService.manualAdd(deviceType, className, hostPort, hostChannel, userName, password, ip, isTour, metString, cameraString, mcuClientCode);
        if (map.size() == 0) {
            json.put("result", 0);
        } else {
            json.put("result", map);
        }
        pw.write(json.toString());
    }

    /**
     * 更新设备名
     *
     * @param hostId   设备的hostId
     * @param hostName 设备的名字
     */
    @RequestMapping("/updateHostName")
    public void updateHostName(String hostId, String hostName, PrintWriter pw) {
        JSONObject json = new JSONObject();
        boolean flag = false;
        try {
            flag = deviceService.updateHostName(hostId, hostName);
        } catch (Exception e) {
            logger.debug("根据Id获取设备信息异常", e);
        }
        Map map = new HashMap();
        map.put("success", flag);
        map.put("msg", flag ? "修改成功" : "修改失败");
        json.put("result", map);
        pw.write(json.toString());
    }

    /**
     * 读取配置文件里的设备类型
     *
     * @param pw
     */
    @RequestMapping("/getTypeByProperties")
    public void getTypeByProperties(PrintWriter pw) {
        JSONObject json = new JSONObject();
        PropertyResourceBundle res = (PropertyResourceBundle) PropertyResourceBundle.getBundle("deviceType");
        String nameStr = "";
        String valStr = res.getString("value");
        try {
            nameStr = new String(res.getString("name").getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("获取配置文件里的信息失败", e);
        }
        json.put("name", nameStr);
        json.put("value", valStr);
        pw.write(json.toString());
    }

}
