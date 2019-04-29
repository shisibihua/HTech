package com.honghe;

import com.honghe.communication.ioc.Command;
import com.honghe.organization.agency.AgencyCommand;
import com.honghe.permissions.PermissionsCommand;
import com.honghe.role.RoleCommand;
import com.honghe.sys.SysCommand;
import com.honghe.user.*;

/**
 * Created by hh on 2016/12/1.
 */
public final class UserCmdFactory {
    private UserCmdFactory() {
    }

    /**
     * 获取具体的命令对象
     *
     * @param cmd_op
     * @return
     */
    public static Command getCommand(String cmd_op) {
        Command command = null;

        if (cmd_op.startsWith("user")) {
            command = new UserCommand();
        } else if (cmd_op.startsWith("role")) {
            command = new RoleCommand();
        } else if (cmd_op.startsWith("auth")) {
            command = new PermissionsCommand();
        } else if (cmd_op.startsWith("sys")) {
            command = new SysCommand();
        } else if (cmd_op.startsWith("agency")) {
            command = new AgencyCommand();
        } else if (cmd_op.startsWith("teacher")) {
            command = new TeacherCommand();
        } else if (cmd_op.startsWith("parent")) {
            command = new ParentCommand();
        } else if (cmd_op.startsWith("student")){
            command = new StudentCommand();
        } else if (cmd_op.startsWith("htechUser")) {
            command = new HTechUserCommand();
        }
        return command;
    }
}
