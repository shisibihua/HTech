package com.honghe.tech.httpservice;

import java.util.List;
import java.util.Map;

/**
 * @author 木夕夕
 * @date 2018/1/29
 */
public interface UserHttpService {


    /**
     * //根据用户id获取用户信息
     *
     * @param id
     * @return
     */
    Map<String, String> getUserById(int id);

    /**
     * //用户登录验证
     *
     * @param loginName
     * @param userPwd
     * @return
     */

    Map<String, String> checkUserLogin(String loginName, String userPwd);

    /**
     * 根据用户id获得用户角色信息
     *
     * @param userId 用户的id
     * @return list 一个用户可有多个角色
     */
    List<Map<String, String>> getUserRole(String userId);

    /**
     * 根据用户id获取地点权限
     *
     * @param userId 用户id
     * @return list          用户地点权限
     */
    Map<String, Object> getAreaByUserId(String userId);

    /**
     * 根据学校id，分页获取教室列表（支持教室名称）
     *
     * @param schoolId    学校id
     * @param name        教室名称 不是必须
     * @param roomType    主讲教室传“1”
     * @param currentPage 当前页数
     * @param pageSize    每页数量
     * @param excRoomIds  需要排除的教室id串
     * @return 教室列表
     */
    Map<String, Object> getRoomBySchoolId(String schoolId, String name, String roomType,
                                          int currentPage, int pageSize, String excRoomIds);

    /**
     * 根据学校id分页获取教师信息（支持教师名称）
     *
     * @param schoolId      学校id
     * @param name          教师名称 不是必须
     * @param currentPage   当前页数
     * @param pageSize      每页大小
     * @param excTeacherIds 需要排除的教师id串
     * @return 教师列表
     */
    Map<String, Object> getTeacherBySchoolId(String schoolId, String name, int currentPage,
                                             int pageSize, String excTeacherIds);

    /**
     * 根据城市、区县、学校id分页获取教室
     *
     * @param provinceId  省id
     * @param cityId      城市id
     * @param countyId    县区id 不是必须
     * @param schoolId    学校id 不是必须
     * @param currentPage 当前页数
     * @param pageSize    每页数量
     * @return 教室信息
     */
    Map<String, Object> getRoomByPage(String provinceId, String cityId, String countyId,
                                      String schoolId, int currentPage, int pageSize);

    /**
     * 根据城市、区县、学校id分页获取除ids之外的教室
     *
     * @param proviceId   省id
     * @param cityId      城市id
     * @param countyId    县区id 不是必须
     * @param schoolId    学校id 不是必须
     * @param roomIds     需要排除的id串 逗号分隔
     * @param currentPage 当前页数
     * @param pageSize    每页数量
     * @return 教室信息
     */
    Map<String, Object> getRoomExceptIds(String proviceId, String cityId, String countyId,
                                         String schoolId, String roomIds, int currentPage, int pageSize);

    /**
     * 根据省id获取市列表
     *
     * @param provinceId 省id
     * @return 市列表
     */
    List<Map<String, String>> getCityByProvince(int provinceId);

    /**
     * 根据城市id获取区县列表
     *
     * @param cityId 城市id
     * @return 区县列表
     */
    List<Map<String, String>> getCountyByCity(int cityId);

    /**
     * 根据区县id获取学校列表
     *
     * @param countyId 区县id
     * @return 学校列表
     */
    List<Map<String, String>> getSchoolByCounty(int countyId);

    /**
     * 根据地点获取教室ids，多个','隔开
     *
     * @param provinceId 省id
     * @param cityId    市id
     * @param countyId  区/县id
     * @param schoolId  学校id
     * @return
     */
    String getRoomIdsByAreaId(String provinceId, String cityId, String countyId, String schoolId);

    /**
     * 根据用户id查询用户权限列表
     *
     * @param userId 用户id
     * @return list
     * @author caoqian
     */
    List<Map<String, String>> getUserPermissionByUserId(String userId);

    /**
     * 根据地点获取不同类型教室数量，多个','隔开
     *
     * @param proviceId 省id
     * @param cityId    市id
     * @param countyId  区/县id
     * @param schoolId  学校id
     * @return
     */
    Map<String, Integer> getRoomsByAreaIdAndType(String proviceId, String cityId, String countyId, String schoolId);

    /**
     * 根据地点id及地点类型获取父级地点名称
     *
     * @param areaType 地点类型
     * @param areaId   地点id
     * @return 父级地点信息
     */
    Map<String, Object> getParentArea(String areaType, String areaId);

    /**
     * 查询所有正在使用的用户信息列表
     * @return 用户信息
     */
    Map<String,Object> getAllUserInfo();

    /**
     * 根据用户id查询用户所在学段
     * @return 用户信息
     */
    Map<String,String> getStagesByUserId(String userId);
}

