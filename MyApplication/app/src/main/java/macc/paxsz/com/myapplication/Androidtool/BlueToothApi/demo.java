package macc.paxsz.com.myapplication.Androidtool.BlueToothApi;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.ConditionVariable;
import android.util.Log;
import android.view.Display;

import java.lang.reflect.Method;
import java.util.Calendar;

import static android.bluetooth.BluetoothDevice.ACTION_PAIRING_REQUEST;
import static android.bluetooth.BluetoothDevice.EXTRA_PAIRING_KEY;
import static android.bluetooth.BluetoothDevice.EXTRA_PAIRING_VARIANT;
import static android.bluetooth.BluetoothDevice.PAIRING_VARIANT_PASSKEY_CONFIRMATION;
import static android.bluetooth.BluetoothDevice.PAIRING_VARIANT_PIN;

/**
 * 通过广播接收者实现对蓝牙状态的一些处理
 * Created by jiangxiaolin on 2021/4/15.
 */

class BluetoothDiscoveryReceiver extends BroadcastReceiver {
    private ConditionVariable done;
    //指定的需要配对的蓝牙地址
    private String boundDeviceAddress;
    BluetoothAdapter adapter=BluetoothAdapter.getDefaultAdapter();

    BluetoothDiscoveryReceiver(ConditionVariable done)
    {
        this.done = done;
    }
    @Override
    public void onReceive(Context arg0, Intent arg1) {
        // TODO Auto-generated method stub
        Log.v("TAG","搜索蓝牙设备onReceive");
        if(arg1.getAction() == BluetoothDevice.ACTION_FOUND)//搜索到蓝牙设备
        {
            Log.v("TAG","搜索到蓝牙设备");
            try{
                String name = arg1.getStringExtra(BluetoothDevice.EXTRA_NAME);
                BluetoothDevice device = arg1.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                //搜索到指定MAC地址设备
                if(boundDeviceAddress.equalsIgnoreCase(device.getAddress()))
                {
                    if(adapter.isDiscovering())
                        adapter.cancelDiscovery();
                    int connectState = device.getBondState();
                    switch (connectState) {
                        case BluetoothDevice.BOND_NONE:
                            //发送配对请求
                            Method createBondMethod = BluetoothDevice.class.getMethod("createBond");
                            createBondMethod.invoke(device);
                            break;
                        case BluetoothDevice.BOND_BONDED:
                            done.open();
                            break;
                    }

                }
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        else if(arg1.getAction() == BluetoothDevice.ACTION_BOND_STATE_CHANGED)//配对状态改变
        {
            Log.v("TAG","配对状态改变");
            BluetoothDevice device = arg1.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            if (boundDeviceAddress.equalsIgnoreCase(device.getAddress())) {
                int connectState = device.getBondState();

                switch (connectState) {
                    case BluetoothDevice.BOND_NONE:
                        break;
                    case BluetoothDevice.BOND_BONDING:
                        break;
                    //配对完成
                    case BluetoothDevice.BOND_BONDED:
                        done.open();
                        break;
                }
            }
        }
        else if(arg1.getAction() == ACTION_PAIRING_REQUEST)//收到配对请求
        {
            Log.v("TAG","收到配对请求");

            BluetoothDevice device = arg1.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

            if(boundDeviceAddress.equalsIgnoreCase(device.getAddress()))
            {
                int mType = arg1.getIntExtra(EXTRA_PAIRING_VARIANT, BluetoothDevice.ERROR);
                switch (mType) {
                    case PAIRING_VARIANT_PASSKEY_CONFIRMATION:
                        int passkey =
                                arg1.getIntExtra(EXTRA_PAIRING_KEY, BluetoothDevice.ERROR);

                        try{
                            //确认进行配对
//                            Utils.setPairingConfirmation(device,true);
                        }catch(Exception e)
                        {
                            e.printStackTrace();
                        }
							/*try{
								Method setPairingConfirmationMethod = BluetoothDevice.class.getMethod("setPairingConfirmation",new Class[]{boolean.class});
								setPairingConfirmationMethod.invoke(device,true);
							}catch(Exception e)
							{
								e.printStackTrace();
							}*/
                        break;

                    case PAIRING_VARIANT_PIN:
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
