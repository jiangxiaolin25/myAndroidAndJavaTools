package macc.paxsz.com.myapplication.Androidtool;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by jiangxiaolin on 2021/8/6.
 */

public class NetworkApi {

    /**获取本机IP地址
     *
     * @return  192.168.1.6  本机IP
     * @throws Exception
     */
    public static  String GetLocalHost() throws Exception {
        InetAddress inetAddress=InetAddress.getLocalHost();
        String hostAddress = inetAddress.getHostAddress();
        System.out.println(hostAddress);
        return hostAddress;
    }
    /**获取本机名字s1002321
     *
     * @return  计算机——属性里面的名字   本机名字s1002321
     * @throws Exception
     */
    public static  String GetHostName(){
        InetAddress inetAddress;
        String hostAddress = null;
        try {
            inetAddress = InetAddress.getLocalHost();
            hostAddress = inetAddress.getHostName();
            System.out.println(hostAddress);
            return hostAddress;
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return hostAddress;

    }


}
