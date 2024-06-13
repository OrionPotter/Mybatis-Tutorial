package com.tutorial.mybatis.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Author: Zhi Liu
 * Date: 2024/6/13 15:21
 * Contact: liuzhi0531@gmail.com
 * Desc:
 */
@Data
public class Dog extends Animal{
    String breed;
    String color;

    public Dog(Integer id, String name,String breed,String color) {
        super(id, name);
        this.breed = breed;
        this.color = color;
    }
}
