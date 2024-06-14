package com.tutorial.mybatis.factory;

import com.tutorial.mybatis.mapper.BlogMapper;
import com.tutorial.mybatis.mapper.UserMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import java.io.IOException;
import java.io.InputStream;

/**
 * Author: Zhi Liu
 * Date: 2024/6/10 11:39
 * Contact: liuzhi0531@gmail.com
 * Desc:
 */
public class BuildSqlSessionFactory {

    /**
     * 基于mybatis配置文件mybatis-config.xml构建SqlSessionFactory
     * @return
     * @throws IOException
     */
    public SqlSessionFactory getSqlSessionFactoryByXml() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory;
    }

    /**
     * 基于自定义的mybatis全局配置类构建SqlSessionFactory
     * 通过此种方式实现自定义连接池，本例为HikariCP连接池
     * @return
     * @throws IOException
     */
    public SqlSessionFactory getSqlSessionFactoryByConfig() throws IOException {
        // 配置 HikariCP
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/mybatis_tutorial?serverTimezone=Asia/Shanghai");
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("123456");
        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setMinimumIdle(5);
        hikariConfig.setConnectionTimeout(30000);
        hikariConfig.setIdleTimeout(600000);
        hikariConfig.setMaxLifetime(1800000);

        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        //configuration.addMappers("com.tutorial.mybatis.mapper");
        configuration.addMapper(BlogMapper.class);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        return sqlSessionFactory;
    }



}
