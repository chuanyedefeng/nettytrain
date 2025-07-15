package com.fyrtrain.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

import java.util.Arrays;

public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//
//        // 1. 丢弃
//        ((ByteBuf)msg).release();
//
//        // 2. 按字符读
//        ByteBuf in = (ByteBuf)msg;
//        try {
//            while (in.isReadable()) {
//                System.out.print((char)in.readByte());
//                System.out.flush();
//            }
//        } finally {
//            ReferenceCountUtil.release(in);
//        }
//
//        // 3. 使用ByteBuf内置api读取
//        ByteBuf in = (ByteBuf)msg;
//        try{
//            System.out.println(in.toString(CharsetUtil.US_ASCII));
//        } finally {
//            in.release();
//        }

//         // 4. echo1
//        ctx.write(msg);
//        ctx.flush();
//
//        // 5. echo2
//        ctx.writeAndFlush(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
