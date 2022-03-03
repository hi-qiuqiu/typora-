介于全自动ORM如Hibernate和手写全部如JdbcTemplate之间，还有一种半自动的ORM，它只负责把ResultSet自动映射到Java Bean，或者自动填充Java Bean参数，但仍需自己写出SQL。[MyBatis](https://mybatis.org/)就是这样一种半自动化ORM框架。

首先，我们要引入MyBatis本身，其次，由于Spring并没有像Hibernate那样内置对MyBatis的集成，所以，我们需要再引入MyBatis官方自己开发的一个与Spring集成的库：

- org.mybatis:mybatis:3.5.4
- org.mybatis:mybatis-spring:2.0.4

和前面一样，先创建`DataSource`是必不可少的：

```java
@Configuration
@ComponentScan
@EnableTransactionManagement
@PropertySource("jdbc.properties")
public class AppConfig {
    @Bean
    DataSource createDataSource() { 
        ikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(jdbcUsername);
        config.setPassword(jdbcPassword);
        config.addDataSourceProperty("autoCommit", "true");
        config.addDataSourceProperty("connectionTimeout", "5");
        config.addDataSourceProperty("idleTimeout", "60");
        return new HikariDataSource(config); 
    }
}
```

再回顾一下Hibernate和JPA的`SessionFactory`与`EntityManagerFactory`，MyBatis与之对应的是`SqlSessionFactory`和`SqlSession`：

| JDBC       | Hibernate      | JPA                  | MyBatis           |
| :--------- | :------------- | :------------------- | :---------------- |
| DataSource | SessionFactory | EntityManagerFactory | SqlSessionFactory |
| Connection | Session        | EntityManager        | SqlSession        |

可见，ORM的设计套路都是类似的。使用MyBatis的核心就是创建`SqlSessionFactory`，这里我们需要创建的是`SqlSessionFactoryBean`：

```java
@Bean
SqlSessionFactoryBean createSqlSessionFactoryBean(@Autowired DataSource dataSource) {
    var sqlSessionFactoryBean = new SqlSessionFactoryBean();
    sqlSessionFactoryBean.setDataSource(dataSource);
    return sqlSessionFactoryBean;
}
```

因为MyBatis可以直接使用Spring管理的声明式事务，因此，创建事务管理器和使用JDBC是一样的：

```java
@Bean
PlatformTransactionManager createTxManager(@Autowired DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
}
```

和Hibernate不同的是，MyBatis使用Mapper来实现映射，而且Mapper必须是接口。我们以User类为例，在User类和users表之间映射的UserMapper编写如下：

```java
public interface UserMapper {
	@Select("SELECT * FROM users WHERE id = #{id}")
	User getById(@Param("id") long id);
}
```

注意：这里的Mapper不是JdbcTemplate的RowMapper的概念，它是定义访问users表的接口方法。比如我们定义了一个`User getById(long)`的主键查询方法，不仅要定义接口方法本身，还要明确写出查询的SQL，这里用注解`@Select`标记。SQL语句的任何参数，都与方法参数按名称对应。例如，方法参数id的名字通过注解`@Param()`标记为`id`，则SQL语句里将来替换的占位符就是`#{id}`。

如果有多个参数，那么每个参数命名后直接在SQL中写出对应的占位符即可：

```java
@Select("SELECT * FROM users LIMIT #{offset}, #{maxResults}")
List<User> getAll(@Param("offset") int offset, @Param("maxResults") int maxResults);
```

注意：MyBatis执行查询后，将根据方法的返回类型自动把ResultSet的每一行转换为User实例，转换规则当然是按列名和属性名对应。如果列名和属性名不同，最简单的方式是编写SELECT语句的别名：

```sql
-- 列名是created_time，属性名是createdAt:
SELECT id, name, email, created_time AS createdAt FROM users
```

执行INSERT语句就稍微麻烦点，因为我们希望传入User实例，因此，定义的方法接口与`@Insert`注解如下：

```java
@Insert("INSERT INTO users (email, password, name, createdAt) VALUES (#{user.email}, #{user.password}, #{user.name}, #{user.createdAt})")
void insert(@Param("user") User user);
```

上述方法传入的参数名称是`user`，参数类型是User类，在SQL中引用的时候，以`#{obj.property}`的方式写占位符。和Hibernate这样的全自动化ORM相比，MyBatis必须写出完整的INSERT语句。

如果`users`表的id是自增主键，那么，我们在SQL中不传入id，但希望获取插入后的主键，需要再加一个`@Options`注解：

```java
@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
@Insert("INSERT INTO users (email, password, name, createdAt) VALUES (#{user.email}, #{user.password}, #{user.name}, #{user.createdAt})")
void insert(@Param("user") User user);
```

`keyProperty`和`keyColumn`分别指出JavaBean的属性和数据库的主键列名。

执行UPDATE和DELETE语句相对比较简单，我们定义方法如下：

```java
@Update("UPDATE users SET name = #{user.name}, createdAt = #{user.createdAt} WHERE id = #{user.id}")
void update(@Param("user") User user);

@Delete("DELETE FROM users WHERE id = #{id}")
void deleteById(@Param("id") long id);
```

有了`UserMapper`接口，还需要对应的实现类才能真正执行这些数据库操作的方法。虽然可以自己写实现类，但我们除了编写`UserMapper`接口外，还有`BookMapper`、`BonusMapper`……一个一个写太麻烦，因此，MyBatis提供了一个`MapperFactoryBean`来自动创建所有Mapper的实现类。可以用一个简单的注解来启用它：

```java
@MapperScan("com.itranswarp.learnjava.mapper")
...其他注解...
public class AppConfig {
    ...
}
```

有了`@MapperScan`，就可以让MyBatis自动扫描指定包的所有Mapper并创建实现类。在真正的业务逻辑中，我们可以直接注入：

```java
@Component
@Transactional
public class UserService {
    // 注入UserMapper:
    @Autowired
    UserMapper userMapper;

    public User getUserById(long id) {
        // 调用Mapper方法:
        User user = userMapper.getById(id);
        if (user == null) {
            throw new RuntimeException("User not found by id.");
        }
        return user;
    }
}
```

可见，业务逻辑主要就是通过`XxxMapper`定义的数据库方法来访问数据库。

### XML配置

上述在Spring中集成MyBatis的方式，我们只需要用到注解，并没有任何XML配置文件。MyBatis也允许使用XML配置映射关系和SQL语句，例如，更新`User`时根据属性值构造动态SQL：

```xml
<update id="updateUser">
  UPDATE users SET
  <set>
    <if test="user.name != null"> name = #{user.name} </if>
    <if test="user.hobby != null"> hobby = #{user.hobby} </if>
    <if test="user.summary != null"> summary = #{user.summary} </if>
  </set>
  WHERE id = #{user.id}
</update>
```

编写XML配置的优点是可以组装出动态SQL，并且把所有SQL操作集中在一起。缺点是配置起来太繁琐，调用方法时如果想查看SQL还需要定位到XML配置中。这里我们不介绍XML的配置方式，需要了解的童鞋请自行阅读[官方文档](https://mybatis.org/mybatis-3/zh/configuration.html)。

使用MyBatis最大的问题是所有SQL都需要全部手写，优点是执行的SQL就是我们自己写的SQL，对SQL进行优化非常简单，也可以编写任意复杂的SQL，或者使用数据库的特定语法，但切换数据库可能就不太容易。好消息是大部分项目并没有切换数据库的需求，完全可以针对某个数据库编写尽可能优化的SQL。