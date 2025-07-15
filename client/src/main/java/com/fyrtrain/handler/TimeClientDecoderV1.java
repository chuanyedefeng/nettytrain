package com.fyrtrain.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

// ByteToMessageDecoder 的结构说明参考： https://www.cnblogs.com/Courage129/p/14237614.html
// 核心静态内部类：Cumulator
// 主要逻辑在：io.netty.handler.codec.ByteToMessageDecoder.channelRead
// 读取ByteBuf，然后执行decode方法，结果放入链表CodecOutputList中并交给下一个handler并释放资源
public class TimeClientDecoderV1 extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {
            return;
        }

        out.add(in.readBytes(4));
    }
}
