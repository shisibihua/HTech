package com.honghe.tech.dao;

import com.honghe.spring.MybatisDao;
import com.honghe.tech.entity.AssteachActivity;
import com.honghe.tech.form.ActivityInfoForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author LiZhuoyuan on 2018/1/29.
 */
@MybatisDao
public interface AssteachActivityDao {
    /**
     * 保存接收教室、辅助教师ids
     * @param assteachActivity 表实体
     */
     void saveAssteachActivity(AssteachActivity assteachActivity);

    /**
     * 通过uuid得到与某个教学活动关联的所有接收教室和辅助教师
     * @param uuidList 唯一标识串
     * @return 接收信息集合
     */
     List selectAssteachActivityByUuids(List uuidList);

    /**
     * 通过一个uuid得到和某个教学活动关联的接听教室和辅助教师
     * @param uuid
     * @return
     */
     List selectAssteachActivityByUuid(String uuid);

    /**
     * 通过一个uuid得到和某个教学活动关联的接听教室和辅助教师
     * @param uuid
     * @return
     */
     List selectAssteachActivityByUuid2(String uuid);

    /**
     * 根据uuid删除所有相关的数据
     * @param uuid 唯一标识
     */
     void deleteAssteachActivityByUuid(String uuid);

    /**
     * 根据uuid查询接收教室及辅助教师id串
     * @param uuid
     * @return 教室id串及对应的教师id串
     */
     Map selectAssRoomTeacherIdsByUuid(String uuid);

    /**
     * 根据接收教室地址查询活动名称
     * @param params
     * @return
     */
     List<ActivityInfoForm> selectActivityByAssRoomAddr(Map params);

    /**
     * 根据辅助教师id查询活动名称
     * @param params
     * @return
     */
     List<ActivityInfoForm> selectActivityByAssTeacherId (Map params);

    /**
     * 获取接收课堂数
     * @param params
     * @return
     */
     int getTotalAcceptCourseCase(Map params);
}
