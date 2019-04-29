package com.honghe.web.user.controller.parent;

import com.honghe.service.client.Result;
import com.honghe.web.user.service.parent.ParentService;
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
@RequestMapping("/Parent")
public class ParentController {

    @Autowired
    ParentService parentService;

    /**
     * 教师详情
     * @params pw 写入数据
     */
    @RequestMapping("/parentDetails")
    public void parentDetails(PrintWriter pw,String parentCode,String userId,String page, String pageSize, String searchWord) {
        JSONObject obj = parentService.parentDetails(parentCode,userId,page,pageSize,searchWord);
        pw.write(obj.toString());
    }

    /**
     * 添加家长
     * @param pw 写入数据
     */
    @RequestMapping("/parentAdd")
    public void parentAdd(HttpServletRequest req, String studentName, String namepy,String parentAvatar, String parentName, String parentRelation, String mobile, String email, String isGuardian, String isTogether, PrintWriter pw) {
        JSONObject obj = parentService.parentAdd(req, studentName,namepy,parentAvatar, parentName, parentRelation, mobile, email, isGuardian, isTogether);
        pw.write(obj.toString());
    }

    /**
     * 编辑家长-修改家长
     * @param pw 写入数据
     */
    @RequestMapping("/parentUpdate")
    public void parentUpdate(HttpServletRequest req, String parentCode, String namePy, String studentName, String parentAvatar, String parentName, String parentRelation, String mobile, String email, String isGuardian, String isTogether,PrintWriter pw) {
        JSONObject obj = parentService.parentUpdate(req, parentCode,namePy,studentName,parentAvatar, parentName, parentRelation, mobile, email, isGuardian, isTogether);;
        pw.write(obj.toString());
    }

    /**
     * 获取家长列表（分页查询）
     * @params pw 写入数据
     */
    @RequestMapping("/parentSearchByPage")
    public void parentSearchByPage(PrintWriter pw,String page ,String pageSize, String searchWord, String parent, String campusId,String userId, HttpServletRequest req) {
        PageData pageData = parentService.parentSearchByPage(page, pageSize, searchWord, parent, campusId, req);
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
     * 获取所有学生
     * @param pw
     */
    @RequestMapping("/getStudent")
    public void getStudent(PrintWriter pw){
        JSONObject json = new JSONObject();
        List studentList = parentService.getStudent();
        json.put("studentList",studentList);
        pw.write(json.toString());
    }


    /**
     * 导入-下载教师模板
     * @params pw 写入数据
     */
    @RequestMapping("/parentDownloadMould")
    public void parentDownloadMould(HttpServletRequest req,HttpServletResponse resp) {
        parentService.parentDownloadMould(req, resp);
    }

    /**
     * 导入-教师导入
     * @params pw 写入数据
     * 用户导入成功后走缓存
     */
    @RequestMapping("/parentUpload")
    public void parentUpload(PrintWriter pw,HttpServletRequest req,HttpServletResponse response) {
        JSONObject obj = parentService.parentUpload(req, response);
        pw.write(obj.toString());
    }

    /**
     * 删除家长
     * @param id 家长id
     * @return
     */
    @RequestMapping("/parentDelete")
    public void parentDelete(String id,PrintWriter pw){
        JSONObject json = new JSONObject();
        Result res = parentService.deleteParent(id);//获取服务中返回的结果
        if (res.getValue() != null) {
            json.put("value",res.getValue());
        }
        json.put("code",res.getCode());
        pw.write(json.toString());//将value和code传给前台
    }

    /**
     * 批量删除家长
     * @param pIds 家长id,多个用逗号隔开
     * @return
     */
    @RequestMapping("/parentsDelete")
    public void parentsDelete(String pIds,PrintWriter pw){
        JSONObject json = new JSONObject();
        Result res = parentService.deletesParent(pIds);//获取服务中返回的结果
        if (res.getValue() != null) {
            json.put("value",res.getValue());
        }
        json.put("code",res.getCode());
        pw.write(json.toString());//将value和code传给前台
    }
    /**
     * 设置教师头像
     */
    @RequestMapping("/imageUpload")
    public void imageUpload(PrintWriter pw,HttpServletRequest req,String x,String y,String w,String h,String width,String height,String extension) {

        JSONObject obj = parentService.imageUpload(req,Integer.parseInt(x),Integer.parseInt(y),Integer.parseInt(w),Integer.parseInt(h),extension);
        pw.write(obj.toString());
    }

    /**
     * 导出-教师数据导出
     * @params pw 写入数据
     */
    @RequestMapping("/parentExportExcel")
    public void parentExportExcel(HttpServletRequest req,HttpServletResponse resp){
        parentService.parentExportExcel(req, resp);
    }

    /**
     * 导出-教师数据导出失败
     * @params pw 写入数据
     */
    @RequestMapping("/parentDownloadFailed")
    public void parentDownloadFailed(HttpServletRequest req, HttpServletResponse resp) {
        parentService.parentDownloadFailed(req, resp);
    }

    /**
     * 检查图片大小
     * @params pw 写入数据
     */
    @RequestMapping("/parentImageCheck")
    public void parentImageCheck(HttpServletRequest req, PrintWriter pw) {
        JSONObject obj = parentService.parentImageCheck(req);
        pw.write(obj.toString());
    }
}

