package com.example.grpcserver.grpcServer;



import com.hgldp.grpc.ExampleGrpcGrpc;
import com.hgldp.grpc.HelloRequest;
import com.hgldp.grpc.HelloResponse;

import io.grpc.stub.StreamObserver;

/**
 * Created by jiangxiaolin on 2021/8/9.
 */

public class GrpcServer extends ExampleGrpcGrpc.ExampleGrpcImplBase  {
    /**
     简单模型
     */
    @Override
    public void sendSimpleMessage(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        //super.sendSimpleMessage(request, responseObserver);
        System.out.println("服务器端收到消息:"+request.getNo());
        responseObserver.onNext(HelloResponse.newBuilder().setName("小明++++小林").build());
        responseObserver.onCompleted();
    }

}
