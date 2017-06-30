package com.neusoft.core.service.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类Processor.java的实现描述：与业务实体处理器挂钩
 * 
 * @author Administrator 2017年3月16日 上午11:17:48
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Processor {

    String bussinessEnum();

}
