package com.tutorial.mybatis.util;

import java.lang.reflect.Field;

/**
 * Author: Zhi Liu
 * Date: 2024/6/14 16:44
 * Contact: liuzhi0531@gmail.com
 * Desc:
 */
public class ReflectUtil {
    public static void setFieldValue(Object obj, String fieldName, Object fieldValue) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, fieldValue);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set field value via reflection", e);
        }
    }
}
