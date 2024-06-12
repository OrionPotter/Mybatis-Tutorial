package com.tutorial.mybatis.mapper;

import com.tutorial.mybatis.pojo.User;



public interface UserMapper {
    User selectUser(Integer id);
}
