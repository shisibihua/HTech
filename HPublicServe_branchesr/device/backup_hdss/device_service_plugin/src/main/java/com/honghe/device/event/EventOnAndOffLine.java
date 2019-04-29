package com.honghe.device.event;


import com.honghe.device.event.DeviceStatusEvent.ManyEventsProsses;
import com.honghe.device.util.JsonUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

/**
 * Created by sky on 2017/6/29.
 */
@WebServlet(urlPatterns = "/eventOnOffLineService", loadOnStartup = 1)
public class EventOnAndOffLine extends HttpServlet {

    private ManyEventsProsses manyEventsProsses = new ManyEventsProsses();

    static final  Logger logger = Logger.getLogger(EventOnAndOffLine.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean isSuccess = false;
        JSONObject object = new JSONObject();
        resp.setContentType("text/html;charset=UTF-8");  //设置响应页面字符编码
        resp.setCharacterEncoding("UTF-8");  //设置响应页面字符编码
        //接收devicegrid发送过来的事件信息
        if (req.getParameter("deviceOnlineStatus")!=null&&!"".equals(req.getParameter("deviceOnlineStatus"))){
            Object deviceOnlineStatus = URLDecoder.decode(req.getParameter("deviceOnlineStatus"), "UTF-8");
            JSONObject json = JSONObject.fromObject(deviceOnlineStatus);
            Map deviceInfo = JsonUtil.jsonToMap(json);
            isSuccess = manyEventsProsses.oneEventProsser(deviceInfo);
        }
        //接收devicegrid发送来的多个设备的上线下事件
        else if (req.getParameter("devicesOnlineStatus")!=null&&!"".equals(req.getParameter("devicesOnlineStatus"))){
            Object devicesOnlineStatus = URLDecoder.decode(req.getParameter("devicesOnlineStatus"), "UTF-8");
            JSONArray jsonArray = JSONArray.fromObject(devicesOnlineStatus);
            List eventsList = (List) JsonUtil.jsonToList(jsonArray);
            isSuccess = manyEventsProsses.manyEventsProsser(eventsList);
        }else {
            resp.getWriter().write("params error!");
            logger.debug("参数接收异常");
        }
        object.put("事件发送状态为：",isSuccess);
        resp.getWriter().write(JSONSerializer.toJSON(object).toString());
        resp.getWriter().flush();
        resp.getWriter().close();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

}
