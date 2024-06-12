package com.tutorial.mybatis.mapper;

import com.tutorial.mybatis.pojo.Order;

public interface OrderMapper {
    Order selectOrder(Integer id);
}
