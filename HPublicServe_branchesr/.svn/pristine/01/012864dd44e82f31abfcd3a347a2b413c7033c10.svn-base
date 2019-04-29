package com.honghe.user.util;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 * Excel工具
 */
public final class ExcelTools {

    private final Logger logger = Logger.getLogger(ExcelTools.class);


    // 导出锁定，防止导出过多而内存溢出
    private static final Object EXPORT_LOCK = new Object();
    // 保存文件扩展名
    public static final String EXCEL_EXT_NAME = ".xls";
    // 允许写入的最大行数
    public static final int MAX_ROW = 65536;
    // 允许写入的最大sheet数
    public static final int MAX_SHEET = 255;
    // 默认最大行数：10000行
    public int default_row = 10000;
    //操作成功
    public static String RESULT_SUCCESS = "success";
    //文件读取错误
    public static String RESULT_FILEERROR = "fileError";
    //文件删除错误
    public static String RESULT_FILEDELETEERROR = "fileDeleteError";
    //导入失败
    public static String RESULT_IMPORTERROR = "importError";

    /**
     * 创建一个实例
     */
    public static ExcelTools createExport() {
        return new ExcelTools();
    }

    /**
     * 修改默认最大导出行数，最大不允许超过65536
     */
    public ExcelTools setExportRow(int row) {
        if (row > MAX_ROW) {
            default_row = MAX_ROW;
        } else if (row > 0) {
            default_row = row;
        }
        return this;
    }

    /**
     * 导出excel文件流
     *
     * @param title   标题
     * @param headers 列名
     * @param list    行数据，必须按照列名的顺序排列
     * @param stream  输出文件流
     * @throws java.io.IOException
     */
    public void exportTable(String title, String[] headers,
                            List<String[]> list, OutputStream stream) throws IOException {

        // 禁止大于单文件允许最大行数
        if (list.size() > default_row) {
            throw new IOException("单文件导出行数超过最大限制：" + list.size() + "/"
                    + MAX_ROW + "，请使用多文件导出命令！");
        }

        // 创建工作对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建sheet
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        /*
         * 一、产生表头
         */
        HSSFRow tableTitle = sheet.createRow(0);
        HSSFCell cellTitle = tableTitle.createCell(0);
        // 生成一个样式
        HSSFCellStyle styleTitle = workbook.createCellStyle();
        // 设置这些样式
        styleTitle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styleTitle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        styleTitle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        styleTitle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        styleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
        styleTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 设置表头样式
        cellTitle.setCellStyle(styleTitle);
        // 设置表头内容
        cellTitle.setCellValue(title);

        // 合并单元格，合并标题的单元格
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headers.length - 1));

        // 二、产生表格列标题
        HSSFRow lieRow = sheet.createRow(1);// 在第二行创建
        // 设置列名称
        // HSSFCell cells[] = new HSSFCell[headers.length];
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = lieRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        /***************************************三、遍历数据集，写入数据**********************************/
        // 设置单元格样式，
        HSSFCellStyle dataStyle = workbook.createCellStyle();
        HSSFDataFormat dataFormat = workbook.createDataFormat();
        dataStyle.setDataFormat(dataFormat.getFormat("@"));// 设置单元格格式为文本

        for (int i = 0; i < list.size(); i++) {
            HSSFRow dataRow = sheet.createRow(i + 2);// 每行的起始数+2
            // HSSFCell datas[] = new HSSFCell[headers.length];
            // 取出一个数据
            String[] dataArr = list.get(i);
            for (int j = 0; j < headers.length; j++) {
                HSSFCell dataCell = dataRow.createCell(j);
                dataCell.setCellValue(dataArr[j]);
                dataCell.setCellStyle(dataStyle);
            }
            /*
             * if (i % 100 == 0) { System.out.println("正在写入数据：" + i); }
             */
        }

        /********************************* 四、写入数据流****************************/
        workbook.write(stream);
        stream.close();
    }

    private String exportExcelFile(String title, String[] headers,
                                   List<String[]> list, String filepath) throws IOException {

        // 创建工作对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建sheet
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        // sheet.setDefaultColumnWidth((short) 15);

        /************************************** 一、产生表头****************************/
        HSSFRow tableTitle = sheet.createRow(0);
        HSSFCell cellTitle = tableTitle.createCell(0);
        // 生成一个样式
        HSSFCellStyle styleTitle = workbook.createCellStyle();
        // 设置这些样式
        styleTitle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styleTitle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        styleTitle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        styleTitle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        styleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
        styleTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 设置表头样式
        cellTitle.setCellStyle(styleTitle);

        //创建字体样式
        HSSFFont titleFont = workbook.createFont();
        HSSFFont cellFont = workbook.createFont();
        HSSFFont dataFont = workbook.createFont();
        titleFont.setFontHeightInPoints((short) 18);//设置字体大小
        cellFont.setFontHeightInPoints((short) 12);
        dataFont.setFontHeightInPoints((short) 10);

        //设置表头字体
        styleTitle.setFont(titleFont);

        // 设置表头内容
        cellTitle.setCellValue(title);
        // 合并单元格，合并标题的单元格
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headers.length - 1));

        /*************************************** 二、产生表格列标题*****************************/
        HSSFRow lieRow = sheet.createRow(1);// 在第二行创建

        // 设置列名称
        // HSSFCell cells[] = new HSSFCell[headers.length];
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = lieRow.createCell(i);
            //创建格式
            HSSFCellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setFont(cellFont);
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//左右居中
            //设置格式
            cell.setCellStyle(cellStyle);
            cell.setCellValue(headers[i]);

        }

        /**************************************** 三、遍历数据集，写入数据*************************/
        // 设置单元格样式，
        HSSFCellStyle dataStyle = workbook.createCellStyle();

        dataStyle.setFont(dataFont);
        HSSFDataFormat dataFormat = workbook.createDataFormat();
        dataStyle.setDataFormat(dataFormat.getFormat("@"));// 设置单元格格式为文本

        for (int i = 0; i < list.size(); i++) {
            HSSFRow dataRow = sheet.createRow(i + 2);// 每行的起始数+2
            // HSSFCell datas[] = new HSSFCell[headers.length];
            // 取出一个数据
            String[] dataArr = list.get(i);
            for (int j = 0; j < headers.length; j++) {
                HSSFCell dataCell = dataRow.createCell(j);
                dataCell.setCellValue(dataArr[j]);
                dataCell.setCellStyle(dataStyle);
            }

            /*
             * if (i == list.size()) { System.out.println("正在写入数据：" + i); }
             */
        }
        /**
         * 调整列宽为自动列宽
         * 用for循环添加 有多少列就将n改为多少
         */
        int n = 13;
        for (int i = 0; i < n; i++) {
            sheet.autoSizeColumn((short) i);
        }
        /************************************* 四、写入数据流***********************/

        OutputStream stream = new FileOutputStream(filepath);
        workbook.write(stream);
        stream.close();
        File failedFile = new File(filepath);
        String str = failedFile.getAbsolutePath();
        return str;

    }

    /**
     * 导出excel文件流，分文件导出
     *
     * @param title    标题
     * @param headers  列名
     * @param list     行数据，必须按照列名的顺序排列
     * @param path     文件输出路径
     * @param fileName 文件名（不含扩展名）
     * @throws java.io.IOException
     */
    public String[] exportTableByFile(String title, String[] headers,
                                      List<String[]> list, String path, String fileName)
            throws IOException {
        // 多文件导出锁定
        synchronized (EXPORT_LOCK) {
            // 文件路径
            String[] filepaths = null;

            // 如果大于默认行数。则分文件进行
            if (list.size() > default_row) {
                // 进行分文件筛选
                int page = list.size() / default_row;
                if (list.size() % default_row != 0) {// 如果有余数则加1页
                    page += 1;
                }

                // 创建文件数组
                filepaths = new String[page];

                for (int i = 0; i < page; i++) {
                    List<String[]> templist = null;
                    if (i == page - 1) {
                        templist = list.subList(i * default_row,
                                list.size() - 1);
                    } else {
                        templist = list.subList(i * default_row, (i + 1)
                                * default_row - 1);
                    }
                    // 导出文件
                    String filepath = exportExcelFile(title, headers, templist,
                            path + fileName + "_" + (i + 1) + "-" + page
                                    + EXCEL_EXT_NAME);
                    // 将文件名写入数组
                    filepaths[i] = filepath;
                }
            } else {
                // 直接导出
                String filepath = exportExcelFile(title, headers, list, path
                        + fileName + EXCEL_EXT_NAME);
                // 将文件名写入数组
                filepaths = new String[]{filepath};
            }
            // 返回文件数组
            return filepaths;
        }
    }


    /**
     * 压缩文件，禁止使用文件夹
     *
     * @param files      需要压缩的文件
     * @param floderName 压缩包内的文件夹名称，如果为null则不创建文件夹
     *                   压缩后的文件路径
     * @return 压缩后的文件
     * @throws java.io.IOException
     */
    @SuppressWarnings("Duplicates")
    public static File zipFilesNoFloder(File[] files, String floderName, File zipfile)
            throws IOException {
        ZipOutputStream outputStream = null;
        try {
            outputStream = new ZipOutputStream(new FileOutputStream(zipfile));

            for (int i = 0; i < files.length; i++) {
                //处理压缩文件后内部文件结构
                String entryname = ("/" + floderName == null ? "" : floderName + "/")
                        + files[i].getName();
                //创建压缩条目（压缩文件内部名称）
                ZipEntry entry = new ZipEntry(entryname);
                outputStream.putNextEntry(entry);
                //写入IO流
                FileInputStream in = null;
                try {
                    in = new FileInputStream(files[i]);
                    int b;
                    while ((b = in.read()) != -1) {
                        outputStream.write(b);
                    }
                }catch (Exception e){
                }finally {
                    if (in != null) {
                        in.close();
                    }

                }
                //关闭当前的条目，以写入下一个条目
                outputStream.closeEntry();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            //完成并关闭流
            outputStream.finish();
        }
        return zipfile;
    }

    /**
     * 导出zip压缩包，内涵多个拆分的xls文件
     *
     * @throws java.io.IOException
     */
    public String exportTableByTempPathAndFilesToZip(String root_path, String title,
                                                     String[] headers, List<String[]> list) throws IOException {

        // 导出文件，获取到文件保存路径
        File root = new File(root_path);
        if (!root.isDirectory()) {
            root.mkdirs();
        }
        String fileName = "file_" + title;
        // 导出文件
        String[] filepaths = exportTableByFile(title, headers, list, root_path, fileName);

        // 转换文件数组
        File[] files = new File[filepaths.length];
        for (int i = 0; i < filepaths.length; i++) {
            files[i] = new File(filepaths[i]);
        }

        // 创建导出压缩文件路径
        File zipfile = new File(root_path + title + fileName + ".zip");
        File finishZip = zipFilesNoFloder(files, fileName, zipfile);

        // 删除xls文件
        for (int i = 0; i < files.length; i++) {
            FileUtils.forceDelete(files[i]);
        }

        // 返回文件保存路径
        return finishZip.getPath();
    }

    /**
     * 导入的回调
     *
     * @author xiaanming
     */
    public interface ImportCallBack {
        /**
         * 处理导入需要导入的工作表的数据库
         *
         * @param sheet 工作表
         * @return
         * @throws Exception
         */
        public List<Object> ImportHandler(Sheet sheet, CellStyle cellStyle) throws Exception;

        /**
         * 将导入失败的数据库回写到excel文件中
         *
         * @param objList 需要插入的数据
         * @param headers 表头
         * @return
         * @throws Exception
         */
        public String exportExcel(List<Object> objList, String fileName, String[] headers) throws Exception;
    }


    /**
     * 处理将excel对象插入到数据库的方法
     *
     * @param file           导入的文件
     * @param fileName       导入失败写入的excel文件的名字
     * @param headers        表头
     * @param importCallBack 导入的处理类
     * @return
     */
    public String importObj(File file, String fileName, String[] headers, ImportCallBack importCallBack) {
        String re_value = RESULT_SUCCESS;
        InputStream stream = null;
        try {
            // 文件流指向excel文件
            stream = new FileInputStream(file);
            //HSSF只能打开2003，XSSF只能打开2007，WorkbookFactory.create兼容以上两个版本
            Workbook workbook = WorkbookFactory.create(stream);

            //设置CELL格式为文本格式 防止科学计数问题
            CellStyle cellStyle = workbook.createCellStyle();
            DataFormat format = workbook.createDataFormat();
            cellStyle.setDataFormat(format.getFormat("@"));

            Sheet sheet = workbook.getSheetAt(0);// 得到工作表
            List<Object> failedList = importCallBack.ImportHandler(sheet, cellStyle);
            if (failedList.size() > 0) {
                re_value = importCallBack.exportExcel(failedList, fileName, headers);
            }
            FileUtils.forceDelete(file);
        } catch (FileNotFoundException e) {
            re_value = RESULT_FILEERROR;
            logger.error(re_value, e);
        } catch (IOException e) {
            re_value = RESULT_FILEDELETEERROR;
            logger.error(re_value, e);
        } catch (Exception e) {
            re_value = RESULT_IMPORTERROR;
            logger.error(re_value, e);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    re_value = RESULT_IMPORTERROR;
                    logger.error(re_value, e);
                }
            }
        }
        return re_value;
    }


//    /**
//     * 解析表格内容 并添加到数据库中
//     *
//     * @param file
//     */
//    public String importUsers(File file) {
//        InputStream stream = null;
//        try {
//            // 文件流指向excel文件
//            stream = new FileInputStream(file);
//            //HSSF只能打开2003，XSSF只能打开2007，WorkbookFactory.create兼容以上两个版本
//            Workbook workbook = WorkbookFactory.create(stream);
//            Sheet sheet = workbook.getSheetAt(0);// 得到工作表
//            Row row = null;// 对应excel的行
//            int totalRow = sheet.getLastRowNum();// 得到excel的总记录条数
//            //逐行写入数据库
//            Result result;
//            String currentType = "";// 保存临时用户身份 用于减少数据库查找次数
//            String currentTypeId = "";// 保存临时用户身份ID
//            List<Map<String, String>> failedList = new LinkedList<>();
//            List<Map<String, String>> campusList = new ArrayList<>();
//            CampusDao campusDao = new CampusDao();
//            campusList = campusDao.getAllCampus();
//            for (int i = 2; i <= totalRow; i++) {
//                //获取单行数据
//                row = sheet.getRow(i);
//                //获取所有数据
//                Map param = new HashMap();
//                //需要判断每个单元格是否为空
//                param.put("cmd_op", "userAdd");
//                param.put("userPwd", "111111");//每个用户默认密码111111
//                param.put("userRealName", row.getCell(0) == null ? "" : row.getCell(0).toString());
//                param.put("userNum", row.getCell(1) == null ? "" : row.getCell(1).toString());
//                param.put("userMobile", row.getCell(2) == null ? "" : row.getCell(2).toString());
//                param.put("userEmail", row.getCell(3) == null ? "" : row.getCell(3).toString());
//                param.put("userGender", row.getCell(4) == null ? "" : row.getCell(4).toString());
//                param.put("userBirthday", row.getCell(5) == null ? "" : row.getCell(5).toString());
//                param.put("userAddress", row.getCell(6) == null ? "" : row.getCell(6).toString());
//                param.put("userType", row.getCell(7) == null ? "" : row.getCell(7).toString());
//
//                param.put("agencyNum", row.getCell(8) == null ? "" : row.getCell(8).toString());
//                param.put("userCard", row.getCell(9) == null ? "" : row.getCell(9).toString());
//                param.put("userCourse", row.getCell(10) == null ? "" : row.getCell(10).toString());
//                param.put("userInfo", row.getCell(11) == null ? "" : row.getCell(11).toString());
//                //此处判断若学号 手机 邮箱都没有时认为参数错误
//                if ("".equals(param.get("userNum")) && "".equals(param.get("userMobile")) && "".equals(param.get("userEmail"))) {
//                    System.out.println("必须输入学号/手机/邮箱");
//                    failedList.add(param);
//                    continue;
//                }
//                //此处需要添加查询身份ID
//                String user_type = row.getCell(7) == null ? "" : row.getCell(7).toString();
//                if (!"".equals(user_type)) {
//                    if (user_type.equals(currentType)) {
//                        user_type = currentTypeId;
//                    } else {
//                        Map map = UserDao.INSTANCE.getUserTypeDao().findByTypeName(user_type);
//                        //判断若没有此身份时认为参数错误
//                        if (map.size() == 0) {
//                            System.out.println("没有这个身份");
//                            failedList.add(param);
//                            continue;
//                        }
//                        //将上一个导入的用户的身份信息保存 为了减少查询数据库的次数
//                        if (map.size() != 0) {
//                            currentType = user_type;
//                            user_type = map.get("typeId").toString();
//                            currentTypeId = map.get("typeId").toString();
//                        }
//                    }
//                }
//                param.put("userType", user_type);
//                //判断性别后转为数据库中代号 1男 2女 0未知
//                String sex = row.getCell(4) == null ? "" : row.getCell(4).toString();
//                if ("男".equals(sex)) {
//                    sex = "1";
//                } else if ("女".equals(sex)) {
//                    sex = "2";
//                } else {
//                    sex = "0";
//                }
//                param.put("userGender", sex);
//                //执行添加操作
//                if (UserDao.INSTANCE.find(param)) {
//                    //用户信息有重复，判断是否要修改，用编号判断，若并不是编号错误则返回到错误文档
//                    System.out.println("用户信息有重复");
//                    //按学号查找用户信息
//                    String userId_ = UserDao.INSTANCE.findByUserNum(row.getCell(1) == null ? "" : row.getCell(1).toString());
//
//                    //若不是学号相同的用户则放入失败列表
//                    if ("".equals(userId_)) {
//                        //若学号不相同则把身份和性别还原后放入失败列表
//                        param.put("userGender", row.getCell(4) == null ? "" : row.getCell(4).toString());
//                        param.put("typeName", row.getCell(7) == null ? "" : row.getCell(7).toString());
//                        failedList.add(param);
//                        continue;
//                    } else {
//                        // 不修改admin信息
//                        if ("1".equals(userId_)) {
//                            failedList.add(param);
//                            continue;
//                        }
//                        //修改user表属性
//                        param.put("cmd_op", "userUpdate");
//                        param.put("userId", userId_);
//                        if (UserDao.INSTANCE.findExceptThis(param)) {
//                            param.put("userGender", row.getCell(4) == null ? "" : row.getCell(4).toString());
//                            failedList.add(param);
//                            continue;
//                        }
//                        UserDao.INSTANCE.update(param);
//                        // 通过身份建立用户与角色关系
//                        if (!"".equals(user_type)) {
//                            makeUser2roleByType(user_type, userId_);
//                        }
//                        // 添加用户与机构关系
//                        String campusId = row.getCell(8) == null ? "" : row.getCell(8).toString();
//                        campusDao.deleteUserAgencyRelation(userId_);
//                        try {
//                            if (hasCampus(campusList, campusId)) {
//                                campusDao.addUserAgencyRelation(campusId, userId_);
//                            }
//                        } catch (java.lang.ClassCastException e) {
//                            continue;
//                        }
//                    }
//                } else {
//                    //信息不重复时直接保存用户角色关系
//                    long userId = UserDao.INSTANCE.add(param);
//                    // 通过身份建立用户与角色关系
//                    if (!"".equals(user_type)) {
//                        makeUser2roleByType(user_type, String.valueOf(userId));
//                    }
//                    // 添加用户与机构关系
//                    String campusId = row.getCell(8) == null ? "" : row.getCell(8).toString();
//                    try {
//                        if (hasCampus(campusList, campusId)) {
//                            campusDao.deleteUserAgencyRelation(String.valueOf(userId));
//                            campusDao.addUserAgencyRelation(campusId, String.valueOf(userId));
//                        }
//                    } catch (java.lang.ClassCastException e) {
//                        continue;
//                    }
//                }
//            }
//            if (failedList.size() > 0) {
//                File failedFile = new File(exportExcel(failedList));
//                return failedFile.getAbsolutePath();
//            }
//            FileUtils.forceDelete(file);
//        } catch (FileNotFoundException e) {
//            return "fileError";
//        } catch (IOException ioe) {
//            return "fileDeleteError";
//        } catch (Exception ex) {
//            return "fileError";
//        } finally {
//            if (stream != null) {
//                try {
//                    stream.close();
//                } catch (IOException e) {
//                    return "fileError";
//                }
//            }
//
//        }
//        return "success";
//    }


//    /**
//     * 生成excel文件
//     *
//     * @param userList 用户列表
//     */
//    private String exportExcel(List<Map<String, String>> userList, String[] headers) {
//        ExcelTools excel = new ExcelTools();
//        // 生成表头
////        String[] headers = {"姓名", "编号*", "手机*", "邮箱*", "性别", "生日", "地址", "身份", "机构编号", "卡号", "学科", "简介"};
//        List<String[]> excelList = new ArrayList<>();
//        // 插入数据
//        for (int i = 0; i < userList.size(); i++) {
//            String[] strList = new String[12];
//            strList[0] = userList.get(i).get("userRealName") == null ? "" : userList.get(i).get("userRealName");
//            strList[1] = userList.get(i).get("userNum") == null ? "" : userList.get(i).get("userNum");
//            strList[2] = userList.get(i).get("userMobile") == null ? "" : userList.get(i).get("userMobile");
//            strList[3] = userList.get(i).get("userEmail") == null ? "" : userList.get(i).get("userEmail");
//            String sex = userList.get(i).get("userGender") == null ? "" : userList.get(i).get("userGender");
//            if (sex.equals("1")) {
//                sex = "男";
//            } else if (sex.equals("2")) {
//                sex = "女";
//            } else {
//                sex = "";
//            }
//            strList[4] = sex;
//            strList[5] = userList.get(i).get("userBirthday") == null ? "" : userList.get(i).get("userBirthday");
//            strList[6] = userList.get(i).get("userAddress") == null ? "" : userList.get(i).get("userAddress");
//
//            strList[7] = userList.get(i).get("typeName") == null ? "" : userList.get(i).get("typeName");
//            strList[8] = userList.get(i).get("agencyNum") == null ? "" : userList.get(i).get("angencyNum");
//            strList[9] = userList.get(i).get("userCard") == null ? "" : userList.get(i).get("userCard");
//            strList[10] = userList.get(i).get("userCourse") == null ? "" : userList.get(i).get("userCourse");
//            strList[11] = userList.get(i).get("userInfo") == null ? "" : userList.get(i).get("userInfo");
//            excelList.add(strList);
//        }
//        String filePath = "";
//        try {
//            filePath = excel.exportTableByFile("用户列表", headers, excelList, "", "tempDownExcel")[0];
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return filePath;
//
//    }

}

