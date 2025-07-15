package com.fyrtrain.client;

import com.fyrtrain.handler.FactorialClientHandler;
import com.fyrtrain.handler.FactorialClientInitializer;
import com.fyrtrain.util.ServerUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;

import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;

public class FactorialClient {

    public static final int PORT = 8080;
    public static final String HOST = "localhost";

    public static final int COUNT = 1000;

    public static void main(String[] args) throws InterruptedException, CertificateException, SSLException {
        final SslContext sslCtx = ServerUtil.buildSslContext();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new FactorialClientInitializer(sslCtx));

            ChannelFuture future = b.connect(HOST, PORT).sync();
            FactorialClientHandler handler = (FactorialClientHandler) future.channel().pipeline().last();
            System.err.printf("Factorial of %,d is: %,d%n", COUNT, handler.getFactorial());
        } finally {
            workGroup.shutdownGracefully();
        }
    }
}
