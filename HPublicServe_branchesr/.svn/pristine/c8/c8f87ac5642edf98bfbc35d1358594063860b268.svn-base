package com.honghe.communication.service;

import com.hht.smartcampus.sip.SIPManager;
import com.honghe.communication.plugin.CommandPluginManager;
import com.honghe.communication.service.sip.SIPCommandService;
import com.honghe.communication.service.udp.UdpCommandService;

import java.util.ResourceBundle;

/**
 * Created by zhanghongbin on 2016/9/26.
 */
public final class ServiceInitLoader {


    private ServiceInitLoader() {

    }

    public static void init() {
        //加载插件
        CommandPluginManager.load();
        initUdpService();
        initSipService();
    }

    private static void initUdpService() {
        //初始化udp
        UdpCommandService.start();
    }


    private static void initSipService() {
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("sip");
            //初始化sip
            SIPManager.init(100, new SIPCommandService(resourceBundle.getString("sipClientId"),
                    resourceBundle.getString("sipClientPort"), resourceBundle.getString("sipServerIp"),
                    resourceBundle.getString("sipServerPort")));
            SIPManager.setPrintMessage(true);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }
}
