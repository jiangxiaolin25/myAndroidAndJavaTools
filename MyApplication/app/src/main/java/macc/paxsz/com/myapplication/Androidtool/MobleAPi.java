package macc.paxsz.com.myapplication.Androidtool;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import macc.paxsz.com.myapplication.MyApplication;

/**
 * Created by jiangxiaolin on 2021/6/3.
 * 主要是关于操作安卓系统里面无线连接相关的API封装
 */

public class MobleAPi {
    ConnectivityManager cm = (ConnectivityManager) MyApplication.mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = cm.getActiveNetworkInfo();


    /**
     * 判断安卓系统网络是否连接成功
     *
     * @return true 表示连接成功， false 连接失败
     */
    public boolean NetIsConnectSucc() {
        if (NetworkInfo.State.CONNECTED == networkInfo.getState() && (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE)) {
            return true;

        } else {
            return false;
        }
    }


}
