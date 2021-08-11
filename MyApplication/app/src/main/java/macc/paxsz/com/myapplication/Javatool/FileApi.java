package macc.paxsz.com.myapplication.Javatool;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Created by jiangxiaolin on 2019/8/22.
 */

    public class FileApi {



        /**
         *
         * @param fromFile 被复制的文件
         * @param toFile 复制的目录文件
         * @param rewrite 是否重新创建文件
         *
         * <p>文件的复制操作方法
         */
        public static void copyfile(File fromFile, File toFile,Boolean rewrite ){

            if(!fromFile.exists()){
                return;
            }
            if(!fromFile.isFile()){
                return;
            }
            if(!fromFile.canRead()){
                return;
            }
            if(!toFile.getParentFile().exists()){
                toFile.getParentFile().mkdirs();
            }
            if(toFile.exists() && rewrite){
                toFile.delete();
            }
            try {
                FileInputStream fosfrom = new FileInputStream(fromFile);
                FileOutputStream fosto = new FileOutputStream(toFile);
                byte[] bt = new byte[1024];
                int c;
                while((c=fosfrom.read(bt)) > 0){
                    fosto.write(bt,0,c);
                }
                //关闭输入、输出流
                fosfrom.close();
                fosto.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    /**在安卓的跟目录新建一个result的txt文档，然后再把txt文档改为csv格式的就变成了excel格式的
     * @param result   新建的result文档的名字
     * @throws Exception
     */
    public static void creatcsv(String result) throws Exception {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(path, result + ".txt");
//        if (file.exists()){
//            file.delete();
//            file.createNewFile();
//        }else {
//            file.createNewFile();
//        }
//        BufferedWriter output = new BufferedWriter(new FileWriter(file, true));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true),"GBK"));
        output.append("测试次数" +","+ "  电池电量"+"\r\n");
        output.close();

    }





    /**
     * 采用channel的方式复制文件内容，大文件的读写上有很大的优势，复制大文件的时候效率会比较高
     *
     * @param starpath 跟文件路径
     * @param edngpath 目标文件路径
     * @throws IOException
     */
    public void channeldoCopy(String starpath, String edngpath) throws IOException {
        RandomAccessFile aFile = new RandomAccessFile(starpath, "rw");
        FileChannel inChannel = aFile.getChannel();
        RandomAccessFile bFile = new RandomAccessFile(edngpath, "rw");
        FileChannel outChannel = bFile.getChannel();
        inChannel.transferTo(0, inChannel.size(), outChannel);
        System.out.println("Copy over");
        inChannel.close();
        outChannel.close();
        aFile.close();
        bFile.close();

    }

    /**
     * 实现用channel的方法实现读取一个文件的内容
     * @param path 需要读取文件的路径
     * @throws Exception
     */
    public static void channeldoread(String path) throws Exception {
        RandomAccessFile aFile = new RandomAccessFile(path, "rw");
        FileChannel inChannel = aFile.getChannel();
        ByteBuffer buf = ByteBuffer.allocate(50);
        int bytesRead = inChannel.read(buf);

        while (bytesRead != -1){
            System.out.println("Read " + bytesRead);
            //flip()之后，读/写指针position指到缓冲区头部，并且设置了最多只能读出之前写入的数据长度
            buf.flip();
            //返回剩余的可用长度
            while(buf.hasRemaining()){
                System.out.print("可用长度"+(char) buf.get());
//                inChannel.write(src);

            }
            //读入channel中的数据
            buf.clear();
            bytesRead = inChannel.read(buf);
        }
        aFile.close();


    }

    /**
     * 读取文件到
     * @param filePath 文件路径
     * @return  返回第10大的数据
     * @throws Exception
     */
    public static Integer name(String filePath) throws Exception {
        // 读取文件
        List<Integer> list = new ArrayList<>();
        File file = new File(filePath);
        // 判断文件是否存在
        if (file.isFile() && file.exists()) {
            InputStreamReader read = new InputStreamReader(new FileInputStream(file));
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                list.add(Integer.parseInt(lineTxt));
            }
            read.close();
        }
        // 打印
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        // 排序
        for (int i = 0; i < 10; i++) {
            boolean flg = false;
            for (int j = 0; j < list.size() - i - 1; j++) {
                if (list.get(j) > list.get(j + 1)) {
                    Integer aInteger = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, aInteger);
                    flg = true;
                }
                if (flg = false) {// 每趟进行判断看是否进行了交换，如果没交换没说明已经有序，直接退出！

                }
            }
        }
        // 打印
        System.out.println("..........................");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        return list.get(list.size() - 10);
    }

    /**
     * 用channel实现写入，如果没有文件就会创建，有的话会把之前的内容覆盖
     * @param filepath      文件路径
     * @param writecontent    写入的内容
     */
    public static void channeldowrite(String filepath , String writecontent) throws Exception {
        // TODO 自动生成的方法存根
        long starttime = System.currentTimeMillis();
        try {
            FileOutputStream f1 = new FileOutputStream(filepath);
            FileChannel fc1 = f1.getChannel();
            ByteBuffer buffer = ByteBuffer.wrap(writecontent.getBytes());
            fc1.write(buffer);

            fc1.close();
            f1.close();
        } catch (FileNotFoundException e) {
            // TODO 自动生成的 catch 块
            throw new Exception(e.toString());
        } catch (IOException e) {
            // TODO: handle exception
            throw new Exception(e.toString());
        }
        long endtime = System.currentTimeMillis();
        System.out.println("Channel策略写文件花费了：" + (endtime - starttime) + "ms");
    }

    /**从asset里面拷贝一个文件
     * @param contextname
     * @param dest
     * @throws IOException
     */
    private  void copyFileUsingFileStreams( String contextname, File dest)
            throws IOException {
//        InputStream input =getClass().getResourceAsStream("/assets/test.txt");
        InputStream input =getClass().getResourceAsStream("/assets/contextname");
        OutputStream output = null;
        try {
//            input = new FileInputStream(source);
            output = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, bytesRead);
            }
        } finally {
            input.close();
            output.close();
        }
    }
   /**
    * 复制单个文件内容
    * @param oldPath String 原文件路径 如：c:/fqf.txt
    * @param newPath String 复制后路径 如：f:/fqf.txt
    * @return boolean
    */
    public static void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }
    }

    /**重命名一个文件后缀
     * @param path  文件路径 包括所有的.txt
     * @param oldExt  旧的后缀
     * @param newExt  新的后缀
     */
    public static void renameFiles(String path, String oldExt, String newExt) {
        File file = new File(path);
        if (!file.exists()) {
            System.err.println("文件路径不存在！");
            return;
        }
        File[] files = file.listFiles();
        if (files.length <= 0) {
            System.err.println("当前路径文件不存在！");
            return;
        }
        for (File f : files) {
            if (f.isDirectory()) {
                renameFiles(f.getPath(), oldExt, newExt);
            } else {
                String name = f.getName();
                if (name.endsWith("." + oldExt)) {
                    name = name.substring(0, name.lastIndexOf(".") + 1);
                    name += newExt;
                    f.renameTo(new File(f.getParent() + "\\" + name));
                }
            }
        }
    }

    /**拷贝大文件
     * @param source    如：   File filep=new File(//storage/sdcard1"+"/SDtest/123.txt");
     * @param dest      如：   File filep=new File(//storage/sdcard0"+"/SDtest/456.txt");
     * @throws IOException
     */
    public   void copyFileUsingFileChannels(File source, File dest)  {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            try {
                inputChannel = new FileInputStream(source).getChannel();
                outputChannel = new FileOutputStream(dest).getChannel();
                outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
            } finally {
                inputChannel.close();
                outputChannel.close();
            }
            } catch (Exception e) {
                e.printStackTrace();
            }

    }

    public  void copyFilenew(String oldPath, String newPath)  {
        //复制文件函数
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File(oldPath));
            FileOutputStream fos = new FileOutputStream(new File(newPath));
            byte[] buf=new byte[2048];
            int a = 0;
            while((a = fis.read(buf)) != -1){
                fos.write(buf, 0, a);
            }
            fis.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void copyFileUsingFileChannels2(String soupath, String despath) throws IOException {
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

    /**拷贝文件
     * @param fromFile
     * @param toFile
     */
    public static void copyfile(InputStream fromFile, File toFile){

//        if(!fromFile.exists()){
//            return;
//        }
//        if(!fromFile.isFile()){
//            return;
//        }
//        if(!fromFile.canRead()){
//            return;
//        }
//        if(!toFile.getParentFile().exists()){
//            toFile.getParentFile().mkdirs();
//        }
//        if(toFile.exists() && rewrite){
//            toFile.delete();
//        }
        try {

            FileInputStream fosfrom = (FileInputStream)fromFile;
            FileOutputStream fosto = new FileOutputStream(toFile);
            byte[] bt = new byte[1024];
            int c;
            while((c=fosfrom.read(bt)) > 0){
                fosto.write(bt,0,c);
            }
            //关闭输入、输出流
            fosfrom.close();
            fosto.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 读取网络资源，写出代码至本地文件
     *
     * @param URLpath  URL路径
     * @param edngpath 本地路径
     * @throws IOException
     */
    public void doURLtopath(String URLpath, String edngpath) throws IOException {
        URL url = new URL(URLpath);
        InputStream is = url.openStream();//输入流，读取
//        OutputStream os = new FileOutputStream("d:\\sdut.html");//输出流，写入这个文件中
        OutputStream os = new FileOutputStream(edngpath);//输出流，写入这个文件中
        byte[] bytes = new byte[1024];//一个读取10个字符，但是写入不是
        int len;
        while ((len = is.read(bytes)) != -1) {
            os.write(bytes, 0, len);
            is.close();
            os.close();
        }
    }

    /**
     * 写字符到到对应文件
     *
     * @param path 写入的文件路径
     * @param str  写入文件的内容
     */
    public void dobufferedWrite(String path, String str) {
        File f = new File(path);
        BufferedOutputStream bos = null;
        OutputStreamWriter writer = null;
        BufferedWriter bw = null;
        try {
            OutputStream os = new FileOutputStream(f);
            bos = new BufferedOutputStream(os);
            writer = new OutputStreamWriter(bos);
            bw = new BufferedWriter(writer);
            //需要换行就用这个
            bw.write(str + "\r\n");
//            不需要就用这个
//            bw.write(str);
            bw.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取path路径下文本的内容
     * @param path    文件路径
     */
    public void dobufferedread(String path) {
        BufferedReader br = null;
        BufferedReader br2 = null;
        try {
            br = new BufferedReader(new FileReader(path));
            // 第一种读取文件方式
            String contentLine;
            while ((contentLine = br.readLine()) != null) {
                contentLine = br.readLine();
                //读取每一行，并输出
                System.out.println(contentLine);
                //将每一行追加到arr1
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doTCPclient(String path) {
        BufferedReader br = null;
        BufferedReader br2 = null;
        try {
            br = new BufferedReader(new FileReader(path));
            // 第一种读取文件方式
            String contentLine;
            while ((contentLine = br.readLine()) != null) {
                contentLine = br.readLine();
                //读取每一行，并输出
                System.out.println(contentLine);
                //将每一行追加到arr1
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**删除整个文件夹里面所有的文件
     * @param SDPATH
     * @param fileName
     * @return
     */
    public boolean delete(String SDPATH,String fileName) {

        //SDPATH目录路径，fileName文件名

        File file = new File(SDPATH + "/" + fileName);
        if (file == null || !file.exists() || file.isDirectory()){
            return false;
        }
        file.delete();

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
    public static boolean deletefile(String delpath) throws Exception {
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
                        System.out
                                .println(delfile.getAbsolutePath() + "删除文件成功");
                    } else if (delfile.isDirectory()) {
                        deletefile(delpath + "\\" + filelist[i]);
                        System.out.println(file + "ssss");
                    }
                }
                if (!file.toString().equals("D:\\file")) {                    //选择不删除自身文件夹
                    System.out.println(file.toString() + "lllllll");
                    file.delete();
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("deletefile() Exception:" + e.getMessage());
        }
        return true;
    }

    /**文件转为字符数组
     * @param filePath  SavePath + "NoEncWhitelist_SIG.xml"  SavePath为终端根目录
     * @return
     */
    public byte[] File2byte(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }



    //删除整个文件夹方法
    public boolean deleteSDFile(File file) {

        //file目标文件夹绝对路径

        if (file.exists()) { //指定文件是否存在
            if (file.isFile()) { //该路径名表示的文件是否是一个标准文件
                file.delete(); //删除该文件
            } else if (file.isDirectory()) { //该路径名表示的文件是否是一个目录（文件夹）
                File[] files = file.listFiles(); //列出当前文件夹下的所有文件
                for (File f : files) {
                    deleteSDFile(f); //递归删除
                    //Log.d("fileName", f.getName()); //打印文件名
                }
            }
            file.delete(); //删除文件夹（song,art,lyric）
        }
        return true;
    }

    /**根据文件的路径，不含后缀名字的路径，获取指定的的文件的file对象
     * @param filepath  文件路径SdFilePath = "/storage/sdcard1";
     * @return   指定文件的filed对象
     */
    public  File listFilesDemo2(String filepath) {
        File file = new File(filepath);
        File[] files = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                // TODO Auto-generated method stub
                Log.v("TAG", "pathname---------:" + pathname);
                return true;
            }
        });
        for (File f : files) {
            if (f.getName().contains("paxTest")) {
                Log.v("TAG", "返回F");
                return f;
            }
        }
        return null;

    }

    /**获取一个file对象的后缀名字
     * @param file
     * @return
     */
    public String getFilesuff(File file) {
        String end = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length()).toLowerCase(Locale.getDefault());
          return end;
        }


    public enum FileUnit {
        KB, MB, GB
    }

    /**
     * 创建指定大小和类型的文件
     * @author jxl
     * @param targetFile 文件路径以及文件名，需要加后缀
     * @param fileLength 文件大小
     * @param unit 单位，KB,MB，GB
     * @retrun boolean
     */
    public boolean createFile(String filename,String targetFile, long fileLength, FileUnit unit) {
        //指定每次分配的块大小
        long KBSIZE = 1024;
        long MBSIZE1 = 1024 * 1024;
        long MBSIZE10 = 1024 * 1024 * 10;
        switch (unit) {
            case KB:
                fileLength = (fileLength * 1024);
                break;
            case MB:
                fileLength =( fileLength * 1024*1024);
                break;
            case GB:
                fileLength = (fileLength * 1024*1024*1024);
                break;
            default:
                break;
        }
        File file=new File(targetFile);
        if (!file.exists()){
            file.mkdirs();
        }
        FileOutputStream fos = null;
        file = new File(targetFile+"/"+filename);
        try {
//            if (!file.exists()) {
//                file.createNewFile();
//            }
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
     * 用于复制文件内容，大文件的读写上有很大的优势
     *
     * @param starpath 跟文件路径
     * @param edngpath 目标文件路径
     * @throws IOException
     */
    public void doCopy(String starpath, String edngpath) throws IOException {
        RandomAccessFile aFile = new RandomAccessFile(starpath, "rw");
        FileChannel inChannel = aFile.getChannel();
        RandomAccessFile bFile = new RandomAccessFile(edngpath, "rw");
        FileChannel outChannel = bFile.getChannel();
        inChannel.transferTo(0, inChannel.size(), outChannel);
        System.out.println("Copy over");
        inChannel.close();
        outChannel.close();
        aFile.close();
        bFile.close();

    }

    /**
     * 读取网络资源，写出代码至本地文件
     *
     * @param URLpath  URL路径
     * @param edngpath 本地路径
     * @throws IOException
     */
    public void doURLtopath2(String URLpath, String edngpath) throws IOException {
        URL url = new URL(URLpath);
        InputStream is = url.openStream();//输入流，读取
//        OutputStream os = new FileOutputStream("d:\\sdut.html");//输出流，写入这个文件中
        OutputStream os = new FileOutputStream(edngpath);//输出流，写入这个文件中
        byte[] bytes = new byte[1024];//一个读取10个字符，但是写入不是
        int len;
        while ((len = is.read(bytes)) != -1) {
            os.write(bytes, 0, len);
            is.close();
            os.close();
        }
    }

    /**
     * 写字符到到对应文件
     *
     * @param path 写入的文件路径
     * @param str  写入文件的内容
     */
    public void dobufferedWrite2(String path, String str) {
        File f = new File(path);
        BufferedOutputStream bos = null;
        OutputStreamWriter writer = null;
        BufferedWriter bw = null;
        try {
            OutputStream os = new FileOutputStream(f);
            bos = new BufferedOutputStream(os);
            writer = new OutputStreamWriter(bos);
            bw = new BufferedWriter(writer);
            //需要换行就用这个
            bw.write(str + "\r\n");
//            不需要就用这个
//            bw.write(str);
            bw.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取path路径下文本的内容
     * @param path    文件路径
     */
    public void dobufferedread2(String path) {
        BufferedReader br = null;
        BufferedReader br2 = null;
        try {
            br = new BufferedReader(new FileReader(path));
            // 第一种读取文件方式
            String contentLine;
            while ((contentLine = br.readLine()) != null) {
                contentLine = br.readLine();
                //读取每一行，并输出
                System.out.println(contentLine);
                //将每一行追加到arr1
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doTCPclient2(String path) {
        BufferedReader br = null;
        BufferedReader br2 = null;
        try {
            br = new BufferedReader(new FileReader(path));
            // 第一种读取文件方式
            String contentLine;
            while ((contentLine = br.readLine()) != null) {
                contentLine = br.readLine();
                //读取每一行，并输出
                System.out.println(contentLine);
                //将每一行追加到arr1
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






}
