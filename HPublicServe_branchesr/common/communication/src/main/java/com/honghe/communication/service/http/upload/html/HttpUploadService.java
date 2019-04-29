package com.honghe.communication.service.http.upload.html;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: 北京鸿合盛视数字媒体技术有限公司</p>
 *
 * @author zhanghongbin
 * @date 2015/8/20
 */
@WebServlet(urlPatterns = "/cloud/httpUploadService")
public class HttpUploadService extends HttpServlet {

    private String getExtensionName(String filename) {
        if (filename != null && filename.length() > 0) {
            int dot = filename.lastIndexOf(46);
            if (dot > -1 && dot < filename.length() - 1) {
                return filename.substring(dot + 1).toLowerCase();
            }
        }

        return filename;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        String upload_dir = req.getRealPath("") + File.separator + "upload_dir";
        File file = new File(upload_dir);
        if (!file.exists()) {
            file.mkdir();
        }

        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory(5 * 1024, file);
        ServletFileUpload upload = new ServletFileUpload(diskFileItemFactory);
        upload.setHeaderEncoding("UTF-8");//解决文件乱码问题
        //upload.setSizeMax(1024 * 1024);
        String result = "";
        try {
            List<FileItem> items = upload.parseRequest(req);
            Iterator<FileItem> itr = items.iterator();
            while (itr.hasNext()) {
                FileItem item = itr.next();
                if (!item.isFormField()) {
                    String name = item.getName();//获得文件名 包括路径啊
                    if (name != null) {
                        name = UUID.randomUUID().toString() + "." + getExtensionName(name);
                        result = name;
                        item.write(new File(upload_dir + File.separator + name));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = "";
        }
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(result);
        printWriter.flush();
        printWriter.close();
    }
}
