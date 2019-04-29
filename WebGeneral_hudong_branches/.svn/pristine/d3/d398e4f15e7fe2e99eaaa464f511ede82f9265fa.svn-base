package com.honghe.tech.dao;

import com.honghe.spring.MybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 日志管理
 * @author caoqian  20180124.
 */
@MybatisDao
public interface OperLogDao {
    /**
     * 保存日志
     * @param logMessage 日志信息
     * @return
     */
     boolean saveLog(@Param("log") Map<String,Object> logMessage);

    /**
     * 根据日志id删除日志，多个","隔开
     * @param logIdsArr   日志ids
     * @return
     */
     boolean deleteLogByIdsArr(@Param("logIdsArr") String[] logIdsArr);

    /**
     * 清空所有日志
     * @return
     */
     boolean deleteAllLog();

    /**
     * 根据日志类型分页查询日志
     * @param logType         日志类型,0:操作日志，1:系统日志
     * @param firstResult     从第几条数据开始
     * @param pageSize        每页条数
     * @param pageBool        是否分页,true分页，false不分页
     * @return
     */
     List<Map<String,Object>> logListByPage(@Param("logType") String logType,
                                                  @Param("firstResult") Integer firstResult,
                                                  @Param("pageSize") Integer pageSize,
                                                  @Param("pageBool") boolean pageBool);

    /**
     * 根据日期范围及日志类型分页查询日志
     * @param logType          日志类型,0:操作日志，1:系统日志
     * @param startDate        开始日期
     * @param endDate          结束日期
     * @param firstResult      从第几条数据开始
     * @param pageSize         每页条数
     * @param pageBool         是否分页,true分页，false不分页
     * @return
     */
     List<Map<String,Object>> logListByDateByPage(@Param("logType") String logType,@Param("startDate")String startDate,
                                                        @Param("endDate")String endDate,@Param("firstResult") Integer firstResult,
                                                        @Param("pageSize") Integer pageSize,@Param("pageBool") boolean pageBool);

    /**
     * 按照日志ids及日志类型查询日志
     * @param logIds        日志ids
     * @param logType       日志类型,0:操作日志，1:系统日志
     * @return
     */
     List<Map<String,Object>> logListByIdsArrAndType(@Param("logIdsArr")String[] logIds,@Param("logType") String logType);

     /**
     * 获取所有日志信息
     * @return list
     * @author caoqian
     */
     List<Map<String,Object>> getAllLogs();
}
