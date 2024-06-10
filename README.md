---
title: Mybatis实践
tag:
- mybatis
---

# 什么是mybatis

MyBatis是持久层框架，它支持自定义SQL、存储过程以及高级映射。MyBatis减少了JDBC设置参数和获取结果集的代码工作。MyBatis通过XML或注解将java的原始类型、接口、POJO映射到数据库中记录。

# 入门

## 安装Mybatis

项目采用Maven构建，只需要导入mybatis依赖：

```xml
	<dependencies>
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.16</version>
        </dependency>
        <!--    测试使用     -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.17</version>
        </dependency>
        <!--   数据源     -->
        <dependency>
            <groupId>com.zaxxer</groupId>
            <artifactId>HikariCP</artifactId>
            <version>5.0.1</version>
        </dependency>
    </dependencies>
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



