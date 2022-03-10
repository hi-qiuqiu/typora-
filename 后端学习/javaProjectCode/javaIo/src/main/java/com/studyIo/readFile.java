package com.studyIo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class readFile {
    public static void main(String[] args) throws IOException {
        readFile();
    }

    static void readFile() throws IOException {
        InputStream input = null;
        try {
            input = new FileInputStream("C:\\Users\\QY\\Desktop\\testIo.doc");
            int n;
            byte[] buffer = new byte[1000];
            while ((n = input.read(buffer)) != -1) { // 利用while同时读取并判断
                System.out.println(n);
            }
        } finally {
            if (input != null) { input.close(); }
        }
    }
}
