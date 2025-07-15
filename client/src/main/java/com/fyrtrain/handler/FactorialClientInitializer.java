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

        pipeline.addLast(new BigIntegerDecoder());
        pipeline.addLast(new NumberEncoder());

        pipeline.addLast(new FactorialClientHandler());
    }
}
