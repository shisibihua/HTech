package com.honghe.user.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Map与javaBean对象转换工具类
 *
 * @author lyx
 * @create 2016-11-26 15:06
 **/
public class BeanToMapUtil {
    private static Logger logger = Logger.getLogger(BeanToMapUtil.class);

    /**
     * Map --> Bean 2: 利用org.apache.commons.beanutils 工具类实现 Map --> Bean
     *
     * @param map
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> T transMap2Bean2(Map<String, Object> map, T obj) {
        if (map == null || obj == null) {
            return null;
        }
        try {
            BeanUtils.populate(obj, map);
        } catch (Exception e) {
            logger.error("transMap2Bean2 Error ", e);
        }

        return obj;
    }

    /**
     *
     * Map --> Bean 1: 利用Introspector,PropertyDescriptor实现 Map --> Bean
     * @param map
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> T transMap2Bean(Map<String, Object> map, T obj) {

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (map.containsKey(key)) {
                    Object value = map.get(key);
                    // 得到property对应的setter方法
                    if("null".equals(value)){
                        value = null;
                    }
                    Method setter = property.getWriteMethod();
                    setter.invoke(obj, value);
                }
            }

        } catch (Exception e) {
            logger.error("transMap2Bean Error ", e);
        }
        return obj;

    }


    // Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
    public static Map<String, Object> transBean2Map(Object obj) {

        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!"class".equals(key)) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);

                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            logger.error("transBean2Map Error ", e);
        }

        return map;

    }
}