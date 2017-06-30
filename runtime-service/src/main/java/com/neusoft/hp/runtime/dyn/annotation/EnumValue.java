package com.neusoft.hp.runtime.dyn.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类EnumValue.java的实现描述：显示时用到，枚举值
 * 
 * @author Administrator 2017年4月21日 下午2:38:04
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EnumValue {

    /**
     * 枚举key
     * 
     * @return
     */
    String key();

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
