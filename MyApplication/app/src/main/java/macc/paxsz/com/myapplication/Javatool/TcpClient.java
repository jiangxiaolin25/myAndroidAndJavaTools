package macc.paxsz.com.myapplication.Javatool;

/**
 * Created by jiangxiaolin on 2019/8/15.
 */


import android.content.Intent;
import android.util.Log;


import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;

import static macc.paxsz.com.myapplication.ui.MainActivity.mcontext;

//        import jason.tcpdemo.funcs.FuncTcpClient;
//        import jason.tcpdemo.funcs.FuncTcpServer;


public class TcpClient implements Runnable{
    private String TAG = "TcpClient";
    private String  serverIP = "10.150.132.71";
    private int serverPort = 1234;
    private PrintWriter pw;
    private InputStream is;
    private DataInputStream dis;
    private boolean isRun = true;
    private Socket socket = null;
    byte buff[]  = new byte[4096];
    private String rcvMsg;
    private int rcvLen;



    public TcpClient(String ip , int port){
         Log.v("TAG","TcpClient");

        this.serverIP = ip;
        this.serverPort = port;
    }

    public void closeSelf(){
        isRun = false;
    }

    public void send(String msg){
        pw.println(msg);
        pw.flush();
    }

    @Override
    public void run() {
        try {
             Log.v("TAG","run");

            socket = new Socket(serverIP,serverPort);
            //每5S执行下面的代码,每隔5S检测服务端是否有数据发回，也就是客户端的socket的DataInputStream有没有内容输入
            socket.setSoTimeout(5000);
            pw = new PrintWriter(socket.getOutputStream(),true);
            is = socket.getInputStream();
            dis = new DataInputStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //
        while (isRun){
            try {
                 Log.v("TAG","等待服务器....");

                rcvLen = dis.read(buff);
                rcvMsg = new String(buff,0,rcvLen,"utf-8");
                Log.i(TAG, "run: 收到消息:"+ rcvMsg);
                Intent intent =new Intent();
                intent.setAction("tcpClientReceiver");
                intent.putExtra("tcpClientReceiver",rcvMsg);
                mcontext.sendBroadcast(intent);//将消息发送给主界面
                if (rcvMsg.equals("QuitClient")){   //服务器要求客户端结束
                    isRun = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        try {
            pw.close();
            is.close();
            dis.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
