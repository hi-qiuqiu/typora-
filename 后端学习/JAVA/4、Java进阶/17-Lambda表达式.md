Lambda表达式：

```Java
public class LambdaDemo1 {

    public static void main(String[] args) {

        // 实例化一个 Runnable 接口的匿名实现类对象
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello, 匿名内部类");
            }
        };
        // 执行匿名内部类的 run() 方法
        runnable.run();
    }

}
```

```Java
public class LambdaDemo1 {

    public static void main(String[] args) {

        // 实例化一个 Runnable 接口的匿名实现类对象
        Runnable runnable = () -> System.out.println("Hello, 匿名内部类");
        // 执行匿名内部类的 run() 方法
        runnable.run();
    }

}
```

1、基础语法
(param1, param2) -> {}

2、使用 Lambda 表达式可以省略掉括号中的类型

```Java
// 省略类型前代码
Consumer<String> consumer2 = (String s) -> {
    System.out.println(s);
};
consumer2.accept("你好，世界！");

// 省略类型后代码
Consumer<String> consumer3 = (s) -> {
    System.out.println(s);
};
consumer3.accept("你好，世界！");

/*
之所以能够省略括号中的数据类型，是因为我们在 Comsumer<String> 处已经指定了泛型，编译器可以推断出类型，后面就不用指定具体类型了。称为类型推断。
*/

```

3、 Lambda 表达式对于接口也是有要求的 —— 接口内部只能包含一个抽象方法，如果接口内部包含多个抽象方法，我们就无法使用 Lambda 表达式了，这样只包含一个抽象方法的接口，我们称为函数式接口