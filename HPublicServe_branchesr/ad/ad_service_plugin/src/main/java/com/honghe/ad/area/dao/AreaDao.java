package com.honghe.ad.area.dao;

import com.honghe.ad.Directory;
import com.honghe.ad.util.JdbcTemplateUtil;
import com.honghe.cache.Cache;
import com.honghe.cache.CacheFactory;
import net.sf.json.JSONSerializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanghongbin on 2016/7/27.
 */
public final class AreaDao {

    private AreaDao() {

    }

    public final static AreaDao INSTANCE = new AreaDao();

    public final long add(Map<String, Object> data) {
        String name = data.get("name").toString();
        String pId = data.get("pId").toString();
        String typeId = data.get("typeId").toString();
        String campus = data.get("campus").toString();
        String number = data.get("number").toString();
        String remark = data.get("remark").toString();
        String isSelect = data.get("isSelect").toString();

        String sql = "insert into ad_area (`name`,p_id,type_id,campus,number,isselect,remark)values" +
                "('" + name + "'," + pId + ",'" + typeId + "','" + campus + "','" + number + "','" + isSelect + "','" + remark + "')";
        return JdbcTemplateUtil.getJdbcTemplate().add(sql, "id");
    }

    /**
     * 检测添加地点时同级目录不可重复的判断
     *
     * @param data
     * @return false 有重复  true 无重复
     */
    public final boolean check(Map<String, Object> data) {
        String name = data.get("name").toString();
        String pId = data.get("pId").toString();
        String sql = "select * from ad_area where p_id = '" + pId.trim() + "' and name = '" + name.trim() + "'";
        if (data.containsKey("id")) {
            String id = data.get("id").toString();
            sql += " and id <> '" + id.trim() + "'";
        }
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        return result.size() > 0 ? false : true;
    }

    public final Map<String, String> findByDeviceId(String deviceId) {
        String sql = "select a.id,a.name,a.p_id as pId,at.name as areaType,a.campus,a.number,a.isselect as isSelect,a.remark from ad_area as a,(select * from ad_area2device where device_id=" + deviceId + ") as b,ad_area_type as at where a.id=b.area_id and a.type_id=at.id";
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        if (result.isEmpty()) {
            return new HashMap<>();
        }
        return result.get(0);
    }

    public final List<Map<String, String>> findByAreaId(String areaId) {
        String sql = "select a.id as area_id,a.name as areaname,type_id,t.name as typename,z.name as campusname," +
                "a.number,campus,isselect,remark,a.p_id from ad_area as a left join ad_area_type as t on " +
                "a.type_id = t.id left join ad_school_zone as z on a.campus=z.id where a.id ";
        StringBuffer sb = new StringBuffer();
        if (areaId.contains(",")) {
            String[] temp = areaId.split(",");
            for (String s : temp) {
                sb.append("'" + s + "',");
            }
            String ids = sb.toString();
            ids = ids.substring(0, ids.length() - 1);
            sql = sql + " in(" + ids + ")";
        } else {
            sql = sql + "='" + areaId + "'";
        }
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        if (result.isEmpty()) {
            return null;
        }
        return result;
    }

    public final boolean delete(String... id) {
        StringBuilder sb = new StringBuilder();
        for (String _id : id) {
            sb.append(_id + ",");
        }
        String where = sb.substring(0, sb.length() - 1);
        String area2deviceDeleteSql = "delete from ad_area2device where area_id in (" + where + ")";
        String areaDeleteDql = "delete from ad_area where id in (" + where + ")";
        return JdbcTemplateUtil.getJdbcTemplate().execute(area2deviceDeleteSql, areaDeleteDql);
    }

    public final boolean update(Map<String, Object> data) {
        StringBuffer sb = new StringBuffer("update ad_area set id=id ");
        if (data.containsKey("name")) {
            sb.append(", name ='" + data.get("name").toString().trim() + "'");
        }
        if (data.containsKey("areaType")) {
            sb.append(", type_id ='" + data.get("areaType").toString().trim() + "'");
        }
        if (data.containsKey("campus")) {
            sb.append(", campus ='" + data.get("campus").toString().trim() + "'");
        }
        if (data.containsKey("number")) {
            sb.append(", number ='" + data.get("number").toString() + "'");
        }

        if (data.containsKey("remark")) {
            sb.append(", remark ='" + data.get("remark").toString().trim() + "'");
        }
        String remark = data.get("remark").toString();
        if (data.containsKey("isSelect")) {
            sb.append(", isSelect ='" + data.get("isSelect").toString() + "'");
        }
        sb.append(" where id=" + data.get("id").toString());
        return JdbcTemplateUtil.getJdbcTemplate().update(sb.toString());
    }

    public final Directory find() {
        String sql = "select a.id,a.name,a.p_id ,at.name as areaType,a.campus,a.number,a.isselect as isSelect,a.remark from ad_area a, ad_area_type at where a.type_id=at.id";
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        return find(result);
    }

    public final List findType(String areaType) {
        String where = "";
        if (areaType != null & areaType != "") {
            where = "" + areaType + "";
        }

        String sql = "select a.id,a.name,a.p_id ,a.campus,a.number,a.isselect as isSelect,a.remark from ad_area a,ad_area_type t WHERE a.type_id = t.id AND t.p_id = '" + where + "'";
        List result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        return result;
    }

    public final Directory find(Object dtypeName) {
        String where = "";
        if (dtypeName != null && !"".equals(dtypeName)) {
            String[] dtypeNameArray = dtypeName.toString().split(",");
            StringBuilder sb = new StringBuilder();
            sb.append("and d.dtype_name in(");
            for (String _dtypeName : dtypeNameArray) {
                sb.append("'" + _dtypeName + "'").append(",");
            }
            where = sb.toString().substring(0, sb.toString().length() - 1) + ")";
        }
        String sql = "select c.*,c2d.id as area2device_id,c2d.host_id,c2d.host_name,c2d.dtype_name,c2d.host_ipaddr,c2d.host_serialno,c2d.dspec_id,c2d.host_username,c2d.host_password,c2d.host_mac,c2d.host_hhtcmac,c2d.host_factory,host_port,host_channel from ad_area as c left join " +
                "(select a.*,group_concat(b.host_id) as host_id,group_concat(b.host_name) as host_name,group_concat(b.dtype_name) as dtype_name,  group_concat(b.host_ipaddr) as host_ipaddr,group_concat(b.host_serialno) as host_serialno, group_concat(b.dspec_id) as dspec_id, group_concat(b.host_username) as host_username, group_concat(b.host_password) as host_password, group_concat(b.host_mac) as host_mac,  group_concat(b.host_factory) as host_factory,  group_concat(b.host_hhtcmac) as host_hhtcmac,group_concat(b.host_port) as host_port,group_concat(b.host_channel) as host_channel from ad_area2device as a " +
                " left join (select h.*,g.dtype_name from device_host h, (select ds.dspec_id,dtype_name  from  device_specification ds,device_type d where ds.dtype_id = d.dtype_id " + where + " ) g where h.dspec_id = g.dspec_id) as b on a.device_id=b.host_id group by area_id) as c2d on c.id=c2d.area_id order by c.id";
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        return find(result);
    }


    private final Directory find(List<Map<String, String>> result) {
        Directory directory = null;
        for (Map<String, String> _result : result) {
            String p_id = _result.get("p_id");
            if (Integer.parseInt(p_id) == 0) {
                directory = new Directory(_result.get("id"), _result.get("name"));
                String area2device_id = _result.get("area2device_id");
                if (area2device_id != null) {
                    addDirectoryData(directory.getData(), _result);
                }
                break;
            }
        }
        if (directory == null) {
            return new Directory("0", "");
        }
        recursiveOrganization(directory.getDirectories(), result, Integer.parseInt(directory.getId()));
        return directory;
    }

    private void recursiveOrganization(List<Directory> directories, List<Map<String, String>> result, int id) {
        for (Map<String, String> _result : result) {
            String p_id = _result.get("p_id");
            if (Integer.parseInt(p_id) == id) {
                String currentId = _result.get("id");
                Directory directory = new Directory(currentId, _result.get("name"));
                String area2device_id = _result.get("area2device_id");
                if (area2device_id != null) {
                    addDirectoryData(directory.getData(), _result);
                }
                directories.add(directory);
                recursiveOrganization(directory.getDirectories(), result, Integer.parseInt(currentId));
            }
        }
    }

    void addDataByCache(Map<String, String> data, String ip) {
        Cache cache = CacheFactory.newIntance();
        Object obj = cache.get("device.info");
        if (obj != null) {
            Object deviceObj = ((Map) obj).get(ip);
            if (deviceObj == null) {
                data.put("deviceStatus", "Offline");
                data.put("mcuAddress", "");
            } else {
                String[] arr = deviceObj.toString().split(",");
//                data.put("deviceStatus", deviceInfo.get("deviceStatus").toString());
//                if (deviceInfo.containsKey("mcuAddress")) {
//                    data.put("mcuAddress", deviceInfo.get("mcuAddress").toString());
//                } else {
//                    data.put("mcuAddress", "");
//                }
            }
        } else {
            data.put("deviceStatus", "Offline");
            data.put("mcuAddress", "");
        }
    }

    void addDirectoryData(List<Map<String, String>> dataList, Map<String, String> _result) {
        String hostId = _result.get("host_id");
        String[] hostIdArray = hostId.split(",");
        for (int i = 0; i < hostIdArray.length; i++) {
            if ("".equals(hostIdArray[i])) {
                continue;
            }
            Map<String, String> data = new HashMap<>();
            data.put("hostId", hostIdArray[i]);
            String[] hostName = _result.get("host_name").split(",");
            if (hostName.length > i) {
                data.put("hostName", hostName[i]);
            } else {
                data.put("hostName", "");
            }
            String[] ip = _result.get("host_ipaddr").split(",");
            if (ip.length > i) {
                data.put("hostIp", ip[i]);
                this.addDataByCache(data, ip[i]);
            } else {
                data.put("deviceStatus", "Offline");
                data.put("mcuAddress", "");
                data.put("hostIp", "");
            }
            String[] hostSearialno = _result.get("host_serialno").split(",");
            if (hostSearialno.length > i) {
                data.put("hostSerialno", hostSearialno[i]);
            } else {
                data.put("hostSerialno", "");
            }
            String[] despecId = _result.get("dspec_id").split(",");
            if (despecId.length > i) {
                data.put("dspecId", despecId[i]);
            } else {
                data.put("dspecId", "");
            }
            String[] hostUsername = _result.get("host_username").split(",");
            if (hostUsername.length > i) {
                data.put("hostUsername", hostUsername[i]);
            } else {
                data.put("hostUsername", "");
            }
            String[] hostPassword = _result.get("host_password").split(",");
            if (hostPassword.length > i) {
                data.put("hostPassword", hostPassword[i]);
            } else {
                data.put("hostPassword", "");
            }
            String[] hostMac = _result.get("host_mac").split(",");
            if (hostMac.length > i) {
                data.put("hostMac", hostMac[i]);
            } else {
                data.put("hostMac", "");
            }
            String[] hostHhtcmac = _result.get("host_hhtcmac").split(",");
            if (hostHhtcmac.length > i) {
                data.put("hostHhtcmac", hostHhtcmac[i]);
            } else {
                data.put("hostHhtcmac", "");
            }
            String[] hostFactory = _result.get("host_factory").split(",");
            if (hostFactory.length > i) {
                data.put("hostFactory", hostFactory[i]);
            } else {
                data.put("hostFactory", "");
            }
            String[] hostPort = _result.get("host_port").split(",");
            if (hostPort.length > i) {
                data.put("hostPort", hostPort[i]);
            } else {
                data.put("hostPort", "");
            }
            String[] hostChannel = _result.get("host_channel").split(",");
            if (hostChannel.length > i) {
                data.put("hostChannel", hostChannel[i]);
            } else {
                data.put("hostChannel", "");
            }
            if (_result.containsKey("dtype_name")) {
                String[] dtypeName = _result.get("dtype_name").split(",");
                if (dtypeName.length > i) {
                    data.put("deviceType", dtypeName[i]);
                } else {
                    data.put("deviceType", "");
                }
            }
            dataList.add(data);
        }

    }
}