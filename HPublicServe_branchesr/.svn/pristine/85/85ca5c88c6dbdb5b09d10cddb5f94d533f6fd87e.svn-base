package com.honghe.web.ad.util;


import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.*;

/**
 * Created by lyx on 2016-07-08.
 * Json字符串处理工具类
 */
public final class JsonUtil {

    public static final Logger logger = Logger.getLogger(JsonUtil.class);

    /**
     * 将Javabean转换为Map
     *
     * @param javaBean javaBean
     * @return Map对象
     */
    public static Map toMap(Object javaBean) {

        Map result = new HashMap();
        java.lang.reflect.Method[] methods = javaBean.getClass().getDeclaredMethods();

        for (java.lang.reflect.Method method : methods) {

            try {
                if (method.getName().startsWith("get")) {
                    String field = method.getName();
                    field = field.substring(field.indexOf("get") + 3);
                    field = field.toLowerCase().charAt(0) + field.substring(1);
                    Object value = method.invoke(javaBean, (Object[]) null);
                    result.put(field, null == value ? "" : value.toString());
                }
            } catch (Exception e) {
                logger.error("将Javabean转换为Map出现异常", e);
            }
        }
        return result;

    }

    /**
     * 将Json对象转换成Map
     *
     * @return Map对象
     * @throws JSONException
     */
    public static Map toMap(String jsonString) throws JSONException {

        JSONObject jsonObject = new JSONObject(jsonString);

        Map result = new HashMap();
        Iterator iterator = jsonObject.keys();
        String key = null;
        String value = null;

        while (iterator.hasNext()) {

            key = (String) iterator.next();
            value = jsonObject.getString(key);
            result.put(key, value);

        }
        return result;

    }

    /**
     * 将JavaBean转换成JSONObject（通过Map中转）
     *
     * @param bean javaBean
     * @return json对象
     */
    public static JSONObject toJSON(Object bean) {

        return new JSONObject(toMap(bean));

    }

    /**
     * 将Map转换成Javabean
     *
     * @param javabean javaBean
     * @param data     Map数据
     */
    public static Object toJavaBean(Object javabean, Map data) {

        java.lang.reflect.Method[] methods = javabean.getClass().getDeclaredMethods();
        for (java.lang.reflect.Method method : methods) {

            try {
                if (method.getName().startsWith("set")) {

                    String field = method.getName();
                    field = field.substring(field.indexOf("set") + 3);
                    field = field.toLowerCase().charAt(0) + field.substring(1);
                    method.invoke(javabean, new Object[]{
                            data.get(field)
                    });

                }
            } catch (Exception e) {
                logger.error("将Map转换成Javabean出现异常", e);
            }

        }

        return javabean;

    }

    /**
     * JSONObject到JavaBean
     *
     * @return json对象
     * @throws ParseException json解析异常
     * @throws JSONException
     */
    public static void toJavaBean(Object javabean, String jsonString)
            throws ParseException, JSONException {

        JSONObject jsonObject = new JSONObject(jsonString);

        Map map = toMap(jsonObject.toString());

        toJavaBean(javabean, map);

    }

    /**
     * 将JSONObjec对象转换成Map集合
     *
     * @param json
     * @return
     */
    public static HashMap<String, Object> jsonToMap(JSONObject json) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        Set keys = json.keySet();
        for (Object key : keys) {
            Object o = json.get(String.valueOf(key));
            if (o instanceof JSONArray)
                map.put((String) key, jsonToList((JSONArray) o));
            else if (o instanceof JSONObject)
                map.put((String) key, jsonToMap((JSONObject) o));
            else
                map.put((String) key, o);
        }
        return map;
    }


    /**
     * 将JSONArray对象转换成List集合
     *
     * @param json
     * @return
     */
    public static Object jsonToList(JSONArray json) {
        List<Object> list = new ArrayList<Object>();
        if (json != null && !json.isNull(0)) {
            for (Object o : json) {
                if (o instanceof JSONArray)
                    list.add(jsonToList((JSONArray) o));
                else if (o instanceof JSONObject)
                    list.add(jsonToMap((JSONObject) o));
                else
                    list.add(o);
            }
        }
        return list;
    }


    /**
     * 将net.sf.json.JSONArray对象转换成List集合
     *
     * @param json
     * @return
     */
    public static Object jsonToList(net.sf.json.JSONArray json) {
        List<Object> list = new ArrayList<Object>();
        if (json != null && !json.isEmpty()) {
            for (Object o : json) {
                if (o instanceof net.sf.json.JSONArray)
                    list.add(jsonToList((net.sf.json.JSONArray) o));
                else if (o instanceof net.sf.json.JSONObject)
                    list.add(jsonToMap((net.sf.json.JSONObject) o));
                else
                    list.add(o);
            }
        }
        return list;
    }

    /**
     * 将net.sf.json.JSONObject对象转换成Map集合
     *
     * @param json
     * @return
     */
    public static Map<String, Object> jsonToMap(net.sf.json.JSONObject json) {
        Map<String, Object> map = new HashMap<>();
        Set keys = json.keySet();
        for (Object key : keys) {
            Object o = json.get(String.valueOf(key));
            if (o instanceof net.sf.json.JSONArray)
                map.put((String) key, jsonToList((net.sf.json.JSONArray) o));
            else if (o instanceof JSONObject)
                map.put((String) key, jsonToMap((net.sf.json.JSONObject) o));
            else
                map.put((String) key, o);
        }
        return map;
    }
}
