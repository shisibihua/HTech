package com.honghe.tech.service;

import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by xinqinggang on 2018/3/1.
 * @author xinqinggang
 */
public interface TermService {
    /**
     * 添加学期或假期信息
     * @param info 学期或假期信息json字符串根式
     * @param operType 操作类型：add:添加；update:修改
     * @param userId 用户ID
     * @return 0:操作成功；-1：课表冲突；-2:未知添加失败；-3:未知操作；-4:该学期名称已存在
     */
    Object addOrUpdateInfo(String info,String operType,int userId);

    /**
     * 查询所有学期或假期 信息
     * @param currentPage 当前页
     * @param pageSize 每页条数
     * @param type 1:学期信息；2：假期信息
     * @return 学期或假期分页信息
     */
    JSONObject findInfoByPage(String currentPage, String pageSize,int type);

    /**
     * 根据学期或假期信息id更改信息状态
     * @param id 学期或假期信息id
     * @param status 状态(0/1)
     * @param userId 用户id
     * @return 是否更改成功
     */
    Object updateInfoStatus(int id,int status,int userId);

    /**
     * 根据信息状态查看学期或假期信息
     * @param type 学期(1)或假期信息(2)
     * @param status 状态(0/1)
     * @return 学期或假期信息
     */
    List<Map<String,String>> selectInfoByStatus(String status,int type);

    /**
     * 根据学期或假期信息id删除信息
     * @param id 学期或假期信息id
     * @param userId 用户id
     * @return 是否删除成功
     */
    Object deleteInfo(int id,int userId);

    /**
     * 根据信息id查看学期信息
     * @param id 信息id
     * @return
     */
    public Map<String,Object> selectInfoById(int id);

    /**
     * 查询所有学年信息
     * @return 学年集合
     */
    List<Map<String ,Object>> getAllSchoolyear();

    /**
     * 根据学年id查询学期信息
     * @param schoolyearId  学年ID
     * @return
     */
    List<Map<String ,Object>> getTermBySchoolyearId(String schoolyearId);

    /**
     * 获取学年时间
     * @param schoolyearId 学年ID
     * @return
     */
    Map<String,String> getSchoolyearTime(String schoolyearId);

    /**
     *根据学年及学期id和参数类型获取时间范围
     * @param schoolyearId 学年id
     * @param termId 学期id
     * @param type done:已完成课时；plan:计划课时
     * @return
     */
    Map<String,String> getTimeRange(String schoolyearId,String termId,String type);
}
