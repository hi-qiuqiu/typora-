package com.studyIo;

import java.io.File;
import java.io.IOException;

public class listFile {
    public static void main(String[] args) throws IOException {
        File fileObj = new File("E:\\typora笔记\\后端学习\\JAVA");
        String[] fileLists = fileObj.list();
        File[] filelistFiles = fileObj.listFiles();
         printFiles("list", fileLists);
        //     printFiles("listFiles", filelistFiles);

    }

    static void printFiles(String listType, String[] fileLists) {
        System.out.println("======" + listType + "=======");
        if(fileLists != null) {
            for(String fileList: fileLists){
                System.out.println(fileList);
            }
        }
        System.out.println("======" + listType + "=======");

    }
}
