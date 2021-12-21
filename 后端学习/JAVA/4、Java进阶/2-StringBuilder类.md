String：不可变的字符序列
StringBuilder：可变的字符序列
StringBuffer：线程安全的可变字符序列

在 Java 字符串的学习中，我们知道了字符串具有不可变性，当频繁操作字符串时候，会在常量池中产生很多无用的数据。而 StringBuilder 与 String 不同，它具有可变性。相较 String 类不会产生大量无用数据，性能上会大大提高。因此对于需要频繁操作字符串的场景，建议使用 Stringbuilder 类来代替 String 类。

StringBuffer：StringBuffer 是 StringBuilder 的前身，在早期的 Java 版本中应用非常广泛，它是 StringBuilder 的线程安全版本，但实现线程安全的代价是执行效率的下降。你可以对比 StringBuilder 和 StringBuffer 的接口文档，它们的接口基本上完全一致。为了提升我们代码的执行效率，在如今的实际开发中 StringBuffer 并不常用。

StringBuilder：
1、构造方法
StringBuilder 类提供了如下 4 个构造方法：

1. StringBuilder() 构造一个空字符串生成器，初始容量为 16 个字符；

2. StringBuilder(int catpacity) 构造一个空字符串生成器，初始容量由参数 capacity 指定；

3. StringBuilder(CharSequence seq) 构造一个字符串生成器，该生成器包含与指定的 CharSequence 相同的字符。；

4. StringBuilder(String str) 构造初始化为指定字符串内容的字符串生成器。

   ```Java
   StringBuilder str = new StringBuilder("Hello");
   ```

2、成员方法
（1）append：可以实现各种类型的连接操作

```Java
public class ConnectString1 {
    public static void main(String[] args) {
        // 初始化一个内容为 Hello 的字符串生成器
        StringBuilder str = new StringBuilder("Hello");
        // 调用append()方法进行字符串的连接
        str.append(" ");
        str.append("World");
       	System.out.println(str); // Hello World
    }
}

// 由于 append() 方法返回的是一个 StringBuilder 类型，我们可以实现链式调用
str.append(" ").append("World").append(172.5f);
```

（2）获取容量
可以使用 int capacity() 方法来获取当前容量，容量指定是可以存储的字符数（包含已写入字符），超过此数将进行自动分配。注意，容量与长度（length）不同，长度指的是已经写入字符的长度。

```Java
public class GetCapacity {
    public static void main(String[] args) {
        // 调用StringBuilder的无参构造方法，生成一个str对象
        StringBuilder str = new StringBuilder();
        System.out.println("str的初始容量为：" + str.capacity()); // 16
        // 循环执行连接操作
        for (int i = 0; i < 16; i ++) {
            str.append(i);
        }
        System.out.println("连接操作后，str的容量为" + str.capacity()); // 34
    }
}
```

（3）字符串替换
可以使用 StringBuilder replace(int start, int end, String str) 方法，来用指定字符串替换从索引位置 start 开始到 end 索引位置结束（不包含 end）的子串

```Java
public class StringReplace {
    public static void main(String[] args) {
        // 初始化一个内容为 Hello 的字符串生成器
        StringBuilder str = new StringBuilder("Hello World!");
        // 调用字符串替换方法，将 World 替换为 Java
        str.replace(6, 11, "Java");
        // 打印替换后的字符串
        System.out.println(str); // Hello Java!
    }
}
```

也可使用 StringBuilder delete(int start, int end) 方法，先来删除索引位置 start 开始到 end 索引位置（不包含 end）的子串，再使用 StringBuilder insert(int offset, String str) 方法，将字符串插入到序列的 offset 索引位置。

```java 
StringBuilder str = new StringBuilder("Hello World!");
str.delete(6, 11);
str.insert(6, "Java");
```

（4）字符串截取
可以使用 StringBuilder substring(int start) 方法来进行字符串截取

```Java
public class StringSub {
    public static void main(String[] args) {
        StringBuilder str = new StringBuilder("你好，欢迎来到慕课网");
        String substring = str.substring(7);
        System.out.println("str截取后子串为：" + substring); // 慕课网
        String substring1 = str.substring(3, 5);
    }
}
```

（5）字符串反转

```Java
public class StringReverse {
    public static void main(String[] args) {
        StringBuilder str = new StringBuilder("Hello Java");
        System.out.println("str经过反转操作后为：" + str.reverse()); 
        // str经过反转操作后为：avaJ olleH

    }
}
```

