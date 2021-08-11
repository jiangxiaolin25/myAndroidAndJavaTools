package com.example.paxsdtest.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Looper;
import android.os.storage.StorageManager;
import android.support.annotation.UiThread;
import android.util.Log;
import android.widget.Toast;

import com.example.paxsdtest.MyApplication;
import com.example.paxsdtest.ui.SDtestActivity;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import static android.R.attr.path;

/**
 * 作者：jiangxiaolin on 2021/4/17
 * 邮箱：jiangxiaolin@xgd.com
 * ToDo：
 */
public class FileUtil {
    /**
     * 拷贝大文件
     *
     * @param
     * @param
     * @throws IOException
     */
    public void copyFileUsingFileChannels2(String soupath, String despath) throws IOException {
        File source, dest;
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            source = new File(soupath);
            dest = new File(despath);
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
//            source==null;
            inputChannel.close();
            outputChannel.close();
        }
    }

    public enum FileUnit {
        KB, MB, GB
    }

    /**
     * 创建指定大小和类型的文件
     *
     * @param targetFile 文件路径以及文件名，需要加后缀
     * @param fileLength 文件大小
     * @param unit       单位，KB,MB，GB
     * @author jxl
     * @retrun boolean
     */
    public boolean createFile(String filename, String targetFile, long fileLength, FileUnit unit) {
        //指定每次分配的块大小
        long KBSIZE = 1024;
        long MBSIZE1 = 1024 * 1024;
        long MBSIZE10 = 1024 * 1024 * 10;
        switch (unit) {
            case KB:
                fileLength = (fileLength * 1024);
                break;
            case MB:
                fileLength = (fileLength * 1024 * 1024);
                break;
            case GB:
                fileLength = (fileLength * 1024 * 1024 * 1024);
                break;
            default:
                break;
        }
        File file1 = new File(targetFile);
        if (!file1.exists()) {
            file1.mkdirs();
        }
        FileOutputStream fos = null;
        File file = new File(targetFile + "/" + filename);
        try {
            file.createNewFile();
            long batchSize = 0;
            batchSize = fileLength;
            if (fileLength > KBSIZE) {
                batchSize = KBSIZE;
            }
            if (fileLength > MBSIZE1) {
                batchSize = MBSIZE1;
            }
            if (fileLength > MBSIZE10) {
                batchSize = MBSIZE10;
            }
            long count = fileLength / batchSize;
            long last = fileLength % batchSize;
            fos = new FileOutputStream(file);
            FileChannel fileChannel = fos.getChannel();
            for (int i = 0; i < count; i++) {
                ByteBuffer buffer = ByteBuffer.allocate((int) batchSize);
                fileChannel.write(buffer);
            }
            if (last != 0) {
                ByteBuffer buffer = ByteBuffer.allocate((int) last);
                fileChannel.write(buffer);
            }
            fos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 创建文件夹
     *
     * @param filepath
     */
    public void cretafiledir(String filepath) {
        File file = new File(filepath);
        if (!file.exists()) {
            file.mkdir();
            file = null;
        }
        {
            file = null;
        }
    }

    /**
     * 获取SD卡文件file对象
     *
     * @param filepath
     * @return
     */
    public File listFilesDemo2(String filepath) {
        File file = new File(filepath);
        File[] files = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                // TODO Auto-generated method stub
                AppLogger.v("TAG", "pathname---------:" + pathname);
                AppLogger.v("TAG","onStartCommand");
                return true;
            }
        });
        for (File f : files) {
            if (f.getName().contains("paxTest")) {
                AppLogger.v("TAG", "返回F");
                return f;
            }
        }
        return null;
    }


}
