package com.honghe.device.util;

import java.util.HashMap;
import java.util.Map;

public enum PlatformToDevices {

    SYS_DMANAGER("hhtdc", "hhtc", "hhrec", "hhtwb", "htpr","nvr"),
    SYS_DSS("hhtdc", "dss","","","",""),
    SYS_CAMPUSLOCATION("hhtdc", "hhtpa","","","","");

    private String deviceType1;
    private String deviceType2;
    private String deviceType3;
    private String deviceType4;
    private String deviceType5;

    PlatformToDevices(String hhtdc, String deviceType1, String deviceType2, String deviceType3, String deviceType4, String deviceType5) {
        this.deviceType1 = deviceType1;
        this.deviceType2 = deviceType2;
        this.deviceType3 = deviceType3;
        this.deviceType4 = deviceType4;
        this.deviceType5 = deviceType5;
    }

    public static PlatformToDevices getPlatformName(String platName) {
        return map.get(platName);
    }

    public String getDeviceType1() {
        return deviceType1;
    }

    public void setDeviceType1(String deviceType1) {
        this.deviceType1 = deviceType1;
    }

    public String getDeviceType2() {
        return deviceType2;
    }

    public void setDeviceType2(String deviceType2) {
        this.deviceType2 = deviceType2;
    }

    public String getDeviceType3() {
        return deviceType3;
    }

    public void setDeviceType3(String deviceType3) {
        this.deviceType3 = deviceType3;
    }

    public String getDeviceType4() {
        return deviceType4;
    }

    public void setDeviceType4(String deviceType4) {
        this.deviceType4 = deviceType4;
    }

    public String getDeviceType5() {
        return deviceType5;
    }

    public void setDeviceType5(String deviceType5) {
        this.deviceType5 = deviceType5;
    }

    @Override
    public String toString() {
        return  deviceType1.trim()
                +"," + deviceType2.trim()
                +"," + deviceType3.trim()
                +"," + deviceType4.trim()
                +"," + deviceType5.trim();
    }


    private static final Map<String, PlatformToDevices> map = new HashMap<String, PlatformToDevices>();


    public static String convertDevicePlatform(String platName) {
        String devices = "";
        switch (platName) {
            case "dmanager":
                devices = PlatformToDevices.SYS_DMANAGER.toString();
                break;
            case "dss":
                devices = PlatformToDevices.SYS_DSS.toString();
                break;
            case "campusLocation":
                devices = PlatformToDevices.SYS_CAMPUSLOCATION.toString();
        }
        return devices;
    }

//    public static void main(String[] args) {
//        String str = convertDevicePlatform("dss");
//        String[] sss = str.split(",");
//        System.out.println(sss.length);
//        System.out.println(str);
//        for (int i=0;i<sss.length;i++){
//            System.out.println(sss[i]);
//        }
//    }
}

