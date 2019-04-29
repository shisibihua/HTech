package com.honghe.user.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaowj on 2017/3/17.
 */
public enum SysName {
    HDSS("hdss"),
    HDEVICECONTROLLER("hdevicecontroller"),
    HSCHEDULE("hschedule"),
    HLOCATIONMANAGER("hlocationmanager"),
    HUSERMANAGER("husermanager"),
    HSECURITY("hsecurity"),
    HRESMANAGER("hresmanager"),
    HDEVICE("hdevice"),
    HMEETING("hmeeting"),
    HINTERACTIVETEACHING("hinteractiveteaching"),
    HCAMPUSAPP("hcampusapp"),
    HHTPM("hhtpm"),
    HHTSM("hhtsm"),
    HHTOCS("hhtocs"),
    HLICENCE("hlicence"),
    HCOURARRANGE("hcourarrange"),
    HCOURCHOOSE("hcourchoose"),
    HAREA("harea");

    private String sysName;
    private static final Map<String, SysName> MAP;

    static {
        MAP = new HashMap();
        for (SysName sysName : values()) {
            MAP.put(sysName.getSysName(), sysName);
        }
    }

    public static SysName getSysName(String sysName) {
        return (SysName) MAP.get(sysName);
    }

    private SysName(String sysName) {
        this.sysName = sysName;
    }

    public String getSysName() {
        return this.sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public static String convertSysName(String oldSysName) {
        String newSysName = oldSysName;
        switch (oldSysName) {
            case "dss":
                newSysName = HDSS.getSysName();
                break;
            case "hhrec":
                newSysName = HDEVICECONTROLLER.getSysName();
                break;
            case "hhtc":
                newSysName = HDEVICECONTROLLER.getSysName();
                break;
            case "hso":
                newSysName = HLOCATIONMANAGER.getSysName();
                break;
            case "resapp":
                newSysName = HRESMANAGER.getSysName();
                break;
            case "hhtme":
                newSysName = HMEETING.getSysName();
                break;
            case "hhtic":
                newSysName = HINTERACTIVETEACHING.getSysName();
                break;
            case "hhtapp":
                newSysName = HCAMPUSAPP.getSysName();
                break;
            default:
                break;
        }
        return newSysName;
    }
}


