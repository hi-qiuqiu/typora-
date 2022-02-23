我们看到，一个完整的Web应用程序的开发流程如下：

1. 编写Servlet；
2. 打包为war文件；
3. 复制到Tomcat的webapps目录下；
4. 启动Tomcat。

我们需要一种简单可靠，能直接在IDE中启动并调试webapp的方法。

因为Tomcat实际上也是一个Java程序，我们看看Tomcat的启动流程：

1. 启动JVM并执行Tomcat的`main()`方法；
2. 加载war并初始化Servlet；
3. 正常服务。

启动Tomcat无非就是设置好classpath并执行Tomcat某个jar包的`main()`方法，我们完全可以把Tomcat的jar包全部引入进来，然后自己编写一个`main()`方法，先启动Tomcat，然后让它加载我们的webapp就行。

Tomcat会自动加载当前工程作为根webapp，可直接在浏览器访问`http://localhost:8080/`

E:\typora笔记\后端学习\javaProjectCode\web\web-servlet-embedded

https://www.liaoxuefeng.com/wiki/1252599548343744/1266264743830016