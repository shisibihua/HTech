package com.honghe.authority;

import com.honghe.util.MD5Utils;
import com.honghe.util.OkHttpUtils;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by lyx on 2015-09-06.
 * 授权验证
 */
public class AuthorityCheck {

    //保存验证或者注册由服务器返回的信息
    public static HashMap<String, AuthorityInfo> info = new HashMap<>();
    public static Logger logger = Logger.getLogger(AuthorityCheck.class);


    /**
     * 授权验证
     *
     * @param _sys  软件代码信息（由双方约定好）
     * @param _path 软件安装路径
     * @param _ver  软件版本信息
     * @return true:验证通过 false：验证未通过
     */
    public static  boolean authorityCheck(String _sys, String _path, String _ver) {

        boolean flag = false;
        flag = communicationbyGet(null,_sys, _path, _ver);
        return flag;
    }


    /**
     * 注册授权码
     *
     * @param _code 授权码
     * @param _sys  软件代码信息
     * @param _path 软件代码所在路径
     * @param _ver  软件版本信息
     * @return true:注册成功 fale：注册失败
     */
    public static  boolean registration(String _code, String _sys, String _path, String _ver) {

        boolean flag = false;
        flag = communicationbyGet(_code, _sys, _path, _ver);
        return flag;
    }

    public static  boolean registration(String _sys, String _path, String _ver, HashMap<String, String> _hashMap) {

        boolean flag = false;
        flag = communicationbyGet(null, _sys, _path, _ver, _hashMap);
        return flag;
    }


    /**
     * 通过协议通信，验证授权或注册授权码是否有效
     *
     * @param _code
     * @param _sys
     * @param _path
     * @param _ver
     * @return
     */
    private static boolean communicationbyGet(String _code, String _sys, String _path, String _ver) {

        boolean flag = false;
        String url = "";
        String time = "";
        String authget = "";

        AuthorityInfo authorityInfo = new AuthorityInfo();

        StringBuffer params = new StringBuffer();
        params.append("sys=" + (null == _sys ? "" : _sys.trim()) + "&ver=" + (null == _ver ? "" : _ver.trim()) + "&path=" + (null == _path ? "" : _path.trim()));
        if (null != _code) {
            params.append("&code=" + _code.trim());
        }

        String json = null;

        try {
            json = OkHttpUtils.getInstance().getJsonByGet(url, params.toString());
        } catch (IOException e) {
            authorityInfo.setMessage("请求本地服务失败！");
            authorityInfo.setState(AuthorityInfo.STATE_SERVICER_FAIL);
            e.printStackTrace();
        }

        // JSON的解析过程
        if (json != null && !json.equals("")) {
            JSONObject jsonObject;
            try {
                jsonObject = JSONObject.fromObject(json);

                authorityInfo.setCode(jsonObject.getString("code"));
                authorityInfo.setMessage(jsonObject.getString("message"));
                authorityInfo.setState(jsonObject.getString("state"));
                authorityInfo.setSys(jsonObject.getString("sys"));
                authorityInfo.setResttime(jsonObject.getString("resttime"));

                authget = jsonObject.getString("authget");
                time = jsonObject.getString("requesttime");

                JSONObject dataArry = jsonObject.getJSONObject("data");
                Map<String, String> map = new HashMap<String, String>();
                Iterator it = dataArry.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    String key = (String) entry.getKey();
                    String value = (String) entry.getValue();
                    map.put(key, value);
                }

                authorityInfo.setDataMap((HashMap<String, String>) map);
                logger.debug("DataMap中数据为："+map.toString());
                if (authorityInfo.getState().equals(AuthorityInfo.STATE_ON_TIME) || authorityInfo.getState().equals(AuthorityInfo.STATE_REG_SUCCESS) ||
                        authorityInfo.getState().equals(AuthorityInfo.STATE_REG_CODE_IN_TIME)
                        ) {


                    flag = true;
                }


                if (!MD5Utils.MD5(authorityInfo.getSys() + time + "").equals(authget)) {
                    authorityInfo.setMessage("数据错误！");
                    authorityInfo.setState(AuthorityInfo.STATE_DATA_ERROR);
                    flag = false;
                }


            } catch (JSONException e) {
                // TODO 自动生成的 catch 块
                authorityInfo.setMessage("返回数据解析失败！");
                authorityInfo.setState(AuthorityInfo.STATE_DATA_FAIL);
                e.printStackTrace();
            }


        }

        info.put(_sys, authorityInfo);
        logger.debug("info中dataMap数据为：" + info.get(_sys).toString());

        return flag;
    }

    /**
     * 通过协议通信，验证授权或注册授权码是否有效
     *
     * @param sys
     * @param path
     * @param ver
     * @return
     */
    private static boolean communicationbyGet(String sys, String path, String ver) {

        boolean flag = false;
        String url = "";
        String time = "";
        String authget = "";

        AuthorityInfo authorityInfo = new AuthorityInfo();

        StringBuffer params = new StringBuffer();
        params.append("sys=" + (null == sys ? "" : sys.trim()) + "&ver=" + (null == ver ? "" : ver.trim()) + "&path=" + (null == path ? "" : path.trim()));


        String json = null;

        try {
            json = OkHttpUtils.getInstance().getJsonByGet(url, params.toString());
        } catch (IOException e) {
            authorityInfo.setMessage("请求本地服务失败！");
            authorityInfo.setState(AuthorityInfo.STATE_SERVICER_FAIL);
            e.printStackTrace();
        }

        // JSON的解析过程
        if (json != null && !json.equals("")) {
            JSONObject jsonObject;
            try {
                jsonObject = JSONObject.fromObject(json);


                authorityInfo.setCode(jsonObject.getString("code"));
                authorityInfo.setMessage(jsonObject.getString("message"));
                authorityInfo.setState(jsonObject.getString("state"));
                authorityInfo.setSys(jsonObject.getString("sys"));
                authorityInfo.setResttime(jsonObject.getString("resttime"));

                authget = jsonObject.getString("authget");
                time = jsonObject.getString("requesttime");

                JSONObject dataArry = jsonObject.getJSONObject("data");
                Map<String, String> map = new HashMap<String, String>();
                Iterator it = dataArry.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    String key = (String) entry.getKey();
                    String value = (String) entry.getValue();
                    map.put(key, value);
                }

                authorityInfo.setDataMap((HashMap<String, String>) map);


                if (authorityInfo.getState().equals(AuthorityInfo.STATE_ON_TIME) || authorityInfo.getState().equals(AuthorityInfo.STATE_REG_SUCCESS) ||
                        authorityInfo.getState().equals(AuthorityInfo.STATE_REG_CODE_IN_TIME)
                        ) {


                    flag = true;
                }


                if (!MD5Utils.MD5(authorityInfo.getSys() + time + "").equals(authget)) {
                    authorityInfo.setMessage("数据错误！");
                    authorityInfo.setState(AuthorityInfo.STATE_DATA_ERROR);
                    flag = false;
                }


            } catch (JSONException e) {
                // TODO 自动生成的 catch 块
                authorityInfo.setMessage("返回数据解析失败！");
                authorityInfo.setState(AuthorityInfo.STATE_DATA_FAIL);
                e.printStackTrace();
            }


        }

        info.put(sys, authorityInfo);

        return flag;
    }


    /**
     * 通过协议通信，验证授权或注册授权码是否有效
     *
     * @param _code
     * @param _sys
     * @param _path
     * @param _ver
     * @return
     */
    private static boolean communicationbyGet(String _code, String _sys, String _path, String _ver, HashMap<String, String> _hashMap) {

        boolean flag = false;
        String url = "";
        String time = "";
        String authget = "";

        AuthorityInfo authorityInfo = new AuthorityInfo();

        StringBuffer params = new StringBuffer();
        params.append("sys=" + (null == _sys ? "" : _sys.trim()) + "&ver=" + (null == _ver ? "" : _ver.trim()) + "&path=" + (null == _path ? "" : _path.trim()));

        if (_hashMap == null) {
            _hashMap = new HashMap<>();
        }

        if (null != _code) {
            params.append("&code=" + _code.trim());
        }


        Iterator iter = _hashMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            String val = (String) entry.getValue();
            params.append("&" + key + "=" + (null == val ? "" : val.trim()));
        }

        logger.debug("params:" + params.toString());

        String json = null;

        try {
            json = OkHttpUtils.getInstance().getJsonByGet(url, params.toString());
        } catch (IOException e) {
            authorityInfo.setMessage("请求本地服务失败！");
            authorityInfo.setState(AuthorityInfo.STATE_SERVICER_FAIL);
            e.printStackTrace();
        }


        // JSON的解析过程
        if (json != null && !json.equals("")) {
            JSONObject jsonObject;
            try {
                jsonObject = JSONObject.fromObject(json);

                authorityInfo.setCode(jsonObject.getString("code"));
                authorityInfo.setMessage(jsonObject.getString("message"));
                authorityInfo.setState(jsonObject.getString("state"));
                authorityInfo.setSys(jsonObject.getString("sys"));
                authorityInfo.setResttime(jsonObject.getString("resttime"));

                authget = jsonObject.getString("authget");
                time = jsonObject.getString("requesttime");

                JSONObject dataArry = jsonObject.getJSONObject("data");
                Map<String, String> map = new HashMap<String, String>();
                Iterator it = dataArry.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    String key = (String) entry.getKey();
                    String value = (String) entry.getValue();
                    map.put(key, value);
                }

                authorityInfo.setDataMap((HashMap<String, String>) map);


                if (authorityInfo.getState().equals(AuthorityInfo.STATE_ON_TIME) || authorityInfo.getState().equals(AuthorityInfo.STATE_REG_SUCCESS) ||
                        authorityInfo.getState().equals(AuthorityInfo.STATE_REG_CODE_IN_TIME)
                        ) {


                    flag = true;
                }


                if (!MD5Utils.MD5(authorityInfo.getSys() + time + "").equals(authget)) {
                    authorityInfo.setMessage("数据错误！");
                    authorityInfo.setState(AuthorityInfo.STATE_DATA_ERROR);
                    flag = false;
                }


            } catch (JSONException e) {
                // TODO 自动生成的 catch 块
                authorityInfo.setMessage("返回数据解析失败！");
                authorityInfo.setState(AuthorityInfo.STATE_DATA_FAIL);
                e.printStackTrace();
            }

        }

        info.put(_sys, authorityInfo);

        return flag;
    }


    /**
     * 通过协议通信，验证授权或注册授权码是否有效
     *
     * @param _code
     * @param _sys
     * @param _path
     * @param _ver
     * @return
     */
    private static boolean communicationbyPost(String _code, String _sys, String _path, String _ver) {

        boolean flag = false;
        String url = "";
        String time = "";
        String authget = "";

        AuthorityInfo authorityInfo = new AuthorityInfo();

        /*List<NameValuePair> params = new ArrayList<>();

        params.add(new BasicNameValuePair("sys", null == _sys ? "" : _sys));
        params.add(new BasicNameValuePair("ver", null == _ver ? "" : _ver));
        params.add(new BasicNameValuePair("path", null == _path ? "" : _path));
        if (null != _code) {
            params.add(new BasicNameValuePair("code", _code));
        }*/

        Map<String, String> mapParams = new HashMap<>();
        mapParams.put("sys",null == _sys ? "" : _sys);
        mapParams.put("ver",null == _ver ? "" : _ver);
        mapParams.put("path",null == _path ? "" : _path);
        if (null != _code) {
            mapParams.put("code", _code);
        }

        String json = null;

        try {
            json = OkHttpUtils.getInstance().getJsonByPost(url, mapParams);
        } catch (IOException e) {
            authorityInfo.setMessage("请求本地服务失败！");
            authorityInfo.setState(AuthorityInfo.STATE_SERVICER_FAIL);
            e.printStackTrace();
        }

        // JSON的解析过程
        if (json != null && !json.equals("")) {
            JSONObject jsonObject;
            try {
                jsonObject = JSONObject.fromObject(json);


                authorityInfo.setCode(jsonObject.getString("code"));
                authorityInfo.setMessage(jsonObject.getString("message"));
                authorityInfo.setState(jsonObject.getString("state"));
                authorityInfo.setSys(jsonObject.getString("sys"));
                authorityInfo.setResttime(jsonObject.getString("resttime"));


                authget = jsonObject.getString("authget");
                time = jsonObject.getString("requesttime");

                JSONObject dataArry = jsonObject.getJSONObject("data");
                Map<String, String> map = new HashMap<String, String>();
                Iterator it = dataArry.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    String key = (String) entry.getKey();
                    String value = (String) entry.getValue();
                    map.put(key, value);
                }

                authorityInfo.setDataMap((HashMap<String, String>) map);


                if (authorityInfo.getState().equals(AuthorityInfo.STATE_ON_TIME) || authorityInfo.getState().equals(AuthorityInfo.STATE_REG_SUCCESS) ||
                        authorityInfo.getState().equals(AuthorityInfo.STATE_REG_CODE_IN_TIME)
                        ) {

                    flag = true;
                }


                if (!MD5Utils.MD5(authorityInfo.getSys() + time + "").equals(authget)) {
                    authorityInfo.setMessage("数据错误！");
                    authorityInfo.setState(AuthorityInfo.STATE_DATA_ERROR);
                    flag = false;
                }

            } catch (JSONException e) {
                // TODO 自动生成的 catch 块
                authorityInfo.setMessage("返回数据解析失败！");
                authorityInfo.setState(AuthorityInfo.STATE_DATA_FAIL);
                e.printStackTrace();
            }

        }

        info.put(_sys, authorityInfo);

        return flag;
    }


    @Deprecated //devicegrid重构后的版本将不建议使用该方法
    public static void getInfo() {
        Iterator iter = info.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            AuthorityInfo val = (AuthorityInfo) entry.getValue();
            logger.debug("key值:" + key + "vlaue:"+val);
            val.getInfo();
        }

    }

//--------------------------------------------------------http调用接口-------------------------------------------------------

    /**
     * 授权验证
     * @param sysNum 系统编号
     * @param path 路径
     * @param softVersion 版本号
     * @return
     */
    public Map<String,Object> authorityInfoCheck(String sysNum, String path, String softVersion){
        Map<String,Object> map ;
        String code = "";
        map = communictateByGet(code, sysNum, path, softVersion);
        return map;
    }

    /**
     * 授权注册
     * @param code 授权码
     * @param sysNum 系统编号
     * @param path 路径
     * @param softVersion 版本号
     * @return
     */
    public Map<String,Object> authorityRegist(String code, String sysNum, String path, String softVersion){
        Map<String,Object> map ;
        map = communictateByGet(code, sysNum, path, softVersion);
        return map;
    }
    /**
     * 获取某种类型设备的授权信息
     * @param sysFlag 设备标识
     * @return
     */
    public AuthorityInfo authorityGetInfo(String sysFlag) {
        AuthorityInfo authorityInfo = new AuthorityInfo();
        //验证该类型设备是否注册成功
        authorityInfoCheck(sysFlag, "", "");
        if(info!=null&&info.size()>0){
            authorityInfo = info.get(sysFlag);
        }
        logger.debug("设备标识为 "+sysFlag+" 的授权设备授权信息为："+authorityInfo.getDataMap().toString());
        return authorityInfo;
    }

    /**
     * 通过协议通信，验证授权或注册授权码是否有效
     *
     * @param code
     * @param sysNum
     * @param path
     * @param softVersion
     * @return
     */
    private  Map<String,Object> communictateByGet(String code, String sysNum, String path, String softVersion) {
        Map<String,Object> map = new HashMap<String,Object>();
        boolean flag = false;
        String url = "";
        String time = "";
        String authget = "";

        AuthorityInfo authorityInfo = new AuthorityInfo();
        StringBuffer params = new StringBuffer();

        params.append("sys=" + (null == sysNum ? "" : sysNum.trim()) + "&ver=" + (null == softVersion ? "" : softVersion.trim()) + "&path=" + (null == path ? "" : path.trim()));
        if (null != code&&!"".equals(code)) {
            params.append("&code=" + code.trim());
        }

        String json = null;

        try {
            json = OkHttpUtils.getInstance().getJsonByGet(url, params.toString());
        } catch (IOException e) {
            authorityInfo.setMessage("请求本地服务失败！");
            authorityInfo.setState(AuthorityInfo.STATE_SERVICER_FAIL);
            e.printStackTrace();
        }
        // JSON的解析过程
        flag = dataParse(json,authorityInfo,sysNum);
        logger.debug("info中dataMap数据为：" + info==null||info.isEmpty() ? "" : info.get(sysNum).toString());
        map.put("isSuccess",flag);
        map.put("message",authorityInfo.getMessage());
        return map;
    }


    /**
     * 解决httpserver给返回的json数据
     * @param jsonStr 返回的json数据
     * @param authorityInfo 授权信息对象
     * @param sysNum 系统编号
     * @return
     */
    private boolean dataParse(String jsonStr,AuthorityInfo authorityInfo,String sysNum) {
        String time = "";
        String authget = "";
//        String timeToMinute = "yyyy-MM-dd HH:mm";
//        String timeToSecond = "yyyy-MM-dd HH:mm:ss";
//        SimpleDateFormat formatTimem = new SimpleDateFormat(timeToMinute);
//        SimpleDateFormat formatTimes = new SimpleDateFormat(timeToSecond);
        boolean flag = false;
        if (jsonStr != null && !jsonStr.equals("")) {
            JSONObject jsonObject ;
            try {
                jsonObject = JSONObject.fromObject(jsonStr);
                String restTime = jsonObject.getString("resttime");
                authorityInfo.setCode(jsonObject.getString("code"));
                authorityInfo.setMessage(jsonObject.getString("message"));
                authorityInfo.setState(jsonObject.getString("state"));
                authorityInfo.setSys(jsonObject.getString("sys"));

                authget = jsonObject.getString("authget");
                time = jsonObject.getString("requesttime");
                long restDate = 0;
                logger.debug("resttime为："+restTime);
                if (restTime!=null&&!"".equals(restTime)) {
                    restDate =Long.parseLong(restTime);
                    if (restDate==2147483647){
                        authorityInfo.setResttime("永久");
                    }else{
                        authorityInfo.setResttime(restTime);
                    }
                }
//                long startDate = new Date().getTime();
//                if (restTime!=null&&!"".equals(restTime)){
//
//                    //通过当前和剩余时间计算截止日期
//                    long restDate = Long.valueOf(restTime);
//                    //判断可用时间是否为永久
//                    if (restDate==2147483647){
//                        authorityInfo.setEndDate("永久");
//                    }else{
//                        long endTime = startDate/1000+restDate* 24 * 60 * 60;
//                        Date end = new Date(endTime*1000);
//                        authorityInfo.setEndDate(formatTimem.format(end));
//                    }
//                }

                JSONObject dataArry = jsonObject.getJSONObject("data");
                Map<String, String> map = new HashMap<String, String>();
                Iterator it = dataArry.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    String key = (String) entry.getKey();
                    String value = (String) entry.getValue();
                    map.put(key, value);
                }

                authorityInfo.setDataMap((HashMap<String, String>) map);

                if (authorityInfo.getState().equals(AuthorityInfo.STATE_ON_TIME) || authorityInfo.getState().equals(AuthorityInfo.STATE_REG_SUCCESS) ||
                        authorityInfo.getState().equals(AuthorityInfo.STATE_REG_CODE_IN_TIME)) {
                    flag = true;
                }
                if (!MD5Utils.MD5(authorityInfo.getSys() + time + "").equals(authget)) {
                    authorityInfo.setMessage("数据错误！");
                    authorityInfo.setState(AuthorityInfo.STATE_DATA_ERROR);
                }

            } catch (JSONException e) {
                // TODO 自动生成的 catch 块
                authorityInfo.setMessage("返回数据解析失败！");
                authorityInfo.setState(AuthorityInfo.STATE_DATA_FAIL);
                e.printStackTrace();
            }
            info.put(sysNum, authorityInfo);
        }
        logger.debug("返回的数据为："+jsonStr);
        return flag;
    }

    /**
     * 心跳接口
     * @return
     */
    public Map<String,String> keep_alive(){
        Map<String,String> aliveMap = new HashMap<>();
        String token = "LICENSE";
        aliveMap.put("token",token);
        return aliveMap;
    }
}
