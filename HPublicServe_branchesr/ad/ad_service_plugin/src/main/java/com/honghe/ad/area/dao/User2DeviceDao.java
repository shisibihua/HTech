package com.honghe.ad.area.dao;

import com.honghe.ad.Directory;
import com.honghe.ad.util.JdbcTemplateUtil;
import jodd.util.StringUtil;
import net.sf.json.JSONSerializer;
import org.apache.commons.collections.map.HashedMap;

import java.util.*;

/**
 *
 * @author zhanghongbin
 * @date 2016/7/29
 */
public final class User2DeviceDao {

    private User2DeviceDao() {

    }
    private static final String ADMIN_ID = "1";
    public final static User2DeviceDao INSTANCE = new User2DeviceDao();

    public final boolean add(String userId, String... deviceId) {
        List<String> sql = new ArrayList<>();
        for (String _id : deviceId) {
            sql.add("insert into ad_user2device(user_id,device_id) values(" + userId + "," + _id + ")");
        }
        return JdbcTemplateUtil.getJdbcTemplate().execute(sql.toArray(new String[sql.size()]));
    }
    public final boolean addByMulipleUserId(String userId, String... deviceId) {
        List<String> sql = new ArrayList<>();
        String[] splitUserId = userId.split(",");
        for (String uId : splitUserId) {
            for (String dId : deviceId) {
                sql.add("insert into ad_user2device(user_id,device_id) values(" + uId + "," + dId + ")");
            }
        }
        return JdbcTemplateUtil.getJdbcTemplate().execute(sql.toArray(new String[sql.size()]));
    }


    public final boolean delete(String... id) {
        List<String> sql = new ArrayList<>();
        for (String _id : id) {
            sql.add("delete from ad_user2device where id=" + _id.trim());
        }
        return JdbcTemplateUtil.getJdbcTemplate().execute(sql.toArray(new String[sql.size()]));
    }

    public final boolean deleteByUserId(String... userId) {
        List<String> sql = new ArrayList<>();
        for (String s : userId) {
            sql.add("delete from ad_user2device where user_id=" + s.trim());

        }
        return JdbcTemplateUtil.getJdbcTemplate().execute(sql.toArray(new String[sql.size()]));
    }
    public final Directory find(String userId, Object dtypeName) {
        String where = find(dtypeName);
        //查找所有地点列表和地点上的设备信息（如果地点和人有关联则显示信息）
        String sql = "select c.*,c2d.dtype_name,c2d.host_id,c2d.host_name,c2d.host_ipaddr,c2d.host_serialno,c2d.dspec_id,c2d.host_username,c2d.user_id, c2d.host_password,c2d.host_mac,c2d.host_hhtcmac,c2d.host_factory,c2d.host_port,c2d.host_channel   from ad_area as c left join" +
                " (select a.*,group_concat(b.dtype_name) as dtype_name,group_concat(b.host_id) as host_id,group_concat(b.host_name) as host_name, group_concat(b.host_ipaddr) as host_ipaddr,group_concat(b.host_serialno) as host_serialno, group_concat(b.dspec_id) as dspec_id, group_concat(b.host_username) as host_username, group_concat(b.host_password) as host_password, group_concat(b.host_mac) as host_mac,  group_concat(b.host_factory) as host_factory,  group_concat(b.host_hhtcmac) as host_hhtcmac,group_concat(b.host_port) as host_port,group_concat(b.host_channel) as host_channel,group_concat(b.user_id) as user_id" +
                " from ad_area2device as a  left join" +
                " (select h.*,g.dtype_name from ( select ih.*,iu2d.user_id from device_host ih,ad_user2device iu2d where ih.host_id = iu2d.device_id and iu2d.user_id=" + userId + " ) h, " +
                "(select ds.dspec_id,d.dtype_name from  device_specification ds,device_type d where ds.dtype_id = d.dtype_id " + where + " ) g where h.dspec_id = g.dspec_id ) as b on a.device_id=b.host_id group by area_id) as c2d on c.id=c2d.area_id " +
                "order by c.id";
        //增加若用户为admin时则显示所有已分配地点的设备
//        if ("1".equals(userId)){
//            sql = "select c.*,c2d.dtype_name,c2d.host_id,c2d.host_name,c2d.host_ipaddr,c2d.host_serialno,c2d.dspec_id,c2d.host_username,c2d.user_id, c2d.host_password,c2d.host_mac,c2d.host_hhtcmac,c2d.host_factory,c2d.host_port,c2d.host_channel   from ad_area as c left join" +
//                    " (select a.*,group_concat(b.dtype_name) as dtype_name,group_concat(b.host_id) as host_id,group_concat(b.host_name) as host_name, group_concat(b.host_ipaddr) as host_ipaddr,group_concat(b.host_serialno) as host_serialno, group_concat(b.dspec_id) as dspec_id, group_concat(b.host_username) as host_username, group_concat(b.host_password) as host_password, group_concat(b.host_mac) as host_mac,  group_concat(b.host_factory) as host_factory,  group_concat(b.host_hhtcmac) as host_hhtcmac,group_concat(b.host_port) as host_port,group_concat(b.host_channel) as host_channel,group_concat(b.user_id) as user_id" +
//                    " from ad_area2device as a  left join" +
//                    " (select h.*,g.dtype_name from ( select ih.*,1 as user_id from host ih ) h, " +
//                    "(select ds.dspec_id,d.dtype_name from  dspecification ds,dtype d where ds.dtype_id = d.dtype_id " + where + " ) g where h.dspec_id = g.dspec_id ) as b on a.device_id=b.host_id group by area_id) as c2d on c.id=c2d.area_id " +
//                    "order by c.id";
//        }
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        Set<String> areaIdSet = new HashSet<>();
        for (Map<String, String> _result : result) {
            if (!"".equals(_result.get("user_id"))) {
                areaIdSet.add(_result.get("id"));
            }
            if (dtypeName != null) {
                if (!"".equals(_result.get("dspec_id"))) {
                    areaIdSet.add(_result.get("id"));
                }
            }
        }
        //如果设备树和设备没有关联，用户和设备进行关联
        if (areaIdSet.isEmpty()) {
            Directory directory = new Directory(result.get(0).get("id"), result.get(0).get("name"));
            sql = " select h.*,g.dtype_name from ( select ih.*,iu2d.user_id from device_host ih,ad_user2device iu2d where ih.host_id = iu2d.device_id and iu2d.user_id=" + userId + ") h,(select ds.dspec_id,d.dtype_name from  device_specification ds,device_type d where ds.dtype_id = d.dtype_id " + where + " ) g where h.dspec_id = g.dspec_id";
//            if ("1".equals(userId)){
//                sql = " select h.*,g.dtype_name from ( select ih.*,1 as user_id from host ih) h,(select ds.dspec_id,d.dtype_name from  dspecification ds,dtype d where ds.dtype_id = d.dtype_id " + where + " ) g where h.dspec_id = g.dspec_id";
//            }
            result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
            for (Map<String, String> _result : result) {
                AreaDao.INSTANCE.addDirectoryData(directory.getData(), _result);
            }
            return directory;
        } else {
            Directory directory = find(result, areaIdSet);
            sql = "select h.*,g.dtype_name from (select ih.* from device_host ih where ih.host_id in(SELECT device_id FROM ad_user2device where user_id=" + userId + " and device_id not in(select device_id from ad_area2device))) h ,(select ds.dspec_id,d.dtype_name from  device_specification ds,device_type d where ds.dtype_id = d.dtype_id " + where + " ) g " +
                    "where h.dspec_id = g.dspec_id";
//            //增加若用户为admin时则显示所有已分配地点的设备
//            if ("1".equals(userId)){
//                sql = "select h.*,g.dtype_name from (select ih.* from host ih where ih.host_id in(SELECT device_id FROM ad_user2device where device_id>0 and device_id not in(select device_id from ad_area2device))) h ,(select ds.dspec_id,d.dtype_name from  dspecification ds,dtype d where ds.dtype_id = d.dtype_id " + where + " ) g " +
//                        "where h.dspec_id = g.dspec_id";
//            }
            result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
            for (Map<String, String> _result : result) {
                AreaDao.INSTANCE.addDirectoryData(directory.getData(), _result);
            }
            return directory;
        }

    }

    private final Directory find(List<Map<String, String>> result, Set<String> campusIdSet) {
        Directory directory = new Directory(result.get(0).get("id"), result.get(0).get("name"));
        //如果存在根节点,直接进行递归
        if (campusIdSet.contains(result.get(0).get("id"))) {
            AreaDao.INSTANCE.addDirectoryData(directory.getData(), result.get(0));
            recursiveOrganization(directory, result, Integer.parseInt(result.get(0).get("id")));
        } else {
            for (Map<String, String> _result : result) {
                String id = _result.get("id");
                if (campusIdSet.contains(id)) {
                    Directory dd = new Directory(id, _result.get("name"));
                    directory.getDirectories().add(dd);
                    AreaDao.INSTANCE.addDirectoryData(dd.getData(), _result);
                    recursiveOrganization(dd, result, Integer.parseInt(id));
                }
            }
        }
        return directory;
    }

    private void recursiveOrganization(Directory directory, List<Map<String, String>> result, int id) {
        for (Map<String, String> _result : result) {
            int pId = Integer.parseInt(_result.get("p_id"));
            if (pId == id) {
                String _id = _result.get("id");
                Directory dd = new Directory(_id, _result.get("name"));
                directory.getDirectories().add(dd);
                AreaDao.INSTANCE.addDirectoryData(dd.getData(), _result);
                recursiveOrganization(dd, result, Integer.parseInt(_id));
            }
        }
    }

    private String find(Object dtypeName) {
        String where = "";
        if (dtypeName != null && !"".equals(dtypeName)) {
            String[] dtypeNameArray = dtypeName.toString().split(",");
            StringBuilder sb = new StringBuilder();
            sb.append("and dtype_name in (");
            for (String _dtypeName : dtypeNameArray) {
                sb.append("'" + _dtypeName + "'").append(",");
            }
            where = sb.toString().substring(0, sb.toString().length() - 1) + ")";
        }
        return where;
    }

    public List<Map<String, String>> find(int userId, Object dtypeName) {
        String where = find(dtypeName);
        //管理员判断
        String userCondition = "";
        if (userId!=1){
            userCondition = " where ih.host_id = iu2d.device_id and iu2d.user_id=" + userId ;
        }
        String sqlWhere = "select h.*,g.dtype_name from ( select ih.*,iu2d.user_id from device_host ih left join ad_user2device iu2d on 1=1 " + userCondition +") h,(select ds.dspec_id,d.dtype_name from  device_specification ds,device_type d where ds.dtype_id = d.dtype_id " + where + " ) g where h.dspec_id = g.dspec_id";
        String sql = "select a.id as areaId, a.name as areaName,b.dtype_name as deviceType,b.host_id as hostId,b.host_name as hostName,b.dspec_id as dspecId,b.host_ipaddr as hostIp,b.host_serialno as hostSerialno," +
                "b.host_username as hostUsername,b.host_password as hostPassword,b.host_mac as hostMac,b.host_hhtcmac as hostHhtcmac,b.host_factory as hostFactory," +
                "b.host_port as hostPort,b.host_channel as hostChannel" +
                " from ad_area as a,(select h.*,i.area_id from (" + sqlWhere + ") as h,ad_area2device as i where h.host_id=i.device_id) as b "+
                "where a.id = b.area_id group by hostId";
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        for (Map<String, String> _result : result) {
            AreaDao.INSTANCE.addDataByCache(_result,_result.get("hostIp"));
        }
        return result;
    }

    /**
     * 前台查询用户设备列表专用
     * @param userId
     * @param dtypeName
     * @param searchWord
     * @return
     */
    public List<Map<String, String>> find(String userId, String dtypeName,String searchWord) {
        String where = "";
        if (!"".equals(dtypeName)){
            where+=" and c.dtype_name = '"+dtypeName+"' ";
        }
        if (!"".equals(searchWord)){
            where+=" and d.host_name like '%"+searchWord+"%'";
        }
        String sql = "select a.user_id as userId,d.host_id as hostId,d.host_name as hostName,d.host_ipaddr as hostIP,b.id as user2deviceId,c.dtype_name_cn as typeName,f.area_id as areaId,g.name as areaName from user " +
                "a,ad_user2device b,device_type c,device_host d,device_specification e,ad_area2device f,ad_area g where a.user_id = b.user_id and b.device_id = d.host_id and d.dspec_id = e.dspec_id and e.dtype_id = c.dtype_id and b.device_id = f.device_id and f.area_id = g.id and a.user_id = '"+userId+"'"+where;
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        return result;
    }

    /**
     * 查找当前用户所用设备的类型
     * @param userId
     * @return
     */
    public List<String> findDeviceTypeByUser(String userId) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT dt.dtype_name FROM" +
                " ad_user2device ud,device_host h,device_specification ds,device_type dt" +
                " WHERE ud.device_id = h.host_id" +
                " AND h.dspec_id = ds.dspec_id" +
                " AND ds.dtype_id = dt.dtype_id");
        //管理员默认查找所有的设备类型
        if(!"1".equals(userId)){
            sb.append(" AND user_id = " + userId + "");
        }
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(sb.toString());
        List<String> retList = new ArrayList<String>();
        for(Map<String, String> map:result){
            retList.add(map.get("dtype_name"));
        }
        return retList;
    }
    /**
     * 获取全部地点和设备
     *
     * @param userId    用户id
     * @param dtypeName 设备名称
     * @return
     */
    public final Directory findAllAreaDevice(String userId, Object dtypeName, String userFlag) {
        String dtypeNameWhere = find(dtypeName);
        Directory directory = null;
        String sql = "select group_concat(dh.host_id) as host_id, group_concat(dh.host_name) as host_name, " +
                "group_concat(dt.dtype_name) as dtype_name, group_concat(dh.host_ipaddr) as host_ipaddr, " +
                "group_concat(dh.host_serialno) as host_serialno, group_concat(ds.dspec_id) as dspec_id, " +
                "group_concat(dh.host_username) as host_username, group_concat(dh.host_password) as host_password, " +
                "group_concat(dh.host_mac) as host_mac, group_concat(dh.host_factory) as host_factory, " +
                "group_concat(dh.host_hhtcmac) as host_hhtcmac, group_concat(dh.host_port) as host_port, " +
                "group_concat(dh.host_channel) as host_channel, group_concat(dh.host_desc) as host_desc," +
                "group_concat(dh.host_systype) as host_systype,aa.id, aa.p_id, aa. name ";
        //获取和用户关联的设备和其地点信息
        if (ADMIN_ID.equals(userFlag)) {
            sql += " from device_host dh,device_type dt, device_specification ds, ad_area aa, ad_area2device aad where ";
        } else {
            sql += ",u2d.user_id from device_host dh, device_type dt, ad_user2device u2d, device_specification ds,ad_area aa,ad_area2device aad " +
                    "where dh.host_id = u2d.device_id and u2d.user_id =" + userId + " and ";
        }
        sql += "aa.id = aad.area_id and aad.device_id = dh.host_id and dh.dspec_id = ds.dspec_id and " +
                "ds.dtype_id = dt.dtype_id " + dtypeNameWhere + " group by area_id";

        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        //获取地点列表
        List<Map<String, String>> areaList = JdbcTemplateUtil.getJdbcTemplate().findList("select * from ad_area");
        Set<Map<String, String>> areaDeviceIds = new HashSet<>();
        //筛选出和用户关联的设备的地点id
        if (!StringUtil.isEmpty(result.toString())) {
            Map map;
            for (Map adId : result) {
                map = new HashedMap();
                map.put("id", adId.get("id").toString());
                map.put("p_id", adId.get("p_id").toString());
                map.put("name", adId.get("name").toString());
                areaDeviceIds.add(map);
                //查找该设备地点id的父节点及以上节点并存储
                getDeviceId(areaDeviceIds, areaList, map);
            }

            // 将Set集合转为List
            List<Map<String, String>> areaIdList = new ArrayList<>(areaDeviceIds);
            List<String> str = new ArrayList<>();
            for (Map deviceMap : result) {
                String string = deviceMap.get("id").toString() + "," + deviceMap.get("p_id").toString();
                if (!"".equals(str)) {
                    str.add(string);
                }
            }

            //将未挂设备的节点信息添加进result里，准备进行地点树加设备
            for (Map areaMap : areaIdList) {
                String areaStr = areaMap.get("id").toString() + "," + areaMap.get("p_id").toString();
                if (!str.contains(areaStr)) {
                    result.add(areaMap);
                }
            }
            if (!areaIdList.isEmpty()) {
                directory = findAreaDevice(result, "0");
            }
        }
        if (directory == null) {
            return new Directory("0", "");
        }
        return directory;
    }

    /**
     * @param result
     * @param rootPId
     * @return
     */
    private final Directory findAreaDevice(List<Map<String, String>> result, String rootPId) {
        Directory directory = null;
        for (Map<String, String> _result : result) {
            String p_id = _result.get("p_id");
            if (p_id.equals(rootPId)) {
                directory = new Directory(_result.get("id"), _result.get("name"));
                break;
            }
        }
        if (directory == null) {
            return new Directory("0", "");
        }
        recursiveDevice(directory.getDirectories(), result, directory.getId());
        return directory;
    }

    /**
     * 递归查询树
     *
     * @param directories
     * @param result
     * @param id
     */
    private void recursiveDevice(List<Directory> directories, List<Map<String, String>> result, String id) {
        Directory directory;
        for (Map<String, String> _result : result) {
            String p_id = _result.get("p_id");
            if (p_id.equals(id)) {
                String currentId = _result.get("id");
                directory = new Directory(currentId, _result.get("name"));
                if (_result.containsKey("host_id")) {
                    AreaDao.INSTANCE.addDirectoryData(directory.getData(), _result);
                }
                directories.add(directory);
                recursiveDevice(directory.getDirectories(), result, directory.getId());
            }
        }
    }

    /**
     * 查找该设备地点id的父节点及以上节点并存储
     *
     * @param areaDeviceIds 存放设备地点父节点及以上节点信息
     * @param areaList      所有的地点信息
     * @param aMap          节点信息，作为下一节点的父节点信息
     */
    private void getDeviceId(Set<Map<String, String>> areaDeviceIds, List<Map<String, String>> areaList, Map<String, String> aMap) {
        if (!"0".equals(aMap.get("p_id"))) {
            Map map = new HashedMap();
            for (Map areaMap : areaList) {
                if (aMap.get("p_id").equals(areaMap.get("id").toString())) {
                    map.put("id", areaMap.get("id").toString());
                    map.put("p_id", areaMap.get("p_id").toString());
                    map.put("name", areaMap.get("name").toString());
                    areaDeviceIds.add(map);
                    break;
                }
            }
            getDeviceId(areaDeviceIds, areaList, map);
        }
    }
}
