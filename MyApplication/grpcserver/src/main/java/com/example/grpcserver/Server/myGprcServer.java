package com.example.grpcserver.Server;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.grpcserver.R;
import com.example.grpcserver.grpcServer.GrpcCustomServer;

import java.io.IOException;

/**
 * Created by jiangxiaolin on 2021/8/10.
 */

public class myGprcServer extends IntentService {
    private NotificationCompat.Builder mNotificationBuilder;

    private static final int ID_FOREGROUND_NOTIFICATION = 1110;


    public myGprcServer() {
        super("myGprcServer");
    }



    private Notification foregroundNotification() {

        mNotificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("服务")
                .setContentText("服务正在运行")
                .setSmallIcon(R.drawable.ic_launcher);

        return mNotificationBuilder.build();
    }

    @Override
    public void onCreate() {
        Log.v("TAG","开始服务onCreate");
        super.onCreate();
        startForeground(ID_FOREGROUND_NOTIFICATION, foregroundNotification());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
         Log.v("TAG","开始服务onHandleIntent");
        GrpcCustomServer grpcCustomServer = new GrpcCustomServer();
        try {
            grpcCustomServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //start方法是异步的，所以这里要把服务器阻塞，不然主程序就结束了
        try {
            grpcCustomServer.blockServer();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
