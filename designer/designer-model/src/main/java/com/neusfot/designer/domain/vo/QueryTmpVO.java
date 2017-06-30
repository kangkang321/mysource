package com.neusfot.designer.domain.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * 类QueryTmpVO.java的实现描述：用于接收前端传递查询模块Json
 * 
 * @author Mike 2017年4月13日 下午10:08:38 { "id" //查询模版id "name" //查询模版名称 "attributes": [ { "id" // "seq" //顺序 "attrCode"
 * //属性编码 "attrName" //属性名称 "attrDisplayName" //显示名称 "isFilterable" //0不可筛选，1可筛选，仅枚举属性可被筛选 "isQuery" //0不可模糊查询，1可以模糊查询
 * "sels":[{"key","value"}]//筛选时的下拉} ] }
 */
@Data
public class QueryTmpVO implements Serializable {

    private static final long serialVersionUID = -3590378061502216613L;

    private String            id;

    private String            objId;                                   // 对象ID

    private String            code;                                    // 查询模版编码

    private String            name;

    private String            description;                             // 查询模版描述

    private Boolean           df;                                      // 是否默认

    private Object            columns;

}
