package com.neusoft.hp.runtime.dyn.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类RefValue.java的实现描述：显示时用到，引用
 * 
 * @author Administrator 2017年4月21日 下午2:39:46
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RefValue {

    /**
     * 根据path生成的sql
     * 
     * @return
     */
    String sql() default "";

    /**
     * 后期可能不是直接用sql查
     * 
     * @return
     */
    String path() default "";

    /**
     * json的key
     * 
     * @return
     */
    String displayName();

    /**
     * 表名、列名
     * 
     * @return
     */
    ColumnInfo columnInfo();
}
