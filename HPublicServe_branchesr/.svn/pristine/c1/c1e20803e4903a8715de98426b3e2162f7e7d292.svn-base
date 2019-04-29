package com.honghe.communication.plugin;

import com.honghe.UserCommandEntry;
import com.honghe.communication.ioc.Command;
import com.honghe.communication.main.CommunicationService;
import com.honghe.communication.main.EmbedHttpServer;
import com.honghe.dao.JdbcTemplateUtil;
import org.apache.commons.io.IOUtils;
import java.util.HashMap;
import java.util.Map;

import static com.honghe.user.util.JdbcTemplateUtil.password;
import static com.honghe.user.util.JdbcTemplateUtil.url;
import static com.honghe.user.util.JdbcTemplateUtil.userName;

/**
 * Created by zhanghongbin on 2015/5/7.
 */
public final class UserCommandPlugin implements CommandPlugin {


    @Override
    public Map<String, Command> regist() {
        try {
            String content = IOUtils.toString(UserCommandPlugin.class.getResourceAsStream("/usercloud.sql"), "UTF-8");
            JdbcTemplateUtil.exeucteOneUserSQLFile(url, userName, password, UserCommandPlugin.class, content,"service");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Command> commandMap = new HashMap<>();
        commandMap.put("user", new UserCommandEntry());
        commandMap.put("agency", new UserCommandEntry());
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
