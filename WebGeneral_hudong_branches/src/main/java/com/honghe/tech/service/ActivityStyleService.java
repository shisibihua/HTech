package com.honghe.tech.service;

/**
 *
 * @author xinqinggang
 * @date 2018/1/25
 */
public interface ActivityStyleService {
    /**
     * 根据模式id查询教学活动模式名称
     * @param id 活动模式id
     * @return 教学活动模式
     */
    public String findStyleById(int id);
}
