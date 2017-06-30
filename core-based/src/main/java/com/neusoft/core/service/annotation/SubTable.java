package com.neusoft.core.service.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.neusoft.core.dao.GenDO;

/**
 * 类SubTable.java的实现描述：myBatis工具生成实体后会给父实体类里子实体加这个注解
 * 
 * @author Administrator 2017年4月18日 下午2:47:18
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SubTable {

    /**
     * 子实体类名
     * 
     * @return
     */
    Class<? extends GenDO> subClass();
}
