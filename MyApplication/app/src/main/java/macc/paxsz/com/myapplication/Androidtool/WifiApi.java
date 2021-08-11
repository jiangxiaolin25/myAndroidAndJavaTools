package macc.paxsz.com.myapplication.Androidtool;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.List;

import macc.paxsz.com.myapplication.MyApplication;

/**
 * Created by jiangxiaolin on 2021/4/15.
 */

public class WifiApi {

    public static final int TYPE_NO_PASSWORD = 1;
    public static final int TYPE_WEP = 2;
    public static final int TYPE_WPA = 3;

    public static WifiConfiguration isExists(Context context, String SSID) {
        WifiManager wifiManager = getWifiManager(context);
        List<WifiConfiguration> existingConfigs = wifiManager
                .getConfiguredNetworks();
        if (existingConfigs != null) {

            for (WifiConfiguration existingConfig : existingConfigs) {
                System.out.println("Exsits SSID:" + existingConfig.SSID);
                if (existingConfig.SSID.equals("\"" + SSID + "\"")) {
                    System.out.println("Get Exsits SSID" + SSID);
                    return existingConfig;
                }
            }
        }
        return null;
    }

    /**
     * 得到连接的ID
     *
     * @param context
     * @return
     */
    public static int getNetworkId(Context context) {
        WifiInfo wifiInfo = getWifiInfo(context);
        return (wifiInfo == null) ? 0 : wifiInfo.getNetworkId();
    }

    /**
     * 取得WifiManager对象
     *
     * @param context
     * @return
     */
    private static WifiInfo getWifiInfo(Context context) {
        return getWifiManager(context).getConnectionInfo();
    }

    /**
     * 初始化网络信息
     *
     * @param context
     * @param SSID
     * @param Password
     * @param Type
     * @return
     */
    public static WifiConfiguration createWifiInfo(Context context, String SSID, String Password, int Type) {

        WifiManager wifiManager = getWifiManager(context);
        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        config.SSID = "\"" + SSID + "\"";
        WifiConfiguration tempConfig = isExists(context, SSID);
        if (tempConfig != null) {
            wifiManager.removeNetwork(tempConfig.networkId);
        }
        if (Type == TYPE_NO_PASSWORD) // WIFICIPHER_NOPASS
        {
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        }
        if (Type == TYPE_WEP) // WIFICIPHER_WEP
        {
            config.hiddenSSID = true;
            int length = Password.length();
            if ((length == 10 || length == 26 || length == 32) && Password.matches("[0-9A-Fa-f]*")) {
                config.wepKeys[0] = Password;  //wep密码格式是十六进制数
            } else {
                config.wepKeys[0] = "\"" + Password + "\"";  //wep密码格式是ASCII码
            }
            config.allowedAuthAlgorithms
                    .set(WifiConfiguration.AuthAlgorithm.SHARED);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            config.allowedGroupCiphers
                    .set(WifiConfiguration.GroupCipher.WEP104);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        }
        if (Type == TYPE_WPA) // WIFICIPHER_WPA
        {
            config.preSharedKey = "\"" + Password + "\"";
            config.hiddenSSID = true;
            config.allowedAuthAlgorithms
                    .set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            config.allowedPairwiseCiphers
                    .set(WifiConfiguration.PairwiseCipher.TKIP);
            // config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedPairwiseCiphers
                    .set(WifiConfiguration.PairwiseCipher.CCMP);
            config.status = WifiConfiguration.Status.ENABLED;
        }
        return config;
    }

    /**
     * 取得WifiManager对象

     * @param context
     * @return
     */
    private static WifiManager getWifiManager(Context context) {
        return (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    /**
     * 添加一个网络并连接
     **一般调用方式
     * connect(mcontext,WifiApi.createWifiInfo(mcontext, "TP-LINK_456", "pax123456", WifiApi.TYPE_WPA));
     * @param context
     * @param wcg
     */
    public static boolean connect(Context context, WifiConfiguration wcg) {
        WifiManager wifiManager = getWifiManager(context);
        int wcgID = wifiManager.addNetwork(wcg);
        boolean b = wifiManager.enableNetwork(wcgID, true);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        int count = 0;
        while(true)
        {
            count++;

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            ConnectivityManager conMan = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo.State wifi = conMan.getNetworkInfo(
                    ConnectivityManager.TYPE_WIFI).getState();
            if (NetworkInfo.State.CONNECTED == wifi) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            }

            if(count>6000)
            {
                b=false;
                break;
            }
        }
        return b;
    }

    /**
     * 打开WIFI(该方法会阻塞线程)
     *
     * @param wifiManager
     */
    public static void openWifi(WifiManager wifiManager) {
//        WifiManager wifiManager = getWifiManager(context);
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
            // 开启wifi功能需要一段时间(我在手机上测试一般需要1-3秒左右)，所以要等到wifi
            // 状态变成WIFI_STATE_ENABLED的时候才能执行下面的语句
            while (wifiManager.getWifiState() != WifiManager.WIFI_STATE_ENABLED) {
                try {
                    // 为了避免程序一直while循环，让它睡个100毫秒检测……
                    Thread.sleep(100);
                } catch (InterruptedException ie) {
                }
            }
        }
    }


    /**获取WLAN的MAC地址
     * @return  返回MAC地址
     */
    public static String getMac() {
        String macNew = null;
        int i=0;
        try {
            NetworkInterface wlan0 = NetworkInterface.getByName("wlan0");
            byte[] hardwareAddress = wlan0.getHardwareAddress();
            String s = bytesToHexString(hardwareAddress);
            for (int j=0;j<((s.length()+1)/2);j++){
                String substring = s.substring(i, i + 2);
                if(i==0){
                    macNew=substring;
                }else{
                    macNew=macNew+":"+substring;
                }
                i=i+2;
            }

        } catch (SocketException e) {
            e.printStackTrace();
        }
        return macNew;
    }

    public static  String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 自动连接WIFI的功能，需要在activity界面才能使用
     */
   public void connect(String SSID,String key) {

//        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiManager wifi = (WifiManager) MyApplication.getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        WifiConfiguration wc = new WifiConfiguration();

//        String SSID = "1234";
//
//        String key = "12345678";


        wc.SSID = "\"" + SSID + "\""; // wifi名称

        wc.preSharedKey = "\"" + key + "\""; // wifi密码

        wc.hiddenSSID = true;

        wc.status = WifiConfiguration.Status.ENABLED;

        wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);

        wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);

        wc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);

        wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);

        wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);

        wc.allowedProtocols.set(WifiConfiguration.Protocol.RSN);

        int res = wifi.addNetwork(wc);

        boolean b = wifi.enableNetwork(res, false);


    }


}
