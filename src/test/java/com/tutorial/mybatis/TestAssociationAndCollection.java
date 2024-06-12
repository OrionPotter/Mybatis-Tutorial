package com.tutorial.mybatis;


import com.tutorial.mybatis.factory.BuildSqlSessionFactoryByXml;
import com.tutorial.mybatis.mapper.OrderMapper;
import com.tutorial.mybatis.mapper.UserMapper;
import com.tutorial.mybatis.pojo.User;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import java.io.IOException;
import com.tutorial.mybatis.pojo.Order;


public class TestAssociationAndCollection {
    @Test
    public void testAssociation() throws IOException {
        BuildSqlSessionFactoryByXml sqlSessionFactory = new BuildSqlSessionFactoryByXml();
        try (SqlSession sqlSession = sqlSessionFactory.getSqlSessionFactory().openSession()){
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            User user = mapper.selectUser(1);
            System.out.println(user.toString());
        }
    }

    @Test
    public void testCollection() throws IOException {
        BuildSqlSessionFactoryByXml sqlSessionFactory = new BuildSqlSessionFactoryByXml();
        try (SqlSession sqlSession = sqlSessionFactory.getSqlSessionFactory().openSession()){
            OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);
            Order order = mapper.selectOrder(1);
            System.out.println(order.toString());
        }
    }
}
