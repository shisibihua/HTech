package com.honghe.device.handler;

import com.hht.DeviceManager.operationRequest.OperationRequest;
import com.hht.callback.DeviceCallBackHandler;
import com.honghe.device.dao.DeviceLogDao;
import com.honghe.device.util.CommonNameUtil;
import org.apache.log4j.Logger;

/**
 * Created by zhanghongbin on 2014/12/6.
 *
 */
public class DefaultResponseHandler implements DeviceCallBackHandler {
//    private static DeviceLogService deviceLogService = ContextLoaderListener.
//            getCurrentWebApplicationContext().getBean(DeviceLogService.class);
final static Logger logger = Logger.getLogger(DefaultResponseHandler.class);

    /**
     * OnInvokeSuccess
     * @param s
     * @param s1
     * @param s2
     * @param operationRequest
     * @param o
     */
    @Override
    public void OnInvokeSuccess(String s, String s1, String s2, OperationRequest operationRequest, Object o) {
        System.out.println(o);
    }

    /**
     *
     * @param strUUID
     * @param strDeviceToken
     * @param strRequestClassName
     * @param strErrorContent
     */
    @Override
    public void OnInvokeError(String strUUID, String strDeviceToken,
                              String strRequestClassName, OperationRequest Request,
                              String strErrorContent) {

        String[] deviceip = strDeviceToken.split("-");
        String ip = deviceip[0];
        if (strErrorContent.indexOf("OperationRequestRemoveLogoConfiguration") != -1) {//删除台标
            strErrorContent = "删除台标失败"+strErrorContent;
        } else if (strErrorContent.indexOf("OperationRequestGetLogoConfiguration")!= -1) {//获取台标
            strErrorContent = "获取台标失败"+strErrorContent;
        } else if (strErrorContent.indexOf("OperationRequestSetResolution") != -1) {//设置录像信息分辨率
            strErrorContent = "设置录像信息分辨率失败"+strErrorContent;
        } else if (strErrorContent.indexOf("OperationRequestSetBitrate") != -1) {//设置录像信息分辨率
            strErrorContent = "设置录像信息分辨率失败"+strErrorContent;
        }else if(strErrorContent.indexOf("OperationRequestRemoveNasSettings") != -1){//清除nas设置
            strErrorContent = "清除nas设置失败"+strErrorContent;
        }else if(strErrorContent.indexOf("OperationRequestSetNasSettings") != -1){//nas设置
            strErrorContent = "nas设置失败"+strErrorContent;
        }else if(strErrorContent.indexOf("OperationRequestRemoveLogoConfiguration") != -1){//清除台标失败
            strErrorContent = "清除台标失败"+strErrorContent;
        }else if(strErrorContent.indexOf("OperationRequestSetLogoConfiguration") != -1){//设置台标失败
            strErrorContent = "设置台标失败"+strErrorContent;
        }else if(strErrorContent.indexOf("OperationRequestShutDown") != -1){
            strErrorContent = "关机失败"+strErrorContent;
        }else if(strErrorContent.indexOf("OperationRequestEventSubscribe") != -1){
            strErrorContent = "订阅失败"+strErrorContent;
        }else if(strErrorContent.indexOf("OperationRequestSetDirectMode") != -1){
            strErrorContent = "设置导播策略失败"+strErrorContent;
        }else if(strErrorContent.indexOf("OperationRequestSetSysTime") != -1){
            strErrorContent = "设置同步时间"+strErrorContent;
        }

        else{
            //巡课和导播
           // this.send(strUUID, strDeviceToken, strErrorContent);
        }
        DeviceLogDao.INSTANCE.addLog(ip, strErrorContent, "SYSTEM", CommonNameUtil.HHREC.toString());
    }

    /**
     *
     * @param uuid id
     * @param token ip
     * @param desc 描述
     */
//    private void send(String uuid, String token, String desc) {
//        String[] uuids = uuid.split("_");
//        if (uuids.length == 2) {
//            DeviceResponseMessage deviceResponseMessage = new DeviceResponseMessage(token, DeviceResponseMessage.Type.RESPONSE, desc);
//            deviceResponseMessage.setUserId(uuids[0]);
//            //updateLog(uuids[2], desc);
//            MessageSender.send(uuids[0], deviceResponseMessage.toJson());
//        }
//
//    }

}
