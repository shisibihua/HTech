package com.honghe.communication.service.udp;

import com.honghe.communication.ioc.CommandIOC;
import com.honghe.communication.ioc.Context;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * Created by zhanghongbin on 2016/7/13.
 */
public final class UdpCommandService {

    private UdpCommandService() {

    }

    private static Bootstrap bootstrap;
    private static EventLoopGroup group;

    static {
        bootstrap = new Bootstrap();
        group = new NioEventLoopGroup();
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    group.shutdownNow();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }));
    }

   public static void start() {
        try {
            bootstrap.group(group)
                    .channel(NioDatagramChannel.class)
//                .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new CustomChannel());
            // bootstrap.bind(9999).sync().channel().closeFuture().wait();
            bootstrap.bind(56666);
        } catch (Exception e) {
            e.printStackTrace();
        }


   }


    private static class CustomChannel extends SimpleChannelInboundHandler<DatagramPacket> {
        @Override
        protected void messageReceived(ChannelHandlerContext ctx,
                                       DatagramPacket packet) throws Exception {
            ByteBuf buf = packet.copy().content();
            byte[] req = new byte[buf.readableBytes()];
            buf.readBytes(req);
            String message = new String(req, "UTF-8");
            buf.clear();
            CommandIOC.execute(new Context(Context.Type.UDP, ctx), message);

        }
    }

}
