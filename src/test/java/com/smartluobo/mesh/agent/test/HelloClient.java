package com.smartluobo.mesh.agent.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class HelloClient {
    public Channel connect(String host, int port) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new HelloClientIntHandler());
                }
            });

            // Start the client.
            Channel channel = b.connect(host, port).sync().channel();

            // Wait until the connection is closed.
           return channel;
        } finally {
            workerGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws Exception {
        HelloClient client = new HelloClient();
        Channel channel = client.connect("192.168.1.3", 8184);
        channel.writeAndFlush("abc");
    }
}
