Filter是一种对HTTP请求进行预处理的组件，它可以构成一个处理链，使得公共处理代码能集中到一起；Filter适用于日志、登录检查、全局设置等。

<img src="C:\Users\QY\AppData\Roaming\Typora\typora-user-images\image-20220224142320375.png" alt="image-20220224142320375" style="zoom:50%;" />

各个Servlet设计功能如下：

- IndexServlet：浏览帖子；
- SignInServlet：登录；
- SignOutServlet：登出；
- ProfileServlet：修改用户资料；
- PostServlet：发帖；
- ReplyServlet：回复。

其中，ProfileServlet、PostServlet和ReplyServlet都需要用户登录后才能操作，否则，应当直接跳转到登录页面。为了把一些公用逻辑从各个Servlet中抽离出来，JavaEE的Servlet规范还提供了一种Filter组件，即过滤器，它的作用是，在HTTP请求到达Servlet之前，可以被一个或多个Filter预处理，类似打印日志、登录检查等逻辑，完全可以放到Filter中。

例如，我们编写一个最简单的EncodingFilter，它强制把输入和输出的编码设置为UTF-8：

```Java
@WebFilter(urlPatterns = "/*")
public class EncodingFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("EncodingFilter:doFilter");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        chain.doFilter(request, response);
    }
}
```

编写Filter时，必须实现`Filter`接口，在`doFilter()`方法内部，要继续处理请求，必须调用`chain.doFilter()`。最后，用`@WebFilter`注解标注该Filter需要过滤的URL。这里的`/*`表示所有路径。

添加了Filter之后，整个请求的处理架构如下：

<img src="C:\Users\QY\AppData\Roaming\Typora\typora-user-images\image-20220224142501239.png" alt="image-20220224142501239" style="zoom: 33%;" />

https://www.liaoxuefeng.com/wiki/1252599548343744/1266264823560128