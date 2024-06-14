package com.tutorial.mybatis;

import com.tutorial.mybatis.factory.BuildSqlSessionFactory;
import com.tutorial.mybatis.mapper.BlogMapper;
import com.tutorial.mybatis.pojo.Blog;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

/**
 * Author: Zhi Liu
 * Date: 2024/6/10 11:49
 * Contact: liuzhi0531@gmail.com
 * Desc:  测试构建SqlSessionFactory 和 SqlSession
 */

public class TestBuildSqlSessionFactoryAndSqlSession {

    @Test
    public void testBuildSqlSessionFactoryByXml() throws IOException {
        BuildSqlSessionFactory sqlSessionFactory = new BuildSqlSessionFactory();
        try (SqlSession sqlSession = sqlSessionFactory.getSqlSessionFactoryByXml().openSession()){
            BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
            Blog blog = mapper.selectBlog(1);
            System.out.println(blog.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            System.out.println(blog.toString());
        }
    }

    @Test
    public void testBuildSqlSessionFactoryByConfig() throws IOException {
        BuildSqlSessionFactory sqlSessionFactory = new BuildSqlSessionFactory();
        try (SqlSession sqlSession = sqlSessionFactory.getSqlSessionFactoryByConfig().openSession()){
            BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
            Blog blog = mapper.SelectById(1);
            System.out.println(blog.toString());
        }

    }
}
