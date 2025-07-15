package com.fyrtrain.handler;

import com.fyrtrain.client.FactorialClient;
import com.fyrtrain.decoder.BigIntegerDecoder;
import com.fyrtrain.encoder.NumberEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;

public class FactorialClientInitializer extends ChannelInitializer<SocketChannel> {

    private final SslContext sslCtx;

    public FactorialClientInitializer(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        if (sslCtx != null) {
            pipeline.addLast(sslCtx.newHandler(ch.alloc(), FactorialClient.HOST, FactorialClient.PORT));
        }

        // read事件处理程序为 in1 -> in2
        // write事件处理程序为 out1
        pipeline.addLast("in1", new BigIntegerDecoder());      // 继承自ChannelInboundHandler
        pipeline.addLast("out1", new NumberEncoder());         // 继承自ChannelOutboundHandler
        pipeline.addLast("in2", new FactorialClientHandler()); // 继承自ChannelInboundHandler

    }
}
