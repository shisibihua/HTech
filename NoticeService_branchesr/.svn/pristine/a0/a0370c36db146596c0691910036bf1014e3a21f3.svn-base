package com.honghe.notification;

import com.honghe.net.OKHttpUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: 北京鸿合盛视数字媒体技术有限公司</p>
 *
 * @author zhanghongbin
 * @date 2016/2/16
 */
class AttachmentUtil {

    private AttachmentUtil() {

    }


    public static Attachment[] getAttachment(String[] attachment) {
        List<Attachment> result = new ArrayList<>();
        for (String _attachment : attachment) {
            String[] fileAllInfo = _attachment.split("=="); //如果存在 == 说明有指定文件名，否则用文件后缀名做名称
            String name;
            if (fileAllInfo.length == 2) {
                int n = fileAllInfo[0].lastIndexOf(".");
                if (n == -1) continue;
                String suff = fileAllInfo[0].substring(n, fileAllInfo[0].length());
                name = fileAllInfo[1] + suff; //数组第二个为文件名

            } else {
                int n = fileAllInfo[0].lastIndexOf("/");
                if (n == -1) continue;
                name = fileAllInfo[0].substring(n + 1, fileAllInfo[0].length()).trim();
            }
            Attachment attachment1 = new Attachment(name, OKHttpUtil.get(fileAllInfo[0]));
            result.add(attachment1);
        }
        return result.toArray(new Attachment[result.size()]);
    }

//    public static void main(String[] args) throws Exception {
//        byte[] bb = HttpUtil.getFileByte("http://192.168.16.173:8181/storage/27/2016-05-24_10-22-03/1319215628/1319215628.doc");
//        IOUtils.write(bb, new FileOutputStream("C:\\Users\\zhanghongbin\\Desktop\\a.doc"));
//    }


}
