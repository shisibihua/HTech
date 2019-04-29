package com.honghe.device.register;/**
 * Created by lyx on 2017-02-22 0022.
 */

import com.hht.callback.DeviceEventHandler;
import com.honghe.communication.ioc.Command;
import com.honghe.communication.ioc.CommandFactory;
import com.honghe.communication.ioc.Response;
import com.honghe.device.SubScribePool;
import com.honghe.device.command.impl.HongheDeviceCommand;
import com.honghe.device.dao.DeviceDao;
import com.honghe.device.dao.SpecDao;
import com.honghe.device.handler.DeviceEventHandlerImpl;
import com.honghe.device.util.CommonNameUtil;
import com.honghe.device.util.DateUtil;
import com.honghe.device.util.JsonUtil;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 客户端设备反向注册
 *
 * @author lyx
 * @create 2017-02-22 9:08
 **/
@WebServlet(urlPatterns = "/deviceRegisterService", loadOnStartup = 1)
public class DeviceRegister extends HttpServlet{
    final static Logger logger = Logger.getLogger(DeviceRegister.class);
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean isSuccess = false;
        resp.setContentType("text/html;charset=UTF-8");  //设置响应页面字符编码
        resp.setCharacterEncoding("UTF-8");  //设置响应页面字符编码
        Object manualDiscover = URLDecoder.decode(req.getParameter("manualDiscover"), "UTF-8")== null ?"":URLDecoder.decode(req.getParameter("manualDiscover"), "UTF-8");
        if("".equals(manualDiscover)){
            resp.getWriter().write("params error!");
            logger.debug("参数接收异常");
        }else {
            JSONObject json = JSONObject.fromObject(manualDiscover);
            logger.debug("接收到设备传来的注册信息："+json.toString());
            Map registerInfo = JsonUtil.jsonToMap(json);
            String ip = registerInfo.get("deviceIp").toString();
            String connectParam = registerInfo.get("deviceModel").toString();
            String name = registerInfo.get("name").toString();
            String value = registerInfo.get("manualDiscover").toString();

            JSONObject jsonObject = JSONObject.fromObject(value);
            Map map = JsonUtil.jsonToMap(jsonObject);

            String deviceType = map.get("deviceType").toString(); //设备服务对应的设备类型
            String typeInt = "";
            String type = "";//平台对应的设备类型
            String dtype = "";//设备类型大写表示形式
            String strValue = "";
            String UTCTime = DateUtil.now();
            Map params = new HashMap();
            Map hashMap = new HashMap();
            try {
                Map specInfo = SpecDao.INSTANCE.getSpecInfoByModel(connectParam);
                typeInt = specInfo.get("typeInt").toString();
                dtype = specInfo.get("dtype_name").toString();
                type = specInfo.get("dtype_desc").toString();

                params.put("ip", ip);
                params.put("deviceType", type);//平台对应的设备类型
                params.put("factory", map.get("manufacturer"));
                params.put("className", map.get("className"));
                params.put("typeInt", typeInt);
                params.put("cmd_op", "manualDiscover");
                Map hostMap = new HashMap();
                boolean isregisted = false;
                String stype = "manualDiscover";
                Object result = null;
                int code = 0;
                //如数据库中已存在说明已经注册成功
                 hostMap = DeviceDao.INSTANCE.getHostInfoByIp(ip);
                if (hostMap!=null&&hostMap.size()>0){
                    Map results = new HashMap();
                    results.put("isMuti","false");
                    results.put("hostIp",ip);
                    results.put("isDevicediscover","true");
                    results.put("hostId",hostMap.get("host_id"));
                    result = results;
                    isregisted = true;
                }else {
                    String commandType = "device";
                    Response res = CommandFactory.getInstance(commandType).execute(null,params);
                    stype = res.getType();
                    result = res.getResult();
                    Map resMap = (Map) result;
                    //判断是否注册成功，当hostId>0时，注册成功
                    if (result!=null&&((Map) result).size()>0&&!"0".equals(resMap.get("hostId"))){
                        isregisted = true;
                    }
                    code = res.getCode();
                }
                hashMap.put("type",stype);
                hashMap.put("result", result);
                hashMap.put("code", code);
                hashMap.put("isregisted",isregisted);
                Set<String> set = SubScribePool.Keys();
                String eventSys = getEventSys(type);
                String positSys = getEventSys(CommonNameUtil.HHTPA.toString());
                String urls = "";
                String positeUrls = "";
                for (String str : set) {
                    if (str.contains(eventSys)) {
                        urls += SubScribePool.getUrl(str) + ",";
                    }
                    if (str.contains(positSys)){
                        positeUrls += SubScribePool.getUrl(str)+",";
                    }
                }
                if (!"".equals(urls) && urls.endsWith(",")) {
                    urls = urls.substring(0, urls.lastIndexOf(","));
                }
                if (!"".equals(positeUrls) && positeUrls.endsWith(",")){
                    positeUrls = positeUrls.substring(0, positeUrls.lastIndexOf(","));
                }
                hashMap.put(eventSys + "urls", urls);
                hashMap.put("attendanceurls",positeUrls);
                strValue = JSONSerializer.toJSON(hashMap).toString();
                DeviceEventHandler deventhandler = new DeviceEventHandlerImpl();
                isSuccess = deventhandler.OnEvent(ip, deviceType, "104", dtype + ":::::::" + UTCTime + ":::::::" + "655@@" + strValue + ":::::::" + name);
                if (isSuccess) {
                    hashMap.put("state", "success");
                } else {
                    hashMap.put("state", "false");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            resp.getWriter().write(JSONSerializer.toJSON(hashMap).toString());
            logger.debug("事件通知：ip:" + ip + "状态：" + isSuccess);
        }
        resp.getWriter().flush();
        resp.getWriter().close();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    protected String getEventSys(String deviceType){
        String sys = "";
        if (CommonNameUtil.HHTC.toString().equals(deviceType)
                || CommonNameUtil.HHREC.toString().equals(deviceType)
                || CommonNameUtil.HHTWB.toString().equals(deviceType)
                || CommonNameUtil.HTPR.toString().equals(deviceType)){
            sys = "dmanager";
        }else if (CommonNameUtil.HHTPA.toString().equals(deviceType)){
            sys = "campusLocation";
        }else if (CommonNameUtil.HHTDC.toString().equals(deviceType)){
            sys = "dss";
        }
        return sys;
    }


}
