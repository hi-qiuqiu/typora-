package com.studyIo;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
//        File f = new File("..");
//        File testIoFile = new File("C:\\Users\\QY\\Desktop\\testIo.doc");
        File testFile = new File("C:\\Users\\QY\\AppData\\Local\\Temp\\tmp-18269470826746299570.txt");
//
////        System.out.println(f.getPath());
////        System.out.println(f.getAbsolutePath());
////        System.out.println(f.getCanonicalPath());
////        System.out.println(File.separator); // 根据当前平台打印"\"或"/"
////        System.out.println(testIoFile.isFile());
////        System.out.println(testIoFile.length());
        System.out.println(testFile.delete());
//
////        File f = new File("C:\\Users\\QY\\Desktop\\testIo.doc");
////        System.out.println(f);
//
    }

//    public static void main(String[] args) throws IOException {
//        File f = File.createTempFile("tmp-", ".txt"); // 提供临时文件的前缀和后缀
////        f.deleteOnExit(); // JVM退出时自动删除
//        System.out.println(f.isFile());
//        System.out.println(f.getAbsolutePath());
//    }
}
