package com.neusoft.hp.runtime.dyn;

import java.lang.reflect.Field;

import lombok.Data;

/**
 * 类AttributeBean.java的实现描述：模板描述信息
 * 
 * @author Administrator 2017年4月18日 下午2:48:23
 */
@Data
public class AttributeBean {

    /**
     * 基准字段
     */
    private Field  field;

    /**
     * 引用字段的路径A$a,B$b,C$c用逗号分割对象
     */
    private String path;

    /**
     * 前端传过来的显示名称
     */
    private String displayName;

    private String sorted;     // asc desc

    private String tableName;

    private String columnName;
}
