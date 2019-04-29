package com.honghe.user.controller;

import com.honghe.exception.ParamException;
import com.honghe.user.dao.entity.Parent;
import com.honghe.user.service.ParentService;
import com.honghe.dao.PageData;
import com.honghe.ServiceFactory;

import java.util.List;

/**
 *
 * @author zhaowj
 * @date 2017/1/10
 */
public class ParentController {

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
    public PageData parentSearchByPage(Integer page, Integer pageSize, String parentName, String parentCode,String campusId) throws Exception {
        if (page == null || page < 0 || pageSize == null || pageSize < 0) {
            throw new ParamException();
        }
        PageData data = parentService.getParentsByPage(page, pageSize, parentName, parentCode,campusId);
        return data;
    }

    /**
     * 依据家长名字返回对象list
     *
     * @param parentName 家长姓名可为空
     * @return
     */
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
    public Object parentsDelete(String parentIds) throws Exception {
        return parentService.parentsDelete(parentIds);
    }
    /**
     * 通过用户id获取学生信息
     * @param userId
     * @return
     * @throws Exception
     */
    public List<Parent> parentGetStudent(String userId) throws Exception {
        return parentService.parentGetStudent(userId);
    }

}
