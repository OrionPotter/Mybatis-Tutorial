package com.tutorial.mybatis.mapper;

import com.tutorial.mybatis.pojo.Books;

/**
 * Author: Zhi Liu
 * Date: 2024/6/13 10:45
 * Contact: liuzhi0531@gmail.com
 * Desc:
 */
public interface BookMapper {
    Books selectById(Integer id);
}
