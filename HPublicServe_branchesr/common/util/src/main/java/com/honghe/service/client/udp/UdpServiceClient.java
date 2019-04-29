package com.honghe.service.client.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by zhanghongbin on 2016/7/12.
 */
public final class UdpServiceClient {

    private UdpServiceClient(){

    }

    /**
     * 向指定的服务端发送数据信息.
     *
     * @param host  服务器主机地址
     * @param port  服务端端口
     * @param bytes 发送的数据信息
     */
    public static final boolean send(final String host, final int port, final byte[] bytes) {
        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket();
            DatagramPacket dp = new DatagramPacket(bytes, bytes.length, InetAddress.getByName(host), port);
            ds.send(dp);
        } catch (Exception e) {
            return false;
        } finally {
            if (ds != null) {
                ds.close();
            }
        }
        return true;

    }

    public static void main(String[] args) {
        UdpServiceClient.send("localhost",9999,new String("士大夫士大夫").getBytes());
    }
}
