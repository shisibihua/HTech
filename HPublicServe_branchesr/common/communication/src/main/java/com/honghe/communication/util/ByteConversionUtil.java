package com.honghe.communication.util;

/**
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: 北京鸿合盛视数字媒体技术有限公司</p>
 *
 * @author zhanghongbin
 * @date 2015/12/14
 */
@Deprecated
public class ByteConversionUtil {


    private ByteConversionUtil() {

    }

    private final static int GB = 1024 * 1024 * 1024;//定义GB的计算常量
    private final static int MB = 1024 * 1024;//定义MB的计算常量
    private final static int KB = 1024;//定义KB的计算常量

    public static String convert(long kSize) {
        if (kSize / GB >= 1)//如果当前Byte的值大于等于1GB
            return (Math.round(kSize / (float) GB)) + "GB";//将其转换成GB
        else if (kSize / MB >= 1)//如果当前Byte的值大于等于1MB
            return (Math.round(kSize / (float) MB))  + "MB";//将其转换成MB
        else if (kSize / KB >= 1)//如果当前Byte的值大于等于1KB
            return (Math.round(kSize / (float) KB))  + "KB";//将其转换成KGB
        else
            return kSize + "Byte";//显示Byte值
    }

}
