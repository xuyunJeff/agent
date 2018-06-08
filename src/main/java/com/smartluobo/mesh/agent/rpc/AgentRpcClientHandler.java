package com.smartluobo.mesh.agent.rpc;

import com.smartluobo.mesh.agent.model.AgentRpcRequestHolder;
import com.smartluobo.mesh.agent.model.DubboRpcRequestHolder;
import com.smartluobo.mesh.agent.model.RpcFuture;
import com.smartluobo.mesh.agent.model.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import sun.management.Agent;

public class AgentRpcClientHandler extends SimpleChannelInboundHandler<RpcResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcResponse response) {
        String requestId = response.getRequestId();
        RpcFuture future = AgentRpcRequestHolder.get(requestId);
        if(null != future){
            AgentRpcRequestHolder.remove(requestId);
            future.done(response);
        }
    }
}