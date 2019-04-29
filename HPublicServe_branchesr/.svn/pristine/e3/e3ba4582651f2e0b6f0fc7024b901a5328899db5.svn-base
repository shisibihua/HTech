package com.honghe.ad.user.controller;/**
 * Created by lyx on 2016-11-29.
 */

import com.honghe.ad.area.controller.AreaController;
import com.honghe.ad.area.dao.User2DeviceDao;
import com.honghe.ad.campus.dao.Campus2UserDao;
import com.honghe.ad.campus.dao.CampusDao;
import com.honghe.ad.excetion.ParamException;
import com.honghe.dao.PageData;
import jodd.util.StringUtil;

import java.util.ArrayList;

/**
 * 用户逻辑操作命令类
 *
 * @author lyx
 * @create 2016-11-29 17:34
 **/
public class UserController {

    private User2DeviceDao user2DeviceDao = User2DeviceDao.INSTANCE;
    private CampusDao campusDao = CampusDao.INSTANCE;
    private Campus2UserDao campus2UserDao = Campus2UserDao.INSTANCE;
    private AreaController areaController = new AreaController();

    /**
     * 增加用户和设备的关联
     *
     * @param userId   用户id
     * @param deviceId 设备id 多个用,分隔
     * @return
     * @throws ParamException
     */
    public boolean user2DeviceAdd(String userId, String deviceId) throws ParamException {
        boolean re_value = false;
        if (userId == null || deviceId == null) {
            throw new ParamException();
        }
        //add方法原来只支持单个userId的插入，后需求变更改为多个----hjt
        re_value = user2DeviceDao.addByMulipleUserId(userId, deviceId.split(","));
//        re_value = user2DeviceDao.add(userId, deviceId.split(","));
        return re_value;
    }

    /**
     * 删除用户和设备的关联
     *
     * @param userId 用户id
     * @param id     用户和设备关联id
     * @return
     * @throws ParamException
     */
    public boolean user2DeviceDelete(String userId, String id) throws ParamException {
        boolean re_value = false;
        if (userId == null) {
            if (id == null) {
                throw new ParamException();
            }
            re_value = user2DeviceDao.delete(id.split(","));
        } else {
            re_value = user2DeviceDao.deleteByUserId(userId.split(","));
        }
        return re_value;
    }

    /**
     * 校园组织机构查询
     * 如果参数为userId则查询这个用户所在结构以及下级的结构
     * 如果参数为userNum（多个用,分隔）获取用户信息和所在组织结构名称
     * 两参数传一个
     *
     * @param userId  用户id
     * @param userNum 用户编号多个用,分隔
     * @return
     * @throws ParamException
     */
    public Object userCampusSearch(String userId,String userType, String userNum) throws ParamException {
        Object re_value = new ArrayList<>();
        if (userId != null&&!"".equals(userId)) {
            re_value = CampusDao.INSTANCE.find(userId,null);
        } else if(userType!=null&&!"".equals(userType)){
            re_value = Campus2UserDao.INSTANCE.findByUserType(userType);
        }else if (userNum != null&&!"".equals(userNum)) {
            re_value = Campus2UserDao.INSTANCE.findByUserNum(userNum.split(","));
        } else {
            throw new ParamException();
        }
        return re_value;
    }

    /**
     * 分页获取没有和校园组织机构关联的用户
     *
     * @param page
     * @param pageSize
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
        pageData = Campus2UserDao.INSTANCE.findUser(page, pageSize, loginName);
        return pageData;
    }



    /**
     * 分页获取没有和校园组织机构关联的教师 学生
     * @param page
     * @param pageSize
     * @param searchName
     * @param campusType
     * @param tsFlag
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
     * 根据用户查询地点下所有设备
     * 注：根据用户id和type值为null 和设备类型查询地点下所有设备
     * 根据用户id和type值为其他 和设备类型查询所有设备和设备所在地点名称
     *
     * @param userId     用户id
     * @param searchWord 设备查询词
     * @param type       d 以目录树展示 t 以设备的展示 列表
     * @param dtypeName  设备类型
     * @return
     * @throws ParamException
     */
    public Object user2DeviceSearch(String userId, String searchWord, String type, String dtypeName) throws ParamException {
        Object object = new Object();
        if (userId == null) {
            throw new ParamException();
        }
        if(dtypeName == null){
            dtypeName = "";
        }
        if (searchWord == null) {
            if (type == null) {
                type = "d";
            }
            if ("d".equals(type)) {
                if("1".equals(userId)) {
                    object = areaController.areaSearch(null, "1", dtypeName,"");
                }else{
                    object = user2DeviceDao.find(userId, dtypeName);
                }
            }else{
                try {
                    int userId_=Integer.parseInt(userId);
                    object = user2DeviceDao.find(userId_,dtypeName);
                }catch (Exception e){}
            }
        } else {
            object = user2DeviceDao.find(userId, dtypeName, searchWord);
        }
        return object;
    }

    /**
     *
     * @param userId
     * @return
     * @throws ParamException
     */
    public Object userCampusSearchById(String userId) throws ParamException {
        Object re_value = new ArrayList<>();
        if (userId != null ) {
            re_value = Campus2UserDao.INSTANCE.findByUserId(userId);
        } else {
            throw new ParamException();
        }
        return re_value;
    }

    /**
     * 查找当前用户所用设备的类型
     * @param userId
     * @return
     * @throws ParamException
     */
    public Object userDeviceTypeSearch(String userId) throws ParamException {
        Object re_value = new ArrayList<String>();
        if (userId != null ) {
            re_value = user2DeviceDao.findDeviceTypeByUser(userId);
        } else {
            throw new ParamException();
        }
        return re_value;
    }

    /**
     * 根据用户查询地点下所有设备
     * 注：根据用户id和type值为null 和设备类型查询地点下所有设备
     * 根据用户id和type值为其他 和设备类型查询所有设备和设备所在地点名称
     *
     * @param userId
     * @param searchWord
     * @param type
     * @param dtypeName
     * @return
     * @throws ParamException
     */
    public Object user2DeviceGroup(String userId, String searchWord, String type, String dtypeName) throws ParamException {
        Object object = new Object();

        //如果用户id不为空则通过id查询，否则返回空
        if (!StringUtil.isEmpty(userId)) {
            String deviceTypeName = dtypeName == null ? "" : dtypeName;
            String showType = type == null ? "d" : type;
            String userFlag = "1";
            if (StringUtil.isEmpty(searchWord)) {
                if ("d".equals(showType)) {
                    if (!"1".equals(userId)) {
                        userFlag = "0";
                    }
                    object = user2DeviceDao.findAllAreaDevice(userId, deviceTypeName, userFlag);
                } else {
                    int userId_ = Integer.parseInt(userId);
                    object = user2DeviceDao.find(userId_, deviceTypeName);
                }
            } else {
                object = user2DeviceDao.find(userId, deviceTypeName, searchWord);
            }

        }
        return object;
    }
}
