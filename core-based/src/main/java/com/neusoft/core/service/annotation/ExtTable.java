package com.neusoft.core.service.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类ExtTable.java的实现描述：动态扩展表增加
 * 
 * @author Administrator 2017年5月8日 上午10:19:16
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExtTable {

}
