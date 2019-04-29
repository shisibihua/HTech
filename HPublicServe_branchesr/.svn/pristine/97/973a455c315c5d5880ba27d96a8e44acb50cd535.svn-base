package com.honghe.user.dao.impl;


import com.honghe.AbstractUserDao;
import com.honghe.user.util.JdbcTemplateUtil;
import com.honghe.dao.PageData;

import java.util.*;

/**
 * @author mz
 * @date 2018/2/9
 */
public class htechUserDao extends AbstractUserDao {

    /**
     * 教室
     */
    private final static String ROOM_TYPE = "10";
    /**
     * 主讲教室
     */
    private final static String MASTER_ROOM_NAME = "主讲教室";

    /**
     * 根据用户id获取用户角色
     *
     * @param userId 用户id
     * @return 角色信息
     */
    public List<Map<String, String>> htechUserGetRoleByUserId(String userId) {
        String sql = "SELECT uur.user_id as userId, uur.role_id as roleId, ur.role_name as roleName " +
                "FROM user_user2role uur " +
                "LEFT JOIN user_role ur ON uur.role_id=ur.role_id WHERE ";
        if (userId != null && !"1".equals(userId)) {
            sql += "uur.user_id=" + userId;
        } else {
            sql += "ur.role_id=1";
        }
        return JdbcTemplateUtil.getJdbcTemplate().findList(sql);
    }

    /**
     * 根据用户id查询组织机构以及下级地点
     *
     * @param userId 用户id
     * @return 地点信息
     */
    public Map<String, Object> htechUserGetAreaByUserId(String userId) {
        //根据用户id查询到关联的组织机构
        String sql = "SELECT distinct id from ad_campus where p_id='0'";
        Map<String, String> rootCampus = JdbcTemplateUtil.getJdbcTemplate().find(sql);
        String root_campus_id = "";
        if (rootCampus != null && !rootCampus.isEmpty()) {
            root_campus_id = rootCampus.get("id");
        }
        String sql_campus = "SELECT ac.id as campusId, ac.name as campusName, ac.type_id as campusType, ac.p_id as parentId, " +
                "ac.hatarea_id as countyId, ac.hatcity_id as cityId, ac.hatprovince_id as provinceId ";
        if (userId != null && !"1".equals(userId)) {
            sql_campus += "FROM ad_campus2user acu  LEFT JOIN ad_campus ac ON acu.campus_id=ac.id WHERE acu.user_id=" + userId;
        } else {
            sql_campus += "FROM ad_campus ac WHERE ac.id='" + root_campus_id + "'";
        }
        Map<String, Object> map = new HashMap<>();

        Map<String, String> mapCampus = JdbcTemplateUtil.getJdbcTemplate().find(sql_campus);
        //组织机构的等级
        String campusType = mapCampus.get("campusType");
        String campusId = mapCampus.get("campusId");
        String parentId = mapCampus.get("parentId");
        //关联的省市区id
        String countyId = mapCampus.get("countyId");
        String cityId = mapCampus.get("cityId");
        String provinceId = mapCampus.get("provinceId");

        if (countyId != null && !"".equals(countyId) && "3".equals(campusType)) {
            //区县级组织结构，查询省-市-区县的信息以及下级学校的信息
            map.putAll(getCountyInfo(countyId));
            map.put("childList", getSchoolByCounty(campusId));
        } else if (cityId != null && !"".equals(cityId) && "2".equals(campusType)) {
            //市级组织机构，查询省-市信息以及下级的区县的信息
            map.putAll(getCityInfo(cityId));
            map.put("childList", getCountyByCity(cityId));
        } else if (provinceId != null && !"".equals(provinceId) && "1".equals(campusType)) {
            //省级组织机构，查询省信息以及下级市的信息
            map.putAll(getProvinceInfo(provinceId));
            map.put("childList", getCityByProvince(provinceId));
        } else if ("5".equals(campusType)) {
            //分校组织机构，先查询上级学校的信息，再查询省-市-区县-学校的信息
            final String sql_school = "SELECT id as schoolId, name as schoolName FROM ad_campus WHERE id='" + parentId + "'";
            Map<String, String> mapSchool = JdbcTemplateUtil.getJdbcTemplate().find(sql_school);
            map.putAll(getCountyInfoByCampus(parentId));
            map.put("schoolId", parentId);
            map.put("schoolName", mapSchool.get("schoolName"));
            map.put("childList", Collections.EMPTY_LIST);
        } else if ("4".equals(campusType)) {
            //学校组织机构，查询省-市-区县-学校的信息
            map.putAll(getCountyInfoByCampus(campusId));
            map.put("schoolId", campusId);
            map.put("schoolName", mapCampus.get("campusName"));
            map.put("childList", Collections.EMPTY_LIST);
        }

        return map;
    }

    /**
     * 根据学校的id查询上层省-市-区县
     *
     * @param campusId 学校id
     * @return 省、市、区县
     */
    private Map<String, String> getCountyInfoByCampus(String campusId) {
        if (campusId != null && !"".equals(campusId)) {
            final String sql = "SELECT ha.id as countyId, ha.area as countyName, hc.id as cityId, " +
                    "hc.city as cityName, hp.id as provinceId, hp.province as provinceName " +
                    "FROM hat_area ha " +
                    "LEFT JOIN hat_city hc ON ha.p_id=hc.cityID " +
                    "LEFT JOIN hat_province hp ON hc.p_id=hp.provinceID " +
                    "WHERE ha.id=(SELECT adc.hatarea_id FROM ad_campus adc WHERE adc.id=(" +
                    "SELECT ac.p_id FROM ad_campus ac WHERE ac.id='" + campusId + "'))";
            Map<String, String> map = JdbcTemplateUtil.getJdbcTemplate().find(sql);
            if (map == null) {
                map = new HashMap<>();
            }
            if (map.size() > 0) {
                return map;
            }
        }

        Map<String, String> map = new HashMap<>();
        map.put("cityId", "");
        map.put("cityName", "");
        map.put("countyName", "");
        map.put("countyId", "");
        map.put("provinceId", "");
        map.put("provinceName", "");
        return map;

    }

    /**
     * 根据省的id获取下级市列表
     *
     * @param provinceId 省id
     * @return 下级市
     */
    private List<Map<String, String>> getCityByProvince(String provinceId) {
        final String sql = "SELECT hc.city as areaName, hc.id as areaId " +
                "FROM hat_city hc " +
                "LEFT JOIN hat_province hp ON hp.provinceID=hc.p_id " +
                "WHERE hp.id=" + provinceId;

        return JdbcTemplateUtil.getJdbcTemplate().findList(sql);
    }

    /**
     * 根据省id获取省信息
     *
     * @param provinceId 省id
     * @return 省信息
     */
    private Map<String, String> getProvinceInfo(String provinceId) {
        final String sql = "SELECT hp.id as provinceId, hp.province as provinceName " +
                "FROM hat_province hp " +
                "WHERE hp.id=" + provinceId;

        Map<String, String> map = JdbcTemplateUtil.getJdbcTemplate().find(sql);
        if (map == null) {
            map = new HashMap<>();
        }
        if (map.size() == 0) {
            map.put("provinceId", "");
            map.put("provinceName", "");
        }

        map.put("cityId", "");
        map.put("cityName", "");
        map.put("countyName", "");
        map.put("countyId", "");
        map.put("schoolId", "");
        map.put("schoolName", "");

        return map;
    }

    /**
     * 根据城市的id获取去下级区县
     *
     * @param cityId 城市id
     * @return 下级区县
     */
    private List<Map<String, String>> getCountyByCity(String cityId) {
        final String sql = "SELECT ha.id as areaId, ha.area as areaName " +
                "FROM hat_area ha " +
                "LEFT JOIN hat_city hc ON hc.cityID=ha.id " +
                "WHERE hc.id=" + cityId;

        return JdbcTemplateUtil.getJdbcTemplate().findList(sql);
    }

    /**
     * 根据城市的id获取省-市信息
     *
     * @param cityId 城市id
     * @return 省、市信息
     */
    private Map<String, String> getCityInfo(String cityId) {
        final String sql = "SELECT hc.id as cityId, hc.city as cityName, " +
                "hp.id as provinceId, hp.province as provinceName " +
                "FROM hat_city hc " +
                "LEFT JOIN hat_province hp ON hc.p_id=hp.provinceID " +
                "WHERE hc.id=" + cityId;

        Map<String, String> map = JdbcTemplateUtil.getJdbcTemplate().find(sql);
        if (map == null) {
            map = new HashMap<>();
        }
        if (map.size() == 0) {
            map.put("cityId", "");
            map.put("cityName", "");
            map.put("provinceId", "");
            map.put("provinceName", "");
        }
        map.put("countyName", "");
        map.put("countyId", "");
        map.put("schoolId", "");
        map.put("schoolName", "");

        return map;
    }

    /**
     * 根据区县id获取省-市-区县的信息
     *
     * @param countyId 区县id
     * @return 省、市、区县
     */
    private Map<String, String> getCountyInfo(String countyId) {
        final String sql = "SELECT ha.id as countyId, ha.area as countyName, hc.id as cityId, " +
                "hc.city as cityName, hp.id as provinceId, hp.province as provinceName " +
                "FROM hat_area ha " +
                "LEFT JOIN hat_city hc ON ha.p_id=hc.cityID " +
                "LEFT JOIN hat_province hp ON hc.p_id=hp.provinceID " +
                "WHERE ha.id=" + countyId;

        Map<String, String> map = JdbcTemplateUtil.getJdbcTemplate().find(sql);

        if (map == null) {
            map = new HashMap<>();
        }

        if (map.size() == 0) {
            //添加key值，避免缺少字段
            map.put("countyId", "");
            map.put("countyName", "");
            map.put("cityId", "");
            map.put("cityName", "");
            map.put("provinceId", "");
            map.put("provinceName", "");
        }

        map.put("schoolId", "");
        map.put("schoolName", "");

        return map;
    }

    /**
     * 根据区县组织结构id获取下级学校的信息
     *
     * @param campusId 区县组织结构的id
     * @return 下级学校
     */
    private List<Map<String, String>> getSchoolByCounty(String campusId) {
        final String sql = "SELECT ac.id as areaId, ac.name as areaName " +
                "FROM ad_campus ac " +
                "WHERE ac.p_id='" + campusId + "'";

        return JdbcTemplateUtil.getJdbcTemplate().findList(sql);
    }

    /**
     * 根据地点id获取上级地点省-市-区-学校
     *
     * @param type   当前地点的类型 市-city，区县-county，学校-school
     * @param areaId 地点的id
     * @return 地点信息 省-市-区-学校
     */
    public Map<String, String> htechUserGetAreaParentById(String type, String areaId) {
        if ("city".equals(type)) {
            //市级地点
            return getCityInfo(areaId);
        } else if ("county".equals(type)) {
            //区县级
            return getCountyInfo(areaId);
        } else if ("school".equals(type)) {
            //学校级
            Map<String, String> mapArea = getCountyInfoByCampus(areaId);
            mapArea.put("schoolId", "");
            mapArea.put("schoolName", "");
            return mapArea;
        }

        Map<String, String> map = new HashMap<>();
        map.put("cityId", "");
        map.put("cityName", "");
        map.put("countyName", "");
        map.put("countyId", "");
        map.put("provinceId", "");
        map.put("provinceName", "");
        return map;
    }

    /**
     * 根据省id获取市列表
     *
     * @param provinceId 省id
     * @return 市列表
     */
    public List<Map<String, String>> htechUserGetCityByProvince(String provinceId) {
        final String sql = "SELECT hc.id as areaId, hc.city as areaName " +
                "FROM hat_city hc " +
                "LEFT JOIN hat_province hp ON hp.provinceID=hc.p_id " +
                "WHERE hp.id=" + provinceId + " order by convert(areaName USING gbk)";

        return JdbcTemplateUtil.getJdbcTemplate().findList(sql);
    }

    /**
     * 根据市id获取区县列表
     *
     * @param cityId 市id
     * @return 区县列表
     */
    public List<Map<String, String>> htechUserGetCountyByCity(String cityId) {
        final String sql = "SELECT ha.id as areaId, ha.area as areaName " +
                "FROM hat_area ha " +
                "LEFT JOIN hat_city hc ON hc.cityID=ha.p_id " +
                "WHERE hc.id=" + cityId;

        return JdbcTemplateUtil.getJdbcTemplate().findList(sql);
    }

    /**
     * 根据区县id获取学校列表
     *
     * @param countyId 区县id
     * @return 学校列表
     */
    public List<Map<String, String>> htechUserGetCampusByCounty(String countyId) {
        final String sql = "SELECT name as areaName, id as areaId " +
                "FROM ad_campus " +
                "WHERE p_id IN " +
                "(SELECT id FROM ad_campus WHERE hatarea_id=" + countyId + ")";
        return JdbcTemplateUtil.getJdbcTemplate().findList(sql);
    }

    /**
     * 根据学校id、教室名称，分页获取教室列表
     *
     * @param schoolId    学校id
     * @param name        教室名称
     * @param roomType    主讲教室：“1”
     * @param pageSize    每页数量
     * @param currentPage 当前页码
     * @return 分页教室列表
     */
    public PageData htechUserGetRoomBySchoolId(String schoolId, String name, String roomType, String roomIds, int pageSize, int currentPage) {
        return getRoomByAreaId(getADAreaId(null, null, null, schoolId), pageSize, currentPage, name, roomType, roomIds);
    }

    /**
     * 根据省、市、区县、学校查询地点下所有教室的信息
     *
     * @param provinceId 省id
     * @param cityId     城市id 不是必须
     * @param countyId   区县id 不是必须
     * @param schoolId   学校id 不是必须
     * @return 教室列表
     */
    public String htechUserGetRoomByArea(String provinceId, String cityId, String countyId, String schoolId) {
        String areaId = getADAreaId(provinceId, cityId, countyId, schoolId);
        //查询地点表所有的数据
        final String sql = "SELECT id as roomId, name as roomName, p_id as parentId, type_id as typeId, number FROM ad_area ";
        List<Map<String, String>> areaList = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        List<Map<String, String>> list = getRoomList(areaList, areaId, null, ROOM_TYPE);
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                buffer.append(list.get(i).get("roomId"));
            } else {
                buffer.append("," + list.get(i).get("roomId"));
            }
        }
        return buffer.toString();
    }

    /**
     * 查询学校下的分校区
     *
     * @param campusId 学校id
     * @return 分校区的id集合
     */
    private List<String> getSubCampus(String campusId) {
        //查询学校下是否有分校区
        final String sql = "SELECT id FROM ad_campus WHERE p_id='" + campusId + "'";
        List<Map<String, String>> list = JdbcTemplateUtil.getJdbcTemplate().findList(sql);

        List<String> listId = new ArrayList<>();
        if (list != null && list.size() > 0) {
            //存在分校区
            for (Map<String, String> map : list) {
                //添加分校区的id
                listId.add(map.get("id"));
            }
        }
        return listId;
    }

    /**
     * 查询主讲教室类型id
     *
     * @return id
     */
    private String getMasterRoomId() {
        String id = "";
        //查询学校下是否有分校区
        final String sql = "SELECT id FROM ad_area_type WHERE name='" + MASTER_ROOM_NAME + "'";
        Map<String, String> resultMap = JdbcTemplateUtil.getJdbcTemplate().find(sql);
        if (resultMap != null && !resultMap.isEmpty()) {
            id = resultMap.get("id");
        }
        return id;
    }

    /**
     * 根据地点的id,获取地点下所有教室信息
     *
     * @param areaId      父地点的id
     * @param pageSize    每页大小
     * @param currentPage 当前页
     * @param name        教室名称
     * @param roomType    主讲教室"1"、辅讲教室
     * @param roomIds     排除roomIds
     * @return 教室信息
     */
    private PageData getRoomByAreaId(String areaId, int pageSize, int currentPage, String name, String roomType, String roomIds) {
        //查询地点表所有的数据
        String sql;
        String typeId;
        if ("1".equals(roomType)) {
            //主讲教室
            sql = "SELECT id as roomId, name as roomName, p_id as parentId, roomtype_id as typeId, number FROM ad_area ";
            typeId = getMasterRoomId();
        } else {
            //全部教室
            sql = "SELECT id as roomId, name as roomName, p_id as parentId, type_id as typeId, number FROM ad_area ";
            typeId = ROOM_TYPE;
        }
        if (roomIds != null && !"".equals(roomIds)) {
            sql += " where id not in(" + roomIds + ")";
        }
        List<Map<String, String>> areaList = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        //获取地点下所有的教室
        List<Map<String, String>> roomList = getRoomList(areaList, areaId, name.toLowerCase(), typeId);

        return getPageData(roomList, pageSize, currentPage);
    }

    /**
     * 分页返回数据
     *
     * @param list        所有数据的集合
     * @param pageSize    每页数量
     * @param currentPage 第几页
     * @return 分页数据
     */
    private PageData getPageData(List<Map<String, String>> list, int pageSize, int currentPage) {
        //分页返回
        int start = pageSize * (currentPage - 1);
        int end = start + pageSize;
        List<Map<String, String>> pageList = new ArrayList<>();
        for (int i = start; i < end; i++) {
            if (i < list.size()) {
                Map<String, String> map = list.get(i);
                //移除不需要返回的元素
                map.remove("typeId");
                map.remove("parentId");
                pageList.add(map);
            } else {
                break;
            }
        }
        return new PageData(currentPage, list.size(), pageSize, pageList);
    }

    /**
     * 根据地点的id,获取地点下所有教室信息
     *
     * @param notInSql    排除上课的教室
     * @param areaMap     地点的信息
     * @param pageSize    每页大小
     * @param currentPage 当前页
     * @return 教室信息
     */
    private PageData getRoomByAreaId(Map<String, String> areaMap, int pageSize, int currentPage, String notInSql) {
        //查询地点表所有的数据
        final String sql = "SELECT area.id as areaId,area.`name` as areaName,area.type_id as typeId,area.p_id as parentId,ha.area as countyName,hc.city as cityName,hp.provinceID as provinceId,hp.province as provinceName " +
                "FROM " +
                "(SELECT aa.id,aa.`name`,aa.type_id,ac.id as ac_id,aa.p_id,ac.`name` as ac_name,ac.hatprovince_id,ac.hatcity_id,ac.hatarea_id " +
                "FROM ad_area aa " +
                "LEFT JOIN ad_campus ac ON aa.org_id=ac.id) as area " +
                "LEFT JOIN hat_area ha ON ha.id=area.hatarea_id " +
                "LEFT JOIN hat_city hc ON hc.id=area.hatcity_id " +
                "LEFT JOIN hat_province hp ON hp.id=area.hatprovince_id " + notInSql;
        List<Map<String, String>> areaList = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        //获取地点下所有的教室
        String areaId = areaMap.get("areaId");
        List<Map<String, String>> roomList = getRoomAndParentInfo(areaList, areaId, areaMap);

        return getPageData(roomList, pageSize, currentPage);
    }

    /**
     * 获取地点下的所有教室
     *
     * @param name     教室名称
     * @param parentId 父节点的id
     * @param areaList 地点集合
     * @return 父节点下的教室
     */
    private List<Map<String, String>> getRoomList(List<Map<String, String>> areaList, String parentId, String name, String typeId) {
        List<Map<String, String>> list = new ArrayList<>();
        if (areaList == null || areaList.size() == 0 || parentId == null || "".equals(parentId) || typeId == null || "".equals(typeId)) {
            return list;
        }

        for (Map<String, String> map : areaList) {
            if (parentId.equals(map.get("parentId"))) {
                if (typeId.equals(map.get("typeId"))) {
                    //是教室
                    if (name != null && !"".equals(name)) {
                        //有名称的限制
                        if (map.get("roomName").toLowerCase().contains(name)) {
                            list.add(map);
                        } else {
                            continue;
                        }
                    } else {
                        list.add(map);
                    }
                } else {
                    list.addAll(getRoomList(areaList, map.get("roomId"), name, typeId));
                }
            } else {
                continue;
            }
        }
        return list;
    }

    /**
     * 递归获取教室，包括上级省、市、区县、学校信息
     *
     * @param areaList 地点集合
     * @param parentId 父节点id
     * @param areaMap  父节点信息
     * @return 教室信息
     */
    private List<Map<String, String>> getRoomAndParentInfo(List<Map<String, String>> areaList, String parentId, Map<String, String> areaMap) {
        List<Map<String, String>> list = new ArrayList<>();
        if (areaList == null || areaList.size() == 0 || parentId == null || "".equals(parentId)) {
            return list;
        }

        for (Map<String, String> map : areaList) {
            if (parentId.equals(map.get("parentId"))) {
                String typeId = map.get("typeId");
                String areaName = map.get("areaName");
                if ("1".equals(typeId)) {
                    //省
                    areaMap.put("provinceId", map.get("provinceId"));
                    areaMap.put("provinceName", map.get("provinceName"));
                } else if ("2".equals(typeId)) {
                    //市
                    areaMap.put("cityName", map.get("cityName"));
                } else if ("3".equals(typeId)) {
                    //区县
                    areaMap.put("countyName", map.get("countyName"));
                } else if ("4".equals(typeId)) {
                    //学校
                    areaMap.put("schoolName", areaName);
                }
                if (ROOM_TYPE.equals(typeId)) {
                    //是教室
                    map.put("provinceName", areaMap.get("provinceName") == null ? "" : areaMap.get("provinceName"));
                    map.put("provinceId", areaMap.get("provinceId") == null ? "" : areaMap.get("provinceId"));
                    map.put("cityName", areaMap.get("cityName") == null ? "" : areaMap.get("cityName"));
                    map.put("countyName", areaMap.get("countyName") == null ? "" : areaMap.get("countyName"));
                    map.put("schoolName", areaMap.get("schoolName") == null ? "" : areaMap.get("schoolName"));
                    list.add(map);
                } else {
                    list.addAll(getRoomAndParentInfo(areaList, map.get("areaId"), areaMap));
                }
            } else {
                continue;
            }
        }
        return list;
    }

    /**
     * 根据学校id获取子地点树(学校以下的地点)
     *
     * @param schoolId 父id
     * @return 地点树
     */
    public Map<String, Object> htechUserGetAreaTree(String schoolId) {
        Map<String, Object> map = new HashMap<>();
        //查询地点表所有的数据
        final String sql = "SELECT id as areaId, name as areaName, p_id as parentId FROM ad_area ";
        List<Map<String, String>> areaList = JdbcTemplateUtil.getJdbcTemplate().findList(sql);

        //根据学校id查询学校的信息
        final String sqlSchool = "SELECT id as areaId, name as areaName, p_id as parentId " +
                "FROM ad_campus " +
                "WHERE id='" + schoolId + "' OR p_id='" + schoolId + "'";
        List<Map<String, String>> list = JdbcTemplateUtil.getJdbcTemplate().findList(sqlSchool);
        int index = -1;
        for (int i = 0; i < list.size(); i++) {
            if (schoolId.equals(list.get(i).get("areaId"))) {
                //学校
                index = i;
                map.putAll(list.get(i));
                break;
            }
        }

        if (index >= 0) {
            //移除学校，只剩下分校
            list.remove(index);
        }

        if (list.size() > 0) {
            //存在分校，查询每个分校下的地点
            List<Map<String, Object>> childList = new ArrayList<>();
            for (Map<String, String> mapSub : list) {
                //分校
                Map<String, Object> childMap = new HashMap<>();
                childMap.putAll(mapSub);
                childMap.put("child", getAreaTree(areaList, mapSub.get("areaId")));
                childList.add(childMap);
            }
            //学校下的地点
            childList.addAll(getAreaTree(areaList, schoolId));
            map.put("child", childList);
        } else {
            //无分校
            //子地点树
            map.put("child", getAreaTree(areaList, schoolId));
        }

        return map;
    }

    /**
     * 获取子地点树
     *
     * @param areaList 所有地点
     * @param parentId 父id
     * @return 子地点树
     */
    private List<Map<String, Object>> getAreaTree(List<Map<String, String>> areaList, String parentId) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (areaList == null || parentId == null) {
            return list;
        }

        for (Map<String, String> map : areaList) {
            if (parentId.equals(map.get("parentId"))) {
                //子地点
                Map<String, Object> objectMap = new HashMap<>();
                objectMap.putAll(map);
                objectMap.put("child", getAreaTree(areaList, map.get("areaId")));
                list.add(objectMap);
            }
        }

        return list;
    }

    /**
     * 根据学校id、教师名称，分页获取教师列表
     *
     * @param schoolId    学校id
     * @param name        教师名称
     * @param teacherIds  排除教师ids
     * @param currentPage 当前页
     * @param pageSize    每页数量
     * @return 教师列表
     */
    public PageData htechUserGetTeacherBySchoolId(String schoolId, String name, String teacherIds, int currentPage, int pageSize) {
        //查询学校(包括分校)下所有教师的语句
        final String sql_user = "(SELECT user_id,user_code,user_realname,user_type FROM `user` u " +
                "WHERE u.user_id IN " +
                "(SELECT acu.user_id FROM ad_campus2user acu " +
                "LEFT JOIN ad_campus ac ON acu.campus_id=ac.id " +
                "WHERE ac.id='" + schoolId + "' OR ac.p_id='" + schoolId + "') and u.user_status=1" +
                ") as u_temp ";

        //查询学科、学段、身份等
        final String sql_result = "SELECT u_temp.user_id as id,u_temp.user_realname as name, rt.tc_name as stage, u_type.type_name as role,cs.`name` as subject ";
        String sql = "FROM " + sql_user +
                "INNER JOIN user_teacher ut ON u_temp.user_code=ut.teacher_code " +
                "LEFT JOIN res_techcolumn rt ON rt.tc_id=ut.stage_id " +
                "LEFT JOIN user_type u_type ON u_type.id=u_temp.user_type " +
                "LEFT JOIN user_teacher2subject uts ON uts.teacher_code=ut.teacher_code " +
                "LEFT JOIN classification_subject cs ON cs.id=uts.subject_id where 1=1 ";


        final int start = (currentPage - 1) * pageSize;
        final String sql_limit = " order by u_temp.user_id limit " + start + "," + pageSize;

        if (name != null && !"".equals(name)) {
            //添加名称的条件
//            sql += " WHERE u_temp.user_realname like '%" + name + "%'";
            sql += " and lower(u_temp.user_realname) like '%" + name.toLowerCase() + "%'";
        }
        if (teacherIds != null && !"".equals(teacherIds)) {
            sql += " and u_temp.user_id not in(" + teacherIds + ")";
        }
        //查询条目数量的语句
        final String sqlCount = "SELECT COUNT(*) " + sql;

        sql += sql_limit;

        return JdbcTemplateUtil.getJdbcTemplate().findByPage(currentPage, pageSize, sqlCount, sql_result + sql);
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
    public PageData htechUserGetRoomByPage(String provinceId, String cityId, String countyId, String schoolId, int currentPage, int pageSize) {
        //先获取地点表中的对应的id，再查询该id下的教室
        Map<String, String> map = getADAreaInfo(provinceId, cityId, countyId, schoolId);
        return getRoomByAreaId(map, pageSize, currentPage, "");
    }

    /**
     * 分页获取所有教室
     *
     * @param currentPage 第几页
     * @param pageSize    每页数量
     * @return 教室信息
     */
    public PageData htechUserGetAllRoomByPage(Integer currentPage, Integer pageSize) {
        Map<String, String> map = new HashMap<>();
        //最高地点的父节点id为0
        map.put("areaId", "0");
        return getRoomByAreaId(map, pageSize, currentPage, "");
    }

    /**
     * 获取地点在ad_area表中的id
     *
     * @param provinceId 省id
     * @param cityId     市id
     * @param countyId   区县id
     * @param schoolId   学校id
     * @return ad_area表中的id
     */
    private String getADAreaId(String provinceId, String cityId, String countyId, String schoolId) {
        final String sql = "SELECT aa.id as areaId " +
                "FROM ad_campus ac " +
                "LEFT JOIN ad_area aa ON ac.id=aa.org_id ";
        String sql_where = null;
        if (schoolId != null && !"".equals(schoolId)) {
            //存在学校id，查询ad_area表中该组织机构的id
            sql_where = "WHERE ac.id='" + schoolId + "'";
        } else if (countyId != null && !"".equals(countyId)) {
            //存在区县id, 查询ad_area表中该组织机构的id
            sql_where = "WHERE ac.hatarea_id=" + countyId + " AND ac.type_id=3";
        } else if (cityId != null && !"".equals(cityId)) {
            //存在城市id，查询ad_area表中该组织机构的id
            sql_where = "WHERE ac.hatcity_id=" + cityId + " AND ac.type_id=2";
        } else if (provinceId != null && !"".equals(provinceId)) {
            //存在省id，查询ad_area表中该组织机构的id
            sql_where = "WHERE ac.hatprovince_id=" + provinceId + " AND ac.type_id=1";
        }

        Map<String, String> map = JdbcTemplateUtil.getJdbcTemplate().find(sql + sql_where);
        return map.get("areaId");
    }

    /**
     * 获取地点信息
     *
     * @param provinceId 省id
     * @param cityId     市id
     * @param countyId   区县id
     * @param schoolId   学校id
     * @return ad_area表中的id
     */
    private Map<String, String> getADAreaInfo(String provinceId, String cityId, String countyId, String schoolId) {
        final String sql = "SELECT aa.id as areaId,aa.name as schoolName,ha.area as countyName,hc.city as cityName,hp.id as provinceId,hp.province as provinceName " +
                "FROM ad_campus ac " +
                "LEFT JOIN ad_area aa ON ac.id=aa.org_id " +
                "LEFT JOIN hat_area ha ON ha.id=ac.hatarea_id " +
                "LEFT JOIN hat_city hc ON hc.id=ac.hatcity_id " +
                "LEFT JOIN hat_province hp ON hp.id=ac.hatprovince_id";

        String sql_where = null;
        if (schoolId != null && !"".equals(schoolId)) {
            //根据学校id，查询信息
            sql_where = " WHERE ac.id='" + schoolId + "'";
        } else if (countyId != null && !"".equals(countyId)) {
            //存在区县id
            sql_where = " WHERE ac.hatarea_id=" + countyId + " AND ac.type_id=3";
        } else if (cityId != null && !"".equals(cityId)) {
            //存在城市id
            sql_where = " WHERE ac.hatcity_id=" + cityId + " AND ac.type_id=2";
        } else if (provinceId != null && !"".equals(provinceId)) {
            //存在省id
            sql_where = " WHERE ac.hatprovince_id=" + provinceId + " AND ac.type_id=1";
        }

        return JdbcTemplateUtil.getJdbcTemplate().find(sql + sql_where);
    }

    /**
     * 根据城市id获取学校列表
     *
     * @param cityId 城市id
     * @return 学校集合
     */
    private List<Map<String, String>> getSchoolByCity(int cityId) {
        //查询到所有区县的id
        final String sql_countyId = "SELECT ha.id " +
                "FROM hat_area ha " +
                "LEFT JOIN hat_city hc ON hc.cityID=ha.p_id " +
                "WHERE hc.id=" + cityId;
        //查询所有的学校
        final String sql = "SELECT id as areaId " +
                "FROM ad_campus " +
                "WHERE p_id IN " +
                "(SELECT id FROM ad_campus WHERE type_id='2' AND hatarea_id IN " +
                "(" + sql_countyId + ")" +
                ")";

        return JdbcTemplateUtil.getJdbcTemplate().findList(sql);
    }

    /**
     * 根据城市、区县、学校分页查询除了ids之外的教室
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
    public PageData htechUserGetRoomExceptIds(String provinceId, String cityId, String countyId, String schoolId, String roomIds, int currentPage, int pageSize) {
        String[] split = roomIds.split(",");
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < split.length; i++) {
            if (i == split.length - 1) {
                //最后一个不加逗号
                stringBuffer.append("'" + split[i] + "'");
            } else {
                stringBuffer.append("'" + split[i] + "',");
            }
        }
        String notInSql = "WHERE area.id NOT IN (" + stringBuffer + ")";
        //先获取对应的ad_area地点表id，再查询教室
        Map<String, String> map = getADAreaInfo(provinceId, cityId, countyId, schoolId);
        return getRoomByAreaId(map, pageSize, currentPage, notInSql);
    }

    /**
     * 根据省id获取所有区县的id
     *
     * @param provinceId 省id
     * @return 区县id的集合
     */
    public List<Map<String, String>> htechUserGetCountyByProvince(String provinceId) {
        String sql = "SELECT ha.id as areaId, ha.area as areaName " +
                "FROM hat_area ha " +
                "LEFT JOIN hat_city hc ON hc.cityID=ha.p_id " +
                "LEFT JOIN hat_province hp ON hp.provinceID=hc.p_id " +
                "WHERE hp.id=" + provinceId + " order by convert(areaName USING gbk)";

        return JdbcTemplateUtil.getJdbcTemplate().findList(sql);
    }

    /**
     * 根据省id获取所有学校的id
     *
     * @param provinceId 省id
     * @return 学校id的集合
     */
    public List<Map<String, String>> htechUserGetSchoolByProvince(String provinceId) {
        //查询到所有区县的id
        final String sql_areaId = "SELECT ha.id as areaId " +
                "FROM hat_area ha " +
                "LEFT JOIN hat_city hc ON hc.cityID=ha.p_id " +
                "LEFT JOIN hat_province hp ON hp.provinceID=hc.p_id " +
                "WHERE hp.id=" + provinceId;
        //查询所有的学校
        final String sql = "SELECT id as areaId, name as areaName " +
                "FROM ad_campus " +
                "WHERE p_id IN " +
                "(SELECT id FROM ad_campus WHERE type_id='3' AND hatarea_id IN " +
                "(" + sql_areaId + ")" +
                ") order by convert(areaName USING gbk)";

        return JdbcTemplateUtil.getJdbcTemplate().findList(sql);
    }

    /**
     * 获取地点下主讲教室和接收教室的数量
     *
     * @param provinceId 省id
     * @param cityId     市id
     * @param countyId   区县id
     * @param schoolId   学校id
     * @return 主讲和接收教室的数量
     */
    public Map<String, Integer> htechUserGetRoomCount(String provinceId, String cityId, String countyId, String schoolId) {
        //获取地点表中的id作为父id
        String areaId = getADAreaId(provinceId, cityId, countyId, schoolId);

        //查询地点表下所有数据
        final String sql = "SELECT id as areaId, p_id as parentId, type_id as typeId, roomtype_id as roomTypeId FROM ad_area ";
        List<Map<String, String>> list = JdbcTemplateUtil.getJdbcTemplate().findList(sql);

        //递归获取该地点下主讲和接收教室的数量
        return getRoomCount(list, areaId);
    }

    /**
     * 获取地点下的主讲教室和接收教室数量
     *
     * @param areaList 地点的集合
     * @param parentId 父地点的id
     * @return 主讲和接收教室的数量
     */
    private Map<String, Integer> getRoomCount(List<Map<String, String>> areaList, String parentId) {
        Map<String, Integer> countMap = new HashMap<>();
        countMap.put("roomCount", 0);
        countMap.put("hostRoomCount", 0);
        if (areaList == null || areaList.size() == 0 || parentId == null || "".equals(parentId)) {
            return countMap;
        }

        for (Map<String, String> map : areaList) {
            if (parentId.equals(map.get("parentId"))) {
                if (ROOM_TYPE.equals(map.get("typeId"))) {
                    //是教室
                    int roomCount = countMap.get("roomCount") == null ? 0 : countMap.get("roomCount");
                    countMap.put("roomCount", roomCount + 1);
                    if (getMasterRoomId().equals(map.get("roomTypeId"))) {
                        //主讲教室
                        int hostRoomCount = countMap.get("hostRoomCount") == null ? 0 : countMap.get("hostRoomCount");
                        countMap.put("hostRoomCount", hostRoomCount + 1);
                    }
                } else {
                    Map<String, Integer> mapChild = getRoomCount(areaList, map.get("areaId"));
                    int roomCount = mapChild.get("roomCount") == null ? 0 : mapChild.get("roomCount");
                    roomCount += countMap.get("roomCount");
                    int hostRoomCount = mapChild.get("hostRoomCount") == null ? 0 : mapChild.get("hostRoomCount");
                    hostRoomCount += countMap.get("hostRoomCount");
                    countMap.put("roomCount", roomCount);
                    countMap.put("hostRoomCount", hostRoomCount);
                }
            }
        }
        return countMap;
    }

    /**
     * 根据省名称获取省信息
     *
     * @param provinceName 省名称
     * @return 省信息
     */
    public Map<String, String> htechUserGetProvinceByName(String provinceName) {
        final String sql = "SELECT id, province FROM hat_province WHERE province like '%" + provinceName + "%'";
        return JdbcTemplateUtil.getJdbcTemplate().find(sql);
    }
}
