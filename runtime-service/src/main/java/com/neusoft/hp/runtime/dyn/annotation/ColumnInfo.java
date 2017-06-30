package com.neusoft.hp.runtime.dyn.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类ColumnInfo.java的实现描述：数据库中表名、列名
 * 
 * @author Administrator 2017年4月21日 下午2:36:47
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ColumnInfo {

    /**
     * 表名
     * 
     * @return
     */
    String tableName();

    /**
     * 列名
     * 
     * @return
     */
    String columnName();

}
