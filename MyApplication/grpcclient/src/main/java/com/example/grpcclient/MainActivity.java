package com.example.grpcclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.grpcclient.Grpc.GrpcTask;
import com.hgldp.grpc.ExampleGrpcGrpc;
import com.hgldp.grpc.HelloRequest;
import com.hgldp.grpc.HelloResponse;

import io.grpc.ManagedChannelBuilder;


public class MainActivity extends AppCompatActivity {

    private Button mButton;
    private TextView mtext;
    private static final int PROT = 55055;
    private static final String NAME = "linjw";
    private static final String HOST = "192.168.1.106";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton=(Button) findViewById(R.id.btclient_1);
        mtext=(TextView) findViewById(R.id.textView);
        mButton.setOnClickListener(new Button.OnClickListener() {

        			@Override
        			public void onClick(View v) {
        				// TODO Auto-generated method stub
                        startClient(HOST,PROT,NAME);







        			}

        		});
    }

    public static void startClient(String host, int port, String name){
        new GrpcTask(host, port, name).execute();
    }
}
