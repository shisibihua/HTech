package com.honghe.tech.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

/**
 * Created by xinqinggang on 2018/2/8.
 */
public class WeekDateUtil {
    //获取某天在该年的第几周(字符串)
    public static int getWeeks(String datetime)
    {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date=sdf.parse(datetime);
            Calendar cal = Calendar.getInstance();
            cal.setFirstDayOfWeek(Calendar.MONDAY);
            cal.setTime(date);
            return  cal.get(Calendar.WEEK_OF_YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    //获取某天在该年的第几周(时间)
    public static int getWeeks(Date datetime)
    {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.setTime(datetime);
        return  cal.get(Calendar.WEEK_OF_YEAR);
    }


    /**
     * 获取某年第几周的开始时间及结束时间
     * @param year
     * @param week
     * @return
     */
    public static Date getDayOfWeek(int year, int week,String type) {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);
        Calendar cal = (GregorianCalendar) c.clone();
        switch(type)
        {
            case "begin": cal.add(Calendar.DATE, (week-1) * 7);
                break;
            case "end":cal.add(Calendar.DATE, week * 7);
                break;
            default:return null;
        }
        return getFirstDayOfWeek(cal.getTime());
    }

    // 获取给定日期所在周的开始日期
    public static Date getFirstDayOfWeek(Date date)
    {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        return c.getTime();
    }

    // 获取给定日期所在周的结束日期
    public static Date getLastDayOfWeek(Date date)
    {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        return c.getTime();
    }

    // 获取给定日期所在周的结束日期
    public static Date getLastDayTimeOfWeek(Date date)
    {
        Date date1=getLastDayOfWeek(date);
        try{
            date1.setHours(23);
            date1.setMinutes(59);
            date1.setSeconds(59);
        }catch (Exception e)
        {
//            System.out.println("转换时间异常");
        }
        return date1;
    }

    // 获取给定日期所在周的开始日期时间
    public static Date getFirstDayTimeOfWeek(Date date)
    {
        Date date1=getFirstDayOfWeek(date);
        try{
            date1.setHours(00);
            date1.setMinutes(00);
            date1.setSeconds(00);
        }catch (Exception e)
        {
//            System.out.println("转换时间异常");
        }
        return date1;
    }

    /**
     * 计算两个日期之间相差的天数
     * @param one
     * @param two
     * @return
     */
    public  static long daysBetween(Date one, Date two) {
        long difference =  (one.getTime()-two.getTime())/86400000;
        return Math.abs(difference);
    }

    /**
     * 计算两个日期之间相差的天数
     * @param dayOne
     * @param dayTwo
     * @return
     */
    public  static long daysBetween(String dayOne, String dayTwo) {
        try{
            Date one=DateTimeUtil.parseDateTime(dayOne);
            Date two=DateTimeUtil.parseDateTime(dayTwo);
            long difference =  (one.getTime()-two.getTime())/86400000;
            return Math.abs(difference);
        }catch (Exception e)
        {
//            System.out.println("计算日期相差天数异常");
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 获取日期
     * @param date yyyy-MM-dd hh:mm:ss
     * @return
     */
    public static Calendar getCalenderTime(String date)
    {
        try {
            Date time=DateTimeUtil.parseDateTime(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(time);
            return cal;
        }catch (Exception e)
        {
//            System.out.println("转换时间异常");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取日期年份
     * @param date
     * @return
     */
    public static int getYearvOfDate(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取日期年份
     * @param date  yyyy-MM-dd hh:mm:ss
     * @return
     */
    public static int getYearvOfDate(String date)
    {
        try {
            Date time=DateTimeUtil.parseDateTime(date);
            return getYearvOfDate(time);
        }catch (Exception e)
        {
//            System.out.println("转换时间异常");
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 获取时间周及各周的开始结束时间
     * @param startTime  yyyy-MM-dd hh:mm:ss
     * @param endTime yyyy-MM-dd hh:mm:ss
     */
    public static JSONArray getTermWeek(String startTime,String endTime)
    {
        JSONArray resultArray=new JSONArray();
        try{
            Calendar c_begin = new GregorianCalendar();
            Calendar c_end = new GregorianCalendar();
            DateFormatSymbols dfs = new DateFormatSymbols();
            String[] weeks = dfs.getWeekdays();
            c_begin.setTime(DateTimeUtil.parseDateTime(startTime));
            c_begin.set(Calendar.HOUR_OF_DAY, 0);
            c_begin.set(Calendar.MILLISECOND, 0);
            c_begin.set(Calendar.MINUTE, 0);
            c_begin.set(Calendar.SECOND, 0);
//            System.out.println(DateTimeUtil.DATETIME_FORMAT.format(c_begin.getTime()));
            c_end.setTime(DateTimeUtil.parseDateTime(endTime));
            c_end.set(Calendar.HOUR_OF_DAY, 0);
            c_end.set(Calendar.MILLISECOND, 0);
            c_end.set(Calendar.MINUTE, 0);
            c_end.set(Calendar.SECOND, 0);
//            System.out.println(DateTimeUtil.DATETIME_FORMAT.format(c_end.getTime()));
    //        Calendar WeekDateUtil.getCalenderTime(startTime);
    //        WeekDateUtil.getCalenderTime(endTime);
    //
    //        c_begin.set(2010, 3, 2); //Calendar的月从0-11，所以4月是3.
    //        c_end.set(2010, 4, 20); //Calendar的月从0-11，所以5月是4.

            int count = 1;
            c_end.add(Calendar.DAY_OF_YEAR, 1);  //结束日期下滚一天是为了包含最后一天
            JSONObject weekJson=new JSONObject();
            weekJson.put("isThisWeek",false);
            Date nowDate=new Date();
            boolean isSaveWeek=false;
            while(c_begin.before(c_end)){
                if(!isSaveWeek)
                {
                    boolean isSaveDay=WeekDateUtil.isSameDay(c_begin.getTime(),nowDate);
                    if(isSaveDay)
                    {
                        isSaveWeek=true;
                        weekJson.put("isThisWeek",true);
                    }
                }
    //            System.out.println("第"+count+"周  日期："+new java.sql.Date(c_begin.getTime().getTime())+","+weeks[c_begin.get(Calendar.DAY_OF_WEEK)]);
                String date=new java.sql.Date(c_begin.getTime().getTime())+"";
                if(c_begin.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY){
                    weekJson.put("weekStartTime",date);
                }
                if(c_begin.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
                    weekJson.put("weekEndTime",date);
                    if(weekJson.get("weekStartTime")==null||weekJson.get("weekStartTime").equals(""))
                    {
                        Date startWeekTime=WeekDateUtil.getFirstDayTimeOfWeek(c_begin.getTime());
                        weekJson.put("weekStartTime",DateTimeUtil.formatDate(startWeekTime));
                    }
                    resultArray.add(weekJson);
                    //周末后进入下一周，初始化weekJson
                    weekJson=new JSONObject();
                    weekJson.put("isThisWeek",false);
                    isSaveWeek=false;
                    count++;
                }
                c_begin.add(Calendar.DATE, 1);
            }
            //最后一周没有周末时，填入周末
            if(weekJson.get("weekStartTime")!=null&&!weekJson.get("weekStartTime").equals(""))
            {
                c_begin.add(Calendar.DATE, -1);
                Date endWeekTime=WeekDateUtil.getLastDayTimeOfWeek(c_begin.getTime());
                weekJson.put("weekEndTime",DateTimeUtil.formatDate(endWeekTime));
                resultArray.add(weekJson);
            }
        }catch (Exception e)
        {
//            System.out.println("转换时间异常");
        }
        return resultArray;
    }

    public static boolean isSameDay(Date date1, Date date2) {
         Calendar calDateA = Calendar.getInstance();
        calDateA.setTime(date1);
        Calendar calDateB = Calendar.getInstance();
        calDateB.setTime(date2);
        return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)
               && calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)
               && calDateA.get(Calendar.DAY_OF_MONTH) == calDateB
                       .get(Calendar.DAY_OF_MONTH);
    }

    public static String checkSts(String string)
    {
        if(string!=null&&!string.equals(""))
        {
            String st[]= string.split(",");
            string="";
            for(String s:st)
            {
                if(s!=null&&!s.equals(""))
                {
                    string+=","+s;
                }else{
                    continue;
                }
            }
            if(string.startsWith(","))
            {
                string=string.substring(1);
            }
        }else{
            return null;
        }
        return string;
    }

    public static void main(String[] args) {
        String roomIds="1,2,3";
        if(roomIds!=null&&!roomIds.equals(""))
        {
            String st[]= roomIds.split(",");
            roomIds="";
            for(String s:st)
            {
                if(s!=null&&!s.equals(""))
                {
                    roomIds+=","+s;
                }else{
                    continue;
                }
            }
            if(roomIds.startsWith(","))
            {
                roomIds=roomIds.substring(1);
            }
        }else{
            roomIds=null;
        }
        System.out.println(roomIds);
    }
}
