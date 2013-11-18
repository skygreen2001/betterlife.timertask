实现目标:
	描述:一般开发网站都采用php,但是需要配合一些系统定时任务,php对多线程支持比较弱,因此配合php开发的web网站，开发了这个通用的定时工作任务器。
	核心实现功能:
		1.可定义定时时间：指定一个时间，周期等
	 	2.可访问多个数据库[现支持Mysql,Sqlserver]
	 	3.可发送邮件|短信|消息
	 	4.可部署在windows上作为服务service
	 	5.可部署在多个平台上,主流考虑部署在linux服务器上(UBuntu)
   		6.方便快捷开发,命名|代码通俗易懂 
   		7.提供工具类自动生成实体类,减少重复工作量
   
启动运行类:
    Application.java : 主程序运行起点
    tools/AutoCodeDomain.java : 生动生成实体数据对象工具类


框架规划:      
	spring+hibernate+quartz
	windows service
			 支持mysql，sqlserver


开发工具:
	JDK                (version:1.7)  :http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html
	Spring Tools Suites(version:3.4.0):http://spring.io/tools/sts/all
	Maven              (version:3.1.1):http://maven.apache.org/download.cgi

注意事项:
连接SqlServer数据库，需要做以下准备工作
1.安装Microsoft JDBC Driver 4.0 for SQL Server:  
	http://www.microsoft.com/zh-CN/download/details.aspx?displaylang=en&id=11774
	
2.开启 TCP/IP服务
	*.点击 开始 --> 所有程序 --> Microsoft SQL Server2005 --> 配置工具-->SQL Server configuration Manager-选择左边 SQL Server 2005 网络配置 -->双击 MSSQLSERVER 协议--> 选中 TCP/IP 右键 点击启用；
	*.选择左边 SQLServer 2005 服务 --> 选择右边SQL Server(MSSQLSERVER) --> 右击选择重新启动 ； 等待 SQL 重启后即可
	注： 若启用TCP/ IP 网络协议 后不重启服务器  SQL 服务是不会生效的，
	特别注意： 若机器不是做服务器用的，不要乱开TCP/ IP 服务，特别是局域网，很易遭受攻击，没用时就禁用。
	
3.运行maven工具:
   mvn install:install-file -Dfile=sqljdbc4.jar -Dpackaging=jar -DgroupId=com.microsoft.sqlserver -DartifactId=sqljdbc4 -Dversion=4.0
		 说明:其中sqljdbc4.jar是 安装Microsoft JDBC Driver 4.0 for SQL Server后的目录下的文件:C:\Java\sqljdbc_4.0\chs[我把其放置在C:\Java目录下]  	