package macc.paxsz.com.myapplication.Androidtool.BlueToothApi;

import android.bluetooth.BluetoothDevice;

import java.lang.reflect.Method;

/**
 * 主要是安卓系统对于蓝牙模块的处理方法
 * Created by jiangxiaolin on 2021/4/15.
 */

public class BluetoothApi {
    BluetoothDevice remoteDevice;


    /**
     * 移除已配对的蓝牙设备
     */
    public void removepaiDevices () {
        try {
            Method removeBondMethod = BluetoothDevice.class.getMethod("removeBond");
            Boolean result = (Boolean) removeBondMethod.invoke(remoteDevice);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
