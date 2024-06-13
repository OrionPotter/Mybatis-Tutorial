package com.tutorial.mybatis.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author: Zhi Liu
 * Date: 2024/6/13 15:19
 * Contact: liuzhi0531@gmail.com
 * Desc:
 */

@Data
@NoArgsConstructor
public abstract class Animal {
    private Integer id;
    private String name;
    public Animal(Integer id,String name){
        this.id = id;
        this.name = name;
    }
}
