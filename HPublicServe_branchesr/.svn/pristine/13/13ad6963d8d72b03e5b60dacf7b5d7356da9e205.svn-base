package com.honghe.user.service;/**
 * Created by lyx on 2017-01-07 0007.
 */

import com.honghe.AbstractService;
import com.honghe.DaoFactory;
import com.honghe.communication.util.WebRootUtil;
import com.honghe.dao.PageData;
import com.honghe.exception.ParamException;
import com.honghe.security.MD5;
import com.honghe.user.dao.CampusDao;
import com.honghe.user.dao.entity.Student;
import com.honghe.user.dao.entity.User;
import com.honghe.user.dao.impl.StudentUserDao;
import com.honghe.user.dao.impl.UserEntityUserDao;
import com.honghe.user.util.ExcelTools;
import com.honghe.user.util.SaltRandom;
import com.honghe.user.util.SerialNumUtil;
import com.honghe.user.util.StringUtil;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 学生操作服务类
 *
 * @author lyx
 * @create 2017-01-07 15:30
 **/
public class StudentService extends AbstractService {

    private final String TAGS = "ST";
    private StudentUserDao studentDao = DaoFactory.getInstance().getUserDaoInstance(StudentUserDao.class);
    private UserEntityUserDao userEntityDao = DaoFactory.getInstance().getUserDaoInstance(UserEntityUserDao.class);
    /**
     * //学生身份
     */
    private final String USERTYPE_STUDENT = "18";

    public String add(Student student) {
        //检查学生是否已经存在
//        if (!userEntityDao.isHas(student.getNamepy(), student.getMobile(), student.getEmail(), student.getStudent_num(),"")) {
        User check_user = userEntityDao.checkUser(student.getNamepy(), student.getMobile(), student.getEmail(), student.getStudent_num(), "");
        if (check_user == null) {
            student.setStudent_code(SerialNumUtil.getNumber(TAGS, getMaxStudentCode()));
            //处理学生头像
            String imgPath = "";
            if (student.getStudent_path() != null && !"".equals(student.getStudent_path())) {
                imgPath = userEntityDao.generateImagePath(student.getStudent_path());
            }
            student.setStudent_path(imgPath);
            //插入学生时，同时生成用户
            User user = student.getUser();
            if (user == null) {
                user = new User();
                String salt = SaltRandom.runVerifyCode(6);
                String userPwd = "123456";
                userPwd = new MD5().encrypt(userPwd + salt);
                user.setUser_pwd(userPwd);
                user.setUser_salt(salt);
            }
            user.setUser_code(student.getStudent_code());
            user.setUser_name(student.getNamepy());
            user.setUser_type(USERTYPE_STUDENT);
            user.setUser_realname(student.getRealName());
            user.setUser_path(imgPath);
            user.setUser_gender(student.getGender());
            user.setUser_birthday(student.getBirthday());
            user.setUser_mobile(student.getMobile());
            user.setUser_email(student.getEmail());
            user.setUser_num(student.getStudent_num());
            user.setUser_regdate(String.valueOf(new Date()));
            user.setUser_status("0");
            student.setUser(user);
        } else {
            return getResultMsg(check_user, student); //学生重复
        }
        return String.valueOf(studentDao.add(student));
    }

    /**
     * 获取数据库中最大的系统编号
     *
     * @return
     */
    public String getMaxStudentCode() {
        return studentDao.getMaxStudentCode();
    }

    /**
     * 分页查询学生信息
     *
     * @param page        当前页
     * @param pageSize    每页条目
     * @param studentName 关键词
     * @param studentCode 关键词
     * @return
     */
    public PageData<Student> getStudentsByPage(int page, int pageSize, String studentName, String studentCode, String campusId) {
        return studentDao.getStudentsByPage(page, pageSize, studentName, studentCode, campusId);
    }

    /**
     * 根据条件查询学生信息
     *
     * @param studentKey 学生姓名或者学籍号
     * @return
     * @throws Exception
     */
    public List<Student> studentSearchByCondition(String studentKey) {
        return studentDao.studentSearchByCondition(studentKey);
    }

//    /**
//     * 获取所有的学生信息
//     *
//     * @return
//     */
//    public List<Object[]> getStudents() {
//        return studentDao.getStudents();
//    }

    //    /**
//     * 修改学生信息
//     *
//     * @param student
//     * @return
//     */
//    @Transactional(value = "hibernateTransactionManager" ,rollbackFor = Exception.class)
//    public String update(Student student) {
//        String update_result="";
//        Student studentDs = null;
//        try {
//            studentDs = studentDao.getStudentByCode(student.getStudent_code());
//            //检查学生是否已经存在
//            if (null == studentDs) {
//                return String.valueOf(ERROR_EXIST_FALSE);
//            } else {
//                //检查学生是否已经存在
////                if (!userEntityDao.isHas(student.getNamepy(), student.getMobile(), student.getEmail(), student.getStudent_num(),student.getStudent_code())) {
//                User check_user=userEntityDao.checkUser(student.getNamepy(), student.getMobile(), student.getEmail(), student.getStudent_num(), student.getStudent_code());
//                if (check_user==null) {
//                    student.setId(studentDs.getId());
//                    User updateUser = userEntityDao.getIdByCode(student.getStudent_code());
//                    //学生头像存储和获取路径
//                    String imgPath ="";
//                    if (!StringUtil.isEmpty(student.getStudent_path())) {
//                        imgPath = userEntityDao.generateImagePath(student.getStudent_path());
//                    }else{
//                        imgPath = studentDs.getStudent_path();
//                    }
//                    student.setStudent_path(imgPath);
//                    updateUser.setUser_path(imgPath);
//                    updateUser.setUser_realname(student.getRealName());
//                    updateUser.setUser_mobile(student.getMobile());
//                    updateUser.setUser_email(student.getEmail());
//                    updateUser.setUser_name(student.getNamepy());
//                    updateUser.setUser_num(student.getStudent_num());
//                    updateUser.setUser_gender(student.getGender());
//                    updateUser.setUser_address(student.getAddress());
//                    updateUser.setUser_birthday(student.getBirthday());
//                    student.setUser(updateUser);
//                }else{
//                    return getResultMsg(check_user,student);
//                }
//            }
//        } catch (Exception e) {
//            logger.error("修改学生对象失败！");
//            return String.valueOf(ERROR_ADD_UPDATE);
//        }
//        return String.valueOf(studentDao.update(student));
//    }

    /**
     * //返回错误信息  add by  caoqian
     *
     * @param check_user
     * @param student
     * @return
     */
    private String getResultMsg(User check_user, Student student) {
        String check_msg = "";
        if (!"".equals(check_user.getUser_name()) && check_user.getUser_name().equals(student.getNamepy())) {
            check_msg += " 学生名";
        }
        if (!"".equals(check_user.getUser_mobile()) && check_user.getUser_mobile().equals(student.getMobile())) {
            check_msg += " 电话号码";
        }
        if (!"".equals(check_user.getUser_email()) && check_user.getUser_email().equals(student.getEmail())) {
            check_msg += " 邮箱";
        }
        if (!"".equals(check_user.getUser_num()) && check_user.getUser_num().equals(student.getStudent_num())) {
            check_msg += " 学籍号";
        }
        check_msg += "已注册,请修改学生信息。";
        return check_msg;
    }

    /**
     * 从excel表导入教师数据
     *
     * @param fileName excel文件名称
     * @return
     * @throws Exception
     */
    public String importExcel(String fileName) throws Exception {
        if (fileName == null || "".equals(fileName)) {
            throw new ParamException();
        }
        String fileTemp = WebRootUtil.getWebRoot() + "upload_dir" + File.separator + fileName;
        final File file = new File(fileTemp);
        ExcelTools tools = new ExcelTools();
        // 生成表头
        String[] headers = {"姓名*", "姓名拼音*", "学籍号*", "手机", "邮箱", "性别", "生日", "户口所在地", "民族", "机构编号*", "就读方式", "在校状态", "入学方式", "入学年度"};
        String excelName = "学生表";

        return tools.importObj(file, excelName, headers, new ExcelTools.ImportCallBack() {
            @Override
            public List<Object> ImportHandler(Sheet sheet, CellStyle cellStyle) throws Exception {
                List<Object> re_value = new ArrayList<>();
                Row row = null;// 对应excel的行
                int totalRow = sheet.getLastRowNum();// 得到excel的总记录条数
                List<Object> failedList = new LinkedList<>();
                List<Map<String, String>> campusList = new ArrayList<>();
                campusList = CampusDao.INSTANCE.getAllCampus();
                String title = sheet.getRow(0).getCell(0).toString().trim().substring(0, 2);
                if (!"学生".equals(title) || !"学籍号*".equals(sheet.getRow(1).getCell(2).toString())) { //导入的文件不是教师列表的
                    row = sheet.createRow(1);
                    row.createCell(0).setCellStyle(cellStyle);
                    row.createCell(0).setCellValue("您导入的文件错误");
                    failedList.add(row);
                    return failedList;
                }
                for (int i = 2; i <= totalRow; i++) {
                    row = sheet.getRow(i);//获取单行数据
                    Student student = new Student();
                    student.setRealName(StringUtil.getCellVal(row.getCell(0), false));
                    if (StringUtil.isEmpty(student.getRealName())) {
                        break;
                    }
                    student.setNamepy(StringUtil.getCellVal(row.getCell(1), false));
                    if (null != row.getCell(2)) {
                        row.getCell(2).setCellStyle(cellStyle); //设置CELL格式为文本格式
                        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                    }
                    if (null != row.getCell(3)) {
                        row.getCell(3).setCellStyle(cellStyle); //设置CELL格式为文本格式
                        row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                    }
                    student.setStudent_num(StringUtil.getCellVal(row.getCell(2), false));
                    student.setMobile(StringUtil.getCellVal(row.getCell(3), false));
                    student.setEmail(StringUtil.getCellVal(row.getCell(4), false));
                    String gender = "1";
                    if (row.getCell(5) == null || "".equals(row.getCell(5).toString())) {
                        gender = "1 ";
                    } else {
                        if ("女".equals(row.getCell(5).toString())) {
                            gender = "2";
                        }
                    }
                    student.setGender(gender);
                    student.setBirthday(StringUtil.getCellVal(row.getCell(6), false));
                    student.setAddress(StringUtil.getCellVal(row.getCell(7), false));
                    student.setNation(StringUtil.getCellVal(row.getCell(8), false));
                    String campusId = (row.getCell(9) == null || "".equals(row.getCell(9).toString())) ? "-1" : row.getCell(9).toString();
                    student.setReadtype((row.getCell(10) == null || "".equals(row.getCell(10).toString())) ? "1" : row.getCell(10).toString());
                    student.setStatus((row.getCell(11) == null || "".equals(row.getCell(11).toString())) ? "1" : row.getCell(11).toString());
                    student.setEnter_way((row.getCell(12) == null || "".equals(row.getCell(12).toString())) ? "1" : row.getCell(12).toString());
                    student.setEnter_year(StringUtil.getCellVal(row.getCell(13), false));
                    if (!checkStudentData(student)) {
                        failedList.add(row);
                        return failedList;
                    }
                    if ("".equals(student.getStudent_num())) {
                        failedList.add(row);
                        return failedList;
                    }
                    String studentId = add(student);
                    if (studentId.indexOf(",") < 0 && Integer.parseInt(studentId) > 0) {
                        User user = student.getUser();
                        String usertype = String.valueOf(user.getUser_type());
                        String userId = String.valueOf(user.getUser_id());
                        // 通过身份建立用户与角色关系
                        userEntityDao.makeUser2roleByType(usertype, userId);
                        // 添加用户与机构关系
                        addCampus2student(campusId, userId, campusList);
                    } else {
                        failedList.add(row);
                    }
                }
                re_value = failedList;
                return re_value;
            }

            /**
             * 将导入失败的数据库回写到excel文件中
             *
             * @param objList 需要插入的数据
             * @param headers 表头
             * @return
             * @throws Exception
             */
            @Override
            public String exportExcel(List<Object> objList, String fileName, String[] headers) throws Exception {
                ExcelTools excel = new ExcelTools();
                List<String[]> excelList = new ArrayList<>();

                for (Object obj : objList) {
                    Row row = (Row) obj;
                    String[] strList = new String[14];
                    for (int i = 0; i < strList.length; i++) {
                        strList[i] = row.getCell(i) == null ? "" : row.getCell(i).toString();
                    }
                    excelList.add(strList);
                }
                String filePath = "";
                try {
                    filePath = excel.exportTableByFile(fileName, headers, excelList, "", "tempDownExcel" + StringUtil.currentTime())[0];
                } catch (IOException e) {
                    logger.error("生成导入结果失败", e);
                }
                return filePath;
            }
        });
    }


    /**
     * 导入时候校验手机号 邮箱 姓名拼音 学籍号 是否有一项有值
     *
     * @param student
     * @return
     */
    private boolean checkStudentData(Student student) {
        boolean check = false;
        if (!StringUtil.isEmpty(student.getStudent_num())) {
            check = true;
        }
        if (!StringUtil.isEmpty(student.getNamepy())) {
            check = true;
        }
        if (!StringUtil.isEmpty(student.getEmail())) {
            check = true;
        }
        if (!StringUtil.isEmpty(student.getMobile())) {
            check = true;
        }
        if (!StringUtil.isEmpty(student.getMobile())) {
            check = true;
        }
        return check;
    }

    /**
     * 导出
     *
     * @param studentName 学生姓名
     * @return
     * @throws Exception
     */
    public List<Map<String, String>> studentExportExcel(String studentName) throws Exception {
        // 为下载的Excel文件查询用户数据
        String name = "";
        if (studentName != null) {
            name = studentName;
        }
        return studentDao.getStudentsByName(name);
    }

    /**
     * 删除学生
     *
     * @param studentCode
     * @return
     * @throws Exception
     */
    public Object studentDelete(String studentCode) throws Exception {
        return studentDao.delete(studentCode);
    }

    /**
     * 批量删除学生
     *
     * @param ids 学生id，多个用逗号隔开
     * @return
     */
    public Object studentsDelete(String ids) throws Exception {
        return studentDao.deleteStudents(ids);
    }

    public void addCampus2student(String campusId, String userId, List<Map<String, String>> campusList) {
        if (userEntityDao.hasCampus(campusList, campusId)) {
            CampusDao.INSTANCE.deleteUserAgencyRelation(userId);
            CampusDao.INSTANCE.addUserAgencyRelation(campusId, userId);
        }
    }
}
