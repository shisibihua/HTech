package com.honghe.tech.dao;

import com.honghe.spring.MybatisDao;
import com.honghe.tech.entity.ActivityStyle;

import java.util.List;

/**
 *
 * @author xinqinggang
 * @date 2018/1/25
 */
@MybatisDao
public interface ActivityStyleDao {
    /**
     * 根据模式id查询教学活动模式
     * @param id 活动模式id
     * @return 教学活动模式
     */
     ActivityStyle findStyleById(int id);
}
