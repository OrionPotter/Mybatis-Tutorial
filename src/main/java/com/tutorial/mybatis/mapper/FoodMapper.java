package com.tutorial.mybatis.mapper;

import com.github.pagehelper.Page;
import com.tutorial.mybatis.pojo.Food;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Author: Zhi Liu
 * Date: 2024/6/14 9:39
 * Contact: liuzhi0531@gmail.com
 * Desc:
 */
public interface FoodMapper {
    List<Food> selectFood(@Param("name") String name, @Param("category") String category, @Param("price") Double price, @Param("available") Boolean available);

    void updateFood(@Param("id") int id, @Param("name") String name, @Param("category") String category, @Param("price") Double price, @Param("available") Boolean available);

    List<Food> selectFoodListById(@Param("listId")List<Integer> listId);

    Page<Food> selectAllFood();

}
