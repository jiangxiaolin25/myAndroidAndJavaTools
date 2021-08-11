package com.example.grpcclient.Grpc;

import android.os.AsyncTask;
import android.util.Log;

import com.google.protobuf.Any;
import com.hgldp.grpc.ExampleGrpcGrpc;
import com.hgldp.grpc.HelloRequest;
import com.hgldp.grpc.HelloResponse;
import io.grpc.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * Created by jiangxiaolin on 2021/8/10.
 */

public class GrpcTask extends AsyncTask<Void, Void, String> {

    private String mHost;
    private String mName;
    private int mPort;
    private ManagedChannel mChannel;
    private String TAG = "TAG";

    public GrpcTask(String host, int port, String name) {
        this.mHost = host;
        this.mName = name;
        this.mPort = port;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(Void... nothing) {
        try {
            mChannel = ManagedChannelBuilder.forAddress(mHost, mPort)
                    .usePlaintext(true)
                    .build();
//            grpcCustomClient.blockingStub = ExampleGrpcGrpc.newBlockingStub(grpcCustomClient.channel);
//            HelloResponse helloResponse = grpcCustomClient.blockingStub.sendSimpleMessage(HelloRequest.newBuilder().setNo("10").build());
//            System.out.println("客户端收到消息:"+helloResponse.getName());
//            System.out.println("----------------------------------------------------------");


            ExampleGrpcGrpc.ExampleGrpcBlockingStub stub = ExampleGrpcGrpc.newBlockingStub(mChannel);
            HelloResponse helloResponse = stub.sendSimpleMessage(HelloRequest.newBuilder().setNo("10").build());
            Log.v("TAG", "\"客户端收到消息:\"+helloResponse.getName()" + helloResponse.getName());
            return helloResponse.getName();

        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.flush();
            return "Failed... : " + System.lineSeparator() + sw;
        }
    }



    @Override
    protected void onPostExecute(String result) {
        try {
            mChannel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Log.d(TAG, result);
    }


}
