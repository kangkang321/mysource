package com.neusoft.hp.runtime.dyn.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.neusoft.hp.runtime.dyn.annotation.DynWhere.DynWheres;

/**
 * 类DynWhere.java的实现描述：动态添加查询条件时用到
 * 
 * @author Administrator 2017年4月21日 下午2:37:17
 */
@Repeatable(DynWheres.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DynWhere {

    /**
     * 表名、列名
     * 
     * @return
     */
    ColumnInfo columnInfo();

    /**
     * 引用值的路径
     * 
     * @return
     */
    String path() default "";

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public static @interface DynWheres {

        DynWhere[] value();
    }
}
