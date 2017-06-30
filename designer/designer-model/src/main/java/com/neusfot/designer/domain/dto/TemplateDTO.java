package com.neusfot.designer.domain.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * 类StyleTmpVO.java的实现描述：
 * 
 * @author Mike 2017年4月13日 下午10:03:45 { "id" //模版id "name" //模版名称 "type" //样式模版类型(卡片、列表) "tags": [ { "id" //页签ID "code"
 * //页签编码 "name" //页签名称 "displayName" //页签显示名称 "position" //位置0表头1表体2表尾 "index" //页签显示顺序 "description" //描述信息
 * "attributes": [ { "id" //单据模版字段id "seq" //顺序 "name" //属性名称 "displayName" //属性显示名称 "width" //宽度 "isReadonly":"" //是否只读
 * "isRequired":"" //是否必填 "isUnique":"" //是否唯一值 "dataType":"" //数据类型 "typeValue":"" //数据值 "fieldName":"" //字段路径
 * "isDatafilter":""//是否数据权限控制 "isFixedlength":""//是否固定长度 "description":""//字段描述 "refAttribute":""//属性表gid } ] } ] }
 */
@Data
public class TemplateDTO implements Serializable {

    private static final long serialVersionUID = 6403771691721498930L;

    private String            id;

    private String            objId;                                  // 对象ID
    
    private String            tempId;                                  //模板Id

    private String            code;                                   // 样式模版code

    private String            name;

    private Integer           type;

    private String            description;                            // 样式模版描述

    private Boolean           df;

}
