package com.fyrtrain;

import com.fyrtrain.server.FactorialServer;
import com.fyrtrain.util.ServerUtil;
import io.netty.handler.ssl.SslContext;

public class Main {

    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

//        // 丢弃服务器
//        new DiscardServer(port).run();
//        // 时间服务器
//        new TimeServer(port).run();


        // 阶乘服务器
        final SslContext sslCtx = ServerUtil.buildSslContext();
        new FactorialServer(port, sslCtx).run();
    }
}