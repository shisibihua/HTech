package com.honghe.communication.service.http.download;


import com.honghe.util.FileUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by zhanghongbin on 2016/11/4.
 */
@WebServlet(urlPatterns = "/cloud/httpDownloadService")
public class HttpDownloadService extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String fileName = req.getParameter("fileName");
        String filePath;
        if (fileName == null){
            filePath = req.getParameter("filePath");
            fileName = FileUtil.getFileName(filePath) + "." + FileUtil.getExtensionName(filePath);
        }else {
            filePath = req.getRealPath("") + File.separator + "upload_dir" + File.separator + fileName;
        }

        File file = new File(filePath);
        if (!file.exists()) return;
       // String name = FileUtil.getFileName(filePath);
        resp.setContentType("application/octet-stream");
        resp.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        InputStream is = null;
        OutputStream os = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            os = new BufferedOutputStream(resp.getOutputStream());
            byte[] buffer = new byte[4 * 1024]; //4k Buffer
            int read = 0;
            while ((read = is.read(buffer)) != -1) {
                baos.write(buffer, 0, read);
            }
            os.write(baos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭输出字节流和response输出流
            try {
                baos.close();
                os.close();
                is.close();
            } catch (Exception e) {

            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }


}
