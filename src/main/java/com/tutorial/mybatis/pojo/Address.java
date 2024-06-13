package com.tutorial.mybatis.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Address implements Serializable {
    private Integer id;
    private String street;
    private String city;
}
