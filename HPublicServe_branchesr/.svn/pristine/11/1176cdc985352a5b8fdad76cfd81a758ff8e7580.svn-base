package com.honghe.communication.service.http;

import com.honghe.communication.ioc.CommandIOC;
import com.honghe.communication.ioc.Context;
import com.honghe.communication.message.MessageFactory;
import com.honghe.communication.service.ServiceInitLoader;
import org.apache.commons.io.IOUtils;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by zhanghongbin on 2015/3/14.
 * http 命令服务
 */
@WebServlet(urlPatterns = "/cloud/httpCommandService", loadOnStartup = 1)
public class HttpCommandService extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        ServiceInitLoader.init();

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        String mt = req.getParameter("mt"); //请求消息类型 map,json,xml
        Object requestMessage;
        if (mt == null) {
            mt = MessageFactory.Type.MAP.name();
            requestMessage = req.getParameterMap();
        } else {
            mt = mt.toUpperCase();
            requestMessage = IOUtils.toString(req.getInputStream(), "UTF-8");
        }
        if (mt.equals(MessageFactory.Type.XML.name())) {
            resp.setContentType("text/xml");
        } else {
            resp.setContentType("application/json");
        }
        String responseMessage = CommandIOC.execute(new Context(Context.Type.HTTP, req), mt, requestMessage);
        if (responseMessage == null) return;
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(responseMessage);
        printWriter.flush();
        printWriter.close();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
