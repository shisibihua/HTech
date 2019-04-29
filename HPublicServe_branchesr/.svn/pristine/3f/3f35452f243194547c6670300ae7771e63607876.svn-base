package com.honghe.communication.service.sip;

import com.hht.smartcampus.sip.SIPManager;
import com.hht.smartcampus.sip.SIPManagerCallback;
import com.honghe.communication.ioc.CommandIOC;
import com.honghe.communication.ioc.Context;

/**
 * Created by zhanghongbin on 2016/8/22.
 */
public final class SIPCommandService extends SIPManagerCallback {

    @Override
    public void onReceiveResponseMessage(String s, String s1, String s2, String s3) {

    }

    public SIPCommandService(String sipClientId, String sipClientPort, String sipServerIp, String sipServerPort) throws Exception {
        // SIPManager.AddAgent("communication1", "", 7777, "192.168.16.173", 9081);
        SIPManager.AddAgent(sipClientId, "", Integer.parseInt(sipClientPort), sipServerIp, Integer.parseInt(sipServerPort));
    }

    @Override
    public String onReceiveMessage(String LocalSIPClientID, String remoteSipClientId, String MessageReceived) {
       // System.out.println("in----------------" + MessageReceived);
        String result = CommandIOC.execute(new Context(Context.Type.SIP, new Object[]{this, remoteSipClientId, LocalSIPClientID}), MessageReceived);

        if (result == null) return "";
       // System.out.println("out---------------" + result);
        return result;
    }
}
