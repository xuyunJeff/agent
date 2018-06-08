package com.smartluobo.mesh.agent.singal;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MessageDecoder extends ByteToMessageDecoder {
    private static final int MAGIC_NUMBER = 0x0CAFFEE0;
    public MessageDecoder() {

    }
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
       DubboProtocolRequest req = new DubboProtocolRequest();
       req.path("server return client");
       out.add(req);
    }
}  