package com.smartluobo.mesh.agent.singal;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicLong;

public class DubboProtocolRequest implements Serializable{
    private static AtomicLong atomicLong = new AtomicLong();
    private long requestId;
    // 协议中header段
    private ByteBuffer header = ByteBuffer.allocate(16);

    // 协议中body段
    private StringBuffer rpcBody = new StringBuffer();

    public DubboProtocolRequest(){
        // 魔数 da bb
        header.put((byte) 0xda);
        header.put((byte) 0xbb);

        // 标识 固定为 C6
        header.put((byte) 0xC6);

        // 响应状态 ，这里没有
        header.put((byte) 0x00);
        requestId = atomicLong.incrementAndGet();

        // 数据长度 这个要在最后计算
    }


    /** 需要是完整的类名 */
    public DubboProtocolRequest dubboVersion(String dubboVersion) {
        rpcBody.append(JSON.toJSONString(dubboVersion));
        rpcBody.append("\r\n");
        return this;
    }

    /** 需要是完整的类名 */
    public DubboProtocolRequest path(String path) {
        rpcBody.append(JSON.toJSONString(path));
        rpcBody.append("\r\n");
        return this;
    }

    /** 服务版本号 */
    public DubboProtocolRequest serviceVersion(String serviceVersion) {
        rpcBody.append(JSON.toJSONString(serviceVersion));
        rpcBody.append("\r\n");
        return this;
    }

    /** 方法名 */
    public DubboProtocolRequest method(String method) {
        rpcBody.append(JSON.toJSONString(method));
        rpcBody.append("\r\n");
        return this;
    }

    /** 参数类型 */
    public DubboProtocolRequest paramTypes(String paramTypes) {
        rpcBody.append(JSON.toJSONString(paramTypes));
        rpcBody.append("\r\n");
        return this;
    }

    /** 参数值 */
    public DubboProtocolRequest parameter(Object... values) {
        for (Object value : values) {
            rpcBody.append(JSON.toJSONString(value));
            rpcBody.append("\r\n");
        }
        return this;
    }

    /** 隐式传参 */
    public DubboProtocolRequest attachments(Object attachment) {

        rpcBody.append(JSON.toJSONString(attachment));
        rpcBody.append("\r\n");
        return this;
    }

    /** 补齐header */
    public DubboProtocolRequest build(long msgId) {
        // 消息ID
        byte[] msgIdBytes = new byte[8];
        long2bytes(msgId, msgIdBytes, 0);
        this.header.put(msgIdBytes);

        // 计算body长度
        int length = this.rpcBody.toString().getBytes().length;
        byte[] lenBytes = new byte[4];
        int2bytes(length, lenBytes, 0);
        this.header.put(lenBytes);
        return this;
    }

    /** int转4字节数组 */
    private void int2bytes(int v, byte[] b, int off) {
        b[off + 3] = (byte) v;
        b[off + 2] = (byte) (v >>> 8);
        b[off + 1] = (byte) (v >>> 16);
        b[off + 0] = (byte) (v >>> 24);
    }

    /** long转8字节数组 */
    private void long2bytes(long v, byte[] b, int off) {
        b[off + 7] = (byte) v;
        b[off + 6] = (byte) (v >>> 8);
        b[off + 5] = (byte) (v >>> 16);
        b[off + 4] = (byte) (v >>> 24);
        b[off + 3] = (byte) (v >>> 32);
        b[off + 2] = (byte) (v >>> 40);
        b[off + 1] = (byte) (v >>> 48);
        b[off + 0] = (byte) (v >>> 56);
    }

    public ByteBuffer getHeader() {
        return header;
    }

    public void setHeader(ByteBuffer header) {
        this.header = header;
    }

    public String getRpcBody() {
        return rpcBody.toString();
    }

    public long getRequestId() {
        return requestId;
    }

}
