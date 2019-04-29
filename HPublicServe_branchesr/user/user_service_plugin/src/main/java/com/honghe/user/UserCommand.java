package com.honghe.user;

import com.cpjit.swagger4j.annotation.API;
import com.cpjit.swagger4j.annotation.APIs;
import com.cpjit.swagger4j.annotation.DataType;
import com.cpjit.swagger4j.annotation.Param;
import com.honghe.BaseReflectCommand;
import com.honghe.ServiceFactory;
import com.honghe.communication.util.WebRootUtil;
import com.honghe.dao.PageData;
import com.honghe.exception.ParamException;
import com.honghe.role.dao.RoleDao;
import com.honghe.security.MD5;
import com.honghe.user.cache.UserCacheDao;
//import com.honghe.user.controller.UserController;
import com.honghe.user.dao.CampusDao;
import com.honghe.user.dao.UserDao;
import com.honghe.user.dao.entity.Student;
import com.honghe.user.dao.entity.Teacher;
import com.honghe.user.dao.entity.User;
import com.honghe.user.dao.impl.User2TeacherUserDao;
import com.honghe.user.service.StudentService;
import com.honghe.user.service.TeacherService;
import com.honghe.user.util.ExcelTools;
import com.honghe.user.util.SaltRandom;
import com.honghe.user.util.StringUtil;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author hh
 * @date 2016/12/2
 */
@APIs(value = "user")
public class UserCommand extends BaseReflectCommand {

    private User2TeacherUserDao user2TeacherDao = User2TeacherUserDao.INSTANCE;
    private UserDao userDao = UserDao.INSTANCE;
    private RoleDao roleDao = RoleDao.INSTANCE;
    private UserCacheDao userCacheDao = UserCacheDao.INSTANCE;
    private TeacherService teacherService = ServiceFactory.getInstance().getServiceInstance(TeacherService.class);
    private StudentService studentService = ServiceFactory.getInstance().getServiceInstance(StudentService.class);

    @Override
    public Logger getLogger() {
        return Logger.getLogger("user");
    }

    @Override
    public Class getCommandClass() {
        return this.getClass();
    }


    /**
     * 用户登陆验证
     *
     * @param loginName 用户名称
     * @param userName  用户名称
     * @param userPwd   用户密码
     * @return
     * @throws Exception
     */
    @API(value = "userCheck",
            method = "get",
            summary = "用户登陆验证",
            description = "用户登陆验证",
            parameters = {
                    @Param(in = "query", name = "loginName", description = "用户名称", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userName", description = "用户名称", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userPwd", description = "用户密码", dataType = DataType.STRING, required = true)
            })
    public Map<String, Object> userCheck(String loginName, String userName, String userPwd) throws Exception {
        String name = "";
        if (loginName != null) {
            name = loginName;
        }
        if ("".equals(name) && userName != null) {
            name = userName;
        }
        if ("".equals(name) || userPwd == null || "".equals(userPwd)) {
            throw new ParamException();
        }
        return userDao.find(name, userPwd);
    }

    /**
     * 用户注册
     *
     * @param userType     注册类型 1老师 2学生
     * @param agencyId     注册机构ID
     * @param userName     注册用户名
     * @param userPwd      注册密码
     * @param userRealName 用户真实姓名
     * @param mobile       用户手机号码
     * @param email        用户邮箱
     * @return int 1注册成功 -1注册失败 -5用户名重复
     */
    @API(value = "userRegister",
            method = "get",
            summary = "用户注册",
            description = "用户注册",
            parameters = {
                    @Param(in = "query", name = "userType", description = "注册类型 1老师 2学生", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "agencyId", description = "注册机构ID", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userName", description = "注册用户名", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userPwd", description = "注册密码", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userRealName", description = "用户真实姓名", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "mobile", description = "用户手机号码", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "email", description = "用户邮箱", dataType = DataType.STRING, required = true)
            })
    public int userRegister(String userType, String agencyId, String userName, String userPwd, String userRealName, String mobile, String email) {
        //先判断用户名/邮箱/手机号是否存在 ture为被使用过
        boolean isUsedUserName = userDao.find(userName);
        boolean isUsedEmail = false;
        if (!"".equals(email)) {
            isUsedEmail = userDao.hasUserEmail(email);
        }
        boolean isUsedMobile = false;
        if (!"".equals(email)) {
            isUsedMobile = userDao.hasUserMobile(mobile);
        }
        boolean isUsed = isUsedUserName || isUsedMobile || isUsedEmail;
        if (isUsed) {
            return -5;
        }
        //若没有注册类型 默认为学生
        if ("".equals(userType) || userName == null) {
            userType = "2";
        }
        int result = 1;
        //添加用户信息
        User user = new User();
        user.setUser_name(userName);
        String salt = SaltRandom.runVerifyCode(6);
        userPwd = new MD5().encrypt(userPwd + salt);
        user.setUser_pwd(userPwd);
        user.setUser_salt(salt);
        //资源平台注册用户标识
        user.setUser_hres("1");
        if ("1".equals(userType)) {
            //若注册人为老师 则添加到老师信息
            Teacher teacher = new Teacher();
            teacher.setUser(user);
            teacher.setTeacher_name(userRealName);
            teacher.setNamepy(userName);
            teacher.setMobile(mobile);
            teacher.setEmail(email);
            teacher.setEmployeeno("");
            teacher.setGender("0");
            teacher.setBirthday("");
            teacher.setAddress("");
            teacher.setQq("");
            teacher.setIdcode("");
            teacher.setShort_num("");
            teacher.setNation("");
            teacher.setPolitical("");
            teacher.setWork_date("");
            teacher.setTeach_date("");
            teacher.setStage_id("1");
            teacher.setProfessional_title("5");
            String teacherId = teacherService.addTeacher(teacher, "", "");
            if (teacherId.indexOf(",") < 0 && Integer.parseInt(teacherId) > 0) {
                User userAdd = teacher.getUser();
                String userId = String.valueOf(userAdd.getUser_id());
                List<Map<String, String>> campusList = CampusDao.INSTANCE.getAllCampus();
                teacherService.addCampus2user(agencyId, userId, campusList);
                //绑定教师角色
                UserDao.INSTANCE.getUserRoleRelationDao().add(userId, new String[]{"3"});
                result = Integer.parseInt(teacherId);
            } else {
                result = -1;
            }
        } else if ("2".equals(userType)) {
            //若注册人为学生 则添加到学生信息
            Student student = new Student();
            student.setUser(user);
            student.setNamepy(userName);
            student.setRealName(userRealName);
            student.setGender("0");
            student.setBirthday("");
            student.setMobile(mobile);
            student.setEmail(email);
            student.setStudent_num("");
            String studentId = studentService.add(student);
            if (studentId.indexOf(",") < 0 && Integer.parseInt(studentId) > 0) {
                User userAdd = student.getUser();
                List<Map<String, String>> campusList = CampusDao.INSTANCE.getAllCampus();
                String userId = String.valueOf(userAdd.getUser_id());
                studentService.addCampus2student(agencyId, userId, campusList);
                //绑定学生角色
                UserDao.INSTANCE.getUserRoleRelationDao().add(userId, new String[]{"4"});
                result = Integer.parseInt(studentId);
            } else {
                result = -1;
            }
        } else {
            //其他情况返回注册失败
            result = -1;
        }
        return result;
    }

    /**
     * 拒绝注册用户（删除）
     *
     * @param userId 用户id 多个用逗号分隔
     * @return
     * @throws Exception
     */
    @API(value = "userDeleteRegister",
            method = "get",
            summary = "拒绝注册用户（删除）",
            description = "拒绝注册用户（删除）",
            parameters = {
                    @Param(in = "query", name = "userId", description = "用户id 多个用逗号分隔", dataType = DataType.STRING, required = true)
            })
    public boolean userDeleteRegister(String userId) throws Exception {
        if (userId == null || "".equals(userId)) {
            throw new ParamException();
        }
        String[] userIds = userId.split(",");
        //先根据用户ID查找用户
        boolean deleteResult = false;
        for (int i = 0, idlen = userIds.length; i < idlen; i++) {
            Map user = userDao.findByUserId(userIds[i]);
            //若无该用户
            if (null == user || user.size() < 1) {
                continue;
            }
            String userCode = user.get("userCode").toString();
            //若删除教师
            if (userCode.startsWith("TE")) {
                deleteResult = (boolean) teacherService.teacherDelete(userCode);
            }
            //若删除学生
            else if (userCode.startsWith("ST")) {
                deleteResult = (boolean) studentService.studentDelete(userCode);
            }
        }
        return deleteResult;
    }

    /**
     * 给用户分配‘管理员’身份
     *
     * @param userId    用户id
     * @param campusIds 组织机构id串，多个用‘,’隔开
     * @return
     * @throws Exception
     */
    @API(value = "userEditAdmin",
            method = "get",
            summary = "给用户分配‘管理员’身份",
            description = "给用户分配‘管理员’身份",
            parameters = {
                    @Param(in = "query", name = "userId", description = "用户id", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "campusIds", description = "组织机构id串，多个用‘,’隔开", dataType = DataType.STRING, required = true)
            })
    public boolean userEditAdmin(String userId, String campusIds) throws Exception {
        boolean flag = false;
        if (campusIds == null || "".equals(campusIds)) {
            throw new ParamException();
        }
        if ("".equals(userId)) {
            flag = userDao.deleteUserAmin(campusIds);
        } else {
            flag = userDao.addUserAdmin(userId, campusIds);
        }
        return flag;
    }

    /**
     * 取消用户‘管理员’身份
     *
     * @param userId 用户id
     * @return
     */
    @API(value = "userClearAdmin",
            method = "get",
            summary = "取消用户‘管理员’身份",
            description = "取消用户‘管理员’身份",
            parameters = {
                    @Param(in = "query", name = "userId", description = "用户id", dataType = DataType.STRING, required = true)
            })
    public boolean userClearAdmin(String userId) throws Exception {
        if (userId == null || "".equals(userId)) {
            throw new ParamException();
        }
        return userDao.deleteUserAmin(userId);
    }

    /**
     * 根据用户编号搜索用户信息
     *
     * @param userNum 用户信息
     * @return
     * @throws ParamException
     */
    @API(value = "userCampusSearchByNum",
            method = "get",
            summary = "根据用户编号搜索用户信息",
            description = "根据用户编号搜索用户信息",
            parameters = {
                    @Param(in = "query", name = "userNum", description = "用户信息", dataType = DataType.STRING, required = true)
            })
    public Map<String, String> userCampusSearchByNum(String userNum) throws ParamException {
        if (userNum == null || userNum == "") {
            throw new ParamException();
        } else {
            return userDao.findUserInfo(userNum);
        }
    }

    /**
     * 查询用户信息
     *
     * @param userId     用户id
     * @param userName   用户名称
     * @param loginName  用户名称
     * @param userMobile 用户手机
     * @param userEmail  用户电子邮箱
     * @param userNum    用户编号
     * @return
     * @throws Exception
     */
    @API(value = "userSearch",
            method = "get",
            summary = "查询用户信息",
            description = "查询用户信息",
            parameters = {
                    @Param(in = "query", name = "userId", description = "用户id", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userName", description = "用户名称", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "loginName", description = "用户名称", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userMobile", description = "用户手机", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userEmail", description = "用户电子邮箱", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userNum", description = "用户编号", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "realName", description = "用户真实姓名", dataType = DataType.STRING, required = true)
            })
    public Object userSearch(String userId, String userName, String loginName, String userMobile,
                             String userEmail, String userNum, String realName) throws Exception {
        Map<String, String> result = null;
        List<Map<String, String>> resultList = null;
        if (userId != null && !"".equals(userId)) {
            String[] userIdArray = userId.split(",");
            if (userIdArray.length == 1) {
                result = userDao.find(Integer.parseInt(userIdArray[0]));
            } else {
                resultList = userDao.find(userIdArray);
            }
        } else if (userName != null && !"".equals(userName)) {
            result = userDao.findByName(userName);
        } else if (loginName != null && !"".equals(loginName)) {
            result = userDao.findByLoginName(loginName);
        } else if (userMobile != null && !"".equals(userMobile)) {
            return userDao.findByUserMobile(userMobile);
        } else if (userEmail != null && !"".equals(userEmail)) {
            return userDao.findByUserEmail(userEmail);
        } else if (userNum != null && !"".equals(userNum)) {
            return userDao.findByUserNum(userNum);
        } else if (realName != null && !"".equals(realName)) {
            resultList = userDao.findByRealName(realName);
        } else {
            throw new ParamException();
        }
        if (result != null) {
            return this.putAdminCampusList(result, userId);
        } else if (resultList != null) {
            List<Map<String, Object>> resList = new ArrayList<>();
            for (Map<String, String> map : resultList) {
                resList.add(this.putAdminCampusList(map, userId));
            }
            return resList;
        } else {
            throw new ParamException();
        }
    }

    private Map<String, Object> putAdminCampusList(Map<String, String> map, String userId) {
        List<Map<String, String>> adminCampusList = Collections.EMPTY_LIST;
        Map<String, Object> res = new HashMap<>();
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            res.put(key, map.get(key));
        }
        if ("1".equals(map.get("userIsAdmin"))) {
            adminCampusList = userDao.findAdminCampusList(map.get("userId"));
            List campusId = new ArrayList();
            for (Map campusMap : adminCampusList) {
                String id = campusMap.get("agencyId").toString();
                campusId.add(id);
            }
            if ("1".equals(userId)) {
                adminCampusList = CampusDao.INSTANCE.getAllCampusInfo();
            }
        }
        res.put("userAdminList", adminCampusList);
        return res;
    }

    /**
     * 通过用户id获取用户机构信息
     *
     * @param userId 登陆用户id
     * @return
     */
    @API(value = "userGetCampusById",
            method = "get",
            summary = "通过用户id获取用户机构信息",
            description = "通过用户id获取用户机构信息",
            parameters = {
                    @Param(in = "query", name = "userId", description = "登陆用户id", dataType = DataType.STRING, required = true)
            })
    public Map<String, Object> userGetCampusById(String userId) {
        Map<String, Object> userInfoMap = new HashMap<>();
        List<Map<String, String>> campusInfo = new ArrayList<>();
        Map<String, String> userInfo = new HashMap<>();
        campusInfo = userDao.getcampusById(userId);
        userInfo = userDao.getUserIsAdmin(userId);
        userInfoMap.put("userCampus", campusInfo);
        userInfoMap.put("userInfo", userInfo);
        return userInfoMap;
    }

    /**
     * 通过userId获取用户信息
     *
     * @param userId 用户id
     * @return
     */
    @API(value = "userGetIsAdmin",
            method = "get",
            summary = "通过userId获取用户信息",
            description = "通过userId获取用户信息",
            parameters = {
                    @Param(in = "query", name = "userId", description = "用户id", dataType = DataType.STRING, required = true)
            })
    public Map<String, Object> userGetIsAdmin(String userId) throws Exception {
        Map<String, Object> userAdminMap = new HashMap<>();
        if (userId == null || "".equals(userId)) {
            throw new ParamException();
        }
        List<Map<String, String>> adminCampus = new ArrayList<>();
        List campusIdList = new ArrayList();
        Map<String, String> userMap = userDao.getUserIsAdmin(userId);
        adminCampus = userDao.getCampusUser(userId);
        if (adminCampus != null && adminCampus.size() > 0) {
            for (Map campusId : adminCampus) {
                campusIdList.add(campusId.get("campus_id").toString());
            }
        }
        userAdminMap.put("userInfo", userMap);
        userAdminMap.put("adminCampus", campusIdList);
        return userAdminMap;
    }

    /**
     * 分页查询用户信息
     *
     * @param page       起始页
     * @param pageSize   总页数
     * @param loginName  关键词
     * @param userName   关键词
     * @param token      系统userName
     * @param userStatus 用户状态
     * @param campusId   组织机构id
     * @param userType   用户类型  teacher(教师),student(学生),parent(家长)
     * @return
     * @throws Exception
     */
    @API(value = "usergSearchByPage",
            method = "get",
            summary = "分页查询用户信息",
            description = "分页查询用户信息",
            parameters = {
                    @Param(in = "query", name = "page", description = "", dataType = DataType.INTEGER, required = true),
                    @Param(in = "query", name = "pageSize", description = "", dataType = DataType.INTEGER, required = true),
                    @Param(in = "query", name = "loginName", description = "", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userName", description = "", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "token", description = "", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userStatus", description = "", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userType", description = "", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "campusId", description = "", dataType = DataType.STRING, required = true)
            })
    public PageData userSearchByPage(Integer page, Integer pageSize, String loginName,
                                     String userName, String token, Integer userStatus, String userType, String campusId) throws Exception {
        if (page == null || page < 0 || pageSize == null || pageSize < 0) {
            throw new ParamException();
        }
        String name = "";
        if (loginName != null) {
            name = loginName;
        }
        if ("".equals(name) && userName != null) {
            name = userName;
        }
        if (token != null && !"".equals(token)) {
            return userDao.find(page, pageSize, name, token);
        } else {
            int state = 1;
            if (userStatus != null) {
                state = userStatus;
            }
            String usertype = "";
            String campusid = "";
            if (userType != null && !"".equals(userType)) {
                usertype = userType;
            }
            if (campusId != null && !"".equals(campusId)) {
                campusid = campusId;
            }
            return userDao.find(page, pageSize, name, state, usertype, campusid);
        }
    }

    /**
     * 分页获取注册用户
     *
     * @param page     当前页
     * @param pageSize 每页条数
     * @param keyword  关键字
     * @param campusId 机构ID
     * @param userType 用户类型
     * @param userId   登录者ID
     * @return
     */
    @API(value = "userRegisterByPage",
            method = "get",
            summary = "分页获取注册用户",
            description = "分页获取注册用户",
            parameters = {
                    @Param(in = "query", name = "page", description = "", dataType = DataType.INTEGER, required = true),
                    @Param(in = "query", name = "pageSize", description = "", dataType = DataType.INTEGER, required = true),
                    @Param(in = "query", name = "keyword", description = "", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "campusId", description = "", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userType", description = "", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userId", description = "", dataType = DataType.STRING, required = true)
            })
    public PageData userRegisterByPage(Integer page, Integer pageSize, String keyword, String campusId, String userType, String userId) throws Exception {
        if (page == null || page < 0 || pageSize == null || pageSize < 0) {
            throw new ParamException();
        }
        return userDao.userRegisterByPage(page, pageSize, keyword, campusId, userType, userId);
    }


    /**
     * 删除用户
     *
     * @param userId 用户id
     * @return
     * @throws Exception
     */
    @API(value = "userDelete",
            method = "get",
            summary = "删除用户",
            description = "删除用户",
            parameters = {
                    @Param(in = "query", name = "userId", description = "用户id", dataType = DataType.STRING, required = true)
            })
    public boolean userDelete(String userId) throws Exception {
        if (userId == null || "".equals(userId)) {
            throw new ParamException();
        }
        return userDao.updateUserStatus(userId, "2");
    }

    /**
     * 修改用户状态
     *
     * @param userId     用户id
     * @param userStatus 用户状态
     * @return
     * @throws Exception
     */
    @API(value = "userUpdateStatus",
            method = "get",
            summary = "修改用户状态",
            description = "修改用户状态",
            parameters = {
                    @Param(in = "query", name = "userId", description = "用户id", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userStatus", description = "用户状态", dataType = DataType.STRING, required = true)
            })
    public boolean userUpdateStatus(String userId, String userStatus) throws Exception {
        if (userId == null || "".equals(userId) || userStatus == null || "".equals(userStatus)) {
            throw new ParamException();
        }
        return userDao.updateUserStatus(userId, userStatus);
    }

    /**
     * 用户是否存在
     *
     * @param userName 用户名称
     * @return
     * @throws Exception
     */
    @API(value = "userIsExist",
            method = "get",
            summary = "用户是否存在",
            description = "用户是否存在",
            parameters = {
                    @Param(in = "query", name = "userName", description = "用户名称", dataType = DataType.STRING, required = true)
            })
    public boolean userIsExist(String userName) throws Exception {
        if (userName == null || "".equals(userName)) {
            throw new ParamException();
        }
        return userDao.find(userName);
    }

    /**
     * 修改用户名称
     *
     * @param userId   用户id
     * @param userName 用户新名称
     * @return
     * @throws Exception
     */
    @API(value = "userUpdateName",
            method = "get",
            summary = "修改用户名称",
            description = "修改用户名称",
            parameters = {
                    @Param(in = "query", name = "userId", description = "用户id", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userName", description = "用户新名称", dataType = DataType.STRING, required = true)
            })
    public Object userUpdateName(String userId, String userName) throws Exception {
        if (userId == null || "".equals(userId) || userName == null || "".equals(userName)) {
            throw new ParamException();
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("userName", userName);
        if (userDao.findExceptThis(map)) {
            return "-1";
        }
        return userDao.update(userId, userName);
    }

    /**
     * 查找身份信息
     *
     * @param typeName 身份名称
     * @param typeId   身份id
     * @return
     * @throws Exception
     */
    @API(value = "userTypeSearch",
            method = "get",
            summary = "查找身份信息",
            description = "查找身份信息",
            parameters = {
                    @Param(in = "query", name = "typeName", description = "身份名称", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "typeId", description = "身份id", dataType = DataType.STRING, required = true)
            })
    public Object userTypeSearch(String typeName, String typeId) throws Exception {
        if (typeName != null && !"".equals(typeName)) {
            return userDao.getUserTypeDao().findByTypeName(typeName);
        } else if (typeId != null && !"".equals(typeId)) {
            return roleDao.getRoleUserTypeRelationDao().findByUserType(typeId);
        }
        return userDao.getUserTypeDao().find();
    }

    /**
     * 通过机构搜索管理员
     *
     * @param agencyId
     * @return
     * @throws Exception
     */
    @API(value = "userAdminSearchByAgency",
            method = "get",
            summary = "通过机构搜索管理员",
            description = "通过机构搜索管理员",
            parameters = {
                    @Param(in = "query", name = "agencyId", description = "机构id", dataType = DataType.STRING, required = true)
            })
    public Map<String, String> userAdminSearchByAgency(String agencyId) throws Exception {
        if (agencyId == null || "".equals(agencyId)) {
            throw new ParamException();
        }
        return userDao.userAdminSearchByAgency(agencyId);
    }

    /**
     * 导出excel表
     *
     * @param loginName  用户名称（与userName相同，只传一个即可）
     * @param userName   用户名称
     * @param userStatus 用户状态
     * @return
     * @throws Exception
     */
    @API(value = "userExportExcel",
            method = "get",
            summary = "导出excel表",
            description = "导出excel表",
            parameters = {
                    @Param(in = "query", name = "loginName", description = "用户名称（与userName相同，只传一个即可）", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userName", description = "用户名称", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userStatus", description = "用户状态", dataType = DataType.STRING, required = true)
            })
    public List<Map<String, String>> userExportExcel(String loginName, String userName, Integer userStatus) throws Exception {
        // 为下载的Excel文件查询用户数据
        String name = "";
        if (loginName != null) {
            name = loginName;
        } else if (userName != null) {
            name = userName;
        }
        //正常用户
        int status = 1;
        if (userStatus != null) {
            status = userStatus;
        }
        return userDao.find(name, status);
    }

    /**
     * 删除角色用户关系
     *
     * @param roleId 角色id
     * @return
     * @throws Exception
     */
    @API(value = "user2roleDelete",
            method = "get",
            summary = "删除角色用户关系",
            description = "删除角色用户关系",
            parameters = {
                    @Param(in = "query", name = "roleId", description = "删除角色用户关系", dataType = DataType.STRING, required = true)
            })
    public boolean user2roleDelete(String roleId) throws Exception {
        if (roleId == null || "".equals(roleId)) {
            throw new ParamException();
        }
        return userCacheDao.getUserRoleRelationDao().deleteByRoleId(roleId);
    }

    /**
     * 导入excl表
     *
     * @param fileName excel文件名称
     * @return
     * @throws Exception
     */
    @API(value = "userImportExcel",
            method = "get",
            summary = "导入excl表",
            description = "导入excl表",
            parameters = {
                    @Param(in = "query", name = "fileName", description = "excel文件名称", dataType = DataType.STRING, required = true)
            })
    public String userImportExcel(String fileName) throws Exception {
        if (fileName == null || "".equals(fileName)) {
            throw new ParamException();
        }
        String fileTemp = WebRootUtil.getWebRoot() + "upload_dir" + File.separator + fileName;
        final File file = new File(fileTemp);
        ExcelTools tools = new ExcelTools();
        // 生成表头
        String[] headers = {"姓名", "编号*", "手机*", "邮箱*", "性别", "生日", "地址", "身份", "机构编号", "卡号", "学科", "简介"};
        String excelName = "用户列表";
        return tools.importObj(file, excelName, headers, new ExcelTools.ImportCallBack() {
            @Override
            public List<Object> ImportHandler(Sheet sheet, CellStyle cellStyle) throws Exception {
                List<Object> re_value = new ArrayList<>();

                Row row = null;// 对应excel的行
                int totalRow = sheet.getLastRowNum();// 得到excel的总记录条数
                String currentType = "";// 保存临时用户身份 用于减少数据库查找次数
                String currentTypeId = "";// 保存临时用户身份ID
                List<Map<String, String>> failedList = new LinkedList<>();
                List<Map<String, String>> campusList = new ArrayList<>();
                CampusDao campusDao = new CampusDao();
                campusList = campusDao.getAllCampus();
                for (int i = 2; i <= totalRow; i++) {
                    row = sheet.getRow(i);//获取单行数据
                    Map param = new HashMap();//获取所有数据
                    //需要判断每个单元格是否为空
                    param.put("cmd_op", "userAdd");
                    param.put("userPwd", "111111");//每个用户默认密码111111
                    param.put("userRealName", row.getCell(0) == null ? "" : row.getCell(0).toString());
                    param.put("userNum", row.getCell(1) == null ? "" : row.getCell(1).toString());
                    param.put("userMobile", row.getCell(2) == null ? "" : row.getCell(2).toString());
                    param.put("userEmail", row.getCell(3) == null ? "" : row.getCell(3).toString());
                    param.put("userGender", row.getCell(4) == null ? "" : row.getCell(4).toString());
                    param.put("userBirthday", row.getCell(5) == null ? "" : row.getCell(5).toString());
                    param.put("userAddress", row.getCell(6) == null ? "" : row.getCell(6).toString());
                    param.put("userType", row.getCell(7) == null ? "" : row.getCell(7).toString());
                    //TODO:暂时缺第八项逻辑方法 该项为机构编号
                    param.put("agencyNum", row.getCell(8) == null ? "" : row.getCell(8).toString());
                    param.put("userCard", row.getCell(9) == null ? "" : row.getCell(9).toString());
                    param.put("userCourse", row.getCell(10) == null ? "" : row.getCell(10).toString());
                    param.put("userInfo", row.getCell(11) == null ? "" : row.getCell(11).toString());
                    //此处判断若学号 手机 邮箱都没有时认为参数错误
                    if ("".equals(param.get("userNum")) && "".equals(param.get("userMobile")) && "".equals(param.get("userEmail"))) {
                        failedList.add(param);
                        continue;
                    }
                    //此处需要添加查询身份ID
                    String user_type = row.getCell(7) == null ? "" : row.getCell(7).toString();
                    if (!"".equals(user_type)) {
                        if (user_type.equals(currentType)) {
                            user_type = currentTypeId;
                        } else {
                            Map map = UserDao.INSTANCE.getUserTypeDao().findByTypeName(user_type);
                            //判断若没有此身份时认为参数错误
                            if (map.size() == 0) {
                                System.out.println("没有这个身份");
                                failedList.add(param);
                                continue;
                            }
                            //将上一个导入的用户的身份信息保存 为了减少查询数据库的次数
                            if (map.size() != 0) {
                                currentType = user_type;
                                user_type = map.get("typeId").toString();
                                currentTypeId = map.get("typeId").toString();
                            }
                        }
                    }
                    param.put("userType", user_type);
                    //判断性别后转为数据库中代号 1男 2女 0未知
                    String sex = row.getCell(4) == null ? "" : row.getCell(4).toString();
                    if ("男".equals(sex)) {
                        sex = "1";
                    } else if ("女".equals(sex)) {
                        sex = "2";
                    } else {
                        sex = "0";
                    }
                    param.put("userGender", sex);
                    //执行添加操作
                    if (UserDao.INSTANCE.find(param)) {
                        //用户信息有重复，判断是否要修改，用编号判断，若并不是编号错误则返回到错误文档
                        System.out.println("用户信息有重复");
                        //按学号查找用户信息
                        String userId_ = UserDao.INSTANCE.findByUserNum(row.getCell(1) == null ? "" : row.getCell(1).toString());

                        //若不是学号相同的用户则放入失败列表
                        if ("".equals(userId_)) {
                            //若学号不相同则把身份和性别还原后放入失败列表
                            param.put("userGender", row.getCell(4) == null ? "" : row.getCell(4).toString());
                            param.put("typeName", row.getCell(7) == null ? "" : row.getCell(7).toString());
                            failedList.add(param);
                            continue;
                        } else {
                            // 不修改admin信息
                            if ("1".equals(userId_)) {
                                failedList.add(param);
                                continue;
                            }
                            //修改user表属性
                            param.put("cmd_op", "userUpdate");
                            param.put("userId", userId_);
                            if (UserDao.INSTANCE.findExceptThis(param)) {
                                param.put("userGender", row.getCell(4) == null ? "" : row.getCell(4).toString());
                                failedList.add(param);
                                continue;
                            }
                            UserDao.INSTANCE.update(param);
                            // 通过身份建立用户与角色关系
                            if (!"".equals(user_type)) {
                                makeUser2roleByType(user_type, userId_);
                            }
                            // 添加用户与机构关系
                            String campusId = row.getCell(8) == null ? "" : row.getCell(8).toString();
                            campusDao.deleteUserAgencyRelation(userId_);
                            try {
                                if (hasCampus(campusList, campusId)) {
                                    campusDao.addUserAgencyRelation(campusId, userId_);
                                }
                            } catch (java.lang.ClassCastException e) {
                                continue;
                            }
                        }
                    } else {
                        //信息不重复时直接保存用户角色关系
                        long userId = UserDao.INSTANCE.add(param);
                        // 通过身份建立用户与角色关系
                        if (!"".equals(user_type)) {
                            makeUser2roleByType(user_type, String.valueOf(userId));
                        }
                        // 添加用户与机构关系
                        String campusId = row.getCell(8) == null ? "" : row.getCell(8).toString();
                        try {
                            if (hasCampus(campusList, campusId)) {
                                campusDao.deleteUserAgencyRelation(String.valueOf(userId));
                                campusDao.addUserAgencyRelation(campusId, String.valueOf(userId));
                            }
                        } catch (java.lang.ClassCastException e) {
                            continue;
                        }
                    }
                }

                FileUtils.forceDelete(file);
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
                // 插入数据
                for (int i = 0; i < objList.size(); i++) {
                    String[] strList = new String[12];
                    Map<String, String> obj = (Map<String, String>) objList.get(i);
                    strList[0] = obj.get("userRealName") == null ? "" : obj.get("userRealName");
                    strList[1] = obj.get("userNum") == null ? "" : obj.get("userNum");
                    strList[2] = obj.get("userMobile") == null ? "" : obj.get("userMobile");
                    strList[3] = obj.get("userEmail") == null ? "" : obj.get("userEmail");
                    String sex = obj.get("userGender") == null ? "" : obj.get("userGender");
                    if ("1".equals(sex)) {
                        sex = "男";
                    } else if ("2".equals(sex)) {
                        sex = "女";
                    } else {
                        sex = "";
                    }
                    strList[4] = sex;
                    strList[5] = obj.get("userBirthday") == null ? "" : obj.get("userBirthday");
                    strList[6] = obj.get("userAddress") == null ? "" : obj.get("userAddress");
                    //TODO：用户身份名称与导入时用户身份名称不统一
                    strList[7] = obj.get("typeName") == null ? "" : obj.get("typeName");
                    strList[8] = obj.get("agencyNum") == null ? "" : obj.get("angencyNum");
                    strList[9] = obj.get("userCard") == null ? "" : obj.get("userCard");
                    strList[10] = obj.get("userCourse") == null ? "" : obj.get("userCourse");
                    strList[11] = obj.get("userInfo") == null ? "" : obj.get("userInfo");
                    excelList.add(strList);
                }
                String filePath = "";
                try {
                    filePath = excel.exportTableByFile(fileName, headers, excelList, "", "tempDownExcel")[0];
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return filePath;
            }


        });
    }


    private void makeUser2roleByType(String userType, String userId) {
        //添加新的用户角色关系表内容
        List<Map<String, String>> roleList = RoleDao.INSTANCE.getRoleUserTypeRelationDao().findByUserType(userType);
        if (roleList.size() > 0) {
            String roleIds = "";
            for (Map map : roleList) {
                roleIds += map.get("roleId") + ",";
            }
            UserDao.INSTANCE.getUserRoleRelationDao().deleteByUserId(userId);
            List<Long> id = UserDao.INSTANCE.getUserRoleRelationDao().add(userId, roleIds.toString().split(","));
        }
    }

    /**
     * Excel中的机构ID是否存在
     *
     * @param campusId 机构ID
     * @return boolean
     */
    private boolean hasCampus(List<Map<String, String>> campusList, String campusId) {
        if ("".equals(campusId)) {
            return false;
        }
        for (int i = 0; i < campusList.size(); i++) {
            if (campusList.get(i).get("id").equals(campusId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 更新用户头像
     *
     * @param userId 用户id
     * @return
     * @throws Exception
     */
    @API(value = "userUpdateAvatar",
            method = "get",
            summary = "更新用户头像",
            description = "更新用户头像",
            parameters = {
                    @Param(in = "query", name = "userId", description = "用户id", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "fileName", description = "文件名称", dataType = DataType.STRING, required = true)
            })
    public Map userUpdateAvatar(String userId, String fileName) throws Exception {
        Map map = new HashMap();
        if (userId == null || "".equals(userId) || fileName == null || "".equals(fileName)) {
            throw new ParamException();
        }
        map = userDao.updateImage(userId, fileName);
        return map;
    }


    /**
     * 添加用户信息
     *
     * @param userType     用户类型
     * @param userRealName 用户姓名
     * @param userNum      用户编号
     * @param userAvatar   用户头像
     * @param userGender   用户性别
     * @param userBirthday 用户生日
     * @param userMobile   用户手机
     * @param userEmail    用户电子邮箱
     * @param userAddress  用户地址
     * @param userPwd      用户密码
     * @param userStatus   用户状态
     * @param userCard
     * @param userCourse
     * @param userInfo
     * @return
     * @throws Exception
     */
    @API(value = "userAdd",
            method = "get",
            summary = "添加用户信息",
            description = "添加用户信息",
            parameters = {
                    @Param(in = "query", name = "userPwd", description = "用户密码", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userType", description = "用户类型", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userRealName", description = "用户姓名", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userName", description = "用户姓名", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userPath", description = "用户头像路径", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userAvatar", description = "用户头像", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userGender", description = "用户性别", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userBirthday", description = "用户生日", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userMobil", description = "用户手机", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userEmail", description = "用户电子邮箱", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userAddress", description = "用户地址", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userNum", description = "用户编号", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userStatus", description = "用户状态", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userCard", description = "身份证号", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userCourse", description = "", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userInfo", description = "", dataType = DataType.STRING, required = true)
            })
    public Object userAdd(String userPwd, String userType, String userRealName, String userName, String userPath,
                          String userAvatar, String userGender, String userBirthday, String userMobile, String userEmail,
                          String userAddress, String userNum, String userStatus, String userCard, String userCourse, String userInfo) throws Exception {


        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userPwd", userPwd);
        map.put("userType", userType);

        if (userInfo != null) {
            map.put("userInfo", userInfo);
        }
        if (userName != null) {
            map.put("userName", userName);
        }
        if (userRealName != null) {
            map.put("userRealName", userRealName);
        }
        if (userPath != null) {
            map.put("userPath", userPath);
        }
        if (userAvatar != null && !"".equals(userAvatar)) {
            map.put("userAvatar", userAvatar);
        }
        if (userGender != null) {
            map.put("userGender", userGender);
        }
        if (userBirthday != null) {
            map.put("userBirthday", userBirthday);
        }
        if (userMobile != null) {
            map.put("userMobile", userMobile);
        }
        if (userEmail != null) {
            map.put("userEmail", userEmail);
        }
        if (userAddress != null) {
            map.put("userAddress", userAddress);
        }
        if (userNum != null) {
            map.put("userNum", userNum);
        }
        if (userStatus != null) {
            map.put("userStatus", userStatus);
        }
        if (userCard != null) {
            map.put("userCard", userCard);
        }
        if (userCourse != null) {
            map.put("userCourse", userCourse);
        }
        if (userInfo != null) {
            map.put("userInfo", userInfo);
        }

        if (userDao.find(map)) {
            return -1;
        }
        long userId = userDao.add(map);
        ExcelTools excelTools = new ExcelTools();
        makeUser2roleByType(userType, String.valueOf(userId));


        return userId;
    }

    /**
     * 修改用户信息
     *
     * @param userId       用户id
     * @param userType     用户类型
     * @param userRealName 用户姓名
     * @param userNum      用户编号
     * @param userAvatar   用户头像
     * @param userGender   用户性别
     * @param userBirthday 用户生日
     * @param userMobile   用户手机
     * @param userEmail    用户电子邮箱
     * @param userAddress  用户地址
     * @param userPwd      用户密码
     * @param userOldPwd   用户旧密码
     * @param userStatus   用户状态
     * @param userCard
     * @param userCourse
     * @param userInfo
     * @return
     * @throws Exception
     */
    @API(value = "userAdd",
            method = "get",
            summary = "添加用户信息",
            description = "添加用户信息",
            parameters = {
                    @Param(in = "query", name = "userId", description = "用户id", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userType", description = "用户类型", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userRealName", description = "用户姓名", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userNum", description = "用户编号", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userName", description = "用户姓名", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userPath", description = "用户头像路径", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userAvatar", description = "用户头像", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userGender", description = "用户性别", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userBirthday", description = "用户生日", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userMobil", description = "用户手机", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userEmail", description = "用户电子邮箱", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userAddress", description = "用户地址", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userPwd", description = "用户密码", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userOldPwd", description = "用户旧密码", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userStatus", description = "用户状态", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userCard", description = "身份证号", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userCourse", description = "", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userInfo", description = "", dataType = DataType.STRING, required = true)
            })
    public Object userUpdate(String userId, String userType, String userRealName, String userNum, String userAvatar,
                             String userGender, String userBirthday, String userMobile, String userEmail, String userAddress,
                             String userPwd, String userOldPwd, String userStatus, String userCard, String userCourse, String userInfo) throws Exception {
        if (userId == null || "".equals(userId)) {
            throw new ParamException();
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        if (userType != null) {
            map.put("userType", userType);
        }
        if (userRealName != null) {
            map.put("userRealName", userRealName);
        }
        if (userAvatar != null && !"".equals(userAvatar)) {
            map.put("userAvatar", userAvatar);
        }
        if (userGender != null) {
            map.put("userGender", userGender);
        }
        if (userBirthday != null) {
            map.put("userBirthday", userBirthday);
        }
        if (userMobile != null) {
            map.put("userMobile", userMobile);
        }
        if (userEmail != null) {
            map.put("userEmail", userEmail);
        }
        if (userAddress != null) {
            map.put("userAddress", userAddress);
        }
        if (userNum != null) {
            map.put("userNum", userNum);
        }
        if (userStatus != null) {
            map.put("userStatus", userStatus);
        }
        if (userCard != null) {
            map.put("userCard", userCard);
        }
        if (userCourse != null) {
            map.put("userCourse", userCourse);
        }
        if (userInfo != null) {
            map.put("userInfo", userInfo);
        }
        if (userDao.findExceptThis(map)) {
            return -1;
        }
        if (userOldPwd == null) {
            userOldPwd = "";
        }
        teacherService.updateTeacherByUserInfo(map);
        if (userPwd != null && !"".equals(userPwd)) {
            return userDao.update(userId, userOldPwd, userPwd);
        } else {
            return userDao.update(map);
        }
    }

    /**
     * 用户导入
     *
     * @param userPwd    用户密码
     * @param userNum    用户
     * @param campusName 组织机构名称
     * @param token      系统
     * @param userType   用户身份
     * @return
     * @throws Exception
     */
    @API(value = "userImport",
            method = "get",
            summary = "用户导入",
            description = "用户导入",
            parameters = {
                    @Param(in = "query", name = "userPwd", description = "用户密码", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userNum", description = "用户", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "campusName", description = "组织机构名称", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "token", description = "系统", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userType", description = "用户身份", dataType = DataType.STRING, required = true)
            })
    public Object userImport(String userPwd, String userNum, String campusName, String token, String userType) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("userPwd", userPwd);
        map.put("userNum", userNum);
        map.put("campusName", campusName);
        map.put("token", token);
        map.put("userType", userType);
        long campusId = 0;
        if (token != null && userPwd != null && userType != null && userNum != null) {
            //先判断用户存不存在

            String userId = userDao.findByUserNum(userNum.trim());
            if ("".equals(userId)) {
                userId = String.valueOf(userDao.add(map));
                if ("0".equals(userId)) {
                    return 0;
                }
            }
            if (campusName != null) { //操作机构表
                campusId = userDao.getCampusDao().findIdByName(campusName.toString());
                if (campusId == 0) {
                    return 0;
                }
                if (!userDao.getCampusDao().hasData(campusId, Long.parseLong(userId))) {
                    Map<String, Object> params = new HashMap<>();
                    params.put("campusId", campusId);
                    params.put("userId", userId);
                    if (userDao.getCampusDao().add(params) == 0) {
                        return 0;
                    }
                }

            }
        } else {
            throw new ParamException();
        }
        return campusId;
    }


    /**
     * 通过用户id获取用户身份
     *
     * @param userId 用户id
     * @return
     * @throws ParamException
     */
    @API(value = "userTypeCheck",
            method = "get",
            summary = "通过用户id获取用户身份",
            description = "通过用户id获取用户身份",
            parameters = {
                    @Param(in = "query", name = "userId", description = "用户id", dataType = DataType.STRING, required = true)
            })
    public String userTypeCheck(String userId) throws ParamException {
        if (userId == null || "".equals(userId)) {
            throw new ParamException();
        }
        return userDao.getType(userId);
    }

    /**
     * 设置教师是否是名师 是否是专家
     * (资源平台使用)
     *
     * @param teacherId   教师id
     * @param isFamous    是否是名师（0否, 1是）
     * @param isProfessor 是否是专家（0否, 1是）
     * @return
     * @throws ParamException
     */
    @API(value = "userSetFamous",
            method = "get",
            summary = "设置教师是否是名师 是否是专家(资源平台使用)",
            description = "设置教师是否是名师 是否是专家(资源平台使用)",
            parameters = {
                    @Param(in = "query", name = "teacherId", description = "教师id", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "isFamous", description = "是否是名师（0否, 1是）", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "isProfessor", description = "是否是专家（0否, 1是）", dataType = DataType.STRING, required = true)
            })
    public boolean userSetFamous(String teacherId, String isFamous, String isProfessor) throws ParamException {
        boolean result = ((StringUtil.isEmpty(isFamous)) && (StringUtil.isEmpty(isProfessor))) || (StringUtil.isEmpty(teacherId));
        if (result) {
            throw new ParamException();
        }
        return this.user2TeacherDao.setFamousTeacher(teacherId, isFamous, isProfessor);
    }

    /**
     * 查询教师是否是名师 是否是专家
     * (资源平台使用)
     *
     * @param teacherId 教师id
     * @return
     * @throws ParamException
     */
    @API(value = "userGetIsFamous",
            method = "get",
            summary = "查询教师是否是名师 是否是专家\n" +
                    "(资源平台使用)",
            description = "查询教师是否是名师 是否是专家\n" +
                    "(资源平台使用)",
            parameters = {
                    @Param(in = "query", name = "teacherId", description = "教师id", dataType = DataType.STRING, required = true)
            })
    public Object userGetIsFamous(String teacherId)
            throws ParamException {
        if (StringUtil.isEmpty(teacherId)) {
            throw new ParamException();
        }
        return this.user2TeacherDao.getIsFamous(teacherId);
    }

    /**
     * 用户关注其他用户功能
     * (资源平台使用)
     *
     * @param userId      必填，用户id
     * @param focusUserId 必填，要关注的用户id
     * @param focusFlag   必填，0取消关注  1关注
     * @return
     * @throws ParamException
     */
    @API(value = "userFocusOtherUser",
            method = "get",
            summary = "用户关注其他用户功能\n" +
                    "(资源平台使用)",
            description = "用户关注其他用户功能\n" +
                    "(资源平台使用)",
            parameters = {
                    @Param(in = "query", name = "userId", description = "必填，用户id", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "focusUserId", description = "必填，要关注的用户id", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "focusFlag", description = "必填，0取消关注  1关注", dataType = DataType.STRING, required = true)
            })
    public boolean userFocusOtherUser(String userId, String focusUserId, String focusFlag)
            throws ParamException {
        if ((StringUtil.isEmpty(userId)) || (StringUtil.isEmpty(focusUserId)) || (StringUtil.isEmpty(focusFlag))) {
            throw new ParamException();
        }
        return this.user2TeacherDao.userFocusOtherUser(userId, focusUserId, focusFlag);
    }

    /**
     * 查询当前用户是否关注某个老师
     * (资源平台使用)
     *
     * @param userId    必填，用户id
     * @param teacherId 必填，教师id
     * @return
     * @throws IllegalArgumentException
     */
    @API(value = "userFocusTeacher",
            method = "get",
            summary = "查询当前用户是否关注某个老师\n" +
                    "(资源平台使用)",
            description = "查询当前用户是否关注某个老师\n" +
                    "(资源平台使用)",
            parameters = {
                    @Param(in = "query", name = "userId", description = "必填，用户id", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "teacherId", description = "必填，教师id", dataType = DataType.STRING, required = true)
            })
    public Object userFocusTeacher(String userId, String teacherId) throws IllegalArgumentException {
        if (StringUtil.isEmpty(userId) || StringUtil.isEmpty(teacherId)) {
            throw new IllegalArgumentException();
        }
        return user2TeacherDao.findUserFocusTeacher(userId, teacherId);
    }

    /**
     * 查询某个用户的粉丝或关注列表
     * (资源平台使用)
     *
     * @param userId    必填，用户id
     * @param queryType 必填，0粉丝列表,1关注列表
     * @return
     * @throws ParamException
     */
    @API(value = "userFindFans",
            method = "get",
            summary = "查询某个用户的粉丝或关注列表\n" +
                    "(资源平台使用)",
            description = "查询某个用户的粉丝或关注列表\n" +
                    "(资源平台使用)",
            parameters = {
                    @Param(in = "query", name = "page", description = "", dataType = DataType.INTEGER, required = true),
                    @Param(in = "query", name = "pageSize", description = "", dataType = DataType.INTEGER, required = true),
                    @Param(in = "query", name = "userId", description = "必填，用户id", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "queryType", description = "必填，0粉丝列表,1关注列表", dataType = DataType.STRING, required = true)
            })
    public Object userFindFans(Integer page, Integer pageSize, String userId, String queryType)
            throws ParamException {
        if ((StringUtil.isEmpty(userId)) || (StringUtil.isEmpty(queryType))) {
            throw new ParamException();
        }
        return this.user2TeacherDao.findFansByUserId(page, pageSize, userId, queryType);
    }

    /**
     * 分页查找名师列表 专家列表
     * (资源平台)
     *
     * @param page
     * @param pageSize
     * @param queryType  1：查询名师分页列表 2查询专家分页列表
     *                   -1：查询所有老师分页列表
     * @param searchWord
     * @param subjectId
     * @param userIds
     * @return
     * @throws ParamException
     */
    @API(value = "userFamousProfessorByPage",
            method = "get",
            summary = "分页查找名师列表 专家列表\n" +
                    "(资源平台)",
            description = "分页查找名师列表 专家列表\n" +
                    "(资源平台)",
            parameters = {
                    @Param(in = "query", name = "page", description = "", dataType = DataType.INTEGER, required = true),
                    @Param(in = "query", name = "pageSize", description = "", dataType = DataType.INTEGER, required = true),
                    @Param(in = "query", name = "queryType", description = "", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "searchWord", description = "", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "subjectId", description = "", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "userIds", description = "", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "campusId", description = "", dataType = DataType.STRING, required = true)
            })
    public PageData userFamousProfessorByPage(Integer page, Integer pageSize, String queryType, String searchWord,
                                              String subjectId, String userIds, String campusId)
            throws ParamException {
        if ((page == null) || (pageSize == null) || (StringUtil.isEmpty(queryType))) {
            throw new ParamException();
        }
        return this.user2TeacherDao.finFamousProfessorList(page.intValue(), pageSize.intValue(), queryType, searchWord, subjectId, userIds, campusId);
    }

    /**
     * 分页查找普通老师列表
     * (资源平台)
     *
     * @param page
     * @param pageSize
     * @param searchWord
     * @param subjectId
     * @return
     * @throws ParamException
     */
    @API(value = "userCommonTeasByPage",
            method = "get",
            summary = "分页查找普通老师列表\n" +
                    "(资源平台)",
            description = "分页查找普通老师列表\n" +
                    "(资源平台)",
            parameters = {
                    @Param(in = "query", name = "page", description = "", dataType = DataType.INTEGER, required = true),
                    @Param(in = "query", name = "pageSize", description = "", dataType = DataType.INTEGER, required = true),
                    @Param(in = "query", name = "searchWord", description = "搜索关键字", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "subjectId", description = "科目id", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "campusId", description = "", dataType = DataType.INTEGER, required = true)
            })
    public PageData userCommonTeasByPage(Integer page, Integer pageSize, String searchWord, String subjectId, String campusId)
            throws ParamException {
        if ((page == null) || (page.intValue() < 0) || (pageSize == null) || (pageSize.intValue() < 0)) {
            throw new ParamException();
        }
        return this.user2TeacherDao.findCommonTeasList(page.intValue(), pageSize.intValue(), searchWord, subjectId, campusId);
    }

    /**
     * 获取粉丝最多的前几名老师 （明星老师）
     * (资源平台)
     *
     * @param topNum
     * @return
     * @throws ParamException
     */
    @API(value = "userGetStarTeacher",
            method = "get",
            summary = "获取粉丝最多的前几名老师 （明星老师）\n" +
                    "(资源平台)",
            description = "获取粉丝最多的前几名老师 （明星老师）\n" +
                    "(资源平台)",
            parameters = {
                    @Param(in = "query", name = "topNum", description = "前多少名", dataType = DataType.INTEGER, required = true)
            })
    public Object userGetStarTeacher(Integer topNum)
            throws ParamException {
        if (null == topNum) {
            throw new ParamException();
        }
        return this.user2TeacherDao.findStarTeacher(topNum.intValue());
    }

    /**
     * 根据身份及机构查询用户列表
     *
     * @param page
     * @param pageSize
     * @param userType
     * @param campusId
     * @param searchWord
     * @return
     */
    @API(value = "findUserByPage",
            method = "get",
            summary = "根据身份及机构查询用户列表",
            description = "根据身份及机构查询用户列表",
            parameters = {
                    @Param(in = "query", name = "page", description = "", dataType = DataType.INTEGER, required = true),
                    @Param(in = "query", name = "pageSize", description = "", dataType = DataType.INTEGER, required = true),
                    @Param(in = "query", name = "userType", description = "用户类型", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "campusId", description = "机构id", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "searchWord", description = "搜索关键词", dataType = DataType.STRING, required = true)
            })
    public Object userFindUserByPage(Integer page, Integer pageSize, String userType, String campusId, String searchWord) {
        return userDao.findUserByPage(userType, page, pageSize, campusId, searchWord);
    }

    /**
     * 获取所有被正常使用的用户信息
     * @return 用户列表
     */
    @API(value = "userFindAllUserInfo",
            method = "get",
            description = "获取所有教室列表"
        )
    public List<Map<String,String>> userFindAllUserInfo(){
        return userDao.getAllUserInfo();
    }



    /**
     * 根据用户id查询用户所在学段
     * @param userId
     * @return
     */

    @API(value = "findStagesByUserId",
            method = "get",
            summary = "根据用户id查询用户所在学段",
            description = "根据用户id查询用户所在学段",
            parameters = {
                    @Param(in = "query", name = "userId", description = "用户id", dataType = DataType.STRING, required = true)
            })
    public Map<String,String> userFindStagesByUserId(String userId){
        return userDao.findStagesByUid(userId);
    }
}
