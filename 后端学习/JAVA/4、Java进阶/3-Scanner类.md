Scanner类：
一直以来，我们都使用System.out.println()方法向屏幕打印内容，那么如何接收输入的内容呢？本小节所学习的Scanner类就可以实现对输入内容的接收。

1、定义
Scanner是一个简单的文本扫描器，可以解析基础数据类型和字符串。它位于java.util包下，因此如果要使用此类，必须使用import语句导入：import java.util.Scanner;

```Java
import java.util.Scanner;

Scanner scanner = new Scanner(System.in); 
// 构造方法的参数System.in表示允许用户从系统中读取内容
// System.in是一个InputStream类型，Scanner类还有很多接收其他类型的构造方法。
```

2、常用方法
（1）next()及其同伴方法
		——next()方法：返回值是字符串类型，可以使用此方法，将用户输入的内容扫描为字符串

```Java
import java.util.Scanner;

public class ScannerDemo1 {
    public static void main(String[] args) {
        // 创建扫描器对象
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入一段内容，输入回车结束：");
        // 可以将用户输入的内容扫描为字符串
        String str = scanner.next();
        // 打印输出
        System.out.println("您输入的内容为：" + str);
        // 关闭扫描器
        scanner.close();
    }
}

```

​	——同伴方法：这里的同伴方法指的是Scanner类中以next单词开头的方法

- nextLine() ：返回输入回车之前的所有字符；
- nextInt() ：将输入内容扫描为int类型；
- nextFloat() ：将输入内容扫描为float类型。

nextLine()与next()：

1. next()方法只有扫描到有效字符后才会结束输入；而nextLine()方法可以直接使用回车结束输入。
2. 另外，next()方法会自动去掉空白（例如回车、空格等），也不能得到带有空格的字符串；nextLine()方法可以得到空白和带有空格的字符串。
3. 注意：Scanner 类读到的内容，只与输入顺序有关，和终端上显示的顺序无关

```Java
import java.util.Scanner;

public class ScannerDemo2 {
    public static void main(String[] args) {
        // 创建扫描器对象
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入您的姓名：");
        // 将第一行输入扫描为字符串
        String name = scanner.nextLine();

        System.out.println("请输入您的年龄：");
        // 将第二行输入扫描为int类型
        int age = scanner.nextInt();

        System.out.println("请输入您的身高：");
        // 将第三行输入扫描为float类型
        float height = scanner.nextFloat();

        // 打印扫描器所扫描的值
        System.out.println("您的姓名为：" + name);
        System.out.println("您的年龄为：" + age);
        System.out.println("您的身高为：" + height);
        // 关闭扫描器
        scanner.close();
    }
}
```

（2）hasNext()及其同伴方法
	——hasNext()：返回值是一个布尔类型，如果输入中包含数据的输入，则返回true。否则返回false。通常用来做输入内容的验证。

​	——同伴方法：诸如hasNextLine()、hasNextInt()等方法

```Java
int age;
if (scanner.hasNextInt()) {
    age = scanner.nextInt();
} else {
    System.out.println("不是int类型");
}

float height;
if (scanner.hasNextFloat()) {
    height = scanner.nextFloat();
} else {
    System.out.println("不是float类型");
}

```

