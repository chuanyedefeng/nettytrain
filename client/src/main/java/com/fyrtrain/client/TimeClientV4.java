package com.fyrtrain.client;

import com.fyrtrain.handler.TimeClientDecoderV1;
import com.fyrtrain.handler.TimeClientDecoderV2;
import com.fyrtrain.handler.TimeClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;


public class TimeClientV4 {

    public static void main(String[] args) throws InterruptedException {
        String host = args[0];
        int port = Integer.parseInt(args[1]);

        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new TimeClientDecoderV2()).addLast(new TimeClientHandler());
                }
            });

            ChannelFuture future = b.connect(host, port);
            future.channel().closeFuture().sync();
        } finally {
            workGroup.shutdownGracefully();
        }
    }
}
