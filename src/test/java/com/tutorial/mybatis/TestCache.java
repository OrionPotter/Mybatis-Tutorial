package com.tutorial.mybatis;

import com.tutorial.mybatis.factory.BuildSqlSessionFactory;
import com.tutorial.mybatis.mapper.UserMapper;
import com.tutorial.mybatis.pojo.User;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.io.IOException;

/**
 * Author: Zhi Liu
 * Date: 2024/6/13 16:21
 * Contact: liuzhi0531@gmail.com
 * Desc:
 */
public class TestCache {
    @Test
    public void testCacheLevel1() throws IOException {
        BuildSqlSessionFactory sqlSessionFactory = new BuildSqlSessionFactory();
        try (SqlSession sqlSession = sqlSessionFactory.getSqlSessionFactoryByXml().openSession()){
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            // 第一次查询，结果会被缓存
            User user1 = mapper.selectUser(1);
            System.out.println(user1.toString());

            // 第二次查询，MyBatis 会从缓存中获取结果，而不是再次查询数据库
            User user2 = mapper.selectUser(1);
            System.out.println(user2.toString());

        }
    }

    @Test
    public void testCacheLevel2() throws IOException {
        BuildSqlSessionFactory sqlSessionFactory = new BuildSqlSessionFactory();
        try (SqlSession sqlSession = sqlSessionFactory.getSqlSessionFactoryByXml().openSession()){
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            // 第一次查询，结果会被缓存
            User user1 = mapper.selectUser(1);
            System.out.println(user1.toString());
        }


        try (SqlSession sqlSession = sqlSessionFactory.getSqlSessionFactoryByXml().openSession()){
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            // 第二次查询，从缓存中查找数据
            User user1 = mapper.selectUser(1);
            System.out.println(user1.toString());
        }
    }
}
