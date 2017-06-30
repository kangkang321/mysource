package com.neusfot.designer.domain.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class TemplateVO implements Serializable {

    private static final long serialVersionUID = 6403771691721498930L;

    private String            id;

    private String            objId;                                  // 对象ID
    
    private String           tempId;                               // 模板id

    private String            code;                                   // 样式模版code

    private String            name;

    private Integer           type;

    private String            description;                            // 样式模版描述
    
    private Boolean           df;                                     // 是否默认

    private Object            columns;

}
