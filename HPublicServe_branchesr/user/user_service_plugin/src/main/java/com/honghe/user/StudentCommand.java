package com.honghe.user;

import com.cpjit.swagger4j.annotation.API;
import com.cpjit.swagger4j.annotation.APIs;
import com.cpjit.swagger4j.annotation.DataType;
import com.cpjit.swagger4j.annotation.Param;
import com.honghe.BaseReflectCommand;
import com.honghe.ServiceFactory;
import com.honghe.dao.PageData;
import com.honghe.exception.ParamException;
import com.honghe.user.dao.CampusDao;
import com.honghe.user.dao.entity.Student;
import com.honghe.user.dao.entity.User;
import com.honghe.user.service.StudentService;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author sky 侯骏涛修改 2018-05-26
 * @date 2017/1/10
 */
@APIs(value = "user")
public class StudentCommand extends BaseReflectCommand {
    private StudentService studentService = ServiceFactory.getInstance().getServiceInstance(StudentService.class);

    @Override
    public Logger getLogger() {
        return Logger.getLogger("user");
    }

    Logger logger = getLogger();

    @Override
    public Class getCommandClass() {
        return this.getClass();
//        return StudentController.class;
    }


    /**
     * 添加学生
     *
     * @param student
     * @return
     * @throws Exception
     */
    @API(value = "studentAdd",
            method = "get",
            summary = "添加学生",
            description = "添加学生",
            parameters = {
                    @Param(in = "query", name = "student", description = "学生实体(此参数为一实体，需要将此类所有属性以kv格式传入，与cmd等参数同级即可", dataType = DataType.UNKNOWN),
                    @Param(in = "query", name = "campusId", description = "机构id", dataType = DataType.STRING, required = true)
            })
    public Object studentAdd(Student student, String campusId) throws Exception {
        String studentId = studentService.add(student);
        if (studentId != null && studentId.indexOf(",") < 0 && Integer.parseInt(studentId) > 0) {
            User user = student.getUser();
            List<Map<String, String>> campusList = new ArrayList<>();
            campusList = CampusDao.INSTANCE.getAllCampus();
            String userId = String.valueOf(user.getUser_id());
            studentService.addCampus2student(campusId, userId, campusList);
        }
        return studentId;
    }


    /**
     * 分页查询学生信息
     *
     * @param page        起始页
     * @param pageSize    总页数
     * @param studentName 关键词
     * @param studentCode 内部流水
     * @return
     * @throws Exception
     */
    @API(value = "studentSearchByPage",
            method = "get",
            summary = "分页查询学生信息",
            description = "分页查询学生信息",
            parameters = {
                    @Param(in = "query", name = "page", description = "", dataType = DataType.INTEGER, required = true),
                    @Param(in = "query", name = "pageSize", description = "", dataType = DataType.INTEGER, required = true),
                    @Param(in = "query", name = "studentName", description = "", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "studentCode", description = "", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "campusId", description = "", dataType = DataType.STRING, required = true)
            })
    public PageData studentSearchByPage(Integer page, Integer pageSize, String studentName, String studentCode, String campusId) throws Exception {
        if (page == null || page < 0 || pageSize == null || pageSize < 0) {
            throw new ParamException();
        }
        return studentService.getStudentsByPage(page, pageSize, studentName, studentCode, campusId);
    }

    /**
     * 根据条件查询学生信息
     *
     * @param studentKey 学生姓名或者学籍号
     * @return
     * @throws Exception
     */
    @API(value = "studentSearchByCondition",
            method = "get",
            summary = "根据条件查询学生信息",
            description = "根据条件查询学生信息",
            parameters = {
                    @Param(in = "query", name = "studentKey", description = "学生姓名或者学籍号", dataType = DataType.STRING, required = true)
            })
    public List<Student> studentSearchByCondition(String studentKey) throws Exception {
        return studentService.studentSearchByCondition(studentKey);
    }

//    /**
//     * 获取所有的学生信息
//     * @return
//     */
//    public List<Object[]> studentList(){
//        return studentService.getStudents();
//    }

    /**
     * 从excel表导入学生数据
     *
     * @param fileName excel文件名称
     * @return
     * @throws Exception
     */
    @API(value = "studentImportExcel",
            method = "post",
            summary = "从excel表导入学生数据",
            description = "从excel表导入学生数据",
            parameters = {
                    @Param(in = "query", name = "fileName", description = "excel文件名称", dataType = DataType.STRING, required = true)
            })
    public String studentImportExcel(String fileName) {
        String re_value = "RESULT_IMPORTERROR";
        try {
            re_value = studentService.importExcel(fileName);
        } catch (Exception e) {
            logger.error("学生导入失败", e);
        }
        return re_value;
    }


    /**
     * 导出到Excel表
     *
     * @param studentName 教师姓名
     * @return
     * @throws Exception
     */
    @API(value = "studentExportExcel",
            method = "post",
            summary = "导出到Excel表",
            description = "导出到Excel表",
            parameters = {
                    @Param(in = "query", name = "studentName", description = "教师姓名", dataType = DataType.STRING, required = true)
            })
    public List<Map<String, String>> studentExportExcel(String studentName) {
        List<Map<String, String>> studentList = new ArrayList<>();
        try {
            studentList = studentService.studentExportExcel(studentName);
        } catch (Exception e) {
            logger.error("学生查询导出失败", e);
        }
        return studentList;
    }

//    /**
//     * 修改学生信息
//     * @param student
//     * @return
//     * @throws Exception
//     */
//    public Object studentUpdate(Student student,String campusId) throws Exception{
//        String value = studentService.update(student);
//        if (value!=null && value.indexOf(",")<0 && Integer.parseInt(value)==0){
//            User user = student.getUser();
//            List<Map<String,String>> campusList = new ArrayList<>();
//            campusList = CampusDao.INSTANCE.getAllCampus();
//            String userId = String.valueOf(user.getUser_id());
//            studentService.addCampus2student(campusId,userId,campusList);
//        }
//        return value;
//    }

    /**
     * 删除学生
     *
     * @param studentCode
     * @return
     * @throws Exception
     */
    @API(value = "studentDelete",
            method = "get",
            summary = "删除学生",
            description = "删除学生",
            parameters = {
                    @Param(in = "query", name = "studentCode", description = "学号", dataType = DataType.STRING, required = true)
            })
    public Object studentDelete(String studentCode) throws Exception {
        return studentService.studentDelete(studentCode);
    }

    /**
     * 批量删除学生
     *
     * @param studentIds 学生id，多个用逗号隔开
     * @return
     */
    @API(value = "studentsDelete",
            method = "get",
            summary = "批量删除学生",
            description = "批量删除学生",
            parameters = {
                    @Param(in = "query", name = "studentIds", description = "学生id，多个用逗号隔开", dataType = DataType.STRING, required = true)
            })
    public Object studentsDelete(String studentIds) throws Exception {
        return studentService.studentsDelete(studentIds);
    }

}
