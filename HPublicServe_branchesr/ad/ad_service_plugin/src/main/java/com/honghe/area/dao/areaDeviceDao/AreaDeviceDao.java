package com.honghe.area.dao.areaDeviceDao;

import com.honghe.ad.util.JdbcTemplateUtil;
import com.honghe.area.dao.BasicDao;
import com.honghe.area.reflect.ParameterValue;

import java.util.List;
import java.util.Map;

/**
 *
 * @author cnLock
 * @date 2017/3/8
 */
public class AreaDeviceDao extends BasicDao {
    /**
     * 根据地点id获取设备id
     * @param areaId 地点id
     * @return
     */
    public List<Map<String,String>> getByArea(@ParameterValue("area_id") String areaId){
        String sql = "SELECT * FROM ad_area2device WHERE area_id = "+areaId;
        return JdbcTemplateUtil.getJdbcTemplate().findList(sql);
    }

    /**
     * 根据设备id获取地点id
     * @param deviceId 设备id
     * @return
     */
    public List<Map<String,String>> getByDevice(@ParameterValue("device_id") String deviceId){
        String sql = "SELECT * FROM ad_area2device WHERE device_id = "+deviceId;
        return JdbcTemplateUtil.getJdbcTemplate().findList(sql);
    }

    /**
     * 添加地点_设备关联
     * @param areaId  地点类型名称
     * @param deviceId  地点类型的排序编码
     * @return
     */
    public long add(@ParameterValue("area_id")String areaId, @ParameterValue("device_id")String deviceId){
        StringBuffer sql = new StringBuffer("INSERT INTO ad_area2device(area_id,device_id) VALUES(");
        sql.append("'"+areaId+"',");
        sql.append("'"+deviceId+"')");
        return JdbcTemplateUtil.getJdbcTemplate().add(sql.toString(),"id");
    }

    /**
     * 删除地点设备关联
     * @param id  id
     * @return
     */
    public boolean delete(@ParameterValue("id")String id){
        String sql = "DELETE FROM ad_area2device WHERE id="+id;
        return JdbcTemplateUtil.getJdbcTemplate().delete(sql);
    }

    /**
     * 修改地点设备关联
     * @param areaId  地点类型id
     * @param deviceId  设备id
     * @param id  关联表id
     * @return
     */
    public boolean update(@ParameterValue("area_id")String areaId, @ParameterValue("device_id")String deviceId, @ParameterValue("id")String id){
        String sql = "UPDATE ad_area2device SET device_id = '"+deviceId+"', area_id = '"+areaId+"' WHERE id = "+id;
        return JdbcTemplateUtil.getJdbcTemplate().update(sql);
    }

    /**
     * 根据设备ip获取地点名称
     * @param hostIp
     * @return
     */
    public String getAreaNameByHostIp(@ParameterValue("hostIp")String hostIp){
        String name="";
        String sql = "select distinct area.name as name from ad_area area left join ad_area2device area2device on area.id=area2device.area_id " +
                "left join device_host device on device.host_id=area2device.device_id where device.host_ipaddr='"+hostIp+"'";
        Map<String,String> areaName=JdbcTemplateUtil.getJdbcTemplate().find(sql);
        if(areaName!=null && !areaName.isEmpty()){
            name=areaName.get("name").toString();
        }
        return name;
    }

}
