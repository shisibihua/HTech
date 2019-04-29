package com.honghe.user.dao;
import com.honghe.dao.PageData;
import com.honghe.user.dao.entity.Parent;

import java.util.List;

/**
 * 家长的操作类
 *
 * @author zhaowj
 * @create 2017-01-09 09:19
 **/
public interface IParentDao {

    /**
     * 新增家长
     *
     * @param parent
     * @return
     */
    public int parentAdd(Parent parent) throws Exception;

//    /**
//     * 删除家长
//     *
//     * @param parentCode
//     * @return
//     */
//    public boolean delete(String parentCode);

    /**
     * 批量删除家长
     *
     * @param ids 家长id，多个用逗号隔开
     * @return
     */
    public boolean deleteParents(final String ids);
    /**
     * 获取所有的家长对象
     *
     * @return
     */
    public List<Parent> getParents();


    /**
     * 分页获取家长信息
     * @param page 当前页
     * @param pageSize  每页的条目
     * @param parentName 搜索关键字
     * @param parentCode  家长内部流水
     * @return
     */
    public PageData<Parent> getParentsByPage(int page, int pageSize, String parentName, String parentCode, String campusId);

    /**
     * 获取当前数据库系统编号的最大值
     *
     * @return
     */
    public String getMaxParentCode();

//    /**
//     * 修改家长
//     * @param parent
//     */
//    public int update(Parent parent);

    /**
     *依据系统编号返回对象
     * @param code
     * @return
     */
    public Parent getParentByCode(final String code);

    /**
     * 依据家长名字返回对象list
     *
     * @param parentName 家长姓名可为空
     * @return
     */
    public List<Parent> getParentByName(final String parentName);


    public List<Parent> getStudentByParent(final String userId);
}
