package com.honghe.communication.plugin.impl.notification;

import com.honghe.communication.ioc.Command;
import com.honghe.communication.ioc.Context;
import com.honghe.communication.ioc.Response;

import java.util.Map;

/**
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: 北京鸿合盛视数字媒体技术有限公司</p>
 *
 * @author zhanghongbin
 * @date 2016/1/28
 */
public class NotificationCommand implements Command {


    @Override
    public Response execute(Context context, Map<String, Object> map) {
        Response response = new Response();
        if (!map.containsKey("cmd_op")) {
            response.setCode(Response.PARAM_ERROR);
            return response;
        }
        String cmd_op = map.get("cmd_op").toString();
        response.setType(cmd_op);
        if (cmd_op.equals("email")) {
            if (!map.containsKey("subject")) {
                response.setCode(Response.PARAM_ERROR);
                return response;
            }
            if (!map.containsKey("content")) {
                response.setCode(Response.PARAM_ERROR);
                return response;
            }
            if (!map.containsKey("to")) {
                response.setCode(Response.PARAM_ERROR);
                return response;
            }
            boolean isHtml = false;
            if (map.containsKey("isHtml")) {
                isHtml = Boolean.parseBoolean(map.get("isHtml").toString());
            }
            String subject = map.get("subject").toString().trim();
            String content = map.get("content").toString().trim();
            String to = map.get("to").toString().trim();
            String[] toArray = to.split(",");
            String smtp = "";
            String account = "";
            String password = "";
            if (map.containsKey("smtp")) {
                smtp = map.get("smtp").toString().trim();
            }
            if (map.containsKey("account")) {
                account = map.get("account").toString().trim();
            }
            if (map.containsKey("password")) {
                password = map.get("password").toString().trim();
            }
            String[] attachment = null;
            if (map.containsKey("attachment")) {
                attachment = map.get("attachment").toString().trim().split(",");
            }
            Notification notification;
            if (smtp.length() != 0 && account.length() != 0 && password.length() != 0) {
                if (attachment == null) {
                    notification = new Email(subject, content, toArray, isHtml, smtp, account, password);
                } else {
                    notification = new Email(subject, content, toArray, AttachmentUtil.getAttachment(attachment), smtp, account, password);
                }

            } else {
                if (attachment == null) {
                    notification = new Email(subject, content, toArray, isHtml);
                } else {
                    notification = new Email(subject, content, toArray, AttachmentUtil.getAttachment(attachment));
                }
            }
            response.setResult(notification.send());
        } else if (cmd_op.equals("sms")) {
            if (!map.containsKey("phoneNum")) {
                response.setCode(Response.PARAM_ERROR);
                return response;
            }
            if (!map.containsKey("content")) {
                response.setCode(Response.PARAM_ERROR);
                return response;
            }
            String phoneNum = map.get("phoneNum").toString().trim();
            String content = map.get("content").toString().trim();
            String[] phoneNumArray = phoneNum.split(",");
            for(String _phoneNum:phoneNumArray){
                Notification notification = new SMS(_phoneNum.trim(), content);
                response.setResult(notification.send());
            }

        } else {
            response.setCode(Response.NO_METHOD);
        }
        return response;
    }
}
