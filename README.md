# 什么是mybatis

MyBatis是持久层框架，它支持自定义SQL、存储过程以及高级映射。MyBatis减少了JDBC设置参数和获取结果集的代码工作。MyBatis通过XML或注解将java的原始类型、接口、POJO映射到数据库中记录。

# 入门

## 安装Mybatis

项目采用Maven构建，只需要导入mybatis依赖：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>Mybatis-Tutorial</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
    </properties>
    <dependencies>
        <!-- MyBatis -->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.16</version>
        </dependency>
        <!-- MySQL Connector -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.17</version>
        </dependency>
        <!-- HikariCP -->
        <dependency>
            <groupId>com.zaxxer</groupId>
            <artifactId>HikariCP</artifactId>
            <version>4.0.3</version>
        </dependency>
        <!-- SLF4J API -->
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>1.7.30</version>
        </dependency>
        <!-- Logback Classic -->
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>1.2.3</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.30</version>
        </dependency>
    </dependencies>
</project>
```

## 构建SqlSessionFactory

MyBatis 的核心是 `SqlSessionFactory`，它负责创建和管理 `SqlSession` 对象，用于执行 SQL 语句、获取映射器（Mapper）以及管理事务。要获得 `SqlSessionFactory` 实例，可以使用 `SqlSessionFactoryBuilder`。`SqlSessionFactoryBuilder` 能够通过读取 XML 配置文件（如 `mybatis-config.xml`）或者通过编程方式配置的 `Configuration` 对象来构建 `SqlSessionFactory` 实例。

### 基于xml构建

从xml构建SqlSessionFactory,需要读取类路径下的资源配置文件，mybatis提供了一个Resources工具类，可以更加简单读取。

```java
public class BuildSqlSessionFactoryByXml {
    public SqlSessionFactory getSqlSessionFactoryByXml() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory;
    }
}
```

**mybatis-config.xml**

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "https://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="com.zaxxer.hikari.HikariDataSource">
                <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
                <property name="jdbcUrl" value="jdbc:mysql://localhost:3306/test"/>
                <property name="username" value="root"/>
                <property name="password" value="123456"/>
                <property name="maximumPoolSize" value="10"/>
                <property name="minimumIdle" value="5"/>
                <property name="connectionTimeout" value="30000"/>
                <property name="idleTimeout" value="600000"/>
                <property name="maxLifetime" value="1800000"/>
            </dataSource>
        </environment>
    </environments>
</configuration>
```

### 基于Configruation构建

```java
public class BuildSqlSessionFactoryByConfig {
    public SqlSessionFactory getSqlSessionFactoryByXml() throws IOException {
        // 配置 HikariCP
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("123456");
        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setMinimumIdle(5);
        hikariConfig.setConnectionTimeout(30000);
        hikariConfig.setIdleTimeout(600000);
        hikariConfig.setMaxLifetime(1800000);

        DataSource dataSource = new HikariDataSource(hikariConfig);
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        return sqlSessionFactory;
    }
}
```

## 构建SqlSession

构建完了SqlSessionFactory之后我们就可以构建SqlSession了，可以进行数据库的查询操作了。

```java
public class BuildSqlSessionTest {

    @Test
    public void testBuildSqlSessionFactoryByXml() throws IOException {
        BuildSqlSessionFactoryByXml sqlSessionFactory = new BuildSqlSessionFactoryByXml();
        try (SqlSession sqlSession = sqlSessionFactory.getSqlSessionFactory().openSession()){
            BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
            Blog blog = mapper.selectBlog(1);
            System.out.println(blog.toString());
        }
    }

    @Test
    public void testBuildSqlSessionFactoryByConfig() throws IOException {
        BuildSqlSessionFactoryByConfig sqlSessionFactory = new BuildSqlSessionFactoryByConfig();
        try (SqlSession sqlSession = sqlSessionFactory.getSqlSessionFactory().openSession()){
            BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
            Blog blog = mapper.selectBlog(1);
            System.out.println(blog.toString());
        }

    }
}
```

构建SqlSessionFactory可以使用两种方式，但是当他们作用于同一个Mapper接口的时候会冲突，目前有三种构建SqlSessionFactory的方式，纯读取mybatis-config.xml、纯读取Configuration、两种混合使用构建，由于Java注解的限制，一些复杂的sql不好实现，一般实际采用的是，配置文件+配置类+映射文件+注解的方式使用。

## 作用域和生命周期

### SqlSessionFactoryBuilder

创建完SqlSessionFactory就可以放弃了，属于方法作用域

### SqlSessionFactory

创建完了就一直存在，每次使用都拿去创建SqlSession对象，作用域是应用作用域，应该使用单例模式或者静态单例模式。

### SqlSession

每个现成都有自己的sqlSession,SqlSession不是线程安全的，不能被共享，作用域是方法作用域，每次操作完就自动关闭，下面是最佳实践。

```java
try (SqlSession session = sqlSessionFactory.openSession()) {
  // 你的应用逻辑代码
}
```

### Mapper

映射器是用来绑定映射语句的接口，映射器的实例是从SqlSession中获取的，使用完即可关闭，最佳实践是放在方法中。

```java
try (SqlSession session = sqlSessionFactory.openSession()) {
  BlogMapper mapper = session.getMapper(BlogMapper.class);
  // 你的应用逻辑代码
}
```

# 配置文件

## mybatis配置文件结构

mybatis的配置文档的顶层结构如下

- configuration（配置）
  - properties（属性）
  - settings（设置）
  - typeAliases（类型别名）
  - typeHandlers（类型处理器）
  - objectFactory（对象工厂）
  - plugins（插件）
  - environments（环境配置）
    - environment（环境变量）
      - transactionManager（事务管理器）
      - dataSource（数据源）
  - databaseIdProvider（数据库厂商标识）
  - mappers（映射器）

## 属性

属性可以采用外部的配置文件，如jdbc.properties，将外部的配置文件引入后，可以在整个mybatis-config.xml配置文件中引用：

**jdbc.properties**

```properties
driver=com.mysql.cj.jdbc.Driver
url=jdbc:mysql://localhost:3306/mybatis_tutorial?serverTimezone=Asia/Shanghai
username=root
password=123456
```

**mybatis-config.xml**

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "https://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
	<!-- 引入外部配置文件 -->
    <properties resource="./jdbc.properties"></properties>

    <settings>
        <setting name="logImpl" value="SLF4J"/>
    </settings>

    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="${driver}"/>
                <property name="url" value="${url}"/>
                <property name="username" value="${username}"/>
                <property name="password" value="${password}"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <mapper resource="com.tutorial.mybatis.mapper/BlogMapper.xml"/>
    </mappers>
</configuration>
```



## 设置

## 类型别名

## 类型处理器

## 对象工厂

## 插件

## 环境配置

## 数据库厂商

## 映射器

# 日志

Mybatis 通过使用内置的日志工厂提供日志功能。内置日志工厂将会把日志工作委托给下面的实现之一：

- SLF4J
- Apache Commons Logging
- Log4j 2
- Log4j（3.5.9起废弃）
- JDK logging

MyBatis 内置日志工厂基于运行时自省机制选择合适的日志工具。它会使用第一个查找得到的工具（按上文列举的顺序查找）。如果一个都未找到，日志功能就会被禁用。

不少应用服务器（如 Tomcat 和 WebShpere）的类路径中已经包含 Commons Logging，所以在这种配置环境下的 MyBatis 会把它作为日志工具，记住这点非常重要。这将意味着，在诸如 WebSphere 的环境中，它提供了 Commons Logging 的私有实现，你的 Log4J 配置将被忽略。MyBatis 将你的 Log4J 配置忽略掉是相当令人郁闷的（事实上，正是因为在这种配置环境下，MyBatis 才会选择使用 Commons Logging 而不是 Log4J）。如果你的应用部署在一个类路径已经包含 Commons Logging 的环境中，而你又想使用其它日志工具，你可以通过在 MyBatis 配置文件 mybatis-config.xml 里面添加一项 setting 来选择别的日志工具。

```xml
<configuration>
  <settings>
    ...
    <setting name="logImpl" value="LOG4J"/>
    ...
  </settings>
</configuration>
```

logImpl 可选的值有：SLF4J、LOG4J、LOG4J2、JDK_LOGGING、COMMONS_LOGGING、STDOUT_LOGGING、NO_LOGGING，或者是实现了接口 `org.apache.ibatis.logging.Log` 的，且构造方法是以字符串为参数的类的完全限定名。（译者注：可以参考org.apache.ibatis.logging.slf4j.Slf4jImpl.java的实现）

你也可以调用如下任一方法来使用日志工具：

```java
org.apache.ibatis.logging.LogFactory.useSlf4jLogging();
org.apache.ibatis.logging.LogFactory.useLog4JLogging();
org.apache.ibatis.logging.LogFactory.useLog4J2Logging();
org.apache.ibatis.logging.LogFactory.useJdkLogging();
org.apache.ibatis.logging.LogFactory.useCommonsLogging();
org.apache.ibatis.logging.LogFactory.useStdOutLogging();
```

如果你决定要调用以上某个方法，请在调用其它 MyBatis 方法之前调用它。另外，仅当运行时类路径中存在该日志工具时，调用与该日志工具对应的方法才会生效，否则 MyBatis 一概忽略。如你环境中并不存在 Log4J2，你却调用了相应的方法，MyBatis 就会忽略这一调用，转而以默认的查找顺序查找日志工具。

关于 SLF4J、Apache Commons Logging、Apache Log4J 和 JDK Logging 的 API 介绍不在本文档介绍范围内。不过，下面的例子可以作为一个快速入门。关于这些日志框架的更多信息，可以参考以下链接：

- [SLF4J](https://www.slf4j.org/)
- [Apache Commons Logging](https://commons.apache.org/proper/commons-logging/)
- [Apache Log4j 2.x](https://logging.apache.org/log4j/2.x/)
- [JDK Logging API](https://docs.oracle.com/javase/8/docs/technotes/guides/logging/overview.html)

## 日志配置

你可以对包、映射类的全限定名、命名空间或全限定语句名开启日志功能来查看 MyBatis 的日志语句。

再次说明下，具体怎么做，由使用的日志工具决定，这里以 SLF4J(Logback) 为例。配置日志功能非常简单：添加一个或多个配置文件（如 `logback.xml`），有时需要添加 jar 包。下面的例子将使用 SLF4J(Logback) 来配置完整的日志服务，共两个步骤：

### 步骤 1：添加 SLF4J + Logback 的 jar 包

因为我们使用的是 SLF4J(Logback)，就要确保它的 jar 包在应用中是可用的。要启用 SLF4J(Logback)，只要将 jar 包添加到应用的类路径中即可。SLF4J(Logback) 的 jar 包可以在上面的链接中下载。

对于 web 应用或企业级应用，则需要将 `logback-classic.jar`, `logback-core.jar` and `slf4j-api.jar` 添加到 `WEB-INF/lib` 目录下；对于独立应用，可以将它添加到JVM 的 `-classpath` 启动参数中。

如果你使用 maven, 你可以通过在 `pom.xml` 中添加下面的依赖来下载 jar 文件。

```xml
<dependency>
  <groupId>ch.qos.logback</groupId>
  <artifactId>logback-classic</artifactId>
  <version>1.x.x</version>
</dependency>
```

### 步骤 2：配置 Logback

配置 Logback 比较简单，假如你需要记录这个映射器接口的日志：

```java
package org.mybatis.example;
public interface BlogMapper {
  @Select("SELECT * FROM blog WHERE id = #{id}")
  Blog selectBlog(int id);
}
```

在应用的类路径中创建一个名称为 `logback.xml` 的文件，文件的具体内容如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration>
<configuration>

  <appender name="stdout" class="ch.qos.logback.core.ConsoleAppender">
    <encoder>
      <pattern>%5level [%thread] - %msg%n</pattern>
    </encoder>
  </appender>

  <logger name="org.mybatis.example.BlogMapper">
    <level value="trace"/>
  </logger>
  <root level="error">
    <appender-ref ref="stdout"/>
  </root>

</configuration>
```

添加以上配置后，SLF4J(Logback) 就会记录 `org.mybatis.example.BlogMapper` 的详细执行操作，且仅记录应用中其它类的错误信息（若有）。

你也可以将日志的记录方式从接口级别切换到语句级别，从而实现更细粒度的控制。如下配置只对 `selectBlog` 语句记录日志：

```xml
<logger name="org.mybatis.example.BlogMapper.selectBlog">
  <level value="trace"/>
</logger>
```

与此相对，可以对一组映射器接口记录日志，只要对映射器接口所在的包开启日志功能即可：

```xml
<logger name="org.mybatis.example">
  <level value="trace"/>
</logger>
```

某些查询可能会返回庞大的结果集，此时只想记录其执行的 SQL 语句而不想记录结果该怎么办？为此，Mybatis 中 SQL 语句的日志级别被设为DEBUG（JDK 日志设为 FINE），结果的日志级别为 TRACE（JDK 日志设为 FINER)。所以，只要将日志级别调整为 DEBUG 即可达到目的：

```xml
<logger name="org.mybatis.example">
  <level value="debug"/>
</logger>
```

要记录日志的是类似下面的映射器文件而不是映射器接口又该怎么做呢？

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "https://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="org.mybatis.example.BlogMapper">
  <select id="selectBlog" resultType="Blog">
    select * from Blog where id = #{id}
  </select>
</mapper>
```

如需对 XML 文件记录日志，只要对命名空间增加日志记录功能即可：

```xml
<logger name="org.mybatis.example.BlogMapper">
  <level value="trace"/>
</logger>
```

要记录具体语句的日志可以这样做：

```xml
<logger name="org.mybatis.example.BlogMapper.selectBlog">
  <level value="trace"/>
</logger>
```

你应该注意到了，为映射器接口和 XML 文件添加日志功能的语句毫无差别。

**注意** 如果你使用的是 SLF4J 或 Log4j 2，MyBatis 将以 `MYBATIS` 这个值进行调用。

配置文件 `log4j.properties` 的余下内容是针对日志输出源的，这一内容已经超出本文档范围。关于 Logback 的更多内容，可以参考[Logback](https://logback.qos.ch/) 的网站。不过，你也可以简单地做做实验，看看不同的配置会产生怎样的效果。

### Log4j 2 配置示例

```xml
<!-- pom.xml -->
<dependency>
  <groupId>org.apache.logging.log4j</groupId>
  <artifactId>log4j-core</artifactId>
  <version>2.x.x</version>
</dependency>
<!-- log4j2.xml -->
<?xml version="1.0" encoding="UTF-8"?>
<Configuration xmlns="http://logging.apache.org/log4j/2.0/config">

  <Appenders>
    <Console name="stdout" target="SYSTEM_OUT">
      <PatternLayout pattern="%5level [%t] - %msg%n"/>
    </Console>
  </Appenders>

  <Loggers>
    <Logger name="org.mybatis.example.BlogMapper" level="trace"/>
    <Root level="error" >
      <AppenderRef ref="stdout"/>
    </Root>
  </Loggers>

</Configuration>
```

### Log4j 配置示例

```xml
<!-- pom.xml -->
<dependency>
  <groupId>log4j</groupId>
  <artifactId>log4j</artifactId>
  <version>1.2.17</version>
</dependency>
# log4j.properties
log4j.rootLogger=ERROR, stdout

log4j.logger.org.mybatis.example.BlogMapper=TRACE

log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%5p [%t] - %m%n
```

### JDK logging 配置示例

```properties
# logging.properties
handlers=java.util.logging.ConsoleHandler
.level=SEVERE

org.mybatis.example.BlogMapper=FINER

java.util.logging.ConsoleHandler.level=ALL
java.util.logging.ConsoleHandler.formatter=java.util.logging.SimpleFormatter
java.util.logging.SimpleFormatter.format=%1$tT.%1$tL %4$s %3$s - %5$s%6$s%n
```









