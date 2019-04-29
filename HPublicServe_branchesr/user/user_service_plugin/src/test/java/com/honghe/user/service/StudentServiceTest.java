//package com.honghe.user.service;
//
//import com.honghe.config.DaoFactory;
//import com.honghe.user.dao.entity.Student;
//import com.honghe.user.util.SerialNumUtil;
//import com.honghe.dao.PageData;
//import org.junit.Before;
//import org.junit.Test;
//
//
///**
// * Created by lyx on 2017-01-07 0007.
// */
//
////@RunWith(SpringJUnit4ClassRunner.class)
////@ContextConfiguration(classes = DataSourceConfig.class)
//public class StudentServiceTest {
//
//    //    @Reference
//    StudentService studentService;
//
//    @Before
//    public void setUp() throws Exception {
//        studentService = DaoFactory.getBean(StudentService.class);
//    }
//
//    @Test
//    public void testAdd() throws Exception {
//        Student student = new Student();
//        String code = SerialNumUtil.getNumber("ST", null);
//        student.setStudent_code(code);
//        student.setStudent_num("100002");
//        student.setNamepy("sunchao");
//        student.setRealName("孙超");
//        student.setGender("1");
//        student.setNation("汉族");
//        student.setStatus("1");
//        student.setEnter_year("2017");
//        student.setMobile("15375206976");
//        student.setEmail("sunchao@honghe-tech.com");
//        student.setEnter_way("1");
//        student.setAddress("河北保定市北市区区");
//        student.setBirthday("1990-07-28");
//
//        int id = studentService.add(student);
//
//        System.out.println("id = " + id);
//
//    }
//
//
//    @Test
//    public void TestGetMaxStudentCode() {
//        String str = studentService.getMaxStudentCode();
//
//        System.out.println("str=" + str);
//    }
//
//
//    @Test
//    public void TestGetStudentsByPage() {
//        int page = 1;
//        int pageSize = 1;
//
//        PageData<Student> pageData = studentService.getStudentsByPage(page, pageSize,"","");
//
//        System.out.println("pageDate" + pageData.toString());
//
//    }
//}