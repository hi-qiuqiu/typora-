一个Web App就是由一个或多个Servlet组成的，每个Servlet通过注解说明自己能处理的路径。

```java
@WebServlet(urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {
    ...
}
// 早期的Servlet需要在web.xml中配置映射路径，但最新Servlet版本只需要通过注解就可以完成映射。
```

浏览器发出的HTTP请求总是由Web Server先接收，然后，根据Servlet配置的映射，不同的路径转发到不同的Servlet：<img src="C:\Users\QY\AppData\Roaming\Typora\typora-user-images\image-20220223171641583.png" alt="image-20220223171641583" style="zoom:50%;" />

https://www.liaoxuefeng.com/wiki/1252599548343744/1328705066500130
