1、Optional类

- 空指针异常（NullPointerExceptions）是 Java 最常见的异常之一，一直以来都困扰着 Java 程序员。一方面，程序员不得不在代码中写很多null的检查逻辑，让代码看起来非常臃肿；另一方面，由于其属于运行时异常，是非常难以预判的。
- Optional 类位于java.util包下，是一个可以为 null 的容器对象，如果值存在则isPresent()方法会返回 true ，调用 get() 方法会返回该对象，可以有效避免空指针异常。

2、创建Optional对象

查看 java.util.Optional类源码，可以发现其构造方法是私有的，因此不能通过new关键字来实例化：<img src="http://img.mukewang.com/wiki/5f113820095ec00414660514.jpg" alt="img" style="zoom:33%;" />

我们可以通过如下几种方法，来创建Optional 对象：

- Optional.of(T t)：创建一个 Optional 对象，参数 t 必须非空；
- Optional.empty()：创建一个空的Optional实例；
- Optional.ofNullable(T t)：创建一个Optional对象，参数t 可以为 null。

```Java
import java.util.Optional;

public class OptionalDemo1 {

    public static void main(String[] args) {
        // 创建一个 StringBuilder 对象
        StringBuilder string = new StringBuilder("我是一个字符串");
        
        // 使用 Optional.of(T t) 方法，创建 Optional 对象，注意 T 不能为空：
        Optional<StringBuilder> stringBuilderOptional = Optional.of(string);
        System.out.println(stringBuilderOptional);

        // 使用 Optional.empty() 方法，创建一个空的 Optional 对象：
        Optional<Object> empty = Optional.empty();
        System.out.println(empty);

        // 使用 Optional.ofNullable(T t) 方法，创建 Optional 对象，注意 t 允许为空：
        stringBuilderOptional = null;
        Optional<Optional<StringBuilder>> stringBuilderOptional1 = Optional.ofNullable(stringBuilderOptional);
        System.out.println(stringBuilderOptional1);
    }

}

/*
Optional[我是一个字符串]
Optional.empty
Optional.empty
*/
```

3、常用方法

Optional<T>类提供了如下常用方法：

- booean isPresent()：判断是否包换对象；
- void ifPresent(Consumer<? super T> consumer)：如果有值，就执行 Consumer 接口的实现代码，并且该值会作为参数传递给它；
- T get()：如果调用对象包含值，返回该值，否则抛出异常；
- T orElse(T other)：如果有值则将其返回，否则返回指定的other 对象；
- T orElseGet(Supplier<? extends T other>)：如果有值则将其返回，否则返回由Supplier接口实现提供的对象；
- T orElseThrow(Supplier<? extends X> exceptionSupplier)：如果有值则将其返回，否则抛出由Supplier接口实现提供的异常。

以下代码运行提示空指针异常：

```Java
import java.util.Optional;

public class OptionalDemo2 {

    static class Category {
        private String name;

        public Category(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Category{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    static class Goods {
        private String name;

        private Category category;

        public Goods() {

        }

        public Goods(String name, Category category) {
            this.name = name;
            this.category = category;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Category getCategory() {
            return category;
        }

        public void setCategory(Category category) {
            this.category = category;
        }

        @Override
        public String toString() {
            return "Good{" +
                    "name='" + name + '\'' +
                    ", category=" + category +
                    '}';
        }
    }

    /**
     * 获取商品的分类名称
     * @param goods 商品
     * @return 分类名称
     */
    static String getGoodsCategoryName(Goods goods) {
        return goods.getCategory().getName();
    }

    public static void main(String[] args) {
        // 实例化一个商品类
        Goods goods = new Goods();
        // 获取商品的分类名称
        String categoryName = getGoodsCategoryName(goods);
        System.out.println(categoryName);
    }
}

/*
实例中，由于在实例化Goods类时，我们没有给其下面的Category类型的属性category赋值，它就为 null，在运行时， null.getName()就会抛出空指针异常。同理，如果goods实例为null，那么null.getCategory()也会抛出空指针异常。
*/
```

```java 
// 修改：
static String getGoodsCategoryName(Goods goods) {
    if (goods != null) {
        Category category = goods.getCategory();
        if (category != null) {
            return category.getName();
        }
    }
    return "该商品无分类";
}

```

将Optional类引入实例代码：

```Java
import java.util.Optional;

public class OptionalDemo3 {

    static class Category {
        private String name;

        public Category(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Category{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    static class Goods {
        private String name;

        private Category category;

        public Goods() {

        }

        public Goods(String name, Category category) {
            this.name = name;
            this.category = category;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Category getCategory() {
            return category;
        }

        public void setCategory(Category category) {
            this.category = category;
        }

        @Override
        public String toString() {
            return "Good{" +
                    "name='" + name + '\'' +
                    ", category=" + category +
                    '}';
        }
    }

    /**
     * 获取商品的分类名称（使用 Optional 类包装）
     * @param goods 商品
     * @return 分类名称
     */
    static String getGoodsCategoryName(Goods goods) {
        // 将商品实例包装入 Optional 类，创建 Optional<Goods> 对象
        Optional<Goods> goodsOptional = Optional.ofNullable(goods);
        Goods goods1 = goodsOptional.orElse(new Goods("默认商品", new Category("默认分类")));
        // 此时 goods1 一定是非空，不会产生空指针异常
        Category category = goods1.getCategory();

        // 将分类实例包装入 Optional 类，创建 Optional<Category> 对象
        Optional<Category> categoryOptional = Optional.ofNullable(category);
        Category category1 = categoryOptional.orElse(new Category("默认分类"));
        // 此时 category1 一定是非空，不会产生空指针异常
        return category1.getName();
    }

    public static void main(String[] args) {
        // 实例化一个商品类
        Goods goods = null;
        // 获取商品的分类名称
        String categoryName = getGoodsCategoryName(goods);
        System.out.println(categoryName);
    }
}

/*
实例中，我们使用Optional类的 ofNullable（T t）方法分别包装了goods对象及其级联属性category对象，允许对象为空，然后又调用了其ofElse(T t)方法保证了对象一定非空。这样，空指针异常就被我们优雅地规避掉了。
*/
```

4. 对于空指针异常的改进Java 14 对于空指针异常有了一些改进，它提供了更明确异常堆栈打印信息，JVM 将精确地确定那个变量是null，不过空指针异常依然无法避免。明确的异常堆栈信息，能够帮助开发者快速定位错误发生的位置。

