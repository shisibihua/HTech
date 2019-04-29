package com.honghe.user;

import com.cpjit.swagger4j.annotation.API;
import com.cpjit.swagger4j.annotation.APIs;
import com.cpjit.swagger4j.annotation.DataType;
import com.cpjit.swagger4j.annotation.Param;
import com.honghe.BaseReflectCommand;
import com.honghe.ServiceFactory;
import com.honghe.dao.PageData;
import com.honghe.user.dao.CampusDao;
import com.honghe.user.dao.entity.Teacher;
import com.honghe.user.dao.entity.User;
import com.honghe.user.dao.impl.User2TeacherUserDao;
import com.honghe.user.service.TeacherService;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author hh 侯骏涛修改 2018-05-26
 * @date 2016/12/2
 */
@APIs(value = "user")
public class TeacherCommand extends BaseReflectCommand {
    private Logger logger = Logger.getLogger("user");

    private TeacherService teacherService = ServiceFactory.getInstance().getServiceInstance(TeacherService.class);

    @Override
    public Logger getLogger() {
        return Logger.getLogger("user");
    }

    @Override
    public Class getCommandClass() {
        return this.getClass();
    }


    /**
     * 添加教师
     *
     * @param teacher     添加的教师对象
     * @param dutyTypeIds 教师职务类型id（多个用“，”连接）
     * @param subjectIds  教师所教科目id（多个用“，”连接）
     * @return int
     * update by caoqian 返回值改为String
     */
    @API(value = "teacherAdd",
            method = "get",
            summary = "添加教师",
            description = "添加教师",
            parameters = {
                    @Param(in = "query", name = "teacher", description = "添加的教师对象", dataType = DataType.UNKNOWN, required = true),
                    @Param(in = "query", name = "dutyTypeIds", description = "教师职务类型id（多个用“，”连接）", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "subjectIds", description = "教师所教科目id（多个用“，”连接）", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "campusId", description = "机构id", dataType = DataType.STRING, required = true)
            })
    public String teacherAdd(Teacher teacher, String dutyTypeIds, String subjectIds, String campusId) {
        String value = teacherService.addTeacher(teacher, dutyTypeIds, subjectIds);
        if (value.indexOf(",") < 0 && Integer.parseInt(value) > 0) {
            User user = teacher.getUser();
            String userId = String.valueOf(user.getUser_id());
            List<Map<String, String>> campusList = new ArrayList<>();
            campusList = CampusDao.INSTANCE.getAllCampus();
            teacherService.addCampus2user(campusId, userId, campusList);
        }
        return value;
    }

    /**
     * 教师信息修改
     *
     * @param teacher     教师对象
     * @param dutyTypeIds 教师职务类型id（多个用“，”连接）
     * @param subjectIds  教师所教科目id（多个用“，”连接）
     * @return String
     */
    @API(value = "teacherUpdate",
            method = "get",
            summary = "教师信息修改",
            description = "教师信息修改",
            parameters = {
                    @Param(in = "query", name = "teacher", description = "教师对象", dataType = DataType.UNKNOWN, required = true),
                    @Param(in = "query", name = "dutyTypeIds", description = "教师职务类型id（多个用“，”连接）", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "subjectIds", description = "教师所教科目id（多个用“，”连接）", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "campusIds", description = "机构id", dataType = DataType.STRING, required = true)
            })
    public String teacherUpdate(Teacher teacher, String dutyTypeIds, String subjectIds, String campusIds) {
        String value = teacherService.updateTeacher(teacher, dutyTypeIds, subjectIds);
        if (value != null && value.indexOf(",") < 0 && Integer.parseInt(value) == 0) {
            User user = teacher.getUser();
            String userId = String.valueOf(user.getUser_id());
            List<Map<String, String>> campusList = new ArrayList<>();
            campusList = CampusDao.INSTANCE.getAllCampus();
            teacherService.addCampus2user(campusIds, userId, campusList);
        }
        return value;
    }


    /**
     * 根据教师内部编码获取教师信息
     *
     * @param teacherCode 教师内部编码
     * @return
     */
    @API(value = "teacherInfoByCode",
            method = "get",
            summary = "根据教师内部编码获取教师信息",
            description = "根据教师内部编码获取教师信息",
            parameters = {
                    @Param(in = "query", name = "teacherCode", description = "教师内部编码", dataType = DataType.STRING, required = true)
            })
    public Teacher teacherInfoByCode(String teacherCode) {
        return teacherService.findTeacherByCode(teacherCode);
    }


    /**
     * 分页获取教师信息
     *
     * @param currentPage
     * @param pageSize
     * @param teacherName
     * @param teacherCode
     * @return
     */
    @API(value = "teacherByPage",
            method = "get",
            summary = "分页获取教师信息",
            description = "分页获取教师信息",
            parameters = {
                    @Param(in = "query", name = "currentPage", description = "当前页", dataType = DataType.INTEGER, required = true),
                    @Param(in = "query", name = "pageSize", description = "页容量", dataType = DataType.INTEGER, required = true),
                    @Param(in = "query", name = "teacherName", description = "教师名称", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "teacherCode", description = "教师编号", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "campusId", description = "机构id", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "flag", description = "", dataType = DataType.STRING, required = true)
            })
    public PageData teacherByPage(Integer currentPage, Integer pageSize, String teacherName, String teacherCode, String campusId, String flag) {
        //todo flag的作用暂未知 侯骏涛
        return teacherService.getTeacherByPage(currentPage, pageSize, teacherName, teacherCode, campusId, flag);
    }


    /**
     * 获取职务类型列表,添加教师的时候用到
     *
     * @return UserType
     */
    @API(value = "teacherDuty",
            method = "get",
            summary = "获取职务类型列表,添加教师的时候用到",
            description = "获取职务类型列表,添加教师的时候用到"
    )
    public List teacherDuty() {
        return teacherService.getTteacherDuty();
    }


    /**
     * 获取学段列表,添加教师时的下拉菜单列表
     *
     * @return Stage
     */
    @API(value = "teacherStage",
            method = "get",
            summary = "获取学段列表,添加教师时的下拉菜单列表",
            description = "获取学段列表,添加教师时的下拉菜单列表"
    )
    public List teacherStage() {
        return teacherService.getStages();
    }


    /**
     * 获取教师科目列表，添加教师时的下拉菜单列表
     *
     * @return Subject
     */
    @API(value = "teacherSubject",
            method = "get",
            summary = "获取教师科目列表，添加教师时的下拉菜单列表",
            description = "获取教师科目列表，添加教师时的下拉菜单列表",
            parameters = {
                    @Param(in = "query", name = "seasonId", description = "学段id", dataType = DataType.STRING, required = true)
            })
    public List teacherSubject(String seasonId) {
        return teacherService.getSubjectInfo(seasonId);
    }


    /**
     * 从excel表导入教师数据
     *
     * @param fileName excel文件名称
     * @return
     * @throws Exception
     */
    @API(value = "teacherImportExcel",
            method = "get",
            summary = "从excel表导入教师数据",
            description = "从excel表导入教师数据",
            parameters = {
                    @Param(in = "query", name = "fileName", description = "excel文件名称", dataType = DataType.STRING, required = true)
            })
    public String teacherImportExcel(String fileName) {
        String re_value = "RESULT_IMPORTERROR";
        try {
            re_value = teacherService.importExcel(fileName);
        } catch (Exception e) {
            logger.error("教师导入失败", e);
        }
        return re_value;
    }


    /**
     * 导出到Excel表
     *
     * @param teacherName 教师姓名
     * @return
     * @throws Exception
     */
    @API(value = "teacherExportExcel",
            method = "get",
            summary = "导出到Excel表",
            description = "导出到Excel表",
            parameters = {
                    @Param(in = "query", name = "teacherName", description = "教师姓名", dataType = DataType.STRING, required = true)
            })
    public List<Map<String, String>> teacherExportExcel(String teacherName) {

        List<Map<String, String>> teacherList = new ArrayList<>();
        try {
            teacherList = teacherService.teacherExportExcel(teacherName);
        } catch (Exception e) {
            logger.error("教师查询导出失败", e);
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
    @API(value = "teacherDelete",
            method = "get",
            summary = "删除教师",
            description = "删除教师",
            parameters = {
                    @Param(in = "query", name = "teacherCode", description = "教师编号", dataType = DataType.STRING, required = true)
            })
    public Object teacherDelete(String teacherCode) throws Exception {
        return teacherService.teacherDelete(teacherCode);
    }

    /**
     * 批量删除教师
     *
     * @param userIds 用户id，多个用逗号隔开
     * @return
     * @throws Exception
     */
    @API(value = "teachersDelete",
            method = "get",
            summary = "批量删除教师",
            description = "批量删除教师",
            parameters = {
                    @Param(in = "query", name = "userIds", description = "用户id", dataType = DataType.STRING, required = true)
            })
    public Object teachersDelete(String userIds) throws Exception {
        return teacherService.teachersDelete(userIds);
    }

    /**
     * 获取科目信息（资源平台）
     *
     * @return
     */
    @API(value = "teacherSubjects",
            method = "get",
            summary = "获取科目信息（资源平台）",
            description = "获取科目信息（资源平台）")
    public Object teacherSubjects() {
        return User2TeacherUserDao.INSTANCE.getSubjects();
    }


}

