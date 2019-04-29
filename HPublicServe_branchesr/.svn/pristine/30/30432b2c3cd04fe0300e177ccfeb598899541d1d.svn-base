package com.honghe.device.DBHelper;

import com.honghe.device.dao.DeviceDao;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Created by hthwx on 2015/3/6.
 */
@WebServlet(urlPatterns = "/sqlTxtDownloadService", loadOnStartup = 1)
public class SqlTxtDownload extends HttpServlet {
    final static Logger logger = Logger.getLogger(SqlTxtDownload.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("ISO8859-1");
        res.setContentType("text/html;charset=utf-8");
        String path = getServletContext().getRealPath("/");
        String cmdcode_update = req.getParameter("cmdcode_update");

        //  newsService.generateSqLiteDbFile(ip,path);
        try {
            List<Map<String, String>> list = DeviceDao.INSTANCE.querryNewsBySql("select dspec_id from cmdcode_update where cmdcode_update='" + cmdcode_update + "'");

            if (list != null && list.size() > 0) {
                Object objArr = list.get(0).get("dspec_id");
                if (objArr != null && !objArr.toString().equals("")) {
                    path += objArr.toString().trim() + ".db";
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
            logger.error("数据库文件下载，读取数据库错误",e);
//            logger.error(e.getMessage());
        }

        // 服务器绝对路径
        //  path=java.net.URLEncoder.encode(path,"utf-8");
        path = new String(path.getBytes("ISO8859-1"), "utf-8");
        // 检查文件是否存在
        File obj = new File(path);
        if (!obj.exists()) {
            //  res.setContentType("text/html;charset=utf-8");
            res.getWriter().print(path);
            res.getWriter().print("指定文件不存在！");
            logger.debug("要下载的数据库不存在，下载path为" + path);
            return;
        }

        // 读取文件名：用于设置客户端保存时指定默认文件名
        int index = path.lastIndexOf("\\"); // 前提：传入的path字符串以“\”表示目录分隔符
        String fileName = path.substring(index + 1);

        // 写流文件到前端浏览器
        ServletOutputStream out = res.getOutputStream();
        res.setHeader("Content-disposition", "attachment;filename=" + fileName);
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(path));
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            logger.debug("下载数据库文件" + fileName + "成功");
        } catch (IOException e) {
            logger.error("下载数据库文件失败",e);
//            logger.error(e.getMessage());
            throw e;
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
