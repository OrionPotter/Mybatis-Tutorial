package com.tutorial.mybatis.pojo;

import lombok.Data;

@Data
public class OrderItem {
    private int id;
    private int orderId;
    private String productName;
    private int quantity;
}
