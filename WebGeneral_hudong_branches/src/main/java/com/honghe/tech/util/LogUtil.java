package com.honghe.tech.util;

import com.honghe.communication.util.WebRootUtil;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by caoqian on 2018/4/2.
 */
public class LogUtil {
    private final Logger logger=Logger.getLogger(LogUtil.class);
    public final static LogUtil INSTANCE=new LogUtil();
    private final String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    private final String daytime = new SimpleDateFormat("yyyyMMdd").format(new Date());
    //创建字符文件流
    private FileWriter fw=null;
    //创建字符缓冲流
    private BufferedWriter bw=null;
    /**
     *
     * @方法功能说明： 写文件的工具类,备份日志信息
     * @修改日期 ： 2015-11-4
     * @参数： @param message
     */
    public boolean backUpLogInfo(String message) throws IOException {
        boolean  backup_result=false;
        try {
            //设置日志的全局变量
            String pathurl =WebRootUtil.getWebRoot()+"upload_dir/backup_log";
            File fi=new File(pathurl);
            //判断文件夹是否存在
            if(!fi.isDirectory()){
                //如果不存在，那么创建一个文件夹
                fi.mkdir();
            }
            //true代表在原有基础上进行添加txt内容
            this.fw=new FileWriter(pathurl+"/back_log_"+daytime+".log",true);
            this.bw=new BufferedWriter(this.fw);
            //写入信息内容
            this.bw.write(time+":"+message+"\r\n");
            backup_result=true;
        } catch (IOException e) {
            backup_result=false;
            this.bw.write("write daily error："+"\r\n"+e.getMessage());
        }finally{
            this.bw.close();
            this.fw.close();
        }
        return backup_result;
    }

    /**
     *
     * @方法功能说明： 获取当前月往前推一个月的日期
     * @修改日期 ： 2015-11-8
     * @修改内容 ：
     * @参数：     int 多少月
     * @return String
     * @异常抛出:
     */
    public  int getForwardDate(int what){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cl = Calendar.getInstance();
        cl.add(Calendar.MONTH, what);//从现在算，之前month个月
        Date dateFrom = cl.getTime();
        return Integer.parseInt(sdf.format(dateFrom));
    }
}
