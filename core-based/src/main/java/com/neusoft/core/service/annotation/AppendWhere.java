package com.neusoft.core.service.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类AppendWhere.java的实现描述：多表联合查询时增加where语句
 * 
 * @author Administrator 2017年5月8日 上午10:16:30
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AppendWhere {

    String tableName();
}
