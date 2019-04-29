package com.honghe.user.util;/**
 * Created by lyx on 2017-01-07 0007.
 */

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 生成对应的流水单号
 *
 * @author lyx
 * @create 2017-01-07 11:18
 **/
public class SerialNumUtil {

    /**
     * 生成一个 tags+年份+五位字符串的单号
     *
     * @param Tags      流程账号标识 例如 NO201700001中的 NO
     * @param oldNumber //从数据库查询出的最大编号 若为NUll则表示从00001开始
     * @return
     */
    public synchronized static String getNumber(String Tags, String oldNumber) {
        String orderno = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy"); // 时间字符串产生方式
        String uid_pfix = Tags + format.format(new Date()); // 组合流水号前一部分，NO+时间字符串，如：NO20160126

        if (oldNumber != null && oldNumber.contains(uid_pfix)) {
            String uid_end = oldNumber.substring(oldNumber.length() - 5, oldNumber.length()); // 截取字符串最后五位，结果:00001
            int endNum = Integer.parseInt(uid_end); // 把String类型的0001转化为int类型的1
            int tmpNum = 1000000 + endNum + 1; // 结果100002
            orderno = uid_pfix + subStr("" + tmpNum, 1);// 把10002首位的1去掉，再拼成NO201601260002字符串
        } else {
            orderno = uid_pfix + "000001";
        }
        return orderno;
    }

    /***
     * 去掉字符串中的位数
     *
     * @param str
     * @param start
     * @return
     */
    public static String subStr(String str, int start) {
        if (str == null || "".equals(str) || str.length() == 0) {
            return "";
        }
        if (start < str.length()) {
            return str.substring(start);
        } else {
            return "";
        }
    }
}
