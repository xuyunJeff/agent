package com.smartluobo.mesh.agent.singal;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyServerHandler extends SimpleChannelInboundHandler<DubboProtocolRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServerHandler.class);
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DubboProtocolRequest msg) throws Exception {
        LOGGER.info("msg: "+msg.getRpcBody());
        ctx.writeAndFlush("hello client");
    }
}  