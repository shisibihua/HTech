package com.honghe.tech.util;

import com.honghe.tech.service.ActivityService;
import com.honghe.tech.service.impl.ActivityServiceImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormatSymbols;
import java.util.*;

/**
 * Created by xinqinggang on 2018/2/26.
 */
public class DataTest {
    static Map<Integer,String> gradeMap=new HashMap();
    static Map<Integer,String> subjectMap=new HashMap();
    static Map<Integer,String> roomMap=new HashMap();
    static Map<Integer,String> teacherMap=new HashMap();
    static Map areaMap=new HashMap();
    static Map countyMap=new HashMap();
    static Map schoolMap=new HashMap();
    static Map acMap=new HashMap();

    /**
     * 获取集合中的任意一个值
     * @param list
     * @return
     */
    public static Object getOne(List list)
    {
        int num=(int)(Math.random()*list.size());
        return list.get(num);
    }

    /**
     * 批量插入活动数据
     */
    public JSONArray insertAc()
    {
        JSONArray array=new JSONArray();
        for (int i=0;i<10000;i++)
        {
//            System.out.println(i);
            JSONObject acJson=new JSONObject();
            int acNum=(int)(Math.random()*10)+1;
            int hostCityNum=(int)(Math.random()*10)+1;
            int hostCountyNum=(int)(Math.random()*10)+1;
            int gradeNum=(int)(Math.random()*10)+1;
            int hostRoomNum=(int)(Math.random()*10)+1;
            int hostRoomStudents=(int)(Math.random()*100)+1;
            int hostTeacherNum=(int)(Math.random()*10)+1;
            int lessonId=(int)(Math.random()*20)+3;
            int schoolNum=(int)(Math.random()*10)+1;
            int subjectNum=(int)(Math.random()*10)+1;
            int month=(int)(Math.random()*6)+1;
            int date=(int)(Math.random()*30)+1;
            int hour=(int)(Math.random()*12)+8;
            String stStartDate="2018-"+month+"-"+date+"  "+hour+":"+00;
            String stEndDate="2018-"+month+"-"+date+"  "+hour+":"+45;
            acJson.put("cityName",areaMap.get(hostCityNum));
            String countyName="";
            try {
                countyName=areaMap.get(hostCityNum).toString()+countyMap.get(hostCountyNum).toString();
            }catch (Exception e)
            {
                System.out.println(hostCityNum+"-"+hostCountyNum);
            }
            acJson.put("className",acMap.get(acNum));
            acJson.put("countyName",countyName);
            acJson.put("endTime","");
            acJson.put("startTime",stStartDate);
            acJson.put("endTime",stEndDate);
            acJson.put("gradeId",gradeNum);
            acJson.put("gradeName",gradeMap.get(gradeNum));
            acJson.put("hostRoomId",hostRoomNum);
            acJson.put("hostRoomName",roomMap.get(hostRoomNum));
            acJson.put("hostRoomStudents",hostRoomStudents);
            acJson.put("hostTeacherId",hostTeacherNum);
            acJson.put("hostTeacherName",teacherMap.get(hostTeacherNum));
//            String uuid= UUID.randomUUID().toString().replaceAll("-", "");
            if((int)(Math.random()*10)!=0)
            {
                JSONObject acceptJson=this.getAccessAcInfo();
                acJson.put("idsInfo",acceptJson);
            }
            acJson.put("roomAddr",("1-"+hostCityNum+"-"+hostCountyNum+"-"+schoolNum));
            acJson.put("lessonId",lessonId);
            acJson.put("provinceName","河北省");
            acJson.put("schoolName",(countyMap.get(hostCountyNum).toString()+schoolMap.get(schoolNum)));
            acJson.put("subjectId",subjectNum);
            acJson.put("subjectName",subjectMap.get(subjectNum));
            acJson.put("userId",1);
            acJson.put("sendEmail",(int)Math.random());
            acJson.put("sendMessage",(int)Math.random());
            acJson.put("cloudLive",(int)Math.random());
            array.add(acJson);
        }
        return array;
    }

    public JSONObject getAccessAcInfo()
    {
        JSONObject acceptJson=new JSONObject();
        int accpetCityNum=(int)(Math.random()*10)+1;
        int accpetCountyNum=(int)(Math.random()*10)+1;
        int accpetRoomNum=(int)(Math.random()*10)+1;
        int accpetRoomStudents=(int)(Math.random()*100)+1;
        int accpetTeacherNum=(int)(Math.random()*10)+1;
        int schoolNum=(int)(Math.random()*10)+1;
        acceptJson.put("acceptRoomId",accpetRoomNum);
        acceptJson.put("acceptRoomName",roomMap.get(accpetRoomNum));
        acceptJson.put("acceptRoomStudents",accpetRoomStudents);
        acceptJson.put("assistTeacherId",accpetTeacherNum);
        acceptJson.put("assistTeacherName",teacherMap.get(accpetTeacherNum));
        acceptJson.put("cityName",areaMap.get(accpetCityNum));
        acceptJson.put("countyName",(areaMap.get(accpetCityNum).toString()+countyMap.get(accpetCountyNum)));
        acceptJson.put("provinceName","河北省");
        acceptJson.put("schoolName",(countyMap.get(accpetCountyNum).toString()+schoolMap.get(schoolNum)));
        acceptJson.put("roomAddr",("1-"+accpetCityNum+"-"+accpetCountyNum+"-"+schoolNum));
        return acceptJson;
    }
    public static void main(String[] args) {
        String st[]=new String[]{"3","1","2"};
        Arrays.sort(st);
        for(String s:st)
        {
            System.out.println(s);
        }
    }
}
