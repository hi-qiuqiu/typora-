复制内容：https://www.cnblogs.com/fysola/p/6143939.html

### NIO.2

JDK7对NIO进行了重大改进，主要包含以下两方面

1. 新增Path接口，Paths工具类，Files工具类。 这些接口和工具类对NIO中的功能进行了高度封装，大大简化了文件系统的IO编程。
2. 基于异步Channel的IO

在NIO基础上改进后的IO被称为NIO.2 ， 上面第一个改进包含在java.nio下新增的包java.nio.file包。 第二个改进包含在原有的java.nio.channels下，新增了多个Aysnchronous开头的channel接口和类。

本文暂时只讨论第一个改进，基于异步Channel的IO将在网络通信中讨论。

### Path接口

在java.io及java.nio中，是通过File类来访问文件系统（文件和目录都用File类表示），但是File类不能利用特定文件系统的特性，且性能不高，抛出的异常也太抽象，因此在NIO.2中引入了Path接口。

Path接口表示的是一个与平台无关的路径（文件和目录都用Path表示）。

Path类中包含了常用的操作路径的方法，

- getRoot()　　-Path对象的跟路径
- toAbsolutePath() -Path对象的绝对路径
- getNameCount()  -Path对象里包含的路径数量

### Paths工具类

Paths工具类包含了两个返回Path对象的静态方法。

- static Path get(URI uri) 直接通过路径返回Path对象
- static Path get(String first, String...more)通过给出的String字符串组装成一个Path对象

下面演示Paths工具类和Path接口用法，

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```java
 1 package nio;
 2 
 3 import java.nio.file.Path;
 4 import java.nio.file.Paths;
 5 
 6 public class PathTest {
 7     public static void main(String[] args) {
 8         //在传统java.io中， 文件和目录都被抽象成File对象， 即 File file = new File(".");
 9         //NIO.2中则引入接口Path代表与平台无关的路径，文件和目录都用Path对象表示
10         //通过路径工具类Paths返回一个路径对象Path
11         Path path = Paths.get(".");
12         System.out.println("path里包含的路径数量：" + path.getNameCount());
13         System.out.println("path的根路径： "+path.getRoot());
14         //path的绝对路径
15         //对比传统java.io, 取绝对路径 file.getAbsoluteFile()
16         Path absolutePath = path.toAbsolutePath();
17         System.out.println("path的绝对路径：");
18         System.out.println(absolutePath);
19         System.out.println("absolutePath的根路径： "+absolutePath.getRoot());
20         System.out.println("absolutePath里包含的路径数量：" + absolutePath.getNameCount());
21         System.out.println(absolutePath.getName(3));
22         //以多个String来构建path
23         Path path2 = Paths.get("g:", "publish" , "codes");
24         System.out.println(path2);
25         
26 
27         
28     }
29 }
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

输出如下

```java
1 path里包含的路径数量：1
2 path的根路径： null
3 path的绝对路径：
4 C:\UsersADMIN\PROJECT\CrazyJAVA\PROJECT_JavaBasic\.
5 absolutePath的根路径： C:\
6 absolutePath里包含的路径数量：6
7 CrazyJAVA
8 g:\publish\codes
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

 

### Files工具类

Files是一个操作文件的工具类，包含了大量静态方法，对旧的IO常用的功能进行了高度封装，例如copty, readAllLines, write, 等等。

在旧的IO中的操作文件的类是File，但是File类只能操作文件（创建，删除，修改属性等），但是不能修改文件的内容。

但是Files工具类可以直接写文件。

File工具类通常需要结合Path对象来使用。 常用方法，

copy(...) 拷贝文件，有3个重载方法，通常需要跟Path结合使用

- **操作文件**

createDirectory(...) 创建目录

createFile(...) 创建文件

delete(Path path) 删除文件

...

- **判断属性**

isHidden(Path path)

isReadable(Path path)

isDirectory(...)

...

- **读写文件**

lines(Path path) 使用默认编码读文件所有行进数组

readAlllines(path ,cs ) 使用指定编码读所有行到数组

write(...) 写文件，可以批量将数组中的数据写入文件，也可以指定编码

walkFileTree() 遍历路径

...

下面演示 Files的用法，

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
 1 package nio;
 2 
 3 import java.io.FileNotFoundException;
 4 import java.io.FileOutputStream;
 5 import java.io.IOException;
 6 import java.nio.charset.Charset;
 7 import java.nio.file.Files;
 8 import java.nio.file.Paths;
 9 import java.util.ArrayList;
10 import java.util.List;
11 
12 //Files工具类通常结合Path对象使用
13 public class FileTest {
14     public static void main(String[] args) throws FileNotFoundException, IOException {
15         //将传统io读写文件高度封装之后，在NIO.2中拷贝文件只需要调用File工具类的copy()方法
16         Files.copy(Paths.get("tmp.txt"), new FileOutputStream("tmp2.txt"));
17         //是否为隐藏文件    
18         System.out.println("tmp.txt是否为隐藏文件: "+Files.isHidden(Paths.get("tmp.txt")));
19         //一次性读取所有行 , 需要指定编码规则
20         List<String> lines = Files.readAllLines(Paths.get("tmp.txt"), Charset.forName("gbk"));
21         System.out.println(lines);
22         //文件大小
23         System.out.println("tmp.txt文件大小为： "+Files.size(Paths.get("tmp.txt")));
24         List<String> poem = new ArrayList<>();
25         poem.add("海阔凭鱼跃");
26         poem.add("天高任鸟飞");
27         //直接将字符串数组写入文件
28         Files.write(Paths.get("tmp.txt"), poem, Charset.forName("gbk"));
29     }
30 }
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

输出结果，

```java 
1 tmp.txt是否为隐藏文件: false
2 [海阔凭鱼跃, 天高任鸟飞]
3 tmp.txt文件大小为： 24
```