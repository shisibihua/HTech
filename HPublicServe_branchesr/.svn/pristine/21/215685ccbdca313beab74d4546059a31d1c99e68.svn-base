package com.honghe.device.event;

import com.hht.DeviceManager.EventHelper;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by zhanghongbin on 2014/11/14.
 */
@WebServlet(urlPatterns = "/eventConsumerService", loadOnStartup = 1)
public class EventConsumer extends HttpServlet {
    final static Logger logger = Logger.getLogger(EventConsumer.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ip = req.getRemoteAddr();
        JSONObject object = new JSONObject();
        try {
            logger.debug("ip==============" + ip);
            boolean flag = EventHelper.ReadBaseNotification(req.getInputStream(), ip);

            if (flag) {
                object.put("state", "success");
            } else {
                object.put("state", "error");
            }

        } catch (Exception e) {
            logger.error("", e);
            object.put("state", "error");
        }

//        String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().write(object.toString());
        resp.getWriter().flush();
        resp.getWriter().close();
        logger.debug("事件通知：ip:" + ip + "状态：" + object.toString());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }


    public void readInputSteam(InputStream sis) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(sis));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            logger.debug(sb.toString());
        } catch (IOException e) {
            logger.debug(e.getMessage());
        } finally {
            try {
                sis.close();
            } catch (IOException e) {
                logger.debug(e.getMessage());
            }
        }

    }
}
