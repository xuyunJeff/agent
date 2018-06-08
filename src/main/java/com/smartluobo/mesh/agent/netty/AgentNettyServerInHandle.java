package com.smartluobo.mesh.agent.netty;

import com.smartluobo.mesh.agent.protocol.AgentProtocolRequest;
import com.smartluobo.mesh.agent.protocol.DubboProtocolRequest;
import com.smartluobo.mesh.agent.rpc.DubboRpcClient;
import com.smartluobo.mesh.agent.rpc.NettyRpcClient;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AgentNettyServerInHandle extends SimpleChannelInboundHandler<byte[]> {
	private NettyRpcClient nettyRpcClient;

	private static final Logger LOGGER = LoggerFactory.getLogger(AgentNettyServerInHandle.class);

	public AgentNettyServerInHandle(NettyRpcClient nettyRpcClient ){
		this.nettyRpcClient = nettyRpcClient;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, byte[] msg) throws Exception {
		try{
			LOGGER.info("provider agent accept consumer agent send message"+msg.toString());
			Object reslut = nettyRpcClient.invoke(msg);
			LOGGER.info("dubbo provider return result : "+reslut);
			ctx.writeAndFlush(reslut);
		}finally {
			ReferenceCountUtil.release(msg);
		}


	}
}
