package com.tutorial.mybatis.mapper;

import com.tutorial.mybatis.pojo.Blog;
import org.apache.ibatis.annotations.Select;

public interface BlogMapper {
    //@Select("SELECT * FROM Blog WHERE id = #{id}")
    Blog selectBlog(String id);
}
