package com.honghe.area.dao.schoolZoneDao;

import com.honghe.ad.util.JdbcTemplateUtil;
import com.honghe.area.dao.BasicDao;
import com.honghe.area.dao.tree.StrNode;
import com.honghe.area.reflect.ParameterValue;

import java.util.List;
import java.util.Map;

/**
 *
 * @author lichong
 * @date 2017/3/14
 */
public class SchoolZoneDao extends BasicDao {
    /**
     * 删除成功
     */
    private static final String SUCCESS = "1";
    /**
     * 删除失败
     */
    private static final String ERROR = "2";
    /**
     * //该地点下存在子节点
     */
    private static final String SONAREA_EXISTTING = "3";
    /**
     * 获取校区的列表
     * @return
     */
    public List<Map<String,String>> list(){
        String sql = "SELECT * FROM ad_school_zone  ORDER BY id ASC";
        return JdbcTemplateUtil.getJdbcTemplate().findList(sql);
    }

    /**
     * 根据校区id获取校区的信息
     * @return
     */
    public List<Map<String,String>> getbyid(@ParameterValue("id")String id){
        String sql = "SELECT * FROM ad_school_zone  WHERE id="+id;
        return JdbcTemplateUtil.getJdbcTemplate().findList(sql);
    }

    /**
     * 添加校区的信息
     * @param name   校区名称
     * @param location    地址
     * @param telephone   电话
     * @param fax    传真
     * @param postcode    邮编
     * @param mapPoint    地点坐标（会管）
     * @return  long类型值
     */
    public boolean add(@ParameterValue("name")String name,@ParameterValue("location")String location,@ParameterValue("telephone")String telephone,
                    @ParameterValue("fax")String fax,@ParameterValue("map_point")String mapPoint,@ParameterValue("postcode")String postcode){
        //先判断名称是否重复
        if(!repeat(name,"")) {
            //获取学校在学校表里面所对应的数据
            List<Map<String, String>> schoolList = JdbcTemplateUtil.getJdbcTemplate().findList("SELECT id FROM ad_school");
            //取出学校id
            String schoolId = schoolList.get(0).get("id");
            //获取学校在地点表里面所对应的数据
            List<Map<String, String>> areaList = JdbcTemplateUtil.getJdbcTemplate().findList("SELECT id FROM ad_area WHERE p_id = 0");
            String pId = areaList.get(0).get("id");
            //取出学校id
            String sql = "INSERT INTO ad_school_zone(name,location,telephone,fax,postcode,school_id,map_point) VALUES('" + name + "','" +
                    location + "','" + telephone + "','" + fax + "','" + postcode + "','" + schoolId + "','" + mapPoint + "')";
            long lg = JdbcTemplateUtil.getJdbcTemplate().add(sql);
            if (lg > 0) {
                String sql2 = "INSERT INTO ad_area(name,p_id,type_id,zone_id)VALUES('" + name + "'," + pId + ",2," + lg + ")";
                long lg2 = JdbcTemplateUtil.getJdbcTemplate().add(sql2);
                if (lg2 > 0) {
                    return true;
                } else {
                    JdbcTemplateUtil.getJdbcTemplate().delete("DELETE FROM ad_school_zone WHERE id=" + lg);
                    return false;
                }
            } else {
                return false;
            }
        }else {
            return false;
        }
    }

    /**
     * 修改校区的信息
     * @param name   校区名称
     * @param location    地址
     * @param telephone   电话
     * @param fax    传真
     * @param mapPoint    地点坐标（会管）
     * @return  long类型值
     */
    public boolean update(@ParameterValue("id")String id,@ParameterValue("name")String name,@ParameterValue("location")String location,@ParameterValue("telephone")String telephone,
                    @ParameterValue("fax")String fax,@ParameterValue("postcode")String postcode,@ParameterValue("map_point")String mapPoint){
        //先判断名称是否重复
        if(!repeat(name," AND id <>"+id)) {
            //获取学校在学校表里面所对应的数据
            List<Map<String, String>> schoolList = JdbcTemplateUtil.getJdbcTemplate().findList("SELECT id FROM ad_school");
            //取出学校id
            String schoolId = schoolList.get(0).get("id");
            String sql = "UPDATE ad_school_zone SET name='" + name + "',location='" + location + "',telephone='" + telephone + "',fax='" + fax + "',postcode='" + postcode + "',school_id='" + schoolId + "',map_point='" + mapPoint + "' WHERE id = " + id;
            boolean bl = JdbcTemplateUtil.getJdbcTemplate().update(sql);
            //学校信息修改成功之后，同时在地点表中进行修改
            if (bl) {
                String sql2 = "UPDATE ad_area SET name='" + name + "' WHERE zone_id=" + id;
                return JdbcTemplateUtil.getJdbcTemplate().update(sql2);
            } else {
                return false;
            }
        }else {
            return false;
        }
    }

    /**
     * 删除校区
     * 同时删除该校区下面的所有地点
     * @param id  校区的id
     * @return
     */
    public String delete(@ParameterValue("id")String id){
        String reValue = "";
        String sql = "select count(*) from ad_area where p_id=(select aa.id from ad_area aa,ad_school_zone asz" +
                " where asz.id=aa.zone_id and asz.id="+id+")";
        long sonAreaCount = JdbcTemplateUtil.getJdbcTemplate().count(sql);
        if (sonAreaCount>0){
            reValue = SONAREA_EXISTTING;
        }else{
            boolean bl = JdbcTemplateUtil.getJdbcTemplate().delete("DELETE FROM ad_school_zone WHERE id="+id);
            //如果校区删除成功，则应该在树中把相应的校区删除
            if(bl){
                //根据校区的id获取校区在地点表里面所对应的数据
                List<Map<String,String>> list = JdbcTemplateUtil.getJdbcTemplate().findList("SELECT id FROM ad_area WHERE zone_id="+id);
                //取出id
                String aid = list.get(0).get("id");
                //获取该节点下面所有子节点的字符串
                String idStrs =  new StrNode().strNode(Integer.valueOf(aid));
                 JdbcTemplateUtil.getJdbcTemplate().delete("DELETE FROM ad_area WHERE id in "+idStrs);
                reValue = SUCCESS;
            }else {
                reValue =ERROR;
            }
        }
        return reValue;
    }

    /**
     * 查重处理
     * @param name  名称
     * @param sqlstr  sql语句
     */
    public boolean repeat(String name,String sqlstr){
        String sql = "SELECT * FROM ad_school_zone  WHERE name = '"+name+"' "+sqlstr;
        List<Map<String,String>> list = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        if(list.size()>0){
            return true;
        }else {
            return false;
        }
    }

}
