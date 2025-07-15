package com.fyrtrain.server;

import com.fyrtrain.handler.FactorialServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.ssl.SslContext;

/**
 * 阶乘服务器
 */
public final class FactorialServer {

    private final SslContext sslCtx;

    private int port;

    public FactorialServer(int port, SslContext sslCtx) {
        this.port = port;
        this.sslCtx = sslCtx;
    }

    public void run() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b = b.group(bossGroup, workGroup);
            b = b.channel(NioServerSocketChannel.class);
            b = b.childHandler(new FactorialServerInitializer(sslCtx));

            b = b.childOption(ChannelOption.SO_KEEPALIVE, true).option(ChannelOption.SO_BACKLOG, 128);
            ChannelFuture future = b.bind(port).sync();

            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
