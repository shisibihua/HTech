package com.honghe.ad;/**
 * Created by lyx on 2016-11-28.
 */

import com.honghe.ad.area.AreaCommand;
import com.honghe.ad.campus.CampusCommand;
import com.honghe.ad.data.DataCommand;
import com.honghe.ad.user.UserCommand;
import com.honghe.communication.ioc.Command;

/**
 * 命令的工厂类
 *
 * @author lyx
 * @create 2016-11-28 11:11
 **/
public final class ADCmdFactory {

    private ADCmdFactory() {
    }

    /**
     * 获取具体的命令对象
     *
     * @param cmd_op
     * @return
     */
    public static Command getCommand(String cmd_op) {
        Command command = null;

        if (cmd_op.startsWith("campus")) {
            command = new CampusCommand();
        } else if (cmd_op.startsWith("area")) {
            command = new AreaCommand();
        } else if (cmd_op.startsWith("user")) {
            command = new UserCommand();
        } else if (cmd_op.startsWith("data")) {
            command = new DataCommand();
        }
        return command;
    }
}
