package com.honghe.deviceNew.dao;

import com.honghe.deviceNew.entity.DeviceCommandCode;
import com.honghe.spring.MybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@MybatisDao
public interface DeviceCommandCodeMapper {
    int deleteByPrimaryKey(Integer codeId);

    int insert(DeviceCommandCode record);

    int insertSelective(DeviceCommandCode record);

    DeviceCommandCode selectByPrimaryKey(Integer codeId);

    int updateByPrimaryKeySelective(DeviceCommandCode record);

    int updateByPrimaryKey(DeviceCommandCode record);

    List<Map<String, String>> getSpecCmd(@Param("dspecId") int dspecid);
}