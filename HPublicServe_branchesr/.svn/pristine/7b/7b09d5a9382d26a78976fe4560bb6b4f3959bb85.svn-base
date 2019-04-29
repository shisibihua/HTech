package com.honghe.device;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.*;

/**
 * Created by lch on 2015/7/19.
 */
public class SubScribePool {
    final static Logger logger = Logger.getLogger(SubScribePool.class);

    public SubScribePool() {
    }

    public static Hashtable<String, String> subScribePool = new Hashtable();
    public static String  PATH = "subscribeaddress.properties";


    /**
     * 添加
     *
     * @param key sys:ip
     * @param url
     */
    public static void addUrl(String key, String url) {
        subScribePool.put(key, url);
        logger.debug("平台订阅key为:" + key + "url为:" + url);
    }

    /**
     * 获取某一个
     *
     * @param key sys:ip
     * @return
     */
    public static String getUrl(String key) {
        String url = "";
        if (subScribePool.containsKey(key)) {
            url = subScribePool.get(key);
        }
        return url;
    }

    /**
     * 删除
     *
     * @param key sys:ip
     * @return
     */
    public static Boolean delUrl(String key) {
        if (subScribePool.containsKey(key)) {
            subScribePool.remove(key);
            return true;
        }
        return false;
    }

    /**
     * 是否存在ip
     *
     * @param key
     * @return
     */
    public final static boolean contains(String key) {
        return subScribePool.containsKey(key);
    }

    /**
     * 获取所有ip
     *
     * @return
     */
    public final static Set<String> Keys() {
        return subScribePool.keySet();
    }


    //写入平台地址到配置文件
    public static void writeProperties(String key,String value){

        Properties properties=new Properties();
        try {
            String[] str = key.split(":");
            String keyStr = getString(str,"_");
            //读取配置文件里的数据
            FileReader reader = new FileReader(SubScribePool.class.getResource("/").getPath() +PATH);
            properties.load(reader);//载入输入流
            //如果配置文件已经包含有该key，则删除再重新添加
            if(properties.containsKey(keyStr)){
                properties.remove(keyStr);
            }
            properties.setProperty(keyStr, value);

            //写入数据到配置文件中
            FileWriter  writer  = new FileWriter(SubScribePool.class.getResource("/").getPath() +PATH);
            properties.store(writer,null);
            reader.close();
            writer.close();
            logger.debug("平台订阅地址写入配置文件，key："+key+"，value："+value);
        } catch (FileNotFoundException e) {
            logger.error("配置文件未找到",e);
        } catch (IOException e) {
            logger.error("平台地址信息写入配置文件异常，key："+key+"，value："+value,e);
        }

    }

    /**
     * 读取配置文件中的平台地址并保存到订阅池中
     */
    public static void getProperties(){
        Properties properties=new Properties();//获取Properties实例
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStream inStream=cl.getResourceAsStream(PATH);//获取配置文件输入流
        if (inStream!=null){
            try {
                properties.load(inStream);//载入输入流
                Enumeration enumeration=properties.propertyNames();//取得配置文件里所有的key值
                while(enumeration.hasMoreElements()){
                    String key=(String) enumeration.nextElement();
                    String[] str = key.split("_");
                    String keyStr="";
                    keyStr = getString(str,":");
                    String value = properties.getProperty(key);
                    addUrl(keyStr,value);
                }
                logger.debug("获取配置文件中的平台地址信息，并写入到SubScribePool中");
            } catch (IOException e) {
                logger.debug("获取配置文件中的平台地址信息异常",e);
            }
        }

    }


    /**
     * 字符串拼接
     * @param str 字符串
     * @param flag 对应的符号
     * @return
     */
    private static String getString(String[] str,String flag){
        String keyString = "";
        for (int i = 0;i<str.length;i++){
            keyString+=str[i]+flag;
        }
        if (!"".equals(keyString)&&keyString.endsWith(flag)){
            keyString = keyString.substring(0,keyString.lastIndexOf(flag));
        }
        return keyString;
    }

}
