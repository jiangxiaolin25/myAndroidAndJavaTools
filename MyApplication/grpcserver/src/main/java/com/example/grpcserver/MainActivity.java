package com.example.grpcserver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.grpcserver.Server.myGprcServer;

public class MainActivity extends AppCompatActivity {

    private Button mButton;
    private TextView Mtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton=(Button) findViewById(R.id.btserver_1);
        Mtext=(TextView) findViewById(R.id.textView);
        final Intent intent=new Intent(this,myGprcServer.class);
        mButton.setOnClickListener(new Button.OnClickListener() {
        			@Override
        			public void onClick(View v) {
        				// TODO Auto-generated method stub
                        //MyService.class自定义继承的服务类
                        intent.setAction("com.jay.example.service.myGprcServer");
                        startService(intent);
                    }

        		});
    }
}
