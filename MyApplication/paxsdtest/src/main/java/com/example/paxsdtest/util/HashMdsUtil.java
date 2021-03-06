package com.example.paxsdtest.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by jiangxiaolin on 2021/4/21.
 */

public class HashMdsUtil {


    /**
     * 获取文件的md5值 ，有可能不是32位
     *
     * @param filePath 文件路径
     * @return
     * @throws FileNotFoundException
     */
    public  String md5HashCode(File filePath) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(filePath);
        return md5HashCode(fis);
    }
    /**
     * java获取文件的md5值
     * @param fis 输入流
     * @return
     */
    public  String md5HashCode(InputStream fis) {
        try {
            //拿到一个MD5转换器,如果想使用SHA-1或SHA-256，则传入SHA-1,SHA-256
            MessageDigest md = MessageDigest.getInstance("MD5");
            //分多次将一个文件读入，对于大型文件而言，比较推荐这种方式，占用内存比较少。
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = fis.read(buffer, 0, 1024)) != -1) {
                md.update(buffer, 0, length);
            }
            fis.close();
            //转换并返回包含16个元素字节数组,返回数值范围为-128到127
            byte[] md5Bytes = md.digest();
            BigInteger bigInt = new BigInteger(1, md5Bytes);//1代表绝对值
            return bigInt.toString(16);//转换为16进制
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}