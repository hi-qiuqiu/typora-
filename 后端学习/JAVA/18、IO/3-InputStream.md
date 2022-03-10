1、InputStream
2、缓冲



1、InputStream：抽象类，所有输入流的超类。

- 方法：int read()：会读取输入流的下一个字节，并返回字节表示的`int`值（0~255）。如果已读到末尾，返回`-1`表示不能继续读取了
- 子类：FileInputStream：从文件流中读取数据
- 提供close()方法关闭流，以便操作系统将资源释放掉
- 出现错误时使用`IOException`异常并抛出 【例如，文件不存在导致无法读取，没有写权限导致写入失败】

```Java
public void readFile() throws IOException {
    // 创建一个FileInputStream对象:
    InputStream input = new FileInputStream("C:\\Users\\QY\\Desktop\\testIo.doc");
    for (;;) {
        int n = input.read(); // 反复调用read()方法，直到返回-1
        if (n == -1) {
            break;
        }
        System.out.println(n); // 打印byte的值
    }
    input.close(); // 关闭流
}
```

仔细观察上面的代码，会发现一个潜在的问题：如果读取过程中发生了IO错误，`InputStream`就没法正确地关闭，资源也就没法及时释放。

因此，我们需要用`try ... finally`来保证`InputStream`在无论是否发生IO错误的时候都能够正确地关闭：

```java
public void readFile() throws IOException {
    InputStream input = null;
    try {
        input = new FileInputStream("C:\\Users\\QY\\Desktop\\testIo.doc");
        int n;
        while ((n = input.read()) != -1) { // 利用while同时读取并判断
            System.out.println(n);
        }
    } finally {
        if (input != null) { input.close(); }
    }
}
```

2、缓冲--一次性读取多个字节

`InputStream`提供了两个重载方法来支持读取多个字节：

- `int read(byte[] b)`：读取若干字节并填充到`byte[]`数组，返回读取的字节数
- `int read(byte[] b, int off, int len)`：指定`byte[]`数组的偏移量和最大填充数

`read()`方法会尽可能多地读取字节到缓冲区， 但不会超过缓冲区的大小。`read()`方法的返回值不再是字节的`int`值，而是返回实际读取了多少个字节。如果返回`-1`，表示没有更多的数据了

```java
static void readFile() throws IOException {
        InputStream input = null;
        try {
            input = new FileInputStream("C:\\Users\\QY\\Desktop\\testIo.doc");
            int n;
            byte[] buffer = new byte[1000]; // 需要定义一个byte[]数组作为缓冲区
            while ((n = input.read(buffer)) != -1) { // 利用while同时读取并判断
                System.out.println(n);
            }
        } finally {
            if (input != null) { input.close(); }
        }
    }
```

read(byte[], int, int)是读指定长度。

但BufferedInputStream实际上默认buffer是8k，无论你调用read()还是read(byte[],int,int)，它都先以8k大小去读数据，再根据实际请求的数据量返回。很可能调用5次read(byte[],int,int)，BufferedInputStream实际只读了一次。

此外BufferedInputStream可以支持mark和reset重复读，而底层FileInputStream很可能不支持。

### 3、阻塞

在调用read()读取数据时，read()方法是阻塞(Blocking)的

```Java
int n;
n = input.read(); // 必须等待read()方法返回才能执行下一行代码
int m = n;

// 因为读取IO流相比执行普通代码，速度会慢很多，因此，无法确定read()方法调用到底要花费多长时间。
```

### 4、InputStream实现类

用`FileInputStream`可以从文件获取输入流，这是`InputStream`常用的一个实现类。此外，`ByteArrayInputStream`可以在内存中模拟一个`InputStream`，可以用来对代码进行测试：

```Java
public class Main {
    public static void main(String[] args) throws IOException {
        byte[] data = { 72, 101, 108, 108, 111, 33 };
        try (InputStream input = new ByteArrayInputStream(data)) {
            String s = readAsString(input);
            System.out.println(s);
        }
    }
    
    public static String readAsString(InputStream input) throws IOException {
        int n;
        StringBuilder sb = new StringBuilder();
        while ((n = input.read()) != -1) {
            sb.append((char) n);
        }
        return sb.toString();
    }
}

```

这就是面向抽象编程原则的应用：接受`InputStream`抽象类型，而不是具体的`FileInputStream`类型，从而使得代码可以处理`InputStream`的任意实现类。