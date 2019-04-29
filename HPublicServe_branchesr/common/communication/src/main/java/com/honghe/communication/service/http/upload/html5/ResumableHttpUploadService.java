package com.honghe.communication.service.http.upload.html5;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

/**
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: 北京鸿合盛视数字媒体技术有限公司</p>
 *
 * @author zhanghongbin
 * @date 2015/12/18
 */
@WebServlet(urlPatterns = "/cloud/resumableHttpUploadService")
public class ResumableHttpUploadService extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int resumableChunkNumber = getResumableChunkNumber(request);

        ResumableInfo info = getResumableInfo(request);

        RandomAccessFile raf = new RandomAccessFile(info.resumableFilePath, "rw");

        //Seek to position
        raf.seek((resumableChunkNumber - 1) * (long) info.resumableChunkSize);

        //Save to file
        InputStream is = request.getInputStream();
        long readed = 0;
        long content_length = request.getContentLength();
        byte[] bytes = new byte[1024 * 100];
        while (readed < content_length) {
            try {
                int r = is.read(bytes);
                if (r < 0) {
                    break;
                }
                raf.write(bytes, 0, r);
                readed += r;
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
        raf.close();
        //Mark as uploaded.
        info.uploadedChunks.add(new ResumableInfo.ResumableChunkNumber(resumableChunkNumber));
        if (info.checkIfUploadFinished()) { //Check if all chunks uploaded, and change filename
            ResumableInfoStorage.getInstance().remove(info);
            response.getWriter().print("Success");
        } else {
            response.getWriter().print("Upload");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int resumableChunkNumber = getResumableChunkNumber(request);
        ResumableInfo info = getResumableInfo(request);

        if (info.uploadedChunks.contains(new ResumableInfo.ResumableChunkNumber(resumableChunkNumber))) {
            response.getWriter().print("Uploaded."); //This Chunk has been Uploaded.
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private int getResumableChunkNumber(HttpServletRequest request) {
        return HttpUtils.toInt(request.getParameter("resumableChunkNumber"), -1);
    }

    private ResumableInfo getResumableInfo(HttpServletRequest request) throws ServletException {
        String upload_dir = request.getRealPath("") + File.separator + "upload_dir";
        File file = new File(upload_dir);
        if (!file.exists()) {
            file.mkdir();
        }
        int resumableChunkSize = HttpUtils.toInt(request.getParameter("resumableChunkSize"), -1);
        long resumableTotalSize = HttpUtils.toLong(request.getParameter("resumableTotalSize"), -1);
        String resumableIdentifier = request.getParameter("resumableIdentifier");
        String resumableFilename = request.getParameter("resumableFilename");
        String resumableRelativePath = request.getParameter("resumableRelativePath");
        //Here we add a ".temp" to every upload file to indicate NON-FINISHED

        String resumableFilePath = new File(upload_dir, resumableFilename).getAbsolutePath() + ".temp";

        ResumableInfoStorage storage = ResumableInfoStorage.getInstance();

        ResumableInfo info = storage.get(resumableChunkSize, resumableTotalSize,
                resumableIdentifier, resumableFilename, resumableRelativePath, resumableFilePath);
        if (!info.vaild()) {
            storage.remove(info);
            throw new ServletException("Invalid request params.");
        }
        return info;
    }
}
