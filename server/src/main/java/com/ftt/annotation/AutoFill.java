package com.ftt.annotation;

import com.ftt.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill
{
    //数据库操作类型
    OperationType value();//相当于给注解一个参数，类型是OprationType类

}
