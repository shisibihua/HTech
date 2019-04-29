package com.honghe.util;

import java.io.File;

/**
 * Created by zhanghongbin on 2016/10/19.
 */
public final class FileUtil {

    private FileUtil() {

    }

    private final static int GB = 1024 * 1024 * 1024;//定义GB的计算常量
    private final static int MB = 1024 * 1024;//定义MB的计算常量
    private final static int KB = 1024;//定义KB的计算常量

    public static String getFileSize(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) return "0KB";
        long kSize = file.length();
        if (kSize / GB >= 1)//如果当前Byte的值大于等于1GB
            return (Math.round(kSize / (float) GB)) + "GB";//将其转换成GB
        else if (kSize / MB >= 1)//如果当前Byte的值大于等于1MB
            return (Math.round(kSize / (float) MB)) + "MB";//将其转换成MB
        else if (kSize / KB >= 1)//如果当前Byte的值大于等于1KB
            return (Math.round(kSize / (float) KB)) + "KB";//将其转换成KGB
        else
            return kSize + "Byte";//显示Byte值
    }

    public static int getFileSizeKB(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) return 0;
        long kSize = file.length();
        return (Math.round(kSize / (float) KB));
    }

    public static String getFileName(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) return "";
        int startIndex = file.getName().lastIndexOf(".");
        if (startIndex == -1) return "";
        return file.getName().substring(0, startIndex);
    }

    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1).toLowerCase();
            }
        }
        return filename;
    }

    public static String getFileDic(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) return "";
        String fileName = file.getName();
        return filePath.replaceAll(fileName, "").trim();
    }

}
