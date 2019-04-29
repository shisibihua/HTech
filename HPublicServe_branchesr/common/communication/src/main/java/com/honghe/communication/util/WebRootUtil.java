package com.honghe.communication.util;

/**
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: 北京鸿合盛视数字媒体技术有限公司</p>
 *
 * @author zhanghongbin
 * @date 2016/3/10
 */
public final class WebRootUtil {

    private WebRootUtil(){

    }

    public static final String getWebRoot() {
        String path = WebRootUtil.class.getResource("/").getPath();
        int n = path.indexOf("WEB-INF/classes/");
        if (n == -1) {
            return "";
        } else {
            path = path.substring(0, n);
            path = path.replaceAll("%20", " ");
            return path;
        }
    }

}
