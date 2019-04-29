package com.honghe.area.dao.picture;

import com.honghe.area.dao.BasicDao;
import com.honghe.communication.ioc.Command;
import com.honghe.communication.ioc.CommandFactory;
import com.honghe.communication.ioc.Response;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author lichong
 * @date 2017/3/21
 */
public class Picture extends BasicDao{

    private Command storageCommand = CommandFactory.getInstance("storage");

    public String imagePath(String imgName) {//对字节数组字符串进行Base64解码并生成图片
        String imgPath = "";
        String uploadFilePath = getWebRoot() + "upload_dir" + File.separator + imgName;
        String path = "";
        if (storageCommand != null) {
            Map<String, Object> params = new HashMap<>();
            params.put("cmd_op", "save");
            params.put("path", uploadFilePath);
            Response storageResponse = storageCommand.execute(null, params);
            if (storageResponse.getCode() == 0) {
                path = storageResponse.getResult().toString();
                if (!"".equals(path)) {
                    imgPath = path.replaceAll("\\\\", "/") + " ";
                }
            }
            try {
                FileUtils.forceDelete(new File(uploadFilePath));
            } catch (Exception e) {

            }
        }
        return imgPath;
    }

    private final String getWebRoot() {
        String path = this.getClass().getResource("/").getPath();
        int n = path.indexOf("WEB-INF/classes/");
        if (n == -1) {
            return "";
        } else {
            path = path.substring(0, n);
            path = path.replaceAll("%20", " ");
            return path;
        }
    }
}
