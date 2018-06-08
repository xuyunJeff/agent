package com.smartluobo.mesh.agent.singal;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyClientHandler extends SimpleChannelInboundHandler<DubboProtocolRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyClientHandler.class);
  
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DubboProtocolRequest request) throws Exception {
        LOGGER.info(request.getRpcBody());
        ctx.channel().writeAndFlush(request);
    }
}