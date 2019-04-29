package com.honghe.version;

import java.util.ResourceBundle;

/**
 * <p>Description:版本信息</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: 北京鸿合盛视数字媒体技术有限公司</p>
 *
 * @author zhanghongbin
 * @date 2015/9/2
 */
public final class Version {

    private Version() {

    }

    private static String versionInfo = "";
    private static ResourceBundle resourceBundle = null;

    static {
        try {
            resourceBundle = ResourceBundle.getBundle("version");
            if (resourceBundle.containsKey("v") && resourceBundle.containsKey("r") && resourceBundle.containsKey("d")) {
                String v = resourceBundle.getString("v");
                String r = resourceBundle.getString("r");
                String d = resourceBundle.getString("d").split(" ")[0].replaceAll("-", "");
                StringBuilder sb = new StringBuilder();
                sb.append(v).append(".").append(d).append(" (" + r + ")");
                versionInfo = sb.toString();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 根据指定的key获取版本信息
     * @param key
     * @return
     */
    public final static String getVersionInfo(String key) {
        if (resourceBundle != null) {
            if (resourceBundle.containsKey(key)) {
                return resourceBundle.getString(key);
            }
        }
        return "";
    }


    /**
     * 获取版本信息
     *
     * @return string 版本信息
     */
    public final static String getVersionInfo() {
        return versionInfo;
    }

}
