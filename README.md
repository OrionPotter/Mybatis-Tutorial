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

属性可以采用外部的配置文件，如jdbc.properties，将外部的配置文件引入后，可以在整个mybatis-config.xml配置文件中引用，也可以在构建`sqlSessionFactory`的时候传入自定义的配置的properties

### 加载外部配置文件

**外部配置文件jdbc.properties**

```properties
driver=com.mysql.cj.jdbc.Driver
url=jdbc:mysql://localhost:3306/mybatis_tutorial?serverTimezone=Asia/Shanghai
username=root
password=123456
```

**mybatis-config.xml引用属性**

```xml
<dataSource type="POOLED">
	<property name="driver" value="${driver}"/>
    <property name="url" value="${url}"/>
    <property name="username" value="${username}"/>
    <property name="password" value="${password}"/>
</dataSource>
```

### 构建SqlSessionFactor加载属性

```java
String resource = "mybatis-config.xml";
InputStream inputStream = Resources.getResourceAsStream(resource);
//自定义配置properties
Properties props = new Properties();
props.setProperty("driver", "com.mysql.cj.jdbc.Driver");
props.setProperty("url", "jdbc:mysql://localhost:3306/mybatis_tutorial?serverTimezone=Asia/Shanghai");
props.setProperty("username", "root");
props.setProperty("password", "123456");
SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, props);
```

### 属性优先级

构建SqlSessionFactor加载属性 > 加载外部配置文件 > 在properties元素体内指定的属性

## 设置

```xml
<settings>
  <!-- 启用或禁用二级缓存 -->
  <setting name="cacheEnabled" value="true"/>
  
  <!-- 启用或禁用延迟加载 -->
  <setting name="lazyLoadingEnabled" value="true"/>
  
  <!-- 启用或禁用激进的延迟加载 -->
  <setting name="aggressiveLazyLoading" value="true"/>
  
  <!-- 启用或禁用多结果集支持 -->
  <setting name="multipleResultSetsEnabled" value="true"/>
  
  <!-- 使用列标签代替列名 -->
  <setting name="useColumnLabel" value="true"/>
  
  <!-- 启用或禁用 JDBC 生成的键 -->
  <setting name="useGeneratedKeys" value="false"/>
  
  <!-- 自动映射行为，PARTIAL 表示部分映射 -->
  <setting name="autoMappingBehavior" value="PARTIAL"/>
  
  <!-- 未知列的自动映射行为，WARNING 表示警告 -->
  <setting name="autoMappingUnknownColumnBehavior" value="WARNING"/>
  
  <!-- 默认的执行器类型，SIMPLE 表示简单执行器 -->
  <setting name="defaultExecutorType" value="SIMPLE"/>
  
  <!-- 默认的语句超时时间（秒） -->
  <setting name="defaultStatementTimeout" value="25"/>
  
  <!-- 默认的获取大小 -->
  <setting name="defaultFetchSize" value="100"/>
  
  <!-- 启用或禁用安全的 RowBounds -->
  <setting name="safeRowBoundsEnabled" value="false"/>
  
  <!-- 启用或禁用安全的结果处理器 -->
  <setting name="safeResultHandlerEnabled" value="true"/>
  
  <!-- 启用或禁用下划线转驼峰命名法 -->
  <setting name="mapUnderscoreToCamelCase" value="false"/>
  
  <!-- 本地缓存范围，SESSION 表示会话级别缓存 -->
  <setting name="localCacheScope" value="SESSION"/>
  
  <!-- 为 NULL 值指定的 JDBC 类型 -->
  <setting name="jdbcTypeForNull" value="OTHER"/>
  
  <!-- 延迟加载触发的方法 -->
  <setting name="lazyLoadTriggerMethods" value="equals,clone,hashCode,toString"/>
  
  <!-- 默认的脚本语言驱动 -->
  <setting name="defaultScriptingLanguage" value="org.apache.ibatis.scripting.xmltags.XMLLanguageDriver"/>
  
  <!-- 默认的枚举类型处理器 -->
  <setting name="defaultEnumTypeHandler" value="org.apache.ibatis.type.EnumTypeHandler"/>
  
  <!-- 启用或禁用在设置 NULL 值时调用 setter 方法 -->
  <setting name="callSettersOnNulls" value="false"/>
  
  <!-- 启用或禁用返回空行的实例 -->
  <setting name="returnInstanceForEmptyRow" value="false"/>
  
  <!-- 日志前缀 -->
  <setting name="logPrefix" value="exampleLogPreFix_"/>
  
  <!-- 日志实现，SLF4J、LOG4J、LOG4J2、JDK_LOGGING、COMMONS_LOGGING、STDOUT_LOGGING、NO_LOGGING -->
  <setting name="logImpl" value="SLF4J"/>
  
  <!-- 代理工厂，CGLIB 或 JAVASSIST -->
  <setting name="proxyFactory" value="CGLIB"/>
  
  <!-- 自定义 VFS 实现 -->
  <setting name="vfsImpl" value="org.mybatis.example.YourselfVfsImpl"/>
  
  <!-- 启用或禁用使用实际的参数名称 -->
  <setting name="useActualParamName" value="true"/>
  
  <!-- 配置工厂类 -->
  <setting name="configurationFactory" value="org.mybatis.example.ConfigurationFactory"/>
</settings>
```

## 类型别名

类型别名可以给一个java类型设置一个缩写名字，他仅适用于xml配置，主要降低全限定类名的书写。

### 配置文件方式

```xml
<typeAliases>
  <typeAlias alias="Author" type="domain.blog.Author"/>
  <typeAlias alias="Blog" type="domain.blog.Blog"/>
  <typeAlias alias="Comment" type="domain.blog.Comment"/>
  <typeAlias alias="Post" type="domain.blog.Post"/>
  <typeAlias alias="Section" type="domain.blog.Section"/>
  <typeAlias alias="Tag" type="domain.blog.Tag"/>
</typeAliases>
```

### 注解方式

typeAlias指定一个包的时候，默认会采用bean的名字首字母小写

```xml
<typeAliases>
  <package name="domain.blog"/>
</typeAliases>
```

```java
@Alias("Author")//不加注解默认是author,加了注解以后就是Author
public class Author {
    
}
```

## 类型处理器

MyBatis在设置预处理语句（PreparedStatement）中的参数或从结果集中取出一个值时，都会用类型处理器将获取到的值以合适的方式转换成 Java 类型，LocalDateTime与数据库中DateTime相互对应，mybatis会自动处理，但是有一些，例如：pojo是list,mysql中是varchar()这就要需要设置自定义类型处理来处理了。

### 配置类型处理器

实现 `org.apache.ibatis.type.TypeHandler` 接口或继承一个很便利的类 `org.apache.ibatis.type.BaseTypeHandler`，将它映射到一个 JDBC 类型。

```java
public class StringListTypeHandler extends BaseTypeHandler<List<String>> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, String.join(",", parameter));
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String columnValue = rs.getString(columnName);
        return columnValue != null ? Arrays.asList(columnValue.split(",")) : null;
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String columnValue = rs.getString(columnIndex);
        return columnValue != null ? Arrays.asList(columnValue.split(",")) : null;
    }

    @Override
    public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String columnValue = cs.getString(columnIndex);
        return columnValue != null ? Arrays.asList(columnValue.split(",")) : null;
    }
}
```

### 注册到Mybatis

```xml
<!-- mybatis-config.xml -->
<typeHandlers>
     <typeHandler handler="com.tutorial.mybatis.handler.StringListTypeHandler" javaType="java.util.List"/>
</typeHandlers>
```

### 编写映射文件配置

```xml
<!-- BlogMapper.xml -->
<mapper namespace="com.tutorial.mybatis.mapper.BlogMapper">
    <resultMap id="BlogResultMap" type="com.tutorial.mybatis.pojo.Blog">
        ……
        <result property="tags" column="tags" typeHandler="com.tutorial.mybatis.handler.StringListTypeHandler"/>
        <result property="categories" column="categories" typeHandler="com.tutorial.mybatis.handler.StringListTypeHandler"/>
        ……
    </resultMap>
    <select id="selectBlog" resultMap="BlogResultMap">
        select * from Blog where id = #{id}
    </select>
</mapper>
```

## 对象工厂

每次 MyBatis 创建结果对象的新实例时，它都会使用一个对象工厂（ObjectFactory）实例来完成实例化工作。 默认的对象工厂需要做的仅仅是实例化目标类，要么通过默认无参构造方法，要么通过存在的参数映射来调用带有参数的构造方法。 如果想覆盖对象工厂的默认行为，可以通过创建自己的对象工厂来实现。比如：

```java
// ExampleObjectFactory.java
public class ExampleObjectFactory extends DefaultObjectFactory {
  @Override
  public <T> T create(Class<T> type) {
    return super.create(type);
  }

  @Override
  public <T> T create(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {
    return super.create(type, constructorArgTypes, constructorArgs);
  }

  @Override
  public void setProperties(Properties properties) {
    super.setProperties(properties);
  }

  @Override
  public <T> boolean isCollection(Class<T> type) {
    return Collection.class.isAssignableFrom(type);
  }}
```

```xml
<!-- mybatis-config.xml -->
<objectFactory type="org.mybatis.example.ExampleObjectFactory">
  <property name="someProperty" value="100"/>
</objectFactory>
```

ObjectFactory 接口很简单，它包含两个创建实例用的方法，一个是处理默认无参构造方法的，另外一个是处理带参数的构造方法的。 另外，setProperties 方法可以被用来配置 ObjectFactory，在初始化你的 ObjectFactory 实例后， objectFactory 元素体中定义的属性会被传递给 setProperties 方法。

## 插件

MyBatis 允许你在映射语句执行过程中的某一点进行拦截调用。默认情况下，MyBatis 允许使用插件来拦截的方法调用包括：

- Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)
- ParameterHandler (getParameterObject, setParameters)
- ResultSetHandler (handleResultSets, handleOutputParameters)
- StatementHandler (prepare, parameterize, batch, update, query)

这些类中方法的细节可以通过查看每个方法的签名来发现，或者直接查看 MyBatis 发行包中的源代码。 如果你想做的不仅仅是监控方法的调用，那么你最好相当了解要重写的方法的行为。 因为在试图修改或重写已有方法的行为时，很可能会破坏 MyBatis 的核心模块。 这些都是更底层的类和方法，所以使用插件的时候要特别当心。

通过 MyBatis 提供的强大机制，使用插件是非常简单的，只需实现 Interceptor 接口，并指定想要拦截的方法签名即可。

```
// ExamplePlugin.java
@Intercepts({@Signature(
  type= Executor.class,
  method = "update",
  args = {MappedStatement.class,Object.class})})
public class ExamplePlugin implements Interceptor {
  private Properties properties = new Properties();

  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    // implement pre processing if need
    Object returnObject = invocation.proceed();
    // implement post processing if need
    return returnObject;
  }

  @Override
  public void setProperties(Properties properties) {
    this.properties = properties;
  }
}
<!-- mybatis-config.xml -->
<plugins>
  <plugin interceptor="org.mybatis.example.ExamplePlugin">
    <property name="someProperty" value="100"/>
  </plugin>
</plugins>
```

上面的插件将会拦截在 Executor 实例中所有的 “update” 方法调用， 这里的 Executor 是负责执行底层映射语句的内部对象。

**覆盖配置类**

除了用插件来修改 MyBatis 核心行为以外，还可以通过完全覆盖配置类来达到目的。只需继承配置类后覆盖其中的某个方法，再把它传递到 SqlSessionFactoryBuilder.build(myConfig) 方法即可。再次重申，这可能会极大影响 MyBatis 的行为，务请慎之又慎。

## 环境配置

生产过程中可能会涉及多个环境，如开发环境、测试环境、生产环境

### 配置文件

```xml
	<!-- 定义多个环境 -->
    <environments default="development">
        <!-- 开发环境 -->
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/dev_db"/>
                <property name="username" value="dev_user"/>
                <property name="password" value="dev_password"/>
            </dataSource>
        </environment>
        <!-- 测试环境 -->
        <environment id="test">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/test_db"/>
                <property name="username" value="test_user"/>
                <property name="password" value="test_password"/>
            </dataSource>
        </environment>
        <!-- 生产环境 -->
        <environment id="production">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/prod_db"/>
                <property name="username" value="prod_user"/>
                <property name="password" value="prod_password"/>
            </dataSource>
        </environment>
    </environments>
```

### 按需使用环境

```java
public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream,"{environment id}");
        return sqlSessionFactory;
    }
```

## 事务管理器

 MyBatis 中有两种类型的事务管理器（也就是 type="[JDBC|MANAGED]"）

- JDBC – 这个配置直接使用了 JDBC 的提交和回滚功能，它依赖从数据源获得的连接来管理事务作用域。默认情况下，为了与某些驱动程序兼容，它在关闭连接时启用自动提交。然而，对于某些驱动程序来说，启用自动提交不仅是不必要的，而且是一个代价高昂的操作。因此，从 3.5.10 版本开始，你可以通过将 "skipSetAutoCommitOnClose" 属性设置为 "true" 来跳过这个步骤。例如：

  ```
  <transactionManager type="JDBC">
    <property name="skipSetAutoCommitOnClose" value="true"/>
  </transactionManager>
  ```

- MANAGED – 这个配置几乎没做什么。它从不提交或回滚一个连接，而是让容器来管理事务的整个生命周期（比如 JEE 应用服务器的上下文）。 默认情况下它会关闭连接。然而一些容器并不希望连接被关闭，因此需要将 closeConnection 属性设置为 false 来阻止默认的关闭行为。例如:

  ```
  <transactionManager type="MANAGED">
    <property name="closeConnection" value="false"/>
  </transactionManager>
  ```

**提示** Spring + MyBatis，则没有必要配置事务管理器，因为 Spring 模块会使用自带的管理器来覆盖前面的配置。

## 数据源

三种内建的数据源类型（也就是 type="[UNPOOLED|POOLED|JNDI]"）：

**UNPOOLED**– 这个数据源的实现会每次请求时打开和关闭连接。

**POOLED**– 这种数据源的实现利用“池”的概念将 JDBC 连接对象组织起来，避免了创建新的连接实例时所必需的初始化和认证时间。默认使用

**JNDI** – 这个数据源实现是为了能在如 EJB 或应用服务器这类容器中使用，容器可以集中或在外部配置数据源，然后放置一个 JNDI 上下文的数据源引用。

## 数据库厂商

MyBatis 可以根据不同的数据库厂商执行不同的语句，这种多厂商的支持是基于映射语句中的 `databaseId` 属性。 MyBatis 会加载带有匹配当前数据库 `databaseId` 属性和所有不带 `databaseId` 属性的语句。 如果同时找到带有 `databaseId` 和不带 `databaseId` 的相同语句，则后者会被舍弃。 为支持多厂商特性，只要像下面这样在 mybatis-config.xml 文件中加入 `databaseIdProvider` 即可：

```
<databaseIdProvider type="DB_VENDOR" />
```

databaseIdProvider 对应的 DB_VENDOR 实现会将 databaseId 设置为 `DatabaseMetaData#getDatabaseProductName()` 返回的字符串。 由于通常情况下这些字符串都非常长，而且相同产品的不同版本会返回不同的值，你可能想通过设置属性别名来使其变短：

```
<databaseIdProvider type="DB_VENDOR">
  <property name="SQL Server" value="sqlserver"/>
  <property name="DB2" value="db2"/>
  <property name="Oracle" value="oracle" />
</databaseIdProvider>
```

在提供了属性别名时，databaseIdProvider 的 DB_VENDOR 实现会将 databaseId 设置为数据库产品名与属性中的名称第一个相匹配的值，如果没有匹配的属性，将会设置为 “null”。 在这个例子中，如果 `getDatabaseProductName()` 返回“Oracle (DataDirect)”，databaseId 将被设置为“oracle”。

你可以通过实现接口 `org.apache.ibatis.mapping.DatabaseIdProvider` 并在 mybatis-config.xml 中注册来构建自己的 DatabaseIdProvider：

```
public interface DatabaseIdProvider {
  default void setProperties(Properties p) { // 从 3.5.2 开始，该方法为默认方法
    // 空实现
  }
  String getDatabaseId(DataSource dataSource) throws SQLException;
}
```

## 映射器

四种映射器方式，可以找到对应配置的映射文件

```xml
<!-- 使用相对于类路径的资源引用 -->
<mappers>
  <mapper resource="org/mybatis/builder/AuthorMapper.xml"/>
  <mapper resource="org/mybatis/builder/BlogMapper.xml"/>
  <mapper resource="org/mybatis/builder/PostMapper.xml"/>
</mappers>
```

```xml
<!-- 使用完全限定资源定位符（URL） -->
<mappers>
  <mapper url="file:///var/mappers/AuthorMapper.xml"/>
  <mapper url="file:///var/mappers/BlogMapper.xml"/>
  <mapper url="file:///var/mappers/PostMapper.xml"/>
</mappers>
```

```xml
<!-- 使用映射器接口实现类的完全限定类名 -->
<mappers>
  <mapper class="org.mybatis.builder.AuthorMapper"/>
  <mapper class="org.mybatis.builder.BlogMapper"/>
  <mapper class="org.mybatis.builder.PostMapper"/>
</mappers>
```

```xml
<!-- 将包内的映射器接口全部注册为映射器 -->
<mappers>
  <package name="org.mybatis.builder"/>
</mappers>
```

# XML 映射器

SQL 映射文件只有很少的几个顶级元素（按照应被定义的顺序列出）：

- `cache` – 该命名空间的缓存配置。
- `cache-ref` – 引用其它命名空间的缓存配置。
- `resultMap` – 描述如何从数据库结果集中加载对象，是最复杂也是最强大的元素。
- `sql` – 可被其它语句引用的可重用语句块。
- `insert` – 映射插入语句。
- `update` – 映射更新语句。
- `delete` – 映射删除语句。
- `select` – 映射查询语句。

## select

```xml
<select id="selectBlog" resultMap="BlogResultMap">
        select * from Blog where id = #{id}
</select>
<!-- 常见参数含义 -->
<select
  id="selectBlog"              <!-- SQL 语句的唯一标识符，用于在 MyBatis 中引用这个语句 -->
  parameterType="int"            <!-- 输入参数的类型，这里是 int 类型 -->
  resultType="hashmap"           <!-- 结果集的类型，这里是 HashMap -->
  resultMap="BlogResultMap"    <!-- 结果映射的 ID，用于复杂的结果集映射 -->
  flushCache="false"             <!-- 是否在执行该语句时刷新缓存，默认值为 false -->
  useCache="true"                <!-- 是否启用二级缓存，默认值为 true -->
  timeout="10"                   <!-- SQL 语句的超时时间，单位为秒 -->
  fetchSize="256"                <!-- 提示驱动程序每次批量返回的行数 -->
  statementType="PREPARED"       <!-- SQL 语句的类型，可以是 STATEMENT、PREPARED 或 CALLABLE -->
  resultSetType="FORWARD_ONLY">  <!-- 结果集的类型，可以是 FORWARD_ONLY、SCROLL_SENSITIVE 或 SCROLL_INSENSITIVE -->
```

## insert, update 和 delete

```xml
<insert
  id="insertAuthor"              <!-- SQL 语句的唯一标识符，用于在 MyBatis 中引用这个语句 -->
  parameterType="domain.blog.Author"  <!-- 输入参数的类型，这里是 domain.blog.Author 类 -->
  flushCache="true"              <!-- 是否在执行该语句时刷新缓存，默认值为 true -->
  statementType="PREPARED"       <!-- SQL 语句的类型，可以是 STATEMENT、PREPARED 或 CALLABLE，默认值为 PREPARED -->
  keyProperty=""                 <!-- （可选）指定生成的主键将被设置到的属性名 -->
  keyColumn=""                   <!-- （可选）指定数据库中生成的主键列名 -->
  useGeneratedKeys=""            <!-- （可选）是否使用 JDBC 的 getGeneratedKeys 方法获取主键，默认值为 false -->
  timeout="20">                  <!-- SQL 语句的超时时间，单位为秒，默认值取决于数据库驱动程序 -->
    <!-- SQL 插入语句 -->
</insert>

<update
  id="updateAuthor"              <!-- SQL 语句的唯一标识符，用于在 MyBatis 中引用这个语句 -->
  parameterType="domain.blog.Author"  <!-- 输入参数的类型，这里是 domain.blog.Author 类 -->
  flushCache="true"              <!-- 是否在执行该语句时刷新缓存，默认值为 true -->
  statementType="PREPARED"       <!-- SQL 语句的类型，可以是 STATEMENT、PREPARED 或 CALLABLE，默认值为 PREPARED -->
  timeout="20">                  <!-- SQL 语句的超时时间，单位为秒，默认值取决于数据库驱动程序 -->
    <!-- SQL 更新语句 -->
</update>

<delete
  id="deleteAuthor"              <!-- SQL 语句的唯一标识符，用于在 MyBatis 中引用这个语句 -->
  parameterType="domain.blog.Author"  <!-- 输入参数的类型，这里是 domain.blog.Author 类 -->
  flushCache="true"              <!-- 是否在执行该语句时刷新缓存，默认值为 true -->
  statementType="PREPARED"       <!-- SQL 语句的类型，可以是 STATEMENT、PREPARED 或 CALLABLE，默认值为 PREPARED -->
  timeout="20">                  <!-- SQL 语句的超时时间，单位为秒，默认值取决于数据库驱动程序 -->
    <!-- SQL 删除语句 -->
</delete>
```

## SQL

定义可重用的 SQL 代码片段，以便在其它语句中使用

```xml
<sql id="userColumns"> ${alias}.id,${alias}.username,${alias}.password </sql>

<select id="selectUsers" resultType="map">
  select
    <include refid="userColumns"><property name="alias" value="t1"/></include>,
    <include refid="userColumns"><property name="alias" value="t2"/></include>
  from some_table t1
    cross join some_table t2
</select>
```

## 参数

对于大多数简单的使用场景，都不需要使用复杂的参数

```xml
<select id="selectUsers" resultType="User">
  select id, username, password
  from users
  where id = #{id}
</select>
```

 JDBC 要求，如果一个列允许使用 null 值，并且会使用值为 null 的参数，就必须要指定 JDBC 类型（jdbcType)。

## 字符串替换

默认情况下，使用 `#{}` 参数语法时，MyBatis 会创建 `PreparedStatement` 参数占位符，并通过占位符安全地设置参数（就像使用 ? 一样）。

有时你就是想直接在 SQL 语句中直接插入一个不转义的字符串。 比如 ORDER BY 子句，这时候你可以

```sql
-- MyBatis 就不会修改或转义该字符串了,用这种方式接受用户的输入，并用作语句参数是不安全的，会导致潜在的 SQL 注入攻击
ORDER BY ${columnName}
```

## 结果映射

### resultType

```xml
<select id="selectUsers" resultType="com.someapp.model.User">
  select id, username, hashedPassword
  from some_table
  where id = #{id}
</select>
```

```java
package com.someapp.model;
public class User {
  private int id;
  private String username;
  private String hashedPassword;
}    
```

### resultMap

解决数据库列名和实体类属性名不一致。

```xml
<resultMap id="userResultMap" type="User">
  <id property="id" column="user_id" />
  <result property="username" column="user_name"/>
  <result property="password" column="hashed_password"/>
</resultMap>

<select id="selectUsers" resultMap="userResultMap">
  select user_id, user_name, hashed_password
  from some_table
  where id = #{id}
</select>
```



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









