package com.honghe.communication.service.http;

import com.honghe.communication.ioc.CommandFactory;
import net.sf.json.JSONSerializer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * Created by zhanghongbin on 2016/7/15.
 */
@WebServlet(urlPatterns = "/cloud/httpSystemService")
public class HttpSystemService extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        String type = req.getParameter("type");
        Object result = null;
        if (type != null) {
            if (type.equals("10001")) { //获取所有插件信息
                result = CommandFactory.keys();
            }
            if (result != null) {
                try {
                    result = JSONSerializer.toJSON(result).toString();
                } catch (Exception e) {

                }
                PrintWriter printWriter = resp.getWriter();
                printWriter.write(result.toString());
                printWriter.flush();
                printWriter.close();
            }
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

}
