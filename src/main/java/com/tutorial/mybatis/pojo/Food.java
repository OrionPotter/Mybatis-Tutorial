package com.tutorial.mybatis.pojo;

import lombok.Data;

/**
 * Author: Zhi Liu
 * Date: 2024/6/14 9:38
 * Contact: liuzhi0531@gmail.com
 * Desc:
 */
@Data
public class Food {
    private Integer id;
    private String name;
    private String category;
    private Double price;
    private Boolean available;
}
