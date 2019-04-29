package com.honghe.tech.util;

import net.sf.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author lichong
 * @date 2017/12/20
 */
public class ParamUtil {
    /**
     * 判断参数是否为空
     * @param params
     * @return
     */
    public static boolean paramNull(String... params){
        for (String param:params){
            if(param==null || param.trim().equals("")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断参数是否是数字
     * @param params
     * @return
     */
    public static boolean paramNumError(String... params) {
        for (String param : params) {
            Pattern pattern = Pattern.compile("[0-9]*");
            Matcher isNum = pattern.matcher(param);
            if (!isNum.matches()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断参数日期格式是否正确
     * @param params
     * @return
     */
    public static boolean paramDateError(String... params) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(String param:params){
            try {
                sdf.parse(param);
            } catch (ParseException e) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断参数时间格式是否正确
     * @param params
     * @return
     */
    public static boolean paramTimeError(String... params) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        for(String param:params){
            try {
                sdf.parse(param);
            } catch (ParseException e) {
                return true;
            }
        }
        return false;
    }

    public static boolean paramJsonError(String... params){
        for(String param:params){
            try {
                JSONObject jsonObject = JSONObject.fromObject( param );
            } catch (Exception e) {
                return true;
            }
        }
        return false;
    }
}
