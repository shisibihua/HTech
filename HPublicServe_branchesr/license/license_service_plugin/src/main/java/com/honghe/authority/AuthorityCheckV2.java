package com.honghe.authority;

import com.honghe.util.MD5Utils;
import com.honghe.util.OkHttpUtils;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by lyx on 2015-09-06.
 * 授权验证
 */
public class AuthorityCheckV2 {
    public static Logger logger = Logger.getLogger(AuthorityCheckV2.class);

    //保存验证或者注册由服务器返回的信息
    public static HashMap<String, AuthorityInfo> info = new HashMap<>();

    /**
     * 授权验证
     *
     * @param _sys  软件代码信息（由双方约定好）
     * @param _path 软件安装路径
     * @param _ver  软件版本信息
     * @return true:验证通过 fale：验证未通过
     */
    public static boolean authorityCheck(String _sys, String _path, String _ver) {

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
    public static boolean registration(String _code, String _sys, String _path, String _ver) {

        boolean flag = false;
        flag = communicationbyGet(_code, _sys, _path, _ver);
        return flag;
    }

    public static boolean registration(String _sys, String _path, String _ver, HashMap<String, String> _hashMap) {

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

//        List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params = new ArrayList<NameValuePair>();
//
//        params.add(new BasicNameValuePair("sys", null == _sys ? "" : _sys));
//        params.add(new BasicNameValuePair("ver", null == _ver ? "" : _ver));
//        params.add(new BasicNameValuePair("path", null == _path ? "" : _path));
//          if (null != _code) {
//             params.add(new BasicNameValuePair("code", _code));
//          }

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
//            e.printStackTrace();
        }

        // JSON的解析过程
         if (                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               json != null && !json.equals("")) {
            JSONObject jsonObject;
            try {
                jsonObject = JSONObject.fromObject(json);
                JSONObject dataArry ;
                Date dates = new Date();
            //-------------------------无论哪种情况下都会传过来的数据-----------------
                authorityInfo.setMessage(jsonObject.getString("message"));//注册结果信息
                authorityInfo.setState(jsonObject.getString("code_id"));
                authorityInfo.setSys(_sys);//软件编号（将传入的值直接放入authorityInfo）
                time = jsonObject.getString("requesttime");
                authget = jsonObject.getString("authget");
            //----------------------------------------------------------------------
                if (jsonObject.containsKey("code_info")) {
                    Map codeinfo = (Map) jsonObject.get("code_info");
                    authorityInfo.setCode((String) codeinfo.get("code"));//获取code_info里的code

                    //通过接收到的数据计算软件剩余时间
                    int regtime = (Integer) codeinfo.get("regtime");
                    int limittime = (Integer) codeinfo.get("timelimit");
                    long restime = Integer.MAX_VALUE;
                    if (limittime != Integer.MAX_VALUE) {
                        long currenttime = (dates.getTime() / 1000L);
                        restime = (regtime + (limittime * 24 * 60 * 60) - currenttime) / (24 * 60 * 60);
                        if(restime<0){
                            restime = -1;
                        }
                    }

                    authorityInfo.setResttime(String.valueOf(restime));
                }

                if (jsonObject.containsKey("system_info")) {
                    dataArry = jsonObject.getJSONObject("system_info");
                    Map<String, String> map = new HashMap<String, String>();
                    Iterator it = dataArry.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry entry = (Map.Entry) it.next();
                        String key = (String) entry.getKey();
                        String value = (String) entry.getValue();
                        map.put(key, value);
                    }

                    authorityInfo.setDataMap((HashMap<String, String>) map);
                }



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
                authorityInfo.setMessage("数据解析失败！");
                authorityInfo.setState(AuthorityInfo.STATE_DATA_FAIL);
                logger.error("返回数据："+"code:" + authorityInfo.getCode() + " message:" + authorityInfo.getMessage() + " state:" + authorityInfo.getState() + " sys:" + authorityInfo.getSys() + " resttime:" + authorityInfo.getResttime() + " value:" + authorityInfo.getValue());
//                e.printStackTrace();
            }


        }

        info.put(_sys, authorityInfo);

        return flag;
    }

    /**
     * 通过协议通信，验证授权或注册授权码是否有效
     *
     * @param _sys
     * @param _path
     * @param _ver
     * @return
     */
    private static boolean communicationbyGet(String _sys, String _path, String _ver) {

        boolean flag = false;
        String url = "";
        String time = "";
        String authget = "";

        AuthorityInfo authorityInfo = new AuthorityInfo();

//        List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params = new ArrayList<NameValuePair>();
//
//        params.add(new BasicNameValuePair("sys", null == _sys ? "" : _sys));
//        params.add(new BasicNameValuePair("ver", null == _ver ? "" : _ver));
//        params.add(new BasicNameValuePair("path", null == _path ? "" : _path));
//          if (null != _code) {
//             params.add(new BasicNameValuePair("code", _code));
//          }
        StringBuffer params = new StringBuffer();
        params.append("sys=" + (null == _sys ? "" : _sys.trim()) + "&ver=" + (null == _ver ? "" : _ver.trim()) + "&path=" + (null == _path ? "" : _path.trim()));
        String json = null;
        try {
            json = OkHttpUtils.getInstance().getJsonByGet(url, params.toString());
        } catch (IOException e) {
            authorityInfo.setMessage("请求本地服务失败！");
            authorityInfo.setState(AuthorityInfo.STATE_SERVICER_FAIL);
//            e.printStackTrace();
        }
        // JSON的解析过程
        if (json != null && !json.equals("")) {
            JSONObject jsonObject;
            try {
                jsonObject = JSONObject.fromObject(json);
                JSONObject dataArry;
                Date dates = new Date();
                //-------------------------无论哪种情况下都会传过来的数据-----------------
                authorityInfo.setMessage(jsonObject.getString("message"));
                authorityInfo.setState(jsonObject.getString("code_id"));
                time = jsonObject.getString("requesttime");
                authget = jsonObject.getString("authget");
                authorityInfo.setSys(_sys);//软件编号（将传入的值直接放入authorityInfo）
                //----------------------------------------------------------------------
                if (jsonObject.containsKey("code_info")) {
                    Map codeinfo = (Map) jsonObject.get("code_info");
                    authorityInfo.setCode((String) codeinfo.get("code"));//获取code_info里的code
                    //通过接收到的数据计算软件剩余时间
                    int regtime = (Integer) codeinfo.get("regtime");
                    int limittime = (Integer) codeinfo.get("timelimit");
                    long restime = Integer.MAX_VALUE;
                    if (limittime != Integer.MAX_VALUE) {
                        long currenttime = (dates.getTime() / 1000L);
                        restime = (regtime + (limittime * 24 * 60 * 60) - currenttime) / (24 * 60 * 60);
                        if(restime<0){
                            restime = -1;
                        }
                    }
                    authorityInfo.setResttime(String.valueOf(restime));
                }
                if (jsonObject.containsKey("system_info")) {    //
                    dataArry = jsonObject.getJSONObject("system_info");
                    Map<String, String> map = new HashMap<String, String>();
                    Iterator it = dataArry.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry entry = (Map.Entry) it.next();
                        String key = (String) entry.getKey();
                        String value = (String) entry.getValue();
                        map.put(key, value);
                    }

                    authorityInfo.setDataMap((HashMap<String, String>) map);
                }
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
                authorityInfo.setMessage("数据解析失败！");
                authorityInfo.setState(AuthorityInfo.STATE_DATA_FAIL);
                logger.error("返回数据："+"code:" + authorityInfo.getCode() + " message:" + authorityInfo.getMessage() + " state:" + authorityInfo.getState() + " sys:" + authorityInfo.getSys() + " resttime:" + authorityInfo.getResttime() + " value:" + authorityInfo.getValue());
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
    private static boolean communicationbyGet(String _code, String _sys, String _path, String _ver, HashMap<String, String> _hashMap) {

        boolean flag = false;
        String url = "";
        String time = "";
        String authget = "";

        AuthorityInfo authorityInfo = new AuthorityInfo();

//        List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params = new ArrayList<NameValuePair>();
//
//        params.add(new BasicNameValuePair("sys", null == _sys ? "" : _sys));
//        params.add(new BasicNameValuePair("ver", null == _ver ? "" : _ver));
//        params.add(new BasicNameValuePair("path", null == _path ? "" : _path));
//          if (null != _code) {
//             params.add(new BasicNameValuePair("code", _code));
//          }

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
//           e.printStackTrace();
        }


        // JSON的解析过程
        if (json != null && !json.equals("")) {
            JSONObject jsonObject;
            try {
                jsonObject = JSONObject.fromObject(json);
                JSONObject dataArry;
                Date dates = new Date();
                //-------------------------无论哪种情况下都会传过来的数据-----------------
                authorityInfo.setMessage(jsonObject.getString("message"));
                authorityInfo.setState(jsonObject.getString("code_id"));
                authorityInfo.setSys(_sys);//软件编号（将传入的值直接放入authorityInfo）
                //----------------------------------------------------------------------
                if (jsonObject.containsKey("code_info")) {
                    Map codeinfo = (Map) jsonObject.get("code_info");
                    authorityInfo.setCode((String) codeinfo.get("code"));//获取code_info里的code

                    //通过接收到的数据计算软件剩余时间
                    int regtime = (Integer) codeinfo.get("regtime");
                    int limittime = (Integer) codeinfo.get("timelimit");
                    long restime = Integer.MAX_VALUE;
                    if (limittime != Integer.MAX_VALUE) {
                        long currenttime = (dates.getTime() / 1000L);
                        restime = (regtime + (limittime * 24 * 60 * 60) - currenttime) / (24 * 60 * 60);
                        if(restime<0){
                            restime = -1;
                        }
                    }

                    authorityInfo.setResttime(String.valueOf(restime));
                }

                time = jsonObject.getString("requesttime");
                authget = jsonObject.getString("authget");
                if (jsonObject.containsKey("system_info")) {    //
                    dataArry = jsonObject.getJSONObject("system_info");
                    Map<String, String> map = new HashMap<String, String>();
                    Iterator it = dataArry.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry entry = (Map.Entry) it.next();
                        String key = (String) entry.getKey();
                        String value = (String) entry.getValue();
                        map.put(key, value);
                    }

                    authorityInfo.setDataMap((HashMap<String, String>) map);
                }

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
                authorityInfo.setMessage("数据解析失败！");
                authorityInfo.setState(AuthorityInfo.STATE_DATA_FAIL);
//                e.printStackTrace();
                logger.error("返回数据："+"code:" + authorityInfo.getCode() + " message:" + authorityInfo.getMessage() + " state:" + authorityInfo.getState() + " sys:" + authorityInfo.getSys() + " resttime:" + authorityInfo.getResttime() + " value:" + authorityInfo.getValue());
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
//           e.printStackTrace();
        }

        // JSON的解析过程
        if (json != null && !json.equals("")) {
            JSONObject jsonObject ;
            try {
                jsonObject = JSONObject.fromObject(json);
                JSONObject dataArry;
                Date dates = new Date();
                //-------------------------无论哪种情况下都会传过来的数据-----------------
                authorityInfo.setMessage(jsonObject.getString("message"));
                authorityInfo.setState(jsonObject.getString("code_id"));
                authorityInfo.setSys(_sys);//软件编号（将传入的值直接放入authorityInfo）
                //----------------------------------------------------------------------
                if (jsonObject.containsKey("code_info")) {
                    Map codeinfo = (Map) jsonObject.get("code_info");
                    authorityInfo.setCode((String) codeinfo.get("code"));//获取code_info里的code

                    //通过接收到的数据计算软件剩余时间
                    int regtime = (Integer) codeinfo.get("regtime");
                    int limittime = (Integer) codeinfo.get("timelimit");
                    long restime = Integer.MAX_VALUE;
                    if (limittime != Integer.MAX_VALUE) {
                        long currenttime = (dates.getTime() / 1000L);
                        restime = (regtime + (limittime * 24 * 60 * 60) - currenttime) / (24 * 60 * 60);
                        if(restime<0){
                            restime = -1;
                        }
                    }

                    authorityInfo.setResttime(String.valueOf(restime));
                }

                time = jsonObject.getString("requesttime");
                authget = jsonObject.getString("authget");
                if (jsonObject.containsKey("system_info")) {    //
                    dataArry = jsonObject.getJSONObject("system_info");
                    Map<String, String> map = new HashMap<String, String>();
                    Iterator it = dataArry.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry entry = (Map.Entry) it.next();
                        String key = (String) entry.getKey();
                        String value = (String) entry.getValue();
                        map.put(key, value);
                    }

                    authorityInfo.setDataMap((HashMap<String, String>) map);
                }


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
                authorityInfo.setMessage("数据解析失败！");
                authorityInfo.setState(AuthorityInfo.STATE_DATA_FAIL);
//                e.printStackTrace();
                logger.error("返回数据："+"code:" + authorityInfo.getCode() + " message:" + authorityInfo.getMessage() + " state:" + authorityInfo.getState() + " sys:" + authorityInfo.getSys() + " resttime:" + authorityInfo.getResttime() + " value:" + authorityInfo.getValue());
            }

        }

        info.put(_sys, authorityInfo);

        return flag;
    }


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

    /**
     * 获取某种类型设备的授权信息
     * @param sysFlag 设备标识
     * @return
     */
    public static AuthorityInfo authorityGetInfo(String sysFlag) {
        AuthorityInfo authorityInfo;
        authorityInfo = info.get(sysFlag);
        logger.debug("设备标识为 "+sysFlag+" 的授权设备授权信息为："+authorityInfo.toString());
        return authorityInfo;
    }
}