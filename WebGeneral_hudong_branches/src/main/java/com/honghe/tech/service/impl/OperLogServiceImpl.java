package com.honghe.tech.service.impl;

import com.honghe.communication.util.WebRootUtil;
import com.honghe.tech.dao.OperLogDao;
import com.honghe.tech.httpservice.UserHttpService;
import com.honghe.tech.service.OperLogService;
import com.honghe.tech.util.HttpRequestUtil;
import com.honghe.tech.util.MD5;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author caoqian
 */
@Service
public class OperLogServiceImpl implements OperLogService {
    private Logger logger=Logger.getLogger(OperLogServiceImpl.class);
    /**
     * 日志导出的excel标题
     */
    private static final String LOG_EXCEL_TITLE= "编号@@用户名称@@时间@@功能模块@@操作@@日志类型";
    private static final String OPER_LOG_TYPE= "操作日志";
    private static final String SYSTEM_LOG_TYPE= "系统日志";
    @Autowired
    private OperLogDao operLogDao;
    @Autowired
    private UserHttpService userHttpService;
    /**
     * 保存日志
     * @param params  日志信息
     * @return boolean  true:保存成功；false:保存失败
     * @author caoqian
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean saveLogTable(Map<String, Object> params) {
        boolean returnResult= false;
        try{
            returnResult=operLogDao.saveLog(params);
        }catch (Exception e){
            returnResult=false;
            logger.error("保存日志异常。",e);
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();   //事物回滚
        }
        return returnResult;
    }

    /**
     * 根据日志ids删除日志
     * @param logIds         日志ids,多个","分割
     * @return  boolean      true:删除成功;false:删除失败
     * @author caoqian
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean deleteLogTableByIds(String logIds){
        boolean returnResult=false;
        if(logIds!=null && !"".equals(logIds)){
            String[] idsArr = logIds.split(",");
            try {
                returnResult = operLogDao.deleteLogByIdsArr(idsArr);
            }catch (Exception e){
                returnResult=false;
                logger.error("根据日志ids删除日志异常,logIds="+logIds);
//                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();   //事物回滚
            }
            return returnResult;
        }else{
            logger.debug("日志ids为空，无法删除日志，参数异常。");
            throw new IllegalArgumentException();
        }
    }

    /**
     * 清空日志
     * @return  boolean      true:删除成功;false:删除失败
     * @author caoqian
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean deleteLogTable() {
        boolean returnResult=false;
        try{
            returnResult=operLogDao.deleteAllLog();
        }catch (Exception e){
            returnResult=false;
            logger.error("清空日志异常",e);
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();   //事物回滚
        }
        return returnResult;
    }

    /**
     * 根据日志类型分页查询
     * @param logType          日志类型，""：全部日志类型；0：操作日志；1:系统日志
     * @param currentPage      当前页
     * @param pageSize         每页条数
     * @return  map            日志数据
     * @author  caoqian
     */
    @Override
    public Map<String, Object> getLogsByPage(String logType, String currentPage, String pageSize) {
        Map<String,Object> logMap=new HashMap<>();
        try{
            logMap.put("currentPage",currentPage);
            logMap.put("pageSize",pageSize);
            //获取第一条开始的位置
            int firstResult = (Integer.parseInt(currentPage)-1)*Integer.parseInt(pageSize)<0?0:(Integer.parseInt(currentPage)-1)*Integer.parseInt(pageSize);
            //总数量
            int total=operLogDao.logListByPage(logType,firstResult,Integer.parseInt(pageSize),false).size();
            //分页数据
            List<Map<String,Object>> logList=operLogDao.logListByPage(logType,firstResult,Integer.parseInt(pageSize),true);
            logMap.put("total",total);
            logMap.put("dataList",logList);
        }catch (Exception e){
            logMap.put("total","");
            logMap.put("dataList","");
            logger.error("根据日志类型查询日志异常，logType="+logType,e);
        }
        return logMap;
    }
    /**
     * 根据日期范围及日志类型分页查询
     * @param logType          日志类型，""：全部日志类型；0：操作日志；1:系统日志
     * @param startDate        开始日期,不是必须
     * @param endDate          结束日期,不是必须
     * @param currentPage      当前页
     * @param pageSize         每页条数
     * @return  map            日志数据
     * @author  caoqian
     */
    @Override
    public Map<String, Object> getLogsByDateByPage(String logType, String startDate, String endDate, String currentPage, String pageSize){
        Map<String, Object> logMap = new HashMap<>();
        if (currentPage == null || "".equals(currentPage) || pageSize == null || "".equals(pageSize)) {
            logger.debug("当前页currentPage:" + currentPage + ",每页条数pageSize:" + pageSize + ",获取日志数据失败，参数异常。");
            throw new IllegalArgumentException();
        } else {
            try {
                logMap.put("currentPage", currentPage);
                logMap.put("pageSize", pageSize);
                //获取第一条开始的位置
                int firstResult = (Integer.parseInt(currentPage) - 1) * Integer.parseInt(pageSize) < 0 ? 0 : (Integer.parseInt(currentPage) - 1) * Integer.parseInt(pageSize);
                //总数量
                int total = 0;
                List<Map<String, Object>> logList = new ArrayList<>();
                if (startDate == null || "".equals(startDate) || endDate == null || "".equals(endDate)) {
                    total = operLogDao.logListByPage(logType, firstResult, Integer.parseInt(pageSize), false).size();
                    //分页数据
                    logList = operLogDao.logListByPage(logType, firstResult, Integer.parseInt(pageSize), true);
                } else {
                    total = operLogDao.logListByDateByPage(logType, startDate, endDate, firstResult, Integer.parseInt(pageSize), false).size();
                    //分页数据
                    logList = operLogDao.logListByDateByPage(logType, startDate, endDate,
                            firstResult, Integer.parseInt(pageSize), true);
                }
                if (!logList.isEmpty()) {
                    Map<String,Object> userInfo=userHttpService.getAllUserInfo();
                    for (Map<String, Object> map : logList) {
                        String userId=String.valueOf(map.get("userId"));
                        if(userInfo!=null && !userInfo.isEmpty() && userInfo.get(userId)!=null) {
                            Map<String,String> user=(Map<String,String>)userInfo.get(userId);
                            //用户真实名称
                            map.put("userName", user.get("userRealName")==null?"":user.get("userRealName"));
                        }else{
                            map.put("userName","");
                        }
                    }
                }
                logMap.put("total", total);
                logMap.put("dataList", logList);
            } catch (Exception e) {
                logMap.put("total", "");
                logMap.put("dataList", "");
                logger.error("根据日期范围及日志类型查询日志异常，startDate=" + startDate + ",endDate=" + endDate + ",logType=" + logType, e);
            }
            return logMap;
        }
    }

    /**
     * 根据日志ids查询日志列表,ids为空时查询所有日志
     * @param ids      日志ids，多个“，”分割,ids=""时查询所有日志
     * @param logType  日志类型，""全部日志，“0”操作日志，“1”系统日志
     * @return list
     * @author caoqian
     */
    @Override
    public List<Map<String, Object>> getLogListByIdsAndType(String ids,String logType) {
        List<Map<String,Object>> logList=new ArrayList<>();
        try{
            if(ids!=null && !"".equals(ids)){
                String[] idsArr=ids.split(",");
                logList=operLogDao.logListByIdsArrAndType(idsArr,logType);
            }else{
                logList=operLogDao.logListByIdsArrAndType(null,logType);
            }
        }catch (Exception e){
            logger.error("根据日志ids及日志类型查询列表异常。",e);
        }
        return logList;
    }

    /**
     * 根据日期范围与日志类型查询日志并导出
     * @param startDate    开始日期，格式:2018-02-05,非必须
     * @param endDate      结束日期，格式:2018-02-05,非必须
     * @param logType      日志类型，""全部日志,"0"操作日志，"1"系统日志
     * @return String      excel路径
     * @author caoqian
     */
    @Override
    public String writeExcel(String startDate,String endDate,String logType){
        String filePath = "";
        String fileName = "";
        if (logType == null) {
            logger.debug("日志类型logType为空，参数异常。");
            throw new IllegalArgumentException();
        } else {
            try {
                if (startDate == null || "".equals(startDate) || endDate == null || "".equals(endDate)) {
                    //文件名
                    fileName = createExcel(getLogListByIdsAndType("", logType));
                } else {
                    //按日期与日志类型获取所有日志信息并写入excel
                    fileName = createExcel(operLogDao.logListByDateByPage(logType, startDate, endDate, 0, 0, false));
                }
                //远程服务路径，如：http://localhost:8756/service/upload_dir/20A9FEAF9866F53153FC42AB8FE7542C.xls
                filePath = getLogDownLoadPath(fileName);
                logger.debug("根据日期范围及日志类型导出的日志文件下载路径filePath=" + filePath);
            } catch (IOException e) {
                logger.error("根据日期与日志类型导出日志信息异常", e);
            }
            return filePath;
        }
    }

    /**
     * 创建excel文档，并写入日志数据，返回文件名
     * @param logList 日志列表
     * @return 输出到浏览器
     * @throws IOException
     */
    private String createExcel(List<Map<String,Object>> logList) throws IOException {
        Date date=new Date();
        String fileName="systemlog-"+String.valueOf(date.getTime())+".xls";
        FileOutputStream out=null;
        String filePath=WebRootUtil.getWebRoot()+"upload_dir/"+ fileName;
        logger.debug("日志文件filePath================"+filePath);
        try {
            File file=new File(filePath);
            if(!file.exists()){
                file.createNewFile();
            }
            out = new FileOutputStream(filePath);
            Workbook wb = new HSSFWorkbook();
            Sheet sh = wb.createSheet();
            HSSFCellStyle cellStyle = (HSSFCellStyle) wb.createCellStyle();
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            Row row = sh.createRow(0);
            String[] headInfo = LOG_EXCEL_TITLE.split("@@");
            for (int i = 0; i < headInfo.length; i++) {
                Cell cell = row.createCell(i);
                sh.setColumnWidth((short)i,(short)80*80);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(headInfo[i]);
            }
            saveLogToExcel(sh,cellStyle,logList);
            wb.write(out);
        }catch (Exception e){
            logger.error("excel文件写入数据异常，文件path="+filePath,e);
        }finally {
            out.close();
        }
        return fileName;
    }

    /**
     * 读取日志数据，写入到excel
     * @param sh
     * @param cellStyle
     * @param list
     */
    @Override
    public void saveLogToExcel(Sheet sh, HSSFCellStyle cellStyle, List<Map<String, Object>> list){
        int rownum=0;
        if(list!=null && !list.isEmpty()){
            Map<String,Object> userInfo=userHttpService.getAllUserInfo();
            for(Map<String,Object> logMap:list) {
                rownum++;
                Row row = sh.createRow(rownum);
                Cell indexCell = row.createCell(0);
                indexCell.setCellStyle(cellStyle);
                sh.setColumnWidth((short) 0, (short) 80*80);
                //编号
                indexCell.setCellValue(String.valueOf(logMap.get("id")));
                //创建单元格
                Cell  userNameCell= row.createCell(1);
                userNameCell.setCellStyle(cellStyle);
                sh.setColumnWidth((short) 0, (short) 80*80);
                String userId=String.valueOf(logMap.get("userId"));
                String userName="";
                if(userInfo!=null && !userInfo.isEmpty() && userInfo.get(userId)!=null){
                    Map<String,String> user=(Map<String,String>)userInfo.get(userId);
                    userName=user.get("userRealName")==null?"":user.get("userRealName");
                }else{
                    userName="";
                }
                //用户名称
                userNameCell.setCellValue(userName);

                Cell  timeCell= row.createCell(2);
                timeCell.setCellStyle(cellStyle);
                sh.setColumnWidth((short) 0, (short) 80*80);
                String time=logMap.get("time").toString();
                timeCell.setCellValue(time);

                Cell  moduleCell= row.createCell(3);
                moduleCell.setCellStyle(cellStyle);
                sh.setColumnWidth((short) 0, (short) 80*80);
                String moduleName=logMap.get("moduleName").toString();
                moduleCell.setCellValue(moduleName);

                Cell  contentCell= row.createCell(4);
                contentCell.setCellStyle(cellStyle);
                sh.setColumnWidth((short) 0, (short) 80*80);
                String content=logMap.get("content").toString();
                contentCell.setCellValue(content);

                Cell  typeCell= row.createCell(5);
                typeCell.setCellStyle(cellStyle);
                sh.setColumnWidth((short) 0, (short) 80*80);
                String type=String.valueOf(logMap.get("type"));
                if("0".equals(type)){
                    type=OPER_LOG_TYPE;
                }else{
                    type=SYSTEM_LOG_TYPE;
                }
                //日志类型
                typeCell.setCellValue(type);
            }
        }
    }

    /**
     * 获取日志文件下载地址
     * @param fileName
     * @return 文件下载路径
     */
    private String getLogDownLoadPath(String fileName){
        //文件下载路径;
        return HttpRequestUtil.getServerUrlPath()+"upload_dir/"+fileName;
    }

    /**
     * 过去所有日志
     * @return
     */
    @Override
    public List<Map<String, Object>> getAllLogs() {
        return operLogDao.getAllLogs();
    }
}
