package com.honghe.device.message;


import com.hht.DeviceManager.operationRequest.OperationRequest;
import com.hht.callback.DeviceCallBackHandler;
import com.hht.device.Device;

/**
 * Created by zhanghongbin on 2015/3/31.
 * 此部分未使用
 */
public class OperationRequestSetLive implements OperationRequest {
    private String result;
    public OperationRequestSetLive(String result){
        this.result  =result;
    }

    /*@Override
    public void StartInvoke(String s, Device device, DeviceCallBackHandler deviceCallBackHandler) {

    }

    @Override
    public Object Invoke(Device device) throws Exception {
        return result;
    }*/

    @Override
    public void startInvoke(String s, Device device, DeviceCallBackHandler deviceCallBackHandler) {

    }

    @Override
    public Object invoke(Device device) throws Exception {
        return null;
    }
}
