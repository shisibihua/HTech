package com.honghe.ad.campus.dao;

import com.honghe.ad.Directory;
import com.honghe.ad.campus.bean.UserInfo;
import com.honghe.ad.excetion.ParamException;
import com.honghe.ad.util.*;
import com.honghe.area.dao.areaDao.AreaDao;

import java.util.*;


/**
 * Created by zhanghongbin on 2016/7/13.
 */
public final class CampusDao {

    private CampusDao() {

    }

    public final static CampusDao INSTANCE = new CampusDao();
    public final static int CAMPUS_SCHOOL = 3;

    public final String add(Map<String, Object> data) throws Exception {
        String name = data.get("name").toString();
        String pId = data.get("pId").toString();
        try {
            Object number = null;
            if (data.containsKey("number") && data.get("number") != null && !"".equals(data.get("number"))) {
                number = Integer.parseInt(data.get("number").toString());
            }
            Object typeId = null;
            if (data.containsKey("typeId") && data.get("typeId") != null && !"".equals(data.get("typeId"))) {
                typeId = Integer.parseInt(data.get("typeId").toString());
            }
            Object stagesId = null;
            if (data.containsKey("stagesId") && data.get("stagesId") != null && !"".equals(data.get("stagesId"))) {
                stagesId = Integer.parseInt(data.get("stagesId").toString());
            }
            Object schoolYear = null;
            if (data.containsKey("schoolYear") && data.get("schoolYear") != null && !"".equals(data.get("schoolYear"))) {
                schoolYear = Integer.parseInt(data.get("schoolYear").toString());
            }

            Object provinceId = null;
            if (data.containsKey("provinceId") && data.get("provinceId") != null && !"".equals(data.get("provinceId"))) {
                provinceId = Integer.parseInt(data.get("provinceId").toString());
            }

            Object cityId = null;
            if (data.containsKey("cityId") && data.get("cityId") != null && !"".equals(data.get("cityId"))) {
                cityId = Integer.parseInt(data.get("cityId").toString());
            }

            Object areaId = null;
            if (data.containsKey("areaId") && data.get("areaId") != null && !"".equals(data.get("areaId"))) {
                areaId = Integer.parseInt(data.get("areaId").toString());
            }

            String url = data.get("url").toString();
            String remark = data.get("remark").toString();
            //使用计算哈希值的方法计算ID
            //String id = String.valueOf(HashUtil.toHash(name));
            //下面使用编号的方法计算ID
            String id="";
            if(null!=pId && "0".equals(pId)){
                id=ExcelUtil.CAMPUS_ROOT_ID;
            }else {
                id=HashUtil.calculateId(pId);
            }
            String sql = "insert into ad_campus (id,name,p_id,number,type_id,stages_id,schoolyear,remark,url, hatprovince_id, hatcity_id, hatarea_id)" +
                    "values('"+id+"', '"+name+"', '"+pId+"', "+number+", "+typeId+", "+stagesId+", "+schoolYear+", '"+remark+"', '"+url+"', "+provinceId+", "+cityId+", "+areaId+")";
            System.out.println(sql);
            if (JdbcTemplateUtil.getJdbcTemplate().add(sql) != 0) {
                return id;
            } else {
                return "-1";
            }
        } catch (ClassCastException e) {
            throw new ParamException();
        }
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
        String sql = "select * from ad_campus where p_id = '" + pId.trim() + "' and name = '" + name.trim() + "'";
        if (data.containsKey("id")) {
            String id = data.get("id").toString();
            sql += " and id <> '" + id.trim() + "'";
        }
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        return result.size() > 0 ? false : true;
    }

    public final boolean delete(String... id) {
        StringBuilder sb = new StringBuilder();
        for (String _id : id) {
            sb.append("'" + _id + "',");
        }
        String where = sb.substring(0, sb.length() - 1);
        String campus2DataDeleteSql = "delete from ad_campus2user where campus_id in (" + where + ") and user_id!=1";
        String campusDeleteDql = "delete from ad_campus where id in (" + where + ")";
        return JdbcTemplateUtil.getJdbcTemplate().execute(campus2DataDeleteSql, campusDeleteDql);
    }

    public final List getUserCountByCampus(String campusId) {
        String sql = "select cu.user_id,u.user_realname from ad_campus2user cu,user u where cu.user_id = u.user_id and " +
                "cu.campus_id = '"+campusId+"' and cu.id!=1";
        return JdbcTemplateUtil.getJdbcTemplate().findList(sql);
    }
    public final boolean update(Map<String, Object> data) {
        StringBuffer sb = new StringBuffer("update ad_campus set id=id ");
        if (data.containsKey("name")) {
            sb.append(", name ='" + data.get("name").toString().trim() + "'");
        }
        if (data.containsKey("number")) {
            Integer number = null;
            if (data.get("number") != null && !"".equals(data.get("number"))){
                number = Integer.parseInt(data.get("number").toString());
            }
            sb.append(", number =" + number);
        }
        if (data.containsKey("typeId") && data.get("typeId") != null && !"".equals(data.get("typeId"))) {
            Integer typeId = Integer.parseInt(data.get("typeId").toString());
            sb.append(", type_id =" + typeId);
        }
        if (data.containsKey("stagesId") && data.get("stagesId") != null && !"".equals(data.get("stagesId"))) {
            Integer stagesId = Integer.parseInt(data.get("stagesId").toString());
            sb.append(", stages_id =" + stagesId);
        }
        if (data.containsKey("schoolYear")) {
            Integer schoolYear = null;
            if (data.get("schoolYear") != null && !"".equals(data.get("schoolYear"))){
                schoolYear = Integer.parseInt(data.get("schoolYear").toString());
            }
            sb.append(", schoolYear =" + schoolYear);
        }
        if (data.containsKey("remark")) {
            sb.append(", remark ='" + data.get("remark").toString().trim() + "' ");
        }
        String url = data.get("url").toString().trim();
        sb.append(",url ='"+url+"' ");
        sb.append("where id='" + data.get("id").toString().trim() + "'");

        return JdbcTemplateUtil.getJdbcTemplate().update(sb.toString());
    }

    /**
     * 获取指定机构的机构树
     * @param campusIds
     * @return
     */
    public final Directory findPartCampus(String campusIds){
        List<Map<String, String>> result = new ArrayList<>();
        String sql = "select a.id,a.name,a.p_id as pId,a.number,a.type_id as typeId," +
                "a.stages_id as stagesId,a.schoolyear,a.remark,a.url,t.name as typeName,t.level " +
                "from ad_campus a,ad_campus_type t where a.type_id=t.id ";
        String campusStr = "";
        String[] campusIdStr = campusIds.split(",");
        if (!"".equals(campusIds)&&campusIdStr.length>0){
            for (int i=0;i<campusIdStr.length;i++){
                campusStr += "'"+campusIdStr[i]+"',";
            }
            if (!"".equals(campusStr)&&campusStr.endsWith(",")){
                campusStr = campusStr.substring(0,campusStr.lastIndexOf(","));
            }
            sql+=" and a.id in ("+campusStr+")";
        }
        sql+=" order by a.id ";
        result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        Directory rootDirectory = new Directory("", "", "", "", "", "", "", "", "", "", "");
        recursiveOrganization(rootDirectory, result, "1");
        return rootDirectory;
    }
    public final Directory find() {

        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList("select a.id,a.name,a.p_id as pId,a.number," +
                "a.type_id as typeId,a.stages_id as stagesId,a.schoolyear,a.remark,a.url,t.name as typeName,t.level from ad_campus a,ad_campus_type t where a.type_id=t.id order by a.id ");
        Directory rootDirectory = null;
        for (Map<String, String> _r : result) {
            if (_r.get("id").equals(ExcelUtil.CAMPUS_ROOT_ID)) {
                rootDirectory = new Directory("1", _r.get("name"), _r.get("number"), _r.get("typeId"), _r.get("typeName"), _r.get("stagesId"), _r.get("schoolYear"), _r.get("remark"), _r.get("level"), _r.get("pId"), "");
                break;
            }
        }
        if (rootDirectory == null) {
            return new Directory("1", "", "", "", "", "", "", "", "", "", "");
        }
        recursiveOrganization(rootDirectory, result, ExcelUtil.CAMPUS_ROOT_ID);
        return rootDirectory;
    }

    public final Directory findByCampusType(String campusType) {
        String sql = "select c.id as id,c.`name`,c.p_id as pId,c.number,c.type_id as typeId,c.stages_id as stagesId,c.schoolyear as schoolYear,c.remark,ct.name as typeName," +
                "u.user_id as userId, u.user_name as userName,u.user_type as userType,u.user_realname as userRealName,u.user_path as userPath,u.user_avatar as userAvatar," +
                "u.user_gender as userGender,u.user_birthday as userBirthday,u.user_mobile as userMobile,u.user_email as userEmail," +
                "u.user_address as userAddress,u.user_num as userNum,u.user_regdate as userRegdate,u.user_status as userStatus," +
                "u.user_card as userCard,u.user_course as userCourse,u.user_info as userInfo " +
                "from ad_campus c left join ad_campus2user ac on c.id=ac.campus_id LEFT JOIN `user` u on ac.user_id=u.user_id LEFT JOIN ad_campus_type ct on ct.id=c.type_id " +
                "where ct.id in (select id from ad_campus_type where level < (select level from ad_campus_type where name='" + campusType + "')) or ct.name='" + campusType + "'";

        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        Directory rootDirectory = null;
        for (Map<String, String> _r : result) {
            if (_r.get("id").equals("1")) {
                rootDirectory = new Directory("1", _r.get("name"), _r.get("number"), _r.get("typeId"), _r.get("typeName"), _r.get("stagesId"), _r.get("schoolYear"), _r.get("remark"), _r.get("level"), _r.get("pId"), "");
                break;
            }
        }
        if (rootDirectory == null) {
            return new Directory("1", "", "", "", "", "", "", "", "", "", "");
        }
        recursiveOrganization(rootDirectory, result, "1");
        return rootDirectory;
    }

    public final List find(int userId) {
        String sql = "select a.id as id,a.name,a.p_id as pId,a.number,a.type_id as typeId,a.stages_id as stagesId,a.schoolyear,a.remark,a.url,t.name as typeName,t.level " +
                "from ad_campus a,ad_campus_type t where a.type_id=t.id and a.id in (SELECT campus_id FROM ad_campus2user where user_id=" + userId + ")";
        List result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        if (result.isEmpty()) {
            return null;
        }
        return result;
    }

    public final List<Map<String, String>> findByCampusId(String campusId) {
        String sql = "select a.id,a.name,a.p_id as pId,a.number,a.type_id as typeId,a.stages_id as stagesId," +
                "a.schoolyear,a.remark,a.url,t.name as typename,t.level,s.`name` as stagename " +
                "FROM ad_campus a LEFT JOIN ad_campus_type t ON a.type_id = t.id " +
                "LEFT JOIN ad_stages s ON a.stages_id = s.id where a.id ";
        StringBuffer sb = new StringBuffer();
        if (campusId.contains(",")) {
            String[] temp = campusId.split(",");
            for (String s : temp) {
                sb.append("'" + s + "',");
            }
            String ids = sb.toString();
            ids = ids.substring(0, ids.length() - 1);
            sql = sql + " in(" + ids + ")";
        } else {
            sql = sql + "='" + campusId + "'";
        }
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        if (result.isEmpty()) {
            return null;
        }
        return result;
    }

    public final Directory find(String userId, String userType) {

        Directory directory = new Directory();
        if(userId.equals("1"))
        {
            directory = INSTANCE.find();
            return directory;
        }
        else {
            String sql = "SELECT campus_id FROM ad_campus2user where user_id=" + userId.trim();
            List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
            if(result.size()!=0) {
                directory = Campus2UserDao.INSTANCE.campusChildSearch(result.get(0).get("campus_id"));
                return directory;
            }
            else
            {
                return directory;
            }
        }
    }

    private final Directory find(List<Map<String, String>> result, Set<String> campusIdSet, Directory rootDirectory) {

        //如果存在根节点,直接进行递归
        if (campusIdSet.contains(rootDirectory.getId())) {
            recursiveOrganization(rootDirectory, result, rootDirectory.getId());
        } else {
            for (Map<String, String> _result : result) {
                if (_result == null) {
                    continue;
                }
                String id = _result.get("id");
                if (campusIdSet.contains(id)) {
                    //a.id as id,a.name,a.p_id as pId,a.number,a.type_id as typeId,a.stages_id as agesId,a.schoolyear,a.remark,t.name as typeName,t.level
                    Directory dd = new Directory(id, _result.get("name"), _result.get("number"), _result.get("typeId"), _result.get("typeName"), _result.get("stagesId"), _result.get("schoolyear"), _result.get("remark"), _result.get("level"), _result.get("pId"), _result.get("count"));
                    rootDirectory.getDirectories().add(dd);
                    recursiveOrganization(dd, result, id);
                }
            }
        }
        return rootDirectory;
    }

    private void recursiveOrganization(Directory directory, List<Map<String, String>> result, String id) {
        for (int i = 0; i < result.size(); i++) {
            Map<String, String> _result = result.get(i);
            if (_result != null) {
                String _id = _result.get("id");
                if (_id.equals(id)) {
                    if (_result.containsKey("userId") && !"".equals(_result.get("userId"))) {
                        directory.addUser(convertUserInfo(_result));
                    }
                    result.set(i, null);
                    continue;
                }
                String pId = _result.get("pId");
                if (pId.equals(id)) {
                    _id = _result.get("id");
                    String count = "";
                    if (_result.containsKey("count")) {
                        count = _result.get("count").toString();
                    }
                    Directory dd = new Directory(_result.get("id"), _result.get("name"), _result.get("number"), _result.get("typeId"), _result.get("typeName"), _result.get("stagesId"), _result.get("schoolYear"), _result.get("remark"), _result.get("level"), _result.get("pId"), count);
                    directory.getDirectories().add(dd);
                    recursiveOrganization(dd, result, _id);
                }
            }
        }
    }

    /**
     * 查询当前等级下所有数据
     *
     * @param searchWord
     * @return
     */
    public List findByWord(char searchWord) {
        return JdbcTemplateUtil.getJdbcTemplate().findList("select a.id as campusId,a.name as campusName,a.p_id as pId,a.number,a.type_id as typeId,a.stages_id as stagesId,a.schoolyear,a.remark,t.name as typeName,t.level " +
                "from ad_campus a,ad_campus_type t where a.type_id=t.id and  a.id like '" + searchWord + "%' order by a.id desc");
    }

    /**
     * 查询所有机构数据
     *
     * @return
     */
    public List findAll() {
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList("select a.id as campusId,a.name as campusName,a.p_id as pId,a.number,a.type_id as typeId,a.stages_id as stagesId,a.schoolyear,a.remark,t.name as typeName,t.level " +
                "from ad_campus a,ad_campus_type t where a.type_id=t.id order by a.id desc");
        return result;
    }

    public boolean addImport(List<Map<String, String>> addList) {
        try {
            boolean isSave=false;
            AreaDao areaDao=new AreaDao();
            for (Map<String, String> data : addList) {
                String name = data.get("name").toString();
                String pId = data.get("pId").toString();
                String id = data.get("id").toString();
                String number = data.get("number").toString();
                String typeId = data.get("typeId").toString();
                String stagesId = data.get("stagesId").toString();
                String schoolYear = data.get("schoolYear").toString();
                String remark = data.get("remark").toString();
                String url = data.get("url").toString();
                //若导入时填写了根目录 则修改之前根目录属性 且以最后一条根目录信息为准
                if (ExcelUtil.CAMPUS_ROOT_ID.equals(id)) {
                    String sql = "update ad_campus set name='" + name.trim() + "',number='" + number.trim() + "',type_id='" + typeId.trim() +
                            "',stages_id='" + stagesId.trim() + "',schoolyear='" + schoolYear.trim() + "',remark='" + remark.trim() +"',url='"
                            + url.trim() + "' where id='"+ExcelUtil.CAMPUS_ROOT_ID+"'";
                    JdbcTemplateUtil.getJdbcTemplate().update(sql);
                    areaDao.syntreeupdate(name.trim(),id);  //修改地点信息
                } else {
                    String sql = "insert into ad_campus (id,name,p_id,number,type_id,stages_id,schoolyear,remark,url)values(?,?,?,?,?,?,?,?,?)";
                    JdbcTemplateUtil.getJdbcTemplate().add(sql, new Object[]{id, name, pId, number, typeId, stagesId, schoolYear, remark,url});
                    isSave=true;
                }
            }
            if(isSave){
                areaDao.syntreeimport(addList);  //批量同步地点，与组织机构数据同步
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private UserInfo convertUserInfo(Map<String, String> map) {

        UserInfo user = new UserInfo();

        user.setUserAddress(map.get("userAddress").toString());
        user.setUserAvatar(map.get("userAvatar").toString());
        user.setUserBirthday(map.get("userBirthday").toString());
        user.setUserCard(map.get("userCard").toString());
        user.setUserCourse(map.get("userCourse").toString());
        user.setUserEmail(map.get("userEmail").toString());
        user.setUserGender(map.get("userGender").toString());
        user.setUserId(map.get("userId").toString());
        user.setUserInfo(map.get("userInfo").toString());
        user.setUserName(map.get("userName").toString());
        user.setUserNum(map.get("userNum").toString());
        user.setUserPath(map.get("userPath").toString());
        user.setUserRealName(map.get("userRealName").toString());
        user.setUserRegdate(map.get("userRegdate").toString());
        user.setUserStatus(map.get("userStatus").toString());
        user.setUserType(map.get("userType").toString());
        user.setUserMobile(map.get("userMobile").toString());

        return user;
    }

    public List<Map<String, String>> getChildListByCampusId(String campusId) {
        String sql = "select a.id as campusId,a.name as campusName,a.url,a.p_id as pId,a.number,a.type_id as typeId,a.stages_id as stagesId,a.schoolyear,a.remark" +
                " from ad_campus a where a.p_id= '"+campusId+"'";
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        return result;
    }

    /**
     * 查询组织机构中学校的组织信息
     * @return
     */
    public List<Map<String, String>> getSchoolList(){
        String sql = "select * from ad_campus where type_id ="+CAMPUS_SCHOOL;
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        return result;
    }


    // TODO: 2018/3/2 新增区域化用户服务 add mz ************************************start******************************************

    public List<Map<String, String>> getProvinceList(){
        final String sql = "select * from hat_province";
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        return result;
    }

    public List<Map<String, String>> getCityList(String provinceId){
        final String sql = "select * from hat_city where p_id = (select provinceID from hat_province where id = " + provinceId + ")";
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        return result;
    }

    public List<Map<String, String>> getCountyList(String cityId){
        final String sql = "select * from hat_area where p_id = (select cityID from hat_city where id = " + cityId + ")";
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        return result;
    }

    // 根据组织机构id获取对应省/市/区列表
    public Map<String, String> getAreaList(String campusId){
//        final String sql = "SELECT id, name, hatarea_id, hatcity_id, hatprovince_id from ad_campus WHERE id='"+campusId+"'";
        final String sql = "SELECT ac.id, ac.name, hp.id as provinceID, hp.province, hc.id AS cityID, hc.city, ha.id AS areaID, ha.area\n" +
                "FROM ad_campus ac \n" +
                "LEFT JOIN hat_province hp ON ac.hatprovince_id=hp.id\n" +
                "LEFT JOIN hat_city hc ON ac.hatcity_id=hc.id\n" +
                "LEFT JOIN hat_area ha ON ac.hatarea_id=ha.id\n" +
                "WHERE ac.id='"+campusId+"'";
        return JdbcTemplateUtil.getJdbcTemplate().find(sql);
    }

     /**
     * 根据用户id获取对应的组织结构
     */
    public Map<String, String> findCampusByUserId (String userId)  {
        String sql = "";
        if(userId!=null && !"1".equals(userId)){
            sql+="SELECT adu.user_id, adc.id, adc.name,adc.p_id,adc.type_id,adc.stages_id FROM ad_campus2user adu LEFT JOIN ad_campus adc ON adu.campus_id=adc.id WHERE user_id=" + userId;
        }else if(userId!=null && "1".equals(userId)){
            sql+="SELECT * from ad_campus where p_id='0'";
        }
        return JdbcTemplateUtil.getJdbcTemplate().find(sql);
    }

    /**
     * 获取全部的组织机构列表
     * @return
     */
    public Set<Node> findCampus() {
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList("select a.id,a.name,a.p_id as pId,a.number," +
                "a.type_id as typeId,a.stages_id as stagesId,a.schoolyear,a.remark,a.url,a.hatarea_id, a.hatcity_id, a.hatprovince_id, t.name as typeName,t.level from ad_campus a,ad_campus_type t where a.type_id=t.id order by a.id ");
        return MultipleTree.getTreeNode(result);
    }

    /**
     * 根据组织机构父节点获取对应的子节点列表
     * @param pid
     * @return
     */
    public Set<Node> findChildCampus(String pid) {
        Set<Node> nodeSet = new LinkedHashSet<>();
        final String sql = "select * from ad_campus where p_id= '"+pid+"'";
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        if (result != null) {
            for (int i=0; i<result.size(); i++) {
                Node node = new Node();
                node.setId(result.get(i).get("id"));
                node.setName(result.get(i).get("name"));
                node.setParentId(result.get(i).get("p_id"));
                node.setTypeId(result.get(i).get("type_id"));
                node.setStageId(result.get(i).get("stages_id"));
                nodeSet.add(node);
                Set<Node> nodes = findChildCampus(result.get(i).get("id"));
                for (Node node1 : nodes) {
                    node.addChild(node1);
                }
            }
        }
        return nodeSet;
    }

    /**
     * 根据组织id获取组织类型名称
     * @param id  组织id
     * @return
     * @throws ParamException
     * @author  caoqian
     */
    public Map<String,String> getCampusType (String id) throws ParamException {
        if(id==null || "".equals(id)){
            throw new ParamException();
        }else{
            String sql="select t.id,t.name,t.level from ad_campus c left join ad_campus_type t on c.type_id=t.id where c.id='"+id+"'";
            return JdbcTemplateUtil.getJdbcTemplate().find(sql);
        }
    }

    /**
     * 获取父级组织机构信息（pid=0）
     * @return
     * @throws ParamException
     * @author  caoqian
     */
    public Map<String,String> findCampusFather() throws ParamException {
        String sql="select t.id,t.name,t.level from ad_campus c left join ad_campus_type t on c.type_id=t.id where c.p_id=0";
        return JdbcTemplateUtil.getJdbcTemplate().find(sql);
    }

    /**
     * 查询根目录组织机构所在的行政区域信息
     * @return
     * @author caoqian
     */
    public Map<String, String> findCampusRoot() {
        final String sql="select camp.id as rootCampusId,camp.name as rootCampusName," +
                "camp.hatprovince_id as provinceId,prov.province as provinceName," +
                "camp.hatcity_id as cityId,city.city as cityName," +
                "camp.hatarea_id as areaId,area.area as areaName," +
                "camp.type_id as typeId " +
                "from ad_campus camp " +
                "left join ad_campus_type type on type.id=camp.type_id " +
                "left join hat_province prov on prov.id=camp.hatprovince_id " +
                "left join hat_city city on city.id=camp.hatcity_id " +
                "left join hat_area area on area.id=camp.hatarea_id " +
                "where camp.p_id='0'";
        System.out.println(sql);
        Map<String, String> result = JdbcTemplateUtil.getJdbcTemplate().find(sql);
        return result;
    }

    /**
     * 根据省/市/区类型获取省/市/区数据
     * @param hatType       省/市/区类型,省:province;市：city;区：area
     * @param hatId         省/市/区id
     * @return
     */
    public Map<String,String> findHat(String hatType,String hatId){
        Map<String, String> result=new HashMap<>();
        String sql="select ";
        switch (hatType){
            case "province":
                  sql+="id,provinceID as hatId,province as hatName from hat_province";
                  break;
            case "city":
                  sql+="id,cityID as hatId,city as hatName,p_Id as pId from hat_city";
                  break;
            case "area":
                  sql+="id,areaID as hatId,area as hatName,p_id as pId from hat_area";
                  break;
            default:
                  sql="";
        }
        if(!"".equals(sql)){
            sql+=" where id='"+hatId+"'";
        }
        return JdbcTemplateUtil.getJdbcTemplate().find(sql);
    }
    /**
     * 根据省/市/区id获取组织机构数据
     * @param hatType       省/市/区类型,省:province;市：city;区：area
     * @param hatId         省/市/区id
     * @return
     */
    public Map<String,String> findCampusByHatId(String hatType, String hatId) {
        Map<String, String> result=new HashMap<>();
        String sql="select camp.id,camp.name,camp.p_id as pId,camp.type_id as typeId,camp.stages_id as stageId," +
                "camp.hatprovince_id as hatProvinceId,camp.hatcity_id as hatCityId,camp.hatarea_id as areaId " +
                "from ad_campus camp " +
                "left join ad_campus_type t on camp.type_id=t.id " +
                "where ";
        switch (hatType){
            case "province":
                sql+="camp.hatprovince_id="+hatId+" and camp.type_id=1";
                break;
            case "city":
                sql+="camp.hatcity_id="+hatId+" and camp.type_id=2";
                break;
            case "area":
                sql+="camp.hatarea_id="+hatId+" and camp.type_id=3";
                break;
            default:
                sql="";
        }
        return JdbcTemplateUtil.getJdbcTemplate().find(sql);
    }
}
