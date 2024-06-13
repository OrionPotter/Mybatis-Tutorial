package com.tutorial.mybatis.mapper;

import com.tutorial.mybatis.pojo.User;
import org.apache.ibatis.annotations.Param;


public interface UserMapper {
    User selectUser(Integer id);
    User selectUserByIdAndName(@Param("id")Integer id, @Param("name")String name);

    User selectUserByIdOrName(@Param("id")Integer id, @Param("name")String name);
}
