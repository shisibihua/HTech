package com.honghe.user;

import com.cpjit.swagger4j.annotation.API;
import com.cpjit.swagger4j.annotation.APIs;
import com.cpjit.swagger4j.annotation.DataType;
import com.cpjit.swagger4j.annotation.Param;
import com.honghe.BaseReflectCommand;
import com.honghe.DaoFactory;
import com.honghe.dao.PageData;
import com.honghe.exception.ParamException;
import com.honghe.user.dao.impl.htechUserDao;
import com.honghe.user.util.ParamUtil;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by mz on 2018/2/9.
 */
@APIs(value = "user")
public class HTechUserCommand extends BaseReflectCommand {

    @Override
    public Logger getLogger() {
        return Logger.getLogger("user");
    }

    @Override
    public Class getCommandClass() {
        return this.getClass();
    }

    Logger logger = Logger.getLogger("user");

    private com.honghe.user.dao.impl.htechUserDao htechUserDao = DaoFactory.getInstance().getUserDaoInstance(htechUserDao.class);

    /**
     * 根据用户id获取用户角色 管理员/领导/教师
     *
     * @param userId 用户id
     * @return 角色信息
     */
    @API(value = "htechUserGetRoleByUserId",
            method = "get",
            summary = "根据用户id获取用户角色 管理员/领导/教师",
            description = "根据用户id获取用户角色 管理员/领导/教师",
            parameters = {
                    @Param(in = "query", name = "userId", description = "用户id", dataType = DataType.STRING)
            })
    public List<Map<String, String>> htechUserGetRoleByUserId(String userId) {
        try {
            ParamUtil.checkParam(userId);
            return htechUserDao.htechUserGetRoleByUserId(userId);
        } catch (ParamException e) {
            logger.error(e);
        }

        return Collections.EMPTY_LIST;
    }

    /**
     * 根据用户id查询组织机构以及下级地点
     *
     * @param userId 用户id
     * @return 地点信息
     */
    @API(value = "htechUserGetAreaByUserId",
            method = "get",
            summary = "根据用户id查询组织机构以及下级地点",
            description = "根据用户id查询组织机构以及下级地点",
            parameters = {
                    @Param(in = "query", name = "userId", description = "用户id", dataType = DataType.STRING)
            })
    public Map<String, Object> htechUserGetAreaByUserId(String userId) {
        try {
            ParamUtil.checkParam(userId);
            return htechUserDao.htechUserGetAreaByUserId(userId);
        } catch (ParamException e) {
            logger.error(e);
        }

        return Collections.EMPTY_MAP;
    }

    /**
     * 根据地点id获取上级地点省-市-区-学校
     *
     * @param type   当前地点的类型 市-city，区县-county，学校-school
     * @param areaId 地点的id
     * @return 地点信息 省-市-区-学校
     */
    @API(value = "htechUserGetAreaParentById",
            method = "get",
            summary = "根据地点id获取上级地点省-市-区-学校",
            description = "根据地点id获取上级地点省-市-区-学校",
            parameters = {
                    @Param(in = "query", name = "type", description = "当前地点的类型 市-city，区县-county，学校-school", dataType = DataType.STRING),
                    @Param(in = "query", name = "areaId", description = "地点的id", dataType = DataType.STRING)
            })
    public Map<String, String> htechUserGetAreaParentById(String type, String areaId) throws ParamException {
        if (areaId != null && type != null) {
            return htechUserDao.htechUserGetAreaParentById(type, areaId);
        } else {
            throw new ParamException();
        }
    }

    /**
     * 根据省id获取市列表
     *
     * @param provinceId 省id
     * @return 市列表
     */
    @API(value = "htechUserGetCityByProvince",
            method = "get",
            summary = "根据省id获取市列表",
            description = "根据省id获取市列表",
            parameters = {
                    @Param(in = "query", name = "provinceId", description = "省id", dataType = DataType.STRING)
            })
    public List<Map<String, String>> htechUserGetCityByProvince(String provinceId) {
        try {
            ParamUtil.checkParam(provinceId);
            return htechUserDao.htechUserGetCityByProvince(provinceId);
        } catch (ParamException e) {
            logger.error(e);
        }

        return Collections.EMPTY_LIST;
    }

    /**
     * 根据市id获取区县列表
     *
     * @param cityId 市id
     * @return 区县列表
     */
    @API(value = "htechUserGetCountyByCity",
            method = "get",
            summary = "根据市id获取区县列表",
            description = "根据市id获取区县列表",
            parameters = {
                    @Param(in = "query", name = "cityId", description = "市id", dataType = DataType.STRING)
            })
    public List<Map<String, String>> htechUserGetCountyByCity(String cityId) {
        try {
            ParamUtil.checkParam(cityId);
            return htechUserDao.htechUserGetCountyByCity(cityId);
        } catch (ParamException e) {
            logger.error(e);
        }

        return Collections.EMPTY_LIST;
    }

    /**
     * 根据区县id获取学校列表
     *
     * @param countyId 区县id
     * @return 学校列表
     */
    @API(value = "htechUserGetCampusByCounty",
            method = "get",
            summary = "根据区县id获取学校列表",
            description = "根据区县id获取学校列表",
            parameters = {
                    @Param(in = "query", name = "countyId", description = "区县id", dataType = DataType.STRING)
            })
    public List<Map<String, String>> htechUserGetCampusByCounty(String countyId) {
        try {
            ParamUtil.checkParam(countyId);
            return htechUserDao.htechUserGetCampusByCounty(countyId);
        } catch (ParamException e) {
            logger.error(e);
        }

        return Collections.EMPTY_LIST;
    }

    /**
     * 根据学校id、教室名称，分页获取教室列表
     *
     * @param schoolId    学校id
     * @param name        教室名称 不是必须
     * @param roomType    教室类型 不是必须
     * @param roomIds     教室编号
     * @param pageSize    每页数量
     * @param currentPage 当前页码
     * @return 分页教室列表
     */
    @API(value = "htechUserGetRoomBySchoolId",
            method = "get",
            summary = "根据学校id、教室名称，分页获取教室列表",
            description = "根据学校id、教室名称，分页获取教室列表",
            parameters = {
                    @Param(in = "query", name = "schoolId", description = "学校id", dataType = DataType.STRING),
                    @Param(in = "query", name = "name", description = "教室名称 不是必须", dataType = DataType.STRING),
                    @Param(in = "query", name = "roomType", description = "教室类型 不是必须", dataType = DataType.STRING),
                    @Param(in = "query", name = "roomIds", description = "教室编号", dataType = DataType.STRING),
                    @Param(in = "query", name = "pageSize", description = "每页数量", dataType = DataType.INTEGER),
                    @Param(in = "query", name = "currentPage", description = "当前页码", dataType = DataType.INTEGER)
            })
    public PageData htechUserGetRoomBySchoolId(String schoolId, String name, String roomType, String roomIds, Integer pageSize, Integer currentPage) throws Exception {
        if (schoolId != null && !"".equals(schoolId) && pageSize > 0 && currentPage > 0) {
            return htechUserDao.htechUserGetRoomBySchoolId(schoolId, name, roomType, roomIds, pageSize, currentPage);
        } else {
            throw new ParamException();
        }
    }

    /**
     * 根据学校id、教师名称，分页获取教师列表
     *
     * @param schoolId    学校id
     * @param name        教师名称 不是必须
     * @param teacherIds  排除教师ids
     * @param pageSize    每页数量
     * @param currentPage 当前页
     * @return 教师列表
     */
    @API(value = "htechUserGetTeacherBySchoolId",
            method = "get",
            summary = "根据学校id、教师名称，分页获取教师列表",
            description = "根据学校id、教师名称，分页获取教师列表",
            parameters = {
                    @Param(in = "query", name = "schoolId", description = "学校id", dataType = DataType.STRING),
                    @Param(in = "query", name = "name", description = "教室名称 不是必须", dataType = DataType.STRING),
                    @Param(in = "query", name = "teacherIds", description = "排除教师ids", dataType = DataType.STRING),
                    @Param(in = "query", name = "pageSize", description = "每页数量", dataType = DataType.INTEGER),
                    @Param(in = "query", name = "currentPage", description = "当前页码", dataType = DataType.INTEGER)
            })
    public PageData htechUserGetTeacherBySchoolId(String schoolId, String name, String teacherIds, Integer currentPage, Integer pageSize) throws Exception {
        if (schoolId != null && !"".equals(schoolId) && pageSize > 0 && currentPage > 0) {
            return htechUserDao.htechUserGetTeacherBySchoolId(schoolId, name, teacherIds, currentPage, pageSize);
        } else {
            throw new ParamException();
        }
    }

    /**
     * 根据省、市、区县、学校分页查询教室信息
     *
     * @param provinceId  省id
     * @param cityId      城市id 不是必须
     * @param countyId    区县id 不是必须
     * @param schoolId    学校id 不是必须
     * @param currentPage 当前页
     * @param pageSize    每页数量
     * @return 分页教室列表
     */
    @API(value = "htechUserGetRoomByPage",
            method = "get",
            summary = "根据省、市、区县、学校分页查询教室信息",
            description = "根据省、市、区县、学校分页查询教室信息",
            parameters = {
                    @Param(in = "query", name = "provinceId", description = "省id", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "cityId", description = "城市id 不是必须", dataType = DataType.STRING),
                    @Param(in = "query", name = "countyId", description = "区县id 不是必须", dataType = DataType.STRING),
                    @Param(in = "query", name = "schoolId", description = "学校id 不是必须", dataType = DataType.STRING),
                    @Param(in = "query", name = "currentPage", description = "当前页", dataType = DataType.INTEGER, required = true),
                    @Param(in = "query", name = "pageSize", description = "每页数量", dataType = DataType.INTEGER, required = true)
            })
    public PageData htechUserGetRoomByPage(String provinceId, String cityId, String countyId, String schoolId, Integer currentPage, Integer pageSize) throws Exception {
        if (currentPage > 0 && pageSize > 0) {
            return htechUserDao.htechUserGetRoomByPage(provinceId, cityId, countyId, schoolId, currentPage, pageSize);
        } else {
            throw new ParamException();
        }
    }

    /**
     * 根据省、市、区县、学校分页查询教室信息
     *
     * @param provinceId 省id
     * @param cityId     城市id 不是必须
     * @param countyId   区县id 不是必须
     * @param schoolId   学校id 不是必须
     * @return 教室id串
     */
    @API(value = "htechUserGetRoomByArea",
            method = "get",
            summary = "根据省、市、区县、学校分页查询教室信息",
            description = "根据省、市、区县、学校分页查询教室信息",
            parameters = {
                    @Param(in = "query", name = "provinceId", description = "省id", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "cityId", description = "城市id 不是必须", dataType = DataType.STRING),
                    @Param(in = "query", name = "countyId", description = "区县id 不是必须", dataType = DataType.STRING),
                    @Param(in = "query", name = "schoolId", description = "学校id 不是必须", dataType = DataType.STRING)
            })
    public String htechUserGetRoomByArea(String provinceId, String cityId, String countyId, String schoolId) {
        return htechUserDao.htechUserGetRoomByArea(provinceId, cityId, countyId, schoolId);
    }

    /**
     * 分页获取所有教室信息
     *
     * @param currentPage 第几页
     * @param pageSize    每页大小
     * @return 教室信息
     * @throws Exception 参数异常
     */
    @API(value = "htechUserGetAllRoomByPage",
            method = "get",
            summary = "分页获取所有教室信息",
            description = "分页获取所有教室信息",
            parameters = {
                    @Param(in = "query", name = "currentPage", description = "第几页", dataType = DataType.INTEGER, required = true),
                    @Param(in = "query", name = "pageSize", description = "每页大小", dataType = DataType.INTEGER, required = true)
            })
    public PageData htechUserGetAllRoomByPage(Integer currentPage, Integer pageSize) throws Exception {
        if (currentPage > 0 && pageSize > 0) {
            return htechUserDao.htechUserGetAllRoomByPage(currentPage, pageSize);
        } else {
            throw new ParamException();
        }
    }


    /**
     * 根据省、市、区县、学校分页查询除了ids之外的教室
     *
     * @param provinceId  省id
     * @param cityId      城市id 不是必须
     * @param countyId    区县id 不是必须
     * @param schoolId    学校id 不是必须
     * @param roomIds     需要排除的教室id 多个用逗号隔开
     * @param currentPage 当前页
     * @param pageSize    每页数量
     * @return 分页教室列表
     */
    @API(value = "htechUserGetRoomExceptIds",
            method = "",
            summary = "根据省、市、区县、学校分页查询除了ids之外的教室",
            description = "根据省、市、区县、学校分页查询除了ids之外的教室",
            parameters = {
                    @Param(in = "query", name = "provinceId", description = "省id", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "cityId", description = "城市id 不是必须", dataType = DataType.STRING),
                    @Param(in = "query", name = "countyId", description = "区县id 不是必须", dataType = DataType.STRING),
                    @Param(in = "query", name = "schoolId", description = "学校id 不是必须", dataType = DataType.STRING),
                    @Param(in = "query", name = "roomIds", description = "需要排除的教室id 多个用逗号隔开", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "currentPage", description = "当前页", dataType = DataType.INTEGER, required = true),
                    @Param(in = "query", name = "pageSize", description = "每页数量", dataType = DataType.INTEGER, required = true)
            })
    public PageData htechUserGetRoomExceptIds(String provinceId, String cityId, String countyId, String schoolId, String roomIds, Integer currentPage, Integer pageSize) throws Exception {
        if (currentPage > 0 && pageSize > 0) {
            if (roomIds == null && "".equals(roomIds)) {
                //没有需要排除的教室
                return htechUserGetRoomByPage(provinceId, cityId, countyId, schoolId, currentPage, pageSize);
            } else {
                return htechUserDao.htechUserGetRoomExceptIds(provinceId, cityId, countyId, schoolId, roomIds, currentPage, pageSize);
            }
        } else {
            throw new ParamException();
        }
    }

    /**
     * 根据省id获取所有区县的id
     *
     * @param provinceId 省id
     * @return 区县id的集合
     */
    @API(value = "htechUserGetCountyByProvince",
            method = "get",
            summary = "根据省id获取所有区县的id",
            description = "根据省id获取所有区县的id",
            parameters = {
                    @Param(in = "query", name = "provinceId", description = "省id", dataType = DataType.STRING, required = true)
            })
    public List<Map<String, String>> htechUserGetCountyByProvince(String provinceId) {
        try {
            ParamUtil.checkParam(provinceId);
            return htechUserDao.htechUserGetCountyByProvince(provinceId);
        } catch (ParamException e) {
            logger.error(e);
        }

        return Collections.EMPTY_LIST;
    }

    /**
     * 根据省id获取所有学校的id
     *
     * @param provinceId 省id
     * @return 学校id的集合
     */
    @API(value = "htechUserGetSchoolByProvince",
            method = "get",
            summary = "根据省id获取所有学校的id",
            description = "根据省id获取所有学校的id",
            parameters = {
                    @Param(in = "query", name = "provinceId", description = "省id", dataType = DataType.STRING, required = true)
            })
    public List<Map<String, String>> htechUserGetSchoolByProvince(String provinceId) {
        try {
            ParamUtil.checkParam(provinceId);
            return htechUserDao.htechUserGetSchoolByProvince(provinceId);
        } catch (ParamException e) {
            logger.error(e);
        }

        return Collections.EMPTY_LIST;
    }

    /**
     * 根据学校id获取子地点树（学校以下的地点）
     *
     * @param schoolId 学校id
     * @return 地点树
     */
    @API(value = "htechUserGetAreaTree",
            method = "get",
            summary = "根据学校id获取子地点树（学校以下的地点）",
            description = "根据学校id获取子地点树（学校以下的地点）",
            parameters = {
                    @Param(in = "query", name = "schoolId", description = "get", dataType = DataType.STRING, required = true)
            })
    public Map<String, Object> htechUserGetAreaTree(String schoolId) {
        try {
            ParamUtil.checkParam(schoolId);
            return htechUserDao.htechUserGetAreaTree(schoolId);
        } catch (ParamException e) {
            logger.error(e);
        }

        return Collections.EMPTY_MAP;
    }

    /**
     * 获取地点下的主讲和接收教室数量
     *
     * @param provinceId 省id
     * @param cityId     市id
     * @param countyId   区县id
     * @param schoolId   学校id
     * @return 主讲和接收的数量
     * @throws Exception 参数异常
     */
    @API(value = "htechUserGetRoomCount",
            method = "get",
            summary = "获取地点下的主讲和接收教室数量",
            description = "获取地点下的主讲和接收教室数量",
            parameters = {
                    @Param(in = "query", name = "provinceId", description = "省id", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "cityId", description = "市id", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "countyId", description = "区县id", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "schoolId", description = "学校id", dataType = DataType.STRING, required = true)
            })
    public Map<String, Integer> htechUserGetRoomCount(String provinceId, String cityId, String countyId, String schoolId) throws Exception {
        if (!"".equals(provinceId) || !"".equals(cityId) || !"".equals(countyId) || !"".equals(schoolId)) {
            return htechUserDao.htechUserGetRoomCount(provinceId, cityId, countyId, schoolId);
        } else {
            throw new ParamException();
        }
    }

    /**
     * 根据省的名称获取省信息
     *
     * @param provinceName 省名称
     * @return 省信息
     */
    @API(value = "htechUserGetProvinceByName",
            method = "get",
            summary = "根据省的名称获取省信息",
            description = "根据省的名称获取省信息",
            parameters = {
                    @Param(in = "query", name = "provinceName", description = "省名称", dataType = DataType.STRING, required = true)
            })
    public Map<String, String> htechUserGetProvinceByName(String provinceName) {
        try {
            ParamUtil.checkParam(provinceName);
            return htechUserDao.htechUserGetProvinceByName(provinceName);
        } catch (ParamException e) {
            logger.error(e);
        }
        return Collections.EMPTY_MAP;
    }
}
