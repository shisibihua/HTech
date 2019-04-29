package com.honghe.communication.plugin;

import com.honghe.ad.ADCommand;
import com.honghe.area.AreaCommand;
import com.honghe.communication.ioc.Command;
import com.honghe.communication.main.CommunicationService;
import com.honghe.communication.main.EmbedHttpServer;
import com.honghe.communication.service.http.HttpCommandService;
import com.honghe.dao.JdbcTemplateUtil;
import org.apache.commons.io.IOUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by zhanghongbin on 2016/7/27.
 */
    public class ADCommandPlugin implements CommandPlugin {
    @Override
    public Map<String, Command> regist() {
        try {
            Properties properties = com.honghe.ad.util.JdbcTemplateUtil.getProperties();
            String content = IOUtils.toString(ADCommandPlugin.class.getResourceAsStream("/adcloud.sql"), "UTF-8");
            JdbcTemplateUtil.exeucteOneUserSQLFile(
                    properties.getProperty("jdbc.url"),
                    properties.getProperty("jdbc.username"),
                    properties.getProperty("jdbc.password"),
                    ADCommandPlugin.class, content, "##");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Command> commandMap = new HashMap<>();
        commandMap.put("ad", new ADCommand());
        commandMap.put("area", new AreaCommand());
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
