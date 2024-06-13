package com.tutorial.mybatis;


import com.tutorial.mybatis.factory.BuildSqlSessionFactoryByXml;
import com.tutorial.mybatis.mapper.AnimalMapper;
import com.tutorial.mybatis.mapper.BookMapper;
import com.tutorial.mybatis.mapper.OrderMapper;
import com.tutorial.mybatis.mapper.UserMapper;
import com.tutorial.mybatis.pojo.*;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import java.io.IOException;
import java.util.List;


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

    @Test
    public void testConstructor() throws IOException {
        BuildSqlSessionFactoryByXml sqlSessionFactory = new BuildSqlSessionFactoryByXml();
        try (SqlSession sqlSession = sqlSessionFactory.getSqlSessionFactory().openSession()){
            BookMapper mapper = sqlSession.getMapper(BookMapper.class);
            Books books = mapper.selectById(1);
            System.out.println(books.toString());
        }
    }

    @Test
    public void testDis() throws IOException {
        BuildSqlSessionFactoryByXml sqlSessionFactory = new BuildSqlSessionFactoryByXml();
        try (SqlSession sqlSession = sqlSessionFactory.getSqlSessionFactory().openSession()){
            AnimalMapper mapper = sqlSession.getMapper(AnimalMapper.class);
            List<Animal> animals = mapper.selectAll();
            for (Animal animal : animals) {
                if (animal instanceof Cat) {
                    Cat cat = (Cat) animal;
                    System.out.println("Cat: " + cat.getName() + ", Breed: " + cat.getBreed() + ", Color: " + cat.getColor());
                } else if (animal instanceof Dog) {
                    Dog dog = (Dog) animal;
                    System.out.println("Dog: " + dog.getName() + ", Breed: " + dog.getBreed() + ", Color: " + dog.getColor());
                }
            }
        }
    }
}
