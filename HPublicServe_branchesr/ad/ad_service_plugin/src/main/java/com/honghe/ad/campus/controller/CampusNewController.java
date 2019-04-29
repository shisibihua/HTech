package com.honghe.ad.campus.controller;

import com.honghe.ad.Directory;
import com.honghe.ad.campus.dao.Campus2UserDao;
import com.honghe.ad.campus.dao.Campus2UserNewDao;
import com.honghe.ad.campus.dao.CampusDao;
import com.honghe.ad.campus.dao.CampusNewDao;
import com.honghe.ad.excetion.ParamException;
import com.honghe.ad.excetion.RepeatingDateException;
import com.honghe.ad.util.ExcelUtil;
import com.honghe.dao.PageData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 组织机构的控制
 *
 * @author lyx
 * @create 2016-11-28 18:21
 **/
public class CampusNewController {

    private CampusNewDao campusNewDao = CampusNewDao.INSTANCE;
    private Campus2UserNewDao campus2UserNewDao = Campus2UserNewDao.INSTANCE;
    private CampusDao campusDao = CampusDao.INSTANCE;
    private Campus2UserDao campus2UserDao = Campus2UserDao.INSTANCE;
    private final String USERTYPE_PARENT = "19"; //家长身份

    /**
     * 增加校园组织机构
     *
     * @param name，pId不能为空
     * @return
     * @throws ParamException
     * @throws RepeatingDateException
     */
    public String campusAdd(String name, String pId, String number, String typeId, String stagesId, String schoolYear, String remark) throws Exception {
        String re_value = "";
        if (name == null || "".equals(name) ||typeId ==null ||"".equals(typeId)|| pId == null || "".equals(pId)) {
            throw new ParamException();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("pId", pId);
        if (!campusDao.check(map)) {
            throw new RepeatingDateException();
        }
        map.put("number", number);
        map.put("typeId", typeId);
        map.put("stagesId", stagesId);
        map.put("schoolYear", schoolYear);
        if (remark == null) {
            map.put("remark", "");
        } else {
            map.put("remark", remark);
        }
        re_value = campusDao.add(map);
        return re_value;
    }

    /**
     * 删除校园组织机构
     *
     * @param id 机构ID，多个用,分隔
     * @return boolean 是否删除成功
     * @throws ParamException
     */
    public boolean campusDelete(String id) throws ParamException {
        boolean re_value = false;
        if (id == null) {
            throw new ParamException();
        }
        re_value = campusDao.delete(id.split(","));
        return re_value;
    }

    /**
     * 要修改的结构名称
     *
     * @param id,name,pId 不能为空
     * @return
     * @throws ParamException
     * @throws RepeatingDateException
     */
    public boolean campusUpdate(String id, String name, String pId, String number, String typeId, String stagesId, String schoolYear, String remark) throws ParamException, RepeatingDateException {
        boolean re_value = false;
        if (id == null || "".equals(id)) {
            throw new ParamException();
        }
        if (pId == null || "".equals(pId)) {
            throw new ParamException();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("pId", pId);
        if (name != null) {
            map.put("name", name);
        }
        if (!campusDao.check(map)) {
            throw new RepeatingDateException();
        }
        map.put("number", number);
        map.put("typeId", typeId);
        map.put("stagesId", stagesId);
        map.put("schoolYear", schoolYear);
        if (remark != null) {
            map.put("remark", remark);
        }
        re_value = campusDao.update(map);
        return re_value;
    }

    /**
     * 向组织机构增加用户
     *
     * @param campusId 结构id
     * @param userId   用户id,多个用,分隔
     * @return
     * @throws ParamException
     */
    public boolean campus2UserAdd(String campusId, String userId) throws ParamException {
        boolean re_value = false;
        if (campusId == null || userId == null || "".equals(campusId)|| "".equals(userId)) {
            throw new ParamException();
        }
        re_value = campus2UserDao.add(campusId, userId.split(","));
        return re_value;
    }

    /**
     * 删除校园组织机构下的用户
     * 1.如果id不为空，根据id删除
     * 2.否则根据机构ID和用户ID 删除
     *
     * @param id       校园组织结构和用户关系表id，  多个用,分隔
     * @param campusId 校园组织id
     * @param userId   用户id,多个用,分隔
     * @return boolean 是否删除成功
     * @throws ParamException
     */
    public boolean campus2UserDelete(String id, String campusId, String userId) throws ParamException {
        boolean re_value = false;
        if (id != null) {
            re_value = campus2UserDao.delete(id.split(","));
        } else {
            if (campusId == null || userId == null) {
                throw new ParamException();
            }
            re_value = campus2UserDao.delete(campusId, userId.split(","));
        }
        return re_value;
    }

    /**
     * 查找校园组织下用户信息
     *
     * @param campusId   组织机构 id
     * @param userType   默认-1 查询所有人，其它值参见用户服务文档
     * @param searchWord 用户真实姓名
     * @return List 机构信息列表
     * @throws ParamException
     */
    public List campus2UserSearch(String campusId, String userType, String searchWord) throws ParamException {
        List re_value = new ArrayList();
        if (campusId == null) {
            throw new ParamException();
        }
        if (userType == null) {
            userType = "";
        }
        re_value = campus2UserNewDao.findUserByCampusId(campusId, String.valueOf(userType), searchWord);
        return re_value;
    }

    /**
     * 查找校园组织下学生信息
     *
     * @param campusIds   多个组织机构 id ，用逗号分隔
     * @return List 机构信息列表
     * @throws ParamException
     */
    public List<Map<String, String>> campusIds2StuSearch(String campusIds) throws ParamException {
        List<Map<String, String>> re_value = new ArrayList<>();
        if (campusIds == null) {
            throw new ParamException();
        }
        re_value = campusNewDao.findStudentUser(campusIds);
        return re_value;
    }

    /**
     * 查找年级下教师信息
     *
     * @param campusIds   多个组织机构 id ，用逗号分隔
     * @return List 机构信息列表
     * @throws ParamException
     */
    public  List<Map<String, String>> campusIds2TeaSearch(String campusIds) throws ParamException {
        List<Map<String, String>> re_value = new ArrayList<>();
        if (campusIds == null) {
            throw new ParamException();
        }
        re_value = campusNewDao.findTeacherUser(campusIds);
        return re_value;
    }




    /**
     * 分页查找校园组织下用户信息
     *
     * @param page      当前页数，第一个页为1
     * @param pageSize  每页显示记录数
     * @param campusId  用户真实姓名
     * @param userType  不是必须）默认-1 查询所有人，其它值参见用户服务文档
     * @param loginName 结构id
     * @return
     * @throws ParamException
     */
    public PageData campus2UserSearchByPage(Integer page, Integer pageSize, String campusId, Integer userType, String loginName) throws ParamException {
        PageData pageData = new PageData();
        if (page == null || pageSize == null || campusId == null) {
            throw new ParamException();
        }
        if (userType == null) {
            userType = -1;
        }
        pageData = campus2UserNewDao.findUser(page, pageSize, campusId, userType, loginName);
        return pageData;
    }

    /**
     * 获取校园组织机构下的用户的数量
     *
     * @param campusId 结构id
     * @param userType 用户类型
     * @return
     * @throws ParamException
     */
    public long campus2UserSearchCount(String campusId, Integer userType) throws ParamException {
        long re_value = 0;
        if (campusId == null) {
            throw new ParamException();
        }
        re_value = campus2UserNewDao.findUserCount(campusId, userType);
        return re_value;
    }

    /**
     * 获取校园组织机构下的用户的数量
     *
     * @param campusId 结构id,多个用“，”分割
     * @return
     * @throws ParamException
     */
    public List<Map<String, String>> campusCountUserById(String campusId) throws ParamException {
        if (campusId == null || "".equals(campusId)) {
            throw new ParamException();
        }
        return campus2UserDao.findUserCountByCampusId(campusId);
    }


    /**
     * 获取校园组织机构下除传入的用户编号之外的用户
     *
     * @param campusId 机构id
     * @param userNums 用户编号，多个用逗号分割
     * @return
     * @throws ParamException
     */
    public List<Map<String, String>> campusNotInUsersSearch(String campusId, String userNums) throws ParamException {
        if (campusId == null || "".equals(campusId)) {
            throw new ParamException();
        }
        return Campus2UserNewDao.INSTANCE.findOtherUserByCampus(campusId, userNums);
    }

    /**
     * 查找组织机构
     *
     * @param userId   用户id
     * @param campusId 组织机构id
     * @return
     */
    public Object campusSearch(String userId, String campusId) {

        if (userId != null && !"".equals(userId)) {
            return campus2UserNewDao.findCampusByUserId(userId);
        }  if (campusId != null) {
            return campus2UserNewDao.findUserByCampusId(campusId, null, null);
        }
       else {
            return campusNewDao.findClassCampus();
      }
    }

    /**
     * 根据组织机构类型查询
     *
     * @param campusType 机构类型
     * @return 组织机构属性结构，每个组织机构包括它下面的用户
     */
    public Object campusSearchByType(String campusType) throws Exception {
        if (campusType == null || "".equals(campusType)) {
            throw new ParamException();
        }
        Directory d = campusDao.findByCampusType(campusType);
        return d;
    }

    /**
     * 用excel文件导入组织机构
     *
     * @param fileName 上传到临时存储目录后，返回的文件名
     * @return String 导入操作结果
     */
    public String campusImport(String fileName) throws ParamException {
        String re_value = "";
        if (fileName != null) {
            //下面解析excel并写入数据库
            re_value = ExcelUtil.importExcel(fileName);
        } else {
            throw new ParamException();
        }
        return re_value;
    }

    public Map<String, String> campusSearchByUser(String userId, String campusType) throws ParamException {
        Map re_value;
        if (userId == null) {
            throw new ParamException();
        } else {
            if (campusType == null) {
                campusType = "";
            }
            re_value = campus2UserNewDao.finds(userId, campusType);
            return re_value;
        }
    }

    /**
     * 教师树(学校->教研组>教师)
     *
     * @return
     * @throws ParamException
     */
    public Object campus2teacher() throws Exception {
        Directory directory = CampusNewDao.INSTANCE.findTeacherGroup();
        return directory;
    }


    /**
     * 班级树(学校->年级>班级)
     *
     * @return
     * @throws ParamException
     */
    public Object campus2Classes() throws Exception {
        Directory directory = CampusNewDao.INSTANCE.findClassCampus();
        return directory;
    }

    /**
     * 教室树(学校->楼栋>教室)
     *
     * @return
     * @throws ParamException
     */
    public Object campus2room() throws Exception {
        Directory directory = CampusNewDao.INSTANCE.findRoomTree();
        return directory;
    }

    /**
     * 根据教室名称或编号获取教室信息
     *
     * @param roomId(两者可都传，也可单传)
     * @param roomName
     * @return
     * @throws ParamException
     */
    public Object campus2roomSearch(String roomId, String roomName) throws Exception {
        if (null == roomId && null == roomName) {
            throw new ParamException();
        }
        List<Map<String, String>> result = CampusNewDao.INSTANCE.campus2roomSearch(roomId, roomName);
        return result;
    }

    /**
     * 根据班级名称获取班级信息
     *
     * @param classesName
     * @return
     * @throws ParamException
     */
    public Object campus2classSearch(String classesName) throws Exception {
        if (null == classesName) {
            throw new ParamException();
        }
        List<Map<String, String>> result = CampusNewDao.INSTANCE.campus2classSearch(classesName);
        return result;
    }


    /**
     * 根据机构id查找机构下所有的子机构
     *
     * @param campusId (必传项)
     * @return
     * @throws ParamException
     */
    public Object campusChildSearch(String campusId) throws Exception {
        if (campusId == null || "".equals(campusId)) {
            throw new ParamException();
        }
        Directory directory = CampusNewDao.INSTANCE.campusChildSearch(campusId);
        return directory;
    }

    /**
     * 获取登录用户相关的班级圈列表
     *
     * @param userId 用户id
     * @return
     * @throws ParamException
     */
    public  Object campusListByUserId(String userId) throws ParamException {
        if (userId == null || "".equals(userId)) {
            throw new ParamException();
        }
        Map<String,String> userInfoMap= campusNewDao.findUserType(userId);
        String userType = userInfoMap.get("user_type") == null ? "": userInfoMap.get("user_type");
        if(userType.equals(USERTYPE_PARENT)){ //家长身份
            return campusNewDao.findClassesByParentId(userId);
        }
        return campusNewDao.findClassesByStuId(userId,userType);
    }

}
