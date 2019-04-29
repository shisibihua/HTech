package com.honghe.service.client.ftp;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferListener;

import java.io.File;
import java.io.FileInputStream;

/**
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: 北京鸿合盛视数字媒体技术有限公司</p>
 *
 * @author zhanghongbin
 * @date 2016/4/5
 */
public final class FtpServiceClient {

    private String ip;
    private int port;
    private String userName;
    private String password;

    public FtpServiceClient(String ip, int port, String userName, String password) {
        this.ip = ip;
        this.port = port;
        this.userName = userName;
        this.password = password;
    }

    public FtpServiceClient(String ip, String userName, String password) {
        this(ip, 21, userName, password);
    }

    public final boolean upload(final File[] files) {
        boolean flag = true;
        FTPClient client = new FTPClient();
        try {
            client.connect(ip, port);
            client.login(this.userName, this.password);
            for (File file : files) {
                client.upload(file, new FTPDataTransferListener() {
                    @Override
                    public void started() {

                    }

                    @Override
                    public void transferred(int i) {

                    }

                    @Override
                    public void completed() {

                    }

                    @Override
                    public void aborted() {
                        throw new RuntimeException("aborted");
                    }

                    @Override
                    public void failed() {
                        throw new RuntimeException("failed");
                    }
                });
            }
        } catch (Exception e) {
            flag = false;
        } finally {
            try {
                client.disconnect(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return flag;
    }

    public final boolean upload(String newFileName, final File file) {
        boolean flag = true;
        FTPClient client = new FTPClient();
        try {
            client.connect(ip, port);
            client.login(this.userName, this.password);
            client.upload(newFileName, new FileInputStream(file), 0, 0, new FTPDataTransferListener() {
                @Override
                public void started() {

                }

                @Override
                public void transferred(int i) {

                }

                @Override
                public void completed() {

                }

                @Override
                public void aborted() {

                }

                @Override
                public void failed() {

                }
            });
        } catch (Exception e) {
            flag = false;
        } finally {
            try {
                client.disconnect(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return flag;
    }

    public static void main(String[] args) {
        FtpServiceClient ftpServiceClient = new FtpServiceClient("192.168.16.173", 21, "hhtftp", "hht.123456");
        ftpServiceClient.upload("aaa.mp4",new File("C:\\Users\\zhanghongbin\\Desktop\\2012473326.mp4"));
    }
}
