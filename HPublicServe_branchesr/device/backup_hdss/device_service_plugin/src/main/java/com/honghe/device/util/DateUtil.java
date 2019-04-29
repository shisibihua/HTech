package com.honghe.device.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhanghongbin on 2014/12/5.
 */
public final class DateUtil {


    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static Calendar cal = Calendar.getInstance();

    private DateUtil() {

    }

    public final static long get(String hms) {
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String h = year + "-" + month + "-" + day + " " + hms;
        try {
            return simpleDateFormat.parse(h).getTime();
        } catch (Exception e) {
            return 0l;
        }

    }

    public final static String now(){
        return simpleDateFormat.format(new Date());
    }

    public final static Calendar nowUTC(){
        Calendar cal = Calendar.getInstance() ;
        // 2、取得时间偏移量：
        int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
        // 3、取得夏令时差：
        int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
        // 4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        return cal;
//        int year = cal.get(Calendar.YEAR);
//        int month = cal.get(Calendar.MONTH)+1;
//        int day = cal.get(Calendar.DAY_OF_MONTH);
//        int hour = cal.get(Calendar.HOUR_OF_DAY);
//        int minute = cal.get(Calendar.MINUTE);
//        int sencond = cal.get(Calendar.SECOND);
    }

//    public static void main(String[] args) {
//        Calendar cal =   nowUTC();
//                int year = cal.get(Calendar.YEAR);
//        int month = cal.get(Calendar.MONTH)+1;
//        int day = cal.get(Calendar.DAY_OF_MONTH);
//        int hour = cal.get(Calendar.HOUR_OF_DAY);
//        int minute = cal.get(Calendar.MINUTE);
//        int sencond = cal.get(Calendar.SECOND);
//        System.out.println(year + "--" + month + "--" + day + "--" + hour + "--" + minute + "---" + sencond);
//    }


}
