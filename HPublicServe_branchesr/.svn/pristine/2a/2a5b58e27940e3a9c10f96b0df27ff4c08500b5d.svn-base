package com.honghe.area.dao.techColumnDao;

import com.honghe.ad.util.JdbcTemplateUtil;
import com.honghe.area.dao.BasicDao;
import com.honghe.area.reflect.ParameterValue;

import java.util.List;
import java.util.Map;

/**
 *
 * @author lichong
 * @date 2017/3/15
 */
public class TechColumnDao extends BasicDao {
    /**
     * 获取学段的列表
     * @return list
     */
    public List<Map<String,String>> list(){
        String sql = "SELECT tc_id AS id,tc_name AS name,tc_school_system AS schoolSystem,tc_status AS status,tc_number AS number,tc_desc AS remark " +
                "FROM res_techcolumn ORDER BY tc_number ASC";
        return JdbcTemplateUtil.getJdbcTemplate().findList(sql);
    }

    /**
     * 根据id获取学段的信息
     * @return list
     */
    public List<Map<String,String>> getbyid(@ParameterValue("id")String id){
        String sql = "SELECT tc_id AS id,tc_name AS name,tc_school_system AS schoolSystem,tc_status AS status,tc_number AS number,tc_desc AS remark " +
                "FROM res_techcolumn WHERE tc_id="+id;
        return JdbcTemplateUtil.getJdbcTemplate().findList(sql);
    }

    /**
     *添加学段
     * @param name   学段名称
     * @param schoolSystem   学制
     * @param status  状态，0-停用，1-启用
     * @param number 排序编码
     * @param remark  备注
     * @return
     */
    public boolean add(@ParameterValue("name")String name,@ParameterValue("schoolSystem")String schoolSystem,@ParameterValue("status")String status,
                       @ParameterValue("number")String number,@ParameterValue("remark")String remark){
        if("".equals(number)){
            number = null;
        }
        if(!repeat(name,"")) {
            String sql = "INSERT INTO res_techcolumn(tc_name,tc_school_system,tc_status,tc_number,tc_desc)VALUES('" + name + "','" + schoolSystem + "'," + status + "," + number + ",'" + remark + "')";
            long lg = JdbcTemplateUtil.getJdbcTemplate().add(sql);
            if (lg > 0) {
                return true;
            } else {
                return false;
            }
        }else {
            return false;
        }
    }

    /**
     *修改学段信息
     * @param id   学段id
     * @param name   学段名称
     * @param schoolSystem   学制
     * @param status  状态，0-停用，1-启用
     * @param number 排序编码
     * @param remark  备注
     * @return
     */
    public boolean update(@ParameterValue("id")String id,@ParameterValue("name")String name,@ParameterValue("schoolSystem")String schoolSystem,@ParameterValue("status")String status,
                       @ParameterValue("number")String number,@ParameterValue("remark")String remark){
        if("".equals(number)){
            number = null;
        }
        if(!repeat(name," AND tc_id <>"+id)) {
            String sql = "UPDATE  res_techcolumn SET tc_name='" + name + "',tc_school_system='" + schoolSystem + "',tc_status=" + status + ",tc_number=" + number + ",tc_desc='" + remark + "' WHERE tc_id=" + id;
            return JdbcTemplateUtil.getJdbcTemplate().update(sql);
        }else {
            return false;
        }
    }

    /**
     * 删除学段
     * @param id
     * @return
     */
    public boolean delete(@ParameterValue("id")String id){
        String sql = "DELETE FROM res_techcolumn WHERE tc_id="+id;
        return JdbcTemplateUtil.getJdbcTemplate().delete(sql);
    }

    /**
     * 查重处理
     * @param name  名称
     * @param sqlstr  sql语句
     */
    public boolean repeat(String name,String sqlstr){
        String sql = "SELECT * FROM res_techcolumn  WHERE name = '"+name+"' "+sqlstr;
        List<Map<String,String>> list = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        if(list.size()>0){
            return true;
        }else {
            return false;
        }
    }
}
