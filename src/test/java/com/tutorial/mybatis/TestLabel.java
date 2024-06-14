package com.tutorial.mybatis;

import com.tutorial.mybatis.factory.BuildSqlSessionFactory;
import com.tutorial.mybatis.mapper.FoodMapper;
import com.tutorial.mybatis.mapper.UserMapper;
import com.tutorial.mybatis.pojo.Food;
import com.tutorial.mybatis.pojo.User;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Author: Zhi Liu
 * Date: 2024/6/13 17:32
 * Contact: liuzhi0531@gmail.com
 * Desc:
 */
public class TestLabel {
    @Test
    public void testIfLabel() throws IOException {
        BuildSqlSessionFactory sqlSessionFactory = new BuildSqlSessionFactory();
        try (SqlSession sqlSession = sqlSessionFactory.getSqlSessionFactoryByXml().openSession()){
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            User user1 = mapper.selectUserByIdAndName(1,"John Doe");
            System.out.println(user1.toString());
            User user2 = mapper.selectUserByIdAndName(2,null);
            System.out.println(user2.toString());
        }
    }

    @Test
    public void testChoiceLabel() throws IOException {
        BuildSqlSessionFactory sqlSessionFactory = new BuildSqlSessionFactory();
        try (SqlSession sqlSession = sqlSessionFactory.getSqlSessionFactoryByXml().openSession()){
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            User user1 = mapper.selectUserByIdOrName(1,"John Doe");
            System.out.println(user1.toString());
            User user2 = mapper.selectUserByIdOrName(null,null);
            System.out.println(user2 == null ? true : false);
        }
    }

    @Test
    public void testTrimWhereSetLabel() throws IOException {
        BuildSqlSessionFactory sqlSessionFactory = new BuildSqlSessionFactory();
        try (SqlSession sqlSession = sqlSessionFactory.getSqlSessionFactoryByXml().openSession()){
            FoodMapper mapper = sqlSession.getMapper(FoodMapper.class);
            List<Food> apple = mapper.selectFood("", "Fruit", null, null);
            apple.forEach(System.out::println);


            mapper.updateFood(1, "Green Apple", "Fruit", 1.99, true);
            sqlSession.commit();

            List<Food> apple2 = mapper.selectFood("", "Fruit", null, null);
            apple2.forEach(System.out::println);

        }
    }

    @Test
    public void testForeachLabel() throws IOException {
        BuildSqlSessionFactory sqlSessionFactory = new BuildSqlSessionFactory();
        try (SqlSession sqlSession = sqlSessionFactory.getSqlSessionFactoryByXml().openSession()){
            FoodMapper mapper = sqlSession.getMapper(FoodMapper.class);
            List<Food> apple = mapper.selectFoodListById(Arrays.asList(1,2,3,4,5,6));
            apple.forEach(System.out::println);
        }
    }
}
