package com.neusfot.designer.domain.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 类ObjectEntityVO.java的实现描述：
 * 
 * @author Mike 2017年4月14日 上午10:46:36 { "id" //实体id "code" //实体编码 "name" //实体名称 "displayName" //显示名称 "parentName"//父实体名称
 * "tableName"//表名 "className"//类名 "description",//描述信息 "isPrimary":"",//是否父实体 "parentGid": "",//父实体Gid "attributes":
 * [//对应的属性数组 { "id": "",//属性ID "code": "",//属性编码 "name": ""//属性名称 "fieldName":""//字段名称 "displayName":""//显示名称
 * "dataType":""//数据类型 "isFixedlength":""//是否固定长度 "typeValue":""//数据值 "isRequired":""//是否必须 "isReadonly":""//是否只读
 * "isDatafilter":""//是否数据权限控制 "isUnique":""//是否唯一 "description":""//字段描述 "clazz":""//类名 "field":""//字段名 } ] }
 */
@Data
public class ObjectEntityVO implements Serializable {

    private static final long        serialVersionUID = -8102232356054988955L;

    private String                   id;

    private String                   code;

    private String                   name;

    private String                   displayName;

    private String                   parentName;

    private String                   tableName;

    private String                   className;

    private String                   description;

    private Boolean                  isPrimary;

    private String                   parentGid;

    private List<ObjectEntityAttrVO> attributes;

}
