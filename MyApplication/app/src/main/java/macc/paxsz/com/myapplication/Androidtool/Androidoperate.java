package macc.paxsz.com.myapplication.Androidtool;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.storage.StorageManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import macc.paxsz.com.myapplication.MyApplication;

import static android.content.Context.POWER_SERVICE;
import static macc.paxsz.com.myapplication.MyApplication.mContext;

/**
 * 主要是对安卓系统的一些操作
 * 作者：jiangxiaolin on 2020/3/18
 * 邮箱：jiangxiaolin@xgd.com
 * ToDo：
 */
public class Androidoperate {



    /**
     * 安卓9.0实现系统重启操作
     */
    public void android9reboot() {
        Class<?> serviceManager = null;
        try {
            serviceManager = Class.forName("android.os.ServiceManager");
            Method getService = serviceManager.getMethod("getService", String.class);
            Object remoteService = getService.invoke(null, POWER_SERVICE);
            Class<?> stub = Class.forName("android.os.IPowerManager$Stub");
            Method asInterface = stub.getMethod("asInterface", IBinder.class);
            Object powerManager = asInterface.invoke(null, remoteService);
            Method shutdown = powerManager.getClass().getDeclaredMethod("reboot",
                    boolean.class, String.class, boolean.class);
            shutdown.invoke(powerManager, false, "", true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**保持屏幕常亮
     * @param context
     */
    public void KeepScrennOn(Activity context) {
        context.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }




    /**获取应用的包名,也就是APP下面的build.gradl 下面的versionName "1.0"值
     * @return
     */
    public static String getTestVersionName(){
        String str = "";
        PackageManager pm =mContext.getPackageManager();

        PackageInfo info = null;
        try {
            info = pm.getPackageInfo(mContext.getPackageName(),0);
            str = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**获取终端所有安装的包名
     * @param context
     */
    public  void getAppProcessName(Context context) {
        //当前应用pid
        final PackageManager packageManager =context.getPackageManager();
        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        // get all apps
        final List<ResolveInfo> apps = packageManager.queryIntentActivities(mainIntent, 0);
        for (int i = 0; i <apps.size() ; i++) {
            String name = apps.get(i).activityInfo.packageName;
//            if(!name.contains("huawei")&&!name.contains("android")){
                Log.i("TAG", "getAppProcessName: "+apps.get(i).activityInfo.packageName);
//            }
        }
    }

    /**
     * 获取运行商类型
     * @return 0-移动，1-联通，2-电信
     */
    public static int getMoblieOperator(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = telephonyManager.getSimOperator();
        String mnc = imsi.substring(3, 5);
        switch (mnc) {
            case "00":
            case "02":
            case "04":
            case "07":
                return 0;
            case "01":
            case "06":
            case "09":
                return 1;
            case "03":
            case "05":
            case "11":
                return 2;
            default:
                return -1;
        }
    }

    /**
     * 检查移动网络状态，超时时间为60s
     * @return
     */
    public static boolean waitMobileDataConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        long start = System.currentTimeMillis();
        while (true) {

            if (System.currentTimeMillis() - start > 60000) {
                return false;
            }

            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null) {

                if (networkInfo.isConnected() && (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE)) {
                    return true;
                }

                SystemClock.sleep(500);
            }
        }
    }






    /**
     *   //保持屏幕常亮
     */
    public void KeepScreenOn() {
        PowerManager powerManager = (PowerManager)MyApplication.getContext().getSystemService(POWER_SERVICE);
        if (powerManager != null) {
            PowerManager.WakeLock mWakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "WakeLock");
        }
        }

    /**
     * 需要放在activity主界面
     * 防止屏幕熄屏和锁屏
     */
    public void KeepScreenOn2() {
//        MyApplication.getContext().getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

    /**
     * 通过一个包名打开一个APP
     * @param app
     * @param context
     * @param packageName   需要打开的APP的包名
     * @throws PackageManager.NameNotFoundException
     */
    @TargetApi(16)
    public static void openAppByPackageName(Activity app, Context context, String packageName)
            throws PackageManager.NameNotFoundException {
        PackageInfo pi;
        try {
            pi = app.getPackageManager().getPackageInfo(packageName, 0);
            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
            resolveIntent.setPackage(pi.packageName);
            PackageManager pManager = app.getPackageManager();
            List<ResolveInfo> apps = pManager.queryIntentActivities(resolveIntent, 0);
            ResolveInfo ri = apps.iterator().next();
            if (ri != null) {
                packageName = ri.activityInfo.packageName;
                String className = ri.activityInfo.name;
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent
                        .FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                //重点是加这个
                ComponentName cn = new ComponentName(packageName, className);
                intent.setComponent(cn);
                context.startActivity(intent);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }




    /**方法  通过包名打开一个应用
     * @param pagename   //例如String packname = "com.example.myapplication";
     */
    public void startAppByPagename(String pagename) {
        PackageManager packageManager = MyApplication.getContext().getPackageManager();
        Intent intent = new Intent();
        intent = packageManager.getLaunchIntentForPackage(pagename);
        if (intent == null) {
            Toast.makeText(MyApplication.getContext().getApplicationContext(), "包名对应的应用未安装", Toast.LENGTH_LONG).show();
        } else {
            MyApplication.getContext().startActivity(intent);
        }
    }



    /**
     * 第二种重启系统的方法,还没成功过，不知道怎么样
     */
    public void secondrebootsys() {


        try {
            //获得ServiceManager类
            Class ServiceManager = Class.forName("android.os.ServiceManager");

            //获得ServiceManager的getService方法

            Method getService = ServiceManager.getMethod("getService", String.class);


            //调用getService获取RemoteService

            Object oRemoteService = getService.invoke(null, POWER_SERVICE);


            //获得IPowerManager.Stub类

            Class cStub = Class

                    .forName("android.os.IPowerManager$Stub");

            //获得asInterface方法

            Method asInterface = cStub.getMethod("asInterface", IBinder.class);

            //调用asInterface方法获取IPowerManager对象

            Object oIPowerManager = asInterface.invoke(null, oRemoteService);

            //获得shutdown()方法

            Method shutdown = oIPowerManager.getClass().getMethod("shutdown", boolean.class, boolean.class);

            //调用shutdown()方法

            shutdown.invoke(oIPowerManager, false, true);


        } catch
        (Exception e) {

            AppLogger.v("TAG","second");

        }

    }

    /**
     * 判断外置SD/TF卡是否挂载
     *
     * @return
     */
    public boolean IsExistCard() {
        boolean result = false;
//        StorageManager mStorageManager = (StorageManager) this.getSystemService(Context.STORAGE_SERVICE);//正常
        StorageManager mStorageManager = (StorageManager) MyApplication.getContext().getSystemService(Context.STORAGE_SERVICE);//正常
        Class<?> storageVolumeClazz = null;
        try {
            storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
            Method getState = storageVolumeClazz.getMethod("getState");
            Object obj = null;
            try {
                obj = getVolumeList.invoke(mStorageManager);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            final int length = Array.getLength(obj);
            for (int i = 0; i < length; i++) {
                Object storageVolumeElement = Array.get(obj, i);
                String path = (String) getPath.invoke(storageVolumeElement);
                Log.i("TAG","IsExistCard+++path"+path);
                boolean removable = (Boolean) isRemovable.invoke(storageVolumeElement);
                String state = (String) getState.invoke(storageVolumeElement);
                Log.i("TAG","IsExistCard+++state"+state);
                if (removable && state.equals(Environment.MEDIA_MOUNTED)) {
                    result = true;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * xgd重启系统的方式
     */
    public void xgdrebootsystem() {

        Intent shutdownintent = new Intent();
        shutdownintent.setAction("com.xgd.powermanager.REBOOT");
        MyApplication.getContext().sendBroadcast(shutdownintent);
        }





}
