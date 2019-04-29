package com.honghe.tech.dao;

import com.honghe.spring.MybatisDao;
import com.honghe.tech.entity.Area;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 查询省名、城市名、区/县名、学校名
 * @author caoqian
 */
@MybatisDao
public interface AreaDao {
    /**
     * 根据地点标识id查询省名、城市名、区/县名、学校名
     * @param areaId
     * @return
     */
     Area  getAreaMessage(@Param("areaId") String areaId);

    /**
     * 添加教学活动地点 李卓远 2018-02-07
     * @param area 地点表实体
     */
     void saveArea(Area area);

    /**
     * 更新地点
     * @param map
     */
     void updateAreaById(Map map);

    /**
     * 根据地点串查询地点信息
     * @param areaAddr
     * @return
     */
     Map<String,String> getAreaInfoByAreaId(String areaAddr);
}
