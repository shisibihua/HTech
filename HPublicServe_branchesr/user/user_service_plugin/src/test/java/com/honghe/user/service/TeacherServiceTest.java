//package com.honghe.user.service;
//
//import com.honghe.config.DaoFactory;
//import com.honghe.user.util.JdbcTemplateUtil;
//import com.honghe.user.dao.entity.Teacher;
//import com.honghe.user.util.SerialNumUtil;
//import com.honghe.dao.PageData;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.List;
//
//public class TeacherServiceTest {
//
//    TeacherService teacherService;
//
//
//    @Before
//    public void setUp() throws Exception {
//        teacherService = DaoFactory.getBean(TeacherService.class);
//        JdbcTemplateUtil.setConnectionInfo("jdbc:mysql://192.168.17.19:3306/service?user=root&password=zhaolijiao&useUnicode=true&characterEncoding=utf8");
//
//        System.out.println("");
//
//    }
//
//    /**
//     * 添加教师信息
//     */
//    @Test
//    public void testAddTeacher() throws Exception {
//        Teacher teacher = new Teacher();
//        String dutyTypeIds = "1";
//        String subjectIds = "2";
//        String code = SerialNumUtil.getNumber("TE", null);
//        teacher.setTeacher_code(code);
//        teacher.setTeacher_name("Hommy");
//        teacher.setEmployeeno("4355");
//        teacher.setNamepy("Hommy");
//        teacher.setTeacher_path("/teacher/img");
//        teacher.setGender("1");
//        teacher.setMobile("148992545852");
//        teacher.setEmail("Tom@honghe-tech.com");
//        teacher.setQq("15225");
//        teacher.setIdcode("130125199202145896");
//        teacher.setBirthday("1970-07-28");
//        teacher.setShort_num("25553");
//        teacher.setNation("汉族");
//        teacher.setPolitical("党员");
//        teacher.setAddress("保定市北市区");
//        teacher.setWork_date("1990-07-28");
//        teacher.setIs_job("1");
//        teacher.setTeach_date("1993-07-08");
//        teacher.setProfessional_title("1");
//        teacher.setStage_id("2");
//        int val = teacherService.addTeacher(teacher, dutyTypeIds, subjectIds);
//        System.out.println(val);
//    }
//
//    /**
//     * 分页获取教师列表
//     */
//    @Test
//    public void testGetTeacherByPage() throws Exception {
//        int currentPage = 1;
//        int pageSize = 7;
//        String teacherName = "";
//        String teacherCode = "";
//        PageData<Teacher> page = teacherService.getTeacherByPage(currentPage, pageSize, teacherName, teacherCode);
//        System.out.println(page);
//
//    }
//
//    /**
//     * 获取数据库中最大的系统编号
//     *
//     * @throws Exception
//     */
//    @Test
//    public void testGetMaxTeacherCode() throws Exception {
//        String str = teacherService.getMaxTeacherCode();
//        System.out.println(str);
//
//    }
//
//    @Test
//    public void testGetUserType() throws Exception {
//        List list = teacherService.getTteacherDuty();
//        System.out.println(list);
//
//    }
//
//    @Test
//    public void testGetStages() throws Exception {
//        List list = teacherService.getStages();
//        System.out.println(list);
//    }
//
//    @Test
//    public void testGetSubjectInfo() throws Exception {
//        List list = teacherService.getSubjectInfo();
//        System.out.println(list);
//    }
//
//    @Test
//    public void testImportExcel() throws Exception {
//        String fileName = "D:\\project_web\\publicServices\\user\\user_service_plugin\\target\\classes\\教师表.xlsx";
//        String result = teacherService.importExcel(fileName);
//        System.out.println(result);
//    }
//}