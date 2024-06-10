package com.tutorial.mybatis;

import com.tutorial.mybatis.factory.BuildSqlSessionFactoryByConfig;
import com.tutorial.mybatis.factory.BuildSqlSessionFactoryByXml;
import com.tutorial.mybatis.mapper.BlogMapper;
import com.tutorial.mybatis.pojo.Blog;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.Reader;

public class BuildTest {

    @Test
    public void testBuildSqlSessionFactoryByXml() throws IOException {
        BuildSqlSessionFactoryByXml sqlSessionFactory = new BuildSqlSessionFactoryByXml();
        try (SqlSession sqlSession = sqlSessionFactory.getSqlSessionFactory().openSession()){
            BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
            Blog blog = mapper.selectBlog("1");
            System.out.println(blog.getName());
        }
    }

    @Test
    public void testBuildSqlSessionFactoryByConfig() throws IOException {
        BuildSqlSessionFactoryByConfig sqlSessionFactory = new BuildSqlSessionFactoryByConfig();
        try (SqlSession sqlSession = sqlSessionFactory.getSqlSessionFactory().openSession()){
            BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
            Blog blog = mapper.selectBlog("1");
            System.out.println(blog.getName());
        }

    }

}
