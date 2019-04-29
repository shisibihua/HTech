package com.honghe.device.DBHelper;

import com.honghe.device.dao.DeviceDao;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by hthwx on 2015/3/14.
 */
@WebServlet(urlPatterns = "/sqliteFilesDownloadService", loadOnStartup = 1)
public class SqliteFilesDownLoad extends HttpServlet {
    final static Logger logger = Logger.getLogger(SqliteFilesDownLoad.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("ISO8859-1");
        res.setContentType("text/html;charset=utf-8");
        String path = getServletContext().getRealPath("/");
        DeviceDao.INSTANCE.generateAllSqLiteByDspecId(path);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
