package macc.paxsz.com.myapplication.Javatool;

import android.content.Intent;
import android.util.Log;



import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import static macc.paxsz.com.myapplication.ui.MainActivity.mcontext;

/**
 * 作者：jiangxiaolin on 2020/3/17
 * 邮箱：jiangxiaolin@xgd.com
 * ToDo：
 */
public class Msocket {

    DatagramPacket UDPSend = null;
    DatagramPacket dpRcv = null;
    DatagramSocket UDPds=null;
    InetSocketAddress UDPmInetSocketAddress;
    private byte[] msgRcv=new byte[1024];
    private boolean udpLife=true;


    /**
     * 主要的功能是UDP连接成功后，服务端发送回馈信息给客户端（这个地址一般都是客户端如下方式获取
     * dpRcv.getAddress().getHostAddress() +"客户端Port:"+ dpRcv.getPort()）
     * @param inetaddress 客户端的IP地址
     * @param port   客户端的端口
     * @param mes    发给客户端的信息
     */
    public void UDPservicesentmes(String inetaddress, int port, String mes) {
        Log.v("TAG", "客户端的地址为：" + inetaddress + "客户端的端口为：" + port);
        try {
            UDPSend = new DatagramPacket(mes.getBytes(), mes.getBytes().length, InetAddress.getByName(inetaddress), port);
            UDPmInetSocketAddress = new InetSocketAddress(inetaddress, port);
            UDPds = new DatagramSocket(UDPmInetSocketAddress);
            UDPds.send(UDPSend);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**主要功能是启动UDP服务，需要指定本地服务的地址和端口，由于一直需要开启状态，在使用的时候需要放在子线程里面执行
     * @param servicesaddress   服务器本地IP地址
     * @param port     服务器指定端口要和client保持一致
     */
    public void UDPservicerecivemes(String servicesaddress, int port) {
        {
            UDPmInetSocketAddress = new InetSocketAddress(servicesaddress, port);
            try {
                UDPds = new DatagramSocket(UDPmInetSocketAddress);
                 Log.v("TAG","UDP服务器已经启动");
                UDPds.setSoTimeout(3000);
//                SetSoTime(3000);
                //设置超时，不需要可以删除
            } catch (SocketException e) {
                e.printStackTrace();
            }
            dpRcv = new DatagramPacket(msgRcv, msgRcv.length);
            while (udpLife) {
                try {
                     Log.v("TAG","UDP监听中");
                    UDPds.receive(dpRcv);
                    String string = new String(dpRcv.getData(), dpRcv.getOffset(), dpRcv.getLength());
                     Log.v("TAG","收到信息：" + string);
                    Intent intent = new Intent();
                    intent.setAction("udpReceiver");
                    intent.putExtra("udpReceiver", string);
                    mcontext.sendBroadcast(intent);//将消息发送给主界面
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            UDPds.close();
//            Log.i("SocketInfo", "UDP监听关闭");
             Log.v("TAG","UDP监听关闭");

            //udp生命结束
            udpLife = false;
        }


    }


}
