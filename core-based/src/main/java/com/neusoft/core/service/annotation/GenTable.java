package com.neusoft.core.service.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.neusoft.core.dao.GenDO;
import com.neusoft.core.dao.GenDao;
import com.neusoft.core.dao.GenExample;

/**
 * 类GenTable.java的实现描述：myBatis工具生成实体后会给实体类加这个注解
 * 
 * @author Administrator 2017年4月18日 下午2:45:35
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface GenTable {

    /**
     * 对象id
     * 
     * @return
     */
    String objId();

    /**
     * 实体对应的dao层接口
     * 
     * @return
     */
    Class<? extends GenDao> mapperClass();

    /**
     * 数据库表名
     * 
     * @return
     */
    String tableName();

    /**
     * 实体id
     * 
     * @return
     */
    String gid();

    /**
     * 父实体
     * 
     * @return
     */
    Class<? extends GenDO> parentClass() default GenDO.class;

    /**
     * 与父实体关联的外键，目前外键的命名规则是父实体表名+GID
     * 
     * @return
     */
    String refKey() default "";

    Class<? extends GenExample> exampleClass();

    /**
     * 是否是单表
     * 
     * @return
     */
    boolean isSingle() default true;

    String owner() default "";
}
