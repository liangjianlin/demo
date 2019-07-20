# IDEA + Spring Boot + Maven + Docker + Jenkins

在IntelliJ IDEA上使用Spring Boot 编写一个Hello World应用，使用Maven插件(docker-maven-plugin)，自动把镜像推送到CentOS里的Docker。

# 安装 IntelliJ IDEA

> 到官网下载 http://www.jetbrains.com/idea/download/#section=windows
### License server
* http://idea.youbbs.org

# 安装JDK
1. 下载地址：https://www.oracle.com/technetwork/java/javaee/downloads/index.html
2. 设置 JAVA 环境变量 JAVA_HOME
3. 系统变量 **Path**，添加变量值：;%JAVA_HOME%\bin
4. 验证是否安装完成，在cmd窗口：
> java
> javac

# 安装 Maven
1. 下载地址：http://maven.apache.org/download.cgi
2. 设置 Maven 环境变量 MAVEN_HOME
3. 系统变量 **Path**，添加变量值：;%MAVEN_HOME%\bin
4. 验证是否安装完成，在cmd窗口:
> mvn -v


# IntelliJ IDEA + Maven
IntelliJ IDEA
File -> Settings -> Build, Execution, Deployment -> Build Tools -> Maven
设置例子：
1. Maven home directory: D:/apache-maven-3.5.0
2. User settings file: D:\apache-maven-3.5.0\conf\settings.xml
3. Local repository: F:\repository

使用阿里云仓库
修改settings.xml添加
```
<mirror>  
  <id>alimaven</id>  
  <name>aliyun maven</name>  
  <url>http://maven.aliyun.com/nexus/content/groups/public/</url>  
  <mirrorOf>central</mirrorOf>          
</mirror>
```
# 使用Spring Boot创建Hello World应用

File -> New -> Project... -> Spring Initializr -> Next -> Next -> Next -> Finish

pom.xml

```
<dependency>  
 <groupId>org.springframework.boot</groupId>  
 <artifactId>spring-boot-starter-web</artifactId>  
</dependency>
```

DemoController.java

```
@RestController  
public class DemoController {  
  
    @RequestMapping("/demo")  
    public String helloWeb() {  
        return "Hello Web";  
  }  
}
```

# Centos 7 安装Docker

> 官网教程 https://docs.docker.com/install/linux/docker-ee/centos/
> 中文教程 https://www.widuu.com/chinese_docker/index.html

# 创建镜像

> mvn clean install docker:build


# CentOS 7 关闭防火墙

查看防火墙状态

```
firewall-cmd --state
```

停止firewall

```
systemctl stop firewalld.service
```

禁止firewall开机启动

```
systemctl disable firewalld.service 
```

## # Docker开启远程API端口

Docker开启远程端口的目的是可以通过Docker提供的 remoteApi文档 管理Docker并且可以操作Docker下容器，监控容器的各项指标，也可以通过remoteApi去实现自己监控Docker告警系统等。默认Docker并没有启动remoteApi，需要我们修改配置才能生效。

默认Centos7.X下配置文件地址在 /usr/lib/systemd/system/ 下面，修改 /usr/lib/systemd/system/docker.service 文件
> sudo vim /usr/lib/systemd/system/docker.service

在 ExecStart=/usr/bin/dockerd 配置文件后面加上 -H tcp://0.0.0.0:2375 -H unix://var/run/docker.sock 保存并退出。

注 :  端口 2375 就是docker remoteApi的 端口，确保此端口linux没有被占用。
执行 重启 docker 命令  docker重新读取配置文件，并重新启动docker服务 
> sudo systemctl daemon-reload && systemctl restart docker

# Pom.xml

```
<?xml version="1.0" encoding="UTF-8"?>  
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">  
 <modelVersion>4.0.0</modelVersion>  
 <parent> <groupId>org.springframework.boot</groupId>  
 <artifactId>spring-boot-starter-parent</artifactId>  
 <version>2.1.6.RELEASE</version>  
 <relativePath/> <!-- lookup parent from repository -->  
  </parent>  
 <groupId>com.example</groupId>  
 <artifactId>demo</artifactId>  
 <version>0.0.1-SNAPSHOT</version>  
 <name>demo</name>  
 <description>Demo project for Spring Boot</description>  
  
 <properties> <java.version>1.8</java.version>  
 </properties>  
 <dependencies> <dependency> <groupId>org.springframework.boot</groupId>  
 <artifactId>spring-boot-starter-web</artifactId>  
 </dependency> <dependency> <groupId>org.springframework.boot</groupId>  
 <artifactId>spring-boot-starter-actuator</artifactId>  
 </dependency> <dependency> <groupId>org.springframework.boot</groupId>  
 <artifactId>spring-boot-devtools</artifactId>  
 <optional>true</optional>  
 </dependency> <dependency> <groupId>org.springframework.boot</groupId>  
 <artifactId>spring-boot-starter-test</artifactId>  
 <scope>test</scope>  
 </dependency> </dependencies>  
 <build> <plugins> <plugin> <groupId>org.springframework.boot</groupId>  
 <artifactId>spring-boot-maven-plugin</artifactId>  
 <configuration> <fork>true</fork>  
 </configuration> </plugin>  <!-- 添加docker-maven-plugin插件 -->  
  <plugin>  
 <groupId>com.spotify</groupId>  
 <artifactId>docker-maven-plugin</artifactId>  
 <version>1.2.0</version>  
 <executions> <execution> <id>default</id>  
 <goals> <goal>build</goal>  
 <goal>push</goal>  
 </goals> </execution> </executions> <configuration>  <!-- 配置docker server 位置，否则默认127.0.0.1 -->  
  <dockerHost>http://192.168.0.12:2375</dockerHost>  
  <!-- imageName冒号前面为容器名，后面是容器版本号 -->  
  <imageName>${project.artifactId}:${project.version}</imageName>  
  <!-- Dockerfile 文件位置 -->  
  <dockerDirectory>${project.basedir}</dockerDirectory>  
 <resources> <resource> <targetPath>/</targetPath>  
 <directory>${project.build.directory}</directory>  
 <include>${project.build.finalName}.jar</include>  
 </resource> </resources> </configuration> </plugin> </plugins> </build>  
</project>
```



# CentOS7 + Docker + Jenkins
> 需要在CentOS7上安装Docker
> 官网：https://jenkins.io/zh/
> 镜像下载：https://hub.docker.com/_/jenkins
> 
拉取Jenkins官方镜像
> docker pull jenkins

查看镜像是否拉取完成
> docker images

创建容器
```
docker create -p 8888:8080 -p 50000:50000 --name jenkins -u root -e TZ="Asia/Shanghai" -v /root:/var/jenkins_home jenkins 
```
查看创建的容器
```
docker container ls -a
```
启动容器
```
docker container start jenkins
```
会自动创建一个admin用户，密码
/var/jenkins_home/secrets/initialAdminPassword

访问：http://[IP]:8888 输入密码初始化Jenkins，创建用户，创建成功。

# Jenkins自动化部署

# Portainer 安装部署
查找镜像
docker search portainer
拉取镜像
docker pull portainer/portainer
运行镜像 
docker run -d -p 9000:9000 --name portainer --restart always -v /var/run/docker.sock:/var/run/docker.sock -v /root/portainer/data:/data portainer/portainer


# 问题：WARNING: IPv4 forwarding is disabled. Networking will not work.
解决办法：https://www.2cto.com/net/201710/688466.html
