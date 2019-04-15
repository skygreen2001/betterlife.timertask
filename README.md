# betterlife.timertask

## 实现目标

### 描述

一般开发网站都采用php,但是需要配合一些系统定时任务,php对多线程支持比较弱,因此配合php开发的web网站，开发了这个通用的定时工作任务器。

### 核心实现功能

- 可定义定时时间：指定一个时间，周期等
- 可访问多个数据库[现支持Mysql,Sqlserver]
- 可发送邮件|短信|消息
- 可部署在windows上作为服务service
- 可部署在多个平台上,主流考虑部署在linux服务器上(UBuntu)
- 方便快捷开发,命名|代码通俗易懂 
- 提供工具类自动生成实体类,减少重复工作量

## 启动运行类

- Application.java : 主程序运行起点
- tools/AutoCodeDomain.java : 生动生成实体数据对象工具类

## 框架规划

- Spring + hibernate + quartz
- 支持mysql，sqlserver
- windows service
- Linux service
  

## 部署

### Mvn 打包
  - mvn -Dmaven.test.skip=true install
  - mvn package
  - 运行: target/./TimerTask-1.0.jar 


#### 定时任务环境部署

	- 每次需在服务器上执行以下指令

    ```
    >[本机] ssh root@xxx.xxx.com "[ -d /root/app/timertask ] && echo ok || mkdir -p /root/app/timertask"  [只需执行一次]
    >[本机] scp ./Deploy/Linux/timertask.sh root@xxx.xxx.com:/root/app/timertask/   [只需执行一次]
    >[本机] cd target && cp TimerTask-1.0.jar timertask.jar && scp ./timertask.jar root@xxx.xxx.com:/root/app/timertask/ && cd ../
    >[本机] ssh -t root@xxx.xxx.com "cd /root/app/timertask/ ; bash"
    
    >[服务器] service timertask restart
    ```

	- 第一次需在服务器上执行以下指令

    ```
    > cd /root/app/timertask/ && chmod 0755 timertask.jar
    > cp timertask.sh /etc/init.d/timertask && chmod 0755 /etc/init.d/timertask
    > update-rc.d timertask defaults
    > service timertask start

    ```

	- 查看log

		- 可通过修改文件:src/main/resources/logback-spring.xml 变量:SERVER_LOG_HOME 调整日志文件所在路径 

		```
		> tail -f -n 400 /var/log/timertask/logFile.%d{yyyy-MM-dd}.log
		```

#### 安装为Systemd服务
    - 在/etc/systemd/system目录下编辑配置文件: timertask.service
    ```
		[Unit]
		Description=timertask
		After=syslog.target

		[Service]
		User=timertask
		ExecStart=/root/app/TimerTask-1.0.jar
		SuccessExitStatus=143

		[Install]
		WantedBy=multi-user.target
    ```
	- 安装Java环境: 
      - sudo apt-get update
      - sudo apt-get install openjdk-8-jdk 
      - java -version
  
    - systemctl enable timertask.service

### Eclipse 打包
  - Eclipse:Export->Runnable JAR file->Select[Library handling:Copy required libraries into generated JAR]->Click Finish
  
### 在Windows下运行服务  
  - 一般相对项目根目录导出到Deploy/windows目录下:betterlife.timertask.jar
  - 在该目录下 运行指令:java -jar betterlife.timertask.jar
  - 默认运行:Deploy/startup.bat
  - [56.2 Microsoft Windows服务](http://blog.didispace.com/books/spring-boot-reference/VI.%20Deploying%20Spring%20Boot%20applications/56.2%20Microsoft%20Windows%20services.html)
  - [Running a Java Application as a Windows Service](http://wrapper.tanukisoftware.org)

## 开发工具

- [JDK(version:1.8)](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Spring Tools 4 for Eclipse](https://spring.io/tools)
- [Spring Tools 4 for Visual Studio Code](https://spring.io/tools)
- [Spring Tools 4 for Atom IDE](https://spring.io/tools)
- Spring Tools Suites(version:3.4.0):http://spring.io/tools/sts/all
	- 安装plugins[在Dashboard首页上]:
	- Groovy 2.1 compiler for groovy-eclipse
	- Groovy-Eclipse

## 注意事项

- 连接SqlServer数据库，需要做以下准备工作
	- 1.安装Microsoft JDBC Driver 4.0 for SQL Server:  
		- http://www.microsoft.com/zh-CN/download/details.aspx?displaylang=en&id=11774
	
	- 2.开启 TCP/IP服务
		*. 点击 开始 --> 所有程序 --> Microsoft SQL Server2005 --> 配置工具-->SQL Server configuration Manager-选择左边 SQL Server 2005 网络配置 -->双击 MSSQLSERVER 协议--> 选中 TCP/IP 右键 点击启用；

		*. 选择左边 SQLServer 2005 服务 --> 选择右边SQL Server(MSSQLSERVER) --> 右击选择重新启动 ； 等待 SQL 重启后即可

		- 注: 若启用TCP/ IP 网络协议后不重启服务器, SQL服务是不会生效的，
		- 特别注意: 若机器不是做服务器用的，不要乱开TCP/IP服务，特别是局域网，很易遭受攻击，没用时就禁用。
	
	- 3.运行maven工具:
   		 mvn install:install-file -Dfile=sqljdbc4.jar -Dpackaging=jar -DgroupId=com.microsoft.sqlserver -DartifactId=sqljdbc4 -Dversion=4.0

		 - 说明:其中sqljdbc4.jar是安装Microsoft JDBC Driver 4.0 for SQL Server后的目录下的文件: C:\Java\sqljdbc_4.0\chs[我把其放置在C:\Java目录下]  	