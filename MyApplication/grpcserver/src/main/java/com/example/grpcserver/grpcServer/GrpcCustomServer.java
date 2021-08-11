package com.example.grpcserver.grpcServer;

import java.io.IOException;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

/**
 * Created by jiangxiaolin on 2021/8/9.
 */

public class GrpcCustomServer {
    private Server server;

    public void start() throws IOException {
        int port = 55055;
        server = ServerBuilder.forPort(port)
                .addService( new GrpcServer())
                .build()
                .start();
        System.out.println("server start....");

        //添加jvm钩子
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("jvm将要关闭");
                stop();
                System.out.println("jvm关闭...");
            }
        }));


    }

    public void stop(){
        if(server != null){
            server.shutdown();
        }
    }

    public void blockServer() throws InterruptedException {
        if(server != null){
            server.awaitTermination();
        }
    }



}
