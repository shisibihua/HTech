package com.honghe.tech.service;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 日志逻辑处理
 * @author  caoqian
 */
public interface OperLogService {
    /**
     * //获取接收课程数
     * @param params
     * @return
     */
    boolean saveLogTable(Map<String,Object> params);

    /**
     * 通过ID删除日志
     * @param logIds
     * @return
     */
    boolean deleteLogTableByIds(String logIds);

    /**
     * 删除日志列表
     * @return
     */
    boolean deleteLogTable();

    /**
     * 分页日志
     * @param logType
     * @param currentPage
     * @param pageSize
     * @return
     */
    Map<String,Object> getLogsByPage(String logType, String currentPage, String pageSize);

    /**
     * 根据日期分页
     * @param logType
     * @param startDate
     * @param endDate
     * @param currentPage
     * @param pageSize
     * @return
     */
    Map<String,Object> getLogsByDateByPage(String logType, String startDate, String endDate, String currentPage, String pageSize);

    /**
     * 根据日志ids及类型查询日志列表
     * @param ids
     * @param logType
     * @return
     */
    List<Map<String,Object>> getLogListByIdsAndType(String ids,String logType);

    /**
     * 根据日期与日志类型日志数据写入excel
     * @param startDate
     * @param endDate
     * @param logType
     * @return
     * @throws IOException
     */
    String writeExcel(String startDate,String endDate,String logType) throws IOException;

    /**
     * 保存日志到excel
     * @param sh
     * @param cellStyle
     * @param list
     */
    void saveLogToExcel(Sheet sh, HSSFCellStyle cellStyle, List<Map<String, Object>> list);

    /**
     * 获取日志列表
     * @return
     */
    List<Map<String,Object>> getAllLogs();

}
