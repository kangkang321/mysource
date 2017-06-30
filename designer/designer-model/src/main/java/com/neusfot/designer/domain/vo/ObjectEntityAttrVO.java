package com.neusfot.designer.domain.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * 类ObjectEntityAttrVO.java的实现描述：
 * 
 * @author Mike 2017年4月14日 上午10:46:52 * { "id": "",//属性ID "code": "",//属性编码 "name": ""//属性名称 "fieldName":""//字段名称
 * "displayName":""//显示名称 "dataType":""//数据类型 "isFixedlength":""//是否固定长度 "typeValue":""//数据值 "isRequired":""//是否必须
 * "isReadonly":""//是否只读 "isDatafilter":""//是否数据权限控制 "isUnique":""//是否唯一 "description":""//字段描述 "clazz":""//类名
 * "field":""//字段名 }
 */
@Data
public class ObjectEntityAttrVO implements Serializable {

    private static final long serialVersionUID = -578983742294712452L;

    private String            id;

    private String            code;

    private String            name;

    private String            fieldName;

    private String            displayName;

    private String            dataType;

    private Boolean           isFixedlength;

    private String            typeValue;

    private Boolean           isRequired;

    private Boolean           isReadonly;

    private Boolean           isDatafilter;

    private Boolean           isUnique;

    private String            description;

    private String            clazz;

    private String            field;
}
