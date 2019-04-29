package com.honghe.web.device.service.group;


import com.honghe.service.client.Result;
import com.honghe.web.device.util.Directory;
import com.honghe.web.device.util.HttpServiceUtil;
import com.honghe.web.device.util.JsonUtil;
import com.honghe.web.device.util.TypeTransform;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sunchao on 2016/10/6.
 */
@Service
public class GroupService {

    Logger logger = Logger.getLogger(GroupService.class);
    String res = "";

    /**
     * 初始化右侧设备(根节点设备)
     *
     * @param type
     * @return
     */
    public Result initDevice(String type) {
        Map<String, String> params = new HashMap<>();//用于存储参数
        params.put("type", type);//存储设备类型
        Result result = new Result("", 0, "");//初始化result
        try {
            result = HttpServiceUtil.adService("areaSearch", params);//请求服务并将结果返回
        } catch (Exception e) {
            logger.error("初始化右侧设备失败：" + e);
        }
        return result;
    }

    /**
     * 添加组织
     *
     * @param pId  组织机构id
     * @param name 组织机构名称
     * @return
     */
    public Result addCampus(String pId, String name, String areaType, String campus, String number, String isSelect, String remark) {
        Map<String, String> params = new HashMap<>();//用于存储参数
        params.put("pId", pId);
        params.put("name", name);
        params.put("areaType", areaType);
        params.put("campus", campus);
        params.put("number", number);
        params.put("isSelect", isSelect);
        params.put("remark", remark);
        Result result = new Result("", 0, "");//初始化result
        try {
            result = HttpServiceUtil.adService("areaAdd", params);//请求服务并将结果返回
        } catch (Exception e) {
            logger.error("添加组织机构失败：" + e);
        }
        return result;
    }

    /**
     * 修改组织名称
     *
     * @param id   地点id
     * @param name 地点名称
     * @return
     */
    public Result updateCampus(String id, String name, String pId, String areaType, String campus, String number, String isSelect, String remark) {
        Map<String, String> params = new HashMap<>();//用于存储参数
        params.put("id", id);
        params.put("name", name);
        params.put("pId", pId);
        params.put("areaType", areaType);
        params.put("campus", campus);
        params.put("number", number);
        params.put("isSelect", isSelect);
        params.put("remark", remark);
        Result result = new Result("", 0, "");//初始化result
        try {
            result = HttpServiceUtil.adService("areaUpdate", params);//请求服务并将结果返回

        } catch (Exception e) {
            logger.error("修改组织机构失败：" + e);
        }
        return result;
    }

    /**
     * 删除组织
     *
     * @param id 地点id
     * @return
     */
    public Result deleteCampus(String id) {
        Map<String, String> params = new HashMap<>();//用于存储参数
        params.put("id", id);//存储参数
        Result result = new Result("", 0, "");//初始化result
        try {
            result = HttpServiceUtil.adService("areaDelete", params);//请求服务并将结果返回

        } catch (Exception e) {
            logger.error("删除组织机构失败：" + e);
        }
        return result;
    }


    /**
     * 获取组织结构左侧树
     *
     * @return
     */
    public String searchCampus(String userId) {
        Map<String, String> params = new HashMap<>();//用于存储参数
        try {
            Map<String, String> getRootAreaParams = new HashMap<>();//用于存储参数
            Result rootAreaIdResult = HttpServiceUtil.areaService("area_getRootAreaId", getRootAreaParams);//请求服务并将结果返回
            String rootAreaId="1";
            if(rootAreaIdResult.getCode()==0 && rootAreaIdResult.getValue()!=null){
                rootAreaId=String.valueOf(rootAreaIdResult.getValue());
            }
            params.put("id", rootAreaId);
            Result schoolResult = HttpServiceUtil.areaService("area_areainfo", params);//请求服务并将结果返回
            if (schoolResult.getValue() != null && schoolResult.getCode() == 0) {
                JSONArray schoolValue = (JSONArray) schoolResult.getValue();//将value转化成JSON
                if (schoolValue.size() <= 0) {
                    res = "<div style=\"margin-left: 20px;margin-top: 20px;\">暂无组织机构数据</div>";
                } else {
                    Object obj = new Object();//用于类型转化
                    params.clear();
                    params.put("userId", userId);
                    Result result = HttpServiceUtil.areaService("area_tree", params); //请求服务并将结果返回
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
                    sb.append("<a title= '" + campusTree.getName() + "' href='javascript:void(0)' id='" + campusTree.getId() + "'><i></i><h6>" + campusTree.getName() +
                            "</h6>" +
                            "<div class='menu-btns'>" +
//                    "<span class='a-btn a-btn-add btn-add-org' title='添加'></span>\n" +
//                    "<span class='a-btn a-btn-edit btn-edit-org' title='编辑'></span>\n" +
                            "</div>" +
                            "</a>");
                    this.recursive(campusTree.getDirectories(), sb);
                    sb.append("</li>" +
                            "</ul>");
                    res = sb.toString();//接受拼接的字符串并传递给controler
                }
            }
        } catch (Exception e) {
            logger.error("初始化失败：" + e);
        }
        return res;

    }

    /**
     * 获取左侧树
     *
     * @param directoryList 用户列表
     * @param sb            拼接字符串
     */

    private void recursive(List directoryList, StringBuilder sb) {
        if (!directoryList.isEmpty()) {
            sb.append("<ul>");
        }
        for (Object directory : directoryList) {
            Directory obj = (Directory) JsonUtil.toJavaBean(new Directory(), (JSONObject) directory);//Json转化成Javabean
            String name = obj.getName();//获取名称
            String id = obj.getId();//获取id
            //拼接左侧机构树
            sb.append("<li>");
            sb.append("<a title= '" + name + "' href='javascript:void(0)' id='" + id + "'><i></i><h6>" + name + " </h6><div class='menu-btns'>\n" +
//                    "<span class='a-btn a-btn-add btn-add-org' title='添加'></span>\n" +
//                    "<span class='a-btn a-btn-edit btn-edit-org' title='编辑'></span>\n" +
//                    "<span class='a-btn a-btn-delete btn-delete-org' title='删除'></span>\n" +
                    "</div></a>");
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
     * 查看设备详情
     *
     * @param hostId 设备id
     * @return
     */
    public Map loadDevices(String hostId) {
        Map<String, String> params = new HashMap<>();//用于存储参数
        Map deviceInfo = new HashMap();
        params.put("hostId", hostId);//存储参数
        Result result = new Result("", 0, "");//初始化result
        try {
            result = HttpServiceUtil.deviceService("getHostInfoById", params);//请求服务并将结果返回

        } catch (Exception e) {
            logger.error("查看信息失败：" + e);
        }
        if (result.getValue() != null) {
            deviceInfo = (Map<String, String>) result.getValue(); //获取服务返回的value
            String type = (String) deviceInfo.get("dtype_name");
            String newtype = TypeTransform.transform(type); //将设备类型英文名转换成中文名
            deviceInfo.put("dtype_name", newtype);
        }
        return deviceInfo;
    }

    /**
     * 删除设备和组织关系
     *
     * @param areaId   地点id
     * @param deviceId 设备id
     * @return
     */
    public Result deleteDevices(String areaId, String deviceId) {

        Map<String, String> params = new HashMap<>();//用于存储参数
        params.put("areaId", areaId);//存储参数
        params.put("deviceId", deviceId);//存储参数
        Result result = new Result("", 0, "");//初始化result
        try {
            result = HttpServiceUtil.adService("area2DeviceDelete", params); //请求服务并将结果返回
        } catch (Exception e) {
            logger.error("删除组织机构设备失败：" + e);
        }
        return result;
    }

    /**
     * 获取设备信息(初始化或搜索)
     *
     * @param page       当前页
     * @param pageSize   总页数
     * @param areaId     地点id
     * @param deviceType 设备类型
     * @param conditions 搜索词
     * @return
     */
    public Result searchDevice(String page, String pageSize, String areaId, String deviceType, String conditions) {
        Map<String, String> params = new HashMap<>();//用于存储参数
        params.put("areaId", areaId);//存储参数
        params.put("currentPage", page);//存储参数
        params.put("pageSize", pageSize);//存储参数
        params.put("deviceType", deviceType);//存储参数
        params.put("conditions", conditions);//存储参数
        Result result = new Result("", 0, "");//初始化result
        try {
            result = HttpServiceUtil.deviceService("getConditionsHostListByPage", params);//请求服务并将结果返回
        } catch (Exception e) {
            logger.error("初始化或查找设备失败：" + e);
        }
        return result;
    }


    /**
     * 分配设备到组织
     *
     * @param areaId   地点id
     * @param deviceId 用户id
     */
    public Result allocateDevices(String areaId, String deviceId) {
        Map<String, String> params = new HashMap<>();//用于存储参数
        params.put("areaId", areaId);//存储参数
        params.put("deviceId", deviceId);//存储参数
        Result result = new Result("", 0, "");//初始化result
        try {
            result = HttpServiceUtil.adService("area2DeviceAdd", params);//请求服务并将结果返回

        } catch (Exception e) {
            logger.error("分配人员失败：" + e);
        }
        return result;
    }

    /**
     * 获取所有设备名称
     *
     * @return
     */
    public Result deviceType() {
        Map<String, String> params = new HashMap<>();
        Result result = new Result("", 0, "");//初始化result
        try {
            result = HttpServiceUtil.deviceService("getExistingTypeList", params);//请求服务并将结果返回

        } catch (Exception e) {
            logger.error("获取所有设备失败：" + e);
        }
        return result;
    }

    /**
     * 获取所有地点名称
     *
     * @return
     */
    public Result areaType() {
        Map<String, String> params = new HashMap<>();
        Result result = new Result("", 0, "");//初始化result
        try {
            result = HttpServiceUtil.adService("dataGetAreaTypes", params);//请求服务并将结果返回

        } catch (Exception e) {
            logger.error("获取地点类型失败：" + e);
        }
        return result;
    }

    /**
     * 获取所有校区名称
     *
     * @return
     */
    public Result getCampus() {
        Map<String, String> params = new HashMap<>();
        Result result = new Result("", 0, "");//初始化result
        try {
            result = HttpServiceUtil.adService("dataGetSchoolZone", params);//请求服务并将结果返回

        } catch (Exception e) {
            logger.error("获取校区类型失败：" + e);
        }
        return result;
    }

    /**
     * 获取所有校区名称
     *
     * @return
     */
    public Result getRoom(String p_id) {
        Map<String, String> params = new HashMap<>();
        params.put("p_id", p_id);
        Result result = new Result("", 0, "");//初始化result
        try {
            result = HttpServiceUtil.adService("dataGetAreaTypes", params);//请求服务并将结果返回

        } catch (Exception e) {
            logger.error("获取校区类型失败：" + e);
        }
        return result;
    }

    /**
     * 获取所有校区名称
     *
     * @return
     */
    public Result getAreaDetail(String areaId) {
        Map<String, String> params = new HashMap<>();
        Result result = new Result("", 0, "");//初始化result
        try {
            params.put("id", areaId);
            result = HttpServiceUtil.areaService("area_areainfo", params);//请求服务并将结果返回

        } catch (Exception e) {
            logger.error("获取组织机构信息失败：" + e);
        }
        return result;
    }
//    /**
//     * 导入-下载用户模板
//     * */
//    public void groupDownloadMould(HttpServletRequest req,HttpServletResponse resp){
//        String filePath = req.getRealPath("/")+"mould.xls";
//        downExcel(filePath,resp);
//    }
//
//    /**
//     * 下载Excel文件的方法
//     * */
//    public void downExcel(String filePath,HttpServletResponse response){
//        File file = new File(filePath);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
//        Date date = new Date();
//        String time = sdf.format(date);
//        String downFilename="机构列表" + time + ".xls";
//        response.setContentType("application/x-msdownload");
//        response.setContentLength((int) file.length());
//        try {
//            response.setHeader("Content-Disposition", "attachment;filename=" + new String(downFilename.getBytes("GBK"), "ISO8859_1"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        OutputStream outputStream = null;
//        InputStream inputStream = null;
//        try {
//            outputStream = response.getOutputStream();
//            inputStream = new FileInputStream(file);
//            byte[] buffer = new byte[1024];
//            int i = -1;
//            while ((i = inputStream.read(buffer)) != -1) {
//                outputStream.write(buffer, 0, i);
//            }
//            outputStream.flush();
//            if (inputStream!=null){
//                inputStream.close();
//            }
//            if (outputStream!=null){
//                outputStream.close();
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}