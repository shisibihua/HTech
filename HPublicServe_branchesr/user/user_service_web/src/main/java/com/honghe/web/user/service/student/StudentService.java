package com.honghe.web.user.service.student;

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
public class StudentService {
    Logger logger = Logger.getLogger(StudentService.class);


    /**
     * 获取用户列表（分页）
     */
    public PageData studentSearchByPage(String page, String pageSize, String searchWord,String campusId, HttpServletRequest req) {
        Map<String, String> params = new HashMap<>();
        params.put("page", page);
        params.put("pageSize", pageSize);
        params.put("studentName", searchWord);
        params.put("campusId", campusId);
        Result result = new Result("",0,"");//初始化result
        try {
            result = HttpServiceUtil.userService("studentSearchByPage", params);
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
    public JSONObject studentAdd(HttpServletRequest req,String studentAvatar, String studentName, String studentNamePy, String studentNo, String birthday, String gender, String nation, String schoolYear, String studentType, String studentState, String studentMobile, String studentEmail,String enterType,String studentAddress,String campusId) {

        String path = req.getRealPath("/headImage");
        File small = new File(path + File.separator + studentAvatar);
        String fileName ="";
        if(small.exists()) {
            fileName = HttpServiceUtil.uploadFile(small);
        }
        JSONObject json = new JSONObject();
        Map<String, String> params = new HashMap<>();
        Map<String, String> map = new HashMap<>();
        params.put("realName", studentName);
        params.put("namepy", studentNamePy);
        params.put("student_num", studentNo);
        params.put("enter_year", schoolYear);
        params.put("birthday", birthday);
        params.put("gender", gender);
        params.put("readtype", studentType);
        params.put("status", studentState);
        params.put("mobile", studentMobile);
        params.put("nation", nation);
        params.put("address", studentAddress);
        params.put("enter_way", enterType);
        params.put("email", studentEmail);
        params.put("student_path", fileName);
        map.put("student",JSONObject.fromObject(params).toString());
        map.put("campusId",campusId);
        try {
            Result res = HttpServiceUtil.userService("studentAdd", map);
            if (res.getValue() != null) {
                String result = res.getValue().toString();
                int code = res.getCode();
                json.put("result", result);
                json.put("code", code);
            }
        } catch (Exception e) {
            logger.error("添加教师失败：" + e);
        }
        return json;
    }

    /**
     * 修改用户的服务
     */
    public JSONObject studentUpdate(HttpServletRequest req,String studentCode,String studentAvatar, String studentName, String studentNamePy, String studentNo, String birthday, String gender, String nation, String schoolYear, String studentType, String studentState, String studentMobile, String studentEmail,String enterType,String studentAddress,String campusId) {


        String path = req.getRealPath("/headImage");
        File small = new File(path + File.separator + studentAvatar);
        String fileName ="";
        if(small.exists()) {
            fileName = HttpServiceUtil.uploadFile(small);
        }
        JSONObject json = new JSONObject();
        Map<String, String> params = new HashMap<>();
        Map<String, String> map = new HashMap<>();
        params.put("student_code", studentCode);
        params.put("realName", studentName);
        params.put("namepy", studentNamePy);
        params.put("student_num", studentNo);
        params.put("enter_year", schoolYear);
        params.put("birthday", birthday);
        params.put("gender", gender);
        params.put("readtype", studentType);
        params.put("status", studentState);
        params.put("mobile", studentMobile);
        params.put("nation", nation);
        params.put("address", studentAddress);
        params.put("enter_way", enterType);
        params.put("email", studentEmail);
        params.put("student_path", fileName);
        map.put("student",JSONObject.fromObject(params).toString());
        map.put("campusId",campusId);
        try {
            Result res = HttpServiceUtil.userService("studentUpdate", map);
            if (res.getValue() != null) {
                String code = (String) res.getValue();
                json.put("code", code);
            }
        } catch (Exception e) {
            logger.error("用户修改失败：" + e);
        }
        return json;
    }

    /**
     * 删除学生
     *
     * @param id 学生id
     * @return
     */
    public Result deleteStudent(String id) {
        Map<String, String> params = new HashMap<>();//用于存储参数
        params.put("studentCode", id);
        Result result = new Result("", 0, "");//初始化result
        try {
            result = HttpServiceUtil.userService("studentDelete", params);//请求服务并将结果返回

        } catch (Exception e) {
            logger.error("删除学生失败：" + e);
        }
        return result;
    }

    /**
     * 批量删除学生
     * @param ids 学生id，多个用逗号隔开
     * @return
     */
    public Result deletesStudent(String ids){
        Map<String, String> params = new HashMap<>();//用于存储参数
        params.put("studentIds", ids);
        Result result = new Result("", 0, "");//初始化result
        try {
            result = HttpServiceUtil.userService("studentsDelete", params);//请求服务并将结果返回

        } catch (Exception e) {
            logger.error("批量删除学生失败：" + e);
        }
        return result;
    }

    /**
     * 学生详情
     */
    public JSONObject studentDetails(String userId, String studentCode, String page, String pageSize, String searchWord) {

        JSONObject json = new JSONObject();
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("studentCode", studentCode);
        params.put("page", page);
        params.put("pageSize", pageSize);
        params.put("studentName", searchWord);
        try {
            Result ures = HttpServiceUtil.userService("studentSearchByPage", params);
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
     * 用户导入
     */
    public JSONObject studentUpload(HttpServletRequest req, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        ExcelTools et = new ExcelTools();
        String msg = et.uploadFile("student", req, response);
        json.put("result", msg);
        return json;
    }

    /**
     * 导入-下载用户模板
     */
    public void studentDownloadMould(HttpServletRequest req, HttpServletResponse resp) {
        String filePath = req.getRealPath("/") + "student_mould.xls";
        ExcelTools et = new ExcelTools();
        et.downExcel(filePath, true, resp);
    }

    /**
     * 用户服务-用户模板导出失败
     */
    public void studentDownloadFailed(HttpServletRequest req, HttpServletResponse resp) {
        String filePath = req.getParameter("filePath");
        ExcelTools et = new ExcelTools();
        et.downExcel(filePath, false, resp);
    }

    /**
     * 用户模板导出
     */
    public void studentExportExcel(HttpServletRequest req, HttpServletResponse resp) {
        Map<String, String> params = new HashMap<>();
        String searchWord = req.getParameter("searchWord");
        String campusId = req.getParameter("campusId");
        if ("1".equals(campusId)){
            campusId = "";
        }
        params.put("studentName", searchWord);
        try {
            Result res = HttpServiceUtil.userService("studentExportExcel", params);
            if (res.getValue() != null) {
                List<Map<String, String>> list = (List<Map<String, String>>) res.getValue();
                List<Map<String, String>> exportList = new ArrayList<>();
                if (!"".equals(campusId)&&campusId!=null){
                    for(Map<String,String> student:list){
                        JSONObject user = JSONObject.fromObject(student.get("user"));
                        JSONArray campusArray = (JSONArray) user.get("campuses");
                        List campusList = (List) JsonUtil.jsonToList(campusArray);
                        for (int i= 0;i<campusList.size();i++){
                            Map<String,String> campus = (Map<String,String>) campusList.get(i);
                            if (campusId.equals(campus.get("id"))){
                                exportList.add(student);
                                continue;
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
        String[] headers = {"姓名", "姓名拼音", "学号","手机", "邮箱", "性别", "生日", "户口所在地", "民族", "就读方式", "在校状态","入学年度","入学方式"};
        List<String[]> excelList = new ArrayList<>();
        // 插入数据
        for (int i = 0; i < userList.size(); i++) {
            String[] strList = new String[13];
            strList[0] = userList.get(i).get("realName") == null ? "" : userList.get(i).get("realName");
            strList[1] = userList.get(i).get("namepy") == null ? "" : userList.get(i).get("namepy");
            strList[2] = userList.get(i).get("student_num") == null ? "" : userList.get(i).get("student_num");
            strList[3] = userList.get(i).get("mobile") == null ? "" : userList.get(i).get("mobile");
            strList[4] = userList.get(i).get("email") == null ? "" : userList.get(i).get("email");
            strList[5] = (userList.get(i).get("gender") == null ? "" : (userList.get(i).get("gender").equals("1") ? "男":(userList.get(i).get("gender") .equals("2") ? "女":"" )));
            strList[6] = userList.get(i).get("birthday") == null ? "" : userList.get(i).get("birthday");
            strList[7] = userList.get(i).get("address") == null ? "" : userList.get(i).get("address");
            strList[8] = userList.get(i).get("nation") == null ? "" : userList.get(i).get("nation");
            strList[9] = (userList.get(i).get("readtype") == null ? "" : (userList.get(i).get("readtype").equals("1") ? "走读":(userList.get(i).get("readtype").equals("2") ? "住宿":(userList.get(i).get("readtype").equals("3") ? "借宿":(userList.get(i).get("readtype").equals("4") ? "其他":"")))));
            strList[10] = (userList.get(i).get("status") == null ? "" : (userList.get(i).get("status").equals("1") ? "在校":(userList.get(i).get("status") .equals("2") ? "离校":userList.get(i).get("status") .equals("2") ? "已毕业":"")));
            strList[11] = userList.get(i).get("enter_year") == null ? "" : userList.get(i).get("enter_year");
            strList[12] = (userList.get(i).get("enter_way") == null ? "" : (userList.get(i).get("enter_way").equals("1") ? "普通入学":(userList.get(i).get("enter_way").equals("2") ? "体育特招":(userList.get(i).get("enter_way").equals("3") ? "外校转入":(userList.get(i).get("enter_way").equals("4") ? "其他":"")))));
            excelList.add(strList);
        }
        String filePath = "";
        try {
            filePath = excel.exportTableByFile("学生列表", headers, excelList, "./", "tempDownExcel")[0];
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public JSONObject studentImageCheck(HttpServletRequest req) {
        AvatarUtil ava = new AvatarUtil();
        File file = ava.uploadImage(req);
        long size = file.length()/1024;
        JSONObject json = new JSONObject();
        json.put("result", size);
        return json;

    }

}
