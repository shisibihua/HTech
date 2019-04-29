package com.honghe.communication.plugin;


import com.honghe.authority.AuthorityCheck;
import com.honghe.authority.AuthorityCheckV2;
import com.honghe.communication.ioc.AnnotationCommand;
import com.honghe.communication.ioc.Command;
import com.honghe.communication.ioc.VersionCommand;
import com.honghe.communication.main.CommunicationService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zlj on 2017/6/8.
 */
public final class NewLineCommandPlugin implements CommandPlugin {

    @Override
    public Map<String, Command> regist() {
        Map<String, Command> versionCommandMap = new HashMap<>();
        versionCommandMap.put("ver1", new AnnotationCommand("authorityV1", new AuthorityCheck()));
        versionCommandMap.put("ver2", new AnnotationCommand("authorityV2", new AuthorityCheckV2()));
        Map<String,Command> commandMap = new HashMap<>();
        commandMap.put("authority", new VersionCommand(versionCommandMap));
        return commandMap;
    }

    public static void main(String[] args) throws Exception {
        try {
            CommunicationService.main(new String[]{"false"});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
