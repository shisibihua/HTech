package com.honghe.ad.user.controller;

import com.honghe.ad.area.controller.AreaController;
import com.honghe.ad.area.dao.User2DeviceDao;
import com.honghe.ad.campus.dao.Campus2UserDao;
import com.honghe.ad.campus.dao.Campus2UserNewDao;
import com.honghe.ad.campus.dao.CampusDao;
import com.honghe.ad.excetion.ParamException;
import com.honghe.dao.JdbcTemplateUtil;
import com.honghe.dao.PageData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sky on 2017/2/28.
 */
public class UserNewController {
    private User2DeviceDao user2DeviceDao = User2DeviceDao.INSTANCE;
    private CampusDao campusDao = CampusDao.INSTANCE;
    private Campus2UserDao campus2UserDao = Campus2UserDao.INSTANCE;
    private AreaController areaController = new AreaController();

    /**
     * 校园组织机构查询
     * 如果参数为userId则查询这个用户所在结构以及下级的结构
     * 如果参数为userNum（多个用,分隔）获取用户信息和所在组织结构名称
     * 两参数传一个
     *
     * @param userId  用户id
     * @param userNum 用户编号多个用,分隔
     * @return
     * @throws com.honghe.ad.excetion.ParamException
     */
    public Object userCampusSearch(String userId,String userType, String userNum) throws ParamException {
        Object re_value = new ArrayList<>();

        if (userId != null&&!"".equals(userId)) {
            re_value = Campus2UserNewDao.INSTANCE.find(userId,null);
        } else if(userType!=null&&!"".equals(userType)){
            re_value = Campus2UserNewDao.INSTANCE.findByUserType(userType);
        }else if (userNum != null&&!"".equals(userNum)) {
            re_value = Campus2UserNewDao.INSTANCE.findByUserNum(userNum.split(","));
        } else {
            throw new ParamException();
        }
        return re_value;
    }


    /**
     * 分页获取没有和校园组织机构关联的用户
     *
     * @param page 当前页
     * @param pageSize 每页条目
     * @param loginName
     * @return
     * @throws ParamException
     */
    public PageData userNotInCampusSearch(Integer page, Integer pageSize, String loginName) throws ParamException {
        PageData pageData = new PageData();
        if (page == null || pageSize == null) {
            throw new ParamException();
        }
        if (loginName == null) {
            loginName = "";
        }
        pageData = Campus2UserNewDao.INSTANCE.findUser(page, pageSize, loginName);
        return pageData;
    }

    /**
     * 分页获取没有和校园组织机构关联的教师 学生
     * @param page 当前页
     * @param pageSize 每页条目
     * @param searchName 查询条件，用户名称
     * @param campusType 机构类型
     * @param tsFlag 用户编号标识，ST：学生，TE：教师
     * @return
     * @throws ParamException
     */
    public PageData userTSnoCampusSearch(Integer page, Integer pageSize, String searchName,String campusType,String campusId,String stageId,String tsFlag) throws ParamException {
        PageData pageData = new PageData();
        if (page == null || pageSize == null) {
            throw new ParamException();
        }
        if (searchName == null) {
            searchName = "";
        }
        pageData = Campus2UserDao.INSTANCE.findTS(page, pageSize, searchName,campusType,campusId,stageId,tsFlag);
        return pageData;
    }

    /**
     *
     * @param userId 用户id
     * @return
     * @throws ParamException
     */
    public Object userCampusSearchById(String userId) throws ParamException {
        Object re_value = new ArrayList<>();
        if (userId != null ) {
            re_value = Campus2UserNewDao.INSTANCE.findByUserId(userId);
        } else {
            throw new ParamException();
        }
        return re_value;
    }

    /**
     * 通过人员id查询用户信息
     * @param uIds 考勤表中的人员id，多个用逗号隔开
     * @param flag 人员标识 17：教师，18：学生
     * @return
     */
    public static List<Map<String,String>> userGetInfoById(String uIds,String flag){
        List<Map<String,String>> list = new ArrayList();
        String [] str = null;
        if (uIds!=null&&!"".equals(uIds)){
            list = Campus2UserNewDao.INSTANCE.findInfoById(uIds,flag);
        }
        return list;
    }
}
