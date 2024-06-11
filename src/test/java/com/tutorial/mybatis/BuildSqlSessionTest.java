package com.tutorial.mybatis;

import com.tutorial.mybatis.factory.BuildSqlSessionFactoryByConfig;
import com.tutorial.mybatis.factory.BuildSqlSessionFactoryByXml;
import com.tutorial.mybatis.mapper.BlogMapper;
import com.tutorial.mybatis.pojo.Blog;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import java.io.IOException;
import java.time.format.DateTimeFormatter;


public class BuildSqlSessionTest {

    @Test
    public void testBuildSqlSessionFactoryByXml() throws IOException {
        BuildSqlSessionFactoryByXml sqlSessionFactory = new BuildSqlSessionFactoryByXml();
        try (SqlSession sqlSession = sqlSessionFactory.getSqlSessionFactory().openSession()){
            BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
            Blog blog = mapper.selectBlog(1);
            System.out.println(blog.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
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
