package com.honghe.organization.agency.dao;


import com.honghe.dao.PageData;
import com.honghe.user.util.JdbcTemplateUtil;

import java.util.*;

/**
 * Created by wei on 2015/12/09.
 */
public class AgencyDao {


    private AgencyDao() {

    }

    public final static AgencyDao INSTANCE = new AgencyDao();

    public final static String SQL = "select u.user_id as userId, u.user_name as userName," +
            "u.user_type as userType,u.user_realname as userRealName,u.user_path as userPath,u.user_avatar as userAvatar," +
            "u.user_gender as userGender,u.user_birthday as userBirthday,u.user_mobile as userMobile,u.user_email as userEmail," +
            "u.user_address as userAddress,u.user_regdate as userRegdate,a.agency_id as agencyId, a.agency_name as agencyName,a.p_id as pId,a.agency_level as agencyLevel from user u left join user2agency on u.user_id=user2agency.user_id " +
            "left join agency a on user2agency.agency_id=a.agency_id";

    /**
     * 按机构名字查询  带用户信息
     *
     * @param name 机构名称
     * @return map集合
     */
    public final List<Map<String, String>> findAgencyByName(String name) {
        return JdbcTemplateUtil.getJdbcTemplate().findList(SQL + " where a.agency_name='" + name + "'");
    }

    /**
     * 按机构名字查询 不带用户信息
     *
     * @param name 机构名称
     * @return 结果集
     */
    public final List<Map<String, String>> getAgencyByName(String name) {
        System.out.println("select agency_id as id,agency_name as name,p_id as pId,agency_level as level from agency a where a.agency_name like " + "'%" + name + "%'");
        return JdbcTemplateUtil.getJdbcTemplate().findList("select agency_id as id,agency_name as name,p_id as pId,agency_level as level from agency a where a.agency_name like " + "'%" + name + "%'");
    }


    /**
     * 根据机构name分页模糊查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return 结果集
     */
    public final PageData findAgencyByName(int page, int pageSize, String name) {
        String condition = "";
        if (!"".equals(name)) {
            condition = "and a.agency_name like '%" + name + "%'";
        }
        int count = (int) JdbcTemplateUtil.getJdbcTemplate().count("select count(*) from user u left join user2agency on u.user_id=user2agency.user_id left join agency a on user2agency.agency_id=a.agency_id where u.user_status=1 and u.user_id <>1 " + condition);
        int start = PageData.calcFirstItemIndexOfPage(page, pageSize, count);
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(SQL +
                " where u.user_status=1 and  u.user_id <>1 " + condition +
                " order by u.user_regdate desc limit " + start + "," + pageSize);
        PageData pageData = new PageData(page, count, pageSize, result);
        return pageData;
    }

    /**
     * add20160314
     *
     * @param agency_id 机构id
     * @return 结果集
     * 根据机构id查询机构
     */

    public final List<Map<String, String>> findAgencyById(int agency_id) {

        return JdbcTemplateUtil.getJdbcTemplate().findList("select agency_id as id,agency_name as name,p_id as pId,agency_level as level from agency a where a.agency_id=" + agency_id);
    }

    /**
     * 根据机构name分页模糊查询 不带用户信息
     *
     * @param page     当前页
     * @param pageSize 每页的个数
     * @param name     机构名称
     * @return 结果集
     */
    public final PageData getAgencyByName1(int page, int pageSize, String name) {
        String condition = "";
        if (!"".equals(name)) {
            condition = "where a.agency_name like '%" + name + "%'";
        }
        int count = (int) JdbcTemplateUtil.getJdbcTemplate().count("select count(*) from agency a " + condition);
        int start = PageData.calcFirstItemIndexOfPage(page, pageSize, count);
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList("select agency_id as id,agency_name as name,p_id as pId,agency_level as level from agency a " + condition +
                " order by a.agency_id limit " + start + "," + pageSize);
        PageData pageData = new PageData(page, count, pageSize, result);
        return pageData;
    }

    /**
     * 根据机构id查询用户信息
     *
     * @param agencyId 机构id
     * @return 结果集
     */

    public final List<Map<String, String>> findUserById(int agencyId) {

        return JdbcTemplateUtil.getJdbcTemplate().findList(SQL + " where u.user_status=1 and a.agency_id=" + agencyId);
    }

    /**
     * add 20160314
     * 根据机构id查询所有子机构以及所有子机构下属机构
     *
     * @param agencyId 机构id
     * @return
     */
    public final Agency findSonAgency(int agencyId) {
        String sql = "select * from agency";
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        Agency rootAgency = new Agency();
        for (Map<String, String> itemResult : result) {
            String id = itemResult.get("agency_id");
            if (Integer.parseInt(id) == agencyId) {
                rootAgency.setId(id);
                rootAgency.setLevel(itemResult.get("agency_level"));
                rootAgency.setName(itemResult.get("agency_name"));
                break;
            }
        }
        roundList(rootAgency.getAgencyList(), result, agencyId);
        return rootAgency;
    }

    private final void roundList(List<Agency> agencyList, List<Map<String, String>> result, int agency_id) {
        for (Map<String, String> itemResult : result) {
            String p_id = itemResult.get("p_id");
            if (Integer.parseInt(p_id) == agency_id) {
                String currentAgencyId = itemResult.get("agency_id");
                Agency agency = new Agency(currentAgencyId, itemResult.get("agency_name"), itemResult.get("agency_level"));
                agencyList.add(agency);
                roundList(agency.getAgencyList(), result, Integer.parseInt(currentAgencyId));
            }
        }
    }


    /**
     * 根据用户id查询机构信息
     *
     * @param userId 用户id
     * @return map集合
     */

    public final Map<String, String> findByUserId(int userId) {
        System.out.println(SQL + " where u.user_status=1 and u.user_id=" + userId);
        return JdbcTemplateUtil.getJdbcTemplate().find(SQL + " where u.user_status=1 and u.user_id=" + userId);
    }

    /**
     * 根据id删除机构
     *
     * @param agencyId 机构id
     * @return true/false
     */

    public final boolean deleteAgencyById(int agencyId) {
        List<Map<String, String>> m = new ArrayList<Map<String, String>>();
        String sql = "select agency_id from agency where p_id=" + "(select agency_id from agency where agency_id=" + agencyId + " )";
        m = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        if (m.size() > 0) {
            for (int i = 0; i < m.size(); i++) {
                Map mm = m.get(i);
                mm.get("agency_id");
                deleteAgencyById(Integer.parseInt(mm.get("agency_id").toString()));
            }
        }

        JdbcTemplateUtil.getJdbcTemplate().delete("delete from user2agency where agency_id=" + agencyId);
        return JdbcTemplateUtil.getJdbcTemplate().delete("delete from agency where agency_id=" + agencyId);


    }



//    public final boolean deleteAgencyById(int agencyId) {
//        List<String> totallist = new ArrayList<String>();
//        List<Map<String, String>> m = new ArrayList<Map<String, String>>();
//        String sql = "select agency_id from agency where p_id=" + "(select agency_id from agency where agency_id=" + agencyId + " )";
//        m = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
//        if (m.size() > 0) {
//                    for (int i = 0; i < m.size(); i++) {
//                        Map mm = m.get(i);
//
//                        // 通过entrySet()取得key值和value值
//                        Iterator<Map.Entry<String, String>> itor = mm.entrySet().iterator();
//                        while (itor.hasNext()) {
//                            Map.Entry<String, String> entry = itor.next();
//                            String value = entry.getValue();
//                            totallist.add(value);
//                            deleteAgencyById(Integer.parseInt(value));
//                        }
//            }
//        }
//
//        for (String s : totallist) {
//            JdbcTemplateUtil.getJdbcTemplate().delete("delete from user2agency where agency_id=" + s);
//            JdbcTemplateUtil.getJdbcTemplate().delete("delete from agency where agency_id=" + s);
//        }
//        JdbcTemplateUtil.getJdbcTemplate().delete("delete from user2agency where agency_id=" + agencyId);
//        return JdbcTemplateUtil.getJdbcTemplate().delete("delete from agency where agency_id=" + agencyId);
//
//
//    }

    /**
     * 更新机构
     *
     * @param data map集合
     * @return true/false
     */

    public final boolean update(Map<String, Object> data) {
        String agencyId = data.get("agencyId").toString();

        String updateSql = "";
        if (data.containsKey("agencyName")) {
            String agencyName = data.get("agencyName").toString().trim();
            updateSql += "agency_name='" + agencyName + "',";
        }
        if (data.containsKey("pId")) {
            String pId = data.get("pId").toString().trim();
            updateSql += "p_id=" + pId + ",";
        }
        if (data.containsKey("agencyLevel")) {
            String agencyLevel = data.get("agencyLevel").toString().trim();
            updateSql += "agency_level=" + agencyLevel + ",";
        }

        if (updateSql.length() == 0) {
            return false;
        }
        updateSql = updateSql.substring(0, updateSql.length() - 1);
        return JdbcTemplateUtil.getJdbcTemplate().update("update agency set " + updateSql + " where agency_id=" + agencyId);
    }

    /**
     * 机构添加
     *
     * @param data map集合
     * @return 添加的个数
     */

    public final long addAgency(Map<String, Object> data) {
        if (!data.containsKey("agencyName") || !data.containsKey("pId")) {
            return 0;
        }
        String agencyName = "";
        if (data.containsKey("agencyName")) {
            agencyName = data.get("agencyName").toString().trim();
        }
        String pId = "";
        if (data.containsKey("pId")) {
            pId = data.get("pId").toString().trim();
        }
        String agencyLevel = "0";
        if (data.containsKey("agencyLevel") && data.get("agencyLevel")!=null) {
            agencyLevel = data.get("agencyLevel").toString().trim();
        }

        String sql = "insert into agency (" + "agency_name," + "p_id," + "agency_level )values('" + agencyName + "'," + pId + "," + agencyLevel + ")";
        return JdbcTemplateUtil.getJdbcTemplate().add(sql, "agencyId");
    }

    /**
     * 添加用户和机构关系
     *
     * @param data map集合
     * @return 添加的个数
     */

    public final Long addAgencyRelation(Map<String, Object> data) {
        if (!data.containsKey("agencyId") || !data.containsKey("userId")) {
            return 0L;
        }
        String agencyId = "";
        if (data.containsKey("agencyId")) {
            agencyId = data.get("agencyId").toString().trim();
        }
        String userId = "";
        if (data.containsKey("userId")) {
            userId = data.get("userId").toString().trim();
        }
        String sql = "insert into user2agency(agency_id,user_id)values(" + agencyId + "," + userId + ")";

        return JdbcTemplateUtil.getJdbcTemplate().add(sql, "id");

    }
    /**
     * 修改用户和机构关系
     *
     * @param userId 用户Id
     * @param agencyId 机构Id
     * @return 添加的个数
     */

    public final boolean updateAgencyRelation(String userId,String agencyId) {

        String sql = "update user2agency set agency_id=" + agencyId + " where user_id=" + userId;

        return JdbcTemplateUtil.getJdbcTemplate().update(sql);

    }
    /**
     * 根据机构id删除机构与用户关联
     *
     * @param agencyId 机构id
     * @return true/false
     */

    public boolean deleteAgencyRelation(int agencyId) {
        return JdbcTemplateUtil.getJdbcTemplate().delete("delete from user2agency where agency_id=" + agencyId);
    }

    /**
     * 20160420
     * 根据机构id和用户id删除机构和用户关联
     *
     * @param agencyId 机构id
     * @param userId   用户id
     * @return true/false
     */
    public boolean deleteUserAgencyRelation(int agencyId, int userId) {
        return JdbcTemplateUtil.getJdbcTemplate().delete("delete from user2agency where agency_id= " + agencyId + " and user_id = " + userId);
    }

    /**
     * 20160420
     * 根据用户id删除机构和用户关联
     *
     * @param userId 用户id
     * @return true/false
     */
    public boolean deleteAgencyRelation(String userId) {
        return JdbcTemplateUtil.getJdbcTemplate().delete("delete from user2agency where user_id=" + userId);
    }

    /**
     * 查询所有机构信息
     *
     * @return 结果集
     */

    public final List<Map<String, String>> findAgency() {

        String sql = "select agency_id as id,agency_name as name,p_id as pId,agency_level as level from agency";
        return JdbcTemplateUtil.getJdbcTemplate().findList(sql);
    }

    /**
     * addby 20160331
     * 根据机构id获取管理员信息
     *
     * @param agencyId 机构id
     * @return map集合
     */
    public final Map<String, String> findAdminByAgencyId(int agencyId) {

        String sql = "select user2agency.user_id from user2agency where agency_id= " + agencyId;
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> list1 = new HashMap<String, String>();
        list = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        int user_id = Integer.parseInt(list.get(0).get("user_id"));
        String sql1 = "select u.user_type from user as u where u.user_id= " + user_id;
        list1 = JdbcTemplateUtil.getJdbcTemplate().find(sql1);
        int user_type = Integer.parseInt(list1.get("user_type"));
        String sql2 = "select ut.id,ut.type_name  from user_type as ut where ut.id = " + user_type;
        return JdbcTemplateUtil.getJdbcTemplate().find(sql2);

    }

    /**
     * 20160420
     * 根据机构id查询他的一级下属机构
     *
     * @param agencyId 机构id
     * @return 结果集
     */
    public final List<Map<String, String>> findSonLowerLevelAgency(int agencyId) {
        String sql = "select agency_id as agencyId,agency_name as agencyName,p_id as pId,agency_level as agencyLevel from agency as a where a.p_id =  " + agencyId;
        return JdbcTemplateUtil.getJdbcTemplate().findList(sql);
    }

    /**
     * 根据机构id获取他的上一级机构
     *
     * @param agencyId
     * @return
     */
    public final Map<String, String> getParentAgency(int agencyId) {
        String sql = "select * from agency where agency_id=(SELECT agency.p_id from agency where agency_id =" + agencyId + ")";
        return JdbcTemplateUtil.getJdbcTemplate().find(sql);
    }


    /**
     * 根据机构多个id查询相应的多个机构信息
     *
     * @param agencyIds
     * @return
     */
    public final List<Map<String, String>> getAgencys(String agencyIds) {
        List<Map<String, String>> list = new ArrayList<>();
        String[] agencyId = agencyIds.split(",");
        for (int i = 0; i < agencyId.length; i++) {
            if (agencyId[i] != null && !"".equals(agencyId[i])) {
                String sql = "select agency_id as agencyId,agency_name as agencyName,p_id as pId,agency_level as agencyLevel from agency as a where a.agency_id = " + agencyId[i];
                list.add(JdbcTemplateUtil.getJdbcTemplate().find(sql));
            }
        }
        return list;
    }

    /**
     * 获取第一级管理员
     *
     * @return
     */
    public final Map<String, String> getFirstAdmin() {
        Map<String, String> map = new HashMap<>();
        List<Map<String, String>> list = new ArrayList<>();
        String sql = "select distinct ut.id,ut.type_name,u.user_id as userId from user_type ut LEFT JOIN `user` u on ut.id = u.user_type " +
                "LEFT JOIN user2agency ua on  ua.user_id=u.user_id LEFT JOIN agency a on " +
                "ua.agency_id = a.agency_id where a.p_id =0 ORDER BY u.user_id";
        list = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        for (int i = 0; i < list.size(); i++) {
            if ("1".equals(list.get(i).get("id"))) {
                return list.get(i);
            }
        }
        for (int i = 0; i < list.size(); i++) {
            if ("2".equals(list.get(i).get("id"))) {
                return list.get(i);
            }
        }
        for (int i = 0; i < list.size(); i++) {
            if ("3".equals(list.get(i).get("id"))) {
                return list.get(i);
            }
        }
        return map;
    }

    public int getAgencyLevel(int agencyId,int count){
        count++;
        String sql = "select p_id from agency where agency_id=" + agencyId;
        Map<String,String> map = JdbcTemplateUtil.getJdbcTemplate().find(sql);
        int pId = Integer.parseInt(map.get("p_id"));
        if(pId == 0){
            return count;
        }else{
            return getAgencyLevel(pId,count);
        }
    }
}


