package com.tutorial.mybatis.mapper;

import com.tutorial.mybatis.pojo.Animal;

import java.util.List;

/**
 * Author: Zhi Liu
 * Date: 2024/6/13 15:26
 * Contact: liuzhi0531@gmail.com
 * Desc:
 */
public interface AnimalMapper {
    List<Animal> selectAll();
}
