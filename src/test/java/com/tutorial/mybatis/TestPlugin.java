package com.tutorial.mybatis;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tutorial.mybatis.factory.BuildSqlSessionFactory;
import com.tutorial.mybatis.mapper.FoodMapper;
import com.tutorial.mybatis.mapper.UserMapper;
import com.tutorial.mybatis.pojo.Food;
import com.tutorial.mybatis.pojo.User;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * Author: Zhi Liu
 * Date: 2024/6/14 16:45
 * Contact: liuzhi0531@gmail.com
 * Desc:
 */
public class TestPlugin {
    @Test
    public void testPlugin() throws IOException {
        BuildSqlSessionFactory sqlSessionFactory = new BuildSqlSessionFactory();
        try (SqlSession sqlSession = sqlSessionFactory.getSqlSessionFactoryByXml().openSession()){
            FoodMapper mapper = sqlSession.getMapper(FoodMapper.class);
            // 查询第一页，每页 2 条记录
            PageHelper.startPage(2, 2);
            Page<Food> foods = mapper.selectAllFood();
            PageInfo<Food> pageInfo = new PageInfo<>(foods);

            // 输出食物信息
            List<Food> foodList = pageInfo.getList();
            foodList.forEach(System.out::println);

            // 输出分页信息
            System.out.println("当前页: " + pageInfo.getPageNum());
            System.out.println("每页的记录数: " + pageInfo.getPageSize());
            System.out.println("总记录数: " + pageInfo.getTotal());
            System.out.println("总页数: " + pageInfo.getPages());

        }
    }
}
