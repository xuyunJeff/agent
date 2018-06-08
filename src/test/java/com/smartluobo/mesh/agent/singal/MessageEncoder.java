package com.smartluobo.mesh.agent.singal;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageEncoder extends MessageToByteEncoder<DubboProtocolRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageEncoder.class);
    private static final int HEADER_LENGTH = 16;

    @Override
    protected void encode(ChannelHandlerContext ctx, DubboProtocolRequest req, ByteBuf buffer) throws Exception {

        int savedWriteIndex = buffer.writerIndex();//获取buffer可以写的位置
        buffer.writerIndex(savedWriteIndex);//标记buffer从什么位置开始写入数据
        buffer.writeBytes(req.getHeader().array()); // 写入header部分

        LOGGER.info("requestId: "+req.getRequestId());
        byte[] body = req.getRpcBody().getBytes();
        buffer.writerIndex(savedWriteIndex+HEADER_LENGTH);//标记body写入buffer的位置为开始获取buffer的写入位置+header的长度定长16
        buffer.writeBytes(body);//写入body数据
        buffer.writerIndex(savedWriteIndex+HEADER_LENGTH+body.length);//设置buffer的可以写入位置

    }

}  