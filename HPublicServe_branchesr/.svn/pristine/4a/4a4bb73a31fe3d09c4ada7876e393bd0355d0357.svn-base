package com.honghe.user.service;

import com.honghe.AbstractService;
import com.honghe.DaoFactory;
import com.honghe.communication.util.WebRootUtil;
import com.honghe.exception.DaoException;
import com.honghe.exception.ParamException;
import com.honghe.security.MD5;
import com.honghe.user.dao.entity.*;
import com.honghe.user.dao.impl.ParentUserDao;
import com.honghe.user.dao.impl.StudentUserDao;
import com.honghe.user.dao.impl.UserEntityUserDao;
import com.honghe.user.util.ExcelTools;
import com.honghe.user.util.SaltRandom;
import com.honghe.user.util.SerialNumUtil;
import com.honghe.user.util.StringUtil;
import com.honghe.dao.PageData;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 家长操作服务类
 *
 * @author zhaowj
 * @create 2017-01-09 15:30
 **/
public class ParentService extends AbstractService {

    private final String TAGS = "PA";
    private ParentUserDao parentDao = DaoFactory.getInstance().getUserDaoInstance(ParentUserDao.class);
    private UserEntityUserDao userEntityDao = DaoFactory.getInstance().getUserDaoInstance(UserEntityUserDao.class);
    private StudentUserDao studentDao = DaoFactory.getInstance().getUserDaoInstance(StudentUserDao.class);
    /**
     * //重复错误返回码
     */
    private final int ERROR_REPEAT = -5;
    /**
     * //家长身份
     */
    private final String USERTYPE_PARENT = "19";

    public int parentAdd(Parent parent, String studentCodes) throws DaoException {
        //检查家长是否已经存在
        if (!userEntityDao.isHas(parent.getNamepy(), parent.getParent_mobile(), parent.getEmail(), "", "")) {
            String parentCode = SerialNumUtil.getNumber(TAGS, getMaxParentCode());
            parent.setParent_code(parentCode);
            String imgPath = "";
            //家长头像存储和获取路径
            if (!"".equals(parent.getParent_path())) {
                imgPath = userEntityDao.generateImagePath(parent.getParent_path());
                parent.setParent_path(imgPath);
            }
            //插入家长时，同时生成用户
            User user = new User();
            String salt = SaltRandom.runVerifyCode(6);
            String userPwd = "123456";
            userPwd = new MD5().encrypt(userPwd + salt);
            user.setUser_code(parent.getParent_code());
            user.setUser_pwd(userPwd);
            user.setUser_salt(salt);
            user.setUser_type(USERTYPE_PARENT);
            user.setUser_realname(parent.getParent_name());
            user.setUser_name(parent.getNamepy());
            user.setUser_path(imgPath);
            user.setUser_mobile(parent.getParent_mobile());
            user.setUser_email(parent.getEmail());
            user.setUser_num(parent.getParent_code());
            user.setUser_regdate(String.valueOf(new Date()));
            user.setUser_status("0");
            //默认是男
            user.setUser_gender("1");
            parent.setUser(user);
            //家长关联的学生集合
            Set<Student> studentSet = new LinkedHashSet<Student>();

            if (studentCodes.contains(",")) {
                String[] arrStudents = studentCodes.split(",");
                for (String item : arrStudents) {
                    Student student = studentDao.getStudentByCode(item);
                    studentSet.add(student);
                }
                parent.setStudentSet(studentSet);
            } else if (!StringUtil.isEmpty(studentCodes)) {
                Student student = studentDao.getStudentByCode(studentCodes);
                studentSet.add(student);
                parent.setStudentSet(studentSet);
            }
        } else {
            //家长重复
            return ERROR_REPEAT;
        }
        return parentDao.parentAdd(parent);
    }

    /**
     * 获取数据库中最大的系统编号
     *
     * @return
     */
    public String getMaxParentCode() {
        return parentDao.getMaxParentCode();
    }

    /**
     * 分页查询家长信息
     *
     * @param page       当前页
     * @param pageSize   每页条目
     * @param parentName 搜索关键字
     * @param parentCode 家长内部流水
     * @return
     */
    public PageData<Parent> getParentsByPage(int page, int pageSize, String parentName, String parentCode, String campusId) {
        return parentDao.getParentsByPage(page, pageSize, parentName, parentCode, campusId);
    }

//    /**
//     * 删除家长
//     *
//     * @param parentCode
//     * @return
//     * @throws Exception
//     */
//    @Transactional(value = "hibernateTransactionManager" ,rollbackFor = Exception.class)
//    public Object parentDelete(String parentCode) throws Exception {
//        return  parentDao.delete(parentCode);
//    }

    /**
     * 批量删除家长
     *
     * @param ids 家长id，多个用逗号隔开
     * @return
     * @throws Exception
     */
    public Object parentsDelete(String ids) throws Exception {
        return parentDao.deleteParents(ids);
    }

    /**
     * 依据家长名字返回对象list
     *
     * @param parentName 家长姓名可为空
     * @return
     */
    public List<Parent> getParentByName(final String parentName) {
        return parentDao.getParentByName(parentName);
    }


    /**
     * 通过用户id获取学生信息
     *
     * @param userId
     * @return
     * @throws Exception
     */
    public List<Parent> parentGetStudent(String userId) throws Exception {
        return parentDao.getStudentByParent(userId);
    }

//    /**
//     * 修改家长信息
//     *
//     * @param parent
//     * @param studentCodes
//     * @return
//     */
//    @Transactional(value = "hibernateTransactionManager" ,rollbackFor = Exception.class)
//    public int update(Parent parent, String studentCodes) {
//        Parent parentDs = null;
//        try {
//            parentDs = parentDao.getParentByCode(parent.getParent_code());
//            //检查家长是否已经存在
//            if (null == parentDs) {
//                return ERROR_EXIST_FALSE;
//            } else {
//                //检查家长是否已经存在
//                if (!userEntityDao.isHas(parent.getNamepy(), parent.getParent_mobile(), parent.getEmail(), "", parentDs.getParent_code())) {
//                    parent.setId(parentDs.getId());
//                    User updateUser = userEntityDao.getIdByCode(parent.getParent_code());
//                    //家长头像存储和获取路径
//                    String imgPath ="";
//                    if (!StringUtil.isEmpty(parent.getParent_path())) {
//                        imgPath = userEntityDao.generateImagePath(parent.getParent_path());
//                    }else{
//                        imgPath = parentDs.getParent_path();
//                    }
//                    parent.setParent_path(imgPath);
//                    updateUser.setUser_path(imgPath);
//                    updateUser.setUser_realname(parent.getParent_name());
//                    updateUser.setUser_mobile(parent.getParent_mobile());
//                    updateUser.setUser_email(parent.getEmail());
//                    updateUser.setUser_name(parent.getNamepy());
//                    parent.setUser(updateUser);
//                    //获取学生信息,并修改
//                    if (!StringUtil.isEmpty(studentCodes)) {
//                        Set<Student> studentSet = studentDao.getStudentsByIds(studentCodes);
//                        parent.setStudentSet(studentSet);
//                    }
//                } else {
//                    return ERROR_REPEAT;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("修改家长对象失败！");
//            return ERROR_ADD_UPDATE;
//
//        }
//        return parentDao.update(parent);
//    }

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
        String[] headers = {"姓名", "姓名拼音*", "手机", "邮箱", "学生学籍号*", "成员关系", "是否是监护人", "是否生活在一起"};
        String excelName = "家长表";
        return tools.importObj(file, excelName, headers, new ExcelTools.ImportCallBack() {
            @Override
            public List<Object> ImportHandler(Sheet sheet, CellStyle cellStyle) throws Exception {
                List<Object> re_value = new ArrayList<>();
                Row row = null;// 对应excel的行
                int totalRow = sheet.getLastRowNum();// 得到excel的总记录条数
                List<Object> failedList = new LinkedList<>();
                String title = sheet.getRow(0).getCell(0).toString().trim().substring(0, 2);
                if (!"家长".equals(title) || !"手机".equals(sheet.getRow(1).getCell(2).toString())) { //导入的文件不是教师列表的
                    row = sheet.createRow(1);
                    row.createCell(0).setCellStyle(cellStyle);
                    row.createCell(0).setCellValue("您导入的文件错误");
                    failedList.add(row);
                    return failedList;
                }
                for (int i = 2; i <= totalRow; i++) {
                    row = sheet.getRow(i);//获取单行数据
                    Parent parent = new Parent();
                    parent.setParent_name(StringUtil.getCellVal(row.getCell(0), false));
                    if (StringUtil.isEmpty(parent.getParent_name())) {
                        break;
                    }
                    parent.setNamepy(StringUtil.getCellVal(row.getCell(1), false));
                    if (null != row.getCell(2)) {
                        row.getCell(2).setCellStyle(cellStyle); //设置CELL格式为文本格式
                        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                    }
                    parent.setParent_mobile(StringUtil.getCellVal(row.getCell(2), false));
                    parent.setEmail(StringUtil.getCellVal(row.getCell(3), false));
                    if (null != row.getCell(4)) {
                        row.getCell(4).setCellStyle(cellStyle); //设置CELL格式为文本格式
                        row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                    }
                    String studentNums = StringUtil.getCellVal(row.getCell(4), false);
                    parent.setMembership((row.getCell(5) == null || "".equals(row.getCell(5).toString())) ? "1" : row.getCell(5).toString());
                    parent.setIs_guardian((row.getCell(6) == null || "".equals(row.getCell(6).toString())) ? "1" : row.getCell(6).toString());
                    parent.setIs_together((row.getCell(7) == null || "".equals(row.getCell(7).toString())) ? "1" : row.getCell(7).toString());
                    String stuCodes = studentDao.getCodesByNums(studentNums); //通过excel上的学籍号获取对应的code值
                    if (!checkParentData(parent)) {
                        failedList.add(row);
                        return failedList;
                    }
                    //查询该家长填写的学生是否存在
                    if (stuCodes == null || "".equals(stuCodes)) {
                        failedList.add(row);
                        return failedList;
                    }
                    long parentId = parentAdd(parent, stuCodes);
                    if (parentId > 0) {
                        User user = parent.getUser();
                        String usertype = String.valueOf(user.getUser_type());
                        String userId = String.valueOf(user.getUser_id());
                        // 通过身份建立用户与角色关系
                        userEntityDao.makeUser2roleByType(usertype, userId);
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
                    String[] strList = new String[12];
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
     * 导入时候校验手机号 邮箱 姓名拼音 是否有一项有值
     *
     * @param parent
     * @return
     */
    private boolean checkParentData(Parent parent) {
        boolean check = false;
        if (!StringUtil.isEmpty(parent.getNamepy())) {
            check = true;
        }
        if (!StringUtil.isEmpty(parent.getEmail())) {
            check = true;
        }
        if (!StringUtil.isEmpty(parent.getParent_mobile())) {
            check = true;
        }
        return check;
    }
}

