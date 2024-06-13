package com.tutorial.mybatis.pojo;

import lombok.*;

/**
 * Author: Zhi Liu
 * Date: 2024/6/13 15:22
 * Contact: liuzhi0531@gmail.com
 * Desc:
 */
@Data
public class Cat extends Animal{
    String breed;
    String color;
    public Cat(Integer id, String name,String breed,String color) {
        super(id, name);
        this.breed = breed;
        this.color = color;
    }

}
