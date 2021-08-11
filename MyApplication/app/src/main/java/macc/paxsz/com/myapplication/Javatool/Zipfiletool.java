package macc.paxsz.com.myapplication.Javatool;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


/**
 * 作者：jiangxiaolin on 2019/10/18
 * 邮箱：jiangxiaolin@xgd.com
 * ToDo：主要是对文件或者文件夹进行压缩和解压缩操作
 */
public class Zipfiletool {


    /**
     * 将一个文件夹全部压缩成一个.zip的压缩包
     *
     *
     * @param suocezippath "F:/aaa/bbb";   需要压缩的文件夹
     * @param targetpath   "F:/aaa/ccc.zip"; ccc是一个压缩包的名字  生成压缩的路径和名字，ccc可以随便取
     */
  //需要安卓版本24才能使用
    @RequiresApi(api = Build.VERSION_CODES.N)
    public  void zipDirectory(String suocezippath, String targetpath) {
        File inputDir=new File(suocezippath);
        File outputZipFile=new File(targetpath);

        // Create parent directory for the output file.
        outputZipFile.getParentFile().mkdirs();

        String inputDirPath = inputDir.getAbsolutePath();
        byte[] buffer = new byte[1024];

        FileOutputStream fileOs = null;
        ZipOutputStream zipOs = null;
        try {
            List<File> allFiles = listChildFiles(inputDir);
            // Create ZipOutputStream object to write to the zip file
            fileOs = new FileOutputStream(outputZipFile);

            zipOs = new ZipOutputStream(fileOs, Charset.forName("GBK"));
            for (File file : allFiles) {
                String filePath = file.getAbsolutePath();

                System.out.println("Zipping " + filePath);
                // entryName: là một đường dẫn tương đối.
                String entryName = filePath.substring(inputDirPath.length() + 1);

                ZipEntry ze = new ZipEntry(entryName);
                // Put new entry into zip file.
                zipOs.putNextEntry(ze);
                // Read the file and write to ZipOutputStream.
                FileInputStream fileIs = new FileInputStream(filePath);

                int len;
                while ((len = fileIs.read(buffer)) > 0) {
                    zipOs.write(buffer, 0, len);
                }
                fileIs.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeQuite(zipOs);
            closeQuite(fileOs);
        }

    }

    private static void closeQuite(OutputStream out) {
        try {
            out.close();
        } catch (Exception e) {
        }
    }

    // This method returns the list of files,
    // including the children, grandchildren files of the input folder.
    private static List<File> listChildFiles(File dir) throws IOException {
        List<File> allFiles = new ArrayList<File>();

        File[] childFiles = dir.listFiles();
        for (File file : childFiles) {
            if (file.isFile()) {
                allFiles.add(file);
            } else {
                List<File> files = listChildFiles(file);
                allFiles.addAll(files);
            }
        }
        return allFiles;
    }


    /**主要功能是解压一个压缩包，到另外的路径
     * @param suocezippath  "F:/aaa/test"  test是压缩包，test.zip，zip在后面自动添加了
     * @param targetpath    F:/aaa/bbb     bbb是压缩包的名字，名字可以随便取，aaa目录下没有bbb也没有关系，会自动创建的
     */
    public  void unzip(String suocezippath,String targetpath  ) {
        final String OUTPUT_FOLDER=targetpath;
        String FILE_PATH=suocezippath+".zip";

        // 判断文件夹是否存在
        File folder = new File(OUTPUT_FOLDER);
        if(!folder.exists()){
            folder.mkdir();
        }

        // 创建buffer
        byte[] buffer = new byte[1024];
        ZipInputStream zipls = null;

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                zipls = new ZipInputStream(new FileInputStream(FILE_PATH), Charset.forName("GBK"));
            }
            ZipEntry entry = null;
            while ((entry=zipls.getNextEntry())!=null){
                System.out.println("entry=zipls.getNextEntry())");
                String entryName = entry.getName();
                String outFileName = OUTPUT_FOLDER + File.separator + entryName;
                System.out.println("Unzip: " + outFileName);

                if(entry.isDirectory()){
                    new File(outFileName).mkdirs();
                }else{
                    FileOutputStream fos = new FileOutputStream(outFileName);
                    int len;
                    while ((len = zipls.read(buffer))>0){
                        System.out.println("zipls.read");

                        fos.write(buffer,0,len);
                    }
                    fos.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                zipls.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    /**把多个文件压缩成一个压缩包
     * @param srcFiles 多个文件的file集合，File[] srcFiles = { new File("F:\\aaa\\bbb\\123.txt")，new File("F:\\aaa\\bbb\\4546.txt")}
     * @param zippath "F:\\aaa\\bbb\\bbb.zip"   bbb是zip包名可以随便取
     */
    public  void zipFiles(File[] srcFiles, String zippath) {
        File zipFile=new File(zippath);
        // 判断压缩后的文件存在不，不存在则创建
        if (!zipFile.exists()) {
            try {
                zipFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 创建 FileOutputStream 对象
        FileOutputStream fileOutputStream = null;
        // 创建 ZipOutputStream
        ZipOutputStream zipOutputStream = null;
        // 创建 FileInputStream 对象
        FileInputStream fileInputStream = null;

        try {
            // 实例化 FileOutputStream 对象
            fileOutputStream = new FileOutputStream(zipFile);
            // 实例化 ZipOutputStream 对象
            zipOutputStream = new ZipOutputStream(fileOutputStream);
            // 创建 ZipEntry 对象
            ZipEntry zipEntry = null;
            // 遍历源文件数组
            for (int i = 0; i < srcFiles.length; i++) {
                // 将源文件数组中的当前文件读入 FileInputStream 流中
                fileInputStream = new FileInputStream(srcFiles[i]);
                // 实例化 ZipEntry 对象，源文件数组中的当前文件
                zipEntry = new ZipEntry(srcFiles[i].getName());
                zipOutputStream.putNextEntry(zipEntry);
                // 该变量记录每次真正读的字节个数
                int len;
                // 定义每次读取的字节数组
                byte[] buffer = new byte[1024];
                while ((len = fileInputStream.read(buffer)) > 0) {
                    zipOutputStream.write(buffer, 0, len);
                }
            }
            zipOutputStream.closeEntry();
            zipOutputStream.close();
            fileInputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
