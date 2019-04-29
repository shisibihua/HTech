package com.honghe.web.user.controller.teacher;

import com.honghe.service.client.Result;
import com.honghe.web.user.service.teacher.TeacherService;
import com.honghe.web.user.util.UserCommonUtil;
import jodd.joy.page.PageData;
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
 * Created by sunchao on 2017/1/9.
 */
@Controller
@RequestMapping("/Teacher")
public class TeacherController {

    @Autowired
    TeacherService teacherService;
    /**
     * 获取所有职务
     * @param pw
     */
    @RequestMapping("/getTeacherDuty")
    public void getTeacherDuty(PrintWriter pw){
        JSONObject json = new JSONObject();
        List teacherDutyList = teacherService.getTeacherDuty();
        json.put("teacherDutyList",teacherDutyList);
        pw.write(json.toString());
    }

    /**
     * 删除教师
     * @param id 教师id
     * @return
     */
    @RequestMapping("/teacherDelete")
    public void teacherDelete(String id,PrintWriter pw){
        JSONObject json = new JSONObject();
        Result res = teacherService.deleteTeacher(id);//获取服务中返回的结果
        if (res.getValue() != null) {
            json.put("value",res.getValue());
        }
        json.put("code",res.getCode());
        pw.write(json.toString());//将value和code传给前台
    }
    /**
     * 批量删除教师
     * @param userIds 用户id，多个用逗号隔开
     * @return
     * @throws Exception
     */
    @RequestMapping("/teachersDelete")
    public void teachersDelete(String userIds,PrintWriter pw){
        JSONObject json = new JSONObject();
        Result res = teacherService.deleteTeachers(userIds);
        if (res.getValue()!=null){
            json.put("value",res.getValue());
        }
        json.put("code",res.getCode());
        pw.write(json.toString());//将value和code传给前台
    }
    /**
     * 获取所有授课科目
     * @param seasonId 学段ID
     * @param pw
     */
    @RequestMapping("/getTeacherSubject")
    public void getTeacherSubject(String seasonId,PrintWriter pw){
        JSONObject json = new JSONObject();
        List teacherSubjectList = teacherService.getTeacherSubject(seasonId);
        json.put("teacherSubjectList",teacherSubjectList);
        pw.write(json.toString());
    }

    /**
     * 获取教师所授课学段
     * @param pw
     */
    @RequestMapping("/getTeacherStage")
    public void getTeacherStage(PrintWriter pw){
        JSONObject json = new JSONObject();
        Result res = teacherService.getTeacherStage();
        if (res.getValue()!=null){
            json.put("value",res.getValue());
        }
        json.put("code",res.getCode());
        pw.write(json.toString());
    }

    /**
     * 教师详情
     * @params pw 写入数据
     */
    @RequestMapping("/teacherDetails")
    public void teacherDetails(PrintWriter pw,String page,String pageSize,String teacherCode,String searchWord,String userId) {
        JSONObject obj = teacherService.teacherDetails(page,pageSize,teacherCode,searchWord,userId);
        pw.write(obj.toString());
    }

    /**
     * 添加教师
     * @param pw 写入数据
     */
    @RequestMapping("/teacherAdd")
    public void teacherAdd(HttpServletRequest req,String teacherAvatar, String teacherName, String teacherNamePy,
                           String employeeNo, String stage, String duty, String subject, String gender, String mobile,
                           String email, String qq, String identity, String birthday, String shortMobile, String nation,
                           String politics, String address, String startWork, String isWork,String qualification,
                           String campusId, PrintWriter pw) {
        JSONObject obj = teacherService.teacherAdd(req, teacherAvatar, teacherName, teacherNamePy,employeeNo, stage, duty, subject, gender, mobile, email, qq,identity,birthday,shortMobile,nation,politics,address,startWork,isWork,qualification,campusId);
        pw.write(obj.toString());
    }

    /**
     * 编辑教师-修改教师
     * @param pw 写入数据
     */
    @RequestMapping("/teacherUpdate")
    public void teacherUpdate(HttpServletRequest req,String teacherCode,String teacherNamePy, String teacherAvatar, String teacherName, String employeeNo, String stage, String duty, String subject, String gender, String mobile, String email, String qq, String identity, String birthday, String shortMobile, String nation, String politics, String address, String startWork, String isWork, String qualification,String campusId,PrintWriter pw) {
        JSONObject obj = teacherService.teacherUpdate(req, teacherCode, teacherNamePy,teacherAvatar, teacherName, employeeNo, stage, duty, subject, gender, mobile, email, qq,identity,birthday,shortMobile,nation,politics,address,startWork,isWork,qualification,campusId);
        pw.write(obj.toString());
    }

    /**
     * 获取教师列表（分页查询）
     * @params pw 写入数据
     */
    @RequestMapping("/teacherSearchByPage")
    public void teacherSearchByPage(PrintWriter pw,String page ,String pageSize, String searchWord, String campusId,String flag,String userId, HttpServletRequest req) {
        PageData pageData = teacherService.teacherSearchByPage(page, pageSize, searchWord, campusId,flag, req);
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

    /**
     * 导入-下载教师模板
     * @params pw 写入数据
     */
    @RequestMapping("/teacherDownloadMould")
    public void teacherDownloadMould(HttpServletRequest req,HttpServletResponse resp) {
        teacherService.teacherDownloadMould(req, resp);
    }

    /**
     * 导入-教师导入
     * @params pw 写入数据
     * 用户导入成功后走缓存
     */
    @RequestMapping("/teacherUpload")
    public void teacherUpload(PrintWriter pw,HttpServletRequest req,HttpServletResponse response) {
        JSONObject obj = teacherService.teacherUpload(req, response);
        pw.write(obj.toString());
    }


    /**
     * 设置教师头像
     */
    @RequestMapping("/imageUpload")
    public void imageUpload(PrintWriter pw,HttpServletRequest req,String x,String y,String w,String h,String width,String height,String extension) {

        JSONObject obj = teacherService.imageUpload(req,Integer.parseInt(x),Integer.parseInt(y),Integer.parseInt(w),Integer.parseInt(h),extension);
        pw.write(obj.toString());
    }

    /**
     * 导出-教师数据导出
     * @params pw 写入数据
     */
    @RequestMapping("/teacherExportExcel")
    public void teacherExportExcel(HttpServletRequest req,HttpServletResponse resp){
        teacherService.teacherExportExcel(req, resp);
    }

    /**
     * 导出-教师数据导出失败
     * @params pw 写入数据
     */
    @RequestMapping("/teacherDownloadFailed")
    public void teacherDownloadFailed(HttpServletRequest req, HttpServletResponse resp) {
        teacherService.teacherDownloadFailed(req, resp);
    }
    /**
     * 检查图片大小
     * @params pw 写入数据
     */
    @RequestMapping("/teacherImageCheck")
    public void teacherImageCheck(HttpServletRequest req, PrintWriter pw) {
        JSONObject obj = teacherService.teacherImageCheck(req);
        pw.write(obj.toString());
    }

}
