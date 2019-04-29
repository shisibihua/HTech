package com.honghe.authority;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by lyx on 2015-09-08.
 * 验证返回信息的保存类
 */
public class AuthorityInfo {
    private final static Logger logger = Logger.getLogger(AuthorityInfo.class);
    //在有效期内
    public final static String STATE_ON_TIME = "on_time";
    //超出有效期
    public final static String STATE_OUT_TIME = "out_time";
    //标识串未绑定
    public final static String STATE_UN_REG = "un_reg";
    //标识串注册成功
    public final static String STATE_REG_SUCCESS = "reg_success";
    //标识串版本异常
    public final static String STATE_SOFTTYPE_ERROR = "softtype_error";
    //标识串过期或超链接数
    public final static String STATE_OUTLIMIT_TIME = "outlimit_time";
    //标识串异常
    public final static String STATE_NON_CODE = "non_code";
    //还在有效期
    public final static String STATE_REG_CODE_IN_TIME = "reg_code_in_time";
    //请求验证，注册本地服务失败
    public final static String STATE_SERVICER_FAIL = "servicer_fail";
    //请求验证,注册返回数据解析失败
    public final static String STATE_DATA_FAIL = "data_fail";
    //请求验证,注册数据错误
    public final static String STATE_DATA_ERROR = "data_error";
    //等待申请通过
    public final static String STATE_WAITING_EXAMINE = "waiting_examine";
    //申请未通过
    public final static String STATE_NOT_PASS_EXAMINE = "not_pass_examine";



    //注册码
    private String code = "";
    //返回注册的信息 例如：网络超时，服务失败等
    private String message = "";
    //当前的状态
    private String state = "";
    //软件代码
    private String sys = "";
    //剩余日期
    private String resttime ="-1";
    //校验值
    private String value = "";
    //激活日期
    private String startDate = "";
    //截止日期
    private String endDate = "";
    //返回对应平台信息，用于不同平台信息扩展
    private HashMap<String, String> dataMap = new HashMap<>();


    public void getInfo() {

        Iterator iter = dataMap.entrySet().iterator();
        StringBuilder sb = new StringBuilder();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            sb.append(key + ":" + val);
            sb.append(";");
        }
        logger.debug("code:" + code + " message:" + message + " state:" + state + " sys:" + sys + " resttime:" + resttime + " value:" + value + sb.toString());

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSys() {
        return sys;
    }

    public void setSys(String sys) {
        this.sys = sys;
    }

    public String getResttime() {
        return resttime;
    }

    public void setResttime(String resttime) {
        this.resttime = resttime;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public HashMap<String, String> getDataMap() {
        return dataMap;
    }

    public void setDataMap(HashMap<String, String> dataMap) {
        this.dataMap = dataMap;
    }

    public static String getStateOnTime() {
        return STATE_ON_TIME;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
