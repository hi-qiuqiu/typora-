使用`JdbcTemplate`的时候，我们用得最多的方法就是`List<T> query(String sql, Object[] args, RowMapper rowMapper)`。这个`RowMapper`的作用就是把`ResultSet`的一行记录映射为Java Bean。

这种把关系数据库的表记录映射为Java对象的过程就是**ORM**：**Object-Relational Mapping**。ORM既可以把记录转换成Java对象，也可以把Java对象转换为行记录。

使用`JdbcTemplate`配合`RowMapper`可以看作是最原始的ORM。如果要实现更自动化的ORM，可以选择成熟的ORM框架，例如[Hibernate](https://hibernate.org/)。