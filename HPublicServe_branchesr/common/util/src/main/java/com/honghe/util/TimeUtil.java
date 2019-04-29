package com.honghe.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static jodd.datetime.JDateTimeDefault.format;

/**
 * Created by zhanghongbin on 2016/10/19.
 */
public final class TimeUtil {

    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private TimeUtil() {

    }


    public static String now() {
        return df.format(new Date());
    }

    public static String now(String pattern){
        return new SimpleDateFormat(pattern).format(new Date());
    }

    public static String nowBefore(String pattern,int day){
        Calendar c = Calendar.getInstance();

        //过去七天
        c.setTime(new Date());
        c.add(Calendar.DATE, - day);
        Date d = c.getTime();
        return new SimpleDateFormat(pattern).format(d);
    }


    public static final String getHMS(long timeMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);
        long hour = (timeMillis % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        String hToHour = "";
        if (String.valueOf(hour).length() == 1) {
            if (hour != 0) {
                hToHour = "0" + hour + ":";
            } else {
                hToHour = "00:";
            }
        } else {
            hToHour = String.valueOf(hour) + ":";
        }

        int minute = calendar.get(Calendar.MINUTE);
        String mToMinute = "";
        if (String.valueOf(minute).length() == 1) {
            if (minute != 0) {
                mToMinute = "0" + minute + ":";
            } else {
                mToMinute = "00:";
            }
        } else {
            mToMinute = String.valueOf(minute) + ":";
        }
        int second = calendar.get(Calendar.SECOND);
        String sToSecond = "";
        if (String.valueOf(second).length() == 1) {
            sToSecond = "0" + second;
        } else {
            sToSecond = String.valueOf(second);
        }
        return hToHour + mToMinute + sToSecond;
    }

    public static void main(String[] args) {
        System.out.println(nowBefore("yyyy-MM-dd",8));
    }
}
