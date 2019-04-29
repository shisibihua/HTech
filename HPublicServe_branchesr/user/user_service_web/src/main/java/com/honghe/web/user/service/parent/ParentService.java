package com.honghe.web.user.service.parent;

import com.honghe.service.client.Result;
import com.honghe.web.user.util.AvatarUtil;
import com.honghe.web.user.util.ExcelTools;
import com.honghe.web.user.util.HttpServiceUtil;
import com.honghe.web.user.util.JsonUtil;
import jodd.joy.page.PageData;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by sunchao on 2017/1/9.
 */
@Service
public class ParentService {

    Logger logger = Logger.getLogger(ParentService.class);


    /**
     * 获取用户列表（分页）
     */
    public PageData parentSearchByPage(String page, String pageSize, String searchWord, String parentCode, String campusId, HttpServletRequest req) {
        Map<String, String> params = new HashMap<>();
        params.put("page", page);
        params.put("pageSize", pageSize);
        params.put("campusId",campusId);
        if(searchWord!=null) {
            params.put("parentName", searchWord);
        }
        if(parentCode!=null) {
            params.put("parentCode", parentCode);
        }
        Result result = new Result("",0,"");//初始化result
            try {
             result = HttpServiceUtil.userService("parentSearchByPage", params);
            }
            catch (Exception e) {
                logger.error("查找用户失败：" + e);
            }
            Map map =new HashMap();
            if (result.getValue()!=null){
                map=(Map) result.getValue();//获取服务返回的value
            }
            List items= (List) map.get("items");//获取map中属性
            int pagesize= (int)map.get("pageSize");//获取map中属性
            int currentPage = (int) map.get("currentPage");//获取map中属性
            int size =(int) map.get("totalItems");//获取map中属性
            // 生成分页显示对象
            PageData  pageData = new PageData(currentPage,size,pagesize,items);
            return pageData;
    }

    /**
     * 添加用户的服务
     */
    public JSONObject parentAdd(HttpServletRequest req,String studentName,String namePy, String parentAvatar, String parentName, String parentRelation, String mobile, String email, String isGuardian, String isTogether) {

        String path = req.getRealPath("/headImage");
        File small = new File(path + File.separator + parentAvatar);
        String fileName ="";
        if(small.exists()) {
            fileName = HttpServiceUtil.uploadFile(small);
        }
        JSONObject json = new JSONObject();
        Map<String, String> params = new HashMap<>();
        params.put("parent_name", parentName);
        params.put("namepy", namePy);
        params.put("membership", parentRelation);
        params.put("parent_mobile", mobile);
        params.put("email", email);
        params.put("is_guardian", isGuardian);
        params.put("is_together", isTogether);
        params.put("parent_path", fileName);
        Map<String, String> map = new HashMap<>();
        map.put("parent",JSONObject.fromObject(params).toString());
        map.put("studentCodes",studentName);
        try {
            Result res = HttpServiceUtil.userService("parentAdd", map);
            if (res.getValue() != null) {
                String result = res.getValue().toString();
                int code = res.getCode();
                json.put("result", result);
                json.put("code", code);
            }
        } catch (Exception e) {
            logger.error("添加家长失败：" + e);
        }
        return json;
    }

    /**
     * 修改用户的服务
     */
    public JSONObject parentUpdate(HttpServletRequest req, String parentCode, String namePy, String studentName, String parentAvatar, String parentName, String parentRelation, String mobile, String email, String isGuardian, String isTogether) {


        String path = req.getRealPath("/headImage");
        File small = new File(path + File.separator + parentAvatar);
        String fileName ="";
        if(small.exists()) {
            fileName = HttpServiceUtil.uploadFile(small);
        }
        JSONObject json = new JSONObject();
        Map<String, String> params = new HashMap<>();
        params.put("parent_name", parentName);
        params.put("parent_code", parentCode);
        params.put("namepy", namePy);
        params.put("membership", parentRelation);
        params.put("parent_mobile", mobile);
        params.put("email", email);
        params.put("is_guardian", isGuardian);
        params.put("is_together", isTogether);
        params.put("parent_path", fileName);
        Map<String, String> map = new HashMap<>();
        map.put("studentCodes", studentName);
        map.put("parent",JSONObject.fromObject(params).toString());
        try {
            Result res = HttpServiceUtil.userService("parentUpdate", map);
            if (res.getValue() != null) {
                int code = (int) res.getValue();
                json.put("code", code);
            }
        } catch (Exception e) {
            logger.error("用户修改失败：" + e);
        }
        return json;
    }

    /**
     * 家长详情
     */
    public JSONObject parentDetails(String parentCode,String userId,String page, String pageSize, String searchWord){

        JSONObject json = new JSONObject();
        Map<String, String> params = new HashMap<>();
        params.put("parentCode", parentCode);
        params.put("userId", userId);
        params.put("page", page);
        params.put("pageSize", pageSize);
        params.put("searchWord", searchWord);
        try {
            Result ures = HttpServiceUtil.userService("parentSearchByPage", params);
            Result rres = HttpServiceUtil.userService("roleSearch", params);
            if (ures.getValue() != null) {
                Map userMap = (Map) ures.getValue();
                json.put("userInfo", userMap);
            }
            if (rres.getValue() != null) {
                Map roleMap = (Map) rres.getValue();
                json.put("roleInfo", roleMap);
            }
        } catch (Exception e) {
            logger.error("用户详情查询失败：" + e);
        }
        return json;
    }

    /**
     * 删除家长
     *
     * @param id 家长id
     * @return
     */
    public Result deleteParent(String id) {
        Map<String, String> params = new HashMap<>();//用于存储参数
        params.put("parentCode", id);
        Result result = new Result("", 0, "");//初始化result
        try {
            result = HttpServiceUtil.userService("parentDelete", params);//请求服务并将结果返回

        } catch (Exception e) {
            logger.error("删除家长失败：" + e);
        }
        return result;
    }

    /**
     * 批量删除家长
     * @param ids 家长id,多个用逗号隔开
     * @return
     */
    public Result deletesParent(String ids){
        Map<String, String> params = new HashMap<>();//用于存储参数
        params.put("parentIds", ids);
        Result result = new Result("", 0, "");//初始化result
        try {
            result = HttpServiceUtil.userService("parentsDelete", params);//请求服务并将结果返回

        } catch (Exception e) {
            logger.error("批量删除家长失败：" + e);
        }
        return result;
    }

    /**
     * 获取学生
     * @return List
     */
    public List getStudent(){
        Map<String, String> params = new HashMap<>();
        List userTypeList = new ArrayList();
        try {
            Result result = HttpServiceUtil.userService("studentList", params);
            if (result.getValue()!=null){
                userTypeList = (List) result.getValue();
            }
        } catch (Exception e) {
            logger.error("获取所有学生失败：",e);
        }
        return userTypeList;
    }

    /**
     * 用户导入
     */
    public JSONObject parentUpload(HttpServletRequest req, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        ExcelTools et = new ExcelTools();
        String msg = et.uploadFile("parent", req, response);
        json.put("result", msg);
        return json;
    }

    /**
     * 导入-下载用户模板
     */
    public void parentDownloadMould(HttpServletRequest req, HttpServletResponse resp) {
        String filePath = req.getRealPath("/") + "parent_mould.xls";
        ExcelTools et = new ExcelTools();
        et.downExcel(filePath, true, resp);
    }

    /**
     * 用户服务-用户模板导出失败
     */
    public void parentDownloadFailed(HttpServletRequest req, HttpServletResponse resp) {
        String filePath = req.getParameter("filePath");
        ExcelTools et = new ExcelTools();
        et.downExcel(filePath, false, resp);
    }

    /**
     * 用户模板导出
     */
    public void parentExportExcel(HttpServletRequest req, HttpServletResponse resp) {
        Map<String, String> params = new HashMap<>();
        String searchWord = req.getParameter("searchWord");
        String campusId = req.getParameter("campusId");
        if ("1".equals(campusId)){
            campusId = "";
        }
        params.put("parentName", searchWord);
        try {
            Result res = HttpServiceUtil.userService("parentSearchByName", params);
            if (res.getValue() != null) {
                List<Map<String, String>> list = (List<Map<String, String>>) res.getValue();
                List<Map<String,String>> exportList = new ArrayList<>();
                if (!"".equals(campusId)&&campusId!=null){
                    for(Map<String,String> parent:list){
                        JSONArray student = JSONArray.fromObject(parent.get("studentSet"));
                        List<Map<String,String>> studentList = (List) JsonUtil.jsonToList(student);
                        for (Map<String,String> students:studentList){
                            JSONObject user = JSONObject.fromObject(students.get("user"));
                            JSONArray campusArray = (JSONArray) user.get("campuses");
                            List campusList = (List) JsonUtil.jsonToList(campusArray);
                            for (int i= 0;i<campusList.size();i++){
                                Map<String,String> campus = (Map<String,String>) campusList.get(i);
                                if (campusId.equals(campus.get("id"))){
                                    exportList.add(parent);
                                    continue;
                                }
                            }

                        }
                    }
                }else{
                    exportList = list;
                }
                exportExcel(exportList, resp);
            }
        } catch (Exception e) {
            logger.error("用户模板导出失败" + e);
        }
    }

    /**
     * 生成excel文件，response不为空时，提供下载
     *
     * @param userList 用户列表
     */
    public String exportExcel(List<Map<String, String>> userList, HttpServletResponse response) {
        ExcelTools excel = new ExcelTools();
        // 生成表头
        String[] headers =  {"姓名", "姓名拼音", "手机", "邮箱", "学生姓名", "成员关系", "是否是监护人", "是否生活在一起"};  //todo
        List<String[]> excelList = new ArrayList<>();
        // 插入数据
        for (int i = 0; i < userList.size(); i++) {
            String[] strList = new String[12];
            strList[0] = userList.get(i).get("parent_name") == null ? "" : userList.get(i).get("parent_name");
            strList[1] = userList.get(i).get("namepy") == null ? "" : userList.get(i).get("namepy");
            strList[2] = userList.get(i).get("parent_mobile") == null ? "" : userList.get(i).get("parent_mobile");
            strList[3] = userList.get(i).get("email") == null ? "" : userList.get(i).get("email");
            strList[4] = userList.get(i).get("studentNames") == null ? "" : userList.get(i).get("studentNames");
            strList[5] = (userList.get(i).get("membership") == null ? "" : (userList.get(i).get("membership").equals("1") ? "父亲":(userList.get(i).get("membership").equals("2") ? "母亲":(userList.get(i).get("membership").equals("3") ? "其他":""))));
            strList[6] = (userList.get(i).get("is_guardian") == null ? "" : (userList.get(i).get("is_guardian").equals("1") ? "是":(userList.get(i).get("is_guardian") .equals("0") ? "否":"" )));
            strList[7] = (userList.get(i).get("is_together") == null ? "" : (userList.get(i).get("is_together").equals("1") ? "是":(userList.get(i).get("is_together").equals("0") ? "否":"" )));
            excelList.add(strList);
        }
        String filePath = "";
        try {
            filePath = excel.exportTableByFile("家长列表", headers, excelList, "./", "tempDownExcel")[0];
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 如果response为空，只生成excel文件
        // response不为空时，提供下载
        if (response == null) {
            return filePath;
        }
        ExcelTools et = new ExcelTools();
        et.downExcel(filePath, false, response);
        return null;
    }

    /**
     * 图片导入
     */
    public JSONObject imageUpload(HttpServletRequest req, int x, int y, int w, int h,String extension) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
        Date date = new Date();
        String time = sdf.format(date);
        JSONObject json = new JSONObject();
        AvatarUtil ava = new AvatarUtil();
        File file = ava.uploadImage(req);
        long filesize =file.length()/1024;
        if(filesize>1024){
            json.put("result", "sizeError");
            return json;
        }
        String path = req.getRealPath("/headImage");
        File dir = new File(path);
        if(!dir.exists()){
            dir.mkdir();
        }
        File small = new File(path + File.separator + time + "."+extension+"");

        try {
            // 上传的原图
            Image image = ImageIO.read(file);
            BufferedImage bf = (BufferedImage)image;
            double height =  bf.getHeight();
            double width = bf.getWidth();
            // 页面展示图片的大小
            double webHeigth = 300;
            double webWidth = 300;
            // 根据比例计算截取图片的坐标，宽，高
            x = (int)(x * (width / webWidth));
            y = (int)(y * (height / webHeigth));
            w = (int)(w * (width / webWidth));
            h = (int)(h * (height / webHeigth));
            // 截图图片
            Iterator iterator = ImageIO.getImageReadersByFormatName(""+extension+"");

            ImageReader reader = (ImageReader) iterator.next();
            InputStream in = new FileInputStream(file);
            ImageInputStream iis = ImageIO.createImageInputStream(in);
            reader.setInput(iis, true);

            ImageReadParam param = reader.getDefaultReadParam();
            Rectangle rect = new Rectangle(x,y,w,h);
            param.setSourceRegion(rect);
            BufferedImage bi = reader.read(0, param);
            // 写出图片
            ImageIO.write(bi, ""+extension+"", small);
            file.delete();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        json.put("result", "success");
        json.put("filename", time);

        return json;
    }

    public JSONObject parentImageCheck(HttpServletRequest req) {
        AvatarUtil ava = new AvatarUtil();
        File file = ava.uploadImage(req);
        long size = file.length()/1024;
        JSONObject json = new JSONObject();
        json.put("result", size);
        return json;

    }

}
