package com.honghe.user.service;

import com.honghe.AbstractService;
import com.honghe.DaoFactory;
import com.honghe.communication.util.WebRootUtil;
import com.honghe.exception.ParamException;
import com.honghe.security.MD5;
import com.honghe.user.dao.CampusDao;
import com.honghe.user.dao.UserDao;
import com.honghe.user.dao.entity.*;
import com.honghe.user.dao.entity.User;
import com.honghe.user.dao.impl.*;
import com.honghe.user.util.ExcelTools;
import com.honghe.user.util.SaltRandom;
import com.honghe.user.util.SerialNumUtil;
import com.honghe.user.util.StringUtil;
import com.honghe.dao.PageData;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.IOException;
import java.util.*;

import org.apache.poi.ss.usermodel.CellStyle;

/**
 * Created by sky on 2017/1/9.
 */
public class TeacherService extends AbstractService {

    /**
     * //修改成功
     */
    private final int SUCCESS_UPDATE = 0;
    /**
     * //添加失败错误返回码
     */
    private final int ERROR_ADD_UPDATE = -1;
    /**
     * //修改时候 用户不存在
     */
    private final int ERROR_EXIST_FALSE = -4;
    /**
     * //重复错误返回码
     */
    private final int ERROR_REPEAT = -5;
    /**
     * //老师身份
     */
    private final String USERTYPE_TEACHER = "17";

    private final String TAG = "TE";
    private TeacherUserDao teacherDao = DaoFactory.getInstance().getUserDaoInstance(TeacherUserDao.class);
    private TeacherDutyTypeUserDao teacherDutyTypeDao = DaoFactory.getInstance().getUserDaoInstance(TeacherDutyTypeUserDao.class);
    private SubjectUserDao subjectDao = DaoFactory.getInstance().getUserDaoInstance(SubjectUserDao.class);
    private UserEntityUserDao userEntityDao = DaoFactory.getInstance().getUserDaoInstance(UserEntityUserDao.class);
    private UserDao userDao=UserDao.INSTANCE;

    /**
     * 添加教师信息
     *
     * @param teacher
     * @return
     */
    public String addTeacher(Teacher teacher, String dutyTypeIds, String subjectIds) {
        String reValue = "";
        //判断新添加的教师在用户表里是否已经存在
//        if (!userEntityDao.isHas(teacher.getNamepy(), teacher.getMobile(), teacher.getEmail(), teacher.getEmployeeno(), "")) {
        User checkUser = userEntityDao.checkUser(teacher.getNamepy(), teacher.getMobile(), teacher.getEmail(), teacher.getEmployeeno(), "");
        if (checkUser == null) {
            //教师头像存储和获取路径
            String imgPath = "";
            if (teacher.getTeacher_path() != null && !"".equals(teacher.getTeacher_path())) {
                imgPath = userEntityDao.generateImagePath(teacher.getTeacher_path());
            }
            teacher.setTeacher_path(imgPath);
            teacher.setTeacher_code(SerialNumUtil.getNumber(TAG, getMaxTeacherCode()));
            //添加教师的时候，添加用户信息
            User user = teacher.getUser();
            //若为从用户服务添加的教师 则添加默认密码
            user = getUser(user);
            if (StringUtil.isEmpty(dutyTypeIds)) {
                dutyTypeIds = USERTYPE_TEACHER;
            }
            user.setUser_name(teacher.getNamepy());
            user.setUser_type(dutyTypeIds);
            user.setUser_realname(teacher.getTeacher_name());
            user.setUser_path(teacher.getTeacher_path());
            user.setUser_gender(teacher.getGender());
            user.setUser_birthday(teacher.getBirthday());
            user.setUser_mobile(teacher.getMobile());
            user.setUser_email(teacher.getEmail());
            user.setUser_num(teacher.getEmployeeno());
            user.setUser_address(teacher.getAddress());
            user.setUser_code(teacher.getTeacher_code());
            user.setUser_status("0");
            user.setUser_regdate(String.valueOf(new Date()));
            teacher.setUser(user);
            //添加教师职务
            Set<TeacherDutyType> userTypeSet = new LinkedHashSet<>();
            if (dutyTypeIds != null && !"".equals(dutyTypeIds)) {
                String[] dutyTypeId = dutyTypeIds.split(",");
                for (int i = 0; i < dutyTypeId.length; i++) {
                    TeacherDutyType teacherDutyType = new TeacherDutyType();
                    teacherDutyType.setId(Integer.parseInt(dutyTypeId[i]));
                    userTypeSet.add(teacherDutyType);
                }
            }
            teacher.setTeacherDutyTypes(userTypeSet);
            //添加教师所教科目
            Set<Subject> subjectSet = new LinkedHashSet<>();
            if (subjectIds != null && !"".equals(subjectIds)) {
                String[] subjectsId = subjectIds.split(",");
                for (int i = 0; i < subjectsId.length; i++) {
                    Subject subject = new Subject();
                    subject.setId(Integer.parseInt(subjectsId[i]));
                    subjectSet.add(subject);
                }
            }
            teacher.setSubject(subjectSet);
            int excute = teacherDao.add(teacher);
            if (excute > 0) {
                reValue = String.valueOf(excute);
            }
        } else {
            reValue = getResultMsg(checkUser, teacher);
        }
        return reValue;
    }

    private User getUser(User user) {
        if (user == null) {
            user = new User();
            String salt = SaltRandom.runVerifyCode(6);
            String userPwd = "123456";
            userPwd = new MD5().encrypt(userPwd + salt);
            user.setUser_pwd(userPwd);
            user.setUser_salt(salt);
        }
        return user;
    }

    /**
     * //返回错误信息  add by  caoqian
     *
     * @param checkUser
     * @param teacher
     * @return
     */
    private String getResultMsg(User checkUser, Teacher teacher) {
        String checkMsg = "";
        if(checkUser.getUser_name().equals(teacher.getEmployeeno())){
            checkMsg+="教师名或工号已注册,请修改教师信息。";
            return checkMsg;
        }
        if (!"".equals(checkUser.getUser_name()) && checkUser.getUser_name().equals(teacher.getNamepy())) {
            checkMsg += " 教师名";
        }
        if (!"".equals(checkUser.getUser_mobile()) && checkUser.getUser_mobile().equals(teacher.getMobile())) {
            checkMsg += " 电话号码";
        }
        if (!"".equals(checkUser.getUser_email()) && checkUser.getUser_email().equals(teacher.getEmail())) {
            checkMsg += " 邮箱";
        }
        if (!"".equals(checkUser.getUser_num()) && checkUser.getUser_num().equals(teacher.getEmployeeno())) {
            checkMsg += " 工号";
        }
        if(!"".equals(checkMsg)) {
            checkMsg += "已注册,请修改教师信息。";
        }
        return checkMsg;
    }

    /**
     * 根据教师内部编码获取教师信息
     *
     * @param teacherCode 教师内部编码
     * @return
     */
    public Teacher findTeacherByCode(String teacherCode) {
        Teacher teacher = new Teacher();
        teacher = (Teacher) teacherDao.findTeacherByCode(teacherCode);
        return teacher;
    }

    public static void main(String[] args) {
        Teacher teacher=new Teacher();
//        teacher.setTeacher_code("TE2018000002");
        teacher.setTeacher_name("侯文轩");
        teacher.setNamepy("houwenxuan");
        teacher.setMobile("15076268249");
        teacher.setEmployeeno("caoqian");
        TeacherService teacherUserDao=new TeacherService();
        String result=teacherUserDao.addTeacher(teacher,"17","1");
        System.out.println(result);
    }
    /**
     * 修改教师信息
     *
     * @param teacher     教师信息
     * @param dutyTypeIds 教师职务id，多个id用逗号分割
     * @param subjectIds  教师所教科目id，多个id用逗号分割
     * @return
     */
    public String updateTeacher(Teacher teacher, String dutyTypeIds, String subjectIds) {
        Teacher oldTeacher = teacherDao.findTeacherByCode(teacher.getTeacher_code());
        if (oldTeacher != null) {
            User checkUser = userEntityDao.checkUser(teacher.getNamepy(), teacher.getMobile(), teacher.getEmail(), teacher.getEmployeeno(), teacher.getTeacher_code());
            if (checkUser == null) {
                try {
                    /*//教师头像存储和获取路径
                    String imgPath = "";
                    if (teacher.getTeacher_path() != null && !"".equals(teacher.getTeacher_path())) {
                        imgPath = userEntityDao.generateImagePath(teacher.getTeacher_path());
                    } else {
                        imgPath = oldTeacher.getTeacher_path();
                    }

                    teacher.setTeacher_path(imgPath);*/
                    teacher.setTeacher_path("");
                    //修改用户相应信息
                    User user = oldTeacher.getUser();
                    if (dutyTypeIds.contains(",")) {
                        String[] dutyTypes = dutyTypeIds.split(",");
                        String dutyTypeId = dutyTypes[0];
                        user.setUser_type(dutyTypeId);
                    } else {
                        user.setUser_type(dutyTypeIds);
                    }
                    user.setUser_name(teacher.getNamepy());
                    user.setUser_realname(teacher.getTeacher_name());
//                    user.setUser_path(imgPath);
                    user.setUser_path("");
                    user.setUser_gender(teacher.getGender());
                    user.setUser_birthday(teacher.getBirthday());
                    user.setUser_mobile(teacher.getMobile());
                    user.setUser_email(teacher.getEmail());
                    user.setUser_num(teacher.getEmployeeno());
                    user.setUser_address(teacher.getAddress());
                    teacher.setId(oldTeacher.getId());
                    teacher.setUser(user);
                    //获取教师职务,并修改
                    if (!StringUtil.isEmpty(dutyTypeIds)) {
                        Set<TeacherDutyType> userTypeSet = teacherDutyTypeDao.getTypeByIds(dutyTypeIds);
                        teacher.setTeacherDutyTypes(userTypeSet);
                    }
                    //获取教师科目,并修改
                    if (!StringUtil.isEmpty(subjectIds)) {
                        Set<Subject> subjectSet = subjectDao.getSubjectById(subjectIds);
                        teacher.setSubject(subjectSet);
                    }
                    boolean execute=teacherDao.updateTeacher(teacher);
                    String result="";
                    if(execute){
                        //修改用户信息
                        updateUserData(user);
                        //修改教师与学科关系
                        teacherDao.saveTeacher2Subject(teacher);
                        result=String.valueOf(SUCCESS_UPDATE);
                    }else{
                        result=String.valueOf(ERROR_ADD_UPDATE);
                    }
                    return result;
                } catch (Exception e) {
                    logger.error("教师修改失败", e);
                    return String.valueOf(ERROR_ADD_UPDATE);
                }
            } else {
                return getResultMsg(checkUser, teacher);
            }
        }
        return String.valueOf(ERROR_EXIST_FALSE);
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    private boolean updateUserData(User user){
        Map<String,Object> userData=new HashMap<>();
        if(user.getUser_name()==null || user.getUser_realname()==null){
            return false;
        }
        userData.put("userId",user.getUser_id());
        userData.put("userType",user.getUser_type()==null?"":user.getUser_type());
        userData.put("userName",user.getUser_name());
        userData.put("userRealName",user.getUser_realname());
        userData.put("userPwd",user.getUser_pwd());
        userData.put("userGender",user.getUser_gender()==null?"1":user.getUser_gender());
        userData.put("userBirthday",user.getUser_birthday()==null?"":user.getUser_birthday());
        userData.put("userMobile",user.getUser_mobile()==null?"":user.getUser_mobile());
        userData.put("userEmail",user.getUser_email()==null?"":user.getUser_email());
        userData.put("userNum",user.getUser_num()==null?"":user.getUser_num());
        userData.put("userAddress",user.getUser_address()==null?"":user.getUser_address());
        return userDao.update(userData);
    }
    /**
     * 分页获取教师列表(包括条件查询)
     *
     * @param pageSize    每页的条目
     * @param currentPage 当前页
     * @param teacherName 教师名称（按名称查询）
     * @param teacherCode 教师内部编码（查询教师详情）
     * @return PageData
     */
    public PageData<Teacher> getTeacherByPage(int currentPage, int pageSize, String teacherName, String teacherCode, String campusId, String flag) {
        return teacherDao.getTeacherByPage(currentPage, pageSize, teacherName, teacherCode, campusId, flag);
    }

    /**
     * 获取数据库中最大的系统编号
     *
     * @return
     */
    public String getMaxTeacherCode() {
        return teacherDao.getMaxTeacherCode();
    }

    /**
     * 获取职务类型列表,添加教师的时候用到
     *
     * @return UserType
     */
    public List getTteacherDuty() {
        return teacherDao.getTteacherDuty();
    }

    /**
     * 获取学段列表,添加教师时的下拉菜单列表
     *
     * @return Stage
     */

    public List getStages() {
        return teacherDao.getStages();
    }

    /**
     * 获取教师科目列表，添加教师时的下拉菜单列表
     *
     * @return Subject
     */

    public List getSubjectInfo(String seasonId) {
        return teacherDao.getSubjectInfo(seasonId);
    }

    /**
     * 从excel表导入教师数据
     *
     * @param fileName excel文件名称，模板中组织机构id改为学校名
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
//        String[] headers = {"姓名*", "姓名拼音*", "编号", "手机", "邮箱", "性别", "生日", "地址", "身份证号", "民族", "政治面貌", "QQ号码", "所在机构名称*", "是否在职", "工作时间", "从教时间", "职称", "学段*", "职务", "学科"};
        String[] headers = {"姓名*", "姓名拼音*", "编号", "手机", "邮箱", "性别", "所在机构名称*", "学段*", "职务", "学科"};
        String excelName = "教师表";
        return tools.importObj(file, excelName, headers, new ExcelTools.ImportCallBack() {
            @Override
            public List<Object> ImportHandler(Sheet sheet, CellStyle cellStyle) throws Exception {
                List<Object> reValue;
                // 对应excel的行
                Row row = null;
                // 得到excel的总记录条数
                int totalRow = sheet.getLastRowNum();
                List<Object> failedList = new LinkedList<>();
                List<Map<String, String>> campusList = CampusDao.INSTANCE.getAllCampus();
                String title = sheet.getRow(0).getCell(0).toString().trim().substring(0, 2);
                //导入的文件不是教师列表的
                if (!"教师".equals(title) || !"编号".equals(sheet.getRow(1).getCell(2).toString())) {
                    logger.error("教师模板错误");
                    row = sheet.createRow(1);
                    row.createCell(0).setCellStyle(cellStyle);
                    row.createCell(0).setCellValue("您导入的文件错误");
                    failedList.add(row);
                    return failedList;
                }
                for (int i = 2; i <= totalRow; i++) {
                    //获取单行数据
                    row = sheet.getRow(i);
                    Teacher teacher = new Teacher();
                    teacher.setTeacher_name(StringUtil.getCellVal(row.getCell(0), false));
                    if (StringUtil.isEmpty(teacher.getTeacher_name())) {
                        break;
                    }
                    teacher.setNamepy(StringUtil.getCellVal(row.getCell(1), false));
                    if (teacher.getNamepy() == null || "".equals(teacher.getNamepy())) {
                        return failedList;
                    }
                    if (null != row.getCell(2)) {
                        //设置CELL格式为文本格式
                        row.getCell(2).setCellStyle(cellStyle);
                        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                    }
                    if (null != row.getCell(3)) {
                        //设置CELL格式为文本格式
                        row.getCell(3).setCellStyle(cellStyle);
                        row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                    }
                    teacher.setEmployeeno(StringUtil.getCellVal(row.getCell(2), false));
                    teacher.setMobile(StringUtil.getCellVal(row.getCell(3), false));
                    teacher.setEmail(StringUtil.getCellVal(row.getCell(4), false));
                    String gender = "1";
                    if (row.getCell(5) == null || "".equals(row.getCell(5).toString())) {
                        gender = "1 ";
                    } else {
                        if ("女".equals(row.getCell(5).toString())) {
                            gender = "2";
                        }
                    }
                    teacher.setGender(gender);
                    String schoolName = (row.getCell(6) == null || "".equals(row.getCell(6).toString())) ? "-1" : row.getCell(6).toString();
                    teacher.setStage_id((row.getCell(7) == null || "".equals(row.getCell(7).toString())) ? "1" : row.getCell(7).toString());
                    String dutyNames = StringUtil.getCellVal(row.getCell(8), false);
                    String subjectNames = StringUtil.getCellVal(row.getCell(9), false);

                    String dutyIds = teacherDutyTypeDao.getIdsByName(dutyNames);
                    String subjectIds = subjectDao.getIdsByName(subjectNames, Integer.valueOf(teacher.getStage_id()));
                    if (!checkTeacherData(teacher)) {
                        failedList.add(row);
                        return failedList;
                    }
                    List<Map<String,String>> schoolList=CampusDao.INSTANCE.getCampusByCampusName(schoolName.trim());
                    if(schoolList!=null && !schoolList.isEmpty()){
                        String teacherId = addTeacher(teacher, dutyIds, subjectIds);
                        if (teacherId.indexOf(",")<0 && Integer.parseInt(teacherId)>0) {
                            logger.debug("导入成功后教师id="+teacherId);
                            User user = teacher.getUser();
                            // 通过身份建立用户与角色关系
                            String userId = String.valueOf(user.getUser_id());
                            String usertypeStr = String.valueOf(user.getUser_type());
                            if (usertypeStr != null && !"".equals(usertypeStr)) {
                                String[] userTypes = usertypeStr.split(",");
                                for (String usertype : userTypes) {
                                    userEntityDao.makeUser2roleByType(usertype, userId);
                                }
                            }
                            String campusIds=schoolList.get(0).get("id");
                            // 添加用户与机构关系
                            addCampus2user(campusIds,userId,campusList);
                        } else {
                            failedList.add(row);
                        }
                    }else{
                        row.getCell(6).setCellStyle(cellStyle);
                        row.getCell(6).setCellValue(row.getCell(6)+",该机构不存在");
                        failedList.add(row);
                    }
                }
                reValue = failedList;
                return reValue;
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
                    String[] strList = new String[20];
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
     * 导入时候校验手机号 邮箱 姓名拼音 工号 是否有一项有值
     *
     * @param teacher
     * @return
     */
    private boolean checkTeacherData(Teacher teacher) {
        boolean check = false;
        if (!StringUtil.isEmpty(teacher.getEmployeeno())) {
            check = true;
        }
        if (!StringUtil.isEmpty(teacher.getNamepy())) {
            check = true;
        }
        if (!StringUtil.isEmpty(teacher.getEmail())) {
            check = true;
        }
        if (!StringUtil.isEmpty(teacher.getMobile())) {
            check = true;
        }
        if (!StringUtil.isEmpty(teacher.getMobile())) {
            check = true;
        }
        return check;
    }


    /**
     * 导出
     *
     * @param teacherName 教师姓名
     * @return
     * @throws Exception
     */
    public List<Map<String, String>> teacherExportExcel(String teacherName) throws Exception {
        // 为下载的Excel文件查询用户数据
        String name = "";
        if (teacherName != null) {
            name = teacherName;
        }
        return teacherDao.findTeachers(name);
    }

    /**
     * 删除教师
     *
     * @param teacherCode
     * @return
     * @throws Exception
     */
    public Object teacherDelete(String teacherCode) throws Exception {
        return teacherDao.delete(teacherCode);
    }

    /**
     * 批量删除教师
     *
     * @param userIds 用户id，多个用逗号隔开
     * @return
     * @throws Exception
     */
    public Object teachersDelete(String userIds) throws Exception {
        return teacherDao.deleteTeachers(userIds);
    }

    public void addCampus2user(String campusIds, String userId, List<Map<String, String>> campusList) {
        if (campusIds != null && !"".equals(campusIds)) {
            String[] campusid = campusIds.split(",");
            CampusDao.INSTANCE.deleteUserAgencyRelation(userId);
            for (String campusId : campusid) {
                if (userEntityDao.hasCampus(campusList, campusId)) {
                    CampusDao.INSTANCE.addUserAgencyRelation(campusId, userId);
                }
            }
        }
    }

    /**
     * 修改教师信息
     * @param map  用户信息
     * @author caoqian
     */
    public boolean updateTeacherByUserInfo(Map<String, Object> map) {
        boolean result=false;
        try{
            StringBuffer sqlBuffer=new StringBuffer();
            sqlBuffer.append("update user_teacher set ");
            if(map!=null && !map.isEmpty()){
                //拼音
                String name="";
                if(map.containsKey("userId")){
                    String userId=map.get("userId").toString().trim();
                    Map<String,String> user=userDao.findByUserId(userId);
                    if(user!=null && !user.isEmpty()){
                        name=user.get("userName");
                    }
                }else{
                    return false;
                }
                if(map.containsKey("userRealName")){
                    sqlBuffer.append("teacher_name='").append(map.get("userRealName").toString().trim()).append("',");
                }
                if(map.containsKey("userGender")){
                    sqlBuffer.append("gender=").append(map.get("userGender").toString().trim()).append(",");
                }
                if(map.containsKey("userBirthday")){
                    sqlBuffer.append("birthday='").append(map.get("userBirthday").toString().trim()).append("',");
                }
                if(map.containsKey("userMobile")){
                    sqlBuffer.append("mobile='").append(map.get("userMobile").toString().trim()).append("',");
                }
                if(map.containsKey("userEmail")){
                    sqlBuffer.append("email='").append(map.get("userEmail").toString().trim()).append("',");
                }
                if(map.containsKey("userAddress")){
                    sqlBuffer.append("address='").append(map.get("userAddress").toString().trim()).append("',");
                }
                if(map.containsKey("userNum")){
                    sqlBuffer.append("employeeno='").append(map.get("userNum").toString().trim()).append("',");
                }
                String sql=sqlBuffer.toString();
                if(sql.endsWith(",")){
                    sql=sql.substring(0,sql.lastIndexOf(","));
                }
                sql+=" where namepy='"+name+"'";
                result=teacherDao.updateTeacherByUserInfo(sql);
//                logger.debug("修改教师sql>>>>>>>>>>>>>>"+sqlBuffer.toString());
            }else{
                result=false;
            }
        }catch (Exception e){
            result= false;
        }
        return result;
    }
}
