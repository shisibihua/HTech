//package com.honghe.user.dao.impl;
//
//import com.honghe.config.DaoFactory;
//import org.junit.Before;
//import org.junit.Test;
//
///**
// * Created by lyx on 2017-01-11 0011.
// */
//public class TeacherDutyTypeDaoTest {
//    TeacherDutyTypeUserDao teacherDutyTypeDao;
//
//    @Before
//    public void setUp() throws Exception {
//        teacherDutyTypeDao = DaoFactory.getBean(TeacherDutyTypeUserDao.class);
//    }
//
//    @Test
//    public void testGetIdsByName() throws Exception {
//        String name = "学生,家长";
//        String ids = teacherDutyTypeDao.getIdsByName(name);
//        System.out.println("ids = " + ids);
//    }
//}