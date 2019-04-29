package com.honghe.communication.plugin;

import com.honghe.campus.CampusCommand;
import com.honghe.communication.main.CommunicationService;

import com.honghe.campus.user2sip.util.db.JDBCConnUtil;
import com.honghe.communication.ioc.Command;
//import com.honghe.dao.JdbcTemplateUtil;
import com.honghe.dao.JdbcTemplateUtil;
import com.honghe.dao.importer.SQLFileImporter;
import org.apache.commons.io.IOUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by zhanghongbin on 2016/9/28.
 * modify:赵健宇
 * getJdbcProperties修改
 * 更新exeucteOneUserSQLFile
 */
public class CampusCommandPlugin implements CommandPlugin {


    @Override
    public Map<String, Command> regist() {
        try {
            String content = IOUtils.toString(CampusCommandPlugin.class.getResourceAsStream("/campuscloud.sql"), "UTF-8");
            //JDBCConnUtil.exeucteOneUserSQLFile(CampusCommandPlugin.class, content);
            Properties jdbcProperties = JdbcTemplateUtil.getJdbcProperties("config/jdbc.properties");
            JdbcTemplateUtil.exeucteOneUserSQLFile(jdbcProperties.get("jdbc.url").toString(),
                    jdbcProperties.getProperty("jdbc.username").toString(),
                    jdbcProperties.get("jdbc.password").toString(),
                    CampusCommandPlugin.class, content, ";");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Command> commandMap = new HashMap<>();
        commandMap.put("campus", new CampusCommand());
        return commandMap;
    }
    public static void main(String[] args) throws Exception {
        try {
            CommunicationService.main(new String[]{"true"});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
