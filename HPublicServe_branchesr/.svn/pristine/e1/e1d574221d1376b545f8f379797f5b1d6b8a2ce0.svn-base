package com.honghe.web.user.controller.student;

import com.honghe.service.client.Result;
import com.honghe.web.user.service.student.StudentService;
import com.honghe.web.user.util.UserCommonUtil;
import jodd.joy.page.PageData;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;

/**
 * Created by sunchao on 2017/1/9.
 */
@Controller
@RequestMapping("/Student")
public class StudentController {

    @Autowired
    StudentService studentService;

    /**
     * 教师详情
     * @params pw 写入数据
     */
    @RequestMapping("/studentDetails")
    public void parentDetails(PrintWriter pw,String userId,String studentCode,String page, String pageSize, String searchWord) {
        JSONObject obj = studentService.studentDetails(userId, studentCode, page, pageSize, searchWord);
        pw.write(obj.toString());
    }

    /**
     * 添加学生
     * @param pw 写入数据
     */
    @RequestMapping("/studentAdd")
    public void studentAdd(HttpServletRequest req,String studentAvatar, String studentName, String studentNamePy, String studentNo, String birthday, String gender, String nation, String schoolYear, String studentType, String studentState, String studentMobile, String studentEmail,String enterType,String studentAddress,String campusId, PrintWriter pw) {
        JSONObject obj = studentService.studentAdd(req, studentAvatar, studentName, studentNamePy, studentNo, birthday, gender, nation, schoolYear, studentType,studentState,studentMobile,studentEmail,enterType,studentAddress,campusId);
        pw.write(obj.toString());
    }

    /**
     * 编辑学生-修改学生
     * @param pw 写入数据
     */
    @RequestMapping("/studentUpdate")
    public void studentUpdate(HttpServletRequest req,String studentCode, String studentAvatar, String studentName, String studentNamePy, String studentNo, String birthday, String gender, String nation, String schoolYear, String studentType, String studentState, String studentMobile, String studentEmail,String enterType,String studentAddress, String campusId,PrintWriter pw) {
        JSONObject obj = studentService.studentUpdate(req,studentCode, studentAvatar, studentName, studentNamePy, studentNo, birthday, gender, nation, schoolYear, studentType,studentState,studentMobile,studentEmail,enterType,studentAddress,campusId);
        pw.write(obj.toString());
    }

    /**
     * 获取学生列表（分页查询）
     * @params pw 写入数据
     */
    @RequestMapping("/studentSearchByPage")
    public void parentSearchByPage(PrintWriter pw,String page ,String pageSize, String searchWord,String campusId,String userId, HttpServletRequest req) {
        PageData pageData = studentService.studentSearchByPage(page, pageSize, searchWord,campusId, req);
        JSONObject json = new JSONObject();
        List<Map<String,String>> list=pageData.getItems();
        int totalCount =pageData.getTotalItems();
        Map<String,String> userInfo = new HashMap<>();
        String isAdmin = "";
        userInfo = UserCommonUtil.getUserInfoById(userId);
        if (userInfo!=null&&!"".equals(userInfo)){
            isAdmin = userInfo.get("userIsAdmin")==null?"0":userInfo.get("userIsAdmin");
        }
        json.put("list",list);
        json.put("totalCount",totalCount);
        json.put("isAdmin",isAdmin);
        if (pageData != null) {
            pw.write(json.toString());
        }
    }

    @RequestMapping("/getPeopleByProperties")
    public void getPeopleByProperties(PrintWriter pw) throws UnsupportedEncodingException {
        JSONObject json = new JSONObject();
        PropertyResourceBundle res = (PropertyResourceBundle) PropertyResourceBundle.getBundle("people");
        String nameStr = "";
        String valStr = "";
        nameStr =  new String(res.getString("name").getBytes("ISO-8859-1"),"GBK");
        valStr =  new String(res.getString("value").getBytes("ISO-8859-1"),"GBK");
        json.put("name",nameStr);
        json.put("value",valStr);
        pw.write(json.toString());
    }

    /**
     * 删除学生
     * @param id 学生id
     * @return
     */
    @RequestMapping("/studentDelete")
    public void studentDelete(String id,PrintWriter pw){
        JSONObject json = new JSONObject();
        Result res = studentService.deleteStudent(id);//获取服务中返回的结果
        if (res.getValue() != null) {
            json.put("value",res.getValue());
        }
        json.put("code",res.getCode());
        pw.write(json.toString());//将value和code传给前台
    }

    /**
     * 批量删除学生
     * @param studentIds 学生id,多个用逗号隔开
     * @return
     */
    @RequestMapping("/studentsDelete")
    public void studentsDelete(String studentIds,PrintWriter pw){
        JSONObject json = new JSONObject();
        Result res = studentService.deletesStudent(studentIds);//获取服务中返回的结果
        if (res.getValue() != null) {
            json.put("value",res.getValue());
        }
        json.put("code",res.getCode());
        pw.write(json.toString());//将value和code传给前台
    }

    /**
     * 导入-下载学生模板
     * @params pw 写入数据
     */
    @RequestMapping("/studentDownloadMould")
    public void studentDownloadMould(HttpServletRequest req,HttpServletResponse resp) {
        studentService.studentDownloadMould(req, resp);
    }

    /**
     * 导入-学生导入
     * @params pw 写入数据
     * 用户导入成功后走缓存
     */
    @RequestMapping("/studentUpload")
    public void studentUpload(PrintWriter pw,HttpServletRequest req,HttpServletResponse response) {
        JSONObject obj = studentService.studentUpload(req, response);
        pw.write(obj.toString());
    }


    /**
     * 设置教师头像
     */
    @RequestMapping("/imageUpload")
    public void imageUpload(PrintWriter pw,HttpServletRequest req,String x,String y,String w,String h,String width,String height,String extension) {

        JSONObject obj = studentService.imageUpload(req,Integer.parseInt(x),Integer.parseInt(y),Integer.parseInt(w),Integer.parseInt(h),extension);
        pw.write(obj.toString());
    }

    /**
     * 导出-教师数据导出
     * @params pw 写入数据
     */
    @RequestMapping("/studentExportExcel")
    public void studentExportExcel(HttpServletRequest req,HttpServletResponse resp){
        studentService.studentExportExcel(req, resp);
    }

    /**
     * 导出-学生数据导出失败
     * @params pw 写入数据
     */
    @RequestMapping("/studentDownloadFailed")
    public void studentDownloadFailed(HttpServletRequest req, HttpServletResponse resp) {
        studentService.studentDownloadFailed(req, resp);
    }

    /**
     * 检查图片大小
     * @params pw 写入数据
     */
    @RequestMapping("/studentImageCheck")
    public void studentImageCheck(HttpServletRequest req, PrintWriter pw) {
        JSONObject obj = studentService.studentImageCheck(req);
        pw.write(obj.toString());
    }
}
