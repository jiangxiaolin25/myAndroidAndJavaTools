package macc.paxsz.com.myapplication.Androidtool;

import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.util.Log;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static macc.paxsz.com.myapplication.ui.MainActivity.mcontext;

/**
 * Created by jiangxiaolin on 2021/4/16.
 */

public class MemoryAPI {

    private String SDPATH=GetSdkaPath();
    private boolean hasSD=isHaveSDka();

    /**获取外部SD卡的路径
     * @return    SD卡路径
     */
    public String GetSdkaPath() {
        String SDPATH = Environment.getExternalStorageDirectory().getPath();
        return SDPATH;
        }

    /**判断终端是否插入SD卡
     * @return
     */
    public boolean isHaveSDka() {
       boolean hasSD = Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
        return hasSD;

        }

    /**
     * 在SD卡上创建文件
     *
     * @throws IOException
     */
    public File createSDFile(String fileName) throws IOException {
        File file = new File(SDPATH + "//" + fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }
    /**
     * 删除SD卡上的文件
     *
     * @param fileName
     */
    public boolean deleteSDFile(String fileName) {
        File file = new File(SDPATH + "//" + fileName);
        if (file == null || !file.exists() || file.isDirectory())
            return false;
        return file.delete();
    }

    public boolean getAvailableExternalMemorySize2() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //sdcard状态是没有挂载的情况
//            Toast.makeText(.this, "sdcard不存在或未挂载", Toast.LENGTH_SHORT).show();
            return true;
        }
        File sdcard_filedir = Environment.getExternalStorageDirectory();//得到sdcard的目录作为一个文件对象
        long usableSpace = sdcard_filedir.getUsableSpace();//获取文件目录对象剩余空间
        long l = usableSpace / (1024 * 1024);
        Log.v("TAG", "空间剩余多少M" + l);
        if (usableSpace < 1024 * 1024 * 100) {//判断剩余空间是否小于200M
            return false;
        }
        return true;
    }
    /**
     * 删除某个文件夹下的所有文件夹和文件
     *
     * @param delpath
     *            String
     * @throws FileNotFoundException
     * @throws IOException
     * @return boolean
     */
    public  boolean deletefile(String delpath) throws Exception {
        try {

            File file = new File(delpath);
            // 当且仅当此抽象路径名表示的文件存在且 是一个目录时，返回 true
            if (!file.isDirectory()) {
                file.delete();
            } else if (file.isDirectory()) {
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File delfile = new File(delpath + "\\" + filelist[i]);
                    if (!delfile.isDirectory()) {
                        delfile.delete();
                        System.out.println(delfile.getAbsolutePath() + "删除文件成功");
                         Log.v("TAG","删除文件成功");
                    } else if (delfile.isDirectory()) {
                        deletefile(delpath + "\\" + filelist[i]);
                        System.out.println(file + "ssss");
                         Log.v("TAG","ssss");
                    }
                }
//                if (!file.toString().equals("D:\\file")) {                    //选择不删除自身文件夹
//                    System.out.println(file.toString() + "lllllll");
//                     Log.v("TAG","lllllll");
//                    file.delete();
//                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("deletefile() Exception:" + e.getMessage());
        }
        return true;
    }



    /**
     * 写入内容到SD卡中的txt文本中
     * str为内容
     */
    public void writeSDFile(String str,String fileName)
    {
        try {
            FileWriter fw = new FileWriter(SDPATH + "//" + fileName);
            File f = new File(SDPATH + "//" + fileName);
            fw.write(str);
            FileOutputStream os = new FileOutputStream(f);
            DataOutputStream out = new DataOutputStream(os);
            out.writeShort(2);
            out.writeUTF("");
            System.out.println(out);
            fw.flush();
            fw.close();
            System.out.println(fw);
        } catch (Exception e) {
        }
    }


    /**获取安卓内外部内存地址
     * @param is_removale true 外置
     *                    false 内置
     *                    获取外置/内置内存卡的地址
     */
    public  String getStoragePath(Context mContext, boolean is_removale) {
        StorageManager mStorageManager = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
        Class<?> storageVolumeClazz = null;
        try {
            storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
            Object result = getVolumeList.invoke(mStorageManager);
            final int length = Array.getLength(result);
            Log.d("sss","---length--"+length);
            for (int i = 0; i < length; i++) {
                Object storageVolumeElement = Array.get(result, i);
                Log.d("sss","---Object--"+storageVolumeElement+"i=="+i);
                String path = (String) getPath.invoke(storageVolumeElement);
                Log.d("sss","---path_total--"+path);
                boolean removable = (Boolean) isRemovable.invoke(storageVolumeElement);
                if (is_removale == removable) {
                    Log.d("sss","---path--"+path);
                    return path;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String name(int para1, int para2) {
          return "测试";
        }

    /**
     * 读取SD卡中文本文件
     *
     * @param fileName
     * @return
     */
    public String readSDFile(String fileName) {
        StringBuffer sb = new StringBuffer();
        File file = new File(SDPATH + "//" + fileName);
        try {
            FileInputStream fis = new FileInputStream(file);
            int c;
            while ((c = fis.read()) != -1) {
                sb.append((char) c);
            }
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
//    public String getFILESPATH() {
//        return FILESPATH;
//    }
    public String getSDPATH() {
        return SDPATH;
    }
    public boolean hasSD() {
        return hasSD;
    }


}
