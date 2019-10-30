package com.lunz.permission.annotation;

import java.lang.annotation.*;

/**
 * author:lizhaojie
 * 创建日期:2019/10/25-19:25
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableInceptor {

    public boolean value() default true;
}