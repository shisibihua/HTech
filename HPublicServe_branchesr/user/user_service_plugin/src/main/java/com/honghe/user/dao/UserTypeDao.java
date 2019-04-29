package com.honghe.user.dao;

import com.honghe.user.util.JdbcTemplateUtil;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanghongbin on 2015/7/10.
 */
public  class UserTypeDao {

    public  List<Map<String, String>> find() {
         return JdbcTemplateUtil.getJdbcTemplate().findList("select id as typeId,type_name as typeName FROM user_type where id<>0 ");

    }

    public  Map<String,String> findByTypeName(String typeName){
        return JdbcTemplateUtil.getJdbcTemplate().find("select id as typeId,type_name as typeName FROM user_type where type_name = '"+typeName+"'");
    }


}
