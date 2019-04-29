package com.honghe.tech.util;

import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期时间工具类
 *
 * @author zhaowj
 *
 */
public class DateTimeUtil {
    private static Logger logger = Logger.getLogger(DateTimeUtil.class);
    /** 锁对象 */
    private static final Object lockObj = new Object();
    /** 存放不同的日期模板格式的sdf的Map */
    private static Map<String, ThreadLocal<SimpleDateFormat>> sdfMap = new HashMap<String, ThreadLocal<SimpleDateFormat>>();

    private static  String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static  String DATE_FORMAT = "yyyy-MM-dd";
    private static  String DATETIME_FORMAT_NOSECOND = "yyyy-MM-dd HH:mm";
    private static  String TIME_FORMAT_NOSECOND = "HH:mm";

    private DateTimeUtil(){}

    /**
     * 格式化日期
     * <p>
     * 日期格式 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static synchronized Date parseDateTime(String dateTime) throws ParseException {
        return parse(dateTime,DATETIME_FORMAT);
    }
    public static synchronized String formatDateTime(Date dateTime) throws ParseException {
        return format(dateTime,DATETIME_FORMAT);
    }
    /**
     * 格式化日期
     * <p>
     * 日期格式 yyyy-MM-dd
     * @return
     */
    public static synchronized Date parseDate(String date) throws ParseException {
        return parse(date,DATE_FORMAT);
    }
    public static synchronized String formatDate(Date date) throws ParseException {
        return format(date,DATE_FORMAT);
    }

    /**
     * 格式化日期
     * <p>
     * 日期格式 yyyy-MM-dd HH:mm
     * @return
     */
    public static synchronized Date parseDateTime_NoSecond(String date) throws ParseException {
        return parse(date,DATETIME_FORMAT_NOSECOND);
    }
    public static synchronized String formatDateTime_NoSecond(Date date) throws ParseException {
        return format(date,DATETIME_FORMAT_NOSECOND);
    }

    /**
     * 格式化日期
     * <p>
     * 日期格式 HH:mm
     * @return
     */
    public static synchronized Date parseTime_NoSecond(String date) throws ParseException {
        return parse(date,TIME_FORMAT_NOSECOND);
    }
    public static synchronized String formatTime_NoSecond(Date date) throws ParseException {
        return format(date,TIME_FORMAT_NOSECOND);
    }


    /**
     * 获取所传入日期是星期几
     * 日期格式只支持“yyyy-MM-dd”
     * @param date
     * @return
     */
    public static int getWeekByDate(String date){
        try {
            Calendar cal = Calendar.getInstance();
            Date datet = parseDate(date);
            cal.setTime(datet);
            int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
            if(w==0){w=7;}
            return w;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Date getThisWeekMonday() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        try {
            return parseDate(formatDate(cal.getTime()));
        } catch (ParseException e) {
            cal.getTime().setHours(0);
            cal.getTime().setMinutes(0);
            cal.getTime().setSeconds(0);
            return cal.getTime();
        }
    }

    public static Date getNextWeekMonday() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday());
        cal.add(Calendar.DATE, 7);
        return cal.getTime();
    }


    public static Date getThisWeekSunday() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday());
        cal.add(Calendar.DATE, 6);
        return cal.getTime();
    }

    public static Date getNextWeekSunday() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getNextWeekMonday());
        cal.add(Calendar.DATE, 6);
        return cal.getTime();
    }

    /**
     * 获取两个日期之间符合weeks条件的日期
     * @param start 开始日期
     * @param end 结束日期
     * @return 日期集合
     */
    public static List<Date> getBetweenDates(String start, String end, String weeks) {
        List<Date> result = new ArrayList<Date>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(!ParamUtil.paramNumError(weeks.replace(",",""))) {
            try {
                Date s = sdf.parse(start);
                Date e = sdf.parse(end);
                Calendar tempStart = Calendar.getInstance();
                tempStart.setTime(s);
                Calendar tempEnd = Calendar.getInstance();
                tempEnd.setTime(e);
                while (tempStart.getTime().getTime() <= tempEnd.getTime().getTime()) {
                    int num = tempStart.get(Calendar.DAY_OF_WEEK) - 1 == 0 ? 7 : tempStart.get(Calendar.DAY_OF_WEEK) - 1;
                    if (weeks.contains(String.valueOf(num))) {
                        result.add(tempStart.getTime());
                    }
                    tempStart.add(Calendar.DAY_OF_YEAR, 1);
                }
                if (weeks.contains(String.valueOf(tempStart.get(Calendar.DAY_OF_WEEK) - 1))) {
                    result.add(tempStart.getTime());
                }
            }catch (Exception e) {
                logger.error("获取" + start + "到" + end + "之间是" + weeks +"的日期异常",e);
            }
        }
        return result;
    }

    public static boolean wetherInWeek(Date date,Date weekMonday,Date weekSunday){
        if(date.getTime()>=weekMonday.getTime() && date.getTime()<=weekSunday.getTime()){
            return true;
        }
        return false;
    }

    public static final int compareTime(String startTime, String endTime) {
        //不能把SimpleDateFormat 声明成静态的，多线程会有问题
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date start = simpleDateFormat.parse(startTime);
            Date end = simpleDateFormat.parse(endTime);
            return (int) (end.getTime() - start.getTime()) / 1000;
        } catch (Exception e) {
        }
        //如果出问题默认返回30s
        return -1;
    }
    /**
     * time 是否在startTime 和endTime 之间 (结束时间如果和time相等返回false)
     *
     * @param time
     * @param startTime
     * @param endTime
     * @return
     */
    public static final boolean isBetween(String time, String startTime, String endTime) {
        //不能把SimpleDateFormat 声明成静态的，多线程会有问题
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar date = Calendar.getInstance();
        try {
            date.setTime(simpleDateFormat.parse(time));
            Calendar after = Calendar.getInstance();
            after.setTime(simpleDateFormat.parse(startTime));
            Calendar before = Calendar.getInstance();
            before.setTime(simpleDateFormat.parse(endTime));
            if (date.compareTo(after) != -1 && date.before(before)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * time 是否在startTime 和endTime 之间 (结束时间如果和time相等返回false)
     *
     * @param time
     * @param startTime
     * @param endTime
     * @return
     */
    public static final boolean isBetween(Date time, String startTime, String endTime) {
        //不能把SimpleDateFormat 声明成静态的，多线程会有问题
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar date = Calendar.getInstance();
        try {
            date.setTime(time);
            Calendar after = Calendar.getInstance();
            after.setTime(simpleDateFormat.parse(startTime));
            Calendar before = Calendar.getInstance();
            before.setTime(simpleDateFormat.parse(endTime));
            if (date.compareTo(after) != -1 && date.before(before)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getLastDayOfMonth(int year,int month){
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfMonth = sdf.format(cal.getTime());
        return lastDayOfMonth;
    }

    /**
     * 将标准时间格式转换为美国时间
     * @param time
     * @return
     */
    public  static  String transTimeToFormat(String time)
    {
        try{
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date=parseDateTime(time);
            time=sdf.format(date);
            return time;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 计算若干小时前的时间
     * @param
     */
    public static String getTimebeforMinutes(String startTime,int minutes) {
        try{
            Calendar c=Calendar.getInstance();
            c.setTime(parseDateTime(startTime));
            c.add(Calendar.MINUTE, minutes);
            return formatDateTime(c.getTime());
        }catch (Exception e)
        {
            logger.error("计算时间异常",e);
            return null;
        }
    }





    /**
     * 返回一个ThreadLocal的sdf,每个线程只会new一次sdf
     *
     * @param pattern
     * @return
     */
    private static  SimpleDateFormat getSdf(final String pattern) {
//        System.out.println(sdfMap);
        ThreadLocal<SimpleDateFormat> tl = sdfMap.get(pattern);

        // 此处的双重判断和同步是为了防止sdfMap这个单例被多次put重复的sdf
        if (tl == null) {
            synchronized (lockObj) {
                tl = sdfMap.get(pattern);
                if (tl == null) {
                    // 只有Map中还没有这个pattern的sdf才会生成新的sdf并放入map
//                    System.out.println("put new sdf of pattern " + pattern + " to map");

                    // 这里是关键,使用ThreadLocal<SimpleDateFormat>替代原来直接new SimpleDateFormat
                    tl = new ThreadLocal<SimpleDateFormat>() {
                        @Override
                        protected SimpleDateFormat initialValue() {
//                            System.out.println("thread: " + Thread.currentThread() + " init pattern: " + pattern);
                            return new SimpleDateFormat(pattern);
                        }
                    };
                    sdfMap.put(pattern, tl);
                }
            }
        }

        return tl.get();
    }

    /**
     * 是用ThreadLocal<SimpleDateFormat>来获取SimpleDateFormat,这样每个线程只会有一个SimpleDateFormat
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        return getSdf(pattern).format(date);
    }

    public static Date parse(String dateStr, String pattern) throws ParseException {
        return getSdf(pattern).parse(dateStr);
    }

    public static void main(String[] args) {
        System.out.println("1111111111111111111111111");
        // sdfMap = new HashMap<String, ThreadLocal<SimpleDateFormat>>();
        SimpleDateFormat hht = getSdf("yyyy-MM-dd HH:mm:ss");
    }



}  