package com.tutorial.mybatis.plugin;


import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.Properties;

/**
 * Author: Zhi Liu
 * Date: 2024/6/14 16:24
 * Contact: liuzhi0531@gmail.com
 * Desc:
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
public class SqlExecutionTimeInterceptor implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(SqlExecutionTimeInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        long startTime = System.currentTimeMillis();

        // 执行被拦截的方法
        Object result = invocation.proceed();

        long endTime = System.currentTimeMillis();
        logger.info("SQL execution took " + (endTime - startTime) + " ms");


        return result;
    }

    @Override
    public Object plugin(Object target) {
        // 创建目标对象的代理
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // 读取插件配置的属性
    }
}
