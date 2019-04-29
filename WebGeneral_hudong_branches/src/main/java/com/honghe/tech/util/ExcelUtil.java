package com.honghe.tech.util;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

public class ExcelUtil {
    private static final Logger logger=Logger.getLogger(ExcelUtil.class);
    private static final SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    /**
     *读取excel
     * @param path 文件路径
     * @return list  excel数据
     * @author caoqian
     */
    public static List<Object[]> readExcel(String path) {
        List<Object[]> list=new ArrayList<Object[]>();
        try {
            if (path != null && !path.equals("")) {
                String ext = getExt(path);
                if (ext != null && !ext.equals("")) {
                    if (ext.equals("xls")) {
                        list=showExcelXls(path);
                    }
                }else{
                    return list;
                }
            }else{
                return list;
            }
        }catch(Exception e){
            logger.error("读取excel文件内容异常",e);
        }
        return list;
    }

    /**
     * 获取文件扩展名
     * @param path    文件路径
     * @return String 文件扩展名
     * @author caoqian
     */
    public static String getExt(String path) {
        if (path == null || path.equals("") || !path.contains(".")) {
            return "";
        } else {
            return path.substring(path.lastIndexOf(".") + 1, path.length());
        }
    }


    /**
     * 读取.xls文件excel
     * @param  path      文件路径
     * @author caoqian
     */
    public static  List<Object[]> showExcelXls(String path) {
        List<Object[]>  list=new ArrayList<Object[]>();
        try{
            HSSFWorkbook workbook=new HSSFWorkbook(new FileInputStream(new File(path)));//获取文件
            HSSFSheet sheet=null;
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                sheet=workbook.getSheetAt(i); //获取每个Sheet表
                for (int j = 0; j < sheet.getPhysicalNumberOfRows(); j++) {
                    HSSFRow row=sheet.getRow(j);//获取每行
                    String isNullStr="";   //判断行是否为空,如果此行数据为空则不保存
                    for (int k = 0; k < sheet.getRow(0).getPhysicalNumberOfCells(); k++) {
                        Cell cell=row.getCell(k);
                        String cellTemp="";
                        try {
                            switch (cell.getCellType()) {
                                case Cell.CELL_TYPE_STRING:
                                    cellTemp = cell.getStringCellValue();
                                    break;
                                case Cell.CELL_TYPE_NUMERIC:
                                    if (DateUtil.isCellDateFormatted(cell)) {
                                        Date date = cell.getDateCellValue();
                                        cellTemp = s.format(date);
                                    } else {
                                        DecimalFormat df = new DecimalFormat("0");
                                        cellTemp = df.format(cell.getNumericCellValue());
                                    }
                                    break;
                                case Cell.CELL_TYPE_BLANK:
                                    cellTemp="null";
                                    break;
                                default:
                                    cellTemp="null";
                                    throw new Exception("读取excel数据类型配置不正确");
                            }
                        }catch (Exception e){
                            cellTemp="null";
                        }
                        isNullStr+=cellTemp+"@@@&&&@@@";
                    }
                    if(!"".equals(isNullStr)) {
                        isNullStr=isNullStr.substring(0,isNullStr.lastIndexOf("@@@&&&@@@"));
                        Object[] obj=isNullStr.split("@@@&&&@@@");
                        if(obj.length>0) {
                            list.add(obj);
                        }
                    }
                }
            }
        }catch(Exception e){
            logger.error("读取.xls扩展名的excel异常",e);
        }
        return list;
    }
}
