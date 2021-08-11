package com.example.paxsdtest.ui;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.os.storage.StorageManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.paxsdtest.MyApplication;
import com.example.paxsdtest.R;
import com.example.paxsdtest.util.AppLogger;
import com.example.paxsdtest.util.FileUtil;
import com.example.paxsdtest.util.HashMdsUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

import static com.example.paxsdtest.util.FileUtil.FileUnit.GB;
import static com.example.paxsdtest.util.FileUtil.FileUnit.KB;
import static com.example.paxsdtest.util.FileUtil.FileUnit.MB;


public class SDtestActivity extends AppCompatActivity {
    public TextView mpr;
    static Context mcontext;
    public Button mbtstartest, mbtendtest, SDtopthon;
    public ProgressBar mProgressBar;
    HashMdsUtil mHashMdsUtil;
    volatile int avalible, testcount , FailCount, SuccesCount;
    mAsyncTask mMAsyncTask;
    private FileUtil mFileUtil;
    String runtype, PhoneFilePath, SdFilePath, UpanFilePath, Sdfilehash, PhoneFileHash;
    String FileName = "SDtest.txt";
    long fileLength, startime, endhoour;
    String radioCheck2, dirname;
    RadioGroup radgroupsize, radgroupsize2;
    File file, phonefile;
    FileUtil.FileUnit munit;

    class mAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            AppLogger.v("TAG", "onPreExecute");
            AppLogger.v("TAG", "onStartCommand");
            switch (runtype) {
                case "SD":
                    avalible = getAvailableExternalMemorySizeSd(SdFilePath, munit);
                    mProgressBar.setMax((int) (avalible / fileLength));
                    break;
                case "Upan":
//                    avalible = getAvailableExternalMemorySizeSd(UpanFilePath, munit);
                    //调试
                    avalible = getAvailableExternalMemorySizeSd(PhoneFilePath, munit);
                    mProgressBar.setMax((int) (avalible / fileLength));
                    break;
                case "phone":
                    mProgressBar.setMax(10000);
                    break;
                default:
                    break;
            }
//            mProgressBar.setMax((int) (avalible / fileLength));
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            AppLogger.v("TAG", "doInBackground");
            int count = 0;
            switch (runtype) {
                case "SD":
                    try {
                        while (true) {
                            avalible = getAvailableExternalMemorySizeSd(SdFilePath, munit);
                            if (avalible < fileLength) {

                                File endfile1 = new File(SdFilePath + dirname);
                                File[] files = endfile1.listFiles();
                                if (files == null) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mpr.setText(MyApplication.getContext().getString(R.string.remind_SD));
                                            Toast.makeText(MyApplication.getContext().getApplicationContext(), MyApplication.getContext().getString(R.string.remind_SD), Toast.LENGTH_LONG).show();
                                        }
                                    });
                                    break;
                                }
                                for (File f : files) {
                                    f.delete();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mpr.setText("删除文件中.....");
                                        }
                                    });
                                    AppLogger.v("TAG", "删除文件成功");
                                }
                                avalible = getAvailableExternalMemorySizeSd(SdFilePath, munit);
                                AppLogger.v("TAG", "文件填满删除完成");
                                testcount++;
                                count = 0;
                                publishProgress(count);
                            } else {
                                mFileUtil.copyFileUsingFileChannels2(PhoneFilePath + dirname + FileName, SdFilePath + dirname + System.currentTimeMillis() + FileName);
                                AppLogger.v("TAG", "复制文件成功");
                                count++;
                                publishProgress(count);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "Upan":
//                    try {
//                        while (true) {
//                            avalible = getAvailableExternalMemorySizeSd(SdFilePath, munit);
//                            if (getAvailableExternalMemorySizeSd(UpanFilePath, munit) < fileLength) {
//                                File endfile1 = new File(UpanFilePath + dirname);
//                                File[] files = endfile1.listFiles();
//                                if (files == null) {
//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            mpr.setText(MyApplication.getContext().getString(R.string.remind_Upan));
//                                            Toast.makeText(MyApplication.getContext().getApplicationContext(), MyApplication.getContext().getString(R.string.remind_Upan), Toast.LENGTH_LONG).show();
//                                        }
//                                    });
//                                    break;
//                                }
//                                for (File f : files) {
//                                    f.delete();
//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            mpr.setText("删除文件中.....");
//                                        }
//
//                                    });
//                                    AppLogger.v("TAG", "删除文件成功");
//                                }
//                                avalible = getAvailableExternalMemorySizeSd(SdFilePath, munit);
//                                testcount++;
//                                count = 0;
//                                publishProgress(count);
//                                AppLogger.v("TAG", "文件填满删除完成");
//                            } else {
//                                mFileUtil.copyFileUsingFileChannels2(PhoneFilePath + dirname + FileName, UpanFilePath + dirname + System.currentTimeMillis() + FileName);
//                                AppLogger.v("TAG", "复制文件成功");
//                                count++;
//                                publishProgress(count);
//                            }
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                    //上面的是代码，下面的是调试用的
                    try {
                        boolean a=true;
                        while (a) {
                            avalible = getAvailableExternalMemorySizeSd(PhoneFilePath, FileUtil.FileUnit.MB);
                            if (getAvailableExternalMemorySizeSd(PhoneFilePath, FileUtil.FileUnit.MB) < 500) {
                                break;
                            } else {
                                mFileUtil.copyFileUsingFileChannels2(PhoneFilePath  + FileName, PhoneFilePath + System.currentTimeMillis() + FileName);
                                AppLogger.v("TAG", "复制文件成功");
                                count++;
                                publishProgress(count);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "phone":
                    try {
                        Sdfilehash = mHashMdsUtil.md5HashCode(file);
                        while (true) {
                            mFileUtil.copyFileUsingFileChannels2(SdFilePath + "/" + FileName, PhoneFilePath + "/" + FileName);
                            if (phonefile == null || !phonefile.exists()) {
                                break;
                            }
                            PhoneFileHash = mHashMdsUtil.md5HashCode(phonefile);
                            if (Sdfilehash.equals(PhoneFileHash)) {
                                SuccesCount++;
                                phonefile.delete();
                            } else {
                                FailCount++;
                                phonefile.delete();
                            }
                            testcount++;
                            AppLogger.v("TAG", "SuccesCount++++" + SuccesCount + "testcount" + testcount);
                            AppLogger.v("TAG", "hash++" + Sdfilehash);
                            publishProgress(testcount);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            AppLogger.v("TAG", "onProgressUpdate");
            mProgressBar.setProgress(values[0]);
            endhoour = runTimeDuring(startime);
            if(runtype.equals("phone")){
                mpr.setText("测试总次数：" + testcount + "\n" + "成功总次数：" + SuccesCount + "\n" + "失败次数：" + FailCount + "\n" + "成功率：" + percentage(SuccesCount,testcount)+"%");
            }else{
                mpr.setText("测试进度：" + (values[0]) + "/" + ((int) (avalible / fileLength) - 1) + "\n" + "总测试数：" + testcount + "次" + "\n" + "测试总时间：" + endhoour + "小时");
            }
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            AppLogger.v("TAG", "onPostExecute");

            super.onPostExecute(s);
        }

        @Override
        protected void onCancelled() {
            AppLogger.v("TAG", "onCancelled");
            super.onCancelled();
        }

        @Override
        protected void onCancelled(String s) {
            AppLogger.v("TAG", "onCancelled");
            super.onCancelled(s);
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sdtestactivity);
        mcontext = SDtestActivity.this;
        init();
        dirname = "/SDtest/";
//        PhoneFilePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        PhoneFilePath = Environment.getExternalStorageDirectory().getPath()+File.separator+"SDtest";
        if (IsExistCard()) {
            SdFilePath = "/storage/sdcard1";
            UpanFilePath = "/storage/usbotg";
        }
        AppLogger.v("TAG", "随机数" + fileLength);
        mFileUtil = new FileUtil();
        mMAsyncTask = new mAsyncTask();
        fileLength = Integer.parseInt(getRadioCheck(radgroupsize));
        KeepScreenOn();
        radioCheck2 = getRadioCheck(radgroupsize2);
        munit = getFileUnit(radioCheck2);
        //选择文件大小
        radgroupsize.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radbtn = (RadioButton) findViewById(checkedId);
                if (radbtn.getText().equals("随机")) {
                    fileLength = new Random().nextInt(100);
                    AppLogger.v("TAG", "随机数" + fileLength);
                } else {
                    fileLength = Integer.parseInt((String) radbtn.getText());
                }
                Toast.makeText(getApplicationContext(), "你选的文件数值为：" + fileLength, Toast.LENGTH_LONG).show();
            }
        });
        //选择文件单位
        radgroupsize2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radbtn = (RadioButton) findViewById(checkedId);
                if (radbtn.getText().equals("随机")) {
                    if ((int) (Math.random() + 0.5) == 0) {
                        munit = getFileUnit("KB");
                        radioCheck2 = "KB";
                        AppLogger.v("TAG", "随机选择KB");
                    } else {
                        munit = getFileUnit("M");
                        radioCheck2 = "M";
                        AppLogger.v("TAG", "随机选择M");
                    }
                } else {
                    radioCheck2 = (String) radbtn.getText();
                    munit = getFileUnit(radioCheck2);
                }
                Toast.makeText(getApplicationContext(), "你选的文件单位为：" + radioCheck2, Toast.LENGTH_LONG).show();
            }
        });
        //终端 to Sd卡
        mFileUtil.cretafiledir(PhoneFilePath + dirname);
        mbtstartest.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                AppLogger.v("TAG", "终端 to Sd卡");
                mpr.setText("终端创建文件中....");
                File mfile = new File(SdFilePath);
                if (!mfile.exists()) {
                    Toast.makeText(getApplicationContext(), MyApplication.getContext().getString(R.string.remind_SD), Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getApplicationContext(), MyApplication.getContext().getString(R.string.star_copy_SD), Toast.LENGTH_SHORT).show();
                mFileUtil.cretafiledir(SdFilePath + dirname);
                runtype = "SD";
                startime = System.currentTimeMillis();
                mFileUtil.createFile(FileName, PhoneFilePath + dirname, fileLength, munit);
//                mMAsyncTask.execute();
            }
        });
        //终端 to U盘
        mbtendtest.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                AppLogger.v("TAG", "终端 to upan");
                mpr.setText("终端创建文件中....");
//                File mfile = new File(UpanFilePath);
//                if (!mfile.exists()) {
//                    Toast.makeText(getApplicationContext(), MyApplication.getContext().getString(R.string.remind_Upan), Toast.LENGTH_SHORT).show();
//                    return;
//                }
                Toast.makeText(getApplicationContext(), MyApplication.getContext().getString(R.string.star_copy_Upan), Toast.LENGTH_SHORT).show();
//                mFileUtil.cretafiledir(UpanFilePath + dirname);
//                mFileUtil.cretafiledir(PhoneFilePath + dirname);
//                runtype = "Upan";
//                startime = System.currentTimeMillis();
//                try {
//                    Runtime.getRuntime().exec("cd " + "/storage/");
//                    Runtime.getRuntime().exec("chmod 777 " + "./");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
////                mFileUtil.createFile(FileName, PhoneFilePath + dirname, fileLength, munit);
//                mMAsyncTask.execute();
                File file=new File(PhoneFilePath+FileName);
                if(file==null){
                   Log.v("TAG","file为空");
                }else{
                    Log.v("TAG","file不为空");

                }
            }

        });
        //从Sd卡拷贝文件到终端校验
        SDtopthon.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                AppLogger.v("TAG", "打开文件");
                file = mFileUtil.listFilesDemo2(SdFilePath);
                if (!file.exists() || file == null) {
                    Toast.makeText(getApplicationContext(), "请拷贝文件到SD卡根目录" + fileLength, Toast.LENGTH_LONG).show();
                    return;
                }
                mpr.setText( MyApplication.getContext().getString(R.string.copy_fiel));
                FileName = file.getName();
                phonefile = new File(PhoneFilePath + "/" + FileName);
                runtype = "phone";
                startime = System.currentTimeMillis();
                mHashMdsUtil = new HashMdsUtil();
                AppLogger.v("TAG", "lvlllll" + SdFilePath + "/" + FileName);
                AppLogger.v("TAG", "lvlllll" + PhoneFilePath + "/" + FileName);
                mMAsyncTask.execute();
            }
        });

    }
    public String percentage(int b, int a) {
        java.text.NumberFormat numberFormat = java.text.NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2); // 保留小数点后两位
        String result = numberFormat.format((float) b / (float) a * 100);
        return result;
        }


    /**
     * 获取文件单位
     *
     * @param size
     * @return
     */
    public FileUtil.FileUnit getFileUnit(String size) {
        FileUtil.FileUnit mfileunit = null;
        switch (size) {
            case "KB":
                mfileunit = KB;
                break;
            case "M":
                mfileunit = MB;
                break;
            case "G":
                mfileunit = GB;
                break;
            default:
                break;
        }
        return mfileunit;
    }


    /**
     * 保持屏幕常亮
     */
    public void KeepScreenOn() {
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        if (powerManager != null) {
            PowerManager.WakeLock mWakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "WakeLock");
        }
    }

    /**
     * 获取SD卡内存的存储可用内存
     *
     * @return
     */
    public int getAvailableExternalMemorySizeSd(String filepath, FileUtil.FileUnit size) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //sdcard状态是没有挂载的情况
            Toast.makeText(this, "sdcard不存在或未挂载", Toast.LENGTH_SHORT).show();
            return -1;
        }
        File sdcard_filedir = new File(filepath);//得到sdcard的目录作为一个文件对象
        long usableSpace = sdcard_filedir.getUsableSpace();//获取文件目录对象剩余空间
        long l = -1;
        switch (size) {
            case GB:
                l = usableSpace / (1024 * 1024 * 1024);
                break;
            case MB:
                l = usableSpace / (1024 * 1024);
                break;
            case KB:
                l = usableSpace / (1024);
                break;
            default:
                break;
        }
        AppLogger.v("TAG", "空间剩余多少M" + l);
        if (usableSpace < 1024 * 1024 * 100) {//判断剩余空间是否小于200M
            return (int) l;
        }
        return (int) l;
    }

    public long runTimeDuring(long startime) {
        long endtime = System.currentTimeMillis();
        long hours = ((endtime - startime) % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        return hours;
    }

    /**
     * 获取radio被选中的值
     */
    public String getRadioCheck(RadioGroup radgroup) {
        for (int i = 0; i < radgroup.getChildCount(); i++) {
            RadioButton rd = (RadioButton) radgroup.getChildAt(i);
            if (rd.isChecked()) {
                return (String) rd.getText();
            }
        }
        return null;
    }

    /**
     * 判断外置SD/TF卡是否挂载
     *
     * @return
     */
    public boolean IsExistCard() {
        boolean result = false;
        StorageManager mStorageManager = (StorageManager) this.getSystemService(Context.STORAGE_SERVICE);
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
                Log.i("TAG", "IsExistCard+++path" + path);
                boolean removable = (Boolean) isRemovable.invoke(storageVolumeElement);
                String state = (String) getState.invoke(storageVolumeElement);
                Log.i("TAG", "IsExistCard+++state" + state);
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


    private void init() {
        mpr = (TextView) findViewById(R.id.progress);
        radgroupsize = (RadioGroup) findViewById(R.id.radioGroup);
        radgroupsize2 = (RadioGroup) findViewById(R.id.radioGroupdw);
        mbtstartest = (Button) findViewById(R.id.btnsatr);
        mbtendtest = (Button) findViewById(R.id.btnend);
        SDtopthon = (Button) findViewById(R.id.btnfixfile);
        mProgressBar = (ProgressBar) findViewById(R.id.pgbar);
    }

    public SDtestActivity() {
        super();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
