package com.neusoft.core.service.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类LazyAutowired.java的实现描述：推遲依賴注入到方法調用
 * 
 * @author Administrator 2017年6月14日 下午4:27:35
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface LazyAutowired {

    String value();
}
