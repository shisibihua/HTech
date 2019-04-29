package com.honghe.ad.area.dao;

import com.honghe.ad.util.JdbcTemplateUtil;
import com.honghe.cache.Cache;
import com.honghe.cache.CacheFactory;
import com.honghe.dao.PageData;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanghongbin on 2016/7/28.
 */
public class Area2DeviceDao {
    Logger logger=Logger.getLogger(Area2DeviceDao.class);
    private Area2DeviceDao() {

    }

    public final static Area2DeviceDao INSTANCE = new Area2DeviceDao();


    public long add(Map<String, Object> data) {
        String areaId = data.get("areaId").toString().trim();
        String deviceId = data.get("deviceId").toString().trim();
        String sql = "insert into ad_area2device(area_id,device_id) values(" + areaId + "," + deviceId + ")";
        return JdbcTemplateUtil.getJdbcTemplate().add(sql, "id");
    }


    public PageData find(int page, int pageSize, String areaId, String deviceName) {
        String where = "";
        if (deviceName != null && deviceName.length() != 0) {
            where = " and h.host_name like '%" + deviceName.trim() + "%'";
        }
        //暂时加入设备中文名称 待做国际化时再做修改 中文名称deviceDesc
        String countSql = "SELECT count(*) FROM ad_area2device as a2d,device_host as h where a2d.device_id=h.host_id and a2d.area_id=" + areaId + where;
        String resultSql = "select a1.*,a2.dtype_name as deviceType,a2.dtype_name_cn as deviceDesc from (SELECT a2d.area_id as areaId,h.host_id as hostId,h.host_name as hostName,h.host_ipaddr as hostIp,h.host_serialno as hostSerialNo,h.dspec_id as despecId,h.host_username as hostUsername,h.host_password as hostPassword," +
                "h.host_mac as hostMac,h.host_hhtcmac as hostHhtcmac,h.host_factory as hostFactory  FROM ad_area2device as a2d,device_host as h where a2d.device_id=h.host_id and a2d.area_id =" + areaId
                + where + ") as a1, (select d.*,ds.dspec_id from device_specification ds,device_type d where ds.dtype_id=d.dtype_id) as a2  where a1.despecId=a2.dspec_id  limit ?," + pageSize;
        PageData pageData = JdbcTemplateUtil.getJdbcTemplate().findByPage(page, pageSize, countSql, resultSql);
        List<Map<String, String>> result = pageData.getItems();
        for (Map<String, String> record : result) {
            String ip = record.get("hostIp");
            Cache cache = CacheFactory.newIntance();
            Object obj = cache.get("device.info");
            if(obj != null){
                Object deviceObj = ((Map) obj).get(ip);
                if(deviceObj == null){
                    record.put("deviceStatus", "Offline");
                }else{
                    String[] arr =  deviceObj.toString().split(",");
                }
            }else{
                record.put("deviceStatus", "Offline");
            }
        }
        return pageData;
    }

    public boolean add(String areaId, String... deviceId) {
        List<String> sql = new ArrayList<>();
        for (String _id : deviceId) {
            sql.add("insert into ad_area2device(area_id,device_id) values(" + areaId + "," + _id + ")");
        }
        return JdbcTemplateUtil.getJdbcTemplate().execute(sql.toArray(new String[sql.size()]));
    }

    public boolean delete(String... id) {
        List<String> sql = new ArrayList<>();
        for (String _id : id) {
            sql.add("delete from ad_area2device where id=" + _id.trim());
        }
        return JdbcTemplateUtil.getJdbcTemplate().execute(sql.toArray(new String[sql.size()]));
    }

    public boolean delete(String areaId, String... deviceId) {
        StringBuilder sb = new StringBuilder();
        for (String _deviceId : deviceId) {
            sb.append(_deviceId + ",");
        }
        String where = sb.toString().substring(0, sb.length() - 1);
        Boolean bool2 =JdbcTemplateUtil.getJdbcTemplate().delete("delete from ad_area2device where area_id=" + areaId + " and device_id in(" + where + ")");
        if(bool2) {
//        if(bool1&&bool2) {
            return true;
        }
        else{
            return false;
        }
    }

    public boolean deleteByDeviceId(String... deviceId) {
        StringBuilder sb = new StringBuilder();
        for (String _deviceId : deviceId) {
            sb.append(_deviceId + ",");
        }
        String where = sb.toString().substring(0, sb.length() - 1);
        return JdbcTemplateUtil.getJdbcTemplate().delete("delete from ad_area2device where  device_id in(" + where + ")");
    }

    public boolean findAreaIdByHostId(String hostId){
        String sql="select count(*) as count from ad_area2device where device_id="+hostId;
        Map<String,String> map=JdbcTemplateUtil.getJdbcTemplate().find(sql);
        if(map!=null && !map.isEmpty() && !"0".equals(map.get("count"))){
            return true;
        }else{
            return false;
        }
    }

}
