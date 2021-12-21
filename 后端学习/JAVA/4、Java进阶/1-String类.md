String类

使用charAt()、indexOf()以及lastIndexOf()方法可以对字符串进行查找；
substring()方法可以对字符串的进行截取，split()、getBytes()方法可以将字符串切割为数组；
toLowerCase()和toUpperCase()方法分别用于大小写转换，使用equals()方法对字符串进行比较，这里要注意，对字符串内容进行比较时，永远都不要使用==运算符。

charAt():

```Java
public class StringMethod2 {
    public static void main(String[] args) {
        String str = "I love Java";
        char c = str.charAt(7);  // J
        System.out.println("索引位置为7的字符为：" + c);
    }
}
```

indexOf() 获取字符或子串在字符串中第一次出现的位置。
lastIndexOf获取字符或子串在字符串中最后一次出现的位置。

```java 
public class StringMethod2 {
    public static void main(String[] args) {
        String str = "I love Java, I love imooc!";
        int first = str.indexOf('a'); // 8
        int last = str.lastIndexOf('a'); // 10
        System.out.println("字符a在字符串str第一次出现的位置为：" + first);
        System.out.println("字符a在字符串str最后出现的位置为：" + last);
    }
}
```

substring()：有两个重载的实例方法
String substring(int beginIndex) 获取从beginIndex位置开始到结束的子串。
String substring(int beginIndex, int endIndex) 获取从beginIndex位置开始到endIndex位置的子串（不包含endIndex位置字符）。

```Java
public class StringMethod3 {
    public static void main(String[] args) {
        String str = "I love Java";
        String substring = str.substring(2); // love Java
        String substring1 = str.substring(2, 6); // love
        System.out.println("从索引位置2到结束的子串为：" + substring);
        System.out.println("从索引位置2到索引位置6的子串为：" + substring1);
    }
}
```

split()：将字符串切割为数组

```java
public class StringMethod4 {
    public static void main(String[] args) {

        String str1 = "I love Java";
        // 将字符串str1以空格分隔，并将分割结果赋值给strArr数组
        String[] strArr = str1.split(" ");
        // 遍历数组，打印每一个元素
        for (String str: strArr) {
            System.out.print(str + '\t'); // "I" "love" "Java"
        }
        
    }
}

// 有几种特殊的分隔符：* ^ : | . \，要使用转义字符转义
// 以*切割
String str2 = "I*love*Java";
String[] strArr2 = str2.split("\\*");

// 以\切割
String str3 = "I\\love\\Java";
String[] strArr4 = str3.split("\\\\");

// 以|切割
String str4 = "I|love|Java";
String[] strArr4 = str4.split("\\|");

/*
另外，还有一个重载方法String[] split(String regex, int limit)，其第二个参数limit用以控制正则匹配被应用的次数，因此会影响结果的长度
*/
```

getBytes()：方法将字符串转换为byte数组

```Java
public class StringMethod4 {
    public static void main(String[] args) {
        String str2 = "我喜欢Java";
        System.out.println("将字符串转换为byte数组：");
        // 将字符串转换为字节数组
        byte[] ascii = str2.getBytes();
        // 遍历字节数组，打印每个元素
        for (byte aByte : ascii) {
            System.out.print(aByte + "\t");
        }
    }
}
/*
将字符串转换为byte数组：
-26	-120	-111	-27	-106	-100	-26	-84	-94	74	97	118	97	
*/

// 将字节数组转换为字符串的方法很简单，直接实例化一个字符串对象，将字节数组作为构造方法的参数即可：
// 此处的ascii为上面通过字符串转换的字节数组
String s = new String(ascii);

```

toLowerCase() 将字符串转换为小写
toUpperCase() 将字符串转换为大写

boolean equals(Object object)方法来比较字符串内容是否相同，返回一个布尔类型的结果。`==`运算符比较的是两个变量的**地址**而不是内容，因此不要用==来比较两个字符串是否相等

```java 
public class StringMethod6 {
    public static void main(String[] args) {
        // 用两种方法创建三个内容相同的字符串
        String str1 = "hello";
        String str2 = "hello";
        String str3 = new String("hello");
        System.out.println("使用equals()方法比较str1和str2的结果为：" + str1.equals(str2)); // ture
        System.out.println("使用==运算符比较str1和str2的结果为：" + (str1 == str2)); // ture
        System.out.println("使用==运算符比较str1和str2的结果为：" + (str1 == str2));// ture
        System.out.println("使用==运算符比较str1和str3的结果为：" + (str1 == str3));// false
    }
}
```

