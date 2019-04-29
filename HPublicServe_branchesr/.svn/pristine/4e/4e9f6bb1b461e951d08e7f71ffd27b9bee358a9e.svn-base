package com.honghe.web.user.util;

import com.honghe.service.client.Result;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 * Excel工具
 */
public final class ExcelTools {

    /**
     * 导出锁定，防止导出过多而内存溢出
     */
    private static final Object EXPORT_LOCK = new Object();

    /**
     * 保存文件扩展名
     */
    public static final String EXCEL_EXT_NAME = ".xls";

    /**
     * 允许写入的最大行数
     */
    public static final int MAX_ROW = 65536;

    /**
     * 允许写入的最大sheet数
     */
    public static final int MAX_SHEET = 255;

    /**
     * 默认最大行数：10000行
     */
    public int default_row = 10000;

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
        // sheet.setDefaultColumnWidth((short) 15);

        /*
         * 一、产生表头
         */
        HSSFRow tableTitle = sheet.createRow(0);
        HSSFCell cellTitle = tableTitle.createCell(0);
        // 生成一个样式
        HSSFCellStyle styleTitle = workbook.createCellStyle();
        // 设置这些样式
        // styleTitle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        // styleTitle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
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

        /*
         * 二、产生表格列标题
         */
        HSSFRow lieRow = sheet.createRow(1);// 在第二行创建
        // 设置列名称
        // HSSFCell cells[] = new HSSFCell[headers.length];
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = lieRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        /*
         * 三、遍历数据集，写入数据
         */
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

        /*
         * 四、写入数据流
         */
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


        /*
         * 一、产生表头
         */
        HSSFRow tableTitle = sheet.createRow(0);
        HSSFCell cellTitle = tableTitle.createCell(0);
        // 生成一个样式
        HSSFCellStyle styleTitle = workbook.createCellStyle();
        // 设置这些样式
        // styleTitle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        // styleTitle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
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

        /*
         * 二、产生表格列标题
         */
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

        /*
         * 三、遍历数据集，写入数据
         */
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
//		sheet.autoSizeColumn((short)0); //调整第一列宽度
//		sheet.autoSizeColumn((short)1); //调整第二列宽度
//		sheet.autoSizeColumn((short)2); //调整第三列宽度
//		sheet.autoSizeColumn((short)3); //调整第四列宽度
//		sheet.autoSizeColumn((short)4); //调整第五列宽度
//		sheet.autoSizeColumn((short)5); //调整第六列宽度
        /*
         * 四、写入数据流
         */
        OutputStream stream = new FileOutputStream(filepath);
        workbook.write(stream);
        stream.close();

        return filepath;

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
    public static File zipFilesNoFloder(File[] files, String floderName, File zipfile)
            throws IOException {
        ZipOutputStream outputStream = new ZipOutputStream(
                new FileOutputStream(zipfile));

        for (int i = 0; i < files.length; i++) {
            //处理压缩文件后内部文件结构
            String entryname = ("/" + floderName == null ? "" : floderName + "/")
                    + files[i].getName();
//			entryname = new String(entryname.getBytes("UTF-8"),"GBK");
            //创建压缩条目（压缩文件内部名称）
            ZipEntry entry = new ZipEntry(entryname);
//			System.out.println(entry.getName());
            outputStream.putNextEntry(entry);
            //写入IO流
            FileInputStream in = new FileInputStream(files[i]);
            int b;
            while ((b = in.read()) != -1) {
                outputStream.write(b);
            }
            in.close();
            //关闭当前的条目，以写入下一个条目
            outputStream.closeEntry();
        }
        //完成并关闭流
        outputStream.finish();
        return zipfile;
    }

    /**
     * 导出zip压缩包，内涵多个拆分的xls文件
     *
     * @throws java.io.IOException
     */
    public String exportTableByTempPathAndFilesToZip(String title,
                                                     String[] headers, List<String[]> list) throws IOException {

        // 获取操作系统缓存根目录
        String root_path = "C:/xls_export/";

        // 导出文件，获取到文件保存路径
        File root = new File(root_path);
        if (!root.isDirectory()) {
            root.mkdirs();
        }
        String fileName = "file_" + title;
        // 导出文件
        String[] filepaths = exportTableByFile(title, headers, list, root_path,
                fileName);

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
     * 下载Excel文件的方法
     */
    public void downExcel(String filePath, boolean isMould, HttpServletResponse response) {
        File file = new File(filePath);
        String downFilename = "";
        if (!isMould) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
            Date date = new Date();
            String time = sdf.format(date);
            downFilename = "用户列表" + time + ".xls";
        } else {
            downFilename = filePath.substring(filePath.lastIndexOf(File.separator) + 1);
        }
        response.setContentType("application/x-msdownload");
        response.setContentLength((int) file.length());
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(downFilename.getBytes("GBK"), "ISO8859_1"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            outputStream = response.getOutputStream();
            inputStream = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int i = -1;
            while ((i = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, i);
            }
            outputStream.flush();
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传用户表格到/upload文件夹中
     *
     * @param req
     * @return
     */
    public String uploadFile(String userOrAgency, HttpServletRequest req, HttpServletResponse response) {
        String msg = "";
        try {
            //设置编码
            req.setCharacterEncoding("utf-8");
            //获得磁盘文件条目工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //获取文件需要上传到的路径
            //updated by HOUJT 2018/6/20 15:43 HttpServletRequest的getRealPath方法已经被ServletContext的getRealPath取代
            String path = req.getSession().getServletContext().getRealPath("/upload");
//			String path = req.getRealPath("/upload");
            //如果没以下两行设置的话，上传大的 文件 会占用 很多内存，
            //设置暂时存放的 存储室 , 这个存储室，可以和 最终存储文件 的目录不同
            /**
             * 原理 它是先存到 暂时存储室，然后在真正写到 对应目录的硬盘上，
             * 按理来说 当上传一个文件时，其实是上传了两份，第一个是以 .tem 格式的
             * 然后再将其真正写到 对应目录的硬盘上
             */
            factory.setRepository(new File(path));
            //设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室
            factory.setSizeThreshold(1024 * 1024);
            //高水平的API文件上传处理
            ServletFileUpload upload = new ServletFileUpload(factory);
            //可以上传多个文件
            List<FileItem> list = (List<FileItem>) upload.parseRequest(req);

            for (FileItem item : list) {
                //获取表单的属性名字
                String name = item.getFieldName();

                //如果获取的 表单信息是普通的 文本 信息
                if (item.isFormField()) {
                    //获取用户具体输入的字符串 ，名字起得挺好，因为表单提交过来的是 字符串类型的
                    String value = item.getString();

                    req.setAttribute(name, value);
                }
                //对传入的非 简单的字符串进行处理 ，比如说二进制的 图片，电影这些
                else {
                    /**
                     * 以下三步，主要获取 上传文件的名字
                     */
                    //获取路径名
                    String value = item.getName();
                    //索引到最后一个反斜杠
                    int start = value.lastIndexOf("\\");
                    //截取 上传文件的 字符串名字，加1是 去掉反斜杠，
                    String filename = value.substring(start + 1);

                    req.setAttribute(name, filename);
                    //真正写到磁盘上
                    //它抛出的异常 用exception 捕捉
                    //item.write( new File(path,filename) );//第三方提供的
                    //手动写的
                    File tempfile = new File(path);
                    if (!tempfile.exists()) {
                        tempfile.mkdirs();
                    }
                    OutputStream out = new FileOutputStream(new File(path, filename));
                    InputStream in = item.getInputStream();
                    int length = 0;
                    byte[] buf = new byte[1024];
                    // in.read(buf) 每次读到的数据存放在   buf 数组中
                    while ((length = in.read(buf)) != -1) {
                        //在   buf 数组中 取出数据 写到 （输出流）磁盘上
                        out.write(buf, 0, length);

                    }
                    if (in != null) {
                        in.close();
                    }
                    if (out != null) {
                        out.close();
                    }
                    File file = new File(path, filename);
                    String fileName = HttpServiceUtil.uploadFile(file);
                    Map params = new HashMap();
                    params.put("fileName", fileName);
                    Result res;
                    if ("user".equals(userOrAgency)) {
                        res = HttpServiceUtil.userService("userImportExcel", params);
                        msg = res.getValue().toString();
                    } else if ("student".equals(userOrAgency)) {
                        res = HttpServiceUtil.userService("studentImportExcel", params);
                        msg = res.getValue().toString();
                    } else if ("teacher".equals(userOrAgency)) {
                        res = HttpServiceUtil.userService("teacherImportExcel", params);
                        msg = res.getValue().toString();
                    } else if ("parent".equals(userOrAgency)) {
                        res = HttpServiceUtil.userService("parentImport", params);
                        msg = res.getValue().toString();
                    } else if ("image".equals(userOrAgency)) {
                        res = HttpServiceUtil.userService("userUpdate", params);
                        msg = res.getValue().toString();
                    } else if ("campus".equals(userOrAgency)) {
                        res = HttpServiceUtil.adService("campusImport", params);
                        msg = res.getValue().toString();
                    }
                }
            }
            return msg;

        } catch (FileUploadException e) {
            return "fileError";
        } catch (Exception e) {
            return "fileError";
        }
    }


}
