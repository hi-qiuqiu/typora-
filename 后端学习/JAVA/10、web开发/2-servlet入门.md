#### Servlet入门

要编写一个完善的HTTP服务器，以HTTP/1.1为例，需要考虑的包括：

- 识别正确和错误的HTTP请求；
- 识别正确和错误的HTTP头；
- 复用TCP连接；
- 复用线程；
- IO异常处理；
- ...

这些基础工作需要耗费大量的时间，并且经过长期测试才能稳定运行。如果我们只需要输出一个简单的HTML页面，就不得不编写上千行底层代码，那就根本无法做到高效而可靠地开发。

因此，在JavaEE平台上，处理TCP连接，解析HTTP协议这些底层工作统统扔给现成的Web服务器去做，我们只需要把自己的应用程序跑在Web服务器上。为了实现这一目的，JavaEE提供了Servlet API，我们使用Servlet API编写自己的Servlet来处理HTTP请求，Web服务器实现Servlet API接口，实现底层功能：<img src="C:\Users\QY\AppData\Roaming\Typora\typora-user-images\image-20220223110203598.png" alt="image-20220223110203598" style="zoom:50%;" />

实现一个最简单的Servlet：
一个Servlet总是继承自`HttpServlet`，然后覆写`doGet()`或`doPost()`方法

```java 
// WebServlet注解表示这是一个Servlet，并映射到地址/:
@WebServlet(urlPatterns = "/")
public class HelloServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 设置响应类型:
        resp.setContentType("text/html");
        // 获取输出流:
        PrintWriter pw = resp.getWriter();
        // 写入响应:
        pw.write("<h1>Hello, world!</h1>");
        // 最后不要忘记flush强制输出:
        pw.flush();
    }
}

// 注意到doGet()方法传入了HttpServletRequest和HttpServletResponse两个对象，分别代表HTTP请求和响应。我们使用Servlet API时，并不直接与底层TCP交互，也不需要解析HTTP协议，因为HttpServletRequest和HttpServletResponse就已经封装好了请求和响应。以发送响应为例，我们只需要设置正确的响应类型，然后获取PrintWriter，写入响应即可
```

Servlet API是谁提供？
Servlet API是一个jar包，我们需要通过Maven来引入它，才能正常编译。
具体操作：E:\typora笔记\后端学习\javaProjectCode\servletWeb

运行Maven命令`mvn clean package`，在`target`目录下得到一个`hello.war`文件，这个文件就是我们编译打包后的Web应用程序。现在问题又来了：我们应该如何运行这个`war`文件？

普通的Java程序是通过启动JVM，然后执行`main()`方法开始运行。但是Web应用程序有所不同，我们无法直接运行`war`文件，必须先启动Web服务器，再由Web服务器加载我们编写的`HelloServlet`，这样就可以让`HelloServlet`处理浏览器发送的请求。

因此，我们首先要找一个支持Servlet API的Web服务器。常用的服务器有：

- [Tomcat](https://tomcat.apache.org/)：由Apache开发的开源免费服务器；
- [Jetty](https://www.eclipse.org/jetty/)：由Eclipse开发的开源免费服务器；
- [GlassFish](https://javaee.github.io/glassfish/)：一个开源的全功能JavaEE服务器。

要运行我们的`hello.war`，首先要[下载Tomcat服务器](https://tomcat.apache.org/download-90.cgi)，解压后，把`hello.war`复制到Tomcat的`webapps`目录下，然后切换到`bin`目录，执行`startup.sh`或`startup.bat`启动Tomcat服务器，在浏览器输入`http://localhost:8080/hello/`即可看到`HelloServlet`的输出：<img src="C:\Users\QY\AppData\Roaming\Typora\typora-user-images\image-20220223145448461.png" alt="image-20220223145448461" style="zoom: 33%;" />

为啥路径是`/hello/`而不是`/`？因为一个Web服务器允许同时运行多个Web App，而我们的Web App叫`hello`，因此，第一级目录`/hello`表示Web App的名字，后面的`/`才是我们在`HelloServlet`中映射的路径。

如果要直接使用/，先关闭Tomcat（执行`shutdown.sh`或`shutdown.bat`），然后删除Tomcat的webapps目录下的所有文件夹和文件，最后把我们的`hello.war`复制过来，改名为`ROOT.war`，文件名为`ROOT`的应用程序将作为默认应用，启动后直接访问`http://localhost:8080/`即可。

实际上，类似Tomcat这样的服务器也是Java编写的，启动Tomcat服务器实际上是启动Java虚拟机，执行Tomcat的`main()`方法，然后由Tomcat负责加载我们的`.war`文件，并创建一个`HelloServlet`实例，最后以多线程的模式来处理HTTP请求。如果Tomcat服务器收到的请求路径是`/`（假定部署文件为ROOT.war），就转发到`HelloServlet`并传入`HttpServletRequest`和`HttpServletResponse`两个对象。

因为我们编写的Servlet并不是直接运行，而是由Web服务器加载后创建实例运行，所以，类似Tomcat这样的Web服务器也称为Servlet容器。

在Servlet容器中运行的Servlet具有如下特点：

- 无法在代码中直接通过new创建Servlet实例，必须由Servlet容器自动创建Servlet实例；
- Servlet容器只会给每个Servlet类创建唯一实例；
- Servlet容器会使用多线程执行`doGet()`或`doPost()`方法。

复习一下Java[多线程](https://www.liaoxuefeng.com/wiki/1252599548343744/1255943750561472)的内容，我们可以得出结论：

- 在Servlet中定义的实例变量会被多个线程同时访问，要注意线程安全；
- `HttpServletRequest`和`HttpServletResponse`实例是由Servlet容器传入的局部变量，它们只能被当前线程访问，不存在多个线程访问的问题；
- 在`doGet()`或`doPost()`方法中，如果使用了`ThreadLocal`，但没有清理，那么它的状态很可能会影响到下次的某个请求，因为Servlet容器很可能用线程池实现线程复用。

因此，正确编写Servlet，要清晰理解Java的多线程模型，需要同步访问的必须同步。
