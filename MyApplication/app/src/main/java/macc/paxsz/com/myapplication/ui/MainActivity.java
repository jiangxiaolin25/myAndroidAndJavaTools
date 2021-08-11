package macc.paxsz.com.myapplication.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import macc.paxsz.com.myapplication.Androidtool.Androidoperate;
import macc.paxsz.com.myapplication.Androidtool.GetAndroidInfoApi;
import macc.paxsz.com.myapplication.Androidtool.MemoryAPI;
import macc.paxsz.com.myapplication.Androidtool.WifiApi;
import macc.paxsz.com.myapplication.Javatool.FileApi;
import macc.paxsz.com.myapplication.MyApplication;
import macc.paxsz.com.myapplication.R;

import static android.R.attr.start;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static junit.framework.Assert.fail;

public class MainActivity extends AppCompatActivity {
    private Button mButton, mButton2, wifibt, uibutton;
    TextView mTextView;
    public static Context mcontext;
    public FileApi fileApi;
    public MemoryAPI mMemoryAPI;
    private GetAndroidInfoApi mgetAndroidinfo;
    Androidoperate androidoperate;
    String phonetFilepath;
    String SDfilepath;
    String filenaem = "SDtest.txt";
    long fileLength;
    File filep;
    File filesd;
    private String SIMstatus = "com.pax.intent.action.MSGSTATUS";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    boolean[] chooseModules = {false, false, false, false, false};
    private ListView lv = null;
    String[] modules = {"Bluetooth", "Bluetooth_D180", "Camera", "Ethernet", "all"};

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 返回false是不消费该事件，后面的监听，这里是BaseActivity(extends BaseActivity)也能得到该事件
        // 返回true是消费，后面的监听得不到该事件了
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            Log.v("TAG", "HOMe");
            return true;
        }
        return false;
    }

    /**
     * 加载menu文件下面的布局文件，主要是显示到和应用名字一排的更多选择里面
     *
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * 点击onCreateOptionsMenu加载的界面后弹出的列表选择框，针对选择的子选项进行处理
     *
     * @param item
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                Toast.makeText(this, "You clicked Add", Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                AlertDialog builder = new AlertDialog.Builder(this)
                        .setTitle("多选框")
//                        module为你显示的列表的值，是一个字符串数组，chooseModules是一个boolean
                        //类型的数组，主要是modules里面的模块是否被勾选，true表示勾选，false表示不勾选
                        .setMultiChoiceItems(modules, chooseModules,
                                new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which, boolean isChecked) {
                                        if (which == modules.length - 1) {
                                            for (int i = 0; i < modules.length; i++) {
                                                lv.setItemChecked(i, isChecked);
                                                chooseModules[i] = isChecked;
                                            }
                                        } else {
                                            chooseModules[which] = isChecked;
                                        }
                                    }
                                })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                int count = lv.getCount();
                                String s = "您选择了:";
                                for (int i = 0; i < modules.length; i++) {
                                    if (lv.getCheckedItemPositions().get(i))
                                        s += i + ":" + lv.getAdapter().getItem(i) + "  ";
                                }
                                if (lv.getCheckedItemPositions().size() > 0) {
                                    new AlertDialog.Builder(MainActivity.this).setMessage(s).show();
                                } else {
                                    new AlertDialog.Builder(MainActivity.this).setMessage("您未选择任何省份").show();
                                }
                            }
                        }).setNegativeButton("取消", null).create();
                lv = builder.getListView();
                builder.show();
                break;
            default:
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mcontext = MainActivity.this;
        androidoperate = new Androidoperate();
        mButton = (Button) findViewById(R.id.BT_1);
        uibutton = (Button) findViewById(R.id.uidebug);
        mButton2 = (Button) findViewById(R.id.BT_3);
        wifibt = (Button) findViewById(R.id.BT_wifi);
        mTextView = (TextView) findViewById(R.id.TX2);

        fileApi = new FileApi();
        mMemoryAPI = new MemoryAPI();
        mgetAndroidinfo = new GetAndroidInfoApi();
        phonetFilepath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/syslog/systemLog_20210707161722.log";
//        phonetFilepath= Environment.getDataDirectory()+"/epay/share/";
        SDfilepath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/syslog/jiangxiaolin.txt";

        filep = new File(phonetFilepath);
//        try {
//
//             fileApi.copyFile(SDfilepath,phonetFilepath);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }for
//        while (true){
//
//        }
//        if(filep!=null){
//           String[] a= filep.list();
//          for (String b : a) {
//               Log.v("TAG","bbbb"+b);
//
//          }
//
//                }else{
//         Log.v("TAG","kong"+123456);
//        }

        Log.v("TAG", "++++" + Environment.getExternalStorageDirectory());
        Log.v("TAG", "++++" + phonetFilepath);
//        SDfilepath="//storage/sdcard1"+"/SDtest/";
//         Log.v("TAG","");
        fileLength = 100;
//         Log.v("TAG","baom"+androidoperate.getTestVersionName());
        name2();
//        filesd=new File(SDfilepath+filenaem);
        //wifi调试
        wifibt.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//                        WifiConfiguration pax123456 = WifiApi.createWifiInfo(mcontext, "TP-LINK_456", "pax123456", WifiApi.TYPE_WPA);
//                        WifiApi.connect(mcontext,pax123456);
//                        int networkId = WifiApi.getNetworkId(mcontext);
//                         Log.v("TAG","networkId..."+networkId);
//                        androidoperate.getAppProcessName(mcontext);
                boolean a = true;
                int b = 1;
                while (a) {
                    Log.w("1", "1234568791234597812345687912345978123456879123459781234568791234597812345687912345978");
//                            b++;
//                            if(b==10000){
//                            a=false;
//                            }
                }
//                        Toast.makeText(getApplicationContext(), "测试完成", Toast.LENGTH_LONG).show();


//

//                        try {
//                            ArrayList commandLine = new ArrayList();
//                            commandLine.add( "logcat");
//                            commandLine.add( "-d");
//                            commandLine.add( "-v");
//                            commandLine.add( "time");
//                            commandLine.add( "-f");
//                            commandLine.add( "/sdcard/log/logcat.txt");
//                            Process process = Runtime.getRuntime().exec( commandLine.toArray( new String[commandLine.size()]));
//                            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(process.getInputStream()), 1024);
//                            String line = bufferedReader.readLine();
//                            while ( line != null) {
//                                log.append(line);
//                                log.append("\n");
//                            }
//                        } catch ( IOException e) {
//                        }


            }
        });

        //SD 卡拷贝到手机内存
        mButton2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                boolean fileb = fileApi.createFile(filenaem, SDfilepath, fileLength, FileApi.FileUnit.MB);
                File file3 = new File(phonetFilepath);
                if (!file3.exists()) {
                    file3.mkdir();
                }
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (mMemoryAPI.getAvailableExternalMemorySize2()) {
                            filep = new File(phonetFilepath + System.currentTimeMillis() + filenaem);
                            fileApi.copyFileUsingFileChannels(filesd, filep);
                            Log.v("TAG", "SD 卡拷贝到手机内存成功");
                        }
                    }
                });
                thread.start();


            }
        });
        //手机内存拷贝到SD卡
        mButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String APkFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/userfilefile/recprdboxApp-v3.0.1605-24010000-release_sign.apk";
                int i = validXmlByApkPuk(APkFilePath);
                Log.v("TAG", "APkFilePath" + APkFilePath + "iiii+" + i);
            }
        });
        //ui界面调试
        uibutton.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(MainActivity.this, SystoolTestActivity.class);
                intent.setAction("android.intent.action.SystoolTestActivity");
                intent.addCategory("android.intent.category.SystoolTestActivity");
                startActivity(intent);


            }

        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void name2() {
        //注册广播
        IntentFilter mFilter = new IntentFilter();
        // 添加接收网络连接状态改变的Action
        mFilter.addAction(SIMstatus);
        registerReceiver(mReceiver, mFilter);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(SIMstatus)) ;
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTextView.setText("收到刷卡广播了");
                    }
                });


            }
        }
    };


    public static int validXmlByApkPuk(String filePath) {
        int result = -1;
        try {
            Class<?> clazz = Class.forName("com.paxdroid.security.PaxSecurity");
            Method method;
            method = clazz.getMethod("verifyApk", String.class);
            result = (Integer) method.invoke(null, filePath);
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
            fail(e1.getMessage().toString());
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
            fail(e2.getMessage().toString());
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
            fail(e3.getMessage().toString());
        } catch (IllegalArgumentException e4) {
            e4.printStackTrace();
            fail(e4.getMessage().toString());
        } catch (InvocationTargetException e5) {
            e5.printStackTrace();
            fail(e5.getMessage().toString());
        }
        return result;
    }

    public boolean isFocusAuto(int cameraId) {
        boolean hasAutoFocus;
        if (cameraId == 0) {
            hasAutoFocus = MainActivity.this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_AUTOFOCUS);
        } else {
            hasAutoFocus = false;
        }
        return hasAutoFocus;
    }

    public void name() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("android.intent.extras.CAMERA_FACING", 0); // 调用前置摄像头
        intent.putExtra("autofocus", true); // 自动对焦
        intent.putExtra("fullScreen", false); // 全屏
        intent.putExtra("showActionIcons", false);
        startActivityForResult(intent, 1);
    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
