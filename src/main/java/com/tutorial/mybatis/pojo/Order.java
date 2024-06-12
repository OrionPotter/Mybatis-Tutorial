package com.tutorial.mybatis.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Order {
    private int id;
    private int userId;
    private List<OrderItem> orderItems;
}
