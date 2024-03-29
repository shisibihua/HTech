package com.honghe.user.util;/**
 * Created by lyx on 2017-01-11 0011.
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 字符串工具类
 *
 * @author lyx
 * @create 2017-01-11 13:17
 **/
public class StringUtil {

    private static final DateFormat FORMATER_DATE = new SimpleDateFormat("yyyyMMdd-HHmmss");

    public static  final String[] mergedStringArr(String[]... strArr){
        List list = new ArrayList();

        for (String[] strings : strArr) {
            list.add(strings);
        }

        String[] s1 = null;

        for (int i = 0; i < list.size(); i++) {
            String[] s2 = (String[]) list.get(i);
            String[] newArr = scarfed(s1, s2);
            s1 = newArr;
        }
        return s1;
    }

    /**
     * 拼接两个数组
     * @param s1
     * @param s2
     * @return
     */
    private static String[] scarfed(String[] s1, String[] s2) {
        if (s1 == null) {
            return s2;
        }
        String[] collStr = new String[s1.length + s2.length];
        for (int i = 0; i < s1.length; i++) {
            collStr[i] = s1[i];
        }
        for (int i = s1.length; i < s1.length + s2.length; i++) {
            collStr[i] = s2[i - s1.length];
        }
        return collStr;

    }

    /**
     * list转换为String
     *
     * @param list
     * @return
     */
    public static String listToString(List<String> list, String separator) {
        if (list == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean first = true;
        //第一个前面不拼接","
        for (String str : list) {
            if (first) {
                first = false;
            } else {
                result.append(separator);
            }
            result.append(str);
        }
        return result.toString();
    }
    /**
     * list转换为String
     *
     * @param list
     * @return
     */
    public static String stringListToString(List<String> list, String separator) {
        if (list == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean first = true;
        //第一个前面不拼接","
        for (String str : list) {
            if (first) {
                first = false;
            } else {
                result.append(separator);
            }
            result.append(str);
        }
        return result.toString();
    }

    /**
     * 将逗号分割的查询字符串，用单引号引起来，用于sql查询
     *
     * @param strs 多个用逗号分割
     * @return
     */
    public static String getSqlString(String strs) {
        String re_value = null;
        if (strs != null) {
            String[] arr_name = strs.split(",");
            StringBuffer sql_name = new StringBuffer();
            for (String name : arr_name) {
                sql_name.append("\'" + name + "\',");
            }
            re_value = sql_name.subSequence(0, sql_name.length() - 1).toString();
        }
        return re_value;
    }


    /**
     * 判断字符串是否为null或为空字符串
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        boolean re_value = false;
        if (str == null || "".equals(str.trim())) {
            re_value = true;
        }
        return re_value;
    }

    /**
     * 判断字符串是否为null或为空字符串
     *
     * @param cell
     * @return
     */
    public static String getCellVal(Object cell, boolean intFlag) {
        String retValue = "";
        if (cell == null || "".equals(cell)) {
            if (intFlag) {
                retValue = "0";
            }
        } else {
            retValue = cell.toString();
        }
        return retValue;
    }


    /**
     * 获取当前时间
     */
    public static Date getCurruntDate() {
        Calendar ca = Calendar.getInstance();
        return ca.getTime();
    }

    /**
     * 返回当前时间的"yyyyMMddhhmmss"格式字符串
     * */
    public static String currentTime() {
        return FORMATER_DATE.format(new Date());
    }

}
