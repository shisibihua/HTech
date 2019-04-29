package com.honghe.user.controller;

import com.honghe.user.dao.CampusDao;
import com.honghe.user.dao.entity.Teacher;
import com.honghe.user.dao.entity.User;
import com.honghe.user.dao.impl.User2TeacherUserDao;
import com.honghe.ServiceFactory;
import com.honghe.user.service.TeacherService;
import com.honghe.dao.PageData;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hh
 * @date 2016/12/1
 */

public class TeacherController {
    Logger logger = Logger.getLogger(TeacherController.class);

    private TeacherService teacherService = ServiceFactory.getInstance().getServiceInstance(TeacherService.class);



    /**
     * 添加教师
     * @param teacher 添加的教师对象
     * @param dutyTypeIds 教师职务类型id（多个用“，”连接）
     * @param subjectIds 教师所教科目id（多个用“，”连接）
     * @return int
     * update by caoqian 返回值改为String
     */
    public String teacherAdd(Teacher teacher,String dutyTypeIds,String subjectIds,String campusId){
        String value="0";
        try {
            value = teacherService.addTeacher(teacher, dutyTypeIds, subjectIds);
        }catch (Exception e){
            value="0";
        }
        if (value.indexOf(",")<0 && Integer.parseInt(value)>0){
            User user = teacher.getUser();
            if(user!=null) {
                String userId = String.valueOf(user.getUser_id());
                List<Map<String, String>> campusList = CampusDao.INSTANCE.getAllCampus();
                teacherService.addCampus2user(campusId, userId, campusList);
            }
        }
        return value;
    }

    /**
     * 教师信息修改
     * @param teacher 教师对象
     * @param dutyTypeIds 教师职务类型id（多个用“，”连接）
     * @param subjectIds 教师所教科目id（多个用“，”连接）
     * @return String
     */
    public String teacherUpdate(Teacher teacher,String dutyTypeIds,String subjectIds,String campusIds){
        String value = teacherService.updateTeacher(teacher,dutyTypeIds,subjectIds);
        if (value!=null && value.indexOf(",")<0 && Integer.parseInt(value)==0){
            User user  = teacher.getUser();
            String userId = String.valueOf(user.getUser_id());
            List<Map<String,String>> campusList = new ArrayList<>();
            campusList = CampusDao.INSTANCE.getAllCampus();
            teacherService.addCampus2user(campusIds,userId,campusList);
        }
        return value;
    }


    /**
     * 根据教师内部编码获取教师信息
     * @param teacherCode 教师内部编码
     * @return
     */
    public Teacher teacherInfoByCode(String teacherCode){
        return teacherService.findTeacherByCode(teacherCode);
    }


    /**
     * 分页获取教师信息
     * @param currentPage
     * @param pageSize
     * @param teacherName
     * @param teacherCode
     * @return
     */
    public PageData teacherByPage(Integer currentPage, Integer pageSize, String teacherName, String teacherCode,String campusId,String flag){
        return teacherService.getTeacherByPage(currentPage,pageSize,teacherName,teacherCode,campusId,flag);
    }


    /**
     *获取职务类型列表,添加教师的时候用到
     * @return UserType
     */
    public List teacherDuty(){
        return teacherService.getTteacherDuty();
    }


   /**
    *获取学段列表,添加教师时的下拉菜单列表
    * @return Stage
    */
    public List teacherStage(){
        return teacherService.getStages();
    }


    /**
     * 获取教师科目列表，添加教师时的下拉菜单列表
     * @return Subject
     */
    public List teacherSubject(String seasonId){
        return teacherService.getSubjectInfo(seasonId);
    }


    /**
     * 从excel表导入教师数据
     *
     * @param fileName excel文件名称
     * @return
     * @throws Exception
     */
    public String teacherImportExcel(String fileName){
        String re_value = "RESULT_IMPORTERROR";
        try {
            re_value =  teacherService.importExcel(fileName);
        } catch (Exception e) {
            logger.error("教师导入失败",e);
        }
        return re_value;
    }


    /**
     * 导出到Excel表
     * @param teacherName 教师姓名
     * @return
     * @throws Exception
     */
    public List<Map<String, String>> teacherExportExcel( String teacherName){

        List<Map<String, String>> teacherList = new ArrayList<>();
        try {
            teacherList = teacherService.teacherExportExcel(teacherName);
        } catch (Exception e) {
            logger.error("教师查询导出失败",e);
        }
        return teacherList;
    }

    /**
     * 删除教师
     *
     * @param teacherCode
     * @return
     * @throws Exception
     */
    public Object teacherDelete(String teacherCode) throws Exception {
        return teacherService.teacherDelete(teacherCode);
    }

    /**
     * 批量删除教师
     * @param userIds 用户id，多个用逗号隔开
     * @return
     * @throws Exception
     */
    public Object teachersDelete(String userIds)throws Exception{
        return teacherService.teachersDelete(userIds);
}
    public Object teacherSubjects() {
        return User2TeacherUserDao.INSTANCE.getSubjects();
    }
}
