package com.tutorial.mybatis.mapper;

import com.tutorial.mybatis.pojo.Blog;
import org.apache.ibatis.annotations.Select;

public interface BlogMapper {
    //纯注解会冲突
    //@Select("SELECT * FROM Blog WHERE id = #{id}")
    Blog selectBlog(Integer id);
}
