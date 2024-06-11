package com.tutorial.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Author: Zhi Liu
 * Date: 2024/6/11 13:48
 * Contact: liuzhi0531@gmail.com
 * Desc:
 */
public class TestGetProperties {

    @Test
    public void test() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        Properties props = new Properties();
        props.setProperty("driver", "com.mysql.cj.jdbc.Driver");
        props.setProperty("url", "jdbc:mysql://localhost:3306/mybatis_tutorial?serverTimezone=Asia/Shanghai");
        props.setProperty("username", "root");
        props.setProperty("password", "123456");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, props);
    }

}
