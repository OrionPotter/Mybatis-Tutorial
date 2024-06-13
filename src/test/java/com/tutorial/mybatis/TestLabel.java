package com.tutorial.mybatis;

import com.tutorial.mybatis.factory.BuildSqlSessionFactoryByXml;
import com.tutorial.mybatis.mapper.UserMapper;
import com.tutorial.mybatis.pojo.User;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.io.IOException;

/**
 * Author: Zhi Liu
 * Date: 2024/6/13 17:32
 * Contact: liuzhi0531@gmail.com
 * Desc:
 */
public class TestLabel {
    @Test
    public void testIfLabel() throws IOException {
        BuildSqlSessionFactoryByXml sqlSessionFactory = new BuildSqlSessionFactoryByXml();
        try (SqlSession sqlSession = sqlSessionFactory.getSqlSessionFactory().openSession()){
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            User user1 = mapper.selectUserByIdAndName(1,"John Doe");
            System.out.println(user1.toString());
            User user2 = mapper.selectUserByIdAndName(2,null);
            System.out.println(user2.toString());
        }
    }

    @Test
    public void testChoiceLabel() throws IOException {
        BuildSqlSessionFactoryByXml sqlSessionFactory = new BuildSqlSessionFactoryByXml();
        try (SqlSession sqlSession = sqlSessionFactory.getSqlSessionFactory().openSession()){
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            User user1 = mapper.selectUserByIdOrName(1,"John Doe");
            System.out.println(user1.toString());
            User user2 = mapper.selectUserByIdAndName(null,null);
            System.out.println(user2.toString());
        }
    }
}
