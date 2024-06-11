package com.tutorial.mybatis.pojo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Blog {
    // 基本属性
    private Integer id;
    private String title;
    private String content;
    private Integer authorId;
    private Date createdAt;
    private Date updatedAt;

    // 内容相关属性
    private String summary;
    private List<String> tags;
    private List<String> categories;

    // 元数据属性
    private String status;
    private Integer views;
    private Integer likes;
    private Integer commentsCount;

    // SEO相关属性
    private String slug;
    private String metaDescription;
    private List<String> metaKeywords;

    // 其他属性
    private String featuredImage;
    private Boolean isFeatured;
    private Boolean allowComments;

}
