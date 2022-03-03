使用Annitation   IOC：

.xml配置：所有的Bean都能一目了然地列出来，并通过配置注入能直观地看到每个Bean的依赖。它的缺点是写起来非常繁琐，每增加一个组件，就必须把新的Bean配置到XML中。

 使用Annitation： 可以完全不需要XML，让Spring自动扫描Bean并组装它们：

- @Component：定义一个bean

- @Autowired：注入，它不但可以写在`set()`方法上，还可以直接写在字段上，甚至可以写在构造方法中：

  ```java
  @Component
  public class UserService {
      MailService mailService;
  
      public UserService(@Autowired MailService mailService) {
          this.mailService = mailService;
      }
      ...
  }
  ```

  我们一般把`@Autowired`写在字段上，通常使用package权限的字段，便于测试。
  
  最后，编写一个`AppConfig`类启动容器：
  
  ```Java
  @Configuration
  @ComponentScan
  public class AppConfig {
      public static void main(String[] args) {
          ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
          UserService userService = context.getBean(UserService.class);
          User user = userService.login("bob@example.com", "password");
          System.out.println(user.getName());
      }
  }
  ```
  
  除了`main()`方法外，`AppConfig`标注了`@Configuration`，表示它是一个配置类，因为我们创建`ApplicationContext`时：

此外，`AppConfig`还标注了`@ComponentScan`，它告诉容器，自动搜索当前类所在的包以及子包，把所有标注为`@Component`的Bean自动创建出来，并根据`@Autowired`进行装配。

整个工程结构如下：

```ascii
spring-ioc-annoconfig
├── pom.xml
└── src
    └── main
        └── java
            └── com
                └── itranswarp
                    └── learnjava
                        ├── AppConfig.java
                        └── service
                            ├── MailService.java
                            ├── User.java
                            └── UserService.java
```

使用Annotation配合自动扫描能大幅简化Spring的配置，我们只需要保证：

- 每个Bean被标注为`@Component`并正确使用`@Autowired`注入；
- 配置类被标注为`@Configuration`和`@ComponentScan`；
- 所有Bean均在指定包以及子包内。

使用`@ComponentScan`非常方便，但是，我们也要特别注意包的层次结构。通常来说，启动配置`AppConfig`位于自定义的顶层包（例如`com.itranswarp.learnjava`），其他Bean按类别放入子包。



