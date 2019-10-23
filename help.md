# easyBox
### 简介
	这是一个简单，轻量，实用的框架。
	在这里，您可以专注于自己的业务，而不需要关心与数据库有关的问题，您甚至不需要关心表的创建与更新，easyBox可以帮您自动创建与更新数据表。
	用最少得编码写出高效的业务，没有最简单，只有更简单，这便是easyBox存在的意义
	我希望让初学者能够轻松搭建一个高并发，低延迟的框架。
### 架构
	easyBox整合了`springboot`+`mybatis`，数据库暂时只支持`oracle`。
###依赖
easyBox自动依赖了最基本的pom，分别是

`ojdbc14`：连接oracle数据库

`lombok`：使编码更优雅，如果您对于lombok使用方式有疑问，可以[点击这里](https://blog.csdn.net/qq_25861361/article/details/90475663 "点击这里")

`spring-boot-starter-aop`：springaop

`spring-boot-starter-web`：springweb

`mybatis-spring-boot-starter`：springboot基本依赖

`gson`：gson

如果您有这几个包的需要可以直接使用，而不需要自己添加
### 使用说明
您可以在pom文件之中添加如下依赖(这里总是最新版本)

```xml
 <dependency>
   	<groupId>com.github.ww2510095</groupId>
   	<artifactId>easybox</artifactId>
   	<version>0.2.2-RELEASE</version>
  </dependency>`
```
### 使用文档
#### 组成部分
easyBox大约分为:缓存，自动化生成表与代码，数据库，安全，工具部分以及核心6个部分组成
#### 核心
	@EasyBoxScan
easyBox的核心便是这个注解，需要添加到启动文档上面

说明：

`@EasyBoxScan`提供了3个参数

	1:beanUrl;

原型`String[] beanUrl() default {};`

	参数的意思是：需要扫描的javaBean路径
 	此参数可以为您纠正一些没必要的错误，不建议使用空值
 	如果您开启了轨迹日志功能，那么范围只限于扫描下的范围
 	如果您开启了自动创建表的功能，同样表的创建也只限于扫描的返回
 	建议您使用您的顶级包名，如:org.yly.framework.easybox

<font color="red">注意:这里需要的是您的最外层包名，这样在启动的时候将浪费您不少的时间</font>

	2:createTab;

原型：`tabBean  createTab() default tabBean.Bean2Tab;`

参数需要的是一个枚举，具体如下图所示

![1](https://gitee.com/ww2510095/picture_warehouse/raw/master/easyBox/1.png "1")

<font color="red">注意:easyBox自带的几张表不管您采用什么模式，他们总是Bean2Tab</font>
 

