package com.honghe.user.dao;/**
 * Created by lyx on 2017-01-11 0011.
 */

import com.honghe.user.dao.entity.TeacherDutyType;

import java.util.Set;

/**
 * 教师职务
 *
 * @author liuyanxue
 * @create 2017-01-11 12:32
 **/
public interface ITeacherDutyType {

    /**
     * 依据名字获取职务Id
     *
     * @param names 职务名，多个用逗号分割
     * @return 返回多个时，用逗号分割
     */
    public String getIdsByName(String names);

    /**
     * 依据id查找职务对象
     * @param ids 职务的id（多个用 ，隔开）
     * @return
     */
    public Set<TeacherDutyType> getTypeByIds(String ids);
}
