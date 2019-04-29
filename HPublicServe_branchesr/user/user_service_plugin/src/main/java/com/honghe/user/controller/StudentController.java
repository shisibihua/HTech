package com.honghe.user.controller;

import com.honghe.exception.ParamException;
import com.honghe.user.dao.CampusDao;
import com.honghe.user.dao.entity.Student;
import com.honghe.user.dao.entity.User;
import com.honghe.ServiceFactory;
import com.honghe.user.service.StudentService;
import com.honghe.dao.PageData;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by zhaowj on 2017/1/10.
 */
public class StudentController {

    private StudentService studentService = ServiceFactory.getInstance().getServiceInstance(StudentService.class);
    Logger logger = Logger.getLogger(StudentController.class);

    /**
     * 添加学生
     *
     * @param student
     * @return
     * @throws Exception
     */
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
    public List<Map<String, String>> studentExportExcel(String studentName) {
        List<Map<String, String>> studentList = new ArrayList<>();
        try {
            studentList = (List<Map<String, String>>) studentService.studentExportExcel(studentName);
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
    public Object studentDelete(String studentCode) throws Exception {
        return studentService.studentDelete(studentCode);
    }

    /**
     * 批量删除学生
     *
     * @param studentIds 学生id，多个用逗号隔开
     * @return
     */
    public Object studentsDelete(String studentIds) throws Exception {
        return studentService.studentsDelete(studentIds);
    }

}
