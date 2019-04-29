package com.honghe.util;

import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * Created by zhanghongbin on 2017/1/19.
 */
public final class HardwareUtil {

    private HardwareUtil() {

    }


    public final static String getMac() {

        try {
            byte[] mac = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
            //下面代码是把mac地址拼装成String
            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < mac.length; i++) {
                if (i != 0) {
                    sb.append("-");
                }
                //mac[i] & 0xFF 是为了把byte转化为正整数
                String s = Integer.toHexString(mac[i] & 0xFF);
                sb.append(s.length() == 1 ? 0 + s : s);
            }

            //把字符串所有小写字母改为大写成为正规的mac地址并返回
            return sb.toString().toUpperCase();
        } catch (Exception e) {
            return "";
        }

    }

    public final static  String getIp(){
        try{
            return InetAddress.getLocalHost().getHostAddress();
        }catch (Exception e){
            return "";
        }

    }

    public static void main(String[] args) {
        System.out.println(HardwareUtil.getIp());
    }

}
