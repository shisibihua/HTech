package com.honghe.area.dao.areaTypeDao;

import com.honghe.ad.util.JdbcTemplateUtil;
import com.honghe.area.dao.BasicDao;
import com.honghe.area.reflect.ParameterValue;
import com.honghe.area.util.AreaTypeUtil;

import java.util.List;
import java.util.Map;

/**
 *
 * @author LC
 * @date 2017/3/8
 */
public class AreaTypeDao extends BasicDao {
    /**
     * 根据地点类型id获取地点类型的数据
     * @param id 地点类型id
     * @return
     */
    public List<Map<String,String>> getbyid(@ParameterValue("id") String id){
        String sql = "SELECT * FROM ad_area_type WHERE id = "+id;
        return JdbcTemplateUtil.getJdbcTemplate().findList(sql);
    }

    /**
     * 获取教室类型的数据
     * @return
     */
    public List<Map<String,String>> list(){
//        String sql = "SELECT id,name FROM ad_area_type WHERE p_id <> 0 and id <> 35";
        String sql = "SELECT id,name FROM ad_area_type WHERE p_id <> 0 and id <> "+AreaTypeUtil.AREATYPE_DEFAULT;
        return JdbcTemplateUtil.getJdbcTemplate().findList(sql);
    }

    /**
     * 添加地点类型
     * @param name  地点类型名称
     * @return
     */
    public boolean add(@ParameterValue("name")String name, @ParameterValue("type")String type){
        boolean reValue;
        if(!repeat(name,"")) {
            List<Map<String, String>> list = JdbcTemplateUtil.getJdbcTemplate().findList("SELECT id FROM ad_area_type WHERE name = '" + type + "'AND p_id = 0 ");
            String id = list.get(0).get("id");

            String sql = "INSERT INTO ad_area_type(name,p_id) VALUES('" + name + "'," + id + ")";
            long lg = JdbcTemplateUtil.getJdbcTemplate().add(sql);
            if (lg > 0) {
                reValue = true;
            }else{
                reValue = false;
            }
        }else{
            reValue = false;
        }
        return reValue;
    }

    /**
     * 删除地点类型(教室类型)
     * @param id  地点类型的id
     * @return
     */
    public boolean delete(@ParameterValue("id")String id){
        boolean reValue;
        String sql = "delete from ad_area_type where id="+id;
        boolean flag = JdbcTemplateUtil.getJdbcTemplate().delete(sql);
        if (flag){
            JdbcTemplateUtil.getJdbcTemplate().update("update ad_area set roomtype_id="+ AreaTypeUtil.AREATYPE_DEFAULT+" where roomtype_id="+id);
            reValue = true;
        }else{
            reValue = false;
        }
        return reValue;
    }

    /**
     * 编辑地点类型
     * @param id  地点类型id
     * @param name  地点类型名称
     * @return
     */
    public boolean update(@ParameterValue("id")String id, @ParameterValue("name")String name){
        if(!repeat(name," AND id <>"+id)) {
            String sql = "UPDATE ad_area_type SET name = '" + name + "' WHERE id = " + id;
            return JdbcTemplateUtil.getJdbcTemplate().delete(sql);
        }else {
            return false;
        }
    }

    /**
     * 查重处理
     * @param name  名称
     * @param sqlstr  sql语句
     */
    public boolean repeat(String name,String sqlstr){
        String sql = "SELECT * FROM ad_area_type  WHERE name = '"+name+"' "+sqlstr;
        List<Map<String,String>> list = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        if(list.size()>0){
            return true;
        }else {
            return false;
        }
    }

}
