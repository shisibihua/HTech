package com.honghe.user.dao;/**
 * Created by lyx on 2017-01-11 0011.
 */

import com.honghe.user.dao.entity.Subject;

import java.util.Set;

/**
 * 科目的操作
 *
 * @author lyx
 * @create 2017-01-11 14:07
 **/
public interface ISubjectDap {

    /**
     * 依据科目名字获取职务Id
     *
     * @param names   科目名，多个用逗号分割
     * @param stageId 学段Id
     * @return 返回多个时，用逗号分割
     */
    public String getIdsByName(String names, Integer stageId);

    /**
     * 根据id获取科目对象
     * @param ids 科目id，多个用逗号分割
     * @return
     */
    public Set<Subject> getSubjectById(String ids);
}
