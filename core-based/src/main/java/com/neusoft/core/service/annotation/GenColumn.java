package com.neusoft.core.service.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类GenColumn.java的实现描述：myBatis工具生成实体后会给实体类里特殊属性字段加这个注解
 * 
 * @author Administrator 2017年4月18日 下午2:46:06
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenColumn {

    /**
     * 属性gid
     * 
     * @return
     */
    String gid();

    /**
     * 数据库列名，一般是驼峰规则
     * 
     * @return
     */
    String columnName();

    /**
     * type=2时关联的实体
     * 
     * @return
     */
    String refObjectId() default "";

    /**
     * type=1时关联的枚举
     * 
     * @return
     */
    String enumId() default "";

    /**
     * 类型
     * 
     * @return
     */
    String type();

}
