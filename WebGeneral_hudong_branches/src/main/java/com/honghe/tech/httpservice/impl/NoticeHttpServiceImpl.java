package com.honghe.tech.httpservice.impl;

import com.honghe.tech.httpservice.NoticeHttpService;
import com.honghe.tech.util.HttpServerUtil;
import com.honghe.tech.util.exceptionutil.HttpServerException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Administrator
 * @date 2018/3/12/012
 */
@Service
public class NoticeHttpServiceImpl implements NoticeHttpService{
    private Logger logger=Logger.getLogger(NoticeHttpServiceImpl.class);
    @Override
    public void sendEamils(String emails, String object, String content){
        Map<String, String> emailParams = new HashMap<>();
        emailParams.put("subject", object);
        emailParams.put("content", content);
        emailParams.put("to", emails);
        try {
            HttpServerUtil.noticeService("email", emailParams);
        }catch (HttpServerException e){
            logger.error("发送邮件服务异常",e);
        }
    }

    @Override
    public void sendPhones(String phone, String content){
        Map<String, String> smsParams = new HashMap<>();
        smsParams.put("phoneNum", phone);
        smsParams.put("content", content);
        try {
            HttpServerUtil.noticeService("sms", smsParams);
        }catch (HttpServerException e){
            logger.error("发送短信服务异常",e);
        }
    }

}
