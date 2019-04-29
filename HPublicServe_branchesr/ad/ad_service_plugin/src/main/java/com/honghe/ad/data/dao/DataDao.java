package com.honghe.ad.data.dao;

import com.honghe.ad.util.JdbcTemplateUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by yuk on 2016/11/30.
 */
public class DataDao {

    private DataDao() {

    }

    public final static DataDao INSTANCE = new DataDao();

    public final List<Map<String, String>> getAreaTypes(String p_id) {
        if (null == p_id || "".equals(p_id)) {
            p_id = "0";
        }
        String sql = "select id,name from ad_area_type where p_id ='" + p_id + "' order by number asc";
        return JdbcTemplateUtil.getJdbcTemplate().findList(sql);
    }

    public final List<Map<String, String>> getStages() {
        String sql = "select tc_id as id ,tc_name as name from res_techcolumn order by tc_id asc";
        return JdbcTemplateUtil.getJdbcTemplate().findList(sql);
    }

    public final List<Map<String, String>> getCampusTypes(String level) {
        String sql = "select id,name from ad_campus_type where level BETWEEN "+level+" AND 4";
        return JdbcTemplateUtil.getJdbcTemplate().findList(sql);
    }

    public final List<Map<String, String>> getSchoolZone() {
        String sql = "select id,name from ad_school_zone ";
        return JdbcTemplateUtil.getJdbcTemplate().findList(sql);
    }
}
