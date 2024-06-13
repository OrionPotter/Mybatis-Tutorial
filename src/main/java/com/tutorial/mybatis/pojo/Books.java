package com.tutorial.mybatis.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Author: Zhi Liu
 * Date: 2024/6/13 10:41
 * Contact: liuzhi0531@gmail.com
 * Desc:
 */
@Data
@AllArgsConstructor
public class Books {
    private Integer id;
    private String title;
    private String author;
    private Double price;
}
