1、创建file对象
2、文件和目录
3、创建和删除文件
4、遍历文件和目录
5、path



1、创建file对象

在计算机系统中，文件是非常重要的存储方式。Java的标准库`java.io`提供了`File`对象来操作文件和目录。
要构造一个`File`对象，需要传入文件路径：

```java 
public class Main {
    public static void main(String[] args) {
        File f = new File("C:\\Windows\\notepad.exe");
        System.out.println(f);
    }
}

// 路径可以是绝对路径和相对路径
// 注意Windows平台使用\作为路径分隔符，在Java字符串中需要用\\表示一个\。Linux平台使用/作为路径分隔符
// .表示当前目录，..表示上级目录

// 假设当前目录是C:\Docs
File f1 = new File("sub\\javac"); // 绝对路径是C:\Docs\sub\javac
File f3 = new File(".\\sub\\javac"); // 绝对路径是C:\Docs\sub\javac
File f3 = new File("..\\sub\\javac"); // 绝对路径是C:\sub\javac
```

File对象有3种形式表示的路径
一种是`getPath()`，返回构造方法传入的路径
一种是`getAbsolutePath()`，返回绝对路径
一种是`getCanonicalPath`，它和绝对路径类似，但是返回的是规范路径。
因为Windows和Linux的路径分隔符不同，File对象有一个静态变量用于表示当前平台的系统分隔符：

```java 
package com.studyIo;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        File f = new File("..");
        System.out.println(f.getPath());
        System.out.println(f.getAbsolutePath());
        System.out.println(f.getCanonicalPath());
        System.out.println(File.separator); // 根据当前平台打印"\"或"/"

//        File f = new File("C:\\Users\\QY\\Desktop\\testIo.doc");
//        System.out.println(f);

    }
}

// ..
// E:\typora笔记\后端学习\javaProjectCode\javaIo\..
// E:\typora笔记\后端学习\javaProjectCode
// \
```

2、文件和目录

`File`对象既可以表示文件，也可以表示目录。特别要注意的是，构造一个`File`对象，即使传入的文件或目录不存在，代码也不会出错，因为构造一个`File`对象，并不会导致任何磁盘操作。只有当我们调用`File`对象的某些方法的时候，才真正进行磁盘操作。

调用`isFile()`，判断该`File`对象是否是一个已存在的文件
调用`isDirectory()`，判断该`File`对象是否是一个已存在的目录

用`File`对象获取到一个文件时，还可以进一步判断文件的权限和大小：

- `boolean canRead()`：是否可读；
- `boolean canWrite()`：是否可写；
- `boolean canExecute()`：是否可执行；对目录而言，是否可执行表示能否列出它包含的文件和子目录。
- `long length()`：文件字节大小。

3、创建和删除文件

创建文件：createNewFile()，返回值为true/false，表示文件是否创建成功
删除文件：delete()，返回值为true/false，表示文件是否删除成功

```Java
// 先使用File创建一个文件对象，再使用createNewFile()创建新文件
public class Main {
    public static void main(String[] args) throws IOException {
        File testFile = new File("C:\\Users\\QY\\Desktop\\file.txt");
        if(testFile.createNewFile()) {
            //文件创建成功
            
            if(testFile.delete()){
                // 文件删除成功
            }
        }

//        File f = new File("C:\\Users\\QY\\Desktop\\testIo.doc");
//        System.out.println(f);

    }
}
```

<img src="C:\Users\QY\AppData\Roaming\Typora\typora-user-images\image-20220310140621530.png" alt="image-20220310140621530" style="zoom:25%;" />

创建临时文件：createTempFile()
在JVM退出时自动删除该文件：deleteOnExit()

```java
public static void main(String[] args) throws IOException {
        File f = File.createTempFile("tmp-", ".txt"); // 提供临时文件的前缀和后缀
        f.deleteOnExit(); // JVM退出时自动删除
        System.out.println(f.isFile());
        System.out.println(f.getAbsolutePath());
    }

// true
// C:\Users\QY\AppData\Local\Temp\tmp-1981589494460023328.txt
```

4、遍历文件和目录

File对象如果表示一个目录:
`list()`和`listFiles()`列出目录下的文件和子目录名。
`listFiles()`提供了一系列重载方法，可以过滤不想要的文件和目录

可以通过以下方法创建和删除目录：

- `boolean mkdir()`：创建当前File对象表示的目录；
- `boolean mkdirs()`：创建当前File对象表示的目录，并在必要时将不存在的父目录也创建出来；
- `boolean delete()`：删除当前File对象表示的目录，当前目录必须为空才能删除成功。

```java
package com.studyIo;

import java.io.File;
import java.io.IOException;

public class listFile {
    public static void main(String[] args) throws IOException {
        File fileObj = new File("E:\\typora笔记\\后端学习\\JAVA");
        String[] fileLists = fileObj.list();
        File[] filelistFiles = fileObj.listFiles();
//         printFiles("list", fileLists);
       printFiles("listFiles", filelistFiles);

    }

    static void printFiles(String listType, File[] fileLists) {
        System.out.println("======" + listType + "=======");
        if(fileLists != null) {
            for(File fileList: fileLists){
                System.out.println(fileList);
            }
        }
        System.out.println("======" + listType + "=======");

    }
}
======list=======
10、web开发
11、多线程
12、jar和war.md
13、声明方法中的关键字.md
15、反射
16、Spring开发
17、JDBC
18、IO
1、了解Java
2、java基础
3、java面向对象
4、Java进阶
5、异常处理
6、Maven
7、单元测试
8、网络编程
9、XML和JSON
JAVA基础知识之NIO.2——Path,Paths,Files.md
MVC形成过程.md
======list=======
    
======listFiles=======
E:\typora笔记\后端学习\JAVA\10、web开发
E:\typora笔记\后端学习\JAVA\11、多线程
E:\typora笔记\后端学习\JAVA\12、jar和war.md
E:\typora笔记\后端学习\JAVA\13、声明方法中的关键字.md
E:\typora笔记\后端学习\JAVA\15、反射
E:\typora笔记\后端学习\JAVA\16、Spring开发
E:\typora笔记\后端学习\JAVA\17、JDBC
E:\typora笔记\后端学习\JAVA\18、IO
E:\typora笔记\后端学习\JAVA\1、了解Java
E:\typora笔记\后端学习\JAVA\2、java基础
E:\typora笔记\后端学习\JAVA\3、java面向对象
E:\typora笔记\后端学习\JAVA\4、Java进阶
E:\typora笔记\后端学习\JAVA\5、异常处理
E:\typora笔记\后端学习\JAVA\6、Maven
E:\typora笔记\后端学习\JAVA\7、单元测试
E:\typora笔记\后端学习\JAVA\8、网络编程
E:\typora笔记\后端学习\JAVA\9、XML和JSON
E:\typora笔记\后端学习\JAVA\JAVA基础知识之NIO.2——Path,Paths,Files.md
E:\typora笔记\后端学习\JAVA\MVC形成过程.md
======listFiles=======

```

5、Path

Java标准库还提供了一个`Path`对象，它位于`java.nio.file`包。`Path`对象和`File`对象类似，但操作更加简单

```java
package com.studyIo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class filePath {
    public static void main(String[] args) throws IOException {
        Path p1 = Paths.get(".", "project", "study"); // 构造一个Path对象
        System.out.println(p1);
        Path p2 = p1.toAbsolutePath(); // 转换为绝对路径
        System.out.println(p2);
        Path p3 = p2.normalize(); // 转换为规范路径
        System.out.println(p3);
        File f = p3.toFile(); // 转换为File对象
        System.out.println(f);
        for (Path p : p2) { // 可以直接遍历Path
            System.out.println("  " + p);
        }
    }
}

.\project\study
E:\typora笔记\后端学习\javaProjectCode\javaIo\.\project\study
E:\typora笔记\后端学习\javaProjectCode\javaIo\project\study
E:\typora笔记\后端学习\javaProjectCode\javaIo\project\study
  typora笔记
  后端学习
  javaProjectCode
  javaIo
  .
  project
  study
```

