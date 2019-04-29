package com.honghe.user;

import com.cpjit.swagger4j.annotation.API;
import com.cpjit.swagger4j.annotation.APIs;
import com.cpjit.swagger4j.annotation.DataType;
import com.cpjit.swagger4j.annotation.Param;
import com.honghe.BaseReflectCommand;
import com.honghe.ServiceFactory;
import com.honghe.dao.PageData;
import com.honghe.exception.ParamException;
import com.honghe.user.dao.entity.Parent;
import com.honghe.user.service.ParentService;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @author hh  侯骏涛修改 2018-05-26
 * @date 2016/12/2
 */
@APIs(value = "user")
public class ParentCommand extends BaseReflectCommand {

    @Override
    public Logger getLogger() {
        return Logger.getLogger("user");
    }

    @Override
    public Class getCommandClass() {
        return this.getClass();
    }

    private ParentService parentService = ServiceFactory.getInstance().getServiceInstance(ParentService.class);

    /**
     * 分页查询家长信息
     *
     * @param page       起始页
     * @param pageSize   总页数
     * @param parentName 关键词
     * @param parentCode 内部流水
     * @return
     * @throws Exception
     */
    @API(value = "parentSearchByPage",
            method = "get",
            summary = "分页查询家长信息",
            description = "分页查询家长信息",
            parameters = {
                    @Param(in = "query", name = "page", description = "", dataType = DataType.INTEGER, required = true),
                    @Param(in = "query", name = "pageSize", description = "", dataType = DataType.INTEGER, required = true),
                    @Param(in = "query", name = "parentName", description = "", dataType = DataType.INTEGER, required = true),
                    @Param(in = "query", name = "parentCode", description = "", dataType = DataType.STRING, required = true),
                    @Param(in = "query", name = "campusId", description = "", dataType = DataType.STRING, required = true)
            })
    public PageData parentSearchByPage(Integer page, Integer pageSize, String parentName, String parentCode, String campusId) throws Exception {
        if (page == null || page < 0 || pageSize == null || pageSize < 0) {
            throw new ParamException();
        }
        PageData data = parentService.getParentsByPage(page, pageSize, parentName, parentCode, campusId);
        return data;
    }

    /**
     * 依据家长名字返回对象list
     *
     * @param parentName 家长姓名可为空
     * @return
     */
    @API(value = "parentSearchByName",
            method = "get",
            summary = "依据家长名字返回对象list",
            description = "依据家长名字返回对象list",
            parameters = {
                    @Param(in = "query", name = "parentName", description = "家长姓名可为空", dataType = DataType.STRING)
            })
    public List<Parent> parentSearchByName(String parentName) {
        return parentService.getParentByName(parentName);
    }

//    /**
//     * 修改家长信息
//     *
//     * @param parent
//     * @return
//     * @throws Exception
//     */
//    public Object parentUpdate(Parent parent, String studentCodes) throws Exception {
//        return parentService.update(parent, studentCodes);
//    }

    /**
     * 导入家长对象
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    @API(value = "parentImport",
            method = "get",
            summary = "导入家长对象",
            description = "导入家长对象",
            parameters = {
                    @Param(in = "query", name = "fileName", description = "要导入的文件名", dataType = DataType.STRING, required = true)
            })
    public String parentImport(String fileName) throws Exception {
        return parentService.importExcel(fileName);
    }

//    /**
//     * 删除家长
//     *
//     * @param parentCode
//     * @return
//     * @throws Exception
//     */
//    public Object parentDelete(String parentCode) throws Exception {
//        return parentService.parentDelete(parentCode);
//    }

    /**
     * 批量删除家长
     *
     * @param parentIds 家长id，多个用逗号隔开
     * @return
     */
    @API(value = "parentsDelete",
            method = "get",
            summary = "批量删除家长",
            description = "批量删除家长",
            parameters = {
                    @Param(in = "query", name = "parentIds", description = "家长id", dataType = DataType.STRING, required = true)
            })
    public Object parentsDelete(String parentIds) throws Exception {
        return parentService.parentsDelete(parentIds);
    }

    /**
     * 通过用户id获取学生信息
     *
     * @param userId
     * @return
     * @throws Exception
     */
    @API(value = "parentGetStudent",
            method = "get",
            summary = "通过用户id获取学生信息",
            description = "通过用户id获取学生信息",
            parameters = {
                    @Param(in = "query", name = "userId", description = "用户id", dataType = DataType.STRING, required = true)
            })
    public List<Parent> parentGetStudent(String userId) throws Exception {
        return parentService.parentGetStudent(userId);
    }

}
