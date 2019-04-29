package com.honghe.device.action.commandHandler;/**
 * Created with IntelliJ IDEA.
 * User: lyx
 * Date: 2017-05-09 0009
 * Time: 13:31
 * To change this template use File | Settings | File Templates.
 */

import java.util.Map;

/**
 * 依据hostId获取设备类型
 *
 * @author lyx
 * @create 2017-05-09 13:31
 **/
public class CommonInterfaceHandler extends AbstractCommndHandler {
    @Override
    public String getComamnd(Map<String, Object> map) {
        String command = null;
        if (map.containsValue("getCmd")||map.containsValue("getAllChannelIntersectionByType")) {
            command = "screenCommand";
        } else {
            if (super.getCommandHandler() != null) {
                command = super.getCommandHandler().getComamnd(map);
            }
        }
        return command;
    }
}
