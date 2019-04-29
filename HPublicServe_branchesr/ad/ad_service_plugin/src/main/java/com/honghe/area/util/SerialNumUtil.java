package com.honghe.area.util;/**
 * Created by lyx on 2017-01-07 0007.
 */

import jodd.util.StringUtil;

/**
 * 生成对应的流水单号
 *
 * @author lyx
 * @create 2017-01-07 11:18
 **/
public class SerialNumUtil {

    private SerialNumUtil() {
    }

    /**
     * 生成一个 tags+四位字符串的单号
     *
     * @param tags      流程账号标识 例如 NO201700001中的 NO
     * @param oldNumber //从数据库查询出的最大编号 若为NUll则表示从001开始
     * @return
     */
    public static synchronized String getNumber(String tags, String oldNumber) {
        String orderno;
        if (!StringUtil.isEmpty(oldNumber)&&oldNumber.contains("AR")) {
            // 截取字符串最后四位，结果:00001
            String uidEnd = oldNumber.substring(oldNumber.length() - 4, oldNumber.length());
            // 把String类型的0001转化为int类型
            int endNum = Integer.parseInt(uidEnd);
            // 结果10002
            int tmpNum = 10000 + endNum + 1;
            // 把10002首位的1去掉，再拼成NO0002字符串
            orderno = tags + subStr(Integer.toString(tmpNum), 1);
        } else {
            orderno = tags + "0001";
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
        String numStr = "";
        if (start < str.length()) {
            numStr = str.substring(start);
        }
        return numStr;
    }
}
